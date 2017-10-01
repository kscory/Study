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

</br>

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

## Gallery
- 카메라는 이미지 저장을 하기 위해서 빈 파일을 미리 생성해둔다.
- 카메라 동작 방식

  </br>

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Camera/picture/camera.png)

  </br>

  ### 1. 카메라 이용 권한
  - 쓰기와 카메라 권한 필요


## 소스 링크
1.
