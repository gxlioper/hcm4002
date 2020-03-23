$(document).ready(function () {
	getcyitemListGrid();
	$("#ser_num").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			chaxun();
		} 
	});
	$("#ser_num").focus(function(){
		$("#ser_num").select();
	});
});

function chaxun(){
	getcustomerInfo();
	$("#cyitemList").datagrid('reload',{"exam_num":$("#ser_num").val()});
}

//查询人员基本信息
function getcustomerInfo(){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+$("#ser_num").val(),
		type:'post',
		success:function(data){
			if(data == 'null'){
				$("#exam_id").val('');
				$("#exam_num").val('');
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
			$("#exam_num").val(obj.exam_num);
			$("#user_name").html(obj.user_name);
			$("#sex").html(obj.sex);
			$("#age").html(obj.age);
			$("#company").html(obj.company);
			$("#customer_type").html(obj.customer_type);
			$("#set_name").html(obj.set_name);
		}
	});
}

function getcyitemListGrid(){
	var model = {"exam_num":$("#ser_num").val()};
	$("#cyitemList").datagrid({
		url: 'getDepPrintSanpleItem.action',
		queryParams: model,
		rownumbers:true,
		columns:[[
//		          {field:'ck',checkbox:true },
		          {align:"center",field:"canl","title":"操作","width":15,"formatter":f_canl},
		          {align:"center",field:"dep_name","title":"科室","width":15},
		          {align:"center",field:"item_code","title":"项目编码","width":15},
		          {align:'center',field:"item_name","title":"项目名称","width":20},
//		          {align:"center",field:"demo_indicator","title":"前缀","width":10},
//		          {align:"center",field:"color","title":"试管颜色","width":15,"formatter":f_color},
		          {align:"center",field:"sample_barcode","title":"条码号","width":30},
		          {align:'center',field:"demo_name","title":"样本名称","width":20},
		          {align:"center",field:"status_y","title":"样本状态","width":20},
//		          {align:"center",field:"is_binding_y","title":"绑管状态","width":20},
		          {align:"center",field:"is_application_y","title":"申请状态","width":20},
//		          {align:"center",field:"print_num","title":"打印次数","width":20}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
//	    	MergeCells('cyitemList', 'id,ck,canl,demo_indicator,barcode,color,demo_name,status_y,is_binding_y,is_application_y,print_num',0);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		toolbar:"#toolbar",
		border:false
	});
}
function f_color(value,row,index){
	return '<div style="height:15px;width:20px;margin-right:auto;margin-left:auto;background:#'+row.demo_color.substring(1,row.demo_color.length)+';"></div>'
}

function f_canl(val,row){
	if(row.status == 'Y' && row.dep_type == 'J'){
		return '<a href=\"javascript:f_canlSamplExamDetail(\''+row.id+'\')\">取消采样</a>';
	}
	return '';
}
function save_sampling_barcode(){
	if($("#ser_num").val() != $("#exam_num").val() || $("#exam_num").val() == ''){
		$.messager.alert('提示信息', '请先查询!','error');
		return;
	}

	var data = $('#cyitemList').datagrid('getRows');
	var sampl = new Array();
	var item_ids = new Array();
	for(i=0;i<data.length;i++){
		if(data[i].status == 'N' || data[i].status == 'W'){
			sampl.push({'id':data[i].id,'dep_type':data[i].dep_type});
			item_ids.push(data[i].item_id);
		}
	}
	if(sampl.length == 0){
		$.messager.alert('提示信息', '没有需要采样的样本!','error');
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({  
		url:'saveDepSampleItem.action',  
		data:{exam_num:$("#ser_num").val(),
			charingids:item_ids.toString(),
		    sampleExamDetails:JSON.stringify(sampl)},          
		type: "post",//数据发送方式   
		success: function(data){
			$(".loading_div").remove();
		    $.messager.alert('提示信息', data,'info');
		    $("#ser_num").focus();
		    $("#cyitemList").datagrid('reload',{"exam_num":$("#ser_num").val()});
		},
		error:function(data){
		    $(".loading_div").remove();
		    $.messager.alert('提示信息', data,'error');
		}
	});
}

function f_canlSamplExamDetail(id){
	if($("#ser_num").val() != $("#exam_num").val() || $("#exam_num").val() == ''){
		$.messager.alert('提示信息', '请先查询!','error');
		return;
	}
	var charingids = new Array();
	var data = $('#cyitemList').datagrid('getData');
	for(i=0;i<data.rows.length;i++){
		if(id == data.rows[i].id){
			charingids.push(data.rows[i].item_id);
		}
	}
	$.messager.confirm('提示信息','是否取消采样？',function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({
		        url:'canlSamplExamDetail.action',  
		        data:{exam_num:$("#ser_num").val(),sampleId:id,charingids:charingids.toString()},          
		        type: "post",//数据发送方式   
		        success: function(data){
		        	$(".loading_div").remove();
		        	$.messager.alert('提示信息', data,function(){});
		        	$("#cyitemList").datagrid('reload',{"exam_num":$("#ser_num").val()});
		        },
		        error:function(data){
		        	$(".loading_div").remove();
		        	$.messager.alert('提示信息', data,function(){});
		        }
			});
		}
	});
}
//补发申请
function send_application(){

	if($("#ser_num").val() != $("#exam_num").val() || $("#exam_num").val() == ''){
		$.messager.alert('提示信息', '请先查询!','error');
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'sendLisApplication.action',  
        data:{exam_num:$("#ser_num").val()},          
        type: "post",//数据发送方式   
        success: function(data){
        	$(".loading_div").remove();
        	$.messager.alert('提示信息', data,'error');
        	chaxun();
        },
        error:function(data){
        	$(".loading_div").remove();
        	$.messager.alert('提示信息', data,'error');
        }
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
//打印条码
function print_barcode(){
	if($("#ser_num").val() != $("#exam_num").val() || $("#exam_num").val() == ''){
		$.messager.alert('提示信息', '请先查询!','error');
		return;
	}
	var data = $('#cyitemList').datagrid('getData');
	var checkitemlists = $('#cyitemList').datagrid('getChecked');
	var item_codes = new Array();
	if(checkitemlists.length == 0){
		$.messager.alert('提示信息', "请选择需要打印条码的样本!",'error');
		return;
	}
	var print_num = 0;
	for(j=0;j<checkitemlists.length;j++){
		if(checkitemlists[j].sample_barcode == ''){
			$.messager.alert('提示信息', "样本"+checkitemlists[j].demo_name+"未绑定条码，不能打印条码!",'error');
			return;
		}
		for(i=0;i<data.rows.length;i++){
			if(checkitemlists[j].id == data.rows[i].id){
				item_codes.push(data.rows[i].item_code);
			}
		}
		if(checkitemlists[j].print_num > print_num){
			print_num = checkitemlists[j].print_num;
		}
	}
	$.ajax({
        url:'verifyUserPrintBarcode.action',  
        data:{},          
        type: "post",//数据发送方式   
        success: function(data){
        	if(data.split('-')[0] == 'ok'){
        		if(data.split('-')[1] > print_num){
        			print_code_now(item_codes.toString());
        		}else{
        			$.messager.confirm('提示信息','您的最大打印次数为'+data.split('-')[1]+'次，已超出打印次数限制，是否继续输入有打印权限的用户？',function(r){
        				if(r){
        					$("#dlg-print").dialog({
        						title:'打印条码',
        						href:'getPrintLoginPage.action',
        					});
        					$('#dlg-print').dialog('open');
        				}
        			});
        		}
        	}else{
        		$.messager.alert('提示信息', data.split('-')[1],'error');
        	}
        },
        error:function(data){
        	$.messager.alert('提示信息', data,'error');
        }
	});
}

function print_code_now(item_codes){
	if($("#barcode_print_type").val() == '1'){//调用旧打印程序
		var exeurl =$("#barexeurl").val() + " barcode "+$("#ser_num").val()+" "+item_codes;
		RunExe(exeurl);
	}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
		var exeurl = 'GuidBarServices://&barcode&'+$("#ser_num").val()+'&'+item_codes+'&1';
		RunServerExe(exeurl);
	}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
		$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
	}else if($("#barcode_print_type").val() == '4' || $("#barcode_print_type").val() == '5'){//调用4.0打印程序中间表调用模式
		var url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&barcode&'+$("#ser_num").val()+'&'+item_codes+'&1&1';
 		RunServerExe(url);
	}else{
		$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
	}
}

function RunExe(strPath) {
	try {
		var objShell = new ActiveXObject('wscript.shell');
		objShell.Run(strPath);
		objShell = null;

	} catch (e) {
		alert('找不到文件"'+strPath+'"(或它的组件之一)。请确定路径和文件名是否正确.');
	}
}

function RunServerExe(url){
	location.href=url;
}
