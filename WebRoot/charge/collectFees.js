$(document).ready(function () {
	getwitemGrid();
	getCharingType();
//	authorization();
	var is_repeat_invoice=Number($("#is_repeat_invoice").val());//是否有重打发票功能，1 没有   2  有
	//配置控制重打发票
	if(is_repeat_invoice=="1"){
		$("#repeatInvoice").hide();
		$("#repeatFapiao").hide();		
	}
	
	$('#zongzhekou').change(function() {
	    if(Number($(this).val())<Number($('#webResource').val())){
  	    	$.messager.alert('提示信息',"本操作员最大权限可打"+$('#webResource').val()+"折！",'error');
  	    }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
  	    	$.messager.alert('提示信息',"没有打折权限",'error');
	    }else {
	    	updateAlldiscount(this.value);
	    }	    
	});
	
	$("#ser_num").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			chaxun();
		} 
	});
	$('#ser_num').css('ime-mode','disabled');
	$('#ser_num').focus();
	
	if($("#isShowInvoicePage").val() == 'Y'){
		$("#fapiao").click();
//		$("#fapiao").attr('disabled',true);
	}
	if($("#is_fees_mx_point_checked").val() == 'Y'){
		$("#shoufeimingxi").attr('checked',true);
	}
	chaxun();
	$('#prescription').combobox({
		url :'getPrescriptionCharge.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight:200,
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			$('#prescription').combobox('setValue', data[0].id);
		}
	});
//	$('#fhr').combobox({
//		url : 'getCashierListCharge.action',
//		editable : false, //不可编辑状态
//		cache : false,
//		panelHeight : 'auto',//自动高度适合
//		valueField : 'id',
//		textField : 'chi_Name',
//		onLoadSuccess : function(data){
////			for (var int = 0; int < data.length; int++) {
////				if($("#center_id").val()!=data[i].id){
//					$('#fhr').combobox('setValue', data[12].id);
////				}
////			}
//			
//		},
//	});
	$("#gfmc").combobox({
		url :'getInformationCharge.action',
		//editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'infoClientName',
		onSelect : function(){
             var id = $("#gfmc").combobox("getValue") ;
             $.ajax({  
    		        "type" : 'post',  
    		        "url": "getInformationCharge.action?infoClientName="+id,
    		        "dataType" : "json",  
    		        "success" : function(data) {
    					$("#gfsh").val(data[0]["infoClientTaxCode"]);
    					$("#gfkhh").val(data[0]["infoClientBankAccount"]);
    					$("#gfdhdz").val(data[0]["infoClientAddressPhone"]);
    				}
            	}); 
        }  
	})
	
//	 
//	yibaoqiandao();
});
function yibaoqiandao(type){
	if(type=="1"){
		citySignInSend("02","1");
		  setTimeout(function(){
			  citySignInSend("03","1");
		    },2000);//延时2秒后执行 	
	}else{
		citySignInSend("02","0");
		  setTimeout(function(){
			  citySignInSend("03","0");
		    },2000);//延时2秒后执行 
	}
	
	
}
function ruku(state,obj1){
	var chargingids = new Array();
	var obj = $("input[name=ck]");
	var data = $('#witemlist').datagrid('getData');
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			chargingids.push(data.rows[i].item_id);
		}
	}
	var isPrintRecepit = "N";// 是否打印发票
	if ($("#fapiao")[0].checked||$("#shouju")[0].checked){
		isPrintRecepit = "Y";
	}
	var invoice_num=$("#invoice_num").val();
	if($("#bill_type").val()=="2"){
		invoice_num=$("#fapiao_num").val();
	}
	$.ajax({
		url : 'putStorageCharge.action',
		data : {
				"exam_num":$("#ser_num").val(),
				"req_nums":state.req_num,
				"invoice_type":$("#invoice_type").val(),
		        "invoice_num":invoice_num,
		        "bill_type":$("#bill_type").val(),
		        "rcpt_print_flag":$("#rcpt_print_flag1").val(),
		        "isPrintRecepit":isPrintRecepit,
		        "chargingids":chargingids.toString()
				},
				type : "post",//数据发送方式   
				success : function(text) {
						if (text.split("-")[0] == 'ok') {
							$(".loading_div").remove();
							alert_autoClose("操作提示", text.split("-")[1],"");
//							$.messager.alert("操作提示", text.split("-")[1], "info");	
							shoufei_show(state,obj1);
							$("#inter_class").val("");
							$("#req_num").val("");
							$("#wayCharge").val("");
						}else{
							$(".loading_div").remove();
							$.messager.alert("操作提示", text.split("-")[1], "error");
							shujushanchu(state.req_num);
//							$.messager.confirm('提示信息','您确认要重新入库吗？',function(r){
//								if(r){
//									ruku(state,obj1);
//								}
//							});
						}
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});	
}
function shujushanchu(req_num){/*
	$.ajax({
		url : 'deleteChargeCharge.action',
		data : {
				"req_nums":req_num
				},
				type : "post",//数据发送方式   
				success : function(text) {
						if (text.split("-")[0] == 'ok') {
							$.messager.alert("操作提示", text.split("-")[1], "info");	
						}else{
							$.messager.alert("操作提示", text.split("-")[1], "error");
						}
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});	
*/}

$(function(){
	$("#invoice_name,#invoice_type,#invoice_num").validatebox({
		required: true
	});
});
function chaxun(){
	getwitemGrid();
	getcustomerInfo();
	shofeiheji();
	$("#yintui").html(0);
	$("#shishou").html(0);
	load_invoice();
	load_receiptis();

	$("input[id='shouju']").attr("checked",true);
	$("#inter_class").val("");
	$("#req_num").val("");
	$("#wayCharge").val("");
	load_receiptis();
	$('#fapiao').attr("disabled",false)//将input元素设置为disabled
}

