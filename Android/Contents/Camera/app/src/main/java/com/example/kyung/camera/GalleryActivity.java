package com.example.kyung.camera;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GalleryAdapter adapter;
    List<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        init();
    }

    private void init(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new GalleryAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        // 데이터를 불러온다.
        images = loadData();
        adapter.setData(images);

    }

    // ContentResolver를 이용하여 이미지 목록을 가져온다.
    private List<String> loadData(){
        List<String> data = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        String projection[] = {
                MediaStore.Images.Thumbnails.DATA
        };

        Cursor cursor = resolver.query(uri,projection,null,null,null);
        if(cursor !=null){
            while(cursor.moveToNext()){
                int index = cursor.getColumnIndex(projection[0]);
                String path = cursor.getString(index);
                data.add(path);
            }
        }

        return data;
    }
}
