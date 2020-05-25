package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.Study_room_messageMapper;
import com.demo.entities.Study_room_message;
import com.demo.service.IStudy_room_messageService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class Study_room_messageServiceImpl extends ServiceImpl<Study_room_messageMapper, Study_room_message> implements IStudy_room_messageService {
    @Override
    public List<Study_room_message> queryStudyRoomMessageByUserId(Integer id) {
        return baseMapper.queryStudyRoomMessageByUserId(id);
    }

    @Override
    public Timestamp maxTimeStampByUserId(Integer id) {
        return baseMapper.maxTimeStampByUserId(id);
    }
}
