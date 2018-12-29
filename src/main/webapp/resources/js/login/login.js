/**
 * 
 */
$(function(){
	var submitUrl = '/mall/login/loginsubmit';//提交登录
	
	//alert("欢迎登录");
	$.toast('欢迎登录');
	$('#submit').click(
			function () {
				var user = {};
				var id = $('#id').val();
				console.log(id);
				user.id = $('#id').val();
				user.password = $('#password').val();
				//$.openPanel('#panel-left-demo');
				//一个新的数据，封装成了JSON格式
				//var formData = new FormData();
				var formData = new FormData();
				formData.append('userStr', JSON.stringify(user));
				//formData.append('userStr', JSON.stringify(user));
				
				//验证码
				var verifyCodeActual = $('#j_captcha').val();
				//如果是空的话，直接结束
				if(!verifyCodeActual){
					$.toast('请输入验证码！');
					return;
				}
				//不为空封装
				formData.append('verifyCodeActual',verifyCodeActual);
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
							$.toast('成功');
							var type = data.user.type;
							alert("你好，"+ type);
							if(type == "管理员"){
								//window.location.href="http://localhost:8888/mall/user/userlist"
								window.location.href="/mall/user/userlist"
							}else if(type == "商家"){
								window.location.href="/mall/product/myproduct"
							}else if(type == "用户"){
								window.location.href="/mall/shopping/userchoosecategory"
							}else{// 后台异常处理
								$.toast('登录失败，用户信息有误，请重新登录');
							}
								
							
						} else {// 后台异常处理
							$.toast('登录失败，'+data.errMsg);
						}
						$('#captcha_img').click();
					}
					
					
				});
    });
	
	$.init();
	
})