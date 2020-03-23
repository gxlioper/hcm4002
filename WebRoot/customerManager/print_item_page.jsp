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
    var model = {"ids":examids.toString()};
    $("#print_item_list").datagrid({
	   	 url:'getAllPrintItemByExamids.action',
	   	 dataType: 'json',
	   	 queryParams:model,
	   	 columns:[[
				{field:'ck',checkbox:true },
	   		 	{align:'center',field:'dep_name',title:'科室',width:10},
	   	 	    {align:'center',field:'item_name',title:'项目名称',width:15},
	   	 	    {align:'center',field:'item_code',title:'项目编码',width:15}
	   	 	]],	    	
	   	 onLoadSuccess:function(value){ 
	       	$("#datatotal").val(value.total);
	     },
	     nowrap:false,
	     singleSelect:false,
	   	 collapsible:true,
	   	 pagination: false,
	   	 fitColumns:true,
	   	 fit:true
   });
}

function print_itemss(){
	var barids="";
	var checkedItems = $('#groupusershow').datagrid('getChecked');
    $.each(checkedItems, function(index, item){
        barids=barids+"$"+(item.exam_num);
    });
    if(barids.length>1) barids=barids.substring(1,barids.length);
    
    var printItems = $('#print_item_list').datagrid('getChecked');
    if(printItems.length == 0){
    	$.messager.alert("操作提示", "请选择需要打印条码的项目","error");
    	return;
    }
    
    var itemcodes = new Array();
    $.each(printItems, function(index, item){
    	itemcodes.push(item.item_code);
    });
    
    if($("#barcode_print_type").val() == '1'){//调用旧打印程序
	    var exeurl =$("#barexeurl").val() +" barcode "+barids+" "+itemcodes.toString();
	    RunExe(exeurl);
    }else if($("#barcode_print_type").val() == '2'){
    	var exeurl = 'GuidBarServices://&barcode&'+barids+'&'+itemcodes.toString()+'&1';
    	RunReportExe(exeurl);
    }else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
		$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
	}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
		new_reprinttiaoma();
	}else{
		$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
	}
}

//新补打导检单4.0
function new_reprinttiaoma(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
				+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		var data_print = new Array();
		var ids_print = new Array();
		var checkedItems = $('#djtGselectItemlist').datagrid('getChecked');
		$.each(checkedItems, function(index, item) {
			ids_print.push(item.item_code);
		});
		if (ids_print.length <= 0) {
			$.messager.alert("操作提示", "请选择需要补打的项目", "error");
			return;
		} else {

			var checkedItems = $('#groupusershow').datagrid('getChecked');
			$.each(checkedItems, function(index, item) {
				data.push({
					exam_num : $("#exam_num").val(),
					print_type : 'B',
					charging_item_codes : ids_print.toString(),
					bar_calss : 1,
					arch_bar_num : 1
				});
			});

			$.ajax({
				url : 'saveExamPrintTmp.action',
				data : {
					examPrintTmpLists : JSON.stringify(data)
				},
				type : "post",//数据发送方式   
				success : function(text) {
					if (text.split("-")[0] == 'ok') {
						var printno = text.split("-")[1];
						var url = 'GuidBarServices://&P&1&' + printno;
						RunServerExe(url);
					} else {
						$.messager.alert("操作提示", text, "error");
					}
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");
				}
			});

		}
	}
	
var runserurl;
function RunServerExe(runserurl){
	location.href=runserurl;
}

</script>
<div class="easyui-layout" style="height:315px;">
	<div data-options="region:'center'" border="false">
		<table id="print_item_list">
      	</table>	
	</div>
</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:print_itemss();" class="easyui-linkbutton c6" style="width:100px;">打印</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-printitem').dialog('close');">关闭</a>
	</div>
</div>