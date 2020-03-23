<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>复查项目及要求</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<%-- <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  --%>

<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/RecheckExclDiseaseItem/echeckExclDisease.js"></script>
<!--表格内容溢出---显示省略号样式  -->
<%-- <style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style> --%>


<body style="height: 100%;padding-right: 10px;padding-bottom: 0px;">
<input type="hidden"   id="intss"  value="<s:property value='intss'/>"/>
<fieldset style="height:95%;">
<legend><strong>复查项目排除目标疾病</strong></legend> 
      <table id="hazardfactorsShow"  style="height:97%;" >
      </table>	
 </fieldset>
 <div id="dlg-custedit" ></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>