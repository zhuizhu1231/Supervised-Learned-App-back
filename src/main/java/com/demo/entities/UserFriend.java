package com.demo.entities;


import com.demo.converter.SpringDataConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.Timestamp;

public class UserFriend {

    private Integer id;
    private Integer friendId;
    private Integer isHide;
    private String remark;
    private String name;
    private Integer sex;
    private String sign;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp timeStamp;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp registerTime;

    public Integer getId() {
        return id;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsHide() {
        return isHide;
    }

    public void setIsHide(Integer isHide) {
        this.isHide = isHide;
    }

    public String getRemark() {
        return remark;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Timestamp getTimestamp() {
        return timeStamp;
    }

    public void setTimestamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static Friend toFriend(int userId, UserFriend userFriend) {
        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setId(userFriend.getId());
        friend.setFriendId(userFriend.getFriendId());
        friend.setRemark(userFriend.getRemark());
        return friend;
    }

    public static UserFriend toUserFriend(Friend friend, User user) {
        UserFriend userFriend = new UserFriend();
        userFriend.setId(friend.getId());
        userFriend.setFriendId(userFriend.getId());
        userFriend.setRegisterTime(user.getRegisterTime());
        userFriend.setSign(user.getSign());
        userFriend.setSex(user.getSex());
        userFriend.setIsHide(user.getIsHide());
        userFriend.setName(user.getName());
        userFriend.setTimestamp(friend.getTimeStamp());
        userFriend.setRemark(friend.getRemark());
        return userFriend;
    }
}
