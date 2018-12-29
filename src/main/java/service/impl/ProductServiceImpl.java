package service.impl;

//import java.io.File;
//import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;

//import ch.qos.logback.core.util.FileUtil;
import dao.ProductDao;
import dto.ImageHolder;
import dto.ProductExecution;
import entity.Order;
import entity.Product;
import entity.User;
import enums.ProductStateEnum;
import exception.ProductOperationException;
import service.ProductService;
import util.ImageUtil;
import util.PageCalculator;
import util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	public Product selectByPrimaryKey(Integer productId) {
		// TODO Auto-generated method stub
		return productDao.selectByPrimaryKey(productId);
		// return null;
	}

	@Override
	@Transactional
	public ProductExecution updateProduct(Product product, ImageHolder thumbnail) {// 和add差不多，就不注释啦
		// TODO Auto-generated method stub

		// 1.首先判断是否处理图片
		// 2.更新产品信息
		// 1.判断是否需要处理图片
		if (product == null || product.getProductId() == null) {
			return new ProductExecution(ProductStateEnum.NULL_SHOPID);
		} else {
			try {
				if (thumbnail != null && thumbnail.getImage() != null && thumbnail.getImageName() != null
						&& !"".equals(thumbnail.getImageName())) {
					Product tempProduct = productDao.selectByPrimaryKey(product.getProductId());
					if (tempProduct.getProductImg() != null) {
						ImageUtil.deleteFile(tempProduct.getProductImg());
					}
					addProductImg(product, thumbnail);
				}
				// 2.更新产品信息
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					return new ProductExecution(ProductStateEnum.INNER_ERROR);
				} else {// 创建成功
					product = productDao.selectByPrimaryKey(product.getProductId());
					return new ProductExecution(ProductStateEnum.SUCCESS, product);
				}
			} catch (Exception e) {
				throw new RuntimeException("modifyProduct error: " + e.getMessage());
			}
		}

		// return 0;
	}

	@Override
	@Transactional
	public int deleteByPrimaryKey(Integer productId) {
		// TODO Auto-generated method stub

		// 空值判断
		if (productId == null) {
			return 0;
		}
		Product product = productDao.selectByPrimaryKey(productId);
		ImageUtil.deleteFile(product.getProductImg());
		int x = productDao.deleteByPrimaryKey(productId);
		if (x < 1) {
			throw new ProductOperationException("删除错误");
		}

		return x;
	}

	@Override
	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail) throws ProductOperationException {
		// TODO Auto-generated method stub
		// 空值判断
		if (product == null) {
			return new ProductExecution(ProductStateEnum.NULL_SHOP_INFO);
		}
		// 用户的自动添加
		product = addUId(product);
		// 正式开始添加商品
		try {
			// 尝试添加
			int effectedNum = productDao.addProduct(product);
			if (effectedNum <= 0) {
				throw new ProductOperationException("产品创建失败");// 会回滚
			} else {
				// 开始搞图片了
				try {
					if (thumbnail.getImage() != null) {
						try {
							// 加入图片
							addProductImg(product, thumbnail);
						} catch (Exception e) {// 加入图片的catch
							throw new ProductOperationException("addShopImg error: " + e.getMessage());
						}

						// 更新图片的地址
						effectedNum = productDao.updateProduct(product);
						if (effectedNum <= 0) {
							throw new ProductOperationException("创建图片地址失败");
						}
					}

				} catch (Exception e) {// 总图片的catch
					throw new ProductOperationException("addShopImg error: " + e.getMessage());
				}
			}
		} // 总catch
		catch (Exception e) {
			throw new ProductOperationException("insertAuthMap error: " + e.getMessage());

		}

		return new ProductExecution(ProductStateEnum.CHECK, product);
	}

	private void addProductImg(Product product, ImageHolder thumbnail) {
		// TODO Auto-generated method stub
		// 获取图片的相对值路径
		String dest = PathUtil.getProductImagePath(product.getProductId());
		String productImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setProductImg(productImgAddr);
	}

	// 用户的自动添加
	/**
	 * 前提是，product的user对象不为空，否则这个函数没有效果
	 * 
	 * @param product
	 * @return
	 */
	private Product addUId(Product product) {
		if (product.getUser() == null) {
			return product;
		}
		if (product.getUser().getId() == null) {
			return product;
		}
		product.setuId(product.getUser().getId());
		return product;

	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {

		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);// 这个方法拿到了起始页的index
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		int count = productDao.queryProductCount(productCondition);
		ProductExecution se = new ProductExecution();
		if (productList != null) {
			se.setProductList(productList);
			se.setCount(count);
		} else {
			se.setState(ProductStateEnum.INNER_ERROR.getState());
		}
		return se;

	}

	@Override
	public String getBase64ByUrl(Product product) throws RuntimeException {
		// TODO Auto-generated method stub
		String imgUrl = product.getProductImg();
		String preString = "D:\\cmbCard\\image\\";
		String url = preString + imgUrl;
		String base64 = ImageUtil.GetImageStrFromPath(url);
		return base64;

	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public int ProductStockModify(Integer productId, Order order) throws RuntimeException {
		// TODO Auto-generated method stub
		if (productId == null || order == null) {
			return 0;
		}
		if (order.getOrderId() == null || order.getNumber() == null) {
			return 0;
		}
		// 拿出产品
		Product oldproduct = productDao.selectByPrimaryKey(productId);
		// 拿出原来的库存
		int oldStock = oldproduct.getStock();
		int newStock = oldStock - order.getNumber();
		if (newStock < 0) {
			throw new ProductOperationException("库存不足");
		}

		Product newproduct = new Product();
		newproduct.setStock(newStock);
		newproduct.setProductId(productId);

		int x = productDao.updateProductStock(newproduct);

		if (x < 0) {
			throw new ProductOperationException("库存修改失败");
		}
		return 1;

	}

	@Override
	public void UserCost(User user, Product product) throws RuntimeException {
		if (user.getCost() == null || user.getCost() <= 0) {
			return;
		}
		Float price = product.getPrice() * user.getCost();
		product.setPrice(price);

	}

	@Override
	public void UserCost(User user, List<Product> productList) throws RuntimeException {
		// TODO Auto-generated method stub
		if (productList == null)
			return;
		for (int i = 0; i < productList.size(); i++) {
			UserCost(user, productList.get(i));
		}

	}

	@Override
	public List<Product> selectAllProduct() {
		// TODO Auto-generated method stub
		return null;
	}

}
