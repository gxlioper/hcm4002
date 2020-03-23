<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/examSet/examSet.js?randomId=<%=Math.random()%>"></script>
<!--表格内容溢出---显示省略号样式  -->
<style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style>


<body >
<input type="hidden"   id="intss"  value="<s:property value='intss'/>"/>
<input type="hidden"  id="app_type" value="<s:property value="#session.defaultapp.comid"/>"/>
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>">
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检套餐查询</strong></legend>
			<div class="user-query" >
				<dl>
					<dt style="width:70px;">套餐名称</dt>
					<dd>
						<input  type="text" class="textinput" id="set_name"  style="height:23px;width:140px;" />
					</dd>
					<dt style="text-align: right;">
						适用性别：
					</dt>
					<dd>
						<input type="radio"   value=''  onclick="getgroupuserGrid();"  id='sexN' name='Jsex' checked="checked"  />&nbsp;全部&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio"   value='男'    onclick="getgroupuserGrid();"  id='sexN' name='Jsex'  />&nbsp;&nbsp;男&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio"  value='女'     onclick="getgroupuserGrid();" id='sexNv' name='Jsex'  />&nbsp;&nbsp;女
					</dd>
					<dt style="width:90px;text-align: right">修改时间</dt>
					<dd>
						&nbsp;&nbsp;<input class="easyui-datebox"   type="text" id="update_time"  style="height:23px;width:140px;"/>
					</dd>
					<dd>至&nbsp;&nbsp;<input class="easyui-datebox"  type="text" id="update_times"  style="height:23px;width:140px;"/></dd>
					<dt style="height:26px;width:65px;"><input id="chkItem" name="chkItem" type="checkbox" checked="checked" value="Y"/>启用</dt>		
					<dt style="height:26px;width:65px;"><input id="chkItem" name="chkItem" type="checkbox" value="N"/>停用</dt>
					<dd><a href="javascript:getgroupuserGrid();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检套餐列表</strong></legend> 
      <table id="groupusershow" style="height:525px"  >
      </table>	
 </fieldset>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 1200,height: 590,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>