/**
 * 
 */

 $(function(){
	 
	// alert("欢迎查看商品列表");
	 $.toast('欢迎查看商品列表');
	 var categoryId = getQueryString("categoryId");
	 //当前的页数
	 var pageIndex = 1;
	 
	 getlist();
	 
	 function getlist(e) {
			$.ajax({
				url : "/mall/shopping/getproductlistbycategory?categoryId=" + categoryId + "&pageIndex=" + pageIndex,
				type : "get",
				dataType : "json",
				success : function(data) {
					if (data.success) {
						handleList(data.productList);
						//handleUser(data.user);
					}
				}
			});
	}
	 
	 function handleList(data) {
			var html = '';
			data.map(function (item, index) {
				html += '<div class="row row-shop"><div class="col-15">'
					+ item.productId +'</div><div class="col-20">'
					+ item.productName +'</div><div class="col-15">'
					+ item.price.toFixed(2) +'</div><div class="col-15">' 
				//	+ item.category.categoryName +'</div><div class="col-15">'
					+ item.stock +'</div><div class="col-25">'
					+ goUser(item.productId) +'</div></div>';

			});
			$('.shop-wrap').html(html);
		}
	 
	 function goUser(id) {
			
			return '<a href="/mall/shopping/userproductdetail?productId='+ id +'" external>详情      </a>';
		
	}

	
	 $('#nextPage').click(
	 			function () {
	 				pageIndex = pageIndex + 1;
	 				$.ajax({
	 					url : "/mall/shopping/getproductlistbycategory?categoryId=" + categoryId + "&pageIndex=" + pageIndex,
	 					type : "get",
	 					dataType : "json",
	 					success : function(data) {
	 						if (data.success) {
	 							handleList(data.productList);
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
	 				if(pageIndex < 1){
	 					$.toast("已经是第一页了");
	 					return;
	 				}
	 				$.ajax({
	 					url : "/mall/shopping/getproductlistbycategory?categoryId=" + categoryId + "&pageIndex=" + pageIndex,
	 					type : "get",
	 					dataType : "json",
	 					success : function(data) {
	 						if (data.success) {
	 							handleList(data.productList);
	 							//handleUser(data.user);
	 						}else{
	 							$.toast(data.errMsg);
	 						}
	 					}
	 				});
	 						
	     });
	 
	 $.init();
	 
 });