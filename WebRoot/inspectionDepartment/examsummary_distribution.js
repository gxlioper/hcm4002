var toolbar = [{
    text:'主检派发',
    iconCls:'icon-add',
    handler:function(){
    	var rows = $("#examinfoList").datagrid('getChecked');
    	if(rows.length == 0){
    		$.messager.alert('提示信息', '请选择需要派发的体检信息!','error');
    		return;
    	}
    	$("#dlg-edit").dialog("open");
    	$("#dlg-edit").dialog("center");
    }
},'-',{
    text:'刷新',
    iconCls:'icon-print',
    handler:function(){
    	$("#examinfoList").datagrid('reload');
    }
}];
$(document).ready(function () {
	getexamInfoGrid();
	$('#exam_doctor').combobox({
		url : 'getFinalDoctorList.action?operation_type=1',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 300,//自动高度适合
		valueField : 'id',
		textField : 'name',
		multiple : false,
		onLoadSuccess : function(data){
			if(data.length > 0){
				$('#exam_doctor').combobox('setValue',data[0].id);
			}
		},
		loadFilter:function(data){
			if(data.length > 0){
				data.splice(0,1);
			}
			return data;
		}
	});
	getfinalCount();
	getfinaldoctor();
});
function getfinalCount(){
	$.ajax({
    	url:'getNotFinalCount.action?operation_type=1',
    	type:'post',
    	success:function(data){
    		var obj=eval("("+data+")");
    		$("#wd_count").html(obj.t);
    		$("#wz_count").html(obj.wz_count);
    	},
    	error:function(){
    		$(".loading_div").remove();
    		$.messager.alert("警告信息","操作失败","error");
    	}
    });
}

function getfinaldoctor(){
	$.ajax({
    	url:'getFinalDoctorList.action?operation_type=1',
    	type:'post',
    	success:function(data){
    		var obj=eval("("+data+")");
    		for(var i=1;i<obj.length;i = i+5){
    			var count = (obj.length - 1 - i);
    			var str = '';
    			if(count >= 5){
    				str = '<dl><dd style="width:10px;"><input type="checkbox" name="final_doctor" id="doctoc_'+obj[i].id+'" value="'+obj[i].id+'"/></dd><dd style="width:140px;" onclick="checke_boxx('+obj[i].id+')">'+obj[i].name+'</dd>'
    			    + '<dd style="width:10px;"><input type="checkbox" name="final_doctor" id="doctoc_'+obj[i+1].id+'" value="'+obj[i+1].id+'"/></dd><dd style="width:140px;" onclick="checke_boxx('+obj[i+1].id+')">'+obj[i+1].name+'</dd>'
    			    + '<dd style="width:10px;"><input type="checkbox" name="final_doctor" id="doctoc_'+obj[i+2].id+'" value="'+obj[i+2].id+'"/></dd><dd style="width:140px;" onclick="checke_boxx('+obj[i+2].id+')">'+obj[i+2].name+'</dd>'
    			    + '<dd style="width:10px;"><input type="checkbox" name="final_doctor" id="doctoc_'+obj[i+3].id+'" value="'+obj[i+3].id+'"/></dd><dd style="width:140px;" onclick="checke_boxx('+obj[i+3].id+')">'+obj[i+3].name+'</dd>'
    			    + '<dd style="width:10px;"><input type="checkbox" name="final_doctor" id="doctoc_'+obj[i+4].id+'" value="'+obj[i+4].id+'"/></dd><dd style="width:140px;" onclick="checke_boxx('+obj[i+4].id+')">'+obj[i+4].name+'</dd></dl>';
    			}else{
    				str = '<dl><dd style="width:10px;"><input type="checkbox" name="final_doctor" id="doctoc_'+obj[i].id+'" value="'+obj[i].id+'"/></dd><dd style="width:140px;" onclick="checke_boxx('+obj[i].id+')">'+obj[i].name+'</dd>'
    				if(count >= 2){
    					str += '<dd style="width:10px;"><input type="checkbox" name="final_doctor" id="doctoc_'+obj[i+1].id+'" value="'+obj[i+1].id+'"/></dd><dd style="width:140px;" onclick="checke_boxx('+obj[i+1].id+')">'+obj[i+1].name+'</dd>';
    				}
    				if(count >= 3){
    					str += '<dd style="width:10px;"><input type="checkbox" name="final_doctor" id="doctoc_'+obj[i+2].id+'" value="'+obj[i+2].id+'"/></dd><dd style="width:140px;" onclick="checke_boxx('+obj[i+2].id+')">'+obj[i+2].name+'</dd>';
    				}
    				if(count >= 4){
    					str += '<dd style="width:10px;"><input type="checkbox" name="final_doctor" id="doctoc_'+obj[i+3].id+'" value="'+obj[i+3].id+'"/></dd><dd style="width:140px;" onclick="checke_boxx('+obj[i+3].id+')">'+obj[i+3].name+'</dd>';
    				}
    				str += '</dl>';
    			}	
    			$("#doctor_list").append(str);
    		}
    		
    	},
    	error:function(){
    		$(".loading_div").remove();
    		$.messager.alert("警告信息","操作失败","error");
    	}
    });
}
function checke_boxx(id){
	if($("#doctoc_"+id).prop('checked')){
		$("#doctoc_"+id).prop('checked',false);
	}else{
		$("#doctoc_"+id).prop('checked',true);
	}
}
//按主检医生派发保存
function final_can_distribution(){
	var can_count = $('#final_can_count').numberbox('getValue');
	if(can_count <= 0){
		$.messager.alert("警告信息","派发数量必须大于零!","error");
		return;
	}
	var userids = new Array();
     $('input:checkbox[name=final_doctor]:checked').each(function (i) {
         userids.push($(this).val());
     });
     if(userids.length <= 0 ){
    	 $.messager.alert("警告信息","请选择需要派发的主检医生!","error");
 		return;
     }
     $.ajax({
 		url : 'saveExamSummaryDistributionByDoctor.action',
 		data:{'exam_num':userids.toString(),'final_worknum':can_count},
 		type : 'post',
 		success : function(data) {
 			$(".loading_div").remove();
 			var obj = data.split("-");
 			if (obj[0] == 'ok') {
 				$.messager.alert('提示信息', obj[1], 'info');
 				getfinalCount();
 			} else {
 				$.messager.alert('提示信息', obj[1], 'error');
 			}
 		},
 		error : function(data) {
 			$(".loading_div").remove();
 			$.messager.alert('提示信息', data, 'error');
 		}
 	});
}

