package service;

import java.util.List;

import entity.Order;

public interface OrderService {
	/**
	 * 增加一个订单!!order中必须有用户信息
	 * 
	 * @param order
	 * @return
	 */
	int AddOrderService(Order order) throws RuntimeException;

	/**
	 * 更新一个订单,!!order中必须有用户信息
	 * 
	 * @param order
	 * @return
	 */
	int modifyService(Order order) throws RuntimeException;

	/**
	 * 条件查询
	 * 
	 * @param order
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<Order> QueryOrderList(Order orderCondition, int pageIndex, int pageSize);

	/**
	 * //添加订单中商品的拥有者信息s
	 * 
	 * @param order
	 * @return
	 */
	Order addOrderProductOwner(Order order) throws RuntimeException;

	/**
	 * //商家自己来查询订单
	 * 
	 * @param order
	 * @return
	 */
	List<Order> selectOrderByShopOwner(Integer id, int pageIndex, int pageSize, Integer orderId);

}