/*************************************************************收费功能**********************************************************************/
function getCharingType(){//获取收费方式
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	$.ajax({
//		url:'getDatadis.action?com_Type=SFFSLXFL&remark=GR',
		url:'getDatadis.action?com_Type=SFFSLX',
//		url:'getDatadis.action?com_Type=SFFSLXFL',
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			var str = '';
			var obj=eval(data);
			for(var i=0;i<6;i++){
				if(i< obj.length){
					if(obj[i].id == '122'){
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(2,this)" style="margin-top:6px;" type="checkbox" style="" childrencode="'+obj[i].data_code_children+'" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input readonly="readonly" onclick="showcardInfo()" id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}else if(obj[i].id == '220'||obj[i].id == '358'){
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(1,this)" style="margin-top:6px;" type="checkbox" style="" childrencode="'+obj[i].data_code_children+'" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input readonly="readonly" id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" onblur="blur_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}else{
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(1,this)" style="margin-top:6px;" type="checkbox" style="" childrencode="'+obj[i].data_code_children+'" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" onblur="blur_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}
				}
			}
			$("#charingtype").html(str);
			
			if(obj.length > 6){
				str = '';
				for(var i=6;i<obj.length;i++){
					if(obj[i].id == '122'){
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(2,this)" style="margin-top:6px;" type="checkbox" style="" childrencode="'+obj[i].data_code_children+'" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input readonly="readonly" onclick="showcardInfo()" id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}else if(obj[i].id == '220'||obj[i].id == '358'){
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(1,this)" style="margin-top:6px;" type="checkbox" style="" childrencode="'+obj[i].data_code_children+'" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input readonly="readonly" id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" onblur="blur_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}else{
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(1,this)" style="margin-top:6px;" type="checkbox" style="" childrencode="'+obj[i].data_code_children+'" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" onblur="blur_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}
				}
				$("#charingtype2").show();
				$("#charingtype2").html(str);
			}
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}


var olddiscount,oldamount;

function getwitemGrid(){//查询未收费项目列表
	 var lastIndex;
	 var model={"exam_num":$("#ser_num").val()};
     $("#witemlist").datagrid({
	 url:'getwitemListCharge.action',
	 dataType: 'json',
	 queryParams:model,
	 rownumbers:true,
     pageSize: 15,//每页显示的记录条数，默认为10 
     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	 columns:[[
	    {align:'center',field:'ck',checkbox:true},
	    {align:'center',field:'item_code',title:'项目编码',width:12},
	 	{align:'center',field:'item_name',title:'项目名称',width:15},
	 	{align:'center',field:'dep_name',title:'科室名称',width:12},
	 	{align:'center',field:'exam_status_y',title:'检查状态',width:12},
	 	{align:'center',field:'item_amount',title:'金额(元)',width:10},
	 	{align:'center',field:'itemnum',title:'数量',width:10},
	 	{align:'center',field:'discount',title:'折扣',width:10,editor:{type:'numberbox',options:{precision:4}}},
	 	{align:'center',field:'amount',title:'折后金额(元)',width:15,editor:{type:'numberbox',options:{precision:4}}},
	 	{align:'center',field:'personal_pay',title:'个人付费金额(元)',width:20},
	 	{align:'center',field:'team_pay',title:'单位付费金额(元)',width:20},
	 	{align:'center',field:'creater',title:'登记人',width:10}	,
	 	{align:'center',field:'create_time',title:'登记时间',width:15},
	 	{align:'center',field:'his_req_status_y',title:'HIS申请',width:15}
	 	]],	    	
    	onLoadSuccess:function(value){
    		$("#datatotal").val(value.total);
    		$('#witemlist').datagrid('checkAll');
    	},
    collapsible:true,
	pagination: false,
    fitColumns:true,
    striped:true,
    fit:true,
    checkOnSelect:false,
    onClickCell:function(index, field, row){
    	$('#witemlist').datagrid('clearSelections', index);
    	//if (lastIndex != index){
    		$('#witemlist').datagrid('endEdit', lastIndex);
			$('#witemlist').datagrid('beginEdit', index);
		//}
		lastIndex = index;
		var editors = $('#witemlist').datagrid('getEditor',{index:index,field:'discount'});
		$(editors.target).numberbox({onChange:function(){
				$('#witemlist').datagrid('endEdit', index);
				shofeiheji();
			}
		});
		var editors1 = $('#witemlist').datagrid('getEditor',{index:index,field:'amount'});
		$(editors1.target).numberbox({onChange:function(){
				$('#witemlist').datagrid('endEdit', index);
				shofeiheji();
			}
		});
    },
    onBeforeEdit:function(rowIndex, rowData){
    	olddiscount = rowData.discount;
    	oldamount = rowData.amount;
    },
    onAfterEdit:function(rowIndex, rowData, changes){    	
    	if(changes.discount != undefined){ //修改单一折扣
    		 if(Number(changes.discount)<Number($('#webResource').val())){
    	  	    	$.messager.alert('提示信息',"本操作员最大权限可打"+$('#webResource').val()+"折！",'error');
    	  	    	rowData.discount=olddiscount;
    	  	    	
    	  	 }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
    	  	    	$.messager.alert('提示信息',"没有打折权限",'error');
    	  	    	rowData.discount=olddiscount;
    		 }else {
    		
	    		if(Number(changes.discount) > 10 || Number(changes.discount) < 0){
	    			$.messager.alert('提示信息', '折扣率不能大于10或小于0',function(){});
	    			rowData.discount = olddiscount;
	        		$('#witemlist').datagrid('refreshRow', rowIndex);
	    		}else{
	    			rowData.discount = Number(changes.discount);
	    			rowData.amount = decimal(Number(rowData.item_amount)*Number(rowData.itemnum) * Number(changes.discount) / 10, 4);
	    			var personal_pay = decimal(Number(rowData.amount) - Number(rowData.team_pay),4);
	    			if(personal_pay < 0){
	    				$.messager.alert('提示信息', '折后金额不能小于单位付费金额！',function(){});
	    				rowData.discount = olddiscount;
	    				rowData.amount = oldamount;
	    				$('#witemlist').datagrid('refreshRow', rowIndex);
	    			}else{
		    			rowData.personal_pay = personal_pay;
		    			$('#witemlist').datagrid('refreshRow', rowIndex);
	    			}
	    		}
    		 }
    		 $('#witemlist').datagrid('refreshRow', rowIndex);
    	}
    	
    	
    	if(changes.amount != undefined){ //修改单一金额
			 
    		if(Number(changes.amount) < 0 || Number(changes.amount) > (Number(rowData.item_amount)*Number(rowData.itemnum))){
    			$.messager.alert('提示信息', '折后金额不能大于项目金额或小于0',function(){});
    			rowData.amount = oldamount;
    			$('#witemlist').datagrid('refreshRow', rowIndex);
    		}else{
    			
    			 var zy_zhekou = decimal(Number(rowData.amount)/(Number(rowData.item_amount)*Number(rowData.itemnum)) *10 ,4);
    			 if(zy_zhekou<Number($('#webResource').val())){
    	 	  	    	$.messager.alert('提示信息',"本操作员最大权限可大"+$('#webResource').val()+"折！",'error');
    	 	  	    	rowData.amount=oldamount;
    	 	  	 }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
    	 	  	    	$.messager.alert('提示信息',"没有打折权限",'error');
    	 	  	    	rowData.amount=oldamount;
    	 		 }else{
    	 			rowData.amount = Number(changes.amount);
        			rowData.discount = decimal(Number(rowData.amount)/(Number(rowData.item_amount)*Number(rowData.itemnum)) *10 ,4);
        			var personal_pay = decimal(Number(rowData.amount) - Number(rowData.team_pay),4);
        			if(personal_pay < 0){
        				$.messager.alert('提示信息', '折后金额不能小于单位付费金额！',function(){});
        				rowData.discount = olddiscount;
        				rowData.amount = oldamount;
        				$('#witemlist').datagrid('refreshRow', rowIndex);
        			}else{
    	    			rowData.personal_pay = personal_pay;
    	    			$('#witemlist').datagrid('refreshRow', rowIndex);
        			}
    	 		 }
    		}
    		 $('#witemlist').datagrid('refreshRow', rowIndex);
    	}
    	return;
    },
    onSelect:function(index,row){
    	$("input[name=ck]").eq(index).attr("checked",true);
    	shofeiheji();
    },
    onUnselect:function(index,row){
    	$("input[name=ck]").eq(index).attr("checked",false);
    	shofeiheji();
    },
    onCheckAll:function(rows){
    	$('#witemlist').datagrid('clearSelections');
    	shofeiheji();
    },
    onUncheckAll:function(rows){
    	shofeiheji();
    }
});
}

//查询人员基本信息
function getcustomerInfo(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+$("#ser_num").val(),
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			if(data == 'null'){
				$("#exam_id").val('');
				$("#user_name").html('');
				$("#sex").html('');
				$("#age").html('');
				$("#company").html('');
				$("#customer_type").html('');
				$("#set_name").html('');
				$("#group_name").html('');
				$("#amount").html('');
				$("#ser_num").focus();
				$("#status").val('');
				return;
			}
			var obj=eval("("+data+")");
			$("#exam_id").val(obj.id);
			$("#user_name").html(obj.user_name);
			$("#sex").html(obj.sex);
			$("#age").html(obj.age);
			$("#company").html(obj.company);
			$("#customer_type").html(obj.customer_type);
			$("#set_name").html(obj.set_name);
			$("#group_name").html(obj.group_name);
			$("#amount").html(obj.amount);
			$("#invoice_name").val(obj.user_name);
			$("#status").val(obj.status);
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}
//收费抹零
function moling(){
	var yinshou = Number($("#yingshou").html());
	var shishou = Number($("#shishou").html());
	if(yinshou <= 0){
		$.messager.alert('提示信息',"应收金额为0，不能执行此操作!",'error');
		return;
	}
	if(shishou <= 0){
		$.messager.alert('提示信息',"实收金额为0，不能执行此操作!",'error');
		return;
	}
	var ling = 0;
	var rows = $('#witemlist').datagrid('getChecked');
	if(rows.length <= 0){
		$.messager.alert('提示信息',"不存在选中的收费项目，不能执行此操作!",'error');
		return;
	}
	if(yinshou > shishou){
		ling = yinshou - shishou;
		if(ling > $("#collect_fees_whole").val()){
			$.messager.alert('提示信息',"凑整金额最大值为"+$("#collect_fees_whole").val()+"元，实际凑整金额为"+ling+"元，不能执行此操作!",'error');
			return;
		}
		var i = couzheng(rows);
		if(rows[i].amount - ling<0){
			$.messager.alert('提示信息',"凑整金额超过金额最大的项目，不能执行此操作!",'error');
			return;
		}
		rows[i].amount =  rows[i].amount - ling;
		rows[i].amount = decimal((rows[i].amount - ling),4);
		var index = $('#witemlist').datagrid('getRowIndex',rows[i]);
		$('#witemlist').datagrid('refreshRow', index);
		$("#yingshou").html(shishou);
	}else{
		ling = shishou - yinshou;
		if(ling > $("#collect_fees_whole").val()){
			$.messager.alert('提示信息',"凑整金额最大值为"+$("#collect_fees_whole").val()+"元，实际凑整金额为"+ling+"元，不能执行此操作!",'error');
			return;
		}
		rows[0].amount =  rows[0].amount + ling;
		rows[0].personal_pay = rows[0].amount;
		var index = $('#witemlist').datagrid('getRowIndex',rows[0]);
		$('#witemlist').datagrid('refreshRow', index);
		$("#yingshou").html(shishou);
	}
	$("#zhaoling").val(0);
	//shofeiheji();
}

function couzheng(rows){
	var index = 0;
	for(var i=1;i<rows.length;i++){
		if(rows[i].amount >= rows[index].amount){
			index = i;
		}
	}
	return index;
}

//计算原价，应收金额
function shofeiheji(){
	var obj = $("input[name=ck]");
	var yuanjia = 0.0,yingshou = 0.0;
	var data = $('#witemlist').datagrid('getData');
	var team_pay = 0.0;
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			yuanjia += Number(data.rows[i].item_amount)*Number(data.rows[i].itemnum);
			yingshou += Number(data.rows[i].personal_pay);
			team_pay += Number(data.rows[i].team_pay);
		}
	}
	
	$("#yuanjia").html(decimal(yuanjia,2));
	$("#yingshou").html(decimal(yingshou,2));
	$("#invoice_type").val(decimal(yingshou,2)+'元');
	if(yuanjia != 0){
		var discount = decimal((yingshou+team_pay)/yuanjia*10,2);
		$("#zongzhekou").val(discount);
	}
	
	var obj1 = $("input[name=sffs_box]:checked");
	for(var i=0;i<obj1.length;i++){
		obj1[i].checked = false;
		$("#sffs_"+$(obj1[i]).val()).val(0);
	}
	$("#shishou").html(0);
}

