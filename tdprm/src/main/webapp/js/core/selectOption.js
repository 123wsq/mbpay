var locationAttrName = "location";//存放远程地址的属性名称

var optionBuff = {};

$.fn.extend({
	selDrawing:function(){//渲染select
		return $(this).each(function (){
			var $sel = $(this);
			var loaded = $sel.attr("loaded") || 'false';//true 为已加载不需要再次加载
			if(loaded === true || loaded === 'true'){
				return;
			}
			$sel.addClass("chzn-select");
			$sel.chosen();
			$sel.attr("loaded","true");//标记为已加载
			
			//触发加载完成事件
			var onLoadDone = $sel.attr('onLoadDone');
			if(onLoadDone){
				onLoadDone = eval('(' + onLoadDone + ')');
				if(typeof onLoadDone === 'function'){
					$sel.on('LoadDone',onLoadDone);
					$sel.trigger('LoadDone');
				}
			}
			
			$sel.cascade();//级联
			
			//如果是只读的，删除选择弹出层
			var readonly = $sel.attr('readonly');
			if(readonly == 'true' || readonly == 'readonly'){
				$('.chzn-drop',$sel.next(".chzn-container")).remove();
			}
			
			return $sel;
		});
	},
	selLoad:function(){//扩展select自动远程加载
		return $(this).each(function(){
			var $sel = $(this);
			var loaded = $sel.attr("loaded") || 'false';//true 为已加载不需要再次加载
			if(loaded === true || loaded === 'true'){
				return $sel;
			}
			var location = $sel.attr(locationAttrName);//动态加载下拉列表，local:本地码表，url：服务器地址
			if(location === 'local'){//本地
				createOption($sel,DICT);//从数据字典中获取
			}else if(location){
				loadSelectOption($sel);//从服务器获取
			}else{
				$sel.selDrawing();
			}
			
			return $sel;
		});
	},
	aceSwitchInit : function(){
		$(this).filter('.ace-switch').each(function(){
			var $swc = $(this);
			//如果没有设置value属性，首先让值等于 “0”
			if(TD.isEmpty($swc.val())){
				$swc.val('1');
			}
			//如果是选中了，让值等于 “1”
			if($swc.attr('checked') === 'checked' || $swc.attr('checked') === 'true' || $swc.attr('checked') === true){
				$swc.val('0');
			}
			
			//如果值是零，就选中
			if($swc.val() == '0'){
				$swc.attr('checked','checked');
			}
			
			$swc.on('change',function(){
				$swc.attr('checked','checked');
				var val = $(this).val();
				if(val == '1'){
					$(this).val('0');
				}else {
					$(this).val('1');
				}
			});
			
		});
	},
	selReset:function(){//扩展select自动远程加载
		return $(this).each(function(){
			var $sel = $(this);
			var location = $sel.attr(locationAttrName);//动态加载下拉列表，local:本地码表，url：服务器地址
			$sel.attr("loaded","refresh");
			if(location === 'local'){//本地
				createOption($sel,DICT);//从数据字典中获取
			}else if(location){
				loadSelectOption($sel);//从服务器获取
			}else{
				$sel.selDrawing();
			}
			
			return $sel;
		});
	},
	cascade:function(){
		$(this).each(function(){
			var $sel = $(this);
			var ref = $sel.attr('ref');//级联只想的下拉列表Id
			//级联对象所在的一个容器Id，只要是父级，级别没要求,防止ID重复
			var refBox = $('#'+$sel.attr('refBox'));
			if(refBox.length == 0){
				refBox = $('body');//如果没有指定，整个body作为容器查找
			}
			var $relSel = $('#' + ref,refBox);//级联的目标对象
			if($relSel.length == 0){
				return;
			}else{
				//值变触发级联
				$sel.off('change').on('change',function(ev){
					$sel.cascade();
				});
			}
			//防止a级联b，b再级联a
			if($relSel.attr('ref') === $sel.attr('id')){
				alertMsg.error("不能循环引用！");
				return;
			}
			
			
			var refUrl = $sel.attr('refUrl');
			refUrl = refUrl.replace("{value}",$sel.val());
			
			//给替换值以后的url给指定下拉列表对象，调用下拉列表的加载函数来完成
			$relSel.attr('location',refUrl);
			$relSel.attr('loaded','refresh');
			var buff = '';
			if((buff=$sel.attr('buff')) && buff.length > 0 && optionBuff[buff]){
				//加载本地缓存
				createOption($relSel,optionBuff[buff][$sel.val()]);
			}else{
				//加载远程地址
				loadSelectOption($relSel);
//				$relSel.selLoad();
			}
			
		});
		
	}
});

/**
 * 远程加载select的选择项
 * @param $sel
 */
function loadSelectOption($sel){
	var url = $sel.attr(locationAttrName);
	var data = $sel.attr("data");
	if(data){
		data = eval('(' + data + ')');
	}else{
		data = {};
	}
	$.ajax({ 
		url: url,
		type:"POST",
		dataType:"json",
		data:data,
		async:true,
		success: function(result){	
			createOption($sel,result);//加载完成后创建option
		},
		error:function(XMLHttpRequest, textStatus){
			msg.alert("错误","错误代码："+XMLHttpRequest.status+",错误描述："+textStatus,'warn');
		}});
}

