package com.example.kyung.remotebbs.model;

/**
 * Created by Kyung on 2017-10-27.
 */

public class Result {
    private Data[] data;
    private String code;
    private String msg;

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data[] getData (){
        return data;
    }
    public void setData (Data[] data){
        this.data = data;
    }

    public String getCode () {
        return code;
    }
    public void setCode (String code) {
        this.code = code;
    }

    public boolean isSuccess(){
        return "200".equals(code);
    }

    @Override
    public String toString() {
        return "ClassPojo [data = "+data+", code = "+code+", msg = "+msg+"]";
    }
}
