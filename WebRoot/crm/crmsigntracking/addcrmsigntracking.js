$(document).ready(function () {
	
});
$(function () {
	$("#addsign_num").combobox({
		valueField: 'sign_num',
        textField: 'sign_name',
        hasDownArrow:true,
        height:26,
        width:209,
        mode:'remote',
        required: true,
        url:'getSignBillPlanByName.action',
        onSelect:function(record){
        	$("#addscontact_name").combobox('reload','getCompanyContactsList.action?company_id='+record.company_id);
        }
	});
	$("#addscontact_name").combobox({
		valueField: 'id',
        textField: 'contacts_name',
        hasDownArrow:true,
        height:26,
        width:209,
        required: true,
        url:'getCompanyContactsList.action?company_id=0',
        onSelect:function(record){
        	$("#addphone").val(record.phone);
        }
	});
	
	$("#addphone,#addtracking_content").validatebox({
		required: true
	});
});

function addCrmSignTracking(){
	var data =  $('#addsign_num').combobox('getData');
	var temp = true;
	for(i=0;i<data.length;i++){
		if($('#addsign_num').combobox('getValue') == data[i].sign_num){
			temp = false;
		}
	}
	if($('#addsign_num').combobox('getValue')==''){
	    $('#addsign_num').combobox().next('span').find('input').focus();
		return;
	}else if(temp){
		$.messager.alert('提示信息','请选择签单计划!','error');
		return;
	}else if($('#addscontact_name').combobox('getText')==''){
		$('#addscontact_name').combobox().next('span').find('input').focus();
		return;
	}else if($('#addphone').val() == ''){
		$('#addphone').focus();
		return;
	}else if($('#addtracking_content').val() == ''){
		$('#addtracking_content').focus();
		return;
	}
	$.ajax({
		url : 'saveCrmSignTracking.action',
		type : 'post',
		data : {
			"id" : $('#addid').val(),
			"sign_num" : $('#addsign_num').combobox('getValue'),
			"contact_name" : $('#addscontact_name').combobox('getText'),
			"phone" : $('#addphone').val(),
			"tracking_content" : $('#addtracking_content').val(),
			"remark" : $('#addremark').val(),
			"tracking_date": $('#addtracking_date').datetimebox('getValue')
		},
		success : function(data) {
			$.messager.alert('提示信息',data);
			$('#dlg-edit').dialog('close');
			getCrmSignTrackingList();
			rili();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}