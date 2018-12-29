package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDao;
import entity.User;

@Service
public class LoginServiceImpl implements service.LoginService {
	@Autowired
	private UserDao userDao;

	@Override
	public User CheckUserPassword(User user) {
		// TODO Auto-generated method stub
		User localUser = userDao.selectUserById(user.getId());
		if (localUser == null)
			return null;
		if (localUser.getPassword().equals(user.getPassword())) {// 密码正确
			return localUser;
		}
		return null;
	}

}
