package com.demo.sessionListener;

import com.demo.entities.User;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class SessionContext {

    private static HashMap<String, HttpSession> userMap = new HashMap();

    public static synchronized void AddSession(HttpSession session) {
        if (session != null) {
            userMap.put(session.getId(), session);
        }
    }

    public static synchronized void DelSession(HttpSession session) {
        //Todo:设置user的最后登录时间  shujuku
        if (session != null) {
            userMap.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
            return null;
        return userMap.get(session_id);
    }

    public static synchronized boolean userRepeat(String sessionId, int userId) {
        for (Map.Entry<String, HttpSession> sessionEntry : userMap.entrySet()) {
            User u = (User) sessionEntry.getValue().getAttribute("user");
            sessionEntry.getKey().equals(sessionId);
            if (u.getId() == userId && !sessionEntry.getKey().equals(sessionId)) {
                DelSession(sessionEntry.getValue());
                return true;
            }
        }
        return false;
    }
}

