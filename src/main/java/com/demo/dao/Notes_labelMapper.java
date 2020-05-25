package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Notes_label;

import java.sql.Timestamp;
import java.util.List;

public interface Notes_labelMapper extends BaseMapper<Notes_label> {
    List<Notes_label> queryNotesLabelByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Notes_label> queryNotesLabelByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
