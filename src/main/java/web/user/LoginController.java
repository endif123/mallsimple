package web.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.User;
import service.LoginService;
import service.UserService;
import util.CodeUtil;
import util.HttpServletRequestUtil;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserService userService;

	@Autowired
	private LoginService loginService;

	/**
	 * 路由，登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userlogin", method = RequestMethod.GET)
	private String addProductTest(HttpServletRequest request) {

		return "login/login";
	}

	/**
	 * 路由，注册
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/registerpage", method = RequestMethod.GET)
	private String Registerpage(HttpServletRequest request) {

		return "login/register";
	}

	/**
	 * 会验证传过来的用户密码，返回如果正确返回user
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loginsubmit")
	@ResponseBody
	private Map<String, Object> loginsubmit(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码验证
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}

		// -------获得对象

		// 首先拿到字符串，有个检验过程
		String userStr = HttpServletRequestUtil.getString(request, "userStr");
		// 反射走起
		ObjectMapper mapper = new ObjectMapper();
		User user = null;
		try {
			user = mapper.readValue(userStr, User.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		// 验证用户是否正确
		User userFlag = loginService.CheckUserPassword(user);
		if (userFlag == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名或密码错误");
			return modelMap;
		}
		// 如果正确，写入session
		userFlag.setPassword(null);
		request.getSession().setAttribute("user", userFlag);
		request.getSession().setAttribute("currentUser", userFlag);

		// request.getParameter("userStr");
		modelMap.put("success", true);
		modelMap.put("user", userFlag);
		return modelMap;
	}

	/**
	 * 路由，登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loginoutpage", method = RequestMethod.GET)
	private String loginoutpage(HttpServletRequest request) {

		return "shopping/loginout";
	}

	/**
	 * 注销登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> customerLogoutCheck(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/registersubmit", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registersubmit(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码验证
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}

		// -------获得user对象

		// 首先拿到字符串，有个检验过程
		String userStr = HttpServletRequestUtil.getString(request, "userStr");
		// 反射走起
		ObjectMapper mapper = new ObjectMapper();
		User user = null;
		try {
			user = mapper.readValue(userStr, User.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		// 尝试注册
		int x = userService.insertUser(user);
		if (x > 0) {
			modelMap.put("success", true);
			modelMap.put("user", user);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户注册失败");
		}

		return modelMap;
	}

}
