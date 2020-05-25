package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Study_room_message;

import java.sql.Timestamp;
import java.util.List;

public interface IStudy_room_messageService extends IService<Study_room_message> {
    List<Study_room_message> queryStudyRoomMessageByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);
}
