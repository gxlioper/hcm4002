$(document).ready(function () {
	
	$('#exam_no').css('ime-mode','disabled');
	$('#exam_no').focus();
	
	$('#message_name').combobox({
		url : 'queryAllMessageName.action',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		textField : 'config_remark',
		valueField : 'config_key',
		onLoadSuccess : function(data) {
			if (data.length>0) {
				$('#message_name').combobox('setValue', data[0].config_remark);
			}
		}
	});
	
	//初始化信息
	getgroupuserGrid();
	
	//初始化日志明细信息
	//getLogDetailDrid("");
});


/**
 * 定义工具栏
 */
var toolbar=[
	{
		text:'导出日志',
		width:90,
		iconCls:'icon-print',
	    handler:function(){
	    	var ids="";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		    	ids=ids+","+(item.id);
		    });
		    if(ids.length>1) ids=ids.substring(1,ids.length);
		    if((ids=="")||(ids.split(',').length!=1)){
	    		$.messager.alert("操作提示", "请选择一条信息导出","error");
	    	}else{
	    		window.location.href = 'expTxtContentFile.action?id='+ids;
	    	}  
	    	
	    }
	},{
		text:'批量删除',
		width:90,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids="";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		    	ids=ids+","+(item.id);
		    });
		    if(ids.length>1) ids=ids.substring(1,ids.length);
		    if(ids==""){
	    		$.messager.alert("操作提示", "请选择要删除的日志","error");
	    	}else{
	    		$.messager.confirm('提示信息','确认删除选中日志？',function(r){
		   			 if(r){
		   				$.ajax({
							url : 'deleteLogMaterAndDetail.action',
							data : {id:ids},
							type : "post",//数据发送方式   
							success : function(text) {
								if (text.split("-")[0] == ' ok') {
									//刷新主表
									getgroupuserGrid();
									$.messager.alert("操作提示", text);
								} else {
									$.messager.alert("操作提示", text, "error");
								}
							},
							error : function() {
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						});
		   			 }
	    		})
	    	}
		    
	    }
	}
];

function getgroupuserGrid(){
	  var chk_value ="";    
	  $('input[name = chkItem]:checked').each(function(){    
	   chk_value=chk_value+","+($(this).val());    
	  }); 
	  if(chk_value.length>1){
		  chk_value=chk_value.substring(1,chk_value.length);
	  }
	  //alert($("#message_name").textbox('getValue'));
     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
		 var model={
				 "req_no":$("#req_no").textbox('getValue'),
				 "exam_no":$("#exam_no").textbox('getValue'),
				 "message_name":$("#message_name").textbox('getValue'),
				 //"message_type":$('#message_type').combobox('getValue'),
				 "flag":$('#flag').combobox('getValue'),
				 "message_startDate":$("#message_startDate").datebox('getValue'),	
				 "message_endDate":$("#message_endDate").datebox('getValue'),
				 "sender":$("#sender").textbox('getValue'),
				 "receiver":$("#receiver").textbox('getValue'),
				 "chkItem":chk_value
		 };
	     $("#groupusershow").datagrid({
		 url:'queryThirdInterfaceLog.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 pageNumber:1,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100,300,500,1000],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true },
		        {align:'center',field:'message_name',title:'接口名称',width:140},	
		        {align:'center',field:'id',title:'日志ID',width:100,sortable:true,hidden:true},
		        {align:'center',field:'req_no',title:'申请号',width:100,sortable:true},
			    {align:'center',field:'exam_no',title:'体检编号',width:100,sortable:true},
			 	{align:'center',field:'message_id',title:'消息标识',width:100,sortable:true},		 	
			 	{align:'center',field:'message_date',title:'消息时间',width:160,sortable:true},
			 	{align:'center',field:'message_type',title:'消息类型',width:100},
			 	{align:'center',field:'sender',title:'发送者',width:70},
			 	{align:'center',field:'receiver',title:'接收者',width:70,sortable:true},
			 	{align:'center',field:'flag',title:'消息处理状态',width:120,sortable:true,"formatter":f_flag},
			 	{align:'center',field:'message_inout',title:'调用方式',width:100,sortable:true,"formatter":f_message_inout},
			 	{align:'center',field:'xtgnb_id',title:'系统模块编号',width:100,sortable:true}	 	
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$(".loading_div").remove();
	    	},
	    	onDblClickRow : function(index, row) {	 
	    		var url= 'viewLogDetailPage.action?id='+row.id;
	    		newWindow = window.open(url, "日志详情", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	    		newWindow.focus();
	    	},
			rowStyler:function(index,row){    
		       if(row.flag==2){
		        	return 'color:#ca0c16;';
		        }    
		    },
			height: window.screen.height-400,
			nowrap:false,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
			fitColumns:true,
			fit:false,
		    striped:true,
		    remoteSort:false,
	        toolbar:toolbar	       
	});
}

