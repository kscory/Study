package com.example.kyung.musicplayer.util;

/**
 * Created by Kyung on 2017-10-15.
 */

public class UnitChangeUtil {

    // 16754625 와 같은 milisecond 단위를 => 03:15 와같이 만든다.
    public String miliToSec(int mili){
        int sec = mili/1000;
        int min = sec/60;
        sec = sec % 60;

        return String.format("%02d",min) + ":" + String.format("%02d",sec);
    }
}
