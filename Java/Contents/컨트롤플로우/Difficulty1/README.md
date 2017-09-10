# 난이도 1 문제
* 그림</br>
![](https://github.com/Lee-KyungSeok/ControlFlowExample/blob/master/Difficulty1/difficulty_1.PNG)

## __풀이__

1. 세로 라인 수 만큼 반복
2. 라인 번호 만큼 값(ex>A)을 출력

#### __소스 코드__
``` java
public class Test01 {

	public void run(String mark, int lines) {

		for(int index=0 ; index<lines ; index=index+1) {
			for(int innerIndex=0 ; innerIndex<index+1 ; innerIndex++) {
				System.out.print(mark);
			}
			System.out.println("");
		}
	}
}
```
