package com.demo.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.converter.MyConverter;
import com.demo.entities.TaskLabel;
import com.demo.json.Bean;
import com.demo.service.ITaskLabelService;
import com.demo.util.StaticFinal;
import com.demo.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Controller
@RequestMapping("/task/label/user")
public class TaskLabelController {
    @Autowired
    private ITaskLabelService labelService;


    @RequestMapping("/insert")
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<TaskLabel>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<TaskLabel> label : list) {
                TaskLabel l = new TaskLabel();
                TaskLabel data = label.getData();
                l.setTitle(data.getTitle()).setUserId(userId)
                        .setTimeStamp(Tool.createNewTimeStamp());
                if (labelService.save(l)) label.setData(l);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", list);
    }


    @RequestMapping("/remove")
    public String remove(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<TaskLabel>> list) {

        if (list != null && list.size() > 0) {
            for (Bean<TaskLabel> label : list) {
                if (labelService.removeById(label.getData().getId())) label.setData(null);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "删除操作：提交成功", list);
    }

    @RequestMapping("/update")
    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<TaskLabel>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<TaskLabel> label : list) {
                label.getData().setTimeStamp(Tool.createNewTimeStamp());
                labelService.updateById(label.getData());
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "更新操作：提交成功", list);
    }

    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("timestamp") Double timestamp) {
        System.out.println(timestamp);
        List<Bean<TaskLabel>> responseTaskLabels = new ArrayList<>();
        QueryWrapper<TaskLabel> wrapper = new QueryWrapper<>();
        wrapper.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("user_id", userId);

        List<TaskLabel> labels = labelService.list(wrapper);
        for (TaskLabel label : labels) {
            Bean<TaskLabel> labelsBean = new Bean<>();
            labelsBean.setData(label);
            responseTaskLabels.add(labelsBean);
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseTaskLabels);
    }

//    @RequestMapping("/query/timestamp")
//    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {
//
//        Timestamp last_syc_time = labelService.maxTimeStampByUserId(userId);
//        return Tool.makeJsonLastSycTimeString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, last_syc_time);
//    }
}
