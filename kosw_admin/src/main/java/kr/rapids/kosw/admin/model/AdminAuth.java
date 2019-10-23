package kr.rapids.kosw.admin.model;

import java.io.Serializable;

public class AdminAuth implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String authSeq;
	private String adminSeq;
	private String authCode;
	private String authRegTime;
	
	public AdminAuth(Admin admin) {
		this.adminSeq = admin.getAdminSeq();
	}
	
	public AdminAuth() {
		// TODO Auto-generated constructor stub
	}

	public String getAuthSeq() {
		return authSeq;
	}
	public void setAuthSeq(String authSeq) {
		this.authSeq = authSeq;
	}
	public String getAdminSeq() {
		return adminSeq;
	}
	public void setAdminSeq(String adminSeq) {
		this.adminSeq = adminSeq;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getAuthRegTime() {
		return authRegTime;
	}
	public void setAuthRegTime(String authRegTime) {
		this.authRegTime = authRegTime;
	}
	@Override
	public String toString() {
		return "AdminAuth [authSeq=" + authSeq + ", adminSeq=" + adminSeq + ", authCode=" + authCode + ", authRegTime="
				+ authRegTime + "]";
	}
	
	
	
}


