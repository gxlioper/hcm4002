/**
 * 验证
 */

	function saveCtms(){
		if (document.getElementById("type_code").value == ''){
			$("#type_code").focus();
			return;
		}else if(document.getElementById("type_name").value == ''){
			$("#type_name").focus();
			return;
		}
	$.ajax({
	    url:'saveCustomer.action', 
	    type: "post",
		data:{
			id:$("#id").val(),
			type_code:$("#type_code").val(),
			type_name:$("#type_name").val(),
			type_comment:$("#type_comment").val(),
		  },          
		success: function(data){  
		  	 $.messager.alert('提示信息', data);
		  	 $("#ctms_edit").dialog("close");
		      	 getGrid();
		    },
		 error:function(){
		    	 $("#ctms_edit").dialog("close");
		    	$.messager.alert('提示信息', "用户操作失败！",function(){});
		    }  
	});
}