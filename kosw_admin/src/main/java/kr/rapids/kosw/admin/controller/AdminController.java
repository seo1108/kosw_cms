package kr.rapids.kosw.admin.controller;

//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.rapids.common.util.DateUtils;
import kr.rapids.common.util.RapidsMap;
import kr.rapids.common.util.StringUtils;
import kr.rapids.kosw.admin.SpringSecurityUser;
import kr.rapids.kosw.admin.model.Admin;
import kr.rapids.kosw.admin.model.AppVersion;
import kr.rapids.kosw.admin.model.Bbs;
import kr.rapids.kosw.admin.model.Beacon;
import kr.rapids.kosw.admin.model.BeaconManufac;
import kr.rapids.kosw.admin.model.Building;
import kr.rapids.kosw.admin.model.BuildingStair;
import kr.rapids.kosw.admin.model.Cafe;
import kr.rapids.kosw.admin.model.Category;
import kr.rapids.kosw.admin.model.Character;
import kr.rapids.kosw.admin.model.Customer;
import kr.rapids.kosw.admin.model.Department;
import kr.rapids.kosw.admin.model.Logo;
import kr.rapids.kosw.admin.model.LogoColor;
import kr.rapids.kosw.admin.model.PageNavigation;
import kr.rapids.kosw.admin.model.Push;
import kr.rapids.kosw.admin.model.Ranking;
import kr.rapids.kosw.admin.model.Sort;
import kr.rapids.kosw.admin.model.User;
import kr.rapids.kosw.admin.service.AdminService;
import kr.rapids.kosw.admin.service.FcmService;
import kr.rapids.kosw.admin.utils.DateFormatUtil;
import kr.rapids.kosw.admin.utils.FilePathUtils;
import kr.rapids.kosw.admin.utils.PagePair;
import kr.rapids.kosw.admin.utils.PushType;
import kr.rapids.kosw.admin.utils.RandomString;
import kr.rapids.kosw.admin.utils.Util;

@Controller
public class AdminController {
	
	private static final String DEFAULT_LOGO_PNG = "/static/assets/img/ic_logo.png";
	private static final int TEMP_PASSWORD_LENGTH = 8;
	private static final int RANDOMCODE_LENGTH = 5;

	private final static Logger logger = LoggerFactory.getLogger(AdminController.class);

	/**
	 * Spring Boot : AutoConfiguration
	 * https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jdbc/DataSourceTransactionManagerAutoConfiguration.java
	 */
	@Autowired
	private DataSourceTransactionManager txManager;
	
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private FilePathUtils filePathUtils;
	
	@Autowired
	private FcmService fcmService;
	
	/**
	 * 에러 처리
	 * @param req
	 * @param ex
	 * @return
	 */
	//@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception ex) {
	    logger.error("Request {} \n {} ",req.getRequestURL(), ex);
	    ModelAndView modelAndView = new ModelAndView("/error/error");
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos);
	    ex.printStackTrace(ps);
	    ps.close();
	    modelAndView.addObject("ex",baos.toString());
	    
	    return modelAndView;
	}
	
	/**
	 * 현재 로그인 되어 있는 관리자
	 * VIEW 에서 admin 으로 바로 접근할 수 있다.
	 * CustomAuthenticationProvider 확인 (isSuperAdmin 설정되어 있음)
	 * 모든 컨트롤러에 포함된다.
	 * @return
	 */
	public Admin currentAdmin(ModelAndView mv){
		// 로그인 관리자
		Admin admin = SpringSecurityUser.getAdmin();
		
		mv.addObject("admin", admin);
		return admin;
	}
	
	public Admin currentAdmin(){
		// 로그인 관리자
		Admin admin = SpringSecurityUser.getAdmin();
		return admin;
	}
	
	

	/**
	 * 부서 추가 처리
	 * 부서를 추가하기 위해서는 고객사와 관리자를 지정하여야 함
	 * @param redirectAttributes
	 * @param department
	 * @return
	 */
	@RequestMapping(path="departmentAdd", method = RequestMethod.POST)
	public ModelAndView departmentAddProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute(name="department") Department department
			){
		
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			department.setCustSeq(admin.getCustSeq());
		}
		String custSeq = department.getCustSeq();
				
		String inputValidateErrroMessage = department.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return redirectCustomerOne(custSeq);
		}
		
		
		boolean successYn = adminService.departmentAdd(department);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCustomerOne(custSeq);
		}
		
		
		logger.info("NEW DEPARTMENT : {}", department.toString());
		redirectAttributes.addFlashAttribute("message", String.format("신규 부서 %s 이(가) 등록되었습니다.", department.getDeptName()));
		return redirectCustomerOne(custSeq);
	}
	
	@RequestMapping(path="departmentEdit", method = RequestMethod.POST)
	public ModelAndView departmentEditProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute(name="department") Department department
			){
		
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			department.setCustSeq(admin.getCustSeq());
		}
		String custSeq = department.getCustSeq();
		String deptSeq = department.getDeptSeq();
		if (StringUtils.isEmpty(deptSeq)){
			redirectAttributes.addFlashAttribute("message", "부서를 입력해주세요.");
			return redirectCustomerOne(custSeq);
		}
				
		String inputValidateErrroMessage = department.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return redirectCustomerOne(custSeq);
		}
		department.setAdminSeq(admin.getAdminSeq());
		
		boolean successYn = adminService.departmentEdit(department);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCustomerOne(custSeq);
		}
		
		logger.info("NEW DEPARTMENT : {}", department.toString());
		redirectAttributes.addFlashAttribute("message", "부서 수정되었습니다.");
		return redirectCustomerOne(custSeq);
	}
	
	/**
	 * 부서 삭제
	 * 부서 삭제시 u_user_building_map 에 매핑된 사용자의 부서를 0 으로 변경
	 * @param redirectAttributes
	 * @param department
	 * @return
	 */
	@RequestMapping(path="departmentDelete", method = RequestMethod.POST)
	public ModelAndView departmentDeleteProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute(name="department") Department department
			){
		
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			department.setCustSeq(admin.getCustSeq());
		}
		
		String custSeq = department.getCustSeq();
		String deptSeq = department.getDeptSeq();
		if (StringUtils.isEmpty(deptSeq)){
			redirectAttributes.addFlashAttribute("message", "부서를 입력해주세요.");
			return redirectCustomerOne(custSeq);
		}
		
		
		boolean successYn = adminService.departmentDelete(department);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCustomerOne(custSeq);
		}
		
		logger.info("NEW DEPARTMENT : {}", department.toString());
		redirectAttributes.addFlashAttribute("message", "부서가 삭제되었습니다.");
		return redirectCustomerOne(custSeq);
	}
	
	
	
	/**
	 * 카테고리 추가 처리
	 * @param redirectAttributes
	 * @param category
	 * @return
	 */
	@RequestMapping(path="categoryAdd", method = RequestMethod.POST)
	public ModelAndView categoryAddProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute(name="category") Category category
			){
		
		if (StringUtils.isEmpty(category.getName())){
			redirectAttributes.addFlashAttribute("message", "카테고리명을 입력해주세요.");
			return redirectCafeOne(category.getCafeseq());
		}
		
		
		boolean successYn = adminService.categoryAdd(category);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCafeOne(category.getCafeseq());
		}
		
		logger.info("NEW CATEGORY : {}", category.toString());
		
		redirectAttributes.addFlashAttribute("message", String.format("신규 카테고리 %s 이(가) 등록되었습니다.", category.getName()));
		return redirectCafeOne(category.getCafeseq());
	}
	
	/**
	 * 카테고리 편집 처리
	 * @param redirectAttributes
	 * @param category
	 * @return
	 */
	@RequestMapping(path="categoryEdit", method = RequestMethod.POST)
	public ModelAndView categoryEditProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute(name="category") Category category
			){
		String cateseq = category.getCateseq();
		if (StringUtils.isEmpty(cateseq)){
			redirectAttributes.addFlashAttribute("message", "카테고리명을 입력해주세요.");
			return redirectCafeOne(category.getCafeseq());
		}
				
		boolean successYn = adminService.categoryEdit(category);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCafeOne(category.getCafeseq());
		}
		
		logger.info("NEW DEPARTMENT : {}", category.toString());
		redirectAttributes.addFlashAttribute("message", "카테고리가 수정되었습니다.");
		return redirectCafeOne(category.getCafeseq());
	}
	
	/**
	 * 카테고리 삭제
	 * 카테고리 삭제시 t_cafe_user 에 매핑된 사용자의 카테고리를 '' 으로 변경
	 * @param redirectAttributes
	 * @param category
	 * @return
	 */
	@RequestMapping(path="categoryDelete", method = RequestMethod.POST)
	public ModelAndView categoryDeleteProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute(name="category") Category category
			){
		
		boolean successYn = adminService.categoryDelete(category);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCafeOne(category.getCafeseq());
		}
		
		logger.info("NEW CATEGORY : {}", category.toString());
		redirectAttributes.addFlashAttribute("message", "카테고리가 삭제되었습니다.");
		return redirectCafeOne(category.getCafeseq());
	}
	
	
	
	/**
	 * 빌딩 추가 처리
	 * 빌딩을 추가하귀 위해서는 관리자와 고객사를 지정 하여야 한다.
	 * @param redirectAttributes
	 * @param building
	 * @return
	 */
	@RequestMapping(path="buildingAdd", method = RequestMethod.POST)
	public ModelAndView buildingAddProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute(name="building") Building building
			){
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			building.setCustSeq(admin.getCustSeq());
		}
		String custSeq = building.getCustSeq();
		
		String inputValidateErrroMessage = building.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return redirectCustomerOne(custSeq);
		}
		
		// 빌딩코드 랜덤 생성 (숫자 7자리)
		generateBuildCode(building, 7);
		boolean successYn = adminService.buildingAdd(building);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCustomerOne(custSeq);
		}
		
		
		logger.info("NEW BUILDING : {}", building.toString());
		redirectAttributes.addFlashAttribute("message", String.format("신규 부서 %s 이(가) 등록되었습니다.", building.getBuildName()));
		return redirectCustomerOne(custSeq);
	}
	
	
	@RequestMapping(path="buildingEdit", method = RequestMethod.POST)
	public ModelAndView buildingEditProc(
			RedirectAttributes redirectAttributes,
			@RequestParam Map<String, String> reqParam,
			@ModelAttribute(name="building") Building building
			){
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			building.setCustSeq(admin.getCustSeq());
		}
		String custSeq = building.getCustSeq();
		String buildSeq = building.getBuildSeq();
		String returnUrl = reqParam.get("returnUrl") ;
		
		
		if (StringUtils.isEmpty(buildSeq)){
			redirectAttributes.addFlashAttribute("message", "수정할 빌딩을 선택해주세요.");
			if (returnUrl != null ) {
				return (new ModelAndView(returnUrl)) ;
			}
			return redirectCustomerOne(custSeq);
		}
		
		String inputValidateErrroMessage = building.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			if (returnUrl != null ) {
				return (new ModelAndView(returnUrl)) ;
			}
			return redirectCustomerOne(custSeq);
		}
		
		boolean successYn = adminService.buildingEdit(building);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			if (returnUrl != null ) {
				return (new ModelAndView(returnUrl)) ;
			}
			return redirectCustomerOne(custSeq);
		}
		
		logger.info("NEW BUILDING : {}", building.toString());
		redirectAttributes.addFlashAttribute("message", String.format("부서 %s 이(가) 수정 되었습니다.", building.getBuildName()));
		if (returnUrl != null ) {
			return (new ModelAndView(returnUrl)) ;
		}
		return redirectCustomerOne(custSeq);
	}
	
	
	@RequestMapping(path="buildingDelete", method = RequestMethod.POST)
	public ModelAndView buildingDeleteProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute(name="building") Building building
			){
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			building.setCustSeq(admin.getCustSeq());
		}
		String custSeq = building.getCustSeq();
		String buildSeq = building.getBuildSeq();
		
		if (StringUtils.isEmpty(buildSeq)){
			redirectAttributes.addFlashAttribute("message", "수정할 빌딩을 선택해주세요.");
			return redirectCustomerOne(custSeq);
		}
		
		boolean successYn = adminService.buildingDelete(building);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCustomerOne(custSeq);
		}
		
		logger.info("NEW BUILDING : {}", building.toString());
		redirectAttributes.addFlashAttribute("message", String.format("삭제 되었습니다.", building.getBuildName()));
		return redirectCustomerOne(custSeq);
	}
	
	
	
	/**
	 * TODO : 비밀번호 보기 ? 액티브 결정
	 * 관리자(일반) 추가 처리
	 * 
	 * 1. 고객사 관리인 경우 ROLE_ADMIN (custSeq 필수)
	 * 2. 슈퍼 관리자는 10000 포토인테리어 이다. 본 메소드에서는 슈퍼관리자 추가는 처리하지 않음
	 * 
	 * Active Flag 는 발송된 인증 메일을 관리자가 확인후 Y 로 변경된다. 
	 * 패스워드는 임시패스워드로 설정
	 * 
	 * @param redirectAttributes
	 * @param admin
	 * @return
	 */
	@RequestMapping(path="adminAdd", method = RequestMethod.POST)
	public ModelAndView adminAddProc(
			RedirectAttributes redirectAttributes, 
			@ModelAttribute(name="admin") Admin admin
	){
		// 슈퍼 관리자 가 아니면 자신의 custSeq 에 대한 권한만 가진다.
		Admin curAdmin = currentAdmin();
		if (!curAdmin.isSuperAdmin()){
			admin.setCustSeq(curAdmin.getCustSeq());
		}
		
		String custSeq = admin.getCustSeq();
		String inputValidateErrroMessage = admin.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return redirectCustomerOne(custSeq);
		}
		
		admin.setActiveFlag("Y"); // 활성화 YES
