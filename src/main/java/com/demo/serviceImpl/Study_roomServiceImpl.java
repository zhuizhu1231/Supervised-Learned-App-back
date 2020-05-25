package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.Study_roomMapper;
import com.demo.entities.Study_room;
import com.demo.service.IStudy_roomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Study_roomServiceImpl extends ServiceImpl<Study_roomMapper, Study_room> implements IStudy_roomService {
    @Override
    public List<Study_room> queryRoomByNameLike(String roomName) {
        return baseMapper.queryRoomByNameLike("%" + roomName + "%");
    }
}
