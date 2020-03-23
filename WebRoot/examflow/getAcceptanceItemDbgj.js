$(document).ready(function () {
	getGrid();
	getExamDisease('');
	$("#ser_num").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			chaxun();
		} 
	});
	$("#ser_num").focus();
	getToUserList();
});
function chaxun(){
	getcustomerInfo();
	getGrid();
}

//报告预览意见
function exam_suggestion(){
	$("#dlg-report").dialog({
		title:'意见建议',
		href:'getExamSuggestionEditPage.action?exam_num='+$("#ser_num").val()
	});
	$('#dlg-report').dialog('open');
}

//报告驳回
function reportReject(){
	if($("#exam_num").val()==null ||$("#exam_num").val()==""){
		$.messager.alert("提示","请先输入体检编号将报告信息查询出来!", "error");
		return;
	}
	$.ajax({
		url:'validateZJ.action?exam_num='+$('#ser_num').val(),
		success:function(data){
			if(data=="未总检"){
				$.messager.alert("警告信息","此纪录还未总检！","error");
				return;
			}else{
				$("#dlg-reject").dialog({
					title:'报告驳回',
					href:'getReportRejectEditPage.action?exam_num='+$('#ser_num').val()
				});
				$('#dlg-reject').dialog('open');
			}
		},
		error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	});

}

function reporbaogao1(type){
	var barids = $("#ser_num").val();
	if(barids == ''){
		$.messager.alert("操作提示", "请先输入体检号!","error");
		return;
	}
	if($("#barcode_print_type").val() == '1' || $("#barcode_print_type").val() == '2'){//调用旧预览程序
		printreport(barids);
	}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
		$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
	}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
    	printreport4(barids);
    }else if($("#barcode_print_type").val() == '5'){//调用5.0打印
    	printreport5($("#exam_num").val(),barids,1);
    }else{
    	$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    }
}
function reporbaogao2(type){
	var barids = $("#ser_num").val();
	if(barids == ''){
		$.messager.alert("操作提示", "请先输入体检号!","error");
		return;
	}
	if($("#zyb_barcode_print_type").val() == '5'){
    	printreport5($("#exam_num").val(),barids,2);
    }else{
    	$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    }
}

function printreport(barids){
	   var exeurl="reportServices://&0&"+barids+"&0";
	   location.href=exeurl;
}

//新版本报告预览4.0
function printreport4(exam_num){
	var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+exam_num;
	location.href=exeurl;
}

function printreport5(id,exam_num,type){
	if(type == 1){//预览普通报告
		var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+exam_num;
		location.href=exeurl;
	}else if(type == '2'){//预览职业病报告
		var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&opreport&"+exam_num;
		location.href=exeurl;
	}
}

function gettjbg(exam_num,app_type){
	var model = {exam_num:exam_num,app_type:app_type};
	document.getElementById("disease_tijianbaogao").innerHTML="";
	$.ajax({
		url:'gettjbgList.action',
		type:'post',
		data:model,
		success:function(data){
			var obj = eval('('+data+')');
			if(obj.DJD_path!=''){
				document.getElementById("disease_tijianbaogao").innerHTML="<img src='picture/"+obj.DJD_path+"' style='width:800px;height:1400px' />";
			}
		}
	})
}

//查询人员基本信息
function getcustomerInfo(){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+$("#ser_num").val(),
		type:'post',
		success:function(data){
			if(data == 'null'){
				$("#exam_num").val('');
				$("#user_name").html('');
				$("#sex").html('');
				$("#age").html('');
				$("#set_name").html('');
				$("#join_date").html('');
				$("#company").html('');
				$("#ser_num").focus();
				return;
			}else{
				var obj=eval("("+data+")");
				$("#exam_num").val(obj.exam_num);
				$("#user_name").html(obj.user_name);
				$("#sex").html(obj.sex);
				$("#age").html(obj.age);
				$("#set_name").html(obj.set_name);
				$("#join_date").html(obj.join_date);
				$("#company").html(obj.company);
				
				getFinalSummary(obj.exam_num);
				getExamDisease(obj.exam_num);
				gettjbg($("#ser_num").val(),"")
			}
		}
	});
}
//总检结论
function getFinalSummary(eid){
	$.ajax({
		url:'getFinalSummaryResult.action',
		data:{"exam_num":eid},
		type:'post',
		success:function(data){
			if(data != 'null'){
				var obj=eval("("+data+")");
				$("#zongjianjielun").val(obj.final_exam_result);
				$("#exam_status").html(obj.exam_status);
				$("#final_doctor").html(obj.final_doctor);
				$("#check_time").html(obj.check_time);
//				var is_acc = '<s:property value="isAcceptanceCheck"/>';
				if(obj.approve_status == 'Y'){
					if(obj.acceptance_check == 0){
						$("#hs_button").show();
						$("#qx_button").hide();
					}else{
						$("#qx_button").show();
						$("#hs_button").hide();
					}
				}
			}
		}
	});
}

function getExamDisease(exam_num){
	 var model={"exam_num":exam_num};
	$("#exam_disease").datagrid({
			 url:'getExamDiseaseResult.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
//		     pageSize: 15,//每页显示的记录条数，默认为10 
//		     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			 columns:[[
			    {align:'',field:'disease_name',title:'阳性/疾病发现',width:10},
			 	{align:'',field:'suggest',title:'阳性/疾病建议',width:20,"formatter":f_font_size}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    	},
		    	singleSelect:true,
//			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false
	});
}
function f_font_size(value,row,index){
	var count = row.suggest.indexOf("：")+1;
	if(count != -1){
		var tou = row.suggest.substring(0,count);
		var wei = row.suggest.substring(count,row.suggest.length-1);
		
		return '<span style="font-weight:bold;">'+tou+'</span>'+wei;
	}else{
		return row.suggest;
	}
}

