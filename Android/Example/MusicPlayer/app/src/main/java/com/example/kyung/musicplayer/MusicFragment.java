package com.example.kyung.musicplayer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kyung.musicplayer.domain.Music;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {
    // 메인엑티비티와 통신하는 인터페이스
    IActivityInteract listener;
    RecyclerView list;

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IActivityInteract){
            listener = (IActivityInteract) context;
        } else{
            throw new RuntimeException("must implement IActivityInteract");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music, container, false);
        list = (RecyclerView) view.findViewById(R.id.list);
        MusicFragmentAdapter adapter = new MusicFragmentAdapter(listener.getList(), listener);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    // 메인엑티비티와 통신하는 인터페이스 (Activity에서 컨트롤하기 위해서 사용) - 이전에는 홀더에서 이용함
    // 엑티비티에서 implement 하지 않으면 앱이 강제로 종료된다.
    // 설계에 따라 달라질 수 있다.
    public interface IActivityInteract{
        List<Music> getList();
        void openPlayer(int position);
    }

}
