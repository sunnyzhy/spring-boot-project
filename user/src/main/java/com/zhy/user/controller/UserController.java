package com.zhy.user.controller;

import com.zhy.user.model.TUser;
import com.zhy.user.service.UserService;
import com.zhy.user.vo.ResponseVO;
import com.zhy.user.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User接口
 */
@Api(value = "UserController")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "add", notes = "添加用户")
    @ApiImplicitParam(name = "user", value = "用户实例", required = true, paramType = "body", dataType = "TUser")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseVO<TUser> add(@RequestBody TUser user) {
        return userService.add(user);
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "update", notes = "修改用户")
    @ApiImplicitParam(name = "user", value = "用户实例", required = true, paramType = "body", dataType = "TUser")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseVO<TUser> update(@RequestBody TUser user) {
        return userService.update(user);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "delete", notes = "删除用户")
    @ApiImplicitParam(name = "id", value = "用户的记录id", required = true, paramType = "path", dataType = "Integer")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseVO<Integer> delete(@PathVariable("id") Integer id) {
        return userService.delete(id);
    }

    /**
     * 查询分页用户
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "selectPage", notes = "查询分页用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "分页的页号", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的记录数", required = true, paramType = "path", dataType = "Integer")
    })
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseVO<PageVO<TUser>> selectPage(@RequestParam("name") String name,
                                         @RequestParam("num") Integer pageNum,
                                         @RequestParam("size") Integer pageSize) {
        return userService.selectPage(name, pageNum, pageSize);
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @ApiOperation(value = "selectAll", notes = "查询所有用户")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseVO<PageVO<TUser>> selectAll() {
        return userService.selectAll();
    }
}
