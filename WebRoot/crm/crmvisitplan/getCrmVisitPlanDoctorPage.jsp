<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(document).ready(function () {
	var flag = $("#add_flag").val();
	if(flag == 1){
		var data = $('#CrmMemberPrivateDoctorAssignshow').datagrid('getSelected');
		$('#addarch_num').val(data.arch_num);
		$("#addexam_num").val(data.exam_num);
		$("#addusername").val(data.user_name);
	}else if(flag == 2){
		var data = $('#CrmVisitPlanshow').datagrid('getSelected');
		$('#addarch_num').val(data.arch_num);
		$("#addexam_num").val(data.exam_num);
		$("#addusername").val(data.personname);
	}else if(flag == 3){
		$('#addarch_num').val($("#arch_num").html());
		$("#addexam_num").val($("#exam_num_x").html());
		$("#addusername").val($("#user_name").html());
		$('#adddoctor').attr("disabled",false);
		$('#adddoctor').combobox({
			url : 'getCrmVisitPlanPerson.action',
			editable : true, //不可编辑状态
			height:26,
			width:210,
			panelHeight : '300',//自动高度适合
			valueField : 0,
			textField : 1,
			onLoadSuccess : function(data) {
				$('#adddoctor').combobox('setText', '<s:property value="doctorName"/>');
				$('#adddoctor').combobox('setValue', '<s:property value="doctor_id"/>');
			}
	 	});
	}
	
	$('#addimportant').combobox({
		url : 'getDatadis.action?com_Type=JHZYJB',
		editable : false, //不可编辑状态
		cache : false,
		height:26,
		width:210,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			$('#addimportant').combobox('setValue', data[0].id);
		}
	});
	$('#upfujianflag').combobox({
	 	data:[{
	 		id:'1',value:'需要'
	 	},{
	 		id:'0',value:'不需要'
	 	}],
	    valueField:'id',    
	    textField:'value',
	    prompt:'请选择是否需要'
 	});
	
});

function addCrmVisitPlanDoctor(){
	if($('#addplan_visit_date').datebox('getValue')==''){
		$.messager.alert('提示信息','回访时间不能为空','error');
		return;
	}
	if( $('#addvisit_content').val()==''){
		$.messager.alert('提示信息','回访内容不能为空','error');
		return;
	}
	var flag = $("#add_flag").val();
	var plan_doctor_id = 0;
	if(flag == 3){
		plan_doctor_id = $("#adddoctor").combobox('getValue');
	}
	$.ajax({
		url : 'saveCrmVisitPlan.action',
		type : 'post',
		data : {
			"arch_num" : $('#addarch_num').val(),
			"exam_num" : $("#addexam_num").val(),
			"visit_content" : $('#addvisit_content').val(),
			"visit_status" : '1',
			"plan_visit_date" : $('#addplan_visit_date').datebox('getValue'),
			"visit_important":$("#addimportant").combobox('getValue'),
			"fujianflag":$("#upfujianflag").combobox('getValue'),
			"plan_doctor_id":plan_doctor_id
		},
		success : function(data) {
			$.messager.alert('提示信息',data);
			$('#dlg-edit').dialog('close');
			
			var flag = $("#add_flag").val();
			if(flag == 2){
				getCrmVisitPlanList();
			}
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}
</script>
<input type="hidden" id="add_flag" value="<s:property value="flag"/>"/>
<fieldset style="margin: 10px;">
	<legend><strong>健康计划编辑</strong></legend> 
	<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
		<dl>
			<dt style="width:100px;">
				<s:text name="tjhname"/>
			</dt>
			<dd>
				<input  type="text" class="textinput"  id="addexam_num" disabled="disabled" class="easyui-validatebox" style="height: 18px; width: 205px;" />
			</dd>
			<dt style="width:100px;">
				姓名 
			</dt>
			<dd>
				<input  type="text" class="textinput"  id="addusername" disabled="disabled" class="easyui-validatebox" style="height: 18px; width: 205px;" />
			</dd>
		</dl>
		<dl>
		    <dt style="width:100px;">重要级别 </dt>
		    <dd>
		       <select class="easyui-combobox" id="addimportant" style="width:210px;height:26px;"></select>
		    </dd>
		    <dt style="width:100px;"><s:text name="tjhname"/></dt>
		    <dd>
		       <input  type="text" class="textinput"  id="addarch_num" disabled="disabled" class="easyui-validatebox" style="height: 18px; width: 205px;" />
		    </dd>
		</dl>
		<dl>
		    <dt style="width:100px;">计划回访时间 </dt>
		    <dd>
		       <input class="easyui-datebox" id="addplan_visit_date" style="width:210px;height:26px;"  data-options="prompt:'请选择计划回访日期'"/>
		    </dd>
		    <dt style="width:100px;">计划回访医生</dt>
		    <dd>
		       <input  type="text" class="textinput"  id="adddoctor" disabled="disabled" style="height: 18px; width: 205px;" />
		    </dd>
		</dl>
		<dl>
		   <dt style="width:100px;">是否需要复检：</dt>
		    <dd>
		       <select class="easyui-combobox" id="upfujianflag" style="width:210px;height:26px;"  data-options="panelHeight:'auto'"></select>
		    </dd>
		</dl>
		<dl>		    
			<dt style="width:100px;">回访内容</dt>
			<dd>
				<textarea   id="addvisit_content" class="textinput"	style="height: 70px; width: 535px;"></textarea>
			</dd>			
		</dl>		
	</div>
  </div>
</form>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addCrmVisitPlanDoctor();" class="easyui-linkbutton c6" style="width:80px;">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
