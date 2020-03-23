<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title> 结束回收</title>
<style>
#main {	width: auto;	height: auto;}
#left {	width: 350px;	height: auto;}
#right {	width: 72.5%;	height: auto;	margin-left: 10px;}
#left, #right {	float: left;}
.left1 {	width: 20%;	height: auto;}
.right1 {	width: 78%;	height: auto;	margin-left: 10px;}
.left1, .right1 {	float: left;}
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
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/examflow/flow_z_show_dbgj.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>';

function liuchenbeizhuPage(){
	$.ajax({
		url:'getNumExamInfo.action?exam_num='+$('#exam_num_s').val()+"&dep_id=1",
		type:'post',
		success:function(data){
			if(data==1){
				$("#dlg-beizhu").dialog({
					title : '流程备注',
					width :800,
					height :500,
					href : 'remakPage.action?exam_num='+$('#exam_num_s').val()+"&process=4"
				});
				$("#dlg-beizhu").dialog('open');  
			} else {
				$.messager.alert("提示信息",tjhname+"无效","error");
				$('#exam_num_s').focus();
				return;
			}
		},
		error:function(){
			$.messager.alert("提示信息","操作失败","error");
		}
	})
	
}
</script>
</head>
<body>
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
<input type="hidden"   id='operation_type' value="1"/>
<div id="tt" class="easyui-tabs">
<div title="未预览人员列表" style="">
	<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 35px; margin-right: 10px" >
    			<legend><strong>获取报告</strong></legend>
					<div class="user-query">		
				<dl>
				    <dt style="height:26px;width:130px;"><input id="ck_exam_num_s" class="ck_exam_num_s" type="checkbox"/><s:text name="tjhname"/>(<s:text name="dahname"/>)：</dt>
					<dd><input type="text" style="width:115px;ime-mode: disabled;" id="exam_num_s" name="exam_num_s"  class="textinput"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_exam_doc_id" type="checkbox" checked="checked"/>主检医生</dt>
					<dd><select class="easyui-combobox" id="exam_doc_id" name="exam_doc_id" data-options="height:26,width:90,panelHeight:'300'">		    	       			
		    	      </select></dd>
		    	   <dd><a href="javascript:chaxunwyl();" class="easyui-linkbutton c6 l-btn l-btn-small"  style="width: 110px">获取主检报告</a></dd>
		    	   <span style="margin-left:25px;">未预览：<font style="color: red;font-weight:bold;" id="wz_count">0</font>人，
							当天已预览：<font style="color: blue;font-weight:bold;" id="yz_count">0</font>人，
							本人当天已预览：<font style="color: blue;font-weight:bold;" id="zj_count">0</font>人。</span>
				</dl>
			</div>
 			</fieldset>
 			
 			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: auto; margin-right: 10px" >
    			<legend><strong>信息显示</strong></legend>
        				<table id="flowexamlist" ></table>		
 			</fieldset> 
</div>
<div title="已预览人员列表" style="">
	<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 35px; margin-right: 10px" >
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
					<dt style="height:26px;width:130px;"><input id="ck_exam_num_p" class="ck_exam_num_p" type="checkbox"/><s:text name="tjhname"/>(<s:text name="dahname"/>)：</dt>
					<dd><input type="text" style="width:115px;ime-mode: disabled;" id="exam_num_p" name="exam_num_p"  class="textinput"/></dd>
						
					<dt style="height:26px;width:80px;"><input id="ck_exam_type" type="checkbox"/>体检类型</dt>
					<dd><select class="easyui-combobox" id="exam_type" name="exam_type" data-options="height:26,width:140,panelHeight:'auto'">		    	       		
		    	       		<option  value="A" selected>不限</option>
		    	       		<option  value="T">团体体检</option>
		    	       		<option  value="G">个人体检</option>
		    	      </select></dd>
				 <%-- <dt style="height:26px;width:80px;"><input id="ck_ptype" type="checkbox" checked="checked"/>上传状态</dt>
					<dd><select class="easyui-combobox" id="ptype" name="ptype" data-options="height:26,width:140,panelHeight:'auto'">
					        <option  value="0" >全部</option>		    	       		
		    	       		<option  value="1" selected>未上传</option>
		    	       		<option  value="2">已上传</option>		    	       		
		    	      </select>
		    	  	</dd> --%>
		    	  	<dt style="height:26px;width:80px;"><input id="ck_exam_doc_search" type="checkbox" checked="checked"/>预览护士</dt>
					<dd><select class="easyui-combobox" id="exam_doc_search" name="exam_doc_search" data-options="height:26,width:90,panelHeight:'300'">		    	       			
		    	      </select></dd>
					 <dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox" checked="checked"/>上传日期</dt>
			         <dd><input class="easyui-datebox" type=text id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
                                              至<input class="easyui-datebox" type=text id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd> 
					<a href="javascript:chaxunyyl();" class="easyui-linkbutton c6 l-btn l-btn-small"  style="width: 90px">查询</a>
				</dl>
				
			</div>
 			</fieldset>
 			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: auto; margin-right: 10px" >
    			<legend><strong>信息显示</strong></legend>
        			<table id="yishangchuan" ></table>		
 			</fieldset>
</div>
<div title="已驳回人员列表" style="">
	<fieldset style="margin:5px;padding-right:0;">
	<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="height:26px;width:60px;"><input id="ck_exam_num2" checked="checked" type="checkbox"/>体检号</dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num2" value="" style="height:26px;width:100px;"/></dd>
					<dt style="height:26px;width:80px;">处理状态</dt>					
					<dd><select class="easyui-combobox" id="done_status" data-options="height:26,width:65,panelHeight:'auto'">
						<option value="0">未处理</option>
						<option value="1">已处理</option>
					</select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_data2" type="checkbox"/>驳回日期</dt>
					<dd><input class="easyui-datebox" id="start_date2" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"/></dd>
                    <dt style="height:26px;width:30px;">至</dt>
                    <dd><input class="easyui-datebox" id="end_date2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"/></dd>
					<dt style="height:26px;width:60px;"><input id="ck_custname2" type="checkbox"/>姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname2" value="" style="height:26px;width:80px;"/></dd>
					<dd><a href="javascript:chaxun3();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">查询</a></dd>
				 </dl>
			</div>
 	</fieldset>
 	<fieldset style="margin:5px;padding-right:0;">
	<legend><strong>体检人员列表</strong></legend>
    <table id="getexamreject">
    </table>	
 </fieldset>
</div> 
</div>
<div id="sendchk_dlg" style="padding-top:50px;" class="easyui-dialog"  data-options="width: 600,height: 300,closed: true,cache: false,modal: true,top:50"></div>
<input type="hidden" id="today" value="<s:property value="model.time1"/>"/>
<div id="transfer_dlg" style="padding-top:50px;" class="easyui-dialog"  data-options="width: 500,height: 300,closed: true,cache: false,modal: true,top:50"></div>
<input type="hidden" id="flow_code" value="z"/>
<div id="dlg-beizhu" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50,resizable:true"></div>
</body>
</html>