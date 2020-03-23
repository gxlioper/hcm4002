$(document).ready(function () {
	 $('#visit_status').combobox({
		 	data:[{
		 		id:'1',value:'计划回访'
		 	},{
		 		id:'2',value:'开始回访'
		 	},{
		 		id:'3',value:'回放结束'
		 	}],
		    valueField:'id',    
		    textField:'value',
		    prompt:'请选择计划回访状态'
	 });
	if( $('#flag').val()=='1'){
		getCrmVisitPlanList();
	}else if( $('#flag').val()=='0'){
		getCrmAllVisitPlanList();
	}
	$('#arch_num').focus();
	$('#exam_num').css('ime-mode','disabled');
	$('#exam_num').focus();
});
function queryplanorallplan(){
	if( $('#flag').val()=='1'){
		 getCrmVisitPlanList();
	 }else if( $('#flag').val()=='0'){
		 getCrmAllVisitPlanList();
	 }
}
/**
 * 清空查询
 */
function empty(){
	 $('#arch_num').val(""),
	 $('#name').val(''),
	 $('#startTime').datebox('setValue',''),
	 $('#endTime').datebox('setValue',''),
	 $('#visit_status').combobox('setValue','')
	 if( $('#flag').val()=='1'){
		 getCrmVisitPlanList();
	 }else if( $('#flag').val()=='0'){
		 getCrmAllVisitPlanList();
	 }
}
function f_xg(val,row){
		return '<a href=\"javascript:f_xgrole(\''+row.visit_num+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"查看回访记录\" /></a>'; 
 }
function f_xgrole(visit_num){
	 $("#dlg-edit").dialog({
			title : '查看回访记录',
			width : 1000,
			height :450,
			href : 'getCrmVisitRecordListPage.action?visit_num='+visit_num
		});
		$("#dlg-edit").dialog('open');
}
/**
 * 显示健康计划表
 */
function getCrmAllVisitPlanList(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $("#CrmAllVisitPlanshow").datagrid({
		 url:'getCrmAllVisitPlanList.action',
		 queryParams:{
			 arch_num:$('#arch_num').val(),
			 exam_num:$("#exam_num").val(),
			 name:$('#name').val(),
			 startTime:$('#startTime').datebox('getValue'),
			 endTime:$('#endTime').datebox('getValue'),
			 visit_status:$('#visit_status').combobox('getValue')
		 },
		 toolbar:toolbarAll,
		 rownumbers:false,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {align:'center',field:'arch_num',title:dahname,width:15},
		        {align:'center',field:'exam_num',title:tjhname,width:18},
		        {align:'center',field:'personname',title:'姓名',width:15},
	            {align:'center',field:'username',title:'计划回访医生',width:25},
			 	{align:'center',field:'plan_visit_date',title:'计划回访时间',width:25},
			 	{align:'center',field:'visit_num',title:'健康计划编码',width:20},
			 	{align:'center',field:'visit_important',title:'重要级别',width:15},
				{align:'center',field:'visit_content',title:'回访内容',width:35},
			 	{align:'center',field:'visit_status',title:'回访状态',width:15},
			 	{align:'center',field:'sname',width:10,title:'创建人',width:15},
			 	{align:'center',field:'create_time',width:10,title:'创建时间',width:28},
			 	{align:"center","field":"ck","title":"查看","width":10,"formatter":f_xg}
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
    	    fitColumns:true,//自适应
	    	//singleSelect:true,
		    //collapsible:false,
			pagination:true,//分页控件
		    striped:true
	});
}
function getCrmVisitPlanList(){
	 $("#CrmVisitPlanshow").datagrid({
		 url:'getCrmVisitPlanList.action',
		 queryParams:{
			 arch_num:$('#arch_num').val(),
			 exam_num:$("#exam_num").val(),
			 name:$('#name').val(),
			 startTime:$('#startTime').datebox('getValue'),
			 endTime:$('#endTime').datebox('getValue'),
			 visit_status:$('#visit_status').combobox('getValue')
		 },
		 toolbar:toolbar,
		 rownumbers:false,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {align:'center',field:'arch_num',title:dahname,width:15},
		        {align:'center',field:'exam_num',title:tjhname,width:18},
		        {align:'center',field:'personname',title:'姓名',width:15},
	            {align:'center',field:'username',title:'计划回访医生',width:25},
			 	{align:'center',field:'plan_visit_date',title:'计划回访时间',width:25},
			 	{align:'center',field:'visit_num',title:'健康计划编码',width:20},
			 	{align:'center',field:'visit_important',title:'重要级别',width:15},
				{align:'center',field:'visit_content',title:'回访内容',width:35},
			 	{align:'center',field:'visit_status',title:'回访状态',width:15},
			 	{align:'center',field:'sname',width:10,title:'创建人',width:15},
			 	{align:'center',field:'create_time',width:10,title:'创建时间',width:28},
			 	{align:"center","field":"ck","title":"查看","width":10,"formatter":f_xg}
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
    	    fitColumns:true,//自适应
	    	//singleSelect:true,
		    //collapsible:false,
			pagination:true,//分页控件
		    striped:true,
		    onDblClickRow:function(index, row){
	        	var row = $('#CrmVisitPlanshow').datagrid('getRows')[index];
	        	if(row.visit_status=='结束回访'){
	        		$.messager.alert('提示信息','该健康计划已结束回访!','error')
	        	}else{
	        	$("#dlg-edit").dialog({
					title : '新建回访记录',
					width : 800,
					height :450,
					href : 'addCrmVisitRecordPage.action'
				});
				$("#dlg-edit").dialog('open');
	        }
		    }
	});
}

