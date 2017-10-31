# Firebase Basic 3
  - Firebase 프로젝트 스튜디오에서 생성방법
  - Signup / Signin (Authentication) 기능
  - Storage 기능

---

## Authentication 기능
  ### 1. firebase 프로젝트(Authentication) 생성 순서
  - Tool의 firebase 클릭 -> 오른쪽 탭에서 Authentication + email... 클릭 -> 순서대로 진행 (진행되면 녹색으로 바뀜)

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase3/picture/firbasecreate.png)

  - ① `connect to firebase` : 프로젝트 생성 및 app에 json 파일 넣는것까지 진행됨
  - ② `Authentication to your app` : 클릭시 그래들에 추가됨
  - ③ `Check current auth state` : 전역변수와 onCreate에 인증 state를 추가할 것 (써있는 것_하라는대로 하면 됨)
  - ④ `Sign up new users` : 아래와 같이 메소드를 만들어 Signup 모듈을 붙여넣는다.

  ```java
  public void signup(String email, String password){
      mAuth.createUserWithEmailAndPassword(email, password)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                          // Sign in success, update UI with the signed-in user's information
                          Log.d(TAG, "createUserWithEmail:success");
                          FirebaseUser user = mAuth.getCurrentUser();
                          updateUI(user);
                      } else {
                          // If sign in fails, display a message to the user.
                          Log.w(TAG, "createUserWithEmail:failure", task.getException());
                          Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                  Toast.LENGTH_SHORT).show();
                          updateUI(null);
                      }
                  }
              });
  }
  ```

  - ⑤ `Sign in existing users` : Signin 모듈을 붙여 넣는다. (Signup과 동일)

  - ⑥ `Access user information` : 사용자 정보를 가져오기 위한 것 (메소드를 만들어 붙여 넣는다.)

  ```java
  public void getUserInfo(){
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      if (user != null) {
          // Name, email address, and profile photo Url
          String name = user.getDisplayName();
          String email = user.getEmail();
          Uri photoUrl = user.getPhotoUrl();

          // 이메일 검증이 완료되었는지 확인
          boolean emailVerified = user.isEmailVerified();

          // The user's ID, unique to the Firebase project. Do NOT use this value to
          // authenticate with your backend server, if you have one. Use
          // FirebaseUser.getToken() instead.
          String uid = user.getUid();
      }
  }
  ```

  - ⑦ `Optional:Configure ProGuard` : project의 app에 있는 proguard rules에 붙여넣으며 이는 난독하지 말라고 선언함 (ProGuard란 코드를 볼 수 없게끔 난독해주는 것인데 특정 모듈은 난독화시키면 안되기 때문에 선언_ 여기서는 signature랑 annotation을 난독화시키지 않겠다는 것)

  - 그 후 프로젝트가 자동으로 생성되어 있게 되며 프로젝트/Authentication에 들어가서 sign-in method의 status를 바꾼다. (email은 쉽게 설정되지만 나머지는 직접 설정해야함, facebook이 설정하기 가장 까다로움)

  ### 2. MainActivity 에서의 sign-up / sign-in
  - [전체코드_MainActivity](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase3/app/src/main/java/com/example/kyung/basicfirebase3/MainActivity.java)
  - sign-up 메소드
    - 아이디 및 패스워드 체크를 한 후 이상이 없을 시 가입가능 ([참고_정규식](https://github.com/Lee-KyungSeok/Study/tree/master/Java/Contents/RegularExpression))
    - 아이디가 중복되거나 브라우저에서 email status를 안바꾼 경우 fail이 뜨니 조심할 것
    - 사용자를 생성하며 이메일 확인 후 이용가능하다.

  > signup 메소드 및 아이디/패스워드 체크

  ```java
  public void signup(View view) {
      // @ 하나, . 하나 필수, "영문" "숫자" "_" "-" 허용
      String email = editEmail.getText().toString();
      // 특수문자 하나 이상 "!, @, #, $, %, ^, &, *, (, )" 들어감 => 바꾸면 안됨
      // 영문, 숫자
      String password = editPassword.getText().toString();
      // validation check
      if(!isValidEmail(email) || !isValidPassword(password)){
          if(!isValidEmail(email)) {
              infoEmail.setText("이메일 형식이 잘못됬습니다");
              infoEmail.setVisibility(View.VISIBLE);
          }
          if(!isValidPassword(password)) {
              infoPassword.setText("비밀번호 형식이 잘못됬습니다");
              infoPassword.setVisibility(View.VISIBLE);
          }
          return;
      }

      // 파이어베이스의 인증모듈로 사용자를 생성
      mAuth.createUserWithEmailAndPassword(email, password)
              // 완료확인 리스너
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                          FirebaseUser user = mAuth.getCurrentUser();
                          // 이메일 유효성을 확인하기 위해 해당 이메일로 메일이 발송된다.
                          user.sendEmailVerification()
                                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                                      @Override
                                      public void onComplete(@NonNull Task<Void> task) {
                                        // 원래는 다음 엑티비티로 이동시킨다.
                                          Toast.makeText(MainActivity.this ,"이메일을 발송하였습니다. 확인해주세요", Toast.LENGTH_SHORT).show();
                                      }
                                  });
                      } else {
                          Toast.makeText(MainActivity.this, "Authentication failed.",
                                  Toast.LENGTH_SHORT).show();
                      }
                  }
              });
  }

  /**
   * Comment  : 정상적인 이메일 / 패스워드인지  인지 검증.
   */
  public static boolean isValidEmail(String email) {
      boolean err = false;
      String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(email);
      if(m.matches()) {
          err = true;
      }
      return err;
  }
  public static boolean isValidPassword(String password) {
      boolean err = false;
      // 영문자와 숫자만 허용, 8자리 이상
      String regex = "^[A-Za-z0-9]{8,}$";
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(password);
      if(m.matches()) {
          err = true;
      }
      return err;
  }
  ```

  - sign-in 메소드
    - 먼저 가입되어 있는지 확인하고 이메일이 검증되었는지 체크 후 엑티비티를 이동시킨다.

  > sign in 메소드

  ```java
  public void signin(View view){
      String email = signEmail.getText().toString();
      String password = signPassword.getText().toString();

      mAuth.signInWithEmailAndPassword(email, password)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                          FirebaseUser user = mAuth.getCurrentUser();

                          // 이메일 검증 확인
                          if(user.isEmailVerified()){
                              // 다음 페이지로 이동
                              Intent intent = new Intent(MainActivity.this, StorageActivity.class);
                              startActivity(intent);
                              finish();
                          }else{
                              Toast.makeText(MainActivity.this,"이메일을 확인하셔야 합니다.",Toast.LENGTH_SHORT).show();
                          }

                      } else {
                          Toast.makeText(MainActivity.this, "Authentication failed.",
                                  Toast.LENGTH_SHORT).show();
                      }
                  }
              });
  }
  ```

  ### 3. Layout 및 결과
  - 보통 Signup / Signin Layout에서는 아래에 아이디/비밀번호를 체크하는 text를 출력한다.
  - Layout 및 결과는 아래와 같다
  - signup 시에는 메일설정을 할 수 있으며 이를 체크한다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase3/picture/signin.png)

---

## Storage 기능
  ### 1. 시작하기
  - 위와 비슷하게 Tool > firebase > Storage > upload and download... > 순서대로 진행
  - load의 ex>

  ```java
  public void upload(){
      // 실제 파일이 있는 경로
      Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
      // 파이어베이스의 스토리지 파일 node
      StorageReference riversRef = storageRef.child("images/rivers.jpg");

      riversRef.putFile(file)
              .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      // Get a URL to the uploaded content
                      Uri downloadUrl = taskSnapshot.getDownloadUrl();
                  }
              })
              .addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception exception) {
                      // Handle unsuccessful uploads
                      // ...
                  }
              });
  }
  ```

  - Firebase 브라우저에서 파일확인하면 아래와 같이 나온다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase3/picture/image.png)

  ### 2. Storage Activity
  - 파일 탐색기를 호출하며 `startActivityForResult` 로 실행
  - 파일이 선택되면 `upload` 메소드를 호출
  - 성공시 파이어베이스에 업로드, 실패시 메세지를 날린다.
  - 파일을 업로드 하기 위해서는 보안 규칙을 설정해주어야 한다.(ex> 공개 만약 위처럼 이메일 인증시는 그냥 두어도 됨)

  > Storage Activity

  ```java
  public class StorageActivity extends AppCompatActivity {
      // 파일 저장소
      private StorageReference storageRef;


      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_storage);

          // 파이어베이스 연결
          storageRef = FirebaseStorage.getInstance().getReference();
      }

      // 파일 탐색기 호출
      public void chooseFile(View view){
          Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
          intent.setType("image/*"); // 갤러리 image/* , 동영상 video/* , 어플리케이션 application/*
          startActivityForResult(intent.createChooser(intent,"Select App"),999);
      }

      // 파일이 선택되면 호출
      @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          if(resultCode == RESULT_OK){
              Uri uri = data.getData();
              upload(uri);
          }
      }

      public void upload(Uri file){
          // 파이어베이스의 스토리지 파일node
          String temp[] = file.getPath().split("/"); // 파일 패스랑 이름이랑 다르므로,,, 패스는 다르게 해야함...
          String filename = temp[temp.length-1];
          StorageReference riversRef = storageRef.child("files/"+filename);

          riversRef.putFile(file)
                  .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          Uri downloadUrl = taskSnapshot.getDownloadUrl();
                          Log.e("Storage","downloadURL"+downloadUrl.getPath());
                      }
                  })
                  .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception exception) {
                          Toast.makeText(StorageActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                      }
                  });
      }

  }
  ```

  ### 3. 결과
  - 파일 업로드를 하면 다음과 같이 스토리지에 파일이 등록된다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase3/picture/fileupload.png)

