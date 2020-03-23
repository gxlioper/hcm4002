 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmsalescost/crmsalescostlist.js?randomId=<%=Math.random()%>"></script>
<!--表格内容溢出---显示省略号样式  -->
<style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style>
</head>
<body>
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>销售费用管理查询</strong></legend>
			<div class="user-query" >
				<dl>
                    <dt style="width:60px;">
				签单计划:
			</dt>
			<dd  style="height:16px;width:140px;">
				<input  class="easyui-combobox"  id="addbatch_num"  
					 class="easyui-validatebox"  value="<s:property  value="batch_num"/>"
					style="height: 26px; width: 140px;"/>
			</dd>
                    <dt style="width:40px;">
				销售员:
			</dt>
			<dd  style="height:16px;width:140px;">
				<input  class="easyui-combobox"  id="addsales_id"  
					 class="easyui-validatebox"  value="<s:property  value="sales_id"/>"
					style="height: 26px; width: 140px;"/>
			</dd>
					<dt style="width:60px;">开始日期</dt>
					<dd>
						<input  type="text" class="easyui-datebox" id="start_date"    style="height:23px;width:140px;" data-options="prompt:'请选择开始查询日期'"/>
					</dd>
					<dt style="width:70px;">结束日期</dt>
					<dd>
						<input  type="text" class="easyui-datebox" id="end_date"    style="height:23px;width:140px;" data-options="prompt:'请选择结束查询日期'"/>
					</dd>	
					
					<dt style="width:70px;">
				费用类型:
			</dt>
			<dd>
				<input  class="easyui-combobox"  id="addcost_type"
					 class="easyui-validatebox"  data-options="panelHeight:'auto'"
					style="height: 26px; width: 140px;"/>
			</dd> 

					<dd><a href="javascript:getCrmSalesCostList();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd>
				</dl>
			</div>
 </fieldset>
  <fieldset style="margin:5px;padding-right:0;">
 <legend><strong>销售管理列表</strong></legend>
 		<!-- <div id="tb"  style="width:300px;float: right;height: 10%">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"/a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true"/a>
		</div>  -->

      <table id="CrmSalesCostshow">
      </table>	
 </fieldset>
  <div id="toolbar">
  		<div>
				<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain"  id="" style="width: 118px;" onclick="javascript:xinzengjiankuan();"><span class="l-btn-left l-btn-icon-left" style="margin-top: 0px;"><span class="l-btn-text">新增借款记录</span><span class="l-btn-icon icon-add">&nbsp;</span></span></a>
				<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain"  id="" style="width: 118px;" onclick="javascript:xinzenghuankuan();"><span class="l-btn-left l-btn-icon-left" style="margin-top: 0px;"><span class="l-btn-text">新增还款记录</span><span class="l-btn-icon icon-add">&nbsp;</span></span></a>
				<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain"  id="" style="width: 118px;" onclick="javascript:bianji();"><span class="l-btn-left l-btn-icon-left" style="margin-top: 0px;"><span class="l-btn-text">编辑</span><span class="l-btn-icon icon-edit">&nbsp;</span></span></a>
				<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain"  id="" style="width: 118px;" onclick="javascript:shanchu();"><span class="l-btn-left l-btn-icon-left" style="margin-top: 0px;"><span class="l-btn-text">删除</span><span class="l-btn-icon icon-cancel">&nbsp;</span></span></a>
				<div style="width: 250px; font-size: 18px;float: right;font-weight:bold;margin:5px">借款总金额：<font id="jiekuan">0</font>元&nbsp;&nbsp;&nbsp;</div>
		<div style="width: 250px; font-size: 18px;float: right;font-weight:bold; color: #f00; margin:5px">还款总金额：<font id="huankuan">0</font>元&nbsp;&nbsp;&nbsp;</div>
		</div>
</div>

 <div id="dlg-edit" class="easyui-dialog" data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>