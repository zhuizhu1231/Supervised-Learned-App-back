package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.Notes_in_labelMapper;
import com.demo.entities.Notes_in_label;
import com.demo.service.INotes_in_labelService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class Notes_in_labelServiceImpl extends ServiceImpl<Notes_in_labelMapper, Notes_in_label> implements INotes_in_labelService {
    @Override
    public List<Notes_in_label> queryNotesRelationByUserId(Integer id) {
        return baseMapper.queryNotesRelationByUserId(id);
    }

    @Override
    public Timestamp maxTimeStampByUserId(Integer id) {
        return baseMapper.maxTimeStampByUserId(id);
    }

    @Override
    public List<Notes_in_label> queryNotesRelationByUserIdAndTimeStamp(int userId, Timestamp timestamp) {
        return baseMapper.queryNotesRelationByUserIdAndTimeStamp(userId, timestamp);
    }
}