function getToUserList(){
	$("#user").combobox({
		url :'getDatadis.action?com_Type=CHECKERS',
		editable : true, //不可编辑状态
		panelHeight : 'auto',//设置固定高度以显示滚动条
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			var inputter = getCookie("yulan_shenhe");
			if(inputter != undefined && inputter > 0){
				$('#user').combobox('setValue', inputter);
			}
		},
		onSelect : function(record){
			setCookie('yulan_shenhe',record.id);
		}
	})
}

//批量发送给审核医生
function sendcheckdoc(){
	if ($("#user").combobox('getValue').trim()==''){
		$('#user').combobox().next('span').find('input').focus();
		return;
	}
	if($("#exam_num").val()==''){
		$.messager.alert("警告信息","体检编号无效","error");
	}else{
	var exam_numList= new Array();
	exam_numList.push($("#exam_num").val());

	$.ajax({
		url:'flowexamsendchk.action', 
	    type: "post",
		data:{
			ids:exam_numList.toString(),
			doctor_id:$("#user").combobox('getValue').trim()
		},
		success: function(text){
		  	if (text.split("-")[0] == 'ok') {
				$.messager.alert("操作提示",text.split("-")[1],"");
				close_page();
			} else {
				$.messager.alert("警告信息",text,"error");
			}
		 	
		 },
		 error:function(){
		    	$("#sendchk_dlg").dialog("close");
		    	$.messager.alert('提示信息', "用户操作失败！",function(){});
		    }
	})
	}
};
function close_page(){
	var _parentWin =  window.opener ; 
	_parentWin.shuaxing();
	window.close();
}

function getGrid(){//
	var model = {"exam_num":$("#ser_num").val()};
	$("#item_result").datagrid({
		url: 'getAcceptanceItemResult.action',
		queryParams: model,
		rownumbers:false,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		sortName: 'cdate',
		sortOrder: 'desc',
		columns:[[
		          {align:"center",field:"dep_name","title":"收费项目","width":10},
		          {align:'center',field:"exam_doctor","title":"检查医生","width":10},
		          {align:'center',field:"exam_date","title":"检查时间","width":15},
		          {align:'',field:"item_name","title":"检查项目","width":15,"styler":f_clolor},
		          {align:"",field:"exam_result","title":"检查结果","width":25,"styler":f_clolor,"formatter":f_flowor}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	MergeCells('item_result', 'dep_name,exam_doctor,exam_date');
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		//striped:true,
		toolbar:"#toolbar",
		border:false,
		nowrap:false
	});
}

function f_clolor(value,row,index){
	if(row.dep_category == '17'){
		if(row.health_level == 'Y'){
			return 'color:#F00;';
		}else if(row.health_level == 'W'){
			return 'color:#FF9B00;';
		}
		if(row.item_id == '0'){
			return 'background:#FEEAA8;color:#ff5100;';
		}
	}else if(row.dep_category == '131'){
		if(row.health_level == 1 || row.health_level == 2 || row.health_level == 3|| row.health_level == 5|| row.health_level == 6){
			return 'color:#F00;';
		}else if(row.health_level == 4){
			return 'color:#FF9B00;';
		}
	}
}

function f_flowor(val,row){
	if(row.dep_category == '21'){
		if(row.exam_result == 'image_path'){
			return '<a href="javascript:void(0)" onclick = "show_picture(\''+row.req_id+'\')">查看图片</a>';
		}else{
			return row.exam_result.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'</br>');
		}
	}else if(row.dep_category == '131'){
		if(row.health_level == 1){
			return row.exam_result+' ↑';
		}else if(row.health_level == 2){
			return row.exam_result+' ↓';
		}else if(row.health_level == 5){
			return row.exam_result+' ↑↑';
		}else if(row.health_level == 6){
			return row.exam_result+' ↓↓';
		}else{
			return row.exam_result;
		}
	}else{
		return row.exam_result.replace(/\n/g,'</br>');
	}
}

function show_picture(id){
	var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+$("#exam_num").val();
	newWindow = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newWindow.focus();
}

//核收与取消核收
function f_heshou(check){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'flowacceptanceCheckExamInfo.action',
		data:{
			  "exam_num":$("#exam_num").val(),
		 	  "fromacc":check},
		type:'post',
		success:function(text){
			$(".loading_div").remove();
			if (text.split("-")[0] == 'ok') {
				$.messager.alert("操作提示", text.split("-")[1]);
			}else{
				$$.messager.alert("操作提示", text.split("-")[1], "error");
			}
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}

/**
2         * EasyUI DataGrid根据字段动态合并单元格
3         * @param fldList 要合并table的id
4         * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
5         */
      function MergeCells(tableID, fldList) {
            var Arr = fldList.split(",");
             var dg = $('#' + tableID);
            var fldName;
             var RowCount = dg.datagrid("getRows").length;
            var span;
            var PerValue = "";
            var CurValue = "";
             var length = Arr.length - 1;
             for (i = length; i >= 0; i--) {
               fldName = Arr[i];
                PerValue = "";
                span = 1;
                for (row = 0; row <= RowCount; row++) {
                    if (row == RowCount) {
                        CurValue = "";
                    }
                    else {
                        CurValue = dg.datagrid("getRows")[row][fldName];
                    }
                     if (PerValue == CurValue) {
                        span += 1;
                    }
                     else {
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