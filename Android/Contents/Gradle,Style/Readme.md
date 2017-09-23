# Gradle / Style
- Gradle에 대한 설명 (주로 App Gradle)
- Style을 변경하는 방법

---

## Gradle
[참고문헌](https://developer.android.com/studio/build/index.html?hl=ko)
- 아래 그림과 같이 두가지의 build.Gradle이 존재

![](ㅇ)

### Project의 Build.Gradle
- 프로젝트의 모든 모듈에 적용되는 빌드 구성을 정의
- 모든 모듈에 공통되는 Gradle 리포지토리와 종속성을 정의

  #### 1. buildscript
  - 빌드스크립트를 수행하는데 필요한 설정을 정의, 아래는 `com.android.tools.build:gradle:2.3.3`를 사용
  - 이 플러그인을 `jcenter`에서 찾음

  ```
  buildscript {
      repositories {
          jcenter()
      }
      dependencies {
          classpath 'com.android.tools.build:gradle:2.3.3'
      }
  }
  ```

### App의 Build.Gradle (모듈레벨)
-

---

## Style 의 변경



## 소스 링크
1.
