<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%  
  application.setAttribute("name","application_James");  
   %>  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>健康体检系统</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="themes/default/layout1.css" />
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript" src="scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="scripts/plugins/jquery.tinyscrollbar.min.js"></script>
<script type="text/javascript" src="scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/main.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">

var isOpen = "1";
$(function(){
	var hht=(window.screen.height)*0.8;
	$("#west").css('height',hht);
	$("#main").height(document.documentElement.clientHeight+'px'); 
	var usertype="<s:property value="#session.usertype"/>";
	var logintype="<s:property value="#session.logintype"/>";	
	var id="<s:property value="#session.userinfo.id"/>";
	var dep_url="<s:property value="#session.username.dep_url"/>";
	var is_print = "<s:property value="#session.username.isPrint"/>";
	var dep_id = "<s:property value="#session.username.dep_id"/>";
	if(id==''){
		window.parent.location="index.html";
	}
	var app_type = "<s:property value="#session.defaultapp.comid"/>";
	if(app_type == 1){
		document.title = '健康体检系统';
	}else if(app_type == 2){
		document.title = '职业病体检系统';
	}else if(app_type == 3){
		document.title = '客户关系系统';
	}
	
	$("#iFrame").attr("src",dep_url);//如果是管理员,则显示个人信息页面
	 $("#obj").css({ "z-index": "1000", "position": "absolute" });     

     var objobj = document.getElementById("obj");
     $(window).scroll(function() {
         objobj.style.marginTop = document.documentElement.scrollTop + "px";
         objobj.style.marginLeft = document.documentElement.scrollLeft + "px";
     });
//     $("#tabs").tabs({    
// 	    onSelect:function(title){    
// 	        if(title == '科室采样'){
// 	        	var cfg = {tabTitle:title,url:'getDepPrintSamplePage.action'}
// 	        	refreshTab(cfg);
// 	        }
// 	    }    
// 	});  
     $("#west").toggle();
     $("#btn").toggleClass("leftToggle");
     $("#btn").click(function() {
         $("#west").toggle();
         $(this).toggleClass("leftToggle");

         if (isOpen == "1") {
             document.getElementById("icon").src = "<%=request.getContextPath()%>/images/z_menuClose.gif";
             isOpen = "0";
         } else {
             document.getElementById("icon").src = "<%=request.getContextPath()%>/images/z_menuOpen.gif";
             isOpen = "1";
         }
     });
   var off_on="0";
  $("#move").click(function(){
	  if(off_on==0){
		  document.getElementById("a").src = "<%=request.getContextPath()%>/images/z_menuClose.gif";
		  $("#float-button").css("display","inline");//点击显示
		  off_on="1";
		 
 	 }else{
 		  document.getElementById("a").src = "<%=request.getContextPath()%>/images/z_menuOpen.gif";
 		 $("#float-button").css("display","none");//点击隐藏
 		  off_on="0";
 	 }
	  });
	//------------------排期----
	if(dep_url =="registerDesk.action"){
		$('#tabs').tabs('add', {
          title: '排期',
          content: '<iframe scrolling="auto" width="100%" onload="Javascript:ifram_height(this)" align="center" id="win" name="win" frameborder="0"  src="getSchedulePage.action"></iframe>',
          closable: false,
          selected:false
      	});
	}
//	if(is_print == '0' && dep_id != '47' && dep_id != '48'){
//		$('#tabs').tabs('add', {
//	          title: '科室采样',
//	          content: '<iframe scrolling="auto" width="100%" onload="Javascript:ifram_height(this)" align="center" id="win" name="win" frameborder="0"  src="getDepPrintSamplePage.action"></iframe>',
//	          closable: false,
//	          selected:false
//	    });
//	}
	  //$('#a').draggable();//拖动 //目前只有 Firefox、Chrome 以及 Safari 支持 draggable 属性。
	 load_paiban_this();//排班
	 yizhou_beiwang();//备忘
	  
	 message_show();
})



var showcontral=1;
function message_show(){
	if(showcontral==1){
		$.post("getUserSystemInfromsList.action", 
		 function(jsonPost) {
			var si=eval(jsonPost);
			if(si.user_id>0){
				 jQuery.messager.show({ 
					  title:'消息提示:', 
					  width: 500,
					  height: 150, 
					  msg:'<div style="font-size:18px;height:90px;overflow:auto;line-height:26px;"><div>'+si.inform_content+'【'+si.chi_name+'】</div> <div style = "text-align:center;margin-top:10px;"><input type="button" style="text-align:center;" onclick="readermessage('+si.user_id+')" value="标记已读" id="sendSms"/></div></div>', 
					  timeout:0, 
					  showType:'slide'
					  }); 
				 showcontral=0; 
				}		
				}, "json");
	}
	setTimeout(function(){message_show();},30*1000);
}

    var messid;
	function readermessage(messid){
		$.ajax({
			url:'updateUserSystemInfroms.action',
			data : {
				cardid : messid
			    },
			type:'post',
			success:function(data){
				showcontral=1;
				$(".messager-body").window('close');
			}
		});
	}
