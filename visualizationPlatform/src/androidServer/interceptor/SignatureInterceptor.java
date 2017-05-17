package androidServer.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import androidServer.util.SessionUtils;

public class SignatureInterceptor extends HandlerInterceptorAdapter {

	private Logger log = Logger.getLogger(SignatureInterceptor.class);
	private String sigPage;

	public void setSigPage(String sigPage) {
		this.sigPage = sigPage;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// String path = request.getServletPath();
		//
		// log.info("SignatureInterceptor path " + path);
		//
		// if (path.matches(sigPage)) {
		// String nonceStr = "chihuo";
		// long timestamp = System.currentTimeMillis();
		// String url = "http://www.zxbupt.cn/zhongchou" + path;
		// String signature = WeiXinUtil
		// .getSignature(nonceStr, timestamp, url);
		//
		// request.setAttribute("appId", WeiXinUtil.appId);
		// request.setAttribute("timestamp", timestamp);
		// request.setAttribute("nonceStr", nonceStr);
		// request.setAttribute("signature", signature);
		// }
		request.setAttribute("mainpath", "/androidServer");
		// request.setAttribute("ciInSession", SessionUtils.getCi(request));

		return true;

	}
}
