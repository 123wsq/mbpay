/***
 * ajax页面加载完成后执行函数，可在此处做加载完成后效果处理
 * @param html
 */
function loadDone(html,$container){
	// /*给 井号“#”开头的“a”标签加上当前页面路径*/
	$.baseHref().loaded();
	
	//页面加载完成后,设置按钮权限
	TDAuth();

	$container = $container || $("body");

	//下拉框
	$(".chzn-select",$container).chosen(); 
	
	//左右单选开关
	$('.ace-switch').aceSwitchInit();
	
	//nav-tabs切换功能
	$(".tabbable",$container).navTabsActive(); 
	
	//加载动态下拉列表
	$("select[location]",$container).selLoad();
	
	var $jqgridVdiv = $(".ui-jqgrid-bdiv",$container);
	$jqgridVdiv.css({"overflow":"auto"});//让表格不出现滚动条
	$jqgridVdiv.css({"width":($jqgridVdiv.width() + 1) + 'px'});
	
	//渲染日期控件
	$('.input-daterange',$container).datepicker({autoclose:true});
//	alert($('.input-daterange').length);
	$('.date-picker').datepicker({
		autoclose: true,
		todayHighlight: true
	});
	//文件上传控件
	$('#id-input-file-1 , #id-input-file-2').ace_file_input({
		no_file:'No File ...',
		btn_choose:'Choose',
		btn_change:'Change',
		droppable:false,
		onchange:null,
		thumbnail:false //| true | large
		//whitelist:'gif|png|jpg|jpeg'
		//blacklist:'exe|php'
		//onchange:''
		//
	});
	$('#id-input-file-3').ace_file_input({
		style:'well',
		btn_choose:'将文件拖入或单击选择',
		btn_change:null,
		no_icon:'ace-icon fa fa-cloud-upload',
		droppable:true,
		thumbnail:'small'//large | fit
		//,icon_remove:null//set null, to hide remove/reset button
		/**,before_change:function(files, dropped) {
			//Check an example below
			//or examples/file-upload.html
			return true;
		}*/
		/**,before_remove : function() {
			return true;
		}*/
		,
		preview_error : function(filename, error_code) {
			//name of the file that failed
			//error_code values
			//1 = 'FILE_LOAD_FAILED',
			//2 = 'IMAGE_LOAD_FAILED',
			//3 = 'THUMBNAIL_FAILED'
			//alert(error_code);
		}

	}).on('change', function(){
		//console.log($(this).data('ace_input_files'));
		//console.log($(this).data('ace_input_method'));
	});
	
	

}