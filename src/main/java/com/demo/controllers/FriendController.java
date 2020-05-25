package com.demo.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.converter.MyConverter;
import com.demo.entities.Friend;
import com.demo.entities.User;
import com.demo.entities.UserFriend;
import com.demo.json.Bean;
import com.demo.service.IFriendService;
import com.demo.service.IUserService;
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
@RequestMapping("/friend/user")
public class FriendController {
    @Autowired
    private IFriendService friendService;
    @Autowired
    private IUserService userService;


    @RequestMapping("/query/like")
    public String query(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("key") String key) {// @RequestBody List<Bean<UserFriend>> list
        List<Bean<UserFriend>> responseFriends = new ArrayList<>();
        if (Tool.isNumber(key)) {
            Bean<UserFriend> userFriendBean = new Bean<>();
            userFriendBean.setData(parseUserFriend(new Friend(), userService.getById(Integer.valueOf(key))));
            responseFriends.add(userFriendBean);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", "%" + key + "%");
        List<User> users = userService.list(wrapper);
        for (User user : users) {
            Bean<UserFriend> userFriendBean = new Bean<>();
            userFriendBean.setData(parseUserFriend(new Friend(), user));
            responseFriends.add(userFriendBean);
        }


        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", responseFriends);
    }

    @RequestMapping("/query/id")
    public String queryById(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("id") Integer id) {
        List<Bean<UserFriend>> responseFriends = new ArrayList<>();
        Bean<UserFriend> userFriendBean = new Bean<>();
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("friend_id", id);
        List<Friend> list = friendService.list(wrapper);
        Friend friend;
        if (list != null && list.size() > 0) friend = list.get(0);
        else friend = new Friend();
        userFriendBean.setData(parseUserFriend(friend, userService.getById(id)));
        responseFriends.add(userFriendBean);
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", responseFriends);
    }

    @RequestMapping("/insert")
    public String insert(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<UserFriend>> list) {
        if (list != null && list.size() > 0) {

            for (Bean<UserFriend> friend : list) {
                UserFriend data = friend.getData();
                QueryWrapper<Friend> wrapper = new QueryWrapper<>();
                wrapper.eq("user_id", userId).eq("friend_id", friend.getData());
                List<Friend> FriendList = friendService.list(wrapper);
                Friend fri = null;
                if (list != null && list.size() > 0) fri = FriendList.get(0);
                if (fri == null) {
                    Friend f = new Friend();
                    f.setFriendId(data.getFriendId()).setUserId(userId).setRemark(data.getRemark()).setTimeStamp(Tool.createNewTimeStamp());
                    if (friendService.save(f)) {
                        User friendInfo = userService.getById(data.getFriendId());
                        friend.setData(parseUserFriend(f, friendInfo));
                    }
                } else {
                    fri.setRemark(data.getRemark()).setTimeStamp(Tool.createNewTimeStamp());
                    friendService.updateById(fri);
                }
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "插入操作：提交成功", list);
    }


    @RequestMapping("/remove")
    public String remove(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<UserFriend>> list) {

        if (list != null && list.size() > 0) {
            for (Bean<UserFriend> friend : list) {
                if (friendService.removeById(friend.getData().getId())) friend.setData(null);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "删除操作：提交成功", list);
    }

    @RequestMapping("/update")
    public String update(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestBody List<Bean<UserFriend>> list) {
        if (list != null && list.size() > 0) {
            for (Bean<UserFriend> friend : list) {
                Friend f = UserFriend.toFriend(userId, friend.getData());
                f.setTimeStamp(Tool.createNewTimeStamp());
                friendService.updateById(f);
            }
        }
        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "更新操作：提交成功", list);
    }

    @RequestMapping("/query/list")
    public String queryList(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId, @RequestParam("timestamp") Double timestamp) {
        List<Bean<UserFriend>> responseFriends = new ArrayList<>();
        // List<Friend> friends=friendService.queryFriendByUserId(userId);;
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.gt("time_stamp", MyConverter.doubleToTimestamp(timestamp)).eq("user_id", userId);
        List<Friend> friends = friendService.list(wrapper);
        for (Friend friend : friends) {
            Bean<UserFriend> userFriendBean = new Bean<>();
            User friendInfo = userService.getById(friend.getFriendId());
            userFriendBean.setData(parseUserFriend(friend, friendInfo));
            responseFriends.add(userFriendBean);
        }


        return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, "查询操作：列表已返回", responseFriends);
    }

    @RequestMapping("/query/timestamp")
    public String isQuery(@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId) {

        Timestamp last_syc_time = friendService.maxTimeStampByUserId(userId);

        return Tool.makeJsonLastSycTimeString(true, StaticFinal.CODE_REQUEST_SUCCESS, sessionId, last_syc_time);
    }

    private UserFriend parseUserFriend(Friend friend, User friendInfo) {
        UserFriend data = new UserFriend();
        data.setTimeStamp(friend.getTimeStamp());
        data.setId(friend.getId());
        data.setName(friendInfo.getName());
        data.setIsHide(friendInfo.getIsHide());
        data.setSex(friendInfo.getSex());
        data.setSign(friendInfo.getSign());
        data.setRemark(friend.getRemark());
        data.setRegisterTime(friendInfo.getRegisterTime());
        data.setFriendId(friendInfo.getId());
        return data;
    }

}
