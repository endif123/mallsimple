package web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.Category;
//import entity.User;
import service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	/**
	 * 先选类别的页面路由
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/choosecategory", method = RequestMethod.GET)
	private String productList(HttpServletRequest request) {

		return "shopping/categorylist";
	}

	/**
	 * 给出所有list 的列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getAllCategory(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Category> categoryList = categoryService.getAllCategory();
		modelMap.put("categoryList", categoryList);
		modelMap.put("success", true);

		return modelMap;

	}

}
