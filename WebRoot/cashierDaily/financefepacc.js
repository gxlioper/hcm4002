$(document).ready(function () {
	getcyitemListGrid();
	gethuizongjineListGrid();
	getwfinaccinvoice(0,'');
	getfinacclist();
	getfinaccpayway('');
	getdailyListGrid('')
	getyfinaccinvoice(1,'');
	
	$('#tt').tabs({    
	    onSelect:function(title,index){    
	    	chaxun(index);    
	    }    
	});
});

function getcyitemListGrid(){
	var model = {};
	$("#witemlist").datagrid({
		url: 'getCashierDailyAccAll.action',
		queryParams: model,
		rownumbers:true,
		columns:[[
//		          {field:'ck',checkbox:true},
		          {align:"center",field:"daily_acc_num","title":"日结号","width":15},
		          {align:"center",field:"daily_acc_amount","title":"总金额(元)","width":15},
		          {align:"center",field:"invoice_amount","title":"发票总金额(元)","width":20},
		          {align:'center',field:"user_name","title":"收费员","width":10},
		          {align:"center",field:"daily_acc_date","title":"日结时间","width":20},
		          {align:"center",field:"data_name","title":"收费方式","width":15},
		          {align:"center",field:"amount","title":"金额(元)","width":10}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	MergeCells('witemlist', 'daily_acc_num,ck,daily_acc_num,daily_acc_amount,invoice_amount,user_name,daily_acc_date',0);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false
		//checkOnSelect:false
	});
}

function gethuizongjineListGrid(){
	var model = {};
	$("#huizongjine").datagrid({
		url: 'getCashierDailyAccSum.action',
		queryParams: model,
		rownumbers:true,
		columns:[[
//		          {field:'ck',checkbox:true},
		          {align:"center",field:"fd_acc_amount","title":"总金额(元)","width":15},
		          {align:"center",field:"invoice_amount","title":"发票总金额(元)","width":20},
		          {align:"center",field:"data_name","title":"收费方式","width":15},
		          {align:"center",field:"way_amount","title":"金额(元)","width":10}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	MergeCells('huizongjine', 'fd_acc_amount,ck,fd_acc_amount,invoice_amount',0);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false,
		toolbar:toolbar
		//checkOnSelect:false
	});
}

/**
 * 定义工具栏
 */
var toolbar=[{
		text:'确定日结',
		width:150,
		height:30,
		iconCls:'icon-check',
	    handler:function(){
	    	$.messager.confirm('确认','您确认日结吗？',function(r){    
	    	    if (r){    
	    	    	saveFinanceDepAcc();    
	    	    }    
	    	});
	    }
	}];

function saveFinanceDepAcc(){
	var obj = $("#huizongjine").datagrid('getRows');
	var amount = 0;
	var payway = new Array();
	for(i=0;i<obj.length;i++){
		amount = obj[i].fd_acc_amount;
		payway.push({'charging_way_id':obj[i].charging_way,'amount':obj[i].way_amount});
	}
	
	var daily = $("#witemlist").datagrid('getRows');
	var dailylist = new Array();
	if(daily.length <= 0){
		$.messager.alert("操作提示", "无收费员日结明细，不需要做日结!", "error");
		return;
	}
	var daily_acc_num = '';
	for(i=0;i<daily.length;i++){
		if(daily_acc_num != daily[i].daily_acc_num){
			dailylist.push({'daily_acc_num':daily[i].daily_acc_num});
			daily_acc_num = daily[i].daily_acc_num;
		}
	}
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveFinanceDepAcc.action',
		type:'post',
		data:{
			'fd_acc_amount' : amount,
			'financePayways': JSON.stringify(payway),
			'financecCashiers': JSON.stringify(dailylist)
		},
		success:function(data){
			$(".loading_div").remove();
			$.messager.alert("操作提示", data, "info");
			chaxun(0);
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
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
/**
 * 定义工具栏
 */
var toolbar1=[{
		text:'打印日结单',
		width:150,
		height:30,
		iconCls:'icon-check',
	    handler:function(){
	    	var obj = $("#finacclist").datagrid('getSelected');
	    	if(obj == null || obj == undefined){
	    		$.messager.alert("操作提示", '请选择需要打印的日结单!',"error");
	    		return;
	    	}
	    	var exeurl="invoiceService://"+$("#user_ids").val()+"$3$"+obj.fd_acc_num;
	    	location.href=exeurl;
	    }
	}];
//获取已日结总记录列表
function getfinacclist(){
	var model = {
			"start_date":$("#start_date").datebox('getValue'),
			"end_date":$("#end_date").datebox('getValue')
	}
	$("#finacclist").datagrid({
		 url:'getFinanceDepAccList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'fd_acc_num',title:'日结号',width:20},
		    {align:'center',field:'fd_acc_amount',title:'总金额(元)',width:15},
		    {align:"center",field:"invoice_amount","title":"发票总金额(元)","width":20},
		 	{align:'center',field:'user_name',title:'日结人',width:10},
		    {align:'center',field:'fd_acc_date',title:'日结时间',width:20}
		 	
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
	    	onDblClickRow : function(index, row) {
	    		$('#waylist').datagrid('load',{'fd_acc_num':row.fd_acc_num});
	    		$('#dailylist').datagrid('load',{'fd_acc_num':row.fd_acc_num});
	    		$('#yinvoicelist').datagrid('load',{'daily_status':1,'fd_acc_num':row.fd_acc_num});
			},
			rownumbers:false,
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
			fit:true,
		    fitColumns:true,
		    striped:true,
		    toolbar:toolbar1
	});
}
//获取日结收费方式列表
function getfinaccpayway(num){
	var model = {'fd_acc_num':num};
	$("#waylist").datagrid({
		url: 'getFinanceDepAccPaywayList.action',
		queryParams: model,
		rownumbers:true,
		columns:[[
		          {align:"center",field:"charging_way","title":"收费方式","width":20},
		          {align:"center",field:"amount","title":"金额(元)","width":15}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false
	});
}

function getdailyListGrid(num){
	var model = {'fd_acc_num':num};
	$("#dailylist").datagrid({
		url: 'getFinanceVsCashierDailyAccList.action',
		queryParams: model,
		rownumbers:true,
		columns:[[
//		          {field:'ck',checkbox:true},
		          {align:"center",field:"daily_acc_num","title":"日结号","width":15},
		          {align:"center",field:"daily_acc_amount","title":"总金额(元)","width":15},
		          {align:"center",field:"invoice_amount","title":"发票总金额(元)","width":20},
		          {align:'center',field:"user_name","title":"收费员","width":10},
		          {align:"center",field:"daily_acc_date","title":"日结时间","width":20}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false
		//checkOnSelect:false
	});
}

//获取未财务日结发票信息列表
function getwfinaccinvoice(daily_status,num){
	var model = {'daily_status':daily_status,'fd_acc_num':num};
	$("#winvoicelist").datagrid({
		url: 'getFinanceVsCashierInvoiceList.action',
		queryParams: model,
		rownumbers:true,
		columns:[[
		          {align:"center",field:"invoice_num","title":"发票号","width":20},
		          {align:"center",field:"charging_way","title":"发票类型","width":15},
		          {align:"center",field:"invoice_statuss","title":"发票状态","width":15},
		          {align:"center",field:"invoice_amount","title":"金额(元)","width":15}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false
	});
}
//获取已财务日结发票信息列表
function getyfinaccinvoice(daily_status,num){
	var model = {'daily_status':daily_status,'fd_acc_num':num};
	$("#yinvoicelist").datagrid({
		url: 'getFinanceVsCashierInvoiceList.action',
		queryParams: model,
		rownumbers:true,
		columns:[[
		          {align:"center",field:"invoice_num","title":"发票号","width":20},
		          {align:"center",field:"charging_way","title":"发票类型","width":15},
		          {align:"center",field:"invoice_statuss","title":"发票状态","width":15},
		          {align:"center",field:"invoice_amount","title":"金额(元)","width":15}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false
	});
}

function chaxun(index){
	if(index == 1){
		$('#finacclist').datagrid('load',{"start_date":$("#start_date").datebox('getValue'),"end_date":$("#end_date").datebox('getValue')});
		$('#waylist').datagrid('load',{'fd_acc_num':''});
		$('#dailylist').datagrid('load',{'fd_acc_num':''});
		$('#yinvoicelist').datagrid('load',{'daily_status':1,'fd_acc_num':''});
	}else if(index == 0){
		$("#witemlist").datagrid('reload');
		$("#huizongjine").datagrid('reload');
		$("#winvoicelist").datagrid('reload');
	}
}