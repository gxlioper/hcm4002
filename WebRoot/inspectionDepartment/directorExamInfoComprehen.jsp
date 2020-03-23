<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<title>体检信息综合查询页面</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/directorExamInfoComprehen.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
	$(function(){
		$("input").attr("maxlength","20");
		$('#exam_num').textbox('textbox').css('ime-mode','disabled');
		$('#exam_num').textbox('textbox').focus();
	})
</script>
</head> 
<body>
<input type="hidden" id="company_id"/>
<input type="hidden" id="arch_com_ids" value="notstz"/>
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
<div class="easyui-layout" fit="true">
 <div data-options="region:'north',border:false" style="height:135px;"> 
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:100px;"><input id="ck_exam_num" class="chk_exam_num" type="checkbox"/><s:text name="tjhname"/>/<s:text name="dahname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:105px;ime-mode: disabled;"/></dd> 
					<dt style="height:26px;width:70px;"><input id="ck_custname" class="chk_custname" type="checkbox" />姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:20px;"><input id="ck_date" type="checkbox" /></dt>
					<dt style="height:26px;width:85px;margin-top:-4px;"><select class="easyui-combobox" id="date_type" data-options="height:26,width:80,panelHeight:'auto',editable:false">
							<option value="">体检日期</option>
							<option value="1">检查日期</option>
							</select>
					</dt>
					<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.s_join_date"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:30px;">至</dt>
                     <dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.e_join_date"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:80px;"><input id="ck_status" type="checkbox"/>体检状态</dt>					
					<dd><select class="easyui-combobox" id="statusss" name="statusss"
					data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_is_guide_back" type="checkbox"/>回收状态</dt>					
					<dd><select class="easyui-combobox" id="is_guide_back" data-options="height:26,width:80,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="N">未回收</option>
						<option value="Y">已回收</option>
					</select></dd>
					<dt style="height:26px;width:60px;"><input id="ck_sex" type="checkbox"/>性别</dt>					
					 <dd><select class="easyui-combobox" id="sex"
					data-options="height:26,width:70">
						<option value="男">男</option>
						<option value="女">女</option>
					</select></dd>   
				</dl>
				<dl>
				    <%-- <dt style="height:26px;width:60px;"><input id="ck_arch_num" type="checkbox"/><s:text name="dahname"/></dt>					
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:100px;"/></dd>    --%>
                    <dt style="height:26px;width:70px;"><input id="ck_id_num" class="chk_id_num" type="checkbox"/>身份证</dt>					
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:70px;"><input id="ck_searchphone" class="chk_searchphone"  type="checkbox"/>电话</dt>					
					<dd><input class="easyui-textbox"  type="text" id="searchphone" value="" style="height:26px;width:135px;"/></dd>     
                    <dt style="height:26px;width:105px;"><input id="ck_company_id" class='chk_com_Name' type="checkbox" />&nbsp;&nbsp;选择单位</dt>					
					<dd><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:240px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:375px;position:fixed;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                    
                    <dt style="height:26px;width:80px;"><input id="ck_examtype" type="checkbox"/>体检类型</dt>					
					<dd><select class="easyui-combobox" id="examtype"
					data-options="height:26,width:100,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="G">个检</option>
						<option value="T">团检</option>
					</select></dd><input type="hidden" id="customer_id" value="1" />
					<dd><a href="javascript:button_select_list(2);"  class="easyui-linkbutton" style="height:26px;width:80px;">三天未导</a></dd>
					<dd><a href="javascript:button_select_list(3);"  class="easyui-linkbutton" style="height:26px;width:80px;">两天未派</a></dd>
					<dd><a href="javascript:button_select_list(4);"  class="easyui-linkbutton" style="height:26px;width:120px;">五天未出报告</a></dd>  
				 </dl>
				 <dl>
				 	<dt style="height:26px;width:70px;"><input id="ck_dep" type="checkbox"/>科室</dt>					
					<dd><select class="easyui-combobox" id="dep"
					data-options="height:26,width:135"></select></dd>   
                    <dt style="height:26px;width:70px;"><input id="ck_item" type="checkbox"/>收费项目</dt>					
					<dd><select class="easyui-combobox" id="item"
					data-options="height:26,width:135"></select></dd>
					<dt style="height:26px;width:105px;"><input id="ck_exam_status" type="checkbox"/>&nbsp;&nbsp;检查状态</dt>					
					 <dd><select class="easyui-combobox" id="exam_status"
					data-options="height:26,width:75,panelHeight:'auto',multiple:true">
						<!-- <option value="">不限</option> -->
						<option value="'N'">未检</option>
						<option value="'Y'">已检</option>
						<option value="'C'">登记</option>
						<option value="'G'">弃检</option>
						<option value="'D'">延期</option>
					</select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_pay_status" type="checkbox"/>收费状态</dt>					
					<dd><select class="easyui-combobox" id="pay_status"
					data-options="height:26,width:75,panelHeight:'auto',multiple:true">
						<!-- <option value="">不限</option> -->
						<option value="'N'">未付费</option>
						<option value="'Y'">已付费</option>
						<option value="'R'">预付费</option>
					</select></dd>
			         <dt style="height:26px;width:80px;"><input id="ck_set_name" type="checkbox"/>体检套餐</dt>					
					 <dd><select class="easyui-combobox" id="set_name"
					data-options="height:26,width:100"></select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_wxzj" type="checkbox"/>总检标志</dt>					
					 <dd><select class="easyui-combobox" id="wxzj"
					data-options="height:26,width:80">
						<option value="0">需要总检</option>
						<option value="1">无需总检</option>
					</select></dd>
					<dt style="height:26px;width:60px;"><input id="ck_is_vip" type="checkbox"/>贵宾</dt>					
					 <dd><select class="easyui-combobox" id="is_vip"
					data-options="height:26,width:80">
						<option value="Y">是</option>
						<option value="N">否</option>
					</select></dd>
				 	<dd><a href="javascript:button_select_list(1);"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:60px;">查询</a>
				 		<a href="javascript:button_select_listts(1);"  class="easyui-linkbutton" style="height:26px;width:80px;">特殊通知</a></dd>
				 </dl>
			</div>
 </fieldset>
 </div>
