$(document).ready(function () {
	$("#danxiang").css('height',window.screen.height-140);
    getdep_disease_logic()
});

/**
 * 显示疾病逻辑列表
 */
 function getdep_disease_logic(){
	     var  model={
            item_num : $('#item_num').val(),
             dep_id:-1,
             logic_class:0
	     }
	     $("#diseaseshow").datagrid({
			 url:'getDiseaseLogicSingleList.action',
			 queryParams:model,
			 rownumbers:true,
		     pageSize:15,//
		     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
			 columns:[[
				{field:'ck',checkbox:true },
				{align:'center',field:'disease_num',title:'阳性指标/疾病编码',width:15},
				{align:'left',field:'logic_name',title:'阳性指标/疾病名称',width:20},
				{align:'center',field:'item_num',title:'检查项目编码',width:15},	
				{align:'center',field:'item_name',title:'检查项目名称',width:20},
				{align:'left',field:'tiaojian',title:'条件描述',width:50},
				{align:'center',field:'sex',title:'适用性别',width:12},
				{align:'center',field:'ageMinMax',title:'适用年龄',width:12},
				{align:'center',field:'chi_name',title:'修改人',width:10},
				{align:'center',field:'update_time',title:'修改时间',width:10},
				{align:'center',field:"isActive",title:"启用状态","formatter":f_status},
				{align:'center',field:'sss',title:'修改',"formatter":f_xg},
				{align:"center",field:'off_on',title:'停用/启用',width:15,"formatter":f_off_on}
		 	]],	
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
		    fit:true,
	        toolbar:toolbar,
	        onDblClickRow:function(index, row){
	        	updataSampleReportDemoPage(row.ids);
	        }
	});
}

function f_status(val,row){
	if(val=="Y"){
		return '<span style="color:#1CC112;">启用中</span>';
	}else if(val=='N') {
		return '<span style="color:#f00;">已停用</span>';
	}
}
//停用/启用按钮
function f_off_on(val,row){
    if(row.isActive=="Y"){
        return '<a style="color:#f00;" href=\"javascript:f_ty(\''+row.ids+'\')\">点击停用</a>';
    }else if(row.isActive=='N') {
       return '<a style="color:#1CC112;" href=\"javascript:f_qy(\''+row.ids+'\')\">点击启用</a>';
    }
}
//停用
function f_ty(id)
{
	$.messager.confirm('提示信息','是否确认停用该条记录？',function(r){
		if(r){
			$.ajax({
         	url:'diseaseLogicSingleStartOrEnd.action?ids='+id+'&isActive=N',
         	type:'post',
         	success:function(data){
         		$.messager.alert('提示信息',data);
    			$('#diseaseshow').datagrid('reload');
         	},
         	error:function(){
         		$.messager.alert('提示信息','操作失败！','error');
         	}
			});
		}
	})
}
//启用
function f_qy(id)
{
	$.ajax({
		url:'diseaseLogicSingleStartOrEnd.action?ids='+id+'&isActive=Y',
		type:'post',
		success:function(data){
			$.messager.alert('提示信息',data);
			$('#diseaseshow').datagrid('reload');
		},
		error:function(){
			$.messager.alert('提示信息','操作失败！','error');
		}
	});
}

//-------------------------------------修改报告样本页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updataSampleReportDemoPage(\''+row.ids+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改单项疾病逻辑
 * 
 */
 function updataSampleReportDemoPage(id){
    $("#dlg-custedit").dialog({
        title : '新增单项阳性自动生成逻辑',
        center:'center',
        href : 'itemDiseaseLogicSingleEditPage.action?ids='+id+'&item_num='+$('#item_num').val()
    })
    $("#dlg-custedit").dialog('open');
    $("#dlg-custedit").dialog('center');
}
//-------------------------------------新增报疾病逻辑修改疾病逻辑----------------------------

//------------------------------------删除疾病逻辑----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.ids+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
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
					$('#diseaseshow').datagrid('reload');
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	})
}
/**
 * 批量删除
 * 
 */
function deluserrow(ids){
	if(ids==""){
		$.messager.alert('提示信息','请选择要删除的疾病逻辑!')
		return;
	}
	$.messager.confirm('提示信息','是否确定删除疾病逻辑',function(r){
	 	if(r){
	 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
				 url : 'delDiseaseLogicSingle.action?ids='+ids,
				type : "post",
				success : function(data) {
					$(".loading_div").remove();	
					$.messager.alert("操作提示",data);
					$('#diseaseshow').datagrid('reload');
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
	handler : function() {
		// var obj = $('#some_tree').tree('getSelected');
		// var dep_id="";
		// if(obj.id != "" && obj.id != -1){
		// 	dep_id=obj.id;
		// }
		$("#dlg-custedit").dialog({
			title : '新增单项阳性自动生成逻辑',
			center:'center',
			href : 'itemDiseaseLogicSingleEditPage.action?item_num='+$('#item_num').val()
		})
		$("#dlg-custedit").dialog('open');
		$("#dlg-custedit").dialog('center');
	}
}, {
	text:'批量删除',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var ids = new Array();
    	var checkedItems = $('#diseaseshow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids.push(item.ids);
	    }); 
	    deluserrow(ids.toString());
    }
} ];