//		String passwd = RandomString.randomString(TEMP_PASSWORD_LENGTH); // 임시 패스워드 지정
//		admin.setPasswd(passwd);

		// 중복체크
		Admin exAdmin = adminService.adminEmailCheck(admin);
		if (exAdmin != null){
			redirectAttributes.addFlashAttribute("message", "이미 등록된 이메일 입니다. 다른 이메일을 사용해주세요.");
			return redirectCustomerOne(custSeq);
		}
		
		// 관리자 추가
		boolean successYn = adminService.adminAdd(admin);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCustomerOne(custSeq);
		}
		
		logger.info("NEW ADMIN : {}", admin.toString());
		redirectAttributes.addFlashAttribute("message", String.format("신규 관리자 %s 이(가) 등록되었습니다.", admin.getAdminName()));
		return redirectCustomerOne(custSeq);

	}
	
	
	@RequestMapping(path="adminEdit", method = RequestMethod.POST)
	public ModelAndView adminEditProc(
			RedirectAttributes redirectAttributes, 
			@ModelAttribute(name="admin") Admin admin
	){
		// 슈퍼 관리자 가 아니면 자신의 custSeq 에 대한 권한만 가진다.
		Admin curAdmin = currentAdmin();
		if (!curAdmin.isSuperAdmin()){
			admin.setCustSeq(curAdmin.getCustSeq());
		}
		
		String custSeq = admin.getCustSeq();
		if (StringUtils.isEmpty(custSeq)){
			custSeq = curAdmin.getCustSeq();
			admin.setCustSeq(custSeq);
		}
		
		String inputValidateErrroMessage = admin.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return redirectCustomerOne(custSeq);
		}
		
		admin.setActiveFlag("Y"); // 활성화 YES

		// 관리자 수정
		boolean successYn = adminService.adminEdit(admin);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCustomerOne(custSeq);
		}
		
		logger.info("NEW ADMIN : {}", admin.toString());
		redirectAttributes.addFlashAttribute("message", String.format("관리자 %s 이(가) 수정 되었습니다.", admin.getAdminName()));
		return redirectCustomerOne(custSeq);

	}
	
	@RequestMapping(path="adminDelete", method = RequestMethod.POST)
	public ModelAndView adminDelete(
			RedirectAttributes redirectAttributes, 
			@ModelAttribute(name="admin") Admin admin
	){
		// 슈퍼 관리자 가 아니면 자신의 custSeq 에 대한 권한만 가진다.
		Admin curAdmin = currentAdmin();
		if (!curAdmin.isSuperAdmin()){
			admin.setCustSeq(curAdmin.getCustSeq());
		}
		
		String custSeq = admin.getCustSeq();
		String inputValidateErrroMessage = admin.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return redirectCustomerOne(custSeq);
		}
		
		/**
		 * cust_seq, admin_seq
		 * 삭제하고자 하는 관리자를 제외한 activeFlag 'Y' 인 관리자
		 */
		List<Admin> adminList = adminService.lastAdminExcept(admin);
		if (adminList == null){
			redirectAttributes.addFlashAttribute("message", "마지막 관리자는 삭제할 수 없습니다.");
			return redirectCustomerOne(custSeq);
		}
		
		// 관리자 수정
		boolean successYn = adminService.adminEdit(admin);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCustomerOne(custSeq);
		}
		
		logger.info("NEW ADMIN : {}", admin.toString());
		redirectAttributes.addFlashAttribute("message", String.format("신규 관리자 %s 이(가) 등록되었습니다.", admin.getAdminName()));
		return redirectCustomerOne(custSeq);

	}
	
	
	
	/**
	 * 고객사 추가 화면
	 * @param redirectAttributes
	 * @param admin
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="customerAdd", method = RequestMethod.GET)
	public ModelAndView customerAdd(
			RedirectAttributes redirectAttributes, 
			@ModelAttribute(name="customer") Customer customer
	){
		return new ModelAndView("customerAdd");
	}
	
	/**
	 * 고객사 추가 처리
	 * @param redirectAttributes
	 * @param customer
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="customerAdd", method = RequestMethod.POST)
	public ModelAndView customerAddProc(
			RedirectAttributes redirectAttributes, 
			@ModelAttribute(name="customer") Customer customer
			){
		
		String inputValidateErrroMessage = customer.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return new ModelAndView("redirect:/customerAdd");
		}

		// SET ADMIN
		Admin admin = currentAdmin();
		customer.setAdminSeq(admin.getAdminSeq());
		
		// 고객사 코드 생성 (렌덤)
		generateCustCode(customer);
		boolean successYn = adminService.customerAdd(customer);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return new ModelAndView("redirect:/customerAdd");
		}
		
		logger.info("NEW COSTOMER : {}", customer.toString());
		redirectAttributes.addFlashAttribute("message", String.format("신규 고객사 %s 이(가) 등록되었습니다.( CODE : %s )", customer.getCustName(), customer.getCustCode()));
		
		String custSeq = customer.getCustSeq();
		// 등록된 고객사 상세 페이지로 이동
		return redirectCustomerOne(custSeq);
	}
	

	
	
	
	/**
	 * 고객사 코드 중복 확인 후 코드 설정
	 * @param customer
	 * @return
	 */
	private Customer generateCustCode(Customer customer){
		String custCode = null;
		while (custCode == null){
			String random = RandomString.randomString(RANDOMCODE_LENGTH);
			if (adminService.custCodeCheck(random)){
				custCode = random;
				break;
			}
		}
		customer.setCustCode(custCode);
		return customer;
	}
	
	/**
	 * 빌딩 코드 랜덤 생성 (숫자만 7자리)
	 * @param building
	 * @param  
	 * @return
	 */
	private Building generateBuildCode(Building building, int codeLength){
		String buildCode = null;
		while (buildCode == null){
			String random = RandomString.randomNumber(codeLength);
			if (adminService.buildCodeCheck(random)){
				buildCode = random;
				break;
			}
		}
		building.setBuildCode(buildCode);
		return building;
	}
	
	
	/**
	 * AUTH - SUPER
	 * 고객사 리스트 페이지 
	 * @param redirectAttributes
	 * @param page
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="customerList", method = RequestMethod.GET)
	public ModelAndView customerList(
			@ModelAttribute Customer customer,
			@RequestParam(name="p", defaultValue="1") String page,
			@RequestParam(name="sort", defaultValue="") String sort,
			@RequestParam(name="startDate", defaultValue="") String startDate,
			@RequestParam(name="endDate", defaultValue="") String endDate,
			@RequestParam(name="reqType", defaultValue="") String reqType
	){
		
		ModelAndView modelAndView = new ModelAndView("customerList");
		modelAndView.addObject("admin", currentAdmin());
		
		if (startDate == null || startDate.length() != 8){
			startDate = DateUtils.getDateTextByAddMonths("yyyyMMdd", DateUtils.currentDate("yyyyMMdd"), -1);
			customer.setStartDate(startDate);
		}
		
		if (endDate == null || endDate.length() != 8){
			endDate = DateUtils.currentDate("yyyyMMdd");
			customer.setEndDate(endDate);
		}
		
		customer.setReqType(reqType);
		
		List sortList = new ArrayList<Sort>() ;
		sortList.add(new Sort(0,"고객사명","cust_name")) ;
		sortList.add(new Sort(1,"등록일시","cust_reg_time desc")) ;
		sortList.add(new Sort(2,"사용자수","user_count desc")) ;
		modelAndView.addObject("sortList", sortList) ;
		customer.setSort(sort);
		
		PagePair selectCustomerList = adminService.selectCustomerList(Integer.valueOf(page), customer);
		
		List<Customer> customerList = (List<Customer>) selectCustomerList.getList();
		modelAndView.addObject("customerList", customerList);
		
		PageNavigation pageNavigation = selectCustomerList.getPageNavigation();
		modelAndView.addObject("pageNavigation", pageNavigation);
		
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		
		return modelAndView;
	}
	
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="customerList/download", method = RequestMethod.GET)
	@ResponseBody
	public void downloadBuildingList(
			@ModelAttribute Customer customer,
			@RequestParam Map<String,Object> params,
			@RequestParam(name="sort", defaultValue="") String sort,
			@RequestParam(name="startDate", defaultValue="") String startDate,
			@RequestParam(name="endDate", defaultValue="") String endDate,
			@RequestParam(name="reqType", defaultValue="") String reqType,
			@RequestParam(name="search", defaultValue="") String search,
			HttpServletRequest req,
			HttpServletResponse res
	){
		try {
			String[] columns 
			= { "번호", "고객사명", "담당자명", "담당자 이메일", "담당자 전화번호", "관리자 수", "비고", "승인", "등록일시", "회원수" };
			String sortname = "";
			
			ModelAndView modelAndView = new ModelAndView("buildingList");
			modelAndView.addObject("admin", currentAdmin());
	
			if (startDate == null || startDate.length() != 8){
				startDate = DateUtils.getDateTextByAddMonths("yyyyMMdd", DateUtils.currentDate("yyyyMMdd"), -1);
				customer.setStartDate(startDate);
			}
			
			if (endDate == null || endDate.length() != 8){
				endDate = DateUtils.currentDate("yyyyMMdd");
				customer.setEndDate(endDate);
			}
			
			customer.setReqType(reqType);
			
			customer.setSearch(search);
			
			if ("0".equals(sort)) {
				sortname = "고객사명";
			} else if ("1".equals(sort)) {
				sortname = "등록일시";
			} else if ("2".equals(sort)) {
				sortname = "사용자수";
			} else {
				sortname = "고객사명";
			}
			
			customer.setSort(sort);
		
			PagePair selectCustomerList = adminService.selectCustomerList(1, customer);
			
			List<Customer> customerList = (List<Customer>) selectCustomerList.getList();
			
			Workbook workbook = new XSSFWorkbook();
		    Sheet sheet = workbook.createSheet("고객사 리스트");
			
		    Font filterFont = workbook.createFont();
		    filterFont.setFontHeightInPoints((short) 12);
		    filterFont.setColor(IndexedColors.RED.getIndex());
		    
		    Font filterNameFont = workbook.createFont();
		    filterNameFont.setFontHeightInPoints((short) 11);
		    filterNameFont.setColor(IndexedColors.BLACK.getIndex());
		    
		    CellStyle filterCellStyle = workbook.createCellStyle();
		    filterCellStyle.setFont(filterFont);
		    
		    CellStyle filterNameCellStyle = workbook.createCellStyle();
		    filterNameCellStyle.setFont(filterNameFont);
		    
		    Row filterRow = sheet.createRow(0);
		    Cell filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("검색조건");
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    filterRow = sheet.createRow(1);
		    filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("조회날짜");
		    filtercell.setCellStyle(filterCellStyle);
		    filtercell = filterRow.createCell(1);
		    filtercell.setCellValue(startDate + " - " + endDate);
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    filterRow = sheet.createRow(2);
		    filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("정렬순서");
		    filtercell.setCellStyle(filterCellStyle);
		    filtercell = filterRow.createCell(1);
		    filtercell.setCellValue(sortname);
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    filterRow = sheet.createRow(3);
		    filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("검색어");
		    filtercell.setCellStyle(filterCellStyle);
		    filtercell = filterRow.createCell(1);
		    filtercell.setCellValue(search);
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    Font headerFont = workbook.createFont();
		    headerFont.setFontHeightInPoints((short) 12);
		    headerFont.setColor(IndexedColors.BLUE_GREY.getIndex());
	
		    CellStyle headerCellStyle = workbook.createCellStyle();
		    headerCellStyle.setFont(headerFont);
		    
			// Create a Row
		    Row headerRow = sheet.createRow(4);
		    headerRow = sheet.createRow(5);
		    
		    res.setContentType("application/vnd.ms-excel");
            ServletOutputStream outStream = res.getOutputStream();
	
		    for (int i = 0; i < columns.length; i++) {
		      Cell cell = headerRow.createCell(i);
		      cell.setCellValue(columns[i]);
		      cell.setCellStyle(headerCellStyle);
		    }
		    
		    // Create Other rows and cells with contacts data
		    int rowNum = 6;
		    
		    for (int i = 0; i < customerList.size(); i++) {
				Row row = sheet.createRow(rowNum++);
				
				row.createCell(0).setCellValue(Util.checkNull(customerList.get(i).getCustSeq(), "-"));
				row.createCell(1).setCellValue(Util.checkNull(customerList.get(i).getCustName(), "-"));
				row.createCell(2).setCellValue(Util.checkNull(customerList.get(i).getPostName(), "-"));
				row.createCell(3).setCellValue(Util.checkNull(customerList.get(i).getPostEmail(), "-"));
				row.createCell(4).setCellValue(Util.checkNull(customerList.get(i).getPostPhone(), "-"));
				row.createCell(5).setCellValue(Util.checkNull(customerList.get(i).getAdminCount(), "-"));
				row.createCell(6).setCellValue(Util.checkNull(customerList.get(i).getCustRemarks(), "-"));
				row.createCell(7).setCellValue(Util.checkNull(customerList.get(i).getApproval_flag(), "-"));
				row.createCell(8).setCellValue(Util.checkNull(customerList.get(i).getCustRegTimeFormat(), "-"));
				row.createCell(9).setCellValue(Util.checkNull(customerList.get(i).getUserCount(), "-"));
			}
			
			// Resize all columns to fit the content size
		    for (int i = 0; i < columns.length; i++) {
		      sheet.autoSizeColumn(i);
		    }
		    
		    String filename = "고객사 리스트_" + DateFormatUtil.getCurrentTime() + ".xlsx";
		    
		    String header = req.getHeader("User-Agent");
			if (header.contains("MSIE") || header.contains("Trident")) {
				filename = URLEncoder.encode(filename,"UTF-8").replaceAll("\\+", "%20");
			    res.setHeader("Content-Disposition", "attachment;filename=" + filename + ";");
			} else {
				filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			    res.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			}
			
		    workbook.write(outStream);
		    outStream.close();
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * AUTH - SUPER
	 * 고객사 리스트 페이지 
	 * @param redirectAttributes
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="cafeList", method = RequestMethod.GET)
	public ModelAndView cafeList(
			@ModelAttribute Cafe cafe,
			@RequestParam(name="p", defaultValue="1") String page,
			/*@RequestParam(name="sort", defaultValue="") String sort,*/
			@RequestParam(name="startDate", defaultValue="") String startDate,
			@RequestParam(name="endDate", defaultValue="") String endDate,
			@RequestParam(name="reqType", defaultValue="") String reqType
	){
		
		ModelAndView modelAndView = new ModelAndView("cafeList");
		modelAndView.addObject("admin", currentAdmin());
		
		if (startDate == null || startDate.length() != 8){
			startDate = DateUtils.getDateTextByAddMonths("yyyyMMdd", DateUtils.currentDate("yyyyMMdd"), -1);
			cafe.setStartDate(startDate);
		}
		
		if (endDate == null || endDate.length() != 8){
			endDate = DateUtils.currentDate("yyyyMMdd");
			cafe.setEndDate(endDate);
		}
		
		cafe.setReqType(reqType);
		
//		@SuppressWarnings("rawtypes")
//		List sortList = new ArrayList<Sort>() ;
//		sortList.add(new Sort(0,"카페명","cafename")) ;
//		sortList.add(new Sort(1,"등록일시","opendate desc")) ;
//		modelAndView.addObject("sortList", sortList) ;
//		cafe.setSort(sort);
		
		PagePair selectCafeList = adminService.selectCafeList(Integer.valueOf(page), cafe);
		
		List<Cafe> cafeList = (List<Cafe>) selectCafeList.getList();
		modelAndView.addObject("cafeList", cafeList);
		
		PageNavigation pageNavigation = selectCafeList.getPageNavigation();
		modelAndView.addObject("pageNavigation", pageNavigation);
		
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		
		return modelAndView;
	}
	
	/**
	 * AUTH - SUPER
	 * 내가 개설한 카페 리스트 페이지 
	 * @param redirectAttributes
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path="cafeOfMineList", method = RequestMethod.GET)
	public ModelAndView cafeOfMineList(
			@ModelAttribute Cafe cafe,
			@RequestParam(name="adminsesq", defaultValue="") String adminseq,
			@RequestParam(name="p", defaultValue="1") String page
	){
		
		ModelAndView modelAndView = new ModelAndView("cafeOfMineList");
		Admin admin = currentAdmin(modelAndView);
		
		//modelAndView.addObject("admin", currentAdmin());
		
		User user = adminService.selectUserByEmail(admin.getEmail());
		
		if (null != user) {
		
			cafe.setAdminseq(user.getUserSeq());
			
			PagePair selectCafeList = adminService.selectCafeOfMineList(Integer.valueOf(page), cafe);
			
			List<Cafe> cafeList = (List<Cafe>) selectCafeList.getList();
			modelAndView.addObject("cafeList", cafeList);
			
			PageNavigation pageNavigation = selectCafeList.getPageNavigation();
			modelAndView.addObject("pageNavigation", pageNavigation);
		} else {
			modelAndView.addObject("cafeList", null);
		}
		
		return modelAndView;
	}
	
	/**
	 * AUTH - SUPER
	 * 건물  리스트 페이지 
	 * @param redirectAttributes
	 * @param page
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="buildingList", method = RequestMethod.GET)
	public ModelAndView buildingList(
			@ModelAttribute Building building,
			@RequestParam(name="p", defaultValue="1") String page,
			@RequestParam(name="sort", defaultValue="") String sort,
			@RequestParam(name="startDate", defaultValue="") String startDate,
			@RequestParam(name="endDate", defaultValue="") String endDate,
			@RequestParam(name="reqType", defaultValue="") String reqType
	){
		
		ModelAndView modelAndView = new ModelAndView("buildingList");
		modelAndView.addObject("admin", currentAdmin());

		if (startDate == null || startDate.length() != 8){
			startDate = DateUtils.getDateTextByAddMonths("yyyyMMdd", DateUtils.currentDate("yyyyMMdd"), -1);
			building.setStartDate(startDate);
		}
		
		if (endDate == null || endDate.length() != 8){
			endDate = DateUtils.currentDate("yyyyMMdd");
			building.setEndDate(endDate);
		}
		
		building.setReqType(reqType);

		List sortList = new ArrayList<Sort>() ;
		sortList.add(new Sort(0,"건물명<br>(가나다순)","build_name asc")) ;
		sortList.add(new Sort(1,"건물명<br>(가나다 역순)","build_name desc")) ;
		sortList.add(new Sort(2,"층수","build_floor_amt desc")) ;
		sortList.add(new Sort(3,"오른층수","s_act_amt desc")) ;
		sortList.add(new Sort(4,"이용날짜","s_act_date desc")) ;
		sortList.add(new Sort(5,"등록일자","build_reg_time desc")) ;
				
		modelAndView.addObject("sortList",sortList);
		building.setSort(sort);
	
		PagePair selectBuildingList = adminService.selectBuildingList(Integer.valueOf(page), building);
		
		List<Building> buildingList = (List<Building>) selectBuildingList.getList();
		modelAndView.addObject("buildingList", buildingList);
		
		PageNavigation pageNavigation = selectBuildingList.getPageNavigation();
		modelAndView.addObject("pageNavigation", pageNavigation);
		
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		
		logger.info("Model {} ",modelAndView);
		
		return modelAndView;
	}
	
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="buildingList/download", method = RequestMethod.GET)
	@ResponseBody
	public void downloadBuildingList(
			@ModelAttribute Building building,
			@RequestParam Map<String,Object> params,
			@RequestParam(name="sort", defaultValue="") String sort,
			@RequestParam(name="startDate", defaultValue="") String startDate,
			@RequestParam(name="endDate", defaultValue="") String endDate,
			@RequestParam(name="reqType", defaultValue="") String reqType,
			@RequestParam(name="search", defaultValue="") String search,
			HttpServletRequest req,
			HttpServletResponse res
	){
		try {
			String[] columns 
			= { "번호", "선물명", "주소", "위도", "경도", "층수", "층간계단", "등록일시", "오른층수", "이용날짜" };
			String sortname = "";
			
			ModelAndView modelAndView = new ModelAndView("buildingList");
			modelAndView.addObject("admin", currentAdmin());
	
			if (startDate == null || startDate.length() != 8){
				startDate = DateUtils.getDateTextByAddMonths("yyyyMMdd", DateUtils.currentDate("yyyyMMdd"), -1);
				building.setStartDate(startDate);
			}
			
			if (endDate == null || endDate.length() != 8){
				endDate = DateUtils.currentDate("yyyyMMdd");
				building.setEndDate(endDate);
			}
			
			building.setReqType(reqType);
			
			building.setSearch(search);
			
			if ("0".equals(sort)) {
				sortname = "건물명 (가나다순)";
			} else if ("1".equals(sort)) {
				sortname = "건물명 (가나다 역순)";
			} else if ("2".equals(sort)) {
				sortname = "층수";
			} else if ("3".equals(sort)) {
				sortname = "오른층수";
			} else if ("4".equals(sort)) {
				sortname = "이용날짜";
			} else if ("5".equals(sort)) {
				sortname = "등록일자";
			} else {
				sortname = "등록일자";
			}
			
			building.setSort(sort);
		
			PagePair selectBuildingList = adminService.selectBuildingList(1, building);
			
			List<Building> buildingList = (List<Building>) selectBuildingList.getList();
			
			Workbook workbook = new XSSFWorkbook();
		    Sheet sheet = workbook.createSheet("건물 리스트");
			
		    Font filterFont = workbook.createFont();
		    filterFont.setFontHeightInPoints((short) 12);
		    filterFont.setColor(IndexedColors.RED.getIndex());
		    
		    Font filterNameFont = workbook.createFont();
		    filterNameFont.setFontHeightInPoints((short) 11);
		    filterNameFont.setColor(IndexedColors.BLACK.getIndex());
		    
		    CellStyle filterCellStyle = workbook.createCellStyle();
		    filterCellStyle.setFont(filterFont);
		    
		    CellStyle filterNameCellStyle = workbook.createCellStyle();
		    filterNameCellStyle.setFont(filterNameFont);
		    
		    Row filterRow = sheet.createRow(0);
		    Cell filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("검색조건");
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    filterRow = sheet.createRow(1);
		    filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("조회날짜");
		    filtercell.setCellStyle(filterCellStyle);
		    filtercell = filterRow.createCell(1);
		    filtercell.setCellValue(startDate + " - " + endDate);
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    filterRow = sheet.createRow(2);
		    filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("정렬순서");
		    filtercell.setCellStyle(filterCellStyle);
		    filtercell = filterRow.createCell(1);
		    filtercell.setCellValue(sortname);
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    filterRow = sheet.createRow(3);
		    filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("검색어");
		    filtercell.setCellStyle(filterCellStyle);
		    filtercell = filterRow.createCell(1);
		    filtercell.setCellValue(search);
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    Font headerFont = workbook.createFont();
		    headerFont.setFontHeightInPoints((short) 12);
		    headerFont.setColor(IndexedColors.BLUE_GREY.getIndex());
	
		    CellStyle headerCellStyle = workbook.createCellStyle();
		    headerCellStyle.setFont(headerFont);
		    
			// Create a Row
		    Row headerRow = sheet.createRow(4);
		    headerRow = sheet.createRow(5);
		    
		    res.setContentType("application/vnd.ms-excel");
            ServletOutputStream outStream = res.getOutputStream();
	
		    for (int i = 0; i < columns.length; i++) {
		      Cell cell = headerRow.createCell(i);
		      cell.setCellValue(columns[i]);
		      cell.setCellStyle(headerCellStyle);
		    }
		    
		    // Create Other rows and cells with contacts data
		    int rowNum = 6;
		    
		    for (int i = 0; i < buildingList.size(); i++) {
				Row row = sheet.createRow(rowNum++);
				
				row.createCell(0).setCellValue(Util.checkNull(buildingList.get(i).getBuildSeq(), "-"));
				row.createCell(1).setCellValue(Util.checkNull(buildingList.get(i).getBuildName(), "-"));
				row.createCell(2).setCellValue(Util.checkNull(buildingList.get(i).getBuildAddr(), "-"));
				row.createCell(3).setCellValue(Util.checkNull(buildingList.get(i).getLatitude(), "-"));
				row.createCell(4).setCellValue(Util.checkNull(buildingList.get(i).getLongitude(), "-"));
				row.createCell(5).setCellValue(Util.checkNull(buildingList.get(i).getBuildFloorAmt(), "-"));
				row.createCell(6).setCellValue(Util.checkNull(buildingList.get(i).getBuildStairAmt(), "-"));
				row.createCell(7).setCellValue(Util.checkNull(buildingList.get(i).getBuildRegTimeFormat(), "-"));
				row.createCell(8).setCellValue(Util.checkNull(buildingList.get(i).getsActAmt(), "-"));
				row.createCell(9).setCellValue(Util.checkNull(buildingList.get(i).getsActDateFormat(), "-"));
			}
			
			// Resize all columns to fit the content size
		    for (int i = 0; i < columns.length; i++) {
		      sheet.autoSizeColumn(i);
		    }
		    
		    String filename = "건물 리스트_" + DateFormatUtil.getCurrentTime() + ".xlsx";
		    
		    String header = req.getHeader("User-Agent");
			if (header.contains("MSIE") || header.contains("Trident")) {
				filename = URLEncoder.encode(filename,"UTF-8").replaceAll("\\+", "%20");
			    res.setHeader("Content-Disposition", "attachment;filename=" + filename + ";");
			} else {
				filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			    res.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			}
			
		    workbook.write(outStream);
		    outStream.close();
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 고객사 관리자 삭제 & 해당 고객사로 등록된 계단 매핑 delete_yn SET Y
	 * @param request
	 * @param redirectAttributes
	 * @param customer
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="deleteAdminCustomer", method = RequestMethod.POST)
	public ModelAndView deleteAdminCustomer(
			HttpServletRequest request,
			RedirectAttributes redirectAttributes,
			@ModelAttribute(name="customer") Customer customer
			){
		
		ModelAndView modelAndView = new ModelAndView("redirect:customerList");
		boolean successYn = adminService.deleteAllAdminOfCustomer(customer);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return modelAndView;
		}
		
		redirectAttributes.addFlashAttribute("message", String.format("고객사 %s 의 모든 관리자가 삭제되었습니다.", customer.getCustName()));

		return modelAndView;
	}
	
	
	/**
	 * 고객사 하나 보기
	 * @param seq
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(path="customerOne", method = RequestMethod.GET)
	public ModelAndView customerOne(
			@RequestParam(name="cSeq", required=false) String custSeq
			,RedirectAttributes redirectAttributes
			){
		
		ModelAndView modelAndView = new ModelAndView("customerOne");
		Admin admin = currentAdmin(modelAndView);
		
		if (StringUtils.isEmpty(custSeq)){
			custSeq = admin.getCustSeq();
			if (StringUtils.isEmpty(custSeq)){
				return null; // CRITICAL ERROR
			}
			return redirectCustomerOne(custSeq);	
		}
		
		if (!admin.isSuperAdmin()){
			custSeq = admin.getCustSeq();
		}else if (StringUtils.isEmpty(custSeq)){
			custSeq = admin.getCustSeq();
			return redirectCustomerOne(custSeq);
		}
		
		// 권한 확인 슈퍼 관리자 또는 고객사의 관리자
		Customer customer = checkCustomerAdmin(custSeq);
		if (customer == null){
			redirectAttributes.addFlashAttribute("message", "잘못된 접근입니다.");
			if (admin.isSuperAdmin()){
				return new ModelAndView("redirect:/customerList");
			}
			return modelAndView;
		}
		
		modelAndView.addObject("customer", customer);
		
		// 관리자 리스트 정보
		List<Admin> adminList = adminService.adminListOfCustomer(customer);
		modelAndView.addObject("adminList", adminList);
				
		// 부서 정보 리스트
		List<Department> departmentList = adminService.departmentListOfCustomer(customer);
		modelAndView.addObject("departmentList", departmentList);
		
		// 건물 리스트
		List<Building> buildingList = adminService.buildingListOfCustomer(customer);
		modelAndView.addObject("buildingList", buildingList);
		
		// 로고 정보
		Logo logo = adminService.getLogo(customer);
		modelAndView.addObject("logo", logo);
		
		// 로고 컬러 리스트
		//modelAndView.addObject("colorNames", LogoColor.getColorNames());
		modelAndView.addObject("logoColors", LogoColor.getColorCodes());
		
		
		return modelAndView;
	}
	
	/**
	 * 카페 상세
	 * @param seq
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(path="cafeOne", method = RequestMethod.GET)
	public ModelAndView cafeOne(
			@RequestParam(name="cafeseq", required=false) String cafeseq
			,RedirectAttributes redirectAttributes
			){
		
		ModelAndView modelAndView = new ModelAndView("cafeOne");
		Admin admin = currentAdmin(modelAndView);
		
		if (StringUtils.isEmpty(cafeseq)){
			return null;
		}
		
		/*if (!admin.isSuperAdmin()){
			custSeq = admin.getCustSeq();
		}else if (StringUtils.isEmpty(custSeq)){
			custSeq = admin.getCustSeq();
			return redirectCustomerOne(custSeq);
		}*/
		
		// 권한 확인 슈퍼 관리자 또는 고객사의 관리자
		Cafe cafe = checkCafeAdmin(cafeseq);
		if (cafe == null){
			redirectAttributes.addFlashAttribute("message", "잘못된 접근입니다.");
			if (admin.isSuperAdmin()){
				return new ModelAndView("redirect:/cafeOfMineList");
			}
			return modelAndView;
		}

		User user = adminService.selectUserByEmail(currentAdmin().getEmail());
		
		modelAndView.addObject("cafe", cafe);
		modelAndView.addObject("loginseq", user.getUserSeq());
		
		// 카테고리 리스트
		List<Category> cafeCategoryList = adminService.cafeCategoryList(cafe);
		modelAndView.addObject("categoryList", cafeCategoryList);
		
		List<User> cafeUserList = adminService.cafeUserList(cafe);
		modelAndView.addObject("cafeUserList", cafeUserList);
		
