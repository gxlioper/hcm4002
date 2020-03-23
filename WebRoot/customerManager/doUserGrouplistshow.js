$(document).ready(function() {
	getgroupGrid();			
	});

function getgroupGrid(){
	 var model={"batch_id":$("#addbatch_id").val()};
    $("#usergrouplist").datagrid({
	 url:'grouplistshow.action',
	 dataType: 'json',
	 queryParams:model,
	 toolbar:'#toolbar',
	 rownumbers:true,
    pageSize: 5,//每页显示的记录条数，默认为10 
    pageList:[5],//可以设置每页记录条数的列表 
	 columns:[[
        {align:'center',field:'id',title:'分组ID',width:20},
	 	{align:'center',field:'group_name',title:'分组名称',width:25},
	 	{align:'center',field:'exam_indicators',title:'结算方式',width:15},
	 	{align:'center',field:'sex',title:'性别',width:15},
	 	{align:'center',field:'min_age',title:'最小年龄',width:15},
	 	{align:'center',field:'max_age',title:'最大年龄',width:15},
	 	{align:'center',field:'is_Marriage',title:'婚否',width:15},
	 	{align:'center',field:'posttion',title:'职位',width:15},
	 	{align:'center',field:'amount',title:'金额',width:15},
	 	{align:'center',field:'discount',title:'折扣率',width:15},		 	
	 	{align:'center',field:'group_index',title:'标志',width:15},			 	
	 	{align:'center',field:'ck',title:'强制分组',width:15,"formatter":f_doqzgroup}
	 	]],	    	
   	onLoadSuccess:function(value){
   		$("#datatotal").val(value.total);
   		var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
		if(is_show_discount==0){
			$("#usergrouplist").datagrid("hideColumn", "discount"); // 设置隐藏列
		}
   	},
   	rowStyler: function(index,row){
		if (row.status == 'D'){
			return 'color:red;';
		}
	},
   	    singleSelect : true,
		collapsible : true,
		pagination : true,
		fitColumns : true,
		autowidth : true,
		striped : true,
		pagination : true
});
}

function f_doqzgroup(val,row){	
 	if(row.status == 'D'){
		return '';
	}else{
		return '<a href=\"javascript:f_doqzgroupdo(\''+row.id+'\')\">选择分组</a>';
	}
 }
	/**
	 * 保存修改
	 */
	function f_doqzgroupdo(rowid) {
	$.messager.confirm('提示信息','是否确定对所选人员强制分组？',function(r){
	  if(r){
		  var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url:'doUserGroupuser.action',
				data:{
					    company_id : $("#company_id").val(),
					    batch_id:$("#addbatch_id").val(),
			            ids:$("#fzids").val(),
			            group_id:rowid
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {								
								getgroupuserGrid();
								$('#dlg-custshow').dialog('close');
								$.messager.alert("操作提示", text);							
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

