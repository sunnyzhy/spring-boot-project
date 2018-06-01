package com.zhy.user.controller;

import com.zhy.user.model.TUser;
import com.zhy.user.service.UserService;
import com.zhy.user.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * API接口
 */
@Api(value = "ApiController")
@RestController
@RequestMapping(value = "/api")
public class ApiController {
    @Autowired
    private UserService userService;

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    @ApiOperation(value = "getUser", notes = "根据用户名获取用户信息")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "path", dataType = "String")
    @RequestMapping(value = "/user/{name}", method = RequestMethod.GET)
    public ResponseVO<TUser> getUser(@PathVariable("name") String userName) {
        return userService.getUser(userName);
    }
}
