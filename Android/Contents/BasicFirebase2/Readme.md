# Firebase Basic 2
  - NoSQL의 구조
  - 간단한 게시판 만들기

---

## NoSQL의 구조
  ### 1. 간단한 RDB의 구조
  - RDB(Relational Database)의 경우 테이블의 형태로 데이터를 가지고 있다.
  - 아래 예시처럼 BBs 테이블과 같은 테이블은 "user_id"를 foreign key로 가지고 있다.
  - RDB의 경우 key뿐만아니라 값으로도 테이블을 검색할 수 있다.
  - User 테이블과 BBs 테이블이 join되어 있는 User_Bbs 테이블도 지닐 수 있다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase2/picture/rdbms.png)

  ### 2. NoSQL의 구조
  - NoSQL은 아래와 같이 tree 구조로 되어있다
  - NoSQL은 값으로 검색할 수 없고 key로만 검색할 수 있다. => 즉, 제목으로 검색할 수 없다.
  - 따라서 검색을 위해서는 아래와 같이 nested model로 되어야 한다.
  - 이로 인해 NoSQL의 경우 검색 속도가 빠르다는 장점이 있다.
  - 또한 검색을 원활하게 하기 위해 tag의 기법이 나왔으며 tag에 값들을 저장하고 있다.
  - 하지만 수정시에는 저장되어 있는 모든 데이터를 수정해주어야 하는 단점이 있다.
  - 예시> bbs의 aaa는 3군데 모두 저장되어 있다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase2/picture/nosql.png)

---

