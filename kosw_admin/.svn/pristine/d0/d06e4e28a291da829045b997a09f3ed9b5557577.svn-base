package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import kr.rapids.kosw.admin.AppConstants;
import kr.rapids.kosw.admin.utils.DateFormatUtil;

@Alias("sort")
public class Sort extends Page implements Serializable, InputFormModel {
	

	private static final long serialVersionUID = 1L;
	
	private int key ;
	private String sortName;
	private String sortField;

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Sort( int key ,String name , String field) {
		this.key = key ;
		this.sortName = name ;
		this.sortField = field ;
	}

	@Override
	public String inputValidateErrroMessage() {
		if (StringUtils.isEmpty(this.sortName)){
			return "입력해주세요.";
		}
		if (StringUtils.isEmpty(this.sortField)){
			return "입력해주세요.";
		}
		return null;
	}
	
}


