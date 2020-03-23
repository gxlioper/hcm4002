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
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/exam_result_detail.js?randomId=<%=Math.random()%>"></script>
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
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden"  id='dep_id' value="<s:property value="#session.username.dep_id"/>"/>
<input type="hidden"  id='dep_num' value="<s:property value="#session.username.deptCode"/>"/>
<input type="hidden" id="isExamResultDetailDoctorPageShow" value="<s:property value='isExamResultDetailDoctorPageShow'/>"/>
<input type="hidden" id="username" value="<s:property value="#session.username.name"/>"/>
<input type="hidden" id="app_type" value="<s:property value="app_type"/>"/>
<input type="hidden" id='status' value="<s:property value='status'/>"/>
<input type="hidden"   id='is_update_critical' value="<s:property value='model.is_update_critical'/>"/>
<div class="easyui-layout" fit="true" style="margin-left:5px;" >
	<div data-options="region:'west',split:true" border="false" style="width:20%;">
    	<fieldset>
    		<legend><strong>基本信息</strong>
    			<span id="vipsigin" style="display:none">&nbsp;&nbsp;&nbsp;&nbsp;<font size="4" color="red">★★★★★</font></span>
    			</legend>
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
			<span style="margin-left:0px;">录入者:</span>					
			<select class="easyui-combobox" id="inputter"data-options="height:26,width:100,panelHeight:'auto'">
			</select>
			 <input type="hidden" value="<s:property value='carPdf_button'/>"/>
			 <s:if test ="carPdf_button eq \"Y\"">
				<a href="javascript:void(0)" class="easyui-linkbutton c6"  id="pdfbuttion" style="display:none;" onclick="javascript:wenjuanpdf();">健康评测报告</a>
			</s:if>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:shezhiyisheng();">确定保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;margin-right:12%" onclick="javascript:close_page();">关闭</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:90px;margin-right:6%" onclick="javascript:tocriticalpage();">危急值信息</a>		
		</div>
	</div>
</div>
<div id="dlg-list" class="easyui-dialog"  data-options="width: 1050,height: 680,closed: true,cache: false,modal: true"></div>
<div id='shou_wenjuan'></div>
<div id="shezhiyisheng" class="easyui-dialog"  data-options="width:500,height:350,closed: true,cache: false"></div>
</body>
</html>