//修改总折扣
function updateAlldiscount(disvar){
	if (!isFloat(disvar)) {
		$.messager.alert('提示信息', '折扣率格式错误！',function(){});
		//alert('折扣率格式错误！');
		$("#zongzhekou").focus();
	} else if (Number(disvar) > 10 || Number(disvar) < 0) {
		//alert('折后金额不能大于项目金额或小于0！');
		$.messager.alert('提示信息', '折后金额不能大于项目金额或小于0！',function(){});
		$("#zongzhekou").focus();
	} else {
		var rows = $('#witemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var j = 0; j <= rows.length - 1; j++)//已选择
			{
				var row = rows[j];
					row.discount = disvar;
					row.amount = decimal(Number(row.item_amount)*Number(row.itemnum) * Number(disvar) / 10, 4);
					var personal_pay = decimal(Number(row.amount) - Number(row.team_pay),4);
					if(personal_pay < 0){
	    				//alert("项目"+row.item_name +"折后金额不能小于单位付费金额！");
	    				$.messager.alert('提示信息', "项目"+row.item_name +"折后金额不能小于单位付费金额！",function(){});
	    			}else{
		    			row.personal_pay = personal_pay;
		    			$('#witemlist').datagrid('refreshRow', j);
	    			}
					
			}//j End             
		}
		shofeiheji();
	}
}
//修改应收金额
function updateAllAmount(){
	var disvar = $("#shishou").html();
	var newdiscont=decimal(Number(disvar) / Number($("#yuanjia").html()) * 10, 4);
	$("#zongzhekou").val(newdiscont);
	$("#yingshou").html(decimal(disvar,2));
	var rows = $('#witemlist').datagrid('getRows');
	if (!rows.length == 0) {
		for (var j = 0; j <= rows.length - 1; j++)//已选择
		{
			var row = rows[j];
				row.discount = newdiscont;
				row.amount = decimal(Number(row.item_amount)*Number(row.itemnum)* Number(newdiscont) /10, 4);
				var personal_pay = decimal(Number(row.amount) - Number(row.team_pay),4);
				if(personal_pay < 0){
    				//alert("项目"+row.item_name +"折后金额不能小于单位付费金额！");
    				$.messager.alert('提示信息', "项目"+row.item_name +"折后金额不能小于单位付费金额！",function(){});
    			}else{
	    			row.personal_pay = personal_pay;
	    			$('#witemlist').datagrid('refreshRow', j);
    			}
		}//j End             
	}
	
	
}
//选择收费方式，计算实收金额与找零
function click_sfbox(type,ths){
	var chk_value =[]; 
	$('input[name="sffs_box"]:checked').each(function(){ 
	chk_value.push($(this).val()); 
	});
	if(ths != undefined){
		if($(ths)[0].checked){
			if($(ths).val()==358||$(ths).val()==220){
				$('#fapiao').attr("disabled",true)//将input元素设置为disabled
			}
			
			if($(ths).val()==358){
				if(chk_value.length>=2){
					
					$("#charingtype input[type='checkbox']").each(function(){this.checked=false;});
					$("#charingtype2 input[type='checkbox']").each(function(){this.checked=false;});
					for (var z = 0; z < chk_value.length; z++) {
						if(chk_value[z]!=358){
							$("#sffs_"+chk_value[z]).val(0);
						}
					}
					$("#shishou").html(0);
					$.messager.alert("操作提示","含有医保收费必须先选择医保收费！", "error");
					return;
				}
				readCard("03","1",type);
			}else if($(ths).val()==220){
				if(chk_value.length>=2){
					$("#charingtype input[type='checkbox']").each(function(){this.checked=false;});
					$("#charingtype2 input[type='checkbox']").each(function(){this.checked=false;});
					for (var z = 0; z < chk_value.length; z++) {
						if(chk_value[z]!=358){
							$("#sffs_"+chk_value[z]).val(0);
						}
					}
					$("#shishou").html(0);
					$.messager.alert("操作提示","含有医保收费必须先选择医保收费！", "error");
					return;
				}
				readCard("02","1",type);
			}else{
				var ys = Number($("#yingshou").html());
				var ss = Number($("#shishou").html());
				var sy = ys - ss;
				if(sy >= 0){
					$("#sffs_"+$(ths).val()).val(decimal(sy,2));
				}	
				showys(type);
			}
			
		}else{
			$('#fapiao').attr("disabled",false)//将input元素设置为disabled
			$("#sffs_"+$(ths).val()).val(0);
			if($(ths).val() == '122'){
				card_xiaofei.length = 0;
			}
			showys(type);
		}
	}else{
		showys(type)
	}
	
	
}
function showys(type){
	var obj = $("input[name=sffs_box]:checked");
	var shishoujine = 0.0;
	if(obj.length > 0){
//		if(obj.length == 1 && type == 1){
//			$("#sffs_"+$(obj[0]).val()).val($("#yingshou").html());
//		}
		for(var i=0;i<obj.length;i++){
			shishoujine += Number($("#sffs_"+$(obj[i]).val()).val());
			if(type == 2 && $(obj[i]).val() == '122'){
				showcardInfo();
			}
		}
	}
	$("#shishou").html(decimal(shishoujine,2));
	
	var yishou = Number($("#yingshou").html());
	
	if(shishoujine > yishou){
		$("#zhaoling").val(shishoujine - yishou);
	}else{
		$("#zhaoling").val(0);
	}
}
var card_xiaofei = new Array();
//选择会员卡收费方式，弹出会员卡操作页面
function showcardInfo(){
	$("#dlg-edit").dialog({
		title:'卡消费',
		href:'showCardInfoCharge.action',
		closable:false,
		onClose:function(){  
			
	    },
	    buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			width :80,
			handler:function(){
				card_xiaofei.length = 0;
				var obj = $("input[name=sffs_box]");
				if(obj.length > 0){
					for(var i=0;i<obj.length;i++){
						if($(obj[i]).val() == '122'){
							if(Number($("#c_shishou").html()) > 0){
								$(obj[i]).attr("checked",true);
								$("#sffs_"+$(obj[i]).val()).val($("#c_shishou").html());
								click_sfbox();
								
								var obj1 = $("input[name=card_ck]");
								var data = $('#cardinfolist').datagrid('getData');
								for(var j=0;j<obj1.length;j++){
									if(obj1[j].checked == true){
										card_xiaofei.push({
											"card_num":data.rows[j].card_num,
											"amount":data.rows[j].xf_amount,
											"face_amount":data.rows[j].amount,
											"card_count":data.rows[j].card_count
										});
									}
								}
							}else{
								$(obj[i]).attr("checked",false);
								$("#sffs_"+$(obj[i]).val()).val(0);
								click_sfbox();
							}
						}
					}
				}
				$('#dlg-edit').dialog('close');
			}
		},{
			text:'关闭',
			width :80,
			handler:function(){
				card_xiaofei.length = 0;
				var obj = $("input[name=sffs_box]");
				if(obj.length > 0){
					for(var i=0;i<obj.length;i++){
						if($(obj[i]).val() == '122'){
							$(obj[i]).attr("checked",false);
							$("#sffs_"+$(obj[i]).val()).val(0);
							card_xiaofei.length = 0;
							click_sfbox();
						}
					}
				}
				$('#dlg-edit').dialog('close');
			}
		}]
	});
	$("#dlg-edit").dialog("open");
}

//收费方式金额限制输入数字
function keyup_sf(dom){
	$(dom).val($(dom).val().replace(/[^\d.]/g, ''));
}
//收费方式金额失去焦点计算金额
function blur_sf(dom){
	if($(dom).val() == '' || $(dom).val() == 0){
		$(dom).val(0);
		$(dom).parent().parent().children('dd').eq(0).children('input')[0].checked = false;
	}else{
		$(dom).parent().parent().children('dd').eq(0).children('input')[0].checked = true;
	}
	click_sfbox();
}

function shoufeiBaodao(){
	if($("#status").val() == 'Y' && $("#isFeesBaodao").val() == 'Y'){
		$.messager.confirm('提示信息','是否报到？',function(r){
			if(r){
				 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
				$.ajax({
					url : 'djtexamInfoStatusdo.action',
					data : {
						    exam_num:$("#ser_num").val()
							},
							type : "post",//数据发送方式   
							success : function(text) {
								$(".loading_div").remove();
								alert(3)
								if (text.split("-")[0] == 'ok') {
									var chk_value =[]; 
									$('input[name="sffs_box"]:checked').each(function(){ 
									chk_value.push($(this).attr("childrencode")); 
									}); 
									if(chk_value.length>2){
										$.messager.alert("操作提示","只能两种收费方式进行能混合收费", "error");
										return;
									}
									if(chk_value.indexOf("101") != -1&&chk_value.indexOf("102") != -1){
										$.messager.alert("操作提示","这两种收费方式不能进行能混合收费", "error");
										return;
									}
									if(chk_value.indexOf("101") != -1&&chk_value.indexOf("115") != -1){
										$.messager.alert("操作提示","这两种收费方式不能进行能混合收费", "error");
										return;	
									}
									if(chk_value.indexOf("102") != -1&&chk_value.indexOf("115") != -1){
										$.messager.alert("操作提示","这两种收费方式不能进行能混合收费", "error");
										return;
									}
									if(chk_value.indexOf("103") != -1&&chk_value.indexOf("104") != -1){
										$.messager.alert("操作提示","这两种收费方式不能进行能混合收费", "error");
										return;
									}
									if(chk_value.indexOf("101") != -1||chk_value.indexOf("102") != -1||chk_value.indexOf("115") != -1){
//										if(chk_value.length>1){
//											$.messager.alert("操作提示","固定pos，微信，支付宝不能混合收费", "error");
//											return;
//										}
										savecollectFees("00",chk_value)
									}else if(chk_value.indexOf("103") != -1||chk_value.indexOf("104") != -1){
//										if(chk_value.length>1){
//											$.messager.alert("操作提示","固定pos，微信，支付宝不能混合收费", "error");
//											return;
//										}
										savecollectFees("01",chk_value);
									}else{
										savecollectFees("","");
									}
								} else {
									$.messager.alert("操作提示", text.split("-")[1], "error");
								}
								
							}
						});

			}
			});
	}else{
		var chk_value =[]; 
		$('input[name="sffs_box"]:checked').each(function(){ 
		chk_value.push($(this).attr("childrencode")); 
		}); 
		if(chk_value.length>2){
			$.messager.alert("操作提示","只能两种收费方式进行能混合收费", "error");
			return;
		}
		if(chk_value.indexOf("101") != -1&&chk_value.indexOf("102") != -1){
			$.messager.alert("操作提示","这两种收费方式不能进行能混合收费", "error");
			return;
		}
		if(chk_value.indexOf("101") != -1&&chk_value.indexOf("115") != -1){
			$.messager.alert("操作提示","这两种收费方式不能进行能混合收费", "error");
			return;	
		}
		if(chk_value.indexOf("102") != -1&&chk_value.indexOf("115") != -1){
			$.messager.alert("操作提示","这两种收费方式不能进行能混合收费", "error");
			return;
		}
		if(chk_value.indexOf("103") != -1&&chk_value.indexOf("104") != -1){
			$.messager.alert("操作提示","这两种收费方式不能进行能混合收费", "error");
			return;
		}
//		if((chk_value.indexOf("101") != -1||chk_value.indexOf("102") != -1||chk_value.indexOf("115") != -1)&&chk_value.length==1){
//			$.messager.alert("操作提示","这两种收费方式不能进行能混合收费", "error");
//			return;
//		}
		if((chk_value.indexOf("101") != -1||chk_value.indexOf("102") != -1||chk_value.indexOf("115") != -1)&&chk_value.length==1){
//			if(chk_value.length>1){
//				$.messager.alert("操作提示","固定pos，微信，支付宝不能混合收费", "error");
//				return;
//			}
			savecollectFees("00",chk_value)
		}else if(chk_value.indexOf("103") != -1||chk_value.indexOf("104") != -1){
//			if(chk_value.length>1){
//				$.messager.alert("操作提示","固定pos，微信，支付宝不能混合收费", "error");
//				return;
//			}
			savecollectFees("01",chk_value);
		}else{
			savecollectFees("","");
		}
	}
}

