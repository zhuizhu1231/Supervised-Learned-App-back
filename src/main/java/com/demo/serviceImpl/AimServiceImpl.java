package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.AimMapper;
import com.demo.entities.Aim;
import com.demo.service.IAimService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AimServiceImpl extends ServiceImpl<AimMapper, Aim> implements IAimService {
    @Override
    public List<Aim> queryAimByUserId(Integer id) {
        return baseMapper.queryAimByUserId(id);
    }

    @Override
    public Timestamp maxTimeStampByUserId(Integer id) {
        return baseMapper.maxTimeStampByUserId(id);
    }

    @Override
    public List<Aim> queryAimByUserIdAndTimeStamp(int userId, Timestamp timestamp) {
        return baseMapper.queryAimByUserIdAndTimeStamp(userId, timestamp);
    }
}
