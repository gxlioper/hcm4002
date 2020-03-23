$(document).ready(function () {  
	var height = window.screen.height-260;  
	stView_layout = $('#batchmanagerbar').layout({  
	height: height 
	});
	showtab();
	
});

var bat_string1="体检任务维护";
var bat_string2="体检任务分组";
var bat_string3="体检任务人员计划";
var tabselid=0;
var count=0;
function showtab(){
	$('#compantmanager').tabs({
		 height: "auto",
		 onSelect : function(title) {
             /*if (title == bat_string1 && count != 0) {
                 var reqUrl = "batchshow.action";
                 refreshTab({tabTitle:title,url:reqUrl});  
             } else if (title == bat_string2) {
                 count = 1;
                 var reqUrl = "companydwmanager.action?id="+tabselid+"";
                 refreshTab({tabTitle:title,url:reqUrl});  
             } else if (title == bat_string3) {
                 count = 1;
                 var reqUrl = "companychange.action?id="+tabselid+"";
                 refreshTab({tabTitle:title,url:reqUrl});  
             }*/
         }
		}); 
	
	    if (!$('#compantmanager').tabs('exists', bat_string1)) {
	        $('#compantmanager').tabs('add', {
	            title: bat_string1,
	            content: '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit" onload="Javascript:SetWinHeightinn(this)" frameborder="0"  src="crmbatchshow.action?sign_num='+$('#batch_sign_num').val()+'"></iframe>',
	            closable: false
	        });
	    }      
	    
	    if (!$('#compantmanager').tabs('exists', bat_string2)) {
	        $('#compantmanager').tabs('add', {
	            title: bat_string2,
	            content: '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit" onload="Javascript:SetWinHeightinn(this)" frameborder="0"  src="crmgroupmanager.action"></iframe>',
	            closable: false
	        });
	    }
	    
	    if (!$('#compantmanager').tabs('exists', bat_string3)) {
	        $('#compantmanager').tabs('add', {
	            title: bat_string3,
	            content: '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit" onload="Javascript:SetWinHeightinn(this)" frameborder="0"  src="crmbatchproshow.action"></iframe>',
	            closable: false
	           
	        });
	    }
	    $("#compantmanager").tabs("select",bat_string1);
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
	}else if (tab_index==3){
		tabtitle=bat_string3;
	}
	refreshTab({tabTitle:tabtitle,url:tab_url});  
	$("#compantmanager").tabs("select",tabtitle);
}

 /**     
  * 刷新tab 
  * @cfg  
  *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
  *如果tabTitle为空，则默认刷新当前选中的tab 
  *如果url为空，则默认以原来的url进行reload 
  */  
 function refreshTab(cfg){  
     var refresh_tab = cfg.tabTitle?$('#compantmanager').tabs('getTab',cfg.tabTitle):$('#compantmanager').tabs('getSelected');  
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