package com.example.kyung.camera;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends BaseActivity {
    private static final int REQ_CAMERA = 222;
    private static final int REQ_GALLERY = 333;

    ImageView imageView;

    @Override
    public void init() {
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    // 저장된 파일의 경로를 가지는 컨텐츠 Uri
    Uri fileUri = null;
    public void goCamera(View view){
        // 카메라 앱 띄워서 결과 이미지 저장하기
        // 1. intent 만들기
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 2. 호환성 처리 버전체크 (롤리팝 이상부터 바뀜)
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP){
            try {
                // 파일은 공용으로 사용할 수 없다.(URI로만 사용)
                // 3. 실제 파일이 저장되는 파일 객체 < 빈 파일을 생성해 둔다.
                // 3.1. 실제 파일이 저장되는 곳에 권한이 부여되어 있어야 한다.
                //      롤리팝 부터는 File Provider를 선언해 줘야만 한다. > Manifest , xml에 설정
                File photoFile = createFile();
                // 갤러리에서 나오지 않을때 설정하는 메소드
                refreshMedia(photoFile);

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

    // 이미지를 저장하기 위해 쓰기 권한이 있는 빈 파일을 생성해두는 메소드
    private File createFile() throws IOException{
        // 임시파일명 생성
        String tempFileName = "Temp_"+System.currentTimeMillis();
        // 임시파일 저장용 디렉토리 설정
        File tempDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/CameraN");
        // 생성 체크
        if(!tempDir.exists()){
            tempDir.mkdirs();
        }
        // 임시파일 생성
        // 앱이 종료되고 나면 반드시 tempfile을 삭제해야 한다.=> 삭제를 조금 쉽게하기 위해서 만들어짐( newfile에서는 그 flag를 넣지 않는다.)
        File tempFile = File.createTempFile(
                tempFileName, // 파일명
                ".jpg",       // 확장자
                tempDir         // 저장될 디렉토리
        );

        //tempFile.deleteOnExit(); // 템프파일 삭제 (현재 안됨)

        return tempFile;
    }

    // 미디어 파일 갱신
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

    public void goGallery(View view){
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivityForResult(intent,REQ_GALLERY);
    }

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

            case REQ_GALLERY:
                if(resultCode==RESULT_OK){
                    String path = data.getStringExtra("imagePath");
                    imageUri = Uri.parse(path);
                    imageView.setImageURI(imageUri);
                }
                break;

        }
    }
}
