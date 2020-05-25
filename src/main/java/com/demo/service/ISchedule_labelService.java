package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Schedule_label;

import java.sql.Timestamp;
import java.util.List;

public interface ISchedule_labelService extends IService<Schedule_label> {
    List<Schedule_label> queryScheduleLabelByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Schedule_label> queryScheduleLabelByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
