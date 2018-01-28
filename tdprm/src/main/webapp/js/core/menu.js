
$(document).ready(function(){
	queryParMenu();
});

///**
// * 获取菜单
// */
function queryParMenu(){
	$.post("auth/queryAuthMenu.do",{"parentId":"00"},function(data){
		var menuHtml = getMenu(data,1);
		$(".nav-list li").last().after(menuHtml);
		$.baseHref();
		var hash = $.getLocalDataUrl();
		url = hash;
		if(typeof update_active === 'function') {
			update_active.call(null, hash, url);
		}
	},"json");
}

function getMenu(menus,level){
	var html = "";
	var cls = 'nav nav-list';
	
	if(level > 1){
		cls = 'submenu';
		html += '<ul class="'+cls+'">';
	}
	
	for(var i = 0 ; i < menus.length ; i ++){
		var a_cls = '';
		var down_icon='';
		
		var menu = menus[i];
		var url = menu['attributes']['url'] || '' ;
		var text = menu['text'];
		var menu_cls = menu['menuCls'] || 'fa-list-alt';
		
		var children = menu['children'] || {};
		if(children.length > 0){
			a_cls = 'dropdown-toggle';
			down_icon = '<b class="arrow fa fa-angle-down"></b>';
		}
		
		html += '<li class="">';
		if(url === ''){//
			html += '<a data-url="'+url+'" href="#'+url+'" class="'+a_cls+'">';	
		}else{
			var _t = generateMixed(32);
			html += '<a id="menu_id_'+_t+'" href="#'+url+'" data-url="'+url+'"  class="'+a_cls+'" onclick=tduid('+_t+')>';	
		}
		html += '<i class="menu-icon fa '+menu_cls+'"></i>';
		html += '<span class="menu-text"> '+text+' </span>';
		html += down_icon;
		html += '</a>';
		html += '<b class="arrow"></b>';
		
		if(children.length > 0){
			html += getMenu(children,level + 1);
		}
		html += "</li>";
	}
	if(level > 1){
		html += "</ul>";
	}
	
	return html;
}

/**
 * 查询菜单并显示在指定的对象内
 * @param obj 需要显示的对象
 * @param option 请求参数
 */
function queryMenuTo(obj,option){
	var url = 'auth/roledo/getRoleMenuForadd.do';
	var data = {};
	if(option){
		url = option['url'] ? option['url'] : url;
		data = option['data'] ? option['data'] : {};
	}
	$.post(url,data,function(data){
		//获取菜单数组(ace_tree格式)
		var tree_data = menuToTreeArray(data,1);
		//渲染菜单树
		
		tdTree(obj,tree_data);
		
	},"json");
//	$.post("auth/queryAuthMenu.do",{"parentId":"00"},function(data){
//		//获取菜单数组(ace_tree格式)
//		var tree_data = menuToTreeArray(data,1);
//		
//		//渲染菜单树
//		tdTree(obj,tree_data);
//		
//	},"json");
}

/**
 * 获取显示菜单树
 * @param menus
 * @param level
 */
function menuToTreeArray(menus,level){
	var tree_data = {};
	
	for(var i = 0 ; i < menus.length ; i ++){
		var menu = menus[i];
//		var url = menu['attributes']['url'] || '';
		var menuId = menu['id'];
		var text = menu['text'];
		var checked = menu['checked'];
//		var menu_cls = menu['menuCls'] || 'fa-list-alt';
		
		var menuAttr = menu['attributes'];
		
		var children = menu['children'] || {};
		
		var attr = {};
		
		attr['data-icon'] = "ace-icon fa fa-check";//选中图标“勾”
		attr['menuId'] = menuId;//设置菜单ID
//		attr['menuType'] = menu['menuType'];
		
		//如果菜单被禁用了，不显示 if(menuAttr && menuAttr['menuStatus'] == '1'){
		if(menuAttr && menuAttr['menuStatus'] == '0'){
			continue;
		}
		
		tree_data[menuId] = {text:text,type:children.length > 0 ? 'folder' : 'item',attr:attr};
		
		if(children.length > 0){
			tree_data[menuId]['additionalParameters'] = {children : menuToTreeArray(children,level+1)};
			//如果子集是按钮，就不展开了，全部展开铺的面积比较广
//			alert(menuAttr['menuStatus']);
//			if(children[0]['attributes'] && children[0]['attributes']['menuType'] == '0'){
				tree_data[menuId]['additionalParameters']['item-selected'] = true;
//			}
		}else{
			if(checked === true){
				attr['className'] = "tree-selected";//选中状态
//				tree_data[menuId]['attr'] = $.extend({},tree_data[menuId]['attr'],attr);
			}
//			tree_data[menuId]['additionalParameters']['item-selected'] = true;
//			tree_data[menuId]['item-selected'] = true;
		}
		
		
	}
	
	return tree_data;
}

