$(document).ready(function () {
	$('#user_id').combobox({
		url : 'getCashierList.action',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'chi_Name',
		onLoadSuccess : function(data){
			if(data.length <= 0 || data[0].id != 0){
				data.unshift({'id':0,'chi_Name':'全部'}); 
				$('#user_id').combobox('loadData',data);
			}
		}
	});
	getgcharginglist();
	gettcharginglist();
	getcharginginvoicelist();
	gethuizonglist();
});

function getgcharginglist(){
	var model = {
			"start_date":$("#start_date").datebox('getValue'),
			"end_date":$("#end_date").datebox('getValue'),
			"user_id":0,
			"daily_status":''
	}
	$("#gcharginglist").datagrid({
		 url:'getChargingSummarySingleList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'exam_num',title:'体检号',width:20},
		    {align:'center',field:'user_name',title:'姓名',width:15},
		 	{align:'center',field:'sex',title:'性别',width:10},
		 	{align:'center',field:'age',title:'年龄',width:10},
		 	{align:'center',field:'amount2',title:'总金额(元)',width:15},
		 	{align:'center',field:'charging_statuss',title:'收费状态',width:10},
		 	{align:'center',field:'daily_statuss',title:'日结状态',width:10},
		 	{align:'center',field:'cashier',title:'收费人',width:10},
		 	{align:'center',field:'cash_date',title:'收费时间',width:20},
		    {align:'center',field:'charging_way',title:'收费方式',width:15},
		    {align:'center',field:'way_amount',title:'金额(元)',width:15},
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		MergeCells('gcharginglist', 'id,exam_num,user_name,sex,age,amount2,charging_statuss,daily_statuss,cashier,cash_date',0);
	    	},
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
			fit:true,
		    fitColumns:true,
		    striped:true
	});
}

function gettcharginglist(){
	var model = {
			"start_date":$("#start_date").datebox('getValue'),
			"end_date":$("#end_date").datebox('getValue'),
			"user_id":0,
			"daily_status":''
	}
	$("#tcharginglist").datagrid({
		 url:'getChargingSummaryGroupList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'com_name',title:'单位名称',width:20},
		    {align:'center',field:'batch_name',title:'批次任务',width:15},
		 	{align:'center',field:'amount2',title:'总金额(元)',width:15},
		 	{align:'center',field:'charging_statuss',title:'收费状态',width:10},
		 	{align:'center',field:'daily_statuss',title:'日结状态',width:10},
		 	{align:'center',field:'cashier',title:'收费人',width:10},
		 	{align:'center',field:'cash_date',title:'收费时间',width:20},
		    {align:'center',field:'charging_way',title:'收费方式',width:15},
		    {align:'center',field:'way_amount',title:'金额(元)',width:15},
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		MergeCells('tcharginglist', 'id,com_name,batch_name,amount2,charging_statuss,daily_statuss,cashier,cash_date',0);
	    	},
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
			fit:true,
		    fitColumns:true,
		    striped:true
	});
}

function getcharginginvoicelist(){
	var model = {
			"start_date":$("#start_date").datebox('getValue'),
			"end_date":$("#end_date").datebox('getValue'),
			"user_id":0,
			"daily_status":''
	}
	$("#invoicelist").datagrid({
		 url:'getChargingInvoiceList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'charging_way',title:'发票类型',width:15},
		    {align:'center',field:'invoice_num',title:'发票号',width:20},
		 	{align:'center',field:'invoice_amount',title:'金额(元)',width:15},
		 	{align:'center',field:'invoice_statuss',title:'发票状态',width:10},
		 	{align:'center',field:'daily_statuss',title:'日结状态',width:10},
		 	{align:'center',field:'user_name',title:'开票人',width:10},
		 	{align:'center',field:'invoice_time',title:'开票时间',width:20}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
			fit:true,
		    fitColumns:true,
		    striped:true
	});
}

function gethuizonglist(){
	var model = {
			"start_date":$("#start_date").datebox('getValue'),
			"end_date":$("#end_date").datebox('getValue'),
			"user_id":0,
			"daily_status":''
	}
	$("#huizonglist").datagrid({
		 url:'getChargingSummarySum.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'fd_acc_amount',title:'总金额(元)',width:15},
		    {align:'center',field:'invoice_amount',title:'发票总金额(元)',width:20},
		    {align:'center',field:'user_name',title:'收费员',width:10},
		 	{align:'center',field:'data_name',title:'收费方式',width:15},
		 	{align:'center',field:'way_amount',title:'金额(元)',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		MergeCells('huizonglist', 'userId,fd_acc_amount,invoice_amount,user_name',0);
	    	},
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
			fit:true,
		    fitColumns:true,
		    striped:true
	});
}


function chaxun(){
	var model = {"start_date":$("#start_date").datebox('getValue'),
			"end_date":$("#end_date").datebox('getValue'),
			"user_id":$("#user_id").combobox('getValue'),
			"daily_status":$("#daily_status").combobox('getValue')};
	$("#gcharginglist").datagrid('reload',model);
	$("#tcharginglist").datagrid('reload',model);
	$("#invoicelist").datagrid('reload',model);
	$("#huizonglist").datagrid('reload',model);
}

/**
2         * EasyUI DataGrid根据字段动态合并单元格
3         * @param fldList 要合并table的id
4         * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
5         */
function MergeCells(tableID, fldList,zhuIndex) {
	var Arr = fldList.split(",");
	var dg = $('#' + tableID);
	var fldName;
	var RowCount = dg.datagrid("getRows").length;
	var span;
	var PerValue = "";
	var CurValue = "";
	var length = Arr.length - 1;
	for (i = length; i >= 1; i--) {
		z_fldName = Arr[zhuIndex];
		fldName = Arr[i];
		PerValue = "";
		span = 1;
		for (row = 0; row <= RowCount; row++) {
			if (row == RowCount) {
				CurValue = "";
			}else {
				CurValue = dg.datagrid("getRows")[row][z_fldName];
			}
			if (PerValue == CurValue) {
				span += 1;
			}else {
				var index = row - span;
				dg.datagrid('mergeCells', {
					index: index,
					field: fldName,
					rowspan: span,
					colspan: null
				});
				span = 1;
				PerValue = CurValue;
			}
		}
	}
}