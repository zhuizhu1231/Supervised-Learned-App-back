package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Task;

import java.sql.Timestamp;
import java.util.List;

public interface TaskMapper extends BaseMapper<Task> {
    List<Task> queryTaskByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Task> queryTaskByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
