$(document).ready(function () { 
	$('#qiandan').combobox({
		valueField: 'sign_num',
        textField: 'sign_name',
        hasDownArrow:true,
        height:24,
        width:240,
        mode:'remote',
        url:'getSignBillPlanByName.action',
        onSelect:function(){
        	$('#batchs').combobox({
        		url : 'getBatchBySignNum.action?sign_num='+$('#qiandan').combobox('getValue'),
        		editable : false, //不可编辑状态
        		cache : false,
        		height:26,
        		width:210,
        		panelHeight : 'auto',//自动高度适合
        		valueField : 'id',
        		textField : 'batch_name',
                onSelect:function(){
                	getgroupGrid();
                }
        	 })
        }
	 })
	 getgroupGrid();
	gettcGrid(0);
	gettjxmGrid(0);
});


 //---------------------------------------显示方案------------------------------------------------------
 /**
  * 
  */
 function getgroupGrid(){
		 var model={"batch_id":$('#batchs').combobox('getValue')};
	     $("#grouplist").datagrid({
		 url:'crmgrouplistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'selectch',checkbox:true },
		 	{align:'center',field:'group_name',title:'分组名称',width:25},
		 	{align:'center',field:'exam_indicators',title:'结算方式',width:15},
		 	{align:'center',field:'sex',title:'性别',width:15},
		 	{align:'center',field:'min_age',title:'最小年龄',width:15},
		 	{align:'center',field:'max_age',title:'最大年龄',width:15},
		 	{align:'center',field:'is_Marriage',title:'婚否',width:15},
		 	{align:'center',field:'posttion',title:'职位',width:15},
		 	{align:'center',field:'amount',title:'金额',width:15},
		 	{align:'center',field:'discount',title:'折扣率',width:15},		 	
		 	{align:'center',field:'group_index',title:'其他',width:15},
		 	{align:'center',field:'ck',title:'查看',width:10,"formatter":f_show}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect : false,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000,
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
		text:'确定复制',
		iconCls:'icon-add',
		width:100,
	    handler:function(){
	    	var checkedItems = $('#grouplist').datagrid('getChecked');
    	    var ids = "";
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    });
    	    if(ids.length>1) ids=ids.substring(1,ids.length);
    	    docopygroup(ids);
	    }
	}];
 
 var ids;
 function docopygroup(ids){
	 if((ids=='')||(ids.length<=0)){
	 		$.messager.alert("操作提示", "请选择分组信息","error");
	 	}else{
		    $.messager.confirm('提示信息','是否确定要拷贝到当前批次任务？',function(r){
		 if(r){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url : 'copygroupdo.action',
				data : {
					    ids:ids,
					    batch_id:$("#newbatch_id").val()
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								$.messager.alert("操作提示", text);
								var _parentWin =  window.opener ; 
								_parentWin.getgroupGrid(); // 主窗口getgroupGrid();刷新  
								window.close();			
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
	 		href:'groupOneshow.action?id='+userid+'&batch_id='+$('#batchs').combobox('getValue')+'&company_id='+$("#company_id").val()
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