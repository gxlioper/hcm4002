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
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmbatchmanager/crmcontractManager.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<body style="height:500px;">
<input type="hidden" id="company_id" maxlength="20" value="<s:property value="model.company_id"/>">
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:100px" id="qiandanjihuaname">
					     选择签单计划&nbsp;&nbsp;&nbsp;
                    </dd>
                    <dd id="qiandanjihua" >
                      <input  class="easyui-combobox" data-options="panelHeight:'auto'" id="qiandan"/>
                    </dd>
                    <dd style="height:20px;">
					     审核状态&nbsp;&nbsp;&nbsp;
                    </dd>
                    <dd><select class="easyui-combobox" id="check_status_d" data-options="height:26,width:100,panelHeight:'auto'">
                    	<option value="">全部</option>
                    	<option value="0" selected="selected">未审核</option>
                    	<option value="2">审核通过</option>
                    	<option value="1">审核未通过</option>
                    </select>
                    <dd style="height:20px;width:100px;">
					     <a href="javascript:chaxun();" class="easyui-linkbutton c6" style="width:100px;">查询</a>
                    </dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>合同列表</strong></legend>
      <table id="contractlist">
      </table>	
 </fieldset>
 
 <div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-groupshow" class="easyui-dialog"  data-options="width: 750,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 </body>