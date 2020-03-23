<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>签单跟踪</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmsigntracking/crmsigntrackinglist.js?randomId=<%=Math.random()%>"></script>
<!--表格内容溢出---显示省略号样式  -->
<style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style>
</head>
<body>
<div class="easyui-layout" fit="true">
 <div data-options="region:'west',split:true" title="签单跟踪日历" style="width:257px;">
 <div  id="genzongrili" class="easyui-calendar" style="width:250px;height:250px;"></div>
 </div>
 <div  data-options="region:'center'">    
    <fieldset style="margin:5px;padding-right:0;">
<legend><strong>签单跟踪查询</strong></legend>
			<div class="user-query" >
				<dl>
                    <dt style="width:60px;">
				签单计划：
			    </dt>
				<dd>
				<input type="text" class="textinput"  id="serch_sign_num" class="easyui-combobox"/>
				</dd>
                    <dt style="width:100x;">
				目标联系人姓名：
				</dt>
				<dd>
					<input  type="text" class="textinput" id="addcontact_name" class="easyui-combobox"/>
				</dd>
					<dt style="width:60px;">开始日期：</dt>
					<dd>
						<input  type="text" class="easyui-datebox" id="start_date"    style="height:23px;width:100px;" data-options="prompt:'请选择开始查询日期'"/>
					</dd>
					<dt style="width:70px;">结束日期：</dt>
					<dd>
						<input  type="text" class="easyui-datebox" id="end_date"    style="height:23px;width:100px;" data-options="prompt:'请选择结束查询日期'"/>
					</dd>
					<dd><a href="javascript:chaxun();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd>
				</dl>
			</div>
 </fieldset>
  <fieldset style="margin:5px;padding-right:0;height:82%;">
 <legend><strong>签单跟踪列表</strong></legend>
      <div id="CrmSignTrackingshow">
      </div>	
 </fieldset>
</div>
 </div>
 <div id="dlg-edit" class="easyui-dialog" data-options="width: 700,height: 400,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>