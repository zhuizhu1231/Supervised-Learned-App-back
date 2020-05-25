package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Notes_in_label;

import java.sql.Timestamp;
import java.util.List;

public interface INotes_in_labelService extends IService<Notes_in_label> {
    List<Notes_in_label> queryNotesRelationByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Notes_in_label> queryNotesRelationByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
