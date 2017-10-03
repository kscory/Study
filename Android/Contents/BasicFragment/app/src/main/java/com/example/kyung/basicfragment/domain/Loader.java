package com.example.kyung.basicfragment.domain;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * 데이터를 불러오는 클래스
 */

public class Loader {
    Context context;
    public Loader(Context context){
        this.context = context;
    }

    // Contact 데이터 로드
    public List<Contact> contactLoad(){
        List<Contact> data = new ArrayList<>();
        // Content Resolver 정의
        ContentResolver resolver = context.getContentResolver();
        // Uri 정의
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // 가져올 컬럼(projection) 정의
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        // 커서 정의
        Cursor cursor = resolver.query(uri,projection,null,null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY +" asc");
        // 반복문을 돌면서 데이터 저장
        if(cursor !=null){
            while(cursor.moveToNext()){
                Contact contact = new Contact();
                int index =  cursor.getColumnIndex(projection[0]);
                contact.setId(cursor.getInt(index));
                index =  cursor.getColumnIndex(projection[1]);
                contact.setName(cursor.getString(index));
                index =  cursor.getColumnIndex(projection[2]);
                contact.setNumber(cursor.getString(index));
                data.add(contact);
            }
        }
        return data;
    }

    // 특정 Contact 로드
    public Contact detailLoad(int id) {
        Contact contact = new Contact();
        ContentResolver resolver = context.getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        Cursor cursor = resolver.query(uri,projection,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                null,null);
        if(cursor !=null){
            while(cursor.moveToNext()){
                int index = cursor.getColumnIndex(projection[0]);
                contact.setId(cursor.getInt(index));
                index = cursor.getColumnIndex(projection[1]);
                contact.setName(cursor.getString(index));
                index = cursor.getColumnIndex(projection[2]);
                contact.setNumber(cursor.getString(index));
            }
        }
        return contact;
    }
}
