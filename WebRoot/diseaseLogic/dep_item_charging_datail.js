$(function() {
	getExaminationItem();//检查项目列表
	seletExaminationItem();//已选中的检查项目清单
	
	$("#item_num").keyup(function(){
		getExaminationItem();
	});
		 
});


var toolbar = [ {
	text : '新增检查项目',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增检查项目',
			align : 'center',
			width : 1000,
			height: 515,
			href : 'addExaminationItemPage.action?dept_id='+$("#dept_id").val()
		});
		$("#dlg-custedit").dialog('open');
	}
}];

/**
* 获取检查项目列表
*/
function getExaminationItem() {
	$("#examinationItem").datagrid({
		height : 433,
		url : "queryExaminationItemByConfig.action",
		queryParams : {
			dept_id:$("#dept_id").val(),
			item_name : $('#item_num').val()
		},
		pageSize : 10,
		pagination : true,
		fitColumns : true,
		pageList : [ 10, 20, 30 ],
		singleSelect : true,
		rownumbers : true,
		striped : true,
		columns : [ [ 
		{
			align : 'center',
			title : '检查项目编码',
			field : 'item_num',
			width : 100
		}, {
			align : 'left',
			title : '检查项目名称',
			field : 'item_name',
			width : 100
		}, {
			align : 'center',
			title : '顺序码',
			field : 'seq_code',
			width : 50
		}, {
			align : 'center',
			field : 'edit',
			title : '修改',
			"formatter" : f_edit,
			width : 50
		} ,{
			align : 'center',
			field : 'ss',
			title : '操作',
			"formatter" : f_add,
			width : 50
		}] ],
		toolbar:toolbar,
		onDblClickRow:function(index,row){
			//fc_add(index)
			//执行查看事件
			$("#dlg-itemView").dialog({
				title:'查看检查项目',
				width : 682,
				height: 500,
				href:'viewExaminationItem.action?id='+row.id
			});
			$("#dlg-itemView").dialog('open');
		}
	})
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
		//$('#examinationItem').datagrid('reload');//刷新检查项目列表
		//seletExaminationItem();//已选中的检查项目清单
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
			columns : [ [ {
				align : 'center',
				field : 'aa',
				title : '操作',
				"formatter" : del_Item,
				width : 10
			}, {
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
		//seletExaminationItem();//已选中的检查项目清单
		//$('#selectedExaminationItem').datagrid('reload');
	} else if (row.item_category == "耗材类型") {
		$('#common').hide();
		$('#item_msg').show();
	}
}



//数据添加到右边表格
function f_add(value, row, index) {
	return '<a href=\"javascript:fc_add(\'' + index + '\')\">添加</a>';
}
function fc_add(index) {
	var Item = $('#selectedExaminationItem').datagrid("getRows");//获取已添加的数据
	var row = $('#examinationItem').datagrid("getRows")[index];//获取选择行的数据
	var s = row.item_num;
	var f = 0;
	$.each(Item, function(k, v) {
		if (v.item_num == s) {
			f = 1;
			$.messager.alert('提示', "检查项目已经在清单中", 'error');
		}
	})
	if (f == '0') {
		$('#selectedExaminationItem').datagrid('appendRow', {
			item_name : row.item_name,
			item_num : row.item_num,
			exam_item_id : row.id,
			seq_code : row.seq_code
		});
		getExamItemAddSave();
	}
}
//修改项目明细
function f_edit(value, row){
	return '<a href=\"javascript:f_edit_item(\'' + row.id + '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
function f_edit_item(id) {
	$("#dlg-custedit").dialog({
		title:'修改检查项目',
		width : 1000,
		height: 515,
		href:'updateExaminationItemPage.action?id='+id
	});
	$("#dlg-custedit").dialog('open');
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
		height : 380,
		//striped:true,
		//fit:true,
		rownumbers : true,
		fitColumns : true,
		columns : [ [ {
			align : 'center',
			field : 'aa',
			title : '操作',
			"formatter" : del_Item,
			width : 10
		}, {
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
			width : 10/*,
			editor : 'text'*/
		} ] ],
		striped : true,
		onDblClickRow :onClickRow
	})

}
//删除已选择检查项目
function del_Item(value, row, index) {
	return '<a href=\"javascript:del_dTem(\'' + index + '\')\">删除</a>';

}
function del_dTem(index) {
	//-------------返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
	//selectchargingItem是datagrid的id
	
	$.messager.confirm('提示信息', '确定删除该项目？', function(r) {
		if (r) {
			var selections = $('#selectedExaminationItem').datagrid('getSelections');
			var selectRows = [];
			for (var i = 0; i < selections.length; i++) {
				selectRows.push(selections[i]);
			}
			for (var j = 0; j < selectRows.length; j++) {
				var index = $('#selectedExaminationItem').datagrid('getRowIndex',selectRows[j]);
				//执行删除行
				//selectchargingItem是datagrid的id,index要删除的行
				$('#selectedExaminationItem').datagrid('deleteRow', index);
			}
			
			//执行保存方法
			getExamItemAddSave();
		}
	})
	
	//$('#selectedExaminationItem').datagrid("deleteRow",index);
}

//开启编辑框
var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#selectedExaminationItem').datagrid('validateRow', editIndex)){
		var ed = $('#selectedExaminationItem').datagrid('getEditor', {index:editIndex,field:'seq_code'});
	var seq_code = $(ed.target).val();
		$('#selectedExaminationItem').datagrid('getRows')[editIndex]['seq_code'] = seq_code;
		$('#selectedExaminationItem').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickRow(index){
		if (editIndex != index){
				if (endEditing()){
					$('#selectedExaminationItem').datagrid('selectRow', index).datagrid('beginEdit', index);
				editIndex = index;
				} else {
					$('#selectedExaminationItem').datagrid('selectRow', editIndex);
				}
		}
}
function accept(){
		if (endEditing()){
			$('#selectedExaminationItem').datagrid('acceptChanges');
		}
}

/**
 * 检查项目名称获取拼音
 */
function pinying(){
	$.ajax({
		url:'pinying.action',
		type:'post',
		data:{pinying:$('#item_name').val()},
		success : function(data) {
			$('#item_pinyin').val(data);
		},
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
