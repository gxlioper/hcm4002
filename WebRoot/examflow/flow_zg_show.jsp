<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>招工体检管理</title>
<style>
.main {	width: auto; height: auto;}
.left {	width: 49%;	height: auto;}
.right { width: 49%; height: auto;	margin-left: 10px;}
.left, .right {	float: left;}
.pop_up_box_lis{
	border:1px solid #ccc;
	background:#fff;
	padding:0 0px 10px;
	position:absolute;
	font-size:12px;
	display:none;
}
.title{
	text-align:center;
	cursor:move;
	height:30px;
	line-height:30px;
	margin-bottom:15px;
	background:#359BCC;
	border-bottom:1px solid #ccc;
	color:#FFFFFF;
	font-size:16px;
}
</style>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>';
$(function(){
	$('#vipflag').combobox({
	 	data:[{
	 		id:'1',value:'普通人员'
	 	},{
	 		id:'2',value:'一般vip'
	 	},{
	 		id:'3',value:'特级vip'
	 	}],
	    valueField:'id',    
	    textField:'value',
	    onLoadSuccess : function(data) {
			$('#vipflag').combobox('select', data[0].id);
		}
	});
	$('#exam_type').combobox({
	 	data:[{
	 		id:'G',value:'个检'
	 	},{
	 		id:'T',value:'团检'
	 	}],
	    valueField:'id',    
	    textField:'value',
	    onLoadSuccess : function(data) {
			$('#exam_type').combobox('select', data[0].id);
		}
	});
	$('#receive_type').combobox({
	 	data:[{
	 		id:'0',value:'未邮寄,未自取'
	 	},{
	 		id:'1',value:'邮寄'
	 	},{
	 		id:'2',value:'自取'
	 	},{
	 		id:'3',value:'邮寄和自取'
	 	}],
	    valueField:'id',    
	    textField:'value',
	    editable:false,
	    onLoadSuccess : function(data) {
			$('#receive_type').combobox('select', 0);
		}
	});
	$('#h0').combobox({
	 	data:[{
	 		id:'1',value:'已核收'
	 	},{
	 		id:'0',value:'未核收'
	 	}],
	    valueField:'id',    
	    textField:'value',
	    onLoadSuccess : function(data) {
			$('#h0').combobox('select', data[0].id);
		}
	});
	$('#com_Type').combobox({
        url:'getDatadis.action?com_Type=TYLX', 
        editable:false, //不可编辑状态
        cache: false,
        panelHeight: 'auto',//自动高度适合
        valueField:'id',   
        textField:'name',
        onLoadSuccess: function (data){
        	$('#com_Type').combobox('setValue',data[0].id);
        }
	});
	$('#exam_result').combobox({
		data:[{
			id:'Y',name:'合格'
		},{
			id:'R',name:'复查'
		},{
			id:'N',name:'不合格'
		},{
			id:'O',name:'其他'
		}],
        editable:false, //不可编辑状态
        cache: false,
        panelHeight: 'auto',//自动高度适合
        valueField:'id',   
        textField:'name',
        onLoadSuccess: function (data){
        	$('#exam_result').combobox('setValue',data[0].id);
        }
	});
	$('#exam_type_report').combobox({
	 	data:[{
	 		id:'G',value:'个检'
	 	},{
	 		id:'T',value:'团检'
	 	}],
	    valueField:'id',
	    textField:'value',
	    onLoadSuccess : function(data) {
			$('#exam_type').combobox('select', data[0].id);
		}
	});
	$('#exam_num_zg_report').css('ime-mode','disabled');
	$('#exam_num_zg_report').focus();
	$('#exam_num_zg').css('ime-mode','disabled');
	$('#exam_num_zg').focus();
});
</script>
</head>
<body >
	<div class="main">
		<div class="left">
			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 100px; margin-right: 10px" >
				<legend><strong>信息检索</strong></legend>
				<div class="user-query">
					<dl>
						<dt style="height:26px;width:135px;"><input id="ck_exam_num_zg" type="checkbox" checked="checked"/><s:text name="tjhname"/>/<s:text name="dahname"/>/身份证</dt>
						<dd><input type="text" style="width:95px;ime-mode: disabled;" id="exam_num_zg" class="textinput"/></dd>
						<dt style="height:26px;width:80px;"><input id="ck_vipflag" type="checkbox"/>会员标识</dt>
						<dd><input class="easyui-combobox" id="vipflag"  data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 100px;" /></dd>
						<dt style="height:26px;width:80px;"><input id="ck_phone" class="ck_phone" type="checkbox"/>手机号：</dt>
						<dd><input type="text" style="width:95px" id="phone" class="textinput"/></dd>
					</dl>
					<dl>
						<dt style="height:26px;width:80px;"><input id="ck_com_Name" type="checkbox"/>单位：</dt>
						<dd style="height:26px;width:150;">
						  <input type="text" class="textinput" id="com_Name" value="" style="width:150px;"/>
						  <div id="com_name_list_div" style="display:none;margin-left:-50px;" 
						      onmouseover="select_com_list_mover()" 
						      onmouseout="select_com_list_amover()">
	                      </div>
	                    </dd>
						<dt style="height:26px;width:80px;"><input id="ck_exam_type" type="checkbox"/>体检类型</dt>
						<dd><input class="easyui-combobox" id="exam_type"  data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 100px;" /></dd>
						<dt style="height:26px;width:80px;"><input id="ck_username" class="ck_username"  type="checkbox"/>姓名：</dt>
						<dd><input type="text" style="width:95px" id="name" class="textinput"/></dd>
					</dl>
					<dl>
						<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox" checked="checked"/>体检日期</dt>
						<dd><input class="easyui-datebox" type=text id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
						至<input class="easyui-datebox" type=text id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd>
						<dt style="height:26px;width:80px;"><input id="ck_h0" type="checkbox"/>核收情况</dt>
						<dd><input class="easyui-combobox" id="h0"  data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 100px;" /></dd>
						<dd>
							<a href="javascript:chaxun();" class="easyui-linkbutton c6 l-btn l-btn-small" style="width: 90px">查询</a>
						</dd>
					</dl>
				</div>
	 		</fieldset>
	 		<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 50px; height: 520px; margin-right: 5px" >
				<legend><strong>目前招工库的导检单</strong></legend>
					<table id="flowexampListzg" ></table>
	        </fieldset>
	    </div>
	    <div class="right">
	    	<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 50px; height: 110px; margin-right: 5px" >
				<legend><strong>单行编辑（双击）</strong></legend>
					<table id="zg_edit_row" ></table>
	        </fieldset>
	        <fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 130px; margin-right: 10px" >
				<legend><strong>信息检索</strong></legend>
				<div class="user-query">
					<dl>
						<dt style="height:26px;width:100px;"><input id="ck_exam_num_zg_report" checked="checked" type="checkbox"/><s:text name="tjhname"/>(<s:text name="dahname"/>)</dt>
						<dd><input type="text" style="width:95px;ime-mode: disabled;" id="exam_num_zg_report" class="textinput"/></dd>
						<dt style="height:26px;width:80px;"><input id="ck_username_report" class="ck_username"  type="checkbox"/>姓名：</dt>
						<dd><input type="text" style="width:95px" id="name_report" class="textinput"/></dd>
						<dt style="height:26px;width:80px;"><input id="ck_receive_type" checked="checked" type="checkbox"/>领取方式</dt>
						<dd><input class="easyui-combobox" id="receive_type"  data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 100px;" /></dd>
					</dl>
					<dl>
						<dt style="height:26px;width:100px;"><input id="ck_com_Type" type="checkbox"/>单位类型</dt>
						<dd><input class="easyui-combobox" id="com_Type"  data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 100px;" /></dd>
						<dt style="height:26px;width:80px;"><input id="ck_phone_report" class="ck_phone" type="checkbox"/>手机号：</dt>
						<dd><input type="text" style="width:95px" id="phone_report" class="textinput"/></dd>
						<dt style="height:26px;width:80px;"><input id="ck_exam_type_report" type="checkbox"/>体检类型</dt>
						<dd><input class="easyui-combobox" id="exam_type_report"  data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 100px;" /></dd>
					</dl>
					<dl>
						<dt style="height:26px;width:80px;"><input id="ck_com_Name1" type="checkbox"/>单位：</dt>
						<dd style="height:26px;width:150;">
						  <input type="text" class="textinput" id="com_Name1" value="" style="width:150px;"/>
						  <div id="com_name_list_div1" style="display:none;margin-left:700px;" 
						      onmouseover="select_com_list_mover1()" 
						      onmouseout="select_com_list_amover1()">
	                      </div>
	                    </dd>
						<dt style="height:26px;width:100px;"><input id="ck_id_num" class="ck_id_num"  type="checkbox"/>身份证号：</dt>
						<dd><input type="text" style="width:95px" id="id_num" class="textinput"/></dd>
						<dt style="height:26px;width:80px;"><input id="ck_exam_result" type="checkbox"/>体检结果</dt>
						<dd><input class="easyui-combobox" id="exam_result"  data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 100px;" /></dd>
					</dl>
					<dl>
						<dt style="height:26px;width:100px;"><input id="ck_date_report" type="checkbox" checked="checked"/>结论日期</dt>
						<dd><input class="easyui-datebox" type=text id="time1_report" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
						至<input class="easyui-datebox" type=text id="time2_report" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd> 
						<dd>
							<a href="javascript:chaxun_report();" class="easyui-linkbutton c6 l-btn l-btn-small" style="width: 90px">查询</a>
						</dd>
					</dl>
				</div>
	 		</fieldset>
	 		<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 50px; height: 420px; margin-right: 5px" >
				<legend><strong>复检登记（双击）</strong></legend>
					<table id="flowexampListzg_report" ></table>
	        </fieldset>
	    </div>
	</div>
<div id="dlg-edit-thems" class="easyui-dialog" data-options="width: 780,height: 350,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-edit-zg-result" class="easyui-dialog" data-options="width: 400,height: 300,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-reedit-zg-result" class="easyui-dialog" data-options="width: 400,height: 300,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>
<script type="text/javascript" src="<%=request.getContextPath()%>/examflow/flow_zg_show.js"></script>