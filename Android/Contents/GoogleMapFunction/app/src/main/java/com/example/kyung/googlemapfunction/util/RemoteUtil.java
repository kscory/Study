package com.example.kyung.googlemapfunction.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Kyung on 2017-11-22.
 */

public class RemoteUtil {

    public static String getData(String string){
        boolean runFlag = true;
        int count = 0;
        StringBuilder result = new StringBuilder();
        while (runFlag) {
            try {
                URL url = new URL(string);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader isr = new InputStreamReader(con.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    String temp = "";
                    while ((temp = br.readLine()) != null) {
                        result.append(temp).append("\n");
                    }

                    br.close();
                    isr.close();

                } else {
                    Log.e("Server Error", con.getResponseCode() + "");
                }
                con.disconnect();
            } catch (Exception e) {
                runFlag = true;
                count++;
                Log.e("Error", e.getMessage());
                if (count == 5) {
                    runFlag = false;
                    return "Const";
                }
            }
        }
        return result.toString();
    }
}
