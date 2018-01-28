/**
 *@author xiejinzhong
 * 
 * 对话框组件  
 * 
 * 	其中包含
 * 	打开指定页面并带参数
 * 	填充表单元素
 * 	与打开来源的页面进行参数传递
 * 	获取指定的对话框
 * 	获取所有打开的对话框
 * 	打开文件上传
 * 	……………………
 * 	可查看对象中定义的属性及说明
 * 
 */


//根据对话框Id转换为对话框对象:$("#dialogId").Dialog().dialogId;
$.fn.extend({
	Dialog:function(key){
		var $dilog = $(this);
		if($dilog && $dilog.length >0){
			var id = $dilog.attr('id');
			return key ? dialog.getById(id)[key] :dialog.getById(id);
		}
		return undefined;
	},
	dialog:function(key){
		return $(this).Dialog(key);
	},
	dialogClose:function(){
		return $(this).Dialog().close();
	}
});


var dialogMap = {};

var TdFileUploadDialogId = "td-file-upload-dailog";//文件上传对话框ID

var dialog = {
	title:'',//标题
	dataUrl:'',//要初始化表单的数据请求地址
	pageUrl:'',//要加载的页面地址(如果不填，以html为准)
	pageParam:{},//请求页面时的请求参数
	dataParam:{},//请求初始化数据时带的参数
	dataName:'',//返回的数据存储名称，空表示直接返回的对象就是参数json，可用 data、data.params、data.params.form 等无限制级别取值
	mask:true,//是否显示遮罩层(默认显示)
	maskObj: null,//遮罩层对象
	move:true,//是否可移动，默认可移动
	html:'',//要展示的内容
	formData:{},//要填充的表单元素 
	btnSettings : false,//设置按钮
	btnReload:false,//刷新按钮
	btnCollapse:false,// 关闭/展开 按钮
	btnClose:true,// 关闭按钮
	className:"dialog_box",
	fileUploadDone:function(result){this.close();},
	zIndex:function(zIdx){
		if(TD.isNotEmpty(zIdx)){
			this.dialogBox().css("z-index",zIdx);
			return zIdx;
		}
		
		return this.dialogBox().css("z-index");
	},
	putMap:function(){//将对话框加入到对话框集合
		dialogMap[this.dialogId] = this;
	},
	getById:function(id){//根据ID获取对话框对象
		return dialogMap[id] || {};
	},
	getFileUploadDialog:function(){//获取打开的文件上传对话框对象
		return this.getById(TdFileUploadDialogId);
	},
	getByChildren:function(elm){//根据传入的对话框的子节点对象来找到当前的对话框容器
		var $cld = $(elm);
		if($cld.length == 0){//没找到指定元素返回最后一个打开的dialog
			return dialog.last.dialogBox();
		}
		var $box = undefined;
		
		//解决查找当前对话框错误的bug
		$box = $cld.parents("." + this.className);
//		$("." + this.className).each(function(){
//			if($(this) === $cld){//如果等于自己，也返回当前对话框
//				$box = $(this);
//				return false;
//			}else if($(this).children($cld).find($cld)){//如果当前对话框存在传入的元素，表示该对话框就是传入元素所在对话框
//				$box = $(this);
//				return false;//匹配到第一个就返回
//			}
//		});
		return $box;
	},
	deleteMap:function(){//删除对话框管理对象中当前对话框
		delete dialogMap[this.dialogId];
	},
	last:{},//最后一个打开的对话框对象
	lastByMap:function(){//到对话框管理列表中找到最后一个打开的对话框
		var $d = {};
		for (var i in dialogMap) $d = dialogMap[i];
		return $d;
	},
	dialogBox:function(){return $('#' + this.dialogId);},//获取当前对话框对象，jquery html element
	dialogBodyBox:function(){return $('.widget-body',this.dialogBox());},//获取对话框的body对象
	dialogBoxHtm:function(){//生成对话框组件html
		//定义的宽度
		if(!isNaN(this.width)){
		   this.width = this.width + 'px';
		}
		//定义的高度
		if(!isNaN(this.height)){
		   this.height = this.height + 'px';
		}
		//widget-header widget-header-blue widget-header-flat
		return '<div id="'+this.dialogId+'" class="dialog_box" style="'+(this.width ? 'width:' + this.width + ';' : '')+ (this.height ? 'height:' + this.height + ';': '') 
		+ '"><div class="widget-box" style="width:100%;height:100%"><div class="widget-header widget-header-flat"><h4 class="widget-title">'
		+this.title+'</h4><span class="widget-toolbar">'+this.toolBar()+'</span></div><div style="display: block;" class="widget-body" style="width:100%;height:100%;"></div></div></div>';
	},
	toolBar:function(){//生成对话框顶部工具条
		var tool = '';
		if(this.btnSettings){
			tool += '<a href="javascript:void(0)" ><i class="ace-icon fa fa-cog"></i></a>';
		}
		if(this.btnReload){
			tool += '<a href="javascript:void(0)" data-action="reload"><i  class="ace-icon fa fa-refresh"></i></a>';
		}
		if(this.btnCollapse){
			tool += '<a href="javascript:void(0)" ><i class="ace-icon fa fa-chevron-up"></i></a>';
		}
		if(this.btnClose){
			tool += '<a href="javascript:void(0)" data-action="close" ><i class="ace-icon fa fa-times"></i></a>';
		}
		return tool;
	},
	refresh:function(){//重新加载
		
	},
	dialogId:'',//对话框ID
	idNum:0,
	width:'',
	height:'',
	closeBy:function(obj){//根据对话框id或对话框内的子元素或当前对话框自己来关闭对话框
		if(typeof obj === 'string'){
			obj = dialog.getById(obj);
		}
		var d = dialog.getByChildren(obj);
		$(d).dialogClose();
	},
	close:function(obj){//关闭对话框
		var $this=this;//默认关闭自己 ，如果传了要关闭的对象，则关闭指定对话框
		if(obj){
			if(typeof obj === 'string'){//如果是传的string类型，认为是对话框ID
				$this = dialog.getById(obj);
			}else{
				//如果是传的对象(对话框内元素或对话框对象)，根据该对象找出所在对话框对象
				$this = dialog.getByChildren(obj).dialog();
			}
		}
		
		var $dialogBox = $this.dialogBox();//获取对话框文档对象
		if($this.dialogBox().length == 0){//如果没有取到，则关闭所有
			$this = dialog.last;
			$dialogBox = $this.dialogBox();//获取最后一个打开的对话框文档对象
		}
		
		if($this.onClose($this) !== false){
			if(!$this.pageUrl){
				$(".dialog_list").append($this.html);//关闭对话框的时候将内容恢复至原来位置
			}
			$dialogBox.remove();//删除对话框对象
			$this.deleteMap();//从对话框列表中删除当前对话框对象
			if(dialog.last === $this){//如果关闭的是最后一个，将最后一个对话框对象置空
				dialog.last = dialog.lastByMap();
			}
			if($this.mask){
				mask.hide($this.maskObj);//关闭当前对话框打开的遮罩层
			}
			return true;
		}else{
			return false;
		}
	},
	closeById:function(id){//根据对话框Id关闭对话框
		$("#" + id).trigger("close");
	},
	closeLast:function(){//关闭最后一个打开的对话框
		dialog.last.close();
	},
	closeAll:function(){//关闭所有打开的对话框
		$("." + this.className).each(function(){
			var $box = $(this);
			$box.dialogClose();
		});
	},
	onClose:function($this){return true;},//关闭前执行的函数,返回false可阻止关闭
	onOpen:function($this){},//打开对话框前执行的函数，返回false可阻止返回
	open:function(){//打开对话框
		this.onOpen(this);
		
		//默认递增的ID
		if(!this.dialogId){
			var d_id = "dialog_box_x_j_z_" + dialog.idNum;
			dialog.idNum = dialog.idNum + 1;
			this.dialogId = d_id;
		}
		
		dialog.last = this;//将最后一个打开的对话框置为当前对话框
		
		$('body').append(this.dialogBoxHtm());//将对话框添加到body里面
		
		//添加到对话框集合
		this.putMap();
		
		this.onEvent();//绑定对话框事件
		this.loadBody();//加载页面以及数据
		
		return this;
	},
	loadBody:function(){//加载内容 如果有指定页面地址，则加载远程页面，如果没有指定地址，则会显示传入的html内容
		var $this = this;
		var $obj = $this.dialogBodyBox();
		if(this.pageUrl){
			this.load($this.pageUrl, $this.pageParam, 'html',function(html){
				$this.drawingBody($this,html);
			});
		}else{
			$obj.html($this,$this.html);
		}
		
	},
	loadData:function(){//加载表单初始化数据
		var $this = this;
		if($this.dataUrl){
			$this.load($this.dataUrl, $this.dataParam, 'json',function(data){
				$this.drawingForm($this,data);
//				$this.formData = $.extend($this.formData,data);
//				$.extend($this.formData,data);
			});
		}else{
			$this.drawingForm($this,$this.formData);
		}

	},
	load:function(url,data,dataType,success){//ajax
		$.ajax({ url: url,type:"POST",data:data ,dataType:dataType,success:success});
	},
	drawingBody:function($this,html){//渲染弹出层内容
		$this.html = html;
//		alert($this.dialogBodyBox().length);
		$this.dialogBodyBox().html(html);
		//加载完body后初始化数据
		$this.loadData();
	},
	drawingForm:function($this,params){//渲染表单
		var fData = getJsonDataByName(params,$this.dataName);
		var formData = $.extend($this.formData,fData);
//		for(var key in formData){
//			alertMsg.info(key + ":" + formData[key]);
//		}
		
		var $form = $('form',$this.dialogBodyBox());
		
		if(typeof fillForm === 'function'){
			fillForm($form,formData);
		}else{
			alertMsg.warn("表单填充函数[fillForm]未找到");
		}
		$this.layout();//显示对话框并渲染
		$this.loaded();//触发加载完成事件
		$this.layout();//防止布局渲染有不正常的情况，加载完成后再次渲染
//		setTimeout(function(){$this.layout();},0);//重新布局
		if(typeof loadDone === 'function'){
			loadDone($this.html,$this.dialogBox());//调用页面加载完成的处理函数
		}
		$this.loadDone($this);//触发加载完成
	},
	layout:function(){//布局控制函数
		var $box = this.dialogBox();
		
		var htmlBodyWidth = $('body').width();
		var htmlBodyHeight = $('body').height();
		
		//对话框内容部分样式设置
		var $widgetBody = $('.widget-body',$box);
		
		var dialogWidth = $box.width();//对话框宽度
		var dialogHeigth = $box.height();//对话框高度
		
		//如果对话框大小超出了当前窗口的大小，则设置和body一样大
//		if(dialogWidth > htmlBodyWidth){
//			$box.css('width',(htmlBodyWidth - 20) + 'px');
//			dialogWidth = $box.width();
//		}
//		if(dialogHeigth > htmlBodyHeight){
//			$box.css('height',(dialogHeigth-20) + 'px');
//			dialogHeigth = $box.height();
//		}
//		$box.css({
//			'width':dialogWidth + 'px',
//			'height':dialogHeigth + 'px'
//		});
		
		var width = $box.width();
		var height = $box.height();
		//对话框布局
		$box.css({
			"margin-top" : (height / 2) * -1,
			"margin-left" : (width / 2) * -1,
			"left" : '50%',
			"top" : '50%'
		});
		
//		dialogHeigth = $box.height();
		var widgetBodyHeigth = dialogHeigth;
		
		widgetBodyHeigth = widgetBodyHeigth - 40;//减去对话框顶部标题栏高度
		
		//如果有下面的确定/关闭等按钮
		var $btnBox = $('.form-button-box',$widgetBody);
		if($btnBox.length > 0){
			widgetBodyHeigth = widgetBodyHeigth - 47;//减去按钮组件高度
		}
		$widgetBody.css({
			'height': widgetBodyHeigth + 'px',
			'width':'100%',
			'overflow-x' : 'hidden',
			'overflow-y' : 'auto',
		});
//		$('.dialog-page').css({
//			'height': widgetBodyHeigth + 'px',
//			'width':'100%',
//			'overflow' : 'hidden',
//		});
		
		
	},
	moveBox:function(x,y){//移动对话框的函数(已废弃,由统一的移动处理函数去处理)
		var $box = this.dialogBox();
//		var x = this.layoutX();
//		alertMsg.info("x:" + (mouseX - x) + ",y:" + (mouseY - y));
		var left = parseFloat($box.css("left"));
		var top = parseFloat($box.css("top"));
//		alertMsg.info("left:" + left + ",x:" + x);
		if(x != 0){
			$box.css({
				left:(left + x * - 1) + 'px'
			});
		}
		if(y != 0){
			$box.css({
				top: (top + y * - 1) + 'px'
			});
		}
		
//		$box.css({
//			"top" : (height / 2) * -1,
//			"margin-left" : (width / 2) * -1
//		});
	},
	mouseup:function(){
		
	},
	dialogHeardBox:function(){
		return $('.widget-header',this.dialogBox());
		
	},
	onEvent:function(){//绑定事件
		var $this = this;
		var $dialogBox = this.dialogBox();
		
		//顶部工具栏按钮事件绑定
		$('a',$dialogBox).on('click',function(event){
			var data_action = $(this).attr('data-action');
			switch(data_action){
				case 'reload' :
					event.stopPropagation();
					break;
				case 'close' :
					if($this.close() === false){
					}
					event.stopPropagation();
					break;
			}
		});
		
		if($this.move == true){//允许移动
			//工具条事件绑定，用于拖动
			if(typeof moveObject === 'function'){
				//调用拖动处理
				moveObject($this.dialogHeardBox(),$dialogBox,function($box,x,y){$this.moveBox(x, y);});
			}
		}
		
		//绑定对话框事件
		$dialogBox.on('close',function(){
			$this.close();
		}).on('resize',function(){
			$this.layout();
		});
		
//		$dialogBox.fn.close = function(){alert('这也行');};
		
	},
	resize:function(){
		$('.dialog_box').triggerHandler('resize');
	},
	loaded:function(){
		//如果需要打开遮罩层并且这个对话框没打开过遮罩层(对话框刷新的时候不需要再次打开)
		if(this.mask && !this.maskObj){
			this.maskObj = mask.show();
		}
		var $dialogBox = this.dialogBox();
		//让对话框显示在遮罩层之上
		mask.onTopOf(this.maskObj, $dialogBox);
		
		//显示对话框
		$dialogBox.show();
	},
	loadDone:function($this){}//加载完成后执行
	
};

