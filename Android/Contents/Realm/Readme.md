# [Realm](https://realm.io/kr/) 데이터베이스
  - Realm 데이터베이스 사용 방법
  - 아래 외 내용들은 공식문서 참조할 것

---

## Realm 데이터베이스 사용방법
  ### 0. 시작하기
  - project gradle과 app gradle에 아래를 추가

  ```java
  // project gradle의 dependency
  classpath "io.realm:realm-gradle-plugin:3.5.0"

  // app gradle의 plugin
  apply plugin: 'realm-android'
  ```

  ### 1. Data class
  - RealmObject를 상속받아 Realm 테이블을 만든다.
  - PrimaryKey 어노테이션을 사용하여 키를 설정한다.
  - Ignore 어노테이션을 사용하여 테이블의 컬럼으로 사용하지 않을 수 있다.
  - 반드시 getter / setter 를 사용해야 한다.

  > Bbs.java

  ```java
  public class Bbs extends RealmObject{
      @PrimaryKey // 테이블의 키를 설정
      private int no;
      private String title;
      private String content;
      private String user;
      private long date;

      @Ignore // 테이블의 컬럼으로 사용되지 않는다.
      private String test;

      public int getNo() { return no; }
      public void setNo(int no) { this.no = no; }
      /* ...getter / setter */
  }
  ```

  ### 2. Realm database init
  - Realm 데이터베이스는 init 할때마다 폴더를 다르게 생성하므로 Application을 상속받아 앱이 켜질때 데이터베이스를 시작하도록 하여 데이터베이스를 일치시키도록 한다.
    - Activity 에서 설정하면 Activity 단위로 로드될 수 있다.
    - 하지만 SQLLite의 경우 사용하는 자원이 달라서 여기서 사용하면 안된다.
  - Manifest에 `application name`에 설정해주어야 앱이 로드될 때 생성시킬 수 있다.
  - 보통 클래스 이름은` 앱이름 + App`으로 설정한다.

  > RealmApp.java

  ```java
  public class RealmApp extends Application{
      @Override
      public void onCreate() {
          super.onCreate();
          Realm.init(this);
      }
  }
  ```

  > Manifest.xml

  ```xml
  <application
      android:name=".RealmApp"
      ...
      android:theme="@style/AppTheme">
  </application>
  ```

  ### 3.1 Realm Create
  - 동기로 입력하는 방법, 비동기로 입력하는 방법 두가지 존재
  - 자동증가 로직은 따로 제공하지는 않으며 직접 로직을 설정해야 한다.
  - PrimaryKey 의 경우 직접 setting 할 수 없고 레코드를 생성할때 따로 지정해 준다.

  > create 메소드

  ```java
  public void create() {
      /* 동기로 데이터 입력 */
      // 1. 인스턴스 생성 - connection
      Realm realm = Realm.getDefaultInstance();
      // 2. 트랜잭션 시작
      realm.beginTransaction();
      // 테이블 처리
      // 자동증가 로직처럼 사용하기
      Number maxValue = realm.where(Bbs.class).max("no");
      int no = (maxValue!=null) ? maxValue.intValue() +1:1;
      // 프라이머리 키는 class 뒤에 설정해준다.
      Bbs bbs = realm.createObject(Bbs.class, no); // 레코드 한개 생성
      bbs.setTitle("제목 1");
      bbs.setContent("내용을 여기에넣는다. \n  ");
      bbs.setDate(System.currentTimeMillis());
      realm.commitTransaction();

      /* 비동기로 데이터 입력 */
      RealmAsyncTask transaction = realm.executeTransactionAsync(
              asyncRealm -> {
                  Bbs bbs2 = asyncRealm.createObject(Bbs.class);
                  bbs2.setNo(2);
                  bbs2.setTitle("제목 2");
                  bbs2.setContent("내용을 여기에넣는다. \n  ");
                  bbs2.setDate(System.currentTimeMillis());
              }
              , () -> { // 성공시 진행
                  afterCreation(); // 데이터 베이스 처리가 끝나고 호출될 함수를 지정
              }
              , error -> { // 에러처리

              }
      );
  }
  public void afterCreation() {
      /* 데이터 생성 후 처리 로직 */
  }
  ```

  ### 3.2 Realm Read
  - 동기처리, 비동기처리 두가지가 존재
  - sql 문의 where절 처럼 And와 Or을 아래와 같이 지저해 줄 수 있다.

  >

  ```java
  public void read() {
      Realm realm = Realm.getDefaultInstance();
      RealmQuery<Bbs> query = realm.where(Bbs.class);
      query.equalTo("no",1);
      query.or();
      query.equalTo("title", "제목 1");
      // select * from bbs where no = 1 or title = '제목1'
      /* 동기로 질의 */
      RealmResults<Bbs> result1 = query.findAll(); // 결과는 array로 가져오게 된다.

      /* 비동기로 질의 */ // 비동기인 경우는 먼저 찍히기 때문에 콜백이 필요
      query.findAllAsync().addChangeListener(new RealmChangeListener<RealmResults<Bbs>>() {
          @Override
          public void onChange(RealmResults<Bbs> bbs) {
              // 결과처리
          }
      });
  }
  ```

  ### 3.3 Realm Update
  - 동기로 하는법 , 비동기로 하는법이 존재한다.
  - 읽기가 선행된 후 삭제한다.
  - 동기로 업데이트하는 경우 반드시 트랜잭션을 열어주어야 한다.
  - `copyToRealmOrUpdate` 는 입력값이 존재하면 수정을, 없으면 insert를 수행하는 메소드이다.

  >

  ```java
  public void update() { //read 후에 update를 실행한다.

      // 동기로 하는 경우
      Realm realm = Realm.getDefaultInstance();
      RealmQuery<Bbs> query2 = realm.where(Bbs.class);
      query2.equalTo("no",1);
      query2.or();
      query2.equalTo("title", "제목 1");
      /* 동기로 질의 */
      RealmResults<Bbs> result2 = query2.findAll();
      Bbs bbsFirst = result2.first();
      realm.beginTransaction();
      bbsFirst.setTitle("수정된 제목");
      realm.commitTransaction();

      // 비동기1
      Bbs bbs = new Bbs();
      bbs.setNo(1);
      bbs.setTitle("제목");
      Realm realm2 = Realm.getDefaultInstance();
      realm2.executeTransaction(new Realm.Transaction() {
          @Override
          public void execute(Realm realm) {
              realm2.copyToRealmOrUpdate(bbs);
          }
      });

      // 비동기2
      Realm realm3 = Realm.getDefaultInstance();
      RealmQuery<Bbs> query = realm3.where(Bbs.class);
      query.equalTo("no",1);
      query.findAllAsync().addChangeListener(new RealmChangeListener<RealmResults<Bbs>>() {
          @Override
          public void onChange(RealmResults<Bbs> bbs) {
              Bbs bbsFirst1 = bbs.first();
              bbsFirst1.setTitle("수정");
          }
      });
  }
  ```

  ### 3.4 Realm Delete
  - 검색결과를 삭제 할 수 있다.
  - 읽기가 선행된 후 삭제한다.

  >

  ```java
  public void delete() {
      Realm realm = Realm.getDefaultInstance();
      final RealmResults<Bbs> result = realm.where(Bbs.class).findAll();
      realm.executeTransaction(new Realm.Transaction() {
          @Override
          public void execute(Realm realm) {
              result.deleteFirstFromRealm(); // 검색결과의 첫번째 삭제

              Bbs bbs = result.get(2); // 특정 행 삭제
              bbs.deleteFromRealm();
              // result.deleteFromRealm(2); // 위와 동일

              result.deleteAllFromRealm(); // 검색결과 전체삭제
          }
      });
  }
  ```

---
## 참고
  ### 1. Create에서의 람다식
  - 원래는 아래와 같이 진행된다.

  ```java
  // 람다식을 안쓰는 경우
  RealmAsyncTask transaction2 = realm.executeTransactionAsync(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {

      }
  }, new Realm.Transaction.OnSuccess() {
      @Override
      public void onSuccess() {

      }
  }, new Realm.Transaction.OnError() {
      @Override
      public void onError(Throwable error) {

      }
  });

  // 람다식
  RealmAsyncTask transaction = realm.executeTransactionAsync(
          asyncRealm -> { // 데이터 세팅

          }
          , () -> { // 성공시 진행

          }
          , error -> { // 에러처리

          }
  );
  ```
