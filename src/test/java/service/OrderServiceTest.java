package service;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import basetest.BaseTest;
import entity.Order;
import entity.User;

@SuppressWarnings("unused")
public class OrderServiceTest extends BaseTest {

	@Autowired
	private OrderService orderService;

	@Test
	@Ignore
	public void testAddOrderService() throws Exception {
		Order order = new Order();
		order.setAddress("德国");
		order.setNumber(1);

		User user = new User();
		user.setId(3);
		order.setUser(user);

		int x = orderService.AddOrderService(order);
		System.out.println(x);

	}

	@Test
	// @Ignore
	public void testQueryOrderList() throws Exception {
		Order order = new Order();
		order.setuId(3);
		// order.setNumber(1);

		User user = new User();
		user.setId(3);
		order.setUser(user);

		List<Order> list = orderService.QueryOrderList(order, 1, 100);
		System.out.println(list.size());

	}

}
