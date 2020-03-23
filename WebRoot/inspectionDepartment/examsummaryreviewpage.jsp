<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<title>通知复查</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/examsummaryreviewpage.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" >
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
</script>
</head> 
<body>
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:110px;"><input id="ck_exam_num" class="ck_exam_num" type="checkbox" /><s:text name="tjhname"/>(<s:text name="dahname"/>)：</dt>
					<dd><input class="easyui-textbox" type="text" id="exam_num" style="height:26px;width:100px;"/></dd> 
					<dt style="height:26px;width:80px;"><input id="ck_review_title" type="checkbox" />复查主题</dt>
					<dd><input class="easyui-textbox" type="text" id="ser_review_title" style="height:26px;width:100px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox"/>体检日期</dt>
					<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"/></dd>
                    <dt style="height:26px;width:20px;">至</dt>
                    <dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"/></dd>
			       	<dt style="height:26px;width:80px;"><input id="ck_fcdate" type="checkbox" checked="checked"/>复查日期</dt>
					<dd><input class="easyui-datebox" id="start_fcdate" value="<s:property value="model.time3"/>" style="width:100px;height:26px;"/></dd>
                    <dt style="height:26px;width:20px;">至</dt>
                    <dd><input class="easyui-datebox" id="end_fcdate" value="<s:property value="model.time4"/>" style="width:100px;height:26px;"/></dd>
                    <dt style="height:26px;width:80px;"><input id="ck_status" type="checkbox"/>复查状态</dt>
                    <dd><select id="re_status" class="easyui-combobox" style="height:26px;width:60px;" data-options="panelHeight:'auto'">
                    	<option value="">全部</option>
                    	<option value="1">未通知</option>
                    	<option value="2">已通知</option>
                    	<option value="3">作废</option>
                    </select></dd>
                    <dd><a href="javascript:chaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:70px;">查询</a></dd>
                  </dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>总检复查列表</strong></legend>
      <table id="finalfuhca_list">
      </table>	
 </fieldset>
<div id="dlg-xz" class="easyui-dialog"  data-options="width:850,height:470,closed: true,cache: false,modal: true,title:'选择通知方式'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dd style="margin-left:60px;"><input type="radio"  onclick="qh(this)" name="no_type" value="1" checked="checked"/>短信</dd>
				<dd style="margin-left:30px;"><input type="radio" onclick="qh(this)" name="no_type" value="2"/>电话</dd>
				<dd style="margin-left:30px;"><input type="radio" onclick="qh(this)" name="no_type" value="3"/>email</dd>
			</dl>
			<dl>
				<fieldset id="dx" style="margin-left:30px;margin-right:65px;padding-top: 0px;">
							<legend><strong>短信编辑</strong></legend> 
							<div class="formDiv" style=" margin-top:0px;width:300px;float: left;padding-left: 0px;">
								<dl style="height: 26px;margin-left: 0px;padding-left: 0px;">
									<dt style="width: 80px;">
										短信模板:
									</dt>
									<dd>
										<input type="text" class="easyui-combobox" id="template_id"   style="width:150px;height:26px;"  />
									</dd>
								</dl>
								<dl style="width: 600px;padding-left: 0px;margin-left: 0px;" >
									<dt style="width:80px;"  >短信内容:
									</dt>
									<dd  >
										<textarea rows="" cols=""  id="sms_note"  style="width:350px;height:150px;resize: none"><s:property value='m.sms_note'/></textarea>
									</dd>
									<dd>
										<table id = "neirong"  style="width: 100px;height:150px;" >
										</table>
									</dd>
								</dl>
							
							</div>
						</fieldset> 
			</dl>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:quedingxuanze();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-xz').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>

 </body>
 </html>