$(document).ready(function () {
	getgroupuserGrid();		
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	chaxun();
	    	
	     }
	}
	setchebox();
});
function getmoban(){
	   $('#template_id').combobox({
			valueField:'id',    
			textField:'sms_name',
		   panelHeight:'auto',
		   onSelect:function(r){
				$('#sms_note').val(r.sms_note)
				$('#sms_note').focus();
			}
			
		})
			$.ajax({
			url:'queryCrmSmsBaseTemplate.action?page=1&pageSize=10000',
			type:'post',
			success:function(data){
				var da = eval('('+data+')');
				$('#template_id').combobox('loadData',da.rows);
			}
			
		})
	}
function chaxun(){
	var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").val();
	 }
	 var review_title = undefined;
	 if($("#ck_review_title")[0].checked){
		 review_title =  $("#ser_review_title").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datebox('getValue');
		 time2 = $("#end_date").datebox('getValue');
	 }
	 var time3 = undefined,time4 = undefined;
	 if($("#ck_fcdate")[0].checked){
		 time3 = $("#start_fcdate").datebox('getValue');
		 time4 = $("#end_fcdate").datebox('getValue');
	 }
	 var review_status = undefined;
	 if($("#ck_status")[0].checked){
		 review_status = $('#re_status').combobox('getValue');
	 }
	 var model={
			 "exam_num":exam_num,
			 "time1":time1,	
			 "time2":time2,
			 "time3":time3,
			 "time4":time4,
			 "review_status":review_status,
			 "review_title":review_title
	};
	$("#finalfuhca_list").datagrid('load',model);
}
function getgroupuserGrid(){
	 var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").val();
	 }
	 var review_title = undefined;
	 if($("#ck_review_title")[0].checked){
		 review_title =  $("#ser_review_title").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datebox('getValue');
		 time2 = $("#end_date").datebox('getValue');
	 }
	 var time3 = undefined,time4 = undefined;
	 if($("#ck_fcdate")[0].checked){
		 time3 = $("#start_fcdate").datebox('getValue');
		 time4 = $("#end_fcdate").datebox('getValue');
	 }
	 var review_status = undefined;
	 if($("#ck_status")[0].checked){
		 review_status = $('#re_status').combobox('getValue');
	 }
	 var model={
			 "exam_num":exam_num,
			 "time1":time1,	
			 "time2":time2,
			 "time3":time3,
			 "time4":time4,
			 "review_status":review_status,
			 "review_title":review_title
		 };
	     $("#finalfuhca_list").datagrid({
		 url:'getExamSummaryReviewList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'exam_num',title:tjhname,width:15,sortable:true},
		    {align:'center',field:'arch_num',title:dahname,width:15,sortable:true},
		 	{align:'center',field:'user_name',title:'姓名',width:15,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'birthday',title:'生日',width:15,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:15,sortable:true},
		 	{align:'center',field:'company',title:'单位',width:20,sortable:true},
		 	{align:'center',field:'join_date',title:'体检日期',width:15,sortable:true},
		 	{align:'center',field:'review_title',title:'复查主题',width:35,sortable:true},
		 	{align:'center',field:'review_date',title:'复查日期',width:15,sortable:true},
		 	{align:'center',field:'review_statuss',title:'复查状态',width:15},
		 	{align:'center',field:'review_user',title:'设定医生',width:10,sortable:true},
		 	{align:'center',field:'review_time',title:'设定时间',width:15,sortable:true},
		 	{align:'center',field:'notice_user',title:'通知人',width:15,sortable:true},
		 	{align:'center',field:'notice_time',title:'通知时间',width:15,sortable:true},
		 	{align:'center',field:'notice_types',title:'通知方式',width:10}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
			height: window.screen.height-350,
			nowrap:true,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
		    toolbar:toolbar,
		    rowStyler:function(rowIndex,rowData){
		    	if(rowData.review_status == '2'){
		    		return 'color:blue';
		    	}else if(rowData.review_status == '3'){
		    		return 'color:red;';
		    	}
		    }
	});
}
function neirong(){
	$('#neirong').datagrid({
		url:'getDatadis.action?com_Type=DXMBNR',
		columns:[[
			    {align:'center',field:'name',title:'',width:10},	
			 	]],
		 	  fitColumns:true,
		    striped:true,
		    fit:false,
		    onDblClickRow:function(index, row){
		    	butt(row);
	        }
	})
}
function butt(row){
	var neirong = $('#sms_note').val();
	var data = neirong+row.remark;
	$('#sms_note').val(data)
	$('#sms_note').focus();
}
/**
 * 定义工具栏
 */
