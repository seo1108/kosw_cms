package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("building")
public class Building extends Page implements Serializable, InputFormModel {
	
	private static final long serialVersionUID = 1L;
	
	
	private String buildSeq;		// ID
	private String custSeq;
	private String adminSeq;		// 관리자 FK
	private String buildCode;		// 건물코드 (5자리 숫자)
	private String buildName;		// 건물명
	private String buildFloorAmt;	// 층수
	private String buildStairAmt;	// 층간 계단수
	private String buildAddr;		// 주소
	private Double latitude;		// 위도
	private Double longitude;		// 경도
	private String buildRegTime;	// 등록일시
	private String placeId ; // 구글 위치  
	private String sActAmt ; // 오른층수 
	private String sActDate ; // 오른 날짜 
	private String startDate;  		// 조회 시작 일자 yyyyMMdd
	private String endDate;			// 조회 종료 일자 yyyyMMdd
	private String reqType;			// excel 다운로드시 사용
	private String userSeq;
	
	// JOIN
	private String adminName;	// 관리자 명
	

	public String getPlaceId() {
		return placeId;
	}


	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}


	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	

	public String getCustSeq() {
		return custSeq;
	}


	public void setCustSeq(String custSeq) {
		this.custSeq = custSeq;
	}


	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.adminSeq)){
			return "관리자를 선택해주세요.";
		}
		if (StringUtils.isEmpty(this.buildName)){
			return "건물명을 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.buildFloorAmt)){
			return "층수를 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.buildStairAmt)){
			return "층간 계단수를 입력해주세요.";
		}
//		if (StringUtils.isEmpty(this.buildAddr)){
//			return "건물 주소를 입력해주세요.";
//		}
//		if (StringUtils.isEmpty(this.latitude)){
//			return "건물 GPS 위도를 입력해주세요.";
//		}
//		if (StringUtils.isEmpty(this.longitude)){
//			return "건물 GPS 경도를 입력해주세요.";
//		}
		return null;
	}
	


	@Override
	public String toString() {
		return "Building [buildSeq=" + buildSeq + ", custSeq=" + custSeq + ", adminSeq=" + adminSeq + ", buildCode="
				+ buildCode + ", buildName=" + buildName + ", buildFloorAmt=" + buildFloorAmt + ", buildStairAmt="
				+ buildStairAmt + ", buildAddr=" + buildAddr + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", adminName=" + adminName + "]";
	}


	public String getBuildSeq() {
		return buildSeq;
	}






	public void setBuildSeq(String buildSeq) {
		this.buildSeq = buildSeq;
	}






	public String getAdminSeq() {
		return adminSeq;
	}






	public void setAdminSeq(String adminSeq) {
		this.adminSeq = adminSeq;
	}






	public String getBuildCode() {
		return buildCode;
	}






	public void setBuildCode(String buildCode) {
		this.buildCode = buildCode;
	}






	public String getBuildName() {
		return buildName;
	}






	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}






	public String getBuildFloorAmt() {
		return buildFloorAmt;
	}






	public void setBuildFloorAmt(String buildFloorAmt) {
		this.buildFloorAmt = buildFloorAmt;
	}






	public String getBuildStairAmt() {
		return buildStairAmt;
	}






	public void setBuildStairAmt(String buildStairAmt) {
		this.buildStairAmt = buildStairAmt;
	}






	public String getBuildAddr() {
		return buildAddr;
	}






	public void setBuildAddr(String buildAddr) {
		this.buildAddr = buildAddr;
	}

	


	public Double getLatitude() {
		return latitude;
	}


	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	public String getBuildRegTime() {
		return buildRegTime;
	}

	public String getBuildRegTimeFormat() {
		return DateFormatUtil.fromFormat2Format(buildRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}


	public void setBuildRegTime(String buildRegTime) {
		this.buildRegTime = buildRegTime;
	}


	public String getsActAmt() {
		return sActAmt;
	}


	public void setsActAmt(String sActAmt) {
		this.sActAmt = sActAmt;
	}


	public String getsActDate() {
		return sActDate;
	}

	public String getsActDateFormat() {
		return DateFormatUtil.fromFormat2Format(sActDate, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}


	public void setsActDate(String sActDate) {
		this.sActDate = sActDate;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getReqType() {
		return reqType;
	}


	public void setReqType(String regType) {
		this.reqType = regType;
	}


	public String getUserSeq() {
		return userSeq;
	}


	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}


	
}