## 간단한 게시판 만들기
  ### 1. 데이터 구조
  - `@Exclude`와 같이 어노테이션을 달아서 여러 처리 가능
  - Bbs 클래스의 경우 `Serializable`를 상속받아 Intent에서 그대로 넘긴다.

  > User.class

  ```java
  public class User {
      public String user_id;
      public String user_password;
      public String username;
      public String age;
      public String email;

      @Exclude // database field 에서 제외하고 싶을때 사용
      public boolean check = false;

      // 내가 작성한 글 목록
      public List<Bbs> bbs;
      public User() {
          // 파이어베이스에서 parsing 할 때 한번 호출
      }

      public User(String user_password, String username, String age, String email) {
          this.user_password = user_password;
          this.username = username;
          this.age = age;
          this.email = email;
      }
  }

  ```

  > Bbs.class

  ```java
  public class Bbs implements Serializable{
      public String bbs_id;
      public String content;
      public String date;
      public String user_id;
      public String username;

      public Bbs(){
          // 파이어베이스에서 parsing 할 때 한번 호출
      }
      public Bbs(String content, String date, String user_id, String username){
          this.content = content;
          this.date = date;
          this.user_id = user_id;
          this.username = username;
      }
  }
  ```

  ### 2. Signin / Signup
  - SigninActivity
    - LoginActivity로 만듦 (전체코드는 들어가서 볼것..)
    - userRef의 리스너를 Asynctask에서 달아주게 되면 비동기로 작동하여 오류가 뜸(userRef의 리스너는 비동기로 작동하므로 이를 주의해서 코딩할 것)
    - `snapshot`에서 Map 형태로 받아올 수 있다.
    - Signup 이후에 바로 로그인을 할 수 있도록 설정
  - SingupActivity
    - 형식 체크 후 가입 및 firebase에 데이터 등록

  > SigninActivity

  ```java
  FirebaseDatabase database;
  DatabaseReference userRef;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_signin);

      database = FirebaseDatabase.getInstance();
      userRef = database.getReference("users");
      /* 생략 */
  }
  /* 생략 */

  // 로그인 테스크 처리
  public class UserLoginTask {

      private final String mId;
      private String name;
      private final String mPassword;

      UserLoginTask(String id, String password) {
          mId = id;
          mPassword = password;
      }

      public void gosignin(){
          // id pw가 일치하는지 확인후 true, false 를 체크 한 후 true면 로그인 실행
          boolean check = false;
          // firebase에서 데이터를 불러옴
          for(User user : userData){
              if(user.user_id.equals(mId) && user.user_password.equals(mPassword)){
                  name = user.username;
                  check=true;
                  break;
              }
          }

          mAuthTask = null;
          showProgress(false);
          if (check) {
              Intent intent = new Intent(SigninActivity.this, ListActivity.class);
              intent.putExtra(Const.user_id,mId);
              intent.putExtra(Const.user_password,mPassword);
              intent.putExtra(Const.user_name, name);
              startActivity(intent);
              finish();
          } else {
              mPasswordView.setError(getString(R.string.error_incorrect_password));
              mPasswordView.requestFocus();
          }

      }
  }

  // 로그인을 위한 데이터(모든 id, pw,name)를 불러오는 리스너
  List<User> userData = new ArrayList<>();
  ValueEventListener valueEventListener = new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
          userData.clear();
          for(DataSnapshot snapshot : dataSnapshot.getChildren()){
              User user = new User();
              Map map = (HashMap) snapshot.getValue();
              String id = snapshot.getKey(); user.user_id = id;
              String user_password = (String) map.get("user_password"); user.user_password = user_password;
              String username = (String) map.get("username"); user.username = username;
              userData.add(user);
          }

          if(!"".equals(signid) && !"".equals(signpw)){
              mAuthTask = new UserLoginTask(signid,signpw);
              mAuthTask.gosignin();
          }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
  };

  @Override
  protected void onResume() {
      super.onResume();
      userRef.addValueEventListener(valueEventListener);
  }

  @Override
  protected void onPause() {
      super.onPause();
      userRef.removeEventListener(valueEventListener);
  }

  // signup 한 이후에 id와 pw를 받아와 로그인 처리를 한다.
  public void signup(View view){
      Intent intent = new Intent(this, SignupActivity.class);
      startActivityForResult(intent,Const.signuupcode);
  }

  String signid="";
  String signpw="";
  // signup 한 이후 로그인 처리
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      switch (requestCode){
          case Const.signuupcode:
              if(resultCode==RESULT_OK){
                  signid = data.getStringExtra(Const.user_id);
                  signpw = data.getStringExtra(Const.user_password);
              }
              break;
      }
  }
  ```

  > SigninActivity

  ```java
  public class SignupActivity extends AppCompatActivity{

      FirebaseDatabase database;
      DatabaseReference userRef;

      private EditText editId, editPassword, editName, editAge, editEmail;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_signup);
          database = FirebaseDatabase.getInstance();
          userRef = database.getReference("users");
          initView();
      }

      private void initView(){
        /* 코드 */
      }

      // 리셋 버튼 클릭시 모든 데이터 리셋(리셋버튼과 연결)
      public void reset(View view){
          editId.setText("");
          editPassword.setText("");
          editName.setText("");
          editAge.setText("");
          editEmail.setText("");
      }
      // 회원가입 취소버튼 클릭시 이전 화면으로 이동(캔슬버튼과 연결)
      public void cancel(View view){
          setResult(RESULT_CANCELED);
          finish();
      }
      // 사용자 등록(siginup버튼과 연결)
      public void signup(View view){
          String user_id = editId.getText().toString();
          String user_password = editPassword.getText().toString();
          String username = editName.getText().toString();
          String age = editAge.getText().toString();
          String email = editEmail.getText().toString();

          // 회원가입 형식 체크
          if(...){
            ... // 생략
          } else {
              User user = new User(user_password,username,age,email);
              userRef.child(user_id).setValue(user);

              // 회원가입 조건 만족시 자동 로그인 처리를 위한 데이터 전달
              Intent intent = getIntent();
              intent.putExtra(Const.user_id,user_id);
              intent.putExtra(Const.user_password,user_password);
              setResult(RESULT_OK,intent);
              finish();
          }
      }
      // 아이디 형식이 유효한지 체크
      private boolean isEmailValid(String email) { /* 코드 */ }
      // 비밀번호 형식이 유효한지 체크 (4자리 이상 & 숫자가 1개 이상)
      private boolean isPasswordValid(String password) { /* 코드 */ }
      // 나이 형식이 유효한지 체크
      private boolean isAgeValid(String age){ /* 코드 */ }
  }
  ```

  ### 3. ListActivity
  - ListActivity(전체코드는 들어가서...)
    - 로그인한 아이디와 비밀번호를 받아 내 글만 보여주는 View와 전체 글을 보여주는 View를 구성
    - Data를 여기서 불러오고 View로 넘김
    - Post 버튼 클릭시 id와 name을 intent로 넘겨 값을 저장
    - recyclerView의 Adpater의 인터페이스를 상속받아 메소드를 정의하며 이 때 Bbs의 Serializable을 이욯하여 Bbs 클래스를 넘김
  - ViewPager
    - 개인이 작성한 글과 모두가 작성한 글을 recyclerView를 이용해 보여줌
    - setData를 통해 recyclerView를 업데이트시킴
  - RecyclerAdapter
    - View에 data를 세팅하며 데이터를 refresh 시킴
    - click시 DetailActivity로 넘어가는 인터페이스를 설계

  > ListActivity

  ```java

  /*====== 코드 ===========*/
  ValueEventListener valueEventListener = new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
          bbsAllList.clear();
          bbsMineList.clear();
          for(DataSnapshot snapshot : dataSnapshot.getChildren()){
              Bbs bbs = snapshot.getValue(Bbs.class);
              bbs.bbs_id = snapshot.getKey();
              bbsAllList.add(bbs);
              if(bbs.user_id.equals(mId)){
                  bbsMineList.add(bbs);
              }
          }
          bbsAll.setDataAll(bbsAllList);
          bbsMine.setDataMine(bbsMineList);
      }
      @Override
      public void onCancelled(DatabaseError databaseError) { }
  };

  /*====== 코드 ===========*/

  // recyclerView의 Adpater의 인터페이스(DetailActivity로 이동)
  @Override
  public void goDetail(final String bbs_id) {
      for(Bbs bbs : bbsAllList){
          if(bbs.bbs_id.equals(bbs_id)){
              Intent intent = new Intent(ListActivity.this,DetailActivity.class);
              intent.putExtra(Const.Bbs,bbs);
              startActivity(intent);
          }
      }
  }
  ```

  > View (BbsView)

  ```java
  public class BbsAll extends FrameLayout{

      RecyclerView recyclerView;
      BbsAdapter adapter;

      public BbsAll(@NonNull Context context) {
          super(context);
          init();
          adapter = new BbsAdapter(context);
          recyclerView.setAdapter(adapter);
          recyclerView.setLayoutManager(new LinearLayoutManager(context));
      }
      public void setDataAll(List<Bbs> bbsList){
          adapter.setDataAndRefresh(bbsList);
      }
      private void init(){
          View view = LayoutInflater.from(getContext()).inflate(R.layout.view_list,null);
          recyclerView = view.findViewById(R.id.recyclerView);
          addView(view);
      }
  }
  ```

  ### 4. PostActivity
  - toolbar를 세팅
  - Datetime 포멧 변경
  - toolbar의 닫기버튼 리스너 연결하여 엑티비티를 종료시킴
  - Post시 User데이터뿐 아니라 Bbs 데이터에도 값을 저장

  > PostActivity

  ```java
  public class PostActivity extends AppCompatActivity {

      /* 전역변수 선언 */

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_post);

          /* 인텐트를 꺼내고 firebase 레퍼런스 설정 */

          initView();
          initListener();
          setSupportActionBar(toolbarPost);

      }

      private void initView() {
          toolbarPost = findViewById(R.id.toolbarPost);
          textPost = toolbarPost.findViewById(R.id.textPost);
          btnfinish = toolbarPost.findViewById(R.id.btnfinish);
          editContent = findViewById(R.id.editContent);
      }

      public void initListener() {
          textPost.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                  String datetime = sdf.format(new Date(System.currentTimeMillis()));
                  String bbs_id = bbsRef.push().getKey();
                  String content = editContent.getText().toString();
                  Bbs bbs = new Bbs(content, datetime, user_id, user_name);

                  bbsRef.child(bbs_id).setValue(bbs);
                  userRef.child(user_id).child("bbs").child(bbs_id).setValue(bbs);

                  setResult(RESULT_OK);
                  finish();
              }
          });

          btnfinish.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  setResult(RESULT_CANCELED);
                  finish();
              }
          });
      }
  }
    // 홈버튼 자동 추가 방법----- title도 getSupportActionBar를 통해 설정
  //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //    @Override
  //    public boolean onOptionsItemSelected(MenuItem item) {
  //        switch (item.getItemId()){
  //            case android.R.id.home:
  //                onBackPressed();
  //                return true;
  //        }
  //        return super.onOptionsItemSelected(item);
  //    }
  ```

  ### 5. DetailActivity
  - `Bbs bbs = (Bbs) intent.getSerializableExtra(Const.Bbs);` 를 통해 데이터를 가져온다.
  - 그 외에는 상세내역을 보여주는 페이지로 특이점 없음

  ### 6. 결과
  - 아래와 같은 기능 가능

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase2/picture/result.png)

