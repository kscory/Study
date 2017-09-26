package com.example.kyung.contactpractice;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.kyung.contactpractice.domain.Contact;
import com.example.kyung.contactpractice.domain.Loader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    RecyclerView recyclerNumber;
    NumberAdapter adapter;

    @Override
    public void init(){
        setContentView(R.layout.activity_main);
        // RecylerView 연결
        recyclerNumber = (RecyclerView) findViewById(R.id.recyclerNumber);
        adapter = new NumberAdapter();
        recyclerNumber.setAdapter(adapter);
        recyclerNumber.setLayoutManager(new LinearLayoutManager(this));

        // Loader로 부터 데이터 받고 adaper에 넘김
        Loader loader = new Loader(this);
        List<Contact> data = loader.dataLoad();
        adapter.setDataAndRefresh(data);
    }
}
