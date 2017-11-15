# RxJava Basic2
  - RxJava의 간단한 Operator의 사용
  - Filter / Map / FlatMap
  - Zip

---

## Map / FlatMap
  ### 0. 발행자 코드
  - 1월부터 12월을 가져온다.

  > Observable

  ```java
  // 1. 발행자
  observable = Observable.create( e -> {
      try {
          for (String month : monthString) {
              e.onNext(month); // subscribe를 함과 동시에 onNext를 호출하면서 구독자의 onNext를 호출
              Thread.sleep(1000);
          }
          e.onComplete(); // 완료되었다고 호출
      } catch (Exception ex){
          throw ex; // 에러가 날 경우가 있을 경우 trycatch문으로 감싸고 에러를 호출
      }
  });
  ```

  ### 1. Filter & Map
  - Filter의 경우 특정 데이터를 boolean으로 걸러주는 역할을 한다.
  - filter에서 사막연산자 사용
  - Map은 데이터 자체를 변형 할 수 있다.

  > Filter , Map 예시

  ```java
  public void doMap(View view){
      // 2. 구독자
      months.clear();
      observable
              .subscribeOn(Schedulers.io()) // 옵저버블의 thread를 지정
              .observeOn(AndroidSchedulers.mainThread()) // 옵저버의 thread를 지정 (안드로이드에만 있음)
              .filter( (str) -> str.equals("3월") ? false : true ) // 걸러주는 역할(데이터에는 손대지 못한다.)
              .map((str) -> {
                      if(str.equals("4월")) return "[" + str + "]";
                      else return str;
              }) // 데이터를 변형
              .subscribe(
                      str -> {
                          months.add(str);
                          adapter.setDataAndRefresh(months);
                      }
              );
  }  
  ```

  ### 2. FlatMap
  - 하나의 데이터를 두개로 쪼개기 위해 사용한다.
  - flatMap은 아이템을 여러개로 분리할 수 있다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RxBasic02/picture/flatmap.png)

  > FlatMap 예시

  ```java
  public void doFlatMap(View view){
      months.clear();
      observable
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .flatMap(item -> {
                  return Observable.fromArray(new String[] {"name:"+item, "["+item+"]"});
              }) // name:월, [월] 두가지의 배열로 증가하게 된다.
              .subscribe(
                      str -> {
                          months.add(str);
                          adapter.setDataAndRefresh(months);
                      }
              );
  }  
  ```

---

## Zip
  ### 1. 발행자 (Observable)
  - 복수개의 observable을 하나로 묶어준다.
  - zip의 경우 observableZip으로 따로 만들어 주어야 한다.

  > observableZip

  ```java
  // 예시 1(just만 사용)
  observableZip = Observable.zip(
          Observable.just("BeWhy", "Curry"),
          Observable.just("Singer", "Basketball Player"),
          (item1, item2) -> "job= ".concat(item2).concat(", name= ").concat(item1)
  );

  // 예시 2(Crate 사용하여 Thread.sleep을 줄 수 있다.)
  Observable<String> obs1 = Observable.create(e -> {
      try {
          Thread.sleep(1000);
          e.onNext("Singer");
          Thread.sleep(1000);
          e.onNext("Athlete");
          Thread.sleep(5000);
          e.onNext("Rapper");
          e.onComplete();
      }catch(Exception ex){
          throw ex;
      }
  });
  observableZip = Observable.zip(
          Observable.just("BeWhy", "Curry", "Zico"),
          obs1,
          (item1, item2) -> "job= ".concat(item2).concat(", name= ").concat(item1)
  );
  ```

  ### 2. 구독자 (Subscribe)
  - 구독자의 사용방법은 동일하다.

  ```java
  public void doZip(View view){
      months.clear();
      observableZip
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(
                      str -> {
                          months.add(str);
                          adapter.setDataAndRefresh(months);
                      }
              );
  }
  ```

---

## 참고 문헌
  ### 1. [ReactiveX Operator](http://reactivex.io/documentation/operators.html)
