package com.example.kyung.dependencyinjection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

/*
Anrdroid Annotation => 편하지만 단점은....=> activity를 다시 생성하기 때문에.... mainifest에서 "_"를 추가시켜 주여야 한다.
*/
@EActivity(R.layout.activity_main) // oncreate 생성
@WindowFeature(Window.FEATURE_NO_TITLE) // 타이틀을 날린다. => 등등 스타일에서 했던 것을 정해줄 수 있다.
@Fullscreen
public class MainActivity extends AppCompatActivity {

    @ViewById
    TextView text;

    @AfterViews
    public void init(){
        text.setText("Hello AA");
    }
}

//public class MainActivity extends AppCompatActivity {
    /*
    버터나이프는 반드시 엑티비티에서 사용해야 한다.
     */
    // 리스트로도 사용이 가능
//    @BindView({R.id.btn_0,R.id.btn_1....})
//    List<Button>
//    @BindView(R.id.text) TextView text; // 아래위 두줄을 한줄로 바꿔주는 것이 dependency injection

    // 일반적인 방법
    //    TextView text;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        // di 사용 (버터나이프)
////        ButterKnife.bind(this);
////        text.setText("Hello Android");
//
//        // di 미사용
//        //text = (TextView) findViewById(R.id.text);
//
//    }
//}
