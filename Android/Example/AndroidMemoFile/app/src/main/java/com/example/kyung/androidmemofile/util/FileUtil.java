package com.example.kyung.androidmemofile.util;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Kyung on 2017-09-20.
 */

// 읽기와 쓰기
public class FileUtil {
    // 읽기
    public static String read(Context context, String filename) throws IOException {
        StringBuilder sb = new StringBuilder();

        // 스트림을 열고 닫는다.
        // 읽기는 버퍼를 사용했다. (다른방법들도 존재) => 버퍼의 읽는 양을 정하고 모두 읽음
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = context.openFileInput(filename);
            bis = new BufferedInputStream(fis);
            byte buffer[] = new byte[1024]; // 버퍼의 양
            int count = 0; // 카운트 (모두 읽으면 -1이 반환됨)
            while ((count=bis.read(buffer)) !=-1 ){
                String data = new String(buffer,0, count);
                sb.append(data);
            }

//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            String row;
//            while((row=br.readLine()) !=null){
//                sb.append(row);
//            }

        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    throw e;
                }
            }
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        return sb.toString();

    }

    /**
     * 파일 쓰기 함수
     * @param context 컨텍스트
     * @param filename 파일이름
     * @param content 내용
     * @throws IOException
     */
    // 쓰기
    public static void write(Context context, String filename, String content) throws IOException {
        // 스트림을 열고 닫는다.
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename,MODE_PRIVATE);
            fos.write(content.getBytes());
        } catch (IOException e) {
            throw  e;
        } finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e){
                    throw e;
                }

            }
        }
    }
}
