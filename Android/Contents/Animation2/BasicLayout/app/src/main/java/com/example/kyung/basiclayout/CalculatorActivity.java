package com.example.kyung.basiclayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6,btn7,btn8,btn9, btnSum, btnSub, btnMutiple, btnDiv, btnClear, btnRun ;
    TextView preView, resultView;
    String preViewText="";
    String resultText="";
    String tempOperSeq="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        preView = (TextView) findViewById(R.id.preview);
        resultView = (TextView) findViewById(R.id.resultView);

        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnSum = (Button) findViewById(R.id.btnSum);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMutiple = (Button) findViewById(R.id.btnMultiple);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnRun = (Button) findViewById(R.id.btnRun);

        preView.setOnClickListener(onClickListener);
        resultView.setOnClickListener(onClickListener);

        btn0.setOnClickListener(onClickListener);
        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);
        btn3.setOnClickListener(onClickListener);
        btn4.setOnClickListener(onClickListener);
        btn5.setOnClickListener(onClickListener);
        btn6.setOnClickListener(onClickListener);
        btn7.setOnClickListener(onClickListener);
        btn8.setOnClickListener(onClickListener);
        btn9.setOnClickListener(onClickListener);
        btnSum.setOnClickListener(onClickListener);
        btnSub.setOnClickListener(onClickListener);
        btnMutiple.setOnClickListener(onClickListener);
        btnDiv.setOnClickListener(onClickListener);
        btnClear.setOnClickListener(onClickListener);
        btnRun.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn0:
                    preViewText = preViewText + "0";
                    preView.setText(preViewText);
                    break;
                case R.id.btn1:
                    preViewText = preViewText + "1";
                    preView.setText(preViewText);
                    break;
                case R.id.btn2:
                    preViewText = preViewText + "2";
                    preView.setText(preViewText);
                    break;
                case R.id.btn3:
                    preViewText = preViewText + "3";
                    preView.setText(preViewText);
                    break;
                case R.id.btn4:
                    preViewText = preViewText + "4";
                    preView.setText(preViewText);
                    break;
                case R.id.btn5:
                    preViewText = preViewText + "5";
                    preView.setText(preViewText);
                    break;
                case R.id.btn6:
                    preViewText = preViewText + "6";
                    preView.setText(preViewText);
                    break;
                case R.id.btn7:
                    preViewText = preViewText + "7";
                    preView.setText(preViewText);
                    break;
                case R.id.btn8:
                    preViewText = preViewText + "8";
                    preView.setText(preViewText);
                    break;
                case R.id.btn9:
                    preViewText = preViewText + "9";
                    preView.setText(preViewText);
                    break;
                case R.id.btnSum:
                    preViewText = preViewText + "+";
                    preView.setText(preViewText);
                    break;
                case R.id.btnSub:
                    preViewText = preViewText + "-";
                    preView.setText(preViewText);
                    break;
                case R.id.btnMultiple:
                    preViewText = preViewText + "*";
                    preView.setText(preViewText);
                    break;
                case R.id.btnDiv:
                    preViewText = preViewText + "/";
                    preView.setText(preViewText);
                    break;
                case R.id.btnClear:
                    preViewText = "";
                    preView.setText(preViewText);
                    resultView.setText(preViewText);
                    break;
                case R.id.btnRun:
                    resultView.setText(preViewText);
                    break;
            }
        }
    };

    private void append(String str){
        if(preView.getText().toString().equals("0")){
            // 처음 연산자가 오면 예외처리
            if(str.equals("+") ||  str.equals("-") || str.equals("*") || str.equals("/")){
                Toast.makeText(this, "연산자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;

                // 연산자 연속으로 오면 예외처리

            }

        }
    }
}
