$(document).ready(function() {
		f_getDatadic();	
	});

	//获取菜单
	function f_getDatadic() {
		var comtype = '<s:property value="model.checktype"/>';
		$('#checktype').combobox({
			url : 'getDatadis.action?com_Type=HTSHLX',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == comtype) {
						$('#checktype').combobox('setValue', data[i].id);
						break;
					} else {
						$('#checktype').combobox('setValue', data[0].id);
					}
				}
			}
		});
	}

	
	/**
	 * 保存修改
	 */
	function batchcheckedit() {
		$.messager.confirm('提示信息','是否确定审核此合同？',function(r){
		 	if(r){
			$.ajax({
				url : 'crmContractCheck.action',
				data : {
					contract_num : $("#contract_num").val(),
					checknotice : $("#checknotice").val(),							
					checktype : document.getElementsByName("checktype")[0].value							
					},
						type : "post",//数据发送方式   
						success : function(text) {
							if (text.split("-")[0] == 'ok') {
								$.messager.alert("操作提示", text);
								getgroupGrid();
								$('#dlg-show').dialog('close');
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
			}
		 })
	}
