package com.example.kyung.musicplayer.player;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.kyung.musicplayer.BaseActivity;
import com.example.kyung.musicplayer.Const;
import com.example.kyung.musicplayer.PlayerService;
import com.example.kyung.musicplayer.R;
import com.example.kyung.musicplayer.domain.Music;
import com.example.kyung.musicplayer.domain.MusicLoader;

public class PlayerActivity extends BaseActivity implements View.OnClickListener, Player.IProgListener {

    private ViewPager albumPager;
    private RelativeLayout controller;
    private SeekBar seekBar;
    private TextView textCurrentTime;
    private TextView textDuration;
    private ImageButton btnPlay;
    private ImageButton btnFf;
    private ImageButton btnRew;
    private ImageButton btnNext;
    private ImageButton btnPrev;

    MusicLoader musicLoader;
    Intent musicServiceIntent;
    Player player;

    @Override
    public void init() {
        Intent intent = getIntent();
        if(intent != null){
            player = Player.getInstance();
            player.setCurrentMusicPosition(intent.getIntExtra(Const.KEY_POSITION,0));
        }
        musicServiceIntent = new Intent(this, PlayerService.class);

        loadMusic();
        initView();
        initViewPager();
    }

    private void initViewPager(){
        PlayerPagerAdapter adapter = new PlayerPagerAdapter(this, musicLoader.musics);
        albumPager.setAdapter(adapter);
        // 뷰페이저에 리스너를 달기전에 페이지를 변경해서 onPageSelected가 호출되지 않는다
        if(player.getCurrentMysicPosition()>0){
            albumPager.setCurrentItem(player.getCurrentMysicPosition());
        }
        albumPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                player.setCurrentMusicPosition(position);
                playerSet();
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadMusic(){
        musicLoader = MusicLoader.getInstance();
        musicLoader.load(this);
        playerSet();
    }

    private void initView(){
        setContentView(R.layout.activity_player);
        albumPager = (ViewPager) findViewById(R.id.albumPager);
        controller = (RelativeLayout) findViewById(R.id.controller);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textCurrentTime = (TextView) findViewById(R.id.textCurrentTime);
        textDuration = (TextView) findViewById(R.id.textDuration);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnFf = (ImageButton) findViewById(R.id.btnFf);
        btnRew = (ImageButton) findViewById(R.id.btnRew);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);

        btnPlay.setOnClickListener(this);
        btnFf.setOnClickListener(this);
        btnRew.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlay:
                if(player.getStatus() == Const.STAT_PLAY){
                    pause();
                } else{
                    start();
                }
                break;
            case R.id.btnFf:
                break;
            case R.id.btnRew:
                break;
            case R.id.btnNext:
                break;
            case R.id.btnPrev:
                break;
        }
    }

    // 서비스 인텐트를 세팅
    public void playerSet(){
        musicServiceIntent.setAction(Const.ACTION_SET);
        musicServiceIntent.putExtra(Const.KEY_POSITION, player.getCurrentMysicPosition());
        startActivity(musicServiceIntent);
    }
    // 시작버튼 클릭시 발생하는 메소드
    public void start(){
        musicServiceIntent.setAction(Const.ACTION_START);
        startActivity(musicServiceIntent);
        changePlayButton(Const.STAT_PLAY);
    }
    // 정지버튼 클릭시 발생하는 메소드
    public void pause(){
        musicServiceIntent.setAction(Const.ACTION_PAUSE);
        startActivity(musicServiceIntent);
        changePlayButton(Const.STAT_PAUSE);
    }
    // 음악을 완전히 정지시 발생하는 메소드
    public void stop(){
        musicServiceIntent.setAction(Const.ACTION_STOP);
        startActivity(musicServiceIntent);
    }
    // 플레이 상태에따라 버튼 교체
    public void changePlayButton(int status){
        if(status == Const.STAT_PLAY){
            btnPlay.setImageResource(android.R.drawable.ic_media_pause);
        } else if(status == Const.STAT_PAUSE){
            btnPlay.setImageResource(android.R.drawable.ic_media_play);
        }
    }
    // 앱 접속시 플레이 상태에 따라 버튼 변경
    public boolean checkPlayer(){
        if(player.isPlay()){
            changePlayButton(Const.STAT_PLAY);
            return true;
        } else{
            changePlayButton(Const.STAT_PAUSE);
            return false;
        }
    }

    // 엑티비티가 Resume / Pause 되면 Thread를 add / remove 시켜준다.
    @Override
    protected void onResume() {
        super.onResume();
        checkPlayer();
        seekBar.setMax(player.getDuration());
        player.addListener(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        player.removeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }

    /**
     * 이 함수는 서브 thread에서 호출되기 때문에 Activity에 코드가 있지만
     * 실행은 서브에서 된다.
     */
    @Override
    public void setProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(player.getCurrentMusicTime());
            }
        });
    }
}
