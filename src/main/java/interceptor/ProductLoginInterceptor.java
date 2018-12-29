package interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import entity.User;
import util.GetUserState;

public class ProductLoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 在controller之前就拦住
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 取出用户的信息
		// 取出用户的信息
		User user = GetUserState.GetUserOrNULL(request);

		// if (user != null && user.getType() == "管理员") {
		if (user != null && (user.getType().equals("管理员") || user.getType().equals("商家"))) {
			// 正常执行
			return true;
		}

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open ('" + request.getContextPath() + "/login/userlogin')");
		out.println("</script>");
		out.println("</html>");
		return false;

	}

}
