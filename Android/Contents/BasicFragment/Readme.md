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
  - textView 클릭시 callback 메소드를 이용하여 주소록 상세내역으로 이동시키며 `id` 값을 가지고 호출한다.
  - `notifyDataSetChanged` 를 이용하여 listview(RecyclerView)를 업데이트

  > ListAdapter.java

  ```java
  public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder>{
      List<Contact> data = new ArrayList<>();
      ListFragment.CallbackDetail callbackDetail;
      Context context;

      public ListAdapter(Context context, final ListFragment.CallbackDetail callbackDetail){
          this.context = context;
          this.callbackDetail = callbackDetail;
      }

      public void setData(List<Contact> data){
          this.data = data;
          notifyDataSetChanged();
      }

      @Override
      public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
          View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.number_list,parent,false);
          return new Holder(view,callbackDetail);
      }

      @Override
      public void onBindViewHolder(Holder holder, int position) {
          Contact contact = data.get(position);
          holder.setTextNameList(contact.getName());
          holder.setId(contact.getId());
      }

      @Override
      public int getItemCount() { return data.size(); }

      class Holder extends RecyclerView.ViewHolder{
          TextView textNameList;
          int id;
          public Holder(View itemView, final ListFragment.CallbackDetail callback) {
              super(itemView);
              textNameList = (TextView) itemView.findViewById(R.id.textNameList);
              // onClick하면 Detail Fragment 호출 (callback 함수 이용)
              textNameList.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      callback.showDetail(id);
                  }
              });
          }
          public void setTextNameList(String name){ textNameList.setText(name); }
          public void setId(int id){ this.id = id; }
      }
  }
  ```

  ### 5. MainActivity
  - BaseActivity를 상속
  - 가로 및 세로체크를 한 후 레이아웃 결정
  - 만약 회전을 시킬 경우 init이 아닌 changeInit을 호출하며 View만 세팅
  - ListFragment의 callback 메소드를 상속받아 DetailFragment로 이동시키는 메소드 구현
  - 만약 세로일 경우 바로 세팅, 가로일 경우 새로운 Fragment를 만들어서 이동시킴
  - Fragment의 경우 `Bundle` 을 이용해 데이터를 전달, `Argument`를 이용

  > MainActivity.java

  ```java
  public class MainActivity extends BaseActivity implements ListFragment.CallbackDetail {

      @Override
      public void init() {
          setContentView(R.layout.activity_main);

          if(getResources().getConfiguration().orientation
                  == Configuration.ORIENTATION_PORTRAIT){ // 현재 레이아웃 세로체크
              initFragment();
          }
      }

      @Override
      public void changeInit() {
          setContentView(R.layout.activity_main);
      }

      // 프래그 먼트를 더함
      private void initFragment(){
          getSupportFragmentManager()
                  .beginTransaction()
                  .add(R.id.container,new ListFragment()) //addtoBack..Stack은 쓰지 않음
                  .commit();
      }

      @Override
      public void showDetail(int id) {
          // 레이아웃이 세로이면 디테일 프레그먼트를 화면에 보이면서 값을 전달
          if(getResources().getConfiguration().orientation
                  == Configuration.ORIENTATION_PORTRAIT){
              DetailFragment detailFragment = new DetailFragment();
              Bundle bundle = new Bundle();
              bundle.putInt("id",id);
              // 프레그먼트로 값 전달하기
              detailFragment.setArguments(bundle);

              getSupportFragmentManager()
                      .beginTransaction()
                      .add(R.id.container, detailFragment)
                      .addToBackStack(null)
                      .commit();
          }
          // 레이아웃이 가로이면 삽입되어 있는 프레그먼트를 가져온다,
          else {
              DetailFragment detailFragment
                      = (DetailFragment) getSupportFragmentManager()
                          .findFragmentById(R.id.fragmentDetail);
              // 만들어놓은 함수를 호출해서 값을 세팅
              if(detailFragment != null){
                  Contact contact;
                  contact = detailFragment.loaddata(id);
                  detailFragment.setTextNo(contact.getId());
                  detailFragment.setTextName(contact.getName());
                  detailFragment.setTextNumber(contact.getNumber());
              }
          }
      }
  }
  ```

  ### 6. ListFragment
  - Fragment는 생성자에는 표시하지 않는다.
  - Context를 Fragment가 Attach(`onAttach`) 할 때 받아놓으며 이는 Activity가 넘어온 것
  - loader를 이용해 데이터를 받아오며 아답터를 연결하여 RecyclerView 구현
  - Callback 인터페이스를 설계

  > ListFragment.java

  ```java
  public class ListFragment extends Fragment {
      Context context;
      RecyclerView recyclerView;
      ListAdapter adapter;
      List<Contact> contacts = new ArrayList<>();
      CallbackDetail callback;

      public ListFragment() {
          // Required empty public constructor
      }

      @Override
      public void onAttach(Context context) {
          super.onAttach(context);
          this.context = context;
          if(context instanceof CallbackDetail){
              callback = (CallbackDetail) context;
          }
      }

      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          View view = inflater.inflate(R.layout.fragment_list,container,false);
          init(view);
          return view;
      }

      private void init(View view){
          recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
          Loader loader = new Loader(context);
          contacts = loader.contactLoad();

          adapter = new ListAdapter(context,callback);
          adapter.setData(contacts);

          recyclerView.setAdapter(adapter);
          recyclerView.setLayoutManager(new LinearLayoutManager(context));
      }

      public interface CallbackDetail{
          public void showDetail(int id);
      }
  }
  ```

  ### 7. DetailFragment
  - 전화 걸기 기능 추가
  - `MainActivity` 에서 넘긴 `Argument` 를 받아 `Bundle` 에 있는 `id` 를 가지고 `loader` 를 이용하여 상세 데이터를 가져옴

  > DetailFragment.java

  ```java
  public class DetailFragment extends Fragment {
      TextView textNo, textName, textNumber;
      ImageButton imageCall;
      String number = null;
      Contact contact;
      Context context;

      public DetailFragment() {
          // Required empty public constructor
      }

      @Override
      public void onAttach(Context context) {
          super.onAttach(context);
          this.context = context;
      }

      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          View view = inflater.inflate(R.layout.fragment_detail, container, false);
          init(view);
          imageCall.setOnClickListener(onClickListener);
          return view;
      }

      private void init(View view){
          textNo = (TextView) view.findViewById(R.id.textNo);
          textName = (TextView) view.findViewById(R.id.textName);
          textNumber = (TextView) view.findViewById(R.id.textNumber);
          imageCall = (ImageButton) view.findViewById(R.id.imageCall);

          // Argument 로 전달된 값 꺼내기
          Bundle bundle = getArguments();
          if(bundle != null) {
              int no = bundle.getInt("id",-1);
              contact = loaddata(no);
              setTextNo(contact.getId());
              setTextName(contact.getName());
              setTextNumber(contact.getNumber());
              number = contact.getNumber();
          }
      }
      public void setTextNo(int no) { textNo.setText(no+""); }

      public void setTextName(String name) { textName.setText(name); }

      public void setTextNumber(String number){ textNumber.setText(number); }

      public Contact loaddata(int id){
          Contact data = null;
          Loader loader = new Loader(context);
          data = loader.detailLoad(id);
          return data;
      }

      View.OnClickListener onClickListener = new View.OnClickListener() {
          @SuppressWarnings("MissingPermission")
          @Override
          public void onClick(View v) {
              switch (v.getId()){
                  case R.id.imageCall:
                      if(number != null){
                          String num = "tel:" +number;
                          Uri uri = Uri.parse(num);
                          Intent intent = new Intent(Intent.ACTION_CALL,uri);
                          v.getContext().startActivity(intent);
                      }
                      break;
              }
          }
      };  
  }
  ```

---

## 참고 링크
#### 1.[프래그먼트](https://developer.android.com/guide/components/fragments.html)
#### 2.[프레그먼트 블로그](http://blog.saltfactory.net/implement-layout-using-with-fragment/)
#### 3.[ContentResolver](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/ContactPractice)
