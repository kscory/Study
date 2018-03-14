package culcumstudy.co.kr.memoprac.domain;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import culcumstudy.co.kr.memoprac.util.DBHelper;

/**
 * Created by Kyung on 2018-03-12.
 */

public class MemoDao {

    DBHelper helper;
    SQLiteDatabase conn;

    public MemoDao(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * 메모를 데이터베이스에 저장하는 메소드
     */
    public void save(Memo memo){
        conn = helper.getWritableDatabase();
        String query = String.format("INSERT INTO memo(title, content, n_date)" +
                "VALUES('%s', '%s', 'datetime('now','localtime'))", memo.getTitle(), memo.getContent());
        conn.execSQL(query);
        conn.close();
    }

    /**
     * 메모 전체를 읽는 메소드
     */
    public List<Memo> readAll(){
        List<Memo> memoList = new ArrayList<>();
        conn = helper.getReadableDatabase();
        String query = "SELECT id, title, n_date FROM memo ORDER BY id";

        Cursor cursor = conn.rawQuery(query, null);
        while (cursor.moveToNext()){
            Memo memo = new Memo();
            int c_index = cursor.getColumnIndex("id");
            memo.setNo(cursor.getInt(c_index));
            c_index = cursor.getColumnIndex("title");
            memo.setTitle(cursor.getString(c_index));
            c_index = cursor.getColumnIndex("n_date");
            memo.setDatetime(cursor.getString(c_index));

            memoList.add(memo);
        }
        conn.close();

        return memoList;
    }

    /**
     * id를 받아 특정 메모를 읽는 메소드
     */
    public Memo readById(int id){
        Memo memo = new Memo();

        conn = helper.getReadableDatabase();

        String query = "SELECT * FROM memo where id ="+id;
        Cursor cursor = conn.rawQuery(query,null);
        while (cursor.moveToNext()){
            int c_index = cursor.getColumnIndex("id");
            memo.setNo(cursor.getInt(c_index));
            c_index = cursor.getColumnIndex("title");
            memo.setTitle(cursor.getString(c_index));
            c_index = cursor.getColumnIndex("content");
            memo.setContent(cursor.getString(c_index));
            c_index = cursor.getColumnIndex("n_date");
            memo.setDatetime(cursor.getString(c_index));
        }
        conn.close();

        return memo;
    }

    /**
     * 특정 메모를 업데이트 하는 메소드
     */
    public void update(Memo memo) {
        conn = helper.getWritableDatabase();
        String query = "UPDATE memo SET title = '" + memo.getTitle() +"', " +
                "content = '" + memo.getContent() + "', " +
                "n_date = '" + memo.getDatetime() + "' " +
                "WHERE id = " + memo.getNo();
        conn.execSQL(query);
        conn.close();
    }

    /**
     * 특정 메모를 지우는 메소드
     */
    public void delete(int id) {
        conn = helper.getWritableDatabase();
        String query = "DELETE FROM memo WHERE id=" + id;
        conn.execSQL(query);
        conn.close();
    }
}
