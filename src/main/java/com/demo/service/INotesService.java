package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Notes;

import java.sql.Timestamp;
import java.util.List;

public interface INotesService extends IService<Notes> {
    List<Notes> queryNotesByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Notes> queryNotesByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
