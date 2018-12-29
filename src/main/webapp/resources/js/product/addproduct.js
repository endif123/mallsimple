/**
 * 
 *//**
  * 
  */
 $(function(){
 	var submitUrl = '/mall/product/addproductdetail';//提交登录
 	
 	//alert("欢迎添加商品！");
 	 $.toast('欢迎添加商品！');
 	
 	getCategoryList();
	
 	
 	
 	$('#submit').click(
 			function () {
 				var product = {};
 				
 				product.productName = IsNullReturnNull($('#productName').val());
 				product.price = IsNullReturnNull($('#price').val());
 				product.description = IsNullReturnNull($('#description').val());
 				product.stock = IsNullReturnNull($('#stock').val());
 				product.stock = IsNullReturnNull($('#stock').val());
 				
 				product.cateId = $('#category').find('option').not(function() {
 							return !this.selected;
 						}).data('id')
 				
 				var productImg = $("#productImg")[0].files[0];
 				
 				
 				//一个新的数据，封装成了JSON格式
 				
 				var formData = new FormData();
 				formData.append('productStr', JSON.stringify(product));
 				formData.append('productImg', productImg);
 				
 				
 				$.ajax({
 					url: submitUrl,
 					type : 'POST',
 					
 					//data : formData,
 					contentType : false,
 					processData : false,
 					cache : false,
 					data: formData,
 					
 					success : function(data) { // 请求成功后处理函数。
 						
 						if (data.success == true) {
 							$.toast('操作成功');
 							window.location.href="/mall/product/myproduct"
 						} else {// 后台异常处理
 							$.toast('失败'+data.errMsg);
 						}
 						//$('#captcha_img').click();
 						
 					}
 					
 					
 				});
     });
 	
 	$.init();
 	
 })

