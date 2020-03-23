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
<script type="text/javascript" src="<%=request.getContextPath()%>/batchManager/copygroupmanager.js?randomId=<%=Math.random()%>"></script>

<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="newbatch_id" value="<s:property value="model.batch_id"/>">
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
                     <dt style="height:26px;width:80px;">体检任务</dt>					
					<dd><select class="easyui-combobox" id="batch_id" name="batch_id"	data-options="height:26,width:140,panelHeight:'auto'"></select></dd>  
					<dd style="height:20px;width:140px;">已选单位 </dd>
					<dd style="height:20px;width:280px;" ><font id="companyname" style="color:red;font-weight:bold;font-style:italic;"><s:property value="model.comname"/></font></dd>
						<dd><a href="javascript:getgroupGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" style="height:26px;width:80px;" onclick="javascript:window.close();">关闭</a>
					</dd>
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