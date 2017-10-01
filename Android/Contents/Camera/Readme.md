# Cmaera & Gallery
- Camera 사용
- Custom Gallery 사용
---

## Camera
- 카메라는 이미지 저장을 하기 위해서 빈 파일을 미리 생성해둔다.
- 카메라 동작 방식

  </br>

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Camera/picture/camera.png)

  </br>

  ### 1. 카메라 이용 권한
  - 쓰기와 카메라 권한 필요

  > java에서 필요한 권한

  ```java
  Manifest.permission.CAMERA,
  Manifest.permission.WRITE_EXTERNAL_STORAGE
  ```

  > Manifest에 추가 필요

  ```xml
  <uses-permission android:name="android.permission.CAMERA"/>
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
  ```

  ### 2. 파일을 저장하기 위한 File Provider 설정 (외부저장소 사용을 위한 권한)
  - 롤리팝 버전 이상부터는 실제 파일이 저장되는 곳에 권한이 부여되어 있어야 한다.
  - Maifest , xml에 설정해준다.

  >xml/file_path

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <paths>
      <!-- name (논리 구조) : uri 주소체계의 prefix (ex>content://Camera.xxxx) -->
      <!-- path (물리 디스크경로) : /외부저장소의루트/CameraN 디렉토리를 사용 -->
      <external-path name = "Camera" path = "CameraN"></external-path>
  </paths>  
  ```

  >Manifest.xml

  ```xml
  <!-- ${그래들} 그래들의 변수명을 지원-->
  <provider
      android:authorities="${applicationId}.provider"
      android:name="android.support.v4.content.FileProvider"
      android:exported="false"
      android:grantUriPermissions="true">
      <!-- 프로바이더가 사용하는 파일의 경로-->
      <meta-data
          android:name="android.support.FILE_PROVIDER_PATHS"
          android:resource="@xml/file_path"
          />

  </provider>  
  ```

  ### 3. 이미지 저장을 위한 빈 파일 생성 메소드 / 미디어 파일 갱신 메소드
  - 앱을 종료하면 반드시 tempfile을 삭제해 주는 작업 진행 필요
  - createNewFile vs createTempFile
    - `createNewFile` : 새로운 파일을 생성
    - `createTempFile` : `tempFile.deleteOnExit()` 를 통해 삭제를 쉽게 할 수 있다. (임시파일용)
  - 갤러리에 찍힌 사진이 나오지 않을 경우 미디어 파일 갱신 메소드 사용

  > 이미지 저장을 위한 빈 파일 생성 메소드

  ```java
  private File createFile() throws IOException{
      // 임시파일명 생성
      String tempFileName = "Temp_"+System.currentTimeMillis();
      // 임시파일 저장용 디렉토리 설정
      File tempDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/CameraN");
      // 디렉토리 생성 체크
      if(!tempDir.exists()){
          tempDir.mkdirs();
      }
      // 임시파일 생성
      File tempFile = File.createTempFile(
              tempFileName, // 파일명
              ".jpg",       // 확장자
              tempDir         // 저장될 디렉토리
      );
      return tempFile;
  }  
  ```

  > 미디어 파일 갱신 메소드

  ```java
  private void refreshMedia(File file){
      String files[] = {file.getAbsolutePath()};
      MediaScannerConnection.scanFile(this,
              files,
              null,
              new MediaScannerConnection.OnScanCompletedListener() {
                  @Override
                  public void onScanCompleted(String path, Uri uri) {
                  }
              });
  }    
  ```

  ### 4. 카메라 실행 메소드 / 카메라로 찍은 후 결과
  - a. intent르 만든다
  - b. 롤리팝 이상 버전체크
  - c. 실제 파일이 저장되는 파일 객체 생성
  - d. 파일 Uri 생성 후 startActivity
  - e. 사진 찍은 뒤 롤리팝 버전 체크 후 imageView에 세팅

  > 카메라 실행 메소드

  ```java
  private static final int REQ_CAMERA = 222;
  Uri fileUri = null;

  public void goCamera(View view){
      // 1. intent 만들기
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      // 2. 호환성 처리 버전체크 (롤리팝 이상부터 바뀜)
      if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP){
          try {
              // 파일은 공용으로 사용할 수 없다.(URI로만 사용)
              // 3. 실제 파일이 저장되는 파일 객체 < 빈 파일을 생성해 둔다.
              File photoFile = createFile();
              // 갤러리에서 나오지 않을때 설정하는 메소드
              refreshMedia(photoFile);
              // 4. 파일 Uri 생성 후 startActivity
              // Gradle에서 app id를 받아올 때 "BuildConfig.APPLICATION_ID" 를 사용
              fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID+".provider",photoFile);
              intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
              startActivityForResult(intent,REQ_CAMERA);
          } catch (IOException e) {
              e.printStackTrace();
          }
      } else{
          startActivityForResult(intent,REQ_CAMERA);
      }
  }
  ```

  > 카메라 사진 찍은 후 결과

  ```java
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Uri imageUri = null;
      switch (requestCode){
          case REQ_CAMERA:
              if(resultCode==RESULT_OK){
                  // 롤리팝 버전 체크
                  if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                      if(data != null) {
                          imageUri = data.getData();
                      }
                  } else{
                      imageUri=fileUri;
                  }
                  imageView.setImageURI(imageUri);
              }
              break;
      }
  }
  ```

---

## Gallery (Custom Gallery)
- ContentResolver를 이용하여 데이터 호출
- Recycler View를 이용하여 Grid 형태로 갤러리 호출
- 갤러리 동작 방식

  </br>

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Camera/picture/gallery.png)

  </br>

  ### 1. 갤러리 이용 권한
  - 읽기 권한 필요

  > java에서 필요한 권한

  ```java
  Manifest.permission.READ_EXTERNAL_STORAGE
  ```

  > Manifest에 추가 필요

  ```xml
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
  ```

  ### 2. 갤러리 intent 및 결과 호출
  - imagepath를 받아 엑티비티에 뿌려준다.

  > MainActivity

  ```java
  private static final int REQ_GALLERY = 333;
  public void goGallery(View view){
      Intent intent = new Intent(this, GalleryActivity.class);
      startActivityForResult(intent,REQ_GALLERY);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Uri imageUri = null;
      switch (requestCode){
          case REQ_GALLERY:
              if(resultCode==RESULT_OK){
                  String path = data.getStringExtra("imagePath");
                  imageUri = Uri.parse(path);
                  imageView.setImageURI(imageUri);
              }
              break;
      }
  }
  ```

  ### 3. CustomGallery (View)
  - Content Resolver 이용
  - Thumbnails 이용

  > GalleryActivity

  ```java
  public class GalleryActivity extends AppCompatActivity {
      RecyclerView recyclerView;
      GalleryAdapter adapter;
      List<String> images;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_gallery);
          init();
      }

      private void init(){
          recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
          adapter = new GalleryAdapter(this);
          recyclerView.setAdapter(adapter);
          recyclerView.setLayoutManager(new GridLayoutManager(this,3));
          images = loadData();
          adapter.setData(images);
      }

      // ContentResolver를 이용하여 이미지 목록을 가져온다.
      private List<String> loadData(){
          List<String> data = new ArrayList<>();
          ContentResolver resolver = getContentResolver();
          Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
          String projection[] = {
                  MediaStore.Images.Thumbnails.DATA
          };
          Cursor cursor = resolver.query(uri,projection,null,null,null);
          if(cursor !=null){
              while(cursor.moveToNext()){
                  int index = cursor.getColumnIndex(projection[0]);
                  String path = cursor.getString(index);
                  data.add(path);
              }
          }
          return data;
      }
  }  
  ```

  ### 4. GalleryAdapter
  - 이미지 클릭시 이미지 경로를 던져준다.

  > GalleryAdapter

  ```java
  public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.Holder> {
      Context context;
      List<String> data;

      public GalleryAdapter(Context context){
          this.context = context;
      }

      public void setData(List<String> data){
          this.data = data;
          // 데이터가 변경되었다고 알려주어야 한다.
          notifyDataSetChanged();
      }

      @Override
      public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
          View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent,false);
          return new Holder(view);
      }

      @Override
      public void onBindViewHolder(Holder holder, int position) {
          String path = data.get(position);
          Uri uri = Uri.parse(path);
          holder.setImagePic(uri);

      }

      @Override
      public int getItemCount() {
          return data.size();
      }

      class Holder extends RecyclerView.ViewHolder{

          private ImageView imagePic;
          private TextView textDate;
          private Uri uri;

          public Holder(View itemView) {
              super(itemView);
              imagePic = (ImageView) itemView.findViewById(R.id.imagePic);
              textDate = (TextView) itemView.findViewById(R.id.textDate);

              imagePic.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent intent = new Intent();
                      intent.putExtra("imagePath", uri.getPath());
                      // 이미지 경로는 Thumbnail 이 아닌 원본 이미지의 경로를 넘겨야 한다
                      Activity activity = (Activity) context;
                      activity.setResult(Activity.RESULT_OK, intent);
                      activity.finish();
                  }
              });

          }
          public void setImagePic(Uri uri){
              this.uri = uri;
              imagePic.setImageURI(uri);
          }
          public void setTextDate(String datetime){
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
              textDate.setText(sdf.format(datetime));
          }
      }
  }  
  ```

---

## 참고 : Permission 및 BaseActivity
- Callback 이용

  ### 1. PermissionUtil
  - 퍼미션을 관리하는 유틸

  ```java
  public class PermissionUtil {
      private int per_code;
      private String[] permission;
      Callback callback;
      Activity activity;

      public PermissionUtil(){

      }

      public PermissionUtil(Activity activity, int per_code, String[] permission){
          this.per_code = per_code;
          this.permission = permission;
          this.activity = activity;
          if(activity instanceof Callback){
              callback = (Callback) activity;
          }
      }

      @TargetApi(Build.VERSION_CODES.M)
      public void requestCheckPermission(){
          List<String> reqPermission = new ArrayList<>();
          for(String perm : permission){
              if(activity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED){
                  reqPermission.add(perm);
              }
          }
          if(reqPermission.size()>0){
              activity.requestPermissions(permission,per_code);
          } else{
              callback.callinit();
          }
      }

      public void checkVersion(){
          if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
              requestCheckPermission();
          } else{
              callback.callinit();
          }
      }

      public void onResult(int requestCode, int grantResult[]){
          if(requestCode==per_code){
              boolean check=true;
              for(int choosing : grantResult){
                  if(choosing != PackageManager.PERMISSION_GRANTED){
                      check=false;
                      break;
                  }
              }
              if(check){
                  callback.callinit();
              } else{
                  Toast.makeText(activity,"권한 승인이 필요합니다.",Toast.LENGTH_LONG).show();
                  activity.finish();
              }
          }
      }

      public interface Callback{
          public void callinit();
      }
  }  
  ```

    ### 2. BaseActivity
    - 기본적으로 상속받는 activity

    ```java
    public abstract class BaseActivity extends AppCompatActivity implements PermissionUtil.Callback {


        private static final int REQ_CODE = 123;
        private static final String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        PermissionUtil permissionUtil;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            permissionUtil = new PermissionUtil(this, REQ_CODE, permissions);
            permissionUtil.checkVersion();
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            permissionUtil.onResult(requestCode,grantResults);
        }

        @Override
        public void callinit() {
            init();
        }

        public abstract void init();
    }
    ```
