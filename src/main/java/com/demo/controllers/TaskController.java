package com.demo.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.converter.MyConverter;
import com.demo.entities.Task;
import com.demo.json.Bean;
import com.demo.service.ITaskService;
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
@RequestMapping("/task/user")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    @RequestMapping("/insert")
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Task>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Task> task : list) {
                Task t = new Task();
                Task data = task.getData();
                t.setRemark(data.getRemark()).setTimeStamp(Tool.createNewTimeStamp())
                        .setTitle(data.getTitle()).setUserId(userId).setWorkCount(data.getWorkCount())
                        .setWorkTime(data.getWorkTime()).setAimId(data.getAimId());
                if (taskService.save(t)) task.setData(t);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", list);
    }

    @RequestMapping("/remove")
    public String remove(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Task>> list) {

        if (list != null && list.size() > 0) {
            for (Bean<Task> task : list) {
                if (taskService.removeById(task.getData().getId())) task.setData(null);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "删除操作：提交成功", list);
    }

    @RequestMapping("/update")
    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Task>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Task> task : list) {
                task.getData().setTimeStamp(Tool.createNewTimeStamp());
                taskService.updateById(task.getData());
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "更新操作：提交成功", list);
    }

    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("timestamp") Double timestamp) {

        System.out.println(timestamp);
        List<Bean<Task>> responseTasks = new ArrayList<>();
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("user_id", userId);
        List<Task> tasks = taskService.list(wrapper);
        for (Task task : tasks) {
            Bean<Task> tasksBean = new Bean<>();
            tasksBean.setData(task);
            responseTasks.add(tasksBean);
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseTasks);
    }

    @RequestMapping("/query/timestamp")
    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {

        Timestamp last_syc_time = taskService.maxTimeStampByUserId(userId);
        return Tool.makeJsonLastSycTimeString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, last_syc_time);
    }
}
