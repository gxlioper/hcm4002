/**
 * 数据验证
 */

	function savedadt(){
		if (document.getElementById("data_code1").value == ''){
			$("#data_code1").focus();
			return;
		}else if(document.getElementById("data_name1").value == ''){
			$("#data_name1").focus();
			return;
		}else if($("#data_type").combobox('getText') == ''){
			alert($("#data_type").combobox('getText'));
			$.messager.alert('提示信息', '请选择或输入数据类型!');
			return;
		}else if(document.getElementById("seq_code").value == ''){
			$("#seq_code").focus();
			return;
		}
	$.ajax({
	    url:'saveDaDt.action', 
	    type: "post",
		data:{
			id:$("#id").val(),
			data_code:$("#data_code1").val(),
			data_name:$("#data_name1").val(),
			data_type:$("#data_type").combobox('getText'),
			seq_code:$("#seq_code").val(),
			remark:$("#remark").val(),
			data_code_children:$("#data_code_children").val(),
			data_class:$("#data_class").combobox('getValue')
		  },          
		success: function(data){  
		  	 $.messager.alert('提示信息', data);
		  	 $("#dlg-edit").dialog("close");
		       getGrid();
		    },
		 error:function(){
		    	 $("#dlg-edit").dialog("close");
		    	$.messager.alert('提示信息', "用户操作失败！",function(){});
		    }  
	});
}