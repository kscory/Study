package com.example.kyung.loginhttp;

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
 * Created by Kyung on 2017-10-27.
 */

public class Remote {
    public static String sendPost(String address, Map data) {
        String result = "";
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            // Post data를 전송하는 부분 ===================
            String postdata = "";
            for (Object key : data.keySet()) {
                // &는 두번째 줄부터 붙이므로 제거해야 한다.
                postdata += "&" + key + "=" + data.get(key);
            }
            postdata = postdata.substring(1); //제일 앞 & 제거
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(postdata.getBytes());
            // 스트림은 항상 닫아 주어야 한다.
            os.flush();
            os.close();
            //==============================================

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String temp = "";
                while ((temp = br.readLine()) != null) {
                    result += temp;
                }
                br.close();
                isr.close();
            } else {
                Log.e("ServerError", con.getResponseCode() + "");
            }
            con.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
