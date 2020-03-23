$(document).ready(function () {
	
	$('input',$('#tj_amount').next('span')).blur(function(){ 
		tj_jine();
	});
	$('input',$('#his_amount').next('span')).blur(function(){ 
		his_jine();
	});
	
	getwitemGrid();
	
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
	
});
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
}

var olddiscount,oldamount;

function getwitemGrid(){//查询未收费项目列表
	 var lastIndex;
	 var model={"exam_num":$("#ser_num").val()};
     $("#witemlist").datagrid({
	 url:'getPricingWitemList.action',
	 dataType: 'json',
	 queryParams:model,
	 rownumbers:true,
	 toolbar:'#toolbar',
     pageSize: 15,//每页显示的记录条数，默认为10 
     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	 columns:[[
	    {align:'center',field:'ck',checkbox:true},
	    {align:'center',field:'item_id',title:'项目ID',width:12},
	    {align:'center',field:'item_code',title:'项目编码',width:12},
	 	{align:'center',field:'item_name',title:'项目名称',width:15},
	 	{align:'center',field:'dep_name',title:'科室名称',width:12},
	 	{align:'center',field:'exam_status_y',title:'检查状态',width:12},
	 	{align:'center',field:'item_amount',title:'金额(元)',width:10},
	 	{align:'center',field:'itemnum',title:'数量',width:10},
	 	{align:'center',field:'discount',title:'折扣',width:10,editor:{type:'numberbox',options:{precision:4}}},
	 	{align:'center',field:'amount',title:'折后金额(元)',width:15,editor:{type:'numberbox',options:{precision:4}}},
//	 	{align:'center',field:'personal_pay',title:'个人付费金额(元)',width:20},
//	 	{align:'center',field:'team_pay',title:'单位付费金额(元)',width:20},
	 	{align:'center',field:'tj_charge_amount',title:'体检收费(元)',width:15},
	 	{align:'center',field:'tj_charge_status',title:'体检收费状态',width:15,"formatter":f_status},
	 	{align:'center',field:'his_charge_amount',title:'his收费(元)',width:15},
	 	{align:'center',field:'his_charge_status',title:'his收费状态',width:15,"formatter":f_status},
	 	{align:'center',field:'pay_status',title:'总收费状态',width:15,"formatter":f_status},
	 	{align:'center',field:'pay_mode_y',title:'付费类型',width:15},
	 	{align:'center',field:'creater',title:'登记人',width:10}	,
	 	{align:'center',field:'create_time',title:'登记时间',width:15},
	 	{align:'center',field:'his_req_status_y',title:'HIS申请',width:15},
	 	{align:'center',field:'is_application',title:'LIS/PACS申请',width:15},
	 	{align:'center',field:'is_print_recepit',title:'发票打印',width:13,"formatter":f_status}
	 	]],	    	
    	onLoadSuccess:function(value){
    		$("#datatotal").val(value.total);
    		$('#witemlist').datagrid('hideColumn','is_application');
    		$('#witemlist').datagrid('hideColumn','item_id');
    		$('#witemlist').datagrid('hideColumn','req_num');
//			$('#witemlist').datagrid('checkAll');
    	},
    collapsible:true,
	pagination: false,
    fitColumns:true,
    striped:true,
    fit:true,
    checkOnSelect:false,
    toolbar:toolbar,
    onClickCell:function(index, field, row){
    	var rows = $('#witemlist').datagrid('getRows');
			 var row = rows[index];
    	if(row.tj_charge_amount == 0 && row.his_charge_amount == 0 && row.pay_status == 'N' && row.his_req_status == 'N'){
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
    	}else{
    		return;
    	}
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
function f_status(val,row){
	 return val == 'Y' ? "已" : "未";
}
var  toolbar=[{
		text:'划价',
		width:90,
		iconCls:'icon-edit',
	    handler:function(){
	    	verification();
			}
	  },{text:'撤销划价',
	     width:100,
	     iconCls:'icon-cancel',
         handler:function(){
	     cancel_pricing();
		 }
        },{text:'收费',
	     width:100,
	     iconCls:'icon-save',
         handler:function(){
         	jiaoyan();
		 }
        },{text:'退费',
	     width:100,
	     iconCls:'icon-edit',
         handler:function(){
         	var obj = $("input[name=ck]");
			var data = $('#witemlist').datagrid('getData');
			var num = 0 ;
			for(var i=0;i<obj.length;i++){
				if(obj[i].checked == true){
					if(data.rows[i].tj_charge_status == 'N' && data.rows[i].his_charge_status == 'N' && data.rows[i].pay_status == 'N'){
						$.messager.alert('提示信息','收费项目[ '+data.rows[i].item_name+' ] 未缴费无需退费,操作不能继续！！','info');
						return ;
					}
					num++;
				}
			}
			if(num == 0){
				$.messager.alert('提示信息','请选择收费项目!!','warning');
				return ;
			}
         	getItemList();
         	addItemList();
         	addItemList1();
         	$("#dlg-refund").dialog('open');
		 }
        },{text:'缴费申请',
	     width:100,
	     iconCls:'icon-save',
         handler:function(){
         	var exam_num = $("#ser_num").val();
			if(exam_num == ''){
				$.messager.alert('提示信息', '请输入体检号!','error');
				return;
			}
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({
				url : 'pricingPaymentApplication.action',
				data : {exam_num:exam_num},
				type : 'post',
				success:function(data){
					$(".loading_div").remove();
					$.messager.alert('提示信息', data,function(){});
					chaxun();
				},
				error:function(){
					$(".loading_div").remove();
					$.messager.alert('提示信息', '发送缴费申请失败!','error');
				}
			});
         }
        }];

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
				if(row.tj_charge_amount <= 0 && row.his_charge_amount <= 0 && row.pay_status == 'N' && row.his_req_status == 'N'){
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


//回车查询
function serch_cls(dom){
	$(dom).unbind("keydown").keydown(function (e) {
	    if (e.which == 13) {
	    	chaxun();
	    }
    });
}

function  pp(){
	$('#itme_pricing').datagrid({    
	    columns:[[
		    {align:'center',field:'ckp',checkbox:true},
		    {align:'center',field:'id',title:'ID',width:10},
		    {align:'center',field:'item_code',title:'项目编码',width:12},
		 	{align:'center',field:'item_name',title:'项目名称',width:15},
		 	{align:'center',field:'dep_name',title:'科室名称',width:12},
		 	{align:'center',field:'exam_status_y',title:'检查状态',width:12},
		 	{align:'center',field:'item_amount',title:'金额(元)',width:10},
		 	{align:'center',field:'discount',title:'折扣',width:10},
		 	{align:'center',field:'amount',title:'折后金额(元)',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	  		    $("#itme_pricing").datagrid("hideColumn", "id");
	    	},
	    collapsible:true,
		pagination: false,
	    fitColumns:true,
	    striped:true,
	    fit:true,
	    striped:true,
	    nowrap:true,
	    rownumbers:true
	});  
	
	$('#teamItme_pricing').datagrid({    
	    columns:[[
	        {align:'center',field:'id',title:'ID',width:10,hidden:'true'},
		    {align:'center',field:'item_code',title:'项目编码',width:12},
		 	{align:'center',field:'item_name',title:'项目名称',width:15},
		 	{align:'center',field:'dep_name',title:'科室名称',width:12},
		 	{align:'center',field:'exam_status_y',title:'检查状态',width:12},
		 	{align:'center',field:'item_amount',title:'金额(元)',width:10},
		 	{align:'center',field:'discount',title:'折扣',width:10},
		 	{align:'center',field:'amount',title:'折后金额(元)',width:15},
		 	{align:'center',field:'pay_mode',title:'付费类型',width:10},
		 	{align:'center',field:'tj_amount',title:'体检收费(元)',width:15},
		 	{align:'center',field:'his_amount',title:'his收费(元)',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	  			$("#teamItme_pricing").datagrid("hideColumn", "id");
	  			$("#teamItme_pricing").datagrid("hideColumn", "pay_mode");
	    	},
	    collapsible:true,
		pagination: false,
	    fitColumns:true,
	    striped:true,
	    fit:true,
	    striped:true,
	    nowrap:true,
	    rownumbers:true
	});  
}



function selectPro(){
	$('#tj_amount').numberbox('setValue',0);
	$('#his_amount').numberbox('setValue',0);
	$("#save_pricing").hide();
	$("#yingshouje").html($("#yingshou").html());
	cilck_tab();
	//清空原有数据
    var item = $('#itme_pricing').datagrid('getRows');
    if (item) {
        for (var i = item.length - 1; i >= 0; i--) {
            var index = $('#itme_pricing').datagrid('getRowIndex', item[i]);
            $('#itme_pricing').datagrid('deleteRow', index);
        }
    }
    $('#itme_pricing').datagrid('loadData', { total: 0, rows: [] }); 
	
	var obj = $("input[name=ck]");
	var data = $('#witemlist').datagrid('getData');
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			$('#itme_pricing').datagrid('insertRow',{
				index: i,
				row: {
				id:data.rows[i].id,
			    item_code:data.rows[i].item_code,
	            item_name:data.rows[i].item_name,
	            dep_name:data.rows[i].dep_name,
	            exam_status_y:data.rows[i].exam_status_y,
	            item_amount:data.rows[i].item_amount,
	            discount:data.rows[i].discount,
	            amount:data.rows[i].amount
				}
			});
		}
	}
	$('#itme_pricing').datagrid('checkAll');
	select_teamItme_pricing();
  }
function  cilck_tab(){
	var tab = $('#tab_ss').tabs('getSelected');
	var index = $('#tab_ss').tabs('getTabIndex',tab);
	if(index == '0'){
		$("#tj").show();
    	$("#his").show();
    	$("#save_pricing").hide();
	}else if(index == '1'){
		$("#tj").hide();
    	$("#his").hide();
    	$("#save_pricing").show();
	}
	
	$('#tab_ss').tabs({
            border: false,
            onSelect: function (title) {
                if(title=='按项目总额划价'){
                	$("#tj").hide();
                	$("#his").hide();
                	$("#save_pricing").show();
                }else{
                	$("#tj").show();
                	$("#his").show();
                	$("#save_pricing").hide();
                }
            }
       });
}
function  select_teamItme_pricing(){
	//清空原有数据
    var item = $('#teamItme_pricing').datagrid('getRows');
    if (item) {
        for (var i = item.length - 1; i >= 0; i--) {
            var index = $('#teamItme_pricing').datagrid('getRowIndex', item[i]);
            $('#teamItme_pricing').datagrid('deleteRow', index);
        }
    }
    $('#teamItme_pricing').datagrid('loadData', { total: 0, rows: [] }); 
	
	var obj = $("input[name=ck]");
	var data = $('#witemlist').datagrid('getData');
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			$('#teamItme_pricing').datagrid('insertRow',{
				index: i,
				row: {
				id:data.rows[i].id,
			    item_code:data.rows[i].item_code,
	            item_name:data.rows[i].item_name,
	            dep_name:data.rows[i].dep_name,
	            exam_status_y:data.rows[i].exam_status_y,
	            item_amount:data.rows[i].item_amount,
	            discount:data.rows[i].discount,
	            amount:data.rows[i].amount
				}
			});
		}
	}
	
}



function tj_jine(){
	qinlin();
	var yingshouje = 0.0;
	yingshouje =  $("#yingshouje").html();
	var amount  = Number($("#tj_amount").val());
	var  his_amount = decimal((yingshouje-amount),4);
	if(amount <= yingshouje){
		$('#his_amount').numberbox('setValue',his_amount);
	}else{
		$('#his_amount').numberbox('setValue',0);
	}
	
	if($("#tj_amount").val() < 0 ||$("#tj_amount").val() == ''){
		$('#tj_amount').numberbox('setValue',0);
		alert_autoClose("操作提示", "金额不能小于0","");
	}else if(amount > yingshouje){
		alert_autoClose("操作提示", "不能大于应收金额","");
	}else {
		var datas = $('#teamItme_pricing').datagrid('getData');
		if (!datas.rows.length == 0) {
			for (var j = 0; j < datas.rows.length; j++)//已选择
			{
				var row = datas.rows[j];
				row.tj_amount = 0 ;
				if(amount > 0){
			 		if(amount >= Number(row.amount)){
				 		amount= amount - row.amount;
				 		row.tj_amount = row.amount;
				 		row.pay_mode = 0 ;
						 	}else if(amount < Number(row.amount)){
						 		row.tj_amount = decimal(amount,4);
						 		row.his_amount =  decimal((row.amount - amount),4);
						 		his_amount - amount;
						 		amount = 0 ;
						 		row.pay_mode = 2 ;
						 	}
				 	}else if(amount == 0){
				 		if(row.amount  > (row.tj_amount + row.his_amount)){
				 			row.his_amount = decimal((row.amount - row.tj_amount),4);
				 			his_amount = his_amount -row.his_amount;
				 			row.pay_mode = 1 ;
				 		}
				 	}
				 $('#teamItme_pricing').datagrid('refreshRow', j);
			}            
		}
	}
}
function  his_jine(){
	var yingshouje = 0.0;
	yingshouje =  $("#yingshouje").html();
	var  his_amount = yingshouje - Number($("#tj_amount").val());
	$('#his_amount').numberbox('setValue',his_amount);
}


function  qinlin(num){
	var datas = $('#teamItme_pricing').datagrid('getData');
	if (!datas.rows.length == 0) {
			for (var j = 0; j < datas.rows.length; j++)//已选择
			{
				var row = datas.rows[j];
					row.tj_amount = 0 ;
					row.his_amount = 0 ;
				$('#teamItme_pricing').datagrid('refreshRow', j);
			}            
		}
}

function save_amount(num){
	var  tj_examInfoCharingItem  = new Array();
		if(num == 0 || num == 1){
			var rows = $('#itme_pricing').datagrid('getSelections');
			if(rows.length <= 0){
				$.messager.alert('提示信息', "请选择至少一个项目！！",'warning');
				return;
			}
			for(var i= 0 ; i< rows.length ; i++){
				tj_examInfoCharingItem.push({"id":rows[i].id,
										  "amount":rows[i].amount,
										  "discount":rows[i].discount});
			}
			$.ajax({
		        url:'savePricing.action',  //保存划价
		        data:{
		          exam_num:$("#ser_num").val(),
		          examInfoCharingItems:JSON.stringify(tj_examInfoCharingItem),
		          pay_mode:num
		          },          
		        type: "post",//数据发送方式   
		        success: function(data){
		        	var mes = data.split("-");
			        	if(mes[0] == 'ok'){
			        		$.messager.alert('提示信息',mes[1],'');
			        		$('#dlg-huajia').dialog('close');
			        		getwitemGrid();
			        	}else{
			        		$.messager.alert('提示信息',mes[1],'error');
			        	}
		            },
		            error:function(){
		            	$.messager.alert('提示信息', "操作失败！",function(){});
		            }  
		    });
			
		}else if(num == 2){
			var datas = $('#teamItme_pricing').datagrid('getData');
			if(datas.rows.length <= 0){
				$.messager.alert('提示信息', "请选择至少一个项目！！",'warning');
				return;
			}
			for(var i= 0 ; i< datas.rows.length ; i++){
				tj_examInfoCharingItem.push({"id":datas.rows[i].id,
										  "his_charge_amount":datas.rows[i].his_amount,
										  "tj_charge_amount":datas.rows[i].tj_amount,
										  "pay_mode":datas.rows[i].pay_mode,
										  "amount":datas.rows[i].amount,
										  "discount":datas.rows[i].discount});
			}
			if((Number($("#tj_amount").val())+Number($("#his_amount").val())) < $("#yingshouje").html()){
				$.messager.alert('提示信息', "请输入收费金额！！",'warning');
				return;
			}
			$.ajax({
		        url:'savePricing.action',  //保存划价
		        data:{
		          exam_num:$("#ser_num").val(),
		          examInfoCharingItems:JSON.stringify(tj_examInfoCharingItem),
		          pay_mode:num
		          },          
		        type: "post",//数据发送方式   
		        success: function(data){
		        	var mes = data.split("-");
			        	if(mes[0] == 'ok'){
			        		$.messager.alert('提示信息',mes[1],'');
			        		$('#dlg-huajia').dialog('close');
			        		getwitemGrid();
			        	}else{
			        		$.messager.alert('提示信息',mes[1],'error');
			        	}
		            },
		            error:function(){
		            	$.messager.alert('提示信息', "操作失败！",function(){});
		            }  
		    });
		}else{
			return;
		}
}
function verification(){
	var obj = $("input[name=ck]");
	var data = $('#witemlist').datagrid('getData');
	var num = 0 ;
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			if(data.rows[i].pay_status == 'Y' || data.rows[i].his_req_status == 'Y'){
				$.messager.alert('提示信息','收费项目[ '+data.rows[i].item_name+' ] 已经交費,操作不能继续！！','info');
				return ;
			}
			if(data.rows[i].tj_charge_amount > 0 || data.rows[i].his_charge_amount > 0){
				$.messager.alert('提示信息','收费项目[ '+data.rows[i].item_name+' ] 已经划价完成,请不要重复操作！！','info');
				return ;
			}
			num++;
		}
	}
	if(num == 0){
		$.messager.alert('提示信息','请选择收费项目!!','warning');
		return ;
	}
	$("#dlg-huajia").window('open');
	pp();
	selectPro();
}
function jiaoyan(){
	var obj = $("input[name=ck]");
	var data = $('#witemlist').datagrid('getData');
	var num = 0 ;
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			if(data.rows[i].pay_status == 'Y' || data.rows[i].his_req_status == 'Y'){
				$.messager.alert('提示信息','收费项目[ '+data.rows[i].item_name+' ] 已经交費,操作不能继续！！','info');
				return ;
			}
			if(data.rows[i].tj_charge_amount < 0 && data.rows[i].his_charge_amount < 0){
				$.messager.alert('提示信息','收费项目[ '+data.rows[i].item_name+' ] 未完成划价,操作不能继续！！','info');
				return ;
			}
			num++;
		}
	}
	if(num == 0){
		$.messager.alert('提示信息','请选择收费项目!!','warning');
		return ;
	}
	$('#dlg-shoufei').dialog('open');
	 getCharingType();
	 getChargeItemList();
	 addChargeItem();
}

function  cancel_pricing(){
	var ids = new Array();
	var obj = $("input[name=ck]");
	var data = $('#witemlist').datagrid('getData');
	
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			if(data.rows[i].tj_charge_amount < 0 && data.rows[i].his_charge_amount < 0){
				$.messager.alert('提示信息','收费项目[ '+data.rows[i].item_name+' ] 未划价,无需撤销！！','info');
				return ;
			}else if(data.rows[i].his_req_status == 'Y' || data.rows[i].pay_status == 'Y' || data.rows[i].his_charge_status == 'Y' || data.rows[i].tj_charge_status == 'Y'){
				$.messager.alert('提示信息','收费项目[ '+data.rows[i].item_name+' ] 已收费,不能撤销！！','info');
				return ;
			}else{
				ids.push(data.rows[i].id);
			}
		}
	}
	if(ids.length <= 0){
		$.messager.alert('提示信息','请选择收费项目!!','warning');
		return ;
	}
	$.messager.confirm('确认对话框', '您确定要撤销选中信息吗？', function(r){
		if(r){
			$.ajax({
	        url:'cancel_pricing.action',  //保存划价
	        data:{
	          exam_num:$("#ser_num").val(),
	          eci_ids:ids.toString()
	          },          
	        type: "post",//数据发送方式   
	        success: function(data){
	        	var mes = data.split("-");
		        	if(mes[0] == 'ok'){
		        		$.messager.alert('提示信息',mes[1],'');
		        		$('#dlg-huajia').dialog('close');
		        		getwitemGrid();
		        	}else{
		        		$.messager.alert('提示信息',mes[1],'error');
		        	}
	            },
	            error:function(){
	            	$.messager.alert('提示信息', "操作失败！",function(){});
	            }  
		    });
		}
		
	});
}
//----------------------------------划价收费----------------------------------------

