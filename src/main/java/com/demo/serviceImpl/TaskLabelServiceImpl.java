package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.TaskLabelMapper;
import com.demo.entities.TaskLabel;
import com.demo.service.ITaskLabelService;
import org.springframework.stereotype.Service;

@Service
public class TaskLabelServiceImpl extends ServiceImpl<TaskLabelMapper, TaskLabel> implements ITaskLabelService {
//    @Override
//    public List<TaskLabel> queryTaskLabelByUserId(Integer id) {
//        return baseMapper.queryTaskLabelByUserId(id);
//    }
//
//    @Override
//    public Timestamp maxTimeStampByUserId(Integer id) {
//        return baseMapper.maxTimeStampByUserId(id);
//    }
//
//    @Override
//    public List<TaskLabel> queryTaskLabelByUserIdAndTimeStamp(int userId, Timestamp timestamp) {
//        return baseMapper.queryTaskLabelByUserIdAndTimeStamp(userId, timestamp);
//    }
}
