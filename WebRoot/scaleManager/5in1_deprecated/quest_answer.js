function chaxun(){
	getcustomerInfo();
	getFirstMenu_questanswer();
}

//查询人员基本信息
function getcustomerInfo(){
	if(!$("#exam_num").val()) {
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

//下拉列表
function getFirstMenu_questanswer(){
	$.ajax({
   		url:'getFirstMenu_questanswer.action',
   		type:'post',
   		data: { quest_code: "Q01"},
   		success:function(data){
   			var daMsg=eval("("+data+")"); //先转字符串
   			var divTitle = "<div id='accordionid' class='easyui-accordion' style='width:250px;'>";
   			var divCont = "";
   			for(var i=0;i<daMsg.length;i++){
   				var str = "";
   				if(daMsg[i].quest_sub_name.length>11){
   					str = daMsg[i].quest_sub_name.substring(0,10)+"..."
   				}else{str =daMsg[i].quest_sub_name}
   				var div= "<div id='title_"+daMsg[i].quest_sub_code+"' title='<a onClick=\"javaScript:showAllQuestion(&#39;"+daMsg[i].quest_sub_code+"&#39;,&#39;"+daMsg[i].quest_sub_name+"&#39;)\" style=\"color:#000; font-size:15px;\">"+str+"</a> '></div>";
   				divCont += div;
   			}
   			var divThree = "</div>";
   			$("#xiaLa").append(divTitle+divCont+divThree);
   			$.parser.parse($("#xiaLa")); //渲染
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载菜单失败！','error');
   		}
   	});
}

function showAllQuestion(quest_sub_code,quest_sub_name){
	$("#titleText").empty();
	$("#titleText").append(quest_sub_name);
	$("#questionTitle").empty();
	
	var url;
	if(quest_sub_code == "QS01") {
		url = 'brainShowPage.action';
	} else {
		url = 'scale_answer.action?scale_code='+quest_sub_code+'&exam_num='+$("#exam_num").val();
	}
	var iframe='<iframe scrolling="auto" width="100%" align="center" onload="Javascript:SetWinHeightinn(this)" frameborder="0"  src="'+url+'"></iframe>';
	$("#questionTitle").append(iframe);
}

function SetWinHeightinn(obj) {
	 var height = window.screen.height-250;  
	 obj.height = height;
} 