package androidServer.aop;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import androidServer.annotation.Authenticate;
import androidServer.bean.User;
import androidServer.util.Constants;
import androidServer.util.JSONUtil;
import androidServer.util.SessionUtils;
import androidServer.util.WebUtil;

public class AuthorityAspect {

	public void doAfter(JoinPoint jp) {
	}

	@SuppressWarnings("unchecked")
	private static <T> T getArgumentByClass(ProceedingJoinPoint pjp, Class<T> argClass) {
		T arg = null;
		Object[] objs = pjp.getArgs();
		for (Object obj : objs) {
			if (obj.getClass().getName() == argClass.getName()) {
				arg = (T) obj;
			}
		}
		return arg;
	}

	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		// check if login
		System.out.println("authority");
		MethodSignature sig = (MethodSignature) pjp.getSignature();
		Annotation[] anns = sig.getMethod().getAnnotations();
		for (Annotation a : anns) {
			if (a.annotationType().equals(Authenticate.class)) {
				Authenticate check = (Authenticate) a;
				int role = check.value();
				boolean isStrict = check.isStrict();
				HttpServletRequest request = getArgumentByClass(pjp, HttpServletRequest.class);
				if (request != null) {
					// 可能是Controller的方法
					User user = SessionUtils.getUser(request);
					if (user != null) {
						boolean pass = false;
						if (isStrict) {
							pass = user.getRole() == role ? true : false;
						} else {
							pass = user.getRole() <= role ? true : false;
						}
						if (!pass) {
							HttpServletResponse response = getArgumentByClass(pjp, HttpServletResponse.class);
							String returnMapJson = JSONUtil.ObjectToJson(WebUtil.errorResponse(request,
									WebUtil.NO_ENOUGH_AUTHORITY, Constants.ErrorType.NO_ENOUGH_AUTHORITY));
							response.getWriter().print(returnMapJson);
							return null;
						}
					}
				} else {
					// user==null ,属于没有登录的情形,留给其他的拦截器或方法处理
				}
				break;
			}
		}

		return pjp.proceed();
	}

	public void doBefore(JoinPoint jp) {
//		System.out.println("authority before");
	}

	public void doThrowing(JoinPoint jp, Throwable ex) {
		System.out.println("method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName()
				+ " throw exception");
		ex.printStackTrace();
	}
}