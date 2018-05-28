package com.zhy.gateway.controller;

import com.zhy.gateway.model.User;
import com.zhy.gateway.service.AuthenticationService;
import com.zhy.gateway.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证
 */
@RestController
@RequestMapping(value = "/authen")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * 登录
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultVO login(@RequestBody User user) {
        return authenticationService.login(user);
    }

    /**
     * 退出登录
     * @param token
     * @param user
     * @return
     */
    @RequestMapping(value = "/login-out", method = RequestMethod.POST)
    public ResultVO loginOut(@RequestHeader("token") String token, @RequestBody User user) {
        return authenticationService.loginOut(token, user);
    }
}
