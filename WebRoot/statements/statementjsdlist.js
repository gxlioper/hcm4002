$(document).ready(function () {
	getTeamAccountForBatchlist();
});

function getTeamAccountForBatchlist(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
		 var model={"company_id":$("#company_id").val(),"batchid":$("#batchid").val()};
	     $("#statementslistshow").datagrid({
		 url:'statementjsdlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 10,//每页显示的记录条数，默认为10 
	     pageList:[10,20,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
           {field:'checkss',checkbox:true },
		    {align:'center',field:'acc_num',title:'结算单号',width:15,"formatter":f_teamaccountone},
		 	{align:'center',field:'contract_num',title:'合同号',width:20},
		 	{align:'center',field:'acc_stautsss',title:'结算状态',width:10,"formatter":f_jszt},	
		 	{align:'center',field:'totalexam',title:'人次',width:10},
		 	{align:'right',field:'prices',title:'应收金额 ',width:10},
		 	{align:'right',field:'trueamt',title:'实收金额 ',width:10},
		 	{align:'right',field:'dec_charges',title:'优惠金额',width:10},
		 	{align:'right',field:'additional',title:'附加费用',width:10,"formatter":f_fjfy},
		 	{align:'center',field:'balance_statusss',title:'结账状态',width:10,"formatter":f_jzzt},
		 	{align:'center',field:'acc_date',title:'结算日期',width:20},
		 	{align:'center',field:'balance_date',title:'结账日期',width:20}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
	});
	}

function savelist(){
	var ids = "";
	var checkedItems = $('#statementslistshow').datagrid('getChecked');
	var amount = 0;
    $.each(checkedItems, function(index, item){
        ids=ids+","+(item.acc_num);
        amount = amount + Number(item.additional);
    });
    if(!$("#is_team_add")[0].checked){
    	amount = 0;
    }
    
    if(ids.split(',').length<=1){
		$.messager.alert("操作提示", "请选择要结算单","error");
	}else{
		if(ids.length>1) ids=ids.substring(1,ids.length);
		 $.ajax({
				url : 'termaccountgroupadd.action',
				data : {
					batchid:$("#batchid").val(),
					ids:ids,
					add_amount:amount
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								getTeamAccountinvoidForBatch();
								$('#dlg-custedit').dialog('close');
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	}
}

