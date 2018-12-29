package dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import basetest.BaseTest;
//import entity.Category;
import entity.Product;
import entity.User;

public class ProductDaoTest  extends BaseTest{
	@Autowired
	private ProductDao productDao;
	
	@Test
	@Ignore
	public void testQueryProductCondition() throws Exception {
		//User user = new User();
		//user.setId(5);
		
//		Category cate = new Category();
//		cate.setCategoryId(2);
		Product productCondition = new Product();
//		
//		//productCondition.setUser(user);
//		productCondition.setCategory(cate);
		productCondition.setProductName("");
		List<Product> list = productDao.queryProductList(productCondition, 1, 2);
		System.out.println(list.get(0).getProductName());
		System.out.println(list.get(1).getProductName());
		//System.out.println(list.get(2).getProductName());
		System.out.println(list.size());
		
		int x = productDao.queryProductCount(productCondition);
		System.out.println(x);
	}
	
	@Test
	@Ignore
	public void testGetProductbyUId() throws Exception {
		Integer uId = 5;
		productDao.selectByUId(uId);
		System.out.println();
	}
	
	@Test
	@Ignore
	public void testAddProduct() throws Exception {
		Product product = new  Product();
		product.setProductName("麝香");
		product.setPrice((float) 3.5);
		//product.setProductId(1);
		int x = productDao.addProduct(product);

		System.out.println(x);
	}
	
	@Test
	@Ignore
	public void testDele() throws Exception {
		int x = productDao.deleteByPrimaryKey(3);
		System.out.println(x);
	}
	
	@Test
	//@Ignore
	public void testUpdateProduct() throws Exception {
		User user = new User();
		user.setId(5);
		
		Product product = new  Product();
		product.setPrice((float) 3.5);
		product.setProductId(11);
		product.setUser(user);
		int x = productDao.updateProduct(product);

		System.out.println(x);
	}
	
	@Test
	@Ignore
	public void testSelectAll() throws Exception {
		List<Product> productList = productDao.selectAllProduct();
		System.out.println(productList.get(0).getProductName());
	}
	
	@Test
	@Ignore
	public void testSelectByPrimaryKey() throws Exception {
		Product product = productDao.selectByPrimaryKey(2);
		System.out.println(product.getUser().getName());
	}
	
	@Test
	@Ignore
	public void testSelectByCategoryKey() throws Exception {
		Product product = productDao.selectByPrimaryKey(2);
		System.out.println(product.getUser().getName());
	}
	

}
