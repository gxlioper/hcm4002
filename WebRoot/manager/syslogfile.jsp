<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>  
<script type="text/javascript">
	$(function(){
		$("input").attr("maxlength","20");
		$("input",$(".easyui-textbox").next("span")).focus(function(){
	        $(this).select();
		});
	});
	$(document).ready(function () {
		$('#user_id').combobox({
			url : 'getSysLogUserList.action',
			editable : true, //不可编辑状态
			cache : false,
			width:100,
			height:26,
			panelHeight : 200,
			valueField : 'id',
			textField : 'chi_Name',
			onLoadSuccess : function(data) {			
			}
		});
		$('#xtgnid').combobox({
			url : 'getSysLogXtgnList.action',
			editable : true, //不可编辑状态
			cache : false,
			panelHeight : 200,
			width:150,
			height:26,
			valueField : 'ID',
			textField : 'NAME',
			onLoadSuccess : function(data) {			
			}
		});
		getgroupuserGrid();
	});
	
	function getgroupuserGrid(){
		var model={};	 
		$("#groupusershow").datagrid({
		url:'getSyslogFileList.action',
		dataType: 'json',
		queryParams:model,
		//toolbar:'#toolbar',
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		columns:[[
			{align:'center',field:'fileName',title:'文件名称',width:20},
			{align:'center',field:'fileSize',title:'大小',width:10},
			{align:'center',field:'fileDate',title:'创建日期',width:25},
			{align:'center',field:'xz',title:'下载',width:25,formatter:fn_xz},	
			{align:'center',field:'sc',title:'删除',width:25,formatter:fn_sc}
		]],	    	
		onLoadSuccess:function(value){ 
		    $("#datatotal").val(value.total);
		},
		height: window.screen.height-380,
		rownumbers:false,
		singleSelect:false,
		collapsible:true,
		pagination: true,
		fitColumns:true,
		striped:true,
		rowStyler:function(index,row){}   
		        //toolbar:toolbar	      
		});
	}
	
	function fn_xz(val,row){
		return '<a href=\"javascript:f_export(\''+row.fileName+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"下载\" /></a>';
	}
	
	function f_export(filePath){
		window.location.href='exportSyslogFile.action?filePath='+filePath;
	}
	
	function fn_sc(val,row){
		return '<a href=\"javascript:f_delete(\''+row.fileName+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}
	
	function f_delete(filePath){
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		$.ajax({
			url:'deleteSyslogFile.action',
			data:{"filePath":filePath},
			type:'post',
			success:function(data){
				$(".loading_div").remove();
				$.messager.alert("操作提示", data, "info");
				getgroupuserGrid();
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
	}
	
	function sys_log_copy(){
		var xtgnid = undefined;
		 if($("#ck_xtgnid")[0].checked){
			 xtgnid =  $('#xtgnid').combobox('getValue');
		 }
		 var time1 = undefined,time2 = undefined;
		 if($("#ck_date")[0].checked){
			 time1 = $("#start_date").datebox('getValue');
			 time2 = $("#end_date").datebox('getValue');
		 }
		 var user_id = undefined;
		 if($("#ck_user_id")[0].checked){
			 user_id = $('#user_id').combobox('getValue');
		 }
		 var types = undefined;
		 if($("#ck_types")[0].checked){
			 types = $('#types').combobox('getValue');
		 }
		 var data={
				 "xtgnid":xtgnid,
				 "userid":user_id,
				 "starttime":time1,	
				 "endtime":time2,	
				 "oper_type":types
		 };
		 
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		$.ajax({
			url:'backupSyslog.action',
			data:data,
			type:'post',
			success:function(data){
				$(".loading_div").remove();
				$.messager.alert("操作提示", data, "info");
				getgroupuserGrid();
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
	}
</script> 
<body>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:80px;"><input id="ck_user_id" type="checkbox" />操作人</dt>
					<dd><select id="user_id"></select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_xtgnid" type="checkbox" />功能</dt>
					<dd><select id="xtgnid"></select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox" checked="checked"/>操作日期</dt>
			         <dd><input class="easyui-datebox" type=text id="start_date" value="<s:property value="model.starttime"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:30px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="end_date" value="<s:property value="model.endtime"/>" style="width:100px;height:26px;"></input>
					 <dt style="height:26px;width:80px;"><input id="ck_types" type="checkbox"/>操作类型</dt>					
					 <dd><select class="easyui-combobox" id="types"
					data-options="height:26,width:100,panelHeight:'auto'">
						<option value="">全部</option>
						<option value="0">查询</option>
						<option value="1">插入</option>
						<option value="2">修改</option>
						<option value="3">删除</option>
						<option value="4">导入</option>
						<option value="5">登录</option>
						<option value="6">报表查询</option>
						<option value="9">其他</option>
					</select></dd> 
					<dd><a href="javascript:sys_log_copy();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">备份</a></dd> 
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>系统日志备份文件列表</strong></legend>
      <table id="groupusershow">
      </table>	
 </fieldset>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 </body>