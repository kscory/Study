package com.example.kyung.jsondatapractice;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kyung on 2017-10-17.
 */

public class Remote {

    public static String getData(String string){
        boolean runFlag = true;
        int accessCount = 0;
        final StringBuilder result = new StringBuilder();
        // 서버에 접속이 안될경우 반복해주며 5번 접속해도 안될 시 Error 값을 리턴
        while(runFlag) {
            try {
                URL url = new URL(string);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    String temp = "";
                    while((temp=br.readLine()) != null){
                        result.append(temp).append("\n");
                    }
                    br.close();
                    isr.close();
                } else{
                    Log.e("ServerError",connection.getResponseCode() +"");
                }
                connection.disconnect();
                runFlag = false;
            } catch (Exception e) {
                runFlag=true;
                accessCount++;
                Log.e("Error", e.toString());
                if(accessCount>5){
                    runFlag=false;
                    return "AccessError";
                }
            }
        }
        return result.toString();
    }
}
