
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script>

$(document).ready(function () {
	getteamlist();
});

var lastindex = null;
function getteamlist(){
	$("#team_add_list").datagrid({
		url:'getteamaccountaddlist.action',
		dataType: 'json',
		queryParams:{'acc_num':$("#acc_num_add").val()},
		rownumbers:false,
		columns:[[
			 {align:'center',field:'item_name',title:'费用名称',width:50},
			 {align:'center',field:'amount',title:'金额',width:50,editor:{type:'numberbox',options:{precision:2}}}
		]],
		singleSelect:true,
		collapsible:true,
		pagination: false,
		striped:true,
		nowrap:false,
		fitColumns:true,
		fit:true,
		onClickCell:function(index, field, row){
			if(lastindex != null){
				$('#team_add_list').datagrid('endEdit', lastindex);
			}
			$('#team_add_list').datagrid('beginEdit', index);
			lastindex = index;
			var editors = $('#team_add_list').datagrid('getEditor',{index:index,field:'amount'});
			$(editors.target).numberbox({onChange:function(){
					$('#team_add_list').datagrid('endEdit', index);
				}
			});
	    }
	});
}
function saveteamadd(){
	var row = $("#team_add_list").datagrid('getRows');
	var teamadd = new Array();
	for(i=0;i<row.length;i++){
		if(row[i].amount != 0){
			teamadd.push({
				'item_id':row[i].item_id,
				'item_name':row[i].item_name,
				'acc_num':row[i].acc_num,
				'amount':row[i].amount
			});
		}
	}
	
	if(teamadd.length == 0){
		$.messager.alert('提示信息','未录入附加费用，不用保存！','error');
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$.ajax({
		url:'saveteamaccountadd.action',
		type:'post',
		data:{'acc_num':$("#acc_num_add").val(),'teamadds':JSON.stringify(teamadd)},
		success:function(data){
			$.messager.alert('提示信息',data,'info');
			$(".loading_div").remove();
			$('#dlg-fjfy').dialog('close');
			$("#statementsshow").datagrid('reload');
		},
		error:function(){
			$.messager.alert('提示信息','操作失败！','error');
			$(".loading_div").remove();
		}
	});
}
</script>
	<form id="addForm">
	<input type="hidden" id="acc_num_add" value="<s:property value="model.acc_num"/>"/>
<div class="formdiv" style="width:400px;height:235px;">
		<table id="team_add_list"></table>
	</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:saveteamadd()" class="easyui-linkbutton c6" style="width:80px;" >确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-fjfy').dialog('close')">关闭</a>
	</div>
</div>
</form>
