# Basic FireBase
  - [FireBase 홈페이지](https://firebase.google.com/)
  - FireBase의 형태 및 시작 / 구성
  - Realtime database 기초

---

## Firebase의 형태 및 시작 / 구성
  ### 1. Firebase의 형태
  - Firebase 는 NoSQL의 DB를 제공한다.
  - 백엔드는 BaaS의 형태(로그인과 테이블 기능 제공)에서 PaaS 의 형태로 진화
  - Firebase는 PaaS(Platform as a Service)의 형태로 BaaS 보다 많은 기능들을 제공한다.
  - Firebase는 manifest에 직접 권한 설정을 하지 않아도 자동으로 처리해준다.

 ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/baas%2Cpaas.png)

  ### 2. Firebase 시작
  - [firebase 홈페이지](https://firebase.google.com/) -> [gotoconsole](https://console.firebase.google.com/?hl=ko) -> 새 프로젝트 생성
  - 아래의 순서로 실행

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/process.png)

  ### 3. Firebase 구성
  - 아래와 같이 구성

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/menu.png)

  - 사용하기 위해서는 아래와 같이 gradle의 Dependency 추가

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/dependency.png)

---

## Realtime database 기초
  ### 1. 시작
  - [realtime database 문서로 이동](https://firebase.google.com/docs/database/android/start/?authuser=0) -> get start ->적혀있는 대로 gradle에 추가 및 실행 -> 만든 프로젝트에서 database를 생성 -> 규칙(rule) 클릭후 [규칙 설정 가이드](https://firebase.google.com/docs/database/security/quickstart?authuser=0) 에 따라 rule 설정

  > app gradle

  ```
  compile 'com.google.firebase:firebase-database:11.4.2'
  (안드로이드 3.0은 compile이 아니라 implementation)
  ```

  > rule 예시 (공개의 경우)

  ```
  {
    "rules": {
      ".read": true,
      ".write": true
    }
  }
  ```

  ### 2. 데이터 만들기
  - NoSQL 형식으로 만들어진다.
  - tree 형태로 값을 입력하기 전까지 하위 디렉토리로 연결할 수 있다.
  - 값을 입력하면 그 아래에는 데이터를 넣을 수 없다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/datainput.png)

  ### 3. 코드 예시
  - `database.getReference()` : 디렉토리(key)를 지정
  - `setValue()` : 값을 저장

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/datainputex.png)

---

## Firebase를 이용한 간단한 예제1 - 채팅기본
  ### 1. 채팅
  - ButterKnife 라이브러리 이용([참고_Dependency Injection](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/DependencyInjection))
  - 데이터 베이스를 연결 -> 레퍼런스 참조 -> 값 세팅 순으로 진행
  - firebase에 값이 바뀌면 리스너가 동작하게 된다.
  - 항상 리스너는 시작과 중지를 해주어야 한다. (onResume과 onPause에서 결정)
    - firebase랑 서버랑 계속 통신하게 되는 문제가 생기게 된다.
  - 유일한 key는 push()로 생성할 수 있으며 getKey로 키를 가져올 수 있다.

  > MainActivity.java

  ```java
  public class MainActivity extends AppCompatActivity {

      @BindView(R.id.textMsg) TextView textMsg;
      @BindView(R.id.editText) EditText editText;

      FirebaseDatabase database;
      DatabaseReference chatRef;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          ButterKnife.bind(this);

          // 데이터 베이스 connection
          database = FirebaseDatabase.getInstance();
          // key값의 위치, 없으면 그냥 생성
          chatRef = database.getReference("chatMsg");
      }

      ValueEventListener valueEventListener = new ValueEventListener() {
          // 순간의 데이터를 모아서 넘겨주는 것이 snapshot
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              textMsg.setText("");
              for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                  String msg = snapshot.getValue(String.class);
                  // 혹은 String msg = (String) snapshot.getValue();

                  textMsg.setText(textMsg.getText().toString()+"\n"+msg);
              }
          }
          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      };

      // 항상 시작후 중지되었을 때 리스너를 중지시켜주어야 한다. (안하게 되면 서버랑 계속해서 통신하게 된다.)
      @Override
      protected void onResume() {
          super.onResume();
          chatRef.addValueEventListener(valueEventListener);
      }

      @Override
      protected void onPause() {
          super.onPause();
          chatRef.removeEventListener(valueEventListener);
      }

      @OnClick(R.id.btnSend)
      public void send(View view){
          String msg = editText.getText().toString();
          // key의 값에 아무것도 없으면 생성이 안되므로 임의값을 넣어준다.
          if(msg == null || "".equals(msg)){
              msg = "none";
          }
          // 유일한 키를 생성하고(push) 그 키를 가져온다.(getkey) => 유일한 node를 하나 생성
          String key = chatRef.push().getKey();
          // 방금 생성한 키로 레퍼런스를 가져온 후(child) node에 값을 넣는다.(setValue)
          chatRef.child(key).setValue(msg);
      }
  }

  ```

  ### 2. 결과
  - 텍스트 입력후 send 시 메시지 전송

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/chat.png)

---

## 참고
  ### 1. 키보드 팝업시 화면사이즈 재조정
  - `android:windowSoftInputMode="adjustResize"`를 manifest의 Activity에 설정해준다.

  ```xml
  <activity android:name=".MainActivity"
            android:windowSoftInputMode="adjustResize"> <!-- 키보드 팝업시 화면사이즈 재조정 -->
      <intent-filter>
          <action android:name="android.intent.action.MAIN" />
          <category android:name="android.intent.category.LAUNCHER" />
      </intent-filter>
  </activity>
  ```

  ### 2. 웹호스팅 / 서버호스팅 / 클라우드
  - 웹호스팅 -> 클라우드 -> 서버호스팅 보통 이 순서로 사용

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/webhosting.png)
