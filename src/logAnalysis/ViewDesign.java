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
		jbtnReport.setBounds(50, 370, 100, 30);
		
		add(jsp);
		add(jbtnReport);
		add(jbtnExit);
		
		setBounds(wd.getX()+50, wd.getY()+50, 500, 450);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}	// ViewDesign

}	// ViewDesign