function deluserrow(id,status){
		if(status=='计划回访'){
			$.messager.confirm('提示信息','是否确定删除选中健康计划',function(r){
			 	if(r){
			 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
					 $.ajax({
						 url : 'deleteCrmVisitPlan.action',
						data : {ids:id},
						type : "post",
						success : function(data) {
							$(".loading_div").remove();
							$.messager.alert("操作提示",data);
							$('#dlg-edit').dialog('close')
							getCrmVisitPlanList();
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert('提示信息', '操作失败！', 'error');
						}
					 });
			 	 }
			 });
		}else{
			$.messager.alert('提示信息', '选中的健康计划不能删除','error');
			return;
		}
}
function updateplanrow(ids,status){
	if(status=='开始回访'){
	$.messager.confirm('提示信息','是否确定结束回访选中的健康计划',function(r){
	 	if(r){
	 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
				 url : 'updateCrmVisitPlanEndStatus.action',
				data : {ids:ids},
				type : "post",
				success : function(data) {
					$(".loading_div").remove();
					$.messager.alert("操作提示",data);
					$('#dlg-edit').dialog('close')
					getCrmVisitPlanList();
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			 });
	 	 }
	 });
	}else{
		$.messager.alert('提示信息', '选中的健康计划不能被结束回访','error');
		return;
	}
}
//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text:'删除',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var status;
    	var id;
    	var item = $('#CrmVisitPlanshow').datagrid('getSelected');
    	if(item==null){
    		$.messager.alert("操作提示","请选中要删除的健康计划",'error');
    	}else{
    	status =item.visit_status;
    	id=item.id;
    	deluserrow(id,status);
    	}
    }
},{
	text : '编辑健康计划',
	iconCls : 'icon-edit',
	width : 120,
	handler :  function() {
		var row=$('#CrmVisitPlanshow').datagrid('getSelected');
		if(row==null){
			$.messager.alert("操作提示","请选择健康计划",'error');
		}else{
			if(row.visit_status!='计划回访'){
				$.messager.alert("操作提示","此健康计划不能编辑",'error');
				return;
			}else{
			$("#dlg-edit").dialog({
				title : '编辑健康计划',
				width : 800,
				height :450,
				href : 'getUpdateCrmVisitPlanPage.action?id='+row.id
			});
			$("#dlg-edit").dialog('open');
			}
	}
	}
},{
	text : '结束回访',
	iconCls : 'icon-edit',
	width : 120,
	handler :  function() {
		var ids ;
		var status;
    	var checkedItems = $('#CrmVisitPlanshow').datagrid('getSelected');
	   if(checkedItems==null){
		   $.messager.alert("操作提示","请选择结束回访的健康计划",'error');
		   return;
	   }else{
		   ids=checkedItems.id;
		   status=checkedItems.visit_status;
		   updateplanrow(ids,status);   
	   }
	}
},{
	text:'新增健康计划',
	width:120,
	iconCls:'icon-add',
	handler:function(){
	var item = $('#CrmVisitPlanshow').datagrid('getSelected');
	if(item!=null){
		 $("#dlg-edit").dialog({
				title : '新增健康计划',
				width : 800,
				height :450,
				href : 'getCrmVisitPlanDoctorPage.action?flag=2'
			});
			$("#dlg-edit").dialog('open');
	}else{
		$.messager.alert("操作提示","请选择需要新建的健康计划",'error');
	}
}
},{
	text:'报告预览',
	width:120,
	iconCls:'icon-print',
    handler:function(){
    	var item = $('#CrmVisitPlanshow').datagrid('getSelected');
    	if(item!=null){
    		printreport(item.exam_num);
   	}else{
   		$.messager.alert("操作提示", "请选择一个体检人员","error");
   	}    
    }
},{
	text:'检查结果',
	width:120,
	iconCls:'icon-print',
    handler:function(){
    	var item = $('#CrmVisitPlanshow').datagrid('getSelected');
    	if(item!=null){
   		 $("#dlg-edit").dialog({
   				title : '检查结果',
   				width : 800,
   				height :450,
   				href : 'getNewVisitPlanPageResult.action?exam_num='+item.exam_num+'&arch_num='+item.arch_num
   			});
   			$("#dlg-edit").dialog('open');
   	}else{
   		$.messager.alert("操作提示","请选择要查看结果的记录",'error');
   	}   
    }
}];
var toolbarAll=[
	{
		text:'报告预览',
		width:120,
		iconCls:'icon-print',
	    handler:function(){
	    	var item = $('#CrmAllVisitPlanshow').datagrid('getSelected');
	    	if(item!=null){
	    		printreport(item.exam_num);
	   	}else{
	   		$.messager.alert("操作提示", "请选择一个体检人员","error");
	   	}    
	    }
	},{
		text:'检查结果',
		width:120,
		iconCls:'icon-print',
	    handler:function(){
	    	var item = $('#CrmAllVisitPlanshow').datagrid('getSelected');
	    	if(item!=null){
	   		 $("#dlg-edit").dialog({
	   				title : '检查结果',
	   				width : 800,
	   				height :450,
	   				href : 'getNewVisitPlanPageResult.action?exam_num='+item.exam_num+'&arch_num='+item.arch_num
	   			});
	   			$("#dlg-edit").dialog('open');
	   	}else{
	   		$.messager.alert("操作提示","请选择要查看结果的记录",'error');
	   	}   
	    }
	}
];
function printreport(barids){
	   var exeurl="reportServices://&0&"+barids+"&0";
	   location.href=exeurl;
}