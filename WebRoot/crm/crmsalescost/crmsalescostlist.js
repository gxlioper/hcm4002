$(document).ready(function () {
	 $('#addcost_type').combobox({
		 	data:[{
		 		id:'1',value:'借款类型'
		 	},{
		 		id:'2',value:'还款类型'
		 	}],
		    valueField:'id',    
		    textField:'value',
		    prompt:'请选择费用类型'
	 }
			 )
	$('#addbatch_num').combobox({
		valueField: 'sign_num',
        textField: 'sign_name',
        hasDownArrow:true,
        mode:'remote',
        url:'getSignBillPlanByName.action'
	})
	 $('#addsales_id').combobox({
			url : 'getCrmUserCenterList.action',
			editable : false, //不可编辑状态
			cache : false,
			height:26,
			width:100,
			panelHeight : 'auto',//自动高度适合
			valueField : 'userid',
			textField : 'name',
			prompt:'请选择销售员',
			onLoadSuccess : function(data) {
			}
	 }
			 )
		getCrmSalesCostList();
	
});
function empty(){
	 $('#addbatch_num').combobox('setValue',''),
	 $('#addsales_id').combobox('setValue',''),
	$('#start_date').datebox('setValue',''),
	 $('#end_date').datebox('setValue',''),
	 $('#addcost_type').combobox('setValue','')
	 getCrmSalesCostList();
}



/**
 * 显示健康计划表
 */
function getCrmSalesCostList(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $("#CrmSalesCostshow").datagrid({
		 url:'getCrmSalesCostList.action',
		 queryParams:{
			 batch_id:$('#addbatch_num').combobox('getValue'),
			 sales_id:$('#addsales_id').combobox('getValue'),
			start_date: $('#start_date').datebox('getValue'),
			 end_date:$('#end_date').datebox('getValue'),
			 cost_type:$('#addcost_type').combobox('getValue')
		 },
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
			 	{align:'center',field:'id',title:'id',hidden:true},
		        {align:'center',field:'sales_id',title:'销售员id',width:15,hidden:true},
		        {align:'center',field:'username',title:'业务员',width:15},
		        {align:'center',field:'cost_amount',title:'金额',width:15},
		        {align:'center',field:'cost_date',title:'日期'},
	            {align:'center',field:'cost_type',title:'费用类型',width:28},
	            {align:'center',field:'cost_type_code',title:'费用类型编码',width:28,hidden:true},
			 	{align:'center',field:'batch_num',title:'签单计划编码',width:28,hidden:true},
			 	{align:'center',field:'batch',title:'签单计划',width:28},
			 	{align:'center',field:'payment_type',title:'报销款项类型',width:15},
				{align:'center',field:'remark',title:'备注',width:35} 
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		var jiekuan = 0.0;
	    		var huankuan=0.0;
		    	$.each(value.rows, function(index, item){
		    		if(item.cost_type_code=='1'){
		    			jiekuan+= item.cost_amount;
		    		}else if(item.cost_type_code=='2'){
		    			huankuan+= item.cost_amount;
		    		}
		    	});
		    	$("#jiekuan").html(decimal(jiekuan,2));
		    	$("#huankuan").html(decimal(huankuan,2));
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
    	    fitColumns:true,//自适应
	    	//singleSelect:true,
		    //collapsible:false,
			pagination:true,//分页控件
		    striped:true
		    
	});
}

function deluserrow(ids){
	$.messager.confirm('提示信息','是否确定删除选中的内容',function(r){
	 	if(r){
	 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
				 url : 'deleteCrmSalesCost.action',
				data : {ids:ids},
				type : "post",
				success : function(data) {
						$.messager.alert("操作提示",data);
						$('#dlg-edit').dialog('close')
						getCrmSalesCostList();
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
					$(".loading_div").remove();
				}
			 });
	 	 }
	 });
}
function xinzengjiankuan(){
	 $("#dlg-edit").dialog({
			title : '新增借款记录',
			width : 800,
			height :350,
			href : 'addCrmSalesCostPage.action?cost_types='+'1'
		});
		$("#dlg-edit").dialog('open');
}
function xinzenghuankuan(){
	 $("#dlg-edit").dialog({
			title : '新增还款记录',
			width : 800,
			height :350,
			href : 'addCrmSalesCostPage.action?cost_types='+'2'
		});
		$("#dlg-edit").dialog('open');
}
function bianji(){
	var row=$('#CrmSalesCostshow').datagrid('getSelected');
	if(row!=null){
		$("#dlg-edit").dialog({
			title : '编辑健康计划',
			width : 800,
			height :350,
			href : 'getUpdateCrmSalesCostPage.action?code='+row.id+'&batch_names='+row.username+'&costs='+row.cost_type
		});
		$("#dlg-edit").dialog('open');
	}else{
		$.messager.alert("操作提示","请选择需要编辑的内容");
	}
}
function shanchu(){
	var str='';
	var row = $('#CrmSalesCostshow').datagrid('getSelected');
	if(row!=null){
		str=row.id;
    deluserrow(str);
	}else{
		$.messager.alert("操作提示","请选择需要删除的内容");
	}
}



