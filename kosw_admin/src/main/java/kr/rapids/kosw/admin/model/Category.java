package kr.rapids.kosw.admin.model;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class Category implements Serializable, InputFormModel {
	
	private static final long serialVersionUID = 1L;
	
	private String cateseq;
	private String name;
	private String user_seq;
	private String cafeseq;
	
	
	public String getCateseq() {
		return cateseq;
	}



	public void setCateseq(String cateseq) {
		this.cateseq = cateseq;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getUser_seq() {
		return user_seq;
	}



	public void setUser_seq(String user_seq) {
		this.user_seq = user_seq;
	}



	public String getCafeseq() {
		return cafeseq;
	}



	public void setCafeseq(String cafeseq) {
		this.cafeseq = cafeseq;
	}



	@Override
	public String inputValidateErrroMessage() {
		return null;
	}
	

}