function getChargeItemList(){
	$('#chargeItemList').datagrid({    
	    columns:[[
	        {align:'center',field:'id',title:'ID',width:10,hidden:'true'},
	        {align:'center',field:'item_id',title:'项目ID',width:12},
		    {align:'center',field:'item_code',title:'项目编码',width:12},
		 	{align:'center',field:'item_name',title:'项目名称',width:15},
		 	{align:'center',field:'dep_name',title:'科室名称',width:12},
		 	{align:'center',field:'exam_status_y',title:'检查状态',width:12},
		 	{align:'center',field:'item_amount',title:'金额(元)',width:10},
		 	{align:'center',field:'discount',title:'折扣',width:10},
		 	{align:'center',field:'amount',title:'折后金额(元)',width:15},
		 	{align:'center',field:'pay_mode',title:'付费类型',width:10},
		 	{align:'center',field:'tj_amount',title:'体检收费(元)',width:15},
		 	{align:'center',field:'his_amount',title:'his收费(元)',width:15},
		 	{align:'center',field:'his_req_status',title:'his申请状态',width:15},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	  			$("#chargeItemList").datagrid("hideColumn", "id");
	  			$("#chargeItemList").datagrid("hideColumn", "item_id");
	  			$("#chargeItemList").datagrid("hideColumn", "his_req_status");
	    	},
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    striped:true,
		    fit:true,
		    striped:true,
		    nowrap:true,
		    rownumbers:true
	});  
}

function addChargeItem(){
	//清空原有数据
    var item = $('#chargeItemList').datagrid('getRows');
    	for (var j = item.length - 1; j >= 0; j--) {
            var index = $('#chargeItemList').datagrid('getRowIndex', item[j]);
            $('#chargeItemList').datagrid('deleteRow', index);
        }
    	$('#chargeItemList').datagrid('loadData', { total: 0, rows: [] }); 
    
    var tj_yingshou = 0;
    var his_yingshou = 0;
    $("#tj_shishou").html('0');
    $("#his_shishou").html('0');
	var obj = $("input[name=ck]");
	var data = $('#witemlist').datagrid('getData');
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			$('#chargeItemList').datagrid('insertRow',{
				index: i,
				row: {
				id:data.rows[i].id,
				item_id:data.rows[i].item_id,
			    item_code:data.rows[i].item_code,
	            item_name:data.rows[i].item_name,
	            dep_name:data.rows[i].dep_name,
	            exam_status_y:data.rows[i].exam_status_y,
	            item_amount:data.rows[i].item_amount,
	            discount:data.rows[i].discount,
	            amount:data.rows[i].amount,
	            tj_amount:data.rows[i].tj_charge_amount,
	            his_amount:data.rows[i].his_charge_amount,
	            pay_mode:data.rows[i].pay_mode_y,
	            his_req_status:data.rows[i].his_req_status
				}
			});
			tj_yingshou += data.rows[i].tj_charge_amount;
			his_yingshou += data.rows[i].his_charge_amount;
		}
	}
	
	$("#tj_yingshou").html(decimal(tj_yingshou,2));
	$("#his_yingshou").html(decimal(his_yingshou,2));
}

