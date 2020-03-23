$(document).ready(function() {
	djtcustChangItemListGrid();
	gettotalinfo();
})

function gettotalinfo() {
	$.post(
					"teamexamItemCount.action",
					{
						"exam_num" : $("#exam_num").val(),
						"acc_num":$("#acc_num").val()
					},
					function(jsonPost) {
						var customertotal = eval(jsonPost);
						document.getElementById("countss").firstChild.nodeValue = customertotal.counts;
						document.getElementById("tyjje").firstChild.nodeValue = customertotal.totalAmt;
						document.getElementById("gyjje").firstChild.nodeValue = customertotal.personAmt;
						document.getElementById("gsjje").firstChild.nodeValue = customertotal.qfAmt;
					}, "json");
}

	

	/**
	 * 显示体检项目套餐信息
	 */
	function djtcustChangItemListGrid() {
		var model = {"acc_num":$("#acc_num").val(),"exam_num":$("#exam_num").val()};
		$("#djtGselectItemcharginglist").datagrid({
			url : 'getExaminfoChargingItemforExamNum.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : true,
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ {align : 'left',field : 'item_code',title : '项目编码',	width : 15},
			             {align : 'left',field : 'item_name',title : '项目名称',	width : 35},
			             {align : 'center',field : 'price',title : '原金额',	width : 10},
			             {align : 'center',field : 'rate',title : '折扣率',	width : 10},
			             {align : 'center',field : 'acc_charge',title : '结算收额',	width : 10}, 
			             {align : 'center',field : 'personal_pay',title : '个人付费金额',	width : 10},
			             {align : 'center',field : 'prePays',title : '是否预结算',	width : 10},			             
			             {align : 'center',field : 'discards',title : '是否弃项',	width : 15}
			          ]],
			onLoadSuccess : function(value) {
			},
			rowStyler:function(index,row){    
		         
		    },
		    singleSelect : false,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000	 
		});
	}