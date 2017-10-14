# Service
  - 서비스에 대해
  - 서비스의 생명주기
  - 서비스 예시

---

## Service 개념
  ### 1. 서비스란
  - Service는 백그라운드에서 오래 실행되는 작업을 수행하게 하며 사용자 인터페이스를 제공하지 않는 애플리케이션 구성 요소
  - 서비스는 본질적으로 두 가지 형식을 취한다. (값을 주고받을 때는 bind서비스 그냥 시작할 때는 start를 주로 사용)
  - `StartService` :
    - 애플리케이션 구성 요소가 startService()를 호출하여 시작
    - 한 번 시작되고 나면 백그라운드에서 무기한으로 실행
  - `BindService` :
    - 애플리케이션 구성 요소가 bindService()를 호출하여 해당 서비스에 바인드되면 실행
    - 클라이언트-서버 인터페이스를 제공하여 구성 요소가 서비스와 상호작용할 수 있도록 함
    - . 여러 개의 구성 요소가 서비스에 한꺼번에 바인드될 수 있지만, 이 모든 것이 바인딩을 해제하면 해당 서비스는 소멸
  - But> 서비스는 두 가지 방식 모두로 작동 가능
  - ※ 주의사항
    - 서비스는 자신의 호스팅 프로세스의 기본 스레드에서 실행 (=> 서비스 내에 새 스레드를 생성하여 해당 작업을 수행해야 함)
    - 즉, 서비스에 무한루프를 돌리면 화면의 버튼클릭이 안되고, 엑티비티에 무한루프를 돌리면 서비스 실행이 안됨 (아래 예시에서 다시 설명)

  ### 2. 서비스 기본사항
  - `onStartCommand()` :  `startService()`를 호출하여 서비스를 시작하도록 요청하면 실행되는 메소드로  `stopSelf()` 또는 `stopService()`를 호출하면 중단
  - `onBind()` : `bindService()`를 호출하여 서비스를 바인드하도록 요청하면 실행되는 메소드,  클라이언트가 서비스와 통신을 주고받기 위해 사용할 인터페이스를 제공해야 하며, `unbindService()`를 호출하면 중단
  - `onCreate()` :  서비스가 처음 생성되어 일회성 설정 절차를 수행 (서비스가 이미 실행 중인 경우, 이 메서드는 호출되지 않음)
  - `onDestroy()` : 서비스를 더 이상 사용하지 않고 소멸 (서비스가 수신하는 마지막 호출)

  ### 3. Foreground
  -  서비스가 포그라운드에서 실행된다고 선언된 경우 Android 시스템이 서비스를 거의 강제 중단시키지 않음
  - `startForeground(Flag값, 알림바)` 로 호출 (flag로 찾아서 이를 stop 할 수 있음)
  - `stopForeground(true)` 호출시 서비스는 유지하면서 포그라운드 상태에서 해제되며 false로 하면 제거하지 않는다

  ### 4. 서비스의 생명주기
  - 서비스도 생명주기를 가진다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/ServiceBasic/picture/service_lifecycle.png)


---

## Service 사용 예시
  ### 1. MainActivity
  - dd

  > MainActivity.java

  ```java

  ```

  ### 2. MyService
  - dd

  > MyService.java

  ```java

  ```
---

## 참고 자료

#### 1. [서비스](https://developer.android.com/guide/components/services.html?hl=ko)
#### 2. [바인드된 서비스](https://developer.android.com/guide/components/bound-services.html?hl=ko#Basics)
