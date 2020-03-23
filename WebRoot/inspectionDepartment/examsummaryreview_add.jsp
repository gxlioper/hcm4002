<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
#main {	width: auto;	height: auto;}
#left {	width: 580px;	height: auto;}
#right {width: 560px;	height: auto;	margin-left: 10px;}
#left, #right {	float: left;}
</style>
<script type="text/javascript">
$(document).ready(function () {
	setfzchareitemListGrid();
	setChangItemListGrid();
	$('#itemname').bind('keyup', function() {
		setChangItemListGrid();
	});
});
function selectyx(){
	var obj = $("#disease_sug_list").datagrid('getRows');
	if(obj.length == 0){
		$.messager.alert("操作提示","阳性发现列表不存在,不需要选择!", "error");
	}else{
		$("#disease_fc").datagrid({
			dataType: 'json',
			data:obj,
			rownumbers:true,
			columns:[[
				 {align:'',field:'disease_name',title:'阳性/疾病发现',width:70}
			]],	    	
			onLoadSuccess:function(value){
			    $("#datatotal").val(value.total);
			},
			singleSelect:false,
			pagination: false,
			fitColumns:true,
			fit:true,
			striped:true,
			nowrap:false
		});
		$("#dlg-fc").dialog("open");
	}
}
function quedingzhuti(){
	var obj = $("#disease_fc").datagrid('getSelections');
	var title = new Array();
	for(i=0;i<obj.length;i++){
		title.push(obj[i].disease_name);
	}
	$("#review_title").textbox('setValue',title.toString());
	$("#dlg-fc").dialog("close");
}
function shezhitianshu(){
	$("#sdts").numberbox('setValue','30');
	$("#sdts").numberbox('textbox').focus();
	$("#dlg-ts").dialog("open");
}
function quedingtianshu(){
	var num = $("#sdts").numberbox('getValue');
	var dateTo = $("#review_date").datebox('getValue');
	$.ajax({
		url:'getdateAddDay.action',
		data:{
			'num':num,
			'review_date':dateTo
		},
		type:'post',
		success:function(data){
			$("#dlg-ts").dialog("close");
			if(data.split(',')[0] == 'ok'){
				$("#review_date").datebox('setValue',data.split(',')[1]);
			}else{
				$.messager.alert("操作提示",data.split(',')[1], "error");
			}
		}
	});
}

function quedingfucha(){
	if($("#review_title").textbox('getValue') == ''){
		$("#review_title").textbox('textbox').focus();
		$.messager.alert("操作提示","请输入或选择复查主题!", "error");
		return;
	}else if($("#review_date").datebox('getValue') == ''){
		$.messager.alert("操作提示","请选择复查日期!", "error");
		return;
	}
	var itemrows = $('#selectchangitemlist').datagrid('getRows');
	var ids = new Array();
	for(i = 0;i < itemrows.length;i++){		  
	    ids.push(itemrows[i].charge_item_id);    
	} 
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveExamSummaryReview.action',
		data:{
			'id':$("#review_id").val(),
			'exam_num':$("#exam_num").val(),
			'review_title':$("#review_title").textbox('getValue'),
			'review_date':$("#review_date").datebox('getValue'),
			'li':ids.toString()
		},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			if(data.split('-')[0] == 'ok'){
				$.messager.alert("操作提示",data.split('-')[1], "info");
				$('#dlg-edit').dialog('close');
			}else{
				$.messager.alert("操作提示",data.split('-')[1], "error");
			}
		}
	});
}
function neirong(){
	$('#neirong').datagrid({
		url:'getDatadis.action?com_Type=DXMBNR',
		columns:[[
			    {align:'center',field:'name',title:'内容',width:10},	
			 	]],
		 	  fitColumns:true,
		    striped:true,
		    fit:false,
		    onDblClickRow:function(index, row){
		    	butt(row);
	        }
	})
}

//----------------------------------------显示收费项目-------------------------------------------------
/**
 * 显示体检项目套餐信息
 */
