$(document).ready(function () {
	getykfpListGrid();
	getyksjListGrid();
	$("#ser_num").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			chaxun();
		} 
	});
	$('#ser_num').css('ime-mode','disabled');
	$('#ser_num').focus();
});

function chaxun(){
	getykfpListGrid();
	getyksjListGrid();
	getcustomerInfo();
	
	$("#yintui").html(0);
	$("#yintui_sj").html(0);
}

/*************************************************************发票tab**********************************************************************/
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
			for(var i=0;i<obj.length;i++){
				if(obj[i].id == '122'){
					str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(2)" style="margin-top:6px;" type="checkbox" style="" value="'+obj[i].id+'"/></dd><dt style="width:50px;">'+obj[i].name+'</dt><dd><input readonly="readonly" onclick="showcardInfo()" id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
				}else{
					str += '<dl><dd><input name="sffs_box" onclick="click_sfbox()" style="margin-top:6px;" type="checkbox" style="" value="'+obj[i].id+'"/></dd><dt style="width:50px;">'+obj[i].name+'</dt><dd><input id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" onblur="blur_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
				}
			}
			
			$("#charingtype").html(str);
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
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
				$("#ser_num").focus();
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
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}

var olddiscount,oldamount;

function getykfpListGrid(){//已开发票项目列表
	var model = {"exam_num":$("#ser_num").val()};
	$("#ykfpList").datagrid({
		url: 'getykfpItemList.action',
		queryParams: model,
		rownumbers:true,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		sortName: 'cdate',
		sortOrder: 'desc',
		columns:[[{align:'center',field:'ykfp_ck',checkbox:true},
		          {align:"center",field:"invoice_num","title":"发票编码","width":20},
		          {align:"center",field:"account_num","title":"收据单号","width":15},
		          {align:"center",field:"invoice_amount","title":"发票金额(元)","width":15},
		          {align:'center',field:"req_num","title":"申请单号","width":15},
		          {align:'center',field:"all_amount","title":"总金额(元)","width":10},
		          {align:"center",field:"item_code","title":"项目编码","width":10},
		          {align:'center',field:"item_name","title":"项目名称","width":15},
		          {align:"center",field:"exam_status_y","title":"检查状态","width":10},
		          {align:"center",field:"item_amount","title":"项目金额(元)","width":15},
		          {align:"center",field:"itemnum","title":"数量","width":10},
		          {align:"center",field:"amount","title":"收费金额(元)","width":15},
		          {align:"center",field:"discount","title":"折扣","width":10},
		          {align:"center",field:"creater","title":"收费人","width":10},
		          {align:"center",field:"create_time","title":"收费日期","width":15},
		          {align:"center",field:"is_application_y","title":"LIS/PACS申请","width":20}]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	var yishou = 0.0;
	    	$.each(value.rows, function(index, item){
	    		yishou += item.amount;
	    	});
	    	$("#yishou").html(decimal(yishou,2));
	    	MergeCells('ykfpList', 'account_num,invoice_num,account_num,invoice_amount',0,'req_num,req_num,all_amount',0);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		toolbar:"#toolbar",
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

//计算退费金额
function tuifeijine1(){
	var data = $('#ykfpList').datagrid('getSelections');
	var yintui = 0.0;
	for(var i=0;i<data.length;i++){
		yintui += data[i].amount;
	}
	$("#yintui").html(decimal(yintui,2));
}




function getyksjListGrid(){//查询已收费项目列表
	var model = {"exam_num":$("#ser_num").val()};
	$("#yksjList").datagrid({
		url: 'getyksjItemList.action',
		queryParams: model,
		rownumbers:true,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		sortName: 'cdate',
		sortOrder: 'desc',
		columns:[[{align:'center',field:'ykfp_ck',checkbox:true},
		          {align:'center',field:"req_num","title":"申请单号","width":20},
		          {align:'center',field:"all_amount","title":"总金额(元)","width":10},
		          {align:"center",field:"item_code","title":"项目编码","width":10},
		          {align:'center',field:"item_name","title":"项目名称","width":15},
		          {align:"center",field:"exam_status_y","title":"检查状态","width":10},
		          {align:"center",field:"item_amount","title":"项目金额(元)","width":15},
		          {align:"center",field:"itemnum","title":"数量","width":10},
		          {align:"center",field:"amount","title":"收费金额(元)","width":15},
		          {align:"center",field:"discount","title":"折扣","width":10},
		          {align:"center",field:"creater","title":"收费人","width":10},
		          {align:"center",field:"create_time","title":"收费日期","width":15},
		          {align:"center",field:"is_application_y","title":"LIS/PACS申请","width":20}]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	var yishou = 0.0;
	    	$.each(value.rows, function(index, item){
	    		yishou += item.amount;
	    	});
	    	$("#yishou_sj").html(decimal(yishou,2));
	    	MergeCells('yksjList', 'req_num,req_num,all_amount',0);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		toolbar:"#toolbar1",
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
}

//计算退费金额
function tuifeijine(){
	var data = $('#yksjList').datagrid('getSelections');
	var yintui = 0.0;
	for(var i=0;i<data.length;i++){
		yintui += data[i].amount;
	}
	$("#yintui_sj").html(decimal(yintui,2));
}

function tuifei_show(type){
	var data,isPrintRecepit;
	var req_nums = new Array();
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
		var req_num = data[0].req_num;
		var account_num = data[0].account_num;
		for(i=0;i<data.length;i++){
			if(data[i].exam_status == 'Y' || data[i].exam_status == 'C'){
				$.messager.alert('提示信息', '收费项目'+data[i].item_name+'已检查，不能退费!',function(){});
				return;
			}
			if(data[i].is_application == 'Y'){
				$.messager.alert('提示信息', '收费项目'+data[i].item_name+'已发申请，请先撤销申请!',function(){});
				return;
			}
			if(type == '1'){
				if(account_num != data[i].account_num){
					$.messager.alert('提示信息', '只能退同一个收据单号的项目!',function(){});
					return;
				}
			}else{
				if(req_num != data[i].req_num){
					$.messager.alert('提示信息', '只能退同一个申请单号的项目!',function(){});
					return;
				}
			}
		}
		
		if(type == '1'){
			var temp = '';
			var checkesitem = $('#ykfpList').datagrid('getRows');
			for(j=0;j<checkesitem.length;j++){
				if(account_num == checkesitem[j].account_num){
					if(temp != checkesitem[j].req_num){
						req_nums.push("'"+checkesitem[j].req_num+"'");
						temp = checkesitem[j].req_num;
					}
				}
			}
		}else{
			req_nums.push("'"+req_num+"'");
		}
	}
	$("#dlg-tuifei").dialog({
		title:'退费',
		href:'showCollectFeesRefuncd.action?isPrintRecepit='+isPrintRecepit+'&req_nums='+req_nums.toString(),
		buttons:[{text:'关闭',
				  width :80,
				  handler:function(){
					  $('#dlg-tuifei').dialog('close');
				  }
				}]
	});
	$('#dlg-tuifei').dialog('open');
}

//撤销收费
function chexiaoshoufei(type){
	var data,isPrintRecepit;
	var req_num = new Array();
	var account_num = new Array();
	if(type == '1'){
		isPrintRecepit = 'Y';
		data = $('#ykfpList').datagrid('getSelections');
		
		var temp = '';
		for(i=0;i<data.length;i++){
			if(temp != data[i].account_num){
				account_num.push("'"+data[i].account_num+"'");
				temp = data[i].account_num;
			}
		}
		if(account_num.length == 0){
			$.messager.alert('提示信息', '请选择需要撤销的收据单号!','error');
			return;
		}
		
	}else{
		isPrintRecepit = 'N';
		data = $('#yksjList').datagrid('getSelections');
		
		var temp = '';
		for(i=0;i<data.length;i++){
			if(temp != data[i].req_num){
				req_num.push("'"+data[i].req_num+"'");
				temp = data[i].req_num;
			}
		}
		if(req_num.length == 0){
			$.messager.alert('提示信息', '请选择需要撤销的申请单号!',function(){});
			return;
		}
	}
	$.messager.confirm('提示信息','确定撤销所选收费记录吗？',function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({
		        url:'chexiaoshoufei.action',  
		        data:{
		          exam_id:$("#exam_id").val(),
		          exam_num:$("#ser_num").val(),
		          isPrintRecepit:isPrintRecepit,
		          account_num:account_num.toString(),
		          req_nums:req_num.toString()
		          },          
		        type: "post",//数据发送方式   
		          success: function(data){
		        	  	$(".loading_div").remove();
		        	  	if(data.split("-")[0] == 'ok'){
		        	  		$.messager.alert('提示信息', data.split("-")[1],'info');
		        	  		chaxun();
		        	  	}else{
		        	  		$.messager.alert('提示信息', data.split("-")[1],'error');
		        	  	}
		            },
		            error:function(){
		            	$(".loading_div").remove();
		            	$.messager.alert('提示信息', "操作失败！",'error');
		            }  
		    });
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

//取消申请
function qvxiaoshenqing(id){
	var data = $('#'+id).datagrid('getSelections');
	
	if(data.length <= 0){
		$.messager.alert('提示信息', '请选择需要撤销申请的项目','error');
		return;
	}else{
		for(i=0;i<data.length;i++){
			if(data[i].exam_status == 'Y' || data[i].exam_status == 'C'){
				$.messager.alert('提示信息', '收费项目'+data[i].item_name+'已检查，不能撤销申请!',function(){});
				return;
			}
			if(data[i].is_application == 'N'){
				$.messager.alert('提示信息', '收费项目'+data[i].item_name+'未发送申请，不需要撤销申请!',function(){});
				return;
			}
		}
	}
	
	//调用取消申请接口
	var chargingids = new Array();
	for(var i=0;i<data.length;i++){
		chargingids.push(data[i].item_id);
	}
	var exam_num = $("#ser_num").val();
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url : 'delLisAndPacsApplication.action',
		data : {exam_num:exam_num,examInfoCharingItems:chargingids.toString()},
		type : 'post',
		success:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', data,function(){});
			chaxun();
		},
		error:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', data,function(){});
		}
	});
}

function jiaofeishengqi(){
	var exam_num = $("#ser_num").val();
	if(exam_num == ''){
		$.messager.alert('提示信息', '请输入体检号!','error');
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url : 'paymentApplication.action',
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

//补打发票
function fapiao_show(){
	
	if($("#ser_num").val() == ''){
		$.messager.alert("操作提示", "请先输入体检号!", "error");
		return;
	}
	
	$("#dlg-fapiao").dialog({
		title:'打印发票',
		href:'chargingSingleInvoickShow.action',
	});
	$('#dlg-fapiao').dialog('open');
}

//作废发票
function zuofeifapiao(){
	var data = $('#ykfpList').datagrid('getSelections');
	if(data.length == 0){
		$.messager.alert('提示信息', '请选择需要作废发票!','error');
		return;
	}
	var invoice_num = new Array();
	var invoice_num1 = new Array();
	var temp = '';
	var invoice_class = "";
	for(i=0;i<data.length;i++){
		if(temp != data[i].invoice_num){
			temp = data[i].invoice_num;
			invoice_num.push(data[i].invoice_num);
			invoice_num1.push("'"+data[i].invoice_num+"'");
			invoice_class = data[i].invoice_class;
		}
	}
	
	$.messager.confirm('提示信息','确定作废所开发票吗？发票号为'+invoice_num.toString(),function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
			$.ajax({
				url : 'invalidInvoiceGe.action',
				data : {"invoice_num":invoice_num1.toString(),"invoice_class":invoice_class},
				type : 'post',
				success:function(data){
					$(".loading_div").remove();
					$.messager.alert("操作提示", data, "info");
					getykfpListGrid();
					getyksjListGrid();
					
					$("#yintui").html(0);
					$("#yintui_sj").html(0);
					$.messager.confirm('提示信息','是否补打发票？',function(r){
        				if(r){
        					fapiao_show();
        				}
        			});
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
		}
	});
}

/************************************************************************************************************************/
//获取发票最大票号
function load_invoice() {
	if ($("#fapiao")[0].checked) {
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
   	    $("body").prepend(str);
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

function bd_mx_point(){
	if($("#ser_num").val() != ''){
		mx_point($("#ser_num").val());
	}else{
		$.messager.alert("操作提示", "请先输入体检号!", "error");
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

//打印发票
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

/**
2         * EasyUI DataGrid根据字段动态合并单元格
3         * @param fldList 要合并table的id
4         * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
5         */
function MergeCells(tableID, fldList,zhuIndex,fldList1,zhuIndex1) {
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
	
	if(fldList1 != undefined){
		Arr = fldList1.split(",");
		PerValue = "";
		CurValue = "";
		length = Arr.length - 1;
		
		for (i = length; i >= 1; i--) {
			z_fldName = Arr[zhuIndex1];
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
}
//打印普通收费单
function pt_point(){
	var a=+$("#ser_num").val();
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