function f_message_name(val,row){
	return '<a href=\"javascript:getLogDetailDrid(\''+row.id+'\')\">'+val+'</a>';
}

function f_flag(val,row){
	if(val==0) return "正常";
	if(val==1) return "拒绝";
	if(val==2) return "错误";
}

function f_message_inout(val,row){
	if(val==0) return "主动请求";
	if(val==1) return "被动调用";
}


/**
 * 详情日志Toobar
 */
var toolbarLog=[
	{
		text:'导出日志',
		width:90,
		iconCls:'icon-print',
	    handler:function(){
	    	var ids="";
	    	var checkedItems = $('#messageLogShow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		    	ids=ids+","+(item.id);
		    });
		    if(ids.length>1) ids=ids.substring(1,ids.length);
		    if(ids==""){
	    		$.messager.alert("操作提示", "请选择至少一条信息","error");
	    	}else{
	    		window.location.href = 'expTxtDetailContentFile.action?id='+ids;
	    	}  
	    	
	    }
	},{
		text:'批量删除',
		width:90,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids="";
	    	var checkedItems = $('#messageLogShow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		    	ids=ids+","+(item.id);
		    });
		    if(ids.length>1) ids=ids.substring(1,ids.length);
		    if(ids==""){
	    		$.messager.alert("操作提示", "请选择要删除的日志","error");
	    	}else{
	    		$.messager.confirm('提示信息','确认删除选中日志？',function(r){
		   			 if(r){
		   				$.ajax({
							url : 'deleteDetailLogMsg.action',
							data : {id:ids},
							type : "post",//数据发送方式   
							success : function(text) {
								if (text.split("-")[0] == ' ok') {
									if($("#click_id").val()!=0){
										getLogDetailDrid($("#click_id").val());
									}
									$.messager.alert("操作提示", text);
								} else {
									$.messager.alert("操作提示", text, "error");
								}
							},
							error : function() {
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						});
		   			 }
	    		})
	    	}
		    
	    }
	}
];

function getLogDetailDrid(id){
	$("#click_id").val(id);
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
		 var model={
			"til_id":id,
		 };
	     $("#messageLogShow").datagrid({
		 url:'getLogDetailDrid.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 pageNumber:1,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100,300,500,1000],//可以设置每页记录条数的列表 
		 columns:[[
			    {field:'ck',checkbox:true },
		        {align:'center',field:'id',title:'日志ID',width:100,sortable:true,hidden:true},
		        {align:'center',field:'til_id',title:'主表ID',width:100,sortable:true,hidden:true},
			    {align:'center',field:'ldate',title:'日志记录时间',width:160,sortable:true},
		        {align:'center',field:'lmessage',title:'日志内容',width:170,sortable:true}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$(".loading_div").remove();
	    	},
	    	onDblClickRow : function(index, row) {
	    		//查看信息
	    		$("#til_viewDetailLogDetail").dialog({
	    			//maximizable:true,
			 		title:'第三方通信日志详情',
			 		href:'viewDetailLogQuery.action?id='+row.id
			 	});
			 	$("#til_viewDetailLogDetail").dialog('open');
			},
			height: window.screen.height-400,
			nowrap:false,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
			fitColumns:false,
			fit:false,
		    striped:true,
	        toolbar:toolbarLog	       
	});
}

/**
 *  导出 日志详情信息
 * @param id
 * @returns
 */
function expTxtDetailContent(id){
	window.location.href = 'expTxtDetailContentFile.action?id='+id;
}