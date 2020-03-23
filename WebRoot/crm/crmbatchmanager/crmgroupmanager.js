$(document).ready(function () { 	
	getgroupGrid();
	gettcGrid(0);
	gettjxmGrid(0);
});
 //---------------------------------------显示方案------------------------------------------------------
 /**
  * 
  */
 function getgroupGrid(){
		 var model={"batch_id":$("#batch_id").val()};
	     $("#grouplist").datagrid({
		 url:'grouplistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:true,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15],//可以设置每页记录条数的列表 
		 columns:[[
            {align:"center",field:"sc",title:"删除",width:10,"formatter":f_sc},
		 	{align:'center',field:'group_name',title:'分组名称',width:25},
		 	{align:'center',field:'exam_indicators',title:'结算方式',width:15},
		 	{align:'center',field:'sex',title:'性别',width:15},
		 	{align:'center',field:'type_name',title:'人员类型',width:15},
		 	{align:'center',field:'min_age',title:'最小年龄',width:15},
		 	{align:'center',field:'max_age',title:'最大年龄',width:15},
		 	{align:'center',field:'is_Marriage',title:'婚否',width:15},
		 	{align:'center',field:'posttion',title:'职位',width:15},
		 	{align:'center',field:'amount',title:'金额',width:15},
		 	{align:'center',field:'discount',title:'折扣率',width:15},		 	
		 	{align:'center',field:'group_index',title:'其他',width:15},			 	
		 	{align:"center",field:"xgddd",title:"修改",width:15,"formatter":f_xg},
		 	{align:'center',field:'ck',title:'查看',width:10,"formatter":f_show}
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
	        toolbar:toolbar,
			onDblClickRow : function(index, row) {
				gettcGrid(row.id);
				gettjxmGrid(row.id);
			}
	});
	}
 
 var newWindow;  
 var timer; 
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'新增分组',
		iconCls:'icon-add',
		width:100,
	    handler:function(){
	    	$.ajax({
	    		url : 'checkBatchStatus.action',
	    		type : 'post',
	    		data : {
	    			batch_id:$("#batch_id").val()
	    		},
	    		success : function(data) {
	    			if(data=="未提交"){
	    				var url='/crmgroupedit.action?id=0&sign_num='+$('#sign_numss').val()+'&batch_id='+$("#batch_id").val()+"&apptype="+$("#apptypess").val();
	    		    	if($("#batch_id").val()>0){
	    		    		newWindow = window.open(url, "新增分组", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	    		    		newWindow.focus();
	    		    		//timer = setInterval("updateAfterClose()", 2000);
	    		    	}else{
	    		    	  $.messager.alert('提示信息',"请先选择体检任务","error");
	    		    	}
	    			}else{
	    				 $.messager.alert('提示信息',"该体检任务已提交审核，不能进行新增分组","error");
	    			}
	    		},
	    		error : function() {
	    			$.messager.alert('提示信息', '操作失败！', 'error');
	    		}
	    	});
	    }
	},{
		text:'复制分组',
		iconCls:'icon-add',
		width:100,
	    handler:function(){
	    	$.ajax({
	    		url : 'checkBatchStatus.action',
	    		type : 'post',
	    		data : {
	    			batch_id:$("#batch_id").val()
	    		},
	    		success : function(data) {
	    			if(data=="未提交"){
	    				var url='/crmcopygroupmanager.action?id=0&sign_num='+$('#sign_numss').val()+'&batch_id='+$("#batch_id").val();
	    		    	if($("#batch_id").val()>0){
	    		    		newWindow = window.open(url, "复制分组", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	    		    		newWindow.focus();
	    		    		//timer = setInterval("updateAfterClose()", 2000);
	    		    	}else{
	    		    	  $.messager.alert('提示信息',"请先选择体检任务","error");
	    		    	}
	    			}else{
	    				 $.messager.alert('提示信息',"该体检任务已提交审核，不能进行复制分组","error");
	    			}
	    		},
	    		error : function() {
	    			$.messager.alert('提示信息', '操作失败！', 'error');
	    		}
	    	});
	    }
	}];
 
 /**
  * 设置每隔2秒钟刷新父节点的表格
  */
 function updateAfterClose() {  
     //父窗口去检测子窗口是否关闭，然后通过自我刷新，而不是子窗口去刷新父窗口  
     if(newWindow.closed == true) {  
     clearInterval(timer);  
     getgroupGrid(); // 主窗口getgroupGrid();刷新  
     return;  
     }  
}  
 //----------------------------------------显示套餐-------------------------------------------------
 /**
  * 显示分组套餐信息
  */
 function gettcGrid(userid){
		 var model={"group_id":userid};
	     $("#examsetlist").datagrid({
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
 function gettjxmGrid(userid){
		 var model={"group_id":userid};
	     $("#chargitemlist").datagrid({
		 url:'groupchargitemlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	     columns : [[ 
	       {align : 'center',field : 'item_code',title : '项目编码',width : 20},
	       {align : 'center',field : 'item_name',title : '项目名称',width : 25}, 
	       {align : 'center',field : 'dep_name',title : '科室',width : 25	}, 
	       {align : 'center',field : 'item_amount',title : '原金额',width : 20},
	       {align : 'center',field : 'discount',title : '折扣率',	width : 20},
	       {align : 'center',field : 'itemnum',title : '数量',	width : 20},
	       {align : 'center',field : 'amount',title : '套餐金额',width : 20,editor : {type : 'text'}}
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
 
 
/**
 * 修改
 * @param val
 * @param row
 * @returns {String}
 */
 function f_xg(val,row){	
 	return '<a href=\"javascript:f_xguser(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
 }
 
 
 /**
  * 显示一条方案
  * @param val
  * @param row
  * @returns {String}
  */
  function f_show(val,row){	
	  return '<a href=\"javascript:f_groupshow(\''+row.id+'\')\">查看</a>';
  }
  
  /**
   * 显示分组套餐和体检项目
   * @param val
   * @param row
   * @returns {String}
   */
   function f_showtctjxm(val,row){	
 	  return '<a href=\"javascript:f_tctjxmshow(\''+row.id+'\')\">查看</a>';
   }
  /**
   * 操作datagrad
   * @param userid
   */
   function f_tctjxmshow(userid){
	   
   }

 /**
  * 删除
  * @param val
  * @param row
  * @returns {String}
  */
 function f_sc(val,row){
 	return '<a href=\"javascript:f_deluser(\''+row.id+'\')\">删除</a>';
 }
 
 function f_groupshow(userid){	 
	 $("#dlg-groupshow").dialog({
	 		title:'单独查询分组信息',
	 		href:'crmgroupOneshow.action?id='+userid+'&batch_id='+$("#batch_id").val()+'&sign_num='+$('#sign_numss').val()
	 	});
	 	$("#dlg-groupshow").dialog('open');
 }
 
 /**
  * 显示一条方案
  * @param userid
  */
 function f_batchshow(){
	 	$("#dlg-show").dialog({
	 		title:'单独查询体检任务',
	 		href:'crmbatchoneshow.action?id='+$("#batch_id").val()+'&sign_num='+$('#sign_numss').val()
	 	});
	 	$("#dlg-show").dialog('open');
	 }
 
 function f_xguser(userid){
 		var iframeurl='crmgroupedit.action?id='+userid+'&sign_num='+$('#sign_numss').val()+'&batch_id='+$("#batch_id").val()+"&apptype="+$("#apptypess").val();
 		newWindow=window.open(iframeurl, "新增分组", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
 		newWindow.focus();
 		timer = setInterval("updateAfterClose()", 1000);
 }
 
 function f_deluser(userid)
 {
 $.messager.confirm('提示信息','是否确定删除此分组？',function(r){
 	if(r){
 		$.ajax({
 		url:'groupdelete.action?id='+userid+'&batch_id='+$("#batch_id").val(),
 		type:'post',
 		success:function(text){
 			if(text.split("-")[0]=='ok'){
         		  $.messager.alert("操作提示", text);
         		  $("#dlg-edit").dialog("close");
         		  getgroupGrid();
         	  }else if(text.split("-")[0]=='error'){
         		  $.messager.alert("操作提示", text,"error");
         	  }
 		},
 		error:function(){
 			$.messager.alert('提示信息','操作失败！','error');
 		}
 		});
 	}
 });
 }