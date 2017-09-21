# FileUtil
- 내부저장소 File을 읽고 쓰는 방법

---
## 들어가기 전에
### 1. __안드로이드에서의 파일 저장소__
- 내부저장소(Internal) : 개별앱만 접근 가능, 파일탐색기에서 검색 불가능
  - 경로 : `data/data/패키지명/(files or database)`
- 외부저장소(External) : 모든앱이 접근 가능 하지만 권한이 필요, sdcard로 되어 있음

### 2. __파일에 접근하는 방법__
자바 API 이용 / 안드로이드 메소드 이용
- 자바 API 이용
  - 다음과 같이 경로지정이 필수 `file = File(경로+파일)`
  - 예시>
```java
// 경로 지정
private static String DIR_INTERNAL = "/data/data/com.example.kyung.androidmemo/files";
// 파일을 지정
File file = new File(DIR_INTERNAL+filename);
// 스트림을 쓴다.
FileOutputStream fos = new FileOutputStream(file);
```

- 안드로이드 메소드 이용 (Context에서 사용 가능)
  - Input(File 읽기) : `openFileInput(파일명)`
  - Output(File 쓰기)`openFileOutput(파일명, 모드 이름)`
  - 예시>
```java
FileInputStream fis = context.openFileInput(filename);
FileOutputStream fos = context.openFileOutput(filename, MODE_PRIVATE);
```

### 3. __파일 입출력 방법__
버퍼 이용 X / 버퍼 이용 O / 텍스트파일 입출력용 클래스
 - 버퍼를 이용한
  - FileOutputStream
  - FileInputStream
 - 버퍼를 이용한
  - BufferedOutputStream
  - BufferedInputStream 이용 (위를 포함)
 -

---

## File 쓰기
자바 API를 이용하는 방법 / 안드로이드 자체 메소드 이용

### 1. __자바 API를 이용__
> 예시

```xml

```
### 2. __Layout에서 적용__


## 소스 링크
1.
