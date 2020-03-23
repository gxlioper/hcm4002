var zj_jbfl_data='',hy_gdt = false;
var fontsize=14;
$(document).ready(function () {
	$.ajax({
		url:'getDatadis.action?com_Type=ZJJBFL',
		type:'post',
		async: false,
		success:function(data){
			zj_jbfl_data = eval(data);
			
//			var str = '<div id="mm3"class="easyui-menu"style="width:60px;">';
//			for(i=0;i<zj_jbfl_data.length;i++){
//				str += '<div onclick="menuModify('+i+')">'+zj_jbfl_data[i].name+'</div>'
//			}
//			str +='</div>';
//			$("#mm").html(str);
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
	}
	var exam_num = $("#exam_num").val();
	var app_type = $("#app_type").val();
	getExamDisease(exam_num,'N',app_type);
	getFinalResult(exam_num,$("#isExamSummaryNew").val(),app_type);

	getptGrid(exam_num,app_type);
	gethyGrid(exam_num,app_type);
	getyxGrid(exam_num,app_type);
	getwzGrid(exam_num);
	gethebinSugGrid(0);
	getoccuhazardfactorsGrid();
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
        	$("#serch_disease").combobox('setValue','');
        	var obj = $("#disease_sug_list").datagrid('getRows');
        	for(i=0;i<obj.length;i++){
        		if(record.id == obj[i].disease_id){
        			$.messager.alert("操作提示","阳性发现："+record.disease_name+"已添加!", "error");
        			return;
        		}
        	}
        	load_suggestion(record.id);
        },
        onLoadSuccess:function(){
        	$('#serch_disease').textbox('textbox').css('padding-top','4px');
        	$('#serch_disease').textbox('textbox').css('padding-bottom','0px');
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
        },
        onLoadSuccess:function(){
        	$('#disease_hebin_name').textbox('textbox').css('padding-top','4px');
        	$('#disease_hebin_name').textbox('textbox').css('padding-bottom','0px');
        }
	});
	jQuery.extend(jQuery.fn.datagrid.defaults.editors, {  
	    textarea: {
	    	init : function(container, options) {
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
function load_suggestion(id){
	$.ajax({
		url:'serchDiseaseSuggestionList.action',
		type:'post',
		data:{disease_id:id,age:$("#age").html(),sex:$("#sex").html()},
		success:function(data){
			var obj=eval("("+data+")");
			if(obj.length == 1){
				obj[0].data_source = 1;
				appendDisease(obj[0]);
			}else{
				$("#dlg-edit").dialog({
		    		title:'健康建议列表',
		    		href:'showExamDiseaseSugList.action?disease_id='+id
		    	});
		    	$("#dlg-edit").dialog("open");
		    	$("#dlg-edit").dialog('center');
			}
		}
	});
}

function appendDisease(row){
	var dis = $("#disease_sug_list").datagrid('getRows');
	row.disease_index = dis.length;
	row.exam_num = $("#exam_num").val();
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
			width : 630,
			height :300,
			href:'showNewZybExamDisease.action'
		});
		$("#dlg-edit").dialog("open");
	}else{
		var dis = $("#disease_sug_list").datagrid('getRows');
		var row = new Object();
		row.id = 0;
		row.exam_num = $("#exam_id").val();
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
		row.exam_result = '';
		row.career_hazards = '';
		row.career_suggest = '';
		row.resultID = '';
		row.occudiseaseIDorcontraindicationID = '';
		
		$("#disease_sug_list").datagrid('insertRow',{'index':0,'row':row});
		sx_disease();
		$("#disease_sug_list").datagrid('enableDnd');
	}
}
var status = '';
//去除空行 
function replaceBlank(data){
	if(data.indexOf("null") != -1){
		data = data.replace(/null/g, '');
	}
    var reg = /\n(\n)*( )*(\n)*\n/g;
    return data.replace(reg,"\n");
}

function getFinalResult(exam_num,type,app_type){
	$.ajax({
		url:'getFinalExamResult.action?exam_num='+exam_num+'&sug_flag='+type+'&app_type='+app_type,
		type:'post',
		success:function(data){
			var obj=eval("("+data+")");
			
			$("#zongjianjielun").val(replaceBlank(obj.final_exam_result));
			$("#id").val(obj.id);
			$("#approve_status").val(obj.approve_status);
			$("#exam_guidance").val(obj.exam_guidance);
			
			$("#zongjian").hide();
			$("#qv_zongjian").hide();
			$("#baocun").hide();
			$("#qv_shenghe").hide();
			$("#shenghe").hide();
			
			status = obj.zyb_final_status;
			if(obj.zyb_final_status != 'Z'){
				$("#zongjian").show();
				$("#baocun").show();
			}else{
				if(obj.approve_status == 'A'){
					if($('#webResource').val() == '1'){
						$("#qv_shenghe").show();
					}
				}else{
					$("#qv_zongjian").show();
					if($('#webResource').val() == '1'){
						$("#shenghe").show();
					}
				}
			}
		}
	});
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
			var model={"exam_num":odl_result_list[i].exam_num};
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
                   {align:'center',field:'dep_name',title:'科室名称',width:10},
		           {align:'center',field:'citem_name',title:'收费项目',width:20},
		           {align:'center',field:"exam_doctor","title":"检查医生","width":10},
		           {align:'center',field:"exam_date","title":"检查时间","width":15},
		           {align:'center',field:'item_name',title:'检查项目',width:15,"styler":f_color_pt},
		           {align:'center',field:'exam_result',title:'检查结果',width:25,"styler":f_color_pt,"formatter":f_result_pt}
		 ]],	    	
		 onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		MergeCells('pt_itemResultList', 'dep_name,citem_name,exam_doctor,exam_date');
	    		changefontsize();
		 },
		 onDblClickCell:function(rowIndex, field, value){
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
				 queryParams:{'exam_num':$("#exam_num").val(),'item_id': rows[rowIndex].item_id},
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
function f_color_pt(value,row,index){
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
				{align:'center',field:'dep_name',title:'收费项目',width:100},
			    {align:'center',field:'item_name',title:'检查项目',width:150,"styler":f_color_hy},
			 	{align:'center',field:'exam_result',title:'检查结果',width:80,"styler":f_color_hy},
			 	{align:'center',field:'bs',title:'标识',width:60,"formatter":f_bs,"styler":f_color_hy},
			 	{align:'center',field:'ref_value',title:'参考值',width:100},
			 	{align:'center',field:'item_unit',title:'单位',width:60},
			 	{align:'center',field:"exam_doctor","title":"检查医生","width":100},
			 	{align:'center',field:"exam_date","title":"检查时间","width":100}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		MergeCells('hy_itemResultList', 'dep_name,exam_doctor,exam_date');
		    		changefontsize();
		    	},
		    	onDblClickCell:function(rowIndex, field, value){
					 var rows = $(this).datagrid('getRows');
					 var tr = $(this).datagrid('getPanel').find('div.datagrid-body tr');
					 var td = $(tr[rowIndex]).children('td[field="'+field+'"]');
					 
					 $("#results_contrast").css("top",'40%');
					 $('#results_contrast').css('display', 'block');
					 
					 $("#item_lishi_table_div").datagrid({
						 url:'getHyItemResultsHistory.action',
						 dataType: 'json',
						 width:600,
						 queryParams:{'exam_num':$("#exam_num").val(),'item_id': rows[rowIndex].item_id},
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
					 drawGraph($("#exam_num").val(), rows[rowIndex].item_id);
					 $('#ls_graph').css('display', 'block');
					 
//					 $(td).mouseleave(function(){
//						 $(td).unbind("mouseleave");
//						 $('#results_contrast').css('display', 'none');
//						 $('#ls_graph').css('display', 'none');
//					 });
					 changefontsize();
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
	if(row.health_level == 1 || row.health_level == 2 || row.health_level == 3|| row.health_level == 5|| row.health_level == 6){
		return 'color:#F00;';
	}else if(row.health_level == 4){
		return 'color:#FF9B00;';
	}
}
function f_bs(val,row){
	if(row.health_level == 1){
		return '↑';
	}else if(row.health_level == 2){
		return '↓';
	}else if(row.health_level == 5){
		return '↑↑';
	}else if(row.health_level == 6){
		return '↓↓';
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
			       {align:'center',field:'dep_name',title:'收费项目',width:10},
		           {align:'center',field:"exam_doctor","title":"检查医生","width":10},
		           {align:'center',field:"exam_date","title":"检查时间","width":10},
		           {align:'center',field:'item_name',title:'检查项目',width:10,"styler":f_color_yx},
		           {align:'center',field:'exam_result',title:'检查结果',width:30,"formatter":f_show_picture,"styler":f_color_yx}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		MergeCells('yx_itemResultList', 'dep_name,exam_doctor,exam_date');
		    		changefontsize();
		    	},
		    	onDblClickCell:function(rowIndex, field, value){
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
						 queryParams:{'exam_num':$("#exam_num").val(),'item_id': rows[rowIndex].sam_demo_id},
						 rownumbers:false,
						 columns:[[
						           {align:'center',field:'exam_date',title:'检查时间',width:10},
						           {align:'center',field:'exam_desc',title:'检查描述',width:40},
						           {align:'center',field:'exam_result',title:'检查结果',width:20},
						           {align:'center',field:'cktp',title:'查看图片',width:10,"formatter":f_ls_picture}
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
	if(row.item_name == '检查结论'){
		return 'background:#FEEAA8;color:#ff5100;';
	}
}
function f_ls_picture(val,row){
	return '<a href="javascript:void(0)" onclick = "show_picture(\''+row.req_id+'\')">查看图片</a>';
}
function f_show_picture(val,row){
	if(row.exam_result == 'image_path'){
		return '<a href="javascript:void(0)" onclick = "show_picture(\''+row.req_id+'\')">查看图片</a>';
	}else{
		return row.exam_result.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'</br>');
	}
}

function show_picture(id){
	var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+$("#exam_num").val();
	newwin = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newwin.focus();
}

function getExamDisease(exam_num,type,app_type){
	var lastIndex;
	 var model={"exam_num":exam_num,"sug_flag":type,"app_type":app_type};
	$("#disease_sug_list").datagrid({
			 url:'getZybExamDiseaseList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
			 columns:[[{field:'ck',checkbox:true },
			    {align:'',field:'disease_name',title:'阳性/疾病发现',width:40,editor:{type:'textarea',options:{}},"formatter":f_jygsh},
			 	{align:'',field:'suggest',title:'阳性/疾病建议',width:70,editor:{type:'textarea',options:{}},"formatter":f_jygsh},
			 	{align:'center',field:'biaoxing',title:'标星',width:10,"formatter":f_biao_xing},
			    {align:'center',field:'caozuo',title:'操作',width:10,"formatter":f_sc_disease}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		$(this).datagrid('enableDnd');
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
			    	
			    	var row = $('#disease_sug_list').datagrid('getRows');
			    	
					var editors = $('#disease_sug_list').datagrid('getEditor',{index:index,field:'disease_name'});
					$(editors.target).change(function(){
						$('#disease_sug_list').datagrid('endEdit', index);
						if(status != 'Z'){
							exam_disease_list();
						}
					});
					var editors1 = $('#disease_sug_list').datagrid('getEditor',{index:index,field:'suggest'});
					$(editors1.target).change(function(){
						$('#disease_sug_list').datagrid('endEdit', index);
						if(status != 'Z'){
							exam_disease_list();
						}
					});
					if(field == 'disease_name'){
						$(editors.target).focus();
					}else if(field == 'suggest'){
						$(editors1.target).focus();
					}
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

function f_tjjl(val,row,index){
	var show_val = '';
	for(var i=0;i<unitJSON.length;i++){
		if(unitJSON[i].resultID == val){
			show_val = unitJSON[i].result_name;
		}
	}
	return show_val;
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
//			$('#mb' + i).menubutton();
		}
	}
}

function f_jbfl(val,row,index){
//	if(row.disease_class == ''){
//		row.disease_class = zj_jbfl_data[0].id;
//		row.disease_classs = zj_jbfl_data[0].name;
//	}
//	return'<a href="javascript:void(0)" id="mb' + row.disease_index + '" style="height:24px;" class="easyui-menubutton" menu="#mm3" onmouseover="ShowMenu(' + row.disease_index + ')">'+row.disease_classs+'</a>';
	var show_val = '';
	if(val == '' && zj_jbfl_data.length != 0){
		show_val = zj_jbfl_data[0].name;
		row.disease_class = zj_jbfl_data[0].id;
	}else{
		for(var i=0;i<zj_jbfl_data.length;i++){
			if(zj_jbfl_data[i].id == val){
				show_val = zj_jbfl_data[i].name;
			}
		}
	}
	return show_val;
}

//var fb_key=''
//function ShowMenu(key) {
//	fb_key = key;
//}
//function menuModify(key) {
//	var obj = $("#disease_sug_list").datagrid('getRows');
//	for(i=0;i<obj.length;i++){
//		if(obj[i].disease_index == fb_key){
//			obj[i].disease_class = zj_jbfl_data[key].id;
//			obj[i].disease_classs = zj_jbfl_data[key].name;
//			$("#disease_sug_list").datagrid('refreshRow',i);
////			$('#mb' + fb_key).menubutton();
//		}
//	}
////	sx_disease();
//}

//刷新疾病表格数据
function sx_disease(){
	var obj = $("#disease_sug_list").datagrid('getRows');
	for(i=0;i<obj.length;i++){
		obj[i].disease_index = i;
		$('#disease_sug_list').datagrid('endEdit', i);
		$("#disease_sug_list").datagrid('refreshRow',i);
	}
	
//	for (i = 0; i < $('#disease_sug_list').datagrid('getRows').length; i++) {        
//        $('#mb' + i).menubutton();
//    }
	if(status != 'Z'){
		exam_disease_list();
	}
}

function exam_disease_list(){
	var examDisease = new Array();
	var obj = $("#disease_sug_list").datagrid('getRows');
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
		url:'saveExamDiseaseList.action',
		type:'post',
		data:{
			app_type:$("#app_type").val(),
			exam_num:$("#exam_num").val(),
			examinfoDiseases:JSON.stringify(examDisease)
		},
		success:function(data){
//			$(".loading_div").remove();
//			var obj = data.split("-");
//			if(obj[0] == 'ok'){
//				if(exam_status == 'N'){
//					$.messager.alert("操作提示",obj[1], "info",refsh_page);
//				}else{
//					$.messager.alert("操作提示",obj[1], "info",close_page);
//				}
//			}else{
//				$.messager.alert("操作提示",obj[1], "error");
//			}
		}
	});
}

//重新生成结论与建议
function creat_new_jielun(){
	$('#disease_sug_list').datagrid('load', {
		"exam_num":$("#exam_num").val(),"sug_flag":"Y","app_type":$("#app_type").val()
	});
	getFinalResult($("#exam_num").val(),'Y',$("#app_type").val());
}
//保存总检数据
function save_examsummary(exam_status){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	var examDisease = new Array();
	var obj = $("#disease_sug_list").datagrid('getRows');
	for(i=0;i<obj.length;i++){
		examDisease.push({
			disease_id:obj[i].disease_id,
			disease_name:obj[i].disease_name.replace(/^\s+|\s+$/g,''),
			disease_type:obj[i].disease_type,
			icd_10:obj[i].icd_10,
			suggest:obj[i].suggest.replace(/^\s+|\s+$/g,''),
			disease_class:obj[i].disease_class,
			disease_num:obj[i].disease_num,
			data_source:obj[i].data_source,
			exam_result:obj[i].exam_result,
			career_hazards:obj[i].career_hazards,
			career_suggest:obj[i].career_suggest,
			diagnosis_source:obj[i].diagnosis_source,
			item_source:obj[i].item_source,
			resultID:obj[i].resultID,
			occudiseaseIDorcontraindicationID:obj[i].occudiseaseIDorcontraindicationID
		});
	}
	$.ajax({
		url:'saveZybExamSummary.action',
		type:'post',
		data:{
			app_type:$("#app_type").val(),
			exam_num:$("#exam_num").val(),
			id:$("#id").val(),
			final_exam_result:$("#zongjianjielun").val(),
			exam_guidance:$("#exam_guidance").val(),
			examinfoDiseases:JSON.stringify(examDisease),
			exam_status:exam_status
		},
		success:function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				if(exam_status == 'N'){
					$.messager.alert("操作提示",obj[1], "info",refsh_page);
				}else{
					$.messager.alert("操作提示",obj[1], "info",close_page);
				}
			}else{
				$.messager.alert("操作提示",obj[1], "error");
			}
		}
	});
}
//取消总检
function qv_examsummary(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);

	$.ajax({
		url:'cancelZybExamSummary.action',
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
				$.messager.alert("操作提示",obj[1], "info",refsh_page);
			}else{
				$.messager.alert("操作提示",obj[1], "error");
			}
		}
	});
}
//审核或取消审核
function shengheorqv(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	
	var examDisease = new Array();
	var obj = $("#disease_sug_list").datagrid('getRows');
	for(i=0;i<obj.length;i++){
		examDisease.push({
			disease_id:obj[i].disease_id,
			disease_name:obj[i].disease_name.replace(/^\s+|\s+$/g,''),
			disease_type:obj[i].disease_type,
			icd_10:obj[i].icd_10,
			suggest:obj[i].suggest.replace(/^\s+|\s+$/g,''),
			disease_class:obj[i].disease_class,
			disease_num:obj[i].disease_num,
			data_source:obj[i].data_source,
			exam_result:obj[i].exam_result,
			career_hazards:obj[i].career_hazards,
			career_suggest:obj[i].career_suggest,
			diagnosis_source:obj[i].diagnosis_source,
			item_source:obj[i].item_source,
			resultID:obj[i].resultID,
			occudiseaseIDorcontraindicationID:obj[i].occudiseaseIDorcontraindicationID
		});
	}
	
	$.ajax({
		url:'approveZybExamSummary.action',
		type:'post',
		data:{
			app_type:$("#app_type").val(),
			exam_num:$("#exam_num").val(),
			id:$("#id").val(),
			approve_status:$("#approve_status").val(),
			final_exam_result:$("#zongjianjielun").val(),
			exam_guidance:$("#exam_guidance").val(),
			examinfoDiseases:JSON.stringify(examDisease)
		},
		success:function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				if($("#approve_status").val() == 'A'){
					$.messager.alert("操作提示",obj[1], "info",refsh_page);
				}else{
					$.messager.alert("操作提示",obj[1], "info",close_page);
				}
			}else{
				$.messager.alert("操作提示",obj[1], "error");
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
		$("#dlg-hb").dialog("center");
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
	$("#dlg-tjzs").dialog("center");
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
	getFinalResult(exam_num,$("#isExamSummaryNew").val(),$("#app_type").val());
}

function printreport(){
	var barids = $("#exam_num").val();
	var exeurl="reportServices://&0&"+barids+"&Y&2";//0代表体检号Y代表预览，2代表职业病
	location.href=exeurl;
//	if($("#zyb_barcode_print_type").val() == '5'){//调用5.0打印
//		var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&opreport&"+barids;
//		location.href=exeurl;
//	}else{
//		$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
//	}
}
function addCrmVisitPlanPage(){
	$("#dlg-edit").dialog({
		title:'编辑健康回访',
		width : 800,
		height :620,
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
      

      function drawGraph(exam_num, itemId) {
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('ls_graph'));
		
		$.ajax({
			url : 'getHyItemResultsHistory.action?exam_num=' + exam_num
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
      
      function getoccuhazardfactorsGrid() {
  		var model = {
  			"exam_num" : $("#exam_num").val()
  		};
  		$("#zywhysset").datagrid({
  			url : 'examoccuhazardfactorslist.action',
  			dataType : 'json',
  			queryParams : model,
  			rownumbers : false,
  			pageSize : 5,// 每页显示的记录条数，默认为10
  			pageList : [ 5, 10, 20 ],// 可以设置每页记录条数的列表
  			columns : [ [ {
  				align : 'center',
  				field : 'hazard_name',
  				title : '职业危害因素',
  				width : 30
  			}, {
  				align : 'center',
  				field : 'occuphyexaclass_name',
  				title : '体检类别',
  				width : 15
  			}, {
  				align : 'center',
  				field : 'hazard_year',
  				title : '危害年限',
  				width : 15
  			}, {
  				align : 'center',
  				field : 'jcyj',
  				title : '检查依据',
  				width : 15,
  				formatter:f_jcyj
  			}] ],
  			onLoadSuccess : function(value) {
  				$("#datatotal").val(value.total);
  			},
  			singleSelect : false,
  			collapsible : true,
  			pagination : false,
  			fitColumns : true,
  			striped : true,
  			fit:true
  		});
  	}
    //显示职业史
      function fn_show_zhiyeshi(){
      	getcusthisGrid();
      	$("#dlg-zys").dialog('open');
      	$("#dlg-zys").dialog('center');
      }
      function f_jcyj(val,row,index){
    	  return '<a href="javascript:void(0)" onclick = "ck_jcyj('+index+')">查看</a>';
      }
      function ck_jcyj(index){
    	  var row = $("#zywhysset").datagrid('getRows')[index];
    	  var model = {'occusectorid':row.hazardfactorsID,'occutypeofworkid':row.occuphyexaclassid};
    	  $("#exam_jcyv").datagrid({
  			url:'getExamSummaryCheckcriterionList.action',
  			dataType: 'json',
  			queryParams:model,
  			rownumbers:false,
  			columns:[[
  				     {align:'center',field:'criterion_name',title:'检查依据名称',width:10}
  			]],	    	
  			onLoadSuccess:function(value){
  			    $("#datatotal").val(value.total);
  			},
  			singleSelect:false,
  			collapsible:true,
  			pagination: false,
  			fitColumns:true,
  			striped:true,
  			fit:true,
  			nowrap:false
  		 });
    	  $("#dlg-jcyj").dialog('open');
    	  $("#dlg-jcyj").dialog('center');
      }
      //////////////////////////////职业病历史处理////////////////////////////////////////
      function getcusthisGrid(){	
      		 var model={
      				 "exam_num":$("#exam_num").val()
      		 };
      	     $("#exam_zhiyeshi").datagrid({
      		 url:'zybCustomerHislist.action',
      		 dataType: 'json',
      		 queryParams:model,
      		 rownumbers:false,
      	     pageSize: 5,//每页显示的记录条数，默认为10 
      	     pageList:[5,10,20],//可以设置每页记录条数的列表 
      		 columns:[[
      		    {align:'center',field:'company',title:'工作单位',width:18},	
      		    {align:'center',field:'workshop',title:'车间部门',width:20},
      		 	{align:'center',field:'worktype',title:'工种',width:20},
      		 	{align:'center',field:'measure',title:'防护措施',width:30},
      		 	{align:'center',field:'harmname',title:'有害因素名称',width:20},
      		 	{align:'center',field:'concentrations',title:'有害因素浓度',width:20},
      		 	{align:'center',field:'isradiation',title:'是否放射',width:15},
      		 	{align:'center',field:'startdate',title:'开始时间',width:15},		 	
      		 	{align:'center',field:'enddate',title:'结束时间',width:15}
      		 	]],	    	
      	    	onLoadSuccess:function(value){
      	    		$("#datatotal").val(value.total);
      	    	},
      	    	singleSelect:false,
      		    collapsible:true,
      			pagination: false,
      		    fitColumns:true,
      		    striped:true
      	});
      }
      //查案职业史
      function fn_show_zhiyeshi(){
    		//getcusthisGrid();
    		var url='getzhiyeshiPage.action?exam_num='+$('#exam_num_x').text();
    		newwin = window.open("", "职业史", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
    		newwin.moveTo(0,0);
    		newwin.resizeTo(screen.width,screen.height-30);
    		newwin.location = url;
    		newwin.focus();
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
function DivClose(id){
	$("#"+id).css('display', 'none');
}
var changefont;
function changefontcss(changefont){
	if(changefont==1){
		fontsize=parseInt(fontsize)+1;
	}else{
		fontsize=parseInt(fontsize)-1;
	}
	 if (fontsize>40){
	    	fontsize=14;
	    }
	setCookie('fontsize',fontsize);
	changefontsize();
}

function changefontsize(){
	$("textarea").css('font-size',fontsize+'px');	
	$('.datagrid-cell').css('font-size',fontsize+'px');		
}
// 查看检查结果
function exan_result() {
	$("#dlg-edit").dialog({
		title : '职业病检查结果',
		width : 800,
		height : 550,
		href : 'getExamSummaryExaminationResultPage.action?exam_num='+$("#exam_num").val()+'&exam_info_id='+$("#exam_info_id").val()
	});
	$("#dlg-edit").dialog("open");
	$("#dlg-edit").dialog('center');
}

function getwzGrid(exam_num){
	var model={"exam_num":exam_num};
	$("#wz_itemResultList").datagrid({
			 url:'getSymptomsAndHistoryList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:false,
			 columns:[[
		           {align:'center',field:'item_name',title:'检查项目',width:20},
		           {align:'',field:'exam_result',title:'检查结果',width:30}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
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

//批量复审
function piliangfushen(){
    var checkedItems = $('#groupusershow').datagrid('getChecked');
    if(checkedItems.length <=0){
        $.messager.alert("操作提示", "请选择一个体检人员","error");
        return;
    }
    var piliang= new Array();
    $.each(checkedItems, function(index, item){
        piliang.push('\''+item.exam_num+'\'');
    });
    var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    $("body").prepend(str);
    $.ajax({
        url:'getBatchRetrial.action',
        data:{
            exam_num:piliang.toString()
        },
        type:'post',
        success:function(data){
            $(".loading_div").remove();
            if(data==""){
                $.messager.alert("提示信息","已复审","info");
                $('#groupusershow').datagrid('reload');
            } else {
                $.messager.alert("复审失败",data,"error");
            }
        },
        error:function(){
            $(".loading_div").remove();
            $.messager.alert("警告信息","操作失败","error");
        }
    })
}