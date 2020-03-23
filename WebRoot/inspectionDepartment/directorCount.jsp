<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<title>体检进度查询页面</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/directorCount.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
	$(function(){
		$("input").attr("maxlength","20");
		$('#exam_num').textbox('textbox').css('ime-mode','disabled');
		$('#exam_num').textbox('textbox').focus();
	})
</script>
</head> 
<body>
<div class="easyui-layout" fit="true">
 <div data-options="region:'north',border:false" style="height:105px;"> 
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:60px;"><input id="ck_exam_num" type="checkbox"/><s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:100px;ime-mode: disabled;"/></dd> 
					<dt style="height:26px;width:60px;"><input id="ck_custname" type="checkbox" />姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox" checked="checked"/>体检日期</dt>
					<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.s_join_date"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:30px;">至</dt>
                     <dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.e_join_date"/>" style="width:100px;height:26px;"/></dd>
                      <dt style="height:26px;width:70px;"><input id="ck_zongjianyisheng" type="checkbox"/>总检医生</dt>					
					<dd><select class="easyui-combobox" id="zongjianyisheng" name="zongjianyisheng"
					data-options="height:26,width:100,panelHeight:'auto'"></select></dd>   
                      <dt style="height:26px;width:70px;"><input id="ck_zongjiandate" type="checkbox"/>总检日期</dt>					
					 <dd><input class="easyui-datebox" id="zongjiandate" value="<s:property value="model.e_zongjiandate"/>" style="width:100px;height:26px;"/></dd>  
				</dl>
				<dl>
				   <dt style="height:26px;width:60px;"><input id="ck_arch_num" type="checkbox"/><s:text name="dahname"/></dt>					
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:100px;"/></dd>   
                    <dt style="height:26px;width:60px;"><input id="ck_id_num" type="checkbox"/>身份证</dt>					
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:135px;"/></dd>   
                    <dt style="height:26px;width:80px;"><input id="ck_company_id" type="checkbox" />选择单位</dt>					
					<dd><input type="hidden" id="company_id" /><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:240px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:375px;position:fixed;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                    <dt style="height:26px;width:70px;"><input id="ck_examtype" type="checkbox"/>体检类型</dt>					
					<dd><select class="easyui-combobox" id="examtype"
					data-options="height:26,width:100,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="G">个检</option>
						<option value="T">团检</option>
					</select></dd>   
                     <dt style="height:26px;width:70px;"><input id="ck_status" type="checkbox"/>状态</dt>					
					<dd><select class="easyui-combobox" id="statusss" name="statusss"
					data-options="height:26,width:100,panelHeight:'auto'"></select></dd>                      
					<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:60px;">查询</a></dd>
				 </dl>
			</div>
 </fieldset>
 </div>
 <div data-options="region:'west',split:true" style="width:40%;">
      <table id="groupusershow">
      </table>	
</div>
<div data-options="region:'center'">
	<div class="easyui-tabs" data-options="fit:true,border:false,plain:true">   
	    <div title="检查结果列表" style="padding:5px;">
	    	<table id="item_result"></table> 
	    </div>   
	</div>  
</div>
 </div>
 <div id="dlg-edit"></div>
 </body>
 </html>