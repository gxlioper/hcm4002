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
	$("#teamAccountExamShow").datagrid({
		url : 'teamAccountExamListShow.action',
		dataType : 'json',
		queryParams : model,
		toolbar : '#toolbar',
		rownumbers : true,
		pageSize : 500,// 每页显示的记录条数，默认为10
		pageList : [ 200,300,500 ],// 可以设置每页记录条数的列表
		columns : [ [ {field:'checkss',checkbox:true },
		              {align : 'center',field : 'acc_num',title : '结算单号',	width : 20}, 
		              {align : 'center',field : 'exam_num',title : '体检编号',width : 25}, 
		              {align : 'center',field : 'id_num',title : '身份证号',width : 40}, 
		              {align : 'center',field : 'user_name',title : '姓名',width : 25}, 
		              {align : 'center',field : 'sex',title : '性别',width : 10}, 
		              {align : 'center',field : 'age',title : '年龄',width : 10}, 
		              {align : 'center',field : 'phone',title : '电话',width : 25}, 
		              {align : 'center',field : 'isPrePays',title : '是否预结算',	width : 15}, 
		              {align : 'center',field : 'isnotPays',title : '是否含弃检',	width : 15}, 
		              {align : 'center',field : 'ck',title : '查看',	width : 10,"formatter" : f_show} 
		              ] ],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			getteamtotalinfo();
			$(".loading_div").remove();
		},
		singleSelect:true,
	    collapsible:true,
		pagination: true,
	    fitColumns:true,
	    striped:true,
		toolbar : toolbar
	});
}
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'打印结算单',
		width:140,
		iconCls:'icon-print',
	    handler:function(){
	    	var barids="";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        barids=barids+","+(item.exam_num);
    	    });
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    if((barids=="")||(barids.split(',').length!=1)){
	    		$.messager.alert("操作提示", "请选择一个体检人员","error");
	    	}else{
	    		printreport(barids);
	    	}
    	    
	    }
	},{
		text:'导出结算单',
		width:140,
		iconCls:'icon-check',
	    handler:function(){
	    	var barids="";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        barids=barids+","+(item.exam_num);
    	    });
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    if((barids=="")||(barids.split(',').length!=1)){
	    		$.messager.alert("操作提示", "请选择一个体检人员","error");
	    	}else{
	    		printreport(barids);
	    	}
    	    
	    }
	}];
 
 
 /**
  * 显示一条方案
  * @param val
  * @param row
  * @returns {String}
  */
  function f_show(val,row){	
	  return '<a href=\"javascript:f_teamexaminfo(\''+row.acc_num+'\',\''+row.exam_num+'\')\">查看</a>';
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
  

 function getteamtotalinfo() {
		$.post(
			"teamItemCount.action",
			{
				"acc_num" : $("#acc_num").val()
			},
			function(jsonPost) {
			    var customertotal = eval(jsonPost);
			    //document.getElementById("zrs").firstChild.nodeValue = customertotal.totalcustume;
				document.getElementById("zrc").firstChild.nodeValue = customertotal.totalexam;
			    document.getElementById("ysje").firstChild.nodeValue = customertotal.totalAmt;
				document.getElementById("ssje").firstChild.nodeValue = customertotal.termAmt;
				document.getElementById("yhje").firstChild.nodeValue = customertotal.personAmt;
			}, "json");
	}
 