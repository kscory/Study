package com.example.kyung.musicplayer;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kyung.musicplayer.domain.Music;
import com.example.kyung.musicplayer.domain.MusicLoader;
import com.example.kyung.musicplayer.player.Player;
import com.example.kyung.musicplayer.player.PlayerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 뮤직 플레이어 만들기
 * 1. 권한설정 : Read_External_storage > BaseActivity
 * 2. 화면만들기 : 메인 -> TabLayout, ViewPager
 *                    -> 목록프래그먼트 -> RecyclerView
 *                                    -> RecyclerAdapter
 *                                    -> item_layout.xml
 *                Music-> load() : 음악목록 가져오기
 *
 *                Player -> ViewPager, Button, SeekBar
 *                          PagerAdapter
 *                          SeekBarThread
 */
public class MainActivity extends BaseActivity implements MusicFragment.IActivityInteract{

    TabLayout tabLayout;
    ViewPager viewPager;

    MusicLoader musicLoader;

    @Override
    public void init() {
        load();
        initView();
        initTabLayout();
        initViewPager();
        initListener();
        checkPlayer();
    }

    // view를 init
    private void initView(){
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    // 탭 추가
    private void initTabLayout(){
        // 액티비티가 아니라면 Context를 넘겨서 getString을 한다.
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_artist)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_album)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_genre)));
    }

    // viewPager에 데이터 세팅
    private void initViewPager(){
        List<Fragment> fragments = new ArrayList<>();
        Fragment titleList = new MusicFragment();
        Fragment artistList = new MusicFragment();
        Fragment albumList = new MusicFragment();
        Fragment genreList = new MusicFragment();
        fragments.add(titleList); fragments.add(artistList); fragments.add(albumList); fragments.add(genreList);

        // 어댑터 연결
        // 버전호환성을 위해 supportFragmentManager 사용
        MusicPagerAdapter adapter = new MusicPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }

    private void initListener(){
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void load(){
        musicLoader = MusicLoader.getInstance();
        musicLoader.load(this);
    }

    private void checkPlayer(){
        if(Player.getInstance().isPlay()){
            openPlayer(-1);
        }
    }


    @Override
    public List<Music> getList() {
        return musicLoader.musics;
    }

    @Override
    public void openPlayer(int position) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(Const.KEY_POSITION, position);
        startActivity(intent);
    }
}
