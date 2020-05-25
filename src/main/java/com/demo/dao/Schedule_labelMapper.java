package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Schedule_label;

import java.sql.Timestamp;
import java.util.List;

public interface Schedule_labelMapper extends BaseMapper<Schedule_label> {
    List<Schedule_label> queryScheduleLabelByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Schedule_label> queryScheduleLabelByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
