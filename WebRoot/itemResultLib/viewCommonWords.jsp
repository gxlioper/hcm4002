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
<title>影像科室常用词列表</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/wk_rederCard.js"></script>
<script type="text/javascript">
var toolbar = [{
    text:'新增常用词',
    iconCls:'icon-add',
    handler:function(){
    	$("#dlg-edit").dialog({
    		title:'新增常用词',
    		href:'editViewCommonWords.action'
    	});
    	$("#dlg-edit").dialog("open");
    }
}];
$(document).ready(function () {
	$('#ser_name').combobox({    
	    url:'getViewCommonSampleList.action',
	    valueField:'id',    
	    textField:'demo_name',
	    height:25
	});
	getGrid();
});

function getGrid(){
	var model = {sample_id:$('#ser_name').combobox('getValue')};
	   $("#viewWordsList").datagrid({
		url: '<%=request.getContextPath()%>/getViewCommonWordsList.action',
		queryParams: model,
		rownumbers:true,
     	pageSize: 15,//每页显示的记录条数，默认为10 
     	pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
     	toolbar: '#toolbar',
     	sortName: 'cdate',
		sortOrder: 'desc',
     //height:390,
     columns:[[{align:"center",field:"sample_name","title":"样本名称","width":15},
     		  {align:'center',field:"exam_desc","title":"描述","width":30},
     		  {align:"center",field:"exam_result","title":"结论","width":35},
     		  {align:"center",field:"seq_code","title":"顺序码","width":10},
     		  {align:"center",field:"is_defaults","title":"默认值","width":15},
     		  {align:"center",field:"updater","title":"修改人","width":10},
     		  {align:"center",field:"update_time","title":"修改时间","width":15},
     		  {align:"center",field:"xg","title":"修改","width":10,"formatter":f_xg},
     		 {align:"center",field:"sc","title":"删除","width":10,"formatter":f_sc}
     		 ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: true,
     	fitColumns:true,
     	striped:true,
     	fit:true,
     	toolbar:toolbar,
     	rowStyler: function(index,row){
	    	if(row.is_default == '1'){
				return 'color:#ff0000;';
			}
		}
	});
}
function f_xg(val,row){	
	return '<a href=\"javascript:f_xgViewWords(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
function f_xgViewWords(id){
		$("#dlg-edit").dialog({
			title:'修改常用词',
			href:'editViewCommonWords.action?id='+id
		});
		$("#dlg-edit").dialog('open');
}
function f_sc(val,row){	
	return '<a href=\"javascript:f_delViewWords(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
function f_delViewWords(id){
	$.messager.confirm("提示信息","是否删除常用词？",function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({
				url : 'delViewCommonWords.action?id='+id,
				type : 'post',
				success:function(data){
					$(".loading_div").remove();
					var obj = data.split("-");
					if(obj[0] == 'ok'){
						$.messager.alert('提示信息', obj[1],function(){});
						getGrid();
					}else{
						$.messager.alert('提示信息', obj[1],function(){});
					}
				},
				error:function(data){
					$(".loading_div").remove();
					$.messager.alert('提示信息', data,function(){});
				}
			});
		}
	})
}
//回车查询
function serch_cls(dom){
	$(dom).unbind("keydown").keydown(function (e) {
	    if (e.which == 13) {
	    	getGrid();
	    }
    });
}
</script>
</head>
<body>
<!-- <div id="cont-page" class="include-page" > -->
<div class="easyui-layout" fit="true">
<div  data-options="region:'north'" style="height:84px;">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd>样本名称：<input maxlength="20" type="text" id="ser_name" class="textinput"/></dd>
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:getGrid();">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 </div>
 <div  data-options="region:'center'">
<fieldset style="margin:5px;padding-right:0; height:95%;">
<legend><strong>影像科室常用词列表</strong></legend>
<table id="viewWordsList"> 
</table>
</fieldset>
</div>
</div>
<!-- </div> -->
<div id="dlg-edit" class="easyui-dialog" data-options="width: 780,height: 420,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>