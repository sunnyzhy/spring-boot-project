package com.zhy.user.service;

import com.github.pagehelper.PageHelper;
import com.zhy.user.mapper.TUserMapper;
import com.zhy.user.model.TUser;
import com.zhy.user.utils.PageVoUtil;
import com.zhy.user.utils.ResponseVoUtil;
import com.zhy.user.vo.ResponseVO;
import com.zhy.user.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * UserService
 */
@Service
public class UserService {
    @Autowired
    private TUserMapper userMapper;
    @Autowired
    private ResponseVoUtil responseVoUtil;
    @Autowired
    private PageVoUtil pageVoUtil;

    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     * @return
     */
    public ResponseVO<TUser> getUser(String userName) {
        TUser user = new TUser();
        user.setUserName(userName);
        TUser one = userMapper.selectOne(user);
        if (one == null) {
            return responseVoUtil.error(-1, "用户名不存在");
        }
        return responseVoUtil.success(one);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    public ResponseVO<TUser> add(TUser user) {
        TUser validateUser = new TUser();
        validateUser.setUserName(user.getUserName());
        if (userMapper.selectOne(validateUser) == null) {
            if (user.getId() != null) {
                user.setId(null);
            }
            userMapper.insertSelective(user);
            return responseVoUtil.success(user);
        } else {
            return responseVoUtil.error(-1, "用户名已存在");
        }
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    public ResponseVO<TUser> update(TUser user) {
        if (userMapper.selectByPrimaryKey(user) == null) {
            return responseVoUtil.error(-1, "该用户不存在");
        } else {
            user.setUserName(null);
            userMapper.updateByPrimaryKeySelective(user);
            return responseVoUtil.success(user);
        }
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    public ResponseVO<Integer> delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
        return responseVoUtil.success(id);
    }

    /**
     * 查询分页用户
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ResponseVO<PageVO<TUser>> selectPage(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(TUser.class);
        if (!StringUtils.isEmpty(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
        }
        List<TUser> userList = userMapper.selectByExample(example);
        return responseVoUtil.success(pageVoUtil.create(userList));
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    public ResponseVO<PageVO<TUser>> selectAll() {
        List<TUser> userList = userMapper.selectAll();
        return responseVoUtil.success(pageVoUtil.create(userList));
    }
}
