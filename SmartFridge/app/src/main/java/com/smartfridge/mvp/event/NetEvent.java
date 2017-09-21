package com.smartfridge.mvp.event;

/**
 * Created by chenglb on 2015/5/16.
 */
public class NetEvent<T> {
    public int code;
    public String message;
    public T data;

    public NetEvent(int code, String message , T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
