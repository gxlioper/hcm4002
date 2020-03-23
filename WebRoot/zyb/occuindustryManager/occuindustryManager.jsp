<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>从业行业管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  

<script type="text/javascript">
	$(document).ready(function () { 
		enter();
		getcyhylist();
	});
	function enter(){
		document.onkeydown = function(e){
			var ev = document.all ? window.event : e;
			if(ev.keyCode==13) {
				getcyhylist();
			}
		}
	};
	function getcyhylist(){
		$('#industry').datagrid({
		    url:'occuindustryList.action', 
		    queryParams:{ industry_code:$('#industry_code_s').textbox('getValue'),
		    	          industry_name:$('#industry_name_s').textbox('getValue'),
		    },
		    dataType: 'json',
		    pageSize: 15,//每页显示的记录条数，默认为10 
		    pageList:[15,20,30,60,75],//可以设置每页记录条数的列表 
		    columns:[[
		        {align:"center",field:'industryID',title:'ID',hidden:true},
		        {align:"center",field:'industry_code',title:'行业编码',width:50},
		        {align:"center",field:'industry_name',title:'行业名称',width:50},
		        {align:"center",field:'package_name',title:'套餐名称',width:50},
		        {align:"center",field:'exam_set_code',title:'套餐编码',width:50},
		        {align:"center",field:'scriptID',title:'报表文件名',width:50},
		        {align:"center",field:'phyexeperiod',title:'体检有效期/年',width:50},
		        {align:"center",field:'trainperiod',title:'培训有效期/年',width:50},
		        {align:"center",field:'upd',title:'修改',width:30,"formatter":f_update},
		        {align:"center",field:'del',title:'删除',width:50,"formatter":f_delete},
		        
		    ]] ,
       		pagination:true,//分页控件
        	rownumbers:true,//行号
        	fitColumns:true,
        	singleSelect:true,
		    fit:true,
        	toolbar:toolbar,
        	onDblClickRow:function(index, row){
       	   	 var row_id = $('#industry').datagrid('getRows')[index].industryID;
       	  			updateIndustry(row_id);
       	       } 
		}); 
	};
	/**
	  * 定义工具栏
	  */
	  var toolbar = [{
		    text:'新增',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#edit_dlg").dialog({
		    		title:'新增',
		    		href:'addOccuIndustry.action'
		    	});
		    	$("#edit_dlg").dialog("open");
		    }
		}];
	
	//修改按钮
	function f_update(val,row){	
			return '<a href=\"javascript:updateIndustry(\''+row.industryID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	 }
	 //删除按钮
	function f_delete(val,row){
			return '<a href=\"javascript:deleteIndustry(\''+row.industryID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}
	//更新
	
	function updateIndustry(id){
	    $("#edit_dlg").dialog({
			title:'修改',
			href:'updateOccuIndustry.action?industryID='+id
		});
		$("#edit_dlg").dialog('open'); 
	}
	//删除
	function deleteIndustry(id){
		$.messager.confirm('提示信息','是否确定删除？',function(r){
			if(r){
				$.ajax({
		   		url:'deleteOccuindustry.action?industryID='+id,
		   		type:'post',
		   		success:function(data){
	   			$.messager.alert('提示信息',data);
	   			getcyhylist();
	   		    },
		   		error:function(){
		   			$.messager.alert('提示信息','操作失败！','error');
		   		}
	   		});
			}
		}) 
	}
	function cleanFun(){
		$("#industry_name_s").textbox('setValue',"");
		$("#industry_code_s").textbox('setValue',"");
		getcyhylist();
	}
	function searchFun(){
		getcyhylist();
	}
</script>
</head>
<body class="easyui-layout"> 
    <div  data-options="region:'north'" style="height:80px;">
		<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
		<legend><strong>信息检索</strong></legend>
		<div id="tb" style="padding-right:20px;padding-left:50px;">
			&nbsp;&nbsp;&nbsp;&nbsp;行业名称: <input id="industry_name_s" name="industry_name" class="easyui-textbox"  data-options="prompt:'行业名称'" />
			&nbsp;&nbsp;&nbsp;&nbsp;行业编码: <input id="industry_code_s" name="industry_code" class="easyui-textbox" data-options="prompt:'行业编码'"/>
			
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
		</div>
		 </fieldset>
		  </div>
    <div data-options="region:'center'" style="padding:5px;background:#FFFFFF;width:90%;">
    	<div  id="industry"></div>
    </div> 
    <div id="edit_dlg" class="easyui-dialog"  data-options="width: 500,height: 370,closed: true,cache: false,modal: true,top:50"></div>  
</body> 
</html>