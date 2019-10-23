package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("version")
public class AppVersion implements Serializable, InputFormModel {
	
	private static final long serialVersionUID = 1L;
	
	private String verSeq;			// ID
	private String adminSeq;		// 관리자 ID
	private String appType;			// android A, iOS I
	private String appVersion;		// 1.0.0 버전
	private String forceKill = "N";
	private String updateDesc;		
	private String verRegTime;		
	
	
	// JOIN
	private String adminName;
	
	
	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.adminSeq)){
			return "관리자를 선택해주세요.";
		}
		if (StringUtils.isEmpty(this.appVersion)){
			return "버전을 입력해 선택해주세요.";
		}
		if (StringUtils.isEmpty(this.appType)){
			return "디바이스 타입을 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.updateDesc)){
			return "내용을 입력해주세요.";
		}
		return null;
	}


	public String getVerSeq() {
		return verSeq;
	}


	public void setVerSeq(String verSeq) {
		this.verSeq = verSeq;
	}


	public String getAdminSeq() {
		return adminSeq;
	}


	public void setAdminSeq(String adminSeq) {
		this.adminSeq = adminSeq;
	}


	public String getAppType() {
		return appType;
	}


	public void setAppType(String appType) {
		this.appType = appType;
	}


	public String getAppVersion() {
		return appVersion;
	}


	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}


	public String getForceKill() {
		return forceKill;
	}


	public void setForceKill(String forceKill) {
		this.forceKill = forceKill;
	}


	public String getUpdateDesc() {
		return updateDesc;
	}


	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}


	public String getVerRegTime() {
		return verRegTime;
	}
	
	public String getVerRegTimeFormat() {
		return DateFormatUtil.fromFormat2Format(verRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}

	public void setVerRegTime(String verRegTime) {
		this.verRegTime = verRegTime;
	}


	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	@Override
	public String toString() {
		return "AppVersion [verSeq=" + verSeq + ", adminSeq=" + adminSeq + ", appType=" + appType + ", appVersion="
				+ appVersion + ", forceKill=" + forceKill + ", updateDesc=" + updateDesc + ", verRegTime=" + verRegTime
				+ ", adminName=" + adminName + "]";
	}
	
	// return DateFormatUtil.fromFormat2Format(pushRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	
	
	
	
	

}