var toolbar=[{
		text:'通知复查',
		width:90,
		iconCls:'icon-save',
	    handler:function(){
	    	var objs = $("#finalfuhca_list").datagrid('getChecked');
	    	if(objs == null || objs.length == 0){
	    		$.messager.alert("操作提示","请选择需要通知复查的总检复查信息!", "error");
	    	}else{
	    		for(var i in objs) {
	    			if(objs[i].review_status == '2'){
		    			$.messager.alert("操作提示","【"+objs[i].user_name+"】总检复查信息已通知，不能继续通知!", "error");
		    			return;
		    		}else if(objs[i].review_status == '3'){
		    			$.messager.alert("操作提示","【"+objs[i].user_name+"】总检复查信息已作废，不能通知!", "error");
		    			return;
		    		}else if(objs[i].review_status == '4'){
		    			$.messager.alert("操作提示","【"+objs[i].user_name+"】总检复查信息已取消，不能通知!", "error");
		    			return;
		    		}
	    		}
    			$("#dlg-xz").dialog("open");
    			$("#dlg-xz").dialog("center");
    			getmoban();
    			neirong();
	    	}
	    }
	},{
		text:'作废复查',
		width:90,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var objs = $("#finalfuhca_list").datagrid('getChecked');
	    	if(objs == null || objs.length == 0){
	    		$.messager.alert("操作提示","请选择需要作废复查的总检复查信息!", "error");
	    	}else{
	    		for(var i in objs) {
	    			if(objs[i].review_status == '2'){
		    			$.messager.alert("操作提示","【"+objs[i].user_name+"】的总检复查信息已通知，不能作废!", "error");
		    			return;
		    		}else if(objs[i].review_status == '3'){
		    			$.messager.alert("操作提示","【"+objs[i].user_name+"】的总检复查信息已作废，不能继续作废!", "error");
		    			return;
		    		}else if(objs[i].review_status == '4'){
		    			$.messager.alert("操作提示","【"+objs[i].user_name+"】的总检复查信息已取消，不能作废!", "error");
		    			return;
		    		}
	    		}

    			var rows = $("#finalfuhca_list").datagrid('getChecked');
    			var li = JSON.stringify(rows);
    			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    			$("body").prepend(str);
    			$.ajax({
    				url:'updateExamSummaryReview.action',
    				data:{
    					'li':li.toString(),
//    					'id':obj.id,
    					'review_status':3
    				},
    				type:'post',
    				success:function(data){
    					$(".loading_div").remove();
    					if(data.split('-')[0] == 'ok'){
    						$.messager.alert("操作提示",data.split('-')[1], "info");
    						$("#finalfuhca_list").datagrid('reload');
    					}else{
    						$.messager.alert("操作提示",data.split('-')[1], "error");
    					}
    				}
    			});
	    	}
	    }
	}];

function quedingxuanze(){
	
	var rows = $("#finalfuhca_list").datagrid('getChecked');
//	var li = "{";
//	var examinfo_id = new Array();
	var li = JSON.stringify(rows);
//	li = li.substring(0,li.length-1)+"}";
//	var obj = $("#finalfuhca_list").datagrid('getSelected');
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'updateExamSummaryReview.action',
		data:{
			'li':li.toString(),
//			'id':obj.id,
			'review_status':2,
			'notice_type':$("input[name='no_type']:checked").val(),
			'sms_note':$('#sms_note').val(),
//			'arch_num':row.arch_num
		},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			if(data.split('-')[0] == 'ok'){
				$.messager.alert("操作提示",data.split('-')[1], "info");
				$("#finalfuhca_list").datagrid('reload');
				$("#dlg-xz").dialog("close");
			}else{
				$.messager.alert("操作提示",data.split('-')[1], "error");
			}
		},error:function(){
			$(".loading_div").remove();
			$.messager.alert("操作提示","操作失败", "error");
		}
	});
}
function qh(id){
	if($(id).val()==1){
		$("#dx").css('display','block');
	} else {
		$("#dx").css('display','none');
	}
	
}


/**
 ****搜索添加输入框根据是否为空自动判断chebox选中
 */
 function setchebox(inp){
 	$('#exam_num').textbox({  
 	    onChange: function(value) {
 	    	if(value==""){
 	    		$('.ck_exam_num').attr('checked',false);
 	    	} else {
 	    		$('.ck_exam_num').attr('checked',true);
 	    	}
 	    }
 	});
 	$('#ser_review_title').textbox({  
 	    onChange: function(value) {
 	    	if(value==""){
 	    		$('#ck_review_title').attr('checked',false);
 	    	} else {
 	    		$('#ck_review_title').attr('checked',true);
 	    	}
 	    }
 	});
 }