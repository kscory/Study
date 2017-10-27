package com.example.kyung.remotebbs;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kyung on 2017-10-27.
 */

public class Remote {

    public static String sendPost(String address, String json){
        String result = "";
        try{
            URL url = new URL(address); // url = naver.com?id=aaa&pw=12345
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            // header 작성 -----------
            con.setRequestProperty("Content-Type","application/json; charset=utf8");
            con.setRequestProperty("Authorization","token=나중에서버랑할때쓸거임");
            // post 데이터를 전송 -----------
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();
            // -----------------------------
            if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                // 여기서 부터는 파일에서 데이터를 가져오는 것과 동일
                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String temp = "";
                while ((temp = br.readLine()) != null) {
                    result += temp;
                }
                br.close();
                isr.close();
            }else{

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String getData(String string){ // naver.com?id=aaa&pw=1234
        final StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(string);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            // header 작성 -----------
            //con.setRequestProperty("Content-Type","application/json; charset=utf8");
            con.setRequestProperty("Authorization","token=나중에서버랑할때쓸거임");
            // 통신이 성공인지 체크
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 여기서 부터는 파일에서 데이터를 가져오는 것과 동일
                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String temp = "";
                while ((temp = br.readLine()) != null) {
                    result.append(temp);
                }
                br.close();
                isr.close();
            } else {
                Log.e("ServerError", con.getResponseCode()+"");
            }
            con.disconnect();
        }catch(Exception e){
            Log.e("Error", e.toString());
        }
        return result.toString();
    }
}