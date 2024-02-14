package logAnalysis;

public class MemberVO {
	
	String id;
	String pw;
	boolean rwx;
	
	public MemberVO(String id, String pw, boolean rwx) {
		this.id = id;
		this.pw = pw;
		this.rwx = rwx;
	}	// MemberVO
	
	public MemberVO(String id, String pw) {
		this.id = id;
		this.pw = pw;
		this.rwx = false;
	}	// MemberVO

	public String getId() {
		return id;
	}

	public String getPw() {
		return pw;
	}

	public boolean isRwx() {
		return rwx;
	}

}	// class