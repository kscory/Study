# Animation
- View Animation
- Property Animation

## View 애니메이션
위치나 크기, 회전을 지정한 시간내에 수행하는 애니메이션
- View 애니메이션 사용 시 버튼클릭은 원래 위치에서 해야한다.
- 종류 : Move / Rotate / Scale / Alpha
- 애니메이션 설정 방법
   <br>   1. java 코드 내에서 이용
   <br>   2. xml 이용

### 1. __코드 내에서 바로 이용__

#### ① Animation 설정
RotateAnimation / ScaleAnimation / TranslateAnimation / AlphaAnimation 존재
#### ② 조건 설정
ex> setDuration, setFillAfter, setInterpolator, setRepeatCount 등
#### ③ 실제 위젯에 적용

> 예시
```java
// 1. 애니메이션 설정
Animation aniView = new TranslateAnimation(0,100,0,0); //aniView 는 View 이름
// 조건 설정
aniView.setDuration(1000);
aniView.setFillAfter(true);
aniView.setInterpolator(new AccelerateInterpolator());
// 3. 실제 위젯에 적용
btnView.startAnimation(aniView);
```

### 2. __xml을 이용한 애니메이션 설정__
<순서> :</br>
①. 애니메이션 xml 정의 () -> ②. AnimationUtil로 정의된 애니메이션을 로드 -> ③. 로드된 애니메이션을 실제 위젯에 적용

#### ① 애니메이션 xml 정의
resource/anim 디렉토리에 xml 파일 생성
 - Move(Translate) : 움직임 조절
 - Rotate : 회전 조절
 - Scale : 크기 조절
 - Alpha : 투명도 조절 (0~1 까지 가능)
 - 복합 : set 안에 여러개 적용 가능

> Syntax
> - 몇가지 설명 </br>
> 》 filAfter =  true-애니메이션의 종료위치 고정 / false-원래위치로 복귀(default = false)</br>
> 》 시간값(duration) = 1/1000 초 단위 설정</br>
> 》 pivot은 %로 기준점 위치

```xml
<!-- set을 translate으로 바꾼다-->
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@[package:]anim/interpolator_resource"
    android:shareInterpolator=["true" | "false"] >
    <alpha
        android:fromAlpha="float"
        android:toAlpha="float" />
    <scale
        android:fromXScale="float"
        android:toXScale="float"
        android:fromYScale="float"
        android:toYScale="float"
        android:pivotX="float"
        android:pivotY="float" />
    <translate
        android:fromXDelta="float"
        android:toXDelta="float"
        android:fromYDelta="float"
        android:toYDelta="float" />
    <rotate
        android:fromDegrees="float"
        android:toDegrees="float"
        android:pivotX="float"
        android:pivotY="float" />
    <set>
        ...
    </set>
</set>
```

> 예시
> 위는 interpolator, 아래는 애니메이션
```xml
<?xml version="1.0" encoding="utf-8"?>
<overshootInterpolator xmlns:android="http://schemas.android.com/apk/res/android"
    android:tension="7.0"
    />
```
```xml
<translate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/accelerate_decelerate_interpolator"
    android:fromXDelta="0"
    android:fromYDelta="0"
    android:toXDelta="100"
    android:toYDelta="300"
    android:fillAfter="true"
    android:duration="3000">
</translate>

<rotate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromDegrees="0"
    android:toDegrees="270"
    android:pivotX="50%"
    android:pivotY="50%">
    <!-- pivot은 기준점 위치를 이야기함-->
</rotate>

<scale
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromXScale="1.0"
    android:fromYScale="1.0"
    android:toXScale="0.5"
    android:toYScale="5">
</scale>

<alpha
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromAlpha="0"
    android:toAlpha="1.0">
    <!--    alpha : 투명도가 0~1까지-->
</alpha>
```

#### ② AnimationUtil로 정의된 애니메이션을 로드
```java
Animation animation = AnimationUtils.loadAnimation(this, R.anim."xml 이름");
```

#### ③ 로드된 애니메이션을 실제 위젯에 적용
```java
private void move(){
    object.startAnimation(animation); //object : 애니메이션을 사용할 위젯
}
```

> 예시

```java
private void rotate(){
    // 2. AnimationUtil로 정의된 애니메이션을 로드
    Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
    // 3. 로드된 애니메이션을 실제 위젯에 적용
    object.startAnimation(animation);
}
```

