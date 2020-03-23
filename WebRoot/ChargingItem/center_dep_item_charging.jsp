<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="/themes/default/style.css" />
<link type="text/css" href="/scripts/jquery-ui.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="/scripts/jquery.validatebox.js"></script>
<script type="text/javascript" src="/scripts/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/ChargingItem/center_dep_item_charging.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<!--表格内容溢出---显示省略号样式  -->
<style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style>
<body >
<input type="hidden" id="leftSelectDeptId" value="" />
<fieldset style="margin:5px;">
		<legend><strong>收费项目查询</strong></legend>
			<div class="user-query">
				<dl>
					<dt  style="width:70px">名称简拼</dt>
					<dd><input class="textinput" type="text" id="pinyin" style="height:23px;width:140px;"/></dd>
					<dt style="width:90px;">项目编码</dt>
					<dd><input class="textinput"  type="text" id="item_code"  style="height:23px;width:140px;"
					class="easyui-validatebox" /></dd>
					<dt style="width:90px;">影像关联码</dt>
					<dd><input class="textinput" type="text" id="view_num" style="height:23px;width:140px;"/></dd>
					<dt style="width:100px">接口标识</dt>
					<dd>
						<select class="easyui-combobox" type="text" id="interface_flag"  data-options="panelHeight:100"  style="height:23px;width:140px;">
							<option value="">无</option>
							<option value="0">不传值</option>
							<option value="1">系统内</option>
							<option value="2">系统外</option>
						</select>
					</dd>
					<dt style="width:100px">样本分类</dt>
					<dd>
						<input type="hidden" id="demo_type"value="" /> 
						<input type="text"class="textinput" id="addDemo_type" data-options="panelHeight:'auto'" style="height: 26px; width: 140px;" />
					</dd>
				</dl>
				<dl>
					<dt style="width: 70px">项目名称</dt>
					<dd><input class="textinput"  type="text" id="item_name"   style="height:23px;width:140px;"/></dd>
					<dt style="width: 90px">检验关联码</dt>
					<dd><input class="textinput" type="text" id="exam_num" style="height:23px;width:140px;"/></dd>
					<dt style="width: 90px">导引单分类</dt>
					<dd><input class="textinput" type="text" id="guide_category"    style="height:23px;width:140px;"/></dd>
					<dt style="width: 100px;">his系统关联码</dt>
					<dd><input class="textinput" type="text" id="his_num" style="height:23px;width:140px;"/></dd>
					
				    <dt style="height:26px;width:65px;"><input id="chkItem" name="chkItem" type="checkbox" checked="checked" value="Y"/>启用</dt>		
					<dt style="height:26px;width:65px;"><input id="chkItem" name="chkItem" type="checkbox" value="N"/>停用</dt>		
								
					<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;height:30px;">查询</a></dd>
				</dl>
			</div>
		 </fieldset>
		 <fieldset style="margin:5px;padding-right:0;height:58%;width: 98.5%">
	<legend><strong>收费项目列表</strong></legend> 
      <table id="groupusershow">
      </table>	
 </fieldset>
 <div id="dlg-custedit" ></div>
 <!-- <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div> -->
 <!-- <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div> -->
 <div id="edit_dlg" class="easyui-dialog"  data-options="width: 480,height: 390,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg_s_show" class="easyui-dialog"  data-options="width: 480,height: 390,closed: true,cache: false,modal: true,top:50"></div>
 
 </body>
</html>