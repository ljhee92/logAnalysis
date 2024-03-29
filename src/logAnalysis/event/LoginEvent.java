package logAnalysis.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import logAnalysis.design.LoginDesign;
import logAnalysis.design.WorkDesign;
import logAnalysis.domain.MemberVO;

public class LoginEvent extends WindowAdapter implements ActionListener {
	
	private LoginDesign ld;
	private MemberVO[] members;
	private String inputID;
	private String inputPW;
	
	public LoginEvent(LoginDesign ld) {
		init();
		this.ld = ld;
	}	// LoginEvent

	@Override
	public void actionPerformed(ActionEvent e) {
		login();
	}	// actionPerformed
	
	private void login() {
		if(!chkNull()){
			return;
		}	// end if
		
		if(!userCheck()) {
			return;
		}	// end if
		
		JOptionPane.showMessageDialog(ld, inputID + "님 로그인 성공");
		String userID = "";
		boolean userRWX = false;
		for(MemberVO member : members) {
			if(inputID.equals(member.getId())) {
				userID = member.getId();
				userRWX = member.isRwx();
			}	// end if
		}	// end for
		new WorkDesign(userID, userRWX);
		closeLd();
	}	// login
	
	private boolean chkNull() {
		inputID = ld.getJtfID().getText().trim();
		inputPW = String.valueOf(ld.getJpfPW().getPassword()).trim();
		try {
			if(inputID == null || inputID.isEmpty()) {
				ld.getJtfID().requestFocus();
				ld.getJlStatus().setText("아이디를 입력하세요.");
				JOptionPane.showMessageDialog(ld, "아이디를 입력하세요.");
				return false;
			}	// end if
			if(inputPW == null || inputPW.isEmpty()) {
				ld.getJpfPW().requestFocus();
				ld.getJlStatus().setText("비밀번호를 입력하세요.");
				JOptionPane.showMessageDialog(ld, "비밀번호를 입력하세요.");
				return false;
			}	// end if
		} catch (NullPointerException ne) {
			ld.getJlStatus().setText("아이디 또는 비밀번호를 입력하세요");
			ne.printStackTrace();
		}	// end catch
		return true;
	}	// chkNullId
	
	private boolean userCheck() {
		for(MemberVO member : members) {
			if(member != null && inputID.equals(member.getId()) && inputPW.equals(member.getPw())){
				return true;
			} else if(member != null && inputID.equals(member.getId()) && !inputPW.equals(member.getPw())) {
				JOptionPane.showMessageDialog(ld, "비밀번호를 확인해주세요.");
				return false;
			} else if(member != null && !inputID.equals(member.getId()) && inputPW.equals(member.getPw())) {
				JOptionPane.showMessageDialog(ld, "아이디를 확인해주세요.");
				return false;
			}	// end else
		}	// end for
		JOptionPane.showMessageDialog(ld, "등록되지 않은 계정입니다.");
		return false;
	}	// login

	@Override
	public void windowClosing(WindowEvent e) {
		closeLd();
	}	// windowClosing
	
	private void closeLd() {
		ld.dispose();
	}	// closeLd
	
	private void init() {
		members = new MemberVO[]{
			new MemberVO("root", "1111", false),
			new MemberVO("admin", "1234", true),
			new MemberVO("administrator", "12345", true)
		};
	}	// init

}	// class