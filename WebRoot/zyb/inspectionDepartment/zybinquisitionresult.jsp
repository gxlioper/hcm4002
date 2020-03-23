<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>职业病问诊科室检查</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/datagrid-dnd.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/inspectionDepartment/zybinquisitionresult.js"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
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
</style>
</head>
<body>
<input type="hidden" id="exam_id" value="<s:property value="examinfo_id"/>"/>
<div class="easyui-layout" fit="true" style="margin-left:5px;" >
	<div data-options="region:'west',split:true" border="false" style="width:25%;">
    	<fieldset style="margin:5px;">
	    			<legend><strong>基本信息</strong></legend>
				<div class="user-query">
						<img id="exampic" style="height:120px;width:100px;float:right;" src="<%=request.getContextPath()%>/themes/default/images/user.png" />
						<dl style="width:160px;">
							<dt style="width:60px;"><s:text name="dahname"/>：</dt><dt style="width:80px;"><s:property value='arch_num'/></dt>
						</dl>
						<dl style="width:160px;">
							<dt style="width:60px;"><s:text name="tjhname"/>：</dt><dt style="width:80px;" id="tjh"><s:property value='exam_num'/></dt>
						</dl>
						<dl style="width:160px;">
							<dt style="width:60px;">姓名：</dt><dt style="width:80px;"><s:property value='user_name'/></dt>
						</dl>
						<dl style="width: 160px;">
							<dt style="width:55px;">性别：</dt><dt id="sex" style="width:30px;"><s:property value='sex'/></dt>
							<dt style="width:45px;">年龄：</dt><dt style="width:25px;"><s:property value='age'/></dt>
						</dl>
						<dl>
							<dt style="width:70px;">人员类型：</dt><dt style="width:200px;"><s:property  value='type_name'/></dt>
						</dl>
						<dl>
							<dt style="width:70px;">体检套餐：</dt><dt style="width:200px"><s:property  value='set_name'/></dt>
						</dl>
						<dl>
							<dt style="width:55px;">单位：</dt><dt style="width:200px;"><s:property value='company'/></dt>
						</dl>
						<dl>
							<dt style="width:60px;">既往史：</dt><dt style="width:200px;"><s:property value='past_medical_history'/></dt>
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
		<div id="yingxiangkeshi_jl" class="image_depart_msg"></div>
	</div>
	<div data-options="region:'south'" style="height:55px;">
		<div class="sub_btn">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:saveInquisitionItem()">确定保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;" onclick="javascript:close_page();">关闭</a>
		</div>
	</div>
</div>
<div id="dlg-edit" class="easyui-dialog"  data-options="width: 700,height: 400,closed: true,cache: false,modal: true"></div>
<div id="shou_wenjuan"></div>
<!-- <div id="dlg-zys" class="easyui-dialog"  data-options="width: 900,height: 450,closed: true,cache: false,modal: true,title:'职业史'">
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
</div> -->
 <div id="dlg-zys" class="easyui-dialog"  data-options="width: 900,height: 450,closed: true,cache: false,modal: true,title:'职业史'"></div>
</body>
</html>