package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Friend;

import java.sql.Timestamp;
import java.util.List;

public interface FriendMapper extends BaseMapper<Friend> {
    List<Friend> queryFriendByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);
}
