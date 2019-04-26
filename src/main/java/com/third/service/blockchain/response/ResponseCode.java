package com.third.service.blockchain.response;

public enum ResponseCode {
    SUCCESS(200),PARAMTER(400),SESSION(401),FORBIDDEN(403),CONTENT(404),ERROE(500);
    private int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
