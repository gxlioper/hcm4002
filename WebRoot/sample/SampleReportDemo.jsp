<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sample/SampleReportDemo.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","45");
})
</script>
<body style="height:460px;">
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>">
<input type="hidden" id="baseurls" value="<%=request.getContextPath()%>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="exeurl" value="<s:property value="model.others"/>">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>报告样本查询</strong></legend>
			<div class="user-query">
				<dl>
					<dt>样本编码</dt>
					<dd><input class="easyui-textbox"  type="text" id="num"  style="height:26px;width:140px;"/></dd>
					<dt>样本名称</dt>
					<dd><input class="easyui-textbox"  type="text" id="demo_name"   style="height:26px;width:140px;"/></dd>
					
					<dt style="height:26px;width:65px;"><input id="chkItem" name="chkItem" type="checkbox" checked="checked" value="Y"/>启用</dt>		
					<dt style="height:26px;width:65px;"><input id="chkItem" name="chkItem" type="checkbox" value="N"/>停用</dt>		
					
					<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>报告样本列表</strong></legend> 
      <table id="groupusershow" class="easyui-datagrid" >
      </table>	
 </fieldset>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  </body>