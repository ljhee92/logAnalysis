package logAnalysis.event;

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

import javax.swing.JOptionPane;

import logAnalysis.design.ViewDesign;
import logAnalysis.design.WorkDesign;

public class WorkEvent extends WindowAdapter implements ActionListener {
	
	private WorkDesign wd;
	private FileDialog fdOpen;
	public static File readFile;
	private List<String> lineList;
	private Map<String, String> splitedData;
	public static List<Map<String, String>> dataList;
	String jtfStart, jtfEnd;
	
	public WorkEvent(WorkDesign wd) {
		this.wd = wd;
	}	// WorkEvent

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == wd.getJbtnView()) {
			if(readFile == null) {
				JOptionPane.showMessageDialog(wd, "파일을 선택해주세요.");
				return;
			}	// end if
			if(chkNum()) {
				new ViewDesign(wd);
			}	// end if
		}	// end if
		if(e.getSource() == wd.getJbtnFileSelect()) {
			openFile();
		}	// end if
	}	// actionPerformed
	
	private boolean chkNum() {
		jtfStart = wd.getJtfStart().getText();
		jtfEnd = wd.getJtfEnd().getText();
		String jlAll = wd.getJlAll().getText().substring(wd.getJlAll().getText().indexOf(":") + 2);
		if(jtfStart.isEmpty() || jtfEnd.isEmpty()) {
			JOptionPane.showMessageDialog(wd, "시작 라인 또는 마지막 라인을 입력해주세요.");
			return false;
		} else if(!jtfStart.matches("\\d+") || !jtfEnd.matches("\\d+")) {
			JOptionPane.showMessageDialog(wd, "시작 라인 또는 마지막 라인은 숫자만 입력 가능합니다.");
			return false;
		} else if(Integer.parseInt(jtfStart) <= 0 || Integer.parseInt(jtfEnd) <= 0) {
			JOptionPane.showMessageDialog(wd, "시작 라인 또는 마지막 라인을 0보다 크게 입력해주세요.");
			return false;
		} else if(Integer.parseInt(jtfStart) > Integer.parseInt(jtfEnd)) {
			JOptionPane.showMessageDialog(wd, "시작 라인을 마지막 라인보다 작게 입력해주세요.");
			return false;
		} else if(Integer.parseInt(jtfEnd) > Integer.parseInt(jlAll)) {
			JOptionPane.showMessageDialog(wd, "마지막 라인을 불러온 라인보다 작게 입력해주세요.");
			return false;
		} else {
			return true;
		}	// end else
	}	// chkNum
	
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
		readFile = new File(fdOpen.getDirectory() + fdOpen.getFile());
		
		if(!fdOpen.getFile().contains(".log")) {
			JOptionPane.showMessageDialog(wd, "로그 형식의 파일을 선택해주세요.");
			return;	
		}	// end if
		
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
			wd.setTitle(readFile.toString());
			wd.getJlAll().setText("불러온 라인 수: " + cntLine);
			wd.getJtfStart().setText("1");
			wd.getJtfEnd().setText(String.valueOf(cntLine));
			splitData();
		} finally {
			if(br != null) {
				br.close();
			}	// end if
		}	// end finally
	}	// readFile
	
	/**
	 * 기능 : List에 저장된 linedata를 대괄호를 기준으로 자르는 일
	 * 1. 라인데이터 하나 불러오기
	 * 2. 라인 맨 앞과 뒤의 [] 자르기
	 * 3. '][' 기준으로 데이터 나누기
	 * 4. 나눈 데이터 맵에 저장하기
	 * 5. 맵을 다시 List에 저장하기
	 * 작성자 : (추후)
	 * 작성 날짜 : (추후)
	 */
	public void splitData() {
		String splitLine = "";
		String[] splitArr = null;
		dataList = new ArrayList<Map<String, String>>();
		jtfStart = wd.getJtfStart().getText();
		jtfEnd = wd.getJtfEnd().getText();
		
		for(String temp : lineList) {	// 라인데이터 불러오기
			splitedData = new HashMap<String, String>();
			splitLine = temp.substring(1, temp.length() - 1);	// 맨 앞, 뒤 [] 자르기
			splitArr = splitLine.split("\\]\\[");	// ][ 기준으로 나누기
			splitedData.put("code", splitArr[0]);	// 맵에 저장
			splitedData.put("url", splitArr[1]);
			splitedData.put("browser", splitArr[2]);
			splitedData.put("date", splitArr[3]);
			dataList.add(splitedData);	// 맵을 리스트에 저장
		}	// end for
	}	// splitData
	
	@Override
	public void windowClosing(WindowEvent e) {
		closeWd();
	}	// windowClosing
	
	private void closeWd() {
		wd.dispose();
	}	// closeWd

}	// class