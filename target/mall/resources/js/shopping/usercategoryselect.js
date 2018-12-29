/**
 * 
 */
/**
 * 
 */
$(function(){
	var getCategoryListUrl = '/mall/category/list';//列表
	var submitUrl = '';
	//alert(getCategoryListUrl);
	getCategoryList();
	function getCategoryList(){
		$.getJSON('/mall/category/list',function(data){
			if(data.success){
				var tempCategoryHtml = '';
				data.categoryList.map(function(item,index){
					tempCategoryHtml += '<option data-id="' + item.categoryId + '">' + item.categoryName + '</option>';
				});
				$('#category').html(tempCategoryHtml);
			}
		});
	}
	
	$('#submit').click(
 			function () {
 				var category = {};
 				
 				category.categoryId =  $('#category').find('option').not(function() {
						return !this.selected;
						}).data('id')
				category.categoryName =  $('#category').find('option').not(function() {
						return !this.selected;
						}).val()
				var url = "/mall/shopping/userproductlist?categoryId=" + category.categoryId;

 			window.location.href=url;
 			}
 				
 				)
 	
 	$.init();
	
	
})