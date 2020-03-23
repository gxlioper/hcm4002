var zj_jbfl_data='',hy_gdt = false;
var fontsize=14;
$(document).ready(function () {
	$.ajax({
		url:'getDatadis.action?com_Type=ZJJBFL',
		type:'post',
		async: false,
		success:function(data){
			zj_jbfl_data = eval(data);
			
			var str = '<div id="mm3"class="easyui-menu"style="width:60px;">';
			for(i=0;i<zj_jbfl_data.length;i++){
				str += '<div onclick="menuModify('+i+')">'+zj_jbfl_data[i].name+'</div>'
			}
			str +='</div>';
			$("#mm").html(str);
		}
	});

	var isExamSummaryEdit = $("#isExamSummaryEdit").val();
	if(isExamSummaryEdit == 'Y'){
		$('#zongjianjielun').attr("readonly",false);
	}else{
		$('#zongjianjielun').attr("readonly",true);
	}
	if($("#examResultStyle").val() == '2' && isExamSummaryEdit == 'Y'){
		$("#scxvhao").show();
	}else if($("#examResultStyle").val() == '3' || $("#examResultStyle").val() == '4'){
		hy_gdt = true;
	}
	if($("#examSummaryPacsUrl").val() != ''){
		$("#pacs_yp").show();
	}
	
	var exam_num = $("#exam_num").val();
	var app_type = $("#app_type").val();
	
	getExamDisease(exam_num,'N',app_type);
	getFinalResult(exam_num,$("#isExamSummaryNew").val(),app_type);
	getcustomerInfo(exam_num);
	getptGrid(exam_num,app_type);
	gethyGrid(exam_num,app_type);
	getyxGrid(exam_num,app_type);
	gethebinSugGrid(0);
	gettjbg(exam_num,app_type);
	disease_logic_composite_list(exam_num,'N',app_type);
	
	$('#ser_exam_guidance').bind('keyup', function() {
		$("#exam_guidance_list").datagrid('reload',{"guidance_word":$('#ser_exam_guidance').val()});
	});
	shoubaogaopdfbuttion();
	
	fontsize=getCookie("fontsize");
	if((fontsize==null)||(fontsize=='')){
		fontsize=14;
	}
    if (parseInt(fontsize)>40){
    	fontsize=14;
    }
    
    
});

$(function () {
	$("#serch_disease").combobox({
		valueField: 'id',
        textField: 'disease_name',
        hasDownArrow:false,
        mode:'remote',
        url:'serchDiseaseList.action',
        onSelect:function(record){
        	var rows = $("#disease_sug_list").datagrid('getChecked');
        	if(rows.length != 1){
        		$.messager.alert("操作提示","单次只能修改一条记录。。", "info");
        		return;
        	}
        	$("#serch_disease").combobox('setValue','');
        	var obj = $("#disease_sug_list").datagrid('getRows');
        	for(i=0;i<obj.length;i++){
        		if(record.id == obj[i].disease_id){
        			$.messager.alert("操作提示","阳性发现："+record.disease_name+"已添加!", "error");
        			return;
        		}
        	}
        	load_suggestion(record.id,rows[0].disease_index);
        }
	});
	$("#disease_hebin_name").combobox({
		valueField: 'id',
        textField: 'disease_name',
        hasDownArrow:false,
        mode:'remote',
        url:'serchDiseaseList.action',
        onSelect:function(record){
        	$("#serch_disease").combobox('setValue','');
        	var obj = $("#disease_sug_list").datagrid('getRows');
        	for(i=0;i<obj.length;i++){
        		if(record.id == obj[i].disease_id){
        			$.messager.alert("操作提示","阳性发现："+record.disease_name+"已添加!", "error");
        			return;
        		}
        	}
        	gethebinSugGrid(record.id);
        }
	});
	jQuery.extend(jQuery.fn.datagrid.defaults.editors, {  
	    textarea: {
	    	init : function(container, options) {
	    		//alert($(container.context)[0].clientHeight); cellIndex
	    		var height = $(container.context)[0].clientHeight;
	    		if($(container.context)[0].cellIndex != 1){
	    			height -= 6;
	    		}
			var input = $(
					'<textarea class="datagrid-editable-input" style="resize:none;height:'+height+'px;font:14px/18px arial,\'Microsoft Yahei\';line-height:25px;"></textarea>')
					.appendTo(container);
				return input;
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, value) {
				$(target).val(value);
			},
			resize : function(target, width) {
				var input = $(target);
				if ($.boxModel == true) {
					input.width(width - (input.outerWidth() - input.width())-5);
				} else {
					input.width(width-5);
				}
			}
		}
	});
	
	//阳性发现合并model
	$("#disease_hebin_name").validatebox({
		required: true
	});
	
});
//加载疾病疾病建议
function load_suggestion(id,index){
	$.ajax({
		url:'serchDiseaseSuggestionList.action',
		type:'post',
		data:{disease_id:id,age:$("#age").html(),sex:$("#sex").html()},
		success:function(data){
			var obj=eval("("+data+")");
			if(obj.length == 1){
				obj[0].data_source = 1;
//				appendDisease(obj[0]);
				addDisease(obj[0],index);
			}else{
		    	$("#dlg-show_dis").dialog("open");
		    	getSugGrid(id,index);
		    	$("#dlg-show_dis").dialog('center');
			}
		}
	});
}

function addDisease(row,index){
	 var rows = $('#disease_sug_list').datagrid('getRows');    
     var r = rows[index];
     r.disease_name = row.disease_name;
     r.suggest = row.disease_name+ ':  '+row.suggest;
     r.disease_type = row.disease_type;
     r.disease_num = row.disease_num;
     r.disease_id = row.disease_id;
     r.data_source = row.data_source
     r.icd_10 = row.icd_10;
     r.disease_class = row.disease_class;
     r.data_source = row.data_source;
     sx_disease();
}

function appendDisease(row){
	var dis = $("#disease_sug_list").datagrid('getRows');
	row.disease_index = dis.length;
	row.exam_info_id = $("#exam_id").val();
	if('Y' == $("#isExamSuggest").val()){
		row.suggest = row.disease_name +'： '+row.suggest;
	}
	$("#disease_sug_list").datagrid('appendRow',row);
	sx_disease();
	$("#disease_sug_list").datagrid('enableDnd');
}

//新增阳性发现
function new_adddisease(){
	if($("#isExamSummaryNewDiseasePageShow").val() == 'Y'){
		$("#dlg-edit").dialog({
			title:'新增阳性发现',
			href:'showNewExamDisease.action'
		});
		$("#dlg-edit").dialog("open");
	}else{
		var dis = $("#disease_sug_list").datagrid('getRows');
		var row = new Object();
		row.id = 0;
		row.exam_info_id = $("#exam_id").val();
		row.disease_id = 0;
		row.disease_name = '';
		row.disease_index = dis.length;
		row.disease_type = 'Y';
		row.disease_types = '阳性发现';
		row.suggest = '';
		row.icd_10 = '';
		row.disease_class = '';
		row.disease_classs = '';
		row.disease_description = '';
		row.data_source = 1;
		row.disease_num = '';
		row.charging_item_name ='';
		row.item_name = '';
		row.exam_result = '';
		$("#disease_sug_list").datagrid('insertRow',{'index':0,'row':row});
		sx_disease();
		$("#disease_sug_list").datagrid('enableDnd');
	}
}

//查询人员基本信息
function getcustomerInfo(exam_num){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+exam_num,
		type:'post',
		success:function(data){
			var obj=eval("("+data+")");
			$("#arch_num").html(obj.arch_num);
			$("#exam_num_x").html(exam_num);
			$("#exam_id").val(obj.id);
			$("#user_name").html(obj.user_name);
			$("#sex").html(obj.sex);
			$("#age").html(obj.age);
			$("#exam_count").html(obj.exam_count+ '次');
			if(obj.exam_count > 1){
				$("#exam_count").css({"color":"red"});
			}
			if(obj.vipsigin=="1")
		       {
		         $("#vipsigin").show();
		       }else{
		    	 $("#vipsigin").hide(); 
		       }
//			$("#set_name").html(obj.set_name);
			if(obj.company == ''){
				$("#company").html('无');
			}else{
				$("#company").html(obj.company);
			}
			$("#phone").html(obj.phone);
			//$("#appletpacs").html(obj.company);
			
			if(obj.past_medical_history == ''){
				$("#past_medical_history").html('无');
			}else{
				$("#past_medical_history").html(obj.past_medical_history);
			}
			
			if('JKZTJ' == obj.exam_type_code){
				$("#jkz").show();
			}
		}
	});
}

