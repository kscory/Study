# 계산기 만들기
- Layout
- 버튼 디자인
- 버튼 클릭 효과
- 애니메이션 적용
- 계산기 Logic(정규식 적용)
- 계산기 예외처리
---

## Layout
아래와 같은 레이아웃 설정</br>
![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Example/BasicCalculator/picture/calculatorlayout.png)

---

## 버튼 디자인
1. resource/drawble 디렉토리에 <shape> 라벨의 xml 생성
2. Layout에서 background에 ""@drawable/xml파일 이름" 적용
#### ※ [버튼 디자인 참고 사이트](http://angrytools.com/android/button/)

### 1. __dresource/drawble 디렉토리에 xml 생성__
> 예시

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="rectangle" >
    <corners
        android:radius="0dp" // 모서리 굴곡 결정
        />
    <gradient
        android:angle="270"
        android:centerX="54%"
        android:centerColor="#252526"
        android:startColor="#665F65"
        android:endColor="#3E3E40"
        android:type="linear"
        />
    <padding
        android:left="2dp"
        android:top="2dp"
        android:right="2dp"
        android:bottom="2dp"
        />
    <size
        android:width="270dp"
        android:height="60dp"
        />
    <stroke
        android:width="1dp"
        android:color="#090116"
        />
</shape>
```
### 2. __Layout에서 적용__


## 소스 링크
1.
