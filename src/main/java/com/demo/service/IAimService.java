package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Aim;

import java.sql.Timestamp;
import java.util.List;

public interface IAimService extends IService<Aim> {
    List<Aim> queryAimByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Aim> queryAimByUserIdAndTimeStamp(int userId, Timestamp timestamp);
}
