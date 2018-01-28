
function logout(){
	alertMsg.confirm({title:'提示',msg:'您确定要退出登录吗?',call:function(ok){
		if(ok == true){
			location.href= server_base_url + 'auth/loginout.do';
		}
	}});
}

$(document).ready(function() {
//	mask.show();
});

/**
 * 获得条件查询表单元素
 * @returns
 */
function getSearchForm(){
//	return $(".search_panel form");
	return $("#GridSearchForm");
}

/**
 * 获取查询条件
 * @returns
 */
function getSearchParams(){
	var $form = getSearchForm();//查询条件表单
	return getFormParams($form);
}

function getFormParams($form){
	var $form = $($form);//查询条件表单
	var search_param = {};
	if(!$form || $form.length !== 1){
		return search_param;
	}
	var search_param_array = $form.serializeArray();//获取条件参数列表
//	data.push({name:"reqType",value:"ajax"});
	//处理 Switch 取值异常问题
	var $input_switch = $('input.ace-switch',$form);//获取表单中 switch 的input元素列表
	$input_switch.each(function(){
		var $input = $(this);
		var p_name = $input.attr('name');
		var p_value = $input.val();
		var isExist = false;
		//遍历序列号的表单参数中是否包含了该元素
		$(search_param_array).each(function(i){
			//如果存在了，则不处理
			if(this.name === p_name){
				isExist = true;
				//退出本次循环
				return false;
			}
		});
		//如果没有，则加入到参数列表
		if(!isExist){
			search_param_array.push({name:p_name,value:p_value});
		}
	});
	//search_param = search_param_array;
	$(search_param_array).each(function(){
		if($form.attr('method') && $form.attr('method').toUpperCase() !== 'POST'){
			search_param[this.name] = encodeURI(this.value);
		}else{
			search_param[this.name] = this.value;
		}
	});
//	if($form.attr('method') && $form.attr('method').toUpperCase() !== 'POST'){
//		$(search_param_array).each(function(){
//			search_param[this.name] = encodeURI(this.value);
//		});
//	}
		
	return search_param;
}

/**
 * 主页面条件查询(到时候可以区分对话框条件查询,模仿dwz)
 * @param $form 表单对象
 * @param $tb
 * @returns {Boolean}
 */
function pageSearch($form,$tb){
	$form = $($form);
	if(!$form || $form.length != 1){
		alertMsg.warn("表单不存在");
		return false;
	}
	$tb = $tb || "#grid-table";
	jqGridSearch($tb,$form);
	
	return false;
}


/**
 * 条件查询
 * @param tb 表格
 */
function jqGridSearch(tb,$form){
	tb = tb || "#grid-table";
	$form = $form || getSearchForm();
	
	var $jqParam = jQuery(tb).jqGrid("getGridParam");//获取当前参数
	//var posData = jQuery(gtable || "#grid-table").jqGrid().p.posData;
	var $tb = jQuery(tb);
	//alert(jQuery(tb).jqGrid('p'));
	
	$jqParam['postData'] = getFormParams($form);
	$jqParam['page'] = 1;//搜索跳到第一页
	$jqParam['mtype'] = $form.attr('method') || 'GET';
	$jqParam['url'] = $form.attr('action') || $jqParam['url'];
	//jQuery(tb)[0].P = search_param;
	//alert($jqParam['url']);
	//jQuery(this).jqGrid('setGridParam', search_param);
	//alert(search_param['menuId']);
	//alert($(grid_selector).jqGrid('getGridParam')['url']);
	jQuery(tb).jqGrid('setGridParam', $jqParam).trigger("reloadGrid");
}



//页面加载完成初始化信息
$(document).ready(function() {

});

/**
 * 打开对话框
 * url  : 请求地址
 * title: 对话框标题
 * w    : 宽
 * h    : 高
 */
/*function openDialog(url,title,w,h){
	
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title =title;
		 diag.URL = url;
		 diag.Width = w;
		 diag.Height = h;
		 diag.CancelEvent = function(){ //关闭事件
			diag.close();
			//setTimeout("location.reload()",100);
		 };
		 diag.show();
}
*/


/**
 * 自定义MAP, 主要存放数据字典。
 * 数据字典格式：{"SEX":{"1":"男","0":"女"},"CERTTYPE":{"04":"学生证","01":"身份证","02":"护照","03":"军官证"}}
 */
