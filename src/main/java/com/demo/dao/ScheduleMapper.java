package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Schedule;

import java.sql.Timestamp;
import java.util.List;

public interface ScheduleMapper extends BaseMapper<Schedule> {
    List<Schedule> queryScheduleByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Schedule> queryScheduleByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
