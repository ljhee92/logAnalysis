package logAnalysis;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class ViewDesign extends JDialog {
	
	private JTextArea jtaLog;
	private JButton jbtnReport, jbtnExit;
	
	public ViewDesign(WorkDesign wd) {
		super(wd, "필요한 정보", true);
		
		setLayout(null);
		
		jtaLog = new JTextArea();
		jbtnReport = new JButton("파일 다운로드");
		jbtnExit = new JButton("종료");
		JScrollPane jsp = new JScrollPane(jtaLog);
		jsp.setBorder(new TitledBorder("결과물"));
		
		jsp.setBounds(10, 10, 460, 350);
		jtaLog.setEditable(false);
		jbtnReport.setBounds(90, 370, 120, 40);
		jbtnExit.setBounds(230, 370, 120, 40);
		
		add(jsp);
		add(jbtnReport);
		add(jbtnExit);
		
		ViewEvent ve = new ViewEvent(this);
		addWindowListener(ve);
		jbtnReport.addActionListener(ve);
		jbtnExit.addActionListener(ve);
		
		setBounds(wd.getX()+50, wd.getY()+50, 500, 450);
		setVisible(true);
//		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}	// ViewDesign

	public JTextArea getJtaLog() {
		return jtaLog;
	}

	public JButton getJbtnReport() {
		return jbtnReport;
	}

	public JButton getJbtnExit() {
		return jbtnExit;
	}

}	// ViewDesign