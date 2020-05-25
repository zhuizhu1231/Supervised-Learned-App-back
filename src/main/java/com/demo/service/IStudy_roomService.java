package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Study_room;

import java.util.List;

public interface IStudy_roomService extends IService<Study_room> {
    List<Study_room> queryRoomByNameLike(String roomName);
}