/**
 * 打开对话框,参数说明：
 * {
 * 	target:$('#id'),//要展示的内容对象
 * 	
 * 
 * 	title:'对话框标题',
 * 	dataUrl:'要初始化表单的数据请求地址',
 * 	dataParam:{请求初始化数据时带的参数}',
 * 	pageUrl:'要加载的页面地址(如果不填，以html为准)',
 * 	pageParam:{请求页面时的请求参数},
 * 	dataName:'ajax请求结果中数据节点名称，支持 a.b.c'，
 * 	formData：{要填充的表单元素},
 * 	reload:true,//刷新按钮是否显示
 * 	collapse:false,//伸缩按钮是否显示
 * 	………………
 * 	更多可参看 dialog对象属性说明
 * }
 */
var openDialog = function(params){
	params = params || {};
	var $target = $(params['target']);
	var html = '';
	html = $target.prop("outerHTML");
	
	//先复制一份对话框对象，以免影响原来的对象的值
	var $dialog = $.extend(true,{},dialog);
	
	$.extend($dialog,{
		html:html,
		onClose:function($dialog){
			
			return true;
		},
		onOpen:function($dialog){
			$target.remove();//将内容复制到对话框后删除原来位置的内容(内容移动)
			return true;
		}
	},params).open();
	
//	var d = dialog.getById($dialog.dialogId);
//	for (var i in dialogMap) alert(i + ':' + dialogMap[i]);
//	alert(dialogMap.length);
	return $dialog;
};

var openFileUpload = function(params){
	params = params || {};
	//先复制一份对话框对象，以免影响原来的对象的值
	var $dialog = $.extend(true,{},dialog);
	var dialogId = TdFileUploadDialogId;
	//默认属性设置
	var def_params = {
		title:'文件上传',
		width :'40%',
		height:'50%',
		btnClose:false,// 关闭按钮
		btnReload:false,//刷新按钮
		titleShow:false,
		dataParam: {
			allowExt : params.allowFile,
			fileSize:params.fileSize
		}
	};
	//固定属性设置  url和dialogId是固定的
	var fixed_params = {
		pageUrl : 'mpbase/fileUploadManage.do',
		dialogId:dialogId
	};
	var d = dialog.getById(dialogId);
	if(d && d.dialogId === dialogId){
		alertMsg.error("不能同时打开多个文件上传对话框");
		return d;
	}
	$.extend($dialog,{
		onClose:function($dialog){
			
			return true;
		},
		onOpen:function($dialog){
			return true;
		}
	},def_params,params,fixed_params).open();
	
	return $dialog;
};

/**
 * 浏览器大小改变，对话框位置随之变化
 */
window.onresize = function(){
	dialog.resize();
};


