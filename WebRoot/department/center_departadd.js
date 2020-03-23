/**
 * 科室验证
 */

		
	function f_depsave(){
		if (document.getElementById("dep_num").value == ''){
			$("#dep_num").focus();
			return;
		}else if($("#message").attr('value') == 'no'){
			$("#dep_num").focus();
			return;
		}else if(document.getElementById("dep_name").value == ''){
			$("#dep_name").focus();
			return;
		}else if($("#dep_category").combobox('getValue')==''){
			//alert('请选择科室类型');
			$.messager.alert('提示信息', '请选择科室类型!');
			return;
		}else if (document.getElementById("sex").value == ''){
			$("#sex").focus();
			return;
		}else if (document.getElementById("seq_code").value == ''){
			$("#seq_code").focus();
			return;
		}
	$.ajax({
	url:'baocunDepartment.action',  
	data:{
		id:$("#id").val(),
		dep_num:$("#dep_num").val(),
		dep_name:$("#dep_name").val(),
		dep_category:$("#dep_category").combobox('getValue'),
		sex:$("#sex").val(),
		seq_code:$("#seq_code").val(),
		remark:$("#remark").val(),
		dep_link:$("#dep_link").val(),
		isPrint_Barcode:$("#isPrint_Barcode").val(),
		dep_inter_num:$("#dep_inter_num").val(),
		calculation_rate:$('#calculation_rate').val()
	  },          
	type: "post",  
	success: function(text){
//		alert(text);
//		  if (text.split("-")[0] == 'ok') {
//	  	 $.messager.alert('提示信息', text.split("-")[1]);
//	  	 $("#dep_edit").dialog("close");
//	      	 getGrid();	  	
//		  }else{
//			$.messager.alert("操作提示", text.split("-")[1], "error");
//		}
		 getGrid();	 
		$.messager.alert("操作提示",text);
		 $("#dep_edit").dialog("close");
	    },
	    error:function(){
	    	alert("失败");
	    	 $("#dep_edit").dialog("close");
	    	$.messager.alert('提示信息', "用户操作失败！",function(){});
	    }  
	});
}