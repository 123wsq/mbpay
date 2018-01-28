/**
 * 验证码加载
 */
$.fn.extend({
	setGaptcha : function(){
		$(this).css({
			"width" : "50px",
			"height" : "22px",
			"background" : "#FFF url('auth/genCaptcha.do?dt="+(new Date()).valueOf()+"') no-repeat -1px -1px",
			"margin" : "5px 1px 4px 0px",
			"cursor": "pointer"
		})
//		.parent().css({"padding-right":"30px"})
		.prev("input[type='text']").css({"padding-right":"60px"})
		;
		return this;
	}
});

function login(form){
	var $form = $(form);
	var url = $form.attr('action');
	url += "";
	var data = $form.serializeArray();
//	data = $.extend(data,{"reqType":"ajax"});
	data.push({name:"reqType",value:"ajax"});
	$.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		data:data,
		success: function(result){	
			if(result.rspcod == 200){
				window.location=server_base_url + result.redirect;//跳到主页
			}else{
				$(".captcha").setGaptcha();
				$(".login_msg").html(result.rspmsg);
			}
		},
		error:function(XMLHttpRequest, textStatus){
			alert("网络异常代码："+XMLHttpRequest.status+",异常描述："+textStatus);
		}
	});
	
	return false;
}

$(document).ready(function() {
	$(".captcha").on("click",function(){
		$(this).setGaptcha();
	}).setGaptcha();

});