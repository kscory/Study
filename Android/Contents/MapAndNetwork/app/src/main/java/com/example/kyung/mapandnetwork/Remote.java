package com.example.kyung.mapandnetwork;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kyung on 2017-10-18.
 */

public class Remote {

    public static String getData(String string) {
        boolean runFlag = true;
        int ServerCount = 5;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String temp="";
                while((temp=br.readLine()) !=null){
                    result.append(temp).append("\n");
                }
                isr.close();
                br.close();
                runFlag=false;
            } else{
                Log.e("ServerError",connection.getResponseCode()+"");
            }
            connection.disconnect();
        } catch (MalformedURLException e) {
            Log.e("URLError",e.toString());
        } catch (IOException e) {
            Log.e("IOError",e.toString());
            ServerCount++;
            runFlag=true;
            if(ServerCount>15){
                runFlag=false;
                return "ConnectionError";
            }
        }
        return result.toString();
    }
}
