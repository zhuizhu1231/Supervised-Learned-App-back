package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.User_in_study_room;

import java.util.List;

public interface User_in_study_roomMapper extends BaseMapper<User_in_study_room> {
    List<User_in_study_room> queryRelationByUserId(Integer id);

    List<User_in_study_room> queryRelationByRoomId(Integer id);
}
