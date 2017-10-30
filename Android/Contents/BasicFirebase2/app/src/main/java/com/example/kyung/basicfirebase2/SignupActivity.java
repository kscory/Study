package com.example.kyung.basicfirebase2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.kyung.basicfirebase2.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Kyung on 2017-10-31.
 */

public class SignupActivity extends AppCompatActivity{

    FirebaseDatabase database;
    DatabaseReference userRef;

    private EditText editId, editPassword, editName, editAge, editEmail;
    private Button btnSign, btnReset, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");

        initView();
    }

    private void initView(){
        editId = findViewById(R.id.editId);
        editPassword = findViewById(R.id.editPassword);
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editEmail = findViewById(R.id.editEmail);

        btnSign = findViewById(R.id.btnSign);
        btnReset = findViewById(R.id.btnReset);
        btnCancel = findViewById(R.id.btnCancel);

    }

    // 리셋 버튼 클릭시 모든 데이터 리셋(리셋버튼과 연결)
    public void reset(View view){
        editId.setText("");
        editPassword.setText("");
        editName.setText("");
        editAge.setText("");
        editEmail.setText("");
    }
    // 회원가입 취소버튼 클릭시 이전 화면으로 이동(캔슬버튼과 연결)
    public void cancel(View view){
        setResult(RESULT_CANCELED);
        finish();
    }
    // 사용자 등록(siginup버튼과 연결)
    public void signup(View view){
        String user_id = editId.getText().toString();
        String user_password = editPassword.getText().toString();
        String username = editName.getText().toString();
        String age = editAge.getText().toString();
        String email = editEmail.getText().toString();

        // 회원가입 형식 체크
        if("".equals(user_id) || "".equals(user_password) || "".equals(username) || "".equals(email) || "".equals(age)){
            Toast.makeText(this, "값을 입력해주세요.", Toast.LENGTH_LONG).show();
        } else if(!isPasswordValid(user_password)){
            Toast.makeText(this, "비밀번호 형식이 유효하지 않습니다.", Toast.LENGTH_LONG).show();
        } else if(!isAgeValid(age)){
            Toast.makeText(this, "나이 형식이 유효하지 않습니다.", Toast.LENGTH_LONG).show();
        } else if(!isEmailValid(email)){
            Toast.makeText(this, "이메일 형식이 유효하지 않습니다.", Toast.LENGTH_LONG).show();
        } else {
            User user = new User(user_password,username,age,email);
            userRef.child(user_id).setValue(user);

            // 회원가입 조건 만족시 자동 로그인 처리를 위한 데이터 전달
            Intent intent = getIntent();
            intent.putExtra(Const.user_id,user_id);
            intent.putExtra(Const.user_password,user_password);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    // 아이디 형식이 유효한지 체크
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
    // 비밀번호 형식이 유효한지 체크 (4자리 이상 & 숫자가 1개 이상)
    private boolean isPasswordValid(String password) {
        boolean result = false;
        if(password.length()>4){
            char digitcheck[] = password.toCharArray();
            for(char checking : digitcheck){
                if(checking>='0' && checking<='9'){
                    result = true;
                    break;
                }
            }
        } else{
            result = false;
        }
        return result;
    }
    // 나이 형식이 유효한지 체크
    private boolean isAgeValid(String age){
        boolean result = true;
        char ages[] = age.toCharArray();
        for(char checking : ages){
            if(checking<'0' || checking>'9'){
                result = false;
                break;
            }
        }
        return result;
    }
}
