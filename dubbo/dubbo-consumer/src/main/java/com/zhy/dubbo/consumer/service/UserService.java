package com.zhy.dubbo.consumer.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhy.dubbo.api.model.User;
import com.zhy.dubbo.api.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author zhy
 * @date 2018/8/18 15:50
 **/
@Service
public class UserService {
    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:" + "${dubbo.protocol.port}")
    private IUserService userService;

    public User getUser(String name, String phone) {
        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        return userService.getUser(user);
    }
}
