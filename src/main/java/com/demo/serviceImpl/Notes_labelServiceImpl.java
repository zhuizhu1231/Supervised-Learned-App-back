package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.Notes_labelMapper;
import com.demo.entities.Notes_label;
import com.demo.service.INotes_labelService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class Notes_labelServiceImpl extends ServiceImpl<Notes_labelMapper, Notes_label> implements INotes_labelService {
    @Override
    public List<Notes_label> queryNotesLabelByUserId(Integer id) {
        return baseMapper.queryNotesLabelByUserId(id);
    }

    @Override
    public Timestamp maxTimeStampByUserId(Integer id) {
        return baseMapper.maxTimeStampByUserId(id);
    }

    @Override
    public List<Notes_label> queryNotesLabelByUserIdAndTimeStamp(int userId, Timestamp timestamp) {
        return baseMapper.queryNotesLabelByUserIdAndTimeStamp(userId, timestamp);
    }
}
