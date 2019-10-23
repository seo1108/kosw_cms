package kr.rapids.kosw.admin;

import java.util.Iterator;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import kr.rapids.kosw.admin.model.Admin;
import kr.rapids.kosw.admin.model.Customer;
import kr.rapids.kosw.admin.service.AdminService;

/**
 * 어플리케이션이 구동 완료 이벤트 수신 후 실행
 * 어플리케이션이 구동된 후 처음 실행시킬 코드를 작성한다. 
 * @author kangnamgyu
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent>{

	
	@Autowired
	private AdminService adminService;
	
	/**
	 * 시스템 설정 확인
	 * 유념해서 볼 속성들 (catalina.base, java.version, java.home, user.dir, wtp.deploy)
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
		
		Properties properties = System.getProperties();
		Iterator<Object> keyIter = properties.keySet().iterator();
		while(keyIter.hasNext()){
			Object key = keyIter.next();
			Object value = properties.get(key);
			System.out.println(key + " : " +value);
		}
		
		// 포토인테리어 회사 CUSTOMER 초기 설정
		String custCode = "10000";
		String custName = "포토인테리어";
		String postName = "포토인테리어 담당자";
		String postEmail = "admin@pothointerior.kr";
		String postPhone = "0212341234";
		String custRemarks = "필수입력";
		
		Customer customer = new Customer();
		customer.setCustCode(custCode);
		customer.setCustName(custName);
		customer.setPostName(postName);
		customer.setPostEmail(postEmail);
		customer.setPostPhone(postPhone);
		customer.setCustRemarks(custRemarks);
		
		Customer superCustomer = adminService.initialSuperCustomerSetting(customer);
		
		// 포토인테리어 소속 슈퍼관리자 초기 설정
		String custSeq = superCustomer.getCustSeq();
		String email = "test2@test.com";
		String passwd = "1234";
		String adminName = "슈퍼관리자";
		String adminPhone = "00012341234";
		
		Admin admin = new Admin();
		admin.setCustSeq(custSeq);
		admin.setEmail(email);
		admin.setPasswd(passwd);
		admin.setAdminName(adminName);
		admin.setAdminPhone(adminPhone);
		admin.setActiveFlag("Y");
		
		adminService.initialAdminUserSetting(admin);
		
	}

	
}
