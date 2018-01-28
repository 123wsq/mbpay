var regexObj = {
		number:function(value){//仅数字
			var reg=/^([0-9]+)$/;
			return reg.test(value);
		},
		Dnumber:function(value){//小数
			var reg=/^[0-9]+([.]{1}[0-9]+){0,1}$/;
			return reg.test(value);
		},
		email:function(value){//邮件
			var reg=/[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
			return reg.test(value);
		},
		mobile:function(value){//手机号
			var reg=/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
			return reg.test(value);
		},
		phone:function(value){//固话
//			var reg=/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$|(^(1[0-9])\d{9}$)/;
			var reg=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
			return reg.test(value);
		},
		zipcode:function(value){ //邮编
			var reg=/^[0-9]{6}$/;
			return reg.test(value);
		},
		chinese:function(value){//仅中文
			var reg=/^[\u4e00-\u9fa5]+$/;
			return reg.test(value);
		},
		idcard:function(value){ //身份证
			var reg=/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
			return reg.test(value);
		},
		url:function(value){ //地址url
			return ture;
		},
		notempty:function(value){ //长度限制
			var reg=/^[\u4e00-\u9fa5_a-zA-Z0-9]{1,10}$/;
			return reg.test(value);
			
		},
		ip4:function(value){
			var reg=/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
			return reg.test(value);
		},
		money:function(value){//金额
			var reg =/^[0-9]*(\.[0-9]{1,2})?$/;
			var reg_=/^([0-9]+)$/;
			var value_ = value.substr(0,1);
			if(!reg_.test(value_)){
				return false;
			}
			if(value.length > 12){
				return false ;
			}
			return reg.test(value);
		},
		word:function(value){
			var reg=/^[\u4e00-\u9fa5_a-zA-Z0-9]+$/;
			return reg.test(value);
		},
		bankcard:function(value){ //银行卡号
			var reg=/^\d{9,19}$/;
			return reg.test(value);
		},
		lenv1:function(value){ //长短校验1（至少输入6位且不含特殊字符）
			var reg=/^[\u4e00-\u9fa5_a-zA-Z0-9]+$/;
			if(value.length < 6){
				return false;
			}
			return reg.test(value);
		},
		lenv2:function(value){ //长短校验2（至少输入6位）
			if(value.length < 6){
				return false;
			}
			return true;
		},
		verion:function(value){ //校验版本号
				var reg=/^\d+(\.\d+){0,2}$/;
				return reg.test(value);
		},
		
};
var regexMsg = {
		number:"只能输入数字!",
		Dnumber:"只能输入整数或小数!",
		email :"邮件格式不正确!",
		mobile:"手机号码格式不正确!",
		phone:"固定电话格式不正确",
		zipcode:"编码格式不正确!",
		chinese:"只能输入中文字符!",
		idcard :"身份证号码格式不正确!",
		money:"金额格式不正确!",
		notempty:"输入长度不能超过10个字符!",
		word:"不能输入非法字符,请重新输入!",	
		bankcard:"银行卡格式输入错误!",
		lenv1:"请最少输入6位,且不含特殊字符!",
		lenv2:"请最少输入6位!",
		verion:"请输入正确的版本号！",
};

/**
 * 获取时间戳
 * @returns
 */
function getTimestamp(){
	return new Date().getTime();
}

/**
 * 日期格式化（年月日时分秒）
 */
function dateFormat(sdate){
	var tmpDate ;
	if(sdate == null || sdate.length != 14 ){
		return  "";
	}
	tmpDate = sdate.substring(0,4) + "-";
	tmpDate = tmpDate + sdate.substring(4,  6) + "-";
	tmpDate = tmpDate + sdate.substring(6,  8) + " ";
	tmpDate = tmpDate + sdate.substring(8, 10) + ":";
	tmpDate = tmpDate + sdate.substring(10,12) + ":";
	tmpDate = tmpDate + sdate.substring(12,14) ;
	return tmpDate;
}

/**
 * 日期格式化（时分秒）
 */
function tmFormat(sdate){
	var tmpDate ;
	if(sdate == null || sdate.length != 6 ){
		return  "";
	}
	tmpDate = sdate.substring(0,2) + ":";
	tmpDate = tmpDate + sdate.substring(2,4) + ":";
	tmpDate = tmpDate + sdate.substring(4);
	return tmpDate;
}
/**
 * 日期格式化（年月日）
 */
function dtFormat(sdate){
	var tmpDate ;
	if(sdate == null || sdate.length != 8 ){
		return  "";
	}
	tmpDate = sdate.substring(0,4) + "-";
	tmpDate = tmpDate + sdate.substring(4,  6) + "-";
	tmpDate = tmpDate + sdate.substring(6,  8) + " ";
	return tmpDate;
}
/**
 * 日期格式化（月日）
 */
function mdFormat(sdate){
	var tmpDate ;
	if(sdate == null || sdate.length != 4 ){
		return  "";
	}
	tmpDate = sdate.substring(0,2) + "-";
	tmpDate = tmpDate + sdate.substring(2,  4) + "";
	return tmpDate;
}
/**
 * 分格式化成元
 * @param cent
 * @returns
 */
function cent2Yuan(cent,bool) { 
	if(cent==null||cent==""||isNaN(cent)){
		return "";
	}
	var yuan=bool==false?cent/100:TDFormatMoney(cent/100,2);
	return yuan.replace(/[0]+$/, "").replace(/[.]+$/, "");
} 
/**
 * 大小格式化(B格式成KB)
 * @param size
 * @returns {String}
 */
function sizeForamt(size){
	if(size == null || size.length == 0){
		return "0KB";
	}
	return Math.round((size/1024)*100)/100 + "KB";
}
/**
 * 毫秒格式化成秒
 * @param ms
 * @returns {String}
 */
function msecFormat(msec){
	if(msec == null || msec.length == 0){
		return "0s";
	}
	return Math.round((msec/60)*100)/100 + "s";
}

/**
 * 格式化金额:  12345.67格式化为 12,345.67 
 * @param s
 * @param n
 * @returns {String}
 */
function TDFormatMoney(s, n)   
{   
   n = n > 0 && n <= 20 ? n : 2;   
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
   var l = s.split(".")[0].split("").reverse(),   
   r = s.split(".")[1];   
   var t = "";   
   for(var i = 0; i < l.length; i ++ )   
   {   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }   
   return t.split("").reverse().join("") + "." + r;   
} 