//		// 로고 정보
//		Logo logo = adminService.getLogo(customer);
//		modelAndView.addObject("logo", logo);
//		
//		// 로고 컬러 리스트
//		//modelAndView.addObject("colorNames", LogoColor.getColorNames());
//		modelAndView.addObject("logoColors", LogoColor.getColorCodes());
		
		
		return modelAndView;
	}
	
	/**
	 * 고객사 수정
	 * redirectAttributes 를 사용하기 위해서는 화면 전환이 필요하다.
	 * @param redirectAttributes
	 * @param customer
	 * @param request 
	 * @return
	 */
	@RequestMapping(path="cafeEdit", method = RequestMethod.POST)
	public ModelAndView cafeEditProc(
			HttpServletRequest request,
			RedirectAttributes redirectAttributes,
			@ModelAttribute(name="cafe") Cafe cafe
			){
		String cafeseq = cafe.getCafeseq();
		
		String inputValidateErrroMessage = cafe.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("cafename", inputValidateErrroMessage);
			return redirectCafeOne(cafeseq);
		}

		boolean successYn = adminService.cafeEdit(cafe);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCafeOne(cafeseq);
		}
		
		logger.info("EDIT CAFE : {}", cafe.toString());
		redirectAttributes.addFlashAttribute("message", String.format("카페 %s 이(가) 정상 수정되었습니다.", cafe.getCafename()));

		return redirectCafeOne(cafeseq);
	}
	
	/**
	 * 고객사 수정
	 * redirectAttributes 를 사용하기 위해서는 화면 전환이 필요하다.
	 * @param redirectAttributes
	 * @param customer
	 * @param request 
	 * @return
	 */
	@RequestMapping(path="customerEdit", method = RequestMethod.POST)
	public ModelAndView customerEditProc(
			HttpServletRequest request,
			RedirectAttributes redirectAttributes,
			@ModelAttribute(name="customer") Customer customer
			){
		
		String custSeq = customer.getCustSeq();
		
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			custSeq = admin.getCustSeq();
		}else if (StringUtils.isEmpty(custSeq)){
			custSeq = admin.getCustSeq();
		}
		
		customer.setCustSeq(custSeq);
		
		String inputValidateErrroMessage = customer.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return redirectCustomerOne(custSeq);
		}

		boolean successYn = adminService.customerEdit(customer);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return redirectCustomerOne(custSeq);
		}
		
		logger.info("EDIT COSTOMER : {}", customer.toString());
		redirectAttributes.addFlashAttribute("message", String.format("고객사 %s 이(가) 정상 수정되었습니다.", customer.getCustName()));

		return redirectCustomerOne(custSeq);
	}
	
	

	/**
	 * 권한 확인 슈퍼 관리자 또는 고객사의 관리자
	 * @param cSeq
	 * @return
	 */
	private Customer checkCustomerAdmin(String cSeq) {
		Customer customer = adminService.selectCustomerBySeq(cSeq);
		if (customer == null){
			return null;
		}
		if (!SpringSecurityUser.isSuperAdmin()){
			Admin currentAdmin = currentAdmin();
			if (currentAdmin == null){
				return null;
			}
			logger.info("ADMIN : {}, CUSTOMER : {}", currentAdmin.getAdminSeq(), customer.getCustSeq());
			String custSeq = currentAdmin.getCustSeq();
			if (!cSeq.equals(custSeq)){
				return null; 
			}
		}
		return customer;
	}
	
	/**
	 * 권한 확인 슈퍼 관리자 또는 카페의 관리자
	 * @param cSeq
	 * @return
	 */
	private Cafe checkCafeAdmin(String adminseq) {
		Cafe cafe = adminService.selectCafeBySeq(adminseq);
		if (cafe == null){
			return null;
		}
//		if (!SpringSecurityUser.isSuperAdmin()){
//			Admin currentAdmin = currentAdmin();
//			if (currentAdmin == null){
//				return null;
//			}
//			logger.info("ADMIN : {}, CUSTOMER : {}", currentAdmin.getAdminSeq(), cafe.getAdminseq());
//			String custSeq = currentAdmin.getCustSeq();
//			if (!adminseq.equals(custSeq)){
//				return null; 
//			}
//		}
		return cafe;
	}
	
	
	
	/**
	 * 슈퍼 관리자의 경우 입력된 파라메터 custSeq 사용
	 * 일반 관리자의 경우 자신의 custSeq 사용
	 * @param custSeq
	 * @param buildSeq
	 * @param building
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(path="building", method = RequestMethod.GET)
	public ModelAndView buildingDetail(
			@RequestParam(name="sort", defaultValue="") String sort,
			@RequestParam(name="cSeq", required=false) String custSeq,
			@RequestParam(name="bSeq", required=false) String buildSeq,
			@ModelAttribute Building building,
			@ModelAttribute(name="rank") Ranking rank
			){
		
		ModelAndView modelAndView = new ModelAndView("building");
		
		String isSuperAdmin = "N";
		
		Admin admin = currentAdmin(modelAndView);
		if (admin.isSuperAdmin()){
			isSuperAdmin = "Y";
		}
		
		if (!admin.isSuperAdmin()){
			custSeq = admin.getCustSeq();
		}else if (StringUtils.isEmpty(custSeq)){
			custSeq = admin.getCustSeq();
		}
		building.setCustSeq(custSeq);
		rank.setCustSeq(custSeq);
		rank.setBuildSeq(buildSeq);
		rank.setSort(sort);
		
		List sortList = new ArrayList<Sort>() ;
		sortList.add(new Sort(0,"이름","user_name")) ;
		sortList.add(new Sort(1,"오른층수","record_amount desc")) ;
		modelAndView.addObject("sortList", sortList) ;

		Customer customer = adminService.getCustomerBySeq(custSeq);
		modelAndView.addObject("customer", customer);

		// 건물에 회사 리스트 
		List<Customer> customerList = adminService.customerListOfBuildSeq(buildSeq);
		modelAndView.addObject("customerList", customerList);
		
		
		// 건물 리스트 (전체 - NO FILTER)
		//List<Building> buildingList = adminService.buildingListOfCustomer(customer);
		List<Building> buildingList = adminService.buildingListOfBuildSeq(buildSeq);
		modelAndView.addObject("buildingList", buildingList);
		
		
		// 계단 (건물로 FILTER)
		building.setCustSeq(custSeq); 	// 고객사
		building.setBuildSeq(buildSeq); // DYNAMIC 건물
		List<BuildingStair> stairList = adminService.selectStairOfBuilding(building);
		modelAndView.addObject("stairList", stairList);
		
		// 각 사용자 랭킹 리스트 (u_user_building_map 기준으로 회사 판별)
		List<Ranking> ranks =  adminService.getRankingIndividualByBuilding(rank);
		
		for (int idx = 0; idx < ranks.size(); idx++) {
			try {
				if (null == ranks.get(idx).getUserSeq()) {
					ranks.remove(idx);
				}
			} catch (Exception ex) {	
				ranks.remove(idx);
			} 
		}
		
		modelAndView.addObject("ranks", ranks);
		
		
		
		Integer totalAmount = 0;
		for (Ranking r:ranks){
			totalAmount += r.getRecordAmount();
		}
		
		
		modelAndView.addObject("totalAmount", totalAmount);
		
		modelAndView.addObject("cSeq", custSeq);
		modelAndView.addObject("bSeq", buildSeq);
		modelAndView.addObject("sort", sort);
		modelAndView.addObject("isSuperAdmin", isSuperAdmin);
		
	/*	// 특정 대상 사용자 기록 조회
		if (rank.getUserSeq() != null){
			List<Ranking> userRecords = adminService.getRecordIndividual(rank);
			modelAndView.addObject("userRecords", userRecords);
		}*/
		
		return modelAndView;
	}
	
	
	@RequestMapping(value="building/download", method = RequestMethod.GET)
	@ResponseBody
	public void downloadBuilding(
			@RequestParam(name="sort", defaultValue="") String sort,
			@RequestParam(name="cSeq", required=false) String custSeq,
			@RequestParam(name="bSeq", required=false) String buildSeq,
			@ModelAttribute Building building,
			@ModelAttribute(name="rank") Ranking rank
			,HttpServletRequest req
			,HttpServletResponse res
			){
		try {
			String[] columns 
				= { "랭킹", "이름", "닉네임", "회사명", "부서명", "총 오른 층수" };
			
			ModelAndView modelAndView = new ModelAndView("building");
			
			Admin admin = currentAdmin(modelAndView);
			if (!admin.isSuperAdmin()){
				custSeq = admin.getCustSeq();
			}else if (StringUtils.isEmpty(custSeq)){
				custSeq = admin.getCustSeq();
			}
			building.setCustSeq(custSeq);
			rank.setCustSeq(custSeq);
			rank.setBuildSeq(buildSeq);
			
			String sortname = "";
			String buildingname = "";
			
			// 건물 리스트 (전체 - NO FILTER)
			List<Building> buildingList = adminService.buildingListOfBuildSeq(buildSeq);
			modelAndView.addObject("buildingList", buildingList);
			
			for (int idx = 0; idx < buildingList.size(); idx++) {
				buildingname += buildingList.get(idx).getBuildName() + ",";
			}
			
			buildingname = buildingname.substring(0, buildingname.length() -1);
			
			if ("0".equals(sort)) {
				sortname = "이름";
			} else if ("1".equals(sort)) {
				sortname = "오른층수";
			} else {
				sortname = "랭킹순";
			}
			
			// 각 사용자 랭킹 리스트 (u_user_building_map 기준으로 회사 판별)
			List<Ranking> ranks =  adminService.getRankingIndividualByBuilding(rank);
			modelAndView.addObject("ranks", ranks);

			
			Workbook workbook = new XSSFWorkbook();
		    Sheet sheet = workbook.createSheet("건물 정보 개인별 랭킹");
			
		    Font filterFont = workbook.createFont();
		    filterFont.setFontHeightInPoints((short) 12);
		    filterFont.setColor(IndexedColors.RED.getIndex());
		    
		    Font filterNameFont = workbook.createFont();
		    filterNameFont.setFontHeightInPoints((short) 11);
		    filterNameFont.setColor(IndexedColors.BLACK.getIndex());
		    
		    CellStyle filterCellStyle = workbook.createCellStyle();
		    filterCellStyle.setFont(filterFont);
		    
		    CellStyle filterNameCellStyle = workbook.createCellStyle();
		    filterNameCellStyle.setFont(filterNameFont);
		    
		    Row filterRow = sheet.createRow(0);
		    Cell filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("검색조건");
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    filterRow = sheet.createRow(1);
		    filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("건물이름");
		    filtercell.setCellStyle(filterCellStyle);
		    filtercell = filterRow.createCell(1);
		    filtercell.setCellValue(buildingname);
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    filterRow = sheet.createRow(2);
		    filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("정렬순서");
		    filtercell.setCellStyle(filterCellStyle);
		    filtercell = filterRow.createCell(1);
		    filtercell.setCellValue(sortname);
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    Font headerFont = workbook.createFont();
		    headerFont.setFontHeightInPoints((short) 12);
		    headerFont.setColor(IndexedColors.BLUE_GREY.getIndex());
	
		    CellStyle headerCellStyle = workbook.createCellStyle();
		    headerCellStyle.setFont(headerFont);
		    
			// Create a Row
		    Row headerRow = sheet.createRow(3);
		    headerRow = sheet.createRow(4);
		    
		    res.setContentType("application/vnd.ms-excel");
            ServletOutputStream outStream = res.getOutputStream();
	
		    for (int i = 0; i < columns.length; i++) {
		      Cell cell = headerRow.createCell(i);
		      cell.setCellValue(columns[i]);
		      cell.setCellStyle(headerCellStyle);
		    }
		    
		    // Create Other rows and cells with contacts data
		    int rowNum = 5;
		    
		    for (int i = 0; i < ranks.size(); i++) {
				Row row = sheet.createRow(rowNum++);
				
				row.createCell(0).setCellValue(Util.checkNull(ranks.get(i).getRanking(), "-"));
				row.createCell(1).setCellValue(Util.checkNull(ranks.get(i).getUserName(), "-"));
				row.createCell(2).setCellValue(Util.checkNull(ranks.get(i).getNickName(), "-"));
				row.createCell(3).setCellValue(Util.checkNull(ranks.get(i).getCustName(), "-"));
				row.createCell(4).setCellValue(Util.checkNull(ranks.get(i).getDeptName(), "-"));
				row.createCell(5).setCellValue(Util.checkNull(ranks.get(i).getRecordAmount(), "-"));
			}
			
			// Resize all columns to fit the content size
		    for (int i = 0; i < columns.length; i++) {
		      sheet.autoSizeColumn(i);
		    }
		    
		    String filename = "건물 정보 개인별 랭킹_" + DateFormatUtil.getCurrentTime() + ".xlsx";
		    
		    String header = req.getHeader("User-Agent");
			if (header.contains("MSIE") || header.contains("Trident")) {
				filename = URLEncoder.encode(filename,"UTF-8").replaceAll("\\+", "%20");
			    res.setHeader("Content-Disposition", "attachment;filename=" + filename + ";");
			} else {
				filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			    res.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			}
			
		    workbook.write(outStream);
		    outStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	@RequestMapping(path="stairEdit", method = RequestMethod.POST)
	public ModelAndView stairEditProc(
			RedirectAttributes redirectAttributes, 
			@ModelAttribute BuildingStair buildingStair
			){
		
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			buildingStair.setCustSeq(admin.getCustSeq());
		}
		buildingStair.setAdminSeq(admin.getAdminSeq());
		
		String custSeq = buildingStair.getCustSeq();
		
		ModelAndView modelAndView = new ModelAndView("redirect:/building");
		modelAndView.getModel().put("cSeq", custSeq);
		
		String inputValidateErrroMessage = buildingStair.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return modelAndView;
		}
		
		
		// PARAM : stairSeq, custSeq
		BuildingStair stair = adminService.selectBuildingStairBySeq(buildingStair);
		if (stair == null){
			redirectAttributes.addFlashAttribute("message", "잘못된 접근입니다.");
			return modelAndView;
		}

		
		boolean successYn = adminService.buildingStairEdit(buildingStair);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return modelAndView;
		}
		
		logger.info("NEW BUILDING STAIR : {}", buildingStair.toString());
		redirectAttributes.addFlashAttribute("message", String.format("계단이 %s 이(가) 수정 되었습니다.", buildingStair.getStairName()));
		return modelAndView;
		
	}
	
	@RequestMapping(path="stairDelete", method = RequestMethod.POST)
	public ModelAndView stairDeleteProc(
			RedirectAttributes redirectAttributes, 
			@ModelAttribute BuildingStair buildingStair
			){
		
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			buildingStair.setCustSeq(admin.getCustSeq());
		}
		
		buildingStair.setAdminSeq(admin.getAdminSeq());
		
		String custSeq = buildingStair.getCustSeq();
		String stairSeq = buildingStair.getStairSeq();
		
		ModelAndView modelAndView = new ModelAndView("redirect:/building");
		modelAndView.getModel().put("cSeq", custSeq);
		
		if (StringUtils.isEmpty(stairSeq)){
			redirectAttributes.addFlashAttribute("message", "잘못된 접근입니다.");
			return modelAndView;
		}
		
		// PARAM : stairSeq, custSeq
		BuildingStair stair = adminService.selectBuildingStairBySeq(buildingStair);
		if (stair == null){
			redirectAttributes.addFlashAttribute("message", "잘못된 접근입니다.");
			return modelAndView;
		}
		
		boolean successYn = adminService.buildingStairDelete(buildingStair);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return modelAndView;
		}
		
		logger.info("NEW BUILDING STAIR : {}", buildingStair.toString());
		redirectAttributes.addFlashAttribute("message", "삭제 되었습니다.");
		return modelAndView;
		
	}
	
	
	
	
	/**
	 * 계단 등록 처리
	 * @param redirectAttributes
	 * @param buildingStair
	 * @return
	 */
	@RequestMapping(path="stairAdd", method = RequestMethod.POST)
	public ModelAndView stairAddProc(
			RedirectAttributes redirectAttributes, 
			@ModelAttribute BuildingStair buildingStair
			){
		
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			buildingStair.setCustSeq(admin.getCustSeq());
		}
		buildingStair.setAdminSeq(admin.getAdminSeq());
		
		String custSeq = buildingStair.getCustSeq();
		
		ModelAndView modelAndView = new ModelAndView("redirect:/building");
		modelAndView.getModel().put("cSeq", custSeq);
		
		
		String inputValidateErrroMessage = buildingStair.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return modelAndView;
		}
		
		String buildSeq = buildingStair.getBuildSeq();

		// PARAM : buildSeq, custSeq
		Building building = adminService.selectBuildingOfStair(buildingStair);
		if (building == null){
			redirectAttributes.addFlashAttribute("message", "잘못된 접근입니다.");
			return modelAndView;
		}

		boolean successYn = adminService.buildingStairAdd(buildingStair);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return modelAndView;
		}
		
		logger.info("NEW BUILDING STAIR : {}", buildingStair.toString());
		redirectAttributes.addFlashAttribute("message", String.format("신규 계단이 %s 이(가) 등록되었습니다.", buildingStair.getStairName()));
				
		return modelAndView;
	}

	
	
	/**
	 * 로고 이미지 파일 응답
	 * logoFile 은 security path 에서 예외 처리 하여야 한다.
	 * @param custSeq
	 * @param response
	 */
	@RequestMapping(path="logoFile/{custSeq:.+}", method=RequestMethod.GET)
	public void readLogoFile(@PathVariable("custSeq") String custSeq, HttpServletResponse response) {
		
		File directory = filePathUtils.getLogoFile();
		if (directory == null){
			return;
		}
		
		File destFile = null;
		String logoImageFile = adminService.getLogoImageFile(custSeq);
		if (logoImageFile != null && !"".equals(logoImageFile)){
			destFile = new File(directory, logoImageFile);
		}
		
		// 없으면 디폴트 이미지
		if (destFile == null || !destFile.exists()){
			try {
				destFile = new ClassPathResource(DEFAULT_LOGO_PNG).getFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		
		
		String name = destFile.getName(); // 548242.png
		String extension = FilenameUtils.getExtension(name); // png
		
		InputStream targetStream  = null;
		ServletOutputStream outputStream = null;
		try {
			targetStream = new FileInputStream(destFile);
			if (extension.toLowerCase().indexOf("png") != -1){
				response.setContentType("image/png");
			}else if (extension.toLowerCase().indexOf("jpeg") != -1){
				response.setContentType("image/jpeg");
			}else if (extension.toLowerCase().indexOf("jpg") != -1){
				response.setContentType("image/jpeg");
			}else if (extension.toLowerCase().indexOf("gif") != -1){
				response.setContentType("image/gif");
			}else{
				response.setContentType("application/octet-stream"); // mime type 이 이미지로 지정되지 않으면 다운로드로 실행됨
			}
			
			outputStream = response.getOutputStream();
			byte[] readBuffer = new byte[1024];
			while (targetStream.read(readBuffer, 0, readBuffer.length) != -1){
				outputStream.write(readBuffer);
			}
			response.flushBuffer();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			try {
				targetStream.close();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 로고 색상 선택
	 * 로고 이미지 파일 업로드
	 * @param redirectAttributes
	 * @param logo
	 * @return
	 */
	@RequestMapping(path="updateCafeLogo", method = RequestMethod.POST)
	public ModelAndView updateCafeLogoProc(
			RedirectAttributes redirectAttributes, 
			@ModelAttribute Cafe cafe
			){
		String cafeseq = cafe.getCafeseq();
		MultipartFile file = cafe.getFile();
		
	
		File directory = filePathUtils.getLogoFile();
		if (directory == null){
			return null;
		}
		
		String fileName = FilenameUtils.getName(file.getOriginalFilename());
		File destFile = new File(directory, fileName);
		String baseName = FilenameUtils.getBaseName(fileName);
		String extension = FilenameUtils.getExtension(fileName);
		
		
		// 중복 OVERWIRETE 방지
		int postfix = 1;
		while(destFile.exists()){
			fileName = baseName +"_"+ String.valueOf(postfix) + "." + extension;
			System.out.println(fileName);
			destFile = new File(directory, fileName);
			postfix += 1;
		}
		
		try {
			file.transferTo(destFile); // OVERWRITE 되어 이름 같으면 기존것 삭제됨
			
			// FULL PATH (?)
			//cafe.setFile(filename);
			//boolean success = adminService.updateCafeLogo(logo);
			
			redirectAttributes.addFlashAttribute("message", "등록되었습니다.");
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "파일 등록 중 에러가 발생하였습니다.");
		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "파일 등록 중 에러가 발생하였습니다.");
		}

		return redirectCafeOne(cafeseq);
	}
	
	
	
	
	/**
	 * 로고 색상 선택
	 * 로고 이미지 파일 업로드
	 * @param redirectAttributes
	 * @param logo
	 * @return
	 */
	@RequestMapping(path="updateLogo", method = RequestMethod.POST)
	public ModelAndView updateLogoProc(
			RedirectAttributes redirectAttributes, 
			@ModelAttribute Logo logo
			){
		
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			logo.setCustSeq(admin.getCustSeq());
		}
		logo.setAdminSeq(admin.getAdminSeq());
		
		
		logger.error(logo.toString());
		
		String custSeq = logo.getCustSeq();
		MultipartFile file = logo.getFile();
		
		if (file.isEmpty()){
			// 로고 색상만 업데이트
			boolean success = adminService.updateLogo(logo);
			
		}else{
			
			File directory = filePathUtils.getLogoFile();
			if (directory == null){
				return null;
			}
			
			String fileName = FilenameUtils.getName(file.getOriginalFilename());
			File destFile = new File(directory, fileName);
			String baseName = FilenameUtils.getBaseName(fileName);
			String extension = FilenameUtils.getExtension(fileName);
			
			
			// 중복 OVERWIRETE 방지
			int postfix = 1;
			while(destFile.exists()){
				fileName = baseName +"_"+ String.valueOf(postfix) + "." + extension;
				System.out.println(fileName);
				destFile = new File(directory, fileName);
				postfix += 1;
			}
			
			try {
				file.transferTo(destFile); // OVERWRITE 되어 이름 같으면 기존것 삭제됨
				
				// FULL PATH (?)
				logo.setLogoImageFile(fileName);
				boolean success = adminService.updateLogo(logo);
				
				redirectAttributes.addFlashAttribute("message", "등록되었습니다.");
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("message", "파일 등록 중 에러가 발생하였습니다.");
			} catch (IOException e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("message", "파일 등록 중 에러가 발생하였습니다.");
			}
			
		}
		return redirectCustomerOne(custSeq);
	}
	
	
	/**
	 * 고객 상세 페이지로 리다이렉트
	 * @param custSeq
	 * @return
	 */
	private ModelAndView redirectCustomerOne(String custSeq) {
		ModelAndView modelAndView = new ModelAndView("redirect:customerOne");
		modelAndView.getModel().put("cSeq", custSeq);
		return modelAndView;
	}
	
	private ModelAndView redirectCafeOne(String cafeseq) {
		ModelAndView modelAndView = new ModelAndView("redirect:cafeOne");
		modelAndView.getModel().put("cafeseq", cafeseq);
		return modelAndView;
	}
	
	
	
	
	/**
	 * 화면 : 케릭터 파일 업로드
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="characterAdd", method = RequestMethod.GET)
	public ModelAndView character(
			){
		
		ModelAndView modelAndView = new ModelAndView("characterAdd");
		
	    String s[] = new String[]{"Y","N"};
		List<String> stair = Arrays.asList(s);
		modelAndView.addObject("stair", stair);
		
		String j[] = new String[]{"default","gold","green","red"};
		List<String> jersey = Arrays.asList(j);
		modelAndView.addObject("jersey", jersey);
		
		
		String b[] = new String[]{"-2","-1","0","+1", "+2"};
		List<String> body = Arrays.asList(b);
		modelAndView.addObject("body", body);
		
		return modelAndView;
	}
	
	
	/**
	 * 케릭터 파일 이미지 파일 응답
	 * characterFile 은 security path 에서 예외 처리 하여야 한다.
	 * @param custSeq
	 * @param response
	 */
	@RequestMapping(path="characterFile/{charSeq:.+}", method=RequestMethod.GET)
	public void readCharaterFile(@PathVariable("charSeq") String charSeq, HttpServletResponse response) {
		
		File directory = filePathUtils.getCharacterFile(); // 1
		if (directory == null){
			return;
		}
		String charImageFile = adminService.getCharacterImageFile(charSeq); // 2
		if (charImageFile == null || "".equals(charImageFile)){
			return;
		}
		
		File destFile = new File(directory, charImageFile); // 3
		if (!destFile.exists()){
			return;
		}
		
		String name = destFile.getName(); // 548242.png
		String extension = FilenameUtils.getExtension(name); // png
		
		InputStream targetStream  = null;
		ServletOutputStream outputStream = null;
		try {
			targetStream = new FileInputStream(destFile);
			if (extension.toLowerCase().indexOf("png") != -1){
				response.setContentType("image/png");
			}else if (extension.toLowerCase().indexOf("jpeg") != -1){
				response.setContentType("image/jpeg");
			}else if (extension.toLowerCase().indexOf("jpg") != -1){
				response.setContentType("image/jpeg");
			}else if (extension.toLowerCase().indexOf("gif") != -1){
				response.setContentType("image/gif");
			}else{
				response.setContentType("application/octet-stream"); // mime type 이 이미지로 지정되지 않으면 다운로드로 실행됨
			}
			
			outputStream = response.getOutputStream();
			byte[] readBuffer = new byte[1024];
			while (targetStream.read(readBuffer, 0, readBuffer.length) != -1){
				outputStream.write(readBuffer);
			}
			response.flushBuffer();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			try {
				targetStream.close();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 화면 : 케릭터 파일 수정
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="characterEdit", method = RequestMethod.GET)
	public ModelAndView characterEdit(
				@RequestParam(name="code") String charCode
			){
		
		ModelAndView modelAndView = new ModelAndView("characterEdit");
		
	    String s[] = new String[]{"Y","N"};
		List<String> stair = Arrays.asList(s);
		modelAndView.addObject("stair", stair);
		
		String j[] = new String[]{"default","gold","green","red"};
		List<String> jersey = Arrays.asList(j);
		modelAndView.addObject("jersey", jersey);
		
		
		String b[] = new String[]{"-2","-1","0","+1", "+2"};
		List<String> body = Arrays.asList(b);
		modelAndView.addObject("body", body);
		
		
		// GET CHARCATER
		List<Character> characterList = adminService.selectCharecterByCode(charCode);
		
		if (characterList != null){
			modelAndView.addObject("characterList", characterList);
			modelAndView.addObject("character", characterList.get(0)); // 대표
		}
		
		return modelAndView;
	}
	
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="characterDelete", method = RequestMethod.POST)
	public ModelAndView characterDeleteProc(
				@ModelAttribute Character character,
				RedirectAttributes redirectAttributes
			){
		
		ModelAndView modelAndView = new ModelAndView("redirect:characterList");
		
		String charCode = character.getCharCode();
		if(StringUtils.isEmpty(charCode)){
			redirectAttributes.addFlashAttribute("message", "케릭터 고유번호를 입력해주세요.");
			return modelAndView;
		}
		
		List<Character> characterAll =  adminService.selectCharacterAll();
		if (characterAll != null && characterAll.size() < 2){
			redirectAttributes.addFlashAttribute("message", "케릭터를 모두 삭제할 수 없습니다.");
			return modelAndView;
		}
		
		boolean success = adminService.characterDelete(character);
		
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버에러가 발생하였습니다.");
			return modelAndView;
		}
		
		redirectAttributes.addFlashAttribute("message", "정상 삭제 되었습니다.");
		return modelAndView;
	}
	
	
	
	/**
	 * 화면 : 케릭터 모음 리스트
	 * @param character
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="characterList", method = RequestMethod.GET)
	public ModelAndView characterList(
			){
		ModelAndView modelAndView = new ModelAndView("characterList");
		List<Character> characterList = adminService.selectCharacterList();
		modelAndView.addObject("characterList", characterList);
		return modelAndView;
	}
	
	
	/**
	 * AJAX JSON RESONSE
	 * 케릭터 파일 수정 처리
	 * @param character
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="characterEdit", method = RequestMethod.POST)
	public @ResponseBody Map<String,String> characterEditProc(
			@ModelAttribute Character character
			){
		
		Map<String, String> jsonResponse = new HashMap<String,String>();
		
		String inputValidateErrroMessage = character.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			jsonResponse.put("success", "false");
			jsonResponse.put("error", inputValidateErrroMessage);
			return jsonResponse;
		}
		
		String charCode = character.getCharCode();
		if (StringUtils.isEmpty(charCode)){
			jsonResponse.put("success", "false");
			jsonResponse.put("error", "파라메터 에러 : charCode");
			return jsonResponse;
		}
		
		
		String charName = character.getCharName();				// NAME
		String charGender = character.getCharGender();			// GENDER
		String charActiveFlag = character.getCharActiveFlag();
		if (!"Y".equals(charActiveFlag)){
			charActiveFlag = "N";
			character.setCharActiveFlag(charActiveFlag);
		}
		
		String charDefaultYn = character.getCharDefaultYn();
		if (!"Y".equals(charDefaultYn)){
			charDefaultYn = "N";
			character.setCharDefaultYn(charDefaultYn);
		}
		
		logger.error("{} , {}, {}, {}",charName, charGender, charActiveFlag, charDefaultYn);
		
		Admin admin = currentAdmin();
		character.setAdminSeq(admin.getAdminSeq());

		// 트랜젝션 시작
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = txManager.getTransaction(def);

		// 업데이트 : 케릭터명, 성별 
		adminService.characterNameGenderEdit(character);
		jsonResponse.put("success", "true");
		
		// 이미지 파일은 수정하지 않았을때
		List<String> editCharSeqs = character.getEditCharSeqs();
		if (editCharSeqs == null || editCharSeqs.size() == 0 ){
			txManager.commit(status);
			return jsonResponse;
		}
		
		List<MultipartFile> characterFiles = character.getCharacterFiles();
		if (characterFiles == null || characterFiles.size() == 0){
			txManager.commit(status);
			return jsonResponse;
		}
		
		if (editCharSeqs.size() != characterFiles.size()){
			jsonResponse.put("success", "false");
			jsonResponse.put("error", "파라메터 에러 : editCharSeqs, characterFiles MATCH");
			txManager.commit(status);
			return jsonResponse;
		}
		
		
		for (int i=0 ; i < characterFiles.size();i++ ){
			String charSeq = editCharSeqs.get(i);
			character.setCharSeq(charSeq);  // SET CHAR SEQ
			
			MultipartFile file = characterFiles.get(i);
			
			// 파일 저장
			File directory = filePathUtils.getCharacterFile();
			if (directory == null){
				jsonResponse.put("success", "false");
				jsonResponse.put("message", "시스템 에러가 발생하였습니다.[directory ERROR]");
				logger.error("{}",jsonResponse);
				txManager.rollback(status);   // ROLL BACK
				return jsonResponse;
			}
			
			String fileName = FilenameUtils.getName(file.getOriginalFilename());
			File destFile = new File(directory, fileName);
			String baseName = FilenameUtils.getBaseName(fileName);
			String extension = FilenameUtils.getExtension(fileName);
			
			// 중복 OVERWIRETE 방지
			int postfix = 1;
			while(destFile.exists()){
				fileName = baseName +"_"+ String.valueOf(postfix) + "." + extension;
				System.out.println(fileName);
				destFile = new File(directory, fileName);
				postfix += 1;
			}
			
			try {
				file.transferTo(destFile); // OVERWRITE 되어 이름 같으면 기존것 삭제됨
				
				character.setCharImageFile(fileName);	// SET CHAR IMAGE FILE
				
				boolean success = adminService.editCharacterImage(character);
				
				if (!success){
					txManager.rollback(status);   // ROLL BACK
					jsonResponse.put("success", "false");
					jsonResponse.put("message", "시스템 에러가 발생하였습니다.");
					logger.error("{}",jsonResponse);
					return jsonResponse;
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
				txManager.rollback(status);		  // ROLL BACK
				jsonResponse.put("success", "false");
				jsonResponse.put("message", "시스템 에러가 발생하였습니다.");
				logger.error("{}",jsonResponse);
				return jsonResponse;
			} catch (IOException e) {
				e.printStackTrace();
				txManager.rollback(status);		  // ROLL BACK
				jsonResponse.put("success", "false");
				jsonResponse.put("message", "시스템 에러가 발생하였습니다.");
				logger.error("{}",jsonResponse);
				return jsonResponse;
			}
		}
		
		txManager.commit(status);
		
		jsonResponse.put("success", "true");
		return jsonResponse;
		
	}
	
	
	
	
	/**
	 * 케릭터명 입력시 중복 여부 체크 
	 * @param name
	 * @return
	 */
	@RequestMapping(path="characterNameCheck", method = RequestMethod.POST)
	public @ResponseBody Map<String,String> characterNameCheck(
				@RequestParam(required=true) String name
			){
		Map<String, String> jsonResponse = new HashMap<String,String>();
		if (StringUtils.isEmpty(name)){
			jsonResponse.put("ok", "false");
			return jsonResponse;
		}
		
		String nameExists = adminService.characterNameCheck(name);
		if (StringUtils.isEmpty(nameExists)){
			jsonResponse.put("ok", "true");
		}else{
			jsonResponse.put("ok", "true");
		}
		
		return jsonResponse;
	}
	
	/**
	 * SUPER ADMIN
	 * AJAX JSON RESPONSE
	 * @param character
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="characterAdd", method = RequestMethod.POST)
	public @ResponseBody Map<String,String> characterProc(
			@ModelAttribute Character character
			){
		
		Map<String, String> jsonResponse = new HashMap<String,String>();
		
		String inputValidateErrroMessage = character.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			jsonResponse.put("success", "false");
			jsonResponse.put("error", inputValidateErrroMessage);
			return jsonResponse;
		}
		
		List<MultipartFile> characterFiles = character.getCharacterFiles();
		if (characterFiles == null || characterFiles.size() != 22){
			jsonResponse.put("success", "false");
			jsonResponse.put("error", "파라메터 에러 : characterFiles");
			return jsonResponse;
		}
		
		String charName = character.getCharName();				// NAME
		String charGender = character.getCharGender();			// GENDER
		String charActiveFlag = character.getCharActiveFlag();	// ACTIVE FLAG
		if (!"Y".equals(charActiveFlag)){
			charActiveFlag = "N";
			character.setCharActiveFlag(charActiveFlag);
		}
		
		String charDefaultYn = character.getCharDefaultYn();
		if (!"Y".equals(charDefaultYn)){
			charDefaultYn = "N";
			character.setCharDefaultYn(charDefaultYn);
		}
		
		logger.error("{} , {}, {}, {}",charName, charGender, charActiveFlag, charDefaultYn);
		
		// CHAR_NAME 중복체크
		String nameExists = adminService.characterNameCheck(charName);
		if (!StringUtils.isEmpty(nameExists)){
			jsonResponse.put("success", "false");
			jsonResponse.put("message", "중복된 케릭터 명이 있습니다. 다른 케릭터명을 입력해주세요.");
			logger.error("{}",jsonResponse);
			return jsonResponse;
		}
		
		// 케릭터 코드 LASTNUM
		String charCode = adminService.characterCodeLast();
		character.setCharCode(charCode);
		
		Admin admin = currentAdmin();
		character.setAdminSeq(admin.getAdminSeq());
		
//		String stair[] = new String[]{"Y","N"};
//		List<String> stairList = Arrays.asList(stair);
		
		String jersey[] = new String[]{"default","gold","green","red"};
		List<String> jerseyList = Arrays.asList(jersey);
		
		String body[] = new String[]{"-2","-1","0","+1", "+2"};
		List<String> bodyList = Arrays.asList(body);
		
		// 트랜젝션 시작
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = txManager.getTransaction(def);
		
		/**
		 * 케릭터 파일 *순서대로* 업로드
		 * 
		 * 1. 저지 구분 없이  
		 * 상반신 케릭터 1개, 계단 미포함 전신 케릭터 1개
		 * 
		 * 2. 저지구분 타입별로
		 * default  : 바디타입(-2), 바디타입(-1), 바디타입(0), 바디타입(+1), 바디타입(+2)
		 * gold  	: 바디타입(-2), 바디타입(-1), 바디타입(0), 바디타입(+1), 바디타입(+2)
		 * green  	: 바디타입(-2), 바디타입(-1), 바디타입(0), 바디타입(+1), 바디타입(+2)
		 * red  	: 바디타입(-2), 바디타입(-1), 바디타입(0), 바디타입(+1), 바디타입(+2)
		 *  
		 */
		
		
		for (int i=0 ; i < 2; i++ ){
			MultipartFile file = characterFiles.get(i);
			String charStairYn = "";
			String charJerseyType = "";
			String charBodyType = "";
			String charBustYn = "";
			
			if (i==0){
				charStairYn = "N";
				charJerseyType = "default";
				charBodyType = "0";
				charBustYn = "Y";
			}else{
				charStairYn = "N";
				charJerseyType = "defualt";
				charBodyType = "0";
				charBustYn = "N";
			}
			
			character.setCharStairYn(charStairYn);  		// SET STAIR
			character.setCharJerseyType(charJerseyType);  	// SET JERSEY
			character.setCharBodyType(charBodyType);		// SET BODY
			character.setCharBustYn(charBustYn); 			// SET BUST
			
			String[] savedPath = saveChracterFile(character, file);
			if (savedPath[0] == null){
				String errroMessage = savedPath[1];
				txManager.rollback(status);   // ROLL BACK
				jsonResponse.put("success", "false");
				jsonResponse.put("message", errroMessage);
				logger.error("{}",jsonResponse);
				return jsonResponse;
			}
			String charImageFile = savedPath[0]; 
			character.setCharImageFile(charImageFile);
			boolean success = adminService.addCharacter(character);
			if (!success){
				txManager.rollback(status);   // ROLL BACK
				jsonResponse.put("success", "false");
				jsonResponse.put("message", "시스템 에러가 발생하였습니다.");
				logger.error("{}",jsonResponse);
				return jsonResponse;
			}
		}
		
		
		try {
			for (int j=0 ; j < jerseyList.size(); j++ ){
				
				for (int b=0 ; b < bodyList.size(); b++ ){
					int idx = j*5 + b + 2;
					
					MultipartFile file = characterFiles.get(idx);
					String charStairYn = "Y";
					String charBustYn = "N";
					String charJerseyType = jerseyList.get(j);
					String charBodyType = bodyList.get(b);
					
					character.setCharStairYn(charStairYn);  		// SET STAIR
					character.setCharJerseyType(charJerseyType);  	// SET JERSEY
					character.setCharBodyType(charBodyType);		// SET BODY
					character.setCharBustYn(charBustYn); 			// SET BUST
					
					
					String[] savedPath = saveChracterFile(character, file);
					if (savedPath[0] == null){
						String errroMessage = savedPath[1];
						txManager.rollback(status);   // ROLL BACK
						jsonResponse.put("success", "false");
						jsonResponse.put("message", errroMessage);
						logger.error("{}",jsonResponse);
						return jsonResponse;
					}
					String charImageFile = savedPath[0]; 
					character.setCharImageFile(charImageFile);
					boolean success = adminService.addCharacter(character);
					if (!success){
						txManager.rollback(status);   // ROLL BACK
						jsonResponse.put("success", "false");
						jsonResponse.put("message", "시스템 에러가 발생하였습니다.");
						logger.error("{}",jsonResponse);
						return jsonResponse;
					}
				}
			}
			
			txManager.commit(status);
			jsonResponse.put("success", "true");
			return jsonResponse;
			
		} catch (Exception e) {
			txManager.rollback(status);   // ROLL BACK
			jsonResponse.put("success", "false");
			jsonResponse.put("message", "시스템 에러가 발생하였습니다.");
			logger.error("{}",jsonResponse);
			return jsonResponse;
			
		}
		
	}
	
	
	
	private String[] saveChracterFile(Character character, MultipartFile file) {
		String[] result = new String[]{null, null};
		
		File directory = filePathUtils.getCharacterFile();
		if (directory == null){
			result[1] = "시스템 에러가 발생하였습니다.[directory ERROR]";
			return result;
		}
		
		
		String fileName = FilenameUtils.getName(file.getOriginalFilename());
		File destFile = new File(directory, fileName);
		String baseName = FilenameUtils.getBaseName(fileName);
		String extension = FilenameUtils.getExtension(fileName);
		
		// 중복 OVERWIRETE 방지
		int postfix = 1;
		while(destFile.exists()){
			fileName = baseName +"_"+ String.valueOf(postfix) + "." + extension;
			destFile = new File(directory, fileName);
			postfix += 1;
		}
		
		try {
			file.transferTo(destFile); // OVERWRITE 되어 이름 같으면 기존것 삭제됨
			character.setCharImageFile(fileName);
			result[0] = fileName;
			return result;
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
			result[1] = "시스템 에러가 발생하였습니다.";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result[1] = "시스템 에러가 발생하였습니다.";
			return result;
		}
		
	}

	/**
	 * 사용자 리스트 http://localhost:8080/admin/userList?page=2&cSeq=1&search=
	 * 
	 * @return
	 */
	@RequestMapping(path="userList", method = RequestMethod.GET)
	public ModelAndView userList(
			User user,
			@RequestParam(name="cSeq") String custSeq,
			@RequestParam(name="p", defaultValue="1") String page
	){

		ModelAndView modelAndView = new ModelAndView("userList");
		
		Admin admin = currentAdmin(modelAndView);
		if (!admin.isSuperAdmin()){
			custSeq = admin.getCustSeq();
		}else if (StringUtils.isEmpty(custSeq)){
			custSeq = admin.getCustSeq();
		}
		user.setCustSeq(custSeq);
		
		Customer customer = adminService.selectCustomerBySeq(custSeq);
		modelAndView.addObject("customer", customer);
		
//		int totalCount = mapper.selectCustomerTotalCount(customer);
//		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
//		customer.setPage(pageNavigation);
//		List<Customer> selectCustomerList = mapper.selectCustomerList(customer);
		
		PagePair pagePair = adminService.selectUserList(Integer.valueOf(page), user);
		List<User> userList = (List<User>) pagePair.getList();
		modelAndView.addObject("userList", userList);
		
		PageNavigation pageNavigation = pagePair.getPageNavigation();
		modelAndView.addObject("pageNavigation", pageNavigation);
		
		return modelAndView;
	}
	

	/**
	 * 회원 리스트
	 * http://localhost:8080/admin/userList?page=2&cSeq=1&search=
	 * @return
	 */
	@RequestMapping(path="userAllList", method = RequestMethod.GET)
	public ModelAndView userAllList(
			User user,
			@RequestParam(name="p", defaultValue="1") String page
	){

		ModelAndView modelAndView = new ModelAndView("userAllList");
		
		
		PagePair pagePair = adminService.selectUserAllList(Integer.valueOf(page), user);
		List<User> userList = (List<User>) pagePair.getList();
		modelAndView.addObject("userList", userList);

		List sortList = new ArrayList<Sort>() ;
		sortList.add(new Sort(0,"이름","user_name")) ;
		sortList.add(new Sort(1,"등록일시","user_reg_time desc")) ;
		sortList.add(new Sort(2,"이용건물수","buildcnt desc")) ;
		sortList.add(new Sort(2,"오른층수","s_act_amt desc")) ;
		modelAndView.addObject("sortList", sortList) ;

		PageNavigation pageNavigation = pagePair.getPageNavigation();
		modelAndView.addObject("pageNavigation", pageNavigation);
		
		return modelAndView;
	}
	
	/**
	 * 사용자 삭제 - 매핑 테이블에서 매핑 삭제
	 * @param redirectAttributes
	 * @param custSeq
	 * @param userSeq
	 * @return
	 */
	@RequestMapping(path="userMapDelete", method = RequestMethod.POST)
	public ModelAndView userMapDelete(
			RedirectAttributes redirectAttributes,
			@RequestParam(name="custSeq") String custSeq,
			@RequestParam(name="userSeq") String userSeq 
	){
		
		ModelAndView modelAndView = new ModelAndView("redirect:userList");
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			custSeq = admin.getCustSeq();
		}else if (StringUtils.isEmpty(custSeq)){
			custSeq = admin.getCustSeq();
		}
		modelAndView.getModel().put("cSeq", custSeq);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("custSeq", custSeq);
		params.put("userSeq", userSeq);
		
		/*boolean success = adminService.deleteUserMapping(params);
		if (success){
			redirectAttributes.addFlashAttribute("message",	"사용자가 삭제 되었습니다.");
		}else{
			redirectAttributes.addFlashAttribute("message",	"에러가 발생하였습니다.");
		}*/
		
		int result = adminService.deleteUserMapping(params);

		//2018.09.02 수정. 
		//1개회사 소속시 전체 데이터 삭제. 2개이상 소속시 소속 회사만 삭제.
		if (result == -2){
			redirectAttributes.addFlashAttribute("message",	"2개 이상 회사에 소속되어 있습니다. 최고관리자에게 문의하세요.");
		}else if(result > 0){
			redirectAttributes.addFlashAttribute("message",	"사용자가 삭제 되었습니다.");
		}else{
			redirectAttributes.addFlashAttribute("message",	"에러가 발생하였습니다.");
		}
		
		return modelAndView;
	}
	
	
	
	
	
	/**
	 * 사용자 승인 변경
	 * @param custSeq
	 * @param userSeq
	 * @param approvalFlag
	 * @return
	 */
	@RequestMapping(path="userApprovalEdit", method = RequestMethod.POST)
	public @ResponseBody Map<String,String> userApprovalEdit(
			@RequestParam(name="cSeq") String custSeq,
			@RequestParam(name="uSeq") String userSeq,
			@RequestParam(name="approvalFlag", defaultValue="N") String approvalFlag
			
	){
		Map<String, String> jsonResponse = new HashMap<String,String>();
		
		Admin admin = currentAdmin();
		User user = adminService.selectUserBySeq(userSeq);
		// 관리자의 사용자에 대한 권한 확인
		if (!admin.isSuperAdmin()){
			custSeq = admin.getCustSeq();
			if (user == null){
				jsonResponse.put("success", "false");
				jsonResponse.put("error", "에러가 발생하였습니다.");
				return jsonResponse;
			}
			
			if (!user.getCustSeq().equals(custSeq)){
				jsonResponse.put("success", "false");
				jsonResponse.put("error", "에러가 발생하였습니다.");
				return jsonResponse;
			}
		}
		
		user.setUserSeq(userSeq);
		user.setCustSeq(custSeq);
		user.setApprovalFlag(approvalFlag);
		user.setAdminSeq(admin.getAdminSeq());
		
		boolean success = adminService.updateUserApprovalFlag(user);
		
		String fcmToken = user.getFcmToken();
		if (!StringUtils.isEmpty(fcmToken)){
			HashMap<String, String> msg = new RapidsMap<>();
			msg.put("push_type", PushType.GENERAL.name());
			
			if ("Y".equals(approvalFlag)){
				fcmService.sendFcmToSingle("계단왕", "등록하신 계단이 승인 완료 되었습니다.", fcmToken, msg);
			}else{
				fcmService.sendFcmToSingle("계단왕", "등록하신 계단이 승인 취소 되었습니다.", fcmToken, msg);
			}
		}
		
		
		if (success){
			jsonResponse.put("success", "true");
		}else{
			jsonResponse.put("success", "false");
			jsonResponse.put("error", "에러가 발생하였습니다.");
		}
		
		return jsonResponse;
	}
	
	
	/**
	 * Cafe 승인 변경
	 * @param custSeq
	 * @param approvalFlag
	 * @return
	 */
	@RequestMapping(path="custApprovalEdit", method = RequestMethod.POST)
	public @ResponseBody Map<String,String> userApprovalEdit(
			@RequestParam(name="cSeq") String custSeq,
			@RequestParam(name="approvalFlag", defaultValue="N") String approvalFlag
			
	){
		Map<String, String> jsonResponse = new HashMap<String,String>();
		
		Admin admin = currentAdmin();
		
		logger.error("ADMIN : {}", admin);
		
		Customer cust = adminService.getCustomerBySeq(custSeq);
		User user = adminService.selectUserByEmail(cust.getPostEmail());
		// 관리자의 사용자에 대한 권한 확인
		if (!admin.isSuperAdmin()){
			jsonResponse.put("success", "false");
			jsonResponse.put("error", "에러가 발생하였습니다.");
			return jsonResponse;
		}
		
		cust.setApproval_flag(approvalFlag);
		
		boolean success = adminService.updateCustomerApprovalFlag(cust);
		
		if (user != null) {
			String fcmToken = user.getFcmToken();
			if (!StringUtils.isEmpty(fcmToken)){
				HashMap<String, String> msg = new RapidsMap<>();
				msg.put("push_type", PushType.GENERAL.name());
				
				if ("Y".equals(approvalFlag)){
					fcmService.sendFcmToSingle("계단왕", "등록하신 Cafe가 승인 완료 되었습니다.", fcmToken, msg);
				}else{
					fcmService.sendFcmToSingle("계단왕", "등록하신 Cafe가 승인 취소 되었습니다.", fcmToken, msg);
				}
			}
		}
		
		if (success){
			jsonResponse.put("success", "true");
		}else{
			jsonResponse.put("success", "false");
			jsonResponse.put("error", "에러가 발생하였습니다.");
		}
		
		return jsonResponse;
	}
	
	
	/**
	 * 비콘 제조사 입력
	 * @param beaconManufac
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconManufacAdd", method = RequestMethod.GET)
	public ModelAndView beaconManufacAdd(
			@ModelAttribute(name="beaconManufac") BeaconManufac beaconManufac 
	){
		return new ModelAndView("beaconManufacAdd");
	}
	
	/**
	 * 비콘 제조사 추가 입력 
	 * @param admin
	 * @param beaconManufac
	 * @param redirectAttributes
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconManufacAdd", method = RequestMethod.POST)
	public ModelAndView beaconManufacAddProc(
			@ModelAttribute BeaconManufac beaconManufac,
			RedirectAttributes redirectAttributes
	){
		Admin admin = currentAdmin();
		String inputValidateErrroMessage = beaconManufac.inputValidateErrroMessage();
		if (!StringUtils.isEmpty(inputValidateErrroMessage)){
			redirectAttributes.addFlashAttribute("message",	inputValidateErrroMessage);
		}
		
		String adminSeq = admin.getAdminSeq();
		beaconManufac.setAdminSeq(adminSeq); 	// SET ADMIN
		
		boolean success = adminService.beaconeManufacAdd(beaconManufac);
		if (!success){
			redirectAttributes.addFlashAttribute("message",	"에러가 발생하였습니다.");
		}
		redirectAttributes.addFlashAttribute("message",	"제조사가 추가 되었습니다.");
		
		return new ModelAndView("redirect:beaconManufacList");
	}
	
	/**
	 * 비콘 제조사 수정 (화면)
	 * @param admin
	 * @param manufacSeq
	 * @param beaconManufac
	 * @param redirectAttributes
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconManufacEdit", method = RequestMethod.GET)
	public ModelAndView beaconManufacEdit(
			@RequestParam (name="mSeq") String manufacSeq,
			@ModelAttribute BeaconManufac beaconManufac,
			RedirectAttributes redirectAttributes
			){
		ModelAndView modelAndView = new ModelAndView("beaconManufacEdit");
		beaconManufac.setManufacSeq(manufacSeq);
		beaconManufac = adminService.selectBeaconManufacBySeq(beaconManufac);
		modelAndView.addObject("beaconManufac", beaconManufac);
		
		return modelAndView;
	}
	
	/**
	 * 비콘 제조사 수정 (처리)  
	 * @param admin
	 * @param beaconManufac
	 * @param redirectAttributes
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconManufacEdit", method = RequestMethod.POST)
	public ModelAndView beaconManufacEditProc(
			@ModelAttribute BeaconManufac beaconManufac,
			RedirectAttributes redirectAttributes
			){
		
		
		Admin admin = currentAdmin();
		String inputValidateErrroMessage = beaconManufac.inputValidateErrroMessage();
		if (!StringUtils.isEmpty(inputValidateErrroMessage)){
			redirectAttributes.addFlashAttribute("message",	inputValidateErrroMessage);
		}
		
		String adminSeq = admin.getAdminSeq();
		beaconManufac.setAdminSeq(adminSeq); 	// SET ADMIN
		
		boolean success = adminService.beaconeManufacEdit(beaconManufac);
		if (!success){
			redirectAttributes.addFlashAttribute("message",	"에러가 발생하였습니다.");
		}
		redirectAttributes.addFlashAttribute("message",	"제조사가 수정 되었습니다.");
		
		return new ModelAndView("redirect:beaconManufacList");
	}
	
	/**
	 * 권한 확인 SUPER
	 * 제조사 삭제
	 * @param admin
	 * @param beaconManufac
	 * @param redirectAttributes
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconManufacDelete", method = RequestMethod.POST)
	public ModelAndView beaconManufacDelete(
			@RequestParam(required=true) String manufacSeq,
			@ModelAttribute BeaconManufac beaconManufac,
			RedirectAttributes redirectAttributes
			){
		
		boolean success = adminService.beaconeManufacDelete(beaconManufac);
		if (!success){
			redirectAttributes.addFlashAttribute("message",	"에러가 발생하였습니다.");
		}
		redirectAttributes.addFlashAttribute("message",	"제조사가 삭제 되었습니다.");
		
		return new ModelAndView("redirect:beaconManufacList");
	}
	
	
	/**
	 * 비콘 제조사 리스트
	 * @param beaconManufac
	 * @param page
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconManufacList", method = RequestMethod.GET)
	public ModelAndView beaconManufacList(
			@ModelAttribute BeaconManufac beaconManufac,
			@RequestParam(name="p", defaultValue="1") String page
	){
		ModelAndView modelAndView = new ModelAndView("beaconManufacList");
		
		PagePair pagePair = adminService.selectBeaconManufacList(Integer.valueOf(page), beaconManufac);
		
		List<User> beaconManufacList = (List<User>) pagePair.getList();
		modelAndView.addObject("beaconManufacList", beaconManufacList);
		
		PageNavigation pageNavigation = pagePair.getPageNavigation();
		modelAndView.addObject("pageNavigation", pageNavigation);
		
		return modelAndView;
		
	}
	
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconSearch", method = RequestMethod.GET)
	public ModelAndView beaconSearch(
			@ModelAttribute Beacon beacon,
			@RequestParam(name="page", defaultValue="1") String page
	){
		
		ModelAndView modelAndView = new ModelAndView("beaconSearch");
		
		List<Beacon> beaconList = adminService.beaconSearchList(beacon);
		modelAndView.addObject("beaconList", beaconList);
		
		List<Customer> customerAll = adminService.customerAll(); 
		modelAndView.addObject("customerAll", customerAll);
		
		String custSeq = beacon.getCustSeq();
		if (!StringUtils.isEmpty(custSeq)){
			Customer customer = new Customer();
			customer.setCustSeq(custSeq);
			List<BuildingStair> stairList = adminService.selectBuildingStairListOfCustomer(customer);
			modelAndView.addObject("stairList", stairList);
		}
		
		// 제조사 선택 (전체)
		List<BeaconManufac> beaconManufacList = adminService.selectBeaconManuListFacAll();
		modelAndView.addObject("beaconManufacList", beaconManufacList);
		
		
		return modelAndView;
		
	}
	
	
	
	/**
	 * 비콘 리스트(화면)
	 * 슈퍼관리자인 경우에만 custSeq 를 사용
	 * 일반관리자인 경우 자신의 custSeq 를 이용 
	 * @param admin
	 * @param beacon
	 * @param page
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconList", method = RequestMethod.GET)
	public ModelAndView beaconList(
			@ModelAttribute Beacon beacon,
			@RequestParam(name="cSeq", required=false) String custSeq,
			@RequestParam(name="bSeq", required=false) String buildSeq,
			@RequestParam(name="sSeq", required=false) String stairSeq,
			@RequestParam(name="p", defaultValue="1") String page
	){
		
		ModelAndView modelAndView = new ModelAndView("beaconList");
		Admin admin = currentAdmin(modelAndView);
		
		if (!admin.isSuperAdmin()){
			custSeq = admin.getCustSeq();
		}else if (StringUtils.isEmpty(custSeq)){
			custSeq = admin.getCustSeq();
		}
		beacon.setCustSeq(custSeq);
		beacon.setBuildSeq(buildSeq);
		beacon.setStairSeq(stairSeq);

		
		Customer customer = adminService.getCustomerBySeq(custSeq);
		modelAndView.addObject("customer", customer);
		

		// 건물 리스트 (ALL)
		List<Building> buildingList = adminService.buildingListOfCustomer(customer);
		modelAndView.addObject("buildingList", buildingList);
		
		
		// 계단 선택 (회사) - custSeq, (buildSeq) 로 필터
		Building building = new Building();
		building.setBuildSeq(buildSeq);
		building.setCustSeq(custSeq);
		List<BuildingStair> buildingStairList = adminService.selectBuildingStairListOfBuilding(building);
		modelAndView.addObject("buildingStairList", buildingStairList);
				
		// 비콘 리스트 - custSeq, buildSeq, stairSeq 로 필터
		List<Beacon> beaconList = adminService.selectBeaconListAll(beacon);
		modelAndView.addObject("beaconList", beaconList);
		
		return modelAndView;
		
	}
	
	/**
	 * 비콘 활성화 / 비활성화 변경
	 * @param beacon
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconEnable", method = RequestMethod.POST)
	@ResponseBody Map<String,String> beaconEnable(
			@ModelAttribute Beacon beacon
	){
		
		Map<String, String> jsonResponse = new HashMap<String,String>();
		
		boolean successYn = adminService.beaconEnable(beacon);
		if (successYn){
			jsonResponse.put("ok", "true");
		}else{
			jsonResponse.put("ok", "false");
		}
		
		return jsonResponse;
		
	}
	
	
	
	
	/**
	 * 비콘 추가(화면)
	 * 슈퍼관리자인 경우에만 custSeq 를 사용
	 * 일반관리자인 경우 자신의 custSeq 를 이용 
	 * @param admin
	 * @param beacon
	 * @param page
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconAdd", method = RequestMethod.GET)
	public ModelAndView beaconAdd(
			@ModelAttribute Beacon beacon,
			@RequestParam(name="cSeq", required=false) String custSeq,
			@RequestParam(name="bSeq", required=false) String buildSeq,
			@RequestParam(name="sSeq", required=false) String stairSeq
	){
		
		ModelAndView modelAndView = new ModelAndView("beaconAdd");
		Admin admin = currentAdmin(modelAndView);
		if (!admin.isSuperAdmin()){
			custSeq = admin.getCustSeq();
		}else if (StringUtils.isEmpty(custSeq)){
			custSeq = admin.getCustSeq();
		}
		
		beacon.setCustSeq(custSeq);
		Customer customer = adminService.getCustomerBySeq(custSeq);
		modelAndView.addObject("customer", customer);
		
		logger.error("ADMIN : {}", admin);

		// 건물 리스트
		List<Building> buildingList = adminService.buildingListOfCustomer(customer);
		modelAndView.addObject("buildingList", buildingList);
				
		// 계단 선택 (회사) - custSeq
//		List<BuildingStair> buildingStairList = adminService.selectBuildingStairListOfCustomer(customer);
//		modelAndView.addObject("buildingStairList", buildingStairList);
		
		// 계단 선택 (회사) - custSeq, (buildSeq) 로 필터
		Building building = new Building();
		building.setBuildSeq(buildSeq);
		building.setCustSeq(custSeq);
		List<BuildingStair> buildingStairList = adminService.selectBuildingStairListOfBuilding(building);
		modelAndView.addObject("buildingStairList", buildingStairList);
		
		// 비콘 리스트 
//		List<Beacon> beaconList = adminService.selectBeaconAllOfCustomer(customer);
//		modelAndView.addObject("beaconList", beaconList);
		
		// 비콘 리스트 - custSeq, buildSeq, stairSeq 로 필터
		List<Beacon> beaconList = adminService.selectBeaconListAll(beacon);
		modelAndView.addObject("beaconList", beaconList);
				
		
		// 제조사 선택 (전체)
		List<BeaconManufac> beaconManufacList = adminService.selectBeaconManuListFacAll();
		modelAndView.addObject("beaconManufacList", beaconManufacList);
		
		return modelAndView;
		
	}
	
	
	@RequestMapping(path="godoEdit", method = RequestMethod.POST)
	@ResponseBody Map<String,String> godoEdit(
			@ModelAttribute Beacon beacon
	){
		
		Map<String, String> jsonResponse = new HashMap<String,String>();
		
		String beaconSeq = beacon.getBeaconSeq();
		if (StringUtils.isEmpty(beaconSeq)){
			jsonResponse.put("ok", "false");
			return jsonResponse;
		}
		
		Double godo = beacon.getGodo();
		if (godo == null){
			jsonResponse.put("ok", "false");
			return jsonResponse;
		}
		
		
		
		boolean successYn = adminService.godoEdit(beacon);
		if (!successYn){
			jsonResponse.put("ok", "false");
			return jsonResponse;
		}
		
		jsonResponse.put("ok", "true");
		return jsonResponse;
		
	}
	
	
	
	
	
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconAdd", method = RequestMethod.POST)
	public ModelAndView beaconAddProc(
			@ModelAttribute Beacon beacon,
			RedirectAttributes redirectAttributes
	){
		String custSeq = beacon.getCustSeq();
		
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			custSeq = admin.getCustSeq();
		}
		beacon.setAdminSeq(admin.getAdminSeq());
		
		ModelAndView modelAndView = new ModelAndView("redirect:beaconAdd");
		modelAndView.getModel().put("cSeq", custSeq);
		String buildSeq = beacon.getBuildSeq();
		if (!StringUtils.isEmpty(buildSeq)){
			modelAndView.getModel().put("bSeq", buildSeq);
		}
		String stairSeq = beacon.getStairSeq();
		if (!StringUtils.isEmpty(stairSeq)){
			modelAndView.getModel().put("sSeq", stairSeq);
		}
		
		
		String inputValidateErrroMessage = beacon.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return modelAndView;
		}
		
		// beacon UUID 체크
//		Beacon exBeacon = adminService.beaconUuidCheck(beacon);
//		if (exBeacon != null){
//			redirectAttributes.addFlashAttribute("message", "비콘 UUID 중복 확인 해주세요.");
//			return modelAndView;
//		}
		
		// 계단, 층 중복 체크
		Beacon exBeacon = adminService.beaconFloorCheck(beacon);
		if (exBeacon != null){
			redirectAttributes.addFlashAttribute("message", "해당 계단의 층에는 이미 설치된 비콘이 있습니다.");
			return modelAndView;
		}
				
		
		boolean success = adminService.beaconAdd(beacon);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
		}
		
		redirectAttributes.addFlashAttribute("message", "비콘이 정상 등록 되었습니다.");
		return modelAndView;
		
	}
	
	/**
	 * 비콘 계단, 설치 층 입력시 중복 여부 체크 
	 * @param name
	 * @return
	 */
	@RequestMapping(path="beaconFloorCheck", method = RequestMethod.POST)
	public @ResponseBody Map<String,String> beaconFloorCheck(
				@RequestParam(name="floor" ,required=true) String installFloor,
				@RequestParam(name="stair" ,required=true) String stairSeq
			){
		
		Map<String, String> jsonResponse = new HashMap<String,String>();
		
		Beacon beacon = new Beacon();
		beacon.setInstallFloor(installFloor);
		beacon.setStairSeq(stairSeq);
		
		Beacon exBeacon = adminService.beaconFloorCheck(beacon);
		if (exBeacon == null){
			jsonResponse.put("ok", "true");
			return jsonResponse;
		}
		
		jsonResponse.put("ok", "false");
		return jsonResponse;
	}
	
	
	
	/**
	 * 비콘 UUID 입력시 중복 여부 체크 
	 * @param name
	 * @return
	 */
	@RequestMapping(path="beaconUuidCheck", method = RequestMethod.POST)
	public @ResponseBody Map<String,String> beaconUuidCheck(
				@RequestParam(name="uuid" ,required=true) String beaconUUID
			){
		
		Map<String, String> jsonResponse = new HashMap<String,String>();
		
		Beacon beacon = new Beacon();
		beacon.setBeaconUUID(beaconUUID);
		
		Beacon exBeacon = adminService.beaconUuidCheck(beacon);
		if (exBeacon == null){
			jsonResponse.put("ok", "true");
			return jsonResponse;
		}
		
		jsonResponse.put("ok", "false");
		return jsonResponse;
	}
	
	
	
	
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconEdit", method = RequestMethod.POST)
	public ModelAndView beaconEditProc(
			@ModelAttribute Beacon beacon,
			RedirectAttributes redirectAttributes
	){
		
		
		String custSeq = beacon.getCustSeq();
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			custSeq = admin.getCustSeq();
		}
		beacon.setAdminSeq(admin.getAdminSeq());
		custSeq = beacon.getCustSeq();
		
		
		ModelAndView modelAndView = new ModelAndView("redirect:beaconAdd");
		modelAndView.getModel().put("cSeq", custSeq);
		String buildSeq = beacon.getBuildSeq();
		if (!StringUtils.isEmpty(buildSeq)){
			modelAndView.getModel().put("bSeq", buildSeq);
		}
		String stairSeq = beacon.getStairSeq();
		if (!StringUtils.isEmpty(stairSeq)){
			modelAndView.getModel().put("sSeq", stairSeq);
		}
		
		String beaconSeq = beacon.getBeaconSeq();
		if (StringUtils.isEmpty(beaconSeq)){
			redirectAttributes.addFlashAttribute("message", "비콘 고유번호를 입력해주세요.");
			return modelAndView;
		}
		
		
		String inputValidateErrroMessage = beacon.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return modelAndView;
		}
		
		
		// 계단, 층 중복 체크
		Beacon exBeacon = adminService.beaconFloorCheck(beacon);
		if (exBeacon != null){
			if(!exBeacon.getBeaconSeq().equals(beaconSeq)){
				redirectAttributes.addFlashAttribute("message", "해당 계단의 층에는 이미 설치된 비콘이 있습니다.");
				return modelAndView;
			}
		}
				
				
		
		boolean success = adminService.beaconEdit(beacon);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
		}
		
		redirectAttributes.addFlashAttribute("message", "비콘이 정상 수정 되었습니다.");
		return modelAndView;
		
	}
	
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="beaconDelete", method = RequestMethod.POST)
	public ModelAndView beaconDeleteProc(
			@ModelAttribute Beacon beacon,
			RedirectAttributes redirectAttributes
	){
		
		String custSeq = beacon.getCustSeq();
		Admin admin = currentAdmin();
		if (!admin.isSuperAdmin()){
			beacon.setCustSeq(admin.getCustSeq());
		}
		
		beacon.setAdminSeq(admin.getAdminSeq());
		custSeq = beacon.getCustSeq();
		
		
		ModelAndView modelAndView = new ModelAndView("redirect:beaconAdd");
		modelAndView.getModel().put("cSeq", custSeq);
		String buildSeq = beacon.getBuildSeq();
		if (!StringUtils.isEmpty(buildSeq)){
			modelAndView.getModel().put("bSeq", buildSeq);
		}
		
		String beaconSeq = beacon.getBeaconSeq();
		if (StringUtils.isEmpty(beaconSeq)){
			redirectAttributes.addFlashAttribute("message", "비콘 고유번호를 입력해주세요.");
			return modelAndView;
		}
		
		
		boolean success = adminService.beaconDelete(beacon);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
		}
		
		redirectAttributes.addFlashAttribute("message", "비콘이 정상 삭제 되었습니다.");
		return modelAndView;
		
	}
	
	/**
	 * 게시판 리스트
	 * custSeq, stairSeq, buildSeq 로 각각 필터
	 * @param beaconManufac
	 * @return
	 */
	@RequestMapping(path="bbsList", method = RequestMethod.GET)
	public ModelAndView bbsList(
			@ModelAttribute Bbs bbs,
			@RequestParam(name="p", defaultValue="1") String page
	){
		
		ModelAndView modelAndView = new ModelAndView("bbsList");
		
		String custSeq = null;
		String buildSeq = bbs.getBuildSeq();
		
		Admin currentAdmin = currentAdmin();
		if (!currentAdmin.isSuperAdmin()){
			custSeq = currentAdmin.getCustSeq();
		}
		bbs.setCustSeq(custSeq);
		
		if (custSeq == null){
			// 전체 고객 리스트
			List<Customer> customerAll = adminService.customerAll();
			modelAndView.addObject("customerAll", customerAll);
		} else {
			Customer customer = adminService.getCustomerBySeq(custSeq);
			// 건물 리스트
			List<Building> buildingList = adminService.buildingListOfCustomer(customer);
			modelAndView.addObject("buildingList", buildingList);
		}
		
		// 계단 리스트
		if (buildSeq != null){
			Building building = new Building();
			building.setBuildSeq(buildSeq);
			building.setCustSeq(custSeq);
			List<BuildingStair> buildingStairList = adminService.selectBuildingStairListOfBuilding(building);
			modelAndView.addObject("buildingStairList", buildingStairList);
		}
		

		PagePair pagePair = adminService.selectBbsList(Integer.valueOf(page), bbs);
		List<Bbs> bbsList = (List<Bbs>) pagePair.getList();
		PageNavigation pageNavigation = pagePair.getPageNavigation();
		
		modelAndView.addObject("bbsList", bbsList);
		modelAndView.addObject("pageNavigation", pageNavigation);
		
		return modelAndView;
	}
	
	
	/**
	 * 게시판 리스트
	 * custSeq, stairSeq, buildSeq 로 각각 필터
	 * @param beaconManufac
	 * @return
	 */
	@RequestMapping(path="cafeNoticeList", method = RequestMethod.GET)
	public ModelAndView cafeNoticeList(
			@ModelAttribute Bbs bbs,
			@ModelAttribute Cafe cafe,
			@RequestParam(name="cafeseq", required=false) String cafeseq,
			@RequestParam(name="p", defaultValue="1") String page
	){
		
		ModelAndView modelAndView = new ModelAndView("cafeNoticeList");
		
		List<Cafe> cafeList;
		
		String isSuperAdmin = "N";
		
		Admin admin = currentAdmin(modelAndView);
		if (admin.isSuperAdmin()){
			isSuperAdmin = "Y";
		}
		
		if ("Y".equals(isSuperAdmin)) {
			cafeList = adminService.selectCafeAllList(cafe);
			
			modelAndView.addObject("cafeList", cafeList);
			modelAndView.addObject("loginseq", "1");
			modelAndView.addObject("cafeseq", cafeseq);
		} else {
			User user = adminService.selectUserByEmail(admin.getEmail());
			if (null != user) {
				cafe.setAdminseq(user.getUserSeq());
				
				cafeList = adminService.selectCafeOfMineAllList(cafe);
				modelAndView.addObject("cafeList", cafeList);
			} else {
				modelAndView.addObject("cafeList", null);
			}
			
			modelAndView.addObject("loginseq", user.getUserSeq());
			modelAndView.addObject("cafeseq", cafeseq);
		}
		
		
		
		if ("".equals(cafeseq)) {
			modelAndView.addObject("cafeSelected", false);
		} else {
			modelAndView.addObject("cafeSelected", true);
			cafe.setCafeseq(cafeseq);
			bbs.setCafeseq(cafeseq);
			//rank.setCafeseq(rank.getCafeseq());
		}

		
		
		PagePair pagePair = adminService.selectCafeBbsList(Integer.valueOf(page), bbs);
		List<Bbs> bbsList = (List<Bbs>) pagePair.getList();
		PageNavigation pageNavigation = pagePair.getPageNavigation();
		
		modelAndView.addObject("bbsList", bbsList);
		modelAndView.addObject("pageNavigation", pageNavigation);
		
		return modelAndView;
	}
	
	/**
	 * 푸쉬 리스트
	 * @param push
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path="cafePushList", method = RequestMethod.GET)
	public ModelAndView cafePushList(
			@ModelAttribute Push push,
			@ModelAttribute Cafe cafe,
			@RequestParam(name="cafeseq", required=false) String cafeseq,
			@RequestParam(name="p", defaultValue="1") String page
	){
		
		ModelAndView modelAndView = new ModelAndView("cafePushList");
		
		
		List<Cafe> cafeList;
		
		String isSuperAdmin = "N";
		
		Admin admin = currentAdmin(modelAndView);
		if (admin.isSuperAdmin()){
			isSuperAdmin = "Y";
		}
		
		if ("Y".equals(isSuperAdmin)) {
			cafeList = adminService.selectCafeAllList(cafe);
			
			modelAndView.addObject("cafeList", cafeList);
			modelAndView.addObject("loginseq", "1");
			modelAndView.addObject("cafeseq", cafeseq);
		} else {
			User user = adminService.selectUserByEmail(admin.getEmail());
			if (null != user) {
				cafe.setAdminseq(user.getUserSeq());
				
				cafeList = adminService.selectCafeOfMineAllList(cafe);
				modelAndView.addObject("cafeList", cafeList);
			} else {
				modelAndView.addObject("cafeList", null);
			}
			
			modelAndView.addObject("loginseq", user.getUserSeq());
			modelAndView.addObject("cafeseq", cafeseq);
		}
		
		
		
		if ("".equals(cafeseq)) {
			modelAndView.addObject("cafeSelected", false);
		} else {
			modelAndView.addObject("cafeSelected", true);
			cafe.setCafeseq(cafeseq);
		}

		push.setAdminSeq(admin.getAdminSeq());
		
		
		PagePair pagePair = adminService.selectCafePushList(Integer.valueOf(page), push);
		List<Push> pushList = (List<Push>) pagePair.getList();
		PageNavigation pageNavigation = pagePair.getPageNavigation();
		
		modelAndView.addObject("pushList", pushList);
		modelAndView.addObject("pageNavigation", pageNavigation);
		
		return modelAndView;
	}
	
	/**
	 * 푸쉬 추가 처리
	 * @param redirectAttributes
	 * @param push
	 * @return
	 */
	@RequestMapping(path="cafePushAdd", method = RequestMethod.POST)
	public ModelAndView cafePushAddProc(
			RedirectAttributes redirectAttributes,
			@RequestParam(name="cafeseq", required=false) String cafeseq,
			@ModelAttribute Push push
	){
		
		Admin currentAdmin = currentAdmin();
		
		
		ModelAndView modelAndView = new ModelAndView("redirect:cafePushList?cafeseq="+cafeseq);
		
		push.setAdminSeq(currentAdmin.getAdminSeq()); // SET ADMIN
		
		String inputValidateErrroMessage = push.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return modelAndView;
		}
		
		System.out.println(push.toString());
		
		String reserveTime = push.getReserveTime();
		if (reserveTime != null && reserveTime.length() == 12){
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
			try {
				f.parse(reserveTime);
			} catch (ParseException e) {
				push.setReserveTime(null);
				e.printStackTrace();
			}
		}else{
			push.setReserveTime(null);
		}
		
		push.setCafeseq(cafeseq);
		boolean success = adminService.cafePushAdd(push);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
		}
		
		
		
		// 즉시 발송이면 푸쉬 발송
		if (push.getReserveTime() == null){
			List<String> targets = adminService.pushTargets(push);
			
			String pushTitle = push.getPushTitle();
			String pushContent = push.getPushContent();
			
			HashMap<String, String> msg = new RapidsMap<>();
			msg.put("push_type", PushType.GENERAL.name());
			
			fcmService.sendFcmToGroup(pushTitle, pushContent, targets, msg);
			
			adminService.pushSent(push); // 발송 여부 저장
			
		}
		
		
		redirectAttributes.addFlashAttribute("message", "등록 되었습니다.");
		return modelAndView;
	}
	
	@RequestMapping(path="cafePushEdit", method = RequestMethod.POST)
	public ModelAndView cafePushEditProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute Push push
	){
		
		ModelAndView modelAndView = new ModelAndView("redirect:cafePushList?cafeseq="+push.getCafeseq());
		
		Admin currentAdmin = currentAdmin();

		push.setAdminSeq(currentAdmin.getAdminSeq()); // SET ADMIN
		String pushSeq = push.getPushSeq();
		if (StringUtils.isEmpty(pushSeq)){
			redirectAttributes.addFlashAttribute("message", "푸쉬 등록 번호가 없습니다.");
			return modelAndView;
		}
		
		String reserveTime = push.getReserveTime();
		if (reserveTime.length() == 12){
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
			try {
				f.parse(reserveTime);
			} catch (ParseException e) {
				push.setReserveTime(null);
				e.printStackTrace();
			}
		}else{
			push.setReserveTime(null);
		}
		

		boolean success = adminService.cafePushEdit(push);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
		}
		
		// 즉시 발송이면 푸쉬 발송
		if (push.getReserveTime() == null){
			List<String> targets = adminService.pushTargets(push);
			
			String pushTitle = push.getPushTitle();
			String pushContent = push.getPushContent();
			
			HashMap<String, String> msg = new RapidsMap<>();
			msg.put("push_type", PushType.GENERAL.name());
			fcmService.sendFcmToGroup(pushTitle, pushContent, targets, msg);
			
			adminService.pushSent(push);
			
		}
		
		redirectAttributes.addFlashAttribute("message", "수정 되었습니다.");
		return modelAndView;
	}
	
	
	@RequestMapping(path="cafePushDelete", method = RequestMethod.POST)
	public ModelAndView cafePushDeleteProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute Push push
	){
		ModelAndView modelAndView = new ModelAndView("redirect:cafePushList?cafeseq="+push.getCafeseq());
		
		String pushSeq = push.getPushSeq();
		if (StringUtils.isEmpty(pushSeq)){
			redirectAttributes.addFlashAttribute("message", "푸쉬 메세지 등록 번호가 없습니다.");
			return modelAndView;
		}
		
		boolean success = adminService.pushDelete(push);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
		}
		
		redirectAttributes.addFlashAttribute("message", "삭제 되었습니다.");
		return modelAndView;
	}
	
	/**
	 * 푸쉬 리스트
	 * @param push
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path="pushList", method = RequestMethod.GET)
	public ModelAndView pushList(
			@ModelAttribute Push push,
			@ModelAttribute Cafe cafe,
			@RequestParam(name="p", defaultValue="1") String page
	){
		
		ModelAndView modelAndView = new ModelAndView("pushList");
		
		String custSeq = null;
		String buildSeq = push.getBuildSeq();

		Admin currentAdmin = currentAdmin();
		if (!currentAdmin.isSuperAdmin()){
			custSeq = currentAdmin.getCustSeq();
		}
		push.setCustSeq(custSeq);
		
		// 내 카페 리스트 획득하기
		User user = adminService.selectUserByEmail(currentAdmin.getEmail());
		if (null != user) {
			
			cafe.setAdminseq(user.getUserSeq());
			
			List<Cafe> cafeList = adminService.selectCafeOfMineAllList(cafe);
			modelAndView.addObject("cafeList", cafeList);
		} else {
			modelAndView.addObject("cafeList", null);
		}
		
		
		
		if (custSeq == null){
			// 전체 고객 리스트
			//List<Customer> customerAll = adminService.customerAll();
			//modelAndView.addObject("customerAll", customerAll);
			List<Cafe> cafeAllList = adminService.selectCafeAllList(cafe);
			modelAndView.addObject("cafeAll", cafeAllList);
		} else {
			Customer customer = adminService.getCustomerBySeq(custSeq);
			// 건물 리스트
			List<Building> buildingList = adminService.buildingListOfCustomer(customer);
			modelAndView.addObject("buildingList", buildingList);
		}
		
		// 계단 리스트
		if (buildSeq != null){
			Building building = new Building();
			building.setBuildSeq(buildSeq);
			building.setCustSeq(custSeq);
			List<BuildingStair> buildingStairList = adminService.selectBuildingStairListOfBuilding(building);
			modelAndView.addObject("buildingStairList", buildingStairList);
		}

		PagePair pagePair = adminService.selectPushList(Integer.valueOf(page), push);
		List<Push> pushList = (List<Push>) pagePair.getList();
		PageNavigation pageNavigation = pagePair.getPageNavigation();
		
		modelAndView.addObject("pushList", pushList);
		modelAndView.addObject("pageNavigation", pageNavigation);
		
		return modelAndView;
	}
	
	/**
	 * 푸쉬 추가 처리
	 * @param redirectAttributes
	 * @param push
	 * @return
	 */
	@RequestMapping(path="pushAdd", method = RequestMethod.POST)
	public ModelAndView pushAddProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute Push push
	){
		
		String custSeq = push.getCustSeq();
		Admin currentAdmin = currentAdmin();
		if (!currentAdmin.isSuperAdmin()){
			custSeq = currentAdmin.getCustSeq();
			push.setCustSeq(custSeq);
		}
		
		
		
		ModelAndView modelAndView = new ModelAndView("redirect:pushList");
		
		push.setAdminSeq(currentAdmin.getAdminSeq()); // SET ADMIN
		
		String inputValidateErrroMessage = push.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return modelAndView;
		}
		
		System.out.println(push.toString());
		
		String reserveTime = push.getReserveTime();
		if (reserveTime != null && reserveTime.length() == 12){
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
			try {
				f.parse(reserveTime);
			} catch (ParseException e) {
				push.setReserveTime(null);
				e.printStackTrace();
			}
		}else{
			push.setReserveTime(null);
		}
		
		
		boolean success = adminService.pushAdd(push);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
		}
		
		
		
		// 즉시 발송이면 푸쉬 발송
		if (push.getReserveTime() == null){
			List<String> targets = adminService.pushTargets(push);
			
			String pushTitle = push.getPushTitle();
			String pushContent = push.getPushContent();
			
			HashMap<String, String> msg = new RapidsMap<>();
			msg.put("push_type", PushType.GENERAL.name());
			
			fcmService.sendFcmToGroup(pushTitle, pushContent, targets, msg);
			
			adminService.pushSent(push); // 발송 여부 저장
			
		}
		
		
		redirectAttributes.addFlashAttribute("message", "등록 되었습니다.");
		return modelAndView;
	}
	
	
	@RequestMapping(path="pushEdit", method = RequestMethod.POST)
	public ModelAndView pushEditProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute Push push
	){
		
		ModelAndView modelAndView = new ModelAndView("redirect:pushList");
		
		Admin currentAdmin = currentAdmin();
		push.setAdminSeq(currentAdmin.getAdminSeq()); // SET ADMIN
		String custSeq = push.getCustSeq();
		if (!currentAdmin.isSuperAdmin()){
			custSeq = currentAdmin.getCustSeq();
			push.setCustSeq(custSeq);
		}
		
		
		String inputValidateErrroMessage = push.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return modelAndView;
		}
		
		String pushSeq = push.getPushSeq();
		if (StringUtils.isEmpty(pushSeq)){
			redirectAttributes.addFlashAttribute("message", "푸쉬 등록 번호가 없습니다.");
			return modelAndView;
		}
		
		if (!currentAdmin.isSuperAdmin()){
			Push rPush = adminService.getPushBySeq(push);
			if (!custSeq.equals(rPush.getCustSeq())){
				redirectAttributes.addFlashAttribute("message", "잘못된 접근입니다.");
				return modelAndView;
			}
		}
		
		
		
		
		String reserveTime = push.getReserveTime();
		if (reserveTime.length() == 12){
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
			try {
				f.parse(reserveTime);
			} catch (ParseException e) {
				push.setReserveTime(null);
				e.printStackTrace();
			}
		}else{
			push.setReserveTime(null);
		}
		
		
		boolean success = adminService.pushEdit(push);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
		}
		
		// 즉시 발송이면 푸쉬 발송
		if (push.getReserveTime() == null){
			List<String> targets = adminService.pushTargets(push);
			
			String pushTitle = push.getPushTitle();
			String pushContent = push.getPushContent();
			
			HashMap<String, String> msg = new RapidsMap<>();
			msg.put("push_type", PushType.GENERAL.name());
			fcmService.sendFcmToGroup(pushTitle, pushContent, targets, msg);
			
			adminService.pushSent(push);
			
		}
		
		redirectAttributes.addFlashAttribute("message", "수정 되었습니다.");
		return modelAndView;
	}
	
	
	@RequestMapping(path="pushDelete", method = RequestMethod.POST)
	public ModelAndView pushDeleteProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute Push push
	){
		
		Admin currentAdmin = currentAdmin();
		push.setAdminSeq(currentAdmin.getAdminSeq()); // SET ADMIN
		String custSeq = push.getCustSeq();
		if (!currentAdmin.isSuperAdmin()){
			custSeq = currentAdmin.getCustSeq();
			push.setCustSeq(custSeq);
		}
		
		ModelAndView modelAndView = new ModelAndView("redirect:pushList");
		
		String pushSeq = push.getPushSeq();
		if (StringUtils.isEmpty(pushSeq)){
			redirectAttributes.addFlashAttribute("message", "푸쉬 메세지 등록 번호가 없습니다.");
			return modelAndView;
		}
		
		if (!currentAdmin.isSuperAdmin()){
			Push rPush = adminService.getPushBySeq(push);
			if (!custSeq.equals(rPush.getCustSeq())){
				redirectAttributes.addFlashAttribute("message", "잘못된 접근입니다.");
				return modelAndView;
			}
		}
		
		// cust_seq, bbs_seq 조건
		boolean success = adminService.pushDelete(push);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
		}
		
		redirectAttributes.addFlashAttribute("message", "삭제 되었습니다.");
		return modelAndView;
	}
	
	
	
	
	
	
	
	
	/**
	 * AJAX
	 * 해당 고객의 빌딩 리스트
	 * @param customer (custSeq)
	 * @return
	 */
	@RequestMapping(path="buildingSelectOption", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> buildingSelectOption(
			@ModelAttribute Customer customer
	){
		
		Admin currentAdmin = currentAdmin();
		if (!currentAdmin.isSuperAdmin()){
			String custSeq = currentAdmin.getCustSeq();
			customer.setCustSeq(custSeq);
		}

		// 건물 리스트
		List<Building> buildingList = adminService.buildingListOfCustomer(customer);
		
		Map<String, Object> jsonResponse = new HashMap<String,Object>();
		jsonResponse.put("success", "true");
		jsonResponse.put("buildingList", buildingList);
		
		return jsonResponse;
	}
	
	
	/**
	 * AJAX
	 * 해당 고객의 계단 리스트
	 * @param customer (buildSeq)
	 * @return
	 */
	@RequestMapping(path="stairSelectOption", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> stairSelectOption(
			@ModelAttribute Building building
	){
		
		Admin currentAdmin = currentAdmin();
		if (!currentAdmin.isSuperAdmin()){
			String custSeq = currentAdmin.getCustSeq();
			building.setCustSeq(custSeq);
		}

		// 계단 선택 (회사) - custSeq, (buildSeq) 로 필터
		List<BuildingStair> buildingStairList = adminService.selectBuildingStairListOfBuilding(building);

		Map<String, Object> jsonResponse = new HashMap<String,Object>();
		jsonResponse.put("success", "true");
		jsonResponse.put("buildingStairList", buildingStairList);
		
		return jsonResponse;
	}
	
	
	
	
	
	
	@SuppressWarnings("null")
	@RequestMapping(path="cafeNoticeAdd", method = RequestMethod.POST)
	public ModelAndView cafeNoticeAddProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute Bbs bbs
	){
		
		ModelAndView modelAndView = new ModelAndView("redirect:cafeNoticeList?cafeseq="+bbs.getCafeseq());
		
		User user = adminService.selectUserByEmail(currentAdmin().getEmail());
		if (null == user) {
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
		} else {
			bbs.setUser_seq(user.getUserSeq());
		}
		
		boolean success = adminService.cafeBbsAdd(bbs);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
		}
		
		redirectAttributes.addFlashAttribute("message", "게시물이 등록 되었습니다.");
		return modelAndView;
	}
	
	@RequestMapping(path="cafeNoticeEdit", method = RequestMethod.POST)
	public ModelAndView cafeNoticeProc(
			RedirectAttributes redirectAttributes,
			@RequestParam(name="cafeseq", required=false) String cafeseq,
			@ModelAttribute Bbs bbs
	){
			
		ModelAndView modelAndView = new ModelAndView("redirect:cafeNoticeList?cafeseq="+cafeseq);

		boolean success = adminService.cafeBbsEdit(bbs);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
			
		}
		
		redirectAttributes.addFlashAttribute("message", "게시물이 수정 되었습니다.");
		return modelAndView;
	}
	
	@RequestMapping(path="cafeNoticeDetail", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> cafeNoticeDetail(
			@ModelAttribute Bbs bbs
	){
		Map<String, Object> jsonResponse = new HashMap<String,Object>();
		
		
		String notiseq = bbs.getNotiseq();
		if (StringUtils.isEmpty(notiseq)){
			jsonResponse.put("success", "false");
			jsonResponse.put("error", "파라메터 에러");
			return jsonResponse;
		}
		
		// cust_seq, bbs_seq 조건
		bbs = adminService.selectCafeNoticeBySeq(bbs);
		if (bbs == null){
			jsonResponse.put("success", "false");
			jsonResponse.put("error", "no bbs");
			return jsonResponse;
		}
		
		jsonResponse.put("success", "true");
		jsonResponse.put("bbs", bbs);
		
		return jsonResponse;
	}
	
	
	
	@RequestMapping(path="bbsAdd", method = RequestMethod.POST)
	public ModelAndView bbsAddProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute Bbs bbs
	){
		
		String custSeq = bbs.getCustSeq();
		Admin currentAdmin = currentAdmin();
		if (!currentAdmin.isSuperAdmin()){
			custSeq = currentAdmin.getCustSeq();
			bbs.setCustSeq(custSeq);
		}
		
		
		
		ModelAndView modelAndView = new ModelAndView("redirect:bbsList");
		
		String inputValidateErrroMessage = bbs.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return modelAndView;
		}
		
		bbs.setAdminSeq(currentAdmin.getAdminSeq()); // SET ADMIN
		
		if (!"Y".equals(bbs.getPushFlag())){		// SET PUSH STATUS
			bbs.setPushFlag("N");
		}
		
		boolean success = adminService.bbsAdd(bbs);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
			
		//등록 되었으면 푸시 전송	
		}else if (bbs.getPushFlag().equals("Y")) {
			
			Push push = new Push();
			push.setCustSeq(bbs.getCustSeq());
			push.setBuildSeq(bbs.getBuildSeq());
			List<String> targets = adminService.pushTargets(push);
			
			
			HashMap<String, String> msg = new RapidsMap<>();
			// 전체 회사 대상 슈퍼관리자
			if (bbs.getCustSeq()== null && currentAdmin.isSuperAdmin()){
				msg.put("target", "main");
			}else{
				msg.put("target", "customer");
			}
			
			adminService.pushSent(push);
			if (targets != null && targets.size() > 0 ){
				msg.put("push_type", PushType.NOTICE_EVENT.name());
				msg.put("message", bbs.getTitle());
				msg.put("bbs_seq", bbs.getBbsSeq());				
				fcmService.sendFcmToGroup("계단왕", bbs.getTitle(), targets, msg);
			}
			
			/*
			List<String> tokens = null;
			HashMap<String, String> msg = new RapidsMap<>();
			
			if( currentAdmin.isSuperAdmin() ){//전체대상 전송 
				tokens = adminService.getFcmTokens("");
				msg.put("target", "main");
			}else{//고객사 소속만 발송 
				String customerSeq = String.valueOf(bbs.getCustSeq());
				tokens = adminService.getFcmTokensOfCustomer(customerSeq);
				msg.put("target", "customer");
			}
			
			if(tokens != null && tokens.size() > 0){
				msg.put("push_type", PushType.NOTICE_EVENT.name());
				msg.put("message", bbs.getTitle());
				msg.put("bbs_seq", bbs.getBbsSeq());				
				fcmService.sendFcmToGroup("계단왕", bbs.getTitle(), tokens, msg);
			}
			*/
			
		}
		
		redirectAttributes.addFlashAttribute("message", "게시물 등록 되었습니다.");
		return modelAndView;
	}
	
	@RequestMapping(path="bbsEdit", method = RequestMethod.POST)
	public ModelAndView bbsEditProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute Bbs bbs
	){
			
		ModelAndView modelAndView = new ModelAndView("redirect:bbsList");
		Admin currentAdmin = currentAdmin();
		bbs.setAdminSeq(currentAdmin.getAdminSeq()); // SET ADMIN
		
		String custSeq = bbs.getCustSeq();
		if (!currentAdmin.isSuperAdmin()){
			custSeq = currentAdmin.getCustSeq();
			bbs.setCustSeq(custSeq);
		}

		
		String inputValidateErrroMessage = bbs.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return modelAndView;
		}
		
		String bbsSeq = bbs.getBbsSeq();
		if (StringUtils.isEmpty(bbsSeq)){
			redirectAttributes.addFlashAttribute("message", "게시물 번호가 없습니다.");
			return modelAndView;
		}
		
		if (!currentAdmin.isSuperAdmin()){
			Bbs rBbs = adminService.selectBbsBySeq(bbs);
			if (!custSeq.equals(rBbs.getCustSeq())){
				redirectAttributes.addFlashAttribute("message", "잘못된 접근입니다.");
				return modelAndView;
			}
		}
		
		
		if (!"Y".equals(bbs.getPushFlag())){		// SET PUSH STATUS
			bbs.setPushFlag("N");
		}
		
		
		boolean success = adminService.bbsEdit(bbs);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
			
		}else if (bbs.getPushFlag().equals("Y")) {
			
			Push push = new Push();
			push.setCustSeq(bbs.getCustSeq());
			push.setBuildSeq(bbs.getBuildSeq());
			List<String> targets = adminService.pushTargets(push);
			
			
			HashMap<String, String> msg = new RapidsMap<>();
			// 전체 회사 대상 슈퍼관리자
			if (bbs.getCustSeq()== null && currentAdmin.isSuperAdmin()){
				msg.put("target", "main");
			}else{
				msg.put("target", "customer");
			}
			
			adminService.pushSent(push);
			if (targets != null && targets.size() > 0 ){
				msg.put("push_type", PushType.NOTICE_EVENT.name());
				msg.put("message", bbs.getTitle());
				msg.put("bbs_seq", bbs.getBbsSeq());				
				fcmService.sendFcmToGroup("계단왕", bbs.getTitle(), targets, msg);
			}
			
		}


		
		redirectAttributes.addFlashAttribute("message", "게시물 수정 되었습니다.");
		return modelAndView;
	}
	
	
	@RequestMapping(path="bbsDelete", method = RequestMethod.POST)
	public ModelAndView bbsDeleteProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute Bbs bbs
	){
		
		Admin currentAdmin = currentAdmin();
		bbs.setAdminSeq(currentAdmin.getAdminSeq()); // SET ADMIN
		String custSeq = bbs.getCustSeq();
		if (!currentAdmin.isSuperAdmin()){
			custSeq = currentAdmin.getCustSeq();
			bbs.setCustSeq(custSeq);
		}
		
		
		ModelAndView modelAndView = new ModelAndView("redirect:bbsList");
		
		String bbsSeq = bbs.getBbsSeq();
		if (StringUtils.isEmpty(bbsSeq)){
			redirectAttributes.addFlashAttribute("message", "게시물 번호가 없습니다.");
			return modelAndView;
		}
		
		if (!currentAdmin.isSuperAdmin()){
			Bbs rBbs = adminService.selectBbsBySeq(bbs);
			if (!custSeq.equals(rBbs.getCustSeq())){
				redirectAttributes.addFlashAttribute("message", "잘못된 접근입니다.");
				return modelAndView;
			}
		}
		
		
		// cust_seq, bbs_seq 조건
		boolean success = adminService.bbsDelete(bbs);
		if (!success){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다.");
			return modelAndView;
		}
		
		redirectAttributes.addFlashAttribute("message", "게시물 삭제 되었습니다.");
		return modelAndView;
	}
	
	
	
	@RequestMapping(path="bbsDetail", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> bbsDetail(
			@ModelAttribute Bbs bbs
	){
		Map<String, Object> jsonResponse = new HashMap<String,Object>();
		
		
		String custSeq = null;
		Admin currentAdmin = currentAdmin();
		if (!currentAdmin.isSuperAdmin()){
			custSeq = currentAdmin.getCustSeq();
			bbs.setCustSeq(custSeq);
		}
		
		
		
		String bbsSeq = bbs.getBbsSeq();
		if (StringUtils.isEmpty(bbsSeq)){
			jsonResponse.put("success", "false");
			jsonResponse.put("error", "파라메터 에러");
			return jsonResponse;
		}
		
		// cust_seq, bbs_seq 조건
		bbs = adminService.selectBbsBySeq(bbs);
		if (bbs == null){
			jsonResponse.put("success", "false");
			jsonResponse.put("error", "no bbs");
			return jsonResponse;
		}
		
		
//		custSeq = bbs.getCustSeq();
//		if (custSeq != null){
//			Customer customer = adminService.getCustomerBySeq(custSeq);
//			// 건물 리스트
//			List<Building> buildingList = adminService.buildingListOfCustomer(customer);
//			jsonResponse.put("buildingList", buildingList);
//		}
//		
//		String buildSeq = bbs.getBuildSeq();
//		if (buildSeq != null){
//			Building building = new Building();
//			building.setBuildSeq(buildSeq);
//			building.setCustSeq(custSeq);
//			List<BuildingStair> buildingStairList = adminService.selectBuildingStairListOfBuilding(building);
//			jsonResponse.put("buildingStairList", buildingStairList);
//		}
		
		
		jsonResponse.put("success", "true");
		jsonResponse.put("bbs", bbs);
		
		return jsonResponse;
	}
	
	
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="appVersion", method = RequestMethod.GET)
	public ModelAndView appVersion(
			RedirectAttributes redirectAttributes
	){
		ModelAndView modelAndView = new ModelAndView("appVersion");
		List<AppVersion> vers = adminService.getAppVersion();
		modelAndView.addObject("vers", vers);
		
		return modelAndView;
	}
	
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="appVersion", method = RequestMethod.POST)
	public ModelAndView appVersionProc(
			RedirectAttributes redirectAttributes,
			@ModelAttribute AppVersion ver
	){

		ModelAndView modelAndView = new ModelAndView("redirect:/appVersion");
		
		Admin currentAdmin = currentAdmin();
		ver.setAdminSeq(currentAdmin.getAdminSeq()); // SET ADMIN
		String inputValidateErrroMessage = ver.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return modelAndView;
		}
		
		boolean successYn = adminService.addVersion(ver);
		if (!successYn){
			redirectAttributes.addFlashAttribute("message", "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
			return modelAndView;
		}
		
		redirectAttributes.addFlashAttribute("message", "등록되었습니다.");
		return modelAndView;
	}
	
	
	
	/**
	 * 사용자 통계
	 * http://localhost:8080/admin/statUser?startDate=20180601&endDate=20180701
	 * @param custSeq, userSeq, deptSeq
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(path="statUser", method = RequestMethod.GET)
	public ModelAndView statUser(
			@ModelAttribute(name="rank")  Ranking rank,
			RedirectAttributes redirectAttributes
			){
		
		ModelAndView modelAndView = new ModelAndView("statUser");
		Admin admin = currentAdmin(modelAndView);
		if (!admin.isSuperAdmin()){
			rank.setCustSeq(admin.getCustSeq());
		}else if (rank.getCustSeq() == null){
			rank.setCustSeq(admin.getCustSeq());
		}
		
		String startDate = rank.getStartDate();
		if (startDate == null || startDate.length() != 8){
			startDate = DateUtils.getDateTextByAddMonths("yyyyMMdd", DateUtils.currentDate("yyyyMMdd"), -1);
			rank.setStartDate(startDate);
		}
		String endDate = rank.getEndDate();
		if (endDate == null || endDate.length() != 8){
			endDate = DateUtils.currentDate("yyyyMMdd");
			rank.setEndDate(endDate);
		}

		
		String inputValidateErrroMessage = rank.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return redirectCustomerOne(rank.getCustSeq());
		}
		
		// 부서 정보 리스트
		Customer customer = new Customer();
		customer.setCustSeq(rank.getCustSeq());
		
		Customer customerInfo = checkCustomerAdmin(rank.getCustSeq());
		
		List<Department> departmentList = adminService.departmentListOfCustomer(customer);
		modelAndView.addObject("departmentList", departmentList);
		
		// 각 사용자 랭킹 리스트 (u_user_building_map 기준으로 회사 판별)
		List<Ranking> ranks = "N".equals(customerInfo.getPermit_other_building()) ? 
				adminService.getRankingIndividual(rank) :
				adminService.getRankingIndividualOther(rank) ;
				
		modelAndView.addObject("ranks", ranks);
		
		Integer totalAmount = 0;
		for (Ranking r:ranks){
			totalAmount += r.getRecordAmount();
		}
		modelAndView.addObject("totalAmount", totalAmount);
		
		
		// 소속 회사 부서별 랭킹 (부서개 2개 이상일때만 표시)
		if (departmentList != null && departmentList.size()> 1){
			List<Ranking> departRanks = "N".equals(customerInfo.getPermit_other_building()) ? 
					adminService.getGroupRankingByDepart(rank):
					adminService.getGroupRankingByDepartOther(rank);
			modelAndView.addObject("departRanks", departRanks);
		}
		
		// 특정 대상 사용자 기록 조회
		if (rank.getUserSeq() != null){
			List<Ranking> userRecords = adminService.getRecordIndividual(rank);
			modelAndView.addObject("userRecords", userRecords);
		}
		
		
		return modelAndView;
	}
	
	@RequestMapping(value="statUser/download", method = RequestMethod.GET)
	@ResponseBody
	public void downloadStatUser(
			@RequestParam Map<String,Object> params
			,@ModelAttribute(name="rank")  Ranking rank
			,HttpServletRequest req
			,HttpServletResponse res
			){
		try {
			rank.setDeptSeq(params.get("inpDeptSeq").toString());
			String[] columns 
				= { "랭킹", "이름", "닉네임", "회사명", "부서명", "총 오른 층수" };
			String deptname = "";
		
			ModelAndView modelAndView = new ModelAndView("statUser");
			Admin admin = currentAdmin(modelAndView);
			if (!admin.isSuperAdmin()){
				rank.setCustSeq(admin.getCustSeq());
			}else if (rank.getCustSeq() == null){
				rank.setCustSeq(admin.getCustSeq());
			}
			
			String startDate = rank.getStartDate();
			if (startDate == null || startDate.length() != 8){
				startDate = DateUtils.getDateTextByAddMonths("yyyyMMdd", DateUtils.currentDate("yyyyMMdd"), -1);
				rank.setStartDate(startDate);
			}
			String endDate = rank.getEndDate();
			if (endDate == null || endDate.length() != 8){
				endDate = DateUtils.currentDate("yyyyMMdd");
				rank.setEndDate(endDate);
			}
			
			// 부서 정보 리스트
			Customer customer = new Customer();
			customer.setCustSeq(rank.getCustSeq());
			Customer customerInfo = checkCustomerAdmin(rank.getCustSeq());
			
			List<Department> departmentList = adminService.departmentListOfCustomer(customer);
			
			for (int idx = 0; idx < departmentList.size(); idx++) {
				if (departmentList.get(idx).getDeptSeq().equals(params.get("inpDeptSeq"))) {
					deptname = departmentList.get(idx).getDeptName();
					break;
				}
			}
			
			if ("".equals(deptname)) {
				deptname = "전체부서";
			}
			
			// 각 사용자 랭킹 리스트 (u_user_building_map 기준으로 회사 판별)
			//List<Ranking> ranks = null;
			//ranks = adminService.getRankingIndividual(rank);
			// 각 사용자 랭킹 리스트 (u_user_building_map 기준으로 회사 판별)
			
			List<Ranking> ranks = "N".equals(customerInfo.getPermit_other_building()) ? 
					adminService.getRankingIndividual(rank) :
					adminService.getRankingIndividualOther(rank) ;
			
			/*if (departmentList != null && departmentList.size()> 1){
				List<Ranking> departRanks = adminService.getGroupRankingByDepart(rank);
				modelAndView.addObject("departRanks", departRanks);
			}
			
			// 특정 대상 사용자 기록 조회
			if (rank.getUserSeq() != null){
				ranks = adminService.getRecordIndividual(rank);
			}*/
			
			
			Workbook workbook = new XSSFWorkbook();
		    Sheet sheet = workbook.createSheet("개인별 랭킹");
			
		    Font filterFont = workbook.createFont();
		    filterFont.setFontHeightInPoints((short) 12);
		    filterFont.setColor(IndexedColors.RED.getIndex());
		    
		    Font filterNameFont = workbook.createFont();
		    filterNameFont.setFontHeightInPoints((short) 11);
		    filterNameFont.setColor(IndexedColors.BLACK.getIndex());
		    
		    CellStyle filterCellStyle = workbook.createCellStyle();
		    filterCellStyle.setFont(filterFont);
		    
		    CellStyle filterNameCellStyle = workbook.createCellStyle();
		    filterNameCellStyle.setFont(filterNameFont);
		    
		    Row filterRow = sheet.createRow(0);
		    Cell filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("검색조건");
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    filterRow = sheet.createRow(1);
		    filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("조회날짜");
		    filtercell.setCellStyle(filterCellStyle);
		    filtercell = filterRow.createCell(1);
		    filtercell.setCellValue(startDate + " - " + endDate);
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    filterRow = sheet.createRow(2);
		    filtercell = filterRow.createCell(0);
		    filtercell.setCellValue("부서명");
		    filtercell.setCellStyle(filterCellStyle);
		    filtercell = filterRow.createCell(1);
		    filtercell.setCellValue(deptname);
		    filtercell.setCellStyle(filterNameCellStyle);
		    
		    Font headerFont = workbook.createFont();
		    headerFont.setFontHeightInPoints((short) 12);
		    headerFont.setColor(IndexedColors.BLUE_GREY.getIndex());
	
		    CellStyle headerCellStyle = workbook.createCellStyle();
		    headerCellStyle.setFont(headerFont);
		    
			// Create a Row
		    Row headerRow = sheet.createRow(3);
		    headerRow = sheet.createRow(4);
		    
		    res.setContentType("application/vnd.ms-excel");
            ServletOutputStream outStream = res.getOutputStream();
	
		    for (int i = 0; i < columns.length; i++) {
		      Cell cell = headerRow.createCell(i);
		      cell.setCellValue(columns[i]);
		      cell.setCellStyle(headerCellStyle);
		    }
		    
		    // Create Other rows and cells with contacts data
		    int rowNum = 5;
		    
		    for (int i = 0; i < ranks.size(); i++) {
				Row row = sheet.createRow(rowNum++);
				
				row.createCell(0).setCellValue(Util.checkNull(ranks.get(i).getRanking(), "-"));
				row.createCell(1).setCellValue(Util.checkNull(ranks.get(i).getUserName(), "-"));
				row.createCell(2).setCellValue(Util.checkNull(ranks.get(i).getNickName(), "-"));
				row.createCell(3).setCellValue(Util.checkNull(ranks.get(i).getCustName(), "-"));
				row.createCell(4).setCellValue(Util.checkNull(ranks.get(i).getDeptName(), "-"));
				row.createCell(5).setCellValue(Util.checkNull(ranks.get(i).getRecordAmount(), "-"));
			}
			
			// Resize all columns to fit the content size
		    for (int i = 0; i < columns.length; i++) {
		      sheet.autoSizeColumn(i);
		    }
		    
		    String filename = "개인별 랭킹_" + DateFormatUtil.getCurrentTime() + ".xlsx";
		    
		    String header = req.getHeader("User-Agent");
			if (header.contains("MSIE") || header.contains("Trident")) {
				filename = URLEncoder.encode(filename,"UTF-8").replaceAll("\\+", "%20");
			    res.setHeader("Content-Disposition", "attachment;filename=" + filename + ";");
			} else {
				filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			    res.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			}
			
		    workbook.write(outStream);
		    outStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 그룹별 통계 
	 * 일반 관리자 : 전체 대상 부서별 통계 + 회사별 통계  
	 * @param custSeq
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(path="statGroup", method = RequestMethod.GET)
	public ModelAndView statGroup(
			@ModelAttribute(name="rank")  Ranking rank,
			RedirectAttributes redirectAttributes
			){
		
		ModelAndView modelAndView = new ModelAndView("statGroup");
		Admin admin = currentAdmin(modelAndView);
		if (!admin.isSuperAdmin()){
			rank.setCustSeq(admin.getCustSeq());
		}else if (rank.getCustSeq() == null){
			rank.setCustSeq(admin.getCustSeq());
		}
		
		if (null == rank.getCustSeq() || "".equals(rank.getCustSeq())){
			rank.setCustSeq(admin.getCustSeq());
		}

		String startDate = rank.getStartDate();
		if (startDate == null || startDate.length() != 8){
			startDate = DateUtils.getDateTextByAddMonths("yyyyMMdd", DateUtils.currentDate("yyyyMMdd"), -1);
			rank.setStartDate(startDate);
		}
		String endDate = rank.getEndDate();
		if (endDate == null || endDate.length() != 8){
			endDate = DateUtils.currentDate("yyyyMMdd");
			rank.setEndDate(endDate);
		}
		
		String inputValidateErrroMessage = rank.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return redirectCustomerOne(rank.getCustSeq());
		}
		
		Customer customerInfo = checkCustomerAdmin(rank.getCustSeq());
		// 부서별 통계
		// rank.setCustSeq(null);
		List<Ranking> departRanks = "N".equals(customerInfo.getPermit_other_building()) ? 
				adminService.getGroupRankingByDepart(rank) :
				adminService.getGroupRankingByDepartOther(rank) ;
		modelAndView.addObject("departRanks", departRanks);
		
		// 회사별 통계
		List<Ranking> companyRanks = "N".equals(customerInfo.getPermit_other_building()) ? 
				adminService.getGroupRankingByCompany(rank) : 
				adminService.getGroupRankingByCompanyOther(rank);		
		modelAndView.addObject("companyRanks", companyRanks);
		modelAndView.addObject("rank", rank);
		return modelAndView;
	}
	
	
	/**
	 * 특정 사용자 통계
	 * http://localhost:8080/admin/statPerUser?userSeq=
	 * @param userSeq
	 * @return
	 */
	@RequestMapping(path="statPerUser", method = RequestMethod.GET)
	public ModelAndView statPerUser(
			@ModelAttribute Building building,
			@ModelAttribute Ranking rank,
			@RequestParam(name="uSeq", required=false) String userSeq
			){
		
		ModelAndView modelAndView = new ModelAndView("statPerUser");
		Admin admin = currentAdmin(modelAndView);
		if (!admin.isSuperAdmin()){
			building.setCustSeq(admin.getCustSeq());
		}else if (building.getCustSeq() == null){
			building.setCustSeq(admin.getCustSeq());
		}
		
		String isSuperAdmin = "N";
		
		if (admin.isSuperAdmin()){
			isSuperAdmin = "Y";
		}
		
		String startDate = rank.getStartDate();
		if (startDate == null || startDate.length() != 8){
			startDate = DateUtils.getDateTextByAddMonths("yyyyMMdd", DateUtils.currentDate("yyyyMMdd"), -1);
			rank.setStartDate(startDate);
		}
		String endDate = rank.getEndDate();
		if (endDate == null || endDate.length() != 8){
			endDate = DateUtils.currentDate("yyyyMMdd");
			rank.setEndDate(endDate);
		}
		
		building.setUserSeq(userSeq);
		rank.setUserSeq(userSeq);
		
		// 건물 리스트 (전체 - NO FILTER)
		//List<Building> buildingList = adminService.buildingListOfCustomer(customer);
		List<Building> buildingList = adminService.buildingListOfUserSeq(building.getUserSeq());
		modelAndView.addObject("buildingList", buildingList);
		
		// daily history
		List<Building> buildingUserList = adminService.historyOfUserSeq(building.getUserSeq());
		modelAndView.addObject("buildingUserList", buildingUserList);
		
		// 특정 대상 사용자 기록 조회
		if (rank.getUserSeq() != null){
			List<Ranking> userRecords = adminService.getRecordIndividual(rank);
			modelAndView.addObject("userRecords", userRecords);
			modelAndView.addObject("rank", rank);
		}
		
		modelAndView.addObject("isSuperAdmin", isSuperAdmin);
		
		return modelAndView;
	}
	
	/**
	 * 사용자 통계
	 * http://localhost:8080/admin/statUser?startDate=20180601&endDate=20180701
	 * @param custSeq, userSeq, deptSeq
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(path="statCafeUser", method = RequestMethod.GET)
	public ModelAndView statCafeUser(
			@ModelAttribute(name="rank")  Ranking rank,
			@ModelAttribute Cafe cafe,
			@RequestParam(name="cafeseq", required=false) String cafeseq,
			@RequestParam(name="type", defaultValue="") String type,
			RedirectAttributes redirectAttributes
			){
		
		ModelAndView modelAndView = new ModelAndView("statCafeUser");
		Admin admin = currentAdmin(modelAndView);
		if (!admin.isSuperAdmin()){
			rank.setCustSeq(admin.getCustSeq());
		}else if (rank.getCustSeq() == null){
			rank.setCustSeq(admin.getCustSeq());
		}
		
		String startDate = rank.getStartDate();
		if (startDate == null || startDate.length() != 8){
			startDate = DateUtils.getDateTextByAddMonths("yyyyMMdd", DateUtils.currentDate("yyyyMMdd"), -1);
			rank.setStartDate(startDate);
		}
		String endDate = rank.getEndDate();
		if (endDate == null || endDate.length() != 8){
			endDate = DateUtils.currentDate("yyyyMMdd");
			rank.setEndDate(endDate);
		}

		
//		String inputValidateErrroMessage = rank.inputValidateErrroMessage();
//		if (inputValidateErrroMessage != null){
//			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
//			return redirectCustomerOne(rank.getCustSeq());
//		}
//		
		User user = adminService.selectUserByEmail(currentAdmin().getEmail());
		if (null != user) {
			cafe.setAdminseq(user.getUserSeq());
			
			List<Cafe> cafeList = adminService.selectCafeOfMineAllList(cafe);
			
			modelAndView.addObject("cafeList", cafeList);
		} else {
			modelAndView.addObject("cafeList", null);
		}
		
		if ("".equals(cafeseq)) {
			modelAndView.addObject("cafeSelected", false);
		} else {
			modelAndView.addObject("cafeSelected", true);
			cafe.setCafeseq(cafeseq);
			//rank.setCafeseq(rank.getCafeseq());
		}
		
		modelAndView.addObject("cafeseq", cafeseq);
		modelAndView.addObject("cateseq", rank.getCateseq());
		
		if (!"".equals(cafeseq)) {
			// 카테고리 리스트
			List<Category> cafeCategoryList = adminService.cafeCategoryList(cafe);
			modelAndView.addObject("categoryList", cafeCategoryList);
					
			// 각 사용자 랭킹 리스트 (u_user_building_map 기준으로 회사 판별)
			List<Ranking> ranks = adminService.getRankingCafeIndividual(rank);
					
			modelAndView.addObject("ranks", ranks);
			
			Integer totalAmount = 0;
			for (Ranking r:ranks){
				totalAmount += r.getRecordAmount();
			}
			modelAndView.addObject("totalAmount", totalAmount);
			
			
			// 소속 회사 부서별 랭킹 (부서개 2개 이상일때만 표시)
//			if (departmentList != null && departmentList.size()> 1){
//				List<Ranking> departRanks = "N".equals(customerInfo.getPermit_other_building()) ? 
//						adminService.getGroupRankingByDepart(rank):
//						adminService.getGroupRankingByDepartOther(rank);
//				modelAndView.addObject("departRanks", departRanks);
//			}
//			
			// 특정 대상 사용자 기록 조회
			if (rank.getUserSeq() != null){
				List<Ranking> userRecords = adminService.getRecordIndividual(rank);
				modelAndView.addObject("userRecords", userRecords);
			}
		} else {
			
		}
		
		modelAndView.addObject("type", type);
		
		return modelAndView;
	}
	
	
	
	/**
	 * 카페 카테고리별 통계 
	 * 일반 관리자 : 전체 대상 부서별 통계 + 회사별 통계  
	 * @param custSeq
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(path="statCafeCategory", method = RequestMethod.GET)
	public ModelAndView statCafeCategory(
			@ModelAttribute(name="rank")  Ranking rank,
			@ModelAttribute Cafe cafe,
			@RequestParam(name="cafeseq", required=false) String cafeseq,
			@RequestParam(name="type", defaultValue="") String type,
			RedirectAttributes redirectAttributes
			){
		
		ModelAndView modelAndView = new ModelAndView("statCafeCategory");
		Admin admin = currentAdmin(modelAndView);
		if (!admin.isSuperAdmin()){
			rank.setCustSeq(admin.getCustSeq());
		}else if (rank.getCustSeq() == null){
			rank.setCustSeq(admin.getCustSeq());
		}
		
		if (null == rank.getCustSeq() || "".equals(rank.getCustSeq())){
			rank.setCustSeq(admin.getCustSeq());
		}

		String startDate = rank.getStartDate();
		if (startDate == null || startDate.length() != 8){
			startDate = DateUtils.getDateTextByAddMonths("yyyyMMdd", DateUtils.currentDate("yyyyMMdd"), -1);
			rank.setStartDate(startDate);
		}
		String endDate = rank.getEndDate();
		if (endDate == null || endDate.length() != 8){
			endDate = DateUtils.currentDate("yyyyMMdd");
			rank.setEndDate(endDate);
		}
		
		String inputValidateErrroMessage = rank.inputValidateErrroMessage();
		if (inputValidateErrroMessage != null){
			redirectAttributes.addFlashAttribute("message", inputValidateErrroMessage);
			return redirectCafeOne(cafeseq);
		}
		
		User user = adminService.selectUserByEmail(currentAdmin().getEmail());
		if (null != user) {
			cafe.setAdminseq(user.getUserSeq());
			
			List<Cafe> cafeList = adminService.selectCafeOfMineAllList(cafe);
			
			modelAndView.addObject("cafeList", cafeList);
		} else {
			modelAndView.addObject("cafeList", null);
		}
		
		
		if ("".equals(cafeseq)) {
			modelAndView.addObject("cafeSelected", false);
		} else {
			modelAndView.addObject("cafeSelected", true);
			cafe.setCafeseq(cafeseq);
			rank.setCafeseq(cafeseq);
		}
		
		modelAndView.addObject("cafeseq", cafeseq);
		
		if (!"".equals(cafeseq)) {
			List<Category> cafeCategoryList = adminService.cafeCategoryList(cafe);
			modelAndView.addObject("categoryList", cafeCategoryList);
			
			// 카테고리별 통계
			List<Ranking> categoryRanks = adminService.getCafeCategoryRanking(rank);
			modelAndView.addObject("departRanks", categoryRanks);
			
			
			//Customer customerInfo = checkCustomerAdmin(rank.getCustSeq());
			// 부서별 통계
			// rank.setCustSeq(null);
//			List<Ranking> departRanks = "N".equals(customerInfo.getPermit_other_building()) ? 
//					adminService.getGroupRankingByDepart(rank) :
//					adminService.getGroupRankingByDepartOther(rank) ;
//			modelAndView.addObject("departRanks", departRanks);
			
			// 회사별 통계
//			List<Ranking> companyRanks = "N".equals(customerInfo.getPermit_other_building()) ? 
//					adminService.getGroupRankingByCompany(rank) : 
//					adminService.getGroupRankingByCompanyOther(rank);		
//			modelAndView.addObject("companyRanks", companyRanks);
//			modelAndView.addObject("rank", rank);
		}
		
		modelAndView.addObject("type", type);
		
		return modelAndView;
	}
	
	/**
	 * AUTH - SUPER
	 * 고객사 리스트 페이지 
	 * @param redirectAttributes
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasRole('ROLE_SUPER')")
	@RequestMapping(path="statCafeList", method = RequestMethod.GET)
	public ModelAndView statCafeList(
			@ModelAttribute Cafe cafe,
			@RequestParam(name="p", defaultValue="1") String page,
			/*@RequestParam(name="sort", defaultValue="") String sort,*/
			@RequestParam(name="type", defaultValue="") String type,
			@RequestParam(name="startDate", defaultValue="") String startDate,
			@RequestParam(name="endDate", defaultValue="") String endDate
	){
		
		ModelAndView modelAndView = new ModelAndView("statCafeList");
		modelAndView.addObject("admin", currentAdmin());
		
		if (startDate == null || startDate.length() != 8){
			startDate = DateUtils.getDateTextByAddMonths("yyyyMMdd", DateUtils.currentDate("yyyyMMdd"), -1);
			cafe.setStartDate(startDate);
		}
		
		if (endDate == null || endDate.length() != 8){
			endDate = DateUtils.currentDate("yyyyMMdd");
			cafe.setEndDate(endDate);
		}
		
//		@SuppressWarnings("rawtypes")
//		List sortList = new ArrayList<Sort>() ;
//		sortList.add(new Sort(0,"카페명","cafename")) ;
//		sortList.add(new Sort(1,"등록일시","opendate desc")) ;
//		modelAndView.addObject("sortList", sortList) ;
//		cafe.setSort(sort);
		
		PagePair selectCafeList = adminService.selectCafeList(Integer.valueOf(page), cafe);
		
		List<Cafe> cafeList = (List<Cafe>) selectCafeList.getList();
		modelAndView.addObject("cafeList", cafeList);
		
		PageNavigation pageNavigation = selectCafeList.getPageNavigation();
		modelAndView.addObject("pageNavigation", pageNavigation);
		
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		modelAndView.addObject("type", type);
		
		return modelAndView;
	}
	
	
	@RequestMapping(path="init", method = RequestMethod.GET)
	public ModelAndView init(
	){
		Admin admin = currentAdmin();
		
		ModelAndView modelAndView = new ModelAndView("init");
		
		
		if (!admin.isSuperAdmin()){
			modelAndView.addObject("isSuper", false);
		} else {
			modelAndView.addObject("isSuper", true);		
		}
		
		return modelAndView;
	}
}


