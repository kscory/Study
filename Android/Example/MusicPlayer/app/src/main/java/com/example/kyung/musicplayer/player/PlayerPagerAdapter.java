package com.example.kyung.musicplayer.player;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyung.musicplayer.R;
import com.example.kyung.musicplayer.domain.Music;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Kyung on 2017-10-15.
 */

public class PlayerPagerAdapter extends PagerAdapter {
    Context context;
    List<Music> data;

    public PlayerPagerAdapter(Context context, List<Music> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Music music = data.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_player,null);
        TextView  textTitle  = (TextView) view.findViewById(R.id.textTitle);
        textTitle.setText(music.getTitle());
        TextView  textArtist = (TextView) view.findViewById(R.id.textArtist);
        textArtist.setText(music.getArtist());
        ImageView imgAlbum   = (ImageView) view.findViewById(R.id.imgAlbum);
        imgAlbum.setImageURI(music.getAlbumUri());
        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
