package com.example.kyung.subwaysearch.CustomButton;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatButton;

import com.example.kyung.subwaysearch.R;

/**
 * Created by Kyung on 2017-10-20.
 */

public class LineButton extends AppCompatButton {

    int position = 0;
    boolean clicked =false;
    int backgroundId = 0;
    int size=150;
    String lineNum =null;

    public LineButton(Context context, String num) {
        super(context);

        btnConstSetting(num);
        btnBackgroundSetting();
    }

    private void btnConstSetting(String num){
        switch (num){
            case "1": backgroundId=R.drawable.linebutton19; lineNum = num; break;
            case "2": backgroundId=R.drawable.linebutton19; lineNum = num; break;
            case "3": backgroundId=R.drawable.linebutton19; lineNum = num; break;
            case "4": backgroundId=R.drawable.linebutton19; lineNum = num; break;
            case "5": backgroundId=R.drawable.linebutton19; lineNum = num; break;
            case "6": backgroundId=R.drawable.linebutton19; lineNum = num; break;
            case "7": backgroundId=R.drawable.linebutton19; lineNum = num; break;
            case "8": backgroundId=R.drawable.linebutton19; lineNum = num; break;
            case "9": backgroundId=R.drawable.linebutton19; lineNum = num; break;
            case "I":  backgroundId=R.drawable.linebutton19; lineNum = "인천1"; break;
            case "I2":  backgroundId=R.drawable.linebutton19; lineNum = "인천2"; break;
            case "K":  backgroundId=R.drawable.linebutton19; lineNum = "경의중앙"; break;
            case "KK":  backgroundId=R.drawable.linebutton19; lineNum = "경강"; break;
            case "B":  backgroundId=R.drawable.linebutton19; lineNum = "분당"; break;
            case "A":  backgroundId=R.drawable.linebutton19; lineNum = "공항"; break;
            case "G":  backgroundId=R.drawable.linebutton19; lineNum = "경춘"; break;
            case "S":  backgroundId=R.drawable.linebutton19; lineNum = "신분당"; break;
            case "SS":  backgroundId=R.drawable.linebutton19; lineNum = "수인"; break;
            default: backgroundId=R.drawable.linebutton19; lineNum = num; break;
        }
    }
    private void btnBackgroundSetting(){
        setBackgroundResource(backgroundId);
        setText(lineNum);
        setTextSize(13);
    }

    public void clickButton(){
        clicked=true;
        setBackgroundResource(R.drawable.linebutton19);
    }
    public void unClickButton(){

    }
    public int getSize(){
        return size;
    }
    public void setPosition(int position){
        this.position = position;
    }
}
