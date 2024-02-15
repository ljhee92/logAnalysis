package logAnalysis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewEvent extends WindowAdapter implements ActionListener {
	
	private ViewDesign vd;
	
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

	}	// setResult

	@Override
	public void windowClosing(WindowEvent e) {
		closeDialog();
	}	// windowClosing
	
	private void closeDialog() {
		vd.dispose();
	}	// closeDialog

}	// ViewEvent