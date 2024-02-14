package logAnalysis;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginDesign extends JFrame {
	
	JLabel jlStatus;
	JTextField jtfID;
	JPasswordField jpfPW;
	JButton jbtnLogin;
	
	public LoginDesign() {
		super("로그분석 프로그램");
		
		jlStatus = new JLabel("아이디와 비밀번호를 입력하세요.");		
		JLabel jlID = new JLabel("ID");
		JLabel jlPW = new JLabel("PW");
		jtfID = new JTextField();
		jpfPW = new JPasswordField();
		jbtnLogin = new JButton("로그인");
		
		setLayout(null);
		jlStatus.setBounds(100, 20, 200, 50);
		jlID.setBounds(30, 80, 50, 40);
		jlPW.setBounds(30, 140, 50, 40);
		jtfID.setBounds(80, 80, 150, 40);
		jpfPW.setBounds(80, 140, 150, 40);
		jbtnLogin.setBounds(255, 100, 100, 60);
		
		jlStatus.setForeground(Color.red);
		
		add(jlStatus);
		add(jlID);
		add(jlPW);
		add(jtfID);
		add(jpfPW);
		add(jbtnLogin);
		
		LoginEvent le = new LoginEvent(this);
		addWindowListener(le);
		jtfID.addActionListener(le);
		jpfPW.addActionListener(le);
		jbtnLogin.addActionListener(le);
		
		setBounds(200, 200, 400, 250);
		setVisible(true);
	}	// LoginDesign

	public JLabel getJlStatus() {
		return jlStatus;
	}

	public JTextField getJtfID() {
		return jtfID;
	}

	public JPasswordField getJpfPW() {
		return jpfPW;
	}

	public JButton getJbtnLogin() {
		return jbtnLogin;
	}

}	// class