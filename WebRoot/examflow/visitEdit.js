$(document).ready(function () {
	$("#remark").validatebox({
		required:false,
		validType:'maxLength[200]'
	});
	
	$('#visit_type').combobox({
		data:[{
	 		id:'5',value:'电话'
	 	},{
	 		id:'6',value:'排查'
	 	}],
	 	editable : false,
	 	panelHeight : 'auto',//自动高度适合 
		valueField : 'id',
		textField : 'value',
	    prompt:'请选择回访方式'
	});
	
	$.ajax({
		url : 'getDatadis.action?com_Type=HFNR',
		type : 'get',
		success : function(data) {
			var arr = eval('('+data+')');
			for(var index in arr) {
				var row = arr[index];
				var html = $("#HFNR").html();
				$("#HFNR").append(row.name);
				if(index < arr.length-1) {
					$("#HFNR").append('\n');
				}
			}
		},
	});
	
	$.ajax({
		url : 'getDatadis.action?com_Type=HFNRBZ',
		type : 'get',
		success : function(data) {
			var arr = eval('('+data+')');
			for(var index in arr) {
				var row = arr[index];
				var html = $("#HFNRBZ").html();
				$("#HFNRBZ").append(row.name);
				if(index < arr.length-1) {
					$("#HFNRBZ").append('\n');
				}
			}
		},
	});
	
	/*$('#HFNR').combobox({
		url : 'getDatadis.action?com_Type=HFNR',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 100,//自动高度适合
		multiple:true,
		valueField : 'id',
		textField : 'name',
		onSelect:function(record) {
			$("#customer_feedback").val($("#customer_feedback").val()+record.name)
		}
	});
	$('#HFNRBZ').combobox({
		url : 'getDatadis.action?com_Type=HFNRBZ',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		prompt:'请选择备注',
		onSelect:function(record) {
			$("#remark").val($("#remark").val()+record.name)
		}
	});*/
	$('#visit_result').combobox({
	 	data:[{
	 		id:'1',value:'回访成功'
	 	},{
	 		id:'2',value:'回访不成功'
	 	}],
	    valueField:'id',    
	    textField:'value',
	    prompt:'请选择回访结果'
	});
	$('#examNum').css('ime-mode','disabled');
	$('#examNum').focus();
});

//获取选中文本
function getSelectedText(fromDom, toID){
	var html = '';
    if (document.selection) //IE
    {
        html += document.selection.createRange().text;
    } 
    else { 
    	html += fromDom.value.substring(fromDom.selectionStart, fromDom.selectionEnd); 
    }
    if($("#"+toID).val().indexOf(html) < 0) {//如果没有包含才添加，这样就不会重复添加。
    	$("#"+toID).val($("#"+toID).val() + html +'\n');
    }
}
var commit = false;
function saveVisit(){
	if(commit) {
		return;
	}
	commit = true;
	
	if($('#visit_type').combobox('getValue')==''){
		$.messager.alert('提示信息','回访方式不能为空','error');
		return;
	}
	
	$.ajax({
		url : 'saveVisit.action',
		type : 'post',
		data : {
			"recordID" : $('#recordID').val(),
			"arch_num" : $('#archNum').val(),
			"exam_num" : $("#examNum").val(),
			"visit_type":$("#visit_type").combobox('getValue'),
			"visit_result" : $('input[name="visit_result"]:checked').val(),
			"customer_feedback" : $('#customer_feedback').val(),
			"remark" : $('#remark').val(),
		},
		success : function(data) {
			$.messager.alert('提示信息',data);
			$('#visit_dlg').dialog('close');
			if($('#recordID').val() == '') {
				getStatisticsData();
				chaxun();
			}
			chaxun_visited();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}
function notNeedVisit() {
	$('#remark').val($('#remark').val()+"无需回访\n");
	saveVisit();
}