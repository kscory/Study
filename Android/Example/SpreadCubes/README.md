# SpreadCubes 만들기
애니메이션을 활용한 간단한 App 만들기

## __문제__
아래와 같이 작동하는 App 만들기 </br>
![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Example/SpreadCubes/picture/SpreadCubes.gif)

## __설명__
1. 버튼 및 위젯 정의, 클릭버튼 활성화
2. 애니메이션 정의
  - 2.1. Moveing 및 Rotation 적용
  - 2.2 함께 실행
3. Spread 와 Converge 메소드 따로 정의
  - 3.1 이동해야할 위치 지정
  - 3.2 클릭버튼 바꾸기

#### 소스코드
```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2, btn3, btn4, btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initView(){
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btnChange = (Button) findViewById(R.id.btnChange);
    }
    private void initListener(){
        btnChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnChange){
            if(btnChange.getText().toString().equals("Spread")){
                spread();
            } else{
                converge();
            }
        }
    }

    int btn1X = 0; int btn1Y = 0;
    int btn2X = 0; int btn2Y = 0;
    int btn3X = 0; int btn3Y = 0;
    int btn4X = 0; int btn4Y = 0;

    // sparead 함수
    public void spread(){
        // 이동해야할 위치 지정
        btn1X+=btn1.getWidth()/2 ; btn1Y+=btn1.getHeight()/2;
        btn2X+=btn2.getWidth()/2 ; btn2Y-=btn2.getHeight()/2;
        btn3X-=btn3.getWidth()/2 ; btn3Y+=btn3.getHeight()/2;
        btn4X-=btn4.getWidth()/2 ; btn4Y-=btn4.getHeight()/2;

        // 애니메이션 실행
        animationEffect(btn1, btn1X, btn1Y);
        animationEffect(btn2,btn2X,btn2Y);
        animationEffect(btn3,btn3X,btn3Y);
        animationEffect(btn4,btn4X,btn4Y);

        // 버튼 이름 변경
        btnChange.setText("Converge");
    }

    //converge 함수
    public void converge(){
        btn1X-=btn1.getWidth()/2 ; btn1Y-=btn1.getHeight()/2;
        btn2X-=btn2.getWidth()/2 ; btn2Y+=btn2.getHeight()/2;
        btn3X+=btn3.getWidth()/2 ; btn3Y-=btn3.getHeight()/2;
        btn4X+=btn4.getWidth()/2 ; btn4Y+=btn4.getHeight()/2;

        // 애니메이션 실행
        animationEffect(btn1, btn1X, btn1Y);
        animationEffect(btn2,btn2X,btn2Y);
        animationEffect(btn3,btn3X,btn3Y);
        animationEffect(btn4,btn4X,btn4Y);

        // 버튼 이름 변경
        btnChange.setText("Spread");
    }

    //애니메이션 정의
    public void animationEffect(View v, int x, int y){
        // 1 Moving
        ObjectAnimator aniBtnX = ObjectAnimator.ofFloat(v, "translationY", x);
        ObjectAnimator aniBtnY = ObjectAnimator.ofFloat(v, "translationX", y);
        aniBtnX.setDuration(1500);
        aniBtnY.setDuration(1500);

        // 2 Roation
        ObjectAnimator aniRotateBtn = ObjectAnimator.ofFloat(v,"rotation",0F,1080F);
        aniRotateBtn.setDuration(2000);
        aniRotateBtn.setInterpolator(new AccelerateInterpolator());

        // 함께 실행
        AnimatorSet aniSet = new AnimatorSet();
        aniSet.playTogether(aniBtnX, aniBtnY, aniRotateBtn);
        aniSet.start();
    }
}
```
