$(document).ready(function () {
	getCrmVisitRecordList();
	//回车事件
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	getCrmVisitRecordList();
	     }
	}
	
});
/**
 * 清空查询
 */
function empty(){
	 $('#startTime').datebox('setValue',''),
	 $('#endTime').datebox('setValue','')
	 getCrmVisitRecordList();
}
/**
 * 显示健康计划表
 */
function getCrmVisitRecordList(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $("#CrmVisitRecordshow").datagrid({
		 url:'getCrmVisitRecordList.action',
		 queryParams:{
			 startTime:$('#startTime').datebox('getValue'),
			 endTime:$('#endTime').datebox('getValue'),
			 visit_date:$('#visit_datelist').val(),
			 type:1
		 },
		 rownumbers:false,
		 height:510,
	     pageSize:5,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
			 	{align:'center',field:'id',title:'id',width:28,hidden:true},
		        {align:'center',field:'arch_num',title:"档案号",width:18},
		        {align:'center',field:'name',title:'姓名',width:10},
	            {align:'center',field:'customer_feedback',title:'客户反馈信息',width:28},
			 	{align:'center',field:'health_suggest',title:'健康建议',width:28},
			 	{align:'center',field:'visit_type',title:'回访方式',width:28},
			 	{align:'center',field:'doctorname',title:'回访医生',width:15},
			 	{align:'center',field:'visit_date',width:10,title:'回访时间',width:28},
			 	{align:'center',field:'visit_num',width:10,title:'健康计划编码',width:18}
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
		    striped:true,
		    onDblClickRow:function(index, row){
	        	var row_id = $('#CrmVisitRecordshow').datagrid('getRows')[index].id;
	        	updateCRM_VisitRecordPage(row_id);
	        },
	       
	       nowrap:false,//内容显示不下换行
	});
}


