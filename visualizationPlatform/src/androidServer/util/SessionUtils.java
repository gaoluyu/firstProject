package androidServer.util;

import javax.servlet.http.HttpServletRequest;

import androidServer.bean.Inspector;
import androidServer.bean.User;

public class SessionUtils {
	public static void addAtt(HttpServletRequest request, String key,
			Object value) {
		request.getSession().setAttribute(key, value);
	}

	public static void removeAtt(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}

	public static String getAtt(HttpServletRequest request, String key) {
		return (String) request.getSession().getAttribute(key);
	}

	public static Object getAttObj(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}

	public static String getProvince(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(
				Constants.SessionKey.USER_PROVINCE);
		// return "北京";
	}

	public static String getCity(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(
				Constants.SessionKey.USER_CITY);
		// return "海淀";
	}

	public static void setProvince(HttpServletRequest request, String province) {
		request.getSession().setAttribute(Constants.SessionKey.USER_PROVINCE,
				province);
	}

	public static void setCity(HttpServletRequest request, String city) {
		request.getSession().setAttribute(Constants.SessionKey.USER_CITY, city);
	}

	public static void putPhoneCode(HttpServletRequest request, String phoneCode) {
		request.getSession().setAttribute(Constants.SessionKey.PHONE_CODE,
				phoneCode);
	}

	public static String getPhoneCode(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(
				Constants.SessionKey.PHONE_CODE);
	}

	public static Integer getCi(HttpServletRequest request) {

		return (Integer) request.getSession().getAttribute(
				Constants.SessionKey.SEARCH_CI_KEY);
	}

	public static void setCi(HttpServletRequest request, int ci) {
		request.getSession().setAttribute(Constants.SessionKey.SEARCH_CI_KEY,
				ci);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Object> T getAtt(HttpServletRequest request,
			String key, Class<T> objectClass) {
		return (T) request.getSession().getAttribute(key);
	}

	public static void setNetwork(HttpServletRequest request, String network) {
		request.getSession().setAttribute(
				Constants.SessionKey.SEARCH_NETWORK_KEY, network);
	}

	public static String getNetwork(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(
				Constants.SessionKey.SEARCH_NETWORK_KEY);
	}

	public static void setUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(Constants.SessionKey.USER_KEY, user);
	}

	public static User getUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(
				Constants.SessionKey.USER_KEY);
	}

	public static void removeUser(HttpServletRequest request) {
		request.getSession().removeAttribute(Constants.SessionKey.USER_KEY);
	}

	public static void setInspetor(HttpServletRequest request,
			Inspector inspector) {
		request.getSession().setAttribute(Constants.SessionKey.INSPECTOR_KEY,
				inspector);
	}

	public static Inspector getInspetor(HttpServletRequest request) {
		return (Inspector) request.getSession().getAttribute(
				Constants.SessionKey.INSPECTOR_KEY);
	}

	public static void removeInspector(HttpServletRequest request) {
		request.getSession()
				.removeAttribute(Constants.SessionKey.INSPECTOR_KEY);
	}

}
