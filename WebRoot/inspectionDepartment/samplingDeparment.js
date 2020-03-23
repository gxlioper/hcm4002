$(document).ready(function () {
	getcyitemListGrid();
	$("#ser_num").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			chaxun();
		} 
	});
	
	$("#ser_barcode").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			yanzheng_barcode();
		} 
	});
	$("#ser_num").focus(function(){
		$("#ser_num").select();
	});
	
	$("#barcode").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			chaxun_barcode();
		} 
	});
});

function chaxun_barcode(){
	$.ajax({
		url:'getExamInfoByBarCode.action?sample_barcode='+$("#barcode").val(),
		type:'post',
		success:function(data){
			if(data == 'no'){
				$.messager.alert('提示信息', '该条码号未绑定!','error',function(){
					$("#barcode").val('');
					$("#ser_num").val('');
					$("#barcode").focus();
					chaxun();
				});
				return;
			}else{
				$("#ser_num").val(data);
				chaxun();
			}
		}
	});
}

function chaxun(){
	getcyitemListGrid();
	getcustomerInfo();
}

//查询人员基本信息
function getcustomerInfo(){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+$("#ser_num").val(),
		type:'post',
		success:function(data){
			$("#exam_id").val('');
			$("#exam_num").val('');
			$("#user_name").html('');
			$("#sex").html('');
			$("#age").html('');
			$("#company").html('');
			$("#customer_type").html('');
			$("#phone").html('');
			$("#exam_types").html('');
			$("#set_name").html('');
			document.getElementsByName("exampic").src="<%=request.getContextPath()%>/themes/default/images/user.png";
			//$("#ser_num").focus();
			if(data == 'null'){
				return;
			}
			var obj=eval("("+data+")");
			if(obj.status == 'Y'){
				return;
			}
			$("#exam_id").val(obj.id);
			$("#exam_num").val(obj.exam_num);
			$("#user_name").html(obj.user_name);
			$("#sex").html(obj.sex);
			$("#age").html(obj.age);
			$("#company").html(obj.company);
			$("#customer_type").html(obj.customer_type);
			$("#phone").html(obj.phone);
			$("#exam_types").html(obj.exam_types);
			$("#set_name").html(obj.set_name);
			if(obj.picture_path != ''){
				document.getElementById("exampic").src="getdjtexamPhoto.action?others="+obj.picture_path+"&"+new Date().getTime();
			}else{
				document.getElementsByName("exampic").src="<%=request.getContextPath()%>/themes/default/images/user.png";
			}
		}
	});
}

