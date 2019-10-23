package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("bbs")
public class Bbs extends Page implements Serializable, InputFormModel {
	

	private static final long serialVersionUID = 1L;
	
	private String bbsSeq;
	private String adminSeq;
	private String bbsType;
	private String title;
	private String content;
	private String expireDate;
	private String bbsRegTime;
	private String pushFlag;
	
	
	
	//JOIN
	private String custSeq;
	private String buildSeq;
	private String stairSeq;
	private String adminName;
	
	private String custName;
	private String buildName;
	
	
	
	
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



	public String getCustSeq() {
		return custSeq;
	}



	public void setCustSeq(String custSeq) {
		if ("".equals(custSeq)){
			custSeq = null;
		}
		this.custSeq = custSeq;
	}



	public String getBuildSeq() {
		return buildSeq;
	}



	public void setBuildSeq(String buildSeq) {
		if ("".equals(buildSeq)){
			buildSeq = null;
		}
		this.buildSeq = buildSeq;
	}



	public String getStairSeq() {
		return stairSeq;
	}



	public void setStairSeq(String stairSeq) {
		if ("".equals(stairSeq)){
			stairSeq = null;
		}
		this.stairSeq = stairSeq;
	}



	public String getAdminName() {
		return adminName;
	}



	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}



	public String getPushFlag() {
		return pushFlag;
	}



	public void setPushFlag(String pushFlag) {
		this.pushFlag = pushFlag;
	}



	public String getBbsType() {
		return bbsType;
	}



	public void setBbsType(String bbsType) {
		this.bbsType = bbsType;
	}



	public String getBbsSeq() {
		return bbsSeq;
	}



	public void setBbsSeq(String bbsSeq) {
		this.bbsSeq = bbsSeq;
	}



	public String getAdminSeq() {
		return adminSeq;
	}



	public void setAdminSeq(String adminSeq) {
		if ("".equals(adminSeq)){
			adminSeq = null;
		}
		
		this.adminSeq = adminSeq;
	}






	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}
	
	public String getContentTrim() {
		if (content.length() > 50){
			return content.substring(0, 50) + "...";
		}
		return content; 
	}
	

	public void setContent(String content) {
		this.content = content;
	}



	public String getExpireDate() {
		return expireDate;
	}
	
	public String getExpireDateFormat() {
		return DateFormatUtil.fromFormat2Format(expireDate, "yyyyMMdd", "yyyy.MM.dd");
	}


	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate.replaceAll("[\\D]", "");
	}



	public String getBbsRegTime() {
		return bbsRegTime;
	}
	
	public String getBbsRegTimeFormat() {
		return DateFormatUtil.fromFormat2Format(bbsRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}
	



	public void setBbsRegTime(String bbsRegTime) {
		this.bbsRegTime = bbsRegTime;
	}






	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.title)){
			return "제목을 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.content)){
			return "내용을 입력해주세요.";
		}
		return null;
	}
	
}


