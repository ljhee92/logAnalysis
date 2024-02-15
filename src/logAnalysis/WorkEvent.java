package logAnalysis;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkEvent extends WindowAdapter implements ActionListener {
	
	private WorkDesign wd;
	private FileDialog fdOpen;
	private List<String> lineList;
	private Map<String, String> splitedData;
	
	public WorkEvent(WorkDesign wd) {
		this.wd = wd;
	}	// WorkEvent

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == wd.getJbtnView()) {
			new ViewDesign(wd);
		}	// end if
		if(e.getSource() == wd.getJbtnFileSelect()) {
			openFile();
		}	// end if
	}	// actionPerformed
	
	private void openFile() {
		fdOpen = new FileDialog(wd, "파일 열기", FileDialog.LOAD);
		fdOpen.setVisible(true);
		
		if(fdOpen.getDirectory() == null) {
			return;
		}	// end if
		
		try {
			readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}	// end catch
	}	// openFile
	
	public void readFile() throws IOException {
		File readFile = new File(fdOpen.getDirectory() + fdOpen.getFile());
		
		BufferedReader br = null;
		String readData = "";
		int cntLine = 0;
		try {
			br = new BufferedReader(new FileReader(readFile));
			lineList = new ArrayList<String>();
			
			while((readData = br.readLine()) != null) {
				lineList.add(readData);	// 리스트에 줄단위로 저장
				wd.getJtaContents().append(++cntLine + ". " + readData + "\n"); // jta에 출력
			}	// end while
			splitData();
			wd.getJlAll().setText("불러온 라인 수: " + cntLine);
			wd.getJtfStart().setText("1");
			wd.getJtfEnd().setText(String.valueOf(cntLine));
		} finally {
			if(br != null) {
				br.close();
			}	// end if
		}	// end finally
	}	// readFile
	
	/**
	 * 기능 : List에 저장된 linedata를 []를 기준으로 자르는 일
	 * 1. 라인데이터 하나 불러오기
	 * 2. 라인 맨 앞과 뒤의 [] 자르기
	 * 3. '][' 기준으로 데이터 나누기
	 * 4. 나눈 데이터 맵에 저장하기
	 * 작성자 :
	 * 작성 날짜 :
	 */
	public void splitData() {
		String test = "";
		String[] splitArr = null;
		splitedData = new HashMap<String, String>();
		
		for(String temp : lineList) {
			test = temp.substring(1, temp.length()-1);
			splitArr = test.split("\\]\\[");
		}	// end for
		splitedData.put("code", splitArr[0]);
		
		System.out.println(splitedData.get("code"));
		
	}	// splitData
	
	@Override
	public void windowClosing(WindowEvent e) {
		closeWd();
	}	// windowClosing
	
	private void closeWd() {
		wd.dispose();
	}	// closeWd

}	// class