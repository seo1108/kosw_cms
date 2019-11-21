package kr.rapids.kosw.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import kr.rapids.kosw.admin.model.Admin;
import kr.rapids.kosw.admin.service.AdminService;
import kr.rapids.kosw.admin.utils.Util;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	AdminService adminService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		Admin admin = new Admin();
		admin.setEmail(username);
		admin.setPasswd(password);
		
		
		Admin infoadmin = new Admin();
		infoadmin = adminService.adminInfoByEmail(admin);
		
//		System.out.println("_________________________________________________________________");
//		System.out.println("_________________________________________________________________");
//		System.out.println(password);
//		System.out.println(new BCryptPasswordEncoder(16).encode(password));
//		System.out.println(new BCryptPasswordEncoder(16).encode(password));
//		System.out.println(infoadmin.getPasswd());
		
		
		BCryptPasswordEncoder bcrypt= new BCryptPasswordEncoder();  
        boolean isPasswordMatches=bcrypt.matches(password, infoadmin.getPasswd());
        if (isPasswordMatches) {
        	System.out.println("1111111111111111111111111111111111111");
        	admin.setPasswd(infoadmin.getPasswd());
        	
        	// 로그인 인증
    		admin = adminService.adminLogin(admin);
    		
    		if (admin != null){
    			if (!"Y".equals(admin.getActiveFlag())){
    				throw new BadCredentialsException("관리자 승인대기 중입니다. 관리자에게 문의해주세요.");
    			}
    			List<String> roles = adminService.getRoles(admin); // "ROLE_SUPER" or "ROLE_NORMAL"
    			
    			List<GrantedAuthority> autoList = new ArrayList<GrantedAuthority>();
    			
    			for (Iterator iterator = roles.iterator(); iterator.hasNext();) {
    				String role = (String) iterator.next();
    				if ("ROLE_SUPER".equals(role)){
    					admin.setSuperAdmin(true);
    				}
    				SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role);
    				autoList.add(auth);
    			}

    			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password, autoList);
    			usernamePasswordAuthenticationToken.setDetails(admin);
    			return usernamePasswordAuthenticationToken;
    		}else{
    			throw new BadCredentialsException("아이디와 비밀번호를 확인해주세요.");
    		}
        } else {
        	System.out.println("2222222222222222222222222222222222222");
        	throw new BadCredentialsException("아이디와 비밀번호를 확인해주세요.");
        }
		
		
//		if ( Util.isMatchPassword(new BCryptPasswordEncoder(16).encode(password), infoadmin.getPasswd())) {
//			System.out.println("1111111111111111111111111111111111111");
//		} else {
//			System.out.println("2222222222222222222222222222222222222");
//		}
//		System.out.println("_________________________________________________________________");
//		System.out.println("_________________________________________________________________");
		
        
        
        
        
        
        
		// 로그인 인증
//		admin = adminService.adminLogin(admin);
//		
//		if (admin != null){
//			if (!"Y".equals(admin.getActiveFlag())){
//				throw new BadCredentialsException("관리자 승인대기 중입니다. 관리자에게 문의해주세요.");
//			}
//			List<String> roles = adminService.getRoles(admin); // "ROLE_SUPER" or "ROLE_NORMAL"
//			
//			List<GrantedAuthority> autoList = new ArrayList<GrantedAuthority>();
//			
//			for (Iterator iterator = roles.iterator(); iterator.hasNext();) {
//				String role = (String) iterator.next();
//				if ("ROLE_SUPER".equals(role)){
//					admin.setSuperAdmin(true);
//				}
//				SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role);
//				autoList.add(auth);
//			}
//
//			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password, autoList);
//			usernamePasswordAuthenticationToken.setDetails(admin);
//			return usernamePasswordAuthenticationToken;
//		}else{
//			throw new BadCredentialsException("아이디와 비밀번호를 확인해주세요.");
//		}
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}



	
}
