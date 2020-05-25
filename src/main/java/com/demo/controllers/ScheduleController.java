package com.demo.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.converter.MyConverter;
import com.demo.entities.Schedule;
import com.demo.json.Bean;
import com.demo.service.IScheduleService;
import com.demo.util.StaticFinal;
import com.demo.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@Controller
@RequestMapping("/schedule/user")
public class ScheduleController {
    @Autowired
    private IScheduleService scheduleService;

    @RequestMapping("/insert")
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Schedule>> list) {
        if (list != null && list.size() > 0) {

            for (Bean<Schedule> schedule : list) {
                Schedule s = new Schedule();
                Schedule data = schedule.getData();
                s.setBelongTime(data.getBelongTime()).setCategory(data.getCategory()).//schedule的category为4个紧要重要程度
                        setIsDone(data.getIsDone()).setLastModifyTime(data.getLastModifyTime())
                        .setProperty(data.getProperty()).setRemark(data.getRemark())
                        .setUserId(userId).setTitle(data.getTitle()).setTimeStamp(Tool.createNewTimeStamp());
                if (scheduleService.save(s)) schedule.setData(s);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", list);
    }


    @RequestMapping("/remove")
    public String remove(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Schedule>> list) {

        if (list != null && list.size() > 0) {
            for (Bean<Schedule> schedule : list) {
                if (scheduleService.removeById(schedule.getData().getId())) schedule.setData(null);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "删除操作：提交成功", list);
    }

    @RequestMapping("/update")
    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Schedule>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Schedule> schedule : list) {
                schedule.getData().setTimeStamp(Tool.createNewTimeStamp());
                scheduleService.updateById(schedule.getData());
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "更新操作：提交成功", list);
    }

    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("timestamp") Double timestamp) {
        System.out.println(timestamp);
        List<Bean<Schedule>> responseSchedules = new ArrayList<>();

        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
        wrapper.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("user_id", userId);
        List<Schedule> schedules = scheduleService.list(wrapper);
        for (Schedule schedule : schedules) {
            Bean<Schedule> schedulesBean = new Bean<>();
            schedulesBean.setData(schedule);
            responseSchedules.add(schedulesBean);
        }


        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseSchedules);
    }

    @RequestMapping("/query/timestamp")
    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {

        Timestamp last_syc_time = scheduleService.maxTimeStampByUserId(userId);
        return Tool.makeJsonLastSycTimeString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, last_syc_time);
    }
}
