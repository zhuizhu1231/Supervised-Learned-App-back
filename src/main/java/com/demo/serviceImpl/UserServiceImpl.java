package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.UserMapper;
import com.demo.entities.User;
import com.demo.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public User login(Integer id, String password) {
        return this.baseMapper.selectUserByIdAndPassword(id, password);
    }
}
