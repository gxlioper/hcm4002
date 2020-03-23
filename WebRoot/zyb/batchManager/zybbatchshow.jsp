<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/batchManager/zybbatchshow.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<input type="hidden" id="company_id">
<input type="hidden" id="tatch_id">
<input type="hidden" id="isaccounttype" value="<s:property value="model.isaccounttype"/>" />
<input type="hidden" id="isunaccounttype" value="<s:property value="model.isunaccounttype"/>" />
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:250px;">
					     选择单位&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" maxlength="20"  type="text" id="com_Name" value=""   style="height:26px;width:140px;"/>
					  <div id="com_name_list_div" style="display:none" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
					<dd style="height:20px;width:600px;" ><font id="companyname" style="color:red;font-weight:bold;font-style:italic;">单位未知</font></dd>
					
				</dl>

			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检任务列表</strong></legend>
      <table id="fanganlist" class="easyui-datagrid" >
      </table>	
 </fieldset>
 <div id="dlg-edit" class="easyui-dialog" style="z-index:2000;" data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog" style="z-index:2000;" data-options="width: 750,height: 450,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-custedit-remarke" class="easyui-dialog"
     data-options="width: 400,height: 380,closed: true,cache: false,modal: true,top:50"></div>