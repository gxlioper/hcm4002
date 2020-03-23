$(document).ready(function () {
	var data = $('#CrmVisitPlanshow').datagrid('getSelected');
	$("#upusername").val(data.personname);
	$("#updoctor").val(data.username);
	 $('#upfujianflag').combobox({
		 	data:[{
		 		id:'1',value:'需要'
		 	},{
		 		id:'0',value:'不需要'
		 	}],
		    valueField:'id',    
		    textField:'value',
		    prompt:'请选择是否需要',
		    onLoadSuccess : function(data) {
				$('#upfujianflag').combobox('setValue', $("#upfujianflag_im").val());
			}
	 });
	$('#upimportant').combobox({
		url : 'getDatadis.action?com_Type=JHZYJB',
		editable : false, //不可编辑状态
		cache : false,
		height:26,
		width:210,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			$('#upimportant').combobox('setValue', $("#updddvisit_important").val());
		}
	});
});
function addCrmVisitPlan(){
	if($('#upplan_visit_date').datebox('getValue')==''){
		$.messager.alert('提示信息','回访时间不能为空','error');
		return;
	}
	
//	if( $('#upvisit_content').val()==''){
//		$.messager.alert('提示信息','回访内容不能为空','error');
//		return;
//	}
	$.ajax({
		url : 'saveCrmVisitPlan.action',
		type : 'post',
		data : {
			"id" : $('#upid').val(),
			"arch_num" : $('#uparch_num').val(),
			"exam_num" : $("#upexam_num").val(),
			"visit_content" : $('#upvisit_content').val(),
			"plan_visit_date" : $('#upplan_visit_date').datebox('getValue'),
			"visit_important":$("#upimportant").combobox('getValue'),
			"fujianflag":$("#upfujianflag").combobox('getValue'),
			"cvr_id":$("#cvr_id").val()
		},
		success : function(data) {
			$.messager.alert('提示信息',data);
			$('#dlg-edit').dialog('close');
			getCrmVisitPlanList()
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}