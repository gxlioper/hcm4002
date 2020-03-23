$(document).ready(function () {
	$('#visitdoctor').combobox({
			url : 'getVisitDoctorList.action',
			cache : false,
			height:26,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'username',
			prompt:'请选择回访医生',
			onLoadSuccess : function(data) {
				$('#visitdoctor').combobox('setValue',$("#user_id").val());
			}
		});
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
	 $('#listimportant').combobox({
			url : 'getDatadis.action?com_Type=JHZYJB',
			cache : false,
			height:26,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			prompt:'请选择重要级别'
		});
	
	 if($('#visitimports').val()!=''){
			$('#listimportant').combobox('setValue',$('#visitimports').val());
		}
	 if($('#visit_statuss').val()!=''){
			$('#visit_status').combobox('setValue',$('#visit_statuss').val());
		}

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

function query(){
	
}
/**
 * 清空查询
 */
function empty(){
	 $('#arch_num').val(""),
	 $('#name').val(''),
	 $('#startTime').datebox('setValue',''),
	 $('#endTime').datebox('setValue',''),
	 $('#visit_status').combobox('setValue',''),
	 $("#listimportant").combobox('setValue','')
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
			 visit_status:$('#visit_status').combobox('getValue'),
			visit_important:$("#listimportant").combobox('getValue'),
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
			 	{align:'center',field:'phone',title:'电话',width:20},
			 	{align:'center',field:'personal_pay',title:'体检金额(元)',width:20},
			 	{align:'center',field:'visit_important',title:'重要级别',width:15},
				{align:'center',field:'visit_content',title:'回访内容',width:35},
			 	{align:'center',field:'visit_status',title:'回访状态',width:15},
			 	{align:'center',field:'record_status',title:'计划状态',width:15,'formatter':f_record_status},
			 	{align:'center',field:'sname',width:10,title:'创建人',width:15},
			 	{align:'center',field:'create_time',width:10,title:'创建时间',width:28},
			 	{align:"center","field":"ck","title":"查看","width":10,"formatter":f_xg}
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		if($("#iszyb").val()==''){
					 $("div.datagrid-toolbar [id ='9']").eq(0).hide();  
				}
	    	},
	    	rowStyler:function(index,row){    
	    		if(row.visit_status == '计划回访'){
	    			return 'color:#f00;';
	    		}
	    		if(row.visit_status == '开始回访'){
	    			return 'color:#008000;';
	    		}	  
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
			 visit_status:$('#visit_status').combobox('getValue'),
			 visit_important:$("#listimportant").combobox('getValue'),
//			 plan_visit_date:$("#plan_visit_datelist").val(),
			 fujianflag:$("#fujianflaglist").val(),
			 plan_doctor_id:$('#visitdoctor').combobox('getValue')
		 },
		 toolbar:toolbar,
		 rownumbers:false,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {align:'center',field:'visit_num',title:'健康计划编码',width:20},
		        {align:'center',field:'tactics_type_s',title:'策略类型',width:18},
		        {align:'center',field:'notices',title:'策略说明',width:18},
//		        {align:'center',field:'rmark',title:'rmark',width:18}, 
//		        {align:'center',field:'arch_num',title:dahname,width:15},
		        {align:'center',field:'exam_num',title:tjhname,width:18},
		        {align:'center',field:'personname',title:'姓名',width:15},
		        {align:'center',field:'phone',title:'电话',width:25},
		        {align:'center',field:'personal_pay',title:'体检金额(元)',width:20},
	            {align:'center',field:'username',title:'计划回访医生',width:25},
			 	{align:'center',field:'plan_visit_date',title:'计划回访时间',width:25},
			 	{align:'center',field:'visit_important',title:'重要级别',width:15},
				{align:'center',field:'visit_content',title:'回访内容',width:35},
			 	{align:'center',field:'visit_status',title:'回访状态',width:15},
			 	{align:'center',field:'record_status',title:'计划状态',width:15,'formatter':f_record_status},
			 	{align:'center',field:'sname',width:10,title:'创建人',width:15},
			 	{align:'center',field:'create_time',width:10,title:'创建时间',width:28},
			 	{align:"center","field":"ck","title":"查看","width":10,"formatter":f_xg}
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		MergeCells('CrmVisitPlanshow', 'visit_num,tactics_type_s,notices');
	    	},
	    	rowStyler:function(index,row){    
	    		if(row.visit_status == '计划回访' ){
	    			return 'color:#f00;';
	    		}
	    		if(row.visit_status == '开始回访' ){
	    			return 'color:#008000;';
	    		}	  
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
	        	if($("#user_id").val() != row.plan_doctor_id){
	        		$.messager.alert('提示信息','对不起，你不是此次计划的回访医生，不能继续回访。。','info')
	        		return;
	        	}
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
function  f_record_status(val,row){
	if(val =='1'){
		return '已完成';
	}else{
		return '<font color="red">未完成</font>';
	}
}
function deluserrow(id,status){
		if(status=='0'){
			$.messager.confirm('提示信息','是否确定删除选中健康计划',function(r){
			 	if(r){
			 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
					 $.ajax({
						 url : 'delVisitRecordById.action',
						data : {cvr_id:id},
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
					data : {ids:ids,visit_status:"3"},
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
function qvxiaohuifang(ids,status){
	$.messager.confirm('提示信息','是否确定取消结束回访选中的健康计划',function(r){
	 	if(r){
	 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
				 url : 'updateCrmVisitPlanEndStatus.action',
				data : {ids:ids,visit_status:"2"},
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
}
//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text:'删除',
	width:80,
	iconCls:'icon-cancel',
    handler:function(){
    	
    	var status;
    	var id;
    	var item = $('#CrmVisitPlanshow').datagrid('getSelected');
    	if(item==null){
    		$.messager.alert("操作提示","请选中要删除的健康计划",'error');
    	}else{
			if($("#user_id").val() != item.plan_doctor_id){
	    		$.messager.alert('提示信息','对不起，你不是此次计划的回访医生，操作不能继续。。','info')
	    		return;
	    	}
	    	status =item.record_status;
	    	id=item.cvr_id;
	    	deluserrow(id,status);
    	}
    }
},{
	text : '编辑健康计划',
	iconCls : 'icon-edit',
	width : 120,
	handler :  function() {
		var row=$('#CrmVisitPlanshow').datagrid('getSelected');
		if($("#user_id").val() != row.plan_doctor_id){
    		$.messager.alert('提示信息','对不起，你不是此次计划的回访医生，不能编辑。。','info')
    		return;
        }
		if(row==null){
			$.messager.alert("操作提示","请选择健康计划",'error');
		}else{
			if(row.visit_status =='结束回访'){
				$.messager.alert("操作提示","此健康计划不能编辑",'error');
				return;
			}else{
				$("#dlg-edit").dialog({
					title : '编辑健康计划',
					width : 800,
					height :480,
					href : 'getUpdateCrmVisitPlanPage.action?id='+row.id+'&cvr_id='+row.cvr_id
				});
				$("#dlg-edit").dialog('open');
			}
	}
	}
},{
	text : '结束回访',
	iconCls : 'icon-edit',
	width : 100,
	handler :  function() {
		var ids ;
		var status;
    	var checkedItems = $('#CrmVisitPlanshow').datagrid('getSelected');
    	
	   if(checkedItems==null){
		   $.messager.alert("操作提示","请选择结束回访的健康计划",'error');
		   return;
	   }else{
		   	if($("#user_id").val() != checkedItems.plan_doctor_id){
	    		$.messager.alert('提示信息','对不起，你不是此次计划的回访医生，操作不能继续。。','info')
	    		return;
	        }
		   ids=checkedItems.id;
		   status=checkedItems.visit_status;
		   updateplanrow(ids,status);   
	   }
	}
},{
	text : '取消结束回访',
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
		   	if($("#user_id").val() != checkedItems.plan_doctor_id){
		    		$.messager.alert('提示信息','对不起，你不是此次计划的回访医生，操作不能继续。。','info')
		    		return;
		        }
		   ids=checkedItems.id;
		   status=checkedItems.visit_status;
		   if(status != '结束回访'){
			   $.messager.alert("操作提示","未结束回访，不能取消结束回访!",'error');
			   return;
		   }else{
			   qvxiaohuifang(ids,status);
		   }
	   }
	}
},{
	text:'新增健康计划',
	width:120,
	iconCls:'icon-add',
	handler:function(){
		var item = $('#CrmVisitPlanshow').datagrid('getSelected');
		if(item!=null){
			var persionName = item.personname;
			var arch_num = item.arch_num;
			var exam_num = item.exam_num;
			
			$("#dlg-edit").dialog({
				title:'编辑回访策略',
				width : 800,
				height :630,
				href : 'getCrmVisitPlanDoctorPage.action?flag=4&arch_num='+arch_num+'&exam_num='+exam_num+'&persionName='+persionName
			});
			$("#dlg-edit").dialog("open");
			$("#dlg-edit").dialog('center');
			
			
//			 $("#dlg-edit").dialog({
//					title : '新增健康计划',
//					width : 800,
//					height :450,
//					href : 'getCrmVisitPlanDoctorPage.action?flag=2'
//				});
//				$("#dlg-edit").dialog('open');
		}else{
			$.messager.alert("操作提示","请选择需要新建的健康计划",'error');
		}
	}
},{
	text:'报告预览',
	width:100,
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
	width:100,
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
		text:'普通报告预览',
		width:150,
		iconCls:'icon-print',
	    handler:function(){
	    	var item = $('#CrmAllVisitPlanshow').datagrid('getSelected');
	    	if(item!=null){
	    		if($("#report_print_type").val() == '5'){
	    			//预览普通报告
	    			var exeurl="reportServices://&0&"+item.exam_num+"&Y&0";//0代表体检号Y代表预览，2代表职业病
	    			location.href=exeurl;
	    	    }else{
	    	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-REPORT_PRINT_TYPE","error");
	    	    }
		   	}else{
		   		$.messager.alert("操作提示", "请选择一个体检人员","error");
		   	}    
	    }
	},{
		id=9,
		text:'职业病报告预览',
		width:150,
		iconCls:'icon-print',
	    handler:function(){
	    	var item = $('#CrmAllVisitPlanshow').datagrid('getSelected');
	    	if(item!=null){
	    		if($("#zyb_report_print_type").val() == '5'){
	    			//预览职业病报告
	    			var exeurl="reportServices://&0&"+item.exam_num+"&Y&2";//0代表体检号Y代表预览，2代表职业病
	    			location.href=exeurl;
	    	    }else{
	    	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-ZYB_REPORT_PRINT_TYPE","error");
	    	    }
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
function printreport5(id,exam_num,type){
	if(type == 1){//预览普通报告
		var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+exam_num;
		location.href=exeurl;
	}else if(type == '2'){//预览职业病报告
		var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&opreport&"+exam_num;
		location.href=exeurl;
	}
}
//合并单元格
function MergeCells(tableID, fldList) {
            var Arr = fldList.split(",");
             var dg = $('#' + tableID);
            var fldName;
             var RowCount = dg.datagrid("getRows").length;
            var span;
            var PerValue = "";
            var CurValue = "";
             var length = Arr.length - 1;
             for (i = length; i >= 0; i--) {
               fldName = Arr[i];
                PerValue = "";
                span = 1;
                for (row = 0; row <= RowCount; row++) {
                    if (row == RowCount) {
                        CurValue = "";
                    }
                    else {
                        CurValue = dg.datagrid("getRows")[row][fldName];
                    }
                     if (PerValue == CurValue) {
                        span += 1;
                    }
                     else {
                        var index = row - span;
                         dg.datagrid('mergeCells', {
                            index: index,
                             field: fldName,
                             rowspan: span,
                             colspan: null
                         });
                         span = 1;
                         PerValue = CurValue;
                     }
                 }
             }
         }