package com.zhy.gateway.service;

import com.zhy.gateway.feign.UserFeign;
import com.zhy.gateway.model.User;
import com.zhy.gateway.vo.ResponseVO;
import com.zhy.gateway.vo.ResultVO;
import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 调用Feign请求失败时，回滚
 * 用于服务之间调用，即没有在配置文件里配置过的路由，例如登录请求
 */
@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserFeign> {
    @Autowired
    private ResponseVO responseVO;

    @Override
    public UserFeign create(Throwable throwable) {
        return new UserFeign(){
            @Override
            public ResultVO<User> getUser(String name) {
                return responseVO.error(501, "The server is unavailable.");
            }
        };
    }
}
