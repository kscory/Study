# [Realm](https://realm.io/kr/) 데이터베이스
  - Realm 데이터베이스 사용 방법

---

## 구성1
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
  - Application을 상속받아 앱이 켜질때 실행하도록 한다.
  - 

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

  ### 3.1 Realm Create
  - 내용

  >

  ```java

  ```

  ### 3.2 Realm Read
  - 내용

  >

  ```java

  ```

  ### 3.3 Realm Update
  - 내용

  >

  ```java

  ```

  ### 3.4 Realm Delete
  - 내용

  >

  ```java

  ```
