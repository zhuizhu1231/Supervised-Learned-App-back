package com.demo.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.converter.MyConverter;
import com.demo.entities.Schedule_label;
import com.demo.json.Bean;
import com.demo.service.ISchedule_labelService;
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
@RequestMapping("/schedule/label/user")
public class ScheduleLabelController {
    @Autowired
    private ISchedule_labelService labelService;

    @RequestMapping("/insert")
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Schedule_label>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Schedule_label> label : list) {
                Schedule_label l = new Schedule_label();
                Schedule_label data = label.getData();
                l.setScheduleCount(data.getScheduleCount()).setTitle(data.getTitle()).setUserId(userId).setTimeStamp(Tool.createNewTimeStamp());
                if (labelService.save(l)) label.setData(l);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", list);
    }

    @RequestMapping("/remove")
    public String remove(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Schedule_label>> list) {

        if (list != null && list.size() > 0) {
            for (Bean<Schedule_label> label : list) {
                if (labelService.removeById(label.getData().getId())) label.setData(null);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "删除操作：提交成功", list);
    }

    @RequestMapping("/update")
    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Schedule_label>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Schedule_label> label : list) {
                label.getData().setTimeStamp(Tool.createNewTimeStamp());
                labelService.updateById(label.getData());
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "更新操作：提交成功", list);
    }

    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("timestamp") Double timestamp) {
        System.out.println(timestamp);
        List<Bean<Schedule_label>> responseSchedule_labels = new ArrayList<>();
        QueryWrapper<Schedule_label> wrapper = new QueryWrapper<>();
        wrapper.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("user_id", userId);
        List<Schedule_label> labels = labelService.list(wrapper);
        for (Schedule_label label : labels) {
            Bean<Schedule_label> labelsBean = new Bean<>();
            labelsBean.setData(label);
            responseSchedule_labels.add(labelsBean);
        }


        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseSchedule_labels);
    }

    @RequestMapping("/query/timestamp")
    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {

        Timestamp last_syc_time = labelService.maxTimeStampByUserId(userId);
        return Tool.makeJsonLastSycTimeString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, last_syc_time);
    }
}
