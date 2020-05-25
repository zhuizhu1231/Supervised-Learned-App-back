package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.User;

//@Mapper配置映射接口
public interface UserMapper extends BaseMapper<User> {

    int insertUser(User user);

    User selectUserByIdAndPassword(Integer id, String password);

}
