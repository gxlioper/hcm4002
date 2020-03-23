$(document).ready(function () {
	getgroupuserGrid();

});
 //-------------------------------显示疾病逻辑列表------------------------------------
/**
 * 显示疾病逻辑列表
 */
 function getgroupuserGrid(){	
	     var  model={
	    		 logic_name:$('#c_logic_name').val(),
	    		 logic_num:$('#c_logic_num').val(),
	    		 logic_type:$('#c_logic_type').combobox('getValue')
	    		 //sex:$('#sex').val()
	     }
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#groupusershow").datagrid({
		 url:'getDiseaseLogic.action',
		 toolbar:'#toolbar',
		 queryParams:model,
		 rownumbers:false,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'id',title:'ID编码',width:7},	
		    {align:'center',field:'logic_type_s',title:'类型',width:7,},
		 	{align:'center',field:'logic_name',title:'阳性指标/疾病名称',width:15},
		 	{align:'center',field:'logic_num',title:'阳性指标/疾病编码',width:10,},
		 	{align:'center',field:'tiaojian',title:'条件描述',width:50},
		 	{align:'center',field:'sss',title:'修改',"formatter":f_xg},
		 	{align:'center',field:'ss',title:'删除',"formatter":f_sc},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolbar
	});
}


//-------------------------------------修改报告样本页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updataSampleReportDemoPage(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改报告样本页面
 * 
 */
 function updataSampleReportDemoPage(id){
			$("#dlg-custedit").dialog({
			title:'修改报告样本',
			width : 600,
			height : 320,
			href:'updataSampleReportDemoPage.action?id='+id
		});
		$("#dlg-custedit").dialog('open');
}
//-------------------------------------新增报疾病逻辑修改疾病逻辑----------------------------

/**
 * 添加报告样本
 * 
 */
function addSampleReportDemo() {
	if($('#addnum').val()==''||/[\u0391-\uFFE5]/g.test(document.getElementById('addnum').value)){
		$('#addnum').focus();
		return;
	}
	
	if ($('#ssnum').text()!='') {
		return;
	}
	if($('#addname').val()==''){
		$('#addname').focus();
		return;
	}

	if(!/^[0-9]{1,20}$/.test(document.getElementById('addseq').value)&&
		document.getElementById('addseq').value!=''){
		$('#addseq').focus();
		return;
	}
	$.ajax({
		url : 'addSampleReportDemo.action',
		type : 'post',
		//dataType: "json", 
		data : {
			"id" : $('#addid').val(),
			"demo_name" : $('#addname').val(),
			"demo_num" : $('#addnum').val(),
			"print_seq" : $('#addseq').val(),
			"remark" : $('#addremark').val(),
			"isPrint_BarCode":$("input[name='isPrint_BarCode']:checked").val(),
		},
		success : function(data) {
			$.messager.alert('添加成功',data);
			$('#dlg-custedit').dialog('close')
			getgroupuserGrid();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}
//------------------------------------删除疾病逻辑----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 删除报告样本提示信息
 * 
 */
function f_deluser(id) {
	$.messager.confirm('提示信息', '确定删除疾病逻辑？', function(r) {
		if (r) {
			$.ajax({
				url : 'deletDiseaseLogic.action?ids='+ id,
				type : 'post',
				success : function(data) {
					$.messager.alert('提示信息', data);
					$('#groupusershow').datagrid('reload')
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	})
}
/**
 * 批量删除报告样本
 * 
 */
function deluserrow(ids){
	if(ids==""){
		$.messager.alert('提示信息','请选择要删除的报告样本!')
		return;
	}
	$.messager.confirm('提示信息','是否确定删除选中报告样本',function(r){
	 	if(r){
	 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
				 url : 'deletDiseaseLogic.action?ids='+ids,
				type : "post",
				success : function(data) {
						$.messager.alert("操作提示",data);
						$('#groupusershow').datagrid('reload');
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			 });
	 	 }
	 });
}
//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text : '新增疾病逻辑',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '疾病自动生成逻辑设置',
			href : 'addDiseaseLogicPage.action'
		});
		$("#dlg-custedit").dialog('open');
		$("#dlg-custedit").dialog('hcenter');
	}
}, {
	text:'批量删除',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var ids = new Array();
    	var checkedItems = $('#groupusershow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids.push(item.id);
	    }); 
	    deluserrow(ids.toString());
    }
} ];