package com.demo.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.converter.MyConverter;
import com.demo.entities.TaskLog;
import com.demo.json.Bean;
import com.demo.service.ITaskLogService;
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
@RequestMapping("/task/log/user")
public class TaskLogController {
    @Autowired
    private ITaskLogService taskLogService;

    @RequestMapping("/insert")
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<TaskLog>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<TaskLog> task : list) {
                TaskLog t = new TaskLog();
                TaskLog data = task.getData();
                t.setTimeStamp(data.getTimeStamp())
                        .setUserId(userId).setTaskId(data.getTaskId());
                if (taskLogService.save(t)) task.setData(t);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", list);
    }

//    @RequestMapping("/remove")
//    public String remove(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<TaskLog>> list){
//
//        if(list!=null&&list.size()>0){
//            for(Bean<TaskLog> task:list){
//                if(taskLogService.removeById(task.getData().getId())) task.setData(null);
//            }
//        }
//        return Tool.makeJsonResultString(true,StaticFinal.CODE_REQUEST_SUCCESS,sessionId,"删除操作：提交成功",list);
//    }

//    @RequestMapping("/update")
//    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<TaskLog>> list){
//        if(list!=null&&list.size()>0){
//            for(Bean<TaskLog> task:list){
//                task.getData().setTimeStamp(Tool.createNewTimeStamp());
//                taskLogService.updateById(task.getData());
//            }
//        }
//        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS,sessionId,"更新操作：提交成功",list);
//    }

    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("timestamp") Double timestamp) {

        System.out.println(timestamp);
        List<Bean<TaskLog>> responseTaskLogs = new ArrayList<>();
        QueryWrapper<TaskLog> wrapper = new QueryWrapper<>();
        wrapper.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("user_id", userId);
        List<TaskLog> tasks = taskLogService.list(wrapper);
        for (TaskLog task : tasks) {
            Bean<TaskLog> tasksBean = new Bean<>();
            tasksBean.setData(task);
            responseTaskLogs.add(tasksBean);
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseTaskLogs);
    }

//    @RequestMapping("/query/timestamp")
//    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId){
//
//        Timestamp last_syc_time =taskLogService.maxTimeStampByUserId(userId);
//        return Tool.makeJsonLastSycTimeString(true,StaticFinal.CODE_REQUEST_SUCCESS,sessionId,last_syc_time);
//    }
}
