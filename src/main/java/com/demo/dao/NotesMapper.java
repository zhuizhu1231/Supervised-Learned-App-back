package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Notes;

import java.sql.Timestamp;
import java.util.List;

public interface NotesMapper extends BaseMapper<Notes> {

    List<Notes> queryNotesByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Notes> queryNotesByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