/**
 * 创建option
 * @param $sel 下拉列表对象
 * @param json 下拉选项存储对象
 */
function createOption($sel,json){
	if(typeof $sel === 'string'){
		$sel = $("#" + $sel);
	}else{
		$sel = $($sel);
	}
	var vn = $sel.attr("valName") || 'value';//value的存储属性名称
	var tn = $sel.attr("textName") || 'text';//text的存储属性名称
	var defVal = $sel.attr("defVal") || '';//默认值
	var defType = $sel.attr("defType") || '';//默认值类型
	var rel = $sel.attr("rel") || '';//option选项在json对象中的属性名称
	var refresh = $sel.attr('loaded');
	
	var options = getJsonDataByName(json,rel);//根据属性名称获取对象 支持 a.b.c 的结构
	var optBf = {};
	var selectType = $sel.attr("multiple") == undefined ?"0":"1";//
	if(selectType === '1'){
		if(defVal){//设置默认值
			if(defType == 'js'){//如果是js表达式，转换为对象,将会自动取值
				defVal = eval('(' + defVal + ')');
			}
		}
		//multiple 类型的选择框
		for(var key in options){
			alert(key);
			optBf[key] = options;
			$sel.append("<option value='"+key+"' " + (defVal==key ? " selected='selected' " : "") + ">"+options[key]+"</option>");
		}
		//loadMultipleSelect();
	}else{
		
		
		if($sel.children().length == 0){//如果没有元素，可加入“请选择”
//			$sel.append("<option value=''>----请选择----</option>");
		}
		
		//如果是刷新，将上一次系统加载结果删除
		if(refresh === 'refresh'){
			$sel.children('option[oper="sys"]').remove();
			$sel.next(".chzn-container").remove();
			$sel.removeClass('chzn-done');
//			$('.chzn-drop',$sel.next(".chzn-container")).remove();
		}
		
		if(!options){//没有可用的选择项
			//加载数据完成后重新渲染
			$sel.selDrawing(); 
			return;
		}
		if(defVal){//设置默认值
			if(defType == 'js'){//如果是js表达式，转换为对象,将会自动取值
				defVal = eval('(' + defVal + ')');
			}
		}
		//var optBf = {};
		
		if(options instanceof Array){//数组，远程url一般返回数组
			for(var i=0;i< options.length;i++){
				var opt = options[i];
				optBf[opt[vn]] = opt;
				$sel.append("<option oper='sys' value='"+opt[vn]+"' " + (defVal==opt[vn] ? " selected='selected' " : "") + ">"+opt[tn]+"</option>");
			}
		}else if(typeof options === 'object'){//本地码表一般为对象
			for(var key in options){
				optBf[key] = options;
				$sel.append("<option oper='sys' value='"+key+"' " + (defVal==key ? " selected='selected' " : "") + ">"+options[key]+"</option>");
			}
		}
		
		var buff = '';
		if((buff=$sel.attr('buff')) && buff.length > 0 && !optionBuff[buff]){
			optionBuff[buff] = optBf;//缓存下拉列表
		}
		$sel.selDrawing(); 
	}
	//	alert($(optionBuff).length);
	//加载数据完成后重新渲染
	
	
}


/**
 * 从码表中获取下拉框
 * @param selectId 码表代码
 * @param value  默认值
 * @param type   类型
 */
function loadOption(selectId,value,type){
	var select = document.getElementById(selectId);
	//ajax 请求
	$.ajax({ 
		url: $.getBaseHerf() +"mpbase/selectoption/query.do?did="+selectId+"&value="+value+"&type="+type,
		type:"POST",
		dataType:"json",
		async:true,
		success: function(result){	
			if(result.rspcod == 200){
				var opp = null;
				var options =  eval("(" + result.obj + ")").options;
				opp = new Option("----请选择----","",true);
				select.add(opp);
				for(var i=0;i<options.length;i++){
					
					if(value == options[i].value){
						opp = new Option(options[i].text,options[i].value,true);
					}else{
						opp = new Option(options[i].text,options[i].value);
					}
					opp.name = "option"+i;
					select.add(opp);
				}
			}else{
				msg.alert("提示","错误代码："+result.rspcod+",错误描述："+result.rspmsg,'error');
				
			}
			//加载数据完成后重新渲染
			$(select).addClass('chzn-select');
			$(select).chosen(); 

		},
		error:function(XMLHttpRequest, textStatus){
			msg.alert("错误","错误代码："+XMLHttpRequest.status+",错误描述："+textStatus,'error');
		}});

	
	
}

/**
 * 从普通数据表获取下拉框
 * @param selectId 码表代码
 * @param value  默认值
 * @param type   类型
 * @param url    请求地址
 */
