/**
 * 驳回意见保存
 */

function f_rejectsave(){
	$.ajax({
	url:'saveRejection.action',  
	data:{
		id:$("#id").val(),
		reject_context:$("#rejectcontext").val()
	  },          
	type: "post",  
	success: function(text){
		  if (text.split("-")[0] == 'ok') {
	  	 $.messager.alert('提示信息', text.split("-")[1]);
	  	 $("#reject_edit").dialog("close");
	      	 getGrid();	  	
		  }else{
			$.messager.alert("操作提示", text.split("-")[1], "error");
		}
	    },
	    error:function(){
	    	 $("#reject_edit").dialog("close");
	    	$.messager.alert('提示信息', "用户操作失败！",function(){});
	    }  
	});
}