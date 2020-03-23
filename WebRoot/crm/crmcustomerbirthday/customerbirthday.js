$(document).ready(function () {
	if($("#bir_dates").val()!=''){
		$("#bir_date").datebox('setValue',$('#bir_dates').val());
	}
	getCrmShengrikehuList();
});

/**
 * 清空查询
 */
function empty(){
	 $('#arch_num').val(""),
	 $('#user_name').val(''),
	 $("#bir_date").datebox('setValue',$('#bir_dates').val());
	 getCrmShengrikehuList()
}
function getCrmShengrikehuList(){
	 $("#CrmShengrikehuList").datagrid({
		 url:'getShengrikehuList.action',
		 queryParams:{
			 arch_num:$('#arch_num').val(),
			 name:$('#user_name').val(),
			 plan_visit_date:$('#bir_date').datebox('getValue')
		 },
		 toolbar:toolbar,
		 rownumbers:false,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {align:'center',field:'arch_num',title:dahname,width:15},
		        {align:'center',field:'user_name',title:'姓名',width:18},
		        {align:'center',field:'sex',title:'性别',width:25},
		        {align:'center',field:'id_num',title:'身份证',width:15},
	            {align:'center',field:'birthday',title:'出生日期',width:25},
	            {align:'center',field:'phone',title:'电话',width:25},
	            
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
