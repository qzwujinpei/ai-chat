package com.dhl.aichat.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {
    private Integer code;
    private String msg;

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(BizExceptionEnume bizExceptionEnume) {
        super(bizExceptionEnume.getMsg());
        this.code = bizExceptionEnume.getCode();
        this.msg = bizExceptionEnume.getMsg();
    }

}
