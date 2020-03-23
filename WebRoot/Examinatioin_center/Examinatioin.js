/**
 * 体检中心验证
 */
	function f_Examsave(){
		if (document.getElementById("center_num").value ==""){
			$("#center_num").focus();
			return;
		}else if($("#message").attr('value') == 'no'){
			$("#center_num").focus();
			return;
		}else if(document.getElementById("center_name").value == ''){
			$("#center_name").focus();
			return;
		}
		
		if($('#limit_count').val()==''){
			 $('#limit_count').focus();
			return;
		}
		if(!/^[0-9]*$/.test($('#limit_count').val())){
			alert("体检中心人数上限请输入正确格式请输入数字");
			return;
		}
		
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	$.ajax({
	    url:'saveExam.action', 
	    type: "post",
		data:{
			id:$("#id").val(),
			center_num:$("#center_num").val(),
			center_name:$("#center_name").val(),
			photo_function_status:$("#photo_function_status").val(),
			limit_count:$('#limit_count').val()
			
		  },          
		success: function(data){
			 $(".loading_div").remove();
		  	 $.messager.alert('提示信息', data);
		  	 $("#dlg-edit").dialog("close");
		      	 getGrid();
		    },
		 error:function(){
			 	$(".loading_div").remove();
		    	$("#dlg-edit").dialog("close");
		    	$.messager.alert('提示信息', "用户操作失败！",function(){});
		    }  
	});
}