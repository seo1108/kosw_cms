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
	
	
	// CafeBBS (t_cafe_notice table)
	private String cafeseq;
	private String contents;
	private String regdate;
	private String user_name;
	private String nickname;
	private String cafename;
	private String notiseq;
	private String user_seq;
	
	
	
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
		if (null != content && content.length() > 50){
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






	public String getCafeseq() {
		return cafeseq;
	}



	public void setCafeseq(String cafeseq) {
		this.cafeseq = cafeseq;
	}



	public String getContents() {
		return contents;
	}
	
	public String getContentsTrim() {
		if (null != contents && contents.length() > 50){
			return contents.substring(0, 50) + "...";
		}
		return contents; 
	}



	public void setContents(String contents) {
		this.contents = contents;
	}



	public String getRegdate() {
		return regdate;
	}



	public void setRegdate(String regdate) {
		this.regdate = regdate;
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



	public String getCafename() {
		return cafename;
	}



	public void setCafename(String cafename) {
		this.cafename = cafename;
	}



	public String getNotiseq() {
		return notiseq;
	}



	public void setNotiseq(String notiseq) {
		this.notiseq = notiseq;
	}



	public String getUser_seq() {
		return user_seq;
	}



	public void setUser_seq(String user_seq) {
		this.user_seq = user_seq;
	}



	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.title)){
			return "제목을 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.content) && StringUtils.isEmpty(this.contents)){
			return "내용을 입력해주세요.";
		}
		return null;
	}
	
}


