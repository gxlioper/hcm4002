$(document).ready(function () {
	if($("#is_use_all_s").val() == 'Y'){
		getuserinvoice_all();
	}else{
		getuserinvoice_sigle();
	}
	$('#fplx_q').combobox({
		url : 'getDatadis.action?com_Type=FPLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data){
			if(data.length > 0){
				$('#fplx_q').combobox('setValue', data[0].id);
			}
		}
	});
});
// 共用发票号段查询列表
function getuserinvoice_all() {
	var model = {"is_use_all" : 'Y'};
	$("#userinvoicelist").datagrid({
		url : 'getUserInvoiceList.action',
		dataType : 'json',
		queryParams : model,
		toolbar : toolbar,
		rownumbers : true,
		columns : [ [ {align : 'center',field : 'invoice_num_min',title : '最小发票号',width : 15}, 
		              {align : 'center',field : 'invoice_num_max',title : '最大发票号',width : 15}, 
		              {align : 'center',field : 'invoice_num_used',title : '已用最大发票',width : 15}, 
		              {align : 'center',field : 'invoice_classs',title : '发票类型',width : 10}, 
		              {align : 'center',field : 'is_active',title : '发票类型启/停',width : 10, "formatter":f_showactive,"styler":f_color},
		              {align : 'center',field : 'creater',title : '操作人',width : 10},
		              {align : 'center',field : 'create_time',title : '操作时间',width : 15}
		          ] ],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
		},
		collapsible : true,
		pagination : false,
		fitColumns : true,
		striped : true,
		fit : true,
		checkOnSelect : false
	});
}

function f_showactive(val,row){
	if(row.is_active == 'Y'){
		return '已启用';
	}else{
		return '已停用';
	}
}

function f_color(val,row){
	if(row.is_active == 'N'){
		return 'color:#f00;';
	}
}

/**
 * 定义工具栏
 */
var toolbar=[{
		text:'维护共用发票号段',
		width:150,
		height:30,
		iconCls:'icon-add',
	    handler:function(){
	    	$("#dlg-edit").dialog({
	    		title:'共用发票号段维护',
	    		href:'getUserInvoiceEditPage.action'
	    	});
	    	$('#dlg-edit').dialog('open');
	    }
	},{
		text:'启用发票类型',
		width:150,
		height:30,
		iconCls:'icon-check',
	    handler:function(){
	    	$("#dlg-fplx").dialog("open");
	    	$("#dlg-fplx").dialog("center");
	    }
	},{
		text:'启用个人发票号段模式',
		width:180,
		height:30,
		iconCls:'icon-check',
		handler:function(){
			$.messager.confirm('提示信息','确定要启用个人发票号段模式吗？',function(r){
				if(r){
					useing_all('N');
				}
			});
		}
	}];

//个人使用发票号段查询列表
function getuserinvoice_sigle(){
	var model = {"is_use_all" : 'N'};
	$("#userinvoicelist").datagrid({
		url : 'getUserInvoiceList.action',
		dataType : 'json',
		queryParams : model,
		toolbar:toolbar1,
		rownumbers : true,
		columns : [ [ {align : 'center',field : 'user_name',title : '收费员',width : 10},
		              {align : 'center',field : 'invoice_num_min',title : '最小发票号',width : 15}, 
		              {align : 'center',field : 'invoice_num_max',title : '最大发票号',width : 15}, 
		              {align : 'center',field : 'invoice_num_used',title : '已用最大发票',width : 15}, 
		              {align : 'center',field : 'invoice_classs',title : '发票类型',width : 10}, 
		              {align : 'center',field : 'is_active',title : '发票类型启/停',width : 10, "formatter":f_showactive,"styler":f_color},
		              {align : 'center',field : 'creater',title : '操作人',width : 10},
		              {align : 'center',field : 'create_time',title : '操作时间',width : 15}
		          ] ],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
		},
		collapsible : true,
		pagination : false,
		fitColumns : true,
		striped : true,
		fit : true,
		checkOnSelect : false
	});
}
/**
 * 定义工具栏
 */
var toolbar1=[{
		text:'维护个人发票号段',
		width:150,
		height:30,
		iconCls:'icon-add',
	    handler:function(){
	    	$("#dlg-edit").dialog({
	    		title:'个人发票号段维护',
	    		href:'getUserInvoiceEditPage.action'
	    	});
	    	$('#dlg-edit').dialog('open');
	    }
	},{
		text:'启用发票类型',
		width:150,
		height:30,
		iconCls:'icon-check',
	    handler:function(){
	    	$("#dlg-fplx").dialog("open");
	    	$("#dlg-fplx").dialog("center");
	    }
	},{
		text:'启用共用发票号段模式',
		width:180,
		height:30,
		iconCls:'icon-check',
		handler:function(){
			$.messager.confirm('提示信息','确定要启用共用发票号段模式吗？',function(r){
				if(r){
					useing_all('Y');
				}
			});
		}
	}];
//启用共用发票号段或个人发票号段
function useing_all(is_use_all){
	$.ajax({
		url : 'saveUserInvoiceUseAll.action',
		data : {is_use_all:is_use_all},
		type : "post",//数据发送方式   
		success : function(text) {
			$.messager.alert("操作提示", text);
			 location.reload();
		},
		error : function() {
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}

function save_fplx(){
	$.ajax({
		url : 'saveUserInvoiceClass.action',
		data : {invoice_class:$('#fplx_q').combobox('getValue')},
		type : "post",//数据发送方式   
		success : function(text) {
			$.messager.alert("操作提示", text);
			$("#dlg-fplx").dialog("close");
			$("#userinvoicelist").datagrid('reload');
		},
		error : function() {
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}