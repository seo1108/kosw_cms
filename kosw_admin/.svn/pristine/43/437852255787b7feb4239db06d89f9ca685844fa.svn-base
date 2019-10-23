package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("logo")
public class Logo implements Serializable, InputFormModel {
	
	private static final long serialVersionUID = 1L;
	
	private String logoSeq;			// ID
	private String adminSeq;		// 관리자 FK
	private String custSeq;			// 고객사 FK
	private String logoImageFile;	// 로고 이미지 파일
	private String logoColor;		// 색상
	private String logoRegTime;		// 등록일시
	
	private MultipartFile file;

	
	@Override
	public String toString() {
		return "Logo [logSeq=" + logoSeq + ", adminSeq=" + adminSeq + ", custSeq=" + custSeq + ", logoImageFile="
				+ logoImageFile + ", logoColor=" + logoColor + ", file=" + file + "]";
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.adminSeq)){
			return "관리자를 선택해주세요..";
		}
		if (StringUtils.isEmpty(this.custSeq)){
			return "회사를 선택해주세요..";
		}
		return null;
	}

	public String getLogoSeq() {
		return logoSeq;
	}

	public void setLogoSeq(String logoSeq) {
		this.logoSeq = logoSeq;
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

	public String getLogoImageFile() {
		return logoImageFile;
	}

	public void setLogoImageFile(String logoImageFile) {
		this.logoImageFile = logoImageFile;
	}

	public String getLogoColor() {
		return logoColor;
	}

	public void setLogoColor(String logoColor) {
		this.logoColor = logoColor;
	}

	public String getLogoRegTime() {
		return logoRegTime;
	}
	
	public String getLogoRegTimeFormat() {
		return DateFormatUtil.fromFormat2Format(logoRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}

	public void setLogoRegTime(String logoRegTime) {
		this.logoRegTime = logoRegTime;
	}
	
	
}


