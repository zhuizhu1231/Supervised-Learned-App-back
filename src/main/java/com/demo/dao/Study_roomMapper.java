package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Study_room;

import java.util.List;

public interface Study_roomMapper extends BaseMapper<Study_room> {
    List<Study_room> queryRoomByNameLike(String name);
}
