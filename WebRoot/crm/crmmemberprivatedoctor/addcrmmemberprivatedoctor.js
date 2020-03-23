$(document).ready(function () {
	
	var data = $('#CrmMemberPrivateDoctorshow').datagrid('getSelections');
	var str = '';
	if(data.length > 3){
		str = data[0].user_name+','+data[1].user_name+','+data[2].user_name+'等 '+data.length+'位体检者';
	}else if(data.length == 3){
		str = data[0].user_name+','+data[1].user_name+','+data[2].user_name;
	}else if(data.length == 2){
		str = data[0].user_name+','+data[1].user_name;
	}else if(data.length == 1){
		str = data[0].user_name;
	}
	$("#show_exam_name").html(str);
	 $('#adddoctor_id').combobox({
		 url:"getCrmVisitPlanPerson.action",
		    valueField:0,    
		    textField:1,
		    prompt:'请选择私人医生'
	 });
});
function addCrmMemberPrivateDoctor(){
	if($('#adddoctor_id').combobox('getValue')==''){
		$.messager.alert('提示信息','请选择要分配的私人医生');
		return;
	}
	var data = $('#CrmMemberPrivateDoctorshow').datagrid('getSelections');
	var exam_nums = new Array();
	var arch_nums = new Array();
	for(i=0;i<data.length;i++){
		exam_nums.push(data[i].exam_num);
		arch_nums.push(data[i].arch_num);
	}
	$.ajax({
		url : 'mergeCrmMemberPrivateDoctor.action',
		type : 'post',
		data : {
			"exam_num" :exam_nums.toString(),
			"arch_num": arch_nums.toString(),
			"doctor_id" : $('#adddoctor_id').combobox('getValue')
		},
		success : function(data) {
			$.messager.alert('提示信息',data);
			$('#dlg-edit').dialog('close');
			getCrmMemberPrivateDoctorList();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}