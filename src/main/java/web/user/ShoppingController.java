package web.user;

import java.util.HashMap;
//import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.fasterxml.jackson.databind.ObjectMapper;

import dto.ProductExecution;
import entity.Category;
import entity.Product;
import entity.User;
import service.ProductService;
import util.GetUserState;
import util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

	@Autowired
	private ProductService productService;

	/**
	 * 先选类别的页面路由
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userchoosecategory", method = RequestMethod.GET)
	private String productList(HttpServletRequest request) {

		return "shopping/usercategorylist";
	}

	/**
	 * 用户根据种类查看商品列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userproductlist", method = RequestMethod.GET)
	private String userProductList(HttpServletRequest request) {

		return "shopping/userproductlist";
	}

	/**
	 * 查看商品详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userproductdetail", method = RequestMethod.GET)
	private String userProductDatail(HttpServletRequest request) {

		return "shopping/userproductdetail";
	}

	/**
	 * 根据种类拿商品
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductlistbycategory", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getproductlistbycategory(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 第几页
		Integer pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		if (pageIndex < 1) {
			pageIndex = 1;
		}

		// 按种类查找，这是哪种商品
		Integer categoryId = HttpServletRequestUtil.getInt(request, "categoryId");
		if (categoryId == null || categoryId < 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "种类信息有误");
			return modelMap;
		}
		Category category = new Category();
		category.setCategoryId(categoryId);

		Product product = new Product();
		product.setCategory(category);
		if (category != null && category.getCategoryId() != null) {
			ProductExecution se = productService.getProductList(product, pageIndex, 3);
			// 用户的折扣
			User user = GetUserState.GetUserOrNULL(request);
			productService.UserCost(user, se.getProductList());
			modelMap.put("productList", se.getProductList());
			modelMap.put("pageCount", se.getCount());
			// modelMap.put("pageCount", se.getCount());
			if ((se.getCount() / 3 + 1) < pageIndex) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "没有了");
				return modelMap;
			}

			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入参数有误或未登录");
		}
		return modelMap;

	}

}
