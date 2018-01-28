/**
 * 验证表单
 * 属性说明:
 * validate="true"  需要验证
 * msg="错误时提示信息"        
 * datatype="数据类型"
 * 
 * 
 */
function TDValidateForm($form){
	 var bool = true;
	 var dmsg = "该字段不能为空!";
	 var tmsg = null;
	 var $id = null;
	 var $datatype = null;
	 var area = "input[type=text],input[type=email],input[type=password]";
	 var rate=true;
	 var rateTop=true;
	 $(area,$form).each(function(){
		 bool = true;
		 tmsg = null;
		 $id = null;
		 $datatype = null;
		 $id = $(this);
		 if($id.attr('validate') == 'true'){
			 if($id.attr('msg') != undefined && $id.attr('msg') != ""){
				 tmsg = $id.attr('msg');
			 }
			 if($id.val() === undefined || $.trim($id.val()) === "" || $id.val() == $id.attr('placeholder')){

				 TDTips($id,tmsg==null?dmsg:tmsg);
				 bool = false;
				 return bool;
			 }else{
			 }
		 }
		 //正则表达式效验datatype
		 $datatype = $.trim($id.attr('datatype'));
		 if($datatype != undefined && $datatype != ""){
			 if($id.val() != undefined && $id.val() != ""){
				 if($datatype == "email"){			
					 if(!regexObj.email($id.val())){
						 TDTips($id,tmsg==null?regexMsg.email:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "url"){
					 //return true;
				 }else if($datatype == "number"){
					 if(!regexObj.number($id.val())){
						 TDTips($id,tmsg==null?regexMsg.number:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "Dnumber"){
					 if(!regexObj.Dnumber($id.val())){
						 TDTips($id,tmsg==null?regexMsg.Dnumber:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "date"){
					 //return true;
				 }else if($datatype == "datetime"){
					 //return true;
				 }else if($datatype == "money"){
					 if(!regexObj.money($id.val())){
						 TDTips($id,tmsg==null?regexMsg.money:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "mobile"){
					 if(!regexObj.mobile($id.val())){
						 TDTips($id,tmsg==null?regexMsg.mobile:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "phone"){
					 if(!regexObj.phone($id.val())){
						 TDTips($id,tmsg==null?regexMsg.phone:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "chinese"){
					 if(!regexObj.chinese($id.val())){
						 TDTips($id,tmsg==null?regexMsg.chinese:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "idcard"){
					 if(!regexObj.idcard($id.val())){
						 TDTips($id,tmsg==null?regexMsg.idcard:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "notempty"){
					 if(!regexObj.notempty($id.val())){
						 TDTips($id,tmsg==null?regexMsg.notempty:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "word"){
					 if(!regexObj.word($id.val())){
						 TDTips($id,tmsg==null?regexMsg.word:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "bankcard"){
					 if(!regexObj.bankcard($id.val())){
						 TDTips($id,tmsg==null?regexMsg.bankcard:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "lenv1"){
					 if(!regexObj.lenv1($id.val())){
						 TDTips($id,tmsg==null?regexMsg.lenv1:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "lenv2"){
					 if(!regexObj.lenv2($id.val())){
						 TDTips($id,tmsg==null?regexMsg.lenv2:tmsg);
						 bool = false;
						 return bool;
					 }
				 }else if($datatype == "verion"){
					 if(!regexObj.verion($id.val())){
						 TDTips($id,tmsg==null?regexMsg.verion:tmsg);
						 bool = false;
						 return bool;
					 }
				 }
				 else{
					 //return true;
				 }
			 }
		 }
		 if(typeof($id.attr("judge"))!="undefined"){
			 if("agentRate"==$.trim($id.attr('judge'))){
				 	var msg="当前代理商";
					if("0001"==UID.sysId){
						msg="平台";
					}
					if(agentInf.agentName!=""){
						msg=agentInf.agentName+"代理商 ";
						if(agentInf.agentName=="null"){
							msg=" 代理商 ";
						}
					}
					var val=$.trim($id.val());
					if(val !="" && val !=0){
						rate=true;
						if(isNaN(val)){
							 TDTips($id,'请输入有效费率值');
							 bool = false;
							 return bool;
						}
						val=parseFloat($id.val());
						if($id.attr("id")=="rateLivelihood"&&val < agentInf.rateLivelihood){
						     TDTips($id,msg_Title+' 民生类费率 不能小于 '+msg+'民生类费率');
							 bool = false;
							 return bool;
						}else if($id.attr("id")=="rateGeneral"&& val <agentInf.rateGeneral){
							 TDTips($id,msg_Title+' 一般类费率 不能小于 '+msg+'一般类费率');
							 bool = false;
							 return bool;
						}else if($id.attr("id")=="rateGeneralTop"&& val <agentInf.rateGeneralTop){
						     TDTips($id,msg_Title+' 批发类费率 不能小于 '+msg+'批发类费率');
							 bool = false;
							 return bool;
						}else if($id.attr("id")=="rateGeneralTop"&& val >agentInf.rateGeneralTop){
							rate=false;
						}else if(rateTop===false&&$id.attr("id")=="rateGeneralMaximun"){
							 TDTips($id,msg_Title+' [批发类费率] 不能为空');
							 bool = false;
							 return bool;
						}else if($id.attr("id")=="rateGeneralMaximun"&& val <cent2Yuan(agentInf.rateGeneralMaximun)){
							 TDTips($id,msg_Title+' 批发类封顶 不能小于 '+msg+'批发类封顶');
							 bool = false;
							 return bool;
						}else if($id.attr("id")=="rateEntertain"&& val < agentInf.rateEntertain){
						     TDTips($id,msg_Title+' 餐娱类费率 不能小于 '+msg+'餐娱类费率');
							 bool = false;
							 return bool;
						}else if($id.attr("id")=="rateEntertainTop"&& val < agentInf.rateEntertainTop){
						     TDTips($id,msg_Title+' 房产类费率 不能小于 '+msg+'房产类费率');
							 bool = false;
							 return bool;
						}else if($id.attr("id")=="rateEntertainTop"&& val > agentInf.rateEntertainTop){
							rate=false;
						}else if(rateTop===false&&$id.attr("id")=="rateEntertainMaximun"){
							 TDTips($id,msg_Title+' [房产类费率] 不能为空');
							 bool = false;
							 return bool;
						}else if($id.attr("id")=="rateEntertainMaximun"&& val < cent2Yuan(agentInf.rateEntertainMaximun)){
						     TDTips($id,msg_Title+' 房产类封顶 不能小于 '+msg+'房产类封顶');
							 bool = false;
							 return bool;
						}
					}else{
						rateTop=true;
						if($id.attr("id")=="rateGeneralTop"){
							rateTop=false;
						}
						if($id.attr("id")=="rateEntertainTop"){
							rateTop=false;
						}
						if(rate===false&&$id.attr("id")=="rateGeneralMaximun"){
							TDTips($id,msg_Title+' 批发类封顶 不能 为空');
							 bool = false;
							 return bool;
						}else if(rate===false&&$id.attr("id")=="rateEntertainMaximun"){
							TDTips($id,msg_Title+' 房产类封顶 不能 为空');
							 bool = false;
							 return bool;
						}
					}
			 }
			 if("grade"==$.trim($id.attr('judge'))){
				 if(parseInt($("#grade_2_end").val())<parseInt($("#grade_1_end").val())){
						TDTips($("#grade_2_end"),'金额过小');
						 bool = false;
						 return bool;
				 }
				 if(parseInt($("#grade_3_end").val())<parseInt($("#grade_2_end").val())){
						TDTips($("#grade_3_end"),'金额过小');
						 bool = false;
						 return bool;
				 }
				 for(var i = 1; i < 4; i++){
					 var gradeId = 'grade_' + i + '_end';
					 if($.trim($("#" + gradeId).val()) == ''){
						 TDTips($("#" + gradeId),'金额不能为空');
						 bool = false;
						 return bool;
					 }
				 }
			 }
		 }
	});
	if(bool){
		 return true;
	}else{
		return false;
	}
	
}
/**
 * 提示
 * @param $id  校验对象
 * @param tmsg 提示信息
 */
function TDTips($id,tmsg){
	 $id.tips({
			side:3,
	        msg:tmsg,
	        bg:'#E3C94C',
	        time:2
		 });
		 $id.focus();
}
