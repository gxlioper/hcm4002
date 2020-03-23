function resizeWidth(obj,param){
	$(parent.window).resize(function(){
		if(window.parent.document.getElementById('tabs')){
			if($('#'+obj).length>0){
				$('#'+obj).datagrid('resize',{
					width: window.parent.document.getElementById("tabs").offsetWidth-param
				});
			}
		}
	});
}