function getFinalResult(exam_num,type,app_type){
	$.ajax({
		url:'getFinalExamResult.action?exam_num='+exam_num+'&sug_flag='+type+'&app_type='+app_type,
		type:'post',
		success:function(data){
			var obj=eval("("+data+")");
			$("#zongjianjielun").val(obj.final_exam_result);
			$("#id").val(obj.id);
			$("#approve_status").val(obj.approve_status);
			$("#exam_guidance").val(obj.exam_guidance);
			$("#censoring_status").val(obj.censoring_status);
			$("#report_dengji").html(obj.report_class + '级');
			if(obj.exam_doctor != ''){
				$("#exam_doctor").html(obj.exam_doctor);
				$('#exam_doctor').tooltip({    
					content: '<span style="color:red;font-size:14px;">总检时间：'+obj.final_date+'</span>',   
				});
			}
			if(obj.app_doctor != ''){
				$("#app_doctor").html(obj.app_doctor);
				$('#app_doctor').tooltip({    
					content: '<span style="color:red;font-size:14px;">审核时间：'+obj.check_time+'</span>',   
				});
			}
			$("#baocun").hide();
			$("#sh_baocun").hide();
			$("#fs_baocun").hide();
			$("#zongjian").hide();
			$("#qv_zongjian").hide();
			$("#sd_teshu").hide();
			$("#qv_teshu").hide();
			$("#shenghe").hide();
			$("#qv_shenghe").hide();
			$("#zhongsheng").hide();
			$("#qv_zhongsheng").hide();
			$("#yijian_qv").hide();
			$("#yijian_hui").hide();
			$("#bohui").hide();
			if($("#cancel_wbeResource").val() == 1){
				if(obj.cancel_type == 1){
					$("#yijian_hui").show();
				}else if(obj.id > 0){
					$("#yijian_qv").show();
				}
			}
			if(obj.cancel_type == 0){
				if($("#operation_type").val() == 1){
					if(obj.exam_status != 'Z'){
						$("#zongjian").show();
						$("#baocun").show();
					}else{
						$("#qv_zongjian").show();
					}
				}else if($("#operation_type").val() == 2){
					if(obj.approve_status == 'A'){
						$("#qv_shenghe").show();
					}else{
						$("#bohui").show();
						$("#shenghe").show();
						$("#sh_baocun").show();
					}
				}else if($("#operation_type").val() == 3){
					if(obj.censoring_status == '0'){
						$("#zhongsheng").show();
						$("#fs_baocun").show();
					}else{
						$("#qv_zhongsheng").show();
					}
				}
			}else{
				if($("#operation_type").val() == 1){
					$("#baocun").show();
				}else if($("#operation_type").val() == 2){
					$("#sh_baocun").show();
				}else if($("#operation_type").val() == 3){
					$("#fs_baocun").show();
				}
			}
			
			if($("#t_wbeResource").val() == 1){//特殊通知资源
				if(obj.t == 0){//设置
					$("#sd_teshu").show();
				}else if(obj.t == 1){
					$("#qv_teshu").show();
				}
			}
		}
	});
//	getfcsd(exam_num);
}

var odl_result_list;
//获取历史记录
function getResultHistory(){
	$.ajax({
		url:'getResultsHistoryList.action?exam_num='+$("#exam_num").val(),
		type:'post',
		success:function(data){
			if(data == 'null' || data == '[]'){
				$.messager.alert("操作提示",'无历史体检结果!', "error");
			}else{
				odl_result_list = eval("("+data+")");
				$("#dlg-ls").dialog({
					title:'历史结果对比',
					href:'resultsHistoryListPage.action?exam_num='+$("#exam_num").val()
				});
				$("#dlg-ls").dialog("open");
			}
		}
	});
}

function getresultList(){
	var str ='';
	for(i=0;i<odl_result_list.length;i++){
		str += '<div title="'+odl_result_list[i].acceptance_date+' 体检" style="padding:5px;" >'
			+'<div class="easyui-layout" style="height:530px;">'
			+'<div data-options="region:\'west\'" style="width:35%;">'
			+'<div class="easyui-panel" title="总检结论" data-options="" fit="true">'
			+'<textarea readonly="readonly" style="width: 99%;resize:none;border: 0px;height: 100%;font-size:14px;">'+odl_result_list[i].final_exam_result+'</textarea>'
			+'</div></div>'
			+'<div data-options="region:\'center\'">'
			+'<table id="ls_disease_'+i+'"></table>'
			+'</div>'
			+'</div>'
			+'</div>';
	}
	$("#ls_tt").html(str);
	
	for(i=0;i<odl_result_list.length;i++){
			var model={"examinfo_id":odl_result_list[i].exam_info_id};
			$("#ls_disease_"+i).datagrid({
					 url:'getExamDiseaseResult.action',
					 dataType: 'json',
					 queryParams:model,
					 rownumbers:true,
					 columns:[[
					    {align:'',field:'disease_name',title:'阳性/疾病发现',width:10},
					 	{align:'',field:'suggest',title:'阳性/疾病建议',width:20}
					 	]],	    	
				    	onLoadSuccess:function(value){
				    		$("#datatotal").val(value.total);
							changefontsize();
				    	},
				    	singleSelect:true,
						pagination: false,
					    fitColumns:true,
					    fit:true,
					    striped:true,
					    nowrap:false
			});
	}
}

//获取普通科室检查结果
function getptGrid(exam_num,app_type){
	 var model={"exam_num":exam_num,"app_type":app_type};
	 $("#pt_itemResultList").datagrid({
		 url:'getPtDepResultList.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
		 columns:[[
		           {align:'center',field:'dep_name',title:'科室',width:20,"formatter":f_date},
		           {align:'left',field:'item_name',title:'检查项目',width:25,"styler":f_color_pt},
		           {align:'left',field:'exam_result',title:'检查结果',width:60,"styler":f_color_pt,"formatter":f_result_pt}
//		           {align:'center',field:"exam_doctor","title":"医生","width":10},
//		           {align:'center',field:"exam_date","title":"时间","width":10}
		 ]],	    	
		 onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		MergeCells('pt_itemResultList', 'dep_name');
	    		changefontsize();
		 },
		 onDblClickCell:function(rowIndex, field, value){
			 if($("#is_final_history_show").val() == 'N'){
			 var rows = $(this).datagrid('getRows');
			 var tr = $(this).datagrid('getPanel').find('div.datagrid-body tr');
			 var td = $(tr[rowIndex]).children('td[field="'+field+'"]');
			 
			 $('#ls_graph').css('display', 'none');
			 $("#results_contrast").css("top",'40%');
			 $('#results_contrast').css('display', 'block');
			 
			 $("#item_lishi_table_div").datagrid({
				 url:'getPtItemResultsHistory.action',
				 dataType: 'json',
				 width:600,
				 queryParams:{'exam_info_id':$("#exam_id").val(),'item_id': rows[rowIndex].item_id},
				 rownumbers:false,
				 columns:[[
				           {align:'center',field:'exam_date',title:'检查时间',width:15},
				           {align:'center',field:'exam_result',title:'检查结果',width:25,"styler":f_color_pt1}
				 ]],
				 singleSelect:true,
				 collapsible:true,
				 pagination: false,
				 striped:true,
				 nowrap:false,
				 fitColumns:true
			 });
			 
//			 $(td).mouseleave(function(){
//				 $(td).unbind("mouseleave");
//				 $('#results_contrast').css('display', 'none');
//			 });
			 changefontsize();
			 }
		 },
		 onClickCell:function(rowIndex, field, value){
			 if($("#is_final_history_show").val() == 'Y'){
				 var rows = $(this).datagrid('getRows');
				 var tr = $(this).datagrid('getPanel').find('div.datagrid-body tr');
				 var td = $(tr[rowIndex]).children('td[field="'+field+'"]');
				 
				 $('#ls_graph').css('display', 'none');
				 $("#results_contrast").css("top",'40%');
				 $('#results_contrast').css('display', 'block');
				 
				 $("#item_lishi_table_div").datagrid({
					 url:'getPtItemResultsHistory.action',
					 dataType: 'json',
					 width:600,
					 queryParams:{'exam_info_id':$("#exam_id").val(),'item_id': rows[rowIndex].item_id},
					 rownumbers:false,
					 columns:[[
					           {align:'center',field:'exam_date',title:'检查时间',width:15},
					           {align:'center',field:'exam_result',title:'检查结果',width:25,"styler":f_color_pt1}
					 ]],
					 singleSelect:true,
					 collapsible:true,
					 pagination: false,
					 striped:true,
					 nowrap:false,
					 fitColumns:true
				 });
				 
				 $(td).mouseleave(function(){
					 $(td).unbind("mouseleave");
					 $('#results_contrast').css('display', 'none');
				 });
				 changefontsize();
			 }
		 },
		 singleSelect:true,
		 collapsible:true,
		 pagination: false,
		 fitColumns:true,
		 striped:true,
		 fit:true,
		 nowrap:false
 });
}
var xvxian = '<DIV style="BORDER-TOP: #00686b 1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV>'
function f_date(value,row,index){
	var date = '';
	if(row.exam_date.length > 0){
		date = row.exam_date.split(" ")[0];
	}
	return row.dep_name = row.dep_name + '</br>'+xvxian + row.exam_doctor +'</br>'+xvxian + date;
}
function f_color_pt(value,row,index){
	if(row.critical_id != null && row.critical_id > 0){
		return 'background:#f43ba1;';
	}
	if(row.health_level == 'Y'){
		return 'color:#F00;';
	}else if(row.health_level == 'W'){
		return 'color:#FF9B00;';
	}
	if(row.item_id == '0'){
		return 'background:#FEEAA8;color:#ff5100;';
	}
}

function f_result_pt(value,row,index){
	if(row.item_id == '0'){
		return row.exam_result.replace(/\n/g,'</br>');
	}else{
		return row.exam_result;
	}
}

function f_color_pt1(value,row,index){
	if(row.health_level == 'Y'){
		return 'color:#F00;';
	}else if(row.health_level == 'W'){
		return 'color:#FF9B00;';
	}
}

