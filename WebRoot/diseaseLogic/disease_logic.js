$(document).ready(function () {
	$('#c_logic_name').focus();
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
		 url:'getDiseaseLogic_all.action',
		 toolbar:'#toolbar',
		 queryParams:model,
		 rownumbers:false,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'id',title:'ID编码',width:7},	
		    {align:'center',field:'logic_type_s',title:'类型',width:7,},
		 	{align:'left',field:'logic_name',title:'阳性指标/疾病名称',width:20},
		 	{align:'center',field:'logic_num',title:'阳性指标/疾病编码',width:10,},
		 	{align:'left',field:'tiaojian',title:'条件描述',width:50},
		 	{align:'center',field:'chi_name',title:'修改人',width:10},
		 	{align:'center',field:'update_time',title:'修改时间',width:10},
		 	{align:'center',field:'sex',title:'适用性别',width:12},
		 	{align:'center',field:'ageMinMax',title:'适用年龄',width:12},
		 	{align:'center',field:'critical_flag',title:'是否危机提醒',width:14,"formatter":f_wjtx},
		 	{align:'center',field:"isActive",title:"启用状态","formatter":f_status},
		 	{align:'center',field:'sss',title:'修改',"formatter":f_xg},
		 	{align:"center",field:'off_on',title:'停用/启用',width:15,"formatter":f_off_on},
		 	//{align:'center',field:'ss',title:'删除',"formatter":f_sc},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:true,
		    striped:true,
	        toolbar:toolbar,
	        onDblClickRow:function(index, row){
	        	var row_id = $('#groupusershow').datagrid('getRows')[index].id;
	        	updataSampleReportDemoPage(row_id);
	        }
	});
}
function f_wjtx(val,row){
	if(val == '1'){
		return "是";
	}else{
		return "否";
	}
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
        return '<a style="color:#f00;" href=\"javascript:f_ty(\''+row.id+'\')\">点击停用</a>';
    }else if(row.isActive=='N') {
       return '<a style="color:#1CC112;" href=\"javascript:f_qy(\''+row.id+'\')\">点击启用</a>';
    }
}
//停用
function f_ty(id)
{
	$.messager.confirm('提示信息','是否确认停用该条记录？',function(r){
		if(r){
			$.ajax({
         	url:'update_diseaseLogic_off.action?ids='+id,
         	type:'post',
         	success:function(data){
         		var obj=data;
         		if(obj=='success'){
         			$.messager.alert('提示信息',"停用该条记录成功！");
         			getgroupuserGrid();
         		}else if(obj=='error'){
         			$.messager.alert('提示信息',"停用该条记录失败！",'error');
         		}else{
         			$.messager.alert('提示信息',obj);
         		}
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
		url:'update_diseaseLogic_on.action?ids='+id,
		type:'post',
		success:function(data){
			var obj=data;
			if(obj=='success'){
				$.messager.alert('提示信息',"启用该条记录成功！");
				getgroupuserGrid();
			}else if(obj=='error'){
				$.messager.alert('提示信息',"启用该条记录失败！",'error');
			}else{
				$.messager.alert('提示信息',obj);
			}
		},
		error:function(){
			$.messager.alert('提示信息','操作失败！','error');
		}
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
			title:'修改疾病自动生成逻辑设置',
			center:'center',
			href:'updateDiseaseLogicPage.action?id='+id+'&ran='+ Math.random()
		});
		$("#dlg-custedit").dialog('open');
}
//-------------------------------------新增报疾病逻辑修改疾病逻辑----------------------------

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
		$.messager.alert('提示信息','请选择要删除的疾病逻辑!')
		return;
	}
	$.messager.confirm('提示信息','是否确定删除疾病逻辑',function(r){
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
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增疾病自动生成逻辑设置',
			center:'center',
			href : 'addDiseaseLogicPage.action'
		});
		$("#dlg-custedit").dialog('open');
	}
}, {
	text:'批量删除',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var ids = new Array();
    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	var hasActive = false;
	    $.each(checkedItems, function(index, item){
	    	if(item.isActive=='Y') {
	    		$.messager.alert('提示信息',"【" + item.logic_name + "】处于激活状态，不能删除！");
	    		hasActive = true;
	    		return;
	    	}
	        ids.push(item.id);
	    });
	    if(hasActive) {
    		return;
    	}
	    deluserrow(ids.toString());
    }
} ];