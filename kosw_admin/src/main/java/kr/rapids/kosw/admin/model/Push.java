package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("push")
public class Push extends Page implements Serializable, InputFormModel {
	
	private static final long serialVersionUID = 1L;
	
	private String pushSeq;			// ID
	private String adminSeq;		// 관리자 ID
	private String pushType;		// 푸쉬 타입
	private String pushTitle;		// 푸쉬 타이틀
	private String pushContent;		// 푸쉬 컨텐츠
	private String sentFlag;		// 발송 여부
	private String reserveTime;		// 푸쉬 발송 예약 시간 YYYYMMddHHmm (30분 단위로 하자)
	private String pushRegTime;
	private String custSeq;			// 대상 회사
	private String buildSeq;		// 대상 건물
	private String stairSeq;		// 다상 계단
	
	// JOIN
	private String adminName;
	private String custName;
	private String buildName;
	
	private String cafeseq;
	private String cafename;
	private String user_name;
	private String nickname;
	
	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.adminSeq)){
			return "관리자를 선택해주세요.";
		}
		if (StringUtils.isEmpty(this.pushType)){
			return "푸쉬 타입을 선택해주세요.";
		}
		if (StringUtils.isEmpty(this.pushTitle)){
			return "제목을 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.pushContent)){
			return "내용을 입력해주세요.";
		}
		return null;
	}
	
	
	public String getPushSeq() {
		return pushSeq;
	}
	public void setPushSeq(String pushSeq) {
		this.pushSeq = pushSeq;
	}
	public String getAdminSeq() {
		return adminSeq;
	}
	public void setAdminSeq(String adminSeq) {
		this.adminSeq = adminSeq;
	}
	public String getPushType() {
		return pushType;
	}
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	public String getPushTitle() {
		return pushTitle;
	}
	public void setPushTitle(String pushTitle) {
		this.pushTitle = pushTitle;
	}
	public String getPushContent() {
		return pushContent;
	}
	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}
	public String getSentFlag() {
		return sentFlag;
	}
	public void setSentFlag(String sentFlag) {
		this.sentFlag = sentFlag;
	}
	public String getReserveTime() {
		return reserveTime;
	}
	
	public String getReserveTimeFormat() {
		return DateFormatUtil.fromFormat2Format(reserveTime, "yyyyMMddHHmm", "yyyy.MM.dd HH:mm");
	}
	
	public void setReserveTime(String reserveTime) {
		if ( reserveTime != null ){
			this.reserveTime = reserveTime.replaceAll("[\\D]", "");
		}else{
			this.reserveTime = null;
		}
	}
	
	public String getPushRegTime() {
		return pushRegTime;
	}
	
	public String getPushRegTimeFormat() {
		return DateFormatUtil.fromFormat2Format(pushRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}
	
	
	public void setPushRegTime(String pushRegTime) {
		this.pushRegTime = pushRegTime;
	}


	public String getCustSeq() {
		return custSeq;
	}


	public void setCustSeq(String custSeq) {
		if ("".equals(custSeq)){
			return;
		}
		this.custSeq = custSeq;
	}


	public String getBuildSeq() {
		return buildSeq;
	}


	public void setBuildSeq(String buildSeq) {
		if ("".equals(buildSeq)){
			return;
		}
		this.buildSeq = buildSeq;
	}


	public String getStairSeq() {
		return stairSeq;
	}


	public void setStairSeq(String stairSeq) {
		if ("".equals(stairSeq)){
			return;
		}
		this.stairSeq = stairSeq;
	}


	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	


	public String getCustName() {
		return custName;
	}


	public void setCustName(String custName) {
		this.custName = custName;
	}


	public String getBuildName() {
		return buildName;
	}


	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}


	public String getCafeseq() {
		return cafeseq;
	}


	public void setCafeseq(String cafeseq) {
		this.cafeseq = cafeseq;
	}


	public String getCafename() {
		return cafename;
	}


	public void setCafename(String cafename) {
		this.cafename = cafename;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	@Override
	public String toString() {
		return "Push [pushSeq=" + pushSeq + ", adminSeq=" + adminSeq + ", pushType=" + pushType + ", pushTitle="
				+ pushTitle + ", pushContent=" + pushContent + ", sentFlag=" + sentFlag + ", reserveTime=" + reserveTime
				+ ", pushRegTime=" + pushRegTime + ", custSeq=" + custSeq + ", buildSeq=" + buildSeq + ", stairSeq="
				+ stairSeq + ", adminName=" + adminName + "]";
	}
	
	
	
	

}
