/**
 * 结束回收
 */
//查询按钮
	function chaxun(){
		getcustomerInfo();//用户信息		
		//体检号赋值
		$("#s_num").val($("#exam_num_s").val());
		gettjbg($("#exam_num_s").val(),"")
	}
//导检单显示
	function gettjbg(exam_num,app_type){
		var model = {exam_num:exam_num,app_type:app_type};
		document.getElementById("disease_tijianbaogao").innerHTML="";
		$.ajax({
			url:'gettjbgList.action',
			type:'post',
			data:model,
			success:function(data){
				var obj = eval('('+data+')');
				if(obj.DJD_path!='NULL'){
					document.getElementById("disease_tijianbaogao").innerHTML="<img src='picture/"+obj.DJD_path+"' style='width:800px;height:1400px;margin-left: -334px;' />";
				}
				//			$('#miaoshu').textbox('setValue',obj.notice);
//				$('#lizi').textbox('setValue',obj.example+obj.examplenote);
			}
		})
	}

//查询人员基本信息
function getcustomerInfo(){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+$("#exam_num_s").val(),
		type:'post',
		async:false,
		success:function(data){
			if(data!="null" && eval("("+data+")").flag!='error'){
				var obj=eval("("+data+")");
				$("#exam_id").val(obj.id);
				$("#exam_num").val(obj.exam_num);
				$("#user_name").html(obj.user_name);
				$("#sex").html(obj.sex+'&nbsp;&nbsp;性');
				$("#age").html(obj.age+'&nbsp;&nbsp;岁');
				$("#userphone").html(obj.phone);
				$("#uservipflag").html(obj.vipflag);
				$("#join_date").html(obj.join_date);
				$("#company").html(obj.company);
				$("#exam_type").html(obj.exam_type);
				$('#getReportWayLine').show();
				$('#getReportWay').combobox('setValue', obj.getReportWay);
				$("#reportAddress").val(obj.reportAddress.trim());
				$("#emailAddress").val(obj.email.trim());
				$("#is_guide_back").html(obj.is_guide_back);
				$("#customer_type").html(obj.customer_type);
				$("#set_name").html(obj.set_name);
				if(obj.getReportWay == '1') {
					$("#reportAddressLine").show();
				} else {
					$("#reportAddressLine").hide();
				}
				if(obj.getReportWay == '3'){
					$("#email").show();
				}else{
					$("#email").hide();
				}
				if(obj.wuxuzongjian==1){
					$("#wuxuzongjian").html("无需总检");
					$("#zongjian").html("总检");
				}else{
					$("#wuxuzongjian").html("");
					$("#zongjian").html("无需总检");
				}
				
				if(obj.is_guide_back=="已回收"){
					$("#is_guide_back").css("color","#ff0000");
				}else{
					$("#is_guide_back").css("color","#434343");
				}
				
				if(obj.exam_dep.length > 0){
					$("#teshuxiangmu").show();
					var str = '';
					for(i=0;i<obj.exam_dep.length;i++){
						str +='<dl><dt>'+obj.exam_dep[i].item_name+'：</dt><dt>'+obj.exam_dep[i].exam_result+'</dt></dl>';
					}
					$("#teshu_div").html(str);
				}else{
					$("#teshuxiangmu").hide();
				}
				
				getwjxmGrid(obj.exam_num);//未检查
				gethfqxGrid(obj.exam_num);//放弃项目
			}else{
				$("#exam_id").val("");
				$("#exam_num").val("");
				$("#user_name").html("");
				$("#sex").html("");
				$("#age").html("");
				$("#userphone").html("");
				$("#uservipflag").html("");
				$("#join_date").html("");
				$("#company").html("");
				$("#exam_type").html("");
				$('#getReportWayLine').hide();
				$('#email').hide();
				$("#reportAddress").val("");
				$("#reportAddressLine").hide();
				$("#wuxuzongjian").html("");
				$("#is_guide_back").html("");
				$("#customer_type").html("");
				$("#set_name").html("");
			}			
			$("#exam_num_s").select();
			$("#exam_num_s").focus();
			
		}
	});
	
}
//弃项
function deleteExam(id,status){
	var IS_SAMPLING_DEL = $("#IS_SAMPLING_DEL").val();
	if(status != 'W' && status != '' && IS_SAMPLING_DEL != 'Y'){
		$.messager.alert("提示","该项目已采样,不能放弃!","error");
    	return;
	}
	$.messager.confirm('提示信息','是否确定放弃该项检查？',function(r){
		if(r){
	    $.ajax({
   		url:'deleteExam.action?id='+id,
   		type:'post',
   		success:function(data){
   			$.messager.alert('提示信息',data);
   			getwjxmGrid($("#exam_num_s").val());
   			gethfqxGrid($("#exam_num_s").val());
   		},
   		error:function(){
   			$.messager.alert('提示信息','操作失败！','error');
   		}
   		});
		}
	})
} 
//恢复
function recover(id){
	$.messager.confirm('提示信息','是否确定恢复该项检查？',function(r){
		if(r){
	    $.ajax({
   		url:'updateExam.action?id='+id,
   		type:'post',
   		success:function(data){
   			$.messager.alert('提示信息',data);
   			getwjxmGrid($("#exam_num_s").val());
   			gethfqxGrid($("#exam_num_s").val());
   		},
   		error:function(){
   			$.messager.alert('提示信息','操作失败！','error');
   		}
   		});
		}
	})
} 


function createImage(){
	if($("#exam_num_s").val() != null && $("#exam_num_s").val() != ""){
		var url="createDJDImage.action?exam_num="+$("#exam_num_s").val();
	   newwin = window.open("", "电子档", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	   newwin.moveTo(0,0);
	   newwin.resizeTo(screen.width,screen.height - 30);
	   newwin.location = url;
	   newwin.focus();
	}else{
		$.messager.alert("操作提示", "请输入体检号", "info");
	}
}

