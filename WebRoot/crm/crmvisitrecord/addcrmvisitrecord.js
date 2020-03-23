$(document).ready(function () {
	
	 var row = $('#CrmVisitPlanshow').datagrid('getSelected');
	 $('#addarch_num').val(row.arch_num);
	 $('#addexam_num').val(row.exam_num);
	 $('#addvisit_num').val(row.visit_num);
	 $('#addusername').val(row.personname);
	 $('#addplanid').val(row.id);
	 $("#phone").val(row.phone);
	 $("#cvr_id").val(row.cvr_id);
	 $("#addcustomer_feedback").val(row.customer_feedback);
	 $("#addhealth_suggest").val(row.health_suggest);
	 
	  $('#addvisit_type').combobox({
		 	data:[{
		 		id:'1',value:'医生主动与体检者电话沟通'
		 	},{
		 		id:'2',value:'体检者主动与医生电话沟通'
		 	},{
		 		id:'3',value:'通过app进行实时沟通'
		 	},{
		 		id:'4',value:'通过微信进行实时沟通'
		 	}],
		    valueField:'id',    
		    textField:'value',
		    prompt:'请选择计划回访方式',
		    onLoadSuccess : function(data) {
			$('#addvisit_type').combobox('setValue',row.visit_type);
		}
	 });
});
function shifang(){
	$.ajax({
		url : 'saveCrmVisitLost.action',
		type : 'post',
		data : {
			"planid" : $('#addplanid').val(),//
			"arch_num" : $('#addarch_num').val(),
			"exam_num" : $('#addexam_num').val(),
			"visit_num" : $('#addvisit_num').val(),//健康计划编码
			"visit_type" : $('#addvisit_type').combobox('getValue'),//回访方式
			"customer_feedback" : $('#addcustomer_feedback').val(),//客户返回信息
			"health_suggest" : $('#addhealth_suggest').val(),//健康建议
			"cvr_id" : $("#cvr_id").val()
		},
		success : function(data) {
			$.messager.alert('提示信息',data);
			$('#dlg-edit').dialog('close');
			getCrmVisitPlanList();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}
function addCrmVisitRecord(){
	if($('#addvisit_type').combobox('getValue')==''){
		$.messager.alert('提示信息','回访方式不能为空','error');
		return;
	}
	if($('#addcustomer_feedback').val() == ''){
		$.messager.alert('提示信息','客户反馈信息不能为空','error');
		return;
	}
	if($('#addhealth_suggest').val() == ''){
		$.messager.alert('提示信息','健康建议不能为空','error');
		return;
	}
	$.ajax({
		url : 'saveCrmVisitRecord.action',
		type : 'post',
		data : {
			"planid" : $('#addplanid').val(),
			"arch_num" : $('#addarch_num').val(),
			"exam_num" : $('#addexam_num').val(),
			"visit_num" : $('#addvisit_num').val(),
			"visit_type" : $('#addvisit_type').combobox('getValue'),
			"customer_feedback" : $('#addcustomer_feedback').val(),
			"health_suggest" : $('#addhealth_suggest').val(),
			"cvr_id" : $("#cvr_id").val()
		},
		success : function(data) {
			$.messager.alert('提示信息',data);
			$('#dlg-edit').dialog('close');
			getCrmVisitPlanList();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}