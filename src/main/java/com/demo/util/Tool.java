package com.demo.util;

import com.alibaba.fastjson.JSON;
import com.demo.converter.MyConverter;
import com.demo.json.Bean;
import com.demo.json.JsonLastSycTime;
import com.demo.json.JsonResult;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Tool {
    public static Timestamp getDeFaultTaskWorkTime() {
        return new Timestamp((long) 1000 * 60 * 25);
    }

    public static Timestamp getDeFaultTargetRelaxTime() {
        return new Timestamp((long) 1000 * 60 * 5);
    }

    public static Timestamp createNewTimeStamp() {
        return MyConverter.longToTimestamp(MyConverter.dateToLong(new Date()));
    }

    public static boolean isEmptyString(String s) {
        if (s != null && s.trim() != "") return false;
        return true;
    }

    public static void sendErrorMessage(HttpServletResponse response, int code, String message) throws IOException {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(code);
        jsonResult.setMessage(message);
        response.getWriter().write(gson.toJson(jsonResult));
    }


    public static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
//    public static String makeJsonResultString(boolean isSuccess,int code, String sessionId,String message, List data)  {
//        JsonResult<User> jsonResult =new JsonResult<>();
//      //  Gson gson=new Gson();
//        jsonResult.setSuccess(isSuccess);
//        jsonResult.setCode(code);
//        jsonResult.setSessionId(sessionId);
//        jsonResult.setMessage(message);
//        jsonResult.setData(data);
//        //gson.toJson(jsonResult);
//        return JSON.toJSONString(jsonResult);
//    }

    public static <T> String makeJsonResultString(boolean isSuccess, int code, String sessionId, String message, List<Bean<T>> data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        //  Gson gson=new Gson();
        jsonResult.setSuccess(isSuccess);
        jsonResult.setCode(code);
        jsonResult.setSessionId(sessionId);
        jsonResult.setMessage(message);
        jsonResult.setData(data);
        //gson.toJson(jsonResult);
        return JSON.toJSONString(jsonResult);
    }

    public static String makeJsonResultStringNotT(boolean isSuccess, int code, String sessionId, String message, List<Bean> data) {
        JsonResult jsonResult = new JsonResult<>();
        //  Gson gson=new Gson();
        jsonResult.setSuccess(isSuccess);
        jsonResult.setCode(code);
        jsonResult.setSessionId(sessionId);
        jsonResult.setMessage(message);
        jsonResult.setData(data);
        //gson.toJson(jsonResult);
        return JSON.toJSONString(jsonResult);
    }

    public static String makeJsonLastSycTimeString(boolean isSuccess, int code, String sessionId, Timestamp lastSycTime) {
        JsonLastSycTime jsonLastSycTime = new JsonLastSycTime();
        jsonLastSycTime.setSuccess(isSuccess);
        jsonLastSycTime.setCode(code);
        jsonLastSycTime.setSessionId(sessionId);
        jsonLastSycTime.setLastSycTime(lastSycTime);
        return JSON.toJSONString(jsonLastSycTime);
    }
}
