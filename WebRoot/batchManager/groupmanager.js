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
			//{align:"center",field:"isActive","title":"启(停)修改","width":18,"formatter":f_qt},
		 	{align:'center',field:'group_name',title:'分组名称',width:25},
		 	{align:'center',field:'exam_indicators',title:'结算方式',width:15},
		 	{align:'center',field:'sex',title:'性别',width:15},
		 	{align:'center',field:'type_name',title:'人员类型',width:15},
		 	{align:'center',field:'min_age',title:'最小年龄',width:15},
		 	{align:'center',field:'max_age',title:'最大年龄',width:15},
		 	{align:'center',field:'is_Marriage',title:'婚否',width:15},
		 	{align:'center',field:'posttion',title:'职位',width:15},
		 	{align:'center',field:'amount',title:'金额',width:15},
		 	{align:'center',field:'maximum_amount',title:'分组最大金额',width:20},
		 	{align:'center',field:'discount',title:'折扣率',width:15},		 	
		 	{align:'center',field:'group_index',title:'其他',width:15},			 	
		 	{align:"center",field:"xgddd",title:"修改",width:15,"formatter":f_xg},
		 	{align:'center',field:'ck',title:'查看',width:10,"formatter":f_show}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
					$("#grouplist").datagrid("hideColumn", "discount"); // 设置隐藏列
				}
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
 
 
//启停修改
 function f_qt(val,row){
  var html='';
     if(val=="N"){
       return '<a style="color:#f00;" href=\"javascript:f_startorblock(\''+row.id+'\',\'启用\')\">启用</a>';
     }else if(val=='Y') {
        return '<a style="color:#1CC112;" href=\"javascript:f_startorblock(\''+row.id+'\',\'停用\')\">停用</a>';
      }
 }


 //启（停）修改
 function f_startorblock(id,html){
 	$.messager.confirm('提示信息','是否确认'+html+'该分组项目？',function(r){
 		if(r){
 		$.ajax({
      	url:'updateDjtPanelStopOrStart.action?ids='+id,
      	type:'post',
      	success:function(data){
 			if(data.split("-")[0] == 'ok'){
 				$.messager.alert("提示信息",html+"该分组项目成功!");
 				$('#grouplist').datagrid('reload');
 			} else {
 				$.messager.alert("提示信息",data.split("-")[1],"error");
 			}
      	},
      	error:function(){
      		$.messager.alert('提示信息','操作失败！','error');
      			}
 			});
 		}
 	})
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
	    	var url='/groupedit.action?id=0&company_id='+$("#company_id").val()+'&batch_id='+$("#batch_id").val();
	    	if($("#batch_id").val()>0){
	    		newWindow = window.open(url, "新增分组", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	    		newWindow.focus();
	    		//timer = setInterval("updateAfterClose()", 2000);
	    	}else{
	    	  $.messager.alert('提示信息',"请先选择体检任务","error");
	    	}
	    }
	},{
		text:'复制分组',
		iconCls:'icon-add',
		width:100,
	    handler:function(){
	    	var url='/copygroupmanager.action?id=0&company_id='+$("#company_id").val()+'&batch_id='+$("#batch_id").val();
	    	if($("#batch_id").val()>0){
	    		newWindow = window.open(url, "复制分组", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	    		newWindow.focus();
	    		//timer = setInterval("updateAfterClose()", 2000);
	    	}else{
	    	  $.messager.alert('提示信息',"请先选择体检任务","error");
	    	}
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
		 	onLoadSuccess:function(value){
		 		var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
					$("#examsetlist").datagrid("hideColumn", "set_discount"); // 设置隐藏列
					$("#examsetlist").datagrid("hideColumn", "set_amount"); // 设置隐藏列
				}
	    	},
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
	     onLoadSuccess:function(value){
    		$("#datatotal").val(value.total);
    		var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
			if(is_show_discount==0){
				$("#chargitemlist").datagrid("hideColumn", "discount"); // 设置隐藏列
				$("#chargitemlist").datagrid("hideColumn", "amount"); // 设置隐藏列
			}
	    },
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
	 		href:'groupOneshow.action?id='+userid+'&batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val()
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
	 		href:'batchoneshow.action?id='+$("#batch_id").val()+'&company_id='+$("#company_id").val()
	 	});
	 	$("#dlg-show").dialog('open');
	 }
 
 function f_xguser(userid){
 		var iframeurl='groupedit.action?id='+userid+'&company_id='+$("#company_id").val()+'&batch_id='+$("#batch_id").val();
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