package com.demo.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.converter.MyConverter;
import com.demo.entities.Milepost;
import com.demo.json.Bean;
import com.demo.service.IMilePostService;
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
@RequestMapping("/milepost/user")
public class MilepostController {
    @Autowired
    private IMilePostService milepostService;

    @RequestMapping("/insert")
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Milepost>> list) {
        if (list != null && list.size() > 0) {

            for (Bean<Milepost> milepost : list) {
                Milepost m = new Milepost();
                Milepost data = milepost.getData();
                m.setDieTime(data.getDieTime()).setRemark(data.getRemark()).setTitle(data.getTitle()).
                        setUserId(userId).setTimeStamp(Tool.createNewTimeStamp());
                if (milepostService.save(m)) milepost.setData(m);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", list);
    }


    @RequestMapping("/remove")
    public String remove(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Milepost>> list) {

        if (list != null && list.size() > 0) {
            for (Bean<Milepost> milepost : list) {
                if (milepostService.removeById(milepost.getData().getId())) milepost.setData(null);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "删除操作：提交成功", list);
    }

    @RequestMapping("/update")
    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Milepost>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Milepost> milepost : list) {
                milepost.getData().setTimeStamp(Tool.createNewTimeStamp());
                milepostService.updateById(milepost.getData());
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "更新操作：提交成功", list);
    }

    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("timestamp") Double timestamp) {
        System.out.println(timestamp);
        List<Bean<Milepost>> responseMileposts = new ArrayList<>();
        QueryWrapper<Milepost> wrapper = new QueryWrapper<>();
        wrapper.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("user_id", userId);

        List<Milepost> mileposts = milepostService.list(wrapper);
        for (Milepost milepost : mileposts) {
            Bean<Milepost> milepostsBean = new Bean<>();
            milepostsBean.setData(milepost);
            responseMileposts.add(milepostsBean);
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseMileposts);
    }

    @RequestMapping("/query/timestamp")
    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {

        Timestamp last_syc_time = milepostService.maxTimeStampByUserId(userId);
        return Tool.makeJsonLastSycTimeString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, last_syc_time);
    }
}
