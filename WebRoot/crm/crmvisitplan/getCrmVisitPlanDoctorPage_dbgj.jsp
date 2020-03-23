<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(document).ready(function () {
	$('#addarch_num').val($("#arch_num").html());
	$("#addexam_num").val($("#exam_num_x").html());
	$("#addusername").val($("#user_name").html());
	$('#adddoctor1').combobox({
		url : 'getCrmVisitPlanPerson.action',
		editable : true, //不可编辑状态
		height:26,
		width:210,
		panelHeight : '300',//自动高度适合
		valueField : 0,
		textField : 1,
		onLoadSuccess : function(data) {
			$('#adddoctor1').combobox('setValue', '<s:property value="doctor_id"/>');
		}
	 });
	$('#adddoctor2').combobox({
		url : 'getCrmVisitPlanPerson.action',
		editable : true, //不可编辑状态
		height:26,
		width:210,
		panelHeight : '300',//自动高度适合
		valueField : 0,
		textField : 1,
		onLoadSuccess : function(data) {
			$('#adddoctor2').combobox('setValue', '<s:property value="doctor_id"/>');
		}
	 });
	$('#adddoctor3').combobox({
		url : 'getCrmVisitPlanPerson.action',
		editable : true, //不可编辑状态
		height:26,
		width:210,
		panelHeight : '300',//自动高度适合
		valueField : 0,
		textField : 1,
		onLoadSuccess : function(data) {
			$('#adddoctor3').combobox('setValue', '<s:property value="doctor_id"/>');
		}
	 });
	$('#adddoctor4').combobox({
		url : 'getCrmVisitPlanPerson.action',
		editable : true, //不可编辑状态
		height:26,
		width:210,
		panelHeight : '300',//自动高度适合
		valueField : 0,
		textField : 1,
		onLoadSuccess : function(data) {
			$('#adddoctor4').combobox('setValue', '<s:property value="doctor_id"/>');
		}
	 });
	
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
	if($('#addplan_visit_date1').datebox('getValue')==''){
		$.messager.alert('提示信息','回访时间1不能为空','error');
		return;
	}
//	if($('#addplan_visit_date2').datebox('getValue')==''){
//		$.messager.alert('提示信息','回访时间2不能为空','error');
//		return;
//	}
//	if($('#addplan_visit_date3').datebox('getValue')==''){
//		$.messager.alert('提示信息','回访时间3不能为空','error');
//		return;
//	}
//	if($('#addplan_visit_date4').datebox('getValue')==''){
//		$.messager.alert('提示信息','回访时间4不能为空','error');
//		return;
//	}
	$.ajax({
		url : 'saveCrmVisitPlanList.action',
		type : 'post',
		data : {
			"arch_num" : $('#addarch_num').val(),
			"exam_num" : $("#addexam_num").val(),
			"visit_content" : $('#addvisit_content').val(),
			"visit_status" : '1',
			"visit_important":$("#addimportant").combobox('getValue'),
			"fujianflag":$("#upfujianflag").combobox('getValue'),
			"plan_visit_date1" : $('#addplan_visit_date1').datebox('getValue'),
			"plan_doctor_id1":$("#adddoctor1").combobox('getValue'),
			"plan_visit_date2" : $('#addplan_visit_date2').datebox('getValue'),
			"plan_doctor_id2":$("#adddoctor2").combobox('getValue'),
			"plan_visit_date3" : $('#addplan_visit_date3').datebox('getValue'),
			"plan_doctor_id3":$("#adddoctor3").combobox('getValue'),
			"plan_visit_date4" : $('#addplan_visit_date4').datebox('getValue'),
			"plan_doctor_id4":$("#adddoctor4").combobox('getValue')
		},
		success : function(data) {
			$.messager.alert('提示信息',data);
			$('#dlg-edit').dialog('close');
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
		    <dt style="width:100px;"><s:text name="dahname"/></dt>
		    <dd>
		       <input  type="text" class="textinput"  id="addarch_num" disabled="disabled" class="easyui-validatebox" style="height: 18px; width: 205px;" />
		    </dd>
		</dl>
		<dl>
		    <dt style="width:100px;">计划回访时间1</dt>
		    <dd>
		       <input class="easyui-datebox" id="addplan_visit_date1" style="width:210px;height:26px;"  data-options="prompt:'请选择计划回访日期'"/>
		    </dd>
		    <dt style="width:100px;">计划回访医生1</dt>
		    <dd>
		       <input  type="text" class="textinput"  id="adddoctor1" style="height: 18px; width: 205px;" />
		    </dd>
		</dl>
		<dl>
		    <dt style="width:100px;">计划回访时间2 </dt>
		    <dd>
		       <input class="easyui-datebox" id="addplan_visit_date2" style="width:210px;height:26px;"  data-options="prompt:'请选择计划回访日期'"/>
		    </dd>
		    <dt style="width:100px;">计划回访医生2</dt>
		    <dd>
		       <input  type="text" class="textinput"  id="adddoctor2" style="height: 18px; width: 205px;" />
		    </dd>
		</dl>
		<dl>
		    <dt style="width:100px;">计划回访时间3</dt>
		    <dd>
		       <input class="easyui-datebox" id="addplan_visit_date3" style="width:210px;height:26px;"  data-options="prompt:'请选择计划回访日期'"/>
		    </dd>
		    <dt style="width:100px;">计划回访医生3</dt>
		    <dd>
		       <input  type="text" class="textinput"  id="adddoctor3" style="height: 18px; width: 205px;" />
		    </dd>
		</dl>
		<dl>
		    <dt style="width:100px;">计划回访时间4</dt>
		    <dd>
		       <input class="easyui-datebox" id="addplan_visit_date4" style="width:210px;height:26px;"  data-options="prompt:'请选择计划回访日期'"/>
		    </dd>
		    <dt style="width:100px;">计划回访医生4</dt>
		    <dd>
		       <input  type="text" class="textinput"  id="adddoctor4" style="height: 18px; width: 205px;" />
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
				<textarea id="addvisit_content" class="textinput" style="height: 70px; width: 535px;resize:none;"></textarea>
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
