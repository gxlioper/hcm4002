$(document).ready(function() {
	f_getDatadic();	
});

	//获取菜单
	function f_getDatadic() {
		$('#check_status').combobox({
			url : 'getDatadis.action?com_Type=FASHLX',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == $("#check_statuss").val()) {
						$('#check_status').combobox('setValue', data[i].id);
						break;
					} else {
						$('#check_status').combobox('setValue', data[0].id);
					}
				}
			}
		});
	}

	
	/**
	 * 保存修改
	 */
	function batchcheckedit() {
		$.messager.confirm('提示信息','是否确定审核此体检任务？',function(r){
		 	if(r){
		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		   	    $("body").prepend(str);
			$.ajax({
				url : 'saveCrmBatchCheck.action',
				data : {
					        company_id : $("#company_id").val(),
							batch_id : $("#id").val(),
							check_type:$("#check_type").val(),
							checknotice : $("#checknotice").val(),							
							check_status : $('#check_status').combobox('getValue')						
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								$('#dlg-show').dialog('close');
								$.messager.alert("操作提示", text);
								$("#fanganchecklist").datagrid('load',{check_status:'0',check_type:$("#check_type").val()});
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
		 })
	}