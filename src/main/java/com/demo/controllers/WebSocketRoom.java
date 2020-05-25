package com.demo.controllers;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.entities.Study_room_message;
import com.demo.entities.User_in_study_room;
import com.demo.service.IStudy_room_messageService;
import com.demo.service.IUserService;
import com.demo.service.IUser_in_study_roomService;
import com.demo.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述:
 * 多个群聊
 * 加上RestController注解,使其可以响应文件上传链接
 * 如果不注册成控制层则必须加一个组件扫描，否则将无法注册
 * 所有的message都将使用json进行传输
 * 可以根据链接的直接得到roomId
 * 关于服务层的注入，只能通过Spring容器的上下文来进行赋值，否则会得到空值
 * ChatMessage为自己定义的进行信息传输的类，这里全部将其转为JSON，方便解析
 *
 * @author songxinyong
 * @ClassName ConsultWebSocket
 * @date 2020-02-29 9:35
 */
@ServerEndpoint(value = "/webSocketRoom/{roomId}/{userId}")
@Component
public class WebSocketRoom {

    // 使用map来收集session，key为roomName，value为同一个房间的用户集合
    private static final Map<Integer, Set<Integer>> rooms = new ConcurrentHashMap<>();
    //缓存session对应的用户
    private static final Map<Integer, Session> users = new ConcurrentHashMap<>();
    private Integer uid;
    private Integer roomId;
    private User_in_study_room user;
    /**
     * 静态注入service
     */
    @Autowired
    private static IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        WebSocketRoom.userService = userService;
    }

    @Autowired
    private static IUser_in_study_roomService userInStudyRoomService;

    @Autowired
    public void setUserInStudyRoomService(IUser_in_study_roomService userInStudyRoomService) {
        WebSocketRoom.userInStudyRoomService = userInStudyRoomService;
    }


    @Autowired
    private static IStudy_room_messageService studyRoomMessageService;

    @Autowired
    public void setStudyRoomMessageService(IStudy_room_messageService studyRoomMessageService) {
        WebSocketRoom.studyRoomMessageService = studyRoomMessageService;
    }


    /**
     * 连接创建后将上线的用户广播给组员
     */
    @OnOpen
    public void connect(@PathParam("roomId") String roomId, @PathParam(value = "userId") String userId, Session session) throws IOException {//

        QueryWrapper<User_in_study_room> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("study_room_id", Integer.valueOf(roomId));
        List<User_in_study_room> relation = userInStudyRoomService.list(wrapper);
        if (relation != null && relation.size() > 0) {
            user = relation.get(0);
            this.roomId = user.getStudyRoomId();
            this.uid = user.getUserId();
            //目前使用随机名称，可以整合自己的session管理，如shiro之类的
            users.put(uid, session);
            // 将session按照房间名来存储，将各个房间的用户隔离
            if (!rooms.containsKey(this.roomId)) {
                // 创建房间不存在时，创建房间
                Set<Integer> room = new HashSet<>();

                // 添加用户
                room.add(uid);
                rooms.put(this.roomId, room);
            } else {
                // 房间已存在，直接添加用户到相应的房间
                if (rooms.get(this.roomId) == null) {
                    Set<Integer> room = new HashSet<>();
                    rooms.put(this.roomId, room);
                }
                rooms.get(this.roomId).add(uid);
            }


            //  向房间的所有人广播谁上线了
//            Study_room_message message=new Study_room_message();
//            message.setTimeStamp(Tool.createNewTimeStamp()).setStudyRoomId(this.roomId)
//                    .setSendTime(Tool.createNewTimeStamp()).setContent(user.getNameInRoom()+ "上线了");
//
//            broadcast(roomId, JSON.toJSONString(MapUnite.getMap(chatMessage)));


            // 加入群
//        Group group = groupService.getById(roomId);
//        if (group != null) {
//            if (group.getPeoples() == null || group.getPeoples().equals("")  ){ // 群里没有用户的情况
//                Group g = new Group();
//                g.setId(group.getId());
//                g.setPeoples(String.valueOf(uid));
//                groupService.updateById(g);
//            }else{ // 群里存在用户，并判断用户有没有进入该群
//                String praise = group.getPeoples();
//                List<String> infoUsers = Arrays.asList(praise.split(","));
//                List<String> infoUsersList = new ArrayList<String>(infoUsers);
//                Integer p = infoUsersList.indexOf(String.valueOf(uid));
//                if(p == -1) {
//                    infoUsersList.add(String.valueOf(uid));
//                    String s = infoUsersList.toString();
//                    s = s.substring(1,s.length()-1);
//                    String aa = s.replace(" ", "");
//                    Group g = new Group();
//                    g.setId(group.getId());
//                    g.setPeoples(aa);
//                    groupService.updateById(g);
//                }
//            }
//        }
            broadcast(this.roomId, this.uid, user.getNameInRoom() + "上线了");

        }

    }

    @OnClose
    public void disConnect() {
        if (uid != null && roomId != null) {
            Set<Integer> set = rooms.get(roomId);
            if (set.contains(uid) && users.containsKey(uid)) {
                set.remove(uid);
                users.remove(uid);

//            Study_room_message message=new Study_room_message();
//            message.setTimeStamp(Tool.createNewTimeStamp()).setStudyRoomId(this.roomId)
//                    .setSendTime(Tool.createNewTimeStamp()).setContent(user.getNameInRoom()+ "下线了");

                broadcast(roomId, this.uid, user.getNameInRoom() + "下线了");
            }
        }

    }
