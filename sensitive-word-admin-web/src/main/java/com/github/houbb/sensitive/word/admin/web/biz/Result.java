package com.github.houbb.sensitive.word.admin.web.biz;

import org.springframework.stereotype.Service;

public class Result<T> {
    private int code;
    private String message;
    private T data;
    private long total;
    /**
     * 操作成功
     */
    public static final Result SUCCESS_RESULT = new Result(200, "成功");
    /**
     * 系统异常
     */
    public static final Result SYSTEM_ERROR_RESULT = new Result(500, "系统异常, 请稍后重试!!!");
    /**
     * 登录异常
     */
    public static final Result LOGIN_ERROR_RESULT = new Result(500, "登录信息已失效, 请重新登录!!!");
    /**
     * 请求参数异常
     */
    public static final Result PARAM_ERROR_RESULT = new Result(500, "请求参数异常, 请重试!!!");
    /**
     * 操作失败
     */
    public static final Result FAIL_RESULT = new Result(500, "操作失败, 请重试!!!");

    /**
     * 默认错误编码
     */
    public static final int ERROR = 500;

    public Result() {
    }

    public Result(T data) {
        this.code = Result.SUCCESS_RESULT.getCode();
        this.message = Result.SUCCESS_RESULT.getMessage();
        this.data = data;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String success, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Result param, T data) {
        this.code = param.getCode();
        this.message = param.getMessage();
        this.data = data;
    }

    public Result(Result param, T data, long total) {
        this.code = param.getCode();
        this.message = param.getMessage();
        this.data = data;
        this.total = total;
    }

    public static <T> Result<T> error(String message) {
        return (Result<T>) new Result(ERROR, message);
    }

    public static <T> Result<T> success(T data) {
        return (Result<T>) new Result(SUCCESS_RESULT, data);
    }

    public static <T> Result<T> success(T data, long total) {
        return (Result<T>) new Result(SUCCESS_RESULT, data, total);
    }
    public boolean isSuccess() {
        return code == 200;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
