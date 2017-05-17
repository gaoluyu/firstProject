package androidServer.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import androidServer.util.AndroidUtil;
import androidServer.util.Constants;
import androidServer.util.JSONUtil;
import androidServer.util.SessionUtils;
import androidServer.util.WebUtil;

public class AndroidLoginInterceptor extends HandlerInterceptorAdapter {

	private Logger log = Logger.getLogger(AndroidLoginInterceptor.class);
	private String noLoginURL;// 利用正则映射到不拦截的路径

	public void setnoLoginURL(String noLoginURL) {
		this.noLoginURL = noLoginURL;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//
		// log.info("interceptor path " + path);
		//
		String path = request.getServletPath();
		if (noLoginURL == null || path.matches(noLoginURL)) {
			// 不需要登陆
			return true;
		}

		// path /page/userCentor
		if (SessionUtils.getInspetor(request) == null) {
			String returnMapJson = JSONUtil.ObjectToJson(
					AndroidUtil.errorResponse(request, AndroidUtil.NONLOGIN_ERROR, AndroidUtil.NONLOGIN_ERROR_REASON));
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(returnMapJson);
			return false;
		}
		return true;

	}
}
