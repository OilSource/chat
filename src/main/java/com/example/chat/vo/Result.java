package com.example.chat.vo;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    private static final Integer SUCCESS_STATUS = 200;
    private static final Integer ERROR_STATUS = -1;
    private static final String SUCCESS_MSG = "一切正常";

    public Result() {
        super();
    }

    public Result(int code) {
        super();
        setStatus(code);
    }

    public Result(HttpStatus status) {
        super();
        setStatus(status.value());
        setMsg(status.getReasonPhrase());
    }

    public Result success() {
        put("msg", SUCCESS_MSG);
        put("status", SUCCESS_STATUS);
        return this;
    }

    public Result success(String msg) {
        put("msg", msg);
        put("status", SUCCESS_STATUS);
        return this;
    }

    public Result error(String msg) {
        put("msg", msg);
        put("status", ERROR_STATUS);
        return this;
    }

    public Result setData(String key, Object obj) {
        @SuppressWarnings("unchecked")
        HashMap<String, Object> data = (HashMap<String, Object>) get("data");
        if (data == null) {
            data = new HashMap<String, Object>();
            put("data", data);
        }
        data.put(key, obj);
        return this;
    }

    public Result setStatus(int status) {
        put("status", status);
        return this;
    }

    public Result setMsg(String msg) {
        put("msg", msg);
        return this;
    }

    public Result setValue(String key, Object val) {
        put(key, val);
        return this;
    }

}
