# 난이도 2 문제
* 그림</br>
![](https://github.com/Lee-KyungSeok/ControlFlowExample/blob/master/Difficulty2/difficulty_2.PNG)

## __풀이__

1. 라인수만큼 반복
2. 시작할 때 공백 ("전체 line개수 - line번호 - 1" 개)
3. 값 표시 ("line번호+1" 개)

#### __소스 코드__
``` java
public class Test02 {

	public void run(String mark, int lines) {

		for(int index=0 ; index<lines ; index=index+1) {

			for(int innerSpaceIndex=0 ; innerSpaceIndex < lines-index-1 ; innerSpaceIndex++) {
				System.out.print(" ");
			}
			for(int innerMarkIndex=0 ; innerMarkIndex<index+1 ; innerMarkIndex=innerMarkIndex+1) {
				System.out.print(mark);
			}
			System.out.println("");

		}
	}
}
```
