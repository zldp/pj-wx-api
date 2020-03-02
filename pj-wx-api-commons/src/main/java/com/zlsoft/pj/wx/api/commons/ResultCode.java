package com.zlsoft.pj.wx.api.commons;

public enum ResultCode {
    // 成功
    SUCCESS(0),
    // 失败
    FAIL(-1),
    // 账号不存在
    NUMBER_NOT_FIND(401),
    // 错误签名
    ERRORSIGN(402),
    // 404 没有此路径
    NOT_FIND(404),
    //服务器内部错误
    INTERNAL_SERVER_ERROR(500);

    private final int code;


    ResultCode(int code) {
        this.code = code;
    }

    public int code(){
        return code;
    }
}
