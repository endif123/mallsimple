package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.Order;

public interface OrderDao {

	/**
	 * 分页查询,可输入的条件有：店铺名（模糊），Id,类别,用户名
	 * 
	 * @param orderCondition
	 * @param rowIndex
	 *            从第几行开始取
	 * @param pageSize
	 *            返回多少行
	 * @return
	 */
	List<Order> queryOrderList(@Param("orderCondition") Order orderCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 生成一个订单
	 * 
	 * @param order
	 * @return
	 */
	int addOrder(Order order);

	/**
	 * 修改一个订单
	 * 
	 * @param order
	 * @return
	 */
	int modifyOrder(Order order);

	/**
	 * 根据一个店家的user搜索他的订单
	 * 
	 * @param //商家的id
	 * @return
	 */
	List<Order> selectOrderByShopOwner(@Param("id") Integer id, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize, @Param("orderId") Integer orderId);
}
