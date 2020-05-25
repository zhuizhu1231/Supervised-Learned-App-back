package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Schedule;

import java.sql.Timestamp;
import java.util.List;

public interface IScheduleService extends IService<Schedule> {
    List<Schedule> queryScheduleByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Schedule> queryScheduleByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
