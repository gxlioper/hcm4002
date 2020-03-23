$(document).ready(function () {
	getCrmVisitLostList();
});

/**
 * 清空查询
 */
function empty(){
	 $('#arch_num').val(""),
	 $('#user_name').val(''),
	 getCrmVisitLostList()
}
function getCrmVisitLostList(){
	 $("#CrmVisitLostshow").datagrid({
		 url:'getCrmVisitLostList.action',
		 queryParams:{
			 arch_num:$('#arch_num').val(),
			 user_name:$('#user_name').val(),
			 create_time:$('#create_timelist').val(),
		 },
		 toolbar:toolbar,
		 rownumbers:false,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {align:'center',field:'arch_num',title:dahname,width:15},
		        {align:'center',field:'user_name',title:'姓名',width:18},
		        {align:'center',field:'chi_name',title:'创建医生',width:15},
	            {align:'center',field:'create_time',title:'创建时间',width:25},
			 	/*{align:'center',field:'flag',title:'处理结果',width:25}*/
	            ]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
    	    fitColumns:true,//自适应
	    	//singleSelect:true,
		    //collapsible:false,
			pagination:true,//分页控件
		    striped:true,
		    
	});
}