---

## 참고
  ### 1. RealPathUtil
  - 출처 : [Get RealPath From Uri](http://gogorchg.tistory.com/entry/Android-Get-RealPath-from-Uri)
  - Uri 경로에서 해당 파일의 실제 경로를 가져 오는 소스 (안드로이드 버전별로 코드가 다르기 때문에 생각할 것)

  ```java
  public class RealPathUtil {

      // 버전별로 path를 가져옴
      public static String getRealPath(Context context, Uri uri) {
          String realPath;
          // SDK < API11
          if (Build.VERSION.SDK_INT < 11) {
              realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(context, uri);
          }

          // SDK >= 11 && SDK < 19
          else if (Build.VERSION.SDK_INT < 19) {
              realPath = RealPathUtil.getRealPathFromURI_API11to18(context, uri);
          }

          // SDK > 19 (Android 4.4)
          else {
              realPath = RealPathUtil.getRealPathFromURI_API19(context, uri);
          }

          return realPath;
      }
      // 파일마다 가져오기 위해서는 다양하게 지정
      @SuppressLint("NewApi")
      public static String getRealPathFromURI_API19(final Context context, final Uri uri) {

          // check here to KITKAT or new version
          final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

          // DocumentProvider
          if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

              // ExternalStorageProvider
              if (isExternalStorageDocument(uri)) {
                  final String docId = DocumentsContract.getDocumentId(uri);
                  final String[] split = docId.split(":");
                  final String type = split[0];

                  if ("primary".equalsIgnoreCase(type)) {
                      return Environment.getExternalStorageDirectory() + "/"
                              + split[1];
                  }
              }
              // DownloadsProvider
              else if (isDownloadsDocument(uri)) {

                  final String id = DocumentsContract.getDocumentId(uri);
                  final Uri contentUri = ContentUris.withAppendedId(
                          Uri.parse("content://downloads/public_downloads"),
                          Long.valueOf(id));

                  return getDataColumn(context, contentUri, null, null);
              }
              // MediaProvider
              else if (isMediaDocument(uri)) {
                  final String docId = DocumentsContract.getDocumentId(uri);
                  final String[] split = docId.split(":");
                  final String type = split[0];

                  Uri contentUri = null;
                  if ("image".equals(type)) {
                      contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                  } else if ("video".equals(type)) {
                      contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                  } else if ("audio".equals(type)) {
                      contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                  }

                  final String selection = "_id=?";
                  final String[] selectionArgs = new String[] { split[1] };

                  return getDataColumn(context, contentUri, selection,
                          selectionArgs);
              }
          }
          // MediaStore (and general)
          else if ("content".equalsIgnoreCase(uri.getScheme())) {

              // Return the remote address
              if (isGooglePhotosUri(uri))
                  return uri.getLastPathSegment();

              return getDataColumn(context, uri, null, null);
          }
          // File
          else if ("file".equalsIgnoreCase(uri.getScheme())) {
              return uri.getPath();
          }

          return null;
      }

      /**
       * Get the value of the data column for this Uri. This is useful for
       * MediaStore Uris, and other file-based ContentProviders.
       *
       * @param context
       *            The context.
       * @param uri
       *            The Uri to query.
       * @param selection
       *            (Optional) Filter used in the query.
       * @param selectionArgs
       *            (Optional) Selection arguments used in the query.
       * @return The value of the _data column, which is typically a file path.
       */
      public static String getDataColumn(Context context, Uri uri,
                                         String selection, String[] selectionArgs) {

          Cursor cursor = null;
          final String column = "_data";
          final String[] projection = { column };

          try {
              cursor = context.getContentResolver().query(uri, projection,
                      selection, selectionArgs, null);
              if (cursor != null && cursor.moveToFirst()) {
                  final int index = cursor.getColumnIndexOrThrow(column);
                  return cursor.getString(index);
              }
          } finally {
              if (cursor != null)
                  cursor.close();
          }
          return null;
      }

      /**
       * @param uri
       *            The Uri to check.
       * @return Whether the Uri authority is ExternalStorageProvider.
       */
      public static boolean isExternalStorageDocument(Uri uri) {
          return "com.android.externalstorage.documents".equals(uri
                  .getAuthority());
      }

      /**
       * @param uri
       *            The Uri to check.
       * @return Whether the Uri authority is DownloadsProvider.
       */
      public static boolean isDownloadsDocument(Uri uri) {
          return "com.android.providers.downloads.documents".equals(uri
                  .getAuthority());
      }

      /**
       * @param uri
       *            The Uri to check.
       * @return Whether the Uri authority is MediaProvider.
       */
      public static boolean isMediaDocument(Uri uri) {
          return "com.android.providers.media.documents".equals(uri
                  .getAuthority());
      }

      /**
       * @param uri
       *            The Uri to check.
       * @return Whether the Uri authority is Google Photos.
       */
      public static boolean isGooglePhotosUri(Uri uri) {
          return "com.google.android.apps.photos.content".equals(uri
                  .getAuthority());
      }

      @SuppressLint("NewApi")
      public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
          String[] proj = { MediaStore.Images.Media.DATA };
          String result = null;

          CursorLoader cursorLoader = new CursorLoader(
                  context,
                  contentUri, proj, null, null, null);
          Cursor cursor = cursorLoader.loadInBackground();

          // 11~18은 그냥 다 긁어옴
          if(cursor != null){
              int column_index =
                      cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
              cursor.moveToFirst();
              result = cursor.getString(column_index);
          }
          return result;
      }

      // 주소에 다 있지만 사용하는 것이 조금 다름
      // 위는 커서로더, 아래는 다르게..
      public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
          String[] proj = { MediaStore.Images.Media.DATA };
          Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
          int column_index
                  = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
          cursor.moveToFirst();
          return cursor.getString(column_index);
      }
  }
  ```