function gethyGrid(exam_num,app_type){
	var model={"exam_num":exam_num,"app_type":app_type};
	$("#hy_itemResultList").datagrid({
			 url:'getHyDepResultList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:false,
			 columns:[[
				{align:'center',field:'dep_name',title:'收费项目',width:100,"formatter":f_date},
				{align:'center',field:'item_name',title:'检查项目',width:150,"styler":f_color_hy},
			 	{align:'center',field:'exam_result',title:'检查结果',width:120,"styler":f_color_hy},
			 	{align:'center',field:'bs',title:'标识',width:60,"formatter":f_bs,"styler":f_color_hy},
			 	{align:'center',field:'ref_value',title:'参考值',width:150},
			 	{align:'center',field:'item_unit',title:'单位',width:60}
//			 	{align:'center',field:"exam_doctor","title":"医生","width":60},
//				{align:'center',field:"exam_date","title":"时间","width":60},
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		MergeCells('hy_itemResultList', 'dep_name,exam_doctor,exam_date');
		    		changefontsize();
		    	},
		    	onDblClickCell:function(rowIndex, field, value){
		    		if($("#is_final_history_show").val() == 'N'){
					 var rows = $(this).datagrid('getRows');
					 var tr = $(this).datagrid('getPanel').find('div.datagrid-body tr');
					 var td = $(tr[rowIndex]).children('td[field="'+field+'"]');
					 
					 $("#results_contrast").css("top",'40%');
					 $('#results_contrast').css('display', 'block');
					 
					 $("#item_lishi_table_div").datagrid({
						 url:'getHyItemResultsHistory.action',
						 dataType: 'json',
						 width:600,
						 queryParams:{'exam_info_id':$("#exam_id").val(),'item_id': rows[rowIndex].item_id},
						 rownumbers:false,
						 columns:[[

						           {align:'center',field:'exam_date',title:'检查时间',width:40},
						           {align:'center',field:'exam_result',title:'检查结果',width:60,"styler":f_color_hy},
								   {align:'center',field:'bs',title:'标识',width:20,"formatter":f_bs,"styler":f_color_hy},
								   {align:'center',field:'item_unit',title:'单位',width:20},
						 ]],
						 singleSelect:true,
						 collapsible:true,
						 pagination: false,
						 striped:true,
						 nowrap:false,
						 fitColumns:true
					 });
					 
					 //历史对比曲线图
					 drawGraph($("#exam_id").val(), rows[rowIndex].item_id);
					 $('#ls_graph').css('display', 'block');
					 
//					 $(td).mouseleave(function(){
//						 $(td).unbind("mouseleave");
//						 $('#results_contrast').css('display', 'none');
//						 $('#ls_graph').css('display', 'none');
//					 });
					 changefontsize();
		    		}
				 },
				 onClickCell:function(rowIndex, field, value){
					 if($("#is_final_history_show").val() == 'Y'){
					 var rows = $(this).datagrid('getRows');
					 var tr = $(this).datagrid('getPanel').find('div.datagrid-body tr');
					 var td = $(tr[rowIndex]).children('td[field="'+field+'"]');
					 
					 $("#results_contrast").css("top",'40%');
					 $('#results_contrast').css('display', 'block');
					 
					 $("#item_lishi_table_div").datagrid({
						 url:'getHyItemResultsHistory.action',
						 dataType: 'json',
						 width:600,
						 queryParams:{'exam_info_id':$("#exam_id").val(),'item_id': rows[rowIndex].item_id},
						 rownumbers:false,
						 columns:[[

						           {align:'center',field:'exam_date',title:'检查时间',width:40},
						           {align:'center',field:'exam_result',title:'检查结果',width:60,"styler":f_color_hy},
								   {align:'center',field:'bs',title:'标识',width:20,"formatter":f_bs,"styler":f_color_hy},
								   {align:'center',field:'item_unit',title:'单位',width:20},
						 ]],
						 singleSelect:true,
						 collapsible:true,
						 pagination: false,
						 striped:true,
						 nowrap:false,
						 fitColumns:true
					 });
					 
					 //历史对比曲线图
					 drawGraph($("#exam_id").val(), rows[rowIndex].item_id);
					 $('#ls_graph').css('display', 'block');
					 
					 $(td).mouseleave(function(){
						 $(td).unbind("mouseleave");
						 $('#results_contrast').css('display', 'none');
						 $('#ls_graph').css('display', 'none');
					 });
					 changefontsize();
					 }
				 },
		    	singleSelect:true,
			    collapsible:true,
				pagination: false,
			    fitColumns:hy_gdt,
			    fit:true,
			    striped:true,
			    nowrap:false
	});
}
function f_color_hy(value,row,index){
	if(row.critical_id != null && row.critical_id > 0){
		return 'background:#f43ba1;';
	}
	if(row.health_level == 1){
		return 'color:#ff0000;';
	}else if(row.health_level == 2){
		return 'color:#0000ff;';
	}else if(row.health_level == 3){
		return 'color:#ff5100;';
	}else if(row.health_level == 4){
		return 'color:#FF9B00;';
	}
}
function f_bs(val,row){
	if(row.health_level == 1){
		return '↑';
	}else if(row.health_level == 2){
		return '↓';
	}else{
		return '';
	}
}

function getyxGrid(exam_num,app_type){
	var model={"exam_num":exam_num,"app_type":app_type};
	$("#yx_itemResultList").datagrid({
			 url:'getYxDepResultList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:false,
			 columns:[[
			       {align:'center',field:'dep_name',title:'科室',width:15,"formatter":f_date},
		           {align:'center',field:'item_name',title:'项目',width:10,"styler":f_color_yx},
		           {align:'left',field:'exam_result',title:'检查结果',width:55,"formatter":f_show_picture,"styler":f_color_yx},
//		           {align:'center',field:"exam_doctor","title":"医生","width":10},
//		           {align:'center',field:"exam_date","title":"时间","width":10}
				   {align:'center',field:"caozuo","title":"操作","width":10,"formatter":f_add}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		MergeCells('yx_itemResultList', 'dep_name,exam_doctor,exam_date');
		    		changefontsize();
		    	},
		    	onDblClickCell:function(rowIndex, field, value){
		    		if($("#is_final_history_show").val() == 'N'){
					 var rows = $(this).datagrid('getRows');
					 var tr = $(this).datagrid('getPanel').find('div.datagrid-body tr');
					 var td = $(tr[rowIndex]).children('td[field="'+field+'"]');
					 $('#ls_graph').css('display', 'none');
					 $("#results_contrast").css("top",'40%');
					 $('#results_contrast').css('display', 'block');
					 
					 $("#item_lishi_table_div").datagrid({
						 url:'getYxItemResultsHistory.action',
						 dataType: 'json',
						 width:700,
						 queryParams:{'exam_info_id':$("#exam_id").val(),'item_id': rows[rowIndex].sam_demo_id},
						 rownumbers:false,
						 columns:[[
						           {align:'center',field:'exam_date',title:'检查时间',width:10},
						           {align:'center',field:'exam_desc',title:'检查描述',width:40},
						           {align:'center',field:'exam_result',title:'检查结果',width:20}
						 ]],
						 singleSelect:true,
						 collapsible:true,
						 pagination: false,
						 striped:true,
						 nowrap:false,
						 fitColumns:true
					 });
//					 $(td).mouseleave(function(){
//						 $(td).unbind("mouseleave");
//						 $('#results_contrast').css('display', 'none');
//					 });
				
					 changefontsize();
		    		}
				 },
				 onClickCell:function(rowIndex, field, value){
					 if($("#is_final_history_show").val() == 'Y'){
					 var rows = $(this).datagrid('getRows');
					 var tr = $(this).datagrid('getPanel').find('div.datagrid-body tr');
					 var td = $(tr[rowIndex]).children('td[field="'+field+'"]');
					 
					 $("#results_contrast").css("top",'40%');
					 $('#results_contrast').css('display', 'block');
					 
					 $("#item_lishi_table_div").datagrid({
						 url:'getYxItemResultsHistory.action',
						 dataType: 'json',
						 width:700,
						 queryParams:{'exam_info_id':$("#exam_id").val(),'item_id': rows[rowIndex].sam_demo_id},
						 rownumbers:false,
						 columns:[[
						           {align:'center',field:'exam_date',title:'检查时间',width:10},
						           {align:'center',field:'exam_desc',title:'检查描述',width:40},
						           {align:'center',field:'exam_result',title:'检查结果',width:20}
						 ]],
						 singleSelect:true,
						 collapsible:true,
						 pagination: false,
						 striped:true,
						 nowrap:false,
						 fitColumns:true
					 });
					 $(td).mouseleave(function(){
						 $(td).unbind("mouseleave");
						 $('#results_contrast').css('display', 'none');
					 });
					 changefontsize();
					 }
				 },
		    	singleSelect:true,
			    collapsible:true,
				pagination: false,
			   fitColumns:true,
 			    fit:true,
			    striped:true,
			    nowrap:false
	});
}
function f_color_yx(val,row){
	if(row.critical_id != null && row.critical_id > 0){
		return 'background:#f43ba1;';
	}
	if(row.item_name == '检查结论'){
		return 'background:#FEEAA8;color:#ff5100;';
	}
}

function f_show_picture(val,row){
	if(row.exam_result == 'image_path'){
		return '<a href="javascript:void(0)" onclick = "show_picture(\''+row.req_id+'\')">查看图片</a>';
	}else{
		if(row.critical_id != null && row.critical_id > 0){
			return row.exam_result.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'</br>');
		}else{
			return row.exam_result.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'</br>');
		}
	}
}

function show_picture(id){
	var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+$("#exam_num").val();
	newwin = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newwin.focus();
}

