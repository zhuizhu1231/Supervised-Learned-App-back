package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Schedule_in_label;

import java.sql.Timestamp;
import java.util.List;

public interface ISchedule_in_labelService extends IService<Schedule_in_label> {
    List<Schedule_in_label> queryNotesRelationByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Schedule_in_label> queryNotesRelationByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
