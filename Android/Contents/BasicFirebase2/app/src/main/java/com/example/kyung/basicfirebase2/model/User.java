package com.example.kyung.basicfirebase2.model;

import com.google.firebase.database.Exclude;

import java.util.List;

/**
 * Created by Kyung on 2017-10-30.
 */

public class User {
    public String user_id;
    public String user_password;
    public String username;
    public String age;
    public String email;

    @Exclude // database field 에서 제외하고 싶을때 사용
    public boolean check = false;

    // 내가 작성한 글 목록
    public List<Bbs> bbs;

    public User() {
        // 쓰지는 않지만 디폴트 생성자를 만든다 => 파이어베이스가 디폴트 생성자를 호출하기 때문
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String user_password, String username, String age, String email) {
        this.user_password = user_password;
        this.username = username;
        this.age = age;
        this.email = email;
    }
}