---

## 참고
  ### 1. 수업시간에 나간 코드에서 생각할 것
  - 아래 코드에서 User클래스의 경우 Bbs가 없는경우, 있는경우가 있으므로 이를 경우로 나누어 진행해주어야 한다.
  - 또한 dataSnapshot을 호출하여 Bbs의 for문을 한번 더 돌려 값을 저장해준다.

  > MainActivity.java

  ```java
  userRef.addValueEventListener(new ValueEventListener() {
              // user 노드의 값이 변경되면 발생
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                  Log.d("FireBase","=========cnt="+dataSnapshot.getChildrenCount());
                  userData.clear();
                  // 변경사항이 스냅샷 형태로 넘어오면
                  // 해당 스냅샷의 하위 노드를 배열로 꺼내서 사용한다.
                  for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                      String id = snapshot.getKey();
                      Log.d("FireBase","user="+id);

                      // 하위 노드 아래에 리스트 형태의 또 다른 노드가 있으면
                      // 부가적인 처리가 필요하다
                      if(snapshot.hasChild("bbs")){
                          User user = new User();
                          Map map = (HashMap) snapshot.getValue();
                          String name = (String) map.get("username");
                          String email = (String) map.get("email");

                          user.username = name;
                          user.email = email;
                          user.user_id = id;

                          // 하위노드의 스냅샷 꺼내기
                          DataSnapshot bbss = snapshot.child("bbs");
                          user.bbs = new ArrayList();
                          // 하위 노드에 리스트가 존재하면 해당 리스트를
                          // 배열로 꺼내서 위의 방법처럼 사용한다
                          for(DataSnapshot item : bbss.getChildren()) {
                              Bbs bbs = item.getValue(Bbs.class);
                              Log.d("FireBase","Bbs in User===="+bbs.title);
                              user.bbs.add(bbs);
                          }
                          userData.add(user);
                          // 하위노드 아래에 키:값 세트만 존재하면 클래스로 바로 컨버팅 할 수 있다.
                      }else{
                          User user = snapshot.getValue(User.class);
                          user.user_id = id;
                          userData.add(user);
                      }
                  }
                  // data 를 아답터에 반영하고 아답터를 notify 한다.
                  userAdapter.setDataAndRefresh(userData);
              }
  ```
