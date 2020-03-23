$(document).ready(function () { 
	if($("#rechargeResources").val() == 'Y'){
		$("#chongzhi").show();
	}else{
		$("#chongzhi").hide();
	}
	if($("#frozenResources").val() == 'Y'){
		$("#dongjie").show();
		$("#jiedong").show();
	}else{
		$("#dongjie").hide();
		$("#jiedong").hide();
	}
	
	reopen();
});
$(function(){
	$("#invoice_num,#invoice_name,#invoice_type").validatebox({
		required: true
	});
});
//加载单位信息
function reopen(){
	var url = 'companychangshow.action?remark=Y';
	var data = {"name":$("#com_name").val()};
	load_post(url,data,showcomtree);
}

/**
 * 显示树形结构
 * @param data
 * @returns
 */
function showcomtree(data){
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"单位","javascript:void(0)","根目录","_self",false);
			var l = data.length;
			for(var index = 0;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:f_getCompanyOne("+data[index].id+",'"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:f_getCompanyOne("+data[index].id+",'"+data[index].text+"')",
							data[index].text,"_self",false);
				}
			}
			$("#depttree").html(mydtree.toString());			
		}

/**
 * 点击树设置内容
 * @param id
 * @param name
 * @returns
 */

var  comName = '';

function f_getCompanyOne(id,comname){
	$("#com_id").val(id);
	comName = comname;
	$.ajax({
			url : 'getMerchantInfoByCom_id.action',
			data : {
				    com_id:id
					},
					type : "post",//数据发送方式   
					success : function(data) {
						if(data == 'null'){
							$.messager.confirm('提示信息','此单位未开户是否现在开户？',function(r){
								if(r){
									saveMerchantInfo(id);
								}else{
									$("#com_num").val("");
									$("#balance").val("");
									$("#com_type").val("");
									$("#creater").val("");
									$("#updater").val("");
									$("#update_date").val("");
									$('#create_date').val("");	
									$("#com_t").val("");
								}
							});
						}else{
							
						var da = eval('('+data+')'); 
						$("#com_num").val(da.com_num);
						$("#balance").val(da.balance);
						$("#com_type").val(da.com_type_s);
						$("#creater").val(da.creater);
						$("#updater").val(da.updater);
						$("#update_date").val(da.update_date);
						$('#create_date').val(da.create_date);	
						$("#com_t").val(da.com_type);
						getTransactionFlow();
						getInvoiceInfoList();
						}
					},
					error : function() {
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
  
}


	
function  saveMerchantInfo(id){
	$.ajax({
		url : 'saveMerchantInfo.action',
		data : {
			    com_id:id
				},
				type : "post",//数据发送方式   
				success : function(data) {
					var mes = data.split("-");
					if(mes[0] == 'ok'){
						$.messager.alert("操作提示", mes[1], "");	
						f_getCompanyOne(id,comName);
					}
				},
				error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
}
	
function  recharge(){
	if($("#com_t").val() != '0'){
			$.messager.alert("操作提示", "正常状态下的商户才能进行充值操作。。", "info");
			return;
		}
	
	$("#amount").numberbox('setValue','');
	var com_num = $("#com_num").val();
	if(com_num.length <= 0){
		$.messager.alert("操作提示", "请先选择单位!", "info");	
		return ;
	}
	$("#dlg-chongzhi").dialog('open');
	var model = {"com_Type":'SFFSLX'};
		$("#getChargingMethod").datagrid({
		url: 'getDatadis.action',
		queryParams: model,
		rownumbers:true,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		sortName: 'cdate',
		sortOrder: 'desc',
		columns:[[{align:'center',field:'sf_ck_t',checkbox:true},
				  {align:"center",field:"id","title":"ID","width":10},
		          {align:"center",field:"name","title":"收费方式","width":10}
		     	]],
	    onLoadSuccess:function(value){
	    	$("#getChargingMethod").datagrid("hideColumn", "id");
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		toolbar:"#toolbar_t",
	    onSelect:function(index,row){
	    	
	    }
	});
	
}
  //充值商户
  function saveRecharge(){
  	
  			var com_num = $("#com_num").val();
		  	var amount = $("#amount").numberbox('getValue');
		  	if(amount > 0){
		  	 var data = $("#getChargingMethod").datagrid('getSelections');
		  	 if(data.length <= 0){
		  	 	$.messager.alert("操作提示", "请选择收费方式！！！", "info");	
		  	 	return;
		  	 }else if(data.length == 1){
		  	 	var charging_way_id = data[0].id;
		  	$.messager.confirm('提示信息',"充值后不支持退费，确定给此商户充值吗？",function(r){ 
		  		if(r){
		  			$.ajax({
						url : 'saveRecharge.action',
						data : {
							    com_num:com_num,
							    amount:amount,
							    charging_way_id:charging_way_id
								},
								type : "post",//数据发送方式   
								success : function(data) {
									var mes = data.split("-");
									if(mes[0] == 'ok'){
										$.messager.alert("操作提示", mes[1], "");	
										$("#dlg-chongzhi").dialog('close');
			//							$("#balance").val(mes[2]);
										f_getCompanyOne($("#com_id").val(),'');
										getTransactionFlow();
									}else{
										$.messager.alert("操作提示", mes[1], "error");
									}
								},
								error : function() {
									$.messager.alert("操作提示", "操作错误", "error");					
								}
							});
		  				}
			  	 	
					});
		  	 	
		  	 }else{
		  	 	$.messager.alert("操作提示", "自能选择一种方式收费，请重新选择！！！", "info");	
		  	 }
		  	}else{
		  		$.messager.alert("操作提示", "请输入充值金额！！", "warning");	
		  	}
  	
  }
function getTransactionFlow(){
	var model = {"com_num":$("#com_num").val()};
	$("#transactionFlow").datagrid({
			url: 'getTransactionFlowList.action',
			queryParams: model,
			rownumbers:true,
			pageSize: 15,//每页显示的记录条数，默认为10 
			pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			sortName: 'cdate',
			sortOrder: 'desc',
			toolbar:"#toolbar_s",
			columns:[[{align:'center',field:'sf_ck_f',checkbox:true},
					  {align:"center",field:"id","title":"ID","width":10},
					  {align:"center",field:"chargingwaycode","title":"chargingwaycode","width":10},
					  {align:"center",field:"jnnumber","title":"流水号","width":20},
			          {align:"center",field:"logicdate","title":"发生日期","width":20},
			          {align:"center",field:"balance","title":"商户余额(元)","width":15},
			          {align:"center",field:"chargingway","title":"交易方式","width":10},
			          {align:"center",field:"trantmt","title":"交易金额(元)","width":10},
			          {align:"center",field:"usednum","title":"使用次数","width":10},
			          {align:"center",field:"transactionType","title":"交易类型","width":10},
			          {align:"center",field:"status","title":"流水状态","width":10},
			          {align:"center",field:"creater","title":"操作员","width":15},
			          {align:"center",field:"jndatetime","title":"交易时间","width":20},
			          {align:"center",field:"invaioce_type","title":"开票状态","width":10,"formatter":f_invaioce_type}
			     	]],
		    onLoadSuccess:function(value){
		    	$("#transactionFlow").datagrid("hideColumn", "id");
		    	$("#transactionFlow").datagrid("hideColumn", "chargingwaycode");
		    },
		    singleSelect:false,
		    collapsible:true,
			pagination: false,
			fitColumns:true,
			fit:true,
			striped:true,
			toolbar:toolbar_s,
		    onSelect:function(index,row){
		    	
		    }
	});
}
var toolbar_s = [{
		text:'开发票',
		width:120,
		iconCls:'icon-print',
	    handler:function(){
	    	var amount = 0;
	    	var data = $("#transactionFlow").datagrid('getSelections');
	    	if(data.length < 1){
	    		$.messager.alert("操作提示", "请选择需要开票的流水！！", "warning");	
	    		return ;
	    	}
	    	for(var i = 0 ; i < data.length ; i++){
	    		if(data[i].trancode != '001'){
	    			$.messager.alert("操作提示", "只有充值流水才能开发票,请重新选择。。", "warning");	
	    			return;
	    		}
	    		if(data[i].jnstatus != '1'){
	    			$.messager.alert("操作提示", "只有有效的流水才能开发票,请重新选择。。", "warning");	
	    			return;
	    		}
	    		if(data[i].invaioce_type == '1'){
	    			$.messager.alert("操作提示", "存在已经开过发票的流水,请重新选择。。", "warning");	
	    			return;
	    		}
	    		if($("#invoiceRepeatType").val()=='N'&&(data[i].chargingwaycode == '372'||data[i].chargingwaycode == '122')){
	    			$.messager.alert("操作提示", "使用商户划账或会员卡收费方式无法开发票。。", "warning");	
	    			return;
	    		}
	    		amount += data[i].trantmt;
	    	}
	    	$("#dlg-fapiao").dialog('open');
	    	getMaxInvoiceNum(amount);
	    }
}];

function f_invaioce_type(val,row){
	 if(val == '1'){
		  return '<font color="red">'+'已开'+'</font>';
	 }else if(val == '0'){
		  return '未开';
	  }else{
	  	 return '';
	  }
}


function  getMaxInvoiceNum(amount){
	$.ajax({
			url : 'getMaxInvoiceNum.action',
			data : {},
			type : 'post',
			success:function(data){
				$("#invoice_num").val(data);
				$("#invoice_name").val(comName);
				$("#invoice_type").val(amount);
			},
			error : function() {
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
}

function save_fapiao(){
	var jnnumbers = new Array();
	var data = $("#transactionFlow").datagrid('getSelections');
	for(var i = 0 ; i < data.length ; i++){
		jnnumbers.push("'"+data[i].jnnumber+"'")
	}
	
	if($("#invoice_num").val() == '' ){
		$.messager.alert("操作提示", "获取发票号失败 ,请联系管理员维护发票号。。", "info");
		return;
	}
	if($("#invoice_type").val() <= 0 ){
		$.messager.alert("操作提示", "发票金额小于等于0 ,无需再看发票。。", "info");
		return;
	}
	if($("#invoice_name").val() == ''){
		$.messager.alert("操作提示", "发票抬头不能为空 ,请核对。。", "info");
		return;
	}
	$.ajax({
			url : 'saveInvoiceInfo.action',
			data : {
				com_num:$("#exam_id").val(),
			    invoice_num:$("#invoice_num").val(),
			    invoice_name:$("#invoice_name").val(),
			    invoice_amount:$("#invoice_type").val(),
			    jnnumbers:jnnumbers.toString()
			},
			type : 'post',
			success:function(data){
				var da = data.split("-")
				if(da[0] == 'ok'){
					$("#dlg-fapiao").dialog('close');
					$.messager.alert("操作提示", da[1], "");	
					getTransactionFlow();
					getInvoiceInfoList();
				}else{
					$.messager.alert("操作提示", da[1], "error");	
				}
			},
			error : function() {
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
}

function  getInvoiceInfoList(){
	var model = {"com_num":$("#com_num").val()};
	$("#invoiceInfo").datagrid({
			url: 'getInvoiceInfoList.action',
			queryParams: model,
			rownumbers:true,
			pageSize: 15,//每页显示的记录条数，默认为10 
			pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			sortName: 'cdate',
			sortOrder: 'desc',
			toolbar:"#toolbar_f",
			columns:[[
//					  {align:'center',field:'sf_ck_f',checkbox:true},
					  {align:"center",field:"id","title":"ID","width":10},
					  {align:"center",field:"title_info","title":"抬头","width":20},
			          {align:"center",field:"invoice_num","title":"发票号","width":20},
			          {align:"center",field:"invoice_amount","title":"发票金额(元)","width":15},
			          {align:"center",field:"creater","title":"开票人","width":10},
			          {align:"center",field:"create_time","title":"开票时间","width":10},
			          {align:"center",field:"account_num","title":"收据单号","width":15}
			     	]],
		    onLoadSuccess:function(value){
		    	$("#invoiceInfo").datagrid("hideColumn", "id");
		    },
		    singleSelect:true,
		    collapsible:true,
			pagination: false,
			fitColumns:true,
			fit:true,
			striped:true,
			toolbar:toolbar_f,
		    onSelect:function(index,row){
		    	
		    }
	});
}
var toolbar_f = [{
		text:'作废发票',
		width:120,
		iconCls:'icon-remove',
	    handler:function(){
	    	var  ids = new Array();
	    	var data = $("#invoiceInfo").datagrid('getSelections');
	    	if(data.length <= 0 ){
	    		$.messager.alert("操作提示", "请选择需要作废的发票!!", "warning");
	    		return ;
	    	}
	    	for(var i = 0; i < data.length ; i++){
	    		ids.push(data[i].id)
	    	}
	    	
	    	$.messager.confirm('提示信息','是否进行批量作废发票？',function(r){
	    		if(r){
		    		$.ajax({
						url : 'deleteInvoiceInfo.action',
						data : {
							ids:ids.toString()
						},
						type : 'post',
						success:function(data){
							var da = data.split("-")
							if(da[0] == 'ok'){
								getInvoiceInfoList();
								getTransactionFlow();
								$.messager.alert("操作提示", da[1], "");	
							}else{
								$.messager.alert("操作提示", da[1], "error");	
							}
						},
						error : function() {
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	    		}
	    	});
	    }
}];

function editMercacc(type){
	var com_num = $("#com_num").val();
	if(com_num.length <= 0){
		$.messager.alert("操作提示", "请先选择单位!", "info");	
		return ;
	};
	
	var  mes ;
	if(type == '1'){
		if($("#com_t").val() == '1'){
			$.messager.alert("操作提示", "已经是冻结状态无需操作!", "info");
			return;
		}
		mes = "冻结之后不能对此商户进行交易，是否冻结此商户？";
	}else{
		if($("#com_t").val() == '0'){
			$.messager.alert("操作提示", "已经是正常状态无需操作!", "info");
			return;
		}
		mes = "解冻之后可以对此商户进行交易，是否解冻？";
	}
	
	$.messager.confirm('提示信息',mes,function(r){
		if(r){
			$.ajax({
				url : 'editCompanyAccount.action',
				data : {
					com_num:com_num,
					type:type
				},
				type : 'post',
				success:function(data){
					var da = data.split("-")
					if(da[0] == 'ok'){
						$.messager.alert("操作提示", da[1], "");	
						f_getCompanyOne($("#com_id").val(),'');
					}else{
						$.messager.alert("操作提示", da[1], "error");	
					}
				},
				error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
		}
		
	});
	
}
