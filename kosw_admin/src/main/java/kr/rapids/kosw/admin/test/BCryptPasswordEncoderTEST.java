package kr.rapids.kosw.admin.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 사용자 비밀번호를 데이타베이스에 저장하기 위해서 Bcrypt 를 사용
 * @author kangnamgyu
 *
 */
public class BCryptPasswordEncoderTEST {
	
	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
		String encode = bCryptPasswordEncoder.encode("namgyu"); // $2a$10$bTULbMzmRIAUQbNbz7Z2A.Nsr1zHGdE1/dnRqPeU1m7Hh.NOEELbi
		System.out.println(encode);
	}
}
