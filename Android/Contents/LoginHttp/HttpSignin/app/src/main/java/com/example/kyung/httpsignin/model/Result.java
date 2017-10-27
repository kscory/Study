package com.example.kyung.httpsignin.model;

/**
 * Created by Kyung on 2017-10-25.
 */

public class Result {
    public static final String OK = "200";

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isOK(){
        return OK.equals(code);
    }
}
