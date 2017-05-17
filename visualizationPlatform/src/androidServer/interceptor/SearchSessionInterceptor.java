package androidServer.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import androidServer.util.Constants;
import androidServer.util.SessionUtils;

public class SearchSessionInterceptor extends HandlerInterceptorAdapter {

	private String interceptorURL;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getServletPath();
		// System.out.println("path " + path);
		// url http://www.zxbupt.cn/zhongchou/page/userCentor
		if (interceptorURL != null && path.matches(interceptorURL)) {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String ci = request.getParameter("ci");
			String wpNetwork = request.getParameter("wpNetwork");
			String network = request.getParameter("network");
			String floor = request.getParameter("floor");
			String buildingId = request.getParameter("buildingId");
			String imsi = request.getParameter("imsi");
			// System.out.println("interceptor:" + network);
			if (startTime != null)
				SessionUtils.addAtt(request, Constants.SessionKey.SEARCH_START_TIME, Long.parseLong(startTime));
			if (endTime != null)
				SessionUtils.addAtt(request, Constants.SessionKey.SEARCH_END_TIME, Long.parseLong(endTime));
			if (ci != null)
				SessionUtils.addAtt(request, Constants.SessionKey.SEARCH_CI_KEY, Integer.parseInt(ci));

			if (wpNetwork != null && path.indexOf("/outdoor") != -1) {
				SessionUtils.addAtt(request, Constants.SessionKey.SEARCH_NETWORK_KEY, wpNetwork);
			}
			// System.out.println(1);
			if (ci != null && network != null && path.indexOf("dataInSession") != -1) {
//				System.out.println("putput");
				SessionUtils.addAtt(request, Constants.SessionKey.CI_TO_BE_MODIFIED, Integer.valueOf(ci));
				SessionUtils.addAtt(request, Constants.SessionKey.NETWORK_TO_BE_MODIFIED, network);
			}
			if (floor != null)
				SessionUtils.addAtt(request, Constants.SessionKey.SEARCH_FLOOR_KEY, floor);
			if (buildingId != null)
				SessionUtils.addAtt(request, Constants.SessionKey.SEARCH_BUILDINGID_KEY, buildingId);
			if (imsi != null)
				SessionUtils.addAtt(request, Constants.SessionKey.SEARCH_IMSI_KEY, imsi);
		}
		return true;
	}

	public String getInterceptorURL() {
		return interceptorURL;
	}

	public void setInterceptorURL(String interceptorURL) {
		this.interceptorURL = interceptorURL;
	}

}
