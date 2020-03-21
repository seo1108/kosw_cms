package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Alias("cafe")
public class Cafe extends Page implements Serializable, InputFormModel {
	
	private static final long serialVersionUID = 1L;
	
	private String cafeseq;
	private String cafename;
	private String cafedesc;
	private String adminseq;
	private String cafekey;
	private String opendate;
	private String confirm;
	private String logo;
	private String additions;
	private String moddate;
	private String expdate;
	private Integer usercount = 0;
	private String adminname;
	private String adminemail;
	private String user_seq;
	private String regdate;
	
	private String startDate;  		// 조회 시작 일자 yyyyMMdd
	private String endDate;			// 조회 종료 일자 yyyyMMdd
	private String reqType;			// excel 다운로드시 사용
	private String keyword;			// 카페 회원 검색
	
	private MultipartFile file;
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public String getAdminemail() {
		return adminemail;
	}

	public void setAdminemail(String adminemail) {
		this.adminemail = adminemail;
	}

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}
	
	

	public Integer getUsercount() {
		return usercount;
	}

	public void setUsercount(Integer usercount) {
		this.usercount = usercount;
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
	public String getCafedesc() {
		return cafedesc;
	}
	public void setCafedesc(String cafedesc) {
		this.cafedesc = cafedesc;
	}
	public String getAdminseq() {
		return adminseq;
	}
	public void setAdminseq(String adminseq) {
		this.adminseq = adminseq;
	}
	public String getCafekey() {
		return cafekey;
	}
	public void setCafekey(String cafekey) {
		this.cafekey = cafekey;
	}
	public String getOpendate() {
		return opendate;
	}
	public void setOpendate(String opendate) {
		this.opendate = opendate;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getAdditions() {
		return additions;
	}
	public void setAdditions(String additions) {
		this.additions = additions;
	}
	public String getModdate() {
		return moddate;
	}
	public void setModdate(String moddate) {
		this.moddate = moddate;
	}
	public String getExpdate() {
		return expdate;
	}
	public void setExpdate(String expdate) {
		this.expdate = expdate;
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
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getUser_seq() {
		return user_seq;
	}

	public void setUser_seq(String user_seq) {
		this.user_seq = user_seq;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.cafename)){
			return "카페명을 입력해주세요.";
		}
		return null;
	}
	
	
}
