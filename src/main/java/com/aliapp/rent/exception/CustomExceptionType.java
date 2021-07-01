package com.aliapp.rent.exception;

public enum CustomExceptionType {
    USER_ERROR(400, "用户输入异常"),
    SERVER_ERROR(500, "服务器内部错误"),
    UNKNOW_ERROR(900, "未知错误");
    //状态码
    private int code;
    //异常类型的描述
    private String message;

    CustomExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
