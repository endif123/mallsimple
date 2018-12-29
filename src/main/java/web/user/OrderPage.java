package web.user;

import java.util.Date;
//import java.sql.Date;
//import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Order;
import entity.User;
import service.OrderService;
import service.ProductService;
import util.GetUserState;
import util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopping")
public class OrderPage {

	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;

	/**
	 * 下单页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/orderpage", method = RequestMethod.GET)
	private String orderpage(HttpServletRequest request) {

		return "shopping/orderpage";
	}

	/**
	 * 订单列表查看路由
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/orderlist", method = RequestMethod.GET)
	private String orderlist(HttpServletRequest request) {

		return "shopping/orderlist";
	}

	/**
	 * //商家自己来查询订单列表 路由
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shoporderlist", method = RequestMethod.GET)
	private String shopownerorderlist(HttpServletRequest request) {

		return "product/shoporderlist";
	}

	/**
	 * 订单详情查看页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/orderdetail", method = RequestMethod.GET)
	private String orderdetail(HttpServletRequest request) {

		return "shopping/orderdetail";
	}

	/**
	 * 商家订单详情查看页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shoporderdetail", method = RequestMethod.GET)
	private String shoporderdetail(HttpServletRequest request) {

		return "product/shoporderdetail";
	}

	/**
	 * 用户提交订单的处理
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/submitorder", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> submitorder(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		// 1. 首先拿到字符串，有个检验过程
		String orderStr = HttpServletRequestUtil.getString(request, "orderStr");
		// 反射走起
		ObjectMapper mapper = new ObjectMapper();
		Order order = null;
		try {
			order = mapper.readValue(orderStr, Order.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		// 2. 验证用户
		User user = GetUserState.GetUserOrNULL(request);
		if (user == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户验证失败");
			return modelMap;
		}

		order.setUser(user);
		int x = 0;
		// 尝试添加
		try {
			x = orderService.AddOrderService(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			modelMap.put("success", false);
			modelMap.put("controller插入时候出了问题", e.getMessage());
		}
		// 查看是否插入成功
		if (x < 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "订单生成失败");
			return modelMap;
		}
		// 减库存
		productService.ProductStockModify(order.getpId(), order);
		modelMap.put("success", true);
		// int x = userService.updateUser(user);
		return modelMap;

	}

	/**
	 * 用户订单列表的处理
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/orderlistdetail")
	@ResponseBody
	private Map<String, Object> orderlistdetail(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		// 1. 验证用户
		User user = GetUserState.GetUserOrNULL(request);
		if (user == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户验证失败");
			return modelMap;
		}
		Order order = new Order();
		order.setUser(user);
		List<Order> orderList = null;

		// 第几页
		Integer pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		if (pageIndex < 1) {
			pageIndex = 1;
		}

		// 尝试添加
		try {
			orderList = orderService.QueryOrderList(order, pageIndex, 5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			modelMap.put("success", false);
			modelMap.put("controller插入时候出了问题", e.getMessage());
		}
		// 如果没有订单就算了
		if (orderList.size() < 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "您没有订单");
			return modelMap;
		}
		// 成功查询
		modelMap.put("orderList", orderList);
		modelMap.put("success", true);
		return modelMap;

	}

	/**
	 * 用户订单号查订单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/orderdetailbyid")
	@ResponseBody
	private Map<String, Object> orderdetailbyid(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		// 1. 拿出订单号
		Integer orderId = HttpServletRequestUtil.getInt(request, "orderId");
		// id不能错啊
		if (orderId == null || orderId < 1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "orderid错误");
			return modelMap;
		}

		// 2. 验证用户
		User user = GetUserState.GetUserOrNULL(request);
		if (user == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户验证失败");
			return modelMap;
		}

		Order order = new Order();
		order.setUser(user);
		order.setOrderId(orderId);
		List<Order> orderList = null;
		// 3. 尝试查询
		try {
			orderList = orderService.QueryOrderList(order, 0, 100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			modelMap.put("success", false);
			modelMap.put("controller插入时候出了问题", e.getMessage());
		}
		// 如果没有订单就算了
		if (orderList == null || orderList.size() == 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "您没有订单");
			return modelMap;
		}
		// 添加商品的拥有者信息
		Order orderTemp = orderList.get(0);
		Order orderResult = orderService.addOrderProductOwner(orderTemp);

		// 成功查询
		modelMap.put("order", orderResult);
		modelMap.put("success", true);
		return modelMap;

	}

	/**
	 * 商家发货
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendorderbyid")
	@ResponseBody
	private Map<String, Object> sendorderbyid(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 1. 拿出订单号
		Integer orderId = HttpServletRequestUtil.getInt(request, "orderId");
		// id不能错啊
		if (orderId == null || orderId < 1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "orderid错误");
			return modelMap;
		}
		// 2. 验证用户
		User user = GetUserState.GetUserOrNULL(request);
		if (user == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户验证失败");
			return modelMap;
		}

		Order order = new Order();
		order.setUser(user);
		order.setOrderId(orderId);
		order.setOrderPayDate(new Date());
		// 3. 尝试发货
		try {
			orderService.modifyService(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			modelMap.put("success", false);
			modelMap.put("controller插入时候出了问题", e.getMessage());
		}
		modelMap.put("success", true);
		return modelMap;
	}

	/**
	 * //商家自己来查询订单 //如果传进来了orderID，那么久说明只查一个
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectOrderByShopOwner")
	@ResponseBody
	private Map<String, Object> selectOrderByShopOwner(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		// 1. 验证用户
		User user = GetUserState.GetUserOrNULL(request);
		if (user == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户验证失败");
			return modelMap;
		}
		int ownerId = user.getId();
		List<Order> orderList = null;

		// 第几页
		Integer pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		if (pageIndex < 1) {
			pageIndex = 1;
		}

		// 是否是单个查询
		Integer orderId = HttpServletRequestUtil.getInt(request, "orderId");
		if (orderId == -1) {
			orderId = null;
		}

		// 尝试查询
		try {
			orderList = orderService.selectOrderByShopOwner(ownerId, pageIndex, 5, orderId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			modelMap.put("success", false);
			modelMap.put("controller插入时候出了问题", e.getMessage());
		}
		// 如果没有订单就算了
		if (orderList == null || orderList.size() < 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "您没有订单");
			return modelMap;
		}
		// 成功查询
		if (orderId == null) {
			modelMap.put("orderList", orderList);
		} else {
			// 添加商品的拥有者信息
			Order orderTemp = orderList.get(0);
			Order orderResult = orderService.addOrderProductOwner(orderTemp);
			modelMap.put("order", orderResult);
		}
		modelMap.put("success", true);
		return modelMap;

	}

}
