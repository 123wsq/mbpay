/***
*个性化设置脚本 ，针对用户设置属性进行保存，用cookie保存用户设置属性，
*打开时初始化成用户上次设置的样式，比如主题、菜单风格等
*/

$(document).ready(function() {
	try {
		$('.user-info').children('.user-name').html(UID.userName);
	} catch (e) {
		// TODO: handle exception
	}
	
	$('#ace-settings-container').hide();//隐藏设置按钮
	
	//设置项固定。菜单固定等操作
	$('#ace-settings-breadcrumbs').each(function(i){
		var $this = $(this);
		$this.trigger('click');
	});
});