function addPanel(subtitle, url, icon, type) {
    var tab = $('#tabs').tabs('getSelected');
    if (!$('#tabs').tabs('exists', subtitle)) {
        $('#tabs').tabs('add', {
            title: subtitle, //onload="Javascript:SetWinHeight(this)"
            content: '<iframe scrolling="auto" width="100%" onload="Javascript:ifram_height(this)" align="center" id="win" name="win" frameborder="0"  src="'+url+'"></iframe>',
            closable: true,
            iconCls:"icon-24 "+icon
        });
        
        
        $("#west").toggle();
        $("#btn").toggleClass("leftToggle");
        if (isOpen == "1") {
            document.getElementById("icon").src = "<%=request.getContextPath()%>/images/z_menuClose.gif";
            isOpen = "0";
        } else {
            document.getElementById("icon").src = "<%=request.getContextPath()%>/images/z_menuOpen.gif";
            isOpen = "1";
        }
    } else {
        $('#tabs').tabs('select', subtitle);
    }    
 }

function addPanel_other(subtitle, url, icon, type) {
    if (!$('#tabs').tabs('exists', subtitle)) {
        $('#tabs').tabs('add', {
            title: subtitle, //onload="Javascript:SetWinHeight(this)"
            content: '<iframe scrolling="auto" width="100%" onload="Javascript:ifram_height(this)" align="center" id="win" name="win" frameborder="0"  src="'+url+'"></iframe>',
            closable: true,
            iconCls:"icon-24 "+icon
        });
        
    } else {
    	$("#tabs").tabs("select",subtitle);
    	refreshTab({tabTitle:subtitle,url:url});  
    }
    
}

/**     
 * 刷新tab 
 * @cfg  
 *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
 *如果tabTitle为空，则默认刷新当前选中的tab 
 *如果url为空，则默认以原来的url进行reload 
 */  
function refreshTab(cfg){
    var refresh_tab = cfg.tabTitle?$('#tabs').tabs('getTab',cfg.tabTitle):$('#tabs').tabs('getSelected');  
    if(refresh_tab && refresh_tab.find('iframe').length > 0){  
    var _refresh_ifram = refresh_tab.find('iframe')[0];  
    var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;  
    //_refresh_ifram.src = refresh_url;  
    _refresh_ifram.contentWindow.location.href=refresh_url;  
    }  
}  


$(function(){
	$("input.textinput").focus(function(){
		$(this).addClass("hasinput");
	}).blur(function(){
		if($(this).val()==""){
			$(this).removeClass("hasinput")
		}
	});
})
function SetWinHeight(obj) {
    var win = obj;
    if (document.getElementById) {
        if (win && !window.opera) {
            if (win.contentDocument && win.contentDocument.body.offsetHeight)
                win.height = win.contentDocument.body.offsetHeight;
            else if (win.Document && win.Document.body.scrollHeight)
                win.height = win.Document.body.scrollHeight;
        }
    }
}

function ifram_height(obj){
	obj.height = (document.documentElement.clientHeight - document.getElementById("content").offsetTop -30)+'px'
	
	//alert(document.body.clientWidth);//网页可见区域宽：
	//alert(document.body.clientHeight);//网页可见区域高：
	//alert(document.body.offsetWidth);//网页可见区域宽： (包括边线的宽)
	//alert(document.body.offsetHeight);//网页可见区域高： (包括边线的宽)
	//alert(document.body.scrollWidth);//网页正文全文宽：
	//alert(document.body.scrollHeight);//网页正文全文高：
	//alert(document.body.scrollTop);//网页被卷去的高：
	//alert(window.screen.height);//	屏幕分辨率的高：
	//alert(window.screen.width);//屏幕分辨率的宽：
	//alert(window.screen.availHeight);//屏幕可用工作区高度：
	//alert(window.screen.availWidth );//屏幕可用工作区宽度：
}
 
 
</script>

 <script language="javascript">
       history.pushState(null, null, document.URL);
       window.addEventListener('popstate', function () {
           history.pushState(null, null, document.URL);
       });
  </script>
