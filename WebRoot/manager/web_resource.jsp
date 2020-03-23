<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/scripts/spectrum-master/spectrum.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
 	getGrid();  
})
/**
 * 获取资源表列表
 */
function getGrid(){
		//角色
		 var model={type:$('#type').val(),iid:$('#iid').val()}
        
        $("#resourceShow").datagrid({
   		 url:'getWebResourceTable.action',
   		 queryParams:model,
		fitColumns:true,
   		 rownumbers:false,
   		 columns:[[
   	           	/* {field:'ck',checkbox:true }, */
   	            {align:'left',field:'name',title:'资源名称',width:10},	
   			    {align:'left',field:'notice',title:'资源描述',width:50},
   			    {align:'left',field:'datavalue',title:'资源值'},
   			    {align:'center',field:'sczy',title:'删除资源',"formatter":f_sc},
   			    {align:'center',field:'xgzy',title:'修改资源',"formatter":xgzy}
   		 ]],	    	
	    onLoadSuccess:function(value){
    		$("#datatotal").val(value.total);
    		$(".loading_div").remove();
	    },
	    onDblClickCell:function(index, field, value){
	    },
	    nowrap:false,
	    singleSelect:false,
		collapsible:true,
		pagination:false,
		striped:true,
		toolbar:toolbar,
		onDblClickRow:function(index,row){
			update_resource(row.id);
		}
	});
}
function addresource(){
	var da = $("#resourceShow").datagrid('getChecked');
	var str="";
 		
 		$.each(da,function(k,v){
			str+='{res_code:"'+v.code+'",userorroleid:"'+$('#iid').val()+'",datavalue:"'+v.example+'",'+'res_type:"'+$('#type').val()+'"},';
 		})
 		var li =str.substring(0,str.length-1);
 		li="["+li+"]";
		var model={"li":li,
				"iid":$('#iid').val(),
				"res_type":$('#type').val()
				}
		$.ajax({
			url:'addWebResource.action',
			data:model,
			type:'post',
			success:function(data){
				$('#dlg_resource').dialog('close');
				$.messager.alert('提示信息',data);
			},
			error:function(){
				$.messager.alert('警告信息','操作失败','error'); 
			}
		})
		
 
}
var toolbar = [{
    text:'新增资源',
    iconCls:'icon-add',
    handler:function(){
    	$("#add_resource").dialog({
    		title:'新增资源',
    		href:'addWebResourcePage.action'
    	});
    	$("#add_resource").dialog("open");
    }
    },{
    	text:'关闭',
    	iconCls: 'icon-cancel',
    	handler:function(){
    		window.close();
    	} 		
}];
//---------------------------------------------删除资源-------------------------------------
/**
 * 删除资源
 */
function f_sc(val, row) {
		return '<a href=\"javascript:delete_resource(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
function delete_resource(id) {
		$.messager.confirm('提示信息', '是否删除资源？', function(r) {
			if (r) {
				$.ajax({
					url : 'deleteWebResource.action?id='+ id,
					type : 'post',
					success : function(data) {
						$("#resourceShow").datagrid("reload");
						$.messager.alert('提示信息', data);
					},
					error : function() {
						$.messager.alert('提示信息', '操作失败！', 'error');
					}
				});
			}
		})
	}
//----------------------------------修改资源-------------------------------------------------------------
function xgzy(val, row){
	return '<a href=\"javascript:update_resource(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}

function update_resource(id) {
	$("#add_resource").dialog({
		title:'修改资源',
		href:'updateWebResourcePage.action?id='+id,
	});
	$("#add_resource").dialog("open");
}
</script>
</head>
<body >   
<input type="hidden"  id="res_type"  value="<s:property value="res_type"/>"/>
<input type="hidden"   id='iid' value="<s:property value='iid'/> "/>
<input type="hidden"  id='type'  value="<s:property value='type'/>"/>
<fieldset style=" margin: 10px; ">
 <legend><strong>资源列表</strong></legend> 
	<div>
		<table id='resourceShow'  style="width:100%;height:700px">
		</table>
	</div>
</fieldset>
<div id="add_resource"  class="easyui-dialog"  data-options="width:550,height:450,closed: true,cache: false,modal: true,top:50"></div>
</body>