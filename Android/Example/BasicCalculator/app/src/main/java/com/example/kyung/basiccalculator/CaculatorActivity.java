package com.example.kyung.basiccalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// View.OnClickListener를 상속 받는 것으로 이번에는 한다.
public class CaculatorActivity extends AppCompatActivity
        implements  View.OnClickListener{

    String preViewText="";
    String resultViewText="";
    String preInput="";
    String preBeforeInput="";
    int leftParenthesesCount=0;
    int rightParenthesesCount=0;
    boolean checkedDot=false;

    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    Button btnSum,btnSubtracttion,btnMultiplication,btnDivison, btnClear, btnRun, btnLeftParentheses, btnRightParentheses;
    Button btnDot;

    TextView textViewPreView, textViewResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caculator);

        initView();
        initListener();

    }

    // 이렇게 하면 this를 할때 MainActivity.this 라고 안해도 된다.
    // 이외는 차이점이 없다.
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn0:
                inputPreView("0");
                break;
            case R.id.btn1:
                inputPreView("1");
                break;
            case R.id.btn2:
                inputPreView("2");
                break;
            case R.id.btn3:
                inputPreView("3");
                break;
            case R.id.btn4:
                inputPreView("4");
                break;
            case R.id.btn5:
                inputPreView("5");
                break;
            case R.id.btn6:
                inputPreView("6");
                break;
            case R.id.btn7:
                inputPreView("7");
                break;
            case R.id.btn8:
                inputPreView("8");
                break;
            case R.id.btn9:
                inputPreView("9");
                break;
            case R.id.btnDot:
                inputPreView(".");
                break;
            case R.id.btnSum:
                inputPreView("+");
                break;
            case R.id.btnSubtraction:
                inputPreView("-");
                break;
            case R.id.btnMultiplication:
                inputPreView("*");
                break;
            case R.id.btnDivision:
                inputPreView("/");
                break;
            case R.id.btnLeftParentheses:
                inputPreView("(");
                break;
            case R.id.btnRightParentheses:
                inputPreView(")");
                break;
            case R.id.btnClear:
                preViewText="입력창";
                resultViewText="결과창";
                preInput="=";
                preBeforeInput="";
                leftParenthesesCount=0;
                rightParenthesesCount=0;
                checkedDot=false;
                textViewPreView.setText(preViewText);
                textViewResultView.setText(resultViewText);
                break;
            case R.id.btnRun:
                inputResultView(preViewText);
                break;
        }
    }
    private double calc(String preViewText){
        double result;
        // 정규식 처리
        String reg = "(?<=[*/+-])|(?=[*/+-])|(?<=[\\(\\)])|(?=[\\(\\)])";
        // split 할때 0번째 인덱스에서 잘릴 경우 맨 앞은 "null" 값이 들어가므로 주의할 것(여기서는 처음에 "(" 가 나올 경우)
        String splittedText[] = preViewText.split(reg);

        // #1
        ArrayList<String> tempCalculation = new ArrayList<>();

        for(String item : splittedText){
            tempCalculation.add(item);
        }

        if(tempCalculation.get(0).equals("")){
            tempCalculation.remove(0);
        }



        while(true){

            if(preViewText.equals("")) {break;}
            boolean checkMDOperation=false;
            int leftParenthesis=0;
            int rightParenthesis=0;
            String tempRec ="";

            for(int i=0; i<tempCalculation.size() ; i++) {
                if(tempCalculation.get(i).equals(")")) {
                    rightParenthesis=i;
                    break;
                }
            }

            for(int i=rightParenthesis; i>=0 ; i--) {
                if(tempCalculation.get(i).equals("(")) {
                    leftParenthesis=i;
                    break;
                }
            }
            //Log.d("1차 결과값", "size2:"+tempCalculation.size()+"");
            for(int i=leftParenthesis+1 ; i<rightParenthesis ;i++) {
                tempRec=tempRec+tempCalculation.get(i);
            }

            if(!tempRec.equals("")) {
                double calParenthesis = calc(tempRec);

                tempCalculation.set(leftParenthesis,calParenthesis+"");
                for(int i =0 ; i<rightParenthesis-leftParenthesis ; i++) {
                    tempCalculation.remove(leftParenthesis+1);
                }

               for(int i=0; i<tempCalculation.size(); i++){
               }
                continue;
            }

            for(int i=0 ; i<tempCalculation.size() ; i++) {
                if(tempCalculation.get(i).equals("*") || tempCalculation.get(i).equals("/")){
                    checkMDOperation=true;
                }
            }
            for(int i=0 ; i<tempCalculation.size() ; i++){
                double tempResult=0;
                if(tempCalculation.get(i).equals("*") || tempCalculation.get(i).equals("/")){
                    if(tempCalculation.get(i).equals("*")) {
                        tempResult = Double.parseDouble(tempCalculation.get(i-1)) * Double.parseDouble(tempCalculation.get(i+1));
                    } else{
                        tempResult = Double.parseDouble(tempCalculation.get(i-1)) / Double.parseDouble(tempCalculation.get(i+1));
                    }

                    tempCalculation.set(i,tempResult+"");
                    tempCalculation.remove(i-1);
                    tempCalculation.remove(i);
                    break;
                }
                if(checkMDOperation == false && (tempCalculation.get(i).equals("+") || tempCalculation.get(i).equals("-"))){
                    if(tempCalculation.get(i).equals("+")) {
                        tempResult = Double.parseDouble(tempCalculation.get(i - 1)) + Double.parseDouble(tempCalculation.get(i+1));
                    } else{
                        tempResult = Double.parseDouble(tempCalculation.get(i - 1)) - Double.parseDouble(tempCalculation.get(i+1));
                    }
                    tempCalculation.set(i,tempResult+"");
                    tempCalculation.remove(i-1);
                    tempCalculation.remove(i);
                    break;
                }
            }
            if(tempCalculation.size()==1){
                break;
            }
        }
        result = Double.parseDouble(tempCalculation.get(0));
        return result;
    }

    public void inputPreView(String value){
        //연산자가 처음에 오면 오류
        if(textViewPreView.getText().toString().equals("입력창") || preInput.equals("=")) {
            if (value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/")) {
                Toast.makeText(this, "0 보다 큰 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            preViewText="";
        }

        // 0이 처음에 연속해서 두번 나올 경우
        if(preViewText.equals("0") && value.equals("0")){
            Toast.makeText(this,"잘못된 입력 형식입니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        // 연산자 + 0 + 0 인 경우
        if(value.equals("0") && preInput.equals("0")){
            if(preBeforeInput.equals("+") || preBeforeInput.equals("-") || preBeforeInput.equals("*") || preBeforeInput.equals("/")
                    || preBeforeInput.equals("(") || preBeforeInput.equals(")")){
                Toast.makeText(this, "잘못된 입력 형식입니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // 연산자 + 0 + 숫자 인 경우
        if(preInput.equals("0") && (value.equals("0") || value.equals("1") || value.equals("2") || value.equals("3") || value.equals("4") || value.equals("5")
                || value.equals("6") || value.equals("7") || value.equals("8") || value.equals("9"))){
            if(preBeforeInput.equals("+") || preBeforeInput.equals("-") || preBeforeInput.equals("*") || preBeforeInput.equals("/")
                    || preBeforeInput.equals("=") || preBeforeInput.equals("")|| preBeforeInput.equals("(")|| preBeforeInput.equals(")")){
                Toast.makeText(this, "잘못된 입력 형식입니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // 괄호 + 0 + 숫자 ???
        // dot + 연산자
        if(preInput.equals(".") && (value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/"))){
            Toast.makeText(this,"잘못된 입력형식입니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        //연속된 연산자 오류
        if(preInput.equals("+") || preInput.equals("-") || preInput.equals("*") || preInput.equals("/")){
            if(value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/")){
                Toast.makeText(this, "연속해서 연산자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

        }

        // dot을 연속해서 쓰는 경우
        if(value.equals(".") && preInput.equals(".")){
            Toast.makeText(this, "연속해서 dot을 입력할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 숫자 뒤에 괄호가 나올 경우 자동 곱하기 계산
        if(preInput.equals("0") || preInput.equals("1") || preInput.equals("2") || preInput.equals("3") || preInput.equals("4") || preInput.equals("5")
                || preInput.equals("6") || preInput.equals("7") || preInput.equals("8") || preInput.equals("9")){
            if(value.equals("(")){
                preViewText=preViewText+"*";
            }
        }
        // 닫는 괄호 뒤에 바로 숫자가 나올 경우 자동 곱하기 계산
        if(value.equals("0") || value.equals("1") || value.equals("2") || value.equals("3") || value.equals("4") || value.equals("5")
                || value.equals("6") || value.equals("7") || value.equals("8") || value.equals("9")){
            if(preInput.equals(")")){
                preViewText=preViewText+"*";
            }
        }

        // 여는 괄호 뒤에 바로 닫는 괄호가 나올 경우 오류
        if(preInput.equals("(") && value.equals(")")){
            Toast.makeText(this, "잘못된 형식입니다..",Toast.LENGTH_SHORT).show();
            return;
        }

        // 연산자 뒤에 괄호를 닫을 시 오류
        if(preInput.equals("+") || preInput.equals("-") || preInput.equals("*") || preInput.equals("/")){
            if(value.equals(")")){
                Toast.makeText(this, "괄호를 닫을 수 없습니다.",Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // 괄호를 연 뒤 바로 연산자가 나올경우 오류
        if(preInput.equals("(")){
            if(value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/")){
                Toast.makeText(this, "괄호 다음에 연산자가 나올 수 없습니다.",Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //결과 다음 입력시 preView 초기화
        if(preInput.equals("=")){
            preViewText="";
            resultViewText="결과창";
            textViewResultView.setText(resultViewText);
        }

        // 처음 숫자가 dot인 경우 0을 써줌
        if((preInput.equals("+") || preInput.equals("-") || preInput.equals("*") || preInput.equals("/")
                || preInput.equals("=") || textViewPreView.getText().toString().equals("입력창"))
                && value.equals(".")){
            preViewText=preViewText+"0";
        }
        // dot 다음 괄호 여닫기
        if(preInput.equals(".") && (value.equals(")") || value.equals("("))){
            Toast.makeText(this,"잘못된 입력입니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        // 괄호 다음 dot 입력
        if(preInput.equals("(") && value.equals(".")){
            preViewText=preViewText+"0";
        }
        if(preInput.equals(")") && value.equals(".")){
            preViewText=preViewText+"*0";
        }

        //연속 괄호
        if(preInput.equals("(") && value.equals(")")){
            Toast.makeText(this,"괄호안에 숫자를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        //괄호 입력시 카운트 추가하여 왼쪽
        if(value.equals(")")){
            if(leftParenthesesCount<=rightParenthesesCount){
                Toast.makeText(this,"왼쪽 괄호를 먼저 입력해주세요",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(value.equals("(")){
            leftParenthesesCount++;
        }
        if(value.equals(")")){
            rightParenthesesCount++;
        }

        // dot + 숫자 + dot
        if(checkedDot==true && value.equals(".")){
            Toast.makeText(this,"숫자를 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        }
        // dot이 눌리면 check는 true로
        if(value.equals(".")){
            checkedDot=true;
        }
        if(value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/") || value.equals("(") || value.equals(")")){
            checkedDot=false;
        }

        preViewText=preViewText+value;
        preBeforeInput=preInput;
        preInput=value;
        textViewPreView.setText(preViewText);
    }

    public void inputResultView(String total){
        if(preViewText.equals("입력창")){
            return;
        }

        // 결과 요청 직전에 연산자, dot이면 오류처리
        if(preInput.equals("+") || preInput.equals("-") || preInput.equals("*") || preInput.equals("/") || preInput.equals("=") || preInput.equals(".")){
            Toast.makeText(this,"잘못된 입력 형식입니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        // 결과 요청 시 왼쪽,오른쪽 괄호 개수가 다르다면 오류처리
        if(leftParenthesesCount!=rightParenthesesCount){
            Toast.makeText(this,"괄호개수를 확인해주세요",Toast.LENGTH_SHORT).show();
            return;
        }

        // 오류가 없으면 계산 시작
        double result = calc(total);

        leftParenthesesCount=0;
        rightParenthesesCount=0;
        checkedDot=false;
        textViewResultView.setText(result+"");
        preBeforeInput=preInput;
        preInput="=";
    }

    private void initView(){
        btn0 = (Button) findViewById(R.id.btn1);
        btn1 = (Button) findViewById(R.id.btn2);
        btn2 = (Button) findViewById(R.id.btn3);
        btn3 = (Button) findViewById(R.id.btn4);
        btn4 = (Button) findViewById(R.id.btn5);
        btn5 = (Button) findViewById(R.id.btn6);
        btn6 = (Button) findViewById(R.id.btn7);
        btn7 = (Button) findViewById(R.id.btn8);
        btn8 = (Button) findViewById(R.id.btn9);
        btn9 = (Button) findViewById(R.id.btn0);
        btnSum = (Button) findViewById(R.id.btnSum);
        btnDot = (Button) findViewById(R.id.btnDot);
        btnSubtracttion = (Button) findViewById(R.id.btnSubtraction);
        btnMultiplication = (Button) findViewById(R.id.btnMultiplication);
        btnDivison = (Button) findViewById(R.id.btnDivision);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnRun = (Button) findViewById(R.id.btnRun);
        btnLeftParentheses = (Button) findViewById(R.id.btnLeftParentheses);
        btnRightParentheses = (Button) findViewById(R.id.btnRightParentheses);

        textViewPreView = (TextView) findViewById(R.id.textViewPreView);
        textViewResultView = (TextView) findViewById(R.id.textViewResultView);
    }
    private void initListener(){
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnSum.setOnClickListener(this);
        btnSubtracttion.setOnClickListener(this);
        btnMultiplication.setOnClickListener(this);
        btnDivison.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnRun.setOnClickListener(this);
        btnLeftParentheses.setOnClickListener(this);
        btnRightParentheses.setOnClickListener(this);
    }
}
