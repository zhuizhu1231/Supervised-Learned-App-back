package com.demo.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.converter.MyConverter;
import com.demo.entities.Notes_label;
import com.demo.json.Bean;
import com.demo.service.INotes_labelService;
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
@RequestMapping("/note/label/user")
public class NotesLabelController {
    @Autowired
    private INotes_labelService labelService;


    @RequestMapping("/insert")
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Notes_label>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Notes_label> label : list) {
                Notes_label l = new Notes_label();
                Notes_label data = label.getData();
                l.setNotesCount(data.getNotesCount()).setTitle(data.getTitle()).setUserId(userId)
                        .setTimeStamp(Tool.createNewTimeStamp());
                if (labelService.save(l)) label.setData(l);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", list);
    }


    @RequestMapping("/remove")
    public String remove(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Notes_label>> list) {

        if (list != null && list.size() > 0) {
            for (Bean<Notes_label> label : list) {
                if (labelService.removeById(label.getData().getId())) label.setData(null);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "删除操作：提交成功", list);
    }

    @RequestMapping("/update")
    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Notes_label>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Notes_label> label : list) {
                label.getData().setTimeStamp(Tool.createNewTimeStamp());
                labelService.updateById(label.getData());
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "更新操作：提交成功", list);
    }

    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("timestamp") Double timestamp) {
        System.out.println(timestamp);
        List<Bean<Notes_label>> responseNotes_labels = new ArrayList<>();
        QueryWrapper<Notes_label> wrapper = new QueryWrapper<>();
        wrapper.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("user_id", userId);

        List<Notes_label> labels = labelService.list(wrapper);
        for (Notes_label label : labels) {
            Bean<Notes_label> labelsBean = new Bean<>();
            labelsBean.setData(label);
            responseNotes_labels.add(labelsBean);
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseNotes_labels);
    }

    @RequestMapping("/query/timestamp")
    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {

        Timestamp last_syc_time = labelService.maxTimeStampByUserId(userId);
        return Tool.makeJsonLastSycTimeString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, last_syc_time);
    }
}
