$(document).ready(function () {
	getcardinfo();
	getcardsalelist();
	$("#invoice_name,#invoice_type,#invoice_num,#invoice_num1,#invoice_name1,#invoice_type1").validatebox({
		required: true
	});
	getcardSaleList();
	getshoukamingxi('');
	getyscardSaleList();
	getyushoumingxi('');
});
/*************************************************************未售卡售卡功能**********************************************************************/
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
		}
	}
	
	var obj = $("input[name=sffs_box]:checked");
	var shishoujine = 0.0;
	if(obj.length > 0){
		for(var i=0;i<obj.length;i++){
			shishoujine += Number($("#sffs_"+$(obj[i]).val()).val());
		}
	}
	$("#shishou").html(decimal(shishoujine,2));
//	var yishou = Number($("#yingshou").html());
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
function printreport_invoice(user_id,acc_num){
	var exeurl="invoiceService://"+user_id+"$1$"+acc_num;
	RunReportExe(exeurl);
}
function RunReportExe(strPath) {
	 location.href=strPath;
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

function chaxuncardinfo(){
	var card_num = undefined;
	 if($("#ck_card_num")[0].checked){
		 card_num =  $("#card_num").textbox('getValue');
	 }
	 var card_num_s = undefined,card_num_e = undefined;
	 if($("#ck_card_num_se")[0].checked){
		 card_num_s = $("#card_num_s").textbox('getValue');
		 card_num_e = $("#card_num_e").textbox('getValue');
	 }
	 var start_date = undefined,end_date = undefined;
	 if($("#ck_date")[0].checked){
		 start_date = $("#start_date").datebox('getValue');
		 end_date = $("#end_date").datebox('getValue');
	 }
	 var company = undefined;
	 if($("#ck_company")[0].checked){
		 company = $("#company").textbox('getValue');
	 }
	var model={
				 "card_num":card_num,
				 "card_num_s":card_num_s,
				 "card_num_e":card_num_e,	
				 "start_date":start_date,	
				 "end_date":end_date,
				 "company":company
		 };
	$('#cardinfolist').datagrid('load',model);
}
var toolbar = [{
    text:'批量添加',
    iconCls:'icon-add',
    handler:function(){
    	var rows = $("#cardinfolist").datagrid('getChecked');
    	if(rows.length == 0){
    		$.messager.alert("操作提示", "请选择需要售卡的卡信息!", "error");
			return;
    	}
    	for(j=0;j<rows.length;j++){
    		if(!append(rows[j])) return;
    	}
    }
}]
//查询未售卡信息列表
function getcardinfo(){
	 var card_num = undefined;
	 if($("#ck_card_num")[0].checked){
		 card_num =  $("#card_num").textbox('getValue');
	 }
	 var card_num_s = undefined,card_num_e = undefined;
	 if($("#ck_card_num_se")[0].checked){
		 card_num_s = $("#card_num_s").textbox('getValue');
		 card_num_e = $("#card_num_e").textbox('getValue');
	 }
	 var start_date = undefined,end_date = undefined;
	 if($("#ck_date")[0].checked){
		 start_date = $("#start_date").datebox('getValue');
		 end_date = $("#end_date").datebox('getValue');
	 }
	 var company = undefined;
	 if($("#ck_company")[0].checked){
		 company = $("#company").textbox('getValue');
	 }
	var model={
				 "card_num":card_num,
				 "card_num_s":card_num_s,
				 "card_num_e":card_num_e,	
				 "start_date":start_date,	
				 "end_date":end_date,
				 "company":company
		 };
	     $("#cardinfolist").datagrid({
		 url:'getNotSaleCardInfoList.action',
		 dataType: 'json',
		 queryParams:model,
	     //pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
            {align:"center",field:"card_num","title":"卡号","width":20,sortable:true},
   		  	{align:'center',field:"status_y","title":"卡状态","width":10},
//   		{align:"center",field:"card_type_y","title":"卡类型","width":10},
   		  	{align:"center",field:"amount","title":"卡余额(元)","width":10},
//   		{align:"center",field:"member_name","title":"绑定人","width":15},
   		  	{align:"center",field:"card_level","title":"卡类型","width":10},
   		  	{align:"center",field:"deadline","title":"有效日期","width":15},
   		  	{align:"center",field:"limit_card_count","title":"限制次数","width":10},
   		  	{align:"center",field:"remark","title":"备注","width":15},
		    {align:"center",field:"face_amount","title":"面值","width":10},
//   		{align:"center",field:"discount","title":"折扣率","width":20},
		    {align:"center",field:"company","title":"单位","width":20},	
		    {align:"center",field:"sale_amount","title":"售卡金额","width":15},
		    {align:"center",field:"jcbd","title":"添加","width":10,"formatter":f_card_add}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
	    	onDblClickRow : function(index, row) {	 
	    		append(row);
			},
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:true,
		    striped:true,
		    toolbar:toolbar
	});
}
function f_card_add(val,row,index){
	return '<a href=\"javascript:f_card_add_s('+index+')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-add\" alt=\"添加\" /></a>';
}
function f_card_add_s(index){
	var rows = $("#cardinfolist").datagrid('getRows');
	append(rows[index]);
}
function append(row){
	var rows = $("#xycardsalelist").datagrid('getRows');
	for(i=0;i<rows.length;i++){
		if(row.card_num == rows[i].card_num){
			$.messager.alert("操作提示", "卡号"+row.card_num+"已添加到售卡清单，不能再次添加!", "error");
			return false;
		}
	}
	if(row.sale_amount <= 0){
		row.sale_amount = row.amount;
	}
	$("#xycardsalelist").datagrid('appendRow',row);
	return true;
}
var toolbar1 = [{
    text:'售卡',
    iconCls:'icon-save',
    handler:function(){
    	var rows = $("#xycardsalelist").datagrid('getRows');
    	if(rows.length == 0){
    		$.messager.alert("操作提示", "请添加售卡信息清单!", "error");
			return;
    	}
    	getCharingType();
    	var yingshou = 0,knamount = 0;
    	for(i=0;i<rows.length;i++){
    		yingshou += Number(rows[i].sale_amount);
    		knamount += Number(rows[i].amount);
    	}
    	$("#shoufeileixing").val(1);//未售卡售卡操作
    	$("#yingshou").html(yingshou);
    	$("#shishou").html(0);
    	$("#shuliang").html(rows.length);
    	$("#knamount").html(knamount);
    	$("#dlg-qrsk").dialog('open');
    	$("#dlg-qrsk").dialog('center');
    }
},{
    text:'预售卡',
    iconCls:'icon-save',
    handler:function(){
    	var rows = $("#xycardsalelist").datagrid('getRows');
    	if(rows.length == 0){
    		$.messager.alert("操作提示", "请添加售卡信息清单!", "error");
			return;
    	}
    	var yingshou = 0,knamount = 0;
    	for(i=0;i<rows.length;i++){
    		yingshou += Number(rows[i].sale_amount);
    		knamount += Number(rows[i].amount);
    	}
    	$.messager.confirm('确认','本次预售卡'+rows.length+'张，预售卡售卡总金额'+yingshou+'元，预售卡内总金额'+knamount+'元。确认预售吗？',function(r){    
    	    if (r){    
    	    	save_card_sale_add();
    	    }    
    	});
    }
},{
    text:'清空清单',
    iconCls:'icon-print',
    handler:function(){
    	$('#xycardsalelist').datagrid('loadData', { total: 0, rows: [] });
    }
},{
    text:'批量修改售卡金额',
    iconCls:'icon-update',
    handler:function(){
    	var rows = $("#xycardsalelist").datagrid('getChecked');
    	if(rows.length == 0){
    		$.messager.alert("操作提示", "请选择需要修改售卡金额的卡信息!", "error");
			return;
    	}
    	$("#up_sale_amount").numberbox('setValue',0);
    	$("#dlg-sale-amount").dialog('open');
    }
}]
function getcardsalelist(){
	var lastIndex;
	$("#xycardsalelist").datagrid({
	     //pageSize: 15,//每页显示的记录条数，默认为10 
		 columns:[[
             {field:'ck',checkbox:true },
             {align:"center",field:"card_num","title":"卡号","width":20,sortable:true},
  		  	 {align:"center",field:"amount","title":"卡余额(元)","width":10},
		     {align:"center",field:"sale_amount","title":"售卡金额","width":15,editor:{type:'numberbox',options:{precision:2}}},
		     {align:"center",field:"sc","title":"删除","width":10,"formatter":f_card_del}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    fit:true,
		    striped:true,
		    toolbar:toolbar1,
		    onClickCell:function(index, field, row){
		    	//if (lastIndex != index){
		    		$('#xycardsalelist').datagrid('endEdit', lastIndex);
					$('#xycardsalelist').datagrid('beginEdit', index);
				//}
				lastIndex = index;
				var editors = $('#xycardsalelist').datagrid('getEditor',{index:index,field:'sale_amount'});
				$(editors.target).numberbox({onChange:function(){
						$('#xycardsalelist').datagrid('endEdit', index);
					}
				});
		    }
	});
}
function f_card_del(val,row,index){
	return '<a href=\"javascript:f_card_del_s(\''+row.card_num+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"添加\" /></a>';
}
function f_card_del_s(card_num){
	var rows = $("#xycardsalelist").datagrid('getRows');
	for(i=0;i<rows.length;i++){
		if(card_num == rows[i].card_num){
			$("#xycardsalelist").datagrid('deleteRow',i);
		}
	}
}

function save_up_sale_amount(){
	var rows = $("#xycardsalelist").datagrid('getChecked');
	var sale_amount = $("#up_sale_amount").numberbox('getValue');
	for(i=0;i<rows.length;i++){
		var index = $("#xycardsalelist").datagrid('getRowIndex',rows[i]);
		rows[i].sale_amount = sale_amount;
		$("#xycardsalelist").datagrid('refreshRow',index)
	}
	$("#dlg-sale-amount").dialog('close');
}

//保存售卡信息
function save_card_sale(){
	var yingshou = Number($("#yingshou").html());
	var shishou = Number($("#shishou").html());
	if(shishou != yingshou){
		$.messager.alert('提示信息', "实收金额不等于应收金额，不能确认售卡！",'error');
		return;
	}
	if($("#shoufeileixing").val() == 1){
		var cardInfoList = new Array();
		var rows = $('#xycardsalelist').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			cardInfoList.push({
				'card_num':rows[i].card_num,
				'amount':rows[i].amount,
				'sale_amount':rows[i].sale_amount
			});
		}
		var charingWay = new Array();
		var obj1 = $("input[name=sffs_box]:checked");
		var Chargingmethod;
		if(obj1.length > 0){
			for(var i=0;i<obj1.length;i++){
				charingWay.push({"charging_way":$(obj1[i]).val(),"amount":$("#sffs_"+$(obj1[i]).val()).val()});
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
			if($("#invoiceRepeatType").val()=='N'){
				if(Chargingmethod==372||Chargingmethod==122){
					$.messager.alert('提示信息', "使用商户划账或会员卡方式收费无法开发票！","error");
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
	        url:'saveCardSaleInfo.action',  
	        data:{
	          'sale_status':1,
	          'amount':$("#knamount").html(),
	          'sale_amount':shishou,
	          'charingWays':JSON.stringify(charingWay),
	          'cardSaleDetails':JSON.stringify(cardInfoList),
	          'isPrintRecepit':isPrintRecepit,
	          'title_info':$("#invoice_name").val(),
	          'invoice_type':$("#invoice_type").val(),
	          'invoice_num':$("#invoice_num").val(),
	          'sale_type':1
	          },          
	        type: "post",//数据发送方式   
	        success: function(data){
	        		$(".loading_div").remove();
	        		var state = eval("("+data+")");
	        		if(state.flag == 'error'){
	        			$.messager.alert('提示信息', state.info,'error');
	        			return;
	        		}
	        		if (isPrintRecepit == "N") {
	    				$.messager.alert('提示信息', state.info,'info');
	    			} else {
	    				$.messager.alert('提示信息', "售卡成功！正在打印发票。",'info');
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
	        		if($("#isShowInvoicePage").val() == 'Y'){
	        			load_invoice();
	        		}else{
	        			$("#fapiao")[0].checked = false;
	            		load_invoice();
	            		$("#invoice_name").val('');
	            		$("#invoice_type").val('');
	            		$("#invoice_num").val('');
	        		}
	        		$("#dlg-qrsk").dialog('close');
	        		$('#xycardsalelist').datagrid('loadData', { total: 0, rows: [] });
	        		chaxuncardinfo();
	        		yskachaxun();
	        		shoukachaxun();
	            },
	            error:function(){
	            	$(".loading_div").remove();
	            	$.messager.alert('提示信息', "操作失败！",'error');
	            }  
	    });
	}else if($("#shoufeileixing").val() == 2){  //预售卡确认售卡
		var cardInfoList = new Array();
		var rows = $('#ysCardSaleDetail').datagrid('getChecked');
		for(var i=0;i<rows.length;i++){
			cardInfoList.push({
				'card_num':rows[i].card_num,
				'amount':rows[i].amount,
				'sale_amount':rows[i].sale_amount
			});
		}
		var charingWay = new Array();
		var obj1 = $("input[name=sffs_box]:checked");
		var Chargingmethod;
		if(obj1.length > 0){
			for(var i=0;i<obj1.length;i++){
				charingWay.push({"charging_way":$(obj1[i]).val(),"amount":$("#sffs_"+$(obj1[i]).val()).val()});
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
			if($("#invoiceRepeatType").val()=='N'){
				if(Chargingmethod==372||Chargingmethod==122){
					$.messager.alert('提示信息', "使用商户划账或会员卡方式收费无法开发票！","error");
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
	        url:'saveCardSaleInfo.action',  
	        data:{
	          'sale_status':1,
	          'amount':$("#knamount").html(),
	          'sale_amount':shishou,
	          'charingWays':JSON.stringify(charingWay),
	          'cardSaleDetails':JSON.stringify(cardInfoList),
	          'isPrintRecepit':isPrintRecepit,
	          'title_info':$("#invoice_name").val(),
	          'invoice_type':$("#invoice_type").val(),
	          'invoice_num':$("#invoice_num").val(),
	          'sale_type':1
	          },          
	        type: "post",//数据发送方式   
	        success: function(data){
	        		$(".loading_div").remove();
	        		var state = eval("("+data+")");
	        		if(state.flag == 'error'){
	        			$.messager.alert('提示信息', state.info,'error');
	        			return;
	        		}
	        		if (isPrintRecepit == "N") {
	    				$.messager.alert('提示信息', state.info,'info');
	    			} else {
	    				$.messager.alert('提示信息', "售卡成功！正在打印发票。",'info');
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
	        		if($("#isShowInvoicePage").val() == 'Y'){
	        			load_invoice();
	        		}else{
	        			$("#fapiao")[0].checked = false;
	            		load_invoice();
	            		$("#invoice_name").val('');
	            		$("#invoice_type").val('');
	            		$("#invoice_num").val('');
	        		}
	        		$("#dlg-qrsk").dialog('close');
	        		$('#ysCardSaleDetail').datagrid('loadData', { total: 0, rows: [] });
	        		var saletradenum = $("#saleTradeNum").val();
	        		getTradeDetail(saletradenum,"ysCardSaleDetail");
	        		chaxuncardinfo();
	        		shoukachaxun();
	            },
	            error:function(){
	            	$(".loading_div").remove();
	            	$.messager.alert('提示信息', "操作失败！",'error');
	            }  
	    });
	}
}
//预售卡信息保存
function save_card_sale_add(){
	var rows = $("#xycardsalelist").datagrid('getRows');
	var cardInfoList = new Array();
	var yingshou = 0,knamount = 0;
	for(i=0;i<rows.length;i++){
		yingshou += Number(rows[i].sale_amount);
		knamount += Number(rows[i].amount);
		cardInfoList.push({
			'card_num':rows[i].card_num,
			'amount':rows[i].amount,
			'sale_amount':rows[i].sale_amount
		});
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'saveAdvCardSaleInfo.action',  
        data:{
          'sale_status':0,
          'amount':knamount.toString(),
          'sale_amount':yingshou.toString(),
          'cardSaleDetails':JSON.stringify(cardInfoList),
          'sale_type':0,
          'isPrintRecepit':'N'
        },          
        type: "post",//数据发送方式   
        success: function(data){
        		$(".loading_div").remove();
        		var state = eval("("+data+")");
        		if(state.flag == 'error'){
        			$.messager.alert('提示信息', state.info,'error');
        			return;
        		}
        		$.messager.alert('提示信息', state.info,'info');
        		$('#xycardsalelist').datagrid('loadData', { total: 0, rows: [] });
        		chaxuncardinfo();
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",'error');
            }  
    });
}
/***********************************已预售卡功能***************************************/
function yskachaxun(){
	var sale_trade_num = undefined;
	 if($("#ck_saleTradeNum")[0].checked){
		 sale_trade_num =  $("#ys_saleTradeNum").textbox('getValue');
	 }
	 var ys_start_date = undefined,ys_end_date = undefined;
	 if($("#ck_ysdate")[0].checked){
		 ys_start_date = $("#ys_start_date").datebox('getValue');
		 ys_end_date = $("#ys_end_date").datebox('getValue');
	 }
	var model={
			"sale_trade_num":sale_trade_num,
			 "start_date":ys_start_date,	
			 "end_date":ys_end_date,
			 "sale_status":0
	 };
	$('#advCardSaleList').datagrid('load',model);
	getyushoumingxi('');
}
function getyscardSaleList(){
	
	var sale_trade_num = undefined;
	 if($("#ck_saleTradeNum")[0].checked){
		 sale_trade_num =  $("#ys_saleTradeNum").textbox('getValue');
	 }
	 var ys_start_date = undefined,ys_end_date = undefined;
	 if($("#ck_ysdate")[0].checked){
		 ys_start_date = $("#ys_start_date").datebox('getValue');
		 ys_end_date = $("#ys_end_date").datebox('getValue');
	 }
	var model={
				 "sale_trade_num":sale_trade_num,
				 "start_date":ys_start_date,	
				 "end_date":ys_end_date,
				 "sale_status":0
		 };
	$("#advCardSaleList").datagrid({
		 url:'getCardSaleList.action',
		 dataType: 'json',
		 queryParams:model,
	     //pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
           {field:'ck',checkbox:true },
           {align:"center",field:"sale_trade_num","title":"交易流水号","width":20,sortable:true},
           {align:'center',field:"invoice_num","title":"发票号","width":15},
  		  	{align:'center',field:"amount","title":"卡内总金额","width":15},
  		  	{align:"center",field:"sale_amount","title":"售卡总金额","width":15},
  		  	{align:"center",field:"sale_types","title":"交易类型","width":10},
  		  	{align:"center",field:"advance_sale_user","title":"预售卡人","width":15},
		    {align:"center",field:"advance_sale_time","title":"预售卡时间","width":25},
		    {align:"center",field:"ckmx","title":"查看明细","width":15,"formatter":f_ckysmx}
//		    {align:"center",field:"cz","title":"操作","width":15,"formatter":f_queren}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:true,
		    striped:true,
		    onDblClickRow:function(rowIndex, rowData){
		    	getyushoumingxi(rowData.sale_trade_num);
		    },
		    toolbar:toolbar2
	});
}

toolbar2 = [{
    text:'绑定单位',
    iconCls:'icon-add',
    handler:function(){
        var rows = $("#advCardSaleList").datagrid('getChecked');
        if(rows.length == 0){
            $.messager.alert("操作提示", "请选择需要操作的卡信息!", "error");
            return;
        }else{
            var sale_trade_num = new Array();;
            for(var i=0;i<rows.length;i++){
                sale_trade_num.push(rows[i].sale_trade_num);
            }
            isCheckBindCompany(sale_trade_num.toString(),"advCardSaleList"); // 校验是否绑定过单位
		}
    }
}]

function setSaleDetailId(saleListId,saleDetailId){
	$("#saleListId").val(saleListId);
	$("#saleDetailId").val(saleDetailId);
}

//绑定单位弹窗
function up_bindCompany(saleListId){
	var rows = $("#"+saleListId+"").datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert("操作提示", "请选择需要操作的卡信息!", "error");
		return;
	}else{
		$("#up_bindCompany").dialog('open');
		$('#companyInfo').combobox({
			url : 'getCompanyList.action',
			editable : true, //不可编辑状态
			cache : false,
			panelHeight : '300',//自动高度适合
			valueField : 'id',
			textField : 'com_Name',
			onLoadSuccess : function(data) {
				
			},
		});
	}
}

function getTradeDetail(sale_trade_num,saleDetailId){
	$("#saleTradeNum").val(sale_trade_num);
	var model={
			 "sale_trade_num":sale_trade_num
	 };
   $("#"+saleDetailId+"").datagrid({
	 url:'getCardSaleDetailList2.action',
	 dataType: 'json',
	 queryParams:model,
	 columns:[[
		 	{field:'ck',checkbox:true },
	        {align:"center",field:"card_num","title":"卡号","width":20,sortable:true},
		  	{align:'center',field:"status_y","title":"卡状态","width":10},
		  	{align:'center',field:"saleStatus_CH","title":"售卡状态","width":10},
		  	{align:"center",field:"sale_amount","title":"售卡金额","width":15},
		  	{align:"center",field:"amount","title":"卡金额(元)","width":10},
		  	{align:"center",field:"card_level","title":"卡类型","width":10},
		  	{align:"center",field:"deadline","title":"有效日期","width":15},
		  	{align:"center",field:"limit_card_count","title":"限制次数","width":10},
		  	{align:"center",field:"remark","title":"备注","width":15},
		  	{align:"center",field:"company","title":"单位","width":20}	
		  	
	 	]],	    	
  	onLoadSuccess:function(value){ 
  		$("#datatotal").val(value.total);
  	},
  
		rownumbers:false,
		singleSelect:false,
	    collapsible:true,
		pagination: false,
	    fitColumns:true,
	    fit:true,
	    striped:true,
	    toolbar:toolbar4
   });
}

function bindCompany(){
	var saleListId = $("#saleListId").val();
    var rows = $("#"+saleListId+"").datagrid("getChecked");
	var company_name = $("#companyInfo").combobox("getText");
	var company_id = $("#companyInfo").combobox("getValue");
	if(rows.length==0){
		$.messager.alert('提示信息', "请选择需要操作的信息!",'error');
	}else{
		var sale_trade_num = new Array();;
		for(var i=0;i<rows.length;i++){
			sale_trade_num.push(rows[i].sale_trade_num);
		}
		$.ajax({
			url:'bindCompany.action',
			data:{
				sale_trade_num:sale_trade_num.toString(),
				company:company_name,
				company_id:company_id
			},
			type:'post',
			success:function(data){
				if("ok"==data){
					var saletradenum = new Array();
					for(var i=0;i<rows.length;i++){
						saletradenum.push(rows[i].sale_trade_num);
					}
					var saleDetailId = $("#saleDetailId").val();
					getTradeDetail(saletradenum.toString(),saleDetailId);
				}else{
					$.messager.alert('提示信息', data.split("-")[1],'error');
				}
				$('#up_bindCompany').dialog('close');
			},
			error:function(){
				$.messager.alert("提示信息","操作失败","error");
			}
		});
	}
}

function f_ckysmx(val,row,index){
	return '<a href=\"javascript:getyushoumingxi(\''+row.sale_trade_num+'\')\">查看明细</a>';
}
function getyushoumingxi(sale_trade_num){
	$("#saleTradeNum").val(sale_trade_num);
	var model={
			 "sale_trade_num":sale_trade_num
	 };
    $("#ysCardSaleDetail").datagrid({
	 url:'getCardSaleDetailList.action',
	 dataType: 'json',
	 queryParams:model,
	 columns:[[
		 	{field:'ck',checkbox:true },
	        {align:"center",field:"card_num","title":"卡号","width":20,sortable:true},
		  	{align:'center',field:"status_y","title":"卡状态","width":10},
		  	{align:'center',field:"saleStatus_CH","title":"售卡状态","width":10},
		  	{align:"center",field:"sale_amount","title":"售卡金额","width":15},
		  	{align:"center",field:"amount","title":"卡金额(元)","width":10},
		  	{align:"center",field:"card_level","title":"卡类型","width":10},
		  	{align:"center",field:"deadline","title":"有效日期","width":15},
		  	{align:"center",field:"limit_card_count","title":"限制次数","width":10},
		  	{align:"center",field:"remark","title":"备注","width":15},
		  	{align:"center",field:"company","title":"单位","width":20}	
		  	
	 	]],	    	
   	onLoadSuccess:function(value){ 
   		$("#datatotal").val(value.total);
   	},
   
		rownumbers:false,
		singleSelect:false,
	    collapsible:true,
		pagination: false,
	    fitColumns:true,
	    fit:true,
	    striped:true,
	    toolbar:toolbar4
    });
}

toolbar4=[{
    text:'确认售卡',
    iconCls:'icon-save',
    handler:function(){
    	var rows = $("#ysCardSaleDetail").datagrid('getChecked');
    	if(rows.length == 0){
    		$.messager.alert("操作提示", "请添加售卡信息清单!", "error");
			return;
    	}
    	getCharingType();
    	var yingshou = 0,knamount = 0;
    	for(i=0;i<rows.length;i++){
    		yingshou += Number(rows[i].sale_amount);
    		knamount += Number(rows[i].amount);
    	}
    	$("#shoufeileixing").val(2);//预售卡卡售卡操作
    	$("#yingshou").html(yingshou);
    	$("#shishou").html(0);
    	$("#shuliang").html(rows.length);
    	$("#knamount").html(knamount);
    	$("#dlg-qrsk").dialog('open');
    	$("#dlg-qrsk").dialog('center');
    }
}]

function f_queren(val,row,index){
	return '<a href=\"javascript:f_querensk(\''+row.sale_trade_num+'\')\">确认售卡</a>';
}
function f_querensk(sale_trade_num){
	var rows = $("#advCardSaleList").datagrid('getRows');
	var row;
	for(i=0;i<rows.length;i++){
		if(sale_trade_num == rows[i].sale_trade_num){
			row = rows[i];
		}
	}
	getCharingType();
	
	$("#shoufeileixing").val(2);//预售卡售卡操作
	$("#shoufeisale_trade_num").val(sale_trade_num);
	$("#yingshou").html(row.sale_amount);
	$("#shishou").html(0);
	$("#shuliang").html(row.card_info_count);
	$("#knamount").html(row.amount);
	$("#dlg-qrsk").dialog('open');
	$("#dlg-qrsk").dialog('center');
}
/**************************************已售卡功能*******************************************/
function shoukachaxun(){
	var model={
			 "start_date":$("#sk_start_date").datebox('getValue'),	
			 "end_date":$("#sk_end_date").datebox('getValue'),
			 "isPrintRecepit":$("#sk_kaipiao").combobox('getValue'),
			 "sale_status":1
	 };
	$('#cardSaleList').datagrid('load',model);
	getshoukamingxi('');
}
//已售卡列表查询
function getcardSaleList(){
	var model={
				 "start_date":$("#sk_start_date").datebox('getValue'),	
				 "end_date":$("#sk_end_date").datebox('getValue'),
				 "isPrintRecepit":$("#sk_kaipiao").combobox('getValue'),
				 "sale_status":1
		 };
	$("#cardSaleList").datagrid({
		 url:'getCardSaleList.action',
		 dataType: 'json',
		 queryParams:model,
	     //pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
           {field:'ck',checkbox:true },
           {align:"center",field:"sale_trade_num","title":"交易流水号","width":20,sortable:true},
           {align:'center',field:"invoice_num","title":"发票号","width":15},
  		  	{align:'center',field:"amount","title":"卡内总金额","width":15},
  		  	{align:"center",field:"sale_amount","title":"售卡总金额","width":15},
  		  	{align:"center",field:"sale_types","title":"交易类型","width":10},
  		    {align:"center",field:"charging_way","title":"收费方式","width":30},
  		  	{align:"center",field:"sale_user","title":"售卡人","width":15},
  		  	{align:"center",field:"sale_time","title":"售卡时间","width":25},
  		  	{align:"center",field:"advance_sale_user","title":"预售卡人","width":15},
		    {align:"center",field:"advance_sale_time","title":"预售卡时间","width":25},
		    {align:"center",field:"ckmx","title":"查看明细","width":15,"formatter":f_ckskmx},
		    {align:"center",field:"cz","title":"操作","width":15,"formatter":f_invoice}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:true,
		    striped:true,
		    toolbar:toolbar3,
		    onDblClickRow:function(rowIndex, rowData){
		    	getshoukamingxi(rowData.sale_trade_num);
		    }
	});
}
toolbar3 = [{
    text:'绑定单位',
    iconCls:'icon-add',
    handler:function(){
        var rows = $("#cardSaleList").datagrid('getChecked');
        if(rows.length == 0){
            $.messager.alert("操作提示", "请选择需要操作的卡信息!", "error");
            return;
        }else{
            var sale_trade_num = new Array();;
            for(var i=0;i<rows.length;i++){
                sale_trade_num.push(rows[i].sale_trade_num);
            }
            isCheckBindCompany(sale_trade_num.toString(),"cardSaleList"); // 校验是否绑定过单位
        }
    	
    }
}]
function f_ckskmx(val,row,index){
	return '<a href=\"javascript:getshoukamingxi(\''+row.sale_trade_num+'\')\">查看明细</a>';
}
function getshoukamingxi(sale_trade_num){
	var model={
			 "sale_trade_num":sale_trade_num
	 };
    $("#skCardSaleDetail").datagrid({
	 url:'getCardSaleDetailList.action',
	 dataType: 'json',
	 queryParams:model,
	 columns:[[
	        {align:"center",field:"card_num","title":"卡号","width":20,sortable:true},
		  	{align:'center',field:"status_y","title":"卡状态","width":10},
		  	{align:"center",field:"sale_amount","title":"售卡金额","width":15},
		  	{align:"center",field:"amount","title":"卡金额(元)","width":10},
		  	{align:"center",field:"card_level","title":"卡类型","width":10},
		  	{align:"center",field:"deadline","title":"有效日期","width":15},
		  	{align:"center",field:"limit_card_count","title":"限制次数","width":10},
		  	{align:"center",field:"remark","title":"备注","width":15},
		  	{align:"center",field:"company","title":"单位","width":20}	
		  	
	 	]],	    	
   	onLoadSuccess:function(value){ 
   		$("#datatotal").val(value.total);
   	},
   
		rownumbers:false,
		singleSelect:false,
	    collapsible:true,
		pagination: false,
	    fitColumns:true,
	    fit:true,
	    striped:true
    });
}
function f_invoice(val,row,index){
	if(row.is_print_recepit == 'N'){
		return '<a href=\"javascript:f_bukai(\''+row.sale_trade_num+'\')\">补开发票</a>';
	}else{
		return '<a href=\"javascript:f_zuofei(\''+row.sale_trade_num+'\')\">作废发票</a>';
	}
}

function f_bukai(sale_trade_num){
	$("#fapiao_sale_trade_num").val(sale_trade_num);
	$.ajax({
		url : 'getMaxInvoiceNum.action',
		data : {},
		type : 'post',
		success:function(data){
			$("#invoice_num1").val(data);
		}
	});
	$("#dlg-bkfp").dialog('open');
	$("#dlg-bkfp").dialog('center');
}
function save_fapiao(){
	if($("#invoice_num1").val() == ''){
		$("#invoice_num1").focus();
		return;
	}else if($("#invoice_name1").val() == ''){
		$("#invoice_name1").focus();
		return;
	}else if($("#invoice_type1").val() == ''){
		$("#invoice_type1").focus();
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'saveCardSaleInvoice.action',  
        data:{
          sale_trade_num:$("#fapiao_sale_trade_num").val(),
          title_info:$("#invoice_name1").val(),
          invoice_type:$("#invoice_type1").val(),
          invoice_num:$("#invoice_num1").val()
          },          
        type: "post",//数据发送方式   
        success: function(data){
        	$(".loading_div").remove();
        		var state = eval("("+data+")");
        		if(state.flag == 'error'){
        			$.messager.alert('提示信息', state.info,'error');
        			return;
        		}
        		$.messager.alert('提示信息', "正在打印发票。",'info');
        		if($("#invoiceprinttype").val()=='1'){
        			if($("input[name='invoice_l1']:checked").val() == 'mx'){
    					fapiao_point_mx($("#invoice_num1").val());
    				}else{
    					fapiao_point($("#invoice_num1").val());
    				}
     		    }else{
     			   printreport_invoice(state.user_id,state.account_num);
     		    }
        		$("#invoice_name1").val('');
        		$("#invoice_type1").val('');
        		$("#invoice_num1").val('');
        		$("#dlg-bkfp").dialog('close');
        		shoukachaxun();
        }
      });
}
function f_zuofei(sale_trade_num){
	$.messager.confirm('确认','确定作废所开发票吗？',function(r){    
	    if (r){    
	    	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
			$.ajax({
				url : 'invalidCardSaleInvoice.action',
				data : {"sale_trade_num":sale_trade_num},
				type : 'post',
				success:function(data){
					$(".loading_div").remove();
					$.messager.alert("操作提示", data, "info");
					shoukachaxun();
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
	    }    
	});
}

function isCheckBindCompany(sale_trade_num,str){
    $.ajax({
        url : 'isCheckBindCompany.action',
        data : {"sale_trade_num":sale_trade_num},
        type : 'post',
        success:function(data){
            if(data.split("-")[0] == 'ok'){
                up_bindCompany(str);
			}else{
                $.messager.alert("操作提示", data, "error");
			}
        },
        error : function() {
            $.messager.alert("操作提示", "操作错误", "error");
        }
    });
}