package com.demo.controllers;

import com.demo.entities.Study_room_message;
import com.demo.json.Bean;
import com.demo.service.IStudy_room_messageService;
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
@RequestMapping("/room/chat")
public class StudyRoomMessageController {
    @Autowired
    private IStudy_room_messageService messageService;

    @RequestMapping("/insert")
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Study_room_message>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Study_room_message> message : list) {
                Study_room_message m = new Study_room_message();
                Study_room_message data = message.getData();
                m.setContent(data.getContent()).setSendTime(Tool.createNewTimeStamp())
                        .setSendUserId(userId).setStudyRoomId(data.getStudyRoomId()).setTimeStamp(Tool.createNewTimeStamp());
                if (messageService.save(m)) message.setData(m);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", list);
    }


    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {
        List<Bean<Study_room_message>> responseStudy_room_messages = new ArrayList<>();
        List<Study_room_message> messages = messageService.queryStudyRoomMessageByUserId(userId);
        ;
        for (Study_room_message message : messages) {
            Bean<Study_room_message> messagesBean = new Bean<>();
            messagesBean.setData(message);
            responseStudy_room_messages.add(messagesBean);
        }


        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseStudy_room_messages);
    }

    @RequestMapping("/query")
    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {

        Timestamp last_syc_time = messageService.maxTimeStampByUserId(userId);

        return Tool.makeJsonLastSycTimeString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, last_syc_time);
    }
}
