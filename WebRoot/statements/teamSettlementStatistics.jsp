<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<title>团体结算查询页面</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/statements/teamSettlementStatistics.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
	$(function(){
		$("input").attr("maxlength","20");
	})
</script>
</head> 
<body>
<div class="easyui-layout" fit="true">
 <div data-options="region:'west',border:false,split:true" style="width:32%;"> 
  <div class="easyui-layout" fit="true">
  	<div data-options="region:'north',border:false" style="height:105px;">
 	<fieldset style="margin:5px;padding-right:0;">
	<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="height:26px;width:60px;">体检日期</dt>
					<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.s_join_date"/>" style="width:100px;height:26px;"/></dd>
					<dt style="height:26px;width:20px;">至</dt>
                     <dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.e_join_date"/>" style="width:100px;height:26px;"/></dd>
					 
				</dl>
				<dl>
					<dt style="height:26px;width:60px;">选择单位</dt>					
					<dd><input type="hidden" id="company_id" /><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:230px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:-21px;position:fixed;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                    <dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:60px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 </div>
 <div data-options="region:'center'">
		<table id="groupusershow">
	     </table>
	 </div>
 </div>
</div>
<div data-options="region:'center'">
	<table id="examinfoshow">
	</table>
</div>
 </div>
 <div id="dlg-edit"></div>
 </body>
 </html>