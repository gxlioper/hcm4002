function chaxun(){
	var url = 'scale_answer.action?scale_code='+$("#scale_code").val();
	if($("#exam_num").val()) {
		url += '&exam_num='+$("#exam_num").val();
	}
	window.location.href = url;
}

function submit() {
	var exam_num = $("#exam_num").val();
	if(!exam_num) {
		$.messager.alert("操作提示", "没有输入体检编号","error");
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	var scale_result_list = '[';
	var index=0;
	var total_score=0;
	while(true) {
		var questionID = $("#question"+index).val();
		var answerID = $("input[name='question"+index+"']:checked").attr("optionId");
		var score = $("input[name='question"+index+"']:checked").attr("optionValue");
		if(!answerID) {
			break;
		}
		
		var scale_result = {
				questionID:questionID,
				answerID:answerID,
				score:score,
		};
		total_score += parseInt(score);
		scale_result_list += (JSON.stringify(scale_result)+",");
		index++;
	}
	full_score = parseInt($("#full_score").val());
	total_score = total_score * 100 / full_score;
	scale_result_list += ']';
	$.ajax({
		url : 'saveScaleResult.action',
		data : {
		    exam_num			:exam_num,
		    scale_code			:$('#scale_code').val(),
		    score				:total_score,
			scale_result_list	:scale_result_list,
			finish				:index==$("#question_num").val(),
		},
		type : "post",//数据发送方式   
		success : function(text) {
			$(".loading_div").remove();
			if (text.split("-")[0] == 'ok') {
				//getgroupuserGrid();
			} else {							
				$.messager.alert("操作提示", text.split("-")[1], "error");
			}
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}

//查询人员基本信息
function getcustomerInfo(){
	if(!$("#exam_num").val()) {
		$("#answer_fs").hide();
		return;
	}
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+$("#exam_num").val(),
		type:'post',
		success:function(data){
			if(data == 'null'){
				$("#exam_id").val('');
				$("#arch_num").val('');
				$("#exam_num").val('');
				$("#user_name").html('');
				$("#sex").html('');
				$("#age").html('');
				$("#company").html('');
				$("#customer_type").html('');
				$("#set_name").html('');
				return;
			}
			var obj=eval("("+data+")");
			if(obj.flag && obj.flag == 'error') {
				$("#answer_fs").hide();
				$.messager.alert('提示信息',obj.info,'error');
				return;
			}
			$("#exam_id").val(obj.id);
			$("#exam_num").val(obj.exam_num);
			$("#arch_num").html(obj.arch_num);
			$("#user_name").html(obj.user_name);
			$("#sex").html(obj.sex);
			$("#age").html(obj.age);
			$("#company").html(obj.company);
			$("#customer_type").html(obj.customer_type);
			$("#set_name").html(obj.set_name);
		}
	});
}