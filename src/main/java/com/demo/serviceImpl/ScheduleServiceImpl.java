package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.ScheduleMapper;
import com.demo.entities.Schedule;
import com.demo.service.IScheduleService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements IScheduleService {
    @Override
    public List<Schedule> queryScheduleByUserId(Integer id) {
        return baseMapper.queryScheduleByUserId(id);
    }

    @Override
    public Timestamp maxTimeStampByUserId(Integer id) {
        return baseMapper.maxTimeStampByUserId(id);
    }

    @Override
    public List<Schedule> queryScheduleByUserIdAndTimeStamp(int userId, Timestamp timestamp) {
        return baseMapper.queryScheduleByUserIdAndTimeStamp(userId, timestamp);
    }
}