function getExamDisease(exam_num,type,app_type){
	var lastIndex;
	var columns = new Array();
	columns.push({field:'ck',checkbox:true });
	columns.push({align:'',field:'charging_item_name',title:'收费项目',width:30,editor:{type:'textarea',options:{}},"formatter":f_jygsh});
	columns.push({align:'',field:'item_name',title:'检查项目',width:20,editor:{type:'textarea',options:{}},"formatter":f_jygsh});
	columns.push({align:'',field:'exam_result',title:'结果',width:40,editor:{type:'textarea',options:{}},"formatter":f_jygsh});
	columns.push({align:'',field:'disease_name',title:'阳性/疾病发现',width:40,editor:{type:'textarea',options:{}},"formatter":f_jygsh});
	columns.push({align:'',field:'suggest',title:'阳性/疾病建议',width:70,editor:{type:'textarea',options:{}},"formatter":f_jygsh});
//	if($("#operation_type").val() == 1){
//		columns.push({align:'',field:'final_remarke',title:'总检备注',width:20,editor:{type:'textarea',options:{}},"formatter":f_jygsh});
//		columns.push({align:'',field:'approve_remarke',title:'审核备注',width:20});
//		columns.push({align:'',field:'censoring_remarke',title:'终审备注',width:20});
//	}else if($("#operation_type").val() == 2){
//		columns.push({align:'',field:'final_remarke',title:'总检备注',width:20});
//		columns.push({align:'',field:'approve_remarke',title:'审核备注',width:20,editor:{type:'textarea',options:{}},"formatter":f_jygsh});
//		columns.push({align:'',field:'censoring_remarke',title:'终审备注',width:20});
//	}else{
//		columns.push({align:'',field:'final_remarke',title:'总检备注',width:20});
//		columns.push({align:'',field:'approve_remarke',title:'审核备注',width:20});
//		columns.push({align:'',field:'censoring_remarke',title:'终审备注',width:20,editor:{type:'textarea',options:{}},"formatter":f_jygsh});
//	}
	columns.push({align:'center',field:'biaoxing',title:'标星',width:10,"formatter":f_biao_xing});
	columns.push({align:'center',field:'jbfl',title:'疾病分类',width:17,"formatter":f_jbfl}),//
	columns.push({align:'center',field:'caozuo',title:'操作',width:10,"formatter":f_sc_disease});
	 var model={"exam_num":exam_num,"sug_flag":type,"app_type":app_type};
	$("#disease_sug_list").datagrid({
			 url:'getExamDiseaseListNew.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
			 columns:[columns],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		$(this).datagrid('enableDnd');
		    		var row = $('#disease_sug_list').datagrid('getRows');
		    		for (i = 0; i < row.length; i++) { 
                        $('#mb' +row[i].disease_index ).menubutton();
                    }
					changefontsize();
		    	},
		    	singleSelect:false,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false,
			    toolbar:"#toolbar",
			    checkOnSelect:false,
			    onClickCell:function(index, field, row){
			    	sx_disease();
			    	$('#disease_sug_list').datagrid('beginEdit', index);
			    	
			    	$("#datagrid-row-r1-2-"+index).unbind();
			    	
					var editors = $('#disease_sug_list').datagrid('getEditor',{index:index,field:'disease_name'});
					$(editors.target).change(function(){
						$('#disease_sug_list').datagrid('endEdit', index);
					});
					var editors1 = $('#disease_sug_list').datagrid('getEditor',{index:index,field:'suggest'});
					$(editors1.target).change(function(){
						$('#disease_sug_list').datagrid('endEdit', index);
					});
//					var editors2 = '';
//					if($("#operation_type").val() == 1){
//						editors2 = $('#disease_sug_list').datagrid('getEditor',{index:index,field:'final_remarke'});
//					}else if($("#operation_type").val() == 2){
//						editors2 = $('#disease_sug_list').datagrid('getEditor',{index:index,field:'approve_remarke'});
//					}else{
//						editors2 = $('#disease_sug_list').datagrid('getEditor',{index:index,field:'censoring_remarke'});
//					}
//					$(editors2.target).change(function(){
//						$('#disease_sug_list').datagrid('endEdit', index);
//					});
					if(field == 'suggest'){
						$(editors1.target).focus();
					}else if(field == 'disease_name'){
						$(editors.target).focus();
					}else{
//						$(editors2.target).focus();
					}
					changefontsize();
			    },
			    onAfterEdit:function(index, row, changes){
			    	$(this).datagrid('enableDnd',index);
			    	changefontsize();
			    },
			    onSelectAll:function (rows){
			    	$('#disease_sug_list').datagrid('clearSelections');
			    	changefontsize();
			    },
			    onSelect:function (rowIndex, rowData){
			    	$('#disease_sug_list').datagrid('clearSelections');
			    	changefontsize();
			    },
			    onCheck:function (rowIndex, rowData){
			    	check_disease_obj.push(rowData);
			    	changefontsize();
			    },
			    onUncheck:function (rowIndex, rowData){
			    	check_disease_obj.remove(rowData);
			    	changefontsize();
			    },
			    onCheckAll:function (rows){
			    	var obj = $("#disease_sug_list").datagrid('getRows');
			    	for(i=0;i<obj.length;i++){
			    		check_disease_obj.push(obj[i]);
			    	}
			    	changefontsize();
			    },
			    onUncheckAll:function (row){
			    	check_disease_obj.length = 0;
			    	changefontsize();
			    }
	});
}
function f_jygsh(val,row,index){
	return val.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'</br>');
}
//删除疾病信息
function f_sc_disease(val,row,index){
	return '<a href="javascript:void(0)" onclick = "sc_disease('+row.disease_index+')">删除</a>';
}

function sc_disease(index){
	var obj = $("#disease_sug_list").datagrid('getRows');
	for(i=0;i<obj.length;i++){
		if(obj[i].disease_index == index){
			$("#disease_sug_list").datagrid('deleteRow',i);
		}
	}
	sx_disease();
}

//标星
function f_biao_xing(val,row,index){
	if(row.suggest.indexOf("★") == -1){//标星
		return '<a href="javascript:void(0)" style="font-size:20px;" onclick = "biao_xing(1,'+row.disease_index+')">☆</a>';
	}else{//取消标星
		return '<a href="javascript:void(0)" style="font-size:20px;" onclick = "biao_xing(2,'+row.disease_index+')">★</a>';
	}
}
function biao_xing(type,index){
	var obj = $("#disease_sug_list").datagrid('getRows');
	for(i=0;i<obj.length;i++){
		if(obj[i].disease_index == index){
			if(type == 1){
				obj[i].suggest = "★"+obj[i].suggest;
				obj[i].disease_name = "★"+obj[i].disease_name;
			}else{
				obj[i].suggest = obj[i].suggest.replace("★","");
				obj[i].disease_name = obj[i].disease_name.replace("★","");
			}
			$("#disease_sug_list").datagrid('refreshRow',i);
			$('#mb' + i).menubutton();
		}
	}
}

function f_jbfl(val,row,index){
	if(row.disease_class == ''){
		row.disease_class = zj_jbfl_data[0].id;
		row.disease_classs = zj_jbfl_data[0].name;
	}
	return'<a href="javascript:void(0)" id="mb' + row.disease_index + '" style="height:24px;" class="easyui-menubutton" menu="#mm3" onmouseover="ShowMenu(' + row.disease_index + ')">'+row.disease_classs+'</a>';
}

var fb_key=''
function ShowMenu(key) {
	fb_key = key;
}
function menuModify(key) {
	var obj = $("#disease_sug_list").datagrid('getRows');
	for(i=0;i<obj.length;i++){
		if(obj[i].disease_index == fb_key){
			obj[i].disease_class = zj_jbfl_data[key].id;
			obj[i].disease_classs = zj_jbfl_data[key].name;
			$("#disease_sug_list").datagrid('refreshRow',i);
			$('#mb' + fb_key).menubutton();
		}
	}
//	sx_disease();
}

//刷新疾病表格数据
function sx_disease(){
	var obj = $("#disease_sug_list").datagrid('getRows');
	for(i=0;i<obj.length;i++){
		obj[i].disease_index = i;
		$('#disease_sug_list').datagrid('endEdit', i);
		$("#disease_sug_list").datagrid('refreshRow',i);
	}
	
	for (i = 0; i < $('#disease_sug_list').datagrid('getRows').length; i++) {        
        $('#mb' + i).menubutton();
    }
}

