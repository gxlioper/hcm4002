//选中日期
var date_arry=new Array(),user_id_arry=new Array();
function aaar(aa,dateText){
	if(date_arry.length>0){
		if(date_arry[0].split("/")[0]!=dateText.split("/")[0]||date_arry[0].split("/")[2]!=dateText.split("/")[2]){
				date_arry.length = 0;
		}
	}
	if (!Array.prototype.indexOf){
		Array.prototype.indexOf = function(elt , from){
			var len = this.length >>> 0;
			var from = Number(arguments[1]) || 0;
			from = (from < 0) ? Math.ceil(from) : Math.floor(from);
			if (from < 0) from += len;
			for (; from < len; from++){
				if (from in this && this[from] === elt) return from;
			}
			return -1;
		};
	}
	if(date_arry.indexOf(dateText)==-1){
		date_arry.push(dateText);
	}else{
		date_arry.splice($.inArray(dateText,date_arry),1);
	} 
	aa.removeClass("ui-state-active");
	for(i=0;i<aa.length;i++){
		var a="0"+aa.eq(i).text();
		a=a.substring(a.length-2,a.length);
		aa.eq(i).css("color","#000");
		aa.eq(i).css("background-color","#E6E6E6");
		aa.eq(i).removeClass("ui-state-active_me");
		for(n=0;n<date_arry.length;n++){
			if(a==date_arry[n].split("/")[1]){
				aa.eq(i).css("color","#fff)");
				aa.eq(i).css("background-color","#6EC226");
			}
		}
	}
}
	
//保存排班信息
function insert_schedu_date(){
	var date_new=new Array();
	for(i=0;i<date_arry.length;i++){
		var d = date_arry[i];
		var year = d.split("/")[2];
		var monthy = d.split("/")[0];
		var day = d.split("/")[1];
		date_new.push(year+"-"+monthy+"-"+day);
	}
	if(date_new.length == 0){
		$.messager.alert('提示信息', "请选择日期！",'error');
		return;
	}
	if($('#plshangliang').val().length == 0){
		$.messager.alert('提示信息', "请输入数量！",'error');
		return;
	}
	$.ajax({
		url:'saveItemschdeul.action',
		data:{"schedule_time":date_new.toString(),"ids":$('#p_id').val(),"schedule_number":$('#plshangliang').val()},          
		type: "post",  
		success: function(data){  
			$.messager.alert('提示信息', data,'info');
			$("#edit_dlg").dialog("close");
			$("#itemscheduleList").datagrid('reload')
		},
		error:function(){
			$("#edit_dlg").dialog("close");
			$.messager.alert('提示信息', "用户操作失败！","error");
		}  
	});
}

//查询上一月和下一月的排班信息
function select_work(){
	$("#datepicker .ui-datepicker-buttonpane button").css("display","none");
	if(user_id_arry.length == 1){
		insert_schedu_one_btn(user_id_arry[0],date_one);
	}
}

function insert_schedu_one_btn(id,date){
	$.ajax({
		url:'scheduledetailsList.action?m_id='+date.split("-")[1]+"&user_id="+id+"&y_id="+date.split("-")[0],//加载已排班信息
		type: "post", 
		success: function(data){
				 var obj=eval("("+data+")");
				// alert(obj[0].working_date);
				 var aa=$("#datepicker tbody td a");
				 for(i=0;i<aa.length;i++){
					 for(n=0;n<obj.length;n++){//写排班
							var day = obj[n].working_date.split("-")[1]+"/"+obj[n].working_date.split("-")[2]+"/"+obj[n].working_date.split("-")[0];
							var a="0"+aa.eq(i).text();
							a=a.substring(a.length-2,a.length);
							
							var b = obj[n].working_date.split("-")[2];
							if(date_one.split("-")[0] == obj[n].working_date.split("-")[0] && ("0"+date_one.split("-")[1]) == obj[n].working_date.split("-")[1] ){
								if(a == b){
									date_arry.push(day);
									aa.eq(i).css("color","#fff)");
									aa.eq(i).css("background-color","#6EC226");
									}
								}
					}
			 	}
			} 
		});
}
	



	



