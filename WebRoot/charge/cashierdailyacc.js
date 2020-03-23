$(document).ready(function () {
	getdailyacclist();
});

//确定日结保存入库
function saveCashierDaily(){
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveCashierDailyaccCharge.action',
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			if(data.split("-")[0] == 'ok'){
				$.messager.alert("操作提示", data, "info");
			}else{
				$.messager.alert("操作提示", data, "error");
			}
			chaxun();
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
	text:'生成日结单',
	width:150,
	height:30,
	iconCls:'icon-save',
    handler:function(){
    	$.messager.confirm('确认','您确认要生成日结单吗？',function(r){    
    	    if (r){    
    	    	saveCashierDaily();    
    	    }    
    	});
    }
},{
	text:'打印日结单',
	width:150,
	height:30,
	iconCls:'icon-check',
	handler:function(){
	    var obj = $("#dailyacclist").datagrid('getSelected');
	    if(obj == null || obj == undefined){
	    	$.messager.alert("操作提示", '请选择需要打印的日结单!',"error");
	    	return;
	    }
	    var exeurl="invoiceService://"+$("#user_ids").val()+"$2$"+obj.daily_acc_num;
	    location.href=exeurl;
	}
}];
//获取已日结总记录列表
function getdailyacclist(){
	var model = {
			"daily_acc_date1":$("#daily_acc_date1").datebox('getValue'),
			"daily_acc_date2":$("#daily_acc_date2").datebox('getValue')
	}
	$("#dailyacclist").datagrid({
		 url:'getCashierDailyAccCharge.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'daily_acc_num',title:'日结号',width:20},
		    {align:'center',field:'daily_acc_amount',title:'总金额(元)',width:15},
		    {align:'center',field:'invoice_amount',title:'发票总金额(元)',width:20},
		    {align:'center',field:'data_name',title:'收费方式',width:15},
		    {align:'center',field:'amount',title:'收费方式金额(元)',width:10},
		 	{align:'center',field:'user_name',title:'日结人',width:10},
		    {align:'center',field:'daily_acc_date',title:'日结时间',width:20},
		    {align:'center',field:'daily_statuss',title:'财务日结',width:10},
		 	{align:'center',field:'sfmx',title:'查看',width:15,"formatter":f_cfmx},
		 	{align:'center',field:'fpmx',title:'查看',width:15,"formatter":f_fpmx}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		MergeCells('dailyacclist', 'daily_acc_num,daily_acc_num,daily_acc_amount,invoice_amount,user_name,daily_acc_date,daily_statuss,sfmx,fpmx',0);
	    	},
	    	onDblClickRow : function(index, row) {
	    		
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
//查看收费明细
function f_cfmx(val,row){
	return '<a href=\"javascript:f_cksfmx(\''+row.daily_acc_num+'\')\">收费明细</a>';
}
//查看发票明细
function f_fpmx(val,row){
	return '<a href=\"javascript:f_ckfpmx(\''+row.daily_acc_num+'\')\">发票明细</a>';
}

function f_cksfmx(daily_acc_num){
	getcashierclasslist(daily_acc_num);
	
	$("#dlg-sfmx").dialog('open');
	$("#dlg-sfmx").dialog('center');
}

function f_ckfpmx(daily_acc_num){
	getcashierinvoice(daily_acc_num);
	$("#dlg-fpmx").dialog('open');
	$("#dlg-fpmx").dialog('center');
}

function getcashierclasslist(daily_acc_num){
	var model = {'daily_acc_num':daily_acc_num};
	$("#dailyacclistclass").datagrid({
		url: 'getCashierDailyAccPaywayCharge.action',
		queryParams: model,
		rownumbers:true,
		columns:[[
		          {align:"center",field:"daily_acc_class_num","title":"流水号","width":15},
		          {align:"center",field:"daily_acc_classs","title":"收费类型","width":15},
		          {align:"center",field:"tran_codes","title":"交易类型","width":10},
		          {align:"center",field:"daily_acc_class_amount","title":"金额(元)","width":10},
		          {align:'center',field:'data_name',title:'收费方式',width:15},
				  {align:'center',field:'amount',title:'收费方式金额(元)',width:10}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	MergeCells('dailyacclistclass', 'daily_acc_class_num,daily_acc_class_num,daily_acc_classs,tran_codes,daily_acc_class_amount',0);
	    	var row = $("#dailyacclistclass").datagrid('getRows');
	    	getcashierclasslistmx(row[0].daily_acc_class_num,row[0].daily_acc_class);
	    },
	    onDblClickRow : function(index, row) {
	    	getcashierclasslistmx(row.daily_acc_class_num,row.daily_acc_class);
		},
	    singleSelect:true,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false
	});
}
//根据收费类型查询收费明细
function getcashierclasslistmx(daily_acc_num,daily_acc_class){
	var model = {'daily_acc_num':daily_acc_num,'is_Active':daily_acc_class};
	var column;
	if(daily_acc_class == '001'){
		column = [[
		           {align:"center",field:"req_num","title":"结算号","width":20},
		           {align:"center",field:"amount","title":"金额(元)","width":15},
		           {align:"center",field:"charging_statuss","title":"结算状态","width":15},
		           {align:"center",field:"cash_date","title":"结算时间","width":25},
				   {align:"center",field:"exam_num","title":"体检号","width":20},
				   {align:"center",field:"user_name","title":"姓名","width":15}
		         ]];
	}else if(daily_acc_class == '002'){
		column = [[
		           {align:"center",field:"req_num","title":"结算号","width":20},
		           {align:"center",field:"amount","title":"金额(元)","width":15},
		           {align:"center",field:"charging_statuss","title":"结算状态","width":15},
		           {align:"center",field:"cash_date","title":"结算时间","width":25},
				   {align:"center",field:"com_name","title":"单位","width":25},
				   {align:"center",field:"batch_name","title":"批次任务","width":15}
		         ]];
	}else if(daily_acc_class == '003'){
		column = [[
		           {align:"center",field:"req_num","title":"结算号","width":20},
		           {align:"center",field:"amount","title":"金额(元)","width":15},
		           {align:"center",field:"charging_statuss","title":"结算状态","width":15},
		           {align:"center",field:"cash_date","title":"结算时间","width":25},
				   {align:"center",field:"exam_num","title":"会员卡号","width":25}
		         ]];
	}else if(daily_acc_class == '004'){
		column = [[
		           {align:"center",field:"req_num","title":"结算号","width":20},
		           {align:"center",field:"amount","title":"金额(元)","width":15},
		           {align:"center",field:"charging_statuss","title":"结算状态","width":15},
		           {align:"center",field:"cash_date","title":"结算时间","width":25},
				   {align:"center",field:"com_name","title":"单位名称","width":25}
		         ]];
	}
	
	$("#dailyacclistclassmx").datagrid({
		url: 'getCashierDailyAccDetailCharge.action',
		queryParams: model,
		rownumbers:true,
		columns:column,
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

//获取日结发票信息列表
function getcashierinvoice(num){
	var model = {'daily_acc_num':num};
	$("#dailyaccinvoicelist").datagrid({
		url: 'getCashierDailyAccInvoiceCharge.action',
		queryParams: model,
		rownumbers:true,
		columns:[[
		          {align:"center",field:"invoice_num","title":"发票号","width":20},
		          {align:"center",field:"title_info","title":"发票抬头","width":30},
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

function chaxun(){
	$('#dailyacclist').datagrid('load',{"daily_acc_date1":$("#daily_acc_date1").datebox('getValue'),"daily_acc_date2":$("#daily_acc_date2").datebox('getValue')});
}

