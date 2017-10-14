# Design Pattern
  - Singleton Pattern
  - Observer Pattern

---

## Singleton Pattern
  ### 1. Singleton Pattern 이란
  - 인스턴스가 사용될 때에 똑같은 인스턴스를 만들어 내는 것이 아니라, 동일 인스턴스를 사용하게끔 하는 것이 기본 전략
  - new가 한번만 진행되도록 설정

  ### 2. Singleton Pattern 사용 예시
  - 하나의 객체만 생성시킨다.

  ```Java
  public class Singleton{
      // 인스턴스를 한개 저장하는 저장소
      private static Singleton instance = null;
      // 얘는 앱 전체에 하나만 new가 되어야 한다. (private 사용)
      private Singleton(){ }
      // 접근 가능한 통로를 한개만 열어준다.
      public static Singleton getInstance(){
          if(instance==null){
              instance = new Singleton();
          }
          return instance;
      }
  }
  ```


---

## Observer Pattern
  ### 1. Observer Pattern 이란
  - 상태를 가지고 있는 주제 객체와 상태의 변경을 알아야 하는 관찰 객체가 존재하며 이들의 관계는 1:1 혹은 1:N이 될 수 있는데 이 때, 객체들 사이에서 다양한 처리를 할 수 있도록 해주는 패턴
  - 한 객체의 상태가 바뀌면 그 객체에 의존하는 다른 객체들한테 연락이 가고 자동으로 내용이 갱신되는 방식으로 일대다(one-to-many) 의존성을 정의

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Java/Contents/DesignPattern/picture/observerPattern.png)

  ### 2. Observer Pattern 사용 예시
  - Interface를 만들고 이를 상속받아 반복문을 돌면서 사용

  ```Java
  public class DPMain {

  	public static void main(String[] args) {
  		Subject server = new Subject();
  		server.start();

  		ClientDeamon deamon = new ClientDeamon(server);
  		deamon.start();
  	}

  }

  class Client1 implements Subject.IObserver{
  	String title = "";
  	public Client1(String title) {
  		this.title = title;
  	}
  	@Override
  	public void noti() {
  		System.out.println("클라이언트 " + title + "에 변경사항이 반영됨");		
  	}

  	public void setTitle(String title) {

  	}
  }

  class Client2 implements Subject.IObserver{
  	String title = "";
  	public Client2(String title) {
  		this.title = title;
  	}
  	@Override
  	public void noti() {
  		System.out.println("클라이언트 " + title + "에 변경사항이 반영됨");		
  	}
  }

  class Subject extends Thread{

  	List<IObserver> clients = new ArrayList<>();

  	public void run() {
  		Random random = new Random();

  		while(true) {
  			for(IObserver observer : clients) {
  				observer.noti();
  			}
  			System.out.println("[Subject] 메세지를 전송하였습니다.");

  			// 비주기적 갱신을 위한 테스트 코드
  			try {
  				Thread.sleep((random.nextInt(10)+1)*1000);
  			} catch (InterruptedException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  		}
  	}

  	public interface IObserver{
  		public void noti();
  	}
  }

  /**
   * 클라이언트 등록을 위한 데몬 클래스
   */
  class ClientDeamon extends Thread{
  	Subject server;
  	public ClientDeamon(Subject server) {
  		this.server = server;
  	}
  	public void run() {
  		int count = 0;
  		while(true) {
  			if(count %2==0) {
  				server.clients.add(new Client1(count+""));
  			} else {
  				server.clients.add(new Client2(count+""));
  			}
  			count++;

  			try {
  				Thread.sleep(5000);
  			} catch (InterruptedException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  		}
  	}
  }

  ```

  > 결과

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Java/Contents/DesignPattern/picture/observerPatternResult.png)

---

## (추가 가능)
  ### 1. ? 이란
  - ㅇㅇ
