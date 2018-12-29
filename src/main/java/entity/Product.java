package entity;

public class Product {
	  private Integer productId;

	    private String productName;

	    private Float price;

	    private String description;

	    private Integer stock;

	    private Integer uId;

	    private Integer cateId;
	    
	    private String productImg;
	    
	    public String getProductImg() {
			return productImg;
		}

		public void setProductImg(String productImg) {
			this.productImg = productImg;
		}

		private User user;
	    
	    private Category category;
	    
	    public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Category getCategory() {
			return category;
		}

		public void setCategory(Category category) {
			this.category = category;
		}

		

	    public Integer getProductId() {
	        return productId;
	    }

	    public void setProductId(Integer productId) {
	        this.productId = productId;
	    }

	    public String getProductName() {
	        return productName;
	    }

	    public void setProductName(String productName) {
	        this.productName = productName == null ? null : productName.trim();
	    }

	    public Float getPrice() {
	        return price;
	    }

	    public void setPrice(Float price) {
	        this.price = price;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description == null ? null : description.trim();
	    }

	    public Integer getStock() {
	        return stock;
	    }

	    public void setStock(Integer stock) {
	        this.stock = stock;
	    }

	    public Integer getuId() {
	        return uId;
	    }

	    public void setuId(Integer uId) {
	        this.uId = uId;
	    }

	    public Integer getCateId() {
	        return cateId;
	    }

	    public void setCateId(Integer cateId) {
	        this.cateId = cateId;
	    }

}
