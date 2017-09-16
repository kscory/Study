package com.example.kyung.basicwidget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerActivity extends AppCompatActivity {
    TextView textView;
    Spinner spinner;
    // 1. 스피너에 입력될 데이터가 정의
    String data[] = {"월", "화", "수", "목", "금", "토", "일"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        textView = (TextView) findViewById(R.id.textView);


        // 2. 스피너와 데이터를 연결하는 아답터를 정의 (안드로이드 정의되어 있는 것은 android. 으로)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, data);

        // 3. 아답터와 스피너(리스트)를 연결
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        // 4. 스피너에 리스너를 달아준다.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            // position : spinner의 인덱스로 data의 인덱스와 동일
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = data[position];
                textView.setText(selectedValue);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    class A{
        public void create(){
            ArrayList a = new ArrayList();

            new B(){
                void call(){
                    // 주소를 참조하는데 수정..... 오류
                    //ArrayList b =a;
                }

            };
        }
    }
    public interface  B{

    }

}
