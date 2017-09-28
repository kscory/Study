# Content Resolver / Intent
- Content 사용법
- 주소록(전화번호부) 구현
- Intent에 대한 간단한 설명

---

## Content Resolver 란
- `Content Provider` 는 어플리케이션 사이에서 Data 를 공유하는 통로 역할을 하며
- `Content Resolver` 결과를 반환하는 브릿지 역할을 한다.
</br>

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/ContactPractice/picture/contentResolver.png)

</br>

- 사용 방법 (전화번호부 예시)

  ### 1. Content Resolver 를 불러오기

  ```java
  // 처음 데이터 정의
  List<Contact> contacts = new ArrayList<>();
  // Content resolver 불러오기
  ContentResolver resolver = getContentResolver();
  ```

  ### 2. 데이터 URI 정의
    - 일종의 DB에서의 테이블 이름 (URI는 주소의 프로토콜을 포함하고 있다-> 일종의 상위개념)

  > 전화번호 URI : ContactsContract.CommonDataKinds.Phone.CONTENT_URI</br>
  > 주소록 URI : ContactsContract.Contacts.CONTENT_URI
  ```java
  Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
  ```

  ### 3. 가져올 컬럼 정의
    - 조건절을 정의할 수도 있다. (옵션)

  ```java
  String projection[] = {
          ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
          ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
          ContactsContract.CommonDataKinds.Phone.NUMBER
  };  
  ```

  ### 4. 불러온 Content Resolver로 쿼리한 데이터를 Cursor에 담는다.
    - 쿼리 결과 -> Cursor (쿼리뒤에 순서대로)
    - 조건절 컬럼명 사용 예시
      - ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id
    - 매핑 조건값 사용 예시 (조건 컬럼값 + ? 들어갈 값)
      - ContactsContract.CommonDataKinds.Phone.MIMETYPE + "=?", </br>
        {ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE }
    - 정렬 조건 사용 예시 (" asc" 띄어쓰기 조심)
      - ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc"

  ```java
  Cursor cursor = resolver.query(
          uri,        // 데이터의 주소 (URI)
          projection, // 가져올 데이터 컬럼명 배열 (projection)
          null,       // 조건절에 들어가는 컬럼명들 지정
          null,       // 지정된 컬럼명과 매핑되는 실제 조건 값 (?에 들어가는 값)
          null        // 정렬
  );
  ```

  ### 5. Cursor에 담긴 데이터를 반복문을 돌면서 처리한다.
    - `set~~` 에 바로 담을 수 없으므로 index를 사용한다.

  ```java
  if(cursor != null){
      while(cursor.moveToNext()){
          Contact contact = new Contact();

          int index = cursor.getColumnIndex(projection[0]);
          contact.setId(cursor.getInt(index));

          index = cursor.getColumnIndex(projection[1]);
          contact.setName(cursor.getString(index));

          index = cursor.getColumnIndex(projection[2]);
          contact.setNumber(cursor.getString(index));

          contacts.add(contact);
      }
  }
  ```

---

## Intent
  ### [pdf 참고](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/ContactPractice/pdf/007_Intent.pdf)

  - Intent는 Explicit / Implicit Intent가 존재
  - Intent Filter (정의된 내장 액션들이 존재)
  - Category 지정시 나중에 검색하기 편리

---

## 주소록 구성 전체 코드 구성
- `domain` : 데이터를 구성하며 로드하는 패키지 (Loader는 Content Resolver로 구성)
  - Contact
  - Loader
- `util` : Persmission 체크를 위한 util로 구성된 패키지
  - PermissionUtil
