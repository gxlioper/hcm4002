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
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmcustomerbirthday/customerbirthday.js?randomId=<%=Math.random()%>"></script>
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
<input id="plan_visit_datedata" value='<s:property value="plan_visit_date"/>' hidden="true">
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>生日客户查询</strong></legend>
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
					<dt style="width:70px;">选择生日</dt>
					<dd>
					<input  id="bir_dates"  value="<s:property value="model.plan_visit_date"/>"  hidden="true" style="height:16px;width:140px;" />
						<input  type="text" class="easyui-datebox" id="bir_date"    style="height:23px;width:140px;" data-options="prompt:'请选择查询日期'"/>
					</dd>
					<dd><a href="javascript:getCrmShengrikehuList();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
 <legend><strong>生日客户列表</strong></legend> 
      <table id="CrmShengrikehuList">
      </table>	
 </fieldset>
 
</body>
</html>