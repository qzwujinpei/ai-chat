package com.dhl.aichat.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIResult<T> implements Serializable {
    /**
     * 返回的消息
     */
    private String msg;
 
    /**
     * 返回成功的消息
     */
    private static String successMsg = "操作成功";
 
    /**
     * 返回错误的消息
     */
    private static String errorMsg = "操作失败";
    /**
     * 返回的状态
     */
    private Integer status = 200;
    /**
     * 返回的结果
     */
    private boolean res = true;
 
    /**
     * 返回的错误码
     */
    private Integer errorCode;
    /**
     * 返回的数据
     */
    private T data;
 
    /**
     * 返回成功的Code
     */
    private Integer okCode;
 
    /**
     * 返回为成功的构造函数
     *
     * @param msg
     * @param data
     */
    public AIResult(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }
 
    public AIResult(String msg, T data, Integer okCode) {
        this.msg = msg;
        this.data = data;
        this.okCode = okCode;
    }
 
    /**
     * 构造失败的回调结果
     *
     * @param msg
     * @param data
     * @param errorCode
     * @param status
     */
    public AIResult(String msg, T data, Integer errorCode, Integer status) {
        this.msg = msg;
        this.data = data;
        this.errorCode = errorCode;
        this.status = status;
        this.res = false;
    }
 
    public AIResult(String msg, int status, Boolean res, T data) {
        this.msg = msg;
        this.res = res;
        this.status = status;
        this.data = data;
    }
 
 
    /**
     * 调用成功的时候，返回成功的状态
     *
     * @param msg
     * @param data
     * @return
     */
    public static <T> AIResult<T> ok(String msg, T data) {
        return new AIResult(msg, data);
    }
 
    /**
     * 调用成功的时候，返回成功的状态
     *
     * @param msg
     * @param data
     * @return
     */
    public static <T> AIResult<T> ok(String msg, T data, Integer okCode) {
        return new AIResult<>(msg, data, okCode);
    }
 
    /**
     * 调用成功的时候，返回成功的状态
     *
     * @return
     */
    public static <T> AIResult<T> get(boolean res) {
        if (res) {
            return AIResult.ok(successMsg);
        } else {
            return AIResult.fail(errorMsg);
        }
    }
 
    public static <T> AIResult<T> get(T data) {
        if (data != null) {
            return AIResult.ok(successMsg, data);
        } else {
            return AIResult.fail(errorMsg);
        }
    }
 
    public static <T> AIResult<T> ok(String msg) {
        return new AIResult<>(msg, null);
    }
 
    public static <T> AIResult<T> ok() {
        return new AIResult<>("操作成功", null);
    }
 
    /**
     * 调用失败的时候，返回失败的状态
     *
     * @param msg
     * @param data
     * @return
     */
    public static <T> AIResult<T> fail(String msg, T data, Integer errorCode, Integer status) {
        return new AIResult<>(msg, data, errorCode, status);
    }
 
    /**
     * 调用失败的时候，返回失败的状态
     */
    public static <T> AIResult<T> fail(String msg) {
        return new AIResult<>(msg, null, null, 101);
    }
    public static <T> AIResult<T> fail(String msg, T data) {
        return new AIResult<>(msg, data, null, null);
    }
    public static <T> AIResult<T> fail() {
        return new AIResult<>(errorMsg, null, null, null);
    }
}