//重新生成结论与建议
function creat_new_jielun(){
	$('#disease_sug_list').datagrid('load', {
		"exam_num":$("#exam_num").val(),"sug_flag":"Y","app_type":$("#app_type").val()
	});
	getFinalResult($("#exam_num").val(),'Y',$("#app_type").val());
	$('#disease_logic_composite_list').datagrid('load', {
		"exam_num":$("#exam_num").val(),"sug_flag":"Y","app_type":$("#app_type").val()
	});
}
//保存总检数据
function save_examsummary(exam_status){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	var examDisease = new Array();
	var dxexamDisease = new Array(); 
	var obj = $("#disease_logic_composite_list").datagrid('getRows');
	var rows = $("#disease_sug_list").datagrid('getRows');
	
	for(i=0;i<rows.length;i++){
		if(rows[i].disease_num != '' && rows[i].disease_num != null){
			dxexamDisease.push({
				disease_id:rows[i].disease_id,
				disease_name:rows[i].disease_name.replace(/^\s+|\s+$/g,''),
				disease_type:rows[i].disease_type,
				icd_10:rows[i].icd_10,
				suggest:rows[i].suggest.replace(/^\s+|\s+$/g,''),
				rows:rows[i].disease_class,
				disease_num:rows[i].disease_num,
				data_source:rows[i].data_source,
				charging_item_code:rows[i].charging_item_code,
				item_code:rows[i].item_code,
				dep_num:rows[i].dep_num
			});
		}
	}
	
	for(i=0;i<obj.length;i++){
		examDisease.push({
			disease_id:obj[i].disease_id,
			disease_name:obj[i].disease_name.replace(/^\s+|\s+$/g,''),
			disease_type:obj[i].disease_type,
			icd_10:obj[i].icd_10,
			suggest:obj[i].suggest.replace(/^\s+|\s+$/g,''),
			disease_class:obj[i].disease_class,
			disease_num:obj[i].disease_num,
			data_source:obj[i].data_source
		});
	}
	
	$.ajax({
		url:'saveExamSummary.action',
		type:'post',
		data:{
			app_type:$("#app_type").val(),
			exam_num:$("#exam_num").val(),
			id:$("#id").val(),
			final_exam_result:$("#zongjianjielun").val(),
			exam_guidance:$("#exam_guidance").val(),
			examinfoDiseases:JSON.stringify(examDisease),
			exam_status:exam_status,
			report_class:$("#report_class").combobox('getValue'),
			operation_type:$("#operation_type").val(),
			dxExamDisease:JSON.stringify(dxexamDisease)
		},
		success:function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				if(exam_status == 'N'){
					alert_autoClosep("操作提示",obj[1], "info",refsh_page);
				}else{
					alert_autoClosep("操作提示",obj[1], "info",close_page);
				}
			}else{
				alert_autoClosep("操作提示",obj[1], "error");
			}
		}
	});
}
//审核、复审保存
function save_examdisease(status){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);

	var examDisease = new Array();
	var dxexamDisease = new Array(); 
	var obj = $("#disease_logic_composite_list").datagrid('getRows');
	var rows = $("#disease_sug_list").datagrid('getRows');
	
	for(i=0;i<rows.length;i++){
		if(rows[i].disease_num != '' && rows[i].disease_num != null){
			dxexamDisease.push({
				disease_id:rows[i].disease_id,
				disease_name:rows[i].disease_name.replace(/^\s+|\s+$/g,''),
				disease_type:rows[i].disease_type,
				icd_10:rows[i].icd_10,
				suggest:rows[i].suggest.replace(/^\s+|\s+$/g,''),
				rows:rows[i].disease_class,
				disease_num:rows[i].disease_num,
				data_source:rows[i].data_source,
				charging_item_code:rows[i].charging_item_code,
				item_code:rows[i].item_code,
				dep_num:rows[i].dep_num
			});
		}
	}
	
	for(i=0;i<obj.length;i++){
		examDisease.push({
			disease_id:obj[i].disease_id,
			disease_name:obj[i].disease_name.replace(/^\s+|\s+$/g,''),
			disease_type:obj[i].disease_type,
			icd_10:obj[i].icd_10,
			suggest:obj[i].suggest.replace(/^\s+|\s+$/g,''),
			disease_class:obj[i].disease_class,
			disease_num:obj[i].disease_num,
			data_source:obj[i].data_source
//			final_remarke:obj[i].final_remarke.replace(/^\s+|\s+$/g,''),
//			approve_remarke:obj[i].approve_remarke.replace(/^\s+|\s+$/g,''),
//			censoring_remarke:obj[i].censoring_remarke.replace(/^\s+|\s+$/g,'')
		});
	}
	$.ajax({
		url:'saveExamInfoDiseaseList.action',
		type:'post',
		data:{
			app_type:$("#app_type").val(),
			exam_num:$("#exam_num").val(),
			id:$("#id").val(),
			examinfoDiseases:JSON.stringify(examDisease),
			exam_status:status,
			dxExamDisease:JSON.stringify(dxexamDisease)
		},
		success:function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				alert_autoClosep("操作提示",obj[1], "info",refsh_page);
			}else{
				alert_autoClosep("操作提示",obj[1], "error");
			}
		}
	});
}

//取消总检
function qv_examsummary(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'cancelExamSummary.action',
		type:'post',
		data:{
			app_type:$("#app_type").val(),
			exam_num:$("#exam_num").val(),
			id:$("#id").val()
		},
		success:function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				alert_autoClosep("操作提示",obj[1], "info",refsh_page);
			}else{
				alert_autoClosep("操作提示",obj[1], "error");
			}
		}
	});
}
function querenshengheorqv(){
	if($("#approve_status").val() == 'B' && $("#is_exam_result_canfinal").val() != '3'){
		$.messager.defaults = { ok: "报告室", cancel: "复审室" };
		$.messager.confirm('提示信息','选择报告发送室？',function(r){
			$.messager.defaults = { ok: "确定", cancel: "取消" };
			if(r){
				shengheorqv();
			}else{
				var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				$("body").prepend(str);
				$.ajax({
					url:'isCensoringExamSummary.action',
					type:'post',
					data:{exam_num:$("#exam_num").val()},
					success:function(data){
						$(".loading_div").remove();
						var obj = data.split("-");
						if(obj[0] == 'ok'){
							shengheorqv();
						}
					}
				});
			}
		});
	}else{
		shengheorqv();
	}
}
//审核或取消审核
function shengheorqv(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	var examDisease = new Array();
	var obj = $("#disease_logic_composite_list").datagrid('getRows');
	
	var dxexamDisease = new Array(); 
	var rows = $("#disease_sug_list").datagrid('getRows');
	
	for(i=0;i<rows.length;i++){
		if(rows[i].disease_num != '' && rows[i].disease_num != null){
			dxexamDisease.push({
				disease_id:rows[i].disease_id,
				disease_name:rows[i].disease_name.replace(/^\s+|\s+$/g,''),
				disease_type:rows[i].disease_type,
				icd_10:rows[i].icd_10,
				suggest:rows[i].suggest.replace(/^\s+|\s+$/g,''),
				rows:rows[i].disease_class,
				disease_num:rows[i].disease_num,
				data_source:rows[i].data_source,
				charging_item_code:rows[i].charging_item_code,
				item_code:rows[i].item_code,
				dep_num:rows[i].dep_num
			});
		}
	}
	
	
	for(i=0;i<obj.length;i++){
		examDisease.push({
			disease_id:obj[i].disease_id,
			disease_name:obj[i].disease_name.replace(/^\s+|\s+$/g,''),
			disease_type:obj[i].disease_type,
			icd_10:obj[i].icd_10,
			suggest:obj[i].suggest.replace(/^\s+|\s+$/g,''),
			disease_class:obj[i].disease_class,
			disease_num:obj[i].disease_num,
			data_source:obj[i].data_source
//			final_remarke:obj[i].final_remarke.replace(/^\s+|\s+$/g,''),
//			approve_remarke:obj[i].approve_remarke.replace(/^\s+|\s+$/g,''),
//			censoring_remarke:obj[i].censoring_remarke.replace(/^\s+|\s+$/g,'')
		});
	}
	
	$.ajax({
		url:'approveExamSummary.action',
		type:'post',
		data:{
			app_type:$("#app_type").val(),
			exam_num:$("#exam_num").val(),
			id:$("#id").val(),
			approve_status:$("#approve_status").val(),
			final_exam_result:$("#zongjianjielun").val(),
			exam_guidance:$("#exam_guidance").val(),
			examinfoDiseases:JSON.stringify(examDisease),
			dxExamDisease:JSON.stringify(dxexamDisease)
		},
		success:function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				if($("#approve_status").val() == 'A'){
					alert_autoClosep("操作提示",obj[1], "info",refsh_page);
				}else{
					alert_autoClosep("操作提示",obj[1], "info",close_page);
				}
			}else{
				alert_autoClosep("操作提示",obj[1], "error");
			}
		}
	});
}
//终审或取消终审
function zhongshengqv(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	
	var examDisease = new Array();
	var obj = $("#disease_logic_composite_list").datagrid('getRows');
	
	var dxexamDisease = new Array(); 
	var rows = $("#disease_sug_list").datagrid('getRows');
	
	for(i=0;i<rows.length;i++){
		if(rows[i].disease_num != '' && rows[i].disease_num != null){
			dxexamDisease.push({
				disease_id:rows[i].disease_id,
				disease_name:rows[i].disease_name.replace(/^\s+|\s+$/g,''),
				disease_type:rows[i].disease_type,
				icd_10:rows[i].icd_10,
				suggest:rows[i].suggest.replace(/^\s+|\s+$/g,''),
				rows:rows[i].disease_class,
				disease_num:rows[i].disease_num,
				data_source:rows[i].data_source,
				charging_item_code:rows[i].charging_item_code,
				item_code:rows[i].item_code,
				dep_num:rows[i].dep_num
			});
		}
	}
	
	
	
	
	for(i=0;i<obj.length;i++){
		examDisease.push({
			disease_id:obj[i].disease_id,
			disease_name:obj[i].disease_name.replace(/^\s+|\s+$/g,''),
			disease_type:obj[i].disease_type,
			icd_10:obj[i].icd_10,
			suggest:obj[i].suggest.replace(/^\s+|\s+$/g,''),
			disease_class:obj[i].disease_class,
			disease_num:obj[i].disease_num,
			data_source:obj[i].data_source
//			final_remarke:obj[i].final_remarke.replace(/^\s+|\s+$/g,''),
//			approve_remarke:obj[i].approve_remarke.replace(/^\s+|\s+$/g,''),
//			censoring_remarke:obj[i].censoring_remarke.replace(/^\s+|\s+$/g,'')
		});
	}
	
	$.ajax({
		url:'censoringExamSummary.action',
		type:'post',
		data:{
			app_type:$("#app_type").val(),
			exam_num:$("#exam_num").val(),
			id:$("#id").val(),
			censoring_status:$("#censoring_status").val(),
			final_exam_result:$("#zongjianjielun").val(),
			exam_guidance:$("#exam_guidance").val(),
			examinfoDiseases:JSON.stringify(examDisease),
			dxExamDisease:JSON.stringify(dxexamDisease)
		},
		success:function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				if($("#censoring_status").val() == '1'){
					alert_autoClosep("操作提示",obj[1], "info",refsh_page);
				}else{
					alert_autoClosep("操作提示",obj[1], "info",close_page);
				}
			}else{
				alert_autoClosep("操作提示",obj[1], "error");
			}
		}
	});
}
//阳性排序
function diseas_paixv(){
	if($("#disease_sug_list").datagrid('getRows').length == 0){
		$.messager.alert("操作提示",'未检出阳性发现,不能排序!', "error");
		return;
	}
	diseasPaixvGrid();
	getDiseasepx();
	$("#dlg-px").dialog("open");
	$("#dlg-px").dialog('center');
	
}
function diseasPaixvGrid(){
	$("#disease_paixv").datagrid({
		dataType: 'json',
		data:$("#disease_sug_list").datagrid('getRows'),
		rownumbers:true,
		columns:[[
			 {align:'',field:'disease_name',title:'阳性/疾病发现',width:70}
		]],	    	
		onLoadSuccess:function(value){
		    $("#datatotal").val(value.total);
		    $(this).datagrid('enableDnd');
		},
		singleSelect:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		nowrap:false
	});
}
function getDiseasepx(){
	var rows = $("#disease_sug_list").datagrid('getRows');
	$("#disease_sug_list").datagrid('loadData',{ total: 0, rows: [] });
	for(i=0;i<rows.length;i++){
		$("#disease_sug_list").datagrid('appendRow',rows[i]);
	}
	sx_disease();
}

