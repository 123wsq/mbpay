
function loadForm(formid,json){
	
	var elements = document.getElementById(formid);
	var a = elements.length;//所有的控件个数
	for (var i=0;i<a;i++){
		if(elements[i].type == "text"){
//			alert(elements[i].name);

		}else if(elements[i].type == "checkbox"){
			
		}else if(elements[i].type == "hidden"){
		}
	} 
	
	
}

/**
 * 填充表单
 * @param form 表单对象
 * @param data 表单数据
 */
function fillForm(form, data) {
	
	var $form = $(form);
	var $data = data || {};
	var value, tagName, type, arr;
	for (var key in $data) {
		value = $data[key];	
		$("[name='" + key + "'],[name='" + key + "_dict'],[name='" + key + "[]']", $form).each(function(i) {			
			tagName = $(this)[0].tagName;
			type = $(this).attr('type');
			if (tagName == 'INPUT') {
				if (type == 'radio') {
					$(this).attr('checked', $(this).val() == value);
				} else if (type == 'checkbox') {
					if(value && value.indexOf(",") != -1){
						arr = value.split(',');
					}else{
						arr = [value];
					}
					for (var i = 0; i < arr.length; i++) {
						//开启/关闭 两项按钮用的checked，给该元素复制达到选中的默认状态
						if($(this).filter('.ace-switch').length == 1){
							$(this).attr('value',arr[i]);
						}else if ($(this).val() == arr[i]) {
							$(this).attr('checked', true);
							break;
						}
					}
				} else {				
					var code = $(this).attr('dict');
					var fmt = $(this).attr('init-format');
					if(fmt){
						try {
							fmt = fmt.replaceAll("{value}",value);
							value = eval('(' + fmt + ')');
						} catch (e) {						
						}
					}
					if(code != undefined && code != ""){
						$(this).val(DICT.get(code, value));
					}else{
						$(this).val(value);
					}
				}
			} else if (tagName == 'SELECT' || tagName == 'TEXTAREA') {
				$(this).val(value);
				if(tagName == 'TEXTAREA'){
					$(this).html(value);
				}
				$(this).attr('defVal',value);
			}else{
				$(this).attr('value',value);
			}

		});
	}
}

function getJsonDataByName(json,dataName){
	var fData = {};
	if(typeof json === 'string'){
		json = eval('(' + json + ')');
	}
	if(dataName && typeof dataName === 'string'){
		if(dataName.indexOf(',') > 0){//多个属性合并
			var p_list = dataName.split(',');
			
			for(var j=0;j<p_list.length;j++){
				var dName = p_list[j];
				if(TD.isEmpty(dName)){
					continue;
				}
				
				fData = $.extend({},fData,getJsonDataByName(json,dName));
			}
			return fData;
		}
		
		if(dataName.indexOf('.') > 0){//参数嵌套
			var p_array = dataName.split('.');
			var d = json;
			for(var i=0;i<p_array.length;i++){
				if(typeof d === 'string'){
					d = eval('(' + d + ')');
				}
				d = d[p_array[i]] || {};
			}
			fData = d || {};
		}else{
			fData = json[dataName] || {};
		}
		
	}else{
		fData = json;
	}
//	//如果没找到，在属性名称前面加一个obj，默认去obj下面取
//	if(!fData){
//		if(dataName && dataName.indexOf("obj") != 0){
//			dataName = "obj." + dataName;
//		}else{
//			dataName = "obj";
//		}
//		return getJsonDataByName(json,dataName);
//	}
	return fData;
}


