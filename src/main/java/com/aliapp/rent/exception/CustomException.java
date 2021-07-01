package com.aliapp.rent.exception;

public class CustomException extends RuntimeException{
    private int code;
    private String message;

    public CustomException(CustomExceptionType customExceptionType, String message){
        this.code = customExceptionType.getCode();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
