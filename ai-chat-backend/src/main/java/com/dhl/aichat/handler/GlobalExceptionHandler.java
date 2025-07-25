package com.dhl.aichat.handler;


import com.dhl.aichat.exception.BizException;
import com.dhl.aichat.result.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result handlerSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        String msg = e.getMessage().split("; ")[1];
        return Result.error(msg);
    }
    /**
     * 处理业务异常情况
     * 当方法中抛出未捕获的BizException时，该方法会被调用
     * 与doException不同，该方法能够处理特定的业务异常，并返回详细的错误代码和消息
     *
     * @param e 业务异常对象
     * @return 返回一个错误结果，包含错误代码和信息
     */
    @ExceptionHandler(BizException.class)
    public Result handlerBizException(BizException e) {
        Integer code = e.getCode();
        String msg = e.getMsg();
        return Result.error(code, msg);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return Result.error(666, "校验失败", errors);
    }
    /**
     * 处理未捕获的异常情况
     * 当方法中抛出未捕获的Exception时，该方法会被调用
     *
     * @param e 异常对象
     * @return 返回一个错误结果，包含异常信息
     */
    @ExceptionHandler(Exception.class)
    public Result doException(Exception e) {
        return Result.error(e.getMessage());
    }

}
