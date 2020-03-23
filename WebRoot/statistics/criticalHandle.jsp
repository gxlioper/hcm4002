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
<title>危急值处理情况统计</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/default/ecard/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function searchFun(){
		getGrid();
	}
	
	$(document).ready(function () {
	    getGrid();
	});
	
	function getGrid(){
		var chk_value ="";    
		  $('input[name = chkItem]:checked').each(function(){    
		   chk_value=chk_value+","+($(this).val());    
		  }); 
		  if(chk_value.length>1){
			  chk_value=chk_value.substring(1,chk_value.length);
		  }
	       var model = {
	    		   "exam_num": $('#exam_num_s').textbox('getValue'),
	    		   "user_name": $('#user_name_s').textbox('getValue'),
	    		   "done_flag":$("#done_flag_ss").textbox('getValue'),
	    		   "time1":$("#time1").datebox('getValue'),
	    		   "time2":$("#time2").datebox('getValue'),
	    		   "time3":$("#time3").datebox('getValue'),
	    		   "time4":$("#time4").datebox('getValue'),
	    		   "chkItem":chk_value,
	    		   };
		   $("#criticalHandleList").datagrid({
			url: 'criticalhandleShow.action',
			queryParams: model,
			ctrlSelect:true,
	        pageSize: 15,//每页显示的记录条数，默认为10 
	        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			sortOrder: 'asc',
			//height:400,
	        columns:[[
	                  {align:'center',field:"exam_num","title":"体检编号",width:20,sortable:true},
	                  {align:'center',field:"company_name","title":"单位",width:20,sortable:true},
	        		  {align:"center",field:"user_name","title":"姓名","width":20,sortable:true},
	        		  {align:"center",field:"exam_result","title":"危急值结果","width":30,sortable:true},
	        		  {align:"center",field:"done_flag_s","title":"处理标识","width":18,sortable:true},
	        		  {align:"center",field:"done_date","title":"处理日期","width":20,sortable:true},
	        		  {align:"center",field:"create_time","title":"危机生成时间","width":20,sortable:true},
	        		  {align:"center",field:"chi_name","title":"危机录入者","width":20,sortable:true},
	        		  {align:"center",field:"note","title":"备注","width":20}
	        		  ]],
		    onLoadSuccess:function(value){
		    	//$("#datatotal").val(value.total);
		    },
		    height: window.screen.height-400,
			nowrap:false,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
		});
	}
	</script>
	</head>
	<body>
	<fieldset style="margin:5px;padding-right:0;">
	<legend><strong>信息检索</strong></legend>
	<div  class="user-query" >
		<dl>
			<dt style="height:26px;width:100px;  text-align: center;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_num"/>体检编号</dt>
			<dd><input class="easyui-textbox"  type="text" id="exam_num_s" value="" style="height:26px;width:120px;"/></dd>
			
			<dt style="height:26px;width:80px;text-align: center;"><input id="chkItem" name="chkItem" type="checkbox" value="user_name"/>姓名</dt>
			<dd><input class="easyui-textbox"  type="text" id="user_name_s" value="" style="height:26px;width:120px;"/></dd>
			
			<dt style="height:26px;width:80px;text-align: center;"><input id="chkItem" name="chkItem" type="checkbox" value="done_flag"/>处理状态</dt>
			<dd><select class="easyui-combobox" id="done_flag_ss" data-options="height:26,width:80,panelHeight:'auto'" >
			<option value="1">已处理</option><option value="0">未处理</option></select></dd>
			
			<dt style="height:26px;width:80px;text-align: center;"><input id="chkItem" name="chkItem" type="checkbox" value="done_date"/>处理时间</dt>
			<dd><input class="easyui-datebox" id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"/>至</dd>
			<dd><input class="easyui-datebox" id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"/></dd>
			
			<dt style="height:26px;width:120px;text-align: center;"><input id="chkItem" name="chkItem" type="checkbox" checked="checked" value="create_time"/>危机生成时间</dt>
			<dd><input class="easyui-datebox" id="time3" value="<s:property value="model.time3"/>" style="width:100px;height:26px;"/>至</dd>
			<dd><input class="easyui-datebox" id="time4" value="<s:property value="model.time4"/>" style="width:100px;height:26px;"/></dd>
			
			<dd><a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:80px;">查询</a></dd>
		</dl>
	</div>
	 </fieldset>
		<fieldset style="margin:5px;padding-right:0;">
		<legend><strong>危急值处理情况统计</strong></legend>
		  <table id="criticalHandleList" ></table>
		</fieldset>
	</body>
	</html>