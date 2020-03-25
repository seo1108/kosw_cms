package kr.rapids.kosw.admin.service;

import java.util.List;
import java.util.Map;

//import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.rapids.kosw.admin.mapper.DbMapper;
import kr.rapids.kosw.admin.model.Admin;
import kr.rapids.kosw.admin.model.AdminAuth;
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
import kr.rapids.kosw.admin.model.PageNavigation;
import kr.rapids.kosw.admin.model.Push;
import kr.rapids.kosw.admin.model.Ranking;
import kr.rapids.kosw.admin.model.User;
import kr.rapids.kosw.admin.utils.PagePair;

@Service
public class AdminService {
	
	private final static Logger logger = LoggerFactory.getLogger(AdminService.class);
	
	
	@Autowired
	private DbMapper mapper;
	
	

	/**
	 * 관리자 추가 (권한 추가)
	 * @param admin
	 */
	public boolean adminAdd(Admin admin) {
		try {
			int count = mapper.adminAdd(admin);
			if (count != 1){
				return false;
			}
			
			AdminAuth auth = new AdminAuth(admin);
			auth.setAuthCode("ROLE_ADMIN");
			
			count = mapper.adminAddAuth(auth);
			if (count != 1){
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 카페 관리자 업데이트
	 * @param admin
	 */
	public boolean updateAdminFlag(Admin admin) {
		try {
			int count = mapper.updateAdminFlag(admin);
			if (count != 1){
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 카페 관리자 업데이트
	 * @param admin
	 */
	public boolean updateCafeAdmin(Cafe cafe) {
		try {
			int count = mapper.updateCafeAdmin(cafe);
			mapper.updateInitCafeAdminFlag(cafe);
			mapper.updateNewCafeAdminFlag(cafe);
			if (count != 1){
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * 관리자 업데이트
	 * 입력 : adminSeq
	 * 업데이트 : passwd, admin_name, admin_phone, active_flag
	 * @param admin
	 */
	public void updateAdminBySeq(Admin admin){
		int count = mapper.updateAdminBySeq(admin);
	}
	
	/**
	 * 관리자 SELECT ONE
	 * @param adminSeq
	 * @return kr.rapids.kosw.admin.model.Admin
	 */
	public Admin selectAdminBySeq(String adminSeq){
		return mapper.selectAdminBySeq(adminSeq);
	}

//	/**
//	 * 관리자 SELECT PAGE
//	 * @param page
//	 * @param totalCount
//	 * @return
//	 */
//	public List<Admin> selectAdminList(int page){
//		int totalCount = mapper.selectAdminTotalCount();
//		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
//		return mapper.selectAdminList(pageNavigation);
//	}

	/**
	 * 고객사 추가
	 * 임의의 기본 부서 표시
	 * @param customer
	 * @return
	 */
	public boolean customerAdd(Customer customer) {
		int count = mapper.customerAdd(customer);
		if (count == 1){
			// 회사 추가시, 기본 부서 추가
			mapper.addDefaultDepart(customer);
			return true;
		}
		return false;
	}

	/**
	 * 고각사 코드 중복확인
	 * @param custCode
	 * @return
	 */
	public boolean custCodeCheck(String custCode) {
		String code = mapper.custCodeCheck(custCode);
		if (code == null){
			return true;
		}
		return false;
	}

	
	
	public Customer selectCustomerBySeq(String custSeq) {
		Customer customer = mapper.selectCustomerBySeq(custSeq);
		return customer;
	}
	
	public Cafe selectCafeBySeq(String custSeq) {
		Cafe cafe = mapper.selectCafeBySeq(custSeq);
		return cafe;
	}

	public List<Admin> adminListOfCustomer(Customer customer) {
		List<Admin> adminList = mapper.adminListOfCustomer(customer);
		return adminList;
	}
	
	public List<Admin> adminListOfCafe(Customer customer) {
		List<Admin> adminList = mapper.adminListOfCustomer(customer);
		return adminList;
	}

	public List<Department> departmentListOfCustomer(Customer customer) {
		List<Department> departmentList = mapper.departmentListOfCustomer(customer);
		return departmentList;
	}

	public String getAdminName(String adminSeq) {
		String adminName = mapper.getAdminName(adminSeq);
		return adminName;
	}
	
	public List<Category> cafeCategoryList(Cafe cafe) {
		List<Category> cafeCategoryList = mapper.cafeCategoryList(cafe);
		return cafeCategoryList;
	}
	
	public List<User> cafeUserList(Cafe cafe) {
		List<User> cafeUserList = mapper.cafeUserList(cafe);
		return cafeUserList;
	}
	
	public Map<String, Object> selectCafeUsersStatus(String cafeseq) {
		Map<String, Object> map = mapper.selectCafeUsersStatus(cafeseq);
		return map;
	}
	

	/**
	 * 해당 고객의 건물 리스트
	 * @param customer (cust_seq)
	 * @return
	 */
	public List<Building> buildingListOfCustomer(Customer customer) {
		List<Building> buildingList = mapper.buildingListOfCustomer(customer);
		return buildingList;
	}

	public List<Building> buildingListOfBuildSeq(String buildSeq) {
		List<Building> buildingList = mapper.buildingListOfBuildSeq(buildSeq);
		return buildingList;
	}
	
	public List<Building> buildingListOfUserSeq(String buildSeq) {
		List<Building> buildingList = mapper.buildingListOfUserSeq(buildSeq);
		return buildingList;
	}
	
	public List<Cafe> cafeListOfUserSeq(String user_seq) {
		List<Cafe> cafeList = mapper.cafeListOfUserSeq(user_seq);
		return cafeList;
	}
	
	public List<Building> historyOfUserSeq(String buildSeq) {
		List<Building> buildingUserList = mapper.historyOfUserSeq(buildSeq);
		return buildingUserList;
	}

	public List<Customer> customerListOfBuildSeq(String buildSeq) {
		List<Customer> customerList = mapper.customerListOfBuildSeq(buildSeq);
		return customerList;
	}
	
	public boolean departmentAdd(Department department) {
		int count = mapper.departmentAdd(department);
		if (count == 1){
			return true;
		}
		return false;
	}

	public boolean buildingAdd(Building building) {
		int count = mapper.buildingAdd(building);
		if (count == 1){
			return true;
		}
		return false;
	}

	public boolean buildCodeCheck(String buildCode) {
		String code = mapper.buildCodeCheck(buildCode);
		if (code == null){
			return true;
		}
		return false;
	}

	public Customer getCustomerBySeq(String custSeq) {
		Customer customer = mapper.selectCustomerBySeq(custSeq);
		return customer;
	}

	/**
	 * custSeq : 필수 조건
	 * buildSeq : 선택 조건
	 * @param building
	 * @return
	 */
	public List<BuildingStair> selectStairOfBuilding(Building building) {
		List<BuildingStair> stairList = mapper.selectStairOfBuilding(building);
		return stairList;
	}

	public boolean buildingStairAdd(BuildingStair buildingStair) {
		int count = mapper.buildingStairAdd(buildingStair);
		if (count == 1){
			return true;
		}
		return false;
	}

	public Building selectBuildingBySeq(String buildSeq) {
		Building building = mapper.selectBuildingBySeq(buildSeq);
		return building;
	}


	/**
	 * 초기 데이타 슈퍼관리자 
	 * @param admin
	 */
	public void initialAdminUserSetting(Admin admin) {
		if (null == mapper.addSuperUserCheck(admin)){
			mapper.addSuperUser(admin);
			String adminSeq = admin.getAdminSeq();
			
			AdminAuth auth = new AdminAuth(admin);
			auth.setAuthCode("ROLE_SUPER");
			mapper.addSuperUserAuth(auth);
		}
	}
	
	/**
	 * 초기 데이타 슈퍼고객사 
	 * @param customer
	 */
	public Customer initialSuperCustomerSetting(Customer customer) {
		Customer superCustomer =  mapper.addSuperCustomerCheck();
		if (superCustomer != null){
			return superCustomer;
		}else{
			mapper.addSuperCustomer(customer);
			return customer;
		}
	}
	
	public Admin adminInfoByEmail(Admin admin) {
		Admin loginAdmin = mapper.adminInfoByEmail(admin);
		return loginAdmin; 
	}


	public Admin adminLogin(Admin admin) {
		Admin loginAdmin = mapper.adminLogin(admin);
		return loginAdmin; 
	}

	public List<String> getRoles(Admin admin) {
		List<String> roles = mapper.getRoles(admin);
		return roles; 
	}

	public boolean updateLogo(Logo logo) {
		int count = 0;
		Logo customerLogo = mapper.selectLogo(logo);
		
		if (customerLogo == null){
			count = mapper.insertLogo(logo);
		}else{			
			count = mapper.updateLogo(logo);
		}
		if (count == 1){
			return true;
		}
		return false;
	}

	public String getLogoImageFile(String custSeq) {
		String logoImageFile = mapper.getLogoImageFile(custSeq);
		return logoImageFile;
	}

	public Logo getLogo(Customer customer) {
		Logo logo = mapper.getLogo(customer);
		return logo;
	}
	
	public boolean characterDelete(Character character) {
		try {
			int count = mapper.characterDelete(character);
			if (count > 0){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}
	

	public boolean addCharacter(Character character) {
		try {
			int count =  mapper.addCharacter(character);
			if (count == 1){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		} 
	}

	public String characterNameCheck(String name) {
		String nameExists = mapper.characterNameCheck(name);
		return nameExists;
	}

	//@Test
	public String characterCodeLast() {
		String charCode = mapper.characterCodeLast();
		if (charCode == null){
			charCode = "1";
		}else{
			charCode = String.valueOf(Integer.parseInt(charCode) + 1);
		}
		return charCode;
	}

	public List<Character> selectCharecterByCode(String charCode) {
		List<Character> characterList = mapper.selectCharecterByCode(charCode);
		return characterList;
	}

	public String getCharacterImageFile(String charSeq) {
		try {
			String  charImageFile = mapper.getCharacterImageFile(charSeq);
			return charImageFile;
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return null;
	}

	public boolean characterNameGenderEdit(Character character) {
		try {
			int count = mapper.characterNameGenderEdit(character);
			if (count > 0){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public boolean editCharacterImage(Character character) {
		try {
			int count = mapper.editCharacterImage(character);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public List<Character> selectCharacterList() {
		List<Character> chracterList = mapper.selectCharacterList();
		return chracterList;
	}
	
	public boolean cafeEdit(Cafe cafe) {
		try {
			int count = mapper.cafeEdit(cafe);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
		
	}
	
	public boolean updateCafeLogo(Cafe cafe) {
		try {
			int count = mapper.updateCafeLogo(cafe);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public boolean customerEdit(Customer customer) {
		try {
			int count = mapper.customerEdit(customer);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
		
	}
	
	public PagePair selectCustomerList(int page, Customer customer) {
		int totalCount = mapper.selectCustomerTotalCount(customer);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		customer.setPage(pageNavigation);
		List<Customer> selectCustomerList = mapper.selectCustomerList(customer);
		return new PagePair(selectCustomerList, pageNavigation);
	}
	
	public PagePair selectCafeList(int page, Cafe cafe) {
		int totalCount = mapper.selectCafeTotalCount(cafe);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		cafe.setPage(pageNavigation);
		List<Cafe> selectCafeList = mapper.selectCafeList(cafe);
		return new PagePair(selectCafeList, pageNavigation);
	}
	
	public List<Cafe> selectCafeAllList(Cafe cafe) {
		List<Cafe> selectCafeList = mapper.selectCafeAllList(cafe);
		return selectCafeList;
	}
	
	public PagePair selectCafeOfMineList(int page, Cafe cafe) {
		int totalCount = mapper.selectCafeOfMineTotalCount(cafe);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		cafe.setPage(pageNavigation);
		List<Cafe> selectCafeList = mapper.selectCafeOfMineList(cafe);
		return new PagePair(selectCafeList, pageNavigation);
	}
	
	public List<Cafe> selectCafeOfMineAllList(Cafe cafe) {
		List<Cafe> selectCafeList = mapper.selectCafeOfMineAllList(cafe);
		return selectCafeList;
	}
	
	/*

	public PagePair selectCustomerListOfBuildSeq(int page, Customer customer) {
		int totalCount = mapper.selectCustomerTotalCount(customer);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		customer.setPage(pageNavigation);
		List<Customer> selectCustomerList = mapper.customerListOfBuildSeq(customer);
		return new PagePair(selectCustomerList, pageNavigation);
	}
	 */

	
	public PagePair selectBuildingList(int page, Building building) {
		int totalCount = mapper.selectBuildingListCount(building);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		building.setPage(pageNavigation);
		List<Building> selectBuildingList = mapper.selectBuildingList(building);
		return new PagePair(selectBuildingList, pageNavigation);
	}
	
	public PagePair selectUserList(Integer page, User user) {
		int totalCount = mapper.selectUserLTotalCount(user);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		user.setPage(pageNavigation);
		List<User> userList = mapper.selectUserList(user);
		return new PagePair(userList, pageNavigation);
	}

	public PagePair selectUserAllList(Integer page, User user) {
		int totalCount = mapper.selectUserAllTotalCount(user);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		user.setPage(pageNavigation);
		List<User> userList = mapper.selectUserAllList(user);
		return new PagePair(userList, pageNavigation);
	}
	
	public Map<String, Object> selectUsersStatus() {
		Map<String, Object> map = mapper.selectUsersStatus();
		return map;
	}
	


	public boolean updateUserApprovalFlag(User user) {
		try {
			int count = mapper.updateUserApprovalFlag(user);
			if (count > 0){
				count = mapper.updateUserApprovalFlagOnUser(user);
				if(count > 0){
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public boolean updateCustomerApprovalFlag(Customer cust) {
		try {
			int count = mapper.updateCustomerApprovalFlag(cust);
			if (count > 0){
					return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}


	
	public PagePair selectBeaconManufacList(Integer page, BeaconManufac beaconManufac) {
		int totalCount = mapper.selectBeaconManufacTotalCount(beaconManufac);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		beaconManufac.setPage(pageNavigation);
		
		List<User> userList = mapper.selectBeaconManufacList(beaconManufac);
		return new PagePair(userList, pageNavigation);
	}

	public boolean beaconeManufacAdd(BeaconManufac beaconManufac) {
		try {
			int count = mapper.beaconeManufacAdd(beaconManufac);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public boolean beaconeManufacEdit(BeaconManufac beaconManufac) {
		try {
			int count = mapper.beaconeManufacEdit(beaconManufac);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public BeaconManufac selectBeaconManufacBySeq(BeaconManufac beaconManufac) {
		BeaconManufac beaconManufacSelected = mapper.selectBeaconManufacBySeq(beaconManufac);
		return beaconManufacSelected;
	}

	public boolean beaconeManufacDelete(BeaconManufac beaconManufac) {
		try {
			int count = mapper.beaconeManufacDelete(beaconManufac);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public PagePair selectBeaconList(Integer page, Beacon beacon) {
		int totalCount = mapper.selectBeaconTotalCount(beacon);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		beacon.setPage(pageNavigation);
		
		
		
		List<Beacon> beaconList = mapper.selectBeaconList(beacon);
		return new PagePair(beaconList, pageNavigation);
	}
	
	public List<Beacon> selectBeaconListAll(Beacon beacon) {
		List<Beacon> beaconList = mapper.selectBeaconListAll(beacon);
		return beaconList;
	}

	public List<BuildingStair> selectBuildingStairsOfAdmin(Admin admin) {
		List<BuildingStair> stairs = mapper.selectBuildingStairsOfAdmin(admin);
		return stairs;
	}

	public List<BuildingStair> selectBuildingStairListOfCustomer(Customer customer) {
		List<BuildingStair> buildingStairList = mapper.selectBuildingStairListOfCustomer(customer);
		return buildingStairList;
	}
	
	
	
	

	public List<BeaconManufac> selectBeaconManuListFacAll() {
		List<BeaconManufac> beaconManuList = mapper.selectBeaconManuListFacAll();
		return beaconManuList;
	}

	public User selectUserBySeq(String userSeq) {
		User user = mapper.selectUserBySeq(userSeq);
		return user;
	}

	public User selectUserByEmail(String userEmail) {
		User user = mapper.selectUserByEmail(userEmail);
		return user;
	}

	
	public Building selectBuildingOfStair(BuildingStair buildingStair) {
		Building building = mapper.selectBuildingOfStair(buildingStair);
		return building;
	}

	public List<Beacon> selectBeaconAllOfCustomer(Customer customer) {
		List<Beacon> beaconList = mapper.selectBeaconAllOfCustomer(customer);
		return beaconList;
	}

	public BuildingStair selectBuildingStairBySeq(BuildingStair buildingStair) {
		buildingStair = mapper.selectBuildingStairBySeq(buildingStair);
		return buildingStair;
	}

	public boolean buildingStairEdit(BuildingStair buildingStair) {
		try {
			int count = mapper.buildingStairEdit(buildingStair);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public boolean adminEdit(Admin admin) {
		try {
			int count = mapper.adminEdit(admin);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public List<Admin> lastAdminExcept(Admin admin) {
		List<Admin> adminList = mapper.lastAdminExcept(admin);
		return adminList;
	}

	public boolean departmentEdit(Department department) {
		try {
			int count = mapper.departmentEdit(department);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public boolean departmentDelete(Department department) {
		try {
			int count = mapper.departmentDelete(department);
			mapper.departmentDeleteStep2(department);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}
	
	public boolean categoryAdd(Category category) {
		int count = mapper.categoryAdd(category);
		mapper.categoryCafeAdd(category);
		if (count == 1){
			return true;
		}
		return false;
	}
	
	public boolean categoryDelete(Category category) {
		try {
			int count = mapper.categoryDelete(category);
			mapper.categoryCafeDelete(category);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}
	
	public boolean categoryEdit(Category category) {
		try {
			int count = mapper.categoryEdit(category);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

//	public boolean departmentDelete(Department department) {
//		try {
//			int count = mapper.departmentDelete(department);
//			mapper.departmentDeleteStep2(department);
//			if (count == 1){
//				return true;
//			}
//		} catch (Exception e) {
//			logger.error("{}", e);
//		}
//		return false;
//	}

	public Admin adminEmailCheck(Admin admin) {
		Admin exAdmin = mapper.adminEmailCheck(admin);
		return exAdmin;
	}

	public boolean buildingEdit(Building building) {
		try {
			int count = mapper.buildingEdit(building);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public boolean buildingDelete(Building building) {
		try {
			int count = mapper.buildingDelete(building);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public boolean buildingStairDelete(BuildingStair buildingStair) {
		try {
			int count = mapper.buildingStairDelete(buildingStair);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public boolean beaconAdd(Beacon beacon) {
		try {
			int count = mapper.beaconAdd(beacon);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public List<BuildingStair> selectBuildingStairListOfBuilding(Building building) {
		List<BuildingStair> buildingStairList = mapper.selectBuildingStairListOfBuilding(building);
		return buildingStairList;
	}

	public boolean beaconEdit(Beacon beacon) {
		try {
			int count = mapper.beaconEdit(beacon);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public boolean beaconDelete(Beacon beacon) {
		try {
			int count = mapper.beaconDelete(beacon);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public Beacon beaconUuidCheck(Beacon beacon) {
		Beacon exBeacon = mapper.beaconUuidCheck(beacon);
		return exBeacon;
	}

	public Beacon beaconFloorCheck(Beacon beacon) {
		Beacon exBeacon = mapper.beaconFloorCheck(beacon);
		return exBeacon;
	}

	public List<Beacon> beaconSearchList(Beacon beacon) {
		List<Beacon> beaconSearchList = mapper.beaconSearchList(beacon);
		return beaconSearchList;
	}

	public List<Customer> customerAll() {
		List<Customer> customerAll = mapper.customerAll();
		return customerAll;
	}

	public PagePair selectBbsList(Integer page, Bbs bbs) {
		int totalCount = mapper.selectBbsTotalCount(bbs);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		bbs.setPage(pageNavigation);
		List<Bbs> userList = mapper.selectBbsList(bbs);
		return new PagePair(userList, pageNavigation);
	}
	
	public PagePair selectBbsToAllList(Integer page, Bbs bbs) {
		int totalCount = mapper.selectBbsToAllTotalCount(bbs);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		bbs.setPage(pageNavigation);
		List<Bbs> userList = mapper.selectBbsToAllList(bbs);
		return new PagePair(userList, pageNavigation);
	}
	
	public boolean bbsToAllAdd(Bbs bbs) {
		try {
			int count = mapper.bbsToAllAdd(bbs);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}
	
	public PagePair selectCafeBbsList(Integer page, Bbs bbs) {
		int totalCount = mapper.selectCafeBbsTotalCount(bbs);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		bbs.setPage(pageNavigation);
		List<Bbs> userList = mapper.selectCafeBbsList(bbs);
		return new PagePair(userList, pageNavigation);
	}

	public boolean cafeBbsAdd(Bbs bbs) {
		try {
			int count = mapper.cafeBbsAdd(bbs);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}
	
	public boolean cafeBbsEdit(Bbs bbs) {
		try {
			int count = mapper.cafeBbsEdit(bbs);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}
	
	public boolean cafeBbsDelete(Bbs bbs) {
		try {
			int count = mapper.cafeBbsDelete(bbs);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}
	
	public Bbs selectCafeNoticeBySeq(Bbs bbs) {
		return mapper.selectCafeNoticeBySeq(bbs);
	}
	
	
	public boolean bbsAdd(Bbs bbs) {
		try {
			int count = mapper.bbsAdd(bbs);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	/**
	 * 게시물 상세 (회사 조건 포함)
	 * @param bbs (cust_seq, bbs_seq)
	 * @return
	 */
	public Bbs selectBbsBySeq(Bbs bbs) {
		return mapper.selectBbsBySeq(bbs);
	}

	/**
	 * 게시물 수정 (회사 조건 포함)
	 * @param bbs (cust_seq, bbs_seq)
	 * @return
	 */
	public boolean bbsEdit(Bbs bbs) {
		try {
			int count = mapper.bbsEdit(bbs);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}
	
	/**
	 * 게시물 삭제 (회사 조건 포함)
	 * @param bbs (bbs_seq, cust_seq)
	 * @return
	 */
	public boolean bbsDelete(Bbs bbs) {
		try {
			int count = mapper.bbsDelete(bbs);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public List<Character> selectCharacterAll() {
		return mapper.selectCharacterAll();
	}
	/**
	 * 전체 회원 FCM 토큰 획득 
	 * @param string
	 * @return
	 */
	public List<String> getFcmTokens(String string) {
		return mapper.selectFcmTokenOfAllUser(string);
	}
	/**
	 * 고객사에 속한 회원의 FCM 토큰 획득 
	 * @param customerSeq
	 * @return
	 */
	public List<String> getFcmTokensOfCustomer(String customerSeq) {
		return mapper.selectFcmTokenOfCustomer(customerSeq);
	}

	public PagePair selectPushToAllList(Integer page, Push push) {
		int totalCount = mapper.selectPushToAllTotalCount(push);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		push.setPage(pageNavigation);
		List<Bbs> userList = mapper.selectPushToAllList(push);
		return new PagePair(userList, pageNavigation);
	}
	
	public boolean PushToAllAdd(Push push) {
		try {
			int count = mapper.PushToAllAdd(push);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}
	
	
	public PagePair selectCafePushList(Integer page, Push push) {
		int totalCount = mapper.selectCafePushTotalCount(push);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		push.setPage(pageNavigation);
		List<Bbs> userList = mapper.selectCafePushList(push);
		return new PagePair(userList, pageNavigation);
	}
	
	public boolean cafePushAdd(Push push) {
		try {
			int count = mapper.cafePushAdd(push);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}
	
	public boolean cafePushEdit(Push push) {
		try {
			int count = mapper.cafePushEdit(push);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}
	
	public PagePair selectPushList(Integer page, Push push) {
		int totalCount = mapper.selectPushTotalCount(push);
		PageNavigation pageNavigation = new PageNavigation(page, totalCount);
		push.setPage(pageNavigation);
		List<Bbs> userList = mapper.selectPushList(push);
		return new PagePair(userList, pageNavigation);
	}

	public boolean pushAdd(Push push) {
		try {
			int count = mapper.pushAdd(push);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public boolean pushEdit(Push push) {
		try {
			int count = mapper.pushEdit(push);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public boolean pushDelete(Push push) {
		try {
			int count = mapper.pushDelete(push);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}

	public List<String> pushTargets(Push push) {
		return mapper.pushTargets(push);
	}

	public void pushSent(Push push) {
		mapper.pushSent(push);
	}

	public Push getPushBySeq(Push push) {
		return mapper.getPushBySeq(push);
	}

	public List<AppVersion> getAppVersion() {
		return mapper.getAppVersion();
	}

	public boolean addVersion(AppVersion ver) {
		try {
			int count = mapper.addVersion(ver);
			if (count == 1){
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return false;
	}


	/**
	 * 예약 발송 푸쉬 리스트 조회
	 * 현재 시간 기준 -20 ~ +10 분
	 * @return
	 */
	public List<Push> scheduledPushList() {
		return mapper.scheduledPushList();
	}


	/**
	 * 푸쉬 발송 대상 토큰 리스트
	 * cust_seq, build_seq 기준 판별
	 * @param push
	 * @return
	 */
	public List<String> pushTargetTokens(Push push) {
		return mapper.pushTargetTokens(push);
	}


	/**
	 * 소속 회사 개인별 랭킹
	 * @param rank
	 * @return
	 */
	public List<Ranking> getRankingIndividual(Ranking rank) {
		return mapper.getRankingIndividual(rank);
	}
	
	/**
	 * 소속 회사 개인별 랭킹
	 * @param rank
	 * @return
	 */
	public List<Ranking> getRankingCafeIndividual(Ranking rank) {
		return mapper.getRankingCafeIndividual(rank);
	}
	
	/**
	 * 빌딩별 개인별 랭킹
	 * @param rank
	 * @return
	 */
	public List<Ranking> getRankingIndividualByBuilding(Ranking rank) {
		return mapper.getRankingIndividualByBuilding(rank);
	}

	/**
	 * 소속 회사 개인별 랭킹 다른건물 포
	 * @param rank
	 * @return
	 */
	public List<Ranking> getRankingIndividualOther(Ranking rank) {
		return mapper.getRankingIndividualOther(rank);
	}
	
	/**
	 * 개인 기록 조회
	 * @param rank
	 * @return
	 */
	public List<Ranking> getRecordIndividual(Ranking rank) {
		return mapper.getRecordIndividual(rank);
	}

	
	/**
	 * 부서별 랭킹 
	 * 회사 한정시 custSeq 입력
	 * @param rank
	 * @return
	 */
	public List<Ranking> getGroupRankingByDepart(Ranking rank) {
		return mapper.selectGroupRankingByDepart(rank);
	}
	
	/**
	 * 부서별 랭킹 
	 * 회사 한정시 custSeq 입력
	 * @param rank
	 * @return
	 */
	public List<Ranking> getCafeCategoryRanking(Ranking rank) {
		return mapper.getCafeCategoryRanking(rank);
	}
	/**
	 * 부서별 랭킹 
	 * 회사 한정시 custSeq 입력
	 * 타 건물 체크 허용시 
	 * @param rank
	 * @return
	 */
	public List<Ranking> getGroupRankingByDepartOther(Ranking rank) {
		return mapper.selectGroupRankingByDepartOther(rank);
	}

	/** 
	 * 회사별 랭킹
	 * @param rank
	 * @return
	 */
	public List<Ranking> getGroupRankingByCompany(Ranking rank) {
		return mapper.selectGroupRankingByCompany(rank);
	}
	/** 
	 * 회사별 랭킹 타 건물 체크 허용시 
	 * @param rank
	 * @return
	 */
	public List<Ranking> getGroupRankingByCompanyOther(Ranking rank) {
		return mapper.selectGroupRankingByCompanyOther(rank);
	}

	public boolean godoEdit(Beacon beacon) {
		return mapper.godoEdit(beacon);
	}

	/**
	 * 사용자 삭제 From User Mapping Table
	 * @param params (userSeq, custSeq)
	 * @return -2 - 매핑된 회사가 2개이상임. 1이상 - 회원 삭제 됨, 0 - 삭제 데이터 없음.
	 */
	public int deleteUserMapping(Map<String, String> params) {
		
//		return mapper.deleteUserMapping(params);
		
		
		int mapCount = mapper.selectMapCount(params);
		if(mapCount > 1){
			return -2;
		}else{
			//return mapper.deleteUserMapping(params) > 0;
			return mapper.deleteUser(params);
		}
		
	}


	public boolean deleteAllAdminOfCustomer(Customer customer) {
		try {
			List<Admin> buildingList = (List<Admin>) adminListOfCustomer(customer);
			for (Admin admin : buildingList) {
				mapper.deleteAdminBbs(admin);
			} 
			mapper.deleteAllAdminOfCustomer(customer);
			mapper.deleteAllAdminOfCustomerStep2(customer);
			mapper.deleteCustomer(customer);
			return true;
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return  false;
	}


	public boolean beaconEnable(Beacon beacon) {
		try {
			return mapper.beaconEnable(beacon) == 1;
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return  false;
	}

	

	

	
}
