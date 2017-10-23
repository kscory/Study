package com.example.kyung.subwaysearch;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kyung on 2017-10-19.
 */

public class Remote {

    public static String getData(String stringURL){
        boolean runFlag = true;
        int ServerConCount = 0;
        String result="";
        while(runFlag) {
            try {
                URL url = new URL(stringURL);
                HttpURLConnection connection = null;
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    String temp = "";
                    while ((temp = br.readLine()) != null) {
                        result = result + temp + "\n";
                    }
                    br.close();
                    isr.close();
                } else {
                    Log.e("ServerError", connection.getResponseCode() + "");
                }
                connection.disconnect();
                runFlag = false;
            } catch (MalformedURLException e) {
                Log.e("URLError",e.toString());
            } catch (IOException e) {
                ServerConCount++;
                Log.e("ConError",e.toString());
                if(ServerConCount>15){
                    runFlag=false;
                    return "ServerConError";
                }
            }
        }
        return result;
    }
}
