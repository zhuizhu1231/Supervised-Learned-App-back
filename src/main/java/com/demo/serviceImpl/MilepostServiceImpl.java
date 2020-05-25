package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.MilePostMapper;
import com.demo.entities.Milepost;
import com.demo.service.IMilePostService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MilepostServiceImpl extends ServiceImpl<MilePostMapper, Milepost> implements IMilePostService {
    @Override
    public List<Milepost> queryMilepostByUserId(Integer id) {
        return baseMapper.queryMilepostByUserId(id);
    }

    @Override
    public Timestamp maxTimeStampByUserId(Integer id) {
        return baseMapper.maxTimeStampByUserId(id);
    }

    @Override
    public List<Milepost> queryMilepostByUserIdAndTimestamp(int userId, Timestamp timestamp) {
        return baseMapper.queryMilepostByUserIdAndTimestamp(userId, timestamp);
    }
}
