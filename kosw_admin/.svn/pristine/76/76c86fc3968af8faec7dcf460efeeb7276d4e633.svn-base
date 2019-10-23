package kr.rapids.kosw.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import kr.rapids.kosw.admin.model.Admin;
import kr.rapids.kosw.admin.service.AdminService;

@Component
public class SpringSecurityUser {

	
	static public Admin getAdmin(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object details = auth.getDetails();
		if (details != null && details.getClass() == Admin.class){
			Admin admin =  (Admin)details;
			return admin;
		}
		return null;
	}
	
	static public boolean isSuperAdmin(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object details = auth.getDetails();
		if (details != null && details.getClass() == Admin.class){
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
			for (GrantedAuthority grantedAuthority : authorities) {
				String authority = grantedAuthority.getAuthority();
				if ("ROLE_SUPER".equals(authority)){
					return true;
				}
			}
		}
		return false;
	}
	
	
}

