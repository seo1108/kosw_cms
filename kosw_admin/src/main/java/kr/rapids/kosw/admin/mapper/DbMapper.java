package kr.rapids.kosw.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

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

@Mapper
public interface DbMapper {


	int adminAdd(Admin admin);

	int updateAdminBySeq(Admin admin);

	Admin selectAdminBySeq(String adminSeq);

	List<Admin> selectAdminList(PageNavigation pageNavigation);

	int selectAdminTotalCount();

	int customerAdd(Customer customer);

	String custCodeCheck(String custCode);

	int selectCustomerTotalCount(Customer customer);
	
	int selectCafeTotalCount(Cafe cafe);
	
	int selectCafeOfMineTotalCount(Cafe cafe);

	int selectBuildingTotalCount(Building building);
	
	List<Customer> selectCustomerList(Customer customer);
	
	List<Cafe> selectCafeList(Cafe cafe);
	
	List<Cafe> selectCafeOfMineList(Cafe cafe);
	
	List<Cafe> selectCafeOfMineAllList(Cafe cafe);

	List<Building> selectBuildingList(Building building);
	int selectBuildingListCount(Building building);


	Customer selectCustomerBySeq(String custSeq);

	Cafe selectCafeBySeq(String custSeq);
	
	List<Admin> adminListOfCustomer(Customer customer);

	List<Department> departmentListOfCustomer(Customer customer);
	
	List<Category> cafeCategoryList(Cafe cafe);
	
	List<User> cafeUserList(Cafe cafe);

	String getAdminName(String adminSeq);

	List<Building> buildingListOfCustomer(Customer customer);

	List<Building> buildingListOfBuildSeq(String buildSeq);
	
	List<Building> buildingListOfUserSeq(String userSeq);
	
	List<Building> historyOfUserSeq(String userSeq);
	
	List<Customer> customerListOfBuildSeq(String buildSeq);

	int departmentAdd(Department department);

	int buildingAdd(Building building);

	String buildCodeCheck(String buildCode);

	List<BuildingStair> selectStairOfBuilding(Building building);

	int buildingStairAdd(BuildingStair buildingStair);

	Building selectBuildingBySeq(String buildSeq);

	void initialAdminUserSetting(Map<String, String> map);

	void addSuperUser(Admin admin);

	Admin addSuperUserCheck(Admin admin);

	void addSuperUserAuth(AdminAuth auth);

	Customer addSuperCustomerCheck();

	void addSuperCustomer(Customer customer);

	Admin adminLogin(Admin admin);

	List<String> getRoles(Admin admin);

	int adminAddAuth(AdminAuth auth);

	int updateLogo(Logo logo);

	Logo selectLogo(Logo logo);

	int insertLogo(Logo logo);

	String getLogoImageFile(String custSeq);

	Logo getLogo(Customer customer);

	int addCharacter(Character character);

	String characterNameCheck(String name);

	String characterCodeLast();

	List<Character> selectCharecterByCode(String charCode);

	String getCharacterImageFile(String charSeq);

	int characterNameGenderEdit(Character character);

	int editCharacterImage(Character character);

	List<Character> selectCharacterList();

	int customerEdit(Customer customer);

	int selectUserLTotalCount(User user);
	int selectUserAllTotalCount(User user);

	List<User> selectUserList(User user);
	List<User> selectUserAllList(User user);

	int updateUserApprovalFlag(User user);
	int updateCustomerApprovalFlag(Customer cust);
	
	int updateUserApprovalFlagOnUser(User user);

	int selectBeaconManufacTotalCount(BeaconManufac beaconManufac);

	List<User> selectBeaconManufacList(BeaconManufac beaconManufac);

	int beaconeManufacAdd(BeaconManufac beaconManufac);

	int beaconeManufacEdit(BeaconManufac beaconManufac);

	BeaconManufac selectBeaconManufacBySeq(BeaconManufac beaconManufac);

	int beaconeManufacDelete(BeaconManufac beaconManufac);

	int selectBeaconTotalCount(Beacon beacon);

	List<Beacon> selectBeaconListAll(Beacon beacon);

	List<BuildingStair> selectBuildingStairsOfAdmin(Admin admin);

	List<BuildingStair> selectBuildingStairListOfCustomer(Customer customer);

	List<BeaconManufac> selectBeaconManuListFacAll();

	User selectUserBySeq(String userSeq);

	User selectUserByEmail(String userEmail);

	Building selectBuildingOfStair(BuildingStair buildingStair);

