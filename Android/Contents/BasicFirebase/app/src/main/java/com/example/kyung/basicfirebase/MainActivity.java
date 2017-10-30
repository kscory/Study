package com.example.kyung.basicfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textMsg) TextView textMsg;
    @BindView(R.id.editText) EditText editText;

    FirebaseDatabase database;
    DatabaseReference chatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 데이터 베이스 connection
        database = FirebaseDatabase.getInstance();
        // key값의 위치, 없으면 그냥 생성
        chatRef = database.getReference("chatMsg");
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            textMsg.setText("");
            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                String msg = snapshot.getValue(String.class);
                // 혹은 String msg = (String) snapshot.getValue();

                textMsg.setText(textMsg.getText().toString()+"\n"+msg);
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    // 항상 시작후 중지되었을 때 리스너를 중지시켜주어야 한다. (안하게 되면 서버랑 계속해서 통신하게 된다.)
    @Override
    protected void onResume() {
        super.onResume();
        chatRef.addValueEventListener(valueEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatRef.removeEventListener(valueEventListener);
    }

    @OnClick(R.id.btnSend)
    public void send(View view){
        String msg = editText.getText().toString();
        if(msg == null || "".equals(msg)){
            msg = "none";
        }
        // 유일한 키를 생성하고(push) 그 키를 가져온다.(getkey) => 유일한 node를 하나 생성
        String key = chatRef.push().getKey();
        // 방금 생성한 키로 레퍼런스를 가져온 후(child) node에 값을 넣는다.(setValue)
        chatRef.child(key).setValue(msg);
    }
}
