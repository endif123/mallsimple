package service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.OrderDao;
import dao.ProductDao;
import entity.Order;
import entity.Product;
import exception.ProductOperationException;
import service.OrderService;
import service.ProductService;
import util.PageCalculator;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductService productService;

	@Override
	@Transactional
	public int AddOrderService(Order order) throws ProductOperationException {
		// TODO Auto-generated method stub
		// 空值判断
		if (order == null) {
			return 0;
		}
		if (order.getNumber() < 1) {
			// 没有东西直接报错
			throw new ProductOperationException("addOrderImg error:没有商品 ");
		}
		order.setuId(order.getUser().getId());
		order.setOrderCreateDate(new Date());
		int x = 0;
		try {
			x = orderDao.addOrder(order);
		} catch (Exception e) {// 没有成功的报错
			throw new ProductOperationException("addOrderImg error: 没有成功修改" + e.getMessage());
		}
		if (x <= 0) {
			// throw new ProductOperationException("addOrderImg error: " + e.getMessage());
		}

		return x;
	}

	@Override
	@Transactional
	public int modifyService(Order order) throws ProductOperationException {
		// TODO Auto-generated method stub
		// 空值判断
		if (order == null) {
			return 0;
		} else if (order.getUser() == null) {
			throw new ProductOperationException("ModifyOrderImg error: 用户信息缺失");
		}
		int x = 0;
		try {
			x = orderDao.modifyOrder(order);
		} catch (Exception e) {// 总图片的catch
			throw new ProductOperationException("ModifyOrderImg error: " + e.getMessage());
		}

		return x;
	}

	@Override
	public List<Order> QueryOrderList(Order orderCondition, int pageIndex, int pageSize) {

		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);// 这个方法拿到了起始页的index
		List<Order> orderList = orderDao.queryOrderList(orderCondition, rowIndex, pageSize);

		return orderList;
	}

	@Override
	public Order addOrderProductOwner(Order order) throws ProductOperationException {
		int productId = order.getProduct().getProductId();
		Product product = productDao.selectByPrimaryKey(productId);
		String base64 = productService.getBase64ByUrl(product);
		product.setProductImg(base64);
		order.setProduct(product);

		return order;
	}

	@Override
	public List<Order> selectOrderByShopOwner(Integer id, int pageIndex, int pageSize, Integer orderId) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);// 这个方法拿到了起始页的index
		List<Order> orderList = orderDao.selectOrderByShopOwner(id, rowIndex, pageSize, orderId);
		return orderList;
	}

}
