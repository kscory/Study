# 계산기 만들기
- Layout
- 버튼 디자인
- 버튼 클릭 효과
- 애니메이션 적용
- 계산기 Logic(정규식 적용)
- 계산기 예외처리
---
## __문제__
아래와 같이 작동하는 계산기 App 만들기 </br>
![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Example/BasicCalculator/picture/calculator.gif)

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
>예시 (android:background="@drawable/buttonexample")

```xml
<Button
    android:id="@+id/btnLeftParentheses"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_weight="1"
    android:background="@drawable/buttonexample"
    android:shadowColor="#A8A8A8"
    android:shadowDx="0"
    android:shadowDy="0"
    android:shadowRadius="5"
    android:text="("
    android:textColor="#D8D7DE"
    android:textSize="13sp" />
```
---

## 버튼 클릭 효과
selector를 이용해 버튼이 클릭되었을때, 안되었을때의 배경을 다르게 넣을 수 있다.
1. resource/drawble 디렉토리에 <selector> 라벨의 xml 생성
2. Layout에서 background에 ""@drawable/xml파일 이름" 적용

### 1. __resource/drawble 디렉토리에 <selector> 라벨의 xml 생성__
> 예시

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:state_pressed="false"
        android:drawable="@drawable/buttonexample"
        />
    <item
        android:state_pressed="true"
        android:drawable="@drawable/button_example_press"
        />
</selector>
<!--
android:state_enabled = 현재 사용 가능 상태일 경우 (유무)
android:state_selected = 현재 선택된 경우 (유무)
android:state_pressed = 현재 클릭이 된 경우 (유무)
android:state_focused = 현재 포커스를 가진 경우 (유무)
android:state_selected = 현재 선택된 경우 (유무)
android:state_checked = 현재 체크된 경우 (유무)
-->
```
### 2. __Layout에서 적용__
>예시 (android:background="@drawable/buttonselect")

```xml
<Button
    android:id="@+id/btnLeftParentheses"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_weight="1"
    android:background="@drawable/buttonselect"
    android:shadowColor="#A8A8A8"
    android:shadowDx="0"
    android:shadowDy="0"
    android:shadowRadius="5"
    android:text="("
    android:textColor="#D8D7DE"
    android:textSize="13sp" />
