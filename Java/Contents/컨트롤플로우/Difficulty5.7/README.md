# 난이도 5.7 문제
* 그림</br>
![](https://github.com/Lee-KyungSeok/ControlFlowExample/blob/master/Difficulty5.7/difficulty_5.7.PNG)

## __풀이__

1. 첫줄 값 표시("2*입력값-1" 개)
2. "라인수-2"(2*입력값-3) 만큼 반복 (3~6번)
3. 왼쪽값 표시
5. 값 사이 공백("2*입력값-3" 개)
6. 오른쪽 값 표시
7. 마지막줄 값 표시("2*입력값-1" 개)

#### __소스 코드__
``` java
public class Test057 {

	public void run(String mark, int lines) {

		//첫줄 출력
		for(int firstIndex=0 ; firstIndex<lines*2-1 ; firstIndex++) {
			System.out.print(mark);
		}
		System.out.println("");

		//가운데 출력
		for(int middleLine=0; middleLine<lines*2-3 ; middleLine++) {
			System.out.print(mark);

			for(int middleSpaceIndex=0; middleSpaceIndex<lines*2-3 ; middleSpaceIndex++) {
				System.out.printf(" ");
			}
			System.out.println(mark);
		}

		//마지막 출력
		for(int finalIndex=0 ; finalIndex<lines*2-1 ; finalIndex++) {
			System.out.print(mark);
		}
	}
}
```
