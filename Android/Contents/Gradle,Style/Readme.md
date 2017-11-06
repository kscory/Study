# Gradle / Style
- Gradle에 대한 설명 (주로 App Gradle)
- Style을 변경하는 방법

---

## Gradle (참고 : [빌드 구성](https://developer.android.com/studio/build/index.html?hl=ko))
- 아래 그림과 같이 두가지의 build.Gradle이 존재

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Gradle%2CStyle/picture/gradle1.png)

### Project의 Build.Gradle
- 프로젝트의 모든 모듈에 적용되는 빌드 구성을 정의
- 모든 모듈에 공통되는 Gradle 리포지토리와 종속성을 정의

  #### 1. buildscript
  - 빌드스크립트를 수행하는데 필요한 설정을 정의, `com.android.tools.build:gradle:2.3.3`를 사용
  - 이 플러그인을 `jcenter`에서 찾음 (Maven Central을 많이 사용)
  ```java
  buildscript {
      repositories {
          jcenter()
      }
      dependencies {
          classpath 'com.android.tools.build:gradle:2.3.3'
      }
  }
  ```

  #### 2. allprojects
  - 모든 프로젝트의 repository를 설정
  ```java
  allprojects {
    repositories {
        jcenter()
    }
  }
  ```

  #### 3. task clean
  - build system을 clean 할때 이용하며 build 디렉토리를 지운다
  - `settings.gradle` 파일을 sync 하면, `rootProject.buildDir` 를 지운다
  ```java
  task clean(type: Delete) {
      delete rootProject.buildDir
  }
  ```

### App의 Build.Gradle (모듈레벨)
- 이 파일이 위치하는 특정 모듈의 빌드 설정을 구성 (사용자 지정 패키징 옵션을 제공)
- main/ 앱 매니페스트 또는 최상위 build.gradle 파일에 있는 설정을 재정의 가능

  #### 1. apply
  -  안드로이드 플러그인을 적용
  ```java
  apply plugin: 'com.android.application'
  ```

  #### 2. android
  - 안드로이드와 관련된 빌드설정
  ```java
  android {
    // 빌드에 사용할 안드로이드 SDK 버전 (API 레벨)
    compileSdkVersion 25
    //  빌드에 사용할 툴 버전
    buildToolsVersion "26.0.1"
    // android maifest에서 사용하는 설정이지만 우선순위가 높음
    defaultConfig {
        applicationId "com.example.kyung.androidmemoorm"
        // 애플리케이션을 실행하기 위한 최소 버전
        minSdkVersion 16
        // 애플리케이션이 주로 실행될 버전
        targetSdkVersion 25
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    // dev, alpha, beta, release와 같이 타입 종류를 지정
    // debug타입 지정시 테스트용이랑 realise 버전이랑 두개로 컴파일 해주는 기능을 지니게 된다.
    buildTypes {
        // 현재 release 타입을 지정
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
    // 빌드별 Flavor 설정 (개발용, QA용, 상용 배포용 으로 나누어 빌드 가능)
    productFlavors {
        free {
            applicationId 'com.example.kyung.androidmemoorm.free'
        }
        paid {
            applicationId 'com.example.kyung.androidmemoorm.paid'
        }
    }

  }
  ```

  #### 3. dependencies
  - 모듈 빌드시 사용할 라이브러리를 설정
  - 라이브러리 이름 : `[패키지 이름]:[라이브러리 이름]:[버전명]`
  - 항상 버전을 맞춰 주어야 한다.
  - 버전을 `5.+` 와 같이 하면 `5.` 버전 중 최신 버전을 다운한다.
  ```java
  dependencies {
    // .jar 파일은 다 쓰겠다.
    compile fileTree(dir: 'libs', include: ['*.jar'])
    //
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    //
    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support.constraint:constraint-layout:1.0.2'
    testCompile 'junit:junit:4.12'

    // 라이브러리 추가 (orm이 추가됨)
    compile 'com.j256.ormlite:ormlite-android:5.0'
  }
  ```

---

## Style (참고 : [스타일 및 테마](https://developer.android.com/guide/topics/ui/themes.html), [머티리얼 테마 사용](https://developer.android.com/training/material/theme.html) )

### style.xml
View 또는 창의 모양과 형식을 지정하는 속성 모음

  - `<style>` 요소에 `parent` 특성을 사용하여 스타일이 속성을 상속해야 하는 상위 스타일을 지정가능
  ```xml
  <style name="GreenText" parent="@android:style/TextAppearance"></style>
  ```

  - `<item>` 요소로 정의되는 스타일 속성 정의(스타일 속성을 여러가지 존재)
    - ex1> `<item name="windowNoTitle">true</item>` : title의 유무 (ture시 삭제)
    - ex2> `<item name="windowActionBar">true</item>` : actionBar의 유무
    - ex3> `<item name="android:windowIsTranslucent">true</item>` : 투명도 설정
    ```xml
    <style name="Theme.AppCompat.Translucent">
    <!-- 타이틀을 날리는 것으 반드시 Style에서 한다. -->
    <item name="windowActionBar">true</item>
    <item name="windowNoTitle">true</item>
    <item name="android:windowIsTranslucent">true</item>
    <item name="android:windowBackground">@color/transparent</item> // color 설정 적용
    </style>
    ```

  - 상태표시줄

    ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/Gradle%2CStyle/picture/style1.png)


### color.xml
색상을 지정
  - 아래와 같이 사용
  ```xml
  <color name="transparent">#33000000</color>
  ```

### Sytle 적용

  - 액티비티 또는 애플리케이션 적용 (AndroidMaifest 파일에서 적용)
  ```xml
  <activity android:name=".WriteActivity"
    android:theme="@style/Theme.AppCompat.Translucent"></activity>
  ```

  - View에 적용
  ```xml
  <TextView
    style="@style/CodeFont" // 적용
    android:text="@string/hello" />
  ```

### EditText의 Style 설명

  ```xml
  <style name="EditTextStyle" parent="Widget.AppCompat.EditText">
      <item name="colorControlNormal">@color/colorWhite</item> <!--포커스 되어있지 않은 경우의 밑줄 부분-->
      <item name="colorControlActivated">@color/colorWhite</item> <!--손끝으로 터치되어 있는 부분. text selection handle라고 부른다고 한다-->
      <item name="android:textCursorDrawable">@color/colorWhite</item> <!--커서 색. drawable 지정되지만, 색을 설정하는것도 된다-->
      <item name="android:textColor">@color/colorWhite</item> <!--텍스트 색-->
      <item name="android:textColorHint">@color/colorWhite</item> <!--입력전의 Hint (포커스 Holder)-->
      <item name="android:textColorHighlight">@color/colorWhite</item> <!--텍스트 선택색-->
      <item name="colorAccent">@color/colorMain</item> <!--포커스 된 밑줄 부분(핑크색 설정 변경) 바꿀 수 있다.-->
  </style>
  ```
---

## 참고
1. 메이븐 중앙 저장소(Maven Central repository)

  - 메이븐은 프로젝트 객체 모델(Project Object Model)이라는 개념을 바탕으로 프로젝트 의존성 관리, 라이브러리 관리, 프로젝트 생명 주기 관리 기능 등을 제공하는 프로젝트 관리 도구이다. 또한 플러그인을 기반으로 소스 코드로부터 배포 가능한 산출물을 만들어 내는 빌드 기능 뿐만 아니라 레포팅 및 documentation 작성 기능 등을 제공

2. jcenter

  - jcenter는 bintray.com이 운영하는 Maven Repository
