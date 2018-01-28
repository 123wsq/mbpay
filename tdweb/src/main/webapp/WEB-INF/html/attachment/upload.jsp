<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>文件上传</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/component/plupload/queue/css/jquery.plupload.queue.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/component/easyui/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/component/easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/component/plupload/plupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/component/plupload/plupload.html4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/component/plupload/plupload.html5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/component/plupload/plupload.flash.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/component/plupload/zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/component/plupload/queue/jquery.plupload.queue.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/component/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/component/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/component/easyui/demo.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/component/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/component/easyui/easyui-lang-zh_CN.js"></script>
</head>
<body style="padding: 0;margin: 0;">
	<div id="uploader">&nbsp;</div>
	<script type="text/javascript">
		var files          = [],
		    errors         = [],
		    type           = 'file',
		    bChunk         = eval('${CHUNK}'),
		    sTableName     = '${TABLENAME}',
		    sno            = '${SNO}',
		    sLx	           = '${LX}',
		    sOrderNum      = '${ORDERNUM}',
		    sPageNameId    = '${PAGENAMEID}',
		    sAllowFileType = '${ALLOWFILETYPE}',
		    max_file_size  = '100mb';
		
		//如果没有设置允许的附件类型，则默认
		if(undefined == sAllowFileType || "" == sAllowFileType || null==sAllowFileType){
			sAllowFileType = "rar,zip,doc,docx,xls,xlsx,ppt,pptx";
		}
		var filters = {title : "文档", extensions : sAllowFileType};
		
		$("#uploader").pluploadQueue($.extend({
			runtimes : 'html5,html4',
			url : $.getBaseHerf() +"attachment/fileUploadHandler.do?TABLENAME="+sTableName+"&SNO="+sno+"&LX="+sLx+"&ORDERNUM="+sOrderNum,
			max_file_size : max_file_size,
			file_data_name:'uplaod_ImportFile',
			unique_names:true,
			filters : [filters],
			flash_swf_url : '${pageContext.request.contextPath}/component/plupload/plupload.flash.swf',
			init:{
				FilesAdded: function (up, files) {
					$.each(up.files, function (i, file) {
				    	if(chLength(file.name)>'${len}'){
				    		alert("文件名过长,请重新选择！\n文件名长度应小于50个字符（当前文件名长度为:"+chLength(file.name)+"）");
				    		up.removeFile(file);
				    	}
				    });
					$.each(up.files, function (i, file) {
				    	if (up.files.length <=1) {
				    		return ;
				        }
				    	up.removeFile(file);
				    });    
					
				},
				FileUploaded:function(uploader,file,response){
					if(response.response){
						var rs = eval('(' + response.response + ')');
						if(rs.STATUS){
							files.push(file.name);
							
							//上传成功后将附件信息回写至父页面
							showUploadFileInf(rs,sPageNameId);
						}else{
							errors.push(file.name);
						}
					}
				},
				Error: function(up, err) {
					//当服务器端返回错误信息时error方法显示错误提示，
					//服务器端返回错误信息会被plupload转换为-200 http错误,
					//所以只能做==-200比较。更好的提示，需要修改插件源代码。
					if(err.code==-200){
						//$.messager.alert('提示',"文件格式错误，请检查后重新上传!\n请选择文件格式为:"+sAllowFileType+"。",
						//'error');
					}
					if(err.code==-602){
						//$.messager.alert('提示',"文件已存在!",
						//'error');
					}
				}
			}
		},(bChunk ? {chunk_size:'10mb'} : {})));
		
		//查找带回，更新父页面信息
		function showUploadFileInf(msg,sPageNameId){
			var bFlag = false;
			if(undefined==sPageNameId || null==sPageNameId){
				sPageNameId = "";
			}else{
				sPageNameId = sPageNameId+"_";
			}
			
			//更新父页面Id信息
			for(var sId in msg){
				if(window.parent.$("#"+sPageNameId+sId) != undefined){
					window.parent.$("#"+sPageNameId+sId).val(msg[sId]);
					bFlag = true;
				}
			}
			
			if(!bFlag){
				//获取当前选中tab
				var tbIframe = getCurTabIframe();
				
				//更新父页面Id信息
				for(var sId in msg){
					if(tbIframe.$("#"+sPageNameId+sId)[0] != undefined){
						tbIframe.$("#"+sPageNameId+sId)[0].value = msg[sId];
					}
				}
			}
		}
	</script>
</body>
</html>