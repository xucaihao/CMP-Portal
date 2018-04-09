package com.cmp.portal.common;

public class ResponseData<T> {

    private String code;

    private T data;

    private String msg;

    private ResponseData(String code) {
        this.code = code;
    }

    private ResponseData(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResponseData(String code, T data) {
        this.code = code;
        this.data = data;
    }

    private ResponseData(String code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> ResponseData<T> success() {
        return new ResponseData<>("Success");
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>("Success", data);
    }

    public static <T> ResponseData<T> success(T data, String msg) {
        return new ResponseData<>("Success", data, msg);
    }

    public static <T> ResponseData<T> warning() {
        return new ResponseData<>("Warning");
    }

    public static <T> ResponseData<T> warning(T data) {
        return new ResponseData<>("Warning", data);
    }

    public static <T> ResponseData<T> failure() {
        return new ResponseData<>("Failure");
    }

    public static <T> ResponseData<T> failure(T data) {
        return new ResponseData<>("Failure", data);
    }

    public static <T> ResponseData<T> failure(String msg) {
        return new ResponseData<>("Failure", null, msg);
    }

    public static <T> ResponseData<T> failure(T data, String msg) {
        return new ResponseData<>("Failure", data, msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
