package com.aliapp.rent.util;

import com.aliapp.rent.exception.CustomException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
public class ResponseEntity implements Serializable {
    private static final Integer SUCCESS_CODE = 200;
    private static final Integer FAIL_CODE = 300;

    @Setter
    @Getter
    private Integer code;
    @Setter
    @Getter
    private Object message;
    @Setter
    @Getter
    private Object data;


    public ResponseEntity(Integer code, Object message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseEntity success() {
        return new ResponseEntity(SUCCESS_CODE, "成功", null);
    }

    public static ResponseEntity success(Object data) {
        return new ResponseEntity(SUCCESS_CODE, "成功", data);
    }

    public static ResponseEntity fail() {
        return new ResponseEntity(FAIL_CODE, "失败", null);
    }

    public static ResponseEntity fail(Object message) {
        return new ResponseEntity(FAIL_CODE, message, null);
    }

    public static ResponseEntity fail(CustomException ex) {
        return new ResponseEntity(ex.getCode(), ex.getMessage(), null);
    }

    public static ResponseEntity build(Integer code, String message, Object data) {
        return new ResponseEntity(code, message, data);
    }
}