<div data-options="region:'center'">
	<table id="groupusershow"></table>  
</div>
</div>
<div id="dlg-item" class="easyui-dialog"  data-options="width: 900,height: 450,closed: true,cache: false,modal: true,title:'查看科室项目列表'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:10px;">
			<div style="height:350px;width:870px;margin-left:10px;">
				<table id="exam_item_list"></table>
			</div>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-item').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
<div id="dlg-result" class="easyui-dialog"  data-options="width: 900,height: 500,closed: true,cache: false,modal: true,title:'查看检查结果与总检结论'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:5px;">
			<div class="easyui-tabs" style="height:405px;width:880px;margin-left:5px;">
				<div title="检查结果" style="padding:0px;">
					<table id="item_result_list"></table>   
    			</div>   
    			<div title="总检结论" style="">   
    			</div>
			</div>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-result').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
<div id="dlg-xxxx" class="easyui-dialog"  data-options="width: 900,height: 540,closed: true,cache: false,modal: true,title:'详细信息'">
	<form id="add1Form">
		<input type="hidden" id="exam_num1"/>
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:5px;">
			<div class="easyui-tabs" id="tab_ss" style="height:450px;width:850px;margin-left:5px;">
				<div title="收费项目列表" style="padding:0px;">
					<table id="exam_item_xx"></table>
    			</div>   
    			<div title="检查结果列表" style="">
    				<table id="item_result_xx"></table>   
    			</div>
    			<div title="总检结论与阳性发现" style=""> 
    				<div class="easyui-layout" fit="true">
		   				<div data-options="region:'west'" style="width:30%;">
		   					<div class="easyui-panel" title="总检结论" fit="true">
				            	<textarea readonly="readonly" style="width: 99%;resize:none;border: 0px;height: 100%;font-size:14px;" id="zongjianjielun"></textarea>
				        </div>
			   			</div>
			   			<div data-options="region:'center'">
			   			<table id="exam_disease">
			      		</table>
		      			</div>
      				</div>  
    			</div>
    			<div title="流程日志列表" style="">
    				<table  id="flowloglist" ></table>   
    			</div>
    			<div title="报告打印流水列表" style="">
    				<table  id="printflowlist" ></table>   
    			</div>
			</div>
	</div>
	</div>
	
	</form>	
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;" onclick="javascript:$('#dlg-xxxx').dialog('close')">关闭</a>
		</div>
	</div>
</div>
<div id="dlg-hisresult"></div>
</body>
 </html>