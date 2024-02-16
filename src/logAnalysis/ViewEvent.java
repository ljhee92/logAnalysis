package logAnalysis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewEvent extends WindowAdapter implements ActionListener {
	
	private ViewDesign vd;
	List<String> keyList;
	
	public ViewEvent(ViewDesign vd) {
		this.vd = vd;
	}	// ViewEvent

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == vd.getJbtnReport()) {
			System.out.println("버튼 클릭 테스트");
		}	// end if
		
		if(e.getSource() == vd.getJbtnExit()) {
			closeDialog();
		}	// end if
	}	// actionPerformed

	@Override
	public void windowOpened(WindowEvent e) {
		setResult();
	}	// windowOpened
	
	private void setResult() {
		StringBuilder sb = new StringBuilder();
		sb.append("1. 최다 사용 키의 이름과 횟수 : ").append("\n\n")
		.append("2. 브라우저별 접속 횟수와 비율 : ").append("\n\n")
		.append("3. 서비스를 성공적으로 수행한 횟수 : ").append("\n\n")
		.append("3-1. 서비스를 실패한 횟수 : ").append("\n\n")
		.append("4. 요청이 가장 많은 시간 : ").append("\n\n")
		.append("5. 비정상적인 요청이 발생한 횟수와 비율 : ").append("\n\n")
		.append("6. books에 대한 요청 URL 중 에러가 발생한 횟수와 비율 : ");
		vd.getJtaLog().setText(sb.toString());
		parseKey();
	}	// setResult
	
	private void parseKey() {
		String url, key;
		int indStart, indEnd;
		keyList = new ArrayList<String>();
		
		for(int i = 0; i < WorkEvent.dataList.size(); i++) {
			url = WorkEvent.dataList.get(i).get("url");
			indStart = url.indexOf("key=") + 4;
			indEnd = url.indexOf("&");
			if(url.contains("key=")) {
				key = url.substring(indStart, indEnd);
				keyList.add(key);
			}	// end if
		}	// end for
//		Set<String> keySet = new HashSet<String>(keyList);
//		for(String keyName : keySet) {
//			System.out.println(keyName + ": " + Collections.frequency(keyList, keySet));
//		}
	}	// parseKey
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		closeDialog();
	}	// windowClosing
	
	private void closeDialog() {
		vd.dispose();
	}	// closeDialog

}	// ViewEvent