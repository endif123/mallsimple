package dao;

import static org.junit.Assert.assertEquals;

//import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import basetest.BaseTest;
import entity.User;

public class UserDaoTest extends BaseTest{
	@Autowired
	private UserDao userDao;
	
	@Test
	public void testASelectOneUser() throws Exception {
		User user = userDao.selectUserById(3);
		System.out.println(user.getName());
		assertEquals("管理员", user.getName());
		
	}
//	@Test
//	public void testASelectUser() throws Exception {
//		List<User> list = userDao.selectAllUser();
//		
//		assertEquals(3, list.size());
//		
//	}
//	@Test
//	public void testinsertUser() throws Exception {
//		//List<User> list = userDao.selectAllUser();
//		User user = new User();
//		user.setPassword("123");
//		user.setName("tom");
//		user.setCost((float) 0.9);
//		user.setType("2");
//		userDao.insertUser(user);
//		
//		
//		assertEquals("tom", user.getName());
//		
//	}
//	@Test
//	public void testdeleteUser() throws Exception {
//		//List<User> list = userDao.selectAllUser();
//		
//		userDao.deleteUser(4);
//		
//		
//		assertEquals("tom", "tom");
//		
//	}
	
	@Test
	@Ignore
	public void testupdateUser() throws Exception {
		//List<User> list = userDao.selectAllUser();
		User user = new User();
		user.setId(3);
		user.setPassword("123");
		user.setName("tom");
		user.setCost((float) 0.8);
		user.setType("2");
		userDao.updateUser(user);
		
		
		assertEquals("tom", user.getName());
		
	}
	
}
