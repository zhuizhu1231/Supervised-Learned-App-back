package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.TaskMapper;
import com.demo.entities.Task;
import com.demo.service.ITaskService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {
    @Override
    public List<Task> queryTaskByUserId(Integer id) {
        return baseMapper.queryTaskByUserId(id);
    }

    @Override
    public Timestamp maxTimeStampByUserId(Integer id) {
        return baseMapper.maxTimeStampByUserId(id);
    }

    @Override
    public List<Task> queryTaskByUserIdAndTimeStamp(int userId, Timestamp timestamp) {
        return baseMapper.queryTaskByUserIdAndTimeStamp(userId, timestamp);
    }
}
