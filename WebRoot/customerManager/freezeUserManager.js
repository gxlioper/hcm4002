$(document).ready(function () {
	var height = window.screen.height-260;  
	stView_layout = $('#feezeUserManagershowlist').layout({  
	height: height 
	});
	stView_layout = $('#feezeUserManager').layout({  
	height: height 
	});
	showtab();
});

var bat_string1="体检信息冻结";
var bat_string2="体检信息解冻";
var tabselid=0;
var count=0;
var companyidss=0;
var batchidss=0;
var groupidss=0;
var count1=0;
var count2=0;
function showtab(){
	$('#feezeUserManager').tabs({
		  height: "auto",
		 onSelect : function(title) {
			 if ((title == bat_string1)&&(count1<1)) {
				 var reqUrl = "freezeusergroupmanager.action";
                 refreshTab({tabTitle:title,url:reqUrl});
                 count1=1;
			 }else if ((title == bat_string2)&&(count2<=1)) {
                 count = 1;
                 count2++;
                 var reqUrl = "freezeteamlistshow.action";
                 refreshTab({tabTitle:title,url:reqUrl});  
             } 
         }
		}); 
	
	 if (!$('#feezeUserManager').tabs('exists', bat_string1)) {
	        $('#feezeUserManager').tabs('add', {
	            title: bat_string1,
	            content: '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit" onload="Javascript:SetWinHeightinn(this)" frameborder="0"  ></iframe>',
	            closable: false
	        });
	    }
	
	    if (!$('#feezeUserManager').tabs('exists', bat_string2)) {
	        $('#feezeUserManager').tabs('add', {
	            title: bat_string2,
	            content: '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit" onload="Javascript:SetWinHeightinn(this)" frameborder="0" ></iframe>',
	            closable: false
	        });
	    }   	   
	   
	    $("#feezeUserManager").tabs("select",bat_string1);
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
	$("#feezeUserManager").tabs("select",tabtitle);
}

 /**     
  * 刷新tab 
  * @cfg  
  *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
  *如果tabTitle为空，则默认刷新当前选中的tab 
  *如果url为空，则默认以原来的url进行reload 
  */  
 function refreshTab(cfg){  
     var refresh_tab = cfg.tabTitle?$('#feezeUserManager').tabs('getTab',cfg.tabTitle):$('#feezeUserManager').tabs('getSelected');  
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
 