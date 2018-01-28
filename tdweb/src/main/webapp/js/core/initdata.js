/**
 * 系统操作员信息
 */
var UID = {
		id      :'',     //操作员系统编号
		userId  :'',     //操作员登录帐号
		userName:'',     //操作员姓名
		orgId   :'',     //机构ID
		orgName :'',     //机构名称
		roleName:'',     //角色名称
		roleId  :'',     //角色ID
		sysId   :'',      //系统ID
		menuAuth:'',
		_USER_NO:'',
		_ROLE_ID:''
};

/**
 * 数据字典
 * 格式：{"SEX":{"1":"男","0":"女"},"CERTTYPE":{"04":"学生证","01":"身份证","02":"护照","03":"军官证"}}
 */
var DICT = {
		obj:null,                  //定义JSON对象存放数据字典
		get:function(code,value){  //获取汉字描述
			if(code == null || code == undefined || (code+'').length == 0){
				return this.obj;
			}else if(value != null && value != undefined && (value + '').length > 0){
				return this.obj[code][value]== undefined ?"":this.obj[code][value];	
			}else{
				return this.obj[code];	
			}
		},
		getKey:function(code,value){//根据文本获取key值，code：指定的数据字典属性名称，value：一般为汉字文本
			if(code == null || code == undefined || (code+'').length == 0){
				return '';
			}
			var data = DICT.get(code);
			for(var key in data){
				if(data[key] == value){
					return key;
				}
			}
			return '';
		},
		getList:function(code){    //获取子节点List
			return null;	
		}
};


/**
 * 异步加载数据字典
 */
$.ajax({
	url: $.getBaseHerf() +"mpbase/dict/query.do",
	type:"POST",
	dataType:"json",
	async:false,
	success: function(result){	
		if(result.rspcod == 200){
			var jobj =	eval("(" + result.obj + ")");
			DICT.obj = jobj;
		}else{
			alert("错误代码："+result.rspcod+",错误描述："+result.rspmsg);
		}
	},
	error:function(XMLHttpRequest, textStatus){
		alert("网络异常代码："+XMLHttpRequest.status+",异常描述："+textStatus);
	}
});



/**
 * 同步加载操作员登录信息
 */
$.ajax({
	url: "auth/uid/query.do",
	type:"POST",
	dataType:"json",
	async:false,
	success: function(result){	
		if(result.rspcod == 200){
			$.extend(UID,result.obj);
		}else{
			alert("错误代码："+result.rspcod+",错误描述："+result.rspmsg);
			//$.getBaseHerf();
		}
	},
	error:function(XMLHttpRequest, textStatus){
		alert("网络异常代码："+XMLHttpRequest.status+",异常描述："+textStatus);
		//$.getBaseHerf();
	}
});



