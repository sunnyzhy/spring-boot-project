package com.zhy.gateway.feign;

import com.zhy.gateway.model.User;
import com.zhy.gateway.service.UserServiceFallbackFactory;
import com.zhy.gateway.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * user-service的feign接口
 */
@Api(value = "user-service的feign接口")
@FeignClient(value = "user-server", fallbackFactory = UserServiceFallbackFactory.class)
@RequestMapping(value = "/api")
public interface UserFeign {
    @ApiOperation(value = "查询用户信息", notes = "根据用户名查询用户信息")
    @ApiImplicitParam(name = "name", value = "登录的用户名", required = true, paramType = "path", dataType = "String")
    @RequestMapping(value = "/user/{name}", method = RequestMethod.GET)
    ResultVO<User> getUser(@PathVariable("name") String name);
}
