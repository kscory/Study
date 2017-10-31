package com.example.kyung.basicfirebase3;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class StorageActivity extends AppCompatActivity {

    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        storageRef = FirebaseStorage.getInstance().getReference();

    }

    public void chooseFile(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*"); // 갤러리 image/* , 동영상 video/* , 어플리케이션 application/*
        startActivityForResult(intent.createChooser(intent,"Select App"),999);
    }

    // 파일이 선택되면 호출
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
//            String realPath = RealPathUtil.getRealPath(this, uri);
//            upload(realPath);
            upload(uri);
        }
    }

//    public void upload(String path){
    public void upload(Uri file){
        // 실제 파일이 있는 경로
//        Uri file = Uri.fromFile(new File(path));
        // 파이어베이스의 스토리지 파일node
        String temp[] = file.getPath().split("/"); // 파일 패스랑 이름이랑 다르므로,,, 패스는 다르게 해야함...
        String filename = temp[temp.length-1];
        StorageReference riversRef = storageRef.child("files/"+filename);

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.e("Storage","downloadURL"+downloadUrl.getPath());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(StorageActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
