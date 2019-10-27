package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("admin")
public class Admin implements Serializable, InputFormModel {
	
	
	private static final long serialVersionUID = 1L;
	
	private String adminSeq;
	private String custSeq;
	private String passwd;
	private String email;
	private String adminName;
	private String adminPhone;
	private String activeFlag;
	private String adminRegTime; // ttttMMddHHmmss
	private String prevAdminseq;
	
	// JOIN
	private boolean superAdmin;

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	// JOIN 
	private String custName; // 회사명
	
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getAdminSeq() {
		return adminSeq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAdminSeq(String adminSeq) {
		this.adminSeq = adminSeq;
	}

	public String getCustSeq() {
		return custSeq;
	}

	public void setCustSeq(String custSeq) {
		this.custSeq = custSeq;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPhone() {
		return adminPhone;
	}

	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getAdminRegTime() {
		return adminRegTime;
	}
	
	public String getAdminRegTimeFormat() {
		return DateFormatUtil.fromFormat2Format(adminRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}
	
	public void setAdminRegTime(String adminRegTime) {
		this.adminRegTime = adminRegTime;
	}

	public String getPrevAdminseq() {
		return prevAdminseq;
	}

	public void setPrevAdminseq(String prevAdminseq) {
		this.prevAdminseq = prevAdminseq;
	}

	@Override
	public String inputValidateErrroMessage() {
		/*if (StringUtils.isEmpty(this.custSeq)){
			return "고객사를 선택해주세요.";
		}*/
		if (StringUtils.isEmpty(this.email)){
			return "관리자 이메일을 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.adminName)){
			return "관리자 이름을 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.adminPhone)){
			return "관리자 전화번호를 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.passwd)){
			return "비밀번호를 입력해주세요.";
		}
		return null;
	}
	
}


