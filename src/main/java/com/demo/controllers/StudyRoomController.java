package com.demo.controllers;

import com.demo.entities.Study_room;
import com.demo.entities.User;
import com.demo.entities.User_in_study_room;
import com.demo.json.Bean;
import com.demo.service.IStudy_roomService;
import com.demo.service.IUserService;
import com.demo.service.IUser_in_study_roomService;
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
@RequestMapping("/room")
public class StudyRoomController {
    @Autowired
    private IStudy_roomService roomService;
    @Autowired
    private IUser_in_study_roomService roomRelationService;
    @Autowired
    private IUserService userService;

    //查找自习室（加入）
    @RequestMapping("/query/like")
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("roomName") String roomName) {
        List<Bean<Study_room>> responseStudyRooms = new ArrayList<>();
        if (Tool.isNumber(roomName)) {
            if (roomService.getById(Integer.valueOf(roomName)) != null) {
                Bean<Study_room> bean = new Bean<>();
                bean.setData(roomService.getById(Integer.valueOf(roomName)));
                responseStudyRooms.add(bean);
            }
        }
        List<Study_room> studyRooms = roomService.queryRoomByNameLike(roomName);
        if (studyRooms != null && studyRooms.size() > 0) {

            for (Study_room room : studyRooms) {
                Bean<Study_room> aimsBean = new Bean<>();
                aimsBean.setData(room);
                responseStudyRooms.add(aimsBean);
            }
            //  return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询成功！匹配的自习室有：", responseStudyRooms);
        }
        return
                Tool.makeJsonResultString(false, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询成功！自习室", responseStudyRooms);
    }

    @RequestMapping("/query/id")
    public String queryById(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("id") Integer id) {
        List<Bean<Study_room>> responseRooms = new ArrayList<>();

        Study_room byId = roomService.getById(id);
        if (byId != null) {
            Bean<Study_room> bean = new Bean<>();
            bean.setData(byId);
            responseRooms.add(bean);
        }

        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", responseRooms);
    }

    //创建自习室
    @RequestMapping("/create")
    public String create(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody Study_room room) {
        if (room != null && !Tool.isEmptyString(room.getName())) {
            Study_room r = new Study_room();
            r.setCreateTime(Tool.createNewTimeStamp()).setDetail(room.getDetail()).setLeastWorkTime(room.getLeastWorkTime())
                    .setName(room.getName()).setUserCount(room.getUserCount()).setUserCreateId(userId).setTimeStamp(Tool.createNewTimeStamp());
            if (roomService.save(r)) {
                User_in_study_room member = new User_in_study_room();
                member.setIsCreate(StaticFinal.ROOM_IS_CREATE_TRUE).setJoinTime(Tool.createNewTimeStamp()).setStudyRoomId(r.getId()).setUserId(userId).setTimeStamp(Tool.createNewTimeStamp()).setNameInRoom(userService.getById(userId).getName());
                roomRelationService.save(member);
                List<Bean<Study_room>> responseStudyRooms = new ArrayList<>();
                Bean<Study_room> roomBean = new Bean<>();
                roomBean.setData(r);
                responseStudyRooms.add(roomBean);
                return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "自习室创建成功", responseStudyRooms);
            } else {
                return Tool.makeJsonResultString(false, StaticFinal.CODE_REQUEST_FAIL, sessionId, "自习室创建失败，请重试", null);
            }
        } else {
            return Tool.makeJsonResultString(false, StaticFinal.CODE_REQUEST_FAIL, sessionId, "创建失败：自习室信息缺少，请重新提交", null);
        }
    }

    //创建自习室
    @RequestMapping("/add")
    public String addByRoomId(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("id") Integer id) {
        Study_room byId = roomService.getById(id);
        List<Bean<Study_room>> responseRooms = new ArrayList<>();
        if (byId != null) {
            User_in_study_room relation = new User_in_study_room();
            User user = userService.getById(userId);
            relation.setTimeStamp(Tool.createNewTimeStamp()).setUserId(userId).setStudyRoomId(id).setJoinTime(Tool.createNewTimeStamp()).setNameInRoom(user.getName()).setIsCreate(StaticFinal.ROOM_IS_CREATE_FALSE);
            roomRelationService.save(relation);
            Bean<Study_room> bean = new Bean<>();
            bean.setData(byId);
            responseRooms.add(bean);
        }


        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "自习室创建成功", responseRooms);

    }


    @RequestMapping("/user/remove")
    public String remove(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Study_room>> list) {

        if (list != null && list.size() > 0) {
            for (Bean<Study_room> milepost : list) {
                if (roomService.removeById(milepost.getData().getId())) milepost.setData(null);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "删除操作：提交成功", list);
    }

    @RequestMapping("/user/update")
    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<Study_room>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<Study_room> milepost : list) {
                milepost.getData().setTimeStamp(Tool.createNewTimeStamp());
                roomService.updateById(milepost.getData());
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "更新操作：提交成功", list);
    }

    //是否更新所在的自习室信息
//
//
    //查找自己所在的自习室列表
    @RequestMapping("/user/query/list")
    public String queryUserRoom(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {
        List<User_in_study_room> relations = roomRelationService.queryRelationByUserId(userId);
        List<Bean<Study_room>> responseStudyRooms = new ArrayList<>();
        for (User_in_study_room relation : relations) {
            Study_room byId = roomService.getById(relation.getStudyRoomId());
            Bean<Study_room> roomBean = new Bean<>();
            roomBean.setData(byId);
            responseStudyRooms.add(roomBean);
        }
        return
                Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseStudyRooms);
    }

    //是否更新User_in_study_room ByRoomId的自习室成员信息

    //   更新自习室成员ByRoomId
    @RequestMapping("/user/room/member")
    public String queryUserRoomsMembers(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("roomId") Integer roomId) {

        Study_room room = roomService.getById(roomId);
        List<Bean<User_in_study_room>> roomsMembersResponse = new ArrayList<>();
        if (room != null) {
            List<User_in_study_room> roomMembers = roomRelationService.queryRelationByRoomId(room.getId());
            for (User_in_study_room member : roomMembers) {
                Bean<User_in_study_room> bean = new Bean<>();
                bean.setData(member);
                roomsMembersResponse.add(bean);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", roomsMembersResponse);
    }

}
