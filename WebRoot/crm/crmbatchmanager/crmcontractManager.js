$(document).ready(function () { 
	$('#qiandan').combobox({
		valueField: 'sign_num',
        textField: 'sign_name',
        hasDownArrow:true,
        height:26,
        width:200,
        mode:'remote',
        url:'getSignBillPlanByName.action?sign_type=all',
        onSelect:function(){
        	$("#contractlist").datagrid('reload',{"sign_num":$('#qiandan').combobox('getValue'),"check_type":$('#check_status_d').combobox('getValue')});
        }
	 })
	getgroupGrid();
});

function chaxun(){
	$("#contractlist").datagrid('reload',{"sign_num":$('#qiandan').combobox('getValue'),"check_type":$('#check_status_d').combobox('getValue')});
}

 //---------------------------------------显示方案------------------------------------------------------
 /**
  * 
  */
 function getgroupGrid(){
		 var model={"sign_num":$('#qiandan').combobox('getValue'),"check_type":$('#check_status_d').combobox('getValue')};
	     $("#contractlist").datagrid({
		 url:'crmcontractlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'contract_num',title:'合同编号',width:20},
		 	{align:'center',field:'company_name',title:'甲方单位',width:25},
		 	{align:'center',field:'batch_name',title:'所属体检任务',width:25},
		 	{align:'center',field:'linkman',title:'联系人',width:15},
		 	{align:'center',field:'tel',title:'联系电话',width:15},
		 	{align:'center',field:'validity_date',title:'有效期',width:15},
		 	{align:'center',field:'stypes',title:'状态',width:15},
		 	{align:'center',field:'ck',title:'查看',width:10,"formatter":f_show},
		 	{align:"center",field:"xgddd",title:"审核",width:10,"formatter":f_xg}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
	});
	}
 
/**
 * 修改
 * @param val
 * @param row
 * @returns {String}
 */
 function f_xg(val,row){	
 	return '<a href=\"javascript:f_shht(\''+row.contract_num+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"审核\" /></a>';
 }
  
 /**
  * 显示一条方案
  * @param val
  * @param row
  * @returns {String}
  */
  function f_show(val,row){	
	  return '<a href=\"javascript:f_contractshow(\''+row.contract_num+'\')\">查看</a>';
  }
  

  /**
   * 设置每隔1秒钟刷新父节点的表格
   */
  function updateAfterClose() {  
      //父窗口去检测子窗口是否关闭，然后通过自我刷新，而不是子窗口去刷新父窗口  
      if(checknewWindow.closed == true) {  
      clearInterval(checktimer);  
      getgroupGrid(); // 主窗口getgroupGrid();刷新  
      return;  
      }  
 } 
  
  var checknewWindow;  
  var checktimer;   
 function f_contractshow(userid){
 	 	var url='/crmcontractonecheckshow.action?contract_num='+userid;
    	if(userid!=''){
    		newWindow = window.open(url, "查看单独合同", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes")
    		newWindow.focus();
    	}else{
    	  $.messager.alert('提示信息',"请先选择合同","error");
    	}
 	 } 
  
 
 function f_shht(userid){
	 $("#dlg-show").dialog({
	 		title:'审核合同',
	 		href:'crmcontractcheckedit.action?contract_num='+userid
	 	});
	 	$("#dlg-show").dialog('open');
 }
 