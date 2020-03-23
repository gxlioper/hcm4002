<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>收费项目编辑</title>
</head>
<script type="text/javascript">
$(function(){
	getHisItem();
	getee();
	$("#his_item_code,#his_item_name").bind('keypress', function (event) {
        if (event.keyCode == "13") {
        	getHisItem();
        }
 	})
})
function getHisItem(){
	$('#his_item').datagrid({    
	    url:'getHisClinicItemVPricelist.action',
	    queryParams:{
	    	item_name_c:$('#his_item_name').val(),
	    	item_code_c:$('#his_item_code').val()
	    },
	    pageSize:10,
	    pageList:[10,20,30,40,50],//可以设置每页记录条数的列表 
	    fitColumns:true,
	    striped:true,
	    singleSelect:true,
	    pagination:true,
	    columns:[[    
	        {field:'item_code_c',title:'诊疗项目编码',width:50},    
	        {field:'item_class_cs',title:'诊疗项目类别',width:50},    
	        {field:'item_name_c',title:'项目名称',width:100},    
	        {field:'input_code_c',title:'项目拼音',width:50}  
	    ]],
	    onClickRow:function(index,row){
	    	$('#hiszongjia').text("");
	    	getee(row.item_code_c);
	    	$.ajax({
	    		url:'getPriceZJ.action',
	    		data:{
	    			item_code_c:row.item_code_c,
	    			item_class_c:row.item_class_c
	    			},
	    		success:function(data){
	    			$('#hiszongjia').text(data);
	    		},
	    		error:function(){
	    			$.messager.alert('警告信息','操作失败','error');
	    		}
	    	})
	    },
	    onDblClickRow:function(index,row){
	    	$('#hiszongjia').text("");
	    	getee(row.item_code);
	    	$.ajax({
	    		url:'getPriceZJ.action',
	    		data:{
	    			item_code_c:row.item_code_c,
	    			item_class_c:row.item_class_c
	    			},
	    		success:function(data){
	    			$('#amount').val(data);
	    			$('#addhis_num').val(row.item_code_c);
	    			$('#item_class').val(row.item_class_c);
	    			$('#item_classs').val(row.item_class_cs);
	    			$('#his_price').dialog('close');
	    			//$('#addhis_num').focus();
	    		},
	    		error:function(){
	    			$.messager.alert('警告信息','操作失败','error');
	    		}
	    	})
	    }
	}); 
}
/**
 * HIS价表
 */
var rowcode="";
function  getee(rowcode){
 	$('#jp').datagrid({
		 url:'getHisPreci.action', 
		 queryParams:{charge_item_code:rowcode},
		 fitColumns:true,
		 singleSelect:true,
	     columns:[[    
	        {field:'item_code_p',title:'项目编码',width:50},    
	        {field:'item_name_p',title:'项目名称',width:100},    
	        {field:'price',title:'价格(元)',width:50}   
	     ]]
	}) 
}
</script>
<fieldset  style=" margin: 10px;margin-bottom:0px;">
	<legend><strong>HIS诊疗项目查询</strong></legend>
	  <div class="formDiv"> 
		<dl>
			<dt style="width:80px">项目编码</dt>
			<dd>
				<input type="text" class="textinput" id="his_item_code"  style="height:26px;width:160px;"/>
			</dd>
			<dt style="width:80px">项目名称</dt>
			<dd>
				<input type="text"  class="textinput"   id="his_item_name"  style="height:26px;width:160px;"/>
			</dd>
			<dd><a href="javascript:getHisItem();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;height:30px;">查询</a></dd>
		</dl>
	 </div>
</fieldset>
<fieldset style=" margin: 10px;margin-bottom:0px; width:47%;float:left;">
	<legend><strong>HIS诊疗项目列表</strong></legend> 
		<table id="his_item"  style="height:365px" ></table>
</fieldset>
<fieldset style=" margin: 10px;margin-bottom:0px; width:47%;">
	<legend><strong>项目价表</strong></legend> 
		<table id="jp"  style="height:340px" >
		</table>
		<div style="float: right;line-height:25px;"><font color="#ff0000" size="4">总价：<span id="hiszongjia"></span>元</font></div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#his_price').dialog('close')">关闭</a>
	</div>
</div>
