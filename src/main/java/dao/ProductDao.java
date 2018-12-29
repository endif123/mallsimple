package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.Product;

public interface ProductDao {

	/**
	 * 分页查询,可输入的条件有：店铺名（模糊），Id,类别,用户名
	 * 
	 * @param
	 * @param rowIndex
	 *            从第几行开始取
	 * @param pageSize
	 *            返回多少行
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 返回符合要求的总数
	 * 
	 * @param productCondition
	 * @return
	 */
	Integer queryProductCount(@Param("productCondition") Product productCondition);

	/**
	 * 
	 * 取出所有的产品
	 * 
	 * @param
	 * @return
	 */
	List<Product> selectAllProduct();

	/**
	 * 
	 * 通过id查产品
	 * 
	 * @param productId
	 * @return
	 */
	Product selectByPrimaryKey(Integer productId);

	/**
	 * 
	 * 通过uId查产品
	 * 
	 * @param uId
	 * @return
	 */
	List<Product> selectByUId(Integer uId);

	/**
	 * 根据种类选产品
	 * 
	 */
	List<Product> selectByCategoryKey(Integer categoryId);

	/**
	 * 更新产品信息
	 */
	int updateProduct(Product record);

	/**
	 * 删除某个id的产品
	 */
	int deleteByPrimaryKey(Integer productId);

	/**
	 * 加一个产品
	 */
	int addProduct(Product record);

	/**
	 * 搞库存，输入一个product,包含id和stock，就可以更新这个stock;
	 */
	int updateProductStock(Product product);

}
