package service;

import java.util.List;

import dto.ImageHolder;
import dto.ProductExecution;
import entity.Order;
import entity.Product;
import entity.User;

public interface ProductService {

	/**
	 * 
	 * 取出所有的产品
	 * 
	 * @param productId
	 * @return
	 */
	List<Product> selectAllProduct() throws RuntimeException;

	/**
	 * 
	 * 通过id查产品
	 * 
	 * @param productId
	 * @return
	 */
	Product selectByPrimaryKey(Integer productId) throws RuntimeException;

	/**
	 * 
	 * @param productCondition（可以根据种类，拥有者，id，等等查询）
	 *            必须是对象才行
	 * @param pageIndex
	 *            第几页？
	 * @param pageSize
	 *            一页多少个
	 * @return
	 */

	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	/**
	 * 更新产品信息
	 */
	ProductExecution updateProduct(Product product, ImageHolder thumbnail) throws RuntimeException;

	/**
	 * 删除某个id的产品
	 */
	int deleteByPrimaryKey(Integer productId) throws RuntimeException;

	/**
	 * 添加某个产品
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail) throws RuntimeException;

	/**
	 * 给出某个图片的base64数据
	 */
	String getBase64ByUrl(Product product) throws RuntimeException;

	/**
	 * 商品库存的修改，order里面买了多少，商品库存就减少多少。
	 */
	int ProductStockModify(Integer productId, Order order) throws RuntimeException;

	/**
	 * 折扣
	 */
	void UserCost(User user, Product product) throws RuntimeException;

	/**
	 * 折扣,列表
	 */
	void UserCost(User user, List<Product> product) throws RuntimeException;

}