function save_diseasPaixv(){
	$("#disease_sug_list").datagrid('loadData',$("#disease_paixv").datagrid('getRows'));
	$("#disease_sug_list").datagrid('enableDnd');
	$("#dlg-px").dialog("close");
}

var check_disease_obj = new Array();
//阳性合并
function diseas_hebin(){
	var obj = $("#disease_sug_list").datagrid('getChecked');
	if(obj.length <= 1){
		$.messager.alert("操作提示",'请选择两种以上的阳性发现!', "error");
		return;
	}
	if($("#isDiseaseMerge").val() == 'N'){
		var rowdata = check_disease_obj[0];
		rowdata.data_source = 2;
		for(i=1;i<check_disease_obj.length;i++){
			rowdata.disease_name += '\n'+check_disease_obj[i].disease_name;
			rowdata.suggest += '\n'+check_disease_obj[i].suggest;
		}
		for(j=0;j<check_disease_obj.length;j++){
			obj = $("#disease_sug_list").datagrid('getRows');
			for(i=0;i<obj.length;i++){
				if(obj[i].disease_index == check_disease_obj[j].disease_index){
					if(j == 0){
						$("#disease_sug_list").datagrid('updateRow',{index:i,row:rowdata});
					}else{
						$("#disease_sug_list").datagrid('deleteRow',i);
					}
				}
			}
		}
		$("#disease_sug_list").datagrid('uncheckAll');
		check_disease_obj.length = 0 ;
		sx_disease();
	}else{
		$("#disease_hebin_name").combobox('clear');
		$("#disease_hebin_sug").datagrid('loadData',{ total: 0, rows: [] });
		$("#dlg-hb").dialog("open");
	}
}

function gethebinSugGrid(id){
	 var model={disease_id:id,age:$("#age").html(),sex:$("#sex").html()};
	$("#disease_hebin_sug").datagrid({
			 url:'serchDiseaseSuggestionList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
			 columns:[[
			 	{align:'',field:'suggest',title:'阳性/疾病建议',width:70}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    	},
		    	singleSelect:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false,
			    onDblClickRow:function(rowIndex, rowData){
			    	save_suggestionhb(rowData)
			    }
	});
}
function save_suggestionhb(row){
	if(row == undefined){
		row = $("#disease_hebin_sug").datagrid('getSelected');
	}
	if(row == null){
		$.messager.alert("操作提示",'请选择阳性发现及建议!', "error");
		return;
	}
	var obj = $("#disease_sug_list").datagrid('getRows');
	var obj1 = $("#disease_sug_list").datagrid('getChecked');
	for(i=0;i<obj.length;i++){
		for(j=0;j<obj1.length;j++){
			if(obj[i].disease_index == obj1[j].disease_index){
				$("#disease_sug_list").datagrid('deleteRow',i);
			}
		}
	}
	row.data_source = 2;
	appendDisease(row);
	$("#dlg-hb").dialog("close");
}
//体检综述选择页面
function show_tjzs(){
	$("#ls_exam_guidance").val($("#exam_guidance").val());
	
	var model = {"guidance_word":$('#ser_exam_guidance').val()};
	$("#exam_guidance_list").datagrid({
		url: 'getExamSummaryGuidanceLibList.action',
		queryParams: model,
		rownumbers:true,
	  	pageSize: 15,//每页显示的记录条数，默认为10 
	  	pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	  	columns:[[{align:"center",field:"guidance_word","title":"体检综述名称","width":15},
  	          {align:"center",field:"tj","title":"添加","width":10,"formatter":f_tjzs}
  		 ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: true,
	  	fitColumns:true,
	  	striped:true,
	  	fit:true,
	  	toolbar:toolbar1,
	  	onDblClickRow:function(index,row){
	  		$("#ls_exam_guidance").val($("#ls_exam_guidance").val()+row.guidance_content+'\n');
	  	}
	});
	
	$("#dlg-tjzs").dialog("open");
}

//报告预览意见
function exam_suggestion(){
	$("#dlg-report").dialog({
		title:'意见建议',
		href:'getExamSuggestionEditPage.action?exam_num='+$("#exam_num").val()
	});
	$('#dlg-report').dialog('open');
}

//报告驳回
function reportReject(){
/*	if($("#exam_id").val()==null ||$("#exam_id").val()==""){
		$.messager.alert("提示","请先输入体检编号将报告信息查询出来!", "error");
		return;
	}*/
	$("#dlg-reject").dialog({
		title:'报告驳回',
		href:'getReportRejectEditPage.action?exam_id='+$("#exam_id").val()
	});
	$('#dlg-reject').dialog('open');
}

function f_tjzs(val,row,index){
	return '<a href="javascript:void(0)" onclick = "f_tjzs_s('+index+')">添加</a>';
}
function f_tjzs_s(i){
	var obj = $("#exam_guidance_list").datagrid('getRows');
	$("#ls_exam_guidance").val($("#ls_exam_guidance").val()+obj[i].guidance_content+'\n');
}
function save_tjzs(){
	$("#exam_guidance").val($("#ls_exam_guidance").val());
	$("#dlg-tjzs").dialog("close");
}
/***************健康证****************/
function jkz_page_show(){
	gethealthnolist();
	$("#dlg-jkz").dialog("open");
	$("#dlg-jkz").dialog("center");
}

function save_jkz_yx(){
	
	var health_no = '';
	if($("#health_no").combobox('getValue') == '0'){
		var row = $("#health_list").datagrid('getSelected');
		if(row == null){
			$.messager.alert("操作提示",'请选择已存在健康证号!', "error");
			return;
		}
		health_no = row.health_no;
	}
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveExamSummaryJianKangZheng.action',
		type:'post',
		data:{
			exam_num:$("#exam_num").val(),
			sffz:$("#jkz_value").combobox('getValue'),
			health_no:health_no
		},
		success:function(data){
			$(".loading_div").remove();
			$("#dlg-jkz").dialog("close");
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				$.messager.alert("操作提示",obj[1], "info");
			}else{
				$.messager.alert("操作提示",obj[1], "error");
			}
		}
	});
}

