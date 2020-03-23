<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	sample_id = '<s:property value="sample_id"/>';
	getWordsGrid(sample_id);
	huiche(sample_id);
	var view_words_add = getCookie("view_words_add");
	if(view_words_add == null){
		$('#view_words_add').attr("checked", false);
	}else{
		$('#view_words_add').attr("checked", true);
	}
});
function checkthedefault(obj){
	if($(obj)[0].checked){
		setCookie($(obj).attr('id'),1);
	}else{
		delCookie($(obj).attr('id'));
	}
}
//回车事件
function huiche(sample_id){
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	getWordsGrid(sample_id);
		}
	}
}

function getWordsGrid(id){
	var model={"sample_id":id,"exam_result":$('#cxcyc').val()};
	$("#view_words_list").datagrid({
			 url:'getViewExamWords.action',
			 dataType: 'json',
			 queryParams:model,
			 //rownumbers:true,
			 columns:[[
			    {align:'',field:'exam_desc',title:'描述',width:10},
			 	{align:'',field:'exam_result',title:'结论',width:20,"formatter":f_add},
			    {align:'',field:'exam_descs',title:'描述',width:10},
			 	{align:'',field:'exam_results',title:'结论',width:20,"formatter":f_adds}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    	},
		    	singleSelect:false,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false/* ,
			     onDblClickRow:function(rowIndex, rowData){
			    	var exam_desc = '';
			    	var exam_result = '';
			    	if($("#view_words_add")[0].checked){
			    		exam_desc = $("#miao_"+sample_id).val();
			    		exam_result = $("#jielun_"+sample_id).val();
			    	}
					$("#jielun_"+sample_id).val(exam_result + rowData.exam_result+'\n');
					$("#miao_"+sample_id).val(exam_desc + rowData.exam_desc+'\n');
			    	$('#dlg-edit').dialog('close');
			    }  */
	/* 		    onDblClickCell: function(index,field,value){
			    	var exam_desc = '';
			    	var exam_result = '';
			    	if($("#view_words_add")[0].checked){
			    		exam_desc = $("#miao_"+sample_id).val();
			    		exam_result = $("#jielun_"+sample_id).val();
			    	}
					if(field=="exam_desc"){
						$("#miao_"+sample_id).val(exam_desc + value+'\n');
					}else if(field=="exam_result"){
						$("#jielun_"+sample_id).val(exam_result + value+'\n');
					}else if(field=="exam_descs"){
						$("#miao_"+sample_id).val(exam_desc + value+'\n');
					}else if(field=="exam_results"){
						$("#jielun_"+sample_id).val(exam_result + value+'\n');
					}

					$('#dlg-edit').dialog('close');
				} */

	});
}
var examdesc="";
var examresult="";
function f_add(val,row){
	examdesc=row.exam_desc;
	examresult=row.exam_result;
	return '<a href="javascript:void(0)" onclick = "showCyc(\''+examdesc+'\',\''+examresult+'\')">'+examresult+'</a>';
}

function showCyc(exam_desc,exam_result){
	var desc = '';
	var result = '';
	if($("#view_words_add")[0].checked){
		desc = $("#miao_"+sample_id).val();
		result = $("#jielun_"+sample_id).val();
	}
	$("#jielun_"+sample_id).val(result + exam_result+'\n');
	$("#miao_"+sample_id).val(desc + exam_desc+'\n');
	$('#dlg-edit').dialog('close');
}

function f_adds(val,row){
	return '<a href="javascript:void(0)" onclick = "showCycs(\''+row.exam_descs+'\',\''+row.exam_results+'\')">'+row.exam_results+'</a>';
}

function showCycs(exam_desc,exam_result){
	var desc = '';
	var result = '';
	if($("#view_words_add")[0].checked){
		desc = $("#miao_"+sample_id).val();
		result = $("#jielun_"+sample_id).val();
	}
	$("#jielun_"+sample_id).val(result + exam_result+'\n');
	$("#miao_"+sample_id).val(desc + exam_desc+'\n');
	$('#dlg-edit').dialog('close');
}

var sample_id;
function quedingxuanze(){
	var row = $("#view_words_list").datagrid('getSelections');
	if(row.length == 0){
		$.messager.alert("操作提示","请选择一条常用词!", "error");
		return;
	}
	var exam_desc = '';
	var exam_result = '';
	if($("#view_words_add")[0].checked){
		exam_desc = $("#miao_"+sample_id).val();
		exam_result = $("#jielun_"+sample_id).val();
	}
	for(i=0;i<row.length;i++){
		if(row[i].exam_result != ''){
			exam_result +=row[i].exam_result+'\n';
		}
		if(row[i].exam_desc != ''){
			exam_desc += row[i].exam_desc+'\n';
		}
	}
	$("#jielun_"+sample_id).val(exam_result);
	$("#miao_"+sample_id).val(exam_desc);
	$('#dlg-edit').dialog('close');
}
</script>
<fieldset style="margin:0px;height:50px;">
			<legend><strong>结论检索</strong></legend>
			<div class="user-query">
 			<dl>
 				<dt style="width: 50px">结论：</dt>
 				<dd><input type="text" id='cxcyc' class="textinput" /></dd>
 				<dd>
 					<a href="javascript:getWordsGrid('<s:property value="sample_id"/>');"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;height:30px;">查询</a>
 				</dd>

 			</dl>
			</div>
   </fieldset>
   <fieldset style="margin:0px;padding-bottom:30px; height:700px;">
   		<legend><strong>项目常用词列表</strong></legend>
   				<table id='view_words_list' style="width:100px;height: 100%;margin-right: 0px"></table>
   </fieldset>
<div class="dialog-button-box">
	<div class="inner-button-box">
		<input type="checkbox" id = "view_words_add" onclick="checkthedefault(this)"/>是否追加结果
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:quedingxuanze();">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>