package com.example.kyung.musicplayer.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import com.example.kyung.musicplayer.Const;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *  음악 재생 플레이어
 */

public class Player {
    //Singleton 디자인 패턴 이용
    private static Player instance = null;
    private Player(){}
    public static Player getInstance(){
        if(instance ==null){
            instance = new Player();
        }
        return instance;
    }

    //음원재생 기능
    private MediaPlayer mediaPlayer;
    // 반복 재생 판별자
    private boolean loop = false;
    // Player의 상태
    private int status = Const.STAT_STOP;

    // 재생될 음원 목록 데이터
    List<MusicList> data;
    // 현재 재생 음악 번호
    private int currentMysicPosition = -1;
    // 음악 재생 플래그
    private static boolean runFlag = true;

    // Play가 되고 있는지 체크하는 thread
    private PlayerThread thread;

    // 음원세팅
    public void set(Context context, Uri musicUri){
        // null 체크를 안하면 음악이 중첩해서 나오게 된다.
        if(mediaPlayer != null){
            // 서브 쓰레드에서 동작하는 것을 멈춤
            // 음악 파일에 꽂혀있는 Stream을 해제
            mediaPlayer.release();
            mediaPlayer = null;
        }
        // musicUri에 해당하는 파일에 Stream을 꽂는다. (음악Uri를 사용해서 플레이어를 초기화)
        mediaPlayer = MediaPlayer.create(context,musicUri);
        mediaPlayer.setLooping(loop); // 음악을 반복할지 결정
    }

    public void start(){
        if(mediaPlayer != null){
            mediaPlayer.start();
            status = Const.STAT_PLAY;
            runFlag = true;
            thread = new PlayerThread();
        }
    }
    public void pause(){
        if(mediaPlayer != null){
            mediaPlayer.pause();
            status=Const.STAT_PAUSE;
            runFlag = false;
        }
    }
    public void stop(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            status=Const.STAT_STOP;
        }
    }

    public boolean isPlay(){
        if(mediaPlayer != null){
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    // 현재 플레이 상태를 가져오는 메소드
    public int getStatus(){
        return status;
    }

    // 현재 재생되고 있는 곡이 재생된 시간을 가져오는 메소드
    public int getCurrentMusicTime(){
        if(mediaPlayer != null){
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    // 현재 재생되는 곡의 전체 시간을 가져오는 메소드
    public int getDuration(){
        if(mediaPlayer != null){
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    // 현재 곡의 번호를 set / get 하는 메소드
    public void setCurrentMusicPosition(int position){
        currentMysicPosition = position;
    }
    public int getCurrentMysicPosition(){
        return currentMysicPosition;
    }

    // 데이터 구조체 또는 인터페이스
    public interface MusicList{
        Uri getMusicUri();
        int getDuration();
    }

    // 이전 SeekBarThread 관련(리스너)
    // 리스너 인터페이스
    public interface IProgListener{
        void setProgress();
    }
    // 옵저버 패턴을 이용한 SeekBar 조절 리스너 //
    // CopyOnWriteArrayList : 동기화를 지원하는 Collection
    // run() 함수의 향상된 for문에서 observers를 읽고 있으면
    // 대기하고 있다가 읽기가 끝나면 add() or remove() 를 실행해서 충돌을 방지해준다.
    List<IProgListener> progListeners = new CopyOnWriteArrayList<>();
    // 리스너를 add / remove하는 메소드
    public void addListener(IProgListener listener){
        progListeners.add(listener);
    }
    public void removeListener(IProgListener listener){
        progListeners.remove(listener);
    }

    private class PlayerThread extends Thread{
        public void run(){
            while(runFlag){
                for(IProgListener listener : progListeners){
                    listener.setProgress();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
