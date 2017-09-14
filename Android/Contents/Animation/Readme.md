# Animation
- View Animation
- Property Animation


## View 애니메이션
위치나 크기, 회전을 지정한 시간내에 수행하는 애니메이션
- View 애니메이션 사용 시 버튼클릭은 원래 위치에서 해야한다.
- 종류 : Move / Rotate / Scale / Alpha
- 애니메이션 설정 방법 1 (xml 이용)
  1. 애니메이션 xml 정의 ()
  2. AnimationUtil로 정의된 애니메이션을 로드
  3. 로드된 애니메이션을 실제 위젯에 적용
- View 애니메이션 설정 방법 2 (java 코드 내에서 이용)

### 1. __xml을 이용한 애니메이션 설정__
#### ① 애니메이션 xml 정의
resource/anim 디렉토리에 xml 파일 생성
 - Move(Translate) : 움직임 조절
 - Rotate : 회전 조절
 - Scale : 크기 조절
 - Alpha : 투명도 조절 (0~1 까지 가능)
 - 복합 : set 안에 여러개 적용 가능

> Syntax
> - 몇가지 설명 </br>
> filAfter =  true-애니메이션의 종료위치 고정 / false-원래위치로 복귀(default = false)</br>
> 시간값(duration) = 1/1000 초 단위 설정</br>
> pivot은 %로 기준점 위치

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

```xml
<translate
    xmlns:android="http://schemas.android.com/apk/res/android"
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

## 참고 문제
Animation을 활용한 문제
1. [SpreadCubes](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Example/SpreadCubes)
