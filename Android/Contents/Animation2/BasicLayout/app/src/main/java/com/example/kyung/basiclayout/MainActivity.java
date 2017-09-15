package com.example.kyung.basiclayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //레이아웃에 정의된 위젯의 아이디로 해당 객체를 변수에 선언
    Button btnFrame, btnLinear, btnGrid, btnRelative, btnCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 선언된 변수에 실제 위젯을 할당
        btnFrame = (Button) findViewById(R.id.btnFrame);
        btnLinear = (Button) findViewById(R.id.btnLinear);
        btnGrid = (Button) findViewById(R.id.btnGrid);
        btnRelative = (Button) findViewById(R.id.btnRelative);
        btnCalculator = (Button) findViewById(R.id.btnCalculator);

        // 위에서 저장한 변수를 사용
        // 아래에 선언한 실행객체를 리스너에 던져준다.
        btnFrame.setOnClickListener(onClickListener);
        btnLinear.setOnClickListener(onClickListener);
        btnGrid.setOnClickListener(onClickListener);
        btnRelative.setOnClickListener(onClickListener);
        btnCalculator.setOnClickListener(onClickListener);

        // Button 같은 경우는 바꿀수도 있다... 하지만 아직....
    }

    // 중복되는 리스너를 전역변수로 선언할 수 있다.
    // View에서 넘겨주므로 id를 넘겨받을 수 있다.
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            // 액티비티(메이저 컴포넌트) 실행
            // 1. 인텐트 (시스템에 전달되는 메시지객체) -> Intent intent = new Intent(컨텍스트("시스템자원"),타겟);
            Intent intent = null;
            switch (v.getId()){
                case R.id.btnFrame:
                    intent = new Intent(MainActivity.this, FrameActivity.class); //this라 하면 알아서 컨텍스트를 불러옴
                    //                      컨텍스트            타겟
                    break;
                case R.id.btnLinear:
                    intent = new Intent(MainActivity.this, LinearActivity.class); //this라 하면 알아서 컨텍스트를 불러옴
                    break;
                case R.id.btnGrid:
                    intent = new Intent(MainActivity.this, GridActivity.class); //this라 하면 알아서 컨텍스트를 불러옴
                    break;
                case R.id.btnRelative:
                    intent = new Intent(MainActivity.this, RelativeActivity.class);
                    break;
                case R.id.btnCalculator:
                    intent = new Intent(MainActivity.this, CalculatorActivity.class);
                    break;
            }
            // 2. 엑티비티 실행요청
            startActivity(intent);

        }
    };
}
