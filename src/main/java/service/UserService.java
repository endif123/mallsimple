package service;

import java.util.List;

import entity.User;

public interface UserService {

	// 查询所有的用户
	List<User> selectAllUser();

	// 增加一个用户
	int insertUser(User user) throws RuntimeException;

	// 删除一个用户
	int deleteUser(int id) throws RuntimeException;

	// 修改一个用户
	int updateUser(User user) throws RuntimeException;

	// 查找一个用户
	User selectUserById(int id);

}
