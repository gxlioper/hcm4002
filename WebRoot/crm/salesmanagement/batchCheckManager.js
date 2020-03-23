$(document).ready(function () { 
	
	$('#qiandanjihua').combobox({
		valueField: 'sign_num',
        textField: 'sign_name',
        hasDownArrow:true,
        height:23,
        width:240,
        mode:'remote',
        url:'getSignBillPlanByName.action?sign_type=all',
        onSelect:function(record){
        	$("#fanganchecklist").datagrid('load',{sign_num:$('#qiandanjihua').combobox('getValue'),check_status:$('#check_status_d').combobox('getValue'),check_type:$("#check_type_d").val()});
        }
	 });
	
	 var check_type = $("#check_type_d").val();
	 if(check_type == '1'){
		 toolbar = [{
			    text:'医生审核',
			    iconCls:'icon-add',
			    handler:function(){
			    	var data = $("#fanganchecklist").datagrid('getSelected');
			    	if(data == null){
			    		$.messager.alert('提示信息',"请先选择体检任务","error");
			    		return;
			    	}
			    	$("#dlg-show").dialog({
				 		title:'医生审核体检任务',
				 		href:'crmBatcheckeditPage.action?id='+data.id+'&company_id='+data.company_id+'&check_type='+1
				 	});
				 	$("#dlg-show").dialog('open');
			    }
			}];
	 }else if(check_type == 2){
			 toolbar = [{
				    text:'财务审核',
				    iconCls:'icon-add',
				    handler:function(){
				    	var data = $("#fanganchecklist").datagrid('getSelected');
				    	if(data == null){
				    		$.messager.alert('提示信息',"请先选择体检任务","error");
				    		return;
				    	}
				    	$("#dlg-show").dialog({
					 		title:'财务审核体检任务',
					 		href:'crmBatcheckeditPage.action?id='+data.id+'&company_id='+data.company_id+'&check_type='+2
					 	});
					 	$("#dlg-show").dialog('open');
				    }
				}];
	}else if(check_type == 3){
		 toolbar = [{
			    text:'上级部门审核',
			    iconCls:'icon-add',
			    handler:function(){
			    	var data = $("#fanganchecklist").datagrid('getSelected');
			    	if(data == null){
			    		$.messager.alert('提示信息',"请先选择体检任务","error");
			    		return;
			    	}
			    	$("#dlg-show").dialog({
				 		title:'上级部门审核体检任务',
				 		href:'crmBatcheckeditPage.action?id='+data.id+'&company_id='+data.company_id+'&check_type='+3
				 	});
				 	$("#dlg-show").dialog('open');
			    }
			}];
	}
	 
	
	getbatchcheckGrid();
	getgroupcheckGrid(0);
	gettccheckGrid(0);
	gettjxmcheckGrid(0);
});

 //---------------------------------------显示方案------------------------------------------------------
var toolbar = [];

function chaxun(){
	$("#fanganchecklist").datagrid('load',{sign_num:$('#qiandanjihua').combobox('getValue'),check_status:$('#check_status_d').combobox('getValue'),check_type:$("#check_type_d").val()});
}

 /**
  * 
  */
 function getbatchcheckGrid(){
		 var model={sign_num:$('#qiandanjihua').combobox('getValue'),check_status:$('#check_status_d').combobox('getValue'),check_type:$("#check_type_d").val()};
	     $("#fanganchecklist").datagrid({
		 url:'crmBatchCheckManageList.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'com_name',title:'签单计划名称',width:25},
		 	{align:'center',field:'batch_name',title:'体检任务名称',width:25},
		 	{align:'center',field:'data_name',title:'结算方式',width:15},
		 	{align:'center',field:'exam_number',title:'预计人数',width:10},
		 	{align:'center',field:'exam_fee',title:'预算金额',width:10},
		 	{align:'center',field:'contact_name',title:'联系人',width:10},
		 	{align:'center',field:'phone',title:'联系电话',width:15},
		 	{align:'center',field:'update_times',title:'修改时间',width:15},
		 	{align:'center',field:'cwcheckstatuss',title:'财务状态',width:10,"formatter":fun_cwchecks},
		 	{align:'center',field:'yscheckstatuss',title:'医生状态',width:10,"formatter":fun_yschecks},
		 	{align:'center',field:'sjcheckstatuss',title:'上级部门状态',width:15,"formatter":fun_sjchecks},
		 	{align:'center',field:'apptypes',title:'体检类型',width:15},
		 	{align:'center',field:'ck',title:'查看',width:15,"formatter":f_showcheck}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : true,
//			onDblClickRow : function(index, row) {
//				$("#groupchecklist").datagrid('reload',{"batch_id":row.id});
//				$("#examsetchecklist").datagrid('reload',{"group_id":0});
//				$("#chargitemchecklist").datagrid('reload',{"group_id":0});
//			},
			toolbar:toolbar
	});
	}
