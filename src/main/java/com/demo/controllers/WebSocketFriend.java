package com.demo.controllers;

import com.alibaba.fastjson.JSON;
import com.demo.entities.Message;
import com.demo.entities.User;
import com.demo.service.IFriendService;
import com.demo.service.IMessageService;
import com.demo.service.IUserService;
import com.demo.util.StaticFinal;
import com.demo.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/webSocketFriend/{userId}")
public class WebSocketFriend {

    // 这里使用静态，让 service 属于类
//    private static UserService userService;
    @Autowired
    private static IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        WebSocketFriend.userService = userService;
    }

    @Autowired
    private IFriendService friendService;

    @Autowired
    private static IMessageService messageService;

    @Autowired
    public void setUserService(IMessageService messageService) {
        WebSocketFriend.messageService = messageService;
    }

    private static volatile int onlineCount;

    private static Map<Integer, WebSocketFriend> connections = new ConcurrentHashMap<>();

    private Session session;
    private Integer id;


    /**
     * 连接建立成功调用的方法
     * <p>
     * <p>
     * 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userId") String userId, Session session) {//,@RequestParam("relationId") int relationId,@RequestParam("toUser") int toUser//@RequestParam("sessionId") String sessionId, @RequestParam("userId") int userId
        // this.session = (Session) SessionContext.getSession(sessionId);
        //   System.out.println("d");
        // this.role = userId;             //用户标识
        //会话标识
        System.out.println(userId);
        this.session = session;
        if (userId != null) {
            try {
                id = Integer.valueOf(userId);
                System.out.println(id);
                connections.put(id, this);     //添加到map中
                addOnlineCount();               // 在线数加
            } catch (Exception e) {

            }

        }


//        System.out.println(param);
//        System.out.println(this.session);
//        System.out.println("有新连接加入！新用户："+role+",当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (id != null) {
            connections.remove(id);  // 从map中移除
            subOnlineCount();          // 在线数减
        }
//        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        Message data = JSON.parseObject(message, Message.class);
        String content = data.getContent();
        send(data.getContent(), data.getSendId(), data.getReceiverId());
//        Friend relation = friendService.getById(socketId);
//        if(relation!=null&&relation.getUserId()==role&&relation.getFriendId()==to)
//            send(string,role,to,socketId);
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    //发送给指定角色
    public void send(String msg, Integer userId, Integer to) {//,Integer socketId
        Message message = new Message();
        message.setContent(msg);
        message.setIsRead(StaticFinal.READ_FALSE);
        message.setIsPrompt(StaticFinal.PROMPT_FALSE);
        message.setTimeStamp(Tool.createNewTimeStamp());
        message.setSendTime(Tool.createNewTimeStamp());
        message.setReceiverId(to);
        message.setSendId(userId);

        messageService.save(message);
        try {

            User u = userService.getById(userId);

            System.out.println(u);
            //to指定用户
            WebSocketFriend con = connections.get(to);
            if (con != null) {
                con.session.getBasicRemote().sendText(JSON.toJSONString(message));
            }
            //from具体用户
            WebSocketFriend confrom = connections.get(userId);
            if (confrom != null) {
                confrom.session.getBasicRemote().sendText(JSON.toJSONString(message));
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketFriend.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketFriend.onlineCount--;
    }
}
