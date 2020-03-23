$(function() {
	//getExaminationItem();//检查项目列表
	seletExaminationItem();//已选中的检查项目清单
});


/**
* 获取检查项目列表
*/
function getExaminationItem() {
}




/**
 * 失去焦点类型切换
 */
function leixing(row) {
	//alert(row.id+"=="+row.item_category+"===="+row.limit_count+"==="+row.dep_id)
	//项目名称赋值
	$("#title_item").html(row.item_code+"（"+row.item_name+"）");
	$('#chargitem_id').val(row.id);
	$('#limit_count').val(row.limit_count);
	$("#dept_id").val(row.dep_id);
	$('#item_msg').hide();
	if (row.item_category == "普通类型") {
		$('#common').show();
		getExaminationItem();//检查项目列表
		$('#selectedExaminationItem').datagrid({
			url : 'getChargingItemExamItem.action',
			queryParams : {
				id : row.id,
				item_name : $('#item_num').val()
			},
			singleSelect : true,
			height : 433,
			striped : true,
			rownumbers : true,
			fitColumns : true,
			columns : [ [
				{
				align : 'center',
				field : 'item_num',
				title : '检查项目编码',
				width : 10
			}, {
				align : 'left',
				field : 'item_name',
				title : '检查项目名称',
				width : 10
			}, {
				align : 'center',
				field : 'exam_item_id',
				title : '检查项目名称',
				width : 10,
				hidden:true
			} ,{
				align : 'center',
				title : '顺序码',
				field : 'seq_code',
				width : 10/*,
				editor : 'text'*/
			}] ]
		})
	} else if (row.item_category == "耗材类型") {
		$('#common').hide();
		$('#item_msg').show();
	}
}
var editRow = ""; 
function seletExaminationItem() {
	$('#selectedExaminationItem').datagrid({
		url : 'getChargingItemExamItem.action',
		queryParams : {
			id : $('#chargitem_id').val(),
			item_name : $('#item_num').val()
		},
		singleSelect : true,
		//striped:true,
		fit:true,
		rownumbers : true,
		fitColumns : true,
		columns : [[{
			align : 'center',
			title : '检查项目编码',
			field : 'item_num',
			width : 10
		}, {
			align : 'left',
			title : '检查项目名称',
			field : 'item_name',
			width : 15
		}, {
			align : 'center',
			title : 'id',
			field : 'id',
			'hidden' : true
		}, {
			align : 'center',
			title : '顺序码',
			field : 'seq_code',
			width : 10
		} ] ],
	})

}

//修改项目细项
function getExamItemAddSave(){
	var j = $('#selectedExaminationItem').datagrid("getRows");//获取已添加的数据
	//得到list列表
	var ar = new Array();
	for (var i = 0; i < j.length; i++) {
		var x_j = new Object();
		x_j.exam_item_id = j[i].exam_item_id;
		x_j.seq_code = j[i].seq_code;
		x_j.item_num = j[i].item_num;
		ar.push(JSON.stringify(x_j));
	}
	//alert('[' + ar.toString() + ']');
	//alert($('#chargitem_id').val());
	$.ajax({
		url:'getExamItemAddSave.action',
		type:'post',
		data:{
			itemId : $('#chargitem_id').val(),
			limitCount:$('#limit_count').val(),
			itemList:'[' + ar.toString() + ']'
		},
		success : function(data) {
			//$.messager.alert('提示', data);
		},
	})
}
