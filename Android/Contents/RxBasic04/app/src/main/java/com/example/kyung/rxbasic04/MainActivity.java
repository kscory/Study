package com.example.kyung.rxbasic04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.Random;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 0. 로그인 체크하기
        Observable<TextViewTextChangeEvent> idEmitter = RxTextView.textChangeEvents(findViewById(R.id.editId));
        Observable<TextViewTextChangeEvent> pwEmitter = RxTextView.textChangeEvents(findViewById(R.id.editPassword));

        // 조건 id가 5자 이상이고, pw가 8자 이상이면 btnSignin의 enable을 true로 아니면 false
        Observable.combineLatest(
                idEmitter,
                pwEmitter,
                (id, pw) -> id.text().length() >= 5 && pw.text().length() >= 8
        ).subscribe(
                flag -> findViewById(R.id.btnSignin).setEnabled(flag)
        );

        // 1. editText에 입력되는 값을 체크해서 실시간으로 Log를 뿌려준다.
        RxTextView.textChangeEvents((EditText)findViewById(R.id.editText))
                .subscribe(ch -> Log.i("RxBinding", "word: " + ch.text()));

        // 2. 버튼을 클릭하면 edditText에 Random 숫자를 입력
        RxView.clicks(findViewById(R.id.button))
                // button 에는 button 타입의 오브젝트가 리턴되는데 이를 문자타입으로 변경
                .map(button -> new Random().nextInt() + "")
                // subscribe 에서는 map 에서 변형된 타입을 받게 된다.
                .subscribe(number -> {
                    ((EditText) findViewById(R.id.editText)).setText(number);
                });
    }
}
