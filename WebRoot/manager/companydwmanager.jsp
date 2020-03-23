<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	getGrid();
});

function getGrid(){
	var model={ "id":$("#id").val()};
    $("#dwlist").datagrid({
	url: 'dwshowall.action',
	dataType: 'json',
	queryParams:model,
	toolbar:'#toolbar',
	 rownumbers:true,
     pageSize: 15,//每页显示的记录条数，默认为10 
     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	// height: $(window).height()-105,
	height: 300,
	 columns:[[
	    {align:'center',field:'company_Id',title:'父级单位ID',width:15},
	 	{align:'center',field:'dep_Name',title:'部门名称',width:30},
	 	{align:'center',field:'create_Times',title:'创建时间',width:20},
	 	{align:'center',field:'update_Times',title:'修改时间',width:20},
	 	{align:"center","field":"id","title":"修改","width":15,"formatter":f_xg},
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

function f_xg(val,row){	
	return '<a href=\"javascript:f_xguser(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}

function f_sc(val,row){
	return '<a href=\"javascript:f_deluser(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}

function f_xguser(userid){
		$("#dlg-edit").dialog({
		title:'修改部门',
		href:'danweiedit.action?id='+userid+'&company_Id='+$("#id").val()
	});
	$("#dlg-edit").dialog('open');
}

function f_deluser(userid)
{
$.messager.confirm('提示信息','是否确定删除该部门？',function(r){
	if(r){
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
   	    $("body").prepend(str);
		$.ajax({
		url:'deldanwei.action?id='+userid,
		type:'post',
		success:function(text){
			$(".loading_div").remove();
			if(text.split("-")[0]=='ok'){
        		  $.messager.alert("操作提示", text);
        		  $("#dlg-edit").dialog("close");
            	  getGrid();
            	  window.parent.reopen();
        	  }else if(text.split("-")[0]=='error'){
        		  $.messager.alert("操作提示", text,"error");
        	  }
		},
		error:function(){
			$(".loading_div").remove();
			$.messager.alert('提示信息','操作失败！','error');
		}
		});
	}
})
}
	 
var toolbar=[{
	text:'新增部门',
	 iconCls:'icon-add',
    handler:function(){
    	$("#dlg-edit").dialog({
    		title:'新增单位下部门',
    		href:'danweiedit.action?company_Id='+$("#id").val()
    	});
    	if($("#id").val()>0){
    	   $("#dlg-edit").dialog("open");
    	}else{
    	   $.messager.alert('提示信息',"请选择单位名称","error");
    	}
    }
}];
</script>
</head>
<body>
<input type="hidden" id="id" value='<s:property value="model.id"/>'/>
<input type="hidden" id="com_Level" value='<s:property value="model.com_Level"/>'/>
<input type="hidden" id="com_Num" value='<s:property value="model.com_Num"/>'/>


<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>选中树形单位信息</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:140px;">本级单位编号</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.id"/></dd>
					<dt style="height:26px;width:140px;">本级单位单名称</dt>
					<dd style="height:26px;width:560px;font-weight:bold;"><s:property value="model.com_Name"/></dd>
				</dl>
			</div>
 </fieldset>
			
<div id="cont-page" class="include-page">	
 <table id="dwlist" class="easyui-datagrid" >
   
</table>
</div>	
<div id="dlg-edit" class="easyui-dialog" data-options="width: 600,height: 300,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>
