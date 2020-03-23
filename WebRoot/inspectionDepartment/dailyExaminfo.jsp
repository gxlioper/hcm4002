<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<title>每日体检者构成</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/dailyExaminfo.js?randomId=<%=Math.random()%>"></script>
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
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:130px;"/></dd>
					<dt style="height:26px;width:30px;"><input id="ck_date" type="checkbox" checked="checked"/></dt>
					<dt style="height:26px;width:85px;margin-top:-4px;"><select class="easyui-combobox" id="date_type" data-options="height:26,width:80,panelHeight:'auto',editable:false">
							<option value="">体检日期</option>
							<option value="1">检查日期</option>
							</select>
					</dt>
					<dd><input class="easyui-datetimebox"    id="start_date"  value="<s:property value="model.s_join_date"/>"   data-options="showSeconds:false" style="width:130px;height:26px;"/></dd>
                     <dt style="height:26px;width:30px;">至</dt>
                     <dd><input class="easyui-datetimebox" id="end_date" value="<s:property value="model.e_join_date"/> 23:59:59"   data-options="showSeconds:false" style="width:130px;height:26px;"/></dd>
                     <dt style="height:26px;width:50px;"><input id="ck_searchphone" type="checkbox"/>电话</dt>					
					 <dd><input class="easyui-textbox"  type="text" id="searchphone" value="" style="height:26px;width:100px;"/></dd>  
			         <dt style="height:26px;width:50px;"><input id="ck_searcsex" type="checkbox"/>性别</dt>					
					 <dd><select class="easyui-combobox" id="searcsex"
							data-options="height:26,width:70,panelHeight:'auto'">
							<option value="">不限</option>
							<option value="男">男</option>
							<option value="女">女</option>
							</select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_examtype" type="checkbox"/>体检类型</dt>					
					<dd><select class="easyui-combobox" id="examtype"
					data-options="height:26,width:100,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="G">个检</option>
						<option value="T">团检</option>
					</select></dd>  
				</dl>
				<dl>
				   <dt style="height:26px;width:60px;"><input id="ck_arch_num" type="checkbox"/><s:text name="dahname"/></dt>					
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:100px;"/></dd>   
                    <dt style="height:26px;width:60px;"><input id="ck_id_num" type="checkbox"/>身份证</dt>					
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:130px;"/></dd>   
                    <dt style="height:26px;width:80px;"><input id="ck_company_id" type="checkbox" />选择单位</dt>					
					<dd><input type="hidden" id="company_id" /><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:280px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:370px;position:fixed;max-height:250px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                    <dt style="height:26px;width:50px;"><input id="ck_levelss" type="checkbox"/>部门</dt>					
					<dd><select class="easyui-combobox" id="levelss"
					data-options="height:26,width:155"></select></dd>   
                     <dt style="height:26px;width:50px;"><input id="ck_status" type="checkbox"/>状态</dt>					
					<dd><select class="easyui-combobox" id="statusss" name="statusss"
					data-options="height:26,width:70,panelHeight:'auto'"></select></dd>

					<dt style="height:26px;width:80px;"><input id="ck_tjlb" type="checkbox"/>体检类别</dt>
					<dd><select class="easyui-combobox" id="tjlb"
								data-options="height:26,width:100,panelHeight:'auto'">
						<option value="0" selected="selected">不限</option>
						<option value="1">健康体检</option>
						<option value="2">职业病体检</option>
					</select></dd>

					<dd><a href="javascript:chaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:60px;">查询</a>
						<a href="javascript:daochu();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">导出EXCEL</a>
						<a href="javascript:daochuxiangxi();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:180px;">详细信息导出EXCEL</a>
					</dd>
				 </dl>
			</div>
 </fieldset>
 </div>
 
 <div data-options="region:'center'">
 	<div class="easyui-accordion" fit="true" id="tjgc_lb">
 		<div title="概览" data-options="selected:true" style="padding:10px;">   
        	<table id="groupusershow"></table>
    	</div>   
	    <div title="详细信息" data-options="selected:true">   
	        <div class="easyui-layout" fit="true">
	        	<div data-options="region:'center'">
 					<table id="examinfolisthow"></table>
 				</div>
 				<div data-options="region:'east',split:true,title:'收费项目明细'" style="width:70%;">
 					<table id="exam_item_list"></table>
 				</div>
	        </div>    
	    </div>
 	</div>
 </div>
 </div>
 </body>
 </html>