function fun_cwchecks(val,row){
	if(row.cwcheckstatuss == ''){
		return '不用审核'
	}
	return row.cwcheckstatuss;
} 

function fun_yschecks(val,row){
	if(row.yscheckstatuss == ''){
		return '不用审核'
	}
	return row.yscheckstatuss;
}
function fun_sjchecks(val,row){
	if(row.sjcheckstatuss == ''){
		return '不用审核'
	}
	return row.sjcheckstatuss;
}
//---------------------------------------显示方案------------------------------------------------------
 /**
  * 
  */
 var batch_id;
 function getgroupcheckGrid(batch_id){
		 var model={"batch_id":batch_id};
	     $("#groupchecklist").datagrid({
		 url:'grouplistshow.action',
		 dataType: 'json',
		 queryParams:model,
	//	 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'group_num',title:'分组编码',width:20},
		 	{align:'center',field:'group_name',title:'分组名称',width:25},
		 	{align:'center',field:'exam_indicators',title:'结算方式',width:15},
		 	{align:'center',field:'sex',title:'性别',width:15},
		 	{align:'center',field:'min_age',title:'最小年龄',width:15},
		 	{align:'center',field:'max_age',title:'最大年龄',width:15},
		 	{align:'center',field:'is_Marriage',title:'婚否',width:15},
		 	{align:'center',field:'posttion',title:'职位',width:15},
		 	{align:'center',field:'amount',title:'金额',width:15},
		 	{align:'center',field:'discount',title:'折扣率',width:15},		 	
		 	{align:'center',field:'group_index',title:'标志',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000,
	      //  toolbar:toolbar,
			onDblClickRow : function(index, row) {
//				gettccheckGrid(row.id);
//				gettjxmcheckGrid(row.id);
				$("#examsetchecklist").datagrid('reload',{"group_id":row.id});
				$("#chargitemchecklist").datagrid('reload',{"group_id":row.id});
			}
	});
	}
 
 
//----------------------------------------显示套餐-------------------------------------------------
 /**
  * 显示分组套餐信息
  */
 function gettccheckGrid(userid){
		 var model={"group_id":userid};
	     $("#examsetchecklist").datagrid({
		 url:'groupsetlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'set_num',title:'套餐编码',width:20},
		 	{align:'center',field:'set_name',title:'套餐名称',width:25},
		 	{align:'center',field:'sex',title:'适用性别',width:15},
		 	{align:'center',field:'set_discount',title:'套餐折扣率',width:15},
		 	{align:'center',field:'set_amount',title:'套餐金额',width:15}
		 	]],	    	
		 	singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000
	});
	}
//----------------------------------------显示套餐-------------------------------------------------
 /**
  * 显示体检项目套餐信息
  */
 function gettjxmcheckGrid(userid){
		 var model={"group_id":userid};
	     $("#chargitemchecklist").datagrid({
		 url:'groupchargitemlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	     columns : [ [ 
	       {align : 'center',field : 'item_code',title : '项目编码',width : 20},
	       {align : 'center',field : 'item_name',title : '项目名称',width : 25}, 
	       {align : 'center',field : 'dep_name',title : '科室',width : 25	}, 
	       {align : 'center',field : 'item_amount',title : '原金额',width : 20},
	       {align : 'center',field : 'discount',title : '折扣率',	width : 20},
	       {align : 'center',field : 'amount',title : '套餐金额',width : 20}
	     ] ],	    	
	     singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000
	});
	}
 
 /**
  * 显示一条
  * @param val
  * @param row
  * @returns {String}
  */
  function f_showcheck(val,row){	
	  return '<a href="javascript:f_batchcheckshow(\''+row.id+'\','+row.company_id+')">查看</a>|<a href="javascript:f_batchgroup('+row.id+')">查看分组</a>';
  }
  
 function f_batchgroup(id){
	$("#dlg-show_mx").dialog({
	 		title:'体检任务分组明细',
	});
	$("#groupchecklist").datagrid('reload',{"batch_id":id});
	$("#examsetchecklist").datagrid('reload',{"group_id":0});
	$("#chargitemchecklist").datagrid('reload',{"group_id":0});
	$("#dlg-show_mx").dialog('open');
 }
  
  var checknewWindow;  
  var checktimer;   
 function f_batchcheckshow(userid,comid){
	 	var url='/batchonecheckshow.action?id='+userid+'&company_id='+comid;
    	if(userid>0){
    		newWindow = window.open(url, "查看单独体检任务", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes")
    		newWindow.focus();
    	}else{
    	  $.messager.alert('提示信息',"请先选择体检任务","error");
    	}
	 }