//获取已使用健康证号列表
function gethealthnolist(){
	var model={"arch_num":$("#arch_num").html()};
	$("#health_list").datagrid({
			 url:'getOldHealthNoOneYear.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:false,
			 width:590,
			 height:200,
			 columns:[[
			       {field:'checkid',checkbox:true},
			       {align:'center',field:'health_no',title:'健康证号',width:10},
			       {align:'center',field:'exam_num',title:tjhname,width:10},
			       {align:'center',field:'rq',title:'体检日期',width:10},
		           {align:'center',field:"sffz","title":"是否允许发证","width":10,'formatter':f_sffz},
		           {align:'center',field:"fzrq","title":"发证日期","width":15}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		changefontsize();
		    	},
		    	singleSelect:true,
			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    striped:true,
			    nowrap:false
	});
}
function f_sffz(val,row,index){
	if(row.sffz == '1'){
		return '允许';
	}else{
		return '不允许';
	}
}

function show_pace_url(){
	var url = $("#examSummaryPacsUrl").val()+$("#arch_num").html();
	newwin = window.open(url, "科室检查", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newwin.focus();
}
//FF中需要修改配置window.close方法才能有作用，为了不需要用户去手动修改，所以用一个空白页面显示并且让后退按钮失效
//Opera浏览器旧版本(小于等于12.16版本)内核是Presto，window.close方法有作用，但页面不是关闭只是跳转到空白页面，后退按钮有效，也需要特殊处理
function close_page(){
	var _parentWin =  window.opener ;
	var userAgent = navigator.userAgent;
	  window.opener = null;
	  window.open('', '_self');
	  window.close();
	  _parentWin.shuxing();
}

function refsh_page(){
//	window.location.reload();
	var exam_num = $("#exam_num").val();
	$('#disease_sug_list').datagrid('load', {
		"exam_num":$("#exam_num").val(),"sug_flag":"N","app_type":$("#app_type").val()
	});
	$('#disease_logic_composite_list').datagrid('load', {
		"exam_num":$("#exam_num").val(),"sug_flag":"N","app_type":$("#app_type").val()
	});
	getFinalResult(exam_num,$("#isExamSummaryNew").val(),$("#app_type").val());
}

function printreport(){
	if($("#barcode_print_type").val() == '1' || $("#barcode_print_type").val() == '2'){//调用旧预览程序
		var barids = $("#exam_num").val();
		var exeurl="reportServices://&0&"+barids+"&0";
		location.href=exeurl;
	}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
		$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
	}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
		var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+$("#exam_num").val();
		location.href=exeurl;
	}else if($("#barcode_print_type").val() == '5'){//
		var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+$("#exam_num").val();
		location.href=exeurl;
	}else{
		$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
	}
}
function addCrmVisitPlanPage(){
	$("#dlg-edit").dialog({
		title:'编辑健康回访',
		width : 800,
		height :450,
		href : 'getCrmVisitPlanDoctorPage.action?flag=3'
	});
	$("#dlg-edit").dialog("open");
	$("#dlg-edit").dialog('center');
}
function addCrmPlanTacticsPage(){
	var persionName = $("#user_name").html();
	var arch_num = $("#arch_num").html();
	var exam_num = $("#exam_num_x").html();
	
	$("#dlg-edit").dialog({
		title:'编辑回访策略',
		width : 800,
		height :630,
		href : 'getCrmVisitPlanDoctorPage.action?flag=4&arch_num='+arch_num+'&exam_num='+exam_num+'&persionName='+persionName
	});
	$("#dlg-edit").dialog("open");
	$("#dlg-edit").dialog('center');
}
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
	if (this[i] == val) return i;
	}
	return -1;
};

Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
	this.splice(index, 1);
	}
};

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
      

      function drawGraph(examInfoId, itemId) {
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('ls_graph'));
		
		$.ajax({
			url : 'getHyItemResultsHistory.action?exam_info_id=' + examInfoId
					+ '&item_id=' + itemId,
			type : 'post',
			success : function(data) {
				var dataList = eval("("+data+")");;
				
				if (dataList == 'null' || data.length <= 0) {
					return;
				}
				
				var s = dataList[0].ref_value;
				var refMax = null;
				var refMin = null;
				var separator = "--";

				var pos = s.indexOf(separator, 0);
				if(pos > 0){
					refMin = s.substring(0, pos);
					refMax = s.substr(pos + separator.length, s.length);
				}
				
				var xExamDate = new Array();
				var examResult = new Array();
				
				for(var i=dataList.length-1; i >= 0; i--){
					xExamDate.push(dataList[i].exam_date);
					examResult.push(dataList[i].exam_result);
				}
				
				option = {
					title : {
						text : '历史结果曲线图',
						subtext : ''
					},
					tooltip : {
						trigger : 'axis'
					},
				    grid : {
				        left: 40,
				        right: 40
				    },
					xAxis : {
						type : 'category',
						boundaryGap : false,
						data : xExamDate
					},
					yAxis : {
						type : 'value',
						axisLabel : {
							formatter : '{value} '
						}
					},
					series : [ {
						name : '检查结果',
						type : 'line',
						data : examResult 
//						markLine : {
//							symbol : 'circle',
//							data : [ {
//								yAxis : 12,
//								name : '参考值上限'
//							}, {
//								yAxis : 0,
//								name : '参考值下限'
//							} ],
//						}
					} ]
				};
	
				if(refMax != null && refMin != null) {
					var markLine =  {	symbol : 'circle',
						data : [ {
							yAxis : Number(refMin),
							name : '参考值下限'
						}, {
							yAxis : Number(refMax),
							name : '参考值上限'
						} ],
					};

					option.series[0].markLine = markLine;
				}
				
				// 使用刚指定的配置项和数据显示图表。
				myChart.setOption(option);
		
				
			}
		});

      }
      
function create_num(){
//	var reg = /\uff08[\u4e00|\u4e8c|\u4e09|\u56db|\u4e94|\u516d|\u4e03|\u516b|\u4e5d|\u5341]{1,2}\uff09/;
	var reg = /[\n]\d{1,2}\./;
	var reg1 = /\(\d{1,2}\)/;
	var str = $("#zongjianjielun").val();
	
	//去除空白行
	var h_arr = str.split('\n');
	var x_str = '';
	for(i=0;i<h_arr.length;i++){
		if(h_arr[i].replace(/ /g,'').replace(/\r/g,'') != ''){
			x_str += h_arr[i] + '\n';
		}
	}
	var arr = x_str.split(reg);
	var rstr = ''
	for(j=0;j<arr.length;j++){
		if(j==0){
			var c_arr = arr[j].replace(/\d{1,2}\./,'1.').split(reg1);
			var c_str = c_arr[0];
			for(k=1;k<c_arr.length;k++){
				c_str += '('+k+')'+c_arr[k];
			}
			rstr += c_str+'\n';
		}else{
			var c_arr = arr[j].split(reg1);
			var c_str = c_arr[0];
			for(k=1;k<c_arr.length;k++){
				c_str += '('+k+')'+c_arr[k];
			}
			rstr += (j+1)+'.' + c_str+'\n';
		}
	}
	$("#zongjianjielun").val(rstr);
}
function shoubaogaopdfbuttion(){
	$.ajax({
		url:'getDepShwoPinCeBaoGaoButtion.action?exam_num='+$('#exam_num_x').text(),
		success:function(data){
			if(data!=""){
			        $("#pdfbuttion").show()
			}
		}
	})
}
function DivClose(id){
	$("#"+id).css('display', 'none');
}
//总检驳回
function zongjianbohui(){
	$("#dlg-bohui").dialog({
		title:'总检驳回',
		href:'getExamSummaryRehectPage.action'
	});
	$("#dlg-bohui").dialog("open");
}
var changefont;
function changefontcss(changefont){
	if(changefont==1){
		fontsize=parseInt(fontsize)+1;
	}else{
		fontsize=parseInt(fontsize)-1;
	}
	 if (fontsize>25){
	    	fontsize=25;
	 }else if (fontsize<14){
	    	fontsize=14;
	    }
	setCookie('fontsize',fontsize);
	changefontsize();
}

function changefontsize(){
	$("textarea").css('font-size',fontsize+'px');	
	$('.datagrid-cell').css('font-size',fontsize+'px');		
}
//设定复查
function shedingfucha(){
	$("#dlg-edit").dialog({
		title:'复查设定',
		height:580,
		width:1200,
		href:'getExamSummaryReviewAddPage.action?exam_num='+$("#exam_num").val()
	});
	$("#dlg-edit").dialog("open");
	$("#dlg-edit").dialog("center");
}

function checkCharingitem() {
	var model = {'examinfo_id':$("#exam_id").val()};
	$("#exam_item_list").datagrid({
		url : 'getDirectorItemStatus.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : true,
		columns : [ [ {align : '',field : 'dep_name',title : '科室',width : 15},
		              {align : '',field : 'item_name',title : '收费项目',width : 15},
		              {align : '',field : 'exam_statuss',title : '检查状态',width : 10},
		              {align : '',field : 'exam_doctor_name',title : '检查医生',width : 10},
		              {align : '',field : 'exam_date',title : '检查时间',width : 25}
		              ] ],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
		},
		singleSelect : true,
		pagination : false,
		fitColumns : true,
		fit : true,
		striped : true,
		nowrap : false
	});
	$("#dlg-item").dialog("open");
}

function fn_sdbgdj() {
	$.ajax({
		url:'getExamSummaryReportClass.action?exam_num='+ $("#exam_num").val()+'&app_type='+$("#app_type").val(),
		type:'post',
		success:function(data){
			var obj=eval("("+data+")");
			$("#report_class").combobox('setValue',obj.report_class);
			$("#report_summary_id").val(obj.id);
			$("#report_class_user").textbox('setValue',obj.report_class_user);
			$("#report_class_date").textbox('setValue',obj.report_class_date);
			$("#dlg-bgdj").dialog("open");
		}
	});
}

function save_bgdj(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveExamSummaryReportClass.action',
		data:{
			'report_class':$("#report_class").combobox('getValue'),
			'id':$("#report_summary_id").val(),
			'operation_type':$("#operation_type").val(),
			'exam_num':$("#exam_num").val(),
			'app_type':$("#app_type").val()
		},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			$.messager.alert("警告信息","设置报告等级成功","info");
			$("#report_dengji").html($("#report_class").combobox('getValue') + '级');
			$("#dlg-bgdj").dialog("close");
//			if(data.split("-")[0] == 'ok'){
//				$.messager.alert("警告信息",data,"info");
//				$("#report_dengji").html($("#report_class").combobox('getValue') + '级');
//				$("#dlg-bgdj").dialog("close");
//			}else{
//				$.messager.alert("警告信息",data,"error");
//			}
		}
	});
}

