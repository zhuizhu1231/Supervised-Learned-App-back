package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.FriendMapper;
import com.demo.entities.Friend;
import com.demo.service.IFriendService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements IFriendService {
    @Override
    public List<Friend> queryFriendByUserId(Integer id) {
        return baseMapper.queryFriendByUserId(id);
    }

    @Override
    public Timestamp maxTimeStampByUserId(Integer id) {
        return baseMapper.maxTimeStampByUserId(id);
    }
}
