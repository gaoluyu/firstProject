package androidServer.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import com.sun.org.apache.bcel.internal.generic.PUTSTATIC;
//
//import sun.rmi.transport.proxy.HttpReceiveSocket;

public class WebUtil {
	public static final Integer OK = 1;
	public static final Integer UPLOAD_ERROR = -100;
	public static final Integer WP_INSERT_FAILED = -101;
	public static final Integer NO_CI_MODIFIED_IN_SESSION = -102;
	public static final Integer NO_BUILDING_MODIFIED_IN_SESSION = -103;
	public static final Integer NO_BEACON_MODIFIED_IN_SESSION = -104;
	public static final Integer ERROR = -105;
	public static final Integer NO_ENOUGH_AUTHORITY = -106;

	public static final Integer BUILDING_INFO_ERROR = -105;
	public static final Integer NO_BEACON_LIST_IN_SESSION = -106;
	public static final Integer UERSNAME_NOT_UNIKUE = -107;
	public static final Integer CODE_UNIKUE = -108;
	public static final Integer LOGIN_ERROR = -109;
	public static final Integer PASSWORD_ERROR = -110;

	public static Map<String, Object> okResponse(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", OK);
		putExtras(map, request);
		return map;
	}

	public static Map<String, Object> errorResponse(HttpServletRequest request, Integer type, String reason) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", type);
		map.put("reason", reason);
		putExtras(map, request);

		return map;
	}

	private static void putExtras(Map<String, Object> data, HttpServletRequest request) {
		// load search session infomation
		data.put("ciInSession", SessionUtils.getCi(request));
		data.put("network", SessionUtils.getNetwork(request));
		data.put("startTimeInSession", SessionUtils.getAttObj(request, Constants.SessionKey.SEARCH_START_TIME));
		data.put("endTimeInSession", SessionUtils.getAttObj(request, Constants.SessionKey.SEARCH_END_TIME));
		data.put("floorInSession", SessionUtils.getAttObj(request, Constants.SessionKey.SEARCH_FLOOR_KEY));
		data.put("buildingIdInSession", SessionUtils.getAttObj(request, Constants.SessionKey.SEARCH_BUILDINGID_KEY));
		data.put("imsiInSession", SessionUtils.getAttObj(request, Constants.SessionKey.SEARCH_IMSI_KEY));
	}

}
