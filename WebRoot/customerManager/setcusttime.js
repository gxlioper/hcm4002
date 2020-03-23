
	function checkinput() {
		var startdate=$("#datetime").datebox('getValue');
		var time1=document.getElementById("time1").value;
		var time2=document.getElementById("time2").value;
		if(startdate==''){
			alert('体检日期不能为空！');
			return false;
		}else if(!isDate(startdate)){
			alert('体检日期格式错误！');
			return false;
		}else if((time1=='')||(time1.length!=5)){
			alert('体检开始时间不能为空或格式错误！');
		    return false;
	    }else if(!isTime(time1)){
			alert('体检开始时间格式错误！');
	        return false;
        }else if((time2=='')||(time2.length!=5)){
			alert('体检结束时间不能为空或格式错误！');
	        return false;
        }else if(!isTime(time2)){
			alert('体检结束时间格式错误！');
	        return false;
        }
		return true;
	}

	/**
	 * 保存修改
	 */
	function addcustomerdo() {
		if (checkinput()) {
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			$.ajax({
				url : 'setcusttimedo.action',
				data : {
					        birthday:$("#datetime").datebox('getValue'),							
							time1 : $("#time1").val(),
							time2 : $("#time2").val(),
							batch_id : $("#addbatch_id").val(),							
							ids:$("#ids").val()
						},
						type : "post",//数据发送方式   
						success : function(text) {
				    		$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								$('#dlg-custedit').dialog('close');
								$("#dlg-custedit-time").dialog('close'); 
								$.messager.alert("操作提示", text);
								gettimeuserGrid();
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
		}
	}

