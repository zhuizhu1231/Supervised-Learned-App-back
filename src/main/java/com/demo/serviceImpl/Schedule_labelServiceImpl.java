package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.Schedule_labelMapper;
import com.demo.entities.Schedule_label;
import com.demo.service.ISchedule_labelService;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class Schedule_labelServiceImpl extends ServiceImpl<Schedule_labelMapper, Schedule_label> implements ISchedule_labelService {
    @Override
    public List<Schedule_label> queryScheduleLabelByUserId(Integer id) {
        return baseMapper.queryScheduleLabelByUserId(id);
    }

    @Override
    public Timestamp maxTimeStampByUserId(Integer id) {
        return baseMapper.maxTimeStampByUserId(id);
    }

    @Override
    public List<Schedule_label> queryScheduleLabelByUserIdAndTimeStamp(int userId, Timestamp timestamp) {
        return baseMapper.queryScheduleLabelByUserIdAndTimeStamp(userId, timestamp);
    }
}
