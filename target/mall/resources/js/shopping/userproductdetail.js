/**
 * 
 *//**
  * 
  */
 $(function(){
 	//var submitUrl = '/mall/product/modifyproductdetail';//提交登录
 	
 	
 	var productId = getQueryString("productId");
 	var productInfoUrl = '/mall/product/getproductdetailbyid?productId=' + productId;//提交登录
 	$.toast('欢迎查看商品详情！');
 	//alert("欢迎查看商品详情！");
 	getInfo(productId);
 	
 	
 	function getInfo(productId) {
		$.getJSON(productInfoUrl, function(data) {
			if (data.success) {
				var product = data.product;
				$('#productName').val(product.productName);
				$('#price').val(product.price.toFixed(2));
				$('#description').val(product.description);
				$('#stock').val(product.stock);
				$('#productUser').val(product.user.name);
				$('#productImg').val(product.productImg);
				var Category = '<option data-id="'
						+ product.category.categoryId + '" selected>'
						+ product.category.categoryName + '</option>';
				
				$('#category').html(Category);
				$('#category').attr('disabled','disabled');
				
				$('#productName').attr('disabled','disabled');
				$('#price').attr('disabled','disabled');
				$('#description').attr('disabled','disabled');
				$('#stock').attr('disabled','disabled');
				$('#productUser').attr('disabled','disabled');
				
				
				var srcString = "data:image/jpeg;base64," + data.base64;
				document.getElementById("productImg").src=srcString;
			}
		});
	}
	
 	
 	
 	$('#submit').click(
 			function () {
 				var url = "/mall/shopping/orderpage?productId=" + productId; 
 				window.location.href=url;
 				
 				
 				
     });
 	
 	$.init();
 	
 })

