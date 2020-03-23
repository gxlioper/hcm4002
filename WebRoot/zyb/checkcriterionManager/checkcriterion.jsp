<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>检查依据管理</title>
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/dtreeck.css" />
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/dtree.css" />
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		getData();
		enter();
	});

	function enter() {
		document.onkeydown = function(e) {//回车查询
			var ev = document.all ? window.event : e;
			if (ev.keyCode == 13) {
				getData();
			}
		}
	}
	function getData() {
		
		$('#list').datagrid({
			url : 'getChecriterionList.action',
			queryParams : {
				criterion_name : $('#criterion_name_s').textbox('getValue')
			},
			pageSize : 15,//每页显示的记录条数，默认为10 
			pageList : [ 15, 20, 30, 60, 75 ],//可以设置每页记录条数的列表 
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				align : "center",
				field : 'criterionID',
				title : 'ID',
				width : 30,
				hidden : true
			}, {
				align : "center",
				field : 'criterion_name',
				title : '检查依据',
				width : 30
			}, {
				align : "center",
				field : 'upd',
				title : '修改',
				width : 20,
				"formatter" : f_update
			}, {
				align : "center",
				field : 'del',
				title : '删除',
				width : 20,
				"formatter" : f_delete
			},

			] ],
			pagination : true,//分页控件
			rownumbers : true,//行号
			//singleSelect:true,
			   fitColumns:true,//自适应
			 fit : true, 
			toolbar : toolbar,
			onDblClickRow : function(index, row) {
				var row_id = $('#list').datagrid('getRows')[index].criterionID;
				updateChecriterion(row_id);
			}
		});
	};
	var toolbar = [ {
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
				$("#edit_dlg").dialog({
					title : '新增',
					center:'center',
					href : 'addChecriterion.action'
				});
				$("#edit_dlg").dialog("open");
		}
	}, {
		text : '批量删除',
		width : 120,
		iconCls : 'icon-cancel',
		handler : function() {
			var ids = new Array();
			var checkedItems = $('#list').datagrid('getChecked');
			$.each(checkedItems, function(index, item) {
				ids.push("'" + item.criterionID + "'");
			});
			f_deletes(ids.toString());
		}
	} ];

	//批量删除
	function f_deletes(id) {
		if (id == null || id == "") {
			$.messager.alert('警告信息', "请选择要删除的记录", 'error');
			return;
		}
		$.messager.confirm('确认', '您确认要删除记录吗？', function(r) {
			if (r) {
				$.ajax({
					url : 'jcyjdeletes.action?ids=' + id,
					type : 'post',
					success : function(data) {
						$.messager.alert('提示信息', data, 'info');
						$('#list').datagrid('reload');
					},
					error : function() {
						$.messager.alert('提示信息', '操作失败！', 'error');
					}
				});
			}
		});
	}

	  
	    	
	//修改按钮
	function f_update(val, row) {
		return '<a href=\"javascript:updateChecriterion(\''
				+ row.criterionID
				+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	}
	//删除按钮
	function f_delete(val, row) {
		return '<a href=\"javascript:deleteChecriterion(\''
				+ row.criterionID
				+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}
	//更新

	function updateChecriterion(id) {
		$("#edit_dlg").dialog({
			title : '修改',
			href : 'updateChecriterion.action?criterionID=' + id
		});
		$("#edit_dlg").dialog('open');
	}
	//删除
	function deleteChecriterion(id) {
		$.messager.confirm('提示信息', '是否确定删除？', function(r) {
			if (r) {
				$.ajax({
					url : 'deleteChecriterion.action?criterionID=' + id,
					type : 'post',
					success : function(data) {
						$.messager.alert('提示信息', data);
						getData();
					},
					error : function() {
						$.messager.alert('提示信息', '操作失败！', 'error');
					}
				});
			}
		})
	}
	function cleanFun() {
		$("#criterion_name_s").textbox('setValue', "");
		getData();
	}
	function searchFun() {
		getData();
	}
	
</script>
</head>
<body> 
		<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
		<legend><strong>信息检索</strong></legend>
			&nbsp;&nbsp;&nbsp;&nbsp;模糊查询: <input id="criterion_name_s"  class="easyui-textbox"/>
			
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
		 </fieldset>
		  </div>
    	<table  id="list" > </table> 
    <div id="edit_dlg" class="easyui-dialog"  data-options="width: 600,height: 370,closed: true,cache: false,modal: true,top:50"></div>  
</body> 
</html>