# SQLite / ORM / Singleton
- SQlite / ORM
- Singleton

---

## SQLite (참고 : [SQL 데이터베이스에 데이터 저장](https://developer.android.com/training/basics/data-storage/databases.html), [SQLiteDatabase](https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html )
- SQLite는 MySQL과 같은 데이터베이스 관리 시스템이지만, 서버가 아니라 응용 프로그램에 넣어 사용하는 비교적 가벼운 데이터베이스
- 안드로이드 운영체제에 기본 탑재된 데이터베이스이다.
- SQLite를 이용하는 두가지 방법
  - db파일을 직접 코드로 생성
  - 로컬에서 만든 파일을 assets에 담은 후 복사 붙여넣기 (우편번호처럼 기반 데이터가 필요한 db일 경우)
- DB 연결 순서 : `1. 데이터베이스에 연결` -> `2. 조작` -> 3. `연결을 해제`

### db파일을 직접 코드로 생성하여 SQLite 이용
- DAO(Data Access Object) / DBHelper 를 이용하여 데이터베이스 이용

  #### 0. 데이터 베이스에 연결을 도와주는 클래스 정의(DBHelper)
  - 보통 DB와 연결을 `DBHelper` 라는 클래스를 새로 생성하여 여기서 처리한다.
  - 생성자
    - super(context, 데이터베이스 이름, 펙토리, 버전)
  - onCreate
  - onUpgrade 존재

  ```java
  public class DBHelper extends SQLiteOpenHelper {

    private static  final String DB_NAME = "sqlite.db";
    private static  final int DB_VERSION =1;

    public DBHelper(Context context){
        // factory : connection factory
        super(context, DB_NAME, null, DB_VERSION);
        // super에서 넘겨받은 데이터베이스가 생성되어 있는지 확인한 후
        // 1. 없으면 onCreate를 호출
        // 2. 있으면 버전을 체크해서 생성되어 있는 DB보다 버전이 높으면 onUpgrade를 호출
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 최초 생성할 테이블을 정의
        String createQuery = "CREATE TABLE `memo` ( \n" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "`title` TEXT, \n" +
                "`content` TEXT, \n" +
                "`n_date` TEXT )";
        // 데이터 베이스가 업데이트 되면 모든 히스토리가 반영되어 있어야 한다.
        // 예>
        // + 'modified' TEXT

        //PreState... 등등을 실행시켜주는 것
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // reversion
        // 변경된 히스토리를 모두 반영해야 한다.
        // 예>
        // if(old <2){
        //    String alterQuery = "Alter TABLE 'memo' (\n Add Column modified text)";
        // }
        // if(old <3){
        //    String alterQuery = "Alter TABLE 'memo' (\n Add Column count text)";
        // }
    }
  ```

  #### 1. 데이터 베이스 연결 (DAO)
  - DBHelper를 통해 DB와 연결

  ```java
  DBHelper helper;
  SQLiteDatabase con ;
  public MemoDAO(Context context){
      helper = new DBHelper(context);
  }
  ```

  #### 2. 데이터 베이스 조작 (DAO)
  - Query를 생성하고 실행
  - C R U D 를 항상 만들고 시작하는 것이 좋다.

  ```java
  // C 생성
  public void create(Memo memo){
      con = helper.getWritableDatabase();

      // 넘겨받은 Memo 클래스를 분해해서 쿼리를 만든다.
      String query = "insert into memo(title, content, n_date)" +
              " values('"+memo.title+"','"+memo.content+"',datetime('now','localtime'))";
      con.execSQL(query);
      // 닫아준다.
      con.close();
  }

  // R 읽기
  public ArrayList<Memo> read(){
      String query = "select id, title, content, n_date from memo";
      // 반환할 결과타입 정의
      ArrayList<Memo> data = new ArrayList<>();
      con = helper.getReadableDatabase();
      Cursor cursor = con.rawQuery(query, null);

      while (cursor.moveToNext()){
          Memo memo = new Memo();
          // 결과를 바로 넣지 않고 coulmninex를 찾은후 그 값을 넣는다.
          int c_index= cursor.getColumnIndex("id");
          memo.id = cursor.getInt(c_index);
          c_index= cursor.getColumnIndex("title");
          memo.title = cursor.getString(c_index);
          c_index= cursor.getColumnIndex("content");
          memo.content = cursor.getString(c_index);
          c_index= cursor.getColumnIndex("n_date");
          memo.n_date = cursor.getString(c_index);
          data.add(memo);
      }
      con.close();
      return data;
  }

  // U 수정
  public void update( String query){
      con = helper.getWritableDatabase();
      con.execSQL(query);
      con.close();
  }
  // D 삭제
  public void delete( String query){
      con = helper.getWritableDatabase();
      con.execSQL(query);
      con.close();
  }
  ```
  #### 3. 데이터 베이스 연결 해제 (DAO)
  - DBHelper를 해제
  - 반드시 닫아주어야 한다.

  ```java
  public void close(){
      helper.close();
  }
  ```

---

## ORM (참고 : [스타일 및 테마](https://developer.android.com/guide/topics/ui/themes.html), [머티리얼 테마 사용](https://developer.android.com/training/material/theme.html) )

### style.xml
View 또는 창의 모양과 형식을 지정하는 속성 모음

  - `<style>` 요소에 `parent` 특성을 사용하여 스타일이 속성을 상속해야 하는 상위 스타일을 지정가능
  ```xml
  <style name="GreenText" parent="@android:style/TextAppearance"></style>
  ```

  - `<item>` 요소로 정의되는 스타일 속성 정의(스타일 속성을 여러가지 존재)
    - ex1> `<item name="windowNoTitle">true</item>` : title의 유무 (ture시 삭제)
    - ex2> `<item name="windowActionBar">true</item>` : actionBar의 유무
    - ex3> `<item name="android:windowIsTranslucent">true</item>` : 투명도 설정
    ```xml
    <style name="Theme.AppCompat.Translucent">
    <!-- 타이틀을 날리는 것으 반드시 Style에서 한다. -->
    <item name="windowActionBar">true</item>
    <item name="windowNoTitle">true</item>
    <item name="android:windowIsTranslucent">true</item>
    <item name="android:windowBackground">@color/transparent</item> // color 설정 적용
    </style>
    ```
---

## 참고
1. Connection pool 이란

  -  매번 새로운 접속을 통해서 쿼리를 통해 DB에서 정보를 가지고 오는 것은 해당 서버의 cpu점유율을 높여 무리를 주는 직접적인 원인이 될수있다. 따라서 DB를 제어하기전에 사용자 지정 갯수만큼 커넥션을 만들어놓고 pool에 넣어놓았다가 필요할때마다 갔다가 쓰고 사용을 다하면 다시 pool에 넣어놓고 사용하는 식으로 시스템을 효율적으로 운영한다. 이 pool을 Connection pool 이라 한다.

2. jcenter

  - jcenter는 bintray.com이 운영하는 Maven Repository
