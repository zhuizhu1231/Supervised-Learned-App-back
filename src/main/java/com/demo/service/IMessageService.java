package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Message;

import java.sql.Timestamp;
import java.util.List;

public interface IMessageService extends IService<Message> {
    List<Message> queryMessageByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);
}
