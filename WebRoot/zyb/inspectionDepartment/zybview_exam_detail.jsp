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
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/inspectionDepartment/zybview_exam_detail.js?randomId=<%=Math.random()%>"></script>
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
	padding:10px 70px;
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
<input type="hidden" id="exam_id" value="<s:property value="examinfo_id"/>"/>
<input type="hidden" id='status' value="<s:property value='status'/>"/>
<input type="hidden" id='picture_path' value="<s:property value='picture_path'/>"/>
<input type="hidden" id='exam_type_code' value="<s:property value='exam_type_code'/>"/>
<div class="easyui-layout" fit="true" style="margin-left:5px;" >
	<div data-options="region:'west',split:true" border="false" style="width:25%;">
    	<fieldset style="margin:5px;">
	    			<legend><strong>基本信息</strong><span id="vipsigin" style="display:none">&nbsp;&nbsp;&nbsp;&nbsp;<font size="4" color="red">★★★★★</font></span></legend>
				<div class="user-query">
						<img id="exampic" style="height:120px;width:100px;float:right;" src="<%=request.getContextPath()%>/themes/default/images/user.png" />
						<dl style="width:160px;">
							<dt style="width:55px;"><s:text name="dahname"/>：</dt><dt style="width:80px;"><s:property value='arch_num'/></dt>
						</dl>
						<dl style="width:160px;">
							<dt style="width:55px;"><s:text name="tjhname"/>：</dt><dt style="width:70px;"  id="tjh"><s:property value='exam_num'/></dt>
						</dl>
						<dl style="width:160px;">
							<dt style="width:55px;">姓名：</dt><dt style="width:80px;"><s:property value='user_name'/></dt>
						</dl>
						<dl style="width: 160px;">
							<dt style="width:55px;">性别：</dt><dt id="sex" style="width:35px;"><s:property value='sex'/></dt>
							<dt style="width:40px;">年龄：</dt><dt style="width:30px;"><s:property value='age'/></dt>
						</dl>
						<dl>
							<dt style="width:65px;">人员类型：</dt><dt style="width:200px;"><s:property  value='type_name'/></dt>
						</dl>
						<dl>
							<dt style="width:65px;">体检套餐：</dt><dt style="width:200px"><s:property  value='set_name'/></dt>
						</dl>
						<dl>
							<dt style="width:50px;">单位：</dt><dt style="width:200px;"><s:property value='company'/></dt>
						</dl>
						<dl>
							<dt style="width:55px;">既往史：</dt><dt style="width:200px;"><s:property value='past_medical_history'/></dt>
						</dl>
						<dl>
							<a href="javascript:void(0)" onclick="shouSurver();" class="easyui-linkbutton c6"  id="wenjuan" style="width:100px;height:26px;display:none;"/>问卷</a>
						</dl>
					   </div>
					</fieldset>
					<fieldset style="margin:5px;">
	    			<legend><strong>从业信息</strong></legend>
	    				<div class="user-query">
	    					<dl>
	    						<dt style="width:70px;">行业：</dt><dt style="width:200px;"><s:property  value='occusectorid'/></dt>
	    						
	    					</dl>
	    					<dl>
	    						<dt style="width:70px;">从业行业：</dt><dt style="width:200px;"><s:property  value='occusector'/></dt>
	    					</dl>
	    					<dl>
	    						<dt style="width:70px;">工种：</dt><dt style="width:200px;"><s:property  value='occutypeofworkid'/></dt>
	    						
	    					</dl>
	    					<dl>
	    						<dt style="width:70px;">从业工种：</dt><dt style="width:200px;"><s:property  value='occutypeofwork'/></dt>
	    					</dl>
	    					<dl>
	    						<dt style="width:70px;">工龄(年)：</dt><dt style="width:30px;"><s:property  value='employeeage'/></dt>
	    						<dt style="width:100px;">接害工龄(年)：</dt><dt style="width:30px;"><s:property  value='damage'/></dt>
	    					</dl>
	    					<dl>
	    						<dt style="width:70px;">进厂日期：</dt><dt style="width:100px;"><s:property  value='joinDatetime'/></dt>
	    						<dt><a href="javascript:void(0)" onclick="fn_show_zhiyeshi();" class="easyui-linkbutton c6" style="width:100px;height:26px;"/>查看职业史</a></dt>
	    					</dl>
	    				</div>
	    			</fieldset>
	    			<div style="height:165px;margin:5px;">
	    				<table id="zywhysset"></table>
	    			</div>
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
<div id="dlg-edit" class="easyui-dialog"  data-options="width: 700,height: 400,closed: true,cache: false,modal: true"></div>
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
<div id="dlg-zys" class="easyui-dialog"  data-options="width: 900,height: 450,closed: true,cache: false,modal: true,title:'职业史'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:10px;">
			<div style="height:350px;width:850px;margin-left:20px;">
				<table id="exam_zhiyeshi"></table>
			</div>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-zys').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
</body>
</html>