function getexamInfoGrid(){
	   $("#examinfoList").datagrid({
	   url:'getExamSummaryDistributionList.action',
	   dataType: 'json',
	   pageSize: 30,//每页显示的记录条数，默认为10 
	   pageList:[30,45,60,100],//可以设置每页记录条数的列表 
	   columns:[[
	            {field:'ck',checkbox:true },
			    {align:'center',field:'exam_num',title:tjhname,width:20,sortable:true},
			    {align:'center',field:'arch_num',title:dahname,width:18,sortable:true},
			 	{align:'center',field:'id_num',title:'身份证号',width:25},
			    {align:'center',field:'exam_types',title:'体检类型',width:15},	
			 	{align:'center',field:'user_name',title:'姓名',width:20,sortable:true},
			 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
			 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
			 	{align:'center',field:'phone',title:'电话',width:20},		 	
			 	{align:'center',field:'company',title:'单位',width:20},
			 	{align:'center',field:'set_name',title:'套餐',width:20},
			 	{align:'center',field:'join_date',title:'体检日期',width:15,sortable:true}
			 	]],	    	
	    onLoadSuccess:function(value){ 
		    $("#datatotal").val(value.total);
		},
		nowrap:true,
		rownumbers:true,
		singleSelect:false,
		collapsible:true,
		pagination: true,
		fitColumns:true,
		fit:true,
	    striped:true,
	    toolbar:toolbar
	});
}
function save_examdistribution(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	var rows = $("#examinfoList").datagrid('getChecked');
	var exam_nums = new Array();
	for(var i=0;i<rows.length;i++){
		exam_nums.push(rows[i].exam_num);
	}
	$.ajax({
		url : 'saveExamSummaryDistribution.action',
		data:{'exam_num':exam_nums.toString(),'censoring_doc':$("#exam_doctor").combobox('getValue')},
		type : 'post',
		success : function(data) {
			$(".loading_div").remove();
			var obj = data.split("-");
			if (obj[0] == 'ok') {
				$.messager.alert('提示信息', obj[1], 'info');
				$("#dlg-edit").dialog("close");
				$("#examinfoList").datagrid('reload');
				getfinalCount();
			} else {
				$.messager.alert('提示信息', obj[1], 'error');
			}
		},
		error : function(data) {
			$(".loading_div").remove();
			$.messager.alert('提示信息', data, 'error');
		}
	});
}
