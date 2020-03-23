$(document).ready(function () {
	gettermaccountlistGrid();
	
});

	 /**
	  * 
	  */
function gettermaccountlistGrid() {
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
			+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);

	var model = {
		"acc_num" : $("#acc_num").val()
	};
	$("#teamaccountwaylist").datagrid({
		url : 'teamAccountExamwayListShow.action',
		dataType : 'json',
		queryParams : model,
		toolbar : '#toolbar',
		rownumbers : true,
		showFooter:true,
		pageSize : 100000,// 每页显示的记录条数，默认为10
		pageList : [ 10, 20, 45, 60, 75 ],// 可以设置每页记录条数的列表
		columns : [ [ 
		              {align : 'center',field : 'acc_num',title : '结算单号',	width : 20}, 
		              {align : 'center',field : 'paywayname',title : '支付方式',width : 25}, 
		              {align : 'center',field : 'charges',title : '支付金额',width : 15}, 
		              {align : 'center',field : 'in_date',title : '生成日期',width : 25}, 
		              {align : 'center',field : 'ck',title : '删除',	width : 10,"formatter" : f_del} 
		              ] ],
		onLoadSuccess : function(value) {
			//$("#datatotal").val(value.total);
			
			$(".loading_div").remove();
		},
		singleSelect : false,
		collapsible : true,
		pagination : false,
		fitColumns : true,
		striped : true,
		toolbar : toolbar
	});
}
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'新增结算方式',
		width:140,
		iconCls:'icon-save',
	    handler:function(){
    		$("#dlg-edit").dialog({
		 		title:'新增结算方式',
		 		href:'teamaccountwayadd.action?acc_num='+$("#batch_id").val()+'&batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val()
		 	});
		 	$("#dlg-edit").dialog('open');
	    }
	}];
 
 
 function windowclosesss(){
	 var _parentWin =  window.opener ; 
		_parentWin.getTeamAccountForBatch();
		window.close();
 }
 
 
 /**
  * 显示一条方案
  * @param val
  * @param row
  * @returns {String}
  */
  function f_del(val,row){	
	  return '<a href=\"javascript:f_teamexamwaydell('+row.id+')\">删除</a>';
  }
  

//结账
  var rowacc_num;
function f_teamexamwaydell(rowacc_id)
{
	$.messager.confirm('提示信息','是否确定此支付方式？',function(r){
		if(r){
			$.ajax({
			url:'teamaccountwaydel.action?id='+rowacc_id+'&batchid='+$("#batch_id").val(),
			type:'post',
			success:function(text){
				if(text.split("-")[0]=='ok'){
	        		  $.messager.alert("操作提示", text);
	        		  gettermaccountlistGrid();
	        	  }else if(text.split("-")[0]=='error'){
	        		  $.messager.alert("操作提示", text,"error");
	        	  }
			},
			error:function(){
				$.messager.alert('提示信息','操作失败！','error');
			}
			});
		}
	});
}
  
  var examaccnum,examnumss;
  function f_teamexaminfo(examaccnum,examnumss){
	  var url='termexamitemlistshow.action?exam_num='+examnumss+'&acc_num='+examaccnum;
	  newWindow = window.open(url, "体检人员结算明细", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	  newWindow.focus();
  }
  
  /**
   * 显示一条方案
   * @param userid
   */
  function f_batchshow(){
 	 	$("#dlg-show").dialog({
 	 		title:'单独查询体检任务',
 	 		href:'batchoneshow.action?id='+$("#batch_id").val()+'&company_id='+$("#company_id").val()
 	 	});
 	 	$("#dlg-show").dialog('open');
 	 }
  
 
 function updateteamaccountdo(){
	 if ($("#invoice_no").val()=='') {
		 $.messager.alert("操作提示", "发票号无效","error");		
	 }else if ($("#invoice_name").val()=='') {
		 $.messager.alert("操作提示", "发票抬头无效","error");		
	 }else{
	 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
		$.ajax({
			url : 'updateteamaccountdo.action',
			data : {
				        company_id : $("#company_id").val(),
						acc_num : $("#acc_num").val(),							
						batchid :$("#batch_id").val(),	
						invoice_no :$("#invoice_no").val(),
						invoice_name:$("#invoice_name").val(),							
						linker :$("#linker").val(),	
						phone:$("#phone").val()
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							$('#dlg-edit').dialog('close');
							$.messager.alert("操作提示", text);
							gettermaccountlistGrid();
							var _parentWin =  window.opener ; 
							_parentWin.getTeamAccountForBatch();
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
 }
 