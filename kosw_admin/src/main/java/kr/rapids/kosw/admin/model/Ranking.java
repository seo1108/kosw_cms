package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class Ranking implements Serializable, InputFormModel {

	private static final long serialVersionUID = 1L;
	
	private String userSeq;			// 사용자 ID
	private String userName;		// 사용자 이름
	private String nickName;		// 사용자 닉네임
	private String deptName;		// 부서 명
	private String custName;		// 고객사 명
	private Integer recordAmount;	// 층 오름 횟수
	private Integer ranking;		// 랭킹
	private String startDate;  		// 조회 시작 일자 yyyyMMdd
	private String endDate;			// 조회 종료 일자 yyyyMMdd
	
	
	private String adminSeq;		// 관리자 ID
	private String custSeq;			// 회사 ID
	private String deptSeq;			// 부서 ID
	
	private String actDate;			// 개인 기록 조회시 날짜
	
	private String buildSeq;
	private String sort;
	
	private String cafeseq;
	private String cafename;
	private String cateseq;
	private String catename;
	
	public String getActDate() {
		return actDate;
	}
	public void setActDate(String actDate) {
		this.actDate = actDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		if (startDate != null){
			startDate.replaceAll("-", "");
		}
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		if (endDate != null){
			endDate.replaceAll("-", "");
		}
		this.endDate = endDate;
	}
	public String getAdminSeq() {
		return adminSeq;
	}
	public void setAdminSeq(String adminSeq) {
		this.adminSeq = adminSeq;
	}
	public String getDeptSeq() {
		return deptSeq;
	}
	public void setDeptSeq(String deptSeq) {
		if (deptSeq != null && deptSeq.equals("")){
			this.deptSeq = null;
			return;
		}
		this.deptSeq = deptSeq;
	}
	public String getCustSeq() {
		return custSeq;
	}
	public void setCustSeq(String custSeq) {
		this.custSeq = custSeq;
	}
	public String getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public Integer getRecordAmount() {
		return recordAmount;
	}
	public void setRecordAmount(Integer recordAmount) {
		this.recordAmount = recordAmount;
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	
	
	
	@Override
	public String toString() {
		return "Ranking [userSeq=" + userSeq + ", userName=" + userName + ", nickName=" + nickName + ", deptName="
				+ deptName + ", custName=" + custName + ", recordAmount=" + recordAmount + ", ranking=" + ranking
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", adminSeq=" + adminSeq + ", custSeq="
				+ custSeq + ", deptSeq=" + deptSeq + ", actDate=" + actDate + ", buildSeq=" + buildSeq + ", sort="
				+ sort + ", cafeseq=" + cafeseq + ", cafename=" + cafename + ", cateseq=" + cateseq + ", catename="
				+ catename + "]";
	}
	
	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.startDate)){
			return "조회 시작일자을 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.endDate)){
			return "조회 종료일자를 입력해주세요.";
		}
		return null;
	}
	public String getBuildSeq() {
		return buildSeq;
	}
	public void setBuildSeq(String buildSeq) {
		this.buildSeq = buildSeq;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
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
	public String getCateseq() {
		return cateseq;
	}
	public void setCateseq(String cateseq) {
		this.cateseq = cateseq;
	}
	public String getCatename() {
		return catename;
	}
	public void setCatename(String catename) {
		this.catename = catename;
	}
	
	
}


