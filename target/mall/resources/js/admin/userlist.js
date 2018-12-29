/**
 * 
 */

 $(function(){
	 
	 //alert("欢迎管理用户");
	 $.toast('欢迎管理用户');
	 getlist();
	 
	 function getlist(e) {
			$.ajax({
				url : "/mall/user/list",
				type : "get",
				dataType : "json",
				success : function(data) {
					if (data.success) {
						handleList(data.userList);
						//handleUser(data.user);
					}else {// 后台异常处理
						$.toast('失败'+data.errMsg);
					}
				}
			});
	}
	 
	 function handleList(data) {
			var html = '';
			data.map(function (item, index) {
				html += '<div class="row row-shop"><div class="col-15">'
					+ item.id +'</div><div class="col-20">'
					+ item.name +'</div><div class="col-20">'
					+ item.type +'</div><div class="col-15">' 
					+ item.cost +'</div><div class="col-30">'
					+ goUser(item.id) + "/"+ deleteUser(item.id) +'</div></div>';

			});
			$('.shop-wrap').html(html);
		}
	 
	 function goUser(id) {
		 if(id!=1)
			{
				return '<a href="/mall/user/getuserpage?userId='+ id +'" external>进入</a>';
			}else{
				return '<a href="/mall/user/getuserpage?userId='+ id +'" external></a>';
			}
		}
	 
	 function deleteUser(id) {
		 if(id!=1){
			return '<a href="/mall/user/deleteuserbyid?userId='+ id +'" external>删除</a>';
		 }else{
			 return '<a href="/mall/user/deleteuserbyid?userId='+ id +'" external></a>';
		 }
		
	}
	 
	 
	 $.init();
	 
 });