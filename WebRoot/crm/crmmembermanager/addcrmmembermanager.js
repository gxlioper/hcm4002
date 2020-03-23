$(document).ready(function () {
	$('#editlevel').combobox({
		url:"getLevel.action",
	    valueField:'level_num',    
	    textField:'level_name',
	    prompt:'请选择等级'
	})
});

function editCrmMember(){
	/*if($('#addcost_amount').val()==''){
		$.messager.alert('提示信息','请填写金额');
		return;
	}*/
	$.ajax({
		url : 'updateCrmMemberManager.action',
		type : 'post',
		//dataType: "json", 
		data : {
			"arch_num":$('#editarch_num').val(),
			"level":$('#editlevel').combobox('getValue'),
		},
		success : function(data) {
			$.messager.alert('提示信息',data);
			$('#dlg-edit').dialog('close');
			getGrid();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}