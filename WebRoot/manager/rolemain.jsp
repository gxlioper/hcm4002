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
<title>角色管理</title>
<!--link href="<%=request.getContextPath()%>/themes/default/ecard/style/main.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/default/ecard/style/tree.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/default/ecard/style/admin.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/default/ecard/style/index.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/default/ecard/style/flexigrid.pack.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/default/ecard/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.flexigrid.js" type="text/javascript"></script-->
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	
	var toolbar=[{
		text:'新增角色',
		 iconCls:'icon-add',
	    handler:function(){
	    	$("#dlg-edit").dialog({
	    		title:'新增角色',
	    		href:'roleedit.action'
	    	});
	    	$("#dlg-edit").dialog("open");
	    }
	}];
	var roleid;
function f_rolesqgn(val,row){
	return '<a href=\"javascript:f_rolegn(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"授权功能\" /></a>';
	
}
$(document).ready(function () {
var mydata;
 $.post('rolemainheader.action',"ok",  
        function (data) {
            var obj;  
            mydata = eval(data); 
            getGrid();
         },"json");  
})
function f_xg(val,row){
	
	if(row.id==defaultrole){
		return;
	}else if(row.id==fixroleadmin){
		return;
	}else if(row.id==mercrole){
		return;
	}else{
		return '<a href=\"javascript:f_xgrole(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	}
 
 }
function f_sc(val,row){
	if(row.id==defaultrole){
		return;
	}else if(row.id==fixroleadmin){
		return;
	}else if(row.id==mercrole){
		return;
	}else if(row.id=="1"){
		return;
	}else if(row.id=="2"){
		return;
	}else if(row.id=="3"){
		return;
	}else{
 		return '<a href=\"javascript:f_delrole(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
 	}
}
function f_rolesqgn(val,row){
	return '<a href=\"javascript:f_rolegn(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"授权功能\" /></a>';
}

var defaultrole="<s:property value="#application.defaultrole"/>";
var fixroleadmin="<s:property value="#application.fixroleadmin"/>";
var mercrole="<s:property value="#application.mercrole"/>";


var roleid;
function f_rolegn(roleid){
		$("#dlg-shanghu").dialog({
		title: '授权功能',
		href:'rolegn.action?id='+roleid
		});
		$("#dlg-shanghu").dialog('open');
}
function f_xgrole(roleid){
			if(roleid==defaultrole){
			//alert("不允许对普通持卡查询业务的角色进行修改！");
				$.messager.alert('提示信息','不允许对普通持卡查询业务的角色进行修改！','warning');
			return;
		}else if(roleid==fixroleadmin){
			//alert("不允许对捡获卡管理的特殊管理员角色进行修改！");
			$.messager.alert('提示信息','不允许对捡获卡管理的特殊管理员角色进行修改！','warning');
			return;
		}else if(roleid==mercrole){
			//alert("不允许对商户默认角色进行修改！");
			$.messager.alert('提示信息','不允许对商户默认角色进行修改！','warning');
			return;
		}else{
			
			$("#dlg-edit").dialog({
				title:'修改角色',
				href:'roleedit.action?id='+roleid
			});
			$("#dlg-edit").dialog('open'); 
		}
	
	}

function f_delrole(roleid)
{
		$.messager.confirm('提示信息','是否确定删除该角色？',function(r){
		if(r){
			if(roleid==defaultrole){
			//alert("不允许对普通持卡查询业务的角色进行删除！");
			$.messager.alert('提示信息','不允许对普通持卡查询业务的角色进行删除！','warning');
			return;
		}else if(roleid==fixroleadmin){
			//alert("不允许对捡获卡管理的特殊管理员角色进行删除！");
			$.messager.alert('提示信息','不允许对捡获卡管理的特殊管理员角色进行删除！','warning');
			return;
		}else if(roleid==mercrole){
			//alert("不允许对商户默认角色进行删除！");
				$.messager.alert('提示信息','不允许对商户默认角色进行删除！','warning');
			return;
		}else{
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
			 $.ajax({
   				url:'delrole.action?id='+roleid,
   				type:'post',
  			 	success:function(data){
  			 		$(".loading_div").remove();
   				var message=eval("("+data+")");
   					//alert(message);
   					$.messager.alert('提示信息',message);
   					getGrid();
   				},
   			error:function(){
   				$(".loading_div").remove();
   			//alert('用户操作失败！');
   			$.messager.alert('提示信息','操作失败！','error');
   			}
  		 });
		}
		}
	})
}

function getGrid(){
        $("#rolelist").datagrid({
			url: 'rolemainshow.action',
			dataType: 'json',
			rownumbers:true,
	        pageSize: 15,//每页显示的记录条数，默认为10 
	        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			columns:[[{
			 	align:'center',field:'rolename',title:'角色名称',width:20},
			 	{align:"center","field":"sqsh","title":"授权功能","width":10,"formatter":f_rolesqgn},
			 	{align:"center","field":"zysq","title":"资源管理","width":10,"formatter":web_resource_a},
			 	{align:"center","field":"xg","title":"修改","width":10,"formatter":f_xg},
	        	{align:"center","field":"sc","title":"删除","width":10,"formatter":f_sc}]],
	        	onLoadSuccess:function(value){
	        		$("#datatotal").val(value.total);
	        	},
	        singleSelect:true,
		    collapsible:true,
			pagination: true,
			fitColumns:true,
	        fit:true,
	        striped:true,
	     	toolbar:toolbar
		});
}
//----------------------------------------------资源管理------------------------------------
/**
 * 资源授权  a标签
 */
function web_resource_a(val,row){
	return '<a href=\"javascript:web_resource(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"资源授权\" /></a>';
}
/**
 * 资源管理
 */
 //+'&type=1'
function web_resource(id){
	 var url='getwebResourceZYGL.action?iid='+id+'&type=1'; 
		newwin = window.open(url,"资源管理", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
		newwin.focus();
}
    </script>
</head>
<body>
<div class="easyui-layout" fit="true" >
<div data-options="region:'center'" fit="true" style="margin:5px;">	
 <table id="rolelist">
   
</table>				
</div>
</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 600,height: 360,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-s" class="easyui-dialog" data-options="width: 600,height: 350,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-shanghu" class="easyui-dialog" data-options="width: 650,height: 480,closed: true,cache: false,modal: true,top:50"></div>	
<div id="dlg_resource" ></div>	
</body>
</html>