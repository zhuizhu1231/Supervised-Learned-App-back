package com.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.NotesMapper;
import com.demo.entities.Notes;
import com.demo.service.INotesService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class NotesServiceImpl extends ServiceImpl<NotesMapper, Notes> implements INotesService {
    @Override
    public List<Notes> queryNotesByUserId(Integer id) {
        return baseMapper.queryNotesByUserId(id);
    }

    @Override
    public Timestamp maxTimeStampByUserId(Integer id) {
        return baseMapper.maxTimeStampByUserId(id);
    }

    @Override
    public List<Notes> queryNotesByUserIdAndTimeStamp(int userId, Timestamp timestamp) {
        return baseMapper.queryNotesByUserIdAndTimeStamp(userId, timestamp);
    }
}
