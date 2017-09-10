import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class Model {
	// 내용 구분자 지정
	private final String COLUM_SEP ="::";
	
	private final String DB_DIR = "C:\\workspaces\\java\\Memo2\\database";//윈도우는 역방향 슬래시, 맥은 정방향 슬래시
	private final String DB_FILE = "memo.txt";
	private final String DB_FILE_INDEX = "Index.txt";
	
	private File database = null;
	private File indexDatabase = null;
	
	// 전체 메모를 저정하는 저장소
	// 여러개의 객체를 저장하는 list가 된다.
	ArrayList<Memo> list = new ArrayList<>();
	
	//생성자
	public Model() {
		File dir = new File(DB_DIR);
		// 디렉토리 존재유무
		if(!dir.exists()) {
			dir.mkdirs(); 
		}
		File file = new File(DB_DIR + File.separator + DB_FILE);
		File fileIndex = new File(DB_DIR + File.separator + DB_FILE_INDEX);
		
		//파일의 존재유무 파악
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
	
	// rowSum : 메모 내용을  받아 row 형태로 반환
	public String rowSum(Memo memo) {
		String row;
		row =  memo.no + COLUM_SEP 
				+ memo.name + COLUM_SEP 
				+ memo.content + COLUM_SEP 
				+ memo.datetime + "\n";
		return row;
	}
	
	//savingMemo : 메모 내용을 받아 파일에 저장
	public boolean savingMemo(File file, boolean adding, String contents) {
		boolean check = false;
		// 출력스트림 활용
		FileOutputStream fos = null;
		try {
			// 스트림을 연다 -> 스트림 중간 처리 -> 버퍼처리
			fos = new FileOutputStream(file, adding);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.append(contents);
			bw.flush();
			check = true;
			
		} catch(Exception e){
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
	
	// loadingMemo : 메모장에 저장된 내용을 List로 불러옴
	public ArrayList<String> loadingMemo(File file) {
		ArrayList<String> rowContent = new ArrayList<>();
		
		// 스트림을 연다 -> 스트림 중간 처리 -> 버퍼처리
		try(FileInputStream fis = new FileInputStream(file)){
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String row;
			
			while((row = br.readLine()) != null) {
				rowContent.add(row);
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return rowContent;
	}
	
	//showIndex : 마지막에 저장된 index를 불러옴
	public int showIndex() {
		int index=0 ; 
		
		// 저장된 index들 load
		ArrayList<String> indexList = loadingMemo(indexDatabase);
		
		// 마지막에 저장된 index를 불러옴
		for(String row : indexList) {
			index = Integer.parseInt(row);
		}
		return index;
	}
	
	// showList : 목록을 가져옴 
	public ArrayList<Memo> showList(){
		
		// 데이터가 중복해서 쌓이지 않도록 저장소를 지워주는 작업이 필요한 경우가 있다.
		list.clear();
		
		//row 별로 정리된 list를 불러온다
		ArrayList<String> rowList = loadingMemo(database);
		
		// list를 memo객체에 맞게 메모리에 저장한다.
		for(String row : rowList) {
			String tempRow[] = row.split(COLUM_SEP);
			Memo memo = new Memo();
			memo.no = Integer.parseInt(tempRow[0]);
			memo.name = tempRow[1];
			memo.content = tempRow[2];
			memo.datetime = Long.parseLong(tempRow[3]);

			list.add(memo);	
		}
		return list;
	}
	
	// create
	public void create(Memo memo) {
		
		//글번호 불러오기
		memo.no = showIndex()+1;
		
		// 저장할 내용을 구분자로 분리하여 한줄의 문자열로 바꾼다.
		String row = rowSum(memo);
		// 전체 메모장에 메모 저장
		savingMemo(database, true, row);
		
		//저장할 인덱스를 구분
		String indexString = Integer.toString(memo.no)+"\n";
		// index 메모장에 인덱스 저장
		savingMemo(indexDatabase, true, indexString);
	}
	
	//read
	public Memo read(int no) {
		
		//메모리 리스트 초기화 및 메모장으로부터 불러옴
		list.clear();
		showList();
		
		// no에 맞는 memo 찾기
		for(Memo memo : list) {
			if(memo.no == no) {
				return memo;
			}
		}
		// 찾는 memo가 없는 경우 null값 반환
		return null;
	}
	
	//update
	public boolean update(int no, Memo memoTemp) {		
		boolean check=false;
		boolean checkTemp = false;
		boolean checkInput = false;
		
		// 메모리 리스트 초기화 및 메모장으로부터 불러옴
		list.clear();
		showList();
		
		// No에 맞는 메모를 찾고 수정
		String row="";
		for(Memo memo : list) {
			if(memo.no == no) {
				memo.name = memoTemp.name;
				memo.content = memoTemp.content;
				checkTemp=true;
			}
			//row에 글을 추가
			row = row +rowSum(memo);
		}
		// 전체 메모장에 메모 저장
		checkInput = savingMemo(database, false, row);
		
		// 수정 완료사항 체크
		if(checkTemp==true && checkInput==true) {
			check = true;
		}
		return check;
	}
	
	//delete
	public boolean delete(int no) {
		boolean check = false;
		boolean checkTemp = false;
		boolean checkInput = false;
		
		// 메모리 리스트 초기화 및 메모장으로부터 불러옴
		list.clear();
		showList();
		
		// No에 맞는 메모를 찾고 삭제
		String row = "";
		Iterator<Memo> iter = list.iterator();
		while(iter.hasNext()) {
			Memo memo = iter.next();
			
			// 삭제할 memo 내용 제외하고 row에 모든 memo를 추가
			if(memo.no == no) {
				checkTemp = true;
			} else {
				row = row +rowSum(memo);
			}			
		}
		// 전체 메모장에 메모 저장
		checkInput = savingMemo(database, false, row);
		
		// 수정 완료사항 체크
		if(checkTemp==true && checkInput) {
			check = true;
		}
		return check;
	}
}