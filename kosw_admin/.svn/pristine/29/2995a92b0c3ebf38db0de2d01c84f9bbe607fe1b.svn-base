package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("beaconManufac")
public class BeaconManufac extends Page implements Serializable, InputFormModel {
	
	private static final long serialVersionUID = 1L;
	
	private String manufacSeq;
	private String adminSeq;
	private String manufacName;
	private String manufacPostName;
	private String manufacPostEmail;
	private String manufacPostPhone;
	private String manufacRemarks;
	private String manufacRegTime;
	
	
	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.manufacName)){
			return "제조사명을 입력해주세요.";
		}
//		if (StringUtils.isEmpty(this.manufacPostName)){
//			return "제조사 담당자명을 입력해주세요.";
//		}
//		if (StringUtils.isEmpty(this.manufacPostEmail)){
//			return "제조사 이메일을 입력해주세요.";
//		}
//		if (StringUtils.isEmpty(this.manufacPostPhone)){
//			return "제조사 전화번호를 입력해주세요.";
//		}
		return null;
	}

	public String getManufacSeq() {
		return manufacSeq;
	}

	public void setManufacSeq(String manufacSeq) {
		this.manufacSeq = manufacSeq;
	}

	public String getAdminSeq() {
		return adminSeq;
	}

	public void setAdminSeq(String adminSeq) {
		this.adminSeq = adminSeq;
	}

	public String getManufacName() {
		return manufacName;
	}

	public void setManufacName(String manufacName) {
		this.manufacName = manufacName;
	}

	public String getManufacPostName() {
		return manufacPostName;
	}

	public void setManufacPostName(String manufacPostName) {
		this.manufacPostName = manufacPostName;
	}

	public String getManufacPostEmail() {
		return manufacPostEmail;
	}

	public void setManufacPostEmail(String manufacPostEmail) {
		this.manufacPostEmail = manufacPostEmail;
	}

	public String getManufacPostPhone() {
		return manufacPostPhone;
	}

	public void setManufacPostPhone(String manufacPostPhone) {
		this.manufacPostPhone = manufacPostPhone.replaceAll("[\\D]", "");
	}

	public String getManufacRemarks() {
		return manufacRemarks;
	}

	public void setManufacRemarks(String manufacRemarks) {
		this.manufacRemarks = manufacRemarks;
	}

	public String getManufacRegTime() {
		return manufacRegTime;
	}
	
	public String getManufacRegTimeFormat() {
		return DateFormatUtil.fromFormat2Format(manufacRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}

	public void setManufacRegTime(String manufacRegTime) {
		this.manufacRegTime = manufacRegTime;
	}
	
	
}


