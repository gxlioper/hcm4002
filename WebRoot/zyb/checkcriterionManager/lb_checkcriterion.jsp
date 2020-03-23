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
		shu();
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
	    var f = "";
	    var z = "";
	    var node = $("#some_tree").tree('getSelected');
	    if(node.lx=='lx'){//职业危害体检类别
 	    	f = '\''+$('#some_tree').tree("getParent",node.target).id+'\''; 
 	    	z = node.id;
	    } else if(node.ys == 'ys'){//因素
	    	f ='\''+ node.id+'\'';
	    	//z = $("#some_tree").tree("getChildren",node.target);
	    } else {
	    	var zi = $('#some_tree').tree("getChildren",node.target)
	    	f = $('#some_tree').tree('getSelected');
	    	var arra = new Array();
	    	for(var i = 0 ; i < zi.length ; i++){
	    		if(zi[i].ys=='ys'){
	    			arra.push('\''+zi[i].id+'\'');
	    		}
	    	}
	    	f = arra.toString();
	    	if(f==""){
	    		f='\'\'';
	    	}
	    }
		
		
		$('#list').datagrid({
			url : 'queryLbcriterionManager.action',
			queryParams : {
				criterion_name : $('#criterion_name_s').textbox('getValue'),
				occuphyexaclassID:z,
				hazardfactorsID:f
			},
			dataType : 'json',
			pageSize : 15,//每页显示的记录条数，默认为10 
			pageList : [ 15, 20, 30, 60, 75 ],//可以设置每页记录条数的列表 
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				align : "center",
				field : 'criterionID',
				title : 'ID',
				hidden : true
			}, {
				align : "left",
				field : 'criterion_name',
				title : '检查依据',
				width : 90
			}, {
				align : "center",
				field : 'DISORDER',
				title : '顺序码',
				width : 20
			},  {
				align : "center",
				field : 'occuphyexaclass_name',
				title : '职业体检类别',
				width : 20
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
			fitColumns : true,
			//singleSelect:true,
			/* fit : true, */
			toolbar : toolbar,
			onDblClickRow : function(index, row) {
				updateChecriterion(row.RID);
			}
		});
	};
	var toolbar = [ {
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
			var node = $("#some_tree").tree('getSelected');
			var lx = node.lx;
			var f_lx = $("#some_tree").tree("getParent",node.target);
			//alert(f_lx.id);
			if(lx=="lx"){
				$("#edit_dlg").dialog({
					title : '新增',
					center:'center',
					href : 'getLbcriterionManagerupdatePage.action?occuphyexaclassID='+node.id+'&hazardfactorsID='+f_lx.id
				});
				$("#edit_dlg").dialog("open");
			} else {
				$.messager.alert("提示信息","请选择危害因素和类别","error");
				return;
			}
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
				+ row.RID
				+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	}
	//删除按钮
	function f_delete(val, row) {
		return '<a href=\"javascript:deleteChecriterion(\''
				+ row.RID
				+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}
	//更新

	function updateChecriterion(id) {
		
		$("#edit_dlg").dialog({
			title : '修改',
			href : 'updateLbcriterionManagerPage.action?RID='+ id
		});
		$("#edit_dlg").dialog('open');
	}
	//删除
	function deleteChecriterion(id) {
		$.messager.confirm('提示信息', '是否确定删除？', function(r) {
			if (r) {
				$.ajax({
					url : 'deleteLbcriterionManager.action?RID=' + id,
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
	
	//--------------------------------------------------树-----------------------------

	/**
	 * 树
	 */
	function shu(){
		/* var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);*/
		$("#some_tree").tree({
			 url:'getZybExamSetTree.action',
			 onClick:function(node){
			 },
			 onLoadSuccess:function(node,data){  
			 		$(".loading_div").remove();
		           $("#some_tree li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
		           var n = $("#some_tree").tree("getSelected");  
		           if(n!=null){  
		                $("#some_tree").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
		           }  
	        },
	        onSelect:function(node){
	        	getData();
	        }
		});
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true"
		style="width: 200px;">
		<ul id="some_tree"></ul>
	</div>
	<div data-options="region:'center'" style="height: 80px;">
		<fieldset
			style="margin: 5px; padding-right: 20px; padding-left: 20px;">
			<legend>
				<strong>信息检索</strong>
			</legend>
			<div id="tb" style="padding-right: 20px; padding-left: 50px;">
				&nbsp;&nbsp;&nbsp;&nbsp;模糊查询: <input id="criterion_name_s"
					class="easyui-textbox" /> &nbsp;&nbsp;&nbsp;&nbsp;<a
					href="javascript:searchFun();"
					class="easyui-linkbutton c6 l-btn l-btn-small" style="width: 70px;">查&nbsp;&nbsp;询</a>
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);"
					class="easyui-linkbutton" onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
			</div>
		</fieldset>
			<div id="list" style="height: 88%"></div>
	</div>
	<div id="edit_dlg" class="easyui-dialog"
		data-options="width: 700,height: 370,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>