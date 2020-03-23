$(document).ready(function () {
	if( $('#allot_dates').val()!=''){
		 $('#allot_date').datebox('setValue',$('#allot_dates').val())
	}
	getCrmMemberPrivateDoctorAssign();
	$('#arch_num').focus();
	
	$('#results_contrast').mouseleave(function(){
		 $('#results_contrast').css('display', 'none');
	});
});
function empty(){
	 $('#arch_num').val(""),
	 $('#user_name').val(''),
     $('#allot_date').datebox('setValue',''),
	 getCrmMemberPrivateDoctorList();
}
function getCrmMemberPrivateDoctorAssign(){
	var allot_date = "";
	 if($("#ck_allot_date")[0].checked){
		 allot_date =  $('#allot_date').datebox('getValue')
	 }
	 $("#CrmMemberPrivateDoctorAssignshow").datagrid({
		 url:'getCrmDoctorMemberList.action',
		 queryParams:{
			 arch_num:$('#arch_num').val(),
			 exam_num:$('#exam_num').val(),
			 user_name:$('#user_name').val(),
			 allot_date:allot_date,
			 flag:$('#flagsss').val()
		 },
		 toolbar:toolbar,
		 rownumbers:false,
		 fit:true,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {align:'center',field:'exam_num',title:tjhname,width:18},
		        {align:'center',field:'arch_num',title:dahname,width:15},
		        {align:'center',field:'user_name',title:'姓名',width:15},
		        {align:'center',field:'id_num',title:'身份证号',width:28},
	            {align:'center',field:'sex',title:'性别',width:10},
	            {align:'center',field:'age',title:'年龄',width:10},
	            {align:'center',field:'phone',title:'电话',width:18},
	            {align:'center',field:'cvp_count',title:'健康策略(条)',width:15,'formatter':f_show},
	            {align:'center',field:'cvr_count',title:'健康计划(条)',width:15},
	            {align:'center',field:'exam_types',title:'体检类型',width:15},
	            {align:'center',field:'join_date',title:'体检日期',width:18},
	            {align:'center',field:'set_name',title:'体检套餐',width:22},
	            {align:'center',field:'personal_pay',title:'体检金额',width:15},
	            {align:'center',field:'statuss',title:'体检状态',width:15},
			 	{align:'center',field:'allot_person_name',title:'分配人',width:15},
			 	{align:'center',field:'allot_date',title:'分配时间',width:18}
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		if($("#iszyb").val()==''){
					 $("div.datagrid-toolbar [id ='9']").eq(0).hide();  
				}
	    	},
	    	singleSelect:true,
   	        fitColumns:true,//自适应
			pagination:true,//分页控件
		    striped:true
	});
}
function f_show(val,row){
	return '<a href="javascript:void(0)" onclick="show_all(\''+row.exam_num+'\')"  onmouseover="showPlanTactics('+row.cvr_count+',\''+row.exam_num+'\',this)">'+row.cvp_count+'</a>';
}
function show_all(exam_num){
	window.parent.addPanel_other('健康回访计划','getCrmVisitPlanListPage.action?exam_num='+exam_num,'"+wx.getIcon_url()+"','1');	
}

function f_xg(val,row){
		return '<a href=\"javascript:f_xgrole(\''+row.visit_num+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"查看回访记录\" /></a>'; 
 }
function f_xgrole(visit_num){
	 $("#dlg-edit").dialog({
			title : '查看回访记录',
			width : 1000,
			height :450,
		});
		$("#dlg-edit").dialog('open');
		getCrmVisitRecordList(visit_num);
}


