package com.cmp.portal.common;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ResponseData<T> {

    private int code;

    private T data;

    private String msg;

    private ResponseData(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> ResponseData<T> build(int code, T data) {
        return new ResponseData<>(code, data, "Success");
    }

    public static <T> ResponseData<T> failure() {
        return new ResponseData<>(BAD_REQUEST.value(), null, "Failure");
    }

    public static <T> ResponseData<T> failure(int code, String msg) {
        return new ResponseData<>(code, null, msg);
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
