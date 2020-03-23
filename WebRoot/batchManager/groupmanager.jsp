<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/batchManager/groupmanager.js?randomId=<%=Math.random()%>"></script>

<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id" value="<s:property value="model.batch_id"/>">
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:140px;">单位名称 </dd>
					<dd style="height:20px;width:280px;" ><font id="companyname" style="color:red;font-weight:bold;font-style:italic;"><s:property value="model.comname"/></font></dd>
					<dd style="height:20px;width:140px;" >体检任务名称：</dd>
					<dd style="height:20px;width:280px;" ><font id="batchname" style="color:red;font-weight:bold;font-style:italic;"><s:property value="model.batch_name"/></font>(<a href="javascript:f_batchshow()">查看</a>)</dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>分组列表</strong></legend>
      <table id="grouplist" class="easyui-datagrid" >
      </table>	
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>分组对应套餐</strong></legend>
      <table id="examsetlist" class="easyui-datagrid" >
      </table>	
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>分组对应体检项目</strong></legend>
      <table id="chargitemlist" class="easyui-datagrid" >
      </table>	
 </fieldset>
 <div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-groupshow" class="easyui-dialog"  data-options="width: 750,height: 380,closed: true,cache: false,modal: true,top:50"></div>