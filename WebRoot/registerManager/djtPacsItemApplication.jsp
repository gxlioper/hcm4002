<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
/**
 * EasyUI DataGrid根据字段动态合并单元格
 * param tableID 要合并table的id
 * param colList 要合并的列,用逗号分隔(例如："name,department,office");
 * param mainColIndex 要合并的主列索引
 */
 function mergeCellsByField(tableID, colList, mainColIndex) {
     var ColArray = colList.split(",");
     var tTable = $('#' + tableID);
     var TableRowCnts = tTable.datagrid("getRows").length;
     var tmpA;
     var tmpB;
     var PerTxt = "";
     var CurTxt = "";
     var alertStr = "";
     for (var i = 0; i <= TableRowCnts ; i++) {
         if (i == TableRowCnts) {
             CurTxt = "";
         }
         else {
             CurTxt = tTable.datagrid("getRows")[i][ColArray[mainColIndex]];
         }
         if (PerTxt == CurTxt) {
             tmpA += 1;
         }
         else {
             tmpB += tmpA;
             for (var j = 0; j < ColArray.length; j++) {
                 tTable.datagrid('mergeCells', {
                     index: i - tmpA,
                     field: ColArray[j],
                     rowspan: tmpA,
                     colspan: null
                 });
             }
             tmpA = 1;
         }
         PerTxt = CurTxt;
     }
 }

$(document).ready(function() {
	setpacsitemlistGrid();
});

/**
* 显示pacsitemlist信息
*/
function setpacsitemlistGrid() {
	var model = {
		"exam_id" : $("#exam_id").val(),
		"exam_num" :$("#exam_num").val()
	};
	$("#pacsitemapplist").datagrid({
		url : 'getPacsItemApplicationList.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : false,
		columns : [[ {field:'ck',checkbox:true }, 
			         {align : 'center',field : 'dep_name',title : '科室',width : 25}, 
			         {align : 'center',field : 'item_code',title : '项目编码',width : 15}, 
			         {align : 'center',field : 'item_name',title : '项目名称',width : 15}
			         ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			mergeCellsByField("pacsitemapplist", "code,ck,dep_name",0);
		},
		singleSelect : false,
		collapsible : true,
		pagination : true,
		fitColumns : true,
		autowidth : true,
		striped : true,
		pagination : false,
		selectOnCheck:false
	});
}
//打印申请单
function new_printsqd(){
	var pacsitemlists = $('#pacsitemapplist').datagrid('getChecked');
	var datas = $('#pacsitemapplist').datagrid('getRows');
	if(pacsitemlists.length<=0){
		$.messager.alert("操作提示", "请选择需要打印申请单的科室!","error");
		return;
	}
	var item_codes = new Array();
	for(i=0;i<pacsitemlists.length;i++){
		for(j=0;j<datas.length;j++){
			if(pacsitemlists[i].code == datas[j].code){
				item_codes.push(datas[j].item_code);
			}
		}
	}
	var url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&req&'+$("#exam_num").val()+"&"+item_codes.toString();
	RunServerExe(url);
	$('#dlg-show_sq').dialog('close');
}
function RunServerExe(url){
	location.href=url;
}

</script>
<style>
#mainbar {	width: auto;	height: auto;}
</style>
<input type="hidden" id="exam_id" value="<s:property value="model.exam_id"/>">
<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">
<fieldset style="margin: 5px; padding-right: 0;">
<div align="center">
	<div class="inner-button-box">
	    <a href="javascript:new_printsqd();" class="easyui-linkbutton" style="height:26px;width:50px;">打印</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="height:26px;width:50px;" onclick="javascript:$('#dlg-show_sq').dialog('close');">关闭</a>
	</div>
</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>打申请单</strong>
	</legend>
	<div>
		<table id="pacsitemapplist"></table>
	</div>

</fieldset>
