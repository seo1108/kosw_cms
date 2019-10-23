package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("department")
public class Department implements Serializable, InputFormModel {
	

	private static final long serialVersionUID = 1L;
	
	private String deptSeq;	// ID
	private String adminSeq;	// 관리자 FK
	private String custSeq;		// 고객사 FK
	private String deptName;	// 부서명
	private String deptRegTime; // ttttMMddHHmmss
	
	// JOIN
	private String adminName;	// 관리자 명
	

	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	@Override
	public String toString() {
		return "Department [departSeq=" + deptSeq + ", adminSeq=" + adminSeq + ", custSeq=" + custSeq + ", deptName="
				+ deptName + "]";
	}


	public String getDeptSeq() {
		return deptSeq;
	}



	public void setDeptSeq(String deptSeq) {
		this.deptSeq = deptSeq;
	}



	public String getAdminSeq() {
		return adminSeq;
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



	public String getDeptName() {
		return deptName;
	}



	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptRegTime() {
		return deptRegTime;
	}
	
	public String getDeptRegTimeFormat() {
		return DateFormatUtil.fromFormat2Format(deptRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}

	public void setDeptRegTime(String deptRegTime) {
		this.deptRegTime = deptRegTime;
	}



	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.custSeq)){
			return "고객사를 선택해주세요..";
		}
		if (StringUtils.isEmpty(this.adminSeq)){
			return "관리자를 선택해주세요.";
		}
		if (StringUtils.isEmpty(this.deptName)){
			return "부서명을 입력해주세요.";
		}
		return null;
	}
	
}


