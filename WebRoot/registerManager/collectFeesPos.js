$(document).ready(function () {
	getwitemGrid();
	getCharingType();
	
	var is_repeat_invoice=Number($("#is_repeat_invoice").val());//是否有重打发票功能，1 没有   2  有
	//配置控制重打发票
	if(is_repeat_invoice=="1"){
		$("#repeatInvoice").hide();
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
	load_invoice();
}

/*************************************************************收费功能**********************************************************************/
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
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(2,this)" style="margin-top:6px;" type="checkbox" style="" childrencode="'+obj[i].data_code_children+'" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input readonly="readonly" onclick="showcardInfo()" id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
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
	 url:'getwitemList.action',
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
		rows[0].amount =  rows[0].amount - ling;
		rows[0].personal_pay = rows[0].amount;
		var index = $('#witemlist').datagrid('getRowIndex',rows[0]);
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
	if(ths != undefined){
		if($(ths)[0].checked){
			var ys = Number($("#yingshou").html());
			var ss = Number($("#shishou").html());
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
	var yingshou = Number($("#yingshou").html());
	var shishou = Number($("#shishou").html());
	var yuanjia = Number($("#yuanjia").html());
	
	if(shishou < yingshou){
		$.messager.alert('提示信息', "实收金额不能小于应收金额，请先反算！",function(){});
		return;
	}
	var examInfoCharingItem = new Array();
	var chargingids = new Array();
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
									  "personal_pay":data.rows[i].personal_pay,
									  "discount":data.rows[i].discount});
			chargingids.push(data.rows[i].item_id);
			
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
	
	var isPrintRecepit = "N";// 是否打印发票
	if ($("#fapiao")[0].checked)
		isPrintRecepit = "Y";
	
	if(isPrintRecepit == 'Y'){
		if($("#invoiceRepeatType").val()=='Y'){
			if(Chargingmethod==372||Chargingmethod==122){
				$.messager.alert('提示信息', "使用商户划账或会员卡方式收费无法开发票!","error");
				return;
			}
		}
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
		
        url:'saveCollectFees.action?language='+$("#language").val(),  
        data:{
          exam_num:$("#ser_num").val(),
          exam_id:$("#exam_id").val(),
          amount1:yuanjia,
          amount2:yingshou,
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
				$("#ser_num").val("");
				
        		chaxun();
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",'error');
            }  
    });
}
//补打发票
function fapiao_show(){
	$("#dlg-fapiao").dialog({
		title:'打印发票',
		href:'chargingSingleInvoickShow.action',
	});
	$('#dlg-fapiao').dialog('open');
}

//重打发票
function fapiao_repeatInvoice(){
	 $("#dlg-fapiaoCD").dialog({
	  title:'打印发票',
	  href:'repeatInvoice.action',
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
        url:'delHisApplication.action?language='+$("#language").val(),  
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

function cksfjl(){
	if($("#ser_num").val().length>0){
		$("#dlg-show_sfjl").dialog({
	 		title:'个人收费记录',
	 		href:'showDetailCollectFees.action?exam_num='+$("#ser_num").val()
	 	});
	 	$("#dlg-show_sfjl").dialog('open');
	}else{
		$.messager.alert("操作提示", "体检人员无效","error");
	}
}


function jiesuanBaodao(){
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
									savecollectFeePos();
								} else {
									$.messager.alert("操作提示", text.split("-")[1], "error");
								}
								
							}
						});

			}
			});
	}else{
		savecollectFeePos();
	}
}

//收费保存收费信息
function savecollectFeePos(){
	var yingshou = Number($("#yingshou").html());
	var shishou = Number($("#shishou").html());
	var yuanjia = Number($("#yuanjia").html());
	
	/*if(shishou < yingshou){
		$.messager.alert('提示信息', "实收金额不能小于应收金额，请先反算！",function(){});
		return;
	}*/
	var examInfoCharingItem = new Array();
	var chargingids = new Array();
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
									  "personal_pay":data.rows[i].personal_pay,
									  "discount":data.rows[i].discount});
			chargingids.push(data.rows[i].item_id);
			
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
	var Chargingmethod;
//	var obj1 = $("input[name=sffs_box]:checked");
	charingWay.length = 0;

	var titleInfo = $("#invoice_name").val();// 发票抬头
	var invoiceType = $("#invoice_type").val();// 发票内容
	var invoiceNum = $("#invoice_num").val();// 发票编码
	
	var isPrintRecepit = "N";// 是否打印发票
	if ($("#fapiao")[0].checked)
		isPrintRecepit = "Y";
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		
        url:'settlement.action?language='+$("#language").val(),  
        data:{
          exam_num:$("#ser_num").val(),
          exam_id:$("#exam_id").val(),
          amount1:yuanjia,
          amount2:yingshou,
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
        		
//        		card_xiaofei.length = 0;
        		var state = eval("("+data+")");
        		$("#summary_id").val(state.summary_id);
        		settlement_show();

            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",'error');
            }  
    });
}
//结算列表
function settlement_show(){
	$("#dlg-fapiao").dialog({
		title:'结算列表',
		href:'settlementShow.action',
	});
	$('#dlg-fapiao').dialog('open');
}