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

 $.post('rolemainheader.action?language='+$("#language").val(),"ok",  
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
	$.messager.confirm('提示信息','确认对该角色进行授权吗？',function(r){
		if(r){
			$("#dlg-shanghu").dialog({
		title: '授权功能',
		href:'rolegn.action?id='+roleid
		});
		$("#dlg-shanghu").dialog('open');
		}
	})
}
function f_xgrole(roleid){
	
		$.messager.confirm('提示信息','是否确定修改该角色？',function(r){
		if(r){
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
	})
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
			 $.ajax({
   				url:'delrole.action?id='+roleid,
   				type:'post',
  			 	success:function(data){
   				var message=eval("("+data+")");
   					//alert(message);
   					$.messager.alert('提示信息',message);
   					getGrid();
   				},
   			error:function(){
   			//alert('用户操作失败！');
   			$.messager.alert('提示信息','操作失败！','error');
   			}
  		 });
		}
		}
	})
}

function getGrid(){
		var model={};
        $("#rolelist").datagrid({
		url: 'rolemainshow.action?language='+$("#language").val(),
		dataType: 'json',
		queryParams:model,
		toolbar:'#toolbar',
		sortName:'cdate',
		sortOrder:'desc',
		 rownumbers:true,
    pageSize: 15,//每页显示的记录条数，默认为10 
    pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		// height: $(window).height()-105,
		height: 430,
		 columns:[[{
		 	align:'center',field:'rolename',title:'角色名称',width:30},
		 	{align:"center","field":"sqsh","title":"授权功能","width":20,"formatter":f_rolesqgn},
		 	{align:"center","field":"xg","title":"修改","width":15,"formatter":f_xg},
        	{align:"center","field":"sc","title":"删除","width":15,"formatter":f_sc}]],
        	onLoadSuccess:function(value){
        		$("#datatotal").val(value.total);
        	},
        	 singleSelect:true,
	    collapsible:true,
		 pagination: true,
        fitColumns:true,
        striped:true,
        toolbar:toolbar
        	

	});
}
    </script>
</head>
<body style="height:600px">
	<div id="cont-page" class="include-page">
	
 <table id="rolelist" class="easyui-datagrid" >
   
</table>
				
			</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 600,height: 300,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-s" class="easyui-dialog" data-options="width: 600,height: 350,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-shanghu" class="easyui-dialog" data-options="width: 650,height: 480,closed: true,cache: false,modal: true,top:50"></div>	
</body>
</html>