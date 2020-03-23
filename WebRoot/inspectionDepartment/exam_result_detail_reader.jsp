<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>检验科室检查</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/datagrid-dnd.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/exam_result_detail_reader.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<style type="text/css">
.sub_btn{
	text-align:right;
	margin-top:10px;
}
.yangshi dt{
	width:60px;
}
</style>
</head>
<body>
<input id="dep_id" type="hidden" value="<s:property value="model.dep_id"/>"/>
<div class="easyui-layout" fit="true" style="margin-left:5px;" >
	<div data-options="region:'west',split:true" border="false" style="width:20%;">
    	<fieldset>
    		<legend><strong>基本信息</strong></legend>
    		<div style="position: fixed;margin-left: 150px;margin-top: 10px;"><img id="exampic" style="height:120px;width:100px;" src="<%=request.getContextPath()%>/themes/default/images/user.png" /></div>
				<div class="user-query yangshi">
					<dl>
						<dt><s:text name="dahname"/>：
						<input type="hidden" name="exam_num" id="exam_num" value="<s:property value="exam_num"/>"/>
						</dt><dd id="arch_num"></dd>
						</dl>
						<dl><dt><s:text name="tjhname"/>：</dt>
						<dd id="exam_num_x"></dd>
						</dl>
						<dl><dt>姓名：<input type="hidden" name="exam_id" id="exam_id"/></dt><dd id="user_name"></dd></dl>
						<dl><dt>性别：</dt><dd id="sex"></dd></dl>
						<dl><dt>年龄：</dt><dd id="age"></dd></dl>
						<dl><dt>体检套餐：</dt><dd id="set_name"></dd></dl>
						<dl><dt>单位：</dt><dd id="company"></dd></dl>
						<dl><dt>既往史：</dt><dd id="past_medical_history"></dd></dl>
						<dl><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:150px;height:27px;display:none;">历史结果对比</a>
					</dl>
					<dl>
					  <a href="javascript:void(0)"  onclick="shouSurver();"  class="easyui-linkbutton c6"  id="wenjuan"    style="width:100px;height:27px;display:none;"   />问卷</a>
					</dl>
					
				</div>
 			</fieldset>
	</div>
	<div data-options="region:'center'" border="false">
		<table id="hy_sam_itemList"></table>
	</div>
	<div data-options="region:'east',split:true" border="false" style="width:40%;">
		<table id="hy_itemResultList"></table>
	</div>
	<div data-options="region:'south'" style="height:55px;">
		<div class="sub_btn">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:readExam_result();">读取结果</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;margin-right:12%;" onclick="javascript:close_page();">关闭</a>
		</div>
	</div>
</div>
<div id='shou_wenjuan'></div>
</body>
</html>