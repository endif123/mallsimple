/**
 * 
 *//**
  * 
  */
 $(function(){
 	
 	
 	//alert("请填写订单信息");
 	$.toast('欢迎查看订单信息');
   
 	var orderId = getQueryString("orderId");
 	var productInfoUrl = '/mall/shopping/orderdetailbyid?orderId=' + orderId;//请求订单信息
 	
 	
 	getInfo(orderId);
 	
 	
	

 	function getInfo(productId) {
		$.getJSON(productInfoUrl, function(data) {
			if (data.success) {
				var order = data.order;
				var product = order.product;
				
				//产品信息
				$('#productName').val(product.productName);
				$('#price').val(product.price);
				
				//$('#stock').val(product.stock);
				$('#productUser').val(product.user.name);
				$('#productImg').val(product.productImg);
				
				
				//订单信息
				$('#totalPrice').val(order.orderPrice);
				$('#buyNumber').val(order.number);
				$('#orderDate').val(formatDate(order.orderCreateDate));
				$('#receiverName').val(order.orderMessage);
				$('#mobile').val(order.mobile);
				$('#address').val(order.address);
				
				//发货的状态
				var sendState = "未发货";
				if(order.orderPayDate != null){
					$('#sendOrNot').val(formatDate(order.orderPayDate));
				}else{
					$('#sendOrNot').val(sendState);
				}
				
				
				//不可修改
				
				$('#productName').attr('disabled','disabled');
				$('#price').attr('disabled','disabled');
				
				$('#stock').attr('disabled','disabled');
				$('#productUser').attr('disabled','disabled');
				$('#totalPrice').attr('disabled','disabled');
				
				$('#totalPrice').attr('disabled','disabled');
				$('#buyNumber').attr('disabled','disabled');
				$('#orderDate').attr('disabled','disabled');
				$('#receiverName').attr('disabled','disabled');
				$('#mobile').attr('disabled','disabled');
				$('#address').attr('disabled','disabled');
				$('#sendOrNot').attr('disabled','disabled');
				
				
				var srcString = "data:image/jpeg;base64," + product.productImg;
				document.getElementById("productImg").src=srcString;
			}
			else{
				$.toast('失败'+data.errMsg);
			}
		});
		
	}
 	
 	$('#submit').click(
 			function () {
 				
 				window.location.href="/mall/shopping/orderlist"
 				
     });
 	
 	
 	
 	
 	$.init();
 	
 })

