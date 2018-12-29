package web.product;

//import java.io.File;
import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.ImageHolder;
import dto.ProductExecution;
import entity.Product;
import entity.User;
import enums.ProductStateEnum;
import service.ProductService;
import util.GetUserState;
import util.HttpServletRequestUtil;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	/**
	 * 路由，商品种类列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myproduct", method = RequestMethod.GET)
	private String productList(HttpServletRequest request) {

		return "product/productlist";
	}

	/**
	 * 路由，加商品
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproduct", method = RequestMethod.GET)
	private String addProductTest(HttpServletRequest request) {

		return "product/addproduct";
	}

	/**
	 * 路由，修改商品
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyproduct", method = RequestMethod.GET)
	private String modifyProductTest(HttpServletRequest request) {

		return "product/modifyproduct";
	}

	/**
	 * 删除
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteproductdetail", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> deleteproductdetail(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Integer productId = HttpServletRequestUtil.getInt(request, "productId");
		// id不能错啊
		if (productId != null && productId > -1) {
			try {
				int x = productService.deleteByPrimaryKey(productId);
				if (x < 1) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "删除行数为0");
					return modelMap;
				}
				// modelMap.put("product", x);
				modelMap.put("success", true);
				return modelMap;
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "传进来的对象不对啊");
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "id不对");
			return modelMap;
		}
		// return null;
	}

	/**
	 * 删除
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteproductbyid", method = RequestMethod.GET)

	private String deleteproductbyid(HttpServletRequest request) {
		// Map<String, Object> modelMap = new HashMap<String, Object>();
		Integer productId = HttpServletRequestUtil.getInt(request, "productId");
		// id不能错啊
		if (productId != null && productId > -1) {
			try {
				int x = productService.deleteByPrimaryKey(productId);
				if (x < 1) {

					return "删除行数为0";
				}
				// modelMap.put("product", x);
				// modelMap.put("success", true);
				return "product/productlist";
			} catch (RuntimeException e) {
				// modelMap.put("success", false);
				// modelMap.put("errMsg", "传进来的对象不对啊");
				return "wrongPage";
			}
		} else {
			// modelMap.put("success", false);
			// modelMap.put("errMsg", "id不对");
			return "wrongPage";
		}
		// return null;
	}

	/**
	 * 拿到商品详细信息
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductdetailbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> updateproductdetail(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Integer productId = HttpServletRequestUtil.getInt(request, "productId");
		// id不能错啊
		if (productId != null && productId > -1) {
			try {
				Product product = productService.selectByPrimaryKey(productId);
				// 处理图片
				if (product.getProductImg() != null && !"".equals(product.getProductImg())) {
					String base64 = productService.getBase64ByUrl(product);
					modelMap.put("base64", base64);
				}
				User user = GetUserState.GetUserOrNULL(request);
				// 用户折扣
				productService.UserCost(user, product);

				modelMap.put("product", product);
				modelMap.put("success", true);
				return modelMap;
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "传进来的对象不对啊");
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "id不对");
			return modelMap;
		}
		// return null;
	}

	/**
	 * 首先搞定添加
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproductdetail", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addShopAuthMap(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		// 1. 接收并转化参数，包括商品和图片
		// 2. 存储商品
		// 3. 返回success

		// 1. 利用反射拿到对象
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {// 反射的catech
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		// 1.1 图片，把前端传来的图片拿到
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile productImg = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			productImg = (CommonsMultipartFile) multipartRequest.getFile("productImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}

		// 2. 插入一个product
		if (product != null && productImg != null) {
			// 自动获取user信息，session存的
			User user = (User) request.getSession().getAttribute("user");

			product.setUser(user);
			ProductExecution se = null;
			try {
				ImageHolder imageHolder = new ImageHolder(productImg.getInputStream(),
						productImg.getOriginalFilename());
				se = productService.addProduct(product, imageHolder);
				if (se.getState() == ProductStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					// 更新该用户可以操作的商品信息
					/*
					 * List<Product> productList = (List<Product>)
					 * request.getSession().getAttribute("productList"); if(productList == null ||
					 * productList.size() == 0) { productList = new ArrayList<Product>(); }
					 * productList.add(se.getProduct());
					 * request.getSession().setAttribute("productList", productList);
					 */

				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", "97");
				}
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}

			return modelMap;

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}

	}

	/**
	 * 修改
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyproductdetail", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyproductdetail(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		// 1. 接收并转化参数，包括商品和图片
		// 2. 存储商品
		// 3. 返回success

		// 1. 利用反射拿到对象
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {// 反射的catech
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		User user = (User) request.getSession().getAttribute("user");

		product.setUser(user);

		// 1.1 图片，把前端传来的图片拿到(不一定有的)
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile productImg = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			productImg = (CommonsMultipartFile) multipartRequest.getFile("productImg");
		}

		// 2. 插入一个product
		if (product != null && product.getProductId() != null) {

			ProductExecution se = null;
			try {
				if (productImg == null) {// 图片为空的判断
					se = productService.updateProduct(product, null);

				} else {
					ImageHolder imageHolder = new ImageHolder(productImg.getInputStream(),
							productImg.getOriginalFilename());
					se = productService.updateProduct(product, imageHolder);
				}
				if (se.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", "97");
				}
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}

			return modelMap;

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
			return modelMap;
		}

	}

	/**
	 * 管理商品列表：只能管自己的
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductlist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getproductlist(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Product productCondition = new Product();
		// 1. 判断用户！！！！！！！！！这个东西应该是从session中拿的,
		User user = (User) request.getSession().getAttribute("user");
		productCondition.setUser(user);
		// 2. 获取前台页码，获取商品数量
		// 页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		pageIndex = 0;
		// 获取最大数量
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		pageSize = 100;
		// 3. 空值判断
		// 如果不为空
		if ((pageIndex > -1) && (pageSize > -1) && (user != null) && (user.getId() != null)) {

			ProductExecution se = productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", se.getProductList());
			modelMap.put("success", true);

			// 拿总数
			modelMap.put("productCount", se.getCount());

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入参数有误或未登录");
		}
		return modelMap;

	}

}
