package com.example.kyung.basicfirebase2.model;

/**
 * Created by Kyung on 2017-10-30.
 */

public class Bbs {
    public String bbs_id;
    public String content;
    public String date;
    public String user_id;
    public String username;

    public Bbs(){
        // 파이어베이스에서 parsing 할 때 한번 호출
    }
    public Bbs(String content, String date, String user_id, String username){
        this.content = content;
        this.date = date;
        this.user_id = user_id;
        this.username = username;
    }
}
