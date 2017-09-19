# [CustomView]
- 특정 위젯을 상속받는 CustomView</br>
- View를 상속받아 코드로 직접 작성
#### 참고 : [그림판](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/CustomView2)

## 특정 위젯을 상속받는 CustomView
1. 커스텀 속성을 attrs.xml 파일에 정의
2. 커스텀할 객체(위젯)를 상속받은 후 재정의
3. 커스텀한 위젯을 레이아웃.xml에서 태그로 사용
---
### 1. __커스텀 속성을 attrs.xml 파일에 정의__
- resource/value 에 저장

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="AniButton"> <!-- 재정의할 객체이름-->
        <attr name="animation" format="string"></attr> <!-- string이랑 resource만 사용-->
        <attr name="delimeter" format="string"></attr>
    </declare-styleable>
</resources>
```

---
### 2. __커스텀할 객체(위젯)를 상속받은 후 재정의__
1. attrs.xml 에 정의된 속성을 가져온다.
2. 해당 이름으로 정의된 속성의 개수를 가져온다.
3. 반복문을 돌면서 해당 속성에 대한 처리를 해준다.
</br> - 3.1 현재 배열에 있는 속성 아이디 가져오기
</br> - 3.2 ID에 맞게 실행

```java
public class AniButton extends AppCompatButton implements View.OnTouchListener {
    // animation이 true일 때만 실행
    boolean animation = false;
    // attr에 정의한 모든 속성이 담겨진다.
    public AniButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 1. attrs.xml 에 정의된 속성을 가져온다.
        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.AniButton);
        // 2. 해당 이름으로 정의된 속성의 개수를 가져온다.
        int size = typed.getIndexCount();
        Log.d("AniButton", "size="+size);
        // 3. 반복문을 돌면서 해당 속성에 대한 처리를 해준다.
        for(int i =0 ; i<size ; i++){
            // 3.1 현재 배열에 있는 속성 아이디 가져오기
            int current_attr = typed.getIndex(i);
            switch (current_attr){
                case R.styleable.AniButton_animation:
                    String animation = typed.getString(current_attr);
                    if("true".equals(animation)){
                        // 현재 입력된 값을 가져올 수 있다.
                        String currentText = getText().toString();
                        setText("[animation]\n" + currentText );
                        // animation을 true로 바꾼다.
                        this.animation=true;
                    }
                    break;
                case R.styleable.AniButton_delimeter:
                    break;
            }
        }
        setOnTouchListener(this);
    }

    // 애니메이션 정의
    private void scaleAni(){
        if(animation){
            // ObjectAnimator startAniScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.5f, 1.0f); 사용 가능

            ObjectAnimator startAniScaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.5f);
            ObjectAnimator startAniScaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.5f);

            ObjectAnimator endAniScaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f);
            ObjectAnimator endAniScaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f);

            AnimatorSet aniSet = new AnimatorSet();
            AnimatorSet aniSet1 = new AnimatorSet();
            AnimatorSet aniSet2 = new AnimatorSet();
            aniSet1.playTogether(startAniScaleX,startAniScaleY);
            aniSet2.playTogether(endAniScaleX,endAniScaleY);

            aniSet.playSequentially(aniSet1,aniSet2);
            aniSet.setDuration(1000);
            aniSet.start();
        }

    }

    // onTouchListener 사용
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scaleAni();
                break;
        }
        return false;
    }
```

---
### 3. __커스텀한 위젯을 레이아웃.xml에서 태그로 사용__
1. 아래를 사용
</br> - 태그 : com.example.kyung.customview."재정의된 객체"
</br> - xmlns:custom="http://schemas.android.com/apk/res-auto"
</br> - custom:"속성"="true" // (or false)

```xml
<com.example.kyung.customview.AniButton xmlns:custom="http://schemas.android.com/apk/res-auto"
    android:id="@+id/aniButton1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello CustomWidget"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    custom:animation="true" />

<com.example.kyung.customview.AniButton xmlns:custom="http://schemas.android.com/apk/res-auto"
    android:id="@+id/aniButton2"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello CustomWidget"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    custom:animation="false"
    custom:layout_constraintVertical_bias="0.665"
    android:layout_marginTop="8dp"
    custom:layout_constraintTop_toBottomOf="@+id/aniButton1" />
```
---
---

## View를 상속받아 코드로 직접 작성
1. View 를 상속받은 후 재정의
2. CustomView 사용
</br> - 2.1 Activity에 코드로 addView
</br> - 2.2 커스텀한 View를 레이아웃.xml에서 태그로 사용
---
### 1. __커스텀할 객체(위젯)를 상속받은 후 재정의__
1. View를 상속
2. onDraw 메서드로 View에 그리기
</br> - Paint : 물감
</br> - setColor :
</br> - drawRect : 사각형 / drawCircle : 원 / drawPath : 경로 그리기
> CustomView.java

```java
public class CustomView extends View{
    // 내가 소스코드에서 생성할 때
    public CustomView(Context context){
        super(context);
    }
    //xml 에서 태그로 사용할 때 시스템에서 호출해주는 생성자
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    // onDraw 메서드로 View에 그리기
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(); // 물감 - 겉모양을 결정하는 도구
        paint.setColor(Color.MAGENTA);
        // 화면에 사각형 그리기
        canvas.drawRect(100, 100, 200, 200, paint);
    }
}
```
---
### 2. __CustomView 사용__
#### 2.1 Activity에 코드로 addView

```java
public class MainActivity extends AppCompatActivity {

    ConstraintLayout stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stage = (ConstraintLayout) findViewById(R.id.stage);
        // 작성 부분
        CustomView cv = new CustomView(this);
        cv.setX(200); // CustomView의 x좌표 설정
        cv.setY(200); // CustomView의 y좌표 설정
        stage.addView(cv); // 화면에 추가
    }
}
```
#### 2.2 커스텀한 View를 레이아웃.xml에서 태그로 사용

```xml
<com.example.kyung.customview.CustomView
    android:layout_width="300dp"
    android:layout_height="150dp"
    android:layout_marginLeft="4dp"
    android:layout_marginTop="6dp"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```

---
## 참고사항
### 1. import android.support.v7.widget.AppCompatButton
- v4 < v7 이라고 되어 있지만 v4에서 지원하는 것을 v7이 지원하지 않을 때도 있다.(ex> ViewPage)
- 따라서 상황에 따라 v4, v7을 적절히 써야 한다.

### 2. Button vs AppCompatButton
- AppCompatButton은 하위레벨 버튼을 포함시켜서 API 레벨이 낮은 경우에도 버튼 실행을 가능하게 한다.
- 사용 : public class AniButton extends AppCompatButton

### 3. "true".equals(animation)
- animation.equals(true) 와 같은 경우 animation에 null이 포함되게 되면 exception이 발생할 수 있으므로 이와같이 코딩한다.

### 4. onTouchListener vs onClickListener
- onTouchListener로 정의시 여러 event(down, press 등)에 맞게 종류별로 사용 가능하다.
- onClickListener로 정의시 클릭후 떼었을때 event가 발생
