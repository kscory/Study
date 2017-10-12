package com.example.kyung.basicthread.secondexample;

/**
 * Created by Kyung on 2017-10-11.
 */

// 스스로 떨어지는 애들은 Thread를 상속
public class RainDrop extends Thread{
    // 속성
    float x;
    float y;
    float speed;
    float size;
    int color;

    // 생명주기 - 바닥에 닿을때까지
    float limit;

    public RainDrop(float x, float y, float speed, float size, int color, float limit){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.color = color;
        this.limit = limit;
    }
    // Thread를 상속받으면 run함수를 가질 수 있다.
//    public void run(){
//        while(y < limit){
//            y += speed;
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
