package logAnalysis.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import logAnalysis.design.ViewDesign;

public class ViewEvent extends WindowAdapter implements ActionListener {
	
	private ViewDesign vd;
	private String url, key, code;
	private int indStart, indEnd, frequency, maxFrequency, totalCodeList;
	private double ratio;
	private DecimalFormat decimalformat = new DecimalFormat("0.00");
	private List<String> codeList;
	
	public ViewEvent(ViewDesign vd) {
		this.vd = vd;
	}	// ViewEvent

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == vd.getJbtnReport()) {
			if(!vd.isUserRWX()) {
				JOptionPane.showMessageDialog(vd, vd.getUserID() + "계정은 파일을 다운로드할 수 없습니다.");
				return;
			}	// end if
			saveFile();
		}	// end if
		
		if(e.getSource() == vd.getJbtnExit()) {
			closeDialog();
		}	// end if
	}	// actionPerformed
	
	private void saveFile() {
		File saveFile = new File("/Users/juhee/eclipse-workspace/logAnalysis/report"); 
		Calendar cal = Calendar.getInstance();
		saveFile.mkdirs();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try(FileWriter fw = new FileWriter(saveFile + "/report_" + cal.getTimeInMillis() + ".dat")){
			fw.write("---------------------------------------------------------\n("
			+ WorkEvent.readFile.getName() + ") log (생성된 날짜 " + sdf.format(cal.getTimeInMillis()) + ")\n"
			+ "---------------------------------------------------------\n" + vd.getJtaLog().getText());
			fw.flush();
			JOptionPane.showMessageDialog(vd, "파일이 저장되었습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}	// end catch
	}	// saveFile

	@Override
	public void windowOpened(WindowEvent e) {
		setResult();
	}	// windowOpened
	
	private void setResult() {
		StringBuilder sb = new StringBuilder();
		sb.append("1. 최다 사용 키의 이름과 횟수 : ").append(parseMaxKey()).append("\n\n")
		.append("2. 브라우저별 접속 횟수와 비율 : ").append("\n").append(parseBrowser()).append("\n")
		.append("3. 서비스를 성공적으로 수행한 횟수 : ").append(parseCode("200")).append("\n\n")
		.append("3-1. 서비스를 실패한 횟수 : ").append(parseCode("404")).append("\n\n")
		.append("4. 요청이 가장 많은 시간 : ").append(parseTime()).append("\n\n")
		.append("5. 비정상적인 요청이 발생한 횟수와 비율 : ").append(parseCode("403")).append("\n\n")
		.append("6. books에 대한 요청 URL 중 에러가 발생한 횟수와 비율 : ").append(parseBooksError());
		vd.getJtaLog().setText(sb.toString());
	}	// setResult
	
	/**
	 * 기능 : 최다 사용 키의 이름과 횟수를 얻는 일
	 * 1. WorkEvent class의 dataList(줄별 Map이 들어있는 List)에서 url만 가져오고,
	 * 2. url 중 key= 가 붙어 있다면 key= 부터 & 까지 텍스트를 잘라 키의 이름들을 얻는다.
	 * 3. 얻은 키의 이름들을 keyList에 저장하고,
	 * 4. 키별 빈도를 계산하여 "키 이름 : 빈도"로 frequencyMap에 저장한다.
	 * 5. frequencyMap의 빈도 중 최대값을 찾고 Map.Entry와 비교하여 텍스트값을 반환한다. 
	 * @return 최다 사용 키, 횟수
	 */
	private String parseMaxKey() {
		List<String> keyList = new ArrayList<String>();
		for(int i = Integer.parseInt(vd.getJtfStart().getText())-1; i < Integer.parseInt(vd.getJtfEnd().getText()); i++) {
			url = WorkEvent.dataList.get(i).get("url");
			indStart = url.indexOf("key=") + 4;
			indEnd = url.indexOf("&");
			if(url.contains("key=")) {
				key = url.substring(indStart, indEnd);
				keyList.add(key);
			}	// end if
		}	// end for
		
		Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
		// keyName의 빈도 계산
		for(String keyName : keyList) {
			frequencyMap.put(keyName, frequencyMap.getOrDefault(keyName, 0) + 1);
		}	// end for
		
		// 빈도 중 최대값 찾기
		maxFrequency = Collections.max(frequencyMap.values());
		// Map.Entry로 키쌍(키 이름-최대 빈도) 비교
		for(Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
			if(entry.getValue() == maxFrequency) {
				return entry.getKey() + ", " + entry.getValue() + "회";
			}	// end if
		}	// end for
		return "";
	}	// parseKey
	
	private String parseBrowser() {
		String browser;
		List<String> browserList = new ArrayList<String>();
		for(int i = Integer.parseInt(vd.getJtfStart().getText())-1; i < Integer.parseInt(vd.getJtfEnd().getText()); i++) {
			browser = WorkEvent.dataList.get(i).get("browser");
			browserList.add(browser);
		}	// end for
		
		int totalBrowserList = browserList.size();
		List<String> browserResult = new ArrayList<String>();
		Set<String> browserSet = new HashSet<String>(browserList);
		for(String browserName : browserSet) {
            frequency = Collections.frequency(browserList, browserName);
            ratio = (double)frequency / totalBrowserList * 100;
            browserResult.add(" - " + browserName + " : " + frequency + "회 (" + decimalformat.format(ratio) + "%)");
        }	// end for
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < browserResult.size(); i++) {
			sb.append(browserResult.get(i)).append("\n");
		}	// end for
		return sb.toString();
	}	// parseBrowser
	
	private String parseCode(String codeNum) {
		codeList = new ArrayList<String>();
		for(int i = Integer.parseInt(vd.getJtfStart().getText())-1; i < Integer.parseInt(vd.getJtfEnd().getText()); i++) {
			code = WorkEvent.dataList.get(i).get("code");
			codeList.add(code);
		}	// end for
		
		totalCodeList = codeList.size();
		Set<String> codeSet = new HashSet<String>(codeList);
		Map<String, Integer> codeMap = new HashMap<String, Integer>();
		for(String codeName : codeSet) {
			frequency = Collections.frequency(codeList, codeName);
			codeMap.put(codeName, frequency);
		}	// end for
		if(codeMap.get(codeNum) == null) {
			ratio = 0.00;
			if(codeNum.equals("200") || codeNum.equals("500") || codeNum.equals("404")) {
				return "0회";
			} else if(codeNum.equals("403")) {
				return "0회 (" + decimalformat.format(ratio) + "%)";
			} else {
				System.err.println("코드 번호를 확인해주세요.");
			}	// end else
		} else {
			ratio = (double)codeMap.get(codeNum) / totalCodeList * 100;
			if(codeNum.equals("200")) {
				return codeMap.get("200") + "회";
			} else if(codeNum.equals("500")) {
				return codeMap.get("500") + "회";
			} else if(codeNum.equals("403")) {
				return codeMap.get("403") + "회 (" + decimalformat.format(ratio) + "%)";
			} else if(codeNum.equals("404")) {
				return codeMap.get("404") + "회";
			} else {
				System.err.println("코드 번호를 확인해주세요.");
			}	// end else
		}	// end else
		return "";
	}	// parseCode
	
	private String parseTime() {
		String date, time;
		List<String> dateList = new ArrayList<String>();
		for(int i = Integer.parseInt(vd.getJtfStart().getText())-1; i < Integer.parseInt(vd.getJtfEnd().getText()); i++) {
			date = WorkEvent.dataList.get(i).get("date");
			indStart = date.indexOf(" ") + 1;
			indEnd = date.indexOf(":");
			time = date.substring(indStart, indEnd);
			dateList.add(time);
		}	// end for\
		
		Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
		for(String whatTime : dateList) {
			frequencyMap.put(whatTime, frequencyMap.getOrDefault(whatTime, 0) + 1);
		}	// end for
		
		maxFrequency = Collections.max(frequencyMap.values());
		for(Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
			if(entry.getValue() == maxFrequency) {
				return entry.getKey() + "시";
			}	// end if
		}	// end for
		return "";
	}	// parseTime
	
	private String parseBooksError() {
		codeList = new ArrayList<String>();
		for(int i = Integer.parseInt(vd.getJtfStart().getText())-1; i < Integer.parseInt(vd.getJtfEnd().getText()); i++) {
			url = WorkEvent.dataList.get(i).get("url");
			code = WorkEvent.dataList.get(i).get("code");
			if(url.contains("books?")) {
				codeList.add(code);
			}	// end if
		}	// end for
		totalCodeList = codeList.size();
		Set<String> codeSet = new HashSet<String>(codeList);
		Map<String, Integer> codeMap = new HashMap<String, Integer>();
		for(String codeName : codeSet) {
			frequency = Collections.frequency(codeList, codeName);
			codeMap.put(codeName, frequency);
		}	// end for
		if(codeMap.get("500") == null) {
			ratio = 0.00;
			return "0회 (" + decimalformat.format(ratio) + "%)";
		}	// end if
		ratio = (double)codeMap.get("500") / totalCodeList * 100;
		return codeMap.get("500") + "회 (" + decimalformat.format(ratio) + "%)";
	}	// parseBooksError
	
	@Override
	public void windowClosing(WindowEvent e) {
		closeDialog();
	}	// windowClosing
	
	private void closeDialog() {
		vd.dispose();
	}	// closeDialog

}	// ViewEvent