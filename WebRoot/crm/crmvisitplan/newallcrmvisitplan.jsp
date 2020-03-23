 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmvisitplan/crmvisitplanlist.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<!--表格内容溢出---显示省略号样式  -->
<style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style>
</head>
<body>
<input  type="text" class="textinput" id="flag"    style="height:16px;width:140px;"  hidden="true" value="<s:property value="flag"/>"/>
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>健康计划查询</strong></legend>
			<div class="user-query" >
				<dl>
					<dt style="width:50px;"><s:text name="dahname"/></dt>
					<dd>
						<input  type="text" class="textinput" id="arch_num"    style="height:16px;width:80px;" />
					</dd>
					<dt style="width:50px;"><s:text name="tjhname"/></dt>
					<dd>
						<input  type="text" class="textinput" id="exam_num"    style="height:16px;width:80px;" />
					</dd>
					<dt style="width:40px;">姓名</dt>
					<dd>
						<input  type="text" class="textinput" id="name"    style="height:16px;width:80px;" />
					</dd>
					<dt style="width:60px;">开始日期</dt>
					<dd>
						<input  type="text" class="easyui-datebox" id="startTime"    style="height:23px;width:100px;" data-options="prompt:'请选择开始查询日期'"/>
					</dd>
					<dt style="width:70px;">结束日期</dt>
					<dd>
						<input  type="text" class="easyui-datebox" id="endTime"    style="height:23px;width:100px;" data-options="prompt:'请选择结束查询日期'"/>
					</dd>	
					<dt style="width:70px;">
				回访状态
			</dt>
			<dd>
				<input  class="easyui-combobox"  id="visit_status"
					 class="easyui-validatebox"  data-options="panelHeight:'auto'"
					style="height: 26px; width: 100px;"/>
			</dd>

					<dd><a href="javascript:queryplanorallplan();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
 <legend><strong>健康计划列表</strong></legend> 
      <table id="CrmVisitPlanshow">
      </table>	
      <table id="CrmAllVisitPlanshow">
      </table>	
 </fieldset>
 
 <div id="dlg-edit" class="easyui-dialog" data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>