function getCharingType(){//获取收费方式
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	$.ajax({
		url:'getDatadis.action?com_Type=SFFSLX',
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			var str = '';
			var obj=eval(data);
			for(var i=0;i<6;i++){
				if(i< obj.length){
					if(obj[i].id == '122'){
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(2,this)" style="margin-top:6px;" type="checkbox" style="" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input readonly="readonly" onclick="showcardInfo()" id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}else{
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(1,this)" style="margin-top:6px;" type="checkbox" style="" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" onblur="blur_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}
				}
			}
			$("#charingtype").html(str);
			
			if(obj.length > 6){
				str = '';
				for(var i=6;i<obj.length;i++){
					if(obj[i].id == '122'){
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(2,this)" style="margin-top:6px;" type="checkbox" style="" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input readonly="readonly" onclick="showcardInfo()" id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}else{
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(1,this)" style="margin-top:6px;" type="checkbox" style="" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" onblur="blur_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
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

//选择收费方式，计算实收金额与找零
function click_sfbox(type,ths){
	if(ths != undefined){
		if($(ths)[0].checked){
			var ys = 0.0;
			var ss = 0.0;
			if($(ths).val() == '203'){
				ys = Number($("#his_yingshou").html());
				ss = Number($("#his_shishou").html());
			}else{
				ys = Number($("#tj_yingshou").html());
				ss = Number($("#tj_shishou").html());
			}
			var sy = ys - ss;
			if(sy >= 0){
				$("#sffs_"+$(ths).val()).val(sy);
			}
		}else{
			$("#sffs_"+$(ths).val()).val(0);
			if($(ths).val() == '122'){
				card_xiaofei.length = 0;
			}
		}
	}
	
	var obj = $("input[name=sffs_box]:checked");
	var shishoujine = 0.0;
	var his_shishou = 0.0;
	if(obj.length > 0){
//		if(obj.length == 1 && type == 1){
//			$("#sffs_"+$(obj[0]).val()).val($("#yingshou").html());
//		}
		for(var i=0;i<obj.length;i++){
			if($(obj[i]).val() == '203'){
				his_shishou += Number($("#sffs_"+$(obj[i]).val()).val());
			}else{
				shishoujine += Number($("#sffs_"+$(obj[i]).val()).val());
			}
			if(type == 2 && $(obj[i]).val() == '122'){
				showcardInfo();
			}
		}
	}
	$("#tj_shishou").html(decimal(shishoujine,2));
	$("#his_shishou").html(decimal(his_shishou,2));
	
	var yishou = Number($("#tj_yingshou").html())+Number($("#his_yingshou").html());
	
	if((shishoujine+his_shishou) > yishou){
		$("#zhaoling").val((shishoujine+his_shishou) - yishou);
	}else{
		$("#zhaoling").val(0);
	}
}

var card_xiaofei = new Array();
//选择会员卡收费方式，弹出会员卡操作页面
function showcardInfo(){
	$("#dlg-edit").dialog({
		title:'卡消费',
		href:'showCardInfo.action',
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
								if (text.split("-")[0] == 'ok') {
									savecollectFees();
								} else {
									$.messager.alert("操作提示", text.split("-")[1], "error");
								}
								
							}
						});

			}
			});
	}else{
		savecollectFees();
	}
}

//收费保存收费信息
function savecollectFees(){
	var tj_yingshou = Number($("#tj_yingshou").html());
	var tj_shishou = Number($("#tj_shishou").html());
	var his_yingshou = Number($("#his_yingshou").html());
	var his_shishou = Number($("#his_shishou").html());
	var yuanjia = Number($("#yuanjia").html());
	
	if(tj_shishou < tj_yingshou || his_shishou < his_yingshou){
		$.messager.alert('提示信息', "实收金额不能小于应收金额，请先反算！",function(){});
		return;
	}
	var examInfoCharingItem = new Array();
	var chargingids = new Array();
	var data = $('#chargeItemList').datagrid('getData');
	var his_flag = false;
	examInfoCharingItem.length = 0;
	for(var i = 0; i < data.rows.length ; i++){
			examInfoCharingItem.push({"id":data.rows[i].id,
									  "item_id":data.rows[i].item_id,
									  "item_code":data.rows[i].item_code,
									  "item_amount":data.rows[i].item_amount,
									  "amount":data.rows[i].amount,
									  "personal_pay":data.rows[i].personal_pay,
									  "discount":data.rows[i].discount,
									  "tj_charge_amount":data.rows[i].tj_amount,
									  "his_charge_amount":data.rows[i].his_amount});
			chargingids.push(data.rows[i].item_id);
			
			if(his_flag == false && data.rows[i].his_req_status == 'Y'){
				his_flag = true;
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
			if(119 == $(obj1[i]).val() && amount == 0){
				charingWay.push({"charging_way":$(obj1[i]).val(),"amount":amount});
			}else{
				charingWay.push({"charging_way":$(obj1[i]).val(),"amount":$("#sffs_"+$(obj1[i]).val()).val()});
			}
			
		}
	}else{
		$.messager.alert('提示信息', "请选择收费方式!",function(){});
		return;
	}
	var titleInfo = $("#invoice_name").val();// 发票抬头
	var invoiceType = $("#invoice_type").val();// 发票内容
	var invoiceNum = $("#invoice_num").val();// 发票编码
	
	var isPrintRecepit = "N";// 是否打印发票
	if ($("#fapiao")[0].checked)
		isPrintRecepit = "Y";
	
	if(isPrintRecepit == 'Y'){
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
	}
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'savePricingCollectFees.action?language='+$("#language").val(),  
        data:{
          exam_num:$("#ser_num").val(),
          exam_id:$("#exam_id").val(),
          amount1:yuanjia,
          amount2:(his_yingshou+tj_yingshou),
          his_amount:his_shishou,
          tj_amount:tj_shishou,
          discount: $("#zongzhekou").val(),
          charingWays:JSON.stringify(charingWay),
          examInfoCharingItems:JSON.stringify(examInfoCharingItem),
          cardInfos:JSON.stringify(card_xiaofei),
          isPrintRecepit:isPrintRecepit,
          title_info:$("#invoice_name").val(),
          invoice_type:$("#invoice_type").val(),
          invoice_num:$("#invoice_num").val(),
          chargingIds:chargingids.toString()
          },          
        type: "post",//数据发送方式   
        success: function(data){
        		$(".loading_div").remove();
        		card_xiaofei.length = 0;
        		var state = eval("("+data+")");
        		if(state.flag == 'error'){
        			$.messager.alert('提示信息', state.info,'error');
        			chaxun();
        			return;
        		}
        		if (isPrintRecepit == "N") {
    				$.messager.alert('提示信息', state.info,'info');
    			} else {
    				$.messager.alert('提示信息', "收费成功！正在打印发票。",'info');
    				if($("#invoiceprinttype").val()=='1'){
	    				if($("input[name='invoice_l']:checked").val() == 'mx'){
	    					fapiao_point_mx($("#invoice_num").val());
	    				}else{
	    					fapiao_point($("#invoice_num").val());
	    				}
    				}else{
    					printreport_invoice(state.user_id,state.account_num);
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
//				$("#ser_num").val("");
				$('#dlg-shoufei').dialog('close');
        		chaxun();
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",'error');
            }  
    });
}
//---------------------------发票-----------------------------------

function weihu_fapiao(){
	$("#dlg-tuifei").dialog({
		title:'用户发票号段维护',
		href:'getUserInvoicePage.action'
	});
	$('#dlg-tuifei').dialog('open');
}

//获取发票最大票号
function load_invoice() {
	if ($("#fapiao")[0].checked) {
		
		$.ajax({
			url : 'getMaxInvoiceNum.action',
			data : {},
			type : 'post',
			success:function(data){
				$(".loading_div").remove();
				$("#invoice_num").val(data);
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
		$("#fapiao_msg").css("display", "block");
	} else {
		$("#fapiao_msg").css("display", "none");
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

function printreport_invoice(user_id,acc_num){
	var exeurl="invoiceService://"+user_id+"$1$"+acc_num;
	RunReportExe(exeurl);
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

//打印票据维护
function print_fapiao(){
	if($("#ser_num").val()!="" && $("#ser_num").val()!=null){
		$("#dlg-print-fapiao").dialog({
			title:'收据打印',
			href:'printInvoicePage.action?exam_num='+$("#ser_num").val()
		});
		$('#dlg-print-fapiao').dialog('open');
	}else{
		$.messager.alert("操作提示", '输入体检号查询',"error");
	}
	
}
//补打发票
function fapiao_show(){
	$("#dlg-fapiao").dialog({
		title:'打印发票',
		href:'chargingSingleInvoickShow.action',
	});
	$('#dlg-fapiao').dialog('open');
}
//------------------------------------------------退费----------------------------------------------------------
function getItemList(){
	$('#yksjList').datagrid({    
	    columns:[[
		    {align:'center',field:'ckp',checkbox:true},
		    {align:'center',field:'id',title:'ID',width:10},
		    {align:'center',field:'item_id',title:'项目ID',width:12},
		    {align:'center',field:'item_code',title:'项目编码',width:12},
		 	{align:'center',field:'item_name',title:'项目名称',width:15},
		 	{align:'center',field:'dep_name',title:'科室名称',width:10},
		 	{align:'center',field:'exam_status_y',title:'检查状态',width:10},
		 	{align:'center',field:'item_amount',title:'项目金额(元)',width:10},
		 	{align:'center',field:'discount',title:'折扣',width:8},
		 	{align:'center',field:'amount',title:'收费金额(元)',width:15},
		 	{align:'center',field:'is_application',title:'LIS/PACS申请',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	  		    $("#yksjList").datagrid("hideColumn", "id");
	  		    
	    	},
	    collapsible:true,
		pagination: false,
	    fitColumns:true,
	    striped:true,
	    fit:true,
	    striped:true,
	    nowrap:true,
	    rownumbers:true,
	    toolbar:"#toolbar",
	    onSelect:function(index,row){
	    	tuifeijine();
	    },
	    onUnselect:function(index,row){
	    	tuifeijine();
	    },
	    onCheckAll:function(rows){
	    	tuifeijine();
	    },
	    onUncheckAll:function(rows){
	    	tuifeijine();
	    }
	});  
	
	$('#ykfpList').datagrid({    
	    columns:[[
		    {align:'center',field:'ckp',checkbox:true},
		    {align:'center',field:'id',title:'ID',width:10},
		    {align:'center',field:'item_id',title:'项目ID',width:12},
		    {align:'center',field:'item_code',title:'项目编码',width:12},
		 	{align:'center',field:'item_name',title:'项目名称',width:15},
		 	{align:'center',field:'dep_name',title:'科室名称',width:12},
		 	{align:'center',field:'exam_status_y',title:'检查状态',width:12},
		 	{align:'center',field:'item_amount',title:'项目金额(元)',width:10},
		 	{align:'center',field:'discount',title:'折扣',width:10},
		 	{align:'center',field:'amount',title:'收费金额(元)',width:15},
		 	{align:'center',field:'is_application',title:'LIS/PACS申请',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	  		    $("#ykfpList").datagrid("hideColumn", "id");
	  		 
	    	},
	    collapsible:true,
		pagination: false,
	    fitColumns:true,
	    striped:true,
	    fit:true,
	    striped:true,
	    nowrap:true,
	    rownumbers:true,
	    toolbar:"#toolbar1",
	     onSelect:function(index,row){
	    	tuifeijine1();
	    },
	    onUnselect:function(index,row){
	    	tuifeijine1();
	    },
	    onCheckAll:function(rows){
	    	tuifeijine1();
	    },
	    onUncheckAll:function(rows){
	    	tuifeijine1();
	    }
	});  
}
function addItemList(){
	var  yksj_amount = 0.0;
	var amount = 0.0 ;
	//清空原有数据
    var item = $('#yksjList').datagrid('getRows');
    	for (var j = item.length - 1; j >= 0; j--) {
            var index = $('#yksjList').datagrid('getRowIndex', item[j]);
            $('#yksjList').datagrid('deleteRow', index);
        }
    	$('#yksjList').datagrid('loadData', { total: 0, rows: [] }); 
    	
	var obj = $("input[name=ck]");
	var data = $('#witemlist').datagrid('getData');
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			if(data.rows[i].is_print_recepit != 'Y'){
				if(data.rows[i].pay_status == 'Y' && (data.rows[i].his_charge_status == 'Y' || data.rows[i].tj_charge_status == 'Y')){
					amount = data.rows[i].his_charge_amount + data.rows[i].tj_charge_amount ;
				}else if(data.rows[i].pay_status == 'Y' && (data.rows[i].his_charge_status == 'N' || data.rows[i].tj_charge_status == 'N')){
					amount = data.rows[i].amount ;
				}else if(data.rows[i].pay_status == 'N' && data.rows[i].his_charge_status == 'Y' && data.rows[i].tj_charge_status == 'N'){
					amount = data.rows[i].his_charge_amount ;
				}else if(data.rows[i].pay_status == 'N' && data.rows[i].his_charge_status == 'N' && data.rows[i].tj_charge_status == 'Y'){
					amount = data.rows[i].tj_charge_amount ;
				}
				yksj_amount += decimal((amount),2);
					$('#yksjList').datagrid('insertRow',{
					index: i,
					row: {
					id:data.rows[i].id,
					item_id:data.rows[i].item_id,
				    item_code:data.rows[i].item_code,
		            item_name:data.rows[i].item_name,
		            dep_name:data.rows[i].dep_name,
		            exam_status_y:data.rows[i].exam_status_y,
		            item_amount:data.rows[i].item_amount,
		            discount:data.rows[i].discount,
		            amount:amount,
		            is_application:data.rows[i].is_application_y
					}
				});
			}
		}
	}
	$("#yksjList").datagrid('checkAll');
	$("#yksj_amount").html(decimal((Number(yksj_amount)),2));
}
function  addItemList1(){
   var amount = 0.0 ;
   var ykfp_amount = 0.0 ;
   var it = $('#ykfpList').datagrid('getRows');
	for (var j = it.length - 1; j >= 0; j--) {
        var index = $('#ykfpList').datagrid('getRowIndex', it[j]);
        $('#ykfpList').datagrid('deleteRow', index);
    }
	$('#ykfpList').datagrid('loadData', { total: 0, rows: [] }); 
	var obj = $("input[name=ck]");
	var data = $('#witemlist').datagrid('getData');
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			if(data.rows[i].is_print_recepit == 'Y'){
				if(data.rows[i].pay_status == 'Y' && (data.rows[i].his_charge_status == 'Y' || data.rows[i].tj_charge_status == 'Y')){
					amount = data.rows[i].his_charge_amount + data.rows[i].tj_charge_amount ;
				}else if(data.rows[i].pay_status == 'Y' && (data.rows[i].his_charge_status == 'N' || data.rows[i].tj_charge_status == 'N')){
					amount = data.rows[i].amount ;
				}else if(data.rows[i].pay_status == 'N' && data.rows[i].his_charge_status == 'Y' && data.rows[i].tj_charge_status == 'N'){
					amount = data.rows[i].his_charge_amount ;
				}else if(data.rows[i].pay_status == 'N' && data.rows[i].his_charge_status == 'N' && data.rows[i].tj_charge_status == 'Y'){
					amount = data.rows[i].tj_charge_amount ;
				}
				ykfp_amount += decimal((amount),2);
					$('#ykfpList').datagrid('insertRow',{
					index: i,
					row: {
					id:data.rows[i].id,
					item_id:data.rows[i].item_id,
				    item_code:data.rows[i].item_code,
		            item_name:data.rows[i].item_name,
		            dep_name:data.rows[i].dep_name,
		            exam_status_y:data.rows[i].exam_status_y,
		            item_amount:data.rows[i].item_amount,
		            discount:data.rows[i].discount,
		            amount:amount,
		            is_application:data.rows[i].is_application_y
					}
				});
			}
		}
	}
	$("#ykfpList").datagrid('checkAll');
	$("#ykfp_amount").html(decimal((Number(ykfp_amount)),2));
}
function tuifeijine(){
	var data = $('#yksjList').datagrid('getSelections');
	var yintui = 0.0;
	for(var i=0;i<data.length;i++){
		yintui += data[i].amount;
	}
	$("#yksj_tui").html(decimal(yintui,2));
}

function tuifeijine1(){
	var data = $('#ykfpList').datagrid('getSelections');
	var yintui = 0.0;
	for(var i=0;i<data.length;i++){
		yintui += data[i].amount;
	}
	$("#ykfp_tui").html(decimal(yintui,2));
}
var isPrintRecepit;
function  f_tuifei(type){
	var data;
	var item_ids = new Array();
	if(type == '1'){
		isPrintRecepit = 'Y';
		data = $('#ykfpList').datagrid('getSelections');
	}else{
		isPrintRecepit = 'N';
		data = $('#yksjList').datagrid('getSelections');
	}
	if(data.length <= 0){
		$.messager.alert('提示信息', '请选择需要退费的项目','error');
		return;
	}else{
		for(i=0;i<data.length;i++){
			if(data[i].exam_status == 'Y' || data[i].exam_status == 'C'){
				$.messager.alert('提示信息', '收费项目'+data[i].item_name+'已检查，不能退费!',function(){});
				return;
			}
			if(data[i].is_application == 'Y'){
				$.messager.alert('提示信息', '收费项目'+data[i].item_name+'已发申请，请先撤销申请!',function(){});
				return;
			}

			item_ids.push(data[i].item_id);
		}
		
	}
	$('#dlg-tuifei').dialog('open');
	gettuifei_fees(item_ids.toString(),isPrintRecepit);
}

var oldamount_t;
function gettuifei_fees(item_ids,isPrintRecepit){
	var model = {"exam_num":$("#ser_num").val(),"item_ids":item_ids};
	$("#tuifei_fees").datagrid({
		url: 'getChargingWayByItemIds.action',
		queryParams: model,
		rownumbers:true,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		sortName: 'cdate',
		sortOrder: 'desc',
		columns:[[{align:'center',field:'sf_ck_t',checkbox:true},
		          {align:"center",field:"charging_way","title":"收费方式","width":10},
		          {align:'center',field:"amount","title":"收费金额(元)","width":15},
		          {align:"center",field:"amount_t","title":"应退金额(元)","width":15,editor:{type:'numberbox',options:{precision:2}}}
		     	]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	$("#shitui_t").html(0);
	    	if(isPrintRecepit  == 'Y'){
	    		$("#yishou_t").html($("#ykfp_tui").html());
	    	}else{
	    		$("#yishou_t").html($("#yksj_tui").html());	
	    	}
	    	$.each(value.rows, function(index, item){
	    		item.amount_t = 0.0;
	    		$('#tuifei_fees').datagrid('refreshRow', index);
	    	});
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		toolbar:"#toolbar_t",
		checkOnSelect:false,
		onClickCell:function(index, field, row){
	    	//$('#tuifei_fees').datagrid('clearSelections', index);
			$('#tuifei_fees').datagrid('beginEdit', index);
			var editors = $('#tuifei_fees').datagrid('getEditor',{index:index,field:'amount_t'});
			$(editors.target).numberbox({onChange:function(){
					$('#tuifei_fees').datagrid('endEdit', index);
					tuifeiheji();
				}
			});
	    },
	    onBeforeEdit:function(rowIndex, rowData){
	    	oldamount_t = rowData.amount_t;
	    },
	    onAfterEdit:function(rowIndex, rowData, changes){
	    	if(changes.amount_t != undefined){ //输入应退金额
	    		if(Number(changes.amount_t) > rowData.amount || Number(changes.amount_t) < 0){
	    			$.messager.alert('提示信息', '应退金额不能大于收费金额或小于0',function(){});
	    			rowData.amount_t = oldamount_t;
	        		$('#tuifei_fees').datagrid('refreshRow', rowIndex);
	    		}
	    	}
	    },
	    onSelect:function(index,row){
	    	if(row.amount == 0){
	    		$('#tuifei_fees').datagrid('unselectRow', index);
	    	}else{
	    		$("input[name=sf_ck_t]").eq(index).attr("checked",true);
		    	tuifeiheji();
	    	}
	    },
	    onUnselect:function(index,row){
	    	$("input[name=sf_ck_t]").eq(index).attr("checked",false);
	    	tuifeiheji();
	    },
	    onCheckAll:function(rows){
	    	tuifeiheji();
	    },
	    onUncheckAll:function(rows){
	    	tuifeiheji();
	    }
	});
}

function tuifeiheji(){
	var obj = $("input[name=sf_ck_t]");
	var shitui = 0.0;
	var data = $('#tuifei_fees').datagrid('getData');
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			shitui += Number(data.rows[i].amount_t);
		}
	}
	$("#shitui_t").html(decimal(shitui,2));
}

function tuifeiClick(){
	if(isPrintRecepit == 'Y'){
		$.messager.confirm('提示信息','确定同时作废所开发票吗？',function(r){
			if(r){
				save_tuifei('ykfpList');
			}
		});
	}else{
		save_tuifei('yksjList');
	}
}

//保存退费信息
function save_tuifei(id){
	var yintui = Number($("#yishou_t").html());
	var shitui_t = Number($("#shitui_t").html());
	
	if(yintui != shitui_t){
		$.messager.alert('提示信息', "应退金额不等于实退金额，不能退费！",function(){});
		return;
	}
	var examInfoCharingItem = new Array();
	var data = $('#'+id).datagrid('getSelections');
	examInfoCharingItem.length = 0;
	
	var account_num = data[0].account_num;
	var req_nums = data[0].req_num;
	for(var i=0;i<data.length;i++){
		examInfoCharingItem.push({"id":data[i].id,
			  "item_id":data[i].item_id,
			  "item_amount":data[i].item_amount,
			  "amount":data[i].amount,
			  "personal_pay":data[i].personal_pay,
			  "discount":data[i].discount
			  });
	}
	
	
	var charingWay = new Array();
	var obj1 = $('#tuifei_fees').datagrid('getData');
	var input = $("input[name=sf_ck_t]");
	charingWay.length = 0;
	if(input.length > 0){
		for(var i=0;i<input.length;i++){
			if(input[i].checked == true){
				charingWay.push({"charging_way":obj1.rows[i].id,"amount":obj1.rows[i].amount_t});
			}
		}
	}else{
		$.messager.alert('提示信息', "请选择退费方式!",function(){});
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'savePricingRefund.action',  
        data:{
          exam_num:$("#ser_num").val(),
          amount1:yintui,
          amount2:shitui_t,
          discount: 10,
          charingWays:JSON.stringify(charingWay),
          examInfoCharingItems:JSON.stringify(examInfoCharingItem),
          isPrintRecepit:isPrintRecepit,
          account_num:account_num,
          req_nums:req_nums
          },          
        type: "post",//数据发送方式   
          success: function(data){
        	  $(".loading_div").remove();
        		$.messager.alert('提示信息', data,'info');
        		$('#dlg-tuifei').dialog('close');
        		$('#dlg-refund').dialog('close');
        		chaxun();
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",'error');
            }  
    });
}

