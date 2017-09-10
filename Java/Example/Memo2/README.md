# File을 활용한 MEMO 만들기
#### ※[MVC 패턴을 활용한 Memo 예제](https://github.com/Lee-KyungSeok/MemoExample) 의 활용이므로 이전문서 참고

## __문제__
메모를 파일로 만들어서 저장

## __설명 (Model만 설명)__
### ● Model
#### 0. 클래스에서 사용되는 값 설정
* 파일 디렉토리 / 파일 이름 / 메모리 저장소 / 구분자 설정

```java
private final String COLUM_SEP ="::";

private final String DB_DIR = "D:\\workspaces\\java\\database";
private final String DB_FILE = "memo.txt";
private final String DB_FILE_INDEX = "Index.txt";

private File database = null;
private File indexDatabase = null;

ArrayList<Memo> list = new ArrayList<>();
```

#### 1. 생성자 (초기화 시 반드시 실행됨)
* 메모 file과 메모내의 내용의 Index를 담는 file을 생성한다.

```java
public Model() {
	File dir = new File(DB_DIR);
	// 디렉토리 존재유무
	if(!dir.exists()) {
		dir.mkdirs();
	}
	File file = new File(DB_DIR + File.separator + DB_FILE);
	File fileIndex = new File(DB_DIR + File.separator + DB_FILE_INDEX);

	//파일의 존재유무
	if(!file.exists()) {
		try {
			file.createNewFile();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	if(!fileIndex.exists()) {
		try {
			fileIndex.createNewFile();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	database = file;
	indexDatabase = fileIndex;
}
```

#### 2. showIndex
* 저장해야 할 마지막 Index를 불러오는 메소드

```java
public int showIndex() {
	int index=0 ;

	FileInputStream fis = null;
	try {
		fis = new FileInputStream(indexDatabase);
		InputStreamReader isr = new InputStreamReader(fis,"MS949");
		BufferedReader br = new BufferedReader(isr);
		String row;

		while((row = br.readLine()) != null) {
			index = Integer.parseInt(row);
		}			
	} catch(Exception e) {
		e.printStackTrace();
	} finally {
		if(fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
				}
		}
	}
	return index;
}
```

#### 3. create
* file에 입력한 memo를 저장

```java
public void create(Memo memo) {
  //글번호 저장
  memo.no = showIndex()+1;

  FileOutputStream fos =null;
  try {
    // 저장할 내용을 구분자로 분리하여 한줄의 문자열로 바꾼다.
    String row = memo.no + COLUM_SEP
        + memo.name + COLUM_SEP
        + memo.content + COLUM_SEP
        + memo.datetime + "\n";

    // 출력 스트림 활용
    fos = new FileOutputStream(database, true);
    OutputStreamWriter osw = new OutputStreamWriter(fos);
    BufferedWriter bw = new BufferedWriter(osw);
    bw.append(row);
    bw.flush();

  } catch (Exception e) { /
    e.printStackTrace();
  } finally {
    if(fos != null) {
      try {
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
```

#### 4. showList
* file에 저장되어 있는 모든 memo를 보여준다.

```java
public ArrayList<Memo> showList(){

  // 데이터가 중복해서 쌓이지 않도록 저장소를 지워주는 작업이 필요한 경우가 있다.
  list.clear();

  // 입력 스트림 활용
  try(FileInputStream fis = new FileInputStream(database)){
    InputStreamReader isr = new InputStreamReader(fis, "MS949");
    BufferedReader br = new BufferedReader(isr);

    // 모든 목록을 line별로 불러온다.
    String row;
    while( (row = br.readLine()) != null ) {
      String tempRow[] = row.split(COLUM_SEP);
      Memo memo = new Memo();
      memo.no = Integer.parseInt(tempRow[0]);
      memo.name = tempRow[1];
      memo.content = tempRow[2];
      memo.datetime = Long.parseLong(tempRow[3]);

      list.add(memo);

    }
  } catch(Exception e) {
    e.printStackTrace();
  }
  return list;
}
```

#### 5. read
* no를 받아 알맞은 memo를 file에서 불러온다.

```java
public Memo read(int no) {

  //list를 초기화하고 목록을 불러와 no에 맞는 memo를 찾는다.
  list.clear();
  showList();
  for(Memo memo : list) {
    if(memo.no == no) {
      return memo;
    }
  }
  return null;
}
```

#### 6. update
* no를 받아 알맞은 memo를 file에서 불러와 그 부분을 수정한다.
* 특정 부분을 수정한 뒤 전체 메모를 덮어 씌우는 방식 사용

```java
public boolean update(int no, Memo memoTemp) {		
  boolean check=false;
  boolean checkTemp = false;
  list.clear();
  showList();

  String row="";
  for(Memo memo : list) {
    if(memo.no == no) {
      //수정할 memo에 글 하나를 저장한 메모리를 덮어씌워 수정
      memo.name = memoTemp.name;
      memo.content = memoTemp.content;
      checkTemp=true;
    }

    // 저장할 메모내용을 하나의 String에 저장한다.
    row = row
      + memo.no + COLUM_SEP
      + memo.name + COLUM_SEP
      + memo.content + COLUM_SEP
      + memo.datetime + "\n";
  }		

  FileOutputStream fos =null;
  try {
    // 출력 스트림 활용 (메모를 전부 덮어 씌우므로 false 활용)
    fos = new FileOutputStream(database, false);
    OutputStreamWriter osw = new OutputStreamWriter(fos);
    BufferedWriter bw = new BufferedWriter(osw);
    bw.append(row);
    bw.flush();
    // 수정 완료시 true 값 반환
    if(checkTemp ==true){
      check = true;
    }
  } catch (Exception e) {
    e.printStackTrace();
  } finally {
    if(fos != null) {
      try {
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  return check;
}
```

#### 7. delete
* no를 받아 알맞은 memo를 file에서 불러와 그 메모를 삭제한다.
* 삭제한 메모를 하나의 변수에 넣어 덮어 씌운다.

```java
public boolean delete(int no) {
  boolean check = false;
  boolean checkTemp = false;

  list.clear();
  showList();

  //글번호를 받아 특정 memo Search		
  String row = "";
  // list에서 삭제시 index오류가 발생할 수 있으므로 iterator 활용
  Iterator<Memo> iter = list.iterator();
  while(iter.hasNext()) {
    Memo memo = iter.next();

    if(memo.no == no) {
      //발견한 특정 memo 삭제
      checkTemp = true;
    } else {
      row = row
          + memo.no + COLUM_SEP
          + memo.name + COLUM_SEP
          + memo.content + COLUM_SEP
          + memo.datetime + "\n";
    }			
  }

  FileOutputStream fos =null;
  try {
    // 출력 스트림 활용
    fos = new FileOutputStream(database, false);
    OutputStreamWriter osw = new OutputStreamWriter(fos);
    BufferedWriter bw = new BufferedWriter(osw);
    bw.append(row);
    bw.flush();
    if(checkTemp ==true){
      check = true;
    }
  } catch (Exception e) {
    e.printStackTrace();
  } finally {
    if(fos != null) {
      try {
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  return check;
}
```

## __Model 전체 소스 코드 링크__
### [코드 보기](https://github.com/Lee-KyungSeok/Study/blob/master/Java/Example/Memo2/src/Model.java)
