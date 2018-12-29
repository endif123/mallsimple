/**
 * 
 */

 $(function(){
	 
	 alert("欢迎管理商品");
	 var categoryId = getQueryString("categoryId");
	 getlist();
	 
	 function getlist(e) {
			$.ajax({
				url : "/mall/shopping/getproductlistbycategory?categoryId=" + categoryId,
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
					+ item.productId +'</div><div class="col-15">'
					+ item.productName +'</div><div class="col-15">'
					+ item.price +'</div><div class="col-15">' 
					+ item.category.categoryName +'</div><div class="col-15">'
					+ item.stock +'</div><div class="col-15">'
					+ goUser(item.productId) + deleteUser(item.productId) +'</div></div>';

			});
			$('.shop-wrap').html(html);
		}
	 
	 function goUser(id) {
			
			return '<a href="/mall/product/modifyproduct?productId='+ id +'" external>修改商品      </a>';
		
	}

	 function deleteUser(id) {
		
		return '<a href="/mall/product/deleteproductbyid?productId='+ id +'" external>删除商品     </a>';
	
	 }
	 
	 
	 $.init();
	 
 });