<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>排班管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset= utf-8">
<link type="text/css" rel="stylesheet" href="/themes/default/style.css" />
<link type="text/css" href="/scripts/jquery-ui.css" rel="stylesheet" />
<script type="text/javascript" src="/scripts/jquery.min.js"></script>
<script type="text/javascript" src="/scripts/jquery.validatebox.js"></script>
<script type="text/javascript" src="/scripts/jquery-ui.js"></script>
<script type="text/javascript" src="/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="/scheduling/calendar.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="/scheduling/cal.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">

    var dg;
    //数据加载
	$(document).ready(function () {
			 paibanguanli();
		});
    
	/**
	  * 查询按钮
	  */
	function searchFun(){
		paibanguanli();
	}
	/**
	  * 清除查询条件
	  */
	function cleanFun(){
		$('#chi_name').textbox('setValue',"");
		paibanguanli();
	}
	 
	/**
	  * 定义工具栏
	  */
	 var toolbar=[{
			iconCls: 'icon-edit',
			text:'批量排班',
			handler: function(){
				var row=dg.datagrid('getSelections');//返回被选中的行，如果没有则返回null
				if (row.length==0) {
					$.messager.alert('警告','请选择要编辑的行!!!','erro');
				}else{
					user_id_arry.length = 0;
					for(i=0;i<row.length;i++){
						user_id_arry.push(row[i].user_id);
					}
					
					$("#edit_dlg").dialog({
						title:'批量排班',
						href:'schedulingBatch.action',
					    modal: true
					});
					$("#edit_dlg").dialog("open");
				}
			}
		}];
	
	function  paibanguanli(){
		//var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 //$("body").prepend(str);
		var model = {"user_name": $('#chi_name').textbox('getValue'),};
		    dg=$("#paiban").datagrid({
			url: 'schedulinglist.action',
			dataType: 'json',
			queryParams:model,
		    pageSize: 15,//每页显示的记录条数，默认为10 
		    pageList:[15,20,30,60,75],//可以设置每页记录条数的列表 
			columns:[[
						{field :'num',title:'编号',align:'center',"checkbox":true},
			            {field:'updatepaiban',title:'排班',width:80,align:'center',"formatter":pbachieve},
			            {field :'user_id',title:'用户编号',align:'center'},
			            {field:'chi_name',title:'用户姓名',width:80,align:'center'},
			            {field:'working_date',title:'上班时间',width:80,align:'center'},
			            {field:'updater',title:'更新者',width:80,align:'center'},
			            {field:'update_time',title:'更新时间',width:80,align:'center'},
			            {field:'lookserch',title:'排班详情',width:80,align:'center',"formatter":lookachieve},
						]],
					    onLoadSuccess:function(value){
				    		$("#datatotal").val(value.total);
				    		$(".loading_div").remove();
				    	}, 
						singleSelect:false,
					    collapsible:true,
						pagination: true,
					    fitColumns:true,
					    striped:true,
					    //fit:true,
				        toolbar:toolbar
		});
	}
	//排班
	 function pbachieve(val,row){
		 var str = '<a href=\"javascript:scheduling(\''+row.user_id+'\')\" class="easyui-linkbutton" style="width:80px;">排班</a>'
		 return str;
	 }

	//paiban
	 function scheduling(user_id){
		 user_id_arry.length = 0;
		 user_id_arry.push(user_id);
				$("#edit_dlg").dialog({
					title:'排班',
					href:'schdeulingAchieve.action?id='+user_id,
					modal: true,
				}); 
			    $("#edit_dlg").dialog('open');
	 }
	
		
	</script>
	

</head>
<body >
	
	<fieldset style="margin:5px;padding-right:20px;">
	<legend><strong>排班信息检索</strong></legend>
	<div id="tb" style="padding-right:20px;padding-left:100px;">
		用户姓名: <input id="chi_name" name="chi_name" class="easyui-textbox" data-options="prompt:'用户名'"/>
		<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
		<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
	 </div>
	 </fieldset>
	 <fieldset style="margin:5px;padding-right:20px;height:95%;">
	<legend><strong>浏览排班信息</strong></legend>
		<div id="paiban"  class="easyui-datagrid"></div>
		<div id="edit_dlg" class="easyui-dialog"  data-options="width: 480,height: 370,closed: true,cache: false,modal: true,top:50"></div>
		<div id="looks" class="easyui-dialog"  data-options="width: 450,height: 350,closed: true,cache: false,modal: true,top:50"></div>
	 </fieldset>
</body>


</html>