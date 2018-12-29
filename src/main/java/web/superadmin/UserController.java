package web.superadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.User;
//import service.CategoryService;
import service.UserService;
import util.HttpServletRequestUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 用户列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	private String userlist(HttpServletRequest request) {

		return "admin/userlist";
	}

	/**
	 * 用户详情页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getuserpage", method = RequestMethod.GET)
	private String getUserPage(HttpServletRequest request) {

		return "admin/userdetail";
	}

	/**
	 * 增加用户页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/adduser", method = RequestMethod.GET)
	private String adduser(HttpServletRequest request) {

		return "admin/adduser";
	}

	/**
	 * // 选择用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getuserbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getUserById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int userId = HttpServletRequestUtil.getInt(request, "userId");
		if (userId != -1) {
			// User user = userService.
			User user = userService.selectUserById(userId);
			modelMap.put("user", user);
			modelMap.put("success", true);
		} else {
			// modelMap.put("user", new User());
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户不存在");
		}
		return modelMap;

	}

	/**
	 * 得到所有用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getAllUser(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<User> list = userService.selectAllUser();
		modelMap.put("userList", list);
		modelMap.put("success", true);
		return modelMap;

	}

	/**
	 * 删除一个用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteuserbyid", method = RequestMethod.GET)

	private String deleteUserById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// List<User> list = userService.selectAllUser();
		int userId = HttpServletRequestUtil.getInt(request, "userId");

		int x = userService.deleteUser(userId);
		if (x > 0) {
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户删除失败");
		}

		return "admin/userlist";

	}

	/**
	 * 修改一个用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateuserbyid", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> updateUserById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
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
		int x = userService.updateUser(user);
		if (x > 0) {
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户修改失败");
		}

		return modelMap;

	}

	/**
	 * 增加一个用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addUser(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
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
		int x = userService.insertUser(user);
		if (x > 0) {
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户增加失败");
		}

		return modelMap;

	}

}
