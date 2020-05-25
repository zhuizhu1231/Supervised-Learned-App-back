package com.demo.interceptor;

import com.demo.json.JsonResult;
import com.demo.sessionListener.SessionContext;
import com.demo.util.StaticFinal;
import com.demo.util.Tool;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserInterceptor implements HandlerInterceptor {

    //定义一个线程域，存放登录的对象
    String[] excludeUrls = new String[]{"/login", "/register"};

    public UserInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        if (requestURI.equals("/webSocketOneToOne")) {
            System.out.println(1);
        }
        if (isPass(requestURI)) return true;
        String sessionId = request.getParameter("sessionId");
        if (Tool.isEmptyString(sessionId)) {
            Tool.sendErrorMessage(response, StaticFinal.OFFLINE, "登录：开启一个新的世界");
            return false;
        }

        HttpSession session = SessionContext.getSession(sessionId);
        if (session == null) {
            Tool.sendErrorMessage(response, StaticFinal.OFFLINE, "已离线：请重新登录后再操作");
            Gson gson = new Gson();
            JsonResult jsonResult = new JsonResult();
            jsonResult.setCode(StaticFinal.OFFLINE);
            jsonResult.setMessage("离线：请登录后再操作");
            response.getWriter().write(gson.toJson(jsonResult));
            return false;
        }
        String userId = request.getParameter("userId");
        if (userId != null) {
            if (!SessionContext.userRepeat(sessionId, Integer.valueOf(userId)))
                return true;
            else {
                Tool.sendErrorMessage(response, StaticFinal.OFFLINE, "提示：该账户已被登录，请重新登录！");
            }
        } else {
            Tool.sendErrorMessage(response, StaticFinal.OFFLINE, "提示：因您操作超时！请重新登录！");
            return false;
        }

        return false;
    }

    private boolean isPass(String uri) {
        for (String includeUrl : excludeUrls) {
            if (includeUrl.equals(uri)) {
                return true;
            }
        }
        return false;
    }
}