	List<Beacon> selectBeaconAllOfCustomer(Customer customer);

	BuildingStair selectBuildingStairBySeq(BuildingStair buildingStair);

	int buildingStairEdit(BuildingStair buildingStair);

	int adminEdit(Admin admin);

	List<Admin> lastAdminExcept(Admin admin);

	int departmentEdit(Department department);

	int departmentDelete(Department department);

	Admin adminEmailCheck(Admin admin);

	int buildingEdit(Building building);

	int buildingDelete(Building building);

	int buildingStairDelete(BuildingStair buildingStair);

	int beaconAdd(Beacon beacon);

	List<BuildingStair> selectBuildingStairListOfBuilding(Building building);

	List<Beacon> selectBeaconList(Beacon beacon);

	int beaconEdit(Beacon beacon);

	int beaconDelete(Beacon beacon);

	Beacon beaconUuidCheck(Beacon beacon);

	Beacon beaconFloorCheck(Beacon beacon);

	List<Beacon> beaconSearchList(Beacon beacon);

	List<Customer> customerAll();

	int selectBbsTotalCount(Bbs bbs);

	List<Bbs> selectBbsList(Bbs bbs);

	int bbsAdd(Bbs bbs);

	Bbs selectBbsBySeq(Bbs bbs);

	int bbsEdit(Bbs bbs);

	int bbsDelete(Bbs bbs);

	int characterDelete(Character character);

	List<Character> selectCharacterAll();
	/**
	 * 전체 회원 FCM 토큰 획득
	 * @param string
	 * @return
	 */
	List<String> selectFcmTokenOfAllUser(String string);
	/**
	 * 고객사에 속한 회원의 FCM 토큰 획득
	 * @param customerSeq
	 * @return
	 */
	List<String> selectFcmTokenOfCustomer(String customerSeq);

	
	
	/**
	 * 푸쉬관련
	 */
	int selectPushTotalCount(Push push);
	List<Bbs> selectPushList(Push push);
	int pushAdd(Push push);
	int pushEdit(Push push);
	int pushDelete(Push push);

	List<String> pushTargets(Push push);

	int pushSent(Push push);

	Push getPushBySeq(Push push);

	
	/**
	 * 앱 버전 관련 
	 */
	List<AppVersion> getAppVersion();
	int addVersion(AppVersion ver);

	List<Push> scheduledPushList();

	List<String> pushTargetTokens(Push push);

	void addDefaultDepart(Customer customer);

	
	/**
	 * 랭킹 관련
	 */
	List<Ranking> getRankingIndividual(Ranking rank);
	List<Ranking> getRankingIndividualOther(Ranking rank);
	List<Ranking> getRankingCafeIndividual(Ranking rank);
	/**
	 * 빌딩별 랭킹 관련
	 */
	List<Ranking> getRankingIndividualByBuilding(Ranking rank);
	
	/**
	 * 개인 기록
	 */
	List<Ranking> getRecordIndividual(Ranking rank);

	/**
	 * 부서별 랭킹 
	 * 회사 한정시 custSeq 입력
	 */
	List<Ranking> selectGroupRankingByDepart(Ranking rank);
	List<Ranking> selectGroupRankingByDepartOther(Ranking rank);
	List<Ranking> getCafeCategoryRanking(Ranking rank);
	/**
	 * 회사별 랭킹
	 */
	List<Ranking> selectGroupRankingByCompany(Ranking rank);
	List<Ranking> selectGroupRankingByCompanyOther(Ranking rank);
	
	boolean godoEdit(Beacon beacon);

	/**
	 * 사용자 삭제 From User Mapping Table
	 * @param params
	 * @return
	 */
	int deleteUserMapping(Map<String, String> params);
	/**
	 * 사용자 삭제를 위한 빌딩 매핑 카운트 
	 * @param params
	 * @return
	 */
	int selectMapCount(Map<String, String> params);
	/**
	 * 사용자 완전 삭제 
	 * @param params
	 * @return
	 */
	int deleteUser(Map<String, String> params);
	/**
	 * 부서 삭제시 u_user_building_map 에 매핑된 사용자의 부서를 0 으로 변경
	 * @param department
	 */
	void departmentDeleteStep2(Department department);

	void deleteAdminBbs(Admin admin);

	void deleteAllAdminOfCustomer(Customer customer);

	void deleteAllAdminOfCustomerStep2(Customer customer);
	void deleteCustomer(Customer customer);

	int beaconEnable(Beacon beacon);


}
