# Activity
- Activity 생명주기와 startActivity 사용

---

##  Activity 생명주기

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Activity/picture/lifecycle.jpg)

### 1. __생명주기 메소드__

메소드 | 설명
     :----: | :----:
     onCreate() | 액티비티가 생성될 때 호출되며 사용자 인터페이스 초기화에 사용된다
     onRestart() | 액티비티가 멈췄다가 다시 시작되기 바로 전에 호출된다
     onStart() | 액티비티가 사용자에게 보여지기 바로 직전에 호출된다
     onResume() | 액티비티가 사용자와 상호작용하기 바로 전에 호출된다
     onPause() | 다른 액티비티가 보여질 때 호출된다.
     onStop() | 액티비티가 더이상 사용자에게 보여지지 않을 때 호출된다
     onDestroy() | 액티비티가 소멸될 때 호출된다.<br> finish() 메소드가 호출되거나 시스템이 메모리 확보를 위해 액티비티를 제거할 때 호출된다

### 2. __Activity 변화에 따른 호출 메소드__

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Activity/picture/lifecycle2.jpg)

---

##  startActivity
1. resource/drawble 디렉토리에 <shape> 라벨의 xml 생성
2. Layout에서 background에 ""@drawable/xml파일 이름" 적용

### 1. __dresource/drawble 디렉토리에 xml 생성__
> 예시

```java

```

---


---
## 참고문제
1. [file을 이용한 메모장 App](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Example/AndroidMemoFile)
