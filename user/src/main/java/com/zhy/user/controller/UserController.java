package com.zhy.user.controller;

import com.zhy.user.model.User;
import com.zhy.user.vo.ResponseVO;
import com.zhy.user.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private ResponseVO responseVO;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO<User> add(@RequestBody User user) {
        user.setId(2);
        return responseVO.success(user);
    }
}
