package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.User_in_study_room;

import java.util.List;

public interface IUser_in_study_roomService extends IService<User_in_study_room> {
    List<User_in_study_room> queryRelationByUserId(Integer id);

    List<User_in_study_room> queryRelationByRoomId(Integer id);
}
