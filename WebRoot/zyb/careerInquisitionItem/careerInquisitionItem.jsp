<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>职业体检问诊项目管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/careerInquisitionItem/careerInquisitionItem.js"></script>
<!--表格内容溢出---显示省略号样式  -->
<style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style>


<body >
<input type="hidden"    id="intss"  value="<s:property value='intss'/>"/>
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>职业体检问诊项目查询</strong></legend>
			<div class="user-query" >
				<dl>
					<dt style="width:70px;">项目编码</dt>
					<dd>
						<input  type="text" class="textinput" id="item_code_c"    style="height:23px;width:140px;" />
					</dd>
					<dt style="width:70px;">项目名称</dt>
					<dd>
						<input  type="text" class="textinput" id="item_name_c"    style="height:23px;width:140px;" />
					</dd>
					
					<dd><a href="javascript:getOccuhazardClassList();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="height: 85%;padding-bottom: 10px;">
<legend><strong>职业体检问诊项目列表</strong></legend> 
      <table id="OccuhazardClassshow" >
      </table>	
 </fieldset>
 <div id="dlg-custedit" ></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>