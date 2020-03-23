$(document).ready(function () {
	$('#serect_creater').combobox({
		url : 'getCrmUserCenterList.action',
		editable : true, //不可编辑状态
		cache : false,
		height:26,
		width:180,
		panelHeight : 'auto',//自动高度适合
		valueField : 'userid',
		textField : 'name',
		onLoadSuccess : function(data) {
		}
	});
	getCountSignBillPlanList();
});

function getCountSignBillPlanList(){
	var model = { "signstartTime":$('#signstartTime').datebox('getValue'),
			 "signendTime":$('#signendTime').datebox('getValue'),"creater":$('#serect_creater').combobox('getValue')};
	$("#sign_bill_span_count_list").datagrid({
		url: 'countSignBillPLanList.action',
		queryParams: model,
		rownumbers:false,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		columns:[[
			{align:"center",field:"creater","title":"creater",hidden:true},
		      {align:"center",field:"name","title":"姓名","width":15},
		      {align:"center",field:"weipaichuzhuangdancount","title":"未排除撞单","width":20},
		      {align:"center",field:"yipaichuzhuangdancount","title":"已排除撞单","width":20},
		      {align:"center",field:"kaishigenzongcount","title":"开始跟踪","width":15},
              {align:"center",field:"zhizuofangancount","title":"制作方案","width":15},
     		  {align:'center',field:"shengchenghetongcount","title":"生成合同","width":20},
     		  {align:"center",field:"yibeidancount","title":"已备单","width":15},
     		 {align:"center",field:"tijianbinghuikuancount","title":"体检并回款","width":20},
     		  {align:"center",field:"gusuanzongrenshu","title":"估算总人数","width":20},
     		 {align:"center",field:"gusuanzongjine","title":"估算总金额","width":20},
     		{align:"center",field:"counts","title":"总签单","width":10},
     		  {align:"center",field:"f_ck","title":"查看表","width":10,"formatter":f_ck}
     		  ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: true,
		fitColumns:true,
		striped:true,
	});
}

function f_ck(value,row,index){
	var str = '<a href=\"javascript:f__sign(\''+row.creater+'\')\">表</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:f__signtb(\''+row.creater+'\')\">图</a>';
	return str;	
}
function f__sign(creater){
	$('#dlg-edit').dialog({    
	    title: '查看统计记录', 
	    href: 'countSignBillPlanDetailPage.action?creater='+creater,
	    width: 1000,    
	    height: 500, 
	    closed: true,
	    cache: false,
	    modal:true
	});
	$('#dlg-edit').dialog('open');
}
function f__signtb(creater){
	$('#dlg-edit').dialog({    
	    title: '查看统计记录', 
	    href: 'tuSignBillPlanDetailPage.action?creater='+creater,
	    width: 1000,    
	    height: 500, 
	    closed: true,
	    cache: false,
	    modal:true
	});
	$('#dlg-edit').dialog('open');
}


