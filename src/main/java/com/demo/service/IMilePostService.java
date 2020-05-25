package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entities.Milepost;

import java.sql.Timestamp;
import java.util.List;

public interface IMilePostService extends IService<Milepost> {
    List<Milepost> queryMilepostByUserId(Integer id);

    Timestamp maxTimeStampByUserId(Integer id);

    List<Milepost> queryMilepostByUserIdAndTimestamp(int userId, Timestamp timestamp);
}
