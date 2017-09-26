package com.example.kyung.contact;

import android.Manifest;
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
import android.widget.Toast;

import com.example.kyung.contact.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Content Resolver 사용하기
 * 1. Content Resolver 를 불러오기
 * 2. 데이터 URI 정의 <- 일종의 DB에서의 테이블 이름 (URI는 주소의 프로토콜을 포함하고 있다-> 일종의 상위개념)
 * 3. 데이터 URI 에서 가져올 컬럼명 정의
 * -. 조건절을 정의할 수도 있다. (옵션)
 * 4. 불러온 Content Resolver로 쿼리한 데이터를 Cursor에 담는다.
 * 5. Cursor에 담긴 데이터를 반복문을 돌면서 처리한다.
 *
 *    - 전화 걸기 권한
 *    Manifest.permission.CALL_PHONE
 *
 *    - 전화 거는 인텐트
 *    String num = "tel:" + number;
 *    Uri uri = Uri.parse(num);
 *    Intent intent = new Intent("android.intent.action.CALL", uri);
 *    startActivity(intent);
 *
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE = 999;
    private static final String permissions[]={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE
    };

    CustomAdapter adapter;
    RecyclerView recyclerView;
    PermissionUtil pUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pUtil = new PermissionUtil(REQ_CODE, permissions);
        if(pUtil.checkPermission(this)){
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(pUtil.afterPermissionResult(requestCode,grantResults)){
            init();
        } else{
            Toast.makeText(this, "승인필요",Toast.LENGTH_LONG).show();
        }
    }

    private void init(){
        adapter = new CustomAdapter();
        adapter.setData(load());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Contact> load(){

        List<Contact> contacts = new ArrayList<>();

        // 1. Content Resolver 불러오기
        ContentResolver resolver = getContentResolver();
        // 2. 데이터 URI 정의
        // 전화번호 URI : ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // 주소록 URI : ContactsContract.Contacts.CONTENT_URI
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // 3. 가져올 컬럼 정의
        String projection[] = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        // 4. 쿼리 결과 -> Cursor (쿼리뒤에 순서대로)
        // 선택할 조건절을 처리할 컬럼명이 selection /
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        // 5. cursor 반복처리
        if(cursor != null){
            while(cursor.moveToNext()){
                Contact contact = new Contact();

                int index = cursor.getColumnIndex(projection[0]);
                contact.setId(cursor.getInt(index));

                index = cursor.getColumnIndex(projection[1]);
                contact.setName(cursor.getString(index));

                index = cursor.getColumnIndex(projection[2]);
                contact.setNumber(cursor.getString(index));

                contacts.add(contact);
            }
        }

        return contacts;
    }
}

class Contact{
    private int id;
    private String name;
    private String number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
