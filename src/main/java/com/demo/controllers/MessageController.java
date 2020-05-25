package com.demo.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.converter.MyConverter;
import com.demo.entities.Message;
import com.demo.json.Bean;
import com.demo.service.IMessageService;
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
@RequestMapping("/message/user")
public class MessageController {
    @Autowired
    private IMessageService messageService;


    @RequestMapping("/update")
    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Message>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Message> messageBean : list) {
                messageBean.getData().setTimeStamp(Tool.createNewTimeStamp());
                messageService.updateById(messageBean.getData());
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "更新操作：提交成功", list);
    }

    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("timestamp") Double timestamp) {
        List<Bean<Message>> responseMessages = new ArrayList<>();
        List<Message> messages = messageService.queryMessageByUserId(userId);
        ;

        QueryWrapper<Message> wrapperSend = new QueryWrapper<>();
        wrapperSend.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("send_id", userId);
        List<Message> messageList = messageService.list(wrapperSend);
        for (Message message : messageList) {
            Bean<Message> messageBean = new Bean<>();
            messageBean.setData(message);
            responseMessages.add(messageBean);
        }

        QueryWrapper<Message> wrapperReceiver = new QueryWrapper<>();
        wrapperReceiver.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("receiver_id", userId);
        List<Message> messageList2 = messageService.list(wrapperReceiver);
        for (Message message : messageList2) {
            Bean<Message> messageBean = new Bean<>();
            messageBean.setData(message);
            responseMessages.add(messageBean);
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseMessages);
    }

//    @RequestMapping("/query")
//    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId){
//
//        Timestamp last_syc_time =messageService.maxTimeStampByUserId(userId);
//        System.out.println(last_syc_time);
//        return Tool.makeJsonLastSycTimeString(true,StaticFinal.CODE_REQUEST_SUCCESS,sessionId,last_syc_time);
//    }
}
