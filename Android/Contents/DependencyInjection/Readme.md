# Dependency Injection
  - DependencyInjection 이란
  - DependencyInjection 라이브러리
    - [ButterKnife](http://jakewharton.github.io/butterknife/)
    - [AndroidAnnotation](http://androidannotations.org/)

---

## Dependency Injection 이란 (+IOC , AOP)
  ### 1. Dependency Injection(DI) - [참고_의존성 주입](https://ko.wikipedia.org/wiki/%EC%9D%98%EC%A1%B4%EC%84%B1_%EC%A3%BC%EC%9E%85)
  - 의존성 주입(Dependency Injection, DI)은 프로그래밍에서 구성요소간의 의존 관계가 소스코드 내부가 아닌 외부의 설정파일 등을 통해 정의되게 하는 디자인 패턴 중의 하나
    - 의존성 : new를 하는 행위 자체
    - 주입 : 내가 가진 변수를 집어 넣을 때
    - 즉 다른 곳에서 만들어져서 나에게 넣어주는것이 의존성 주입(DI)
  - 의존성 주입(DI)의 장점
    - 의존 관계 설정이 컴파일시가 아닌 실행시에 이루어져 모듈들간의 결합도 를 낮출 수 있다.
    - 코드 재사용을 높여서 작성된 모듈을 여러 곳에서 소스코드의 수정 없이 사용할 수 있다.
    - 모의 객체 등을 이용한 단위 테스트의 편의성을 높여준다.

  ### 2. Inverstion Of Control (IOC)-[참고_제어  반전](https://ko.wikipedia.org/wiki/%EC%A0%9C%EC%96%B4_%EB%B0%98%EC%A0%84)
  - 의존성이 생기는 것들을 한곳에서 관리하는 것
  - 프로그래머가 작성한 프로그램이 재사용 라이브러리의 흐름 제어를 받게 되는 소프트웨어 디자인 패턴
    > 전통적인 프로그래밍에서 흐름은 프로그래머가 작성한 프로그램이 외부 라이브러리의 코드를 호출해 이용한다. 하지만 제어 반전이 적용된 구조에서는 외부 라이브러리의 코드가 프로그래머가 작성한 코드를 호출

  - 사용 이유
    - 작업을 구현하는 방식과 작업 수행 자체를 분리한다.
    - 모듈을 제작할 때, 모듈과 외부 프로그램의 결합에 대해 고민할 필요 없이 모듈의 목적에 집중할 수 있다.
    - 다른 시스템이 어떻게 동작할지에 대해 고민할 필요 없이, 미리 정해진 협약대로만 동작하게 하면 된다.
    - 모듈을 바꾸어도 다른 시스템에 부작용을 일으키지 않는다.

  - DI, IOC 그림
  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/DependencyInjection/picture/DI,IOP.png)

  ### 3. Aspect Oriented Programming(AOP) - [참고_관점지향프로그래밍](https://ko.wikipedia.org/wiki/%EA%B4%80%EC%A0%90_%EC%A7%80%ED%96%A5_%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D)
  - 관점 지향 프로그래밍(aspect-oriented programming, AOP)은 횡단 관심사(cross-cutting concern)의 분리를 허용함으로써 모듈성을 증가시키는 것이 목적인 프로그래밍 패러다임
  - 그림으로 이해

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/DependencyInjection/picture/AOP.png)

---

## ButterKnife
  ### 1. ButterKnife란?
  - 내용

  ### 2. ButterKnife 사용방법

  ```java

  ```

---

## AndroidAnnotation
  ### 1. AndroidAnnotation 이란?
  - 내용

  ### 2. AndroidAnnotation 사용방법

  ```java

  ```
