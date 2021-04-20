package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("user")
public class User extends Page implements Serializable, InputFormModel {
	

	private static final long serialVersionUID = 1L;
	
	private String userSeq;
	private String adminSeq;
	private String deptSeq;
	private String custSeq;
	private String userEmail;
	private String userPwd;
	private String userName;
	private String nickName;
	private String userRegTime; 
	private String approvalFlag; 
	private String approvalTime; 
	private String deviceType; 
	private String fcmToken; 
	private String emailAuthCode;
	private String authFinishFlag;
	private int buildCnt ;
	private int cafeCnt;
	private int sActAmt ;
	private String loginType;
	
	private String startSearchDate;  		// 조회 시작 일자 yyyyMMdd
	private String endSearchDate;			// 조회 종료 일자 yyyyMMdd
	
	public String getLoginType() {
		return loginType;
	}



	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}



	private String cafeseq;
	private String isAdmin;
	private String regdate;
	private String status;
	private int walkcount;
	private String catename;
	private String additions;
	private String reqType;			// excel 다운로드시 사용
	
	public int getsActAmt() {
		return sActAmt;
	}



	public void setsActAmt(int sActAmt) {
		this.sActAmt = sActAmt;
	}


	public int getBuildCnt() {
		return buildCnt;
	}



	public void setBuildCnt(int buildCnt) {
		this.buildCnt = buildCnt;
	}



	public int getCafeCnt() {
		return cafeCnt;
	}



	public void setCafeCnt(int cafeCnt) {
		this.cafeCnt = cafeCnt;
	}



	public String getUserSeq() {
		return userSeq;
	}



	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
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
		this.deptSeq = deptSeq;
	}



	public String getCustSeq() {
		return custSeq;
	}



	public void setCustSeq(String custSeq) {
		this.custSeq = custSeq;
	}



	public String getUserEmail() {
		return userEmail;
	}



	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}



	public String getUserPwd() {
		return userPwd;
	}



	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
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



	public String getUserRegTime() {
		return userRegTime;
	}
	
	public String getUserRegTimeFormat() {
		return DateFormatUtil.fromFormat2Format(userRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}



	public void setUserRegTime(String userRegTime) {
		this.userRegTime = userRegTime;
	}



	public String getApprovalFlag() {
		return approvalFlag;
	}



	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}



	public String getApprovalTime() {
		return approvalTime;
	}

	
	public String getApprovalTimeFormat() {
		return DateFormatUtil.fromFormat2Format(approvalTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}


	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}



	public String getDeviceType() {
		return deviceType;
	}



	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}



	public String getFcmToken() {
		return fcmToken;
	}



	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}



	public String getEmailAuthCode() {
		return emailAuthCode;
	}



	public void setEmailAuthCode(String emailAuthCode) {
		this.emailAuthCode = emailAuthCode;
	}



	public String getAuthFinishFlag() {
		return authFinishFlag;
	}



	public void setAuthFinishFlag(String authFinishFlag) {
		this.authFinishFlag = authFinishFlag;
	}



	public String getCafeseq() {
		return cafeseq;
	}



	public void setCafeseq(String cafeseq) {
		this.cafeseq = cafeseq;
	}



	public String getIsAdmin() {
		return isAdmin;
	}



	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}



	public String getRegdate() {
		return regdate;
	}



	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public int getWalkcount() {
		return walkcount;
	}



	public void setWalkcount(int walkcount) {
		this.walkcount = walkcount;
	}



	public String getAdditions() {
		return additions;
	}



	public void setAdditions(String additions) {
		this.additions = additions;
	}



	public String getCatename() {
		return catename;
	}



	public void setCatename(String catename) {
		this.catename = catename;
	}



	public String getReqType() {
		return reqType;
	}



	public void setReqType(String reqType) {
		this.reqType = reqType;
	}





	public String getStartSearchDate() {
		return startSearchDate;
	}



	public void setStartSearchDate(String startSearchDate) {
		this.startSearchDate = startSearchDate;
	}



	public String getEndSearchDate() {
		return endSearchDate;
	}



	public void setEndSearchDate(String endSearchDate) {
		this.endSearchDate = endSearchDate;
	}



	@Override
	public String inputValidateErrroMessage() {
		return null;
	}
	
}


