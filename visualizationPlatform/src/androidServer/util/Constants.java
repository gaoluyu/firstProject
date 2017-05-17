package androidServer.util;

import androidServer.util.AuthenticateConstant.AuthenticationBit;

public class Constants {
	public static class SessionKey {
		public static final String USER_ID_KEY = "userIdKey";
		public static final String USER_OPENID_KEY = "userOpenIdKey";
		public static final String USER_BEAN_KEY = "userBeanKey";
		public static final String SEARCH_CI_KEY = "userCI";
		public static final String SEARCH_START_TIME = "startTime";
		public static final String SEARCH_END_TIME = "endTime";
		public static final String SEARCH_FLOOR_KEY = "floorInSession";
		public static final String SEARCH_BUILDINGID_KEY = "buildingIdInSession";
		public static final String SEARCH_IMSI_KEY = "imsiInSession";
		public static final String CI_TO_BE_MODIFIED = "ciToBeModified";
		public static final String NETWORK_TO_BE_MODIFIED = "networkToBeModified";
		public static final String BUILDING_TO_BE_MODIFIED = "buildingToBeModified";
		public static final String BEACON_TO_BE_MODIFIED = "beaconToBeModified";
		public static final String BEACON_IN_BUILDING = "beaconInBuilding";
		public static final String PHONE_CODE = "phoneCode";
		public static final String SEARCH_NETWORK_KEY = "userNetwork";
		public static final String USER_KEY = "loginedUser";
		public static final String USER_PROVINCE = "userProvince";
		public static final String USER_CITY = "userCity";
		public static final String INSPECTOR_KEY = "inspector";

	}

	public static class ErrorType {
		public static final String UPLOAD_ERROR = "上传失败";
		public static final String WP_INSERT_FAILED = "批量导入工参表失败";
		public static final String NO_CI_MODIFIED_IN_SESSION = "会话中未制定ci";
		public static final String NO_BUILDING_MODIFIED_IN_SESSION = "会话中未制定building信息";
		public static final String NO_BEACON_MODIFIED_IN_SESSION = "会话中未制定beacon信息";
		public static final String BUILDING_INFO_ERROR = "building信息格式错误（请检查单元号）";
		public static final String NO_BEACON_LIST_IN_SESSION = "beacon列表信息拉去失败（会话不存在）";
		public static final String UERSNAME_NOT_UNIKUE = "用户名已经被用过了";
		public static final String CODE_UNIKUE = "验证码不正确";
		public static final String LOGIN_ERROR = "登陆错误";
		public static final String ERROR = "数据库错误";
		public static final String PASSWORD_ERROR = "密码错误";
		public static final String NO_ENOUGH_AUTHORITY = "权限认证失败";

	}

	public static class Network {
		public static final String ChinaMobile = "'00','02'";
		public static final String ChinaUnion = "'01'";
		public static final String ChinaTelecom = "'03'";
		public static final String All = "'00','01','02','03'";

		public static final String _2G = "'EDGE','GPRS','CDMA','1xRTT','IDEN'";
		public static final String _3G = "'UMTS','EVDO_0','EVDO_A','HSDPA','HSPA','HSPAP','HSUPA','EVDO_B','EHRPD','UNKNOWN'";
		public static final String _4G = "'LTE'";

	}

	public static class Role {
		// -1:all privileges
		public static final int superAdministrator = 0xffffffff;
		public static final int operator = 0;
		// value 3:0000 0011
		public static final int inspector = AuthenticationBit.inspect_report_bit | AuthenticationBit.inspect_report_bit;
		public static final int Inspector = 2;
	}

}
