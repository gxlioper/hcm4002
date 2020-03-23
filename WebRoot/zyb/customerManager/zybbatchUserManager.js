$(document).ready(function () {
	var height = window.screen.height-260;  
	stView_layout = $('#batchUserManagershowlist').layout({  
	height: height 
	});
	stView_layout = $('#batchUserManager').layout({  
	height: height 
	});
	showtab();
});

var bat_string1="人员分组";
var bat_string2="预约管理";
var bat_string3="职业病名单导入(危害因素关联套餐)";
var bat_string4="职业病名单导入(关联自选套餐)";
var bat_string5="放射性职业病名单导入";
var tabselid=0;
var count=0;
var companyidss=0;
var batchidss=0;
var groupidss=0;
var count1=0;
var count2=0;
var count3=0;
var count4=0;
var count5=0;
function showtab(){
	$('#batchUserManager').tabs({
		  height: "auto",
		 onSelect : function(title) {
			 if ((title == bat_string1)&&(count1<1)) {
				 var reqUrl = "zybusergroupmanager.action";
                 refreshTab({tabTitle:title,url:reqUrl});
                 count1=1;
			 }else if ((title == bat_string2)&&(count2<=1)) {
                 count = 1;
                 count2++;
                 var reqUrl = "custappointmentshow.action";
                 refreshTab({tabTitle:title,url:reqUrl});  
             } else if ((title == bat_string3)&&(count3<=1)) {
                 count = 1;
                 count3++;
                 var reqUrl = "zybimpusershow.action";
                 refreshTab({tabTitle:title,url:reqUrl});  
             } else if ((title == bat_string4)&&(count4<=1)) {
                 count = 1;
                 count4++;
                 var reqUrl = "zybimpusershow_set.action";
                 refreshTab({tabTitle:title,url:reqUrl});  
             } else if ((title == bat_string5)&&(count5<=1)) {
            	 count = 1;
            	 count5++;
            	 var reqUrl = "fsxzybimpusershow.action";
            	 refreshTab({tabTitle:title,url:reqUrl});  
             }
         }
		}); 
	
	 if (!$('#batchUserManager').tabs('exists', bat_string1)) {
	        $('#batchUserManager').tabs('add', {
	            title: bat_string1,
	            content: '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit" onload="Javascript:SetWinHeightinn(this)" frameborder="0"  ></iframe>',
	            closable: false
	        });
	    }
	
	    if (!$('#batchUserManager').tabs('exists', bat_string2)) {
	        $('#batchUserManager').tabs('add', {
	            title: bat_string2,
	            content: '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit" onload="Javascript:SetWinHeightinn(this)" frameborder="0" ></iframe>',
	            closable: false
	        });
	    }   	   
	    
	    if (!$('#batchUserManager').tabs('exists', bat_string3)) {
	        $('#batchUserManager').tabs('add', {
	            title: bat_string3,
	            content: '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit" onload="Javascript:SetWinHeightinn(this)" frameborder="0" ></iframe>',
	            closable: false
	           
	        });
	    }
	    
	    if (!$('#batchUserManager').tabs('exists', bat_string4)) {
	    	$('#batchUserManager').tabs('add', {
	    		title: bat_string4,
	    		content: '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit" onload="Javascript:SetWinHeightinn(this)" frameborder="0" ></iframe>',
	    		closable: false
	    		
	    	});
	    }
	    
	    if (!$('#batchUserManager').tabs('exists', bat_string5)) {
	    	$('#batchUserManager').tabs('add', {
	    		title: bat_string5,
	    		content: '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit" onload="Javascript:SetWinHeightinn(this)" frameborder="0" ></iframe>',
	    		closable: false
	    		
	    	});
	    }
	    $("#batchUserManager").tabs("select",bat_string1);
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
	}else if (tab_index==4){
		tabtitle=bat_string4;
	}else if (tab_index==5){
		tabtitle=bat_string5;
	}
	refreshTab({tabTitle:tabtitle,url:tab_url});  
	$("#batchUserManager").tabs("select",tabtitle);
}

 /**     
  * 刷新tab 
  * @cfg  
  *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
  *如果tabTitle为空，则默认刷新当前选中的tab 
  *如果url为空，则默认以原来的url进行reload 
  */  
 function refreshTab(cfg){  
     var refresh_tab = cfg.tabTitle?$('#batchUserManager').tabs('getTab',cfg.tabTitle):$('#batchUserManager').tabs('getSelected');  
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
 