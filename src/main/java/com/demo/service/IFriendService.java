package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Friend;

import java.sql.Timestamp;
import java.util.List;

public interface IFriendService extends IService<Friend> {
    List<Friend> queryFriendByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);
}
