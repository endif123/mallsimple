/**
 * 
 */
/**
 * 
 */
$(function(){
	var getCategoryListUrl = '/mall/category/list';//列表
	var submitUrl = '';
	//salert(getCategoryListUrl);
	$.toast('欢迎');
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

 			window.location.href="/mall/product/myproduct";
 			}
 				
 				)
 	
 	$.init();
	
	
})