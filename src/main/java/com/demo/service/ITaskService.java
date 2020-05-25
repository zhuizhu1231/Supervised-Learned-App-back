package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Task;

import java.sql.Timestamp;
import java.util.List;

public interface ITaskService extends IService<Task> {
    List<Task> queryTaskByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Task> queryTaskByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
