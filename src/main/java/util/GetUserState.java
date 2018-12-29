package util;

import javax.servlet.http.HttpServletRequest;

import entity.User;

public class GetUserState {
	
	/**
	 * 看看session有没有user，如果没有就返回空，否则返回user
	 * @param request
	 * @return
	 */
	public static User GetUserOrNULL(HttpServletRequest request) {
		if(request == null) {
			return null;
		}
		if(request.getSession().getAttribute("user") == null) {
			return null;
		}
		User currentUser = (User) request.getSession().getAttribute("user");
		return currentUser;
		
	}
	
	public static String GetUserStyle(HttpServletRequest request) {
		if(request == null) {
			return "未登录";
		}
		if(request.getSession().getAttribute("currentUser") == null) {
			return "未登录";
		}
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		return currentUser.getType();
	}
	
	public static Integer GetCurrentUserId(HttpServletRequest request) {
		if(request == null) {
			return -1;
		}
		if(request.getSession().getAttribute("currentUser") == null) {
			return -1;
		}
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		return currentUser.getId();
	}
	
	

}
