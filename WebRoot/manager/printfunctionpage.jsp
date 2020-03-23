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
<title>打印的功能页面</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">

$(document).ready(function(){
	getGrid();
	$('#exam_num').textbox('textbox').css('ime-mode','disabled');
	$('#exam_num').textbox('textbox').focus();
});

function getGrid() {
	var model = {
		"exam_num" : $("#exam_num").val()
	};
	$("#printlist").datagrid({
		url : 'getPrintListShow.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : false,
		pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
		columns : [[ {field:'ck',checkbox:true }, 
			         {align : 'center',field : 'dep_name',title : '科室',width : 25}, 
			         {align : 'center',field : 'item_code',title : '项目编码',width : 15}, 
			         {align : 'center',field : 'item_name',title : '项目名称',width : 15}
			         ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
		},
		singleSelect : false,
		collapsible : true,
		pagination : true,
		fitColumns : true,
		autowidth : true,
		striped : true,
		pagination : false,
		pageNumber : 1,
		pageSize : 1000
	});
}

function functionprint(){

		var itemcode = new Array();
		var lextitemlists = $('#printlist').datagrid('getChecked');
	    $.each(lextitemlists, function(index, item){
	    	itemcode.push(item.item_code);
	    });
	    
	    if(itemcode.length == 0){
	    	$.messager.alert("操作提示", "请选择需要打印条码的科室!", "error");
	    	return ;
	    }
		$.ajax({
			url : 'updateBarcodePrintStatus.action',
			data : {ids:"'"+$("#exam_num").val()+"'"},
			type : "post",//数据发送方式   
			success : function(text) {
				var exeurl ='GuidBarServices://&barcode&'+$("#exam_num").val()+'&'+itemcode.toString()+'&1'; 
				RunServerExe(exeurl);
			},
			error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
		
	 	function RunServerExe(url){
			location.href=url;
		}	 

}
</script>
</head>
<body>
<div class="easyui-layout" fit="true">
<div  data-options="region:'north'" style="height:84px;">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query"  style="padding-left:30px;">
				体检号: <input id="exam_num" name="exam_num" class="easyui-textbox" style="ime-mode:disabled;"/>&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;height:27px;" onclick="javascript:getGrid();">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;height:27px;" onclick="javascript:functionprint();">打印</a>
			</div>
 </fieldset>
 </div>
 <div  data-options="region:'center'">
<fieldset style="margin:5px;padding-right:0; height:95%;">
<legend><strong>信息列表</strong></legend>
<table id="printlist"> 
</table>
</fieldset>
</div>
</div>
</body>
</html>