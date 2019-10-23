package kr.rapids.kosw.admin.utils;

import java.util.Random;

public class RandomString {
	
	static public String randomString(int length){
		String charSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int charSetLength = charSet.length();
		StringBuffer randStr = new StringBuffer();
		for(int i=0; i<length; i++){
			Random random = new Random();
			int r = random.nextInt(charSetLength);
			char charAt = charSet.charAt(r);
			randStr.append(charAt);
		}
		return randStr.toString();
	}
	
	static public String randomNumber(int length){
		String charSet = "0123456789";
		int charSetLength = charSet.length();
		StringBuffer randStr = new StringBuffer();
		for(int i=0; i<length; i++){
			Random random = new Random();
			int r = random.nextInt(charSetLength);
			char charAt = charSet.charAt(r);
			randStr.append(charAt);
		}
		return randStr.toString();
	}
}
