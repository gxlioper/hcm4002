$(document).ready(function () {
	
	showtab();	
	var height = window.screen.height-260;  
	stView_layout = $('#djtregisterglistshow').layout({  
	height: height 
	});
});

var bat_string1="职业病个人登记";
var bat_string2="健康档案对比";
var bat_string3="报告领取方式";
var bat_string4="职业病信息维护";
/*
var bat_string5="放射性职业史";
var bat_string6="既往史";*/
var tabselid=0;
var count=0;
var va =1;
function ztabsselect(){
	 $("#getDjtRegisterGList").tabs("select",bat_string1);
}
//关闭选项卡
function tabsClose(){  
//    var tab=$('#getDjtRegisterGList').tabs('getSelected');//获取当前选中tabs  
//    var index = $('#getDjtRegisterGList').tabs('getTabIndex',tab);//获取当前选中tabs的index  
//    $('#getDjtRegisterGList').tabs('close',index);//关闭对应index的tabs 
    
    $("#getDjtRegisterGList").tabs("select",bat_string1);
}  
function showtab(){
	$('#getDjtRegisterGList').tabs({
		 height: "auto",
		 onSelect : function(title,index) {
			 if (title == bat_string4) {
                 count = 1;
                 var reqUrl = "getZybDjtRegisterExtEdit.action";
                 refreshTab({tabTitle:title,url:reqUrl});  
             } 
			if( va==2 ){
				if(index=='2'){
					var statisDate = window.frames["0"].document.getElementById('customer_id').value;//取子frame子页面值
					window.frames["2"].document.getElementById('exam_id').value=statisDate;//取子frame子页面值
					window.frames["2"].getcustomer_info();
				}else if(index=='3'){
					var statisDate = window.frames["0"].document.getElementById('exam_num').value;//取子frame子页面值
					if(statisDate==""){
						$.messager.alert("提示信息","请输入人员基本信息","error");
						$("#getDjtRegisterGList").tabs("select",bat_string1);
						return;
					}
					window.frames["3"].document.getElementById('s_num').value=statisDate;//取子frame子页面值
					window.frames["3"].getreportreceve();
				}
			} 
         },
	 })
	 if (!$('#getDjtRegisterGList').tabs('exists', bat_string1)) {
		 $('#getDjtRegisterGList').tabs('add', {
			 title: bat_string1,
			 content: '<iframe scrolling="auto" width="100%" align="center" id="igetDjtRegisterGedit" name="igetDjtRegisterGedit"  onload="Javascript:SetWinHeightinn(this)" frameborder="0"  src="getZybDjtRegisterGEdit.action?exam_num='+$('#exam_num').val()+'"></iframe>',
			 closable: false
		 });
	 }     
	 if(!$('#getDjtRegisterGList').tabs('exists', bat_string4)){
		 $('#getDjtRegisterGList').tabs('add', {
			 title: bat_string4,
			 content: '<iframe scrolling="auto" width="100%" align="center" id="igetDjtRegisterGedit" name="igetDjtRegisterGedit"   onload="Javascript:SetWinHeightinn(this)" frameborder="0"  src="getZybDjtRegisterExtEdit.action"></iframe>',
			 closable: false
		 });
	 }
	 if (!$('#getDjtRegisterGList').tabs('exists', bat_string2)) {
		 $('#getDjtRegisterGList').tabs('add', {
			 title: bat_string2,
			 content: '<iframe scrolling="auto" width="100%" align="center" id="igetDjtRegisterGedit" name="igetDjtRegisterGedit"   onload="Javascript:SetWinHeightinn(this)" frameborder="0"  src="getDjtRegisterHistrjn.action"></iframe>',
			 closable: false
		 });
	 }
	 if(!$('#getDjtRegisterGList').tabs('exists', bat_string3)){
		 $('#getDjtRegisterGList').tabs('add', {
			 title: bat_string3,
			 content: '<iframe scrolling="auto" width="100%" align="center" id="igetDjtRegisterGedit" name="igetDjtRegisterGedit"   onload="Javascript:SetWinHeightinn(this)" frameborder="0"  src="getIndividualReportReceive.action"></iframe>',
			 closable: false
		 });
	 }
	 $("#getDjtRegisterGList").tabs("select",bat_string1);
	va=2;
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
	}else if(tab_index==3){
		tabtitle=bat_string3;
	}else if(tab_index==4){
		tabtitle=bat_string4;
	}
	refreshTab({tabTitle:tabtitle,url:tab_url});  
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
	 var height = window.screen.height-300;  
	 obj.height = height;
	} 
 