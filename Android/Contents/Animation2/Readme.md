# Animation 활용
- Animation / dummy Button / 넓이,높이, 좌표값 계산</br>
- [Animation 참고 자료](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/Animation)

(ex> 아래와 같이 버튼 클릭시 목표지점으로 이동 표현)</br>
![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Animation2/picture/Button.gif)


## Layout
아래와 같은 레이아웃 설정</br>
![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Animation2/picture/layout.png)

## Code
- 모든 Layout은 addView를 이용해 위젯을 추가시킬 수 있다.
- dummy 위젯을 생성하여 애니메이션을 이용
- [전체코드 보기](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Animation2/BasicLayoutReview/app/src/main/java/com/example/kyung/basiclayoutreview/MainActivity.java)

### 1. __dummy 생성__
- LinearLayout 안에 버튼이 설정되어 있어 original 로 버튼을 이동시킬 경우 LinearLayout의 범위 안에서만 이동이 가능하므로 dummy button을 생성해서 이동
- <사용 메소드>
</br> - getX : 위젯의 X좌표를 받아온다. / getY : 위젯의 Y좌표를 받아온다.
</br> - getWidth : 위젯의 넓이를 받아온다 / getHeight : 위젯의 높이를 받아온다.
- <★(중요) 좌표 받아오기>
</br> - original 버튼의 위치값은 LinearLayout에서의 좌표값을 받아오므로, 부모 레이아웃의 좌표값에 original 버튼의 좌표값을 더해 좌표를 계산한다.

> onClick 메소드

```java
// view 변수가 Button 클래스의 인스턴스인지를 체크
if (view instanceof Button) {
    Button original = (Button) view;

    float goalY = btnGoal.getY();
    float goalX = btnGoal.getX();

    // 더미를 생성해서 상위 레이아웃에 담은 후 처리
    final Button dummy = new Button(this);
    // 생성된 더미에 클릭한 버튼의 속성값을 적용
    dummy.setText(original.getText().toString());
    dummy.setWidth(original.getWidth());
    dummy.setHeight(original.getHeight());
    dummy.setBackgroundColor(Color.RED);

    // 부모 레이아웃을 가져와서 원래 클래스로 캐스팅
    LinearLayout parent = (LinearLayout) original.getParent();

    // 부모 레이아웃의 위치값과 원래 버튼의 위치값을 더해서 좌표 설정
    dummy.setX(original.getX() + parent.getX());
    dummy.setY(original.getY() + parent.getY());

    // 더미를 상위 레이아웃에 추가
    stage.addView(dummy);
```

### 2. __animation 생성__
- 애니메이션 설정은 이전과 동일
- ※ 애니메이션 종료를 하기 위해서는 리스너를 달아 끝날 때 효과를 줄 수 있다.

```java
ObjectAnimator aniY = ObjectAnimator.ofFloat(
        dummy, "y", goalY
);
ObjectAnimator aniX = ObjectAnimator.ofFloat(
        dummy, "X", goalX
);
ObjectAnimator aniR = ObjectAnimator.ofFloat(
        dummy, "rotation", 720
);

AnimatorSet aniSet = new AnimatorSet();
aniSet.playTogether(aniX,aniY,aniR);
aniSet.setDuration(3000);
aniSet.setInterpolator(new AccelerateInterpolator());

// 애니메이션 종료를 체크하기 위한 리스너를 달아준다.
aniSet.addListener(new Animator.AnimatorListener() {
    @Override
    public void onAnimationStart(Animator animation) {
    }
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

aniSet.start();
```

## 참고 사항

### LinearLayout 에서 버튼의 겹침 문제
 - 버튼을 새로 생성하여 적용하면 아래와 같이 좌표값이 변경된다.</br>
 ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Animation2/picture/layout2.png)
 - 또한 LinearLayout은 match-parent 가 마지막에 적용되므로 layoutParams을 통해 강제로 넓이와 높이를 정해줄 수 있다.

```java
// Layout 가져오기
stage = (LinearLayout) findViewById(R.id.stage);
// 버튼에 커스텀 레아아웃 적용
LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
// 버튼을 코드로 생성
Button player = new Button(this);
// 넓이, 높이, text 설정
player.setWidth(100);
player.setHeight(100);
player.setText("P");
// 좌표를 컨트롤
player.setY(100);
player.setLayoutParams(layoutParams);
// 빨간색 버튼을 y축 250 위치에 생성
Button player2 = new Button(this);
player2.setBackgroundColor(Color.RED);
player2.setWidth(100);
player2.setHeight(100);
player2.setText("P");
player2.setY(50);
// 컨테이너에 뷰를 담는다.
stage.addView(player);
stage.addView(player2);
```

## 참고 문제
Animation을 활용한 문제
1. [SpreadCubes](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Example/SpreadCubes)
