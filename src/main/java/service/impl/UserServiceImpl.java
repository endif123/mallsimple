package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import entity.User;
import service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	/**
	 * 查询
	 */
	@Override
	public List<User> selectAllUser() {
		// TODO Auto-generated method stub
		return userDao.selectAllUser();
	}

	/**
	 * 添加用户
	 */
	@Override
	@Transactional
	public int insertUser(User user) {
		// TODO Auto-generated method stub
		if (user == null) {
			return 0;
		}
		return userDao.insertUser(user);
	}

	/**
	 * 删除用户
	 */
	@Override
	@Transactional
	public int deleteUser(int id) {
		// TODO Auto-generated method stub
		int x = 0;
		// 不允许删除超级管理员
		if (id != 1) {
			x = userDao.deleteUser(id);
		}
		return x;
	}

	/**
	 * 更新用户
	 */

	@Override
	@Transactional
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		int x = 0;

		// 不允许删除超级管理员
		if (user.getId() != 1) {
			x = userDao.updateUser(user);
		}
		return x;
	}

	@Override
	public User selectUserById(int id) {
		// TODO Auto-generated method stub
		return userDao.selectUserById(id);
	}

}
