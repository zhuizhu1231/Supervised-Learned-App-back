package com.demo.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.converter.MyConverter;
import com.demo.entities.Notes;
import com.demo.json.Bean;
import com.demo.service.INotesService;
import com.demo.util.StaticFinal;
import com.demo.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//ok
@RestController
@Controller
@RequestMapping("/note/user")
public class NotesController {
    @Autowired
    private INotesService notesService;

    //    @RequestMapping("/insert")
    //   public String insert(@RequestBody String sessionId, @RequestBody User user, @RequestBody List<Notes> list){
//        List<Notes> responseNotes=new ArrayList<>();
//        if(list!=null&&list.size()>0){
//            Integer userId = user.getId();
//            for(Notes notes:list){
//                Notes n=new Notes();
//                n.setContent(notes.getContent()).setLastTime(Tool.createNewTimeStamp()).setUserId(userId);
//                if(notesService.save(n)) responseNotes.add(n);
//            }
//        }
//        return Tool.makeJsonResultString(true,StaticFinal.CODE_REQUEST_SUCCESS,sessionId,"插入操作：提交成功",responseNotes);
//    }
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Notes>> list) {//@RequestBody String list
        if (list != null && list.size() > 0) {
            for (Bean<Notes> note : list) {
                Notes n = new Notes();
                Notes data = note.getData();
                n.setContent(data.getContent()).setLastTime(data.getLastTime()).setUserId(userId).setTimeStamp(Tool.createNewTimeStamp());
                if (notesService.save(n)) note.setData(n);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", list);
    }

    @RequestMapping("/remove")
    public String remove(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Notes>> list) {

        if (list != null && list.size() > 0) {
            for (Bean<Notes> notes : list) {
                if (notesService.removeById(notes.getData().getId())) notes.setData(null);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "删除操作：提交成功", list);
    }

    @RequestMapping("/update")
    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Notes>> list) {

        if (list != null && list.size() > 0) {
            for (Bean<Notes> notes : list) {
                notes.getData().setTimeStamp(Tool.createNewTimeStamp());
                notesService.updateById(notes.getData());
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "更新操作：提交成功", list);
    }

    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("timestamp") Double timestamp) {
        System.out.println(timestamp);
        List<Bean<Notes>> responseNotes = new ArrayList<>();
        QueryWrapper<Notes> wrapper = new QueryWrapper<>();
        wrapper.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("user_id", userId);

        List<Notes> notes = notesService.list(wrapper);
        for (Notes note : notes) {
            Bean<Notes> notesBean = new Bean<>();
            notesBean.setData(note);
            responseNotes.add(notesBean);
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseNotes);
    }

    @RequestMapping("/query/timestamp")
    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {

        Timestamp last_syc_time = notesService.maxTimeStampByUserId(userId);
        return Tool.makeJsonLastSycTimeString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, last_syc_time);
    }
}
