<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>职业危管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<%-- <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  --%>

<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/occudisease/occudisease.js"></script>
<!--表格内容溢出---显示省略号样式  -->
<%-- <style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style> --%>


<body>
<div id="cc" class="easyui-layout"   data-options="fit:'true'" >   
	 <div data-options="region:'west',title:'职业病类别列表',collapsible:true" style="width:20%;">
	 			<div  data-options="region:'north'"  style="height:5%;padding-left: 20px;padding-top: 10px;margin-bottom:20px;">
	 				<input type="text"  id="cx"    style="width:140px;height: 23px;"  value=""  />
	 				<input type="button"  value="查询"  onclick="shu($('#cx').val());"  style="width:50px;height: 25px"/>
	 			</div>
	 			<div	data-options="region:'south'"	>
		 	    	<ul id="some_tree"></ul>
	 			</div>
	 </div>   
	  <div data-options="region:'center'" >
	  	<table id='hazardfactorsShow' >
	  		
	  	</table>
	  </div>   
</div>
<div id="dlg-custedit"></div>
</body>
</html>