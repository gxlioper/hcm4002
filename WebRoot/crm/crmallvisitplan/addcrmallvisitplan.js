$(document).ready(function () {
	 $('#addplan_doctor_id').combobox({
		 url:"getCrmVisitPlanPerson.action",
		    valueField:0,    
		    textField:1,
		    prompt:'请选择计划回访医生'
	 }
			 )
});
function addCrmVisitPlan(){
	if($('#addplan_doctor_id').combobox('getValue')==''){
		$.messager.alert('提示信息','计划回访医生不能为空');
		return;
	}
	if($('#addplan_visit_date').datebox('getValue')==''){
		$.messager.alert('提示信息','回访时间不能为空');
		return;
	}
	
	if( $('#addvisit_content').val()==''){
		$.messager.alert('提示信息','回访内容不能为空');
		return;
	}
	$.ajax({
		url : 'saveCrmVisitPlan.action',
		type : 'post',
		//dataType: "json", 
		data : {
			"id" : $('#addid').val(),
			"arch_num" : $('#addarch_num').val(),
			"plan_doctor_id" : $('#addplan_doctor_id').combobox('getValue'),
			"visit_content" : $('#addvisit_content').val(),
			"visit_status" : '1',
			"plan_visit_date" : $('#addplan_visit_date').datebox('getValue'),
		},
		success : function(data) {
			$.messager.alert('提示信息',data);
			$('#dlg-edit').dialog('close');
			if( $('#addid').val()!=''){getCrmVisitPlanList()};
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}