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
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmvisitlost/crmvisitlost.js?randomId=<%=Math.random()%>"></script>
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
<input id="create_timelist" value='<s:property value="create_time"/>' hidden="true"/>
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>失访查询</strong></legend>
			<div class="user-query" >
				<dl>
					<dt style="width:50px;"><s:text name="dahname"/></dt>
					<dd>
						<input  type="text" class="textinput" id="arch_num"    style="height:16px;width:80px;" />
					</dd>
					<dt style="width:40px;">姓名</dt>
					<dd>
						<input  type="text" class="textinput" id="user_name"    style="height:16px;width:80px;" />
					</dd>
				
					<dd><a href="javascript:getCrmVisitLostList();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
 <legend><strong>失访记录列表</strong></legend> 
      <table id="CrmVisitLostshow">
      </table>	
 </fieldset>
 
 <div id="dlg-edit" class="easyui-dialog" data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>