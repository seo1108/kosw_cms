package kr.rapids.kosw.admin.controller;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import kr.rapids.kosw.admin.SpringSecurityUser;
import kr.rapids.kosw.admin.model.Admin;

@Controller
public class LoginController {
	
	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	
	@RequestMapping(value="/welcome", method=RequestMethod.GET)
	public ModelAndView welcome(Authentication authentication) {
		Admin admin = SpringSecurityUser.getAdmin();
		if (admin != null){
			logger.error("ADMIN : {}", admin.toString());
			if (SpringSecurityUser.isSuperAdmin()){
				logger.error("SUPER");
			}else{
				logger.error("NOT SUPER");
			}
			
		}else{
			logger.error("NO ADMIN");
		}
		
		return new ModelAndView();
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView();
	}
	
	
}
