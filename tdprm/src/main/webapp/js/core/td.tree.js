/**
 * xiejinzhong 2015-02-28
 * 
 * 树形结构渲染处理
 */

$.fn.extend({
	tdTree : function(){//得到树的对象，id为唯一标识
		var tree = TdTreeMap[$(this).first().attr('id')] || {};
		return tree;
	}
});

//数对象集合
var TdTreeMap = {};

var tdTree = function(treeNode,treeData){
	this.treeNode = treeNode;
	this.$targetObj={};
	this.treeData = treeData;
	this.cls_selected = 'tree-selected';//选中的样式类名
	this.cls_checkBox = 'tree-item';//“复选框”的样式类名
	this.cls_branch = 'tree-branch';//是树枝的li节点的class标识
	this.cls_selected_part = 'tree-selected-part';//选中但没有全选的样式类名（主要针对枝节点）
	this.init = function(){
		var $targetObj = {};
		if(typeof this.$targetObj === 'string'){
			$targetObj = $("#" + this.treeNode);
		}else{
			$targetObj = $(this.treeNode);
		}
		
		//如果存在，重新刷新
		if(TdTreeMap[$targetObj.attr('id')]){
			var treeId = $targetObj.attr('id');
			var $parent = $targetObj.parent();
			$targetObj.remove();
			var treeHtml = '<ul id="'+treeId+'"></ul>';
			$parent.append(treeHtml);
			$targetObj = $("#" + treeId);
		}
		//将创建的对象存入map,id为唯一key
		TdTreeMap[$targetObj.attr('id')] = this;
		this.$targetObj = $targetObj;
		var $this = this;
		var dataSource = function(options, callback){
			var $data = null;
			if(!("text" in options) && !("type" in options)){
				$data = $this.treeData;//the root tree
				callback({ data: $data });
				return;
			}
			else if("type" in options && options.type == "folder") {
				if("additionalParameters" in options && "children" in options.additionalParameters)
					$data = options.additionalParameters.children;
				else $data = {};//no data
			}
			
			if($data != null)//this setTimeout is only for mimicking some random delay
//				setTimeout(function(){callback({ data: $data });} ,0);
				/**加载子节点*/
				callback({ data: $data });
			
			//we have used static data here
			//but you can retrieve your data dynamically from a server using ajax call
			//checkout examples/treeview.html and examples/treeview.js for more info
		};
		$targetObj.ace_tree({
			dataSource: dataSource,
			multiSelect: true,
			cacheItems: true,
			itemLoadDone : function($el){//当前元素加载完成触发
				var $tree_branch = $this.getThisLiElm($el);
//				var $next = $tree_branch.nextAll();
				var $children_branch = $('.tree-branch',$tree_branch);
				//如果还有同级节点，不处理
//				if($next.length > 0){
//					return;
//				}
				//如果子节点还有枝干
				if($children_branch.length > 0){
					return;
				}
//				alert($next.length);
				
				setTimeout(function(){$this.selectBranch($el);}, 0);
				
				setTimeout(function(){
					//关闭最后一层的展开状态
					$('.tree-branch-header',$tree_branch).trigger('click');
				}, 20);
				
				
			},
			selectItem:function(ev){//选择复选框按钮事件,返回false阻止框架处理
				//菜单树选择框叶子节点阻止事件冒泡，为了防止点击枝节点的复选框时触发展开关闭事件
				ev.stopPropagation(); 
				
				//得到当前触发事件的节点
				var $el = $this.getCurrentElm(ev);
//				alert($this.isSelected(ev));
				
				//根据当前节点的选中状态自动改变子节点的选中状态
				$this.selectChildrenByThis($el);
				
				//改变父节点的选中状态
				$this.selectParent($el);
				
				//如果是枝节点，阻止ace框架对选择样式的操作
				if($this.isFolder($el)){
//					alert('isFolder');
					return false;
				}
				
				return false;//阻止ace框架对选择样式的操作
			},
			selectFolder:function(ev){//展开搜索枝节点事件,返回false阻止框架处理
				var $el = $this.getCurrentElm(ev);
//				alert($el.attr('class'));
//				$this.selectParent($el);
				
//				$this.selectBranch($el);
				
				return true;
			},
			selectFolderDone : function(ev){
//				var $el = $this.getCurrentElm(ev);
//				$this.selectBranch($el);
			},
			'open-icon' : 'ace-icon tree-minus',
			'close-icon' : 'ace-icon tree-plus',
			'selectable' : true,
			'selected-icon' : 'ace-icon fa fa-check',
			'unselected-icon' : 'ace-icon fa fa-times',
			loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>'
		});
	};
	
	/**
	 * 判断：如果是叶子，返回 true ，否则返回 false
	 * 	判断规则：自定义如果是li表示是最后一级
	 */
	this.isItem = function($el){
//		if(this.isLi(ev.currentTarget)){
//			return true;
//		}
		//获取当前元素所属的li节点
		var $li = this.getThisLiElm($el);
		//如果是li
		if(this.isLi($li)){
			//如果class属性包含tree-item
			if($li.filter('.' + this.cls_checkBox).length){
				return true;
			}
		}
		return false;
	};
	
	this.getTagName = function($elm){
		if($elm.tagName){
			return $elm.tagName.toUpperCase();
		}else if($elm[0] && $elm[0].tagName){
			return $elm[0].tagName.toUpperCase();
		}
		return '';
	};
	
	this.isLi = function($elm){
		if(this.getTagName($elm) == 'LI'){
			return true;
		}
		return false;
	};
	/**
	 * 判断：如果是枝干，返回 true ，否则返回 false
	 * 	判断规则：自定义如果不是是li表示是还有子集
	 */
	this.isFolder = function($el){
//		return !this.isItem(ev);
		//获取当前元素所属的li节点
		var $li = this.getThisLiElm($el);
		//如果是li
		if(this.isLi($li)){
			//如果class属性包含tree-branch,是树枝
			if($li.filter('.' + this.cls_branch).length){
				return true;
			}
		}
		return false;
	};
	
	
	/***
	 * 如果是选中操作，返回true
	 */
	this.isSelected = function($el){
		$el = $($el);;
//		var cls = $el.filter('.' + this.cls_selected);
		//如果在class属性里面找到了已选择的样式，说明当前操作是在取消
		if($el.filter('.' + this.cls_selected).length){
			return false;
		}
		return true;
	};
	
	this.getCurrentElm = function(ev){
		return $(ev.currentTarget);
	};
	
	this.getThisLiElm = function($elm){
		$elm = $($elm);
		
		//如果找到树根节点还没找到，则返回，防止死循环
		if(!$elm || $elm.length == 0 || $elm.attr('id') === this.$targetObj.attr('id') || this.getTagName($elm) === 'BODY' || $elm == $elm.parent()){
			return {};
		}
		if(this.isLi($elm)){
			return $elm;
		}else{
			return this.getThisLiElm($elm.parent());
		}
	};
	
	this.getSelectedElm = function($el){
		if(!$el){//如果没有指定父节点，搜索范围为当前树
			$el = this.$targetObj;
		}else{
			$el = $($el);
		}
//		var $selectedElm = $('*[class*="tree-selected"]',$el).filter(".tree-selected");
		//根据选中样式“tree-selected”获取已选中的节点
		var $selectedElm = $(".tree-selected",$el);
		return $selectedElm;
	};
	
	// 选中/取消 所有子节点(根据当前节点的状态)
	this.selectChildrenByThis = function($el){
		if(this.isSelected($el)){
			this.selectChildren($el, true);
		}else{
			this.selectChildren($el, false);
		}
	};
	
	// 选中/取消 所有子节点
	this.selectChildren = function($el,sel){
		//获得当前li节点
		$el = this.getThisLiElm($el);
		var $children = {};
		
		if(this.isItem($el)){
			$children = $el;
		}else{
			//得到当前节点下所有复选框节点
			$children = $('.' + this.cls_checkBox,$el);
		}

		//如果是选中
		if(sel == true){
			$children.removeClass(this.cls_selected_part);//删除部分选中样式
			$children.addClass(this.cls_selected);
		}else{//取消选中
			$children.removeClass(this.cls_selected);
			$children.removeClass(this.cls_selected_part);
		}
	};
	
	//设置枝干节点的选择状态
	this.selectBranch = function($el){
		$el = this.getThisLiElm($el);
//		var branch = {};
//		if($el && $el.length){
//			branch = $('*',this.$targetObj).filter($el).filter('.' + this.cls_branch);
//		}else{
//			branch = $('.' + this.cls_branch,this.$targetObj).last();
//		}
//		alert($('li.' + this.cls_branch,$el).length);
		this.selectParent($('.' + this.cls_checkBox,$el).last(),true);
	};
	
	//设置当前枝节点的选中状态
	this.selectThisBranch = function($el){
		$el = this.getThisLiElm($el);
		this.selectParent($('.' + this.cls_checkBox,$el).last(),false);
	};
	
	//改变父节点的选中状态
	this.selectParent = function($el,selParent){
		//获取当前节点对应的li元素
		var $li = this.getThisLiElm($el);
		var $item = {};
		if(this.isItem($li)){
			$item = $li;
		}else if(this.isFolder($li)){
			$item = $('.'+this.cls_checkBox + ':eq(0)',$li);
		}
//		alert(this.isLi($li));
		//如果是LI，说明还没有到达tree顶点
		if(this.isLi($li)){
			//如果是枝干
			if(this.isFolder($li)){
//				alert('isFolder...');
				//只操作li标签中为复选框的元素(直接用li标签的是叶节点，枝节点用的是span标签)
				var checkBoxList = $('li.' + this.cls_checkBox,$li);
//				alert(checkBoxList.length);
				if(checkBoxList.length){//有子节点
					//获取当前树枝下已经选中的
					var selectedList = checkBoxList.filter('li.' + this.cls_selected);
//					alert(selectedList.length);
					if(checkBoxList.length  == selectedList.length){//如果子节点都选中了，则当前节点设置为全选状态
						$item.removeClass(this.cls_selected_part);
						$item.addClass(this.cls_selected);
					}else if(selectedList.length == 0){//子节点都没选中
						$item.removeClass(this.cls_selected);
						$item.removeClass(this.cls_selected_part);
					}else{//部分选中
						$item.addClass(this.cls_selected);
						$item.addClass(this.cls_selected_part);
					}
				}else{//没有子节点
					//删除部分选中样式
					$item.removeClass(this.cls_selected_part);
				}
			}
			//
			if(selParent === false){
				return;
				
			}else{
				//得到父级li元素
				var $parent = this.getThisLiElm($li.parent());
				//递归直到最上一层
				this.selectParent($parent,true);
			}
		}
	}; 
	
	//生成树
	this.init();
	return this;
};