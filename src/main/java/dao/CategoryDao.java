package dao;

import java.util.List;

import entity.Category;

public interface CategoryDao {
	/**
	 * 按id查询类别
	 * 
	 * @param categoryId
	 * @return
	 */
	Category selectByPrimaryKey(Integer categoryId);

	// 查询所有的类别
	List<Category> selectAllCategory();

}
