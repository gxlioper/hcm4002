<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	    	getsampItem();
	     }
	}
	getsampItem();
	getsampItemX();
})
 function getsampItem(){
	     $("#item_d").datagrid({
		 url:'getThridLisClassList.action',
		 queryParams:{
			 lisid:$('#lisid').val(),		//检验项目编码
			 lisclassname:$('#lisclassname').val(),		//检验项目名称
	 	 },
		 rownumbers:false,
	     pageSize:15,	     
	    // pageNumber:page,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	            {align:'center',field:'lisid',title:'项目ID',width:18},	
	            {align:'left',field:'lisclassname',title:'项目名称',width:28},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	//singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
	    	singleSelect:false,
		    collapsible:false,
			pagination:true,//分页控件
		    fitColumns:true,//自适应
		    striped:true,
		    fit:true,
	        singleSelect:true,//只允许选择一行
	        onDblClickRow:function(index, row){
	        	closeLisItemPage(row.lisid);
	        },
	        onClickRow:function(index, row){
	        	getsampItemX(row.lisid);
	        }
	});
}
var zhi = "";
function getsampItemX(zhi){
    $("#item_x").datagrid({
	 url:'getThridLisItemList.action',
	 queryParams:{
		 lisid:zhi		//检验项目编码
	 },
	 rownumbers:false,
    pageSize:15,	     
    pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
	 columns:[[
           {align:'center',field:'lisitemid',title:'项目ID',width:18},	
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
      singleSelect:true//只允许选择一行
});
}
function closeLisItemPage(id){
	$('#addexam_num').val(id);
	$('#lis_open').dialog('close');
}
</script>

<fieldset  style=" margin: 10px;margin-bottom:0px;height:50px;">
	<legend><strong>lis组合项目查询</strong></legend>
	  <div class="formDiv"> 
		<dl>
			<dt style="width:80px">组合项目编码</dt>
			<dd>
				<input type="text" class="textinput" id="lisid"  style="height:26px;width:160px;"/>
			</dd>
			<dt style="width:80px">组合项目名称</dt>
			<dd>
				<input type="text"  class="textinput"   id="lisclassname"  style="height:26px;width:160px;"/>
			</dd>
			<dd><a href="javascript:getsampItem();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;height:30px;">查询</a></dd>
		</dl>
	 </div>
</fieldset>
<fieldset style=" margin:5px;margin-bottom:0px; width:47%;float:left;height:75%">
	<legend><strong>项目列表</strong></legend> 
		<table id="item_d"   ></table>
</fieldset>
<fieldset style=" margin:5px;margin-bottom:0px; width:47%;height:75%">
	<legend><strong>检验项目细项列表</strong></legend> 
		<table id="item_x"   >
		</table>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#lis_open').dialog('close')">关闭</a>
	</div>
</div>	
</html>