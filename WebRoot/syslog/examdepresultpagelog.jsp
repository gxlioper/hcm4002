<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	getExamdepGrid();
});
function getExamdepGrid(){
	$("#dep_list").datagrid({
		 url:'getExamDepResultCountLog.action?exam_num='+$("#exam_num").val(),
		 dataType: 'json',
		 columns:[[
		    {align:'center',field:'dep_name',title:'科室名称',width:20},
		 	{align:'center',field:'check_count',title:'检查次数',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
			rownumbers:true,
	    	singleSelect:true,
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    fit:true,
		    striped:true,
		    onDblClickRow : function(rowIndex, rowData){
		    	createtab(rowData.id,rowData.check_count);
		    }
	});
}
function createtab(dep_id,check_count){
	var tabs = $('#tab').tabs('tabs');
	for(i=tabs.length;i>0;i--){
		$('#tab').tabs('close',0);
	}
	for(i=1;i<=check_count;i++){
		$('#tab').tabs('add',{    
		    title:'第('+i+")次检查",    
		    content:'<table id="resultlist'+i+'"></table>'   
		});
		getptGrid(dep_id,i);
	}
}

//获取普通科室检查结果
function getptGrid(dep_id,check_count){
	 var model={'id':$("#examinfo_id").val(),'exam_num':$("#exam_num").val(),"dep_id":dep_id,'check_count':check_count};
	 var gredid = 'resultlist'+i;
	 $("#"+gredid).datagrid({
		 url:'getCommonExamDetailListLog.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:true,
		 border:false,
		 columns:[[
		           {align:'center',field:'item_name',title:'检查项目',width:15,"styler":f_color_pt},
		           {align:'center',field:'exam_result',title:'检查结果',width:25,"styler":f_color_pt,"formatter":f_result_pt},
		           {align:'center',field:'exam_desc',title:'上次检查结果',width:25,"styler":f_color_pt,"formatter":f_result_pt1},
		           {align:'center',field:"exam_doctor","title":"检查医生","width":10},
		           {align:'center',field:"exam_date","title":"检查时间","width":15}
		 ]],	    	
		 onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		MergeCells(gredid, 'exam_doctor,exam_date');
		 },
		 singleSelect:true,
		 collapsible:true,
		 pagination: false,
		 fitColumns:true,
		 striped:true,
		 fit:true,
		 nowrap:false
 });
}
function f_color_pt(value,row,index){
	if(row.item_id == '0'){
		return 'background:#FEEAA8;color:#ff5100;';
	}
	if(row.exam_status == 'XG'){
		return 'color:#F00;';
	}
}

function f_result_pt(value,row,index){
	if(row.item_id == '0'){
		return row.exam_result.replace(/\n/g,'</br>');
	}else{
		return row.exam_result;
	}
}
function f_result_pt1(value,row,index){
	if(row.item_id == '0'){
		return row.exam_desc.replace(/\n/g,'</br>');
	}else{
		return row.exam_desc;
	}
}
/**
2         * EasyUI DataGrid根据字段动态合并单元格
3         * @param fldList 要合并table的id
4         * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
5         */
      function MergeCells(tableID, fldList) {
            var Arr = fldList.split(",");
             var dg = $('#' + tableID);
            var fldName;
             var RowCount = dg.datagrid("getRows").length;
            var span;
            var PerValue = "";
            var CurValue = "";
             var length = Arr.length - 1;
             for (i = length; i >= 0; i--) {
               fldName = Arr[i];
                PerValue = "";
                span = 1;
                for (row = 0; row <= RowCount; row++) {
                    if (row == RowCount) {
                        CurValue = "";
                    }
                    else {
                        CurValue = dg.datagrid("getRows")[row][fldName];
                    }
                     if (PerValue == CurValue) {
                        span += 1;
                    }
                     else {
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
<form id="add1Form">
<input id="exam_num" type="hidden" value="<s:property value="model.exam_num"/>"/>
	<div class="easyui-layout" style="height:410px;width:1200px;">
		<div data-options="region:'west',title:'科室信息',split:true" style="width:300px;">
			<table id="dep_list"></table>
		</div>
		<div data-options="region:'center',title:'项目检查信息'">
			<div id="tab" class="easyui-tabs" data-options="fit:true,border:false">
			</div>
		</div> 
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-s').dialog('close')">关闭</a>
	</div>
</div>
</form>