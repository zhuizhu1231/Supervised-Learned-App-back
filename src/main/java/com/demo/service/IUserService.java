package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.User;

public interface IUserService extends IService<User> {
    User login(Integer id, String password);
}
