/**
 * 
 *//**
  * 
  */
 $(function(){
 	
 	
 	//alert("请填写订单信息");
 	 $.toast('请填写订单信息');
 	
    var submitUrl = '/mall/shopping/submitorder';//提交登录
 	
 	
 	var productId = getQueryString("productId");
 	var productInfoUrl = '/mall/product/getproductdetailbyid?productId=' + productId;//提交登录
 	
 	
 	var stock = getInfo(productId);
 	var list;
 	
//	alert("请填写订单信息");
// 	values: function() {
//			var stock =  IsNullReturnNull($('#stock').val());
//			var number = parseInt(stock);
//			var list=new Array();
//			for(var i = 1; i <= number; i++){
//				list[i] = i;
//			}
//			return list;
//		}
 	
 	
	//alert("请填写订单信息");
 	
 	function getInfo(productId) {
		$.getJSON(productInfoUrl, function(data) {
			if (data.success) {
				var product = data.product;
				$('#productName').val(product.productName);
				$('#price').val(product.price.toFixed(2));
				
				$('#stock').val(product.stock);
				$('#productUser').val(product.user.name);
				$('#productImg').val(product.productImg);
				$('#totalPrice').val(product.price.toFixed(2));
				$('#buyNumber').val(1);
				
				$('#productName').attr('disabled','disabled');
				$('#price').attr('disabled','disabled');
				
				$('#stock').attr('disabled','disabled');
				$('#productUser').attr('disabled','disabled');
				$('#totalPrice').attr('disabled','disabled');
				
				
				var srcString = "data:image/jpeg;base64," + data.base64;
				document.getElementById("productImg").src=srcString;
			}
		});
		
	}
 	
 	$('#submit').click(
 			function () {
 				
 				
 				
 				var order = {};
 				
 				order.address = IsNullReturnNull($('#address').val());
 				order.mobile = IsNullReturnNull($('#mobile').val());
 				order.orderMessage = IsNullReturnNull($('#receiverName').val());
 				order.number = IsNullReturnNull($('#buyNumber').val());
 				order.orderPrice = IsNullReturnNull($('#totalPrice').val());
 				order.pId = productId;
 				
 				
 				
 				var formData = new FormData();
 				formData.append('orderStr', JSON.stringify(order));
 				/**
 				 * 
 				 */
 				var stock =  IsNullReturnNull($('#stock').val());
 				var number = parseInt(stock);
 				var list=new Array();
 				for(var i = 1; i <= number; i++){
 					list[i-1] = i;
 				}
 				
 				
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
 							window.location.href="/mall/shopping/orderlist"
 						} else {// 后台异常处理
 							$.toast('失败'+data.errMsg);
 						}
 						
 						
 					}
 					
 					
 				});
     });
 	
 	
 	$('#buyNumber').change(
 			function () {
 				
 				var buynumber = $('#buyNumber').val();
 				//数量
 				var number = parseInt(buynumber);
 				$.toast(buynumber);
 				//单价
 				singlePrice = IsNullReturnNull($('#price').val());
 				//总价
 				totalPrice = singlePrice*number;
 				
 				$('#totalPrice').val(totalPrice.toFixed(2));
 				
 				
 				//库存
 				stock =  IsNullReturnNull($('#stock').val());
 				if(number>stock ||number <0){
 					$('#buyNumber').val(stock);
 					$.toast("不能超过最大库存哦");
 				}
 			}
 	)
 	
 	$("#buyNumber").picker({
 		toolbarTemplate: '<header class="bar bar-nav">\
 			<button class="button button-link pull-left"></button>\
 			<button class="button button-link pull-right close-picker">确定</button>\
 			<h1 class="title">标题</h1>\
 			</header>',
 			cols: [
 				{
 					textAlign: 'center',
 					values: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]
 					
 				}
 				]
 	});
 	
 	$.init();
 	
 })

