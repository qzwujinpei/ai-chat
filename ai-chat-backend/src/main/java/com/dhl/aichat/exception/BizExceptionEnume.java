package com.dhl.aichat.exception;

import lombok.Getter;

@Getter
public enum BizExceptionEnume {
    // 用户已存在
    USER_EXIST(100, "用户已存在"),
    // 用户不存在
    USER_NOT_EXIST(101, "用户不存在"),
    CAPTCHA_ERROR(102, "验证码错误"),



    // 文件上产和深度思考不能同时开启
    FILE_UPLOAD_AND_DEEP_THINKING_CANNOT_BE_OPENED_AT_THE_SAME_TIME(200, "文件上产和深度思考不能同时开启"),
    ;

    private final Integer code;
    private final String msg;

    BizExceptionEnume(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
