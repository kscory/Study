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
  - SQLiteOpenHelper 상속
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
- ORM(Object-relational mapping)을 단순하게 표현하면 객체와 관계와의 설정
- 객체와 테이블간의 관계를 설정하여 자동으로 처리

### ORM 설정
- gradle 에서 dependencies에 `compile 'com.android.support:cardview-v7:25.3.1'`추가하여 라이브러리 다운
- syne now 클릭 (or Build -> makeproject 클릭)

### ORM 사용 순서
- SQLite 만을 이용할 때와 사용 방법은 비슷
- DAO(Data Access Object) / DBHelper 를 이용하여 데이터베이스 이용

  #### 0. 데이터 베이스에 연결을 도와주는 클래스 정의(DBHelper)
  - OrmLiteSqliteOpenHelper를 상속
  ```java
  public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "ormlite.db";
    private static final int DB_VERSION =1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            // PicNote.class를 참조해서 테이블을 생성해준다.
            TableUtils.createTable(connectionSource, PicNote.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
  }
  ```

  #### 1. 데이터 베이스 연결 (DAO)
  - DBHelper를 통해 DB와 연결

  ```java
  DBHelper helper;
  Dao<PicNote, Long> dao=null;
  public PicNoteDAO(Context context){
      helper = new DBHelper(context);
      // 어떤 테이블의 DAO를 쓸꺼니?? (우리는 picnote)
      try {
          dao = helper.getDao(PicNote.class);
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }
  ```

  #### 2. 데이터 베이스 조작 (DAO)
  - Query를 생성하고 실행
  - C R U D 를 항상 만들고 시작하는 것이 좋다.
  - read를 만들 때는 주의한다. (나머지는 아래와 같은 형식으로 사용)

  ```java
  // 생성
     public void create(PicNote picNote){
         try {
             dao.create(picNote);
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

     public List<PicNote> readAll(/*쿼리를 할 수 있는 조건*/){
         List<PicNote> result = null;
         try {
             // 전체 데이터를 가져올 경우 queryForAll();
             result = dao.queryForAll();
         } catch (SQLException e) {
             e.printStackTrace();
         }

         return result;
     }

     public PicNote readOneById(long id){
         PicNote result = null;
         try {
             result = dao.queryForId(id);
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return result;
     }

     // 형식은 그냥 쓸 것.
     public List<PicNote> search(String word){ //ex> word=그림
         // 요즘에는 format을 많이 사용 (알아보기 편하다)
         String query = String.format("select * from picnote where title like '%% %s %%'",word);
         List<PicNote> result= null;
         try {
             GenericRawResults<PicNote> temp = dao.queryRaw(query, dao.getRawRowMapper());
             result = temp.getResults();
         } catch (SQLException e) {
             e.printStackTrace();
         }

         return result;
     }

     public void update(PicNote picNote){
         try {
             dao.update(picNote);
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

     public void delete(PicNote picNote){
         try {
             dao.delete(picNote);
         } catch (SQLException e) {
             e.printStackTrace();
         }
         // deleteById를 하면 id만 넘겨주어도 삭제 가능
     }
  ```


---

## 참고
1. Connection pool 이란

  -  매번 새로운 접속을 통해서 쿼리를 통해 DB에서 정보를 가지고 오는 것은 해당 서버의 cpu점유율을 높여 무리를 주는 직접적인 원인이 될수있다. 따라서 DB를 제어하기전에 사용자 지정 갯수만큼 커넥션을 만들어놓고 pool에 넣어놓았다가 필요할때마다 갔다가 쓰고 사용을 다하면 다시 pool에 넣어놓고 사용하는 식으로 시스템을 효율적으로 운영한다. 이 pool을 Connection pool 이라 한다.

2. jcenter

  - jcenter는 bintray.com이 운영하는 Maven Repository
