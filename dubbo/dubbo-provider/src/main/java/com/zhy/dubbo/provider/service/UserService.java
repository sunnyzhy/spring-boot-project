package com.zhy.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zhy.dubbo.api.model.User;
import com.zhy.dubbo.api.service.IUserService;

/**
 * @author zhy
 * @date 2018/8/18 15:45
 **/
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class UserService implements IUserService {
    @Override
    public User getUser(User user) {
        user.setId((int) (Math.random() * 100));
        return user;
    }
}
