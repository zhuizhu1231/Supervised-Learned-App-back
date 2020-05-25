package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Milepost;

import java.sql.Timestamp;
import java.util.List;

public interface MilePostMapper extends BaseMapper<Milepost> {
    List<Milepost> queryMilepostByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Milepost> queryMilepostByUserIdAndTimestamp(int userId, Timestamp timestamp);
}