function Map() {
  var struct = function(key, value) {
	  this.key = key;
	  this.value = value;
   };
  //添加数据
  var put = function(key, value){
	  for (var i = 0; i < this.arr.length; i++) {
	    if ( this.arr[i].key === key ) {
	    	this.arr[i].value = value;
	    	return;
	   }
	  }
	  this.arr[this.arr.length] = new struct(key, value);
  };
  //获取数据
  var get = function(key) {
	  for (var i = 0; i < this.arr.length; i++) {
		  if ( this.arr[i].key === key ) {
			  return this.arr[i].value;
		  }
	  }
	  return null;
  };
  //删除数据
  var remove = function(key) {
	  var v;
	  for (var i = 0; i < this.arr.length; i++) {
		  v = this.arr.pop();
		  if ( v.key === key ) {
			  continue;
		  }
		  this.arr.unshift(v);
	  }
  };
  //返回map大小
  var size = function() {
	  return this.arr.length;
  };
  //判断是否空
  var isEmpty = function() {
	  return this.arr.length <= 0;
  } ;
  
  this.arr = new Array();
  this.get = get;
  this.put = put;
  this.remove = remove;
  this.size = size;
  this.isEmpty = isEmpty;
}

/**
 * 处理表格值转换
 * @param cellvalue 表格值
 * @param options 配置属性
 * @param rowObject 行对象
 * @returns
 */
function gridFormatByDict(cellvalue, options, rowObject){
	if(TD.isEmpty(cellvalue) ){
		return cellvalue || '';
	}
	var colModel = options['colModel'];
	if(typeof colModel === 'object'){
		var ditcKey = colModel['ditcKey'];
		if(ditcKey != null && ditcKey != undefined && (ditcKey+"").length > 0 ){
			return DICT.get(ditcKey,cellvalue) || '';
		}
	}
	return cellvalue || '';
}

/**
 * 日期格式化
 * @param cellvalue 表格值
 * @param options 配置属性
 * @param rowObject 行对象
 * @returns
 */
function gridFormatByDate(cellvalue, options, rowObject){
	if(TD.isEmpty(cellvalue) ){
		return cellvalue || '';
	}
	return dateFormat(cellvalue);
}

/**
 * 单张图片视图
 * @param picId
 */
function picview(picId){
	openDialog({
		dialogId : 'dlg-picview',
		title : '预览',
		pageUrl : 'mpbase/pic/view.do',
		width :'550px',
		height:'550px',
		dataParam : {
			timestamp:getTimestamp(),
			picid : picId
		},
		dataName : 'obj' //表单数据存储对象名称
	});
}
function fileDownloadDialog(){
	openDialog({
		dialogId : 'dlg-fileDownload',
		title : '文件下载',
		pageUrl : 'mpbase/fileDownloadManageByCuday/view.do',
		width :'70%',
		height:'80%',
		dataName : 'obj' //表单数据存储对象名称
	});
}
function editPwdDialog() {
	openDialog({
		dialogId : 'dlg-editPwd',
		title : '修改密码',
		pageUrl : 'auth/index/userPwdEditView.do',
		width : '40%',
	    height:'50%'
	});
}
function report(querycon){	
	msg.confirm({title:'确认',position:'center',msg:'确认导出当前数据吗？',call:function(ok){
		if(ok){
			$.ajax({
				type : "post",
			    url : "mpbase/fileDownloadManage/report.do",
				dataType : 'json',
				contentType:"application/json",
				data:JSON.stringify(querycon),
				success : function(result) {
					if (result.rspcod != 200) {
						msg.alert("错误", result.rspmsg, 'error');
					} else {
						msg.alert("提示", result.rspmsg, 'correct');
					}
				},
				error : function(XMLHttpRequest, textStatus) {
					msg.alert("错误", "错误代码：" + XMLHttpRequest.status + ",错误描述："
							+ textStatus, 'error');
				}
			});
		}
	}});
}
function reportEsign(querycon){	
	msg.confirm({title:'确认',position:'center',msg:'确认导出当天T+0收款的电子签名单？',call:function(ok){
		if(ok){
			$.ajax({
				type : "post",
			    url : "mpbase/fileDownloadManage/reportEsign.do",
				dataType : 'json',
				contentType:"application/json",
				data:JSON.stringify(querycon),
				success : function(result) {
					if (result.rspcod != 200) {
						msg.alert("错误", result.rspmsg, 'error');
					} else {
						msg.alert("提示", result.rspmsg, 'correct');
					}
				},
				error : function(XMLHttpRequest, textStatus) {
					msg.alert("错误", "错误代码：" + XMLHttpRequest.status + ",错误描述："
							+ textStatus, 'error');
				}
			});
		}
	}});
}

/**
 * 获取报表数据
 * @param url
 */
function loadReportData(url){
	var data = null;
	$.ajax({ 
		url: url,
		type:"POST",
		dataType:"json",
		async:false,
		success: function(result){	
			data = result.obj;
		},
		error:function(XMLHttpRequest, textStatus){
			msg.alert("错误","错误代码："+XMLHttpRequest.status+",错误描述："+textStatus,'warn');
		}});
	return data ;
}


