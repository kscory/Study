package com.example.kyung.musicplayer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kyung.musicplayer.domain.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-10-15.
 */

public class MusicFragmentAdapter extends RecyclerView.Adapter<MusicFragmentAdapter.Holder> {
    List<Music> data;
    MusicFragment.IActivityInteract listener;

    public MusicFragmentAdapter(List<Music> data, MusicFragment.IActivityInteract listener){
        this.data = data;
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);
        return new Holder(view);
    }

    public void onBindViewHolder(Holder holder, int position) {
        Music music = data.get(position);
        holder.setPosition(position);
        holder.setMusicId(music.getId());
        holder.setContent(music.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private int position;
        private TextView textId;
        private TextView textContent;

        public Holder(View itemView) {
            super(itemView);

            textId = (TextView) itemView.findViewById(R.id.textId);
            textContent = (TextView) itemView.findViewById(R.id.textContent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.openPlayer(position);
                }
            });
        }

        public void setPosition(int position){
            this.position = position;
        }
        public void setMusicId(String id){
            textId.setText(id);
        }
        public void setContent(String content){
            textContent.setText(content);
        }
    }
}
