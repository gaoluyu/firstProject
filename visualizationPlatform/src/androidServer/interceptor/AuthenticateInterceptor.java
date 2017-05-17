package androidServer.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import androidServer.annotation.Authenticate;
import androidServer.annotation.AuthenticatePlus;
import androidServer.bean.User;
import androidServer.service.DBService;
import androidServer.util.Constants;
import androidServer.util.JSONUtil;
import androidServer.util.SessionUtils;
import androidServer.util.WebUtil;

public class AuthenticateInterceptor extends HandlerInterceptorAdapter {

	private String noInterceptorURL;
	@Autowired
	DBService dbService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getServletPath();
		// System.out.println("AuthenticateInterceptor");
		// url http://www.zxbupt.cn/zhongchou/page/userCentor
		if (noInterceptorURL != null && path.matches(noInterceptorURL)) {
			return true;
		}
		if (request != null) {
			User user = SessionUtils.getUser(request);
			if (user != null) {
				if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
					HandlerMethod method = (HandlerMethod) handler;
					// Authenticate check =
					// method.getMethodAnnotation(Authenticate.class);
					AuthenticatePlus check = method.getMethodAnnotation(AuthenticatePlus.class);
					if (check != null) {
						boolean pass = validate(check, user);
						if (!pass) {
							String returnMapJson = JSONUtil.ObjectToJson(WebUtil.errorResponse(request,
									WebUtil.NO_ENOUGH_AUTHORITY, Constants.ErrorType.NO_ENOUGH_AUTHORITY));
							response.setCharacterEncoding("UTF-8");
							response.setContentType("application/json;charset=UTF-8");
							response.getWriter().print(returnMapJson);

							return false;
						}

					} else {
						// 没有annotation
						if (path.indexOf("/page/") != -1) {
							// 检查页面请求的Url
							String availablePagesRegrex = dbService.getAuthenticatedPages(user.getRole());

							if (availablePagesRegrex == null || !path.matches(availablePagesRegrex)) {
								System.out.println(path + " \nno enough authority " + availablePagesRegrex);
								request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
								return false;
							}
						}

					}
				}

			} else {
				// user==null ,属于没有登录的情形,留给其他的拦截器或方法处理
			}
		}
		return true;

	}

	private boolean validate(Authenticate check, User user) {
		int role = check.value();
		boolean isStrict = check.isStrict();

		boolean pass = false;
		if (isStrict) {
			pass = user.getRole() == role ? true : false;
		} else {
			pass = user.getRole() <= role ? true : false;
		}
		return pass;

	}

	private boolean validate(AuthenticatePlus check, User user) {
		int authenticateBits = check.value();
		// boolean pass = false;
		// pass = (user.getRole() & authenticateBits) == 0 ? false : true;
		return (user.getRole() & authenticateBits) == 0 ? false : true;
	}

	public String getNoInterceptorURL() {
		return noInterceptorURL;
	}

	public void setNoInterceptorURL(String noInterceptorURL) {
		this.noInterceptorURL = noInterceptorURL;
	}

}