```
---
## 애니메이션 적용
dummy textview를 생성하여 Previewtext에 랜덤한 방향으로 날린다.
1. original view를 생성하고 dummy를 생성
2. dummy 속성값 세팅
</br> - 좌표값 : LinearLayout이 두개이므로 이 좌표값을 더해준다.
</br> - 나머지 : orignal 속성 적용 (LinearLayout은 match_parent가 마지막에 적용되는것을 감안할 것)
3. 버튼이 날아갈 지점을 정의하고 애니메이션 설정
4. 종료시 실행할 애니메이션을 설정하기 위하여 리스너를 연결
5. 애니메이션 시작

#### ※ [애니메이션 참고 자료_Animation2](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/Animation2)

```java
public void buttonEffect(View view, String value){
    // 넘겨받은 view 가 Button 인지 체크
    if (view instanceof Button) {
        // 오리지날 뷰를 생성
        Button original = (Button) view;
        // dummy를 상위 레이아웃에 생성
        final TextView dummy = new TextView(this);

        // original 위치 파악 후 dummy에 세팅
        // Layout이 총 3개 이므로 두명의 부모 Layout으로 부터 초기 좌표를 받아서 해결
        LinearLayout parent1 = (LinearLayout) original.getParent();
        LinearLayout parent2 = (LinearLayout) parent1.getParent();
        float basiclocationX = parent2.getX() + parent1.getX() + original.getX();
        float basiclocationY = parent2.getY() + parent1.getY() + original.getY();
        // 세팅
        dummy.setX(basiclocationX);
        dummy.setY(basiclocationY);

        // original의 속성값 적용
        dummy.setText(original.getText().toString());
        dummy.setWidth(original.getWidth());
        dummy.setHeight(original.getHeight());
        dummy.setTextSize(original.getTextSize());
        dummy.setGravity(original.getGravity());
        dummy.setTextColor(Color.GRAY);
        // LinearLayout 의 크기는 matchparent를 마지막에 받기 때문에 이를 줄여준다.
        LinearLayout.LayoutParams size = new LinearLayout.LayoutParams(original.getWidth(),original.getHeight());
        dummy.setLayoutParams(size);

        // dummy를 상위 레이아웃에 담는다.
        stage.addView(dummy);

        // 목표지점을 선택한다.(X좌표 랜덤 선택)
        Random random = new Random();
        float goalX = random.nextInt(textViewPreView.getWidth());
        float goalY = textViewPreView.getY();

        // 애니메이션을 만든다.
        ObjectAnimator aniX = ObjectAnimator.ofFloat(dummy, "x", goalX);
        ObjectAnimator aniY = ObjectAnimator.ofFloat(dummy, "y", goalY);
        ObjectAnimator aniR = ObjectAnimator.ofFloat(dummy, "rotation", 0F, 360F);
        AnimatorSet aniSet = new AnimatorSet();
        aniSet.playTogether(aniX,aniY,aniR);
        aniSet.setDuration(1000);
        aniSet.setInterpolator(new AccelerateInterpolator());
        // 애니메이션 종료를 위한 리스너 사라지면 버튼 입력
        aniSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            // 종료
            @Override
            public void onAnimationEnd(Animator animation) {
                stage.removeView(dummy);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        // 애니메이션 시작
        aniSet.start();
    }

}
```
---
## 계산기 Logic(정규식 적용)
문자열을 분리한 후 괄호 안의 계산식을 다시 메소드에 적용하는 재귀함수를 이용하여 먼저 괄호 안의 로직을 시행시키면서 하나의 문자열이 남을때까지 로직을 반복한다.

1. 정규식을 이용하여 ( "+", "-", "\*", "/", "(", ")" ) 를분리
</br> ※ 단 split 할때 처음에 문자가 분리될 문자가 오는경우 0번 인덱스에는 null이 삽입되므로 주의
2. 분리된 문자열을 List에 담는다.
3. 첫번째 문자가 "(" 인 경우 null 을 제거한다.)
4. 무한루프를 이용하여 로직을 반복
5. 괄호가 존재하면 먼저 계산
</br> - 첫번째 ")" 과 마지막"(" 를 찾아 안을 계산
</br> - 괄호 안의 문자들을 계산 로직에 반복 적용(재귀함수)
</br> - 결과값을 적용한 후 나머지 숫자 및 문자를 제거
6. \*와 / 를 먼저 계산하는데 check를 이용하여 있으면 먼저 반복 계산
7. +와 - 는 나중에 계산
8. 계산한 뒤 계산결과를 제외하고 모두 제거
9. List에 하나의 결과값이 남았다면 무한루프 break 후 결과값 출력

```java
private double calc(String preViewText){
    double result;
    // 정규식 처리
    String reg = "(?<=[*/+-])|(?=[*/+-])|(?<=[\\(\\)])|(?=[\\(\\)])";
    // split 할때 0번째 인덱스에서 잘릴 경우 맨 앞은 "null" 값이 들어가므로 주의할 것(여기서는 처음에 "(" 가 나올 경우)
    String splittedText[] = preViewText.split(reg);

    // 문자열 리스트에 담기
    ArrayList<String> tempCalculation = new ArrayList<>();
    for(String item : splittedText){
        tempCalculation.add(item);
    }

    // null 제거
    if(tempCalculation.get(0).equals("")){
        tempCalculation.remove(0);
    }

    // 무한 루프 반복문
    while(true){

        if(preViewText.equals("")) {break;}
        boolean checkMDOperation=false;//* 과 / 있는지 check
        int leftParenthesis=0; // ( 위치
        int rightParenthesis=0; // ) 위치
        String tempRec =""; // 괄호 안 계산식

        // 괄호 안 먼저 계산
        for(int i=0; i<tempCalculation.size() ; i++) {
            if(tempCalculation.get(i).equals(")")) {
                rightParenthesis=i;
                break;
            }
        }
        for(int i=rightParenthesis; i>=0 ; i--) {
            if(tempCalculation.get(i).equals("(")) {
                leftParenthesis=i;
                break;
            }
        }
        for(int i=leftParenthesis+1 ; i<rightParenthesis ;i++) {
            tempRec=tempRec+tempCalculation.get(i);
        }
        // 괄호안에 숫자가 존재할 경우 재귀함수를 호출한 뒤 하나의 문자열을 제외하고 제거
        if(!tempRec.equals("")) {
            double calParenthesis = calc(tempRec);
            tempCalculation.set(leftParenthesis,calParenthesis+"");
            for(int i =0 ; i<rightParenthesis-leftParenthesis ; i++) {
                tempCalculation.remove(leftParenthesis+1);
            }
           for(int i=0; i<tempCalculation.size(); i++){
           }
            continue;
        }

        // *와 / 를 계산하기 위해 check
        for(int i=0 ; i<tempCalculation.size() ; i++) {
            if(tempCalculation.get(i).equals("*") || tempCalculation.get(i).equals("/")){
                checkMDOperation=true;
            }
        }
        //*와 / 먼저 계산 하고 결과값을 제외하고 나머지 문자 제거
        for(int i=0 ; i<tempCalculation.size() ; i++){
            double tempResult=0;
            if(tempCalculation.get(i).equals("*") || tempCalculation.get(i).equals("/")){
                if(tempCalculation.get(i).equals("*")) {
                    tempResult = Double.parseDouble(tempCalculation.get(i-1)) * Double.parseDouble(tempCalculation.get(i+1));
                } else{
                    tempResult = Double.parseDouble(tempCalculation.get(i-1)) / Double.parseDouble(tempCalculation.get(i+1));
                }
                tempCalculation.set(i,tempResult+"");
                tempCalculation.remove(i-1);
                tempCalculation.remove(i);
                break;
            }

            // -와 +를 (*및 /가 없다면) 계산
            if(checkMDOperation == false && (tempCalculation.get(i).equals("+") || tempCalculation.get(i).equals("-"))){
                if(tempCalculation.get(i).equals("+")) {
                    tempResult = Double.parseDouble(tempCalculation.get(i - 1)) + Double.parseDouble(tempCalculation.get(i+1));
                } else{
                    tempResult = Double.parseDouble(tempCalculation.get(i - 1)) - Double.parseDouble(tempCalculation.get(i+1));
                }
                tempCalculation.set(i,tempResult+"");
                tempCalculation.remove(i-1);
                tempCalculation.remove(i);
                break;
            }
        }

        //List에 결과값 한개만 존재한다면 루프 종료
        if(tempCalculation.size()==1){
            break;
        }
    }
    result = Double.parseDouble(tempCalculation.get(0));
    return result;
}
```
---
## 계산기 예외처리 및 문자열 등록
> 들어가기 전 변수 설정

```java
String preViewText=""; // preViewText 내용
String resultViewText=""; // Result
String preInput=""; // 이전에입력된 문자
String preBeforeInput=""; // 두번째 전에 입력된 문자
int leftParenthesesCount=0; // 왼쪽 괄호 갯수
int rightParenthesesCount=0; // 오른쪽 괄호 갯수
boolean checkedDot=false; // dot이 입력되었는지 확인
```

> 예외처리 및 입력

```java
public void inputPreView(String value){

    //연산자가 처음에 오면 오류
    if(textViewPreView.getText().toString().equals("입력창") || preInput.equals("=")) {
        if (value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/")) {
            Toast.makeText(this, "0 보다 큰 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        preViewText="";
    }

    // 0이 처음에 연속해서 두번 나올 경우
    if(preViewText.equals("0") && value.equals("0")){
        Toast.makeText(this,"잘못된 입력 형식입니다.",Toast.LENGTH_SHORT).show();
        return;
    }

    // 연산자 + 0 + 0 인 경우
    if(value.equals("0") && preInput.equals("0")){
        if(preBeforeInput.equals("+") || preBeforeInput.equals("-") || preBeforeInput.equals("*") || preBeforeInput.equals("/")
                || preBeforeInput.equals("(") || preBeforeInput.equals(")")){
            Toast.makeText(this, "잘못된 입력 형식입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    // 연산자 + 0 + 숫자 인 경우
    if(preInput.equals("0") && (value.equals("0") || value.equals("1") || value.equals("2") || value.equals("3") || value.equals("4") || value.equals("5")
            || value.equals("6") || value.equals("7") || value.equals("8") || value.equals("9"))){
        if(preBeforeInput.equals("+") || preBeforeInput.equals("-") || preBeforeInput.equals("*") || preBeforeInput.equals("/")
                || preBeforeInput.equals("=") || preBeforeInput.equals("")|| preBeforeInput.equals("(")|| preBeforeInput.equals(")")){
            Toast.makeText(this, "잘못된 입력 형식입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    // dot + 연산자
    if(preInput.equals(".") && (value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/"))){
        Toast.makeText(this,"잘못된 입력형식입니다.",Toast.LENGTH_SHORT).show();
        return;
    }

    //연속된 연산자 오류
    if(preInput.equals("+") || preInput.equals("-") || preInput.equals("*") || preInput.equals("/")){
        if(value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/")){
            Toast.makeText(this, "연속해서 연산자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    // dot을 연속해서 쓰는 경우
    if(value.equals(".") && preInput.equals(".")){
        Toast.makeText(this, "연속해서 dot을 입력할 수 없습니다.", Toast.LENGTH_SHORT).show();
        return;
    }

    // 숫자 뒤에 괄호가 나올 경우 자동 곱하기 계산
    if(preInput.equals("0") || preInput.equals("1") || preInput.equals("2") || preInput.equals("3") || preInput.equals("4") || preInput.equals("5")
            || preInput.equals("6") || preInput.equals("7") || preInput.equals("8") || preInput.equals("9")){
        if(value.equals("(")){
            preViewText=preViewText+"*";
        }
    }

    // 닫는 괄호 뒤에 바로 숫자가 나올 경우 자동 곱하기 계산
    if(value.equals("0") || value.equals("1") || value.equals("2") || value.equals("3") || value.equals("4") || value.equals("5")
            || value.equals("6") || value.equals("7") || value.equals("8") || value.equals("9")){
        if(preInput.equals(")")){
            preViewText=preViewText+"*";
        }
    }

    // 여는 괄호 뒤에 바로 닫는 괄호가 나올 경우 오류
    if(preInput.equals("(") && value.equals(")")){
        Toast.makeText(this, "잘못된 형식입니다..",Toast.LENGTH_SHORT).show();
        return;
    }

    // 연산자 뒤에 괄호를 닫을 시 오류
    if(preInput.equals("+") || preInput.equals("-") || preInput.equals("*") || preInput.equals("/")){
        if(value.equals(")")){
            Toast.makeText(this, "괄호를 닫을 수 없습니다.",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    // 괄호를 연 뒤 바로 연산자가 나올경우 오류
    if(preInput.equals("(")){
        if(value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/")){
            Toast.makeText(this, "괄호 다음에 연산자가 나올 수 없습니다.",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //결과 다음 입력시 preView 초기화
    if(preInput.equals("=")){
        preViewText="";
        resultViewText="결과창";
        textViewResultView.setText(resultViewText);
    }

    // 처음 숫자가 dot인 경우 0을 써줌
    if((preInput.equals("+") || preInput.equals("-") || preInput.equals("*") || preInput.equals("/")
            || preInput.equals("=") || textViewPreView.getText().toString().equals("입력창"))
            && value.equals(".")){
        preViewText=preViewText+"0";
    }
    // dot 다음 괄호 여닫기
    if(preInput.equals(".") && (value.equals(")") || value.equals("("))){
        Toast.makeText(this,"잘못된 입력입니다.",Toast.LENGTH_SHORT).show();
        return;
    }

    // 괄호 다음 dot 입력
    if(preInput.equals("(") && value.equals(".")){
        preViewText=preViewText+"0";
    }
    if(preInput.equals(")") && value.equals(".")){
        preViewText=preViewText+"*0";
    }

    //연속 괄호
    if(preInput.equals("(") && value.equals(")")){
        Toast.makeText(this,"괄호안에 숫자를 입력해주세요.",Toast.LENGTH_SHORT).show();
        return;
    }

    //괄호 입력시 카운트 추가하여 왼쪽
    if(value.equals(")")){
        if(leftParenthesesCount<=rightParenthesesCount){
            Toast.makeText(this,"왼쪽 괄호를 먼저 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        }
    }
    if(value.equals("(")){
        leftParenthesesCount++;
    }
    if(value.equals(")")){
        rightParenthesesCount++;
    }

    // dot + 숫자 + dot
    if(checkedDot==true && value.equals(".")){
        Toast.makeText(this,"숫자를 입력해주세요",Toast.LENGTH_SHORT).show();
        return;
    }
    // dot이 눌리면 check는 true로
    if(value.equals(".")){
        checkedDot=true;
    }
    if(value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/") || value.equals("(") || value.equals(")")){
        checkedDot=false;
    }

    preViewText=preViewText+value;
    preBeforeInput=preInput;
    preInput=value;
    textViewPreView.setText(preViewText);
}

// "=" 이 입력되면 로직을 실행
public void inputResultView(String total){
    if(preViewText.equals("입력창")){
        return;
    }

    // 결과 요청 직전에 연산자, dot이면 오류처리
    if(preInput.equals("+") || preInput.equals("-") || preInput.equals("*") || preInput.equals("/") || preInput.equals("=") || preInput.equals(".")){
        Toast.makeText(this,"잘못된 입력 형식입니다.",Toast.LENGTH_SHORT).show();
        return;
    }
    // 결과 요청 시 왼쪽,오른쪽 괄호 개수가 다르다면 오류처리
    if(leftParenthesesCount!=rightParenthesesCount){
        Toast.makeText(this,"괄호개수를 확인해주세요",Toast.LENGTH_SHORT).show();
        return;
    }

    // 오류가 없으면 계산 시작
    double result = calc(total);

    leftParenthesesCount=0;
    rightParenthesesCount=0;
    checkedDot=false;
    textViewResultView.setText(result+"");
    preBeforeInput=preInput;
    preInput="=";
}
```
---


## 소스 링크
1.  [Layout](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Example/BasicCalculator/app/src/main/res/layout/activity_caculator.xml)
2. [design 폴더](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Example/BasicCalculator/app/src/main/res/drawable)
3. [로직/애니메이션 등 소스코드](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Example/BasicCalculator/app/src/main/java/com/example/kyung/basiccalculator/CaculatorActivity.java)
