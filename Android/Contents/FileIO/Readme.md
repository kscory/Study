# File I/O

---
## File I/O
스트림을 열고 -> 중간처리(버퍼) -> 스트림을 닫는다.
##### 참고사항 [file을 이용한 memo App](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Example/AndroidMemoFile)

### 1. __안드로이드에서의 파일 저장소__
- 내부저장소(Internal) : 개별앱만 접근 가능, 파일탐색기에서 검색 불가능
  - 경로 : `data/data/패키지명/(files or database)`
- 외부저장소(External) : 모든앱이 접근 가능 하지만 권한이 필요, sdcard로 되어 있음

### 2. __파일에 접근하는 방법__
자바 API 이용 / 안드로이드 메소드 이용
- 자바 API 이용
  - 다음과 같이 경로지정이 필수 `File file = File(경로+파일)`</br>

    ```java
    // 경로 지정
    private static String DIR_INTERNAL = "/data/data/com.example.kyung.androidmemo/files";
    // 파일을 지정
    File file = new File(DIR_INTERNAL+filename);
    // 스트림을 쓴다.
    FileOutputStream fos = new FileOutputStream(file);
    ```


- 안드로이드 메소드 이용 (Context에 존재하므로 Context를 받아야 사용 가능)
  - Output(File 쓰기) : `openFileOutput(파일명, 모드 이름)`
  - Input(File 읽기) : `openFileInput(파일명)`</br>

    ```java
    FileInputStream fis = context.openFileInput(filename);
    FileOutputStream fos = context.openFileOutput(filename, MODE_PRIVATE);
    ```

### 3. __파일 입출력 방법__
기본 / 버퍼 이용 O / 텍스트파일 입출력용 클래스</br>
##### 참고 : [바이너리 파일 입출력](http://recipes4dev.tistory.com/109), [안드로이드 텍스트파일 입출력](http://recipes4dev.tistory.com/113)

- 기본적인 파일 입출력
  - Output(File 쓰기) : `FileOutputStream`
  - Input(File 읽기) : `FileInputStream`

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/FileIO/picture/file1.png)


- 버퍼를 사용한 파일 입출력 _ 보통 버퍼를 이용하여 사용
  - Output(File 쓰기) :  `BufferedOutputStream`
  - Input(File 읽기) : `BufferedInputStream`

    ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/FileIO/picture/file2.png)

- 텍스트파일 입출력용 클래스
  - Output(File 쓰기) :  `FileWriter`
  - Input(File 읽기) : `FileReader`

    ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/FileIO/picture/file3.png)

### 4. __read 버퍼의 특이사항__
- `read(buffer)` 의 경우 읽은 값을 `buffer`에 저장하고 읽은 count의 값(최대1024바이트) 을 넘겨준다.
- 모두 읽은 경우 `-1`의 값을 넘겨준다.
- 아래에서 `new String(buffer,0,count)` 란 buffer를 0부터 count 까지 입력한다는 이야기.

  ```java
  StringBuilder sb = new StringBuilder();
  // 1. 스트림을 열고
  FileInputStream fis = context.openFileInput(filename);
  // 2. 버퍼를 달고
  BufferedInputStream bis = new BufferedInputStream(fis);
  // 한번에 읽어올 버퍼양을 설정
  byte buffer[] = new byte[1024];
  // 현재 읽은 양을 담는 변수설정
  int count=0;
  while( (count = bis.read(buffer)) !=-1 ) {
      String data = new String(buffer, 0, count);
      sb.append(data);
  }
  ```
---



## 소스 링크
1.
