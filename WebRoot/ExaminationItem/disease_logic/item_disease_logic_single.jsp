<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<%  
        application.setAttribute("name","application_James");  
       
   %>  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--不保存缓存  -->
<meta HTTP-EQUIV="pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<meta HTTP-EQUIV="expires" CONTENT="0">
<title>单项阳性逻辑维护</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ExaminationItem/disease_logic/item_disease_logic_single.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
	$(function() {
		$("input").attr("maxlength", "45");
	})
</script>
</head>
<body>
<input type="hidden"  id='web_Resource'  value='<s:property value="web_Resource"/>' />
<input type="hidden"  id='item_num'  value='<s:property value="item_num"/>' />
    <fieldset style="margin: 5px; padding-right: 0;" id="danxiang">
        <legend>
            <strong>单项疾病逻辑列表</strong>
        </legend>
        <table id="diseaseshow" >
        </table>
    </fieldset>
	<div id="dlg-custedit" class="easyui-dialog" data-options="width:'1070',closed: true,cache: false,modal:true,top:50" style="min-height: 500px"></div>
</body>
</html>