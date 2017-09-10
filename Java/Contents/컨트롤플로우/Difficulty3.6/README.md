# 난이도 3.6 문제
* 그림</br>
![](https://github.com/Lee-KyungSeok/ControlFlowExample/blob/master/Difficulty3.6/difficulty_3.6.PNG)

## __풀이__

1. "라인수-1" 만큼 반복
2. 시작할 때 공백 ("전체 line개수-line번호-1" 개)
3. 왼쪽 값(가운데 포함) 표시
4. 값 사이 공백 (0번째 line 제외하고 "line개수*2-1" 개)
5. 오른쪽 값 표시(0번째 제외)
6. 마지막 줄 값 표시 ("line*2 -1" 개)

#### __소스 코드__
``` java
public class Test036 {

	public void run(String mark, int lines) {

		//라인수 만큼 반복
		for(int index=0 ; index<lines-1 ; index=index+1) {

			// 시작 공백 표시
			for(int innerSpaceIndex=0 ; innerSpaceIndex < lines-index-1 ; innerSpaceIndex++) {				
				System.out.print(" ");
			}

			//왼쪽 값 mark
			System.out.print(mark);

			//가운데 공백 표시
			for(int innerMarkIndex=0 ; innerMarkIndex<index*2-1 ; innerMarkIndex++) {
				System.out.print(" ");
			}

			//오른쪽 갑 mark
			if(index==0) {
				System.out.println("");
			} else {
				System.out.println(mark);				
			}			
		}

		//마지막 라인 값 mark
		for(int finalIndex=0 ; finalIndex < lines*2-1 ; finalIndex++) {
			System.out.print(mark);
		}
	}
}
```
