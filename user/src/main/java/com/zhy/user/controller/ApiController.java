package com.zhy.user.controller;

import com.zhy.user.model.User;
import com.zhy.user.vo.ResponseVO;
import com.zhy.user.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiController {
    @Autowired
    private ResponseVO responseVO;

    @RequestMapping(value = "/user/{name}", method = RequestMethod.GET)
    public ResultVO<User> getUser(@PathVariable("name") String name) {
        User user = new User();
        user.setId(1);
        user.setName(name);
        user.setPassword("admin");
        return responseVO.success(user);
    }
}
