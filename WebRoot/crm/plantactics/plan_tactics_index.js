$(document).ready(function () {
	$('#doctor_name').combobox({
		url : 'getDepuser.action?type='+1,
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'chi_Name',
		onLoadSuccess : function(data) {
			$('#doctor_name').combobox('setText',data[0].chi_Name);
			$('#doctor_name').combobox('setValue',data[0].id);
		}
	});
	getPlantacTicsList();
	getPlantacTicsDetailList();
});
var num;
function getPlantacTicsList(){
	$("#crm_plan_tactics").datagrid({
		url: 'getPlantacTicsList.action',
		rownumbers:false,
		toolbar:'#toolbar',
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		columns:[[
//			  {field:'ck',checkbox:true },
			  {align:"center",field:"id","title":"id",hidden:true},
		      {align:"center",field:"tactics_num","title":"策略编码","width":15},
		      {align:"center",field:"notices","title":"策略描述","width":25},
		      {align:"center",field:"tactics_type_s","title":"策略类型","width":10},
		      {align:"center",field:"rmark","title":"对应策略说明","width":25},
              {align:"center",field:"creater","title":"创建者","width":10},
     		  {align:'center',field:"create_date","title":"创建时间","width":15},
     		  {align:"center",field:"updater","title":"修改者","width":10},
     		  {align:"center",field:"update_date","title":"修改时间","width":15},
     		  {align:"center",field:"add_tactics_detail","title":"增加明细","width":15,"formatter":f_tactics_detail}
     		  ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    onDblClickRow:function(rowIndex,row){
	    	num = row.tactics_num;
	    	getPlantacTicsDetailList();
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: true,
		fitColumns:true,
		striped:true,
//		rownumbers:true,
		toolbar:toolbar	
	});
}


var toolbar=[{
		text:'新增',
		width:90,
		iconCls:'icon-print',
	    handler:function(){
	    	$.ajax({
				url : 'getTacticsNum.action',
				type : "post",//数据发送方式   
				success : function(text) {
					if(text != '' && text != null){
						$("#tactics_num").val(text);
						$("#dlg-save").dialog('open');
						$("#notices").val('');
						$("#rmark").val('');
						$("#tactics_id").val('');
					}else{
						$.messager.alert("操作提示", "获取策略编号失败，请重新获取", "info");		
					}
				},
				error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
	    	
	    }
	},{
		text:'修改',
		width:90,
		iconCls:'icon-edit',
	    handler:function(){
	    	var row = $("#crm_plan_tactics").datagrid("getSelections");
	    	if(row.length == '1'){
	    		$("#tactics_id").val(row[0].id);
	    		$("#tactics_num").val(row[0].tactics_num);
//	    		$("#distancedate").combobox('setValue',row[0].tactics_type);
	    		$("#notices").val(row[0].notices);
	    		$("#rmark").val(row[0].rmark);
	    		$("#dlg-save").dialog('open');
	    		
	    	}else{
	    		$.messager.alert("操作提示", "单次只能修改一条数据，请重新选择。。", "error");		
	    	}
	    }
	},{
		text:'删除',
		width:90,
		iconCls:'icon-remove',
	    handler:function(){
	    	var row = $("#crm_plan_tactics").datagrid("getSelections");
	    	if(row.length == 0 ){
	    		$.messager.alert("操作提示", "请选择需要删除的数据。。。", "info");	
	    		return;
	    	}
	    	var tactics_nums = new Array();
	    	for(var i = 0 ; i < row.length ; i++){
	    		tactics_nums.push("'"+row[i].tactics_num+"'");
	    	}
	    	
	    	$.messager.confirm('提示信息','删除当前选择的策略,策略明细也将一起删除，操作是否继续？',function(r){
	    		if(r){
	    			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					$("body").prepend(str);
	    			$.ajax({
						url : 'delPlanTactics.action',
						data : {"tactics_nums":tactics_nums.toString()
								},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split('-')[0] == 'ok') {
								$("#crm_plan_tactics").datagrid('reload');
								num = '';
								$("#crm_plan_tactics_detail").datagrid('reload');
								$.messager.alert("操作提示", text);
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	    		}
	    	});
	    }
	}];

function f_tactics_detail(val,row){
	return '<a href=\"javascript:add_tactics_detail(\''+row.tactics_num+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"授权\" /></a>';
}

function add_tactics_detail(tactics_num){
	$("#tactics_num_d").val(tactics_num);
	$("#distancedate").numberbox('setValue','');
	$("#notices_d").val('');
// 	$("#tactics_num_d").val('');
 	$("#tactics_detail_id").val('');
	
	$("#dlg-detail").dialog('open');
	
}


function getPlantacTicsDetailList(){
	 var model={"tactics_num":num};
	$("#crm_plan_tactics_detail").datagrid({
		url: 'getPlantacTicsDetailList.action',
		queryParams:model,
		rownumbers:false,
		toolbar:"#toolbar1",
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		columns:[[
//			  {field:'cke',checkbox:true },
			  {align:"center",field:"id","title":"id",hidden:true},
		      {align:"center",field:"tactics_num","title":"策略编码","width":15},
		      {align:"center",field:"notices","title":"回访内容描述","width":20},
		      {align:"center",field:"plan_doctor","title":"回访医生","width":20},
		      {align:"center",field:"distancedate","title":"距离体检几天回访","width":15},
              {align:"center",field:"creater","title":"创建者","width":15},
     		  {align:'center',field:"create_date","title":"创建时间","width":20},
     		  {align:"center",field:"updater","title":"修改者","width":15},
     		  {align:"center",field:"update_date","title":"修改时间","width":20}
     		  ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: true,
		fitColumns:true,
		striped:true,
//		rownumbers:true,
		toolbar:toolbar1	
	});
}
var toolbar1=[{
		text:'删除',
		width:90,
		iconCls:'icon-remove',
	    handler:function(){
	    	var row = $("#crm_plan_tactics_detail").datagrid("getSelections");
	    	if(row.length == 0 ){
	    		$.messager.alert("操作提示", "请选择需要删除的数据。。。", "info");	
	    		return;
	    	}
	    	var tactics_detail_ids = new Array();
	    	for(var i = 0 ; i < row.length ; i++){
	    		tactics_detail_ids.push(row[i].id);
	    	}
	    	
	    	$.messager.confirm('提示信息','确定删除当前选择的策略明细吗？',function(r){
	    		if(r){
	    			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					$("body").prepend(str);
	    			$.ajax({
						url : 'delPlanTacticsDetail.action',
						data : {"tactics_detail_ids":tactics_detail_ids.toString()
								},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split('-')[0] == 'ok') {
								$("#crm_plan_tactics_detail").datagrid('reload');
								
								$.messager.alert("操作提示", text);
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	    		}
	    	});
	    }
	},{
		text:'修改',
		width:90,
		iconCls:'icon-edit',
	    handler:function(){
	    	var row = $("#crm_plan_tactics_detail").datagrid("getSelections");
	    	if(row.length == '1'){
	    		$("#tactics_detail_id").val(row[0].id);
	    		$("#tactics_num_d").val(row[0].tactics_num);
	    		$("#distancedate").numberbox('setValue',row[0].distancedate);
	    		$("#notices_d").val(row[0].notices);
	    		$('#doctor_name').combobox('setText',row[0].plan_doctor);
				$('#doctor_name').combobox('setValue',row[0].plan_doctor_id);
              	$("#dlg-detail").dialog('open');
	    	}else{
	    		$.messager.alert("操作提示", "单次只能修改一条数据，请重新选择。。", "error");		
	    	}
	    }
	}];
function save(){//保存策略信息
	if($("#tactics_num").val() == '' || $("#tactics_num").val() == null){
		$.messager.alert("操作提示", "编码不能为空，操作不能继续。。。", "warning");
		return;
	}
	var tactics_num = $("#tactics_num").val();
	var notices = $("#notices").val();
	var tactics_type = $("#tactics_type").combobox('getValue');
	var rmark = $("#rmark").val();
	var tactics_id = $("#tactics_id").val();
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
			url : 'savePlanTactics.action',
			data : {"tactics_num":tactics_num,
				    "notices":notices,
			        "tactics_type":tactics_type,
			        "rmark":rmark,
			        "tactics_id":tactics_id
					},
			type : "post",//数据发送方式   
			success : function(text) {
				$(".loading_div").remove();
				if (text.split('-')[0] == 'ok') {
					$("#dlg-save").dialog('close');
					getPlantacTicsList();
					$.messager.alert("操作提示", text);
				} else {
					$.messager.alert("操作提示", text, "error");
				}
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
}
function save_detail(){
	var distancedate = $("#distancedate").val();
	var plan_doctor_id = $("#doctor_name").combobox('getValue');
	var notices = $("#notices_d").val();
	var tactics_num = $("#tactics_num_d").val();
	var tactics_detail_id = $("#tactics_detail_id").val();
	if(distancedate == '' || distancedate < 0){
		$.messager.alert("操作提示", "回访日期不能为空，操作不能继续。。。", "warning");
		return ;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
			url : 'savePlanTacticsDetail.action',
			data : {"tactics_num":tactics_num,
				    "notices":notices,
			        "plan_doctor_id":plan_doctor_id,
			        "distancedate":distancedate,
			        "tactics_detail_id":tactics_detail_id
					},
			type : "post",//数据发送方式   
			success : function(text) {
				$(".loading_div").remove();
				if (text.split('-')[0] == 'ok') {
					$("#dlg-detail").dialog('close');
					$("#crm_plan_tactics_detail").datagrid('reload');
					$.messager.alert("操作提示", text);
				} else {
					$.messager.alert("操作提示", text, "error");
				}
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
		$("#distancedate").numberbox('setValue','');
		$("#notices_d").val('');
	 	$("#tactics_num_d").val('');
	 	$("#tactics_detail_id").val('');
}

