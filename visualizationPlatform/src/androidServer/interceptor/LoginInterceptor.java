package androidServer.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import androidServer.util.SessionUtils;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private Logger log = Logger.getLogger(LoginInterceptor.class);
	private String noLoginURL;// 利用正则映射到不拦截的路径

	public void setnoLoginURL(String noLoginURL) {
		this.noLoginURL = noLoginURL;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		request.setAttribute("mainpath", "/androidServer");
//		
//		log.info("interceptor path " + path);
//		
		String path = request.getServletPath();
		if (noLoginURL == null || path.matches(noLoginURL)) {
			// 不需要登陆
			return true;
		}
		 
		// path /page/userCentor
		if (SessionUtils.getUser(request) == null) {
			if(path.contains("Wy")){
				request.getRequestDispatcher("/WEB-INF/pages/loginWy.jsp").forward(
						request, response);	
			return false;
			}else{
			request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(
					request, response);
			return false;
		}
		}
		return true;
 
	}
}