//收费保存收费信息
function savecollectFees(chargeType,chk_value){
			var yingshou = Number($("#yingshou").html());
			var shishou = Number($("#shishou").html());
			var yuanjia = Number($("#yuanjia").html());
			if(shishou < yingshou){
				$.messager.alert('提示信息', "实收金额不能小于应收金额，请先反算！",function(){});
				return;
			}
			var examInfoCharingItem = new Array();
			var chargingids = new Array();
			var itemCodeStr = new Array();
			var obj = $("input[name=ck]");
			var data = $('#witemlist').datagrid('getData');
			
			var his_flag = false;
			examInfoCharingItem.length = 0;
			for(var i=0;i<obj.length;i++){
				if(obj[i].checked == true){
					examInfoCharingItem.push({"id":data.rows[i].id,
											  "item_id":data.rows[i].item_id,
											  "item_amount":data.rows[i].item_amount,
											  "amount":data.rows[i].amount,
											  "item_code":data.rows[i].item_code,
											  "item_name":data.rows[i].item_name,
											  "personal_pay":data.rows[i].personal_pay,
											  "discount":data.rows[i].discount});
					chargingids.push(data.rows[i].item_id);
					itemCodeStr.push(data.rows[i].item_code);
					if(his_flag == false && data.rows[i].his_req_status == 'Y'){
						his_flag = true;
					}
				}
			}
			if(examInfoCharingItem.length <= 0){
				$.messager.alert('提示信息', "请选择需要收费的项目!",function(){});
				return;
			}
			if(his_flag){
				$.messager.alert('提示信息', "存在已发HIS申请项目,请先撤销HIS申请!",function(){});
				return;
			}
			var charingWay = new Array();
			var cash=false;
			var Chargingmethod;
			var obj1 = $("input[name=sffs_box]:checked");
			charingWay.length = 0;
			if(obj1.length > 0){
				var amount = Number($("#sffs_119").val()) - Number($("#zhaoling").val());
				if(amount < 0){
					$.messager.alert('提示信息', "只有现金收费存在找零,请检查收费方式金额!",'error');
					return;
				}
				for(var i=0;i<obj1.length;i++){
					if(Number($("#sffs_"+$(obj1[i]).val()).val()) == 0 && $("#isChargingWayZero").val() == 'N'){
						continue;
					}
					if(119 == $(obj1[i]).val()){
						cash=true;
						if(amount == 0 && $("#isChargingWayZero").val() == 'N'){
							continue;
						}else{
							charingWay.push({"charging_way":$(obj1[i]).val(),"amount":amount});
						}
					}else{
						charingWay.push({"charging_way":$(obj1[i]).val(),"amount":$("#sffs_"+$(obj1[i]).val()).val()});
					}
					
					if(372 == $(obj1[i]).attr("childrencode")||122 == $(obj1[i]).attr("childrencode")){
						Chargingmethod = $(obj1[i]).attr("childrencode");
					}
					
				}
			}else{
				$.messager.alert('提示信息', "请选择收费方式!",function(){});
				return;
			}
			var titleInfo = $("#invoice_name").val();// 发票抬头
			var invoiceType = $("#invoice_type").val();// 发票内容
			var invoiceNum = $("#invoice_num").val();// 发票编码
			if($("#bill_type").val()==2){
				titleInfo = $("#gfmc").combobox("getText");// 发票抬头
				invoiceType = $("#spmc").val();// 发票内容
				invoiceNum = $("#fapiao_num").val();// 发票编码
			}
			var isPrintRecepit = "N";// 是否打印发票
			if ($("#fapiao")[0].checked||$("#shouju")[0].checked){
				isPrintRecepit = "Y";
			}
				
			if(isPrintRecepit == 'Y'){
				if($("#invoiceRepeatType").val()=='Y'){
					if(Chargingmethod==372||Chargingmethod==122){
						$.messager.alert('提示信息', "使用商户划账或会员卡方式收费无法开发票!","error");
						return;
					}
				}else{
					$.messager.alert('提示信息', "请修改数据库配置--INVOICE_REPEAT_TYPE--状态!","error");
					return;
				}

				if($("#bill_type").val()==1){
					if($("#invoice_num").val() == ''){
						$("#invoice_num").focus();
						return;
					}else if($("#invoice_name").val() == ''){
						$("#invoice_name").focus();
						return;
					}else if($("#invoice_type").val() == ''){
						$("#invoice_type").focus();
						return;
					}
					}else if($("#bill_type").val()==2){
						if($("#fapiao_num").val() == ''){
							$("#fapiao_num").focus();
							return;
						}else if($("#gfmc").combobox("getText") == ''){
							$("#gfmc").focus();
							return;
						}else if($("#spmc").val() == ''){
							$("#spmc").focus();
							return;
						}else if(($("#gfsh").val() != '')&& $("#gfsh").val().length<14){
							$("#gfsh").focus();
							$.messager.alert('提示信息', "购方税号位数不对!","error");
							return;
						}
					}				
			}
			if(chk_value.indexOf("103") != -1||chk_value.indexOf("104") != -1){
				if(shishou != yingshou){
					$.messager.alert('提示信息', "医保收费不能打折！",function(){});
					return;
				}
				if($("#prescription").combobox('getValue')==null||$("#prescription").combobox('getValue')==""){
					$.messager.alert('提示信息', "请选择处方医师!","error");
					return;
				}
				if($("#prescription").combobox('getText')==null||$("#prescription").combobox('getText')==""){
					$.messager.alert('提示信息', "请选择处方医师!","error");
					return;
				}
				var inter_class='02';
				if(chk_value.indexOf("103") != -1){
					inter_class='02';
				}else if(chk_value.indexOf("104") != -1){
					inter_class='03';
				}
				$.ajax({
			        url:'insuranceComparisonCharge.action',  
			        data:{
			          examInfoCharingItems:itemCodeStr.toString(),
			          inter_class:inter_class,
			          },          
			        type: "post",//数据发送方式   
			        success: function(data){
			        		var state = eval("("+data+")");
			        		var patNo=$("#patNo").val();
			        		$(".loading_div").remove();
			        		if(state.flag == 'ok'){
			        			if(chk_value.indexOf("103") != -1){
			    					$.messager.confirm('提示信息','您确认市医保缴费吗？',function(r){
			    						if(r){
			    							SaveFee(obj1,patNo,inter_class,chargeType,yuanjia,yingshou,charingWay,examInfoCharingItem,card_xiaofei,isPrintRecepit,chargingids,cash,chk_value,titleInfo,invoiceType,invoiceNum);
			    						  }
			    					})	
			    				}else if(chk_value.indexOf("104") != -1){
			    					$.messager.confirm('提示信息','您确认省医保缴费吗？',function(r){
			    						if(r){
			    							SaveFee(obj1,patNo,inter_class,chargeType,yuanjia,yingshou,charingWay,examInfoCharingItem,card_xiaofei,isPrintRecepit,chargingids,cash,chk_value,titleInfo,invoiceType,invoiceNum);
			    						 }
			    					})	
			    				}
				        	}else{
				        		$.messager.alert('提示信息', state.info,'error');
				        	}
			            },
			            error:function(){
			            	$(".loading_div").remove();
			            	$.messager.alert('提示信息', "操作失败！",'error');
			            }  
			    });
			}else{
				if(chk_value.indexOf("101") != -1||chk_value.indexOf("102") != -1){
					var wayCharge =$("#wayCharge").val();
	    			var trade_class="";
	    			if(wayCharge=="02"){
	    				trade_class="00";
	    			}else /*if(wayCharge=="00")*/{
	    				trade_class="01";
	    			}
	    			var qrcode= $("#sfm").val();
	    			
					if(trade_class=="01"){
						if(qrcode!=""&&qrcode!=null){
							if(qrcode.length!=18){
		    					$.messager.alert("操作提示", "请输入正确收费码！！！", "error");
		    					return;
		    				}
						}else{
							$.messager.alert("操作提示", "请输入收费码！！！", "error");
	    					return;
						}	
						if(chk_value.indexOf("101") != -1){
							if(qrcode.substr(0,1)!="1"){
								$.messager.alert("操作提示", "收费码是支付宝收费码", "error");	
								return;
							}
						}
						if(chk_value.indexOf("102") != -1){
							if(qrcode.substr(0,1)!="2"){
								$.messager.alert("操作提示", "收费码是微信收费码", "error");	
								return;
							}
						}
						
	    			}
//					weChatAlipay(chk_value,qrcode);
					
				}
				$.messager.confirm('提示信息','您确认缴费吗？',function(r){
					if(r){
						SaveFee(obj1,"","",chargeType,yuanjia,yingshou,charingWay,examInfoCharingItem,card_xiaofei,
								isPrintRecepit,chargingids,"",chk_value,titleInfo,invoiceType,invoiceNum);
					  }
				})	
				
			}		
}

var titleInfo,invoiceType,invoiceNum
function SaveFee(obj1,PatNo,inter_class,chargeType,yuanjia,yingshou,charingWay,examInfoCharingItem,card_xiaofei,isPrintRecepit,chargingids,cash,chk_value,titleInfo,invoiceType,invoiceNum){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'saveCollectFeesCharge.action?language='+$("#language").val(),  
        data:{
          exam_num:$("#ser_num").val(),
          exam_id:$("#exam_id").val(),
          chargeType:chargeType,
          amount1:yuanjia,
          amount2:yingshou,
          discount: $("#zongzhekou").val(),
          charingWays:JSON.stringify(charingWay),
          examInfoCharingItems:JSON.stringify(examInfoCharingItem),
          cardInfos:JSON.stringify(card_xiaofei),
          isPrintRecepit:isPrintRecepit,
          title_info:titleInfo,
          invoice_type:invoiceType,
          invoice_num:invoiceNum,
          bill_type:$("#bill_type").val(),
          inter_class:inter_class,
          patNo:PatNo,
          prescription_num:$("#prescription").combobox('getValue'),
          prescription_name:$("#prescription").combobox('getText'),
          chargingIds:chargingids.toString(),
          rcpt_print_flag:$("#rcpt_print_flag1").val()
          },          
        type: "post",//数据发送方式   
        success: function(data){
//	        	alert(chargeType);
        		var state = eval("("+data+")");
        		$("#data").val(data);
        		$(".loading_div").remove();
        		if((state.flag == 'ok'&&chargeType=="00")){
        			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:70px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在收费中，请稍后。。。</font></div></div>';
        			 $("body").prepend(str);
        			$("#req_num").val(state.req_num);
        			var trade_class="";
        			if(chk_value.indexOf("115") != -1){
	        			$("#wayCharge").val("02");
	        			trade_class="00";
	        		}else if(chk_value.indexOf("101") != -1||chk_value.indexOf("102") != -1){
	        			$("#wayCharge").val("00");
	        			trade_class="01";
	        		}
//        			cerate_socket(state,obj1);
//        			$("#posAmount").val($("#shishou").html());
        			var qrcode= $("#sfm").val();
        			WebSocketPos(state,obj1,state.req_num,qrcode,trade_class,"","")
        		    
	        	}else if((state.flag == 'ok'&&chargeType=="01")){
	        		if(chk_value.indexOf("115") != -1){
	        			$("#wayCharge").val("02");
	        		}/*else if((chk_value.indexOf("103") != -1||chk_value.indexOf("104") != -1)&&(chk_value.indexOf("101") == -1||chk_value.indexOf("102") == -1||chk_value.indexOf("115") == -1)){
	        			$("#wayCharge").val("01");
	        		}*/else if(chk_value.indexOf("101") != -1||chk_value.indexOf("102") != -1){
	        			$("#wayCharge").val("00");
	        		}
	        		registration(state,obj1,inter_class,PatNo,cash,chargeType,chk_value);
//	        		webSocketRegistration(state,obj1,inter_class,PatNo,cash,chargeType,chk_value);
	        	}else{
	        		$("#wayCharge").val(chargeType);
	        		shoufei_show(state,obj1)	
	        	}
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",'error');
            }  
    });
			
}

function shoufei_show(state,obj1){
		if(state.flag == 'error'){
			$.messager.alert('提示信息', state.info,'error');
			//$.messager.alert('提示信息', state.info,'error');
			chaxun();
			return;
		}
		var isPrintRecepit = "N";// 是否打印发票
		if ($("#fapiao")[0].checked){
			isPrintRecepit = "Y";
		}else if ($("#shouju")[0].checked){
			isPrintRecepit = "Y";
		}
		if (isPrintRecepit == "Y") {
			if ($("#bill_type").val()==1){										
				$.messager.alert('提示信息', "收费成功！正在打印收据。",'info');
				if($("#invoiceprinttype").val()=='1'){
					if($("input[name='invoice_l']:checked").val() == 'mx'){
						shouju_point_mx($("#invoice_num").val());
					}else{
						shouju_point($("#invoice_num").val());
					}
				}else{
					printreport_invoice($("#invoice_num").val());
				}
		}else if ($("#fapiao")[0].checked){
			jinshuipanprint(state.user_id,state.req_num);
		}
		}
		
		if($("#shoufeimingxi")[0].checked){
			if($("#fees_mx_point").val() == 2){
				mx_point(state.req_num);
			}else{
				mx_point($("#ser_num").val());
			}
		}
		if($("#putongmingxi")[0].checked){
			pt_point($("#ser_num").val());
		}
		
		for(var i=0;i<obj1.length;i++){
			obj1[i].checked = false;
			$("#sffs_"+$(obj1[i]).val()).val(0);
		}
		if($("#isShowInvoicePage").val() == 'Y'){
			load_invoice();
		}else{
			$("#fapiao")[0].checked = false;
			load_invoice();
			$("#invoice_name").val('');
			$("#invoice_type").val('');
			$("#invoice_num").val('');
		}
		//清除体检号
		$("#ser_num").val("");
		
		chaxun();
}