- `NumberAdapter` : RecylerAdapter
- `BaseActivity` : 기본으로 실행되어야 하는 액티비티로 상속받아 사용됨
- `MainActivity` : MainActivity

  ### 0. 레이아웃 구성 및 Manifest에 존재하는 퍼미션
  - Layout 구성

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/ContactPractice/picture/contentResolver2.png)

  - Manifest에 추가

  ```xml
  <uses-permission android:name="android.permission.READ_CONTACTS"/>
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
  <uses-permission android:name="android.permission.CALL_PHONE"/>
  ```
  ### 1. domain (Contact / Loader)

  - Contact

  ```java
  public class Contact {
      private int id;
      private String number;
      private String name;

      public int getId() { return id; }
      public void setId(int id) { this.id = id; }
      public String getNumber() { return number; }
      public void setNumber(String number) { this.number = number; }
      public String getName() { return name; }
      public void setName(String name) { this.name = name; }
  }
  ```

  - Loader

  ```java
  public class Loader {
      Context context;
      public Loader(Context context){
          this.context = context;
      }
      public List<Contact> dataLoad(){
          List<Contact> data = new ArrayList<>();

          // 1. Content Resolver
          ContentResolver resolver = context.getContentResolver();
          // 2. 데이터 uri 정의
          Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
          // 3. 가져올 컬럼 정의
          String[] projection = {
                  ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                  ContactsContract.CommonDataKinds.Phone.NUMBER
          };
          // 4. 쿼리 결과 저장
          Cursor cursor = resolver.query(uri,projection,null,null,ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");
          // 5. cursor 반복처리
          if(cursor !=null){
              while(cursor.moveToNext()){
                  Contact contact = new Contact();
                  int index = cursor.getColumnIndex(projection[0]);
                  contact.setId(cursor.getInt(index));
                  index = cursor.getColumnIndex(projection[1]);
                  contact.setName(cursor.getString(index));
                  index = cursor.getColumnIndex(projection[2]);
                  contact.setNumber(cursor.getString(index));

                  data.add(contact);
              }
          }
          return data;
      }
  }
  ```  

  ### 2. util (PermissionUtil)
  - PermissionUtil (Callback 메소드로 init메소드 강제 실행)

  ```java
  public class PermissionUtil {
      private int per_code;
      private String[] perm;

      public PermissionUtil(){ }

      public PermissionUtil(Context context, int per_code, String[] permissions){
          this.per_code = per_code;
          this.perm = permissions;
      }

      // 승인 여부 확인 및 요청
      @TargetApi(Build.VERSION_CODES.M)
      private void customCheckPermission(Activity activity){
          List<String> requires = new ArrayList<>();
          for(String permisson : perm){
              if(activity.checkSelfPermission(permisson) != PackageManager.PERMISSION_GRANTED){
                  requires.add(permisson);
              }
          }
          // 승인이 안되어 있으면 승인요청, 되어 있으면 init을 callback으로 실행
          if(requires.size()>0){
              activity.requestPermissions(perm,per_code);
          } else{
              callInit(activity);
          }
      }

      public void checkVersion(Activity activity){
          if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
              customCheckPermission(activity);
          } else {
              callInit(activity);
          }
      }

      public void onResult(Activity activity, int requestCode, int grantResult[]){
          if(requestCode==per_code){
              boolean check = true;
              for(int grant : grantResult){
                  if (grant != PackageManager.PERMISSION_GRANTED) {
                      check = false;
                      break;
                  }
              }
              if(check){
                  callInit(activity);
              } else{
                  Toast.makeText(activity,"권한 승인이 필요합니다.",Toast.LENGTH_LONG).show();
                  activity.finish();
              }
          }
      }
      // Callback 메소드 구현
      public interface Callback{
          void callInit();
      }
      public static void callInit(Activity activity){
          ((Callback)activity).callInit();
      }
  }
  ```

  ### 3. NumberAdapter
  - 항상 데이터 변경을 알리는 `notifyDataSetChanged()` 메소드를 사용해야 한다.
  - 전화 거는 인텐트
    - String num = "tel:" + number; </br>
    Uri uri = Uri.parse(num); </br>
    Intent intent = new Intent("android.intent.action.CALL", uri); </br>
    startActivity(intent);

  ```java
  public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.Holder> {
      List<Contact> data = new ArrayList<>();

      public void setDataAndRefresh(List<Contact> data){
          this.data = data;
          // 데이터 변경을 알린다.
          notifyDataSetChanged();
      }
      @Override
      public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
          View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_number,parent,false);
          return new Holder(view);
      }
      @Override
      public void onBindViewHolder(Holder holder, int position) {
          Contact contact = data.get(position);
          holder.setNo(contact.getId());
          holder.setName(contact.getName());
          holder.setNumber(contact.getNumber());
      }
      @Override
      public int getItemCount() {
          return data.size();
      }
      class Holder extends RecyclerView.ViewHolder{
          private TextView textNo, textNumber, textName;
          private ImageView imagebtnCall;
          private String number;

          public Holder(final View itemView) {
              super(itemView);
              initView(itemView);

              // 전화 걸기
              imagebtnCall.setOnClickListener(new View.OnClickListener() {
                  @SuppressWarnings("MissingPermission")
                  @Override
                  public void onClick(View v) {
                      String num = "tel:" +number;
                      Uri uri = Uri.parse(num);
                      Intent intent = new Intent(Intent.ACTION_CALL,uri);
                      v.getContext().startActivity(intent);
                  }
              });
          }

          private void initView(View view){
              textNo = (TextView) view.findViewById(R.id.textNo);
              textNumber = (TextView) view.findViewById(R.id.textNumber);
              textName = (TextView) view.findViewById(R.id.textName);
              imagebtnCall = (ImageButton) view.findViewById(R.id.imagebtnCall);
          }
          public void setNo(int no){
              textNo.setText(no+"");
          }
          public void setNumber(String number){
              textNumber.setText(number);
              this.number = number;
          }
          public void setName(String name){
              textName.setText(name);
          }
      }
  }
  ```

  ### 4. BaseActivity

  ```java
  public abstract class BaseActivity extends AppCompatActivity implements PermissionUtil.Callback {

      private static final int REQ_CODE = 888;
      private static String[] permissions = {
              Manifest.permission.CALL_PHONE,
              Manifest.permission.READ_EXTERNAL_STORAGE,
              Manifest.permission.READ_CONTACTS
      };
      PermissionUtil permissionUtil;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          // 퍼미션 체크
          permissionUtil = new PermissionUtil(this, REQ_CODE, permissions);
          permissionUtil.checkVersion(this);
      }

      @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
          super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          permissionUtil.onResult(this,requestCode,grantResults);
      }
      // 콜백함수 이용
      public void callInit(){
          init();
      }
      public abstract void init();
  }
  ```

  ### 5. MainActivity

  ```java
  public class MainActivity extends BaseActivity {
      RecyclerView recyclerNumber;
      NumberAdapter adapter;

      @Override
      public void init(){
          setContentView(R.layout.activity_main);
          // RecylerView 연결
          recyclerNumber = (RecyclerView) findViewById(R.id.recyclerNumber);
          adapter = new NumberAdapter();
          recyclerNumber.setAdapter(adapter);
          recyclerNumber.setLayoutManager(new LinearLayoutManager(this));
          // Loader로 부터 데이터 받고 adaper에 넘김
          Loader loader = new Loader(this);
          List<Contact> data = loader.dataLoad();
          adapter.setDataAndRefresh(data);
      }
  }
  ```

---

## 참고 자료
#### [콘텐츠 제공자 기본 사항](https://developer.android.com/guide/topics/providers/content-provider-basics.html?hl=ko)
