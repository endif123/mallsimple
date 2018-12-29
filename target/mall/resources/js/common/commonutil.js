/**
 * 
 */
Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}


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

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return decodeURIComponent(r[2]);
	}
	return '';
}
function changeVerifyCode(img) {
	img.src = "/mall/Kaptcha?" + Math.floor(Math.random() * 100);
	//img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}

function IsNullReturnNull(data){
	if(data.length<1){
		return null;
	}else{
		return data;
	}
}

function handleUser(data) {
	$('#user-name').text(data.name);
}

function getQueryString(name) {
	    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	    var r = window.location.search.substr(1).match(reg);
	    if(r!=null)return  decodeURI(r[2]); return null;
	}

function formatDate(timestamp) {
	dates = new Date(parseInt(timestamp)).toLocaleDateString();
    dates = dates.split("/");
    if(dates.length == 3) {
        if(dates[1].length == 1) {
            dates[1] = "0" + dates[1];
        }
        if (dates[2].length == 1) {
            dates[2] = "0" + dates[2];
        }
        date = dates.join("-");
        return date;
    } else {
        return null;
    }
}
$("#loginoutplace").load("../loginout.html");

//$('#loginout').click(
//			function () {
//				submitUrl = '/mall/login/loginout'
//				$.ajax({
//					url: submitUrl,
//					type : 'GET',
//					
//					
//					
//					success : function(data) { // 请求成功后处理函数。
//						
//						if (data.success == true) {
//							$.toast('操作成功');
//							window.location.href="http://localhost:8888/mall/shopping/userlogin"
//						} else {// 后台异常处理
//							$.toast('失败'+data.errMsg);
//						}
//						//$('#captcha_img').click();
//						
//					}
//					
//					
//				});
// });

	