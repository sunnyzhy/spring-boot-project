package com.zhy.gateway.jwt;

import com.zhy.gateway.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用JWT生成token，并做登录验证
 */
@Component
public class Jwt {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private final String KEY = "z*h%&y_86";
    private final String CLAIM_KEY_ID = "id";
    private final String CLAIM_KEY_NAME = "name";
    private final String CLAIM_KEY_UUID = "uuid";
    private final String CLAIM_KEY_CREATED = "created";

    /**
     * 登录时，生成token
     * @param user
     * @return
     */
    public String createToken(User user) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_ID, user.getId());
        claims.put(CLAIM_KEY_NAME, user.getName());
        claims.put(CLAIM_KEY_UUID, uuid);
        claims.put(CLAIM_KEY_CREATED, new Date());

        //  生成token
        String token = Jwts.builder().
                setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 7200 * 1000))
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();

        //  把token保存到redis
        String key = String.format("token:%s:%s", user.getName(), uuid);
        redisTemplate.opsForValue().set(key, token);

        return token;
    }

    /**
     * 请求被路由时，检验token
     * @param token
     * @return
     */
    public boolean checkToken(String token) {
        if (token == null || token.length() == 0) {
            return false;
        }

        //  解析token
        Claims claims = getClaims(token);
        if (claims == null) {
            return false;
        }

        //  比较http header里的token与redis里存储的token
        String key = String.format("token:%s:%s",
                claims.get(CLAIM_KEY_NAME).toString(),
                claims.get(CLAIM_KEY_UUID).toString());
        String redisToken = redisTemplate.opsForValue().get(key);
        if (redisToken == null || !redisToken.equals(token)) {
            return false;
        }

        //  判断token是否过期
        if (claims.getExpiration().before(new Date())) {
            return false;
        }

        return true;
    }

    /**
     * 根据token，获取用户信息
     * @param token
     * @return
     */
    public User getUserFromToken(String token) {
        //  解析token
        Claims claims = getClaims(token);
        if (claims == null) {
            return null;
        }

        try {
            User user = new User();
            user.setName(claims.get(CLAIM_KEY_NAME).toString());
            user.setId(Integer.parseInt(claims.get(CLAIM_KEY_ID).toString()));
            user.setUuid(claims.get(CLAIM_KEY_UUID).toString());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退出登录时，使token失效
     * @param token
     */
    public void invalidToken(String token) {
        //  解析token
        Claims claims = getClaims(token);
        if (claims == null) {
            return;
        }

        //  删除redis里存储的token
        String key = String.format("token:%s:%s",
                claims.get(CLAIM_KEY_NAME).toString(),
                claims.get(CLAIM_KEY_UUID).toString());
        redisTemplate.delete(key);
    }
}
