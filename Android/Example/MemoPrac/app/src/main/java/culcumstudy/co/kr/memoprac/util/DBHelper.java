package culcumstudy.co.kr.memoprac.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kyung on 2018-03-12.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "memo.db";
    private static int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // 데이터 베이스 없으면 onCreate, 있다면 버전 체크해서 버전이 높으면 onUpgrade 호출
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 최초 생성할 테이블 정의
        String createQuery = "CREATE TABLE 'memo' ( \n" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "'title' TEXT, \n" +
                "'content' TEXT, \n" +
                "'n_date' TEXT )";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
