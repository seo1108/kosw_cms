package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("stair")
public class BuildingStair implements Serializable, InputFormModel {
	
	private static final long serialVersionUID = 1L;
	
	private String stairSeq;		// ID
	private String adminSeq;		// 관리자 FK
	private String buildSeq;		// 건물 FK
	private String stairName;		// 계단 명
	private String stairRegTime;	// 등록일시 yyyyMMddHHmmss
	private String custSeq;		// 고객사
	
	
	// JOIN
	private String buildName;	// 건물명
	private String buildFloorAmt; // 층수
	private String buildCode;	// 건물코드
	private String adminName;	// 관리자명

	
	public String getBuildFloorAmt() {
		return buildFloorAmt;
	}


	public void setBuildFloorAmt(String buildFloorAmt) {
		this.buildFloorAmt = buildFloorAmt;
	}


	public String getCustSeq() {
		return custSeq;
	}


	public void setCustSeq(String custSeq) {
		this.custSeq = custSeq;
	}


	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	public String getAdminSeq() {
		return adminSeq;
	}


	public void setAdminSeq(String adminSeq) {
		this.adminSeq = adminSeq;
	}


	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.buildSeq)){
			return "건물을 선택해주세요.";
		}
		if (StringUtils.isEmpty(this.stairName)){
			return "층명을 입력해주세요.";
		}
		return null;
	}
	

	@Override
	public String toString() {
		return "BuildingStair [stairSeq=" + stairSeq + ", buildSeq=" + buildSeq + ", stairName=" + stairName
				+ ", buildName=" + buildName + ", buildCode=" + buildCode + "]";
	}

	public String getStairSeq() {
		return stairSeq;
	}


	public void setStairSeq(String stairSeq) {
		this.stairSeq = stairSeq;
	}


	public String getBuildSeq() {
		return buildSeq;
	}


	public void setBuildSeq(String buildSeq) {
		this.buildSeq = buildSeq;
	}


	public String getStairName() {
		return stairName;
	}


	public void setStairName(String stairName) {
		this.stairName = stairName;
	}


	public String getStairRegTime() {
		return stairRegTime;
	}
	
	public String getStairRegTimeFormat() {
		return DateFormatUtil.fromFormat2Format(stairRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}

	public void setStairRegTime(String stairRegTime) {
		this.stairRegTime = stairRegTime;
	}


	public String getBuildName() {
		return buildName;
	}


	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}


	public String getBuildCode() {
		return buildCode;
	}


	public void setBuildCode(String buildCode) {
		this.buildCode = buildCode;
	}
	
	
}


