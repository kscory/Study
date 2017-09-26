package com.example.kyung.contactpractice.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-09-27.
 */

public class Loader {
    Context context;
    public Loader(Context context){
        this.context = context;
    }
    public List<Contact> dataLoad(){
        List<Contact> data = new ArrayList<>();

        // 1. Content Resolver
        ContentResolver resolver = context.getContentResolver();
        // 2. 데이터 uri 정의
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // 3. 가져올 컬럼 정의
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        // 4. 쿼리 결과를 Cursor에 순서대로
        Cursor cursor = resolver.query(uri,projection,null,null,null);
        // 5. cursor 반복처리
        if(cursor !=null){
            while(cursor.moveToNext()){
                Contact contact = new Contact();
                int index = cursor.getColumnIndex(projection[0]);
                contact.setId(cursor.getInt(index));
                index = cursor.getColumnIndex(projection[1]);
                contact.setName(cursor.getString(index));
                index = cursor.getColumnIndex(projection[2]);
                contact.setNumber(cursor.getString(index));

                data.add(contact);
            }
        }

        return data;
    }
}
