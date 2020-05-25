package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Study_room_message;

import java.sql.Timestamp;
import java.util.List;

public interface Study_room_messageMapper extends BaseMapper<Study_room_message> {
    List<Study_room_message> queryStudyRoomMessageByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);
}
