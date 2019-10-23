package kr.rapids.kosw.admin.vo;

public interface ResCode {
	
    public static final String SUCCESS = "0000";
    public static final String ERROR_DUPLICATED = "1001";
    public static final String ERROR_PARAMETER = "1002";
    public static final String ERROR_NODATA = "1003";
    public static final String ERROR_PAGE = "1004";
    public static final String ERROR_DATA_MISMATCH = "1005";
    public static final String ERROR_KAKAO_MAPPING_REQUIRED = "1006";
    public static final String ERROR_SERVER = "2000";
    public static final String ERROR_AUTH = "3000";
    public static final String ERROR_ETC = "9999";
	
    /**
     * Http Response code정의 enum.
     * @author kugie
     */
    public enum ResType {
		SUCCESS("0000"),
	    ERROR_DUPLICATED("1001"),
	    ERROR_PARAMETER("1002"),
	    ERROR_NODATA("1003"),
	    ERROR_PAGE("1004"),
	    ERROR_DATA_MISMATCH("1005"),
	    ERROR_KAKAO_MAPPING_REQUIRED("1006"),
	    ERROR_SERVER("2000"),
	    ERROR_AUTH("3000"),
	    ERROR_ETC("9999");
		private String resCode;
		ResType(String code){
			this.resCode = code;
		}
		public String code() {
			return resCode;
		}
	}
}