function setChangItemListGrid() {
	var model = {
		"setname" : $("#itemname").val(),
		"sex": $("#sex").html()
	};
	$("#changitemlist").datagrid(
			{
				url : 'changitemlist.action',
				dataType : 'json',
				queryParams : model,
				rownumbers : false,
				pageSize: 30,//每页显示的记录条数，默认为10 
				pageList : [ 30, 60, 90, 120, 150 ],//可以设置每页记录条数的列表 
				columns : [[ {align : 'center',
					field : 'item_code',
					title : '项目编码',
					width : 20
				}, {
					align : 'center',
					field : 'item_name',
					title : '项目名称',
					width : 25
				}, {
					align : 'center',
					field : 'dep_name',
					title : '所属科室',
					width : 15
				}, {
					align : 'center',
					field : 'sex',
					title : '适用性别',
					width : 15
				}, {
					align : 'center',
					field : 'item_amount',
					title : '金额',
					width : 15
				},{
					align : 'center',
					field : 'tj',
					title : '添加',
					width : 10,
					formatter : f_addchargitem
				}]],
				onLoadSuccess : function(value) {
					$("#datatotal").val(value.total);
				},
				height:320,
				singleSelect : true,
				collapsible : true,
				pagination : true,
				fitColumns : true,
				striped : true,
				onDblClickRow : function(index, row) {
					insertitem(row);
					$('#itemname').focus(); 
					$("#itemname").select();
				}
			});
}
function f_addchargitem(val, row,rowIndex){
	return '<a href=\"javascript:f_addchargitemone('+rowIndex+')\">添加</a>';
}
function f_addchargitemone(rowIndex){
	var row = $("#changitemlist").datagrid('getRows')[rowIndex];
	insertitem(row);
}
/**
 * 增加分组项目
 */
function insertitem(row) {
	var rowsLength = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;// 不相等
	var selectitemcode = "";
	var itemtypeflag = true;
	if (!rowsLength.length == 0) {
		for (var j = 0; j <= rowsLength.length - 1; j++)// 已选择
		{
			if (row.item_code == rowsLength[j].item_code) {
				flag = false;// 相等
			}
			if ((row.item_type != '')
					&& (row.item_type == rowsLength[j].item_type)) {
				itemtypeflag = false;
			}
			selectitemcode = selectitemcode + ",'" + rowsLength[j].item_code
					+ "'";
		}// j End
	}
	if (flag == true) {

		var usersex = $("#sex").html();
		var sexflag = false;
		// alert(usersex+"---"+row.sex);
		if (usersex == '') {
			sexflag = true;
		} else if (row.sex == '全部') {
			sexflag = true;
		} else if (usersex == row.sex) {
			sexflag = true;
		}
		if (sexflag) {
			if (itemtypeflag) {
				$('#selectchangitemlist').datagrid("appendRow", {
					charge_item_id:row.id,
					item_code : row.item_code,
					item_name : row.item_name,
					dep_name : row.dep_name,
					item_amount : row.item_amount,
					sex : row.sex,
					itemnum : row.itemnum,
					item_type:row.item_type,
					discount : row.discount,
					set_num : row.set_num,
					amount : row.amount
				});
			} else {
				$.messager.confirm('提示信息', '[' + row.item_code + '-'
						+ row.item_name + ']冲突，是否添加？', function(r) {
					if (r) {
						$('#selectchangitemlist').datagrid("appendRow", {
							charge_item_id:row.id,
							item_code : row.item_code,
							item_name : row.item_name,
							dep_name : row.dep_name,
							item_amount : row.item_amount,
							sex : row.sex,
							itemnum : row.itemnum,
							item_type:row.item_type,
							discount : row.discount,
							set_num : row.set_num,
							amount : row.amount 
						});
					}
				});
			}
		} else {
			alert_autoClosep("操作提示", "性别冲突，不能添加！", "error");
		}
	}else {
		alert_autoClosep("操作提示", "项目冲突，不能添加！", "error");
	}
}
//----------------------------------------显示复查收费项目-------------------------------------------------
/**
 * 显示体检项目套餐信息
 */
function setfzchareitemListGrid() {
	var model = {
		"exam_num" : $("#exam_num").val()
	};
	$("#selectchangitemlist").datagrid({
		url : 'getExamSummaryReviewItemList.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : false,
		//pageSize: 8,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
		columns : [[ {
			align : "center",
			field : "fxfzddd",
			title : "删除",
			width : 15,
			"formatter" : f_dellchargitem
		}, {
			align : 'center',
			field : 'item_code',
			title : '项目编码',
			width : 20
		}, {
			align : 'center',
			field : 'item_name',
			title : '项目名称',
			width : 25
		},{
			align : 'center',
			field : 'dep_name',
			title : '科室',
			width : 25
		}, {
			align : 'center',
			field : 'item_amount',
			title : '金额',
			width : 20
		}]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
		},
		height:320,
		singleSelect : true,
		collapsible : true,
		fitColumns : true,
		autowidth : true,
		striped : true,
		pagination : false
	});
}
/**
 * 删除收费项目
 * @param val
 * @param row
 * @returns {String}
 */
