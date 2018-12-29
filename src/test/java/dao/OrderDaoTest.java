package dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import basetest.BaseTest;
import entity.Order;
import entity.User;

public class OrderDaoTest extends BaseTest {

	@Autowired
	private OrderDao orderDao;

	@Test
	// @Ignore
	public void testSelectOrderByShopOwner() {

		List<Order> list = orderDao.selectOrderByShopOwner(2, 0, 100, null);
		System.out.println(list.size());

	}

	@Test
	@Ignore
	public void testQueryOrder() {
		User user = new User();
		user.setId(3);

		Order order = new Order();
		order.setUser(user);

		List<Order> list = orderDao.queryOrderList(order, 0, 100);
		System.out.println(list.size());

	}

	@Test
	@Ignore
	public void testAddOrder() {
		Order order = new Order();
		order.setAddress("11111");
		order.setMobile("1111");
		// order.setOrderCreateDate(new Date());
		order.setNumber(1);
		// order.setuId(3);
		order.setpId(1);
		orderDao.addOrder(order);
		assertEquals((Integer) 3, order.getOrderId());
	}

	@Test
	@Ignore
	public void testModifyOrder() {
		Order order = new Order();
		order.setOrderId(3);
		order.setAddress("浦东");
		order.setMobile("19999");
		order.setOrderCreateDate(new Date());
		order.setNumber(3);
		order.setuId(3);
		order.setpId(1);
		User user = new User();
		user.setId(3);
		order.setUser(user);
		orderDao.modifyOrder(order);
		assertEquals("188", orderDao.queryOrderList(order, 0, 10).get(0).getMobile());
	}

}
