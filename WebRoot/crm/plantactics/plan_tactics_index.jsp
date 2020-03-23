<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>健康计划策略</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/plantactics/plan_tactics_index.js?randomId=<%=Math.random()%>"></script>
<style type="text/css">
.scolor{color:#fff;}
</style>
</head>
<body>
	<fieldset style="margin:5px;padding-right:0;">
		<legend><strong>健康计划策略列表</strong></legend>
			<table id="crm_plan_tactics"> 
		</table>
	</fieldset>
	<fieldset style="margin:5px;padding-right:0;">
		<legend><strong>健康计划策略明细</strong></legend>
			<table id="crm_plan_tactics_detail"> 
		</table>
	</fieldset>
	
	
	<div id="dlg-save" class="easyui-dialog" title="策略" style="width:750px;height:420px" 
    	data-options="width: 750,height: 420,top:50, closed: true,cache: false,modal: true">
	    	<div class="formdiv fomr3" style="padding-top:20px;">
				<dl>
					<dt>
						<input type="hidden" id="tactics_id" value=""/>
					        策略编码：
					</dt>
					<dd><input type="text" id="tactics_num" value=""  disabled="disabled"  class="textinput"/><strong class="red">*</strong></dd>
					
					<dt>
						策略类型：
					</dt>
						<dd><select class="easyui-combobox"  id="tactics_type" name="tactics_type" style="width:143px">  
							<option value="1" selected="selected">慢病</option>
							<option value="2">复查</option>
							<option value="3">危机值</option>
							<option value="4">vip回访</option>
							<option value="5">特殊回访</option>
							</select>
							<strong class="red">*</strong>
						</dd>
				</dl>
				
				<dl >
					<dt>策略描述：</dt>
					<dd>
						<textarea id='notices' rows="4" cols="55"></textarea>
					</dd>
				</dl>
				
				<dl style="margin-top: 20px;">
					<dt>对应策略说明：</dt>
					<dd>
						<textarea id='rmark' rows="4" cols="55"></textarea>
					</dd>
				</dl>
				
		</div>
		<div class="dialog-button-box">
				<div class="inner-button-box">
					<a href="javascript:void(0)" class="easyui-linkbutton" style="width:90px;" onclick="javascript:save()">确定</a>&nbsp;&nbsp;&nbsp;
				    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:90px;" onclick="javascript:$('#dlg-save').dialog('close')">关闭</a>&nbsp;&nbsp;&nbsp;
				</div>
		</div>
    </div>
    
    <div id="dlg-detail" class="easyui-dialog" title="策略明细" style="width:750px;height:420px" 
    	data-options="width: 750,height: 420,top:50, closed: true,cache: false,modal: true">
	    	<div class="formdiv fomr3" style="padding-top:20px;">
				<dl>
					<dt><input type="hidden" id="tactics_num_d" value=""/>
						<input type="hidden" id="tactics_detail_id" value=""/>
						回访医生：
					</dt>
						<dd>
							<select class="easyui-combobox" id="doctor_name"
								data-options="height:26,width:143,panelHeight:'auto'">
							</select>
						</dd>
				</dl>
				
				<dl>
					<dt>回访时间(天)：</dt>
					<dd>
						<input type="text" id='distancedate' class="easyui-numberbox" value=""><strong class="red">*体检日期后</strong>
					</dd>
				</dl>
				
				<dl >
					<dt>回访内容：</dt>
					<dd>
						<textarea id='notices_d' rows="4" cols="55"></textarea>
					</dd>
				</dl>
				
				
				
		</div>
		<div class="dialog-button-box">
				<div class="inner-button-box">
					<a href="javascript:void(0)" class="easyui-linkbutton" style="width:90px;" onclick="javascript:save_detail()">确定</a>&nbsp;&nbsp;&nbsp;
				    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:90px;" onclick="javascript:$('#dlg-detail').dialog('close')">关闭</a>&nbsp;&nbsp;&nbsp;
				</div>
		</div>
    </div>
    
</body>
</html>