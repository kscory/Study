# Activity (+ Intent)
- Activity 생명주기와 startActivity 사용 / Intent의 사용

---

##  Activity 생명주기

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Activity/picture/lifecycle.jpg)

### 1. 생명주기 메소드

  메소드 | 설명
  :----: | :----:
  onCreate() | 액티비티가 생성될 때 호출되며 사용자 인터페이스 초기화에 사용된다
  onRestart() | 액티비티가 멈췄다가 다시 시작되기 바로 전에 호출된다
  onStart() | 액티비티가 사용자에게 보여지기 바로 직전에 호출된다
  onResume() | 액티비티가 사용자와 상호작용하기 바로 전에 호출된다
  onPause() | 다른 액티비티가 보여질 때 호출된다.
  onStop() | 액티비티가 더이상 사용자에게 보여지지 않을 때 호출된다
  onDestroy() | 액티비티가 소멸될 때 호출된다.<br> finish() 메소드가 호출되거나 시스템이 메모리 확보를 위해 액티비티를 제거할 때 호출된다

### 2. Activity 변화에 따른 호출 메소드

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Activity/picture/lifecycle2.jpg)

---

##  Activity Result
#### [참고자료1](https://developer.android.com/training/basics/intents/result.html?hl=ko#ReceiveResult)
#### [참고자료2](http://developljy.tistory.com/16)
### 0. startActivity vs startActivityForResult
- `startActivity(intent)` : 단순히 다른 엑티비티로 이동하기 위한 용도로 사용

- `startActivityForResult(intent, 식별코드(번호표))` : 실행시킨 Activity 의 결과를 받아 이를 통해 다양한 작업의 실행이 가능

### 1. BaseActivity의 코드
시작 : `startActivityForResult` / 결과값 수신 : `onActivityResult`

- `startActivityForResult` : 다른 엑티비티로 이동시키 메소드

- `onActivityResult(int requestCode, int resultCode, Intent data)` : 후속 액티비티 작업을 마치고 돌아온 후 호출되는 메소드
  - `requestCode` : 후속 Activity의 작업 결과를 식별하는 코드로 보통 내장되어 있는 상수를 사용하며 정의 가능
    - `RESULT_OK` : 작업이 성공한 경우 (int로 -1)
    - `RESULT_CANCELED` : 작업이 실패한 경우 (int로 0)
    - 그 외 식별코드 참고 : [참고자료](https://developer.android.com/reference/android/app/Activity.html?hl=ko#RESULT_CANCELED)
  - `resultCode` : 후속 Activity의 식별코드로 startActivityForResult에서 던져준 번호표
  - `Intent data` : 후속 Activity에서 던져준 intent (결과값)

```java
// startActivityForResult
int WRITE_ACTIVITy=12345;
Intent intent = new Intent(ListActivity.this, WriteActivity.class);
startActivityForResult(intent, WRITE_ACTIVITy);

// onActivityResult
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode){
        case WRITE_ACTIVITy:
            if(resultCode == RESULT_OK){
                data.getStringIntent("resultValue")
                init(); // 새로 시작하라
            }
            if(resultCode == RESULT_CANCELED){
              // to do Noting
            }
            break;
        case 1111:
            /* 코드 */
    }
}
```

### 2. 호출된 Activity의 코드
- Intent를 이용해 결과값 저장 (putExtra)
- `setResult(결과,intent)` 를 이용해 성공여부를 날려준다. (intent 생략하고 결과여부만 전송 가능)

```java
Intent intent = getIntent().putExtra("resultValue",83777);
setResult(RESULT_OK,intent);
```
---
##  참고자료(Intent의 사용)

### 1. Intent의 생성
```java
// 1. 새로 Intent를 생성
Intent intent = new Intent(ListActivity.this,WriteActivity.class);
// 2. intent를 받아올때 생성
Intent intent = getIntent();
```

### 2. putExtra
- `putExtra("넘기는 값에 대한 tag", 넘기는 값);`
```java
// 1. 받아온 Intent를 활용
Intent intent = getIntent().putExtra("resultValue",83777);
// 2. Intent를 새로 생성하여 전달
Intent intent = new Intent(NewActivity.this,MainActivity.class)
intent.putExtra("title",textTitle.getText());
```

### 3. getExtra
- `String`의 경우 null이 자동 입력되지만</br> `기본형(exInt)` 경우 null이 입력되지 않으므로 초기값을 반드시 설정해 주어야 한다.
```java
// 1. String의 경우
String aaa = data.getStringExtra("value");
// 2. 기본형의 경우
int value = data.getIntExtra("valueResult",0);
```

---
## 참고문제
1. [file을 이용한 메모장 App](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Example/AndroidMemoFile)
