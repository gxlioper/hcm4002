<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>报告追踪查看</title>
<style>
#main {	width: auto;	height: auto;}
#left {	width: 35%;	height: auto;}
#right {	width: 63%;	height: auto;	margin-left: 10px;}
#left, #right {	float: left;}
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
<script type="text/javascript" src="<%=request.getContextPath()%>/examflow/flow_show.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	shangchuanchaxun();
	//回车查询事件
	 $("#exam_num_p").keydown(function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	shangchuanchaxun();
		    }
    }); 
	 $('#exam_num_p').css('ime-mode','disabled');
	 $('#exam_num_p').focus();
});

</script>
</head>
<body>
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
	<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 80px; margin-right: 10px" >
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
					<dt style="height:26px;width:80px;"><input id="ck_com_Name" type="checkbox"/>单位：</dt>
					<dd style="height:26px;width:140;">
					  <input type="text" class="textinput" id="com_Name" value="" style="width:140px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:-50px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
					<dt style="height:26px;width:80px;"><input id="ck_tjrw" type="checkbox"/>体检任务</dt>
					<dd>
						<input  class="easyui-combobox" type="text" id="tjrw" style="height:26px;width:140px;"/>
						  <div id="batch_list_div" style="display:none;margin-left:200px;" 
						      onmouseover="select_batchcom_list_mover()" 
						      onmouseout="select_batchcom_list_amover()">
	                      </div>
					</dd>
					<dt style="height:26px;width:80px;"><input id="ck_group_id" type="checkbox"/>分组：</dt>
					<dd><select class="easyui-combobox" id="group_id" name="group_id"
							data-options="valueField:'id', textField:'group_name',height:26,width:220,panelHeight:'auto'"></select>
					</dd>
					<dt style="height:26px;width:80px;"><input id="ck_set_id" type="checkbox"/>套餐：</dt>
					<dd><select class="easyui-combobox" id="set_id" name="set_id"	data-options="height:26,width:220,panelHeight:'auto'"></select></dd>
		    	    	</dl>
						<dl>
					<dt style="height:26px;width:80px;"><input id="ck_exam_num_p" type="checkbox"/><s:text name="tjhname"/>：</dt>
					<dd><input type="text" style="width:140px;ime-mode: disabled;" id="exam_num_p" name="exam_num_p"  class="textinput"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_exam_type" type="checkbox"/>体检类型</dt>
					<dd><select class="easyui-combobox" id="exam_type" name="exam_type" data-options="height:26,width:140,panelHeight:'auto'">		    	       		
		    	       		<option  value="A" selected>不限</option>
		    	       		<option  value="T">团体体检</option>
		    	       		<option  value="G">个人体检</option>
		    	      </select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_username" type="checkbox"/>姓名：</dt>
					<dd><input type="text" style="width:215px" id="username" class="textinput"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_usersex" type="checkbox"/>性别：</dt>
					<dd><select class="easyui-combobox" id="usersex" data-options="height:26,width:220,panelHeight:'auto'">		    	       		
		    	       		<option  value="A" selected>不限</option>
		    	       		<option  value="M">男</option>
		    	       		<option  value="F">女</option>
		    	      </select></dd>
					
					<dd><a href="javascript:shangchuanchaxun();" class="easyui-linkbutton c6 l-btn l-btn-small"  style="width: 90px">查询</a></dd>
				</dl>
			</div>
 			</fieldset>
 			
 			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: auto; margin-right: 10px" >
    			<legend><strong>信息显示</strong></legend>
        				<table id="flowloglist" ></table>	
 			</fieldset> 
</body>
</html>