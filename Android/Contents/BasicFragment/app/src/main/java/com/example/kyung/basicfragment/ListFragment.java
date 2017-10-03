package com.example.kyung.basicfragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kyung.basicfragment.domain.Contact;
import com.example.kyung.basicfragment.domain.Loader;
import com.example.kyung.basicfragment.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    Context context;
    RecyclerView recyclerView;
    ListAdapter adapter;
    List<Contact> contacts = new ArrayList<>();
    CallbackDetail callback;

    public ListFragment() {
        // Required empty public constructor
    }


    // 컨텍스트를 받아놓으면 쓸 곳이 많다.
    // (이 컨텍스트는 Activity가 넘어온 것이고
    // , Context로 캐스팅되어 사용되는 것)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if(context instanceof CallbackDetail){
            callback = (CallbackDetail) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list,container,false);
        init(view);
        return view;
    }

    private void init(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        Loader loader = new Loader(context);
        contacts = loader.contactLoad();

        adapter = new ListAdapter(context,callback);
        adapter.setData(contacts);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public interface CallbackDetail{
        public void showDetail(int id);
    }

}
