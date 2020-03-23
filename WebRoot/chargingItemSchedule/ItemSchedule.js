$(function(){
	var height = window.screen.height-260;  
	var datae = "";
	var strs=[];
	/**
	 * 科室下拉框
	 */
	$('#pq_dep_id').combobox({    
	    url:'getDepartment_dep.action',
	    valueField:'id',    
	    textField:'dep_name'
	});
	//strs=data.split(',');
	$('#genzongrili').calendar({
		styler: function(date){
			for(var i=0;i<strs.length;i++){
			if((date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate())==strs[i]){
				return 'background-color:#ccc';
			}
			}
		},
		onSelect: function(date){
			var dtime = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
			$("#itemscheduleList").datagrid('reload',{
				schedule_time:dtime,
				item_code:$('#pq_item_code').val(),	
				 item_name:$('#pq_item_name').val(),	
				 dep_id:$('#pq_dep_id').combobox('getValue'),}
			)
			
		}
	})
	datagridSchedule();
})
var obj = "";
 function SetWinHeightinn(obj) {
	 var height = window.screen.height-270;  
	 obj.height = height;
	} 
 function datagridSchedule(){
	     var xuaznahong = $('.calendar-selected').attr('abbr');
	     var tihuan = xuaznahong.replace(/,/g,'-');
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#itemscheduleList").datagrid({
		 url:'getItemScheduleList.action',
		 queryParams:{
			 schedule_time:tihuan,
			/* item_pinyin:$('#pinyin').val(),*/
			 item_code:$('#pq_item_code').val(),	//收费项目编码
			 item_name:$('#pq_item_name').val(),	//收费项目名称
			 dep_id:$('#pq_dep_id').combobox('getValue')
			 /*		 view_num:$('#view_num').val(),		//影像系统编码
			 exam_num:$('#exam_num').val(),		//检验系统编码
			 his_num:$("#his_num").val(),		//his系统关联码
			dep_id:$('#dep_id').combobox('getValue'),//所属科室	
			  interface_flag:$('#interface_flag').combobox('getValue'),//接口标识
			guide_category:$('#guide_category').combobox('getValue')//导引单分类	
*/	 	 },
	 	 type:'post',
	 	 toolbar: '#tb',
		 rownumbers:false,
	     pageSize:15,	     
	    // pageNumber:page,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	         	{field:'ck',checkbox:true },
	            {align:'center',field:'item_code',title:'收费项目编码',width:15},	
	            {align:'left',field:'item_name',title:'收费项目名称',width:15},
	            {align:'left',field:'dep_name',title:'科室',width:15},
			 	/*{align:'left',field:'d_name',title:'所属科室',width:15},
			 	{align:'left',field:'dname',title:'统计科室',width:15},*/
			 	/*{align:'left',field:'s_name',title:'所属样本',width:15},*/
			 /*	{align:'left',field:'report_name',title:'所属报告样本',width:20},*/
			/* 	{align:'center',field:'amount',title:'金额',width:15},*/
			 	/*{align:'left',field:'item_note',title:'项目描述',width:15},*/
			 /*	{align:'center',field:'his_num',title:'his关联码',width:15},
			 	{align:'center',field:'exam_num',title:'检验关联码',width:15},
			 	{align:'center',field:'view_num',title:'影像关联码',width:15},*/
			 	/*{align:'center',field:'f_name',title:'财务类别',width:10},*/
			 	{align:'center',field:'schedule_time',title:'日期',width:15},
			 	{align:'center',field:'schedule_number',title:'数量',width:15},
			 	{align:'center',field:'yuyue_num',title:'预约',width:15},
			 	{align:'center',field:'dengji_number',title:'登记',width:15},
			 	{align:'center',field:'p_xiugai',title:'修改',width:10,"formatter":p_xiugai},
			 	{align:'center',field:'ss',title:'删除',width:10,"formatter":p_sc}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	//singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
	    	singleSelect:false,
		    collapsible:false,
			pagination:true,//分页控件
		    fitColumns:true,//自适应
		    fit:true,
		    striped:true,
	        //singleSelect:true,//只允许选择一行
	        onDblClickRow:function(index, row){
	        	/*var row_id = $('#groupusershow').datagrid('getRows')[index].id;
	        	updateSampleDemo(row_id);*/
	        }
	});
}
 function p_xiugai(val,row){
	 
		return '<a href=\"javascript:p_xiugai_update(\''+row.id_s+'\',\''+row.schedule_time+'\',\''+row.schedule_number+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
function p_xiugai_update(id,schedule_time,schedule_number){
	$("#edit_dlg").dialog({
		title:'排期',
		modal: true,
		method:'post',
		href:'updateItemschdeulPage.action?id='+id+'&schedule_time=\''+schedule_time+'\'&schedule_number='+schedule_number,
	}); 
    $("#edit_dlg").dialog('open');
}
//------------------------------------删除收费项目----------------------------------
function p_sc(val, row) {
	return '<a href=\"javascript:p_f_sc(\''+ row.id_s+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 单个删除收费项目
 * @param id
 */
function p_f_sc(id) {
	$.messager.confirm("提示信息","是否删除记录",function(r){
		if(r){
			$.ajax({
				url : 'deleteItemschdeulPage.action?id='+id,
				type : 'post',
				success : function(data) {
					$.messager.alert('提示信息', data);
					$("#itemscheduleList").datagrid('reload')
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	})
}	
function paiqile(){
	var ids = new Array();
	var checkedItems = $('#itemscheduleList').datagrid('getChecked');
	if(checkedItems==""){
		$.messager.alert("提示信息","请选择项目！","error");
		return;
	}
    $.each(checkedItems, function(index, item){
        ids.push(item.id);
    }); 
	$("#edit_dlg").dialog({
		title:'排期',
		href:'getItemschdeulChajian.action?ids='+ids.toString(),
		modal: true,
	}); 
    $("#edit_dlg").dialog('open');
}