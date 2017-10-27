package com.example.kyung.httpsignin;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Kyung on 2017-10-19.
 */

public class Remote {
    public static String sendPost(String address, String json){
        String result = "";

        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            // Post로 데이터 전송 =================
            con.getDoOutput();
            OutputStream os = con.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();
            //=====================================
            if(con.getResponseCode()==HttpURLConnection.HTTP_OK) {
                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String temp = "";
                while ((temp = br.readLine()) != null) {
                    result += temp;
                }
                Log.e("result!!!!","======================"+result);
                br.close();
                isr.close();
                con.disconnect();
            } else {
                Log.e("ServerError", con.getResponseCode() + "");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }
}
