package com.demo.converter;


import java.sql.Timestamp;
import java.util.Date;

public class MyConverter {


    public static Date dateToLong(Long value) {
        return value == null ? null : new Date(value);
    }


    public static Long dateToLong(Date date) {
        return date == null ? null : date.getTime();
    }


    public static Timestamp longToTimestamp(Long value) {
        return value == null ? null : new Timestamp(value);
    }


    public static Long timestampToLong(Timestamp date) {
        return date == null ? null : date.getTime();
    }


    public static Timestamp doubleToTimestamp(Double date) {
        return date == null ? null : new Timestamp(date.longValue());
    }


    public static int longToInt(long date) {
        return Integer.parseInt(String.valueOf(date));
    }


    public static Timestamp dateToTimeStamp(Date date) {
        return date == null ? null : new Timestamp(date.getTime());
    }


    public static Date timeStampToDate(Timestamp value) {
        return value == null ? null : new Date(value.getTime());
    }


//做一个客户端与服务端数据相互转换的转换器
//    @TypeConverter
//    public static LiveData<List<com.example.demo.data.entities.Task>> modelTaskToEntityTask(LiveData<List<Task>> tasks) {
//        List<Task> values = tasks.getValue();
//
//        LiveData<List<com.example.demo.data.entities.Task>> converter=
//
//
//        return null;
//    }
}
