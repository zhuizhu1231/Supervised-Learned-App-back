package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.User_in_study_roomMapper;
import com.demo.entities.User_in_study_room;
import com.demo.service.IUser_in_study_roomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User_in_study_roomServiceImpl extends ServiceImpl<User_in_study_roomMapper, User_in_study_room> implements IUser_in_study_roomService {
    @Override
    public List<User_in_study_room> queryRelationByUserId(Integer id) {
        return baseMapper.queryRelationByUserId(id);
    }

    @Override
    public List<User_in_study_room> queryRelationByRoomId(Integer id) {
        return baseMapper.queryRelationByRoomId(id);
    }
}
