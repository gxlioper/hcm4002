$(document).ready(function() {
	setselectListGrid();
	djtcustChangItemListGrid();
	gettotalinfo();
})

function gettotalinfo() {
	$.post(
					"djtGItemCount.action",
					{
						"exam_id" : $("#exam_id").val(),
						"exam_type" : 'T'
					},
					function(jsonPost) {
						var customertotal = eval(jsonPost);
						document.getElementById("countss").firstChild.nodeValue = customertotal.counts;
						document.getElementById("tyjje").firstChild.nodeValue = customertotal.termAmt;
						document.getElementById("gyjje").firstChild.nodeValue = customertotal.personAmt;
						document.getElementById("gsjje").firstChild.nodeValue = customertotal.personYfAmt;
						document.getElementById("gwjje").firstChild.nodeValue = customertotal.qfAmt;
					}, "json");
}

	/**
	 * 显示分组套餐信息
	 */
	function setselectListGrid() {
		var model = {
			"exam_id" :document.getElementById("exam_id").value
		};
		$("#djtGselectsetlist").datagrid({
			url : 'exam_tclistshow.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : false,
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ {
				align : 'center',
				field : 'set_num',
				title : '套餐编码',
				width : 15
			}, {
				align : 'center',
				field : 'set_name',
				title : '套餐名称',
				width : 45
			}, {
				align : 'center',
				field : 'sex',
				title : '适用性别',
				width : 20
			}, {
				align : 'center',
				field : 'set_discount',
				title : '套餐折扣率',
				width : 30
			}, {
				align : 'center',
				field : 'set_amount',
				title : '套餐金额',
				width : 20
			} ]],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
			},
			singleSelect : true,
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

	/**
	 * 显示体检项目套餐信息
	 */
	function djtcustChangItemListGrid() {
		var model = {"exam_num" :$("#exam_num").val()};
		$("#djtGselectItemlist").datagrid({
			url : 'djtcustchangitemlist.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : true,
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ {align : 'left',field : 'item_code',title : '项目编码',	width : 15},
			             {align : 'left',field : 'item_name',title : '项目名称',	width : 35},
			             {align : 'center',field : 'item_amount',title : '原金额',	width : 10},
			             {align : 'center',field : 'discount',title : '折扣率',	width : 10},
			             {align : 'center',field : 'is_new_added',title : '增加次数',	width : 1},
			             {align : 'center',field : 'amount',title : '应收额',	width : 10},
			             {align : 'center',field : 'pay_statuss',title : '结算状态',	width : 15}, 
			             {align : 'center',field : 'team_pay',title : '团体金额',	width : 10},
			             {align : 'center',field : 'personal_pay',title : '个人金额',	width : 10},			             
			             {align : 'center',field : 'exam_indicators',title : '付费方式',	width : 15}
			          ]],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
				$("#djtGselectItemlist").datagrid("hideColumn", "is_new_added"); // 设置隐藏列   
				$("#djtGselectItemlist").datagrid("hideColumn", "item_code"); // 设置隐藏列  
			},
			rowStyler:function(index,row){    
		        if (row.is_new_added>0){    
		            return 'font-weight:bold;';    
		        }    
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