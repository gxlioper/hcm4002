<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/CrmSmsBaseTemplate/CrmSmsBaseTemplate.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","45");
})
</script>
<body >
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>">
<input type="hidden" id="baseurls" value="<%=request.getContextPath()%>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="exeurl" value="<s:property value="model.others"/>">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>短信模板查询</strong></legend>
			<div class="user-query">
				<dl>
					<dt>模板名称</dt>
					<dd><input class="easyui-textbox"  type="text" id="num"  style="height:26px;width:140px;"/></dd>
					<dt>模板分类</dt>
					<dd><input id="fenlei"  /></dd>
					<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;height:85%" >
<legend><strong>模板列表</strong></legend> 
      <table id="groupusershow" class="easyui-datagrid" >
      </table>	
 </fieldset>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  </body>