<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>影像科室检查</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/datagrid-dnd.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/view_exam_detail.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<style type="text/css">
.sub_btn{
	text-align:center;
	margin-top:10px;
	padding:0 30px;
}
.yangshi dt{
	width:60px;
}
.image_depart_msg {
	font-size: 13px;
	padding:0px 70px;
}
.B-ultrasound {
	margin-bottom: 20px;
}
.B-ultrasound p {
	font-weight: bold;
	margin-bottom: 5px;
	font-size:16px;
}
.B-ultrasound input {
	height:20px;
}
.label_area {
	float: left;
	width:40px;
}
.label_item{
	float: left;
	width:160px;
}
.in_conclusion{ margin-bottom:10px;}
.in_conclusion textarea{ resize:none;}
.view a {
	margin-right: 10px;
}
.view {margin-left: 40px;}
.B-ultrasound img.v_i_items{
	float: left;
	width: 150px;
	margin: 10px;
	height: 180px;
	background: #ccc;
}
</style>
</head>
<body>
<input  type="hidden"  id="c_id"  value="" />
<input  type="hidden"  id="isViewExamImageShow"  value="<s:property value="isViewExamImageShow"/>" />
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden"  id='dep_id' value="<s:property value="#session.username.dep_id"/>"/>
<input type="hidden"  id='dep_num' value="<s:property value="#session.username.deptCode"/>"/>
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
					
						</dt>
						<dd id="arch_num"  style="width: 85px;"></dd>
						</dl>
						<dl><dt><s:text name="tjhname"/>：</dt><dd id="exam_num_x"></dd></dl>
						<dl><dt>姓名：<input type="hidden" name="exam_id" id="exam_id"/></dt><dd id="user_name"></dd></dl>
						<dl><dt>性别：</dt><dd id="sex"></dd></dl>
						<dl><dt>年龄：</dt><dd id="age"></dd></dl>
						<dl><dt>体检套餐：</dt><dd id="set_name"></dd></dl>
						<dl><dt>单位：</dt><dd id="company"></dd></dl>
						<dl ><dt>既往史：</dt><dd id="past_medical_history"></dd></dl> 
					    <dl>
					    	<a href="javascript:shouSurver();"  id="wenjuan"   class="easyui-linkbutton c6" style="width:100px;height:26px;display:none;">问卷</a>
					    </dl>
						<dl><a href="javascript:void(0);" id="wjzxx" style="width:100px;height:26px;" class="easyui-linkbutton c6" onclick="tocriticalpage()">危急值信息</a></dl>
					    <s:if test ="carPdf_button eq \"Y\"">
							<dl><input value="问卷评测报告"  id="pdfbuttion" style="display:none;"  type="button"  onclick="wenjuanpdf()" /></dl>
						</s:if>
				</div>
 			</fieldset>
 			<fieldset class="helthsize" style="margin:5px;margin-left :0px;margin-left:3px;padding-right:0px;padding-left:0px;height: 37%">
	    			<legend><strong>健康档案对比</strong></legend>
						<div style="padding-right: 5px;height: 30px;text-align: right;">
		    				<a href="javascript:void(0)" onclick="lishijieguoduibi();" class="easyui-linkbutton c6" style="width:120px;height:26px;"/>历史结果对比</a>
						</div>
						<div  class="user-query"   id='result'></div>
	 		</fieldset>
	</div>
	<div data-options="region:'center'" border="false">
		<div id="div_dep" class="easyui-layout" fit="true">
			<div style="height:80%;" data-options="region:'north',title:'全科会诊协作平台'">
				<div class="easyui-tabs" fit="true">
					<div title="危急值"  data-options="tabWidth:200,border:false">   
						<div id="weijizhi_div"></div>    
					</div>   
					<div title="异常指数"  data-options="tabWidth:200,border:false">   
						<div id="yichang"></div>    
					</div>   
					<div title="全部" data-options="tabWidth:200,border:false">   
						<div id="all"></div>
					</div>   
				</div>
 			</div>
 			<div data-options="region:'center'">
 				<div style="padding: 10px 70px;text-align: right;margin-top: 10px;margin-right: 60px;"><span style="margin-left:55px;">录入者:</span><select class="easyui-combobox" id="inputter"data-options="height:26,width:100,panelHeight:'auto'"></select></div>
				<div id="yingxiangkeshi_jl" class="image_depart_msg" style="margin-top: -20px;"></div>
			</div>
		</div>
	</div>
</div>
<div id="dlg-edit" class="easyui-dialog"  data-options="width: 1050,height: 680,closed: true,cache: false,modal: true"></div>
<div id="dlg-list" class="easyui-dialog"  data-options="width: 1050,height: 680,closed: true,cache: false,modal: true"></div>
<div id="shou_wenjuan"></div>
<div id="dlg-history" class="easyui-dialog"  data-options="width: 800,height: 460,closed: true,cache: false,modal: true,title:'历史结果对比'">
	<div style="height:350px;width:750px;margin-left:20px;margin-top:20px;">
		<div id="tt" class="easyui-tabs" data-options="fit:true">
		</div>		
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-history').dialog('close')">关闭</a>
		</div>
	</div>
</div>
</body>
</html>