function openPanel(url){
	
}
//
//var menuHtml='<li class=""><a data-url="page/buttons.html" href="#page/buttons.html"> '+
//'<i class="menu-icon fa fa-tachometer"></i> <span class="menu-text">Dashboard </span>'+
//'</a> <b class="arrow"></b></li>';
//var sub1Html='';
//var sub2Html='';
//function menuLoop(arr,level){
//	level+=1;
//    for(var i = 0 ; i < arr.length ; i ++)
//    {	
//    	var fartherMenuHtml='';
//    	var subMenuUl='<ul class="submenu">#1</ul>';
//    	var subMenuLi='';
//    	
//		var name=arr[i].text;
//		var url=arr[i].url;
//    	if(level==1){
//    		menuHtml=menuHtml.replace(new RegExp("#2","i"), sub1Html);
//    		sub1Html='';
//    		sub2Html='';
//    		fartherMenuHtml='<li class=""><a href="#" class="dropdown-toggle">'+
//    		'<class="menu-icon fa fa-desktop"></i> <span class="menu-text">#1</span> '+
//    		'<b class="arrow fa fa-angle-down"></b></a> <b class="arrow"></b>#2</li>';
//    		menuHtml+=fartherMenuHtml.replace(new RegExp("#1","i"), name);
//    	}else if(level==2){
//			subMenuUl=subMenuUl.replace(new RegExp("#1","i"), sub2Html);
//			sub1Html=sub1Html.replace(new RegExp("#2","i"), subMenuUl);
//			
//			subMenuLi='<li class=""><a href="javascript:;" class="dropdown-toggle"> '+
//			'<i class="menu-icon fa fa-caret-right"></i> #1<b class="arrow fa fa-angle-down"></b>'+
//			'</a> <b class="arrow"></b>#2</li>';
//			subMenuLi=subMenuLi.replace(new RegExp("#1","i"), name);
//			sub1Html+=subMenuLi;
//    	}else{
//			subMenuLi='<li class=""><a href="#1" class="dropdown-toggle"> '+
//			'<i class="menu-icon fa fa-caret-right"></i> #2<b class="arrow fa fa-angle-down"></b>'+
//			'</a> <b class="arrow"></b></li>';
//			subMenuLi=subMenuLi.replace(new RegExp("#1","i"), url);
//			subMenuLi=subMenuLi.replace(new RegExp("#2","i"), name);
//			sub2Html+=subMenuLi;
//    	}
//        if(arr[i].children instanceof Array&&arr[i].children.length>0) {
//        	menuLoop(arr[i].children,level);
//        }
//    }
//    console.info("结果:"+menuHtml);
//    console.info("菜单级数:"+level);
//    return menuHtml;
//}
function tduid(_t){
	var _obj  = document.getElementById("menu_id_"+_t);
	var _href = _obj.data-url;
	//alert(_href);
	_obj.target = "?_t=" + generateMixed(32); 
	// window.location = _href +generateMixed(32); 
}

var _chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

function generateMixed(n) {
     var res = "";
     for(var i = 0; i < n ; i ++) {
         var id = Math.ceil(Math.random()*35);
         res += _chars[id];
     }
     return res;
}

