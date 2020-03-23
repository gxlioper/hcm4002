$(document).ready(function () {
	$('#addbatch_nums').combobox({
		valueField: 'sign_num',
        textField: 'sign_name',
        hasDownArrow:true,
        mode:'remote',
        url:'getSignBillPlanByName.action'
	})
	$('#addpayment_types').combobox({
		url:"getCrmSalesCostBX.action",
	    valueField:'payment_type',    
	    textField:'payment_type_name',
	    prompt:'请选择报销款项类型'
	})
});

function addCrmVisitPlan(){
	if($('#addcost_amount').val()==''){
		$.messager.alert('提示信息','请填写金额');
		return;
	}
	if($('#addcost_types').val()=='2'){
		if($('#addbatch_nums').combobox('getValue')==''){
			$.messager.alert('提示信息','请选择签单计划');
			return;
		}
		if($('#addpayment_types').combobox('getValue')==''){
			$.messager.alert('提示信息','请选择报销款类型');
			return;
		}
	}
	$.ajax({
		url : 'saveCrmSalesCost.action',
		type : 'post',
		//dataType: "json", 
		data : {
			"id" : $('#addid').val(),
			"sales_id" : $('#addsales_ids').val(),
			"cost_amount" : $('#addcost_amount').val(),
			"cost_date" : $('#addcost_date').val(),
			"cost_type" : $('#addcost_types').val(),
			"batch_num" : $('#addbatch_nums').combobox('getValue'),
			"payment_type" : $('#addpayment_types').combobox('getValue'),
			"remark" : $('#addremark').val(),
		},
		success : function(data) {
			$.messager.alert('提示信息',data);
			$('#dlg-edit').dialog('close');
			getCrmSalesCostList();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}