package logAnalysis.design;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logAnalysis.event.ViewEvent;

@SuppressWarnings("serial")
public class ViewDesign extends JDialog {
	
	private JTextArea jtaLog;
	private JButton jbtnReport, jbtnExit;
	private String userID;
	private boolean userRWX;
	private JTextField jtfStart, jtfEnd;
	
	public ViewDesign(WorkDesign wd) {
		super(wd, "필요한 정보", true);
		this.userID = wd.getUserID();
		this.userRWX = wd.isUserRWX();
		this.jtfStart = wd.getJtfStart();
		this.jtfEnd = wd.getJtfEnd();
		
		setLayout(null);
		
		jtaLog = new JTextArea();
		jbtnReport = new JButton("파일 다운로드");
		jbtnExit = new JButton("종료");
		JScrollPane jsp = new JScrollPane(jtaLog);
		jsp.setBorder(new TitledBorder("결과물"));
		
		jsp.setBounds(10, 10, 460, 350);
		jtaLog.setEditable(false);
		jbtnReport.setBounds(100, 370, 120, 40);
		jbtnExit.setBounds(260, 370, 120, 40);
		
		add(jsp);
		add(jbtnReport);
		add(jbtnExit);
		
		ViewEvent ve = new ViewEvent(this);
		addWindowListener(ve);
		jbtnReport.addActionListener(ve);
		jbtnExit.addActionListener(ve);
		
		setBounds(wd.getX()+50, wd.getY()+50, 500, 470);
		setVisible(true);
//		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}	// ViewDesign
	
	public JTextField getJtfStart() {
		return jtfStart;
	}

	public JTextField getJtfEnd() {
		return jtfEnd;
	}

	public JTextArea getJtaLog() {
		return jtaLog;
	}

	public JButton getJbtnReport() {
		return jbtnReport;
	}

	public JButton getJbtnExit() {
		return jbtnExit;
	}

	public String getUserID() {
		return userID;
	}

	public boolean isUserRWX() {
		return userRWX;
	}

}	// ViewDesign