package logAnalysis;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WorkEvent extends WindowAdapter implements ActionListener {
	
	private WorkDesign wd;
	
	public WorkEvent(WorkDesign wd) {
		this.wd = wd;
	}	// WorkEvent

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == wd.getJbtnView()) {
			System.out.println("버튼 클릭 테스트");
		}	// end if
		if(e.getSource() == wd.getJbtnFileSelect()) {
			openFile();
		}	// end if
	}	// actionPerformed
	
	private void openFile() {
		FileDialog fdOpen = new FileDialog(wd, "파일 열기", FileDialog.LOAD);
		fdOpen.setVisible(true);
		
		if(fdOpen.getDirectory() == null) {
			return;
		}	// end if
		
		
	}	// openFile

	@Override
	public void windowClosing(WindowEvent e) {
		closeWd();
	}	// windowClosing
	
	private void closeWd() {
		wd.dispose();
	}	// closeWd

}	// class