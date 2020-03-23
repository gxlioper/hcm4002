<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/salesmanagement/batchCheckManager.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" >
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<body style="height:500px;">
<input type="hidden" id="check_type_d" value="<s:property value="check_type" />">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:120px">
					     选择签单计划&nbsp;&nbsp;&nbsp;
                    </dd>
                    <dd>
                      <input class="easyui-combobox" id="qiandanjihua" style="height: 26px; width: 240px"/>
                    </dd>
                    <dd style="height:20px;">
					     审核状态&nbsp;&nbsp;&nbsp;
                    </dd>
                    <dd><select class="easyui-combobox" id="check_status_d" data-options="height:26,width:140,panelHeight:'auto'">
                    	<option value="">全部</option>
                    	<option value="0" selected="selected">未审核</option>
                    	<option value="2">审核通过</option>
                    	<option value="1">审核未通过</option>
                    </select>
                    </dd>
                    <dd><a href="javascript:chaxun();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检任务列表</strong></legend>
      <table id="fanganchecklist">
      </table>	
 </fieldset>
 
 <div id="dlg-checkedit" class="easyui-dialog" style="z-index:2000;" data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog" style="z-index:2000;" data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show_mx" class="easyui-dialog" data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50">
 	<div style="width: 720px;">
 	<fieldset style="margin:5px;padding-right:5;">
      <legend><strong>分组列表</strong></legend>
      <table id="groupchecklist" class="easyui-datagrid" >
      </table>	
 	</fieldset>
 	<fieldset style="margin:5px;padding-right:5;">
	<legend><strong>分组对应套餐</strong></legend>
      <table id="examsetchecklist" class="easyui-datagrid" >
      </table>	
 	</fieldset>
 	<fieldset style="margin:5px;padding-right:5;">
		<legend><strong>分组对应体检项目</strong></legend>
      <table id="chargitemchecklist" class="easyui-datagrid" >
      </table>	
 	</fieldset>
    </div>
</div>
 </div>
</body>