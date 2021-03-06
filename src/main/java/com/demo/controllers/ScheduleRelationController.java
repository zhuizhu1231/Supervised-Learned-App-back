package com.demo.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.converter.MyConverter;
import com.demo.entities.Schedule_in_label;
import com.demo.json.Bean;
import com.demo.service.ISchedule_in_labelService;
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
@RequestMapping("/schedule/relation/user")
public class ScheduleRelationController {
    @Autowired
    private ISchedule_in_labelService relationService;

    @RequestMapping("/insert")
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Schedule_in_label>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Schedule_in_label> relation : list) {
                Schedule_in_label r = new Schedule_in_label();
                Schedule_in_label data = relation.getData();
                r.setScheduleId(data.getScheduleId()).setScheduleLabelId(data.getScheduleLabelId()).setUserId(userId).setTimeStamp(Tool.createNewTimeStamp());
                if (relationService.save(r)) relation.setData(r);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", list);
    }

    @RequestMapping("/remove")
    public String remove(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Schedule_in_label>> list) {

        if (list != null && list.size() > 0) {
            for (Bean<Schedule_in_label> relation : list) {
                if (relationService.removeById(relation.getData().getId())) relation.setData(null);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "删除操作：提交成功", list);
    }

    @RequestMapping("/update")
    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Schedule_in_label>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Schedule_in_label> relation : list) {
                relation.getData().setTimeStamp(Tool.createNewTimeStamp());
                relationService.updateById(relation.getData());
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "更新操作：提交成功", list);
    }

    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("timestamp") Double timestamp) {
        System.out.println(timestamp);
        List<Bean<Schedule_in_label>> responseSchedule_in_labels = new ArrayList<>();
        QueryWrapper<Schedule_in_label> wrapper = new QueryWrapper<>();
        wrapper.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("user_id", userId);
        List<Schedule_in_label> relations = relationService.list(wrapper);
        for (Schedule_in_label relation : relations) {
            Bean<Schedule_in_label> relationsBean = new Bean<>();
            relationsBean.setData(relation);
            responseSchedule_in_labels.add(relationsBean);
        }


        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseSchedule_in_labels);
    }

    @RequestMapping("/query/timestamp")
    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {

        Timestamp last_syc_time = relationService.maxTimeStampByUserId(userId);
        return Tool.makeJsonLastSycTimeString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, last_syc_time);
    }
}
