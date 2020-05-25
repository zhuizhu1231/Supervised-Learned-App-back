package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.Message;

import java.sql.Timestamp;
import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
    List<Message> queryMessageByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);
}
