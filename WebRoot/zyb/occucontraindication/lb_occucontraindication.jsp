<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/occucontraindication/lb_occucontraindication.js"></script>
<!--表格内容溢出---显示省略号样式  -->
<%-- <style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style> --%>


<body class="easyui-layout" style="height: 99%">
	<div data-options="region:'west',title:'危害因素',split:true" style="width:200px;">
    		<ul id="some_tree"></ul>
    </div>   
    <div data-options="region:'center',title:'禁忌证'" style="padding:5px;">
    		<div style="height:50px;padding-left: 40px;padding-top:15px;" >
				禁忌证名称<input type="text"  id='contraindication_name'  class = 'textinput'  style="height: 26px;"  />
				<a href="javascript:getOccuhazardfactorsList();" class="easyui-linkbutton c6" style="width:100px;">查询</a>
    		</div>
 			<table id='hazardfactorsShow' style="height:88%"  ></table>
  	</div>
  	<div id="dlg-custedit" class="easyui-dialog"  data-options="width:795,height:385,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>