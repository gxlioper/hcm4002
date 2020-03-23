$(document).ready(function () {
	getyuyueGrid();
});

	 /**
	  * 
	  */
function getyuyueGrid() {
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
			+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	 var chk_value ="";    
	  $('input[name = chkItem]:checked').each(function(){    
	   chk_value=chk_value+","+($(this).val());    
	  }); 
	  if(chk_value.length>1){
		  chk_value=chk_value.substring(1,chk_value.length);
	  }
	 var model={
			 "exam_num":$("#exam_num").val(),
			 "time1":$("#time1").datebox('getValue'),	
			 "time2":$("#time2").datebox('getValue'),				 
			 "custname":$("#custname").val(),
			 "phone":$("#phone").val(),
			 "chkItem":chk_value,
			 "orderid":$("#orderid").val(),
			 "exam_type":document.getElementsByName("exam_type")[0].value,
			 "status":document.getElementsByName("statusss")[0].value
	 };
	$("#yuyueShowList").datagrid({
		url : 'yuyueShowListdo.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : true,
		pageSize : 50,// 每页显示的记录条数，默认为10
		pageList : [50,150,300 ],// 可以设置每页记录条数的列表
		columns : [ [ {align : 'center',field : 'order_id',title : '订单单号',	width : 25}, 
		              {align : 'center',field : 'exam_num',title : '体检编号',width : 15}, 
		              {align : 'center',field : 'id_num',title : '身份证号',width : 40}, 
		              {align : 'center',field : 'user_name',title : '姓名',width : 25}, 
		              {align : 'center',field : 'sex',title : '性别',width : 10}, 
		              {align : 'center',field : 'age',title : '年龄',width : 10}, 
		              {align : 'center',field : 'phone',title : '电话',width : 15},
		              {align : 'center',field : 'orderstatuss',title : '状态',width : 10},		              
		              {align : 'center',field : 'register_date',title : '预约日期',width : 15}, 		              
		              {align : 'center',field : 'create_time',title : '订单日期',	width : 25} ,
		              {align : 'center',field : 'budget_amount',title : '订单金额',	width : 15} 
		              ] ],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			$(".loading_div").remove();
		},
		singleSelect:false,
	    collapsible:true,
		pagination: true,
	    fitColumns:true,
	    striped:true
	});
}

 