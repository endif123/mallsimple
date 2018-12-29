/**
 * 
 */

 $(function(){
	 
	 //salert("欢迎查看订单列表");
	 $.toast('欢迎查看订单列表');
	   
	 var categoryId = getQueryString("categoryId");
	 //当前的页数
	 var pageIndex = 1;
	 //最大的页数
	 var maxPage = 5;
	 
	 
	 getlist();
	 
	 function getlist(e) {
			$.ajax({
				url : "/mall/shopping/orderlistdetail?pageIndex=" + pageIndex,
				type : "get",
				dataType : "json",
				success : function(data) {
					if (data.success) {
						handleList(data.orderList);
						//handleUser(data.user);
					}
				}
			});
	}
	 
	 function handleList(data) {
			var html = '';
			data.map(function (item, index) {
				html += '<div class="row row-shop"><div class="col-15">'
					+ item.orderId +'</div><div class="col-20">'
					+ item.product.productName +'</div><div class="col-20">'
					//+ item.orderMessage +'</div><div class="col-15">' 
					+ item.orderPrice +'</div><div class="col-30">'
					+ formatDate(item.orderCreateDate) +'</div><div class="col-15">'
					+ goUser(item.orderId) +'</div></div>';
				//$.toast(formatDate(item.orderCreateDate));

			});
			$('.shop-wrap').html(html);
		}
	 
	 function goUser(id) {
			
			return '<a href="/mall/shopping/orderdetail?orderId='+ id +'" external>详情      </a>';
		
	}

	
	 $('#nextPage').click(
	 			function () {
	 				if(maxPage == 5){
	 					pageIndex = pageIndex + 1;
	 				}else{
	 					$.toast("已经是最后一页了");
	 					return;
	 				}
	 				
	 				
	 				$.ajax({
	 					url : "/mall/shopping/orderlistdetail?pageIndex=" + pageIndex,
	 					type : "get",
	 					dataType : "json",
	 					success : function(data) {
	 						if (data.success) {
	 							if(data.orderList.length<5){
	 								maxPage = 0;
	 							}
	 							handleList(data.orderList);
	 							//handleUser(data.user);
	 						}else{
	 							$.toast(data.errMsg);
	 						}
	 					}
	 				});
	 						
	     });
	 
	 $('#prePage').click(
	 			function () {
	 				pageIndex = pageIndex - 1;
	 				maxPage = 5;
	 				if(pageIndex < 1){
	 					$.toast("已经是第一页了");
	 					return;
	 				}
	 				$.ajax({
	 					url : "/mall/shopping/orderlistdetail?pageIndex=" + pageIndex,
	 					type : "get",
	 					dataType : "json",
	 					success : function(data) {
	 						if (data.success) {
	 							handleList(data.orderList);
	 							//handleUser(data.user);
	 						}else{
	 							$.toast(data.errMsg);
	 						}
	 					}
	 				});
	 						
	     });
	 
	 $.init();
	 
 });