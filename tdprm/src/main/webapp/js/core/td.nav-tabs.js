
/**
 * xiejinzhong
 * 小标签页上一步下一步切换功能
 * 页面多标签尽量都用改js组件控制
 * 支持自动创建上一步、下一步按钮
 * 支持控制保存按钮的自动显示与隐藏（提交按钮选择代码：$('button[type="submit"],.btn-submit')）
 * 如果保存按钮不需要由改组件控制，可以在class上面添加“no-hidden”
 * 
 * 属性介绍 ：
 * 	class 包含 “tabbable” 则会进行处理
 *  create-buttom="auto" 表示自动创建按钮
 *  create-to 该属性指定自动创建的按钮显示的容器 ID
 *  
 * 
 **/
$.fn.extend({
	navTabsActive : function(){
		$(this).filter('.tabbable').each(function(){
			var $box = $(this);
			var navUl = $('.nav',$box);
			//如果有该属性并且有元素的id等于该值，将会把上一步、下一步的按钮创建在里面
			var createTo = $box.attr('create-to');
			var navTabs = navUl.children().filter('li');
			var $formBtnBox = null;
			var createButton = $box.attr('create-buttom');
			if(navTabs.length && createButton){
				if(createButton==='auto' || createButton == 'ture'){
					var html = '<button class="btn btn-info nav-bnt" type="button">\
						上一步\
						</button>\
						<button class="btn btn-info nav-bnt" type="button">\
						下一步\
						</button>';
					
					if(createTo){
						$formBtnBox = $('#'+createTo);
					}else{
						$formBtnBox = $('.form-button-box',$box);
					}
					if(!$formBtnBox || $formBtnBox.length == 0){
						return ;
					}
					$formBtnBox.html(html+$formBtnBox.html());
				}
			}
			
			$('.nav-bnt',$formBtnBox).on('click',function(){
				$(this).navTabsChange($box);
			});
			$('a',navTabs).on('click',function(){
				setTimeout(function(){$box.navTabsInit();} ,200);
			});
			
			//提交按钮
			var submitBtn = $('button[type="submit"],.btn-submit',$formBtnBox);
			submitBtn.on('click',function(event){//提交前验证表单，如果有哪一页不通过，则将该页设置为活动页
				var ok = true;
				navTabs.each(function(idx){
					var $active = $(navTabs.get(idx));
					
					var activeNav = $('a',$active);
					var navId = activeNav.attr('href');
					navId = navId.replace($.getNowHref(),'');
					var activeDiv = $(navId);
					ok = TDValidateForm(activeDiv);
					if(ok==false){
//						alert($('.jq_tips_box').length);
						$('.jq_tips_box').remove();
						activeNav.trigger('click');
						setTimeout(function(){
							TDValidateForm(activeDiv);
						}, 500);
						return false;
					}
					
				});
				
				return ok;
			});
//			navTabs.on('click',function(){
//				setTimeout(function(){$box.navTabsInit();} ,200);
//			});
			
			$box.navTabsInit();
			
		});
	},
	navTabsInit : function(){
		$(this).each(function(){
			var $box = $(this);
			var navUl = $('.nav',$box);
			var navTabs = navUl.children().filter('li');
			var createTo = $box.attr('create-to');
			var $formBtnBox = null;
			if(createTo){
				$formBtnBox = $('#'+createTo);
			}else{
				$formBtnBox = $('.form-button-box',$box);
			}
			//当前活动的下标
			var activeInx = navTabs.filter('.active').index();
			
			var navBtns = $('.nav-bnt',$formBtnBox);
			navBtns.hide();
			
			var submitBtn = $('button[type="submit"],.btn-submit',$formBtnBox);
			
			//需要控制的才控制 no-hidden 表示不需要组件自动控制该按钮
			submitBtn = submitBtn.not(submitBtn.filter(".no-hidden"));
			submitBtn.hide();
//			navBtns.show();
			//如果没有或者只有一个，不需要上一步下一步按钮
			if(navTabs.length == 0 || navTabs.length == 1){
				navBtns.hide();
				submitBtn.show();
			}else if(activeInx == navTabs.length - 1){//如果是最后一步隐藏下一步，显示上一步
				$(navBtns.get(0)).show();
				submitBtn.show();
			}else if(activeInx == 0){
				$(navBtns.get(0)).hide();
				$(navBtns.get(1)).show();
			}else{//是中间不走同时显示上一步和下一步
				$(navBtns.get(0)).show();
				$(navBtns.get(1)).show();
			}
		});

		
	},
	navTabsChange : function($box){
		$(this).each(function(){
			var $formBtnBox = null;
			var createTo = $box.attr('create-to');
			if(createTo){
				$formBtnBox = $('#'+createTo);
			}else{
				$formBtnBox = $('.form-button-box',$box);
			}
			var navBtns = $('.nav-bnt',$formBtnBox);
			var idx = navBtns.filter($(this)).index();
			var navUl = $('.nav',$box);
			var navTabs = navUl.children().filter('li');//活动选项
			var checkForm = $box.attr('check-form');
			//当前活动的下标
			var activeInx = navTabs.filter('.active').index();
			if(idx == 0){//上一步
				if(activeInx > 0){
					$('a',$(navTabs.get(activeInx - 1))).trigger('click');
					$box.navTabsInit();
				}
			}else if(idx == 1){//下一步
				if(activeInx < navTabs.length - 1){
					//如果要验证当页表单
					var $active = $(navTabs.get(activeInx));
					
					var activeNav = $('a',$active);
					var navId = activeNav.attr('href');
					navId = navId.replace($.getNowHref(),'');
					var activeDiv = $(navId);
					if(checkForm == 'true'){
						var ok = TDValidateForm(activeDiv);
						if(ok==false){
							return;
						}
					}
					$('a',$(navTabs.get(activeInx + 1))).trigger('click');
					$box.navTabsInit();
				}
			}
		});
	}
});


function navTabsActive($box){
	$box = $($box);
	
	var navTabs = $('.nav',$box).children().filter('li');
	
	var createButton = $box.attr('create-buttom');
	if(createButton){
		if(createButton==='auto' || createButton == 'ture'){
			
		}
	}
	
}