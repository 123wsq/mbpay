
/**
 * 设置按钮权限
 * 
 */
function TDAuth(){
	var auth_path = null;
	
	//如果还没正常加载菜单列表，延时调用
	if(!UID.menuAuth){
		setTimeout(function(){
			TDAuth();
		}, 500);
		return false;
	}
	
	//选择所有带有权限属性的元素
	var $auth_elm = $('*[auth]');	
	$auth_elm.each(function(){  
	    auth_path = $(this).attr('auth');
	    if(auth_path && !UID.menuAuth[auth_path]){
			$(this).addClass("notallowed");
			$(this).addClass("disabled");
			$(this).removeAttr("onclick");
			$(this).unbind('click');
	    }
	});
}