function f_dellchargitem(val, row) {
	return '<a href=\"javascript:deletechargitemOne(\''
			+ row.item_code
			+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"删除\" /></a>';
}
/**
 * 删除收费项目
 */
function deletechargitemOne(set_numsss) {
	$.messager.confirm('提示信息', '确定删除此收费项目吗？', function(r) {
		if (r) {
			var rows = $('#selectchangitemlist').datagrid('getRows');
			if (!rows.length == 0) {
				for (var i = rows.length - 1; i >= 0; i--) {
					if (set_numsss == rows[i].item_code) {
						var index1 = $('#selectchangitemlist').datagrid(
								'getRowIndex', rows[i]);//获取指定索引
						$('#selectchangitemlist').datagrid('deleteRow',
								index1);//删除指定索引的行
						break;
					}
				}//j End             
			}
		}
	})
}
</script>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3">
			<dl style="margin-top:20px;">
				<dt style="width:100px;">复查主题：<input type="hidden" id="review_id" value="<s:property value='id'/>"/></dt>
				<dd><input type="text" style="width:600px;height:26px;" id="review_title" class="easyui-textbox" value="<s:property value='review_title'/>" data-options="buttonText:'选择',onClickButton:function(){selectyx()}"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt style="width:100px;">复查日期：</dt>
				<dd><input type="text" style="width:100px;height:26px;" id="review_date" class="easyui-datebox" value="<s:property value='review_date'/>"/><strong class="red">*</strong>
				<a href="javascript:void(0)" class="easyui-linkbutton" style="width:50px;height:26px;" onclick="javascript:shezhitianshu();">设置</a></dd>
				<dt style="width:80px;">复查状态：</dt>
				<dd><input type="text" style="width:60px;height:26px;" readonly="readonly" id="review_status" class="easyui-textbox" value="<s:property value='review_status'/>"/></dd>
				<dt style="width:80px;">设定医生：</dt>
				<dd><input type="text" style="width:80px;height:26px;" readonly="readonly" id="review_user" class="easyui-textbox" value="<s:property value='review_user'/>"/></dd>
				<dt style="width:80px;">通知日期：</dt>
				<dd><input type="text" style="width:100px;height:26px;" readonly="readonly" id="notice_time" class="easyui-datebox" value="<s:property value='notice_time'/>"/></dd>
				<dt style="width:80px;">通知方式：</dt>
				<dd><input type="text" style="width:60px;height:26px;" readonly="readonly" id="notice_type" class="easyui-textbox" value="<s:property value='notice_type'/>"/></dd>
				<dt style="width:80px;">通知医生：</dt>
				<dd><input type="text" style="width:80px;height:26px;" readonly="readonly" id="notice_user" class="easyui-textbox" value="<s:property value='notice_user'/>"/></dd>
			</dl>
	</div>
	<div>
	<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>选择体检项目</strong>
	</legend>
	<div id="main">
		<div id="left">
			项目列表<input type="text" id="itemname" name="itemname" value="" />
			<div id="changitemlist"></div>
		</div>

		<div id="right">
			已选项目
			<div id="selectchangitemlist"></div>
		</div>

	</div>
	</fieldset>
</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:quedingfucha();">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
</form>
<div id="dlg-fc" class="easyui-dialog"  data-options="width: 500,height: 650,closed: true,cache: false,modal: true,title:'选择复查主题'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:10px;">
			<div style="height:550px;width:450px;margin-left:20px;">
				<table id="disease_fc"></table>
			</div>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:quedingzhuti();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-fc').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
<div id="dlg-ts" class="easyui-dialog"  data-options="width: 300,height: 150,closed: true,cache: false,modal: true,title:'设置几天后复查'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<div style="height:50px;width:200px;margin-left:70px;">
				本顾客<input type="text" style="width:40px;height:26px;" id="sdts" class="easyui-numberbox" data-options="min:1" value="30"/>天后复查
			</div>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:quedingtianshu();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-ts').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
