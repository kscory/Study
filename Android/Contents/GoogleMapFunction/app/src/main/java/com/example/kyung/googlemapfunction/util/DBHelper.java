package com.example.kyung.googlemapfunction.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kyung on 2017-11-22.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bike.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // super에서 넘겨받은 데이터베이스가 생성되어 있는지 확인 후
        // 1. 없으면 onCreate를 호출
        // 2. 있으면 버전을 체크해서 생성되어 있는 DB보다 버전이 높으면 onUpgrade를 호출
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 최초 생성할 테이블을 정의
        String createQuery = "CREATE TABLE `bike_convention` (\n" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "`object_id` TEXT , \n" +
                "`file_path` TEXT, \n" +
                "`type` TEXT, \n" +
                "`address` TEXT, \n" +
                "`lat` TEXT, \n" +
                "`lng` TEXT )";

        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
