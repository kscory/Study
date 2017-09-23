# Gradle / Style
- Gradle에 대한 설명 (주로 App Gradle)
- Style을 변경하는 방법

---

## Gradle
[참고문헌](https://developer.android.com/studio/build/index.html?hl=ko)
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

## Style 의 변경



## 참고
1. 메이븐 중앙 저장소(Maven Central repository)
2. jcenter
