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
<script type="text/javascript" src="<%=request.getContextPath()%>/SysSurvey/SysSurvey.js?randomId=<%=Math.random()%>"></script>
<!--表格内容溢出---显示省略号样式  -->
<style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style>


<body >
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>题目查询</strong></legend>
			<div class="user-query" >
				<dl>
					<dt style="width:70px;">题目编码</dt>
					<dd>
						<input  type="text" class="textinput" id="c_code"  style="height:23px;width:140px;" />
					</dd>
					<dt style="text-align: right;">
						题目名称
					</dt>
					<dd>
						<input  type="text" class="textinput" id="c_name"  style="height:23px;width:140px;" />
					</dd>
					<dt style="width:90px;text-align: right">题目类型</dt>
					<dd>
						<input  type="text" class="easyui-combobox" id="c_qust_type_id"  style="height:23px;width:140px;" />
					</dd>
					<dd><a href="javascript:getgroupuserGrid();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>题目列表</strong></legend> 
      <table id="groupusershow" style="height:525px"  >
      </table>	
 </fieldset>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>

</body>
</html>