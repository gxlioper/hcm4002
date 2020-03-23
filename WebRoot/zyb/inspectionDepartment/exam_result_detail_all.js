$(document).ready(function () {
	
	showtab();	
	var height = window.screen.height-120;  
	stView_layout = $('#djtregisterglistshow').layout({  
	height: height 
	});
	$("#getDjtRegisterGList").tabs("select",bat_string2);
	$("#getDjtRegisterGList").tabs("select",bat_string1);
});

var bat_string1="健康体检";
var bat_string2="职业病体检";
var tabselid=0;
var count=0;
//关闭选项卡
function showtab(){
	$('#getDjtRegisterGList').tabs({
		 height: window.screen.height-98
	 })
	 if (!$('#getDjtRegisterGList').tabs('exists', bat_string1)) {
		 $('#getDjtRegisterGList').tabs('add', {
			 title: bat_string1,
			 content: '<iframe scrolling="auto" width="100%" height="100%" align="center" id="igetDjtRegisterGedit" name="igetDjtRegisterGedit"  onload="Javascript:SetWinHeightinn(this)" frameborder="0"  src="getExamResultDetail.action?exam_num='+$("#exam_num").val()+'&status=3"></iframe>',
			 closable: false
		 });
	 }     
	 if (!$('#getDjtRegisterGList').tabs('exists', bat_string2)) {
		 $('#getDjtRegisterGList').tabs('add', {
			 title: bat_string2,
			 content: '<iframe scrolling="auto" width="100%" height="100%" align="center" id="igetDjtRegisterGedit" name="igetDjtRegisterGedit"   onload="Javascript:SetWinHeightinn(this)" frameborder="0"  src="getZybExamResultDetail.action?examinfo_id='+$("#examinfo_id").val()+'&status=3"></iframe>',
			 closable: false
		 });
	 }
	 $("#getDjtRegisterGList").tabs("select",bat_string1);
}


/**
 * 显示选定的tab，并刷新
 */
var tab_index,tab_url;
function showOnetab(tab_index,tab_url){
	var tabtitle=bat_string1;
	if (tab_index==1){
		tabtitle=bat_string1;
	}else if (tab_index==2){
		tabtitle=bat_string2;
	}
	$("#getDjtRegisterGList").tabs("select",tabtitle);
}

 /**     
  * 刷新tab 
  * @cfg  
  *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
  *如果tabTitle为空，则默认刷新当前选中的tab 
  *如果url为空，则默认以原来的url进行reload 
  */  
 function refreshTab(cfg){  
     var refresh_tab = cfg.tabTitle?$('#getDjtRegisterGList').tabs('getTab',cfg.tabTitle):$('#getDjtRegisterGList').tabs('getSelected');  
     if(refresh_tab && refresh_tab.find('iframe').length > 0){  
     var _refresh_ifram = refresh_tab.find('iframe')[0];  
     var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;  
     //_refresh_ifram.src = refresh_url;  
     _refresh_ifram.contentWindow.location.href=refresh_url;  
     }  
 }  
 
 function SetWinHeightinn(obj) {
	 var height = window.screen.height-120;  
	 obj.height = height;
	} 
 
function close_page(){
	 var _parentWin =  window.opener ;
	 var userAgent = navigator.userAgent;
	 window.opener = null;
	 window.open('', '_self');
	 window.close();
	 _parentWin.getgroupuserGrid();
}
 