$.extend({
	/**
	 * @ 得到url所传参数的值 @ key 变量名 @ frame 获取某帧
	 */
	getURLParameter : function(key, frame) {
		var param = (frame || window).location.search, reg = new RegExp(
				"[&\?]+" + key + "=([^&]*)"), str = "";
		if (reg.test(param))
			str = RegExp.$1;
		return str;
	},
	/**
	 * @ 获取Cookie的值 @ cookieName cookie变量名
	 */
	getCookie : function(cookieName) {
		var getC = document.cookie, reg = new RegExp(cookieName + "=([^;]*)");
		var val = "";
		if (reg.test(getC)) {
			val = RegExp.$1;
		}
		return val;
	},
	/**
	 * @ 计算毫秒，返回毫秒数 @ temer 要计算的字符串
	 */
	getDates : function(temer) {
		// var timeSize = [ "s", "m", "h", "D", "W", "M", "Y" ];
		// var tl = temer.length;
		var str = {};
		var s = 24 * 60 * 60;
		var sum = 0;
		var arra = temer.match(/\d+\w/g);
		if (arra == null)
			return false;
		for (var i = 0, l = arra.length; i < l; i++) {
			new RegExp("^(\\d+)([a-z]+)$", "i").test(arra[i]);
			str[RegExp.$2] = RegExp.$1;
		}
		if (str.s)
			sum = +str.s;
		if (str.m)
			sum += +str.m * 60;
		if (str.h)
			sum += +str.h * 60 * 60;
		if (str.D)
			sum += +str.D * s;
		if (str.W)
			sum += +str.W * s * 7;
		if (str.M)
			sum += +str.M * s * 30;
		if (str.Y)
			sum += +str.Y * s * 365;
		return sum * 1000;
	},
	/**
	 * @ 设置Cookie的方法 @ cookieName Cookie的名字 @ cookieInfo 参数是Cookie内容和要设置的时间
	 */
	setCookie : function(cookieName, cookieInfo) {
		var str = [];
		// 判断参数类型
		if (typeof cookieInfo == "string") {
			str = cookieInfo;
		} else {
			if (typeof cookieInfo.values == "object") {
				for ( var o in cookieInfo.values) {
					str.push(o + "=" + cookieInfo.values[o] + "&");
				}
				str = str.join("").slice(0, -1);
			} else {
				str = cookieInfo.values;
			}
		}
		// 判断时间的存在
		if (cookieInfo.temer) {
			var d = $.getDates(cookieInfo.temer);
			document.cookie = cookieName + "=" + str + ";expires="
					+ new Date(new Date().getTime() + d).toGMTString();
		} else
			document.cookie = cookieName + "=" + str;
	},
	/**
	 * @ 删除Cookie的方法 @ cookieName Cookie的名字
	 */
	delCookie : function(cookieName) {
		var getC = document.cookie, reg = new RegExp(cookieName + "=[^;]?");
		if (reg.test(getC))
			document.cookie = cookieName + "=;expires="
					+ new Date(-1).toGMTString();
	},
//	ajax:function(option){
//		$.ajax(option);
//	},
	getHostUrl : function() {
		return window.location.protocol + "//" + window.location.host;
	},
	getHostName : function(){
		var pathname = window.location.pathname;
		return pathname = pathname.substring(0, pathname.indexOf("/", 1) + 1);
	},
	getBaseHerf : function() {
		return $.getHostUrl() + $.getHostName();
	},
	getNowHref : function() {
		return $.getHostUrl() + window.location.pathname;
	},
	getLocalDataUrl : function() {
		return window.location.href.replace(/^(\#\!)?\#/, '');
	},
	baseHref : function() {
		$("a[href^='#']").baseHref();
		return this;
	},
	toLoginPage:function(){
		logout();
	},
	toUrl : function(url){
		location.href = url;
	},
	doAjaxRespons : function(response, jqXHR, isSuccess,arguments){//处理ajax返回
		try {
			if(isSuccess){//成功
				var data = response['data'];
//				var state = response.state;
//				var responseDate = jqXHR['responseJSON'];
//				if('parsererror' === state){//json解析失败
//					responseDate = jqXHR['responseText'];
//					if(alertMsg){
//						alertMsg.error({"title":"数据解析失败","msg":responseDate});
//					}else{
//						alert("数据解析失败:" + responseDate);
//					}
//					return;
//				}
				
				//如果是json格式的数据才操作
				if(typeof data !== 'object'){
					data = jQuery.parseJSON( data );
				}
				
				//如果是json格式的数据才操作
				if(typeof data === 'object'){
					var tp = data['tp'];
					var redirect = data['redirect'];
					var msg = data['msg'];
					if(tp && redirect){
						if(msg){
							alert(msg);
						}else{
							alert("请重新登录");
						}
						$.toUrl(redirect);
						response['data'] = "";
					}
				}
			}else{//失败
				
			}
		} catch (e) {
			
		}
	},	
	loaded : function() {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax(
				'loadScripts',
				scripts,
				function() {
					// inline scripts related to this page
					jQuery(function($) {
						$('#loading-btn').on(ace.click_event, function() {
							var btn = $(this);
							btn.button('loading');
							setTimeout(function() {
								btn.button('reset');
							}, 2000);
						});

						$('#id-button-borders').attr('checked', 'checked').on(
								'click',
								function() {
									$('#default-buttons .btn').toggleClass(
											'no-border');
								});
					});
				});
		return this;
	}
});

/**
 * 每次都会打开一个遮罩层，所以多个dialog打开并关闭一个时，遮罩层不会全部消失
 */
var mask = {
	idNum : 0,
	show : function() {
		this.idNum = this.idNum + 1;
		var maskId = 'mask_' + this.idNum;
		// if(!$(".mask") || $(".mask").length <= 0){
		var html = '<div id="' + maskId + '" class="mask"></div>';
		$("body").append(html);
		// }
		$("body").css("overflow", "hidden");
		var $mask = $("#" + maskId);
		//遮罩层每次添加一个 z-index 都在上一个的基础上加上100
		$mask.css("z-index",1000 + ($(".mask").length * 100));
//		alert($(".mask").last().css('z-index'));
//		alert($mask.css("z-index"));
		$mask.show();// 显示
		return $mask;
	},
	showWait : function() {
		this.idNum = this.idNum + 1;
		var maskId = 'mask_' + this.idNum;
		// if(!$(".mask") || $(".mask").length <= 0){
		var html = '<div id="' + maskId + '" class="mask"><div><i sytle="margin-left: 500px !important;margin-top: 500px !important" class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i><b>数据加载中...<b/></div></div>';
		$("body").append(html);
		// }
		$("body").css("overflow", "hidden");
		var $mask = $("#" + maskId);
		//遮罩层每次添加一个 z-index 都在上一个的基础上加上100
		$mask.css("z-index",1000 + ($(".mask").length * 100));
//		alert($(".mask").last().css('z-index'));
//		alert($mask.css("z-index"));
		$mask.show();// 显示
		return $mask;
	},
	hide : function(obj) {
		this.close(obj);
	},
	close : function(obj) {
		if (obj) {// 如果是关闭指定mask
			$(obj).remove();
		} else {
			$(".mask").last().remove();// 没指定则关闭最后一个
		}
		var $maskAll = $(".mask");
		if ($maskAll.length == 0) {// 所有遮罩层都关闭了才放出body的滚动条
			$("body").css("overflow", "");
		}
	},
	closeWait : function() {
		var obj = $(".mask").last();
		if (obj) {// 如果是关闭指定mask
			$(obj).remove();
		} else {
			$(".mask").last().remove();// 没指定则关闭最后一个
		}
		var $maskAll = $(".mask");
		if ($maskAll.length == 0) {// 所有遮罩层都关闭了才放出body的滚动条
			$("body").css("overflow", "");
		}
	},
	closeAll : function() {
		$(".mask").remove();
		$("body").css("overflow", "");
	},
	last:function(){
		return $(".mask").last();
	},
	zIndex : function(obj,zIdx) {
		if (!obj) {
			obj = $(".mask").last();
		}
		if(TD.isNotEmpty(zIdx)){
			$(obj).css("z-index",zIdx);
			return zIdx;
		}
		return $(obj).css("z-index") || '1100';
	},
	onTopOf:function($mask,$obj){
		if($obj){
			if(!$mask){
				$mask = mask.last();
			}
			var zIdx = parseInt(mask.zIndex($mask));
			$obj = $($obj).css("z-index",zIdx+1);
		}
	}
};

$.fn.extend({
	baseHref : function() {// a标签地址前加上base href
		// $this = $(this);
		var base_href = $.getNowHref();
		return this.attr("href", function() {
			var href = $(this).attr("href");
			if (href && href.indexOf("#") == 0) {
				return base_href + href;
			} else {
				return href;
			}
		});

		// return $this;
	}
});

/**
 * 顶部弹框
 */
// var alertMsg = {
// correct : function (option){//成功提示框
// if(option != undefined){
// if(typeof option ==="string"){
// option = {msg:option};
// }
// }else{
// option = {};
// }
// if(!option['title']){
// option['title'] = "成功";
// }
// if(!option['msg']){
// option['msg'] = "操作成功!";
// }
// option['level'] = 'correct';
// return this.show(option);
// },
// error : function(option){//错误提示框
// if(option != undefined){
// if(typeof option ==="string"){
// option = {msg:option};
// }
// }else{
// option = {};
// }
// if(!option['title']){
// option['title'] = "失败";
// }
// if(!option['msg']){
// option['msg'] = "操作失败!";
// }
// option['level'] = 'error';
// return this.show(option);
// },
// info:function (option){//信息提示框
// if(option != undefined){
// if(typeof option ==="string"){
// option = {msg:option};
// }
// }else{
// option = {};
// }
// if(!option['title']){
// option['title'] = "提示";
// }
// if(!option['msg']){
// option['msg'] = "操作提示！";
// }
// option['level'] = 'info';
// return this.show(option);
// },
// warn : function(option){//警告提示框
// if(option != undefined){
// if(typeof option ==="string"){
// option = {msg:option};
// }
// }else{
// option = {};
// }
// if(!option['title']){
// option['title'] = "警告";
// }
// if(!option['msg']){
// option['msg'] = "操作警告！";
// }
// option['level'] = 'warn';
// return this.show(option);
// },
// confirm:function(option){
// var op={level:'confirm',okCall:null,cancelCall:null};
// $.extend(op,option);
// mask.show();
// return this.show(op);
// },
// getObj:function(){
// return $('#alertMsgBox');
// },
// show:function(op){
// $target = this.getObj();
// this.destroy($target);
// this.init(this.create(op));
//		
// $target = this.getObj();
// $target.css({top:$target.height()*-1});
// $target.show();
// this.getObj().animate({top: 0}, 350);
// return this;
// },
// close:function(){
// $this = this;
// $target = this.getObj();
// var height = $target.height();
// $target.animate({top: (height * -1) - 10 }, 250);//向上隐藏动画效果
// mask.hide();
// return this;
// },
// callBack:function(v,fn){
// this.close();
// if(typeof fn === 'function'){
// fn.call();
// }
// },
// create:function(option){
// var isConfirm = 0;
// if(option['level'] === 'confirm'){
// option.level = 'info';
// isConfirm = 1;
// }
// var html = '<div id="alertMsgBox" class="alertMsgBox theme">';
// html += ' <div class="alertContent '+(option.level || 'info')+'">';
// html += ' <div class="alertInner">';
// html += ' <h1>' + (option.title || '提示') + '</h1>';
// html += ' <div class="msg">'+(option.msg||'提示!')+'</div>';
// html += ' </div>';
// html += ' </div>';
// html += ' <div class="toolBar theme">';
// html += ' <div class="btn-group pull-right">';
// if(isConfirm === 1){
// html += ' <a class="btn btn-xs theme" rel=""
// onclick="alertMsg.callBack(\'ok\','+option['okCall']+')"
// href="javascript:void(0)"><span>确定</span></a>';
// html += ' <a class="btn btn-xs theme" rel=""
// onclick="alertMsg.callBack(\'cancel\','+option['cancelCall']+')"
// href="javascript:void(0)"><span>取消</span></a>';
// }else{
// html += ' <a class="btn btn-xs theme" rel=""
// onclick="alertMsg.callBack(\'cancel\')"
// href="javascript:void(0)"><span>确定</span></a>';
// }
// html += ' </div>';
// html += ' </div>';
// html += '</div>';
//			
// return html;
// },
// destroy:function(obj){
// $(obj).remove();
// return this;
// },
// init:function(html){
// this.destroy(this.getObj());
//		
// $("body").after(html);
//		
// return this;
// }
// };
/**
 * 右下角弹窗
 */
var alertMsg = {
	num : 1,
	closeTime : 8000,// 自动关闭延时时间(毫秒)
	fadeTime : 1000,// 出入场动画时间(毫秒)
	alert : function(title, msg, level, position) {
		var option = {
			title : title,
			msg : msg,
			position : position || 'right-bottom'
		};
		var $alert = {};
		switch (level) {
		case "correct":// 成功
			$alert = this.correct(option);
			break;
		case "success":// 成功
			$alert = this.success(option);
			break;
		case "error":// 错误
			option['position'] = position || 'center-center';// 错误提示默认居中
			option['mask'] = true;// 错误框默认添加遮罩层
			$alert = this.error(option);
			break;
		case "info":// 普通信息
			$alert = this.info(option);
			break;
		case "warn":// 警告
			option['mask'] = true;// 警告框框默认添加遮罩层
			$alert = this.warn(option);
			break;
		default:// 默认为普通提示
			$alert = this.info(option);
			break;
		}

		return $alert;

	},
	success : function(option) {
		return correct(option);
	},
	correct : function(option) {// 成功提示框
		if (option != undefined) {
			if (typeof option === "string") {
				option = {
					msg : option
				};
			}
		} else {
			option = {};
		}
		if (!option['title']) {
			option['title'] = "成功";
		}
		if (!option['msg']) {
			option['msg'] = "操作成功!";
		}
		option['title'] = '<i class="ace-icon fa fa-check"></i>'
				+ option['title'];
		option['level'] = 'success';
		return this.show(option);
	},
	error : function(option) {// 错误提示框
		if (option != undefined) {
			if (typeof option === "string") {
				option = {
					msg : option
				};
			}
		} else {
			option = {};
		}
		if (!option['title']) {
			option['title'] = "错误";
		}
		if (!option['msg']) {
			option['msg'] = "操作失败!";
		}
		option['title'] = '<i class="ace-icon fa fa-times"></i>'
				+ option['title'];
		option['level'] = 'danger';
		return this.show(option);
	},
	info : function(option) {// 信息提示框
		if (option != undefined) {
			if (typeof option === "string") {
				option = {
					msg : option
				};
			}
		} else {
			option = {};
		}
		if (!option['title']) {
			option['title'] = "提示";
		}
		if (!option['msg']) {
			option['msg'] = "操作提示！";
		}
		option['level'] = 'info';
		return this.show(option);
	},
	warn : function(option) {// 警告提示框
		if (option != undefined) {
			if (typeof option === "string") {
				option = {
					msg : option
				};
			}
		} else {
			option = {};
		}
		if (!option['title']) {
			option['title'] = "警告!";
		}
		if (!option['msg']) {
			option['msg'] = "操作警告！";
		}
		option['level'] = 'warning';
		return this.show(option);
	},
	confirm : function(option, msg, call) {
		var op = {
			level : 'confirm',
			call : null
		};
		// 如果option为string类型，将参数依次作为title、msg、callback处理，否则认为是所有参数的对象
		if (typeof option === 'string') {
			option = {
				title : option,
				msg : msg,
				call : call
			};
		}
		option['mask'] = option['mask'] || true;// 默认打开遮罩层
		option['close'] = 'false';// 不自动关闭
		$.extend(op, option);
		return this.show(op);
	},
	getBox : function() {
		var $box = $('#rightFootMsgBox');
		if ($box.length === 0) {
			$("body")
					.append(
							'<div id="rightFootMsgBox" class="rightFootMsgBox"><div class="tool" ><button type="button" class="close"><i class="ace-icon fa fa-times"></i></button></div></dvi>');
			$box = $('#rightFootMsgBox');
			$box.children(".tool").children(".close").on('click',
					alertMsg.closeAll);
		}
		return $box;
	},
	show : function(op) {
		this.init();
		return this.create(op);
		;
	},
	close : function(obj) {
		alertMsg.init();// 消息全部关闭按钮显示/隐藏
		if(obj){
			$(obj).fadeOut(this.fadeTime, function() {
				alertMsg.destroy(obj);
			});
		}
		return this;
	},
	closeById : function(id) {
		this.close($("#" + id));
	},
	closeAll : function() {
		alertMsg.getBox().fadeOut(this.fadeTime, function() {
			alertMsg.getBox().remove();
		});
	},
	callBack : function(v, fn) {
		if (typeof fn === 'function') {
			fn(v);
		}
	},
	create : function(option) {
		var isConfirm = 0;
		if (option['level'] === 'confirm') {
//			option.level = 'info';
			isConfirm = 1;
		}
		var alertMsgId = option.id;// 支持自定义ID
		if (!alertMsgId) {
			this.num = this.num + 1;
			alertMsgId = "alertMsgId" + this.num;
		}
		var html = '';
		var $alert = {};

		var op_mask = option['mask'] || false;
		var $mask = null;
		if(op_mask){//如果有遮罩层的，都到中间打开
			var class_name = '';
			var alt_level = option['level'];
			switch (alt_level) {
			case "info" :
				alt_level = "confirm";
			case "confirm":
				html = '<div class="alert-btn-box"><button class="btn btn-xs btn-yellow ok" type="button">确定</button><button class="btn btn-xs btn-yellow cancel" type="button" >取消</button></div>';
				class_name = "gritter-warning gritter-center alert-confirm";
				break;
			case "warning":
				html = '<div class="alert-btn-box"><button class="btn btn-xs btn-yellow ok" type="button">确定</button></div>';
				class_name = "gritter-warning gritter-center alert-confirm";
				break;
			case "danger" :
				alt_level = "error";
			case "error" :
				html = '<div class="alert-btn-box"><button class="btn btn-xs btn-pink ok" type="button">确定</button></div>';
				class_name = "gritter-error gritter-center alert-confirm";
				break;
			default:
				return null;
			}
			$.gritter.add({
				title:option.title,
				text:option.msg,
				class_name:class_name,
				sticky:true,//不自动关闭
				bottom:html
			});
				
			$mask = mask.show();//开启遮罩
			var ok = false;
			$alert = $('.alert-confirm');
			mask.onTopOf($mask, $alert);//弹出错误提示框在当前遮罩层之上
			var $closeIcon = $('.gritter-close',$alert).on('click',function(){
				alertMsg.callBack(ok, option.call);
				if($mask){
					mask.hide($mask);
				}
			});
			$('.alert-btn-box > .ok').on('click',function(){
				ok = true;
				$closeIcon.trigger('click');
			});
			$('.alert-btn-box > .cancel').on('click',function(){
				ok = false;
				$closeIcon.trigger('click');
			});
			
			return $alert;
			
		}
		html += '<div class="alert alert-' + (option.level || 'info')
				+ '" id="' + alertMsgId + '">';
		html += '	<button type="button" class="close"><i class="ace-icon fa fa-times"></i></button>';
		html += '	<div class="title"><strong>' + option.title
				+ '</strong></div>';
		html += '	<div class="message">' + option.msg + '</div>';
	// if(isConfirm === 1){
	// html+=' <div class="align-right alert-btn-box"><button class="btn
	// btn-xs" type="button">确定</button><button class="btn btn-xs"
	// type="button">取消</button></div>';
		html += '</div>';
		var $alertBox = this.getBox().append(html);

		$alert = $("#" + alertMsgId);

//		if (isConfirm === 1) {
//			var $btnBox = $alert
//					.append(' <div class="align-right alert-btn-box"><button class="btn btn-xs" type="button">确定</button><button class="btn btn-xs" type="button">取消</button></div>');
//			var height = parseFloat($alert.css("height"));
////			var $btnBoxHeight = parseFloat($('.alert-btn-box > button', $alert).css(
////					"height"));
////			alert($('.alert-btn-box > button:eq(0)', $alert).css(
////			"width"));
//			$alert.css('height', (height + 10) + 'px');
//			$('.alert-btn-box > button', $btnBox).css("margin-right","5px");
//		}
		
		//提示框位置设置
//		var position = option.position;
//		var posLevel  = '';//水平布局
//		var posVertical = '';//垂直布局
//		if(position){
//			//水平、垂直两个布局属性
//			if(position.indexOf("-") != -1){
//				var posArray = position.split("-");
//				posLevel = posArray[0];
//				posVertical = posArray[1];
//			}else{//只有一个布局属性的让两个相等
//				posLevel = position;
//				posVertical = position;
//			}
//			
//			$alert.addClass("position-level-" + posLevel);
//			$alert.addClass("position-vertical-" + posVertical);
//		}
//		$alertBox.addClass("full-screen");
		

		$(".close", $alert).on('click', function() {
			$alert.trigger("close");
		});
		// 渐入显示
		$alert.fadeIn(option.time || this.fadeTime);
		var closeType = option.close || 'auto';
		// 自动关闭
		if (closeType === 'auto' || closeType === 'true') {
			var at_time = option.time || this.closeTime;
			setTimeout(function() {
				$alert.trigger("close");
			}, at_time);
		}
		// }


		$alert.on('close', function() {
			if (op_mask === true || op_mask === 'true') {
				mask.hide($mask);
			}
			alertMsg.close($alert);
		});

		return $alert;
	},
	destroy : function(obj) {
		$(obj).remove();
		return this;
	},
	init : function() {
		var $box = this.getBox();
		// 如果弹出层个数超过5个，显示“关闭所有”按钮
		if ($box.children(".alert").length >= 5) {
			$box.children(".tool").children(".close").show();
		} else {
			$box.children(".tool").children(".close").hide();
		}
		return this;
	}
};


// 对象copy
var msg = alertMsg;
$.extend(msg, alertMsg);
/**
 * 调用ace ajax请求加载完成后执行的函数
 * 
 * @param result
 *            ajax返回结果
 */
function onLoaded(result) {
	// /*给 井号“#”开头的“a”标签加上当前页面路径*/
	$.baseHref().loaded();

	if (typeof loadDone === 'function') {
		loadDone(result);
	}
}

var TD = {
	option : {
		type : 'POST',
		url : '#',
		data : {}
	},
	jsonEval : function(data) {
		try {
			if ($.type(data) == 'string')
				return eval('(' + data + ')');
			else
				return data;
		} catch (e) {
			return {};
		}
	},
	TD_STATUS_CODE : {
		ok : '200',
		warn : '200000',
		timeout : '300000',
		error : '400000'
	},
	ajaxDone : function(json) {
		if (TD.TD_STATUS_CODE.ok == json.rspcode) {
			alertMsg.correct(json.msg);
		} else {
			alertMsg.error(json.msg);
		}
	},
	ajaxError : function(json) {
		alert('提交失败');
		alertMsg.error();
	},
	isEmpty:function(value){
		if(value == null || value == undefined){
			return true;
		}
		
		if((value + '').length == 0){
			return true;
		}
		
		return false;
	},
	isNotEmpty : function(value){
		return !TD.isEmpty(value);
	}
};

function formSubmitForAjax(opt,$form) {
	$.ajax({
		type : opt.method || 'POST',
		url : opt.url,
		data : opt.data,
		dataType : opt.dataType || "json",
		cache : false,
		success : function(result){
			if(typeof opt.success === 'function'){
				opt.success(result,$form);
			}else{
				TD.ajaxDone(result,$form);
			}
		}, 
		error : opt.error || TD.ajaxError
	});
}

/**
 * 表单验证并且在验证通过后提交到服务器
 * 
 * @param form
 *            表单对象
 * @param callback
 *            回调函数
 * @returns {Boolean}
 */
function validateCallback(form, callback) {
	var $form = $(form);
	
	doAceSwitchByForm($form);
	
	if(TDValidateForm($form)){
		var param = {};
		//在base.js中定义了getFormParams 来获取表单的参数列表,由该方法处理左右切换开关取值问题
		if(typeof getFormParams === 'function'){
			param = getFormParams($form);
		}else{
			param =  $form.serializeArray();
		}
		var opt = {
				method : $form.attr('method'),
				url : $form.attr("action"),
				data : param,
				dataType : "json",
				success : callback
			};
			formSubmitForAjax(opt,$form);
			return false;
	}else{
		return false;
	}
	
	
}


/**
 * 对话框ajax回调
 * @param result 返回结果集
 * @param $form 提交的表单(可根据当前表单定位到哪个对话框)
 */
function dialogAjaxDone(result,$form) {
//	var code = result.rspcod;
//	var type = result.callBackType;
	if (result.rspcod != TD.TD_STATUS_CODE.ok) {// 失败
		alertMsg.error(result.rspmsg);
	} else {
		dialog.last.close();
		jqGridSearch();
	}
}

/**
 * 处理状态开关 开启/关闭 的值
 * @param $form
 */
function doAceSwitchByForm($form){
	//得到当前表单下的switch开关列表
	var aceSwitch = $('.ace-switch',$form);
	return doAceSwitch(aceSwitch);
}

function doAceSwitch($switch){
	//得到当前表单下的switch开关列表
	$switch.filter('.ace-switch').attr('checked','checked');
}

function refreshPageByUrl(url,data){
	$.ajax({ url: url,type:"POST",data:data ,dataType:'html',success:function(html){
		refreshPageContent(html);
	}});
}

function htmlAjaxDone(obj, html) {
	return refresh(obj, html);
}

function getPageContent() {
	return $(".page-content-area");
}

function refreshPageContent(html) {
	var obj = getPageContent();
	return refresh(obj, html);
}

function refresh(obj, html) {
	return $(obj).html(html);
}

$(document).ready(function() {

	// 处理#号的连接，在#号前面加上当前请求地址
	// $.baseHref();
});

// 拖动功能
var drag_ = false;
var mouseX, mouseY;
/**
 * 拖动功能函数
 * 
 * @param $listenBox
 *            触发拖动事件的对象或对象ID
 * @param $moveBox
 *            要移动的对象或对象ID，不传时等于拖动事件的对象
 * @param moveBox
 *            移动效果的处理函数，会传入移动的x,y大小,不传有默认移动效果处理
 * @returns {Boolean}
 */
function moveObject($listenBox, $moveBox, moveBox) {
	if (!$listenBox) {
		return false;
	}
	if (typeof $listenBox === 'string') {// id
		$listenBox = $("#" + $listenBox);
	}
	if (typeof $moveBox === 'string') {// id
		$moveBox = $("#" + $moveBox);
	}
	$moveBox = $moveBox || $listenBox;
	$listenBox.on('mousedown', function(event) {// 按下
		if (event.button == 0) {// 如果是左键按下
			drag_ = true;// 处理鼠标移动的标识
			$("body").css({
				cursor : "move"
			});// 设置鼠标手势为移动图标

			// 触发移动事件时设置鼠标当前的x,y
			mouseX = event.clientX;
			mouseY = event.clientY;
			// 触发移动后监听body而不监听div是因为防止鼠标移除了div有bug
			$("body").on('mousemove', function(event) {
				if (drag_) {
					// 鼠标超出body不移动
					if (event.clientX < 0) {
						mouseX = event.clientX;
					}
					if (event.clientY < 0) {
						mouseY = event.clientY;
					}
					var x = mouseX - event.clientX;// 计算移动的 x 大小
					var y = mouseY - event.clientY;// 计算移动的y 大小
					if (typeof moveBox === 'function') {// 如果有指定处理函数，调用指定处理函数
						moveBox($moveBox, x, y, event.clientX, event.clientY);
					} else {// 没有指定处理函数时按常规的top、left坐标移动
						var left = parseFloat($moveBox.css("left"));
						var top = parseFloat($moveBox.css("top"));
						if (x != 0) {
							$moveBox.css({
								left : (left + x * -1) + 'px'
							});
						}
						if (y != 0) {
							$moveBox.css({
								top : (top + y * -1) + 'px'
							});
						}
					}
					// 移动后保存当前鼠标坐标
					mouseX = event.clientX;
					mouseY = event.clientY;
				}
			}).on('mouseup', function(event) {// 松开
				if (event.button == 0) {// 左键松开，解绑事件
					drag_ = false;// 移动标识改为false
					$("body").css({
						cursor : ""
					});// 鼠标样式设置为默认
					$("body").unbind('mousemove').unbind('mouseup');// 取消事件监听
				}
			});
		}
		;
	});

	return true;
}

/**
 * 提供字符串全部替换的方法
 */
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}
function resetForm(){
   $container =  $("body");
   $("select[location]",$container).selReset();
}