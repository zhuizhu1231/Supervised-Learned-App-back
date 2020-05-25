package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Notes_in_label;

import java.sql.Timestamp;
import java.util.List;

public interface Notes_in_labelMapper extends BaseMapper<Notes_in_label> {
    List<Notes_in_label> queryNotesRelationByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Notes_in_label> queryNotesRelationByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
