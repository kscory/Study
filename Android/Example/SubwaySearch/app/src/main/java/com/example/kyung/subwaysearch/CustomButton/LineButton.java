package com.example.kyung.subwaysearch.CustomButton;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;

import com.example.kyung.subwaysearch.R;

/**
 * Created by Kyung on 2017-10-20.
 */

public class LineButton extends AppCompatButton {

    int position = 0;
    private int backgroundId = 0;
    private int size=150;
    private String lineNum =null;
    int color = 0;
    // 이를 사용하면 백그라운드 세팅 가능
    GradientDrawable gd = new GradientDrawable();

    public LineButton(Context context, String num) {
        super(context);

        btnConstSetting(num);
        btnBackgroundSetting();
    }

    private void btnConstSetting(String num){
        switch (num){
            case "1": color = R.color.colorLine1; lineNum = num; break;
            case "2": color = R.color.colorLine2; lineNum = num; break;
            case "3": color = R.color.colorLine3; lineNum = num; break;
            case "4": color = R.color.colorLine4; lineNum = num; break;
            case "5": color = R.color.colorLine5; lineNum = num; break;
            case "6": color = R.color.colorLine6; lineNum = num; break;
            case "7": color = R.color.colorLine7; lineNum = num; break;
            case "8": color = R.color.colorLine8; lineNum = num; break;
            case "9": color = R.color.colorLine9; lineNum = num; break;
            case "I": color = R.color.colorLineI; lineNum = "인천1"; break;
            case "I2": color = R.color.colorLineI2; lineNum = "인천2"; break;
            case "K": color = R.color.colorLineK; lineNum = "경의중앙"; break;
            case "KK": color = R.color.colorLineKK; lineNum = "경강"; break;
            case "B": color = R.color.colorLineB; lineNum = "분당"; break;
            case "A": color = R.color.colorLineA; lineNum = "공항"; break;
            case "G": color = R.color.colorLineG; lineNum = "경춘"; break;
            case "S": color = R.color.colorLineS; lineNum = "신분당"; break;
            default: color = R.color.colorLineSU; lineNum = "수인"; break;
        }
    }
    private void btnBackgroundSetting(){
        color = ContextCompat.getColor(getContext(),color);
        gd.setColor(Color.WHITE);
        gd.setShape(GradientDrawable.OVAL);
        gd.setStroke(4,color);
        setBackground(gd);
        setText(lineNum);
        setTextSize(13);
        setTextColor(color);
    }

    public void clickButton(){
        setSelected(true);
        gd.setColor(color);
        setTextColor(Color.WHITE);
    }
    public void unClickButton(){
        setSelected(false);
        gd.setColor(Color.WHITE);
        setTextColor(color);
    }
    public int getSize(){
        return size;
    }
    public void setPosition(int position){
        this.position = position;
    }
    public int getPosition(){
        return position;
    }
    public int getColor(){
        return color;
    }
}