//金税盘打印发票
function jinshuipanprint(user_id,account_num){
	 $.ajax({
			url : 'getamtForCHARGSSReqNumCharge.action',
			data : {
				req_nums : account_num
			    },
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] !='') {
							var kaipiaodata={
									yymc:'dbgj',
									kprbm:$("#userid").val(),
									kprgh:$("#work_other_num").val(),
									fplx:$("#fplx").val(),////发票类型 0 专票  2 普票
									gfmc:$("#gfmc").combobox("getText"),//购方名称
									gfsh:$("#gfsh").val(),//购方税号
									gfkhh:$("#gfkhh").val(),//购方开户行及账号
									gfdhdz:$("#gfdhdz").val(),//购方地址电话
									jspsl:"6",//税率
									spmc:$("#spmc").val(),//商品名称
									spje:text.split("#")[2],//商品金额
									kpr:$("#username").val(),//开票人
									shuimu:"3070202",//税目
									spgg:$("#dw").val(),//商品规格
									spsl:$("#spsl").val(),//商品数量
									spdj:$("#spdj").val(),//商品单价
									fhr:$('#fhr').combobox('getText'),//复核人
									skr:$("#username").val(),//收费人
									bz:$("#bz").val()//备注
							};
							doFpInvoice(text.split("#")[1],kaipiaodata,function(objvalue) {
							    if(objvalue.error_flag=='0'){
								    	$.messager.alert("操作提示", objvalue.error_msg, "");
								    }else{
								    	$.messager.alert("操作提示", objvalue.error_msg, "error");
								    }
								    
								});
							
						}
					},
					error : function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
	
}

//补打发票
function fapiao_show(){
	$("#dlg-fapiao").dialog({
		title:'打印发票',
		href:'chargingSingleInvoickShowCharge.action?bill_type=2',
	});
	$('#dlg-fapiao').dialog('open');
}

//补打收据
function shouju_show(){
	$("#dlg-shouju").dialog({
		title:'打印发票',
		href:'chargingSingleInvoickShowCharge.action?bill_type=1',
	});
	$('#dlg-shouju').dialog('open');
}

//重打收据
function fapiao_repeatInvoice(){
	 $("#dlg-fapiaoCD").dialog({
	  title:'打印发票',
	  href:'repeatInvoiceCharge.action?bill_type=2',
	 });
	 $('#dlg-fapiaoCD').dialog('open');
}

//重打发票
function shouju_repeatInvoice(){
	 $("#dlg-fapiaoCD").dialog({
	  title:'打印发票',
	  href:'repeatInvoiceCharge.action?bill_type=1',
	 });
	 $('#dlg-fapiaoCD').dialog('open');
}

//撤销HIS申请
function del_his(){
	var chargingids = new Array();
	var obj = $("input[name=ck]");
	var data = $('#witemlist').datagrid('getData');
	
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			chargingids.push(data.rows[i].item_id);
		}
	}
	if(chargingids.length <= 0){
		$.messager.alert('提示信息', "请选择需要撤销申请的项目!",function(){});
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'delHisApplicationCharge.action?language='+$("#language").val(),  
        data:{
          exam_num:$("#ser_num").val(),
          chargingIds:chargingids.toString()
          },          
        type: "post",//数据发送方式   
        success: function(data){
        		$(".loading_div").remove();
        		var state = data.split("-");
        		if(state[0] == 'error'){
        			$.messager.alert('提示信息', state[1],'error');
        			return;
        		}else{
        			$.messager.alert('提示信息', state[1],'info');
        			$('#witemlist').datagrid('reload');
        		}
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",function(){});
            }  
    });
}

//回车查询
function serch_cls(dom){
	$(dom).unbind("keydown").keydown(function (e) {
	    if (e.which == 13) {
	    	chaxun();
	    }
    });
}

/**********************************************************发票**************************************************************/

function weihu_fapiao(){
	$("#dlg-tuifei").dialog({
		title:'用户发票号段维护',
		href:'getUserInvoicePageCharge.action?bill_type=2'
	});
	$('#dlg-tuifei').dialog('open');
}

