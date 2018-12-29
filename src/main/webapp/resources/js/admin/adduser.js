/**
 * 
 *//**
  * 
  */
 $(function(){
 	var submitUrl = '/mall/user/adduser';//提交登录
 	
 	//alert("欢迎登录");
 	
 	$.toast('欢迎修改用户');
 	$('#submit').click(
 			function () {
 				var user = {};
 				
 				user.name = IsNullReturnNull($('#name').val());
 				user.password = IsNullReturnNull($('#password').val());
 				user.type = IsNullReturnNull($('#type').val());
 				user.cost = IsNullReturnNull($('#cost').val());
 				
 				
 				//$.openPanel('#panel-left-demo');
 				//一个新的数据，封装成了JSON格式
 				//var formData = new FormData();
 				var formData = new FormData();
 				formData.append('userStr', JSON.stringify(user));
 				//formData.append('userStr', JSON.stringify(user));
 				
 				//验证码
 				//var verifyCodeActual = $('#j_captcha').val();
 				//如果是空的话，直接结束
// 				if(!verifyCodeActual){
// 					$.toast('请输入验证码！');
// 					return;
// 				}
 				//不为空封装
 				//formData.append('verifyCodeActual',verifyCodeActual);
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
 							window.location.href="/mall/user/userlist"
 						} else {// 后台异常处理
 							$.toast('失败'+data.errMsg);
 						}
 						$('#captcha_img').click();
 						
 					}
 					
 					
 				});
     });
 	
 	$("#type").picker({
 		toolbarTemplate: '<header class="bar bar-nav">\
 			<button class="button button-link pull-left"></button>\
 			<button class="button button-link pull-right close-picker">确定</button>\
 			<h1 class="title">标题</h1>\
 			</header>',
 			cols: [
 				{
 					textAlign: 'center',
 					values: ['用户','商家','管理员']
 					
 				}
 				]
 	});
 	
 	$.init();
 	
 })

