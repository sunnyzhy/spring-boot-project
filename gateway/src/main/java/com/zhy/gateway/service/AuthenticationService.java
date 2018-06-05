package com.zhy.gateway.service;

import com.zhy.gateway.feign.UserFeign;
import com.zhy.gateway.jwt.Jwt;
import com.zhy.gateway.model.TUser;
import com.zhy.gateway.model.User;
import com.zhy.gateway.vo.ResponseVO;
import com.zhy.gateway.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 认证服务
 */
@Service
public class AuthenticationService {
    @Autowired
    private ResponseVO responseVO;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private Jwt jwt;

    public ResultVO login(User user) {
        ResultVO<TUser> resultVO = userFeign.getUser(user.getName());
        if (resultVO.getCode() != 0) {
            return resultVO;
        }
        TUser userVO = resultVO.getData();
        User usr = new User();
        usr.setId(userVO.getId());
        usr.setName(userVO.getName());
        usr.setPassword(userVO.getPassword());
        if (userVO.getPassword().equals(user.getPassword())) {
            return responseVO.success(jwt.createToken(usr));
        } else {
            return responseVO.error(-1, "用户名或密码错误!");
        }
    }

    public ResultVO loginOut(String token, User user) {
        if (!jwt.checkToken(token)) {
            return responseVO.error(401, "token error!");
        }
        User u = jwt.getUserFromToken(token);
        if (u == null) {
            return responseVO.error(401, "token error!");
        }
        if (!u.getName().equals(user.getName())) {
            return responseVO.error(401, "token error!");
        }
        jwt.invalidToken(token);
        return responseVO.success();
    }
}