//

    /**
     * @param msg 前台传回来的数据应为json数据
     */
    @OnMessage
    public void receiveMsg(String msg, Session session) {
//        String[] arr = param.split(",");
//        this.roomId = String.valueOf(arr[0]);
//        this.uid= Integer.valueOf(arr[1]);
//        User user = userService.getById(uid);
//        Map a = (Map) JSON.parse(Util.base64Untie(user.getUserinfo()));
//
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setCreateTime(Util.getDate());
//        chatMessage.setUserName(String.valueOf(a.get("nickName")));
//        chatMessage.setUserId(uid);
//        chatMessage.setAvatarUrl(String.valueOf(a.get("avatarUrl")));
//        chatMessage.setTypes(StateUtil.ONE);
//        chatMessage.setChatContent(Util.base64Plus(msg));
//        chatMessage.setGroupId(Integer.valueOf(roomId));
        // 此处应该有html过滤，进行数据加工
//        msg = users.get(session.getId()) + ":" + msg;
        // 接收到信息后进行广播

        Study_room_message data = JSON.parseObject(msg, Study_room_message.class);
        String content = data.getContent();
        broadcast(data.getStudyRoomId(), data.getSendUserId(), data.getContent());

    }

    // 按照房间名进行广播
    private void broadcast(Integer roomId, Integer uid, String msg) {
        Study_room_message message = new Study_room_message();
        message.setTimeStamp(Tool.createNewTimeStamp()).setStudyRoomId(this.roomId).setSendUserId(uid)
                .setSendTime(Tool.createNewTimeStamp()).setContent(msg);
        if (uid != null)
            studyRoomMessageService.save(message);
        String jsonString = JSON.toJSONString(message);
        rooms.get(this.roomId.intValue()).forEach(s -> {
            try {
                users.get(s).getBasicRemote().sendText(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //将聊天记录加入缓存
        //这里需要将此服务层的bean手动注入
//        if (chatCacheService == null) {
//            chatCacheService = ApplicationContextRegister.getApplicationContext().getBean(ChatCacheService.class);
//        }
//        chatCacheService.cacheMsg(msg, roomName, CacheType.CONSULT);
    }
//    public static Map getMap(Object object){
//        Map map = new HashMap();
//        if (object == null) {
//            return map;
//        }
//        Class clazz = object.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        try {
//            for (Field field : fields) {
//                field.setAccessible(true);
//                if (field.getName().equals("createTime")){
//                    map.put(field.getName(), Util.getDateFromat((Date) field.get(object)));
//                }else if(field.getName().equals("chatContent")){
//                    map.put(field.getName(), Util.base64Untie((String) field.get(object)));
//                } else{
//                    map.put(field.getName(), field.get(object));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return map;
//    }


}
