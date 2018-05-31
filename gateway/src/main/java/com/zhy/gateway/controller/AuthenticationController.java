package com.zhy.gateway.controller;

import com.zhy.gateway.model.User;
import com.zhy.gateway.service.AuthenticationService;
import com.zhy.gateway.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证
 */
@Api(value = "认证")
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
    @ApiOperation(value = "登录", notes = "用户登录")
    @ApiImplicitParam(name = "user", value = "用户实例", required = true, paramType = "body", dataType = "User")
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
    @ApiOperation(value = "退出登录", notes = "用户退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "user", value = "用户实例", required = true, paramType = "body", dataType = "User")
    })
    @RequestMapping(value = "/login-out", method = RequestMethod.POST)
    public ResultVO loginOut(@RequestHeader("token") String token, @RequestBody User user) {
        return authenticationService.loginOut(token, user);
    }
}
