$(function() {
		$("#datepicker").datepicker({ 
			showButtonPanel:true,
//		 	monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
//          changeMonth:true,
//         	changeYear:true,
//         	showMonthAfterYear:true,//是否把月放在年的后面  
//         	dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'], 
//         	minDate:'2000-01-01',//最小日期  
//          maxDate:'2020-12-30',//最大日期  	
         	onSelect: function(dateText, inst) {
				var aa=$("#datepicker tbody td a");//
				aaar(aa,dateText);
         	},
		}); 
	});
	
	$(document).ready(function (){
		
		$("#datepicker .ui-datepicker-buttonpane button").click();
		$("#datepicker .ui-datepicker-buttonpane button").css("display","none");
		var aa=$("#datepicker tbody td a");
		for(i=0;i<aa.length;i++){
			aa.eq(i).css("color","#000)");
			aa.eq(i).css("background-color","#E6E6E6");
		}
//		$( "#datepicker" ).datepicker( "option", "minDate",'2017-11-02');
//		 $( "#datepicker" ).datepicker( "option", "maxDate",'2017-11-06');
//		 
//		date_arry.length=0;
//		//--'<s:property value="working_date"/>'
//		//if(user_id_arry.length == 1){
//			insert_schedu_one_btn(user_id_arry[0],'2017-11-09');
//		//}
//			
//			
//			// alert(obj[0].working_date);
//			 var aa=$("#datepicker tbody td a");
//			 var data_one = '2017-11-6';
//			 var obj = "";
//			 for(var i=0;i<aa.length;i++){
//				 for(var n=0;n<obj.length;n++){//写排班
//					 	alert("进来2");
//						var day = obj[n].working_date.split("-")[1]+"/"+obj[n].working_date.split("-")[2]+"/"+obj[n].working_date.split("-")[0];
//						var a="0"+aa.eq(i).text();
//						console.log(a);
//						a=a.substring(a.length-2,a.length);
//						
//						var b = obj[n].working_date.split("-")[2];
//						if(date_one.split("-")[0] == obj[n].working_date.split("-")[0] && ("0"+date_one.split("-")[1]) == obj[n].working_date.split("-")[1] ){
//							if(a == b){
//								date_arry.push(day);
//							/*	aa.eq(i).css("color","#FF0000)");*/
//								aa.eq(i).css("color","#FF0000");
//								aa.eq(i).css("background-color","#FF0000");
//								}
//							}
//				}
//		 	}
	});
	//---------------------//
	//修改排期
	function xiugaipaiqi(){
		if($('#up_piliangshuliang').val()==""){
			$.messager.alert("提示消息","数量无效","error");
			return;
		}
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
//		$.ajax({
//			url:'updateItemschdeul.action',
//			data:{
//				id:$('#up_id').val(),
//				schedule_time:date_new.toString(),
//				schedule_number:$('#up_piliangshuliang').val()	
//			},
//			type:'post',
//			success:function(data){
//				$.messager.alert("提示信息",data,"info");
//			},
//			error:function(){
//				$.messager.alert("提示信息","操作失败！","error");
//			}
//		
//		})
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