package com.example.kyung.googlemapfunction.domain;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kyung.googlemapfunction.domain.bikeconvention.Row;
import com.example.kyung.googlemapfunction.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-11-22.
 */

public class BikeConventionDao {
    DBHelper helper;
    SQLiteDatabase con;

    public BikeConventionDao(Context context){
        // 데이터베이스 연결
        helper = new DBHelper(context);
    }

    // 여러개 값을 받거나 한개만 받았을 경우 데이터를 저장
    public void save(List<Row> bikeConventionList) {
        con = helper.getWritableDatabase();
        // list이므로 for문을 돌며 create Query를 만들어 넣어준다.
        for(Row bikeConvention : bikeConventionList) {
            String query = String.format("insert into bike_convention(object_id, file_path, type, address, lat, lng) " +
                    "values('%s','%s','%s','%s','%s','%s')"
                    ,bikeConvention.getOBJECTID(), bikeConvention.getFILENAME(), bikeConvention.getCLASS()
                    , bikeConvention.getADDRESS(), bikeConvention.getLAT(), bikeConvention.getLNG());
            con.execSQL(query);
        }
        // 데이터베이스 닫기
        con.close();
    }
    public void save(Row bikeConvention) {
        con = helper.getWritableDatabase();
        String query = String.format("insert into bike_convention(object_id, file_path, type, address, lat, lng) " +
                        "values('%s','%s','%s','%s','%s','%s')"
                ,bikeConvention.getOBJECTID(), bikeConvention.getFILENAME(), bikeConvention.getCLASS()
                , bikeConvention.getADDRESS(), bikeConvention.getLAT(), bikeConvention.getLNG());
        con.execSQL(query);
        con.close();
    }

    // objList 혹은 obj를 받아 저장된 데이터베이스 읽기
    public List<Row> readByObjId(List<String> objIdList){
        List<Row> data = new ArrayList<>();
        if(objIdList.size()>0) {
            String query = "SELECT * FROM bike_convention WHERE object_id IN (";
            for (String obj : objIdList) {
                query += "'"+ obj + "',";
            }
            query = query.substring(0,query.lastIndexOf(",")).concat(")");
            con = helper.getReadableDatabase();
            Cursor cursor = con.rawQuery(query,null);
            while (cursor.moveToNext()) {
                Row row = new Row();

                int cursorIndex = cursor.getColumnIndex("object_id");
                row.setOBJECTID(cursor.getString(cursorIndex));
                cursorIndex = cursor.getColumnIndex("file_path");
                row.setFILENAME(cursor.getString(cursorIndex));
                cursorIndex = cursor.getColumnIndex("type");
                row.setCLASS(cursor.getString(cursorIndex));
                cursorIndex = cursor.getColumnIndex("address");
                row.setADDRESS(cursor.getString(cursorIndex));
                cursorIndex = cursor.getColumnIndex("lat");
                row.setLAT(cursor.getString(cursorIndex));
                cursorIndex = cursor.getColumnIndex("lng");
                row.setLNG(cursor.getString(cursorIndex));

                data.add(row);
            }
            con.close();
        }
        return data;
    }
    public Row readByObjId(String objId){
        String query = "select * from bike_convention where object_id = '" + objId + "'";
        Row row = new Row();
        con = helper.getReadableDatabase();
        Cursor cursor = con.rawQuery(query,null);
        while (cursor.moveToNext()) {
            int cursorIndex = cursor.getColumnIndex("object_id");
            row.setOBJECTID(cursor.getString(cursorIndex));
            cursorIndex = cursor.getColumnIndex("file_path");
            row.setFILENAME(cursor.getString(cursorIndex));
            cursorIndex = cursor.getColumnIndex("type");
            row.setCLASS(cursor.getString(cursorIndex));
            cursorIndex = cursor.getColumnIndex("address");
            row.setADDRESS(cursor.getString(cursorIndex));
            cursorIndex = cursor.getColumnIndex("lat");
            row.setLAT(cursor.getString(cursorIndex));
            cursorIndex = cursor.getColumnIndex("lng");
            row.setLNG(cursor.getString(cursorIndex));
        }
        cursor.close();
        con.close();

        return row;
    }

    public List<Row> readAll(){
        List<Row> data = new ArrayList<>();
        String query = "select * from bike_convention";

        Row row = new Row();
        con = helper.getReadableDatabase();
        Cursor cursor = con.rawQuery(query,null);
        while (cursor.moveToNext()) {
            int cursorIndex = cursor.getColumnIndex("object_id");
            row.setOBJECTID(cursor.getString(cursorIndex));
            cursorIndex = cursor.getColumnIndex("file_path");
            row.setFILENAME(cursor.getString(cursorIndex));
            cursorIndex = cursor.getColumnIndex("type");
            row.setCLASS(cursor.getString(cursorIndex));
            cursorIndex = cursor.getColumnIndex("address");
            row.setADDRESS(cursor.getString(cursorIndex));
            cursorIndex = cursor.getColumnIndex("lat");
            row.setLAT(cursor.getString(cursorIndex));
            cursorIndex = cursor.getColumnIndex("lng");
            row.setLNG(cursor.getString(cursorIndex));

            data.add(row);
        }
        con.close();

        return data;
    }

    public void deleteAll(){
        con = helper.getWritableDatabase();
        String query = "DELETE FROM bike_convention";
        con.execSQL(query);
        con.close();
    }
}
