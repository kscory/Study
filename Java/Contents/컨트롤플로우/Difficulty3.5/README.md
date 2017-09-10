# 난이도 3.5 문제
* 그림</br>
![](https://github.com/Lee-KyungSeok/ControlFlowExample/blob/master/Difficulty3.5/difficulty_3.5.PNG)

## __풀이__

1. 라인수만큼 반복
2. 시작할 때 공백 ("전체 line개수-line번호-1" 개)
3. 왼쪽 값(가운데 포함) 표시
4. 값 사이 공백 (0번째 line 제외하고 "line개수*2-1" 개)
5. 오른쪽 값 표시(0번째 제외)

#### __소스 코드__
``` java
public class Test035 {

	public void run(String mark, int lines) {

		//라인수만큼 반복
		for(int index=0 ; index<lines ; index=index+1) {

			//시작할 때 공백 표시
			for(int innerSpaceIndex=0 ; innerSpaceIndex < lines-index-1 ; innerSpaceIndex++) {				
				System.out.print(" ");
			}

			// 왼쪽 mark
			System.out.print(mark);

			//값 사이 공백 표시
			for(int innerMarkIndex=0 ; innerMarkIndex<index*2-1 ; innerMarkIndex++) {
				System.out.print(" ");
			}

			//오른쪽 mark
			if(index==0) {
				System.out.println("");
			} else {
				System.out.println(mark);				
			}
		}
	}
}
```
