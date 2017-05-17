package androidServer.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class AndroidUtil {
	public static final Integer OK = 1;
 
	public static final Integer LOGIN_ERROR = -109;
	public static final Integer NONLOGIN_ERROR = -108;
	public static final Integer PARAMETER_ERROR = -100;
	public static final Integer REGISTRY_ERROR = -101;
	public static final Integer UPLOAD_ERROR = -102;

	public static final String LOGIN_ERROR_REASON = "登录失败";
	public static final String NONLOGIN_ERROR_REASON ="没有登录";
	public static final String PARAMETER_ERROR_REASON = "上传参数有误";
	public static final String REGISTRY_ERROR_REASON = "注册失败";
	public static final String UPLOAD_ERROR_REASON = "上传失败";
	public static final String REGISTRY_NAME_ERROR = "用户名已存在";

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
		// data.put("ciInSession", SessionUtils.getCi(request));
		// data.put("network", SessionUtils.getNetwork(request));
		// data.put("startTimeInSession", SessionUtils.getAttObj(request,
		// Constants.SessionKey.SEARCH_START_TIME));
		// data.put("endTimeInSession", SessionUtils.getAttObj(request,
		// Constants.SessionKey.SEARCH_END_TIME));
	}
}
