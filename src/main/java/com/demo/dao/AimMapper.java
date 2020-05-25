package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Aim;

import java.sql.Timestamp;
import java.util.List;

public interface AimMapper extends BaseMapper<Aim> {

    List<Aim> queryAimByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Aim> queryAimByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
