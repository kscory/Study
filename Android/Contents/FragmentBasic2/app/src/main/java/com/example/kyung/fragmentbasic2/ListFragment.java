package com.example.kyung.fragmentbasic2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    Context context;
    Callback callback;

    public ListFragment() {
        // Required empty public constructor
    }

    // 컨텍스트를 받아놓으면 쓸 곳이 많다.
    // (이 컨텍스트는 Activity가 넘어온 것이고, Context로 캐스팅되어 사용되는 것)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;
        if(context instanceof Callback){
            callback = (Callback) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        init(view);
        return view;
    }

    RecyclerView recyclerView;
    CustomAdapter adapter;
    private void init(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // 데이터를 이므이로 생성해서 넘겨준다.
        List<String> data = new ArrayList<>();
        for(int i=0 ; i<100 ; i++){
            data.add("tempData : "+i);
        }
        adapter = new CustomAdapter(context,callback, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public interface Callback {
        public void goDetail(String value);
    }

}
