package kr.rapids.kosw.admin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelValidation {
	static public boolean isPhoneNumber(String phoneNum){
		String phoneRegex = "^[\\d|\\-|\\+|\\(|\\)|\\.]+$";
		Pattern p = Pattern.compile(phoneRegex);
		Matcher matcher = p.matcher(phoneNum);
		if (matcher.matches()){
			String group = matcher.group();
			String numberOnly = group.replaceAll("[\\D]", "");
			if (numberOnly.length() > 8 && numberOnly.length() < 17){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
}
