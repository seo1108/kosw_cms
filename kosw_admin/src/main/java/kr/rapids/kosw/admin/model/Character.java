package kr.rapids.kosw.admin.model;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("character")
public class Character implements Serializable, InputFormModel {
	
	private static final long serialVersionUID = 1L;
	
	private String charSeq;			// ID
	private String charCode;		// 케릭터 일련번호 
	private String adminSeq;		// 관리자 FK
	private String charName;		// 케릭터 이름 
	private String charActiveFlag;	// 사용 여부
	private String charRegTime;		// 등록일시
	private String charImageFile;	// 이미지 경로
	private String charBodyType;	// 신체 타입 -2, -1, 0, 1, 2
	private String charJerseyType;	// 저지 타입 default, gold, green, red
	private String charGender;		// 성별
	private String charStairYn;		// 계단 포함 이미지 Y, N
	private String charBustYn;		// 상반신 이미지 Y, N
	private String charDefaultYn;	// 기본 케릭터 - 삭제 불가 Y, N
	
	private String useCount;
	
	
	
	public String getCharBustYn() {
		return charBustYn;
	}


	public void setCharBustYn(String charBustYn) {
		this.charBustYn = charBustYn;
	}


	public String getCharDefaultYn() {
		return charDefaultYn;
	}


	public void setCharDefaultYn(String charDefaultYn) {
		this.charDefaultYn = charDefaultYn;
	}


	public String getUseCount() {
		return useCount;
	}


	public void setUseCount(String useCount) {
		this.useCount = useCount;
	}


	// JOIN 
	private String adminName;
	
	
	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	// 케릭터 파일 수정시 사용
	private List<String> editCharSeqs;
	// 케릭터 파일 등록, 수정시 사용
	private List<MultipartFile> characterFiles;

	
	public List<String> getEditCharSeqs() {
		return editCharSeqs;
	}
	

	public void setEditCharSeqs(List<String> editCharSeqs) {
		this.editCharSeqs = editCharSeqs;
	}

	public String getCharCode() {
		return charCode;
	}

	public void setCharCode(String charCode) {
		this.charCode = charCode;
	}

	public String getCharStairYn() {
		return charStairYn;
	}

	public void setCharStairYn(String charStairYn) {
		this.charStairYn = charStairYn;
	}



	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.charName)){
			return "케릭터 이름을 입력해주세요.";
		}
		if (StringUtils.isEmpty(this.charGender)){
			return "케릭터 성별을 입력해주세요.";
		}
//		if (this.characterFiles.size() != 40){
//			return "모든 타입의 케릭터 이미지를 업로드 해주세요.";
//		}
//		if (this.characterFiles.size() == 0){
//			return "모든 타입의 케릭터 이미지를 업로드 해주세요.";
//		}
		return null;
	}
	

	@Override
	public String toString() {
		return "Character [charSeq=" + charSeq + ", adminSeq=" + adminSeq + ", charName=" + charName
				+ ", charActiveFlag=" + charActiveFlag + ", charRegTime=" + charRegTime + ", charImageFile="
				+ charImageFile + ", charBodyType=" + charBodyType + ", charJerseyType=" + charJerseyType
				+ ", charGender=" + charGender + ", characterFiles=" + characterFiles + "]";
	}




	public List<MultipartFile> getCharacterFiles() {
		return characterFiles;
	}




	public void setCharacterFiles(List<MultipartFile> characterFiles) {
		this.characterFiles = characterFiles;
	}




	public String getCharSeq() {
		return charSeq;
	}


	public void setCharSeq(String charSeq) {
		this.charSeq = charSeq;
	}


	public String getAdminSeq() {
		return adminSeq;
	}


	public void setAdminSeq(String adminSeq) {
		this.adminSeq = adminSeq;
	}


	public String getCharName() {
		return charName;
	}


	public void setCharName(String charName) {
		this.charName = charName;
	}


	public String getCharActiveFlag() {
		return charActiveFlag;
	}

	public void setCharActiveFlag(String charActiveFlag) {
		this.charActiveFlag = charActiveFlag;
	}

	public String getCharRegTime() {
		return charRegTime;
	}
	
	public String getCharRegTimeFormat() {
		return DateFormatUtil.fromFormat2Format(charRegTime, "yyyyMMddHHmmss", AppConstants.DATE_DISPLAY_FORMAT);
	}

	public void setCharRegTime(String charRegTime) {
		this.charRegTime = charRegTime;
	}


	public String getCharImageFile() {
		return charImageFile;
	}


	public void setCharImageFile(String charImageFile) {
		this.charImageFile = charImageFile;
	}


	public String getCharBodyType() {
		return charBodyType;
	}


	public void setCharBodyType(String charBodyType) {
		this.charBodyType = charBodyType;
	}


	public String getCharJerseyType() {
		return charJerseyType;
	}


	public void setCharJerseyType(String charJerseyType) {
		this.charJerseyType = charJerseyType;
	}


	public String getCharGender() {
		return charGender;
	}


	public void setCharGender(String charGender) {
		this.charGender = charGender;
	}



	

	
	
	
}


