$(document).ready(function () {
	$('#fenlei').combobox({
		url:'getDatadisKongGe.action?com_Type=DXMBLX',    
		valueField:'id',    
		textField:'name',
	   panelHeight:'auto'
			
	})
	getgroupuserGrid();
});
/**
 * 清空查询
 */
function empty(){
	$('#num').textbox('setValue',"");
	$('#demo_name').textbox('setValue',"");
	getgroupuserGrid();
}
 //-------------------------------显示报告样本------------------------------------
/**
 * 显示报告样本列表
 */
 function getgroupuserGrid(){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#groupusershow").datagrid({
		 url:'queryCrmSmsBaseTemplate.action',
		 queryParams:{
			 "sms_name":$('#num').val(),
			 "sms_category":$('#fenlei').combobox('getValue')
		 },
		 toolbar:'#toolbar',
		 rownumbers:true,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'sms_name',title:'模板名称',width:15},	
		    {align:'center',field:'sms_category',title:'模板分类',width:10,},
		 	{align:'left',field:'sms_note',title:'模板内容',width:50},
		 	{align:'center',field:'sms_state_s',title:'是否默认',width:10},
		 	{align:'center',field:'chi_name_c',title:'创建人',width:10},
		 	{align:'center',field:'create_time',title:'创建时间',width:10},
		 	{align:'center',field:'chi_name_x',title:'修改人',width:10},
		 	{align:'center',field:'update_time',title:'修改时间',width:10},
		 	{align:'center',field:'sss',title:'修改',width:10,"formatter":f_xg},
		 	{align:'center',field:'ss',title:'删除',width:10,"formatter":f_sc},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
		    fit:true,
	        toolbar:toolbar,
	        onDblClickRow:function(index, row){
	        	var row_id = $('#groupusershow').datagrid('getRows')[index].id;
	        	updataSampleReportDemoPage(row_id);
	        }
	});
}


//-------------------------------------修改报告样本页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updataSampleReportDemoPage(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改报告样本页面
 * 
 */
 function updataSampleReportDemoPage(id){
			$("#dlg-custedit").dialog({
			title:'修改短信模板',
			title : '新增模板',
			width : 700,
			height :450,
			href:'updateCrmSmsBaseTemplatePage.action?id='+id
		});
		$("#dlg-custedit").dialog('open');
}
//-------------------------------------新增报告样本&&修改报告样本----------------------------

/**
 * 验证报告编号是否可用
 */
function addnum() {
	$('#addnum').validatebox({
		required : true,
	});
	$.ajax({
		url : 'getSampleReportDemoBynum.action?demo_num=' + $("#addnum").val(),
		type : 'post',
		success : function(data) {
			if (data=='no') {
				$('#ssnum').text("编号已存在!")
				return false;
			} else if (data=='ok') {
				$('#ssnum').text("")
				return true;
			}
		}
	});
}
/**
 * 添加报告样本
 * 
 */
function addSampleReportDemo() {
	if($('#addnum').val()==''||/[\u0391-\uFFE5]/g.test(document.getElementById('addnum').value)){
		$('#addnum').focus();
		return;
	}
	
	if ($('#ssnum').text()!='') {
		return;
	}
	if($('#addname').val()==''){
		$('#addname').focus();
		return;
	}

	if(!/^[0-9]{1,20}$/.test(document.getElementById('addseq').value)&&
		document.getElementById('addseq').value!=''){
		$('#addseq').focus();
		return;
	}
	$.ajax({
		url : 'addSampleReportDemo.action',
		type : 'post',
		//dataType: "json", 
		data : {
			"id" : $('#addid').val(),
			"demo_name" : $('#addname').val(),
			"demo_num" : $('#addnum').val(),
			"print_seq" : $('#addseq').val(),
			"remark" : $('#addremark').val(),
			"isPrint_BarCode":$("input[name='isPrint_BarCode']:checked").val(),
		},
		success : function(data) {
			$.messager.alert('添加成功',data);
			$('#dlg-custedit').dialog('close')
			getgroupuserGrid();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}
//------------------------------------删除报告样本----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 删除报告样本提示信息
 * 
 */
function f_deluser(id) {
	$.messager.confirm('提示信息', '是否删除模板？', function(r) {
		if (r) {
			$.ajax({
				url : 'deleteCrmSmsBaseTemplate.action?id='+ id,
				type : 'post',
				success : function(data) {
					$.messager.alert('提示信息', data);
					getgroupuserGrid();
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	})
}
/**
 * 批量删除报告样本
 * 
 */
function deluserrow(ids){
	if(ids==""){
		$.messager.alert('提示信息','请选择要删除的报告样本!')
		return;
	}
	$.messager.confirm('提示信息','是否确定删除选中报告样本',function(r){
	 	if(r){
	 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
				 url : 'deleteSampleReportDemo.action',
				data : {ids:ids},
				type : "post",
				success : function(data) {
						$.messager.alert("操作提示",data);
						getgroupuserGrid();
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			 });
	 	 }
	 });
}
//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text : '新增模板',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增模板',
			width : 700,
			height :450,
			href : 'addCrmSmsBaseTemplatePage.action'
		});
		$("#dlg-custedit").dialog('open');
	}
}/*, {
	text:'批量删除',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var ids = new Array();
    	var checkedItems = $('#groupusershow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids.push(item.id);
	    }); 
	    deluserrow(ids.toString());
    }
}*/ ];
