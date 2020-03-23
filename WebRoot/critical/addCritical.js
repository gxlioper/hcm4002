$(document).ready(function () {
	getGrid();
	getExamDisease('');
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
	getcustomerInfo();
	getGrid();
}

//查询人员基本信息
function getcustomerInfo(){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+$("#ser_num").val(),
		type:'post',
		success:function(data){
			if(data == 'null'){
				$("#exam_id").val('');
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
				$("#exam_id").val(obj.exam_num);
				$("#user_name").html(obj.user_name);
				$("#sex").html(obj.sex);
				$("#age").html(obj.age);
				$("#set_name").html(obj.set_name);
				$("#join_date").html(obj.join_date);
				$("#company").html(obj.company);
				
				getFinalSummary(obj.exam_num);
				getExamDisease(obj.exam_num);
			}
		}
	});
}
//总检结论
function getFinalSummary(exam_num){
	$.ajax({
		url:'getFinalSummaryResult.action',
		data:{"exam_num":exam_num},
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
		          {align:"center",field:"dep_name","title":"收费项目","width":8},
		          {align:'center',field:"exam_doctor","title":"检查医生","width":10},
		          {align:'center',field:"exam_date","title":"检查时间","width":10},
		          {align:'',field:"item_name","title":"检查项目","width":15,"styler":f_clolor},
		          {align:"",field:"exam_result","title":"检查结果","width":25,"styler":f_clolor,"formatter":f_flowor},
	           	  {align:'center',field:"caozuo","title":"操作","width":10,"formatter":f_add}
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
	if(row.critical_id != null && row.critical_id > 0){
			return 'background:#f43ba1;';
		}
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
	var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+$("#exam_id").val();
	newWindow = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newWindow.focus();
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
function  f_add(val,row){
	if(row.item_name == '图片' || row.item_name == '科室结论' || row.item_name == '检查描述' || row.exam_result == ''){
		return "";
	}else{
		if(row.critical_id > 0){
			return '<a href="javascript:void(0)" onclick = "delCritical('+row.id+','+row.dep_category+','+row.item_id+')">撤销</a>' ;
		}else{
			return '<a href="javascript:void(0)" onclick = "addCritical('+row.id+','+row.dep_category+','+row.item_id+')">添加</a>';
		}
		
	}
	
}
function addCritical(id,dep_category,item_id){
//	alert(id);
//	alert(dep_category);
//	alert(item_id);
     $.messager.confirm('提示信息','确定添加此危机吗 ？',function(r){
     	if(r){
     		 $.ajax({
			url : 'addCritical.action',
			data : {
				    examinfo_id : id,
				    dep_category:dep_category,
				    item_id:item_id
					},
					type : "post",//数据发送方式   
					success : function(text) {
						if (text.split("-")[0] == 'ok') {
							$.messager.alert("操作提示", text);
							getGrid();
						} else {
							$.messager.alert("操作提示", text, "error");
						}
					},
					error : function() {
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
     	}
     });
     
}

function delCritical(id,dep_category,item_id){
	$.messager.confirm('提示信息','确定撤销此危机吗 ？',function(r){
     	if(r){
     		 $.ajax({
			url : 'delCritical.action',
			data : {
				    examinfo_id : id,
				    dep_category:dep_category,
				    item_id:item_id
					},
					type : "post",//数据发送方式   
					success : function(text) {
						if (text.split("-")[0] == 'ok') {
							$.messager.alert("操作提示", text);
							getGrid();
						} else {
							$.messager.alert("操作提示", text, "error");
						}
					},
					error : function() {
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
     	}
     });
}
