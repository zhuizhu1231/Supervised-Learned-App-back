package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.Schedule_in_labelMapper;
import com.demo.entities.Schedule_in_label;
import com.demo.service.ISchedule_in_labelService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class Schedule_in_labelServiceImpl extends ServiceImpl<Schedule_in_labelMapper, Schedule_in_label> implements ISchedule_in_labelService {
    @Override
    public List<Schedule_in_label> queryNotesRelationByUserId(Integer id) {
        return baseMapper.queryNotesRelationByUserId(id);
    }

    @Override
    public Timestamp maxTimeStampByUserId(Integer id) {
        return baseMapper.maxTimeStampByUserId(id);
    }

    @Override
    public List<Schedule_in_label> queryNotesRelationByUserIdAndTimeStamp(int userId, Timestamp timestamp) {
        return baseMapper.queryNotesRelationByUserIdAndTimeStamp(userId, timestamp);
    }
}
