package com.zlsoft.pj.wx.api.commons;

import lombok.Data;

/**
 * 响应结果实体类
 * @author dp
 * @version 1.0.0
 * @date 2020-02-28 14:29
 */
@Data
public class Result<T> {
    /**
     * 返回码
     */
    private int code;
    /**
     * 返回具体消息
     */
    private String msg;

    /**
     * 具体返回的数据
     */
    private Object data;

    /**
     * 设置返回的所有内容
     * @param code
     * @param msg
     * @param data
     */
    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(){}

    /**
     * 设置返回码
     * @param resultCode
     * @return
     */
    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }
}
