function init(){
	/**
	 * 长度验证方法
	 */
	alert($("input[maxlength]"));
	 $("input[maxlength]").off("change").on('change', function(e) {
		 if(this.getAttribute('maxlength')!=null){
			var len=this.getAttribute('maxlength');
			while(this.value.replace(/[^\x00-\xff]/g, '**').length>len)this.value=this.value.slice(0,-1);
		}
	});
	/**
	 * 长度验证方法
	 */
	 $("input[maxlength]").off("keyup").on('keyup', function(e) {
		 if(this.getAttribute('maxlength')!=null){
			var len=this.getAttribute('maxlength');
			while(this.value.replace(/[^\x00-\xff]/g, '**').length>len)this.value=this.value.slice(0,-1);
		}
	});
}

/**
 * 返回字符长度
 * @param value
 * @param len
 * @returns {Boolean}
 */
function chLength(value){
	return value.replace(/[^\x00-\xff]/g, '**').length;

}
