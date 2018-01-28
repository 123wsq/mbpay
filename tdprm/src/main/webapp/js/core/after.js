/**
*加载完了最后执行的内容
*/

$(document).ready(function() {
	//处理#号的连接，在#号前面加上当前请求地址
	$.baseHref();
	
	if(UID.isFirstLoginFlag==0){
		openDialog({
			dialogId : 'dlg-editPwd',
			title : '首次登录系统，请修改密码后再操作',
			pageUrl : 'auth/index/userPwdEditView.do',
			width : '40%',
		    height:'50%',
		    btnClose:false,
		    pageParam: {
				type:'isFirstLoginFlag',
			}
		});
	}
});