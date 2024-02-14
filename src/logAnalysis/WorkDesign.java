package logAnalysis;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class WorkDesign extends JFrame {
	
	private String userID;
	public boolean userRWX;
	private JLabel jlStart, jlEnd, jlAll;
	private JTextField jtfStart, jtfEnd;
	private JTextArea jtaContents;
	private JButton jbtnView, jbtnFileSelect;
	
	public WorkDesign(String userID, Boolean userRWX) {
		super("로그분석 프로그램");
		this.userID = userID;
		this.userRWX = userRWX;
		System.out.println(userID);
		System.out.println(userRWX);
		
		setLayout(null);
		
		jlStart = new JLabel("시작 라인:");
		jlEnd = new JLabel("마지막 라인:");
		jlAll = new JLabel("불러온 라인 수:");
		jtfStart = new JTextField();
		jtfEnd = new JTextField();
		jtaContents = new JTextArea();
		jtaContents.setEditable(false);
		JScrollPane jsp = new JScrollPane(jtaContents);
		jbtnView = new JButton("로그 정보 보기");
		jbtnFileSelect = new JButton("파일 선택");
		
		jlStart.setBounds(20, 20, 60, 30);
		jtfStart.setBounds(90, 20, 100, 30);
		jlEnd.setBounds(200, 20, 70, 30);
		jtfEnd.setBounds(280, 20, 100, 30);
		jlAll.setBounds(400, 20, 150, 30);
		jsp.setBounds(20, 70, 540, 350);
		jbtnView.setBounds(110, 440, 150, 50);
		jbtnFileSelect.setBounds(300, 440, 150, 50);
		
		add(jlStart);
		add(jlEnd);
		add(jlAll);
		add(jtfStart);
		add(jtfEnd);
		add(jsp);
		add(jbtnView);
		add(jbtnFileSelect);
		
		setBounds(200, 200, 600, 550);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	// WorkDesign

}	// class