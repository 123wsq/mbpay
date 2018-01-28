// 分页组件----------------------------------------------------------START

function initPagingBar(gtable){
	//var paging_info = " &nbsp;页次:&nbsp;<label id='current_page_lbl'>{0}</label>/{1} &nbsp;<label id='total_count_lbl'>共 {2} 条记录</label>，&nbsp;10条/页"  
	var page = $(gtable).getGridParam('page');
	pagebarLoad(gtable,page);
}

//渲染
function pagebarLoad(gtable,page){
	
	var records = $(gtable).jqGrid('getGridParam','records');
	var rowNum  = $(gtable).jqGrid('getGridParam','rowNum');
	var pageTotal  = parseInt((records + rowNum -1) / rowNum);
    var pageinfo ="";
    var pagebar = "";
    var page_1 = 1;
    var total = 0;
    var pageindex = "";
    var len = 4;  //分页按钮个数
	if(pageTotal >=len ){
		if(page >= len-1){
			page_1 = page -2;
		}
		total = parseInt(page_1)+len;
		if(total >= pageTotal){
			total  = pageTotal;
			page_1 = pageTotal - len;
		}
		page_1=page_1==0?1:page_1;
		for (var i=page_1;i<=total;i++){
			if(i == parseInt(page_1)+3){
				flag = true;
			}
			if(i < 10){
				pageindex = "&nbsp;"+i+"&nbsp;";
			}else{
				pageindex = i;
			}
			if(page == i){
				pageinfo  = pageinfo + "<li class=\"active\"><a href=\"javascript:pageIndex('"+gtable+"','"+i+"')\">"+pageindex+"</a></li>";
			}else{
				pageinfo  = pageinfo + "<li><a href=\"javascript:pageIndex('"+gtable+"','"+i+"')\">"+pageindex+"</a></li>";
			}
		}


	}else{
		for (var i=1;i<=pageTotal;i++)
		{
			if(page == i){
				pageinfo  = pageinfo + "<li class=\"active\"><a href=\"javascript:pageIndex('"+gtable+"','"+i+"')\">"+i+"</a></li>";
			}else{
				pageinfo  = pageinfo + "<li><a href=\"javascript:pageIndex('"+gtable+"','"+i+"')\">"+i+"</a></li>";
			}
		}
	}
	
	
	pagebar = "<ul class=\"pagination pull-right no-margin\">" +
//	"<li class=\"prev disabled\">" + 
//	"	<a href=\"#\">" +
//	"		<i class=\"ace-icon fa fa-angle-double-left\"></i>" +
//	"	</a>" +
//	"</li>" +
	"<li >" +
	"	<a>共"+records+"条</a>" +
	"</li>" ;
	if(page == 1){
		pagebar = pagebar +
		"<li class=\"prev disabled\">" +
		"	<a href=\"javascript:pageIndex('"+gtable+"','1')\">首页</a>" +
		"</li>" +
		"<li class=\"prev disabled\">" +
		"	<a href=\"javascript:pageUp('"+gtable+"')\">上一页</a>" +
		"</li>" ;
	}else{
		pagebar = pagebar +
		"<li >" +
		"	<a href=\"javascript:pageHead('"+gtable+"')\">首页</a>" +
		"</li>" +
		"<li >" +
		"	<a href=\"javascript:pageUp('"+gtable+"')\">上一页</a>" +
		"</li>" ;
	}
	pagebar = pagebar +pageinfo;
	
	 

//	"<li class=\"next\">" +
//	"	<a href=\"#\">" +
//	"		<i class=\"ace-icon fa fa-angle-double-right\"></i>" +
//	"	</a>" +
//	"</li>" +
	if(page == pageTotal){
		pagebar = pagebar +
		"<li class=\"prev disabled\">" +
		"	<a href=\"javascript:pageNext('"+gtable+"')\">下一页</a>" +
		"</li>" +
		"<li class=\"prev disabled\">" +
		"	<a href=\"javascript:pageIndex('"+gtable+"','"+pageTotal+"')\">尾页</a>" +
		"</li>" ;
	}else{
		pagebar = pagebar +
		"<li>" +
		"	<a href=\"javascript:pageNext('"+gtable+"')\">下一页</a>" +
		"</li>" +
		"<li>" +
		"	<a href=\"javascript:pageTail('"+gtable+"')\">尾页</a>" +
		"</li>" ;
	}
	pagebar = pagebar +
//	"<li >" +
//	"	<a>第"+page+"页</a>" +
//	"</li>" +
	"<li >" +
	"	<a>第"+(pageTotal==0?0:page)+"/"+pageTotal+"页</a>" +
	"</li>" ;
	
	pagebar = pagebar + "</ul>";	
	
	var $paging_bar = $("#paging_bar");
	$paging_bar.html( pagebar);
	
	
}

//制定页跳转
function pageIndex(gtable,index){
	var grid_url  = $(gtable).getGridParam("url");
	
	pagebarLoad(gtable,index);
	jQuery(gtable).jqGrid('setGridParam', {
        url : grid_url,
        page:index
        //postData: $.toJSON(params)
    }).trigger("reloadGrid");
}
//上一页
function pageUp(gtable){
	var grid_url  = $(gtable).getGridParam("url");
	var up = parseInt($(gtable).getGridParam('page'))-1;
	if(up == 0){
		return ;
	}
	pagebarLoad(gtable,up);
	jQuery(gtable).jqGrid('setGridParam', {
        url : grid_url,
        page:up
    }).trigger("reloadGrid");
}
//下一页
function pageNext(gtable){
	
	var grid_url  = $(gtable).getGridParam("url");
	var next = parseInt($(gtable).getGridParam('page'))+1;
	
	var records = $(gtable).jqGrid('getGridParam','records');
	var rowNum = $(gtable).jqGrid('getGridParam','rowNum');
	var pages  = parseInt((records + rowNum -1) / rowNum);
	if(pages+1 == next){
		return ;
	}
	pagebarLoad(gtable,next);
	jQuery(gtable).jqGrid('setGridParam', {
        url : grid_url,
        page:next
    }).trigger("reloadGrid");
	
}

function pageHead(gtable){
	var grid_url  = $(gtable).getGridParam("url");
	var next = 1;
	
	pagebarLoad(gtable,next);
	jQuery(gtable).jqGrid('setGridParam', {
        url : grid_url,
        page:next
    }).trigger("reloadGrid");
}
function pageTail(gtable){
	var grid_url  = $(gtable).getGridParam("url");
	var records = $(gtable).jqGrid('getGridParam','records');
	var rowNum = $(gtable).jqGrid('getGridParam','rowNum');
	var pageTotal  = parseInt((records + rowNum -1) / rowNum);

	var next = pageTotal;
	pagebarLoad(gtable,next);
	jQuery(gtable).jqGrid('setGridParam', {
        url : grid_url,
        page:next
    }).trigger("reloadGrid");
}
//分页组件----------------------------------------------------------END