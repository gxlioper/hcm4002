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
<title>疾病知识库</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/DiseaseKnowloedgeManage/DiseaseKnowloedge_look.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
	$(document).ready(function (){
		getGrid();
		getsuggestionGrid(0);
		$('#disease_level_z').combobox({
			url :'getDatadis.action?com_Type=JBDJ',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
	});
	$('#disease_classification').combobox({
		url :'getDatadis.action?com_Type=JBFL',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
	}) 
});
	
</script>

</head>
<body>
	<div class="easyui-layout" fit="true">
      <div  data-options="region:'north'" style="height:80px;">
		<fieldset style="margin:5px;padding-right:20px;">
		<legend><strong>疾病信息检索</strong></legend>
		<div id="tb" style="padding-right:20px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;疾病类型:<select id="disease_type_s"> 
						<option value=""></option>
	    	       		<option  value="Y">阳性发现</option>
	    	       		<option value="D">疾病</option>
	    	       </select> &nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;疾病名称: <input id="disease_name_s" name="disease_name_s" class="easyui-textbox" data-options="prompt:'疾病名称'" size="26"/>&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;疾病级别: <input id="disease_level_z" name="disease_level_z" class="easyui-combobox" data-options="prompt:'疾病级别'" size="26"/>&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a> &nbsp;&nbsp;&nbsp;&nbsp;	       
			<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
		</div>
		</fieldset>
	 </div>
	 <div  data-options="region:'center'" >
	  <div id="centerLeft" style="height:98%;">
		<fieldset style="margin-right:0px;padding-right:10px;height:95%;Float:left;width:41%;">
			<legend><strong>疾病信息列表</strong></legend>
			   <div id="diseaseKnowloedgeList"> </div>
		</fieldset>
		<fieldset style="margin-left:0px;padding-right:10px;height:95%;Float:left;width:55%;">
			<legend><strong>健康建议</strong></legend>
			   <div id="suggestionList"> </div> 
		</fieldset>
	 </div>
	 </div>
	 </div>
	<div id="dlg-edit" class="easyui-dialog" data-options="width: 830,height: 500,closed: true,cache: false,modal: true,top:5"></div>
	<div id="dlg-look" class="easyui-dialog" data-options="width: 830,height: 520,closed: true,cache: false,modal: true,top:5"></div>
	<div id="dlg-add" class="easyui-dialog" data-options="width: 800,height: 430,closed: true,cache: false,modal: true,top:5"></div>
	
	</body>
	</html>