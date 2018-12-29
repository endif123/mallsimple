/**
 * 
 */

 $(function(){
	 
	 //alert("欢迎管理商品");
	 $.toast('欢迎管理商品');
	 getlist();
	 
	 function getlist(e) {
			$.ajax({
				url : "/mall/product/getproductlist",
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
					+ item.productName +'</div><div class="col-20">'
					+ item.price +'</div><div class="col-20">' 
					//+ item.category.categoryName +'</div><div class="col-15">'
					+ item.stock +'</div><div class="col-30">'
					+ goUser(item.productId) + deleteUser(item.productId) +'</div></div>';

			});
			$('.shop-wrap').html(html);
		}
	 
	 function goUser(id) {
			
			return '<a href="/mall/product/modifyproduct?productId='+ id +'" external>修改/</a>';
		
	}

	 function deleteUser(id) {
		
		return '<a href="/mall/product/deleteproductbyid?productId='+ id +'" external>删除  </a>';
	
	 }
	 
	 
	 $.init();
	 
 });