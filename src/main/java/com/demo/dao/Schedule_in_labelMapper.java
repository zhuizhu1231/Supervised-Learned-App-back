package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Schedule_in_label;

import java.sql.Timestamp;
import java.util.List;

public interface Schedule_in_labelMapper extends BaseMapper<Schedule_in_label> {
    List<Schedule_in_label> queryNotesRelationByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Schedule_in_label> queryNotesRelationByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
