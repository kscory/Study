# Fragment
  - Fragment에 대해

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFragment/picture/fragments.png)

---

## Fragment의 생명주기
  ### Fragment의 생명주기 (그림)

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFragment/picture/fragment_lifecycle.png)

  ### 코드 예시
  - 로그로 생명주기 확인

  >  MainActivity

  ```java
  public class MainActivity extends AppCompatActivity implements ListFragment.Callback {

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          initFragment();
      }

      public void initFragment(){
          // 1. 프레그먼트 매니저
          FragmentManager manager = getSupportFragmentManager();
          // 2. 트랜잭션 처리자
          FragmentTransaction transaction = manager.beginTransaction(); // 프래크먼트를 처리하기 위한 트랜잭션을 시작
          // 3. 액티비티 레이아웃에 프레그먼트를 부착하고
          Log.d("Activity","======================================before add()");
          transaction.add(R.id.container, new ListFragment());
          Log.d("Activity","======================================afteradd()");
          // 4. 커밋해서 완료
          transaction.commit();
          Log.d("Activity","======================================commit()");
      }

      @Override
      protected void onStart() {
          super.onStart();
          Log.d("Activity","======================================onStart()");
      }

      @Override
      protected void onResume() {
          super.onResume();
          Log.d("Activity","======================================onResume()");
      }
  }
  ```

  >  Fragment

  ```java
  public class ListFragment extends Fragment {
      public ListFragment() {

      }

      @Override
      public void onAttach(Context context) {
          Log.d("Fragment","==========onAttach()");
          super.onAttach(context);
      }

      @Override
      public void onDetach() {
          Log.d("Fragment","==========onDetach()");
          super.onDetach();
      }

      // 액티비티에 부착되면서 동작시작
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
          Log.d("Fragment","==========onCreateView()");
          View view = inflater.inflate(R.layout.fragment_list, container, false);

          return view;
      }

      @Override
      public void onStart() {
          Log.d("Fragment","==========onStart()");
          super.onStart();
      }

      @Override
      public void onResume() {
          Log.d("Fragment","==========onResume()");
          super.onResume();
      }
  }  
  ```

  > 결과

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFragment/picture/fragments_ex.png)

---

## Fragment를 이용한 주소록 예시

  ### 1. Layout 구성
  - res/layout-land에 세로방향 layout xml 설정
    - layout-land에 저장하게 되면 세로방향으로 인식을 하게 된다.
  - 주소록 목록을 보여주는 ListFragment 생성
  - 상세 내역을 보여주는 DetailFragment를 구성
  - Layout 구성 그림

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFragment/picture/fragments_Layout.png)

  ### 2. domain / util
  - util은 생략 _ [참고 링크](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/Permission)
  - domain은 Contact 모델과 데이터 Loader로 구성
  - Loader의 경우 Content Resolver 이용했으며 모든 데이터를 불러오는 메소드와 id를 받아 한개의 데이터만 불러오는 메소드로 구성

  > Contact.java

  ```java
  public class Contact {
      private int id;
      private String name;
      private String number;

      public int getId() { return id; }
      public void setId(int id) { this.id = id; }
      public String getName() { return name; }
      public void setName(String name) { this.name = name; }
      public String getNumber() { return number; }
      public void setNumber(String number) { this.number = number; }
  }
  ```

  > Loader.java

  ```java
  public class Loader {
      Context context;
      public Loader(Context context){
          this.context = context;
      }

      // 모든 Contact 데이터 로드
      public List<Contact> contactLoad(){
          List<Contact> data = new ArrayList<>();
          // Content Resolver 정의
          ContentResolver resolver = context.getContentResolver();
          // Uri 정의
          Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
          // 가져올 컬럼(projection) 정의
          String[] projection = {
                  ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                  ContactsContract.CommonDataKinds.Phone.NUMBER
          };
          // 커서 정의
          Cursor cursor = resolver.query(uri,projection,null,null,
                  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY +" asc");
          // 반복문을 돌면서 데이터 저장
          if(cursor !=null){
              while(cursor.moveToNext()){
                  Contact contact = new Contact();
                  int index =  cursor.getColumnIndex(projection[0]);
                  contact.setId(cursor.getInt(index));
                  index =  cursor.getColumnIndex(projection[1]);
                  contact.setName(cursor.getString(index));
                  index =  cursor.getColumnIndex(projection[2]);
                  contact.setNumber(cursor.getString(index));
                  data.add(contact);
              }
          }
          return data;
      }

      // 특정 Contact 데이터 로드
      public Contact detailLoad(int id) {
          Contact contact = new Contact();
          ContentResolver resolver = context.getContentResolver();
          Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
          String[] projection = {
                  ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                  ContactsContract.CommonDataKinds.Phone.NUMBER
          };
          Cursor cursor = resolver.query(uri,projection,
                  ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                  null,null);
          if(cursor !=null){
              while(cursor.moveToNext()){
                  int index = cursor.getColumnIndex(projection[0]);
                  contact.setId(cursor.getInt(index));
                  index = cursor.getColumnIndex(projection[1]);
                  contact.setName(cursor.getString(index));
                  index = cursor.getColumnIndex(projection[2]);
                  contact.setNumber(cursor.getString(index));
              }
          }
          return contact;
      }
  }
  ```

  ### 3. BaseActivity
  - 가로모드와 세로모드를 일정하게 유지하기 위해 `init` 메소드와 `changeInit` 메소드 두가지로 나누어 다르게 진행 강제
  - 퍼미션을 확인(3가지), 퍼미션 솩인 후 init 실행을 위한 `Callback` 인터페이스 구현

  > BaseActivity.java

  ```java
  public abstract class BaseActivity extends AppCompatActivity implements PermissionUtil.Callback {
      // 상수 정의
      private static final int PER_CODE = 999;
      private static final String[] permission = {
              Manifest.permission.CALL_PHONE,
              Manifest.permission.READ_EXTERNAL_STORAGE,
              Manifest.permission.READ_CONTACTS
      };
      PermissionUtil pUtil;

      // 추상 메소드 정의 (init을 강제로 호출)
      public abstract void init();
      public abstract void changeInit();

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          // 가로 모드시 리셋되게 하지 않음
          if(savedInstanceState != null) {
              changeInit();
              return;
          }

          pUtil = new PermissionUtil(this,PER_CODE,permission);
          pUtil.checkVersion();
      }

      // 퍼미션 체크
      @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
          super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          pUtil.onResult(requestCode,grantResults);
      }
      // 인터페이스 구현 및 callback 호출
      public void callinit(){
          init();
      }
  }
  ```

  ### 4. ListAdapter

  ### 5. MainActivity

  ### 6. ListFragment

  ### 7. DetailFragment
---

## 참고 링크
#### 1.[프래그먼트](https://developer.android.com/guide/components/fragments.html)
#### 2.[프레그먼트 블로그](http://blog.saltfactory.net/implement-layout-using-with-fragment/)