//获取发票最大票号
function load_invoice() {
	$("#shouju")[0].checked=false;
	if ($("#fapiao")[0].checked) {	
		$("#bill_type").val(2);		
//		getClientflag("dbgj",function(objvalue){
//			$("#rcpt_print_flag1").val(objvalue.rcpt_print_flag);
//			if(objvalue.error_flag=='0'){
			$.ajax({
				url : 'getMaxInvoiceNumCharge.action',
				data : {
					bill_type:2
//					user_id:objvalue.rcpt_print_flag
					},
				type : 'post',
				success:function(data){
					$("#fapiao_num").val(data);
				},
				error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
//			}else{
//				$.messager.alert("操作提示", objvalue.error_msg, "error");
//			}
//		});	
				
		$("#fapiao_msg").css("display", "block");
		$("#shouju_msg").css("display", "none");
	} else {
		$("#fapiao_msg").css("display", "none");
		$("#shouju_msg").css("display", "none");
	}
}

function weihu_shouju(){
	var  rcpt_print_flag=$("#rcpt_print_flag1").val();
	$("#dlg-tuifei").dialog({
		title:'用户发票号段维护',
		href:'getUserInvoicePageCharge.action?bill_type=1&&rcpt_print_flag='+rcpt_print_flag
	});
	$('#dlg-tuifei').dialog('open');
}

//获取收据最大票号
function load_receiptis() {
	$("#fapiao")[0].checked=false;
	if ($("#shouju")[0].checked) {
		$("#bill_type").val(1);
//		getClientflag("dbgj",function(objvalue){
//			if(objvalue.error_flag=='0'){
//				$("#rcpt_print_flag1").val(objvalue.rcpt_print_flag);
			$.ajax({
				url : 'getMaxInvoiceNumCharge.action',
				data : {
					bill_type:1
//					user_id:objvalue.rcpt_print_flag
					},
				type : 'post',
				success:function(data){
					$("#invoice_num").val(data);
				},
				error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
//			}else{
//				$.messager.alert("操作提示", objvalue.error_msg, "error");
//			}
//		});	
		
		$("#shouju_msg").css("display", "block");
		$("#fapiao_msg").css("display", "none");
	} else {
		$("#fapiao_msg").css("display", "none");
		$("#shouju_msg").css("display", "none");
	}
}
//打印明细单
function mx_point(a){
	var reportlets = new Array();
	var sa = {
		"reportlet" : 'mx_point.cpt',
		"a" : a
	};
	reportlets.push(sa);
	var printurl = "../../ReportServer";
	var config = {
		url : printurl,
		isPopUp : true,
		data : {
			reportlets : reportlets
		}
	}
	FR.doURLPDFPrint(config);
}
//打印普通收费单
function pt_point(a){
	var reportlets = new Array();
	var sa = {
		"reportlet" : 'pt_point.cpt',
		"a" : a
	};
	reportlets.push(sa);
	var printurl = "../../ReportServer";
	var config = {
		url : printurl,
		isPopUp : true,
		data : {
			reportlets : reportlets
		}
	}
	FR.doURLPDFPrint(config);
}

//打印普通发票
function fapiao_point(a){
	var reportlets = new Array();
	var sa = {
		"reportlet" : 'fp_point.cpt',
		"a" : a
	};
	reportlets.push(sa);
	var printurl = "../../ReportServer";
	var config = {
		url : printurl,
		isPopUp : true,
		data : {
			reportlets : reportlets
		}
	}
	FR.doURLPDFPrint(config);
}
//打印明细发票
function fapiao_point_mx(a){
	var reportlets = new Array();
	var sa = {
		"reportlet" : 'fp_point_mx.cpt',
		"a" : a
	};
	reportlets.push(sa);
	var printurl = "../../ReportServer";
	var config = {
		url : printurl,
		isPopUp : true,
		data : {
			reportlets : reportlets
		}
	}
	FR.doURLPDFPrint(config);
}

//打印收据
function shouju_point(a){
//	window.location.href="invoiceService://"+1+"$"+a;
	var reportlets = new Array();
	var sa = {
		"reportlet" : 'fp_point.cpt',
		"a" : a
	};
	reportlets.push(sa);
	var printurl = "../../ReportServer";
	var config = {
		url : printurl,
		isPopUp : true,
		data : {
			reportlets : reportlets
		}
	}
	FR.doURLPDFPrint(config);
}
//打印收据明细
function shouju_point_mx(a){
//	window.location.href="invoiceService://"+1+"$"+a;
	var reportlets = new Array();
	var sa = {
		"reportlet" : 'fp_point_mx.cpt',
		"a" : a
	};
	reportlets.push(sa);
	var printurl = "../../ReportServer";
	var config = {
		url : printurl,
		isPopUp : true,
		data : {
			reportlets : reportlets
		}
	}
	FR.doURLPDFPrint(config);
}

function printreport_invoice(a){
	window.location.href="invoiceService://"+1+"$"+a;
}
function RunReportExe(strPath) {
	 location.href=strPath;
}
function RunExe(strPath) {
	try {
		var ws = new ActiveXObject("WScript.Shell");
		ws.Exec(strPath);
	} catch (e) {
		$.messager.alert("操作提示", '被浏览器拒绝：' + e,"error");
	}
}

function cksfjl(){
	if($("#ser_num").val().length>0){
		$("#dlg-show_sfjl").dialog({
	 		title:'个人收费记录',
	 		href:'showDetailCollectFeesCharge.action?exam_num='+$("#ser_num").val()
	 	});
	 	$("#dlg-show_sfjl").dialog('open');
	}else{
		$.messager.alert("操作提示", "体检人员无效","error");
	}
}
//获取所有被选中的收费方式的id
function jqchk(){
	var chk_value =[]; 
	$('input[name="sffs_box"]:checked').each(function(){ 
	chk_value.push($(this).val()); 
	}); 
//	alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value);
	return chk_value;
}
//登记
function webSocketRegistration(state,obj1,inter_class,PatNo,cash,chargeType,chk_value){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在登记中，请稍后。。。</font></div></div>';
	$("body").prepend(str);
	insurance_send('dbgj',inter_class,'2210','{"inter_class":"'+inter_class+'","trade_code":"2210","operator_id":"'+$("#userid").val()+'","operator_code":"'+$("#work_other_num").val()+'","operator_name":"'+$("#name").val()+'","patNo":"'+PatNo+'","pay_class":"01","peis_trade_code":"'+state.req_num+'"}',function(objvalue) {
//		 alert(objvalue.error_flag);
		 $(".loading_div").remove();
//		 alert(objvalue.error_flag)
			if(objvalue.error_flag=="1"){
				var obj=eval("("+objvalue.jsondata+")");
//				alert();
				$(".loading_div").remove();
				if(obj.erro_code=="0"){
//					$.messager.alert("操作提示", '登记成功',"info");
					webSocketCityHealth(state,obj1,inter_class,PatNo,cash,chargeType,chk_value);
				}else{
					$(".loading_div").remove();
					shujushanchu(state.req_num);
					$.messager.alert("操作提示", '登记失败',"error");
					
				}
			}else{
				$(".loading_div").remove();
				shujushanchu(state.req_num);
				$.messager.alert("操作提示", '登记失败',"error");
			}
		})
}
//市医保收费
function webSocketCityHealth(state,obj1,inter_class,PatNo,cash,chargeType,chk_value){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在收费中，请稍后。。。</font></div></div>';
	 $("body").prepend(str);
	 var amount=Number($("#shishou").html());
	 var exam_num=$("#ser_num").val();
	 var trade_code="2410";
	
	 insurance_send('dbgj',inter_class,trade_code,'{"inter_class":"'+inter_class+'","trade_code":"'+trade_code+'","operator_id":"'+$("#userid").val()+'","operator_code":"'+$("#work_other_num").val()+'","operator_name":"'+$("#name").val()+'","patNo":"'+PatNo+'","pay_class":"01","peis_trade_code":"'+state.req_num+'","peis_amount":"'+amount+'"}',function(objvalue) {
//		 alert(objvalue.error_flag);
//		 $(".loading_div").remove();
			if(objvalue.error_flag=="1"){
				var obj=eval("("+objvalue.jsondata+")");
				if(obj.erro_code=="0"){
					
					
					$.ajax({
						url : 'getInsureAccountCharge.action',
						data : {"req_nums":state.req_num},
						type : 'post',
						success:function(data){
							var state1 = eval("("+data+")");
			        		if(state1.flag == 'ok'){
								var total_amount=state1.info.split("-")[0];
								var account_pay=state1.info.split("-")[1];
								var cash_pay=state1.info.split("-")[2];
								var peis_trade_code=state1.req_num;
								if(Number(total_amount)!=Number(account_pay)){
									$("#inter_class").val(inter_class);
									$("#req_num").val(state.req_num);
				        			$("#wayCharge").val(chargeType);
//					        			cerate_socket(state,obj1);
				        			if(cash){
				        				state.flag = 'ok';
										ruku(state,obj1);
				        			}else{
				        				$("#posAmount").val(cash_pay);
				        				$("#posanniu").show();
//				        				checkCorrect(peis_trade_code,inter_class);
				        				if(chk_value.indexOf("115") != -1){
				        					$("#wayCharge").val("02");
				        				}else if(chk_value.indexOf("101") != -1||chk_value.indexOf("102") != -1){
				        					$("#wayCharge").val("00");
				        				}
				        				settlement_show(state,obj1,peis_trade_code);	
				        			}
								}else{
									state.flag = 'ok';
									ruku(state,obj1);
								}
			        		}else{
			        			$(".loading_div").remove();
			        			$.messager.alert("操作提示", "查询医保金额失败", "error");	
			        		}
							
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
					
					
					
					
				
				}else{
					$(".loading_div").remove();
//					shujushanchu(state.req_num);
					$.messager.alert("操作提示", '医保收费失败,'+obj.error_msg,"error");
				}
			}else{
				$(".loading_div").remove();
				shujushanchu(state.req_num);
				$.messager.alert("操作提示", '医保收费失败',"error");
			}
		})
}
//结算列表
var  state1 =null
var  obj2 =null
var peis_trade_code="";
function settlement_show(state,obj1,trade_code){
	state1 =state;
	obj2=obj1;
	peis_trade_code=trade_code;
	 $(".loading_div").remove();
	 var strurl='chargeConfirmationCharge.action';
	$("#dlg-fapiao").dialog({
		title:'确认收费',
		href:strurl,
	});
	$('#dlg-fapiao').dialog('open');
}
function posanniu(){
	 $(".loading_div").remove();
	 var strurl='chargeConfirmationCharge.action';
	$("#dlg-fapiao").dialog({
		title:'确认收费',
		href:strurl,
	});
	$('#dlg-fapiao').dialog('open');
}
function posjson(){
	shoufei_show(state1,obj2);
}
function save_pos(){
	var wayCharge =$("#wayCharge").val();
	var trade_class="";
	if(wayCharge=="02"){
		trade_class="00";
	}else /*if(wayCharge=="00")*/{
		trade_class="01";
	}
	var qrcode= $("#sfm").val();
	if(trade_class=="01"){
		if(qrcode.length!=18){
			$.messager.alert("操作提示", "请输入正确收费码！！！", "error");
			return;
		}
	}
	var chk_value =[]; 
	$('input[name="sffs_box"]:checked').each(function(){ 
	chk_value.push($(this).attr("childrencode")); 
	}); 
//	weChatAlipay(chk_value,qrcode);
	if(chk_value.indexOf("101") != -1){
		if(qrcode.substr(0,1)!="1"){
			$.messager.alert("操作提示", "收费码是支付宝收费码", "error");	
			return;
		}
	}
	if(chk_value.indexOf("102") != -1){
		if(qrcode.substr(0,1)!="2"){
			$.messager.alert("操作提示", "收费码是微信收费码", "error");	
			return;
		}
	}
    var req_num = $("#req_num").val();
   var inter_class= $("#inter_class").val();
   
    WebSocketPos(state1,obj2,req_num,qrcode,trade_class,inter_class,peis_trade_code);
}
//pos收费
function WebSocketPos(state,obj1,req_num,qrcode,trade_class,inter_class,peis_trade_code){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 var shishou = Number($("#shishou1").html());
	 var nowDate = new Date();
	  var year = nowDate.getFullYear();
	  var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
	  var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
	  var dateStr = year+""+ month+"" + day;
	 insurance_send('dbgj','01','00',
			 '{"inter_class":"01",'
		 +'"trade_class":"'+trade_class+'",'
		 +'"trade_op_code":"00",'
		 +'"pay_class":"01",'
		 +'"peis_trade_code":"'+req_num+'",'
		 +'"amount":"'+shishou+'",'
		 +'"operator_id":'+$("#center_id").val()+','
		 +'"operator_code":"'+$("#work_other_num").val()+'",'
		 +'"trade_date":"'+dateStr+'",'
		 +'"trade_no":"",'  //原交易参考号
		 +'"voucher_no":"",'  //原凭证号
		 +'"qrcode":"'+qrcode+'"}',function(objvalue) {
//		 alert(objvalue);
//		 $(".loading_div").remove(); 
		 $('#dlg-fapiao').dialog('close')
//		 alert(objvalue.error_flag)
		 	$("#posanniu").attr("style","display:none;");//隐藏div
			if(objvalue.error_flag=="1"){
				var obj=eval("("+objvalue.jsondata+")");
				if(obj.erro_code=="0"){
//					$(".loading_div").remove();
					state.flag = 'ok';
//					ruku(state,obj1);
					savePosRes(state,obj1,obj);
//					$.messager.alert('提示信息', "缴费成功！",'info');
				}else{
					$(".loading_div").remove();
					if(inter_class=="02"||inter_class=="03"){
						checkCorrect(peis_trade_code,inter_class);
					}
					shujushanchu(req_num);
					$.messager.alert("操作提示", '缴费失败'+obj.error_msg,"error");
				}
			}else{
				if(inter_class=="02"||inter_class=="03"){
					checkCorrect(peis_trade_code,inter_class);
				   }
				shujushanchu(req_num);
				$(".loading_div").remove();
				$.messager.alert("操作提示", '缴费失败',"error");
			}
		})
}
//读卡
function readCardAccBalance(inter_class,type_class,type){
	var inter ="市医保";
	var ner ="220";
	if(inter_class=="03"){
		inter ="省医保";
		ner ="358";
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在读卡中，请稍后。。。</font></div></div>';
	 $("body").prepend(str);
	 insurance_send('dbgj',inter_class,'2100','{"inter_class":"'+inter_class+'","trade_code":"2100","operator_id":"'+$("#userid").val()+'","operator_code":"'+$("#work_other_num").val()+'","operator_name":"'+$("#name").val()+'"}',function(objvalue) {
			if(objvalue.error_flag=="1"){
				$(".loading_div").remove();
				var obj=eval("("+objvalue.jsondata+")");
				if(obj.erro_code=="0"){
					$("#patNo").val(obj.PatNo);
					if(type_class=="1"){
						if(obj.sex!=$("#sex").html()){
							$(".loading_div").remove();
							$.messager.alert('提示信息', "体检人与医保卡性别不符!","error");
							return;
						}
						if(Number(obj.acc_balance)> Number($("#yingshou").html())){
								$("#sffs_"+ner).val(Number($("#yingshou").html()));
						}else{
							$("#sffs_"+ner).val(Number(obj.acc_balance));
						}
						$.ajax({
					        url:'save_insuranceCharge.action',  
					        data:{
					        	patNoDK:obj.PatNo,
					        	id_num:obj.id_num,
					        	name:obj.name,
					        	sex:obj.sex,
					        	birthday:obj.birthday,
					        	nation:obj.nation,
					        	acc_balance:obj.acc_balance,
					        	insurance_status:obj.insurance_status,
					        	ic:obj.ic,
					        	medical_type:inter_class,
					        	business_type:"1"
					          },          
					        type: "post",//数据发送方式   
					        success: function(text){
					        	$(".loading_div").remove();
					        	if(text.split("-")[0]!="ok"){
					        		$.messager.alert('提示信息', "保存医保卡信息失败！",'error');	
					        	}
					            },
					            error:function(){
					            	$(".loading_div").remove();
					            	$.messager.alert('提示信息', "操作失败！",'error');
					            }  
					    });
						showys(type);
					}else{
						$.messager.alert('提示信息', inter+"用户姓名为："+obj.name+"，卡余额为:"+obj.acc_balance,'info');	
					}
						
				}else{
					$(".loading_div").remove();
					$.messager.alert('提示信息', "读取卡失败！"+obj.error_msg,'error');
				}
//				var obj=eval(data);
			}else{
				$(".loading_div").remove();
				$.messager.alert('提示信息', "读取卡失败！"+obj.error_msg,'error');
			}
		})
}

//--------------冲正交易--------
function checkCorrect(req,inter_class){
	alert(req);
	if(req == null || req == ''){
		$.messager.alert("操作提示", "体检申请号错误！", "info");		
		return ;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
  	$.ajax({
		url : 'checkCorrectTransactionCharge.action', //校验交易是否能冲正
		data : {
			    "req_num":req
				},
				type : "post",//数据发送方式   
				success : function(data) {
					if("ok"==data.split("-")[0]){  // 可以冲正操作
							var obj = {
								 "inter_class":inter_class,   //接口类型(01:银联POS通;02:市医保;03:省医保)
								 "trade_code":"2421",      //业务编码(2421:冲正交易)
								 "operator_id":$("#center_id").val(),  //操作员ID
								 "operator_code":$("#work_other_num").val(),   //操作员工号
								 "operator_name":$("#name").val(),  //操作员姓名
								 "peis_trade_code":req, //被冲正体检交易流水号
								 "reversal_trade_code":"2410",//冲正业务编码(仅支持2210（入院登记），2310（处方明细上传），2410（费用结算）交易的冲正)
								 "reversal_reason":"02" //冲正原因(二级编码 冲正原因： 01 没有得到中心端响应  02 HIS系统处理失败 03 对账发现中心多数据 09 动态库自动冲正)
							}
						//----------医保申请----------	
						 insurance_send('dbgj',inter_class,2421,JSON.stringify(obj),function(objvalue) {//申请
						 	$(".loading_div").remove();
						 	if(objvalue.error_flag == '1'){//调用成功
						 		var val= eval('('+objvalue.jsondata+')');//接口返回结果
						 		if(val.erro_code == '0'){//成功
						 			$.messager.alert("操作提示", "冲正成功，请重新对账。。", "");
//						 			getmedicalInsuranceDetail();//重新加载数据
									chaxun();
						 		}else{//失败
						 			$.messager.alert("操作提示", val.error_msg, "error");		
									return;
						 		}
						 	}else{//失败
						 		$.messager.alert("操作提示", objvalue.error_msg, "error");		
								return;
						 	}
						 });
					}else{
						$(".loading_div").remove();
						$.messager.alert("操作提示", data.split("-")[1], "error");		
						return;
					}
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");		
					return;
				}
			});
  
}
function shoufeiBaocun(){
	var chk_value =[]; 
	$('input[name="sffs_box"]:checked').each(function(){ 
	chk_value.push($(this).attr("childrencode")); 
	}); 
	if(chk_value.indexOf("115") != -1){
		$("#wayCharge").val("02");
	}else if(chk_value.indexOf("101") != -1||chk_value.indexOf("102") != -1){
		$("#wayCharge").val("00");
	}
	if((chk_value.indexOf("101") != -1||chk_value.indexOf("102") != -1||chk_value.indexOf("115") != -1)&&chk_value.length==1){
		$("#posAmount").val($("#shishou").html());
		var strurl='chargeConfirmationCharge.action';
			$("#dlg-fapiao").dialog({
				title:'确认收费',
				href:strurl,
			});
			$('#dlg-fapiao').dialog('open');
	}else{
			shoufeiBaodao();
		
	}
	
}
function posAnniu(){
	var inter_class= $("#inter_class").val();
//	alert(inter_class);
	if(inter_class=="02"||inter_class=="03"){
		save_pos();
	}else{
		shoufeiBaodao();
	}
}

//  pos 返回信息
function  savePosRes(state,obj1,resinfo){
	var chargingids = new Array();
	var obj = $("input[name=ck]");
	var data = $('#witemlist').datagrid('getData');
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			chargingids.push(data.rows[i].item_id);
		}
	}
	var isPrintRecepit = "N";// 是否打印发票
	if ($("#fapiao")[0].checked||$("#shouju")[0].checked){
		isPrintRecepit = "Y";
	}
	var invoice_num=$("#invoice_num").val();
	if($("#bill_type").val()=="2"){
		invoice_num=$("#fapiao_num").val();
	}
	$.ajax({
		url : 'putStorageCharge.action',
		data : {
				"exam_num":$("#ser_num").val(),
				"req_nums":state.req_num,
				"invoice_type":$("#invoice_type").val(),
		        "invoice_num":invoice_num,
		        "bill_type":$("#bill_type").val(),
		        "chargingids":chargingids.toString(),
		        "rcpt_print_flag":$("#rcpt_print_flag1").val(),
		        "voucher_no":resinfo.voucher_no,
		        "isPrintRecepit":isPrintRecepit,
		        "trade_no":resinfo.trans_no
				},
				type : "post",//数据发送方式   
				success : function(text) {
						if (text.split("-")[0] == 'ok') {
							$(".loading_div").remove();
							alert_autoClose("操作提示", text.split("-")[1],"");
//							$.messager.alert("操作提示", text.split("-")[1], "info");	
							shoufei_show(state,obj1);
							$("#inter_class").val("");
							$("#req_num").val("");
							$("#wayCharge").val("");
						}else{
							$(".loading_div").remove();
							$.messager.alert("操作提示", text.split("-")[1], "error");
							shujushanchu(state.req_num);
//							$.messager.confirm('提示信息','您确认要重新入库吗？',function(r){
//								if(r){
//									ruku(state,obj1);
//								}
//							});
						}
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});	
}

//身份认证
function certification(cardnum,inter_class,type_class,type){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在身份认证中，请稍后。。。</font></div></div>';
	 $("body").prepend(str);
	var inter ="市医保";
	var ner ="220";
	if(inter_class=="03"){
		inter ="省医保";
		ner ="358";
	}
	var inputValueStr = JSON.stringify({
		 "data": {
			 "aaz500":cardnum,
			 "aaz501":"",
			 "bkz543":""
		 },
		 "usr": "350603010835060300202",
		 "pwd": "123",
		 "funid": "yb04.10.01.16"
		});
	 insurance_send(inter_class,"yb04.10.01.16",inputValueStr,function(val){
			var oj= translation(val);
			
			if(oj.flag=="1"){
				$(".loading_div").remove();
					$("#patNo").val(oj.data.aaz500);
					$("#ybname").val(oj.data.aac003);
					 
					if(type_class=="1"){
//						alert(oj.data.akc087+"  --  "+$("#sffs_220").val()+"   "+$("#yingshou").html());
						if(Number(oj.data.akc087)> Number($("#yingshou").html())){
								$("#sffs_220").val(Number($("#yingshou").html()));
						}else{
							$("#sffs_220").val(oj.data.akc087);
						}
						 
						$.ajax({
							url:'save_insuranceCharge.action',  
							data:{
								flag:oj.flag,
								cause:oj.cause,
								ginseng_administrative:oj.data.aab301,	   //参保地行政区划	varchar2(6)	y	
								ginseng_administrative_name:oj.data.aab301_mc,	   //参保地行政区划名称	varchar2(50)	y	
								nam_entity:oj.data.aab004,  //单位名称	varchar2(100)	n	
								pat_no:oj.data.aaz500,   //社会保障卡号	varchar2(20)	y	
								document_type:oj.data.aac058,   //证件类型	varchar2(3)	y	参见编码附件
								document_name:oj.data.aac058_mc,   //证件类型名称	varchar2(50)	y	
								document_number:oj.data.aac002,	   //证件号码（社会保障号）	varchar2(18)	y	
								personal_code:oj.data.aac999,	   //个人管理码	varchar2(20)	y	地市唯一标识码（id0000）
								name:oj.data.aac003,	   //姓名	varchar2(50)	y	
								sex:oj.data.aac004,	  //性别	varchar2(3)	y	
								sex_name:oj.data.aac004_mc,	   //别名称	varchar2(10)	y	
								date_birth:oj.data.aac006,  //出生日期	number(8)	y	
								medical_identification:oj.data.bka162,	   //医疗救助认定身份	varchar2(3)	n	
								medical_identification_name:oj.data.bka162_mc,	   //医疗救助认定身份名称	varchar2(50)	n	
								personal_account_balance:oj.data.akc087	  //个人账户余额	number(16,2)	y	
							  },          
							type: "post",//数据发送方式   
							success: function(text){
									$(".loading_div").remove();
									if(text.split("-")[0]!="ok"){
										$.messager.alert('提示信息', "保存医保卡信息失败！",'error');	
									}
								},
								error:function(){
									$(".loading_div").remove();
									$.messager.alert('提示信息', "操作失败！",'error');
								}  
						});
						showys(type);
					}else{
						$.messager.alert('提示信息', inter+"用户姓名为："+oj.data.aac003+"，卡余额为:"+oj.data.akc087,'info');	
					}
			}else{
				$.messager.alert("操作提示",state.cause, "error");
			}
	 });
}
//授权
function authorization(){
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在授权中，请稍后。。。</font></div></div>';
	 $("body").prepend(str);
	var xmlhttp;
	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	  }	else{// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			insturance_log("res","02","yb04.07.01.01",xmlhttp.responseText);
			var state = eval("("+xmlhttp.responseText+")");
			if(state.flag=="1"){
				$.messager.alert("操作提示",state.cause, "info");
			}else{
				$.messager.alert("操作提示",state.cause, "error");
			}
//		alert(xmlhttp.responseText);
//		document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
		}else{
			$(".loading_div").remove();
			//$.messager.alert("操作提示",'授权失败', "error");
		}
	  }
	var url = "http://localhost:7999/sieaf/authorization/";
	xmlhttp.open("post", url, true);
	//xmlhttp.open("GET","demo_get.asp",true);
	var user = "350603010835060300202";
	var pwd = "123";
	var data = JSON.stringify({
	"usr": user,
	"pwd": pwd
	});
	insturance_log("req","02","yb04.07.01.01",data);
	xmlhttp.send(data);
}
//登记
function registration(state,obj1,inter_class,PatNo,cash,chargeType,chk_value){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在登记中，请稍后。。。</font></div></div>';
	$("body").prepend(str);
	var akc190;
	var ybname=$("#ybname").val();
	var PatNo = $("#patNo").val();
	 
	var inputValueStr = JSON.stringify( {
		 "data": {
			 "aaz500": PatNo,//	社会保障卡号	varchar2(20)	y
			 "aac002": PatNo,
			 "aac003": ybname,//	姓名	varchar2(50)	y
			 "aka078": "10",//	医疗就诊方式	varchar2(3)	y
			 "aka130": "11",//	医疗类别	varchar2(3)	y
			 "bke042": state.req_num,//	his流水号	varchar2(50)	y
			 "akf001": "",
			 "akf002": "",
			 "bkf237": "001",//	his内部科室编码	varchar2(50)	y
			 "bkf238": "体检科",//	his内部科室名称	varchar2(50)	y
			 "akc069": "0",//	急诊标志	varchar2(3)	y
			 "akc066": "0",//	外伤标志	varchar2(3)	y
			 "akc192": "20200101",//	实际门诊/入院日期	number(14)	y
			 "aae030": "",
			 "aae031": "",
			 "bke241": "01", //	数据来源	varchar2(3)	y
			 "aaz501": "",
			 "akc193": ""
		 },
		 "usr": "350603010835060300202",
		 "pwd": "123",
		 "funid": "yb04.07.01.01"
		});
	insurance_send(inter_class,"yb04.07.01.01",inputValueStr,function(obj){
		var oj= translation(obj);
		if(oj.flag=="1"){
			$(".loading_div").remove();
			uploadDetail(state,obj1,inter_class,PatNo,cash,chargeType,chk_value,oj.data.akc190);
		}else{
			$(".loading_div").remove();
			$.messager.alert("操作提示",state.cause, "error");
		}
	});
	
}
//读取卡号
function readCard(inter_class,type_class,type){
	
	var xmlhttp;
	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	  }	else{
		  // code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  xmlhttp.onreadystatechange=function(){
		  $(".loading_div").remove();
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			insturance_log("res","02","dk",xmlhttp.responseText);
			  var obj = eval("("+xmlhttp.responseText+")");
			  certification(obj.data.cardNum,inter_class,type_class,type)
		}
	  }
	var url = "http://localhost:7999/sieaf/read_cardnum/";
	xmlhttp.open("post", url, true);
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在读卡中，请稍后。。。</font></div></div>';
	 $("body").prepend(str);
	var data = JSON.stringify({});
	insturance_log("req","02","dk",data);
	xmlhttp.send(data);
}
//页面点击读取卡号
function readCardym(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在读卡中，请稍后。。。</font></div></div>';
	 $("body").prepend(str);
	var xmlhttp;
	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	  }	else{
		  // code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			insturance_log("res","02","dk",xmlhttp.responseText);
			  var obj = eval("("+xmlhttp.responseText+")");
				var inputValueStr1 = JSON.stringify({
					 "data": {
						 "aaz500":obj.data.cardNum,
						 "aaz501":"",
						 "bkz543":""
					 },
					 "usr": "350603010835060300202",
					 "pwd": "123",
					 "funid": "yb04.10.01.16"
					});
				 insurance_send("02","yb04.10.01.16",inputValueStr1,function(val){
						var oj1= translation(val);
						if(oj1.flag=="1"){
							$(".loading_div").remove();
							$.messager.alert('提示信息', "用户姓名为："+oj1.data.aac003+"，卡余额为:"+oj1.data.akc087,'info');	
						}else{
							$.messager.alert("操作提示",state.cause, "error");
						}
				 });
		}
	  }
	var url = "http://localhost:7999/sieaf/read_cardnum/";
	xmlhttp.open("post", url, true);
	var data = JSON.stringify({});
	insturance_log("req","02","dk",data);
	xmlhttp.send(data);
}
//上传明细
function uploadDetail(state,obj1,inter_class,PatNo,cash,chargeType,chk_value,akc190){

	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在上传明细中，请稍后。。。</font></div></div>';
	$("body").prepend(str);
	var amount=Number($("#shishou").html());
	$.ajax({
        url:'save_uploadDetailCharge.action',  
        data : {"req_nums":state.req_num,
        	 	"prescription_num":$("#prescription").combobox('getValue'),
        	 	"prescription_name":$("#prescription").combobox('getText')
             },
        type: "post",//数据发送方式   
        success: function(text){
        	$(".loading_div").remove();
        	var objstar = eval("("+text+")");
        	var ybname=$("#ybname").val();
        	var inputValueStr = JSON.stringify( {
        		 "data": {
        			 "aaz500":PatNo,//	社会保障卡号
        			 "aac003":ybname,//	姓名
        			 "aac002":"",//	证件号码（社会保障号）
        			 "aka078":"10",//	医疗就诊方式
        			 "akc190":akc190,//	医保流水号
        			 "bke042":state.req_num,//	his流水号
        			 "itemcount":objstar.length,//	明细项目数量
        			 "akb065":amount,//	医疗费总金额
        			 "bke241":"01",//	数据来源
        			 "mxlist":objstar,
        			 "zdlist": [{
        					"bke087": "01",
        					"bke088": "症状名称1",
        					"bke301": "01",
        					"bke302": "01"
        				}]
        		 },
        		 "usr": "350603010835060300202",
        		 "pwd": "123",
        		 "funid": "yb04.07.01.03"
        		});
	        	insurance_send(inter_class,"yb04.07.01.03",inputValueStr,function(obj){
		        	var oj= translation(obj);
		 			if(oj.flag=="1"){
		 				$(".loading_div").remove();
		 				insuranceFee(state,obj1,inter_class,PatNo,cash,chargeType,chk_value,akc190,objstar.length,oj.data.bke297)
		 			}else{
		 				$(".loading_div").remove();
		 				$.messager.alert("操作提示",state.cause, "error");
		 			}
	        	});
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",'error');
            }  
    });
	
}
//收费
function insuranceFee(state,obj1,inter_class,PatNo,cash,chargeType,chk_value,akc190,mx_num,bke297){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在收费中，请稍后。。。</font></div></div>';
	$("body").prepend(str);
	var amount=Number($("#shishou").html());
	 var nowDate = new Date();
	  var year = nowDate.getFullYear();
	  var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
	  var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
	  var dateStr = year+""+ month+"" + day;
	
        	var ybname=$("#ybname").val();
        	var inputValueStr = JSON.stringify( {
        		 "data": {
        			 "aaz500":PatNo,//	社会保障卡号
        			 "aac002":"",//	证件号码(社会保障号)
        			 "aac003":ybname,//	姓名
        			 "aka078":"10",//	医疗就诊方式
        			 "aka130":"11",//	医疗类别
        			 "akc190":akc190,//	医保流水号
        			 "bke298":state.req_num,//	his收费单据流水号
        			 "aaz149":"",//	病种编码    n
        			 "bke286":"2",//	结算标识    n
        			 "bkc014":dateStr,//	实际就诊日期   
        			 "bkc171":"",//	实际就诊时间    n
        			 "akc194":"",//	出院日期    n
        			 "bke078":"9",//	离院方式
        			 "itemcount":mx_num,//	处方项目数
        			 "akc227":amount,//	医疗费总金额
        			 "bke058":"",//	住院天数    n
        			 "amc029":"",//	计划生育手术类别    n
        			 "amc026":"",//	生育类别    n
        			 "amc028":"",//	胎儿数    n
        			 "amc020":"",//	计划生育手术或生育日期    n
        			 "bmc041":"",//	怀孕天数    n
        			 "bke241":"01",//	数据来源
        			 "bka190":"N",//	是否共济扣款
        			 "cflist": [{
        				 "bke297":bke297,//	医保明细流水号
        				}]
        		 },
        		 "usr": "350603010835060300202",
        		 "pwd": "123",
        		 "funid": "yb04.07.01.04"
        		});
	        	insurance_send(inter_class,"yb04.07.01.04",inputValueStr,function(obj){
		        	var oj= translation(obj);
					if(oj.flag != '1'){
						$(".loading_div").remove();
						$.messager.alert("操作提示", oj.cause, "error");	
						return;
					}
					  var st = JSON.stringify(oj.data);
//					  alert("发送收费的包"+st);
		 			if(obj.flag=="1"){
//		 				$(".loading_div").remove();
							$.ajax({
								url : 'getInsureAccountCharge.action',
								data : {"req_nums":state.req_num,
									    "insureAccount":st,
									    "medical_type":"01"},
								type : 'post',
								success:function(data){
									var state1 = eval("("+data+")");
					        		if(state1.flag == 'ok'){
										var total_amount=state1.info.split("-")[0];
										var account_pay=state1.info.split("-")[1];
										var cash_pay=state1.info.split("-")[2];
										var peis_trade_code=state1.req_num;
										if(Number(total_amount)!=Number(account_pay)){
											$("#inter_class").val(inter_class);
											$("#req_num").val(state.req_num);
						        			$("#wayCharge").val(chargeType);
	//						        			cerate_socket(state,obj1);
						        			if(cash){
						        				state.flag = 'ok';
												ruku(state,obj1);
						        			}else{
						        				$(".loading_div").remove();
						        				$("#posAmount").val(cash_pay);
						        				$("#posanniu").show();
	//					        				checkCorrect(peis_trade_code,inter_class);
						        				if(chk_value.indexOf("115") != -1){
						        					$("#wayCharge").val("02");
						        				}else if(chk_value.indexOf("101") != -1||chk_value.indexOf("102") != -1){
						        					$("#wayCharge").val("00");
						        				}
						        				settlement_show(state,obj1,peis_trade_code);	
						        			}
										}else{
											//交易对账
											$(".loading_div").remove();
											 trading(state,obj1,oj.data.akc190,oj.data.aae072,inter_class);
											
										}
					        		}else{
					        			$(".loading_div").remove();
					        			$.messager.alert("操作提示", "医保收费失败", "error");	
					        		}
									
								},
								error : function() {
									$(".loading_div").remove();
									$.messager.alert("操作提示", "操作错误", "error");					
								}
							});
							
		 			}else{
		 				$.messager.alert("操作提示",state.cause, "error");
		 			}
	        	});
	
}
//对账
function trading(state,obj1,medical_req_num,medical_charge_req_num,inter_class){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在交易对账中，请稍后。。。</font></div></div>';
	$("body").prepend(str);
	var inputValueStr = JSON.stringify( {
		 "data": {
			 "aka078":"10",//	医疗就诊方式
			 "akc190":medical_req_num,//	医保流水号
			 "aae072":medical_charge_req_num,//	医保收费流水号
			 "bke298":state.req_num//	his收费流水号
		 },
		 "usr": "350603010835060300202",
		 "pwd": "123",
		 "funid": "yb04.07.03.05"
		});
	insurance_send(inter_class,"yb04.07.03.05",inputValueStr,function(obj){
		var oj= translation(obj);
		if(oj.flag=="1"){
			state.flag = 'ok';
			ruku(state,obj1);
		}else{
			$(".loading_div").remove();
			checkCorrect(req,inter_class,medical_req_num,medical_charge_req_num);
		}
	});
	
}
//--------------冲正交易--------
function checkCorrect(req,inter_class,medical_req_num,medical_charge_req_num){
	if(req == null || req == ''){
		$.messager.alert("操作提示", "体检申请号错误！", "info");		
		return ;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
  	$.ajax({
		url : 'checkCorrectTransactionCharge.action', //校验交易是否能冲正
		data : {
			    "req_num":req
				},
				type : "post",//数据发送方式   
				success : function(data) {
					if("ok"==data.split("-")[0]){  // 可以冲正操作
						var inputValueStr = JSON.stringify({
							 "data": {
								 "funid":"yb04.07.01.04",	//原交易功能代码
								 "aka078":"60",	//医疗就诊方式
								 "akc190":medical_req_num,	//医保流水号
								 "aae072":medical_charge_req_num	//医保收费流水号
							 },
							 "usr": "350603010835060300202",
							 "pwd": "123",
							 "funid": "yb04.07.03.04"
							});
						insurance_send(inter_class,"yb04.07.03.04",inputValueStr,function(obj){
							var oj= translation(obj);
							if(oj.flag=="1"){
								$(".loading_div").remove();
								uploadDetail(state,obj1,inter_class,PatNo,cash,chargeType,chk_value,oj.akc190);
							}else{
								$.messager.alert("操作提示",state.cause, "error");
							}
						});
					}else{
						$(".loading_div").remove();
						$.messager.alert("操作提示", data.split("-")[1], "error");		
						return;
					}
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");		
					return;
				}
			});
  
}
