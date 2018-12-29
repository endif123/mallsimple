package dao;

import java.util.List;

//import entity.Shop;
import entity.User;

public interface UserDao {

	// 查询所有的用户
	List<User> selectAllUser();

	// 查找一个用户
	User selectUserById(int id);

	// 增加一个用户
	int insertUser(User user);

	// 删除一个用户
	int deleteUser(int id);

	// 修改一个用户
	int updateUser(User user);

}