</head>
<body id="main" style="overflow:hidden;">
<div id="north"></div>
<div id="content">
	<div class="content-panel"  style="height:100%">	
	<div id="obj" class="obj">

      <div id="west" class="dtree"  style="position:absolute; overflow-y :auto;overflow-x:hidden; width:200px;"></div>
      <div class="btn" id="btn"  >
           <img id="icon"   alt="方向" src="<%=request.getContextPath()%>/images/z_menuOpen.gif"/>
      </div>
      <div id="move" style="position: fixed;height: 60px;width: 12px; bottom: 430px;right: 1px; background: #d9d9d9;padding-top:30px;">
	  
	  	<img id="a" alt="排班/备忘" src="<%=request.getContextPath()%>/images/z_menuClose.gif"/>
	  </div>
       <div id="float-button" style="position: fixed;height: 74%;width: 400px; top: 105px;right: 12px; background: #E8F2FE;display: none;">
       		<div id="contentup">
       			<div id="head" style="height:34px;width:100%;text-align:center;line-height:34px;font-size:20px;color:#359BCC;background: #359BCC;color:#FFFFFF;">排班信息</div>
       			<div id="paiban_title" style="height:34px;width:50%;text-align:center;font-size:16px;line-height:34px;float:left;background: #D9D9D9;"></div>
       			<div id="now" style="height:34px;width:50%;text-align:center;font-size:16px;line-height:34px;float:right;background: #D9D9D9;">
       				<a  href="javascript:void(0)" onclick="load_paiban_this(-1)">上月 </a> &nbsp;&nbsp; <a href="javascript:void(0)" onclick="load_paiban_this(1)">下月</a></div>
       			<div id="paiban_table" style="width:100%;">
       				 <table border="0" cellpadding="0" cellspacing="9" id="paiban_table_list"  width="100%" style="text-align:center;"></table>
       			</div>
       		</div>
       		<div id="memo">
       			<div style="height:34px;width:100%;text-align:center;line-height:34px;font-size:20px;background:#359BCC;color:#FFFFFF;">备忘录</div>
       			<div id="button" style="height:34px;width:50%;background:#d9d9d9;text-align:center;font-size:16px;line-height:34px;float:left;">
       				<input type="button" id="b" value="添加" style="margin-top:3px;" onclick="newNote()" />
       			</div>
       			<div id="week" style="height:34px;width:50%;background:#d9d9d9;text-align:center;font-size:16px;line-height:34px;float:right;">
       				<a  href="javascript:void(0)" onclick="yizhou_beiwang(-1)">上周</a>&nbsp;&nbsp; &nbsp; <a href="javascript:void(0)" onclick="yizhou_beiwang(1)">下周</a></div>
       			<fieldset style="height:345px;overflow:auto;padding-top:0px;border:0px;">
       			<div id="notesdata"  style="padding-left:10px;padding-top:10px;padding-bottom:10px;"></div>	</fieldset>
       		</div>
       </div>
      
    </div>
		<div id="mainbox" style="height:100%">		
			<div id="tabs" class="easyui-tabs" data-options="tabHeight:30" border="false" >
    					<div title="欢迎使用">
							<iframe id="iFrame" name="iFrame" frameborder="0" width="100%" onload="ifram_height(this)" scrolling="no"></iframe>
						</div>
	        </div>			
		</div>
	</div>
</div>

<div id="south"></div>
<div id="edit_dlg" class="easyui-dialog"  data-options="width: 500,height: 370,closed: true,cache: false,modal: true,top:150"></div>
<!-- 超时 -->
<div id="timeout-dlg" class="panel window" style="width: 344px; left: 508px; top: 244px; z-index: 9002;display:none;">
	<div class="panel-header panel-header-noborder window-header" style="width: 344px;"><div class="panel-title">登录超时</div><div class="panel-tool"></div></div>
	<div class="panel-body panel-body-noborder window-body" style="width: 344px; height: 128px;">
	<div style="padding:10px 0 0 10px;">
		<table>
			<tbody>
			<tr>
				<td colspan="2" style="padding-left:10px;">
					<h2 class="logined-name"><img alt="" style="margin-top:-4px;" class="icon-24 ico-7-24" src="themes/default/images/blank.gif">dd</h2>
				</td>
			</tr>
			<tr>
				<td><div class="timeout-login-file"><span class="files-ico pass"></span><input type="password" value="" rel="tipslayer" id="pwd" name="pwd" class="textinput password"></div></td>
				<td><div><button class="easyui-linkbutton c6" style="width:110px;height:40px;" onclick='$(".body-mask").remove();$("#timeout-dlg").hide();'>重新登录</button></div></td>
			</tr>
			</tbody>
		</table>
	</div>
	</div>
</div>
<!-- 超时 End -->
</body>
</html>