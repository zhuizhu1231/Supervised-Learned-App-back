package com.demo.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.converter.MyConverter;
import com.demo.entities.Aim;
import com.demo.json.Bean;
import com.demo.service.IAimService;
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

//ok
@RestController
@Controller
@RequestMapping("/aim/user")
public class AimController {
    @Autowired
    private IAimService aimService;

    @RequestMapping("/insert")
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Aim>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Aim> aim : list) {
                Aim a = new Aim();
                Aim data = aim.getData();
                a.setRemark(data.getRemark()).setCreateTime(data.getCreateTime())
                        .setRestTime(data.getRestTime()).setTitle(data.getTitle())
                        .setUserId(userId).setTimeStamp(Tool.createNewTimeStamp());
                if (aimService.save(a)) aim.setData(a);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", list);
    }


    @RequestMapping("/remove")
    public String remove(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Aim>> list) {

        if (list != null && list.size() > 0) {
            for (Bean<Aim> aim : list) {
                if (aimService.removeById(aim.getData().getId())) aim.setData(null);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "删除操作：提交成功", list);
    }

    @RequestMapping("/update")
    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Aim>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Aim> aim : list) {
                aim.getData().setTimeStamp(Tool.createNewTimeStamp());
                aimService.updateById(aim.getData());
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "更新操作：提交成功", list);
    }

    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("timestamp") Double timestamp) {

        System.out.println(timestamp);
        List<Bean<Aim>> responseAims = new ArrayList<>();
        QueryWrapper<Aim> wrapper = new QueryWrapper<>();
        wrapper.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("user_id", userId);
        List<Aim> aims = aimService.list(wrapper);
        for (Aim aim : aims) {
            Bean<Aim> aimsBean = new Bean<>();
            aimsBean.setData(aim);
            responseAims.add(aimsBean);
        }


        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseAims);
    }

    @RequestMapping("/query/timestamp")
    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {

        Timestamp last_syc_time = aimService.maxTimeStampByUserId(userId);
        return Tool.makeJsonLastSycTimeString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, last_syc_time);
    }
}