function getCrmVisitRecordList(visit_num){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $("#CrmVisitRecordshow").datagrid({
		 url:'getCrmVisitRecordList.action?model.visit_num='+ visit_num,
		 rownumbers:false,
		 height:510,
	     pageSize:5,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
			 	{align:'center',field:'id',title:'id',width:28,hidden:true},
		        {align:'center',field:'arch_num',title:dahname,width:18},
		        {align:'center',field:'name',title:'姓名',width:10},
	            {align:'center',field:'customer_feedback',title:'客户反馈信息',width:28},
			 	{align:'center',field:'health_suggest',title:'健康建议',width:28},
			 	{align:'center',field:'visit_type',title:'回访方式',width:28},
			 	{align:'center',field:'actual_doctor_name',title:'回访医生',width:15},
			 	{align:'center',field:'actual_date',width:10,title:'回访时间',width:28}
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:true,
    	    fitColumns:true,//自适应
			pagination:true,//分页控件
		    striped:true,
	       nowrap:false,//内容显示不下换行
	});
}
function showPlanTactics(c,e,ths){
	var height = window.screen.height-210 - $(ths).offset().top;
		if(height > 238){
			$("#results_contrast").css("top",$(ths).offset().top);
		}else{
			$("#results_contrast").css("top",$(ths).offset().top-(238-height));
		}
		$("#results_contrast").css("left","47%");
		$('#results_contrast').css('display','block');
		$("#examitem_div").datagrid({
			url:'getCrmVisitPlanList.action',
			dataType: 'json',
			queryParams:{'exam_num':e},
			rownumbers:false,
			width:800,
			height:250,
			columns:[[
//				 {align:'center',field:'visit_num',title:'健康计划编码',width:20},
		         {align:'center',field:'notices',title:'策略描述',width:18},
		         {align:'center',field:'tactics_type_s',title:'策略类型',width:18},
		         {align:'center',field:'visit_status',title:'回访状态',width:15},
		         {align:'center',field:'record_status',title:'计划状态',width:15,'formatter':f_record_status},
		         {align:'center',field:'username',title:'计划回访医生',width:15},
			 	 {align:'center',field:'plan_visit_date',title:'计划回访时间',width:20},
				 {align:'center',field:'visit_content',title:'回访内容',width:35},
				 {align:"center","field":"ck","title":"查看","width":10,"formatter":f_xg},
				 {align:"center","field":"del","title":"删除","width":10,"formatter":f_del}
			 	 
			]],
			onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		MergeCells('examitem_div', 'tactics_type_s,notices');
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
			collapsible:true,
			pagination: false,
			striped:true,
			nowrap:false,
			fitColumns:true
		});
}
function f_del(val,row){
	return '<a href=\"javascript:del_visit_record('+row.record_status+',\''+row.cvr_id+'\')\">删除</a>'; 
}
function  f_record_status(val,row){
	if(val =='1'){
		return '已完成';
	}else{
		return '<font color="red">未完成</font>';
	}
}
function del_visit_record(record_status,crvid){
	if(record_status == '1'){
		$.messager.alert("操作提示", "此次计划已完成，不能删除。。", "info");
		return;
	}
	$.messager.confirm('提示信息','确定删除此次计划吗？',function(r){
			 if(r){
				 $.ajax({
						url : 'delVisitRecordById.action',
						data : {cvr_id:crvid},
						type : "post",//数据发送方式   
						success : function(text) {
							if (text.split("-")[0] == 'ok') {
								getCrmMemberPrivateDoctorAssign();
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
		});
}
//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text:'新增健康计划',
	width:120,
	iconCls:'icon-add',
	handler:function(){
		var item = $('#CrmMemberPrivateDoctorAssignshow').datagrid('getSelected');
		if(item==null){
			$.messager.alert("操作提示","请选择体检者",'error');
		}else{
			
				var persionName = item.user_name;
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
			
	//	    $("#dlg-edit").dialog({
	//			title : '新增健康计划',
	//			width : 800,
	//			height :450,
	//			href : 'getCrmVisitPlanDoctorPage.action?flag=1'
	//		});
	//		$("#dlg-edit").dialog('open');
		}
}
},{
	id:8,
	text:'普通报告预览',
	width:150,
	iconCls:'icon-print',
    handler:function(){
    	var item = $('#CrmMemberPrivateDoctorAssignshow').datagrid('getSelected');
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
	id:9,
	text:'职业病报告预览',
	width:150,
	iconCls:'icon-print',
    handler:function(){
    	var item = $('#CrmMemberPrivateDoctorAssignshow').datagrid('getSelected');
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
    	var item = $('#CrmMemberPrivateDoctorAssignshow').datagrid('getSelected');
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