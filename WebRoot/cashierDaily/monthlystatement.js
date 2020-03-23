$(document).ready(function () {
	getfinacclist();
	gethuizongjineListGrid();
	
	var myDate= new Date();
	$('#month').combobox('setValue',myDate.getMonth()+1);
});

window.onload=function(){
	//设置年份的选择
	var myDate= new Date();
	var startYear=myDate.getFullYear()-50;//起始年份
	var endYear=myDate.getFullYear()+50;//结束年份
	var data = new Array();
	for (var i=startYear;i<=endYear;i++){
		data.push({'id':i,'name':i+'年'});
	}
	$('#year').combobox({    
	    data:data,    
	    valueField:'id',    
	    textField:'name'
	});
	$('#year').combobox('setValue',myDate.getFullYear());
} 
/**
 * 定义工具栏
 */
var toolbar=[{
		text:'打印月结单',
		width:150,
		height:30,
		iconCls:'icon-check',
	    handler:function(){
	    	var obj = $("#finacclist").datagrid('getRows');
	    	if(obj.length == 0){
	    		$.messager.alert("操作提示", '本月没有财务日结数据,不需要打印月结单!',"error");
	    		return;
	    	}
	    	var start_date = '',end_date = '';
	    	if($("input[name=datetype]:checked").eq(0).val() == 1){
	    		start_date = $("#start_date").datebox('getValue');
	    		end_date = $("#end_date").datebox('getValue');
	    	}else{
	    		var year = $('#year').combobox('getValue');
	    		var month = $('#month').combobox('getValue');
	    		var day = days(year,month);
	    		start_date = year + '-' + month + '-01';
	    		end_date = year + '-' + month + '-' + day;
	    	}
	    	var exeurl="invoiceService://"+$("#user_ids").val()+"$4$"+start_date+"$"+end_date;
	    	location.href=exeurl;
	    }
	}];
//获取已日结总记录列表
function getfinacclist(){
	var model = {
			"start_date":$("#start_date").datebox('getValue'),
			"end_date":$("#end_date").datebox('getValue')
	}
	$("#dailylist").datagrid({
		 url:'getFinanceDepAccList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'fd_acc_num',title:'财务日结号',width:20},
		    {align:'center',field:'fd_acc_amount',title:'总金额(元)',width:15},
		    {align:"center",field:"invoice_amount","title":"发票总金额(元)","width":20},
		 	{align:'center',field:'user_name',title:'日结人',width:10},
		    {align:'center',field:'fd_acc_date',title:'日结时间',width:20}
		 	
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
			rownumbers:false,
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
			fit:true,
		    fitColumns:true,
		    striped:true
	});
}

function gethuizongjineListGrid(){
	var model = {
			"start_date":$("#start_date").datebox('getValue'),
			"end_date":$("#end_date").datebox('getValue')
	}
	$("#finacclist").datagrid({
		url: 'getMonthlyStatementSum.action',
		queryParams: model,
		rownumbers:false,
		columns:[[
		          {align:"center",field:"fd_acc_amount","title":"总金额(元)","width":15},
		          {align:"center",field:"invoice_amount","title":"发票总金额(元)","width":20},
		          {align:"center",field:"data_name","title":"收费方式","width":15},
		          {align:"center",field:"way_amount","title":"金额(元)","width":10}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	MergeCells('finacclist', 'fd_acc_amount,ck,fd_acc_amount,invoice_amount',0);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false,
		toolbar:toolbar
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

function days(year,month){
    var dayCount;
    now = new Date(year,month, 0);
    dayCount = now.getDate();
    return dayCount;
}

function chaxun(){
	var start_date = '',end_date = '';
	if($("input[name=datetype]:checked").eq(0).val() == 1){
		start_date = $("#start_date").datebox('getValue');
		end_date = $("#end_date").datebox('getValue');
	}else{
		var year = $('#year').combobox('getValue');
		var month = $('#month').combobox('getValue');
		var day = days(year,month);
		start_date = year + '-' + month + '-01';
		end_date = year + '-' + month + '-' + day;
	}
	
	var model = {
			"start_date":start_date,
			"end_date":end_date
	}
	$("#finacclist").datagrid('load',model);
	$("#dailylist").datagrid('load',model);
}