function loadOption(selectId,value,type,url){
	var select = document.getElementById(selectId);
	//ajax 请求
	$.ajax({ 
		url: url,
		type:"POST",
		dataType:"json",
		async:true,
		success: function(result){	
			if(result.rspcod == 200){
				var opp = null;
				var options =  eval("(" + result.obj + ")").options;
				opp = new Option("----请选择----","",true);
				select.add(opp);
				for(var i=0;i<options.length;i++){
					
					if(value == options[i].value){
						opp = new Option(options[i].text,options[i].value,true);
					}else{
						opp = new Option(options[i].text,options[i].value);
					}
					opp.name = "option"+i;
					select.add(opp);
				}
			}else{
				msg.alert("提示","错误代码："+result.rspcod+",错误描述："+result.rspmsg,'error');
			}
			//加载数据完成后重新渲染
			$(select).addClass('chzn-select');
			$(select).chosen(); 

		},
		error:function(XMLHttpRequest, textStatus){
			msg.alert("错误","错误代码："+XMLHttpRequest.status+",错误描述："+textStatus,'error');
		}});
}


var SELECT2TEXT = {
		obj:{},
		get:function(code,name){  //获取text
			if(code == null || code == undefined || (code+'').length == 0){
				return '';
			}else if(code != null && code != undefined && (code + '').length > 0){
				var json=this.obj[name];
				for(var i=0; i<json.length; i++){  
					if(json[i].value==code){
						return json[i].text;
					}
				  }  
				return '';
			}
		}
};

function select2textData(url,name){
	if(SELECT2TEXT.obj==null||SELECT2TEXT.obj[name]==null){
		$.ajax({
			url: url,
			type:"POST",
			dataType:"json",
			async:false,
			success: function(result){
				if(result.rspcod == 200){
					SELECT2TEXT.obj[name]= eval("(" + result.obj + ")")['options'];
				}else{
					alert("错误代码："+result.rspcod+",错误描述："+result.rspmsg);
				}
			},
			error:function(XMLHttpRequest, textStatus){
				alert("网络异常代码："+XMLHttpRequest.status+",异常描述："+textStatus);
			}
		});
	}
}

function loadMultipleSelect(){
		var demo1 = $('select[name="duallistbox_demo1[]"]')
				.bootstrapDualListbox(
						{
							infoTextFiltered : '<span class="label label-purple label-lg">检索</span>'
						});
		var container1 = demo1.bootstrapDualListbox('getContainer');
		container1.find('.btn').addClass('btn-white btn-info btn-bold');

		//////////////////
		//select2
		$('.select2').css('width', '500px').select2({
			allowClear : true
		})
		$('#select2-multiple-style .btn').on('click', function(e) {
			var target = $(this).find('input[type=radio]');
			var which = parseInt(target.val());
			if (which == 2)
				$('.select2').addClass('tag-input-style');
			else
				$('.select2').removeClass('tag-input-style');
		});

		//////////////////
		$('.multiselect')
				.multiselect(
						{
							enableFiltering : true,
							buttonClass : 'btn btn-white btn-primary',
							templates : {
								button : '<button type="button" class="multiselect dropdown-toggle" data-toggle="dropdown"></button>',
								ul : '<ul class="multiselect-container dropdown-menu"></ul>',
								filter : '<li class="multiselect-item filter"><div class="input-group"><span class="input-group-addon"><i class="fa fa-search"></i></span><input class="form-control multiselect-search" type="text"></div></li>',
								filterClearBtn : '<span class="input-group-btn"><button class="btn btn-default btn-white btn-grey multiselect-clear-filter" type="button"><i class="fa fa-times-circle red2"></i></button></span>',
								li : '<li><a href="javascript:void(0);"><label></label></a></li>',
								divider : '<li class="multiselect-item divider"></li>',
								liGroup : '<li class="multiselect-item group"><label class="multiselect-group"></label></li>'
							}
						});

		///////////////////u

		//typeahead.js
		//example taken from plugin's page at: https://twitter.github.io/typeahead.js/examples/
		var substringMatcher = function(strs) {
			return function findMatches(q, cb) {
				var matches, substringRegex;

				// an array that will be populated with substring matches
				matches = [];

				// regex used to determine if a string contains the substring `q`
				substrRegex = new RegExp(q, 'i');

				// iterate through the pool of strings and for any string that
				// contains the substring `q`, add it to the `matches` array
				$.each(strs, function(i, str) {
					if (substrRegex.test(str)) {
						// the typeahead jQuery plugin expects suggestions to a
						// JavaScript object, refer to typeahead docs for more info
						matches.push({
							value : str
						});
					}
				});

				cb(matches);
			}
		}

		//in ajax mode, remove remaining elements before leaving page
		$(document).one(
				'ajaxloadstart.page',
				function(e) {
					$('[class*=select2]').remove();
					$('select[name="duallistbox_demo1[]"]')
							.bootstrapDualListbox('destroy');
					$('.rating').raty('destroy');
					$('.multiselect').multiselect('destroy');
				});
}