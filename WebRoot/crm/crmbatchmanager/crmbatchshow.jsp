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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmbatchmanager/crmbatchshow.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<input type="hidden" id="company_id">
<input type="hidden" id="tatch_id">
<input type="hidden" id="batch_sign_nums" value="<s:property value="sign_num"/>">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:120px" id="qiandanjihuaname">
					     选择签单计划&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </dd>
                    <dd id="qiandanjihua" >
                      <input  class="easyui-combobox" data-options="panelHeight:'auto'" id="qiandan"
					style="height: 26px; width: 140px"/>
                    </dd>
                    <dd style="height:20px;width:120px">
					     选择体检类型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </dd>
                    <dd>
                      <input id="tijiantype" style="height: 26px; width: 140px"/>
                    </dd>
                      <dd><a href="javascript:getbatchGrid();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
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
 <div id="dlg-copy" class="easyui-dialog" style="z-index:2000;" data-options="width: 750,height: 450,closed: true,cache: false,modal: true,top:50"></div>
