package com.zhy.dubbo.consumer.controller;

import com.zhy.dubbo.api.model.User;
import com.zhy.dubbo.consumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhy
 * @date 2018/8/18 15:49
 **/
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{name}/{phone}", method = RequestMethod.GET)
    public User getUser(@PathVariable("name") String name,
                        @PathVariable("phone") String phone) {
        return userService.getUser(name, phone);
    }
}
