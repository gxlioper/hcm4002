<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript">
$(document).ready(function() {
	get_print_item_list();
});

function get_print_item_list(){
	var examids = new Array();
	var checkedItems = $('#groupusershow').datagrid('getChecked');
    $.each(checkedItems, function(index, item){
        examids.push(item.exam_num);
   	});
    var model = {"examinfo_ids":examids.toString()};
    $("#sample_item_list").datagrid({
	   	 url:'getTeamSampleListItem.action',
	   	 dataType: 'json',
	   	 queryParams:model,
	   	 columns:[[
				{field:'ck',checkbox:true },
	   		 	{align:'center',field:'demo_name',title:'样本名称',width:10},
	   		 	{align:'center',field:'item_code',title:'项目编码',width:15},
	   	 	    {align:'center',field:'item_name',title:'项目名称',width:15}
	   	 	]],	    	
	   	 onLoadSuccess:function(value){ 
	       	$("#datatotal").val(value.total);
	       	MergeCells('sample_item_list', 'sample_id,ck,demo_name',0);
	     },
	     nowrap:false,
	     singleSelect:false,
	   	 collapsible:true,
	   	 pagination: false,
	   	 fitColumns:true,
	   	 fit:true
   });
}
//显示已选择样本页面
function show_sample_list(){
	var row = $('#sample_item_list').datagrid('getChecked');
	if(row.length < 2){
		$.messager.alert('提示信息', '请选择两个以上的样本!','error');
		return;
	}
	$("#dlg-hbyb").dialog("open");
	$("#dlg-hbyb").dialog("center");
	$('#qzhbyb_list').datagrid({
		dataType: 'json',
		data:row,    
	    columns:[[    
	        {field:'demo_name',title:'样本名称',width:100}
	    ]],
	    singleSelect:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		nowrap:false
	}); 
}

function save_hebin_sample(){
	var row = $('#qzhbyb_list').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示信息', '请选择合并目标样本!','error');
		return;
	}
	var data = $('#qzhbyb_list').datagrid('getRows');
	var olds = new Array();
	for(i=0;i<data.length;i++){
		if(row.sample_id != data[i].sample_id){
			olds.push(data[i].sample_id);
		}
	}
	
	var examids = new Array();
	var checkedItems = $('#groupusershow').datagrid('getChecked');
    $.each(checkedItems, function(index, item){
        examids.push(item.exam_num);
   	});
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'saveTeamSampleListItem.action',  
        data:{'examinfo_ids':examids.toString(),'sampleId':row.sample_id,'old_sampleIds':olds.toString()},          
        type: "post",//数据发送方式   
        success: function(data){
        	$(".loading_div").remove();
        	$.messager.alert('提示信息', data);
        	$("#dlg-hbyb").dialog("close");
        	$("#dlg-printitem").dialog("close");
        },
        error:function(data){
        	$(".loading_div").remove();
        	$.messager.alert('提示信息', data,function(){});
        }
	});
}

/**
2         * EasyUI DataGrid根据字段动态合并单元格
3         * @param fldList 要合并table的id
4         * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
5         */
function MergeCells(tableID, fldList,zhuIndex) {
	var Arr = fldList.split(",");
	var dg = $('#' + tableID);
	var fldName;
	var RowCount = dg.datagrid("getRows").length;
	var span;
	var PerValue = "";
	var CurValue = "";
	var length = Arr.length - 1;
	for (i = length; i >= 1; i--) {
		z_fldName = Arr[zhuIndex];
		fldName = Arr[i];
		PerValue = "";
		span = 1;
		for (row = 0; row <= RowCount; row++) {
			if (row == RowCount) {
				CurValue = "";
			}else {
				CurValue = dg.datagrid("getRows")[row][z_fldName];
			}
			if (PerValue == CurValue) {
				span += 1;
			}else {
				var index = row - span;
				dg.datagrid('mergeCells', {
					index: index,
					field: fldName,
					rowspan: span,
					colspan: null
				});
				span = 1;
				PerValue = CurValue;
			}
		}
	}
}
</script>

<div class="easyui-layout" style="height:415px;">
	<div data-options="region:'center'" border="false">
		<table id="sample_item_list">
      	</table>	
	</div>
</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:show_sample_list();" class="easyui-linkbutton c6" style="width:100px;">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-printitem').dialog('close');">关闭</a>
	</div>
</div>
<div id="dlg-hbyb" class="easyui-dialog"  data-options="width: 300,height: 400,closed: true,cache: false,modal: true,title:'批量合并样本'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<div style="margin-left:20px;">请选择合并目标样本:</div>
			<div style="height:260px;width:260px;margin-left:20px;">
				<table id="qzhbyb_list"></table>
			</div>
		</div>
		</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_hebin_sample();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-hbyb').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>