# [그림판]
CustomView를 활용한 그림판 App 만들기
- CustomView / onDraw / onTouchListener / Path 활용

---
## __문제__
아래와 같이 작동하는 App 만들기 </br>
![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Example/CustomView2/picture/draw.gif)

---
## __설명__
1. View를 상속받아 CustomView 생성
2. Activity에 그리기

---
### 1. __View를 상속받아 CustomView 생성__
- Path 메소드 활용
- onDraw 메소드 활용
- onTouchListener 활용
> DrawView.java

```java
public class DrawView extends View {

    Paint paint;
    Path currentPath;

    List<PathTool> paths = new ArrayList<>();
    // 소스코드에서 사용하기 때문에 생성자 파라미터는 context만 필요
    public DrawView(Context context) {
        super(context);

        paint = new Paint();
        init();
    }

    public void init(){
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        setColor(Color.BLACK);
    }
    // Color를 세팅
    public void setColor(int color){
        PathTool tool = new PathTool();
        tool.setColor(color);
        currentPath = tool;
        paths.add(tool);
    }
    // 그림판 초기화 작업
    public void memoClear(){
        int iColor = paths.get(paths.size()-1).getColor() ;
        paths.clear();
        init();
        paths.get(0).setColor(iColor);
        invalidate();
    }
    // 화면을 그려주는 onDraw 오버라이드
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(PathTool tool : paths){
            paint.setColor(tool.getColor());
            canvas.drawPath(tool,paint);
        }
    }
    // 터치가 일어났을시 이벤트 발생
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 좌표 터치시 해당 좌표로 이동한다. (이전점과 현재점 사이를 그리지 않는다.)
                currentPath.moveTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                / 터치가 일어나면 패스를 해당 좌표까지 선을 긎는다. (이전점과 현재점 사이를 그린다.)
                currentPath.lineTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                // nothing to do
                break;
        }
        // onDraw를 호출하는 메소드
        invalidate();
        return true;
    }
}
// Path를 상속받아 사용
class PathTool extends Path{
    private int color;
    public void setColor(int color){
        this.color = color;
    }
    public int getColor(){
        return this.color;
    }
}
```

---
2. Activity에 그리기

> MainActiity.java

```java
public class MainActivity extends AppCompatActivity {
    FrameLayout stage;
    Button btnClear;
    RadioGroup radioColor;
    DrawView draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        // CustomView 사용
        draw = new DrawView(this);
        stage.addView(draw);
        radioColor.setOnCheckedChangeListener(radioListener);
        btnClear.setOnClickListener(onClickListener);
    }

    // 라디오 버튼 클릭마다 색이 바뀌도록 설정
    RadioGroup.OnCheckedChangeListener radioListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch(checkedId){
                case R.id.radioBlack:
                    draw.setColor(Color.BLACK);
                    break;
                case R.id.radioCyan:
                    draw.setColor(Color.CYAN);
                    break;
                case R.id.radioMagenta:
                    draw.setColor(Color.MAGENTA);
                    break;
                case R.id.radioYellow:
                    draw.setColor(Color.YELLOW);
                    break;
            }
        }
    };

    // 클리어 버튼 클릭시 모두 지움
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnClear:
                    draw.memoClear();
                    break;
            }
        }
    };

    private void initView(){
        stage = (FrameLayout) findViewById(R.id.stage);
        btnClear = (Button) findViewById(R.id.btnClear);
        radioColor = (RadioGroup) findViewById(R.id.radioColor);
    }
}
```

---
## 참고사항
### 1. super?
- 상위 클래스의 인자를 호출
- super() 는 생성자를 호출한다.(super(인자) -> super.생성자(인자))

### 2. invalidate
- onDraw를 호출한다.

### 3. (Path) 상속을 사용한 이유?
- Path를 상속받아 효율적으로 이용이 가능
