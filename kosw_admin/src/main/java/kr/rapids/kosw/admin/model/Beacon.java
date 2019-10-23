package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("beacon")
public class Beacon extends Page implements Serializable, InputFormModel {
	
	private static final long serialVersionUID = 1L;
	
	private String beaconSeq;
	private String stairSeq;
	private String adminSeq;
	private String manufacSeq;
	private String custSeq;
	private String modelName;
	private String serialOne;
	private String serialTwo;
	private String beaconUUID;
	private String majorValue;
	private String minorValue;
	private String installFloor;
	private String enabled = "N";
	private String beaconRegTime;
	private Double godo;
	
	//JOIN
	private String custName;
	
	private String buildSeq;
	
	private String stairName;
	private String buildName;
	private String manufacName;
	
	


	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public Double getGodo() {
		return godo;
	}

	public void setGodo(Double godo) {
		this.godo = godo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getBuildSeq() {
		return buildSeq;
	}

	public void setBuildSeq(String buildSeq) {
		this.buildSeq = buildSeq;
	}

	public String getManufacName() {
		return manufacName;
	}

	public void setManufacName(String manufacName) {
		this.manufacName = manufacName;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getStairName() {
		return stairName;
	}

	public void setStairName(String stairName) {
		this.stairName = stairName;
	}



	private String search;
	
	public String getCustSeq() {
		return custSeq;
	}

	public void setCustSeq(String custSeq) {
		this.custSeq = custSeq;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	
	

	public String getBeaconSeq() {
		return beaconSeq;
	}



	public void setBeaconSeq(String beaconSeq) {
		this.beaconSeq = beaconSeq;
	}



	public String getStairSeq() {
		return stairSeq;
	}



	public void setStairSeq(String stairSeq) {
		this.stairSeq = stairSeq;
	}


	



	public String getAdminSeq() {
		return adminSeq;
	}

	public void setAdminSeq(String adminSeq) {
		this.adminSeq = adminSeq;
	}

	public String getManufacSeq() {
		return manufacSeq;
	}



	public void setManufacSeq(String manufacSeq) {
		this.manufacSeq = manufacSeq;
	}



	public String getModelName() {
		return modelName;
	}



	public void setModelName(String modelName) {
		this.modelName = modelName;
	}



	public String getSerialOne() {
		return serialOne;
	}



	public void setSerialOne(String serialOne) {
		this.serialOne = serialOne;
	}



	public String getSerialTwo() {
		return serialTwo;
	}



	public void setSerialTwo(String serialTwo) {
		this.serialTwo = serialTwo;
	}



	public String getBeaconUUID() {
		return beaconUUID;
	}



	public void setBeaconUUID(String beaconUUID) {
		this.beaconUUID = beaconUUID;
	}



	public String getMajorValue() {
		return majorValue;
	}



	public void setMajorValue(String majorValue) {
		this.majorValue = majorValue;
	}



	public String getMinorValue() {
		return minorValue;
	}



	public void setMinorValue(String minorValue) {
		this.minorValue = minorValue;
	}



	public String getInstallFloor() {
		return installFloor;
	}



	public void setInstallFloor(String installFloor) {
		this.installFloor = installFloor;
	}



	public String getBeaconRegTime() {
		return beaconRegTime;
	}



	public void setBeaconRegTime(String beaconRegTime) {
		this.beaconRegTime = beaconRegTime;
	}

	public String getBeaconRegTimeFormat() {
		return DateFormatUtil.fromFormat2Format(beaconRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}


	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.stairSeq)){
			return "계단을 선택해주세요.";
		}
		if (StringUtils.isEmpty(this.manufacSeq)){
			return "설치 비콘 제품을 선택해주세요.";
		}
		if (StringUtils.isEmpty(this.modelName)){
			return "비콘 모델명을 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.beaconUUID)){
			return "비콘 UUID를 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.serialOne)){
			return "시리얼 #1 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.serialTwo)){
			return "시리얼 #2를 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.majorValue)){
			return "Major 값을 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.minorValue)){
			return "Minor 값을 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.installFloor)){
			return "설치층을 입력해주세요.";
		}
		
		return null;
	}
	
}


