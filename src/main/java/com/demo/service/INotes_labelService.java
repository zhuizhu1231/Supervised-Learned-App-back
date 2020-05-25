package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Notes_label;

import java.sql.Timestamp;
import java.util.List;

public interface INotes_labelService extends IService<Notes_label> {
    List<Notes_label> queryNotesLabelByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Notes_label> queryNotesLabelByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
