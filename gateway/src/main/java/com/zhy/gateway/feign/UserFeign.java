package com.zhy.gateway.feign;

import com.zhy.gateway.model.User;
import com.zhy.gateway.service.UserServiceFallbackFactory;
import com.zhy.gateway.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * user-service的feign接口
 */
@FeignClient(value = "user-server", fallbackFactory = UserServiceFallbackFactory.class)
@RequestMapping(value = "/api")
public interface UserFeign {
    @RequestMapping(value = "/user/{name}", method = RequestMethod.GET)
    ResultVO<User> getUser(@PathVariable("name") String name);
}
