package com.example.kyung.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

import com.example.kyung.musicplayer.domain.MusicLoader;
import com.example.kyung.musicplayer.player.Player;

public class PlayerService extends Service {
    Player player;
    MusicLoader musicLoader;

    public PlayerService() {}

    // 생명주기로 plyer 제어를 한다.
    // plyaer를 가져오고 null처리가 필요
    @Override
    public void onCreate() {
        super.onCreate();
        player = Player.getInstance();
        musicLoader = MusicLoader.getInstance();
    }

    // Bind를 사용하게 되면 서비스에서 엑티비티 접근이 불가능하여 SeekBar 컨드롤이 불가능
    // 명령어를 날리는 구조에서는 Bind를 쓰기 어렵다.
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String action = intent.getAction();
            switch (action){
                case Const.ACTION_SET:
                    int position = intent.getIntExtra(Const.KEY_POSITION,0);
                    playerSet(position);
                    break;
                case Const.ACTION_START:
                    start();
                    break;
                case Const.ACTION_PAUSE:
                    pause();
                    break;
                case Const.ACTION_STOP:
                    stop();
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    void playerSet(int position){
        if(position > -1) {
            player.set(getBaseContext(), musicLoader.musics.get(position).getMusicUri());
        }
    }
    void start(){
        player.start();
    }
    void pause(){
        player.pause();
    }
    void stop(){
        player.stop();
    }

    @Override
    public void onDestroy() {
        if(player != null){
            stop();
            player = null;
        }
        super.onDestroy();
    }
}
