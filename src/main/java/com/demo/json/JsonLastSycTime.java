package com.demo.json;

import java.sql.Timestamp;

public class JsonLastSycTime {
    private Boolean isSuccess;
    private Integer code;
    private String sessionId;
    private Timestamp lastSycTime;

    public JsonLastSycTime() {
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Timestamp getLastSycTime() {
        return lastSycTime;
    }

    public void setLastSycTime(Timestamp lastSycTime) {
        this.lastSycTime = lastSycTime;
    }
}