## Property 애니메이션
위치나 크기, 회전을 지정한 시간내에 수행하는 애니메이션
- ※ View 애니메이션 사용 시 버튼클릭은 바뀐 위치에서 실행 가능
- 애니메이션 설정 방법
   </br>  1. java 코드 내에서 이용 1
   </br>  2. java 코드 내에서 이용 2
   </br>  3. xml 이용

### 1. __코드 내에서 바로 이용_1__
ViewPropertyAnimator 사용</br>
translationX / translationY / translationbyX / translationbyY / rotation / etc ...
> 예시

```java
btnView.animate().translationX(100).withLayer();
///btnView 는 View id를 뜻함
```

### 2. __코드 내에서 바로 이용_2__

#### ① 대상을 정의한다.

#### ② 애니메이터를 설정한다.
  - 2.1. 움직일 대상을 넣는다.
  - 2.2. 애니메이션 속성 (움직임)
  - 2.3. 속성 값(위치일경우는 거리)

#### ③ 애니메이터를 실행한다.

```java
// 2. 애니메이션 속성 정의
ObjectAnimator ani = ObjectAnimator.ofFloat( //소수점 단위로 애니메이트 시킴
        위젯,      // 가 . 움직일 대상을 넣는다.
        "속성",    // 나. 애니메이션 속성 (움직임)
        값        // 다. 속성 값(위치일경우는 거리)
);

// 3. 애니메이터를 실행한다.
ani.start();
```

> 예시 (복합애니메이션 예시)

```java
ObjectAnimator aniY = ObjectAnimator.ofFloat(btnObject, "translationY", 300);
ObjectAnimator aniX = ObjectAnimator.ofFloat(btnObject, "translationX", 300);
ObjectAnimator anirot = ObjectAnimator.ofFloat(btnObject, "rotation", 0F,360F);

// 애니메이션 셋에 담아서 동시에 실행 가능
AnimatorSet aniSet = new AnimatorSet();
aniSet.playTogether(aniY, aniX);
aniSet.setDuration(3000);
aniSet.setInterpolator(new LinearInterpolator());
aniSet.start();
```

### 3. __xml을 이용한 애니메이션 설정__
<순서> :</br>
①. 애니메이션 xml 정의 () -> ②. AnimatorInflater로 정의된 애니메이션을 로드 -> ③. 로드된 애니메이션을 실제 위젯에 적용

#### ① 애니메이션 xml 정의
resource/animator 디렉토리에 xml 파일 생성
-

> Syntax
```xml
<set
  android:ordering=["together" | "sequentially"]>
    <objectAnimator
        android:propertyName="string"
        android:duration="int"
        android:valueFrom="float | int | color"
        android:valueTo="float | int | color"
        android:startOffset="int"
        android:repeatCount="int"
        android:repeatMode=["repeat" | "reverse"]
        android:valueType=["intType" | "floatType"]/>

    <animator
        android:duration="int"
        android:valueFrom="float | int | color"
        android:valueTo="float | int | color"
        android:startOffset="int"
        android:repeatCount="int"
        android:repeatMode=["repeat" | "reverse"]
        android:valueType=["intType" | "floatType"]/>
    <set>
        ...
    </set>
</set>
```

> 예시

```java
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <objectAnimator
        android:propertyName="x"
        android:duration="2000"
        android:valueFrom="0"
        android:valueTo="40"
        android:startOffset="4"
        android:valueType="floatType"/>
</set>
```

#### ② AnimatorInflater로 정의된 애니메이션을 로드
#### ③ 로드된 애니메이션을 실제 위젯에 적용
```java
// AnimatorInflater로 정의된 애니메이션을 로드
Animator animation = AnimatorInflater.loadAnimator(this, R.animator.move);

// 로드된 애니메이션을 실제 위젯에 적용
animation.setTarget(object);
animation.start();
```

## 참고 사항
1. interpolator
//움직임의 정도를 설정
```
// 속도가 동일하게 이동 : linear_interpolator
// 점점 빠르게 이동 : accerlerate_interpolator
// 점점 느리게 이동 : decelerate_interpolator
// 위 둘을 동시에 : accerlerate_decelerate_interpolator
// 시작위치에서 조금 뒤로 당겼다 이동 : anticipate__interpolator
// 도착위치를 조금 지나쳤다가 도착위치로 이동 : overshoot_interpolator
// 위 둘을 동시에 : anticipate_overshoot_interpolator
// 도착위치에서 튕김 : bounce_interpolator
```

## 참고 문제
Animation을 활용한 문제
1. [SpreadCubes](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Example/SpreadCubes)
2. [JoyStick(코드만 제시)](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/Animation/app/src/main/java/com/example/kyung/animation/JoystickActivity.java)
