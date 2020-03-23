$(function(){
    $("#north").load("header.html");
    $("#footer").load("footer.html");
	//$('#mainbox').css('min-height',document.documentElement.clientHeight-193+'px');
	if($.browser.msie){
		if($.browser.version == '6.0'){
			$('#mainbox').css('height',document.documentElement.clientHeight-193+'px');
		}
		
	}
	
//查询信息
	$("#showQueryDrop").powerFloat({
		offsets:{x:10,y:-26},
		zIndex:9,
		position:"3-2"
	});

//Tab 切换
	$('#cardBaseInfo').Tabs({
		event:'click'
	});
	$('#newsInfo').Tabs({
		event:'click'
	});		

});
