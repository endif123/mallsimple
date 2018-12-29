package interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import entity.User;

public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 在controller之前就拦住
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 取出用户的信息
		Object userObj = request.getSession().getAttribute("user");
		if (userObj != null) {
			User user = (User) userObj;
			if (user != null && user.getId() != null && user.getId() > 0 && user.getType() == "用户")
				// 正常执行
				return true;
		}

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open ('" + request.getContextPath() + "http://localhost:8888/mall/login/userlogin')");
		out.println("</script>");
		out.println("</html>");
		return false;

	}

}