function getcyitemListGrid(){
	var model = {"exam_num":$("#ser_num").val()};
	$("#cyitemList").datagrid({
		url: 'getsamplingItemList.action',
		queryParams: model,
		rownumbers:true,
		columns:[[{field:'ck',checkbox:true },
		          {align:"center",field:"canl","title":"操作","width":15,"formatter":f_canl},
		          {align:"center",field:"item_code","title":"项目编码","width":15},
		          {align:'center',field:"item_name","title":"项目名称","width":20},
		          {align:"center",field:"demo_indicator","title":"前缀","width":10},
		          {align:"center",field:"color","title":"试管颜色","width":15,"formatter":f_color},
		          {align:"center",field:"barcode","title":"条码号","width":30,"formatter":f_barcode},
		          {align:'center',field:"demo_name","title":"样本名称","width":20},
		          {align:"center",field:"status_y","title":"样本状态","width":20},
		          {align:"center",field:"is_binding_y","title":"绑管状态","width":20},
		          {align:"center",field:"is_application_y","title":"申请状态","width":20},
		          {align:"center",field:"print_num","title":"打印次数","width":20}
		          ]],
	    onLoadSuccess:function(value){
	    	$(".loading_div").remove();
	    	$("#datatotal").val(value.total);
	    	MergeCells('cyitemList', 'id,ck,canl,demo_indicator,barcode,color,demo_name,status_y,is_binding_y,is_application_y,print_num',0);
	    	if($("#ser_num").val() == ''){
	    		$("#ser_num").focus();
	    	}else{
	    		var data = $('#cyitemList').datagrid('getRows');
	    		var boxs = $("input:checkbox[name='ck']");
	    		if(data.length == 0){
	    			$("#ser_num").focus();
	    		}else{
	    			$("#code_0").focus();
	    		}
	    		var old_id = 0;
	    		for(i=0;i<data.length;i++){
	    			if(old_id != data[i].id){
	    				old_id = data[i].id;
	    				if(data[i].status == 'W'){
	    					//2018-04-19,东北国际需求，进入页面时都不选中，然后哪一行扫入条码号默认自动选中哪一行
	    					//$('#cyitemList').datagrid('checkRow',i);
	    				}
//	    				else{
//	    					$(boxs[i]).parent().html('');
//	    				}
	    			}else{
	    				$(boxs[i]).parent().html('');
	    			}
	    		}
	    	}
	    	$(".datagrid-view").css('background-color','#F4F4F4');
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		toolbar:"#toolbar",
		border:false,
		checkOnSelect :false,
		onCheckAll:function(){
			$('#cyitemList').datagrid('clearSelections');
		},
		onClickRow : function(rowIndex, rowData){
			$('#cyitemList').datagrid('clearSelections');
		},
		onCheck:function(rowIndex,rowData){
			$('#cyitemList').datagrid('clearSelections');
		}
	});
}


function getcyitemListGrid_saveok(){
	var model = {"exam_num":$("#ser_num").val()};
	$("#cyitemList").datagrid({
		url: 'getsamplingItemList.action',
		queryParams: model,
		rownumbers:true,
		columns:[[{field:'ck',checkbox:true },
		          {align:"center",field:"canl","title":"操作","width":15,"formatter":f_canl},
		          {align:"center",field:"item_code","title":"项目编码","width":15},
		          {align:'center',field:"item_name","title":"项目名称","width":20},
		          {align:"center",field:"demo_indicator","title":"前缀","width":10},
		          {align:"center",field:"color","title":"试管颜色","width":15,"formatter":f_color},
		          {align:"center",field:"barcode","title":"条码号","width":30,"formatter":f_barcode},
		          {align:'center',field:"demo_name","title":"样本名称","width":20},
		          {align:"center",field:"status_y","title":"样本状态","width":20},
		          {align:"center",field:"is_binding_y","title":"绑管状态","width":20},
		          {align:"center",field:"is_application_y","title":"申请状态","width":20},
		          {align:"center",field:"print_num","title":"打印次数","width":20}
		          ]],
	    onLoadSuccess:function(value){
	    	$(".loading_div").remove();
	    	$("#datatotal").val(value.total);
	    	MergeCells('cyitemList', 'id,ck,canl,demo_indicator,barcode,color,demo_name,status_y,is_binding_y,is_application_y,print_num',0);
	    	$("#ser_num").focus();	    	
	    	$(".datagrid-view").css('background-color','#F4F4F4');
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		toolbar:"#toolbar",
		border:false,
		checkOnSelect :false,
		onCheckAll:function(){
			$('#cyitemList').datagrid('clearSelections');
		},
		onClickRow : function(rowIndex, rowData){
			$('#cyitemList').datagrid('clearSelections');
		},
		onCheck:function(rowIndex,rowData){
			$('#cyitemList').datagrid('clearSelections');
		}
	});
}

//扫码枪触发onchange事件自动选中该行
function autoCheckRow(index) {
	$('#cyitemList').datagrid('checkRow',index);
}

function f_barcode(val,row,index){
	if(row.status == 'W'){
		if(row.barCode_Class == '1'){
			return '<input class="jre textinput" type="text" id="code_'+index+'" value="'+row.sample_barcode+'" onblur="yangzhengbarcode(this,'+row.id+',\''+row.demo_indicator+'\')" onchange="autoCheckRow('+index+')"/>';
		}
//		else{
//			return '<input disabled="disabled" textinput" type="text" id="code_'+index+'" value="'+row.sample_barcode+'" onblur="yangzhengbarcode(this,'+row.id+',\''+row.demo_indicator+'\')" />'
//		}
		return row.sample_barcode;
	}else{
		return row.sample_barcode;
	}
}

function f_color(value,row,index){
	return '<div style="height:15px;width:20px;margin-right:auto;margin-left:auto;background:#'+row.demo_color.substring(1,row.demo_color.length)+';"></div>'
}

function f_canl(val,row){
	if(row.status == 'Y'){
		return '<a href=\"javascript:f_canlSamplExamDetail(\''+row.id+'\')\">取消采样</a>';
	}
	return '';
}

function yanzheng_barcode(){
	$("#erro").html("");
	var barcode = $("#ser_barcode").val();
	if(barcode == ''){
		return;
	}
	$.ajax({
		url:'yanzhengBarcode.action?sample_barcode='+$("#ser_barcode").val(),
		type:'post',
		success:function(data){
			if(data == 'no'){
				$("#erro").html("该条码号已存在!");
				$("#ser_barcode").val('');
				$("#ser_barcode").focus();
				return;
			}
			var data = $('#cyitemList').datagrid('getData');
			var count = 0;
			var code_count = 0;
			var aa = 0;
			for(i=0;i<data.rows.length;i++){
				if(data.rows[i].demo_indicator != '' && 
						barcode.indexOf(data.rows[i].demo_indicator) == 0 && 
						data.rows[i].status == 'W' && aa == 0){
					$('#code_'+i).val(barcode);
					aa++;
				}else{
					count ++;
				}
				
				if($('#code_'+i).val() != ''){
					code_count ++;
				}
			}
			
			if(count == data.rows.length){
				$("#erro").html("该条码号没有匹配的样本!");
			}
			$("#ser_barcode").val('');
			$("#ser_barcode").focus();

			if(code_count == data.rows.length){
				save_sampling_barcode();
			}
		},
		error:function(data){
			$("#erro").html("查询出错!");
		}
	});
}

function yangzhengbarcode(obj,id,indicator){
	if($(obj).val() == ''){
		return;
	}
	$.ajax({
		url:'yanzhengBarcode.action?sample_barcode='+$(obj).val()+'&sampleId='+id,
		type:'post',
		success:function(data){
			if($(obj).val().indexOf(indicator) != 0){
				$.messager.alert('提示信息', '该条码号颜色不对,请重新扫码!','error',function(){
					$(obj).val('');
					$(obj).focus();
				});
				return;
			}
			if(data == 'no'){
				$.messager.alert('提示信息', '该条码号已存在!','error',function(){
					$(obj).val('');
					$(obj).focus();
				});
			}
			var inputs=$(".jre");
			for(i=0;i<inputs.length;i++){
				if(inputs[i] != obj){
					if($(obj).val() == $(inputs[i]).val()){
						$.messager.alert('提示信息', '该条码号已存在,不能重复扫码!','error',function(){
							$(obj).val('');
							$(obj).focus();
						});
						return;
					}
				}
			}
		},
		error:function(data){
			$.messager.alert('提示信息', '查询出错!','error',function(){
				$(obj).focus();
			});
		}
	});
}

function save_sampling_barcode(){
	if($("#ser_num").val() != $("#exam_num").val() || $("#exam_num").val() == ''){
		$.messager.alert('提示信息', '请先查询!','error');
		return;
	}
	var rows = $('#cyitemList').datagrid('getRows');
	var checkrows = $('#cyitemList').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert('提示信息', '没有需要采样的样本!','error');
		return;
	}
	if(checkrows.length == 0){
		$.messager.alert('提示信息', '请选择需要采样的样本!','error');
		return;
	}
	for(i=0;i<checkrows.length;i++){
		if(checkrows[i].status != 'W'){
			$.messager.alert('提示信息', '样本'+checkrows[i].demo_name+'已采样，不能再次采样!','error');
			return;
		}
		if(checkrows[i].barCode_Class == 1){
			flag = true;
			for(j=0;j<rows.length;j++){
				if(checkrows[i].id == rows[j].id){
					if(flag){
						if($('#code_'+j).val() == ''){
							$.messager.alert('提示信息', '样本'+checkrows[i].demo_name+'未绑定条码，不能采样!','error');
							$('#code_'+j).focus();
							return;
						}
						flag = false;
					}
				}
			}
		}
	}
	$.messager.confirm('提示信息','全部条码已录完,是否保存？',function(r){
		if(r){
			var sampl = new Array();
			var item_ids = new Array();
			for(i=0;i<checkrows.length;i++){
				if(checkrows[i].status != 'W'){
					$.messager.alert('提示信息', '样本'+checkrows[i].demo_name+'已采样，不能再次采样!','error');
					return;
				}
				var flag  = true;
				for(j=0;j<rows.length;j++){
					if(checkrows[i].id == rows[j].id && checkrows[i].print_dep == $("#dep_id").val()){
						if(flag){
							if(checkrows[i].barCode_Class == 1){
								sampl.push({"id":rows[j].id,"sample_barcode":$('#code_'+j).val()});
							}else{
								sampl.push({"id":rows[j].id,"sample_barcode":rows[j].sample_barcode});
							}
							flag = false;
						}
						item_ids.push(rows[j].item_id);
					}
				}
			}
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({  
		        url:'samplingSamplExamDetail.action',  
		        data:{exam_num:$("#ser_num").val(),
		        	charingids:item_ids.toString(),
		        	sampleExamDetails:JSON.stringify(sampl)},          
		        type: "post",//数据发送方式   
		        success: function(data){
		        	$(".loading_div").remove();
		        	alert_autoClose("提示信息", data,"");
		        	$("#erro").html("");
		        	getcyitemListGrid_saveok();
					getcustomerInfo();
		        },
		        error:function(data){
		        	$(".loading_div").remove();
		        	$.messager.alert('提示信息', data,'error');
		        }
			});
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
		        	alert_autoClose("提示信息", data,"");
		        	chaxun();
		        },
		        error:function(data){
		        	$(".loading_div").remove();
		        	$.messager.alert('提示信息', data,function(){});
		        }
			});
		}
	});
}
//合并样本
function merge_sample(){
	if($("#ser_num").val() != $("#exam_num").val() || $("#exam_num").val() == ''){
		$.messager.alert('提示信息', '请先查询!','error');
		return;
	}
	
	var objs = $('#cyitemList').datagrid('getChecked');
	var row = new Array();
	var id = 0;
	for(i=0;i<objs.length;i++){
		if(id != objs[i].id){
			row.push(objs[i]);
			id=objs[i].id;
		}
	}
	if(row.length < 2){
		$.messager.alert('提示信息', '请选择两个以上的样本!','error');
		return;
	}
	for(i=0;i<row.length;i++){
		if(row[i].status != 'W'){
			$.messager.alert('提示信息', '样本('+row[i].demo_name+')不是未采样状态，不能合并!','error');
			return;
		}
	}
	$("#dlg-hbyb").dialog("open");
	$("#dlg-hbyb").dialog("center");
	$('#qzhbyb_list').datagrid({
		dataType: 'json',
		data:row,    
	    columns:[[    
	        {field:'demo_name',title:'样本名称',width:100}
	    ]],
	    singleSelect:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		nowrap:false
	}); 
}
//确认保存合并
function save_hebin_sample(){
	var row = $('#qzhbyb_list').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示信息', '请选择合并目标样本!','error');
		return;
	}
	var data = $('#qzhbyb_list').datagrid('getRows');
	var olds = new Array();
	for(i=0;i<data.length;i++){
		if(row.id != data[i].id){
			olds.push(data[i].id);
		}
	}
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'mergeSampleExamDetail.action',  
        data:{'sampleId':row.id,'old_sampleIds':olds.toString()},          
        type: "post",//数据发送方式   
        success: function(data){
        	$(".loading_div").remove();
        	alert_autoClose("提示信息", data,"");
        	$("#dlg-hbyb").dialog("close");
        	chaxun();
        },
        error:function(data){
        	$(".loading_div").remove();
        	$.messager.alert('提示信息', data,function(){});
        }
	});
}
//拆分样本
function split_up_sample(){
	if($("#ser_num").val() != $("#exam_num").val() || $("#exam_num").val() == ''){
		$.messager.alert('提示信息', '请先查询!','error');
		return;
	}
	var objs = $('#cyitemList').datagrid('getChecked');
	var row = new Array();
	var id = 0;
	for(i=0;i<objs.length;i++){
		if(id != objs[i].id){
			row.push(objs[i]);
			id=objs[i].id;
		}
	}
	if(row == null || row.length != 1){
		$.messager.alert('提示信息', '请选择一个需要拆分的样本!','error');
		return;
	}else if(row[0].status != 'W'){
		$.messager.alert('提示信息', '样本('+row[0].demo_name+')不是未采样状态，不能拆分!请先取消采样!','error');
		return;
	}
	var data = $('#cyitemList').datagrid('getRows');
	var rows = new Array();
	for(i=0;i<data.length;i++){
		if(row[0].id == data[i].id){
			rows.push(data[i]);
		}
	}
	
	$("#dlg-cfyb").dialog("open");
	$("#dlg-cfyb").dialog("center");
	$('#cfyb_list').datagrid({
		dataType: 'json',
		data:rows,    
	    columns:[[
	        {field:'ck_cf',checkbox:true },
	        {field:'item_code',title:'项目编码',width:20},
	        {field:'item_name',title:'项目名称',width:20}
	    ]],
	    singleSelect:false,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		nowrap:false
	}); 
}
//保存拆分样本数据
function save_split_sample(){
	var rows = $('#cfyb_list').datagrid('getRows');
	var select = $('#cfyb_list').datagrid('getSelections');
	if(select.length == 0){
		$.messager.alert('提示信息', '请选择需要拆分的目标收费项目!','error');
		return;
	}else if(rows.length == select.length){
		$.messager.alert('提示信息', '已选择了该样本下的所有项目,不需要拆分!','error');
		return;
	}
	
	var id = 0;
	var sample_id = 0;
	var chargingids = new Array();
	for(i=0;i<select.length;i++){
		chargingids.push(select[i].item_id);
		id = select[i].id;
		sample_id = select[i].sample_id;
	}
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'splitUpSampleExamDetail.action',  
        data:{
        	'id':id,
        	'sampleId':sample_id,
        	'examinfo_id':$("#exam_id").val(),
        	'exam_num':$("#exam_num").val(),
        	'charingids':chargingids.toString()},          
        type: "post",//数据发送方式   
        success: function(data){
        	$(".loading_div").remove();
        	alert_autoClose("提示信息", data,"");
        	$("#dlg-cfyb").dialog("close");
        	chaxun();
        },
        error:function(data){
        	$(".loading_div").remove();
        	$.messager.alert('提示信息', data,function(){});
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

function keyDown(event){ 
	var obj=$(".jre");
	var inputs = new Array();
	for(k=0;k<obj.length;k++){
		if($(obj[k]).parent().parent().eq(0).css("display") != 'none'){
			inputs.push(obj[k]);
		}
	}
    var focus=document.activeElement;
    var event=window.event||event;
    var key=event.keyCode; 
    for(var i=0; i<inputs.length; i++) { 
        if(inputs[i]===focus) break; 
    } 
    switch(key) { 
        case 37: //左
            if(i>0) inputs[i-1].focus(); 
            break; 
        case 38: //上
            if(i>0) inputs[i-1].focus();
            break; 
        case 39: //右
            if(i<inputs.length-1) inputs[i+1].focus(); 
            break; 
        case 40: //下
            if(i<inputs.length-1) inputs[i+1].focus();
            break;
		case 13://回车 
			if(i<inputs.length-1) inputs[i+1].focus();
            break;
    }
    if(key == 13 && i == inputs.length-1){
    	$("#save_exam").focus();
    }
} 

//弹出框自动关闭
function alert_autoClose(title,msg,icon){  
	 var interval;  
	 var time=500;  
	 var x=1;    //设置时间2s
	$.messager.alert(title,msg,icon,function(){});  
	 interval=setInterval(fun,time);  
	        function fun(){  
	      --x;  
	      if(x==0){  
	          clearInterval(interval);  
	  $(".messager-body").window('close');    
	       }  
	}; 
	}

//打印条码

function print_barcode(){
	if($("#ser_num").val() != $("#exam_num").val() || $("#exam_num").val() == ''){
		$.messager.alert('提示信息', '请先查询!','error');
		return;
	}
	var data = $('#cyitemList').datagrid('getData');
//	var checkitemlists = $('#cyitemList').datagrid('getChecked');
	var objs = $('#cyitemList').datagrid('getChecked');
	var checkitemlists = new Array();
	var id = 0;
	for(i=0;i<objs.length;i++){
		if(id != objs[i].id){
			checkitemlists.push(objs[i]);
			id=objs[i].id;
		}
	}
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
		setTimeout(function (){$("#cyitemList").datagrid("load",{"exam_num":$("#ser_num").val()});},2000); 
	}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
		var exeurl = 'GuidBarServices://&barcode&'+$("#ser_num").val()+'&'+item_codes+'&1';
		RunServerExe(exeurl);
		setTimeout(function (){$("#cyitemList").datagrid("load",{"exam_num":$("#ser_num").val()});},2000); 
	}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
		$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
	}else if($("#barcode_print_type").val() == '4' || $("#barcode_print_type").val() == '5'){//调用4.0打印程序中间表调用模式
		var url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&barcode&'+$("#ser_num").val()+'&'+item_codes+'&1&1';
 		RunServerExe(url);
 		setTimeout(function (){$("#cyitemList").datagrid("load",{"exam_num":$("#ser_num").val()});},2000);
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
		alert_all('找不到文件"'+strPath+'"(或它的组件之一)。请确定路径和文件名是否正确.');
	}
}

function RunServerExe(url){
	location.href=url;
}
