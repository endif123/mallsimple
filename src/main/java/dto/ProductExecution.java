package dto;

import java.util.List;

import entity.Product;
import enums.ProductStateEnum;

public class ProductExecution {

	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 产品数量
	private int count;

	// 操作的product（增删改的时候用）
	private Product product;

	// 操作的product列表（增删改的时候用）
	private List<Product> productList;

	// private Integer count;

	public ProductExecution() {
		super();
	}

	// 操作失败的构造器，只返回状态就OK
	public ProductExecution(ProductStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 成功的构造器，需要返回状态以及对象
	public ProductExecution(ProductStateEnum stateEnum, Product product) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.product = product;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

}