function shedingteshu(type){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveExamInfoSpecialNotice.action',
		data:{
			'exam_num':$("#exam_num").val(),
			'operation_type':type
		},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			if(data.split("-")[0] == 'ok'){
				$.messager.alert("警告信息",data.split("-")[1],"info");
				if(type == 1){
					$("#sd_teshu").hide();
					$("#qv_teshu").show();
				}else{
					$("#sd_teshu").show();
					$("#qv_teshu").hide();
				}
			}else{
				$.messager.alert("警告信息",data.split("-")[1],"error");
			}
		}
	});
}
//一键取消恢复
function yijiancancel(cancel_type){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveExamSummaryCancel.action',
		data:{
			'id':$("#id").val(),
			'exam_num':$("#exam_num").val(),
			'cancel_type':cancel_type
		},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			if(data.split("-")[0] == 'ok'){
				alert_autoClosep("操作提示",data.split("-")[1], "info",refsh_page);
			}else{
				$.messager.alert("警告信息",data.split("-")[1],"error");
			}
		}
	});
}

//弹出框自动关闭
function alert_autoClosep(title, msg, icon,fn) {
	var interval;
	var time = 1000;
	var x = 1; //设置时间2s
	$.messager.alert(title, msg, icon);
	interval = setInterval(fun, time);
	function fun() {
		--x;
		if (x == 0) {
			clearInterval(interval);
			$(".messager-body").window('close');
			if(fn != null || fn != undefined){
				fn();
			}
		}
	}
}

function getfcsd(exam_num){
	$.ajax({
		url:'getExamSummaryReview.action?exam_num='+exam_num,
		type:'post',
		success:function(data){
			if(data == 'null'){
				$("#fcsd").html('未设定复查');
				return;
			}
			var obj=eval("("+data+")");
			$("#qxsd_fucha").hide();
			$("#hfsd_fucha").hide();
			$("#fcsd").html('已设定复查');
			$("#fcsd").css({"color":"red"});
			$("#review_id").val(obj.id);
			if($("#operation_type").val() == 2 || $("#operation_type").val() == 3){
				if(obj.review_status == '1'){
					$("#qxsd_fucha").show();
				}else if(obj.review_status == '4'){
					$("#hfsd_fucha").show();
				}
			}
		}
	});
}

function qh_shedingfucha(review_status){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveCancelReview.action',
		type:'post',
		data:{
			'review_status':review_status,
			'id':$("#review_id").val()
		},
		success:function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				alert_autoClosep("操作提示",obj[1], "info",refsh_page);
			}else{
				alert_autoClosep("操作提示",obj[1], "error");
			}
		}
	});
}

function  f_add(val,row){
	if(row.item_name == '图片' || row.item_name == '科室结论' || row.item_name == '检查描述' || row.exam_result == ''){
		return "";
	}else{
		if(row.critical_id > 0){
			return '<a href="javascript:void(0)" onclick = "delCritical('+row.item_id+')">撤销</a>' ;
		}else{
			return '<a href="javascript:void(0)" onclick = "addCritical('+row.item_id+')">添加</a>';
		}
		
	}
	
}
function addCritical(item_id){
     $.messager.confirm('提示信息','确定添加此危机吗 ？',function(r){
     	if(r){
     		 $.ajax({
			url : 'addCritical.action',
			data : {
				    examinfo_id : $("#exam_id").val(),
				    dep_category:21,
				    item_id:item_id
					},
					type : "post",//数据发送方式   
					success : function(text) {
						if (text.split("-")[0] == 'ok') {
							$.messager.alert("操作提示", text);
							getyxGrid($("#exam_num").val());
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

function delCritical(item_id){
	$.messager.confirm('提示信息','确定撤销此危机吗 ？',function(r){
     	if(r){
     		 $.ajax({
			url : 'delCritical.action',
			data : {
				    examinfo_id : $("#exam_id").val(),
				    dep_category:21,
				    item_id:item_id
					},
					type : "post",//数据发送方式   
					success : function(text) {
						if (text.split("-")[0] == 'ok') {
							$.messager.alert("操作提示", text);
						getyxGrid($("#exam_num").val());
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
function addCrmVisitPlanPage(){
	$("#dlg-edit").dialog({
		title:'编辑健康回访',
		width : 800,
		height :450,
		href : 'getCrmVisitPlanDoctorPage.action?flag=3'
	});
	$("#dlg-edit").dialog("open");
}

//体检报告图片
function gettjbg(exam_num,app_type){
	var model = {exam_num:exam_num,app_type:app_type}
	$.ajax({
		url:'gettjbgList.action',
		type:'post',
		data:model,
		success:function(data){
			var obj = eval('('+data+')');
			if(obj.DJD_path!='NULL'){
				document.getElementById("disease_tijianbaogao").innerHTML="<img src='picture/"+obj.DJD_path+"' style='width:800px;height:1400px' />";
			}
		}
	})
}
function disease_logic_composite_list(exam_num,type,app_type){
	var lastIndex;
	var model={"exam_num":exam_num,"sug_flag":type,"app_type":app_type};
	$("#disease_logic_composite_list").datagrid({
			 url:'getExamCompositeDiseaseList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
			 columns:[[
				 {align:'',field:'disease_name',title:'阳性/疾病发现',width:40,editor:{type:'textarea',options:{}},"formatter":f_jygsh},
				 {align:'',field:'suggest',title:'阳性/疾病建议',width:70,editor:{type:'textarea',options:{}},"formatter":f_jygsh},
				 {align:'center',field:'caozuo',title:'操作',width:10,"formatter":f_scfh_disease}
			 ]],
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		$(this).datagrid('enableDnd');
		    		var row = $('#disease_logic_composite_list').datagrid('getRows');
		    		for (i = 0; i < row.length; i++) { 
                        $('#mb' +row[i].disease_index ).menubutton();
                    }
					changefontsize();
		    	},
		    	singleSelect:false,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false,
			    checkOnSelect:false,
			    onClickCell:function(index, field, row){
//			    	sxfh_disease();
			    	$('#disease_logic_composite_list').datagrid('beginEdit', index);
			    	$("#datagrid-row-r6-2-"+index).unbind();
					var edit = $('#disease_logic_composite_list').datagrid('getEditor',{index:index,field:'disease_name'});
					$(edit.target).change(function(){
						$('#disease_logic_composite_list').datagrid('endEdit', index);
					});
					var edit1 = $('#disease_logic_composite_list').datagrid('getEditor',{index:index,field:'suggest'});
					$(edit1.target).change(function(){
						$('#disease_logic_composite_list').datagrid('endEdit', index);
					});

					if(field == 'suggest'){
						$(edit1.target).focus();
					}else if(field == 'disease_name'){
						$(edit.target).focus();
					}

					changefontsize();
			    },
			    onAfterEdit:function(index, row, changes){
			    	$(disease_logic_composite_list).datagrid('enableDnd',index);
			    	changefontsize();
			    },
			    onSelectAll:function (rows){
			    	$('#disease_logic_composite_list').datagrid('clearSelections');
			    	changefontsize();
			    },
			    onSelect:function (rowIndex, rowData){
			    	$('#disease_logic_composite_list').datagrid('clearSelections');
			    	changefontsize();
			    },
			    onCheck:function (rowIndex, rowData){
			    	check_disease_obj.push(rowData);
			    	changefontsize();
			    },
			    onUncheck:function (rowIndex, rowData){
			    	check_disease_obj.remove(rowData);
			    	changefontsize();
			    },
			    onCheckAll:function (rows){
			    	var obj = $("#disease_logic_composite_list").datagrid('getRows');
			    	for(i=0;i<obj.length;i++){
			    		check_disease_obj.push(obj[i]);
			    	}
			    	changefontsize();
			    },
			    onUncheckAll:function (row){
			    	check_disease_obj.length = 0;
			    	changefontsize();
			    }
	});
	
}

//删除疾病信息
function f_scfh_disease(val,row,index){
	return '<a href="javascript:void(0)" onclick = "scfh_disease('+row.disease_index+')">删除</a>';
}

function scfh_disease(index){
	var obj = $("#disease_logic_composite_list").datagrid('getRows');
	for(i=0;i<obj.length;i++){
		if(obj[i].disease_index == index){
			$("#disease_logic_composite_list").datagrid('deleteRow',i);
		}
	}
	sxfh_disease();
}
//刷新疾病表格数据
function sxfh_disease(){
	var obj = $("#disease_logic_composite_list").datagrid('getRows');
	for(i=0;i<obj.length;i++){
		obj[i].disease_index = i;
		$('#disease_logic_composite_list').datagrid('endEdit', i);
		$("#disease_logic_composite_list").datagrid('refreshRow',i);
	}
	
	for (i = 0; i < $('#disease_logic_composite_list').datagrid('getRows').length; i++) {        
        $('#mb' + i).menubutton();
    }
}
function getSugGrid(id,index){
	 var model={disease_id:id,age:$("#age").html(),sex:$("#sex").html()};
	$("#sug_list").datagrid({
			 url:'serchDiseaseSuggestionList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
			 columns:[[
			 	{align:'',field:'suggest',title:'阳性/疾病建议',width:70}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    	},
		    	singleSelect:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false,
			    onDblClickRow:function(rowIndex, rowData){
			    	rowData.data_source = 1;
			    	addDisease(rowData,index);
			    	$('#dlg-show_dis').dialog('close');
			    }
	});
}
