package com.example.kyung.realm.realm;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Kyung on 2017-11-17.
 */

// RealmObject를 상속받으면 Realm 테이블이 된다.
public class Bbs extends RealmObject{
    @PrimaryKey // 키로 설정해 준다,
    private int no;

    private String title;
    private String content;
    private String user;
    private long date;

    @Ignore // 테이블의 컬럼으로 사용되지 않는다.
    private String test;

    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
}
