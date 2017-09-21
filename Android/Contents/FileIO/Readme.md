# FileUtil
- 내부저장소 File을 읽고 쓰는 방법

---
## 들어가기 전에
### - __안드로이드에서의 파일 저장소__
1. 내부저장소(Internal) : 개별앱만 접근 가능, 파일탐색기에서 검색 불가능
</br> - 경로 : `data/data/패키지명/(files or database)`
2. 외부저장소(External) : 모든앱이 접근 가능 하지만 권한이 필요, sdcard로 되어 있음

### - __파일에 접근하는 방법__
1. 자바 API 이용
  - 경로지정이 필수 `file = File(경로+파일)`
> 예시
```java
// 경로 지정
private static String DIR_INTERNAL = "/data/data/com.example.kyung.androidmemo/files";
// 파일을 지정
File file = new File(DIR_INTERNAL+filename);
// 스트림을 쓴다.
FileOutputStream fos = new FileOutputStream(file);
```

2. 안드로이드 메소드 이용 : 경로를 알아서 찾아줌
```java
```

### - __파일 입출력 방법__
* FileOutputStream / FileInputStream 이용
* BufferedOutputStream / BufferedInputStream 이용 (위를 포함)

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
