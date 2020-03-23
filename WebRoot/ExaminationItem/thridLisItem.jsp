<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="s" uri="/struts-tags"	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>检验项目页面</title>
</head>
<script type="text/javascript">
$(document).ready(function(){ 
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	getsampItemX();
	     }
	}
	getsampItem();
	getsampItemX();
})
 function getsampItem(){
		 var str = $('#s_exam_num').val();
		 if(str==""){
			 str = "-tj0077"
		 }
	     $("#item_d").datagrid({
		 url:'getExamintionThridLisItemList.action',
		 queryParams:{
			 lisid:str
	 	 },
		 rownumbers:false,
	     pageSize:15,	     
	    // pageNumber:page,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
				{align:'center',field:'ss',title:'删除',width:10,"formatter":deldetrow},
	            {align:'center',field:'lisitemid',title:'项目编码',width:18},	
	            {align:'left',field:'lisitemname',title:'项目名称',width:28},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:false,
		    fitColumns:true,//自适应
		    striped:true,
		    fit:true,
	        singleSelect:true,//只允许选择一行
	       
	});
}
var row="";
function addrow(row){
	$('#item_d').datagrid('appendRow',row);
}
function deldetrow(value,row,index) {
	return '<a href=\"javascript:s_deldetrow(\''+ index+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
function s_deldetrow(index){
	 //-------------返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
	 //selectchargingItem是datagrid的id
	 var selections  =$('#item_d').datagrid('getSelections');
	 var selectRows = [];
	 for ( var i= 0; i< selections.length; i++) {
	   selectRows.push(selections[i]);
	 }
	 for(var j =0;j<selectRows.length;j++){
	   var index = $('#item_d').datagrid('getRowIndex',selectRows[j]);
	   //执行删除行
	   //selectchargingItem是datagrid的id,index要删除的行
	   $('#item_d').datagrid('deleteRow',index);
	 }
}
var zhi = "";
function getsampItemX(zhi){
    $("#item_x").datagrid({
	 url:'getExamintionThridLisItemTable.action',
	 queryParams:{
		 lisid:$('#c_lisid').val(),	//检验项目编码
		 lisname:$('#c_lisname').val()
	 },
	 rownumbers:false,
    pageSize:15,	     
    pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
	 columns:[[
           {align:'center',field:'lisitemid',title:'项目编码',width:18},	
           {align:'left',field:'lisitemname',title:'项目名称',width:28},
	 	]],	    	
   	onLoadSuccess:function(value){
   		$("#datatotal").val(value.total);
   		$(".loading_div").remove();
   	},
   	singleSelect:false,
    collapsible:false,
	pagination:true,//分页控件
    fitColumns:true,//自适应
    striped:true,
    fit:true,
    singleSelect:true,//只允许选择一行
    onDblClickRow:function(index, row){
    	var r = $('#item_d').datagrid('getRows');
    	var flf = "0";
    	for(var i = 0 ; i < r.length ; i++){
    		if(row.lisitemid==r[i].lisitemid){
    			alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
    			flf = "1";
    			break;
    		}
    	}
    	if( flf == "0"){
	    	addrow(row);
    	}
    }
     
});
}
/**
 * 保存已选择列表
 */
function saveexam_num(){
	var rows = $("#item_d").datagrid('getRows');
	var num = "";
	for(var i = 0 ; i<rows.length ; i++){
		
		if(i==rows.length-1){
			num+=rows[i].lisitemid;
		} else {
			num+=rows[i].lisitemid+"+";
		}
	}
	$('#exam_num').val(num);
	$('#lis_open').dialog('close');
}
</script>
<input type="hidden" id="s_exam_num"   value="<s:property value="exam_num"/>"/>
<fieldset  style=" margin: 5px;height:50px;">
	<legend><strong>检验明细项目查询</strong></legend>
	  <div class="formDiv"> 
		<dl>
			<dt style="width:80px">项目编码</dt>
			<dd>
				<input type="text" class="textinput" id="c_lisid"  style="height:26px;width:150px;"/>
			</dd>
			<dt style="width:80px">项目名称</dt>
			<dd>
				<input type="text"  class="textinput"   id="c_lisname"  style="height:26px;width:150px;"/>
			</dd>
			<dd><a href="javascript:getsampItemX();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;height:30px;">查询</a></dd>
		</dl>
	 </div>
</fieldset>
<fieldset style="width:46%;height:75%;float:left;margin-left:5px;margin-right: 5px;">
	<legend><strong>检验项目明细列表</strong></legend> 
		<table id="item_x"   >
		</table>
</fieldset>
<fieldset style="width:49%;height:75%;">
	<legend><strong>已关联项目明细列表</strong></legend> 
		<table id="item_d"   ></table>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
		 <a href="javascript:saveexam_num();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#lis_open').dialog('close')">关闭</a>
	</div>
</div>	
</html>