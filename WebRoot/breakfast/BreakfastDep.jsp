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
<title>早餐科室</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/breakfast/Breakfast.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	$("input").attr("maxlength","20");
	getGrid();
});

 $(document).keydown(function(e) {  
          if (e.keyCode == 13) {  
        	  getGrid();
      		getcustomerInfo();  
          }  
   })
</script>

</head>
<body>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:true" style="width:20%;">
		<%-- <fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;height:27px;" onclick="javascript:chaxun();">查询</a></dd>
						</dl>
					</div>
 			</fieldset> --%>
 			<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>基本信息</strong></legend>
					<div class="user-query">
						<dl><dt style="width:60px;"><s:text name="dahname"/>：</dt><dt id="arch_num"></dt></dl>
						<dl><dt style="width:60px;"><s:text name="tjhname"/>：<input type="hidden" id="ser_num" value="<s:property value="exam_num"/>"/></dt><dt><s:property value="exam_num"/></dt></dl>
						<dl><dt style="width:60px;">姓名：<input type="hidden" name="exam_id" id="exam_id"/></dt><dt id="user_name"></dt></dl>
						<dl><dt style="width:60px;">性别：</dt><dt id="sex"></dt></dl>
						<dl><dt style="width:60px;">年龄：</dt><dt id="age"></dt></dl>
						<dl><dt style="width:60px;">单位：</dt><dt id="company" style="width:70%;"></dt></dl>
						<dl><dt style="width:60px;">人员类型：</dt><dt id="customer_type"></dt></dl>
						<dl><dt style="width:60px;">体检套餐：</dt><dt id="set_name" style="width:70%;"></dt></dl>
					</div>
			</fieldset>
	</div>
    <div data-options="region:'center'" ><div id="zcitemList"></div></div>
</div>
<div id="dlg-fapiao" class="easyui-dialog" data-options="width: 600,height: 408,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>