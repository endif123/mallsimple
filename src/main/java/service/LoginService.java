package service;

import entity.User;

public interface LoginService {

	/**
	 * 用来检测用户名和密码
	 * 
	 * @param user
	 * @return
	 */
	User CheckUserPassword(User user);

}
