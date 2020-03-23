<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>收费项目编辑</title>
</head>
<script type="text/javascript">
$(function(){	
	$('#his_item_class').combobox({
		url : 'getDatadis.action?com_Type=HISZLLB',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name'
	});
	
	$('#his_pric_class').combobox({
		url : 'getDatadis.action?com_Type=HISJBLB',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name'
	});
	
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
	    	item_code_c:$('#his_item_code').val(),
	    	item_class_c:$('#his_item_class').combobox('getValue')
	    },
	    pageSize:10,
	    pageList:[10,20,30,40,50],//可以设置每页记录条数的列表 
	    fitColumns:true,
	    striped:true,
	    singleSelect:true,
	    pagination:true,
	    columns:[[
	        {field:'id',title:'编号',width:10},   
	        {field:'item_code_c',title:'编码',width:50},    
	        {field:'item_class_name',title:'类别',width:50},
	        {field:'price',title:'价格(元)',width:50},
	        {field:'item_name_c',title:'名称',width:100} 
	    ]],
	    onDblClickRow:function(index,row){
	    	$.ajax({
	    		url:'getPriceChingItem.action',
	    		data:{
	    			id:$('#chargitem_id').val(),
	    			item_id_c:row.id,
	    			item_code_c:row.item_code_c,
	    			item_class_c:row.item_class_c,
	    			amount:row.price,
	    			remark:'2'
	    		},
	    		success:function(text){	
	    			if (text.split("-")[0] == 'ok') {
	    			$('#his_price').dialog('close');
	    			window.location.reload(); 
	    			}else{
	    				$.messager.alert("操作提示", text.split("-")[1], "error");
	    			}
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
function  getee(){
 	$('#jp').datagrid({
		 url:'getHisPricelist.action', 
		 queryParams:{
			item_name_c:$('#his_pric_name').val(),
		    item_code_c:$('#his_pric_code').val(),
		    item_class_c:$('#his_pric_class').combobox('getValue')
		 },
		 pageSize:10,
		    pageList:[10,20,30,40,50],//可以设置每页记录条数的列表 
		    fitColumns:true,
		    striped:true,
		    singleSelect:true,
		    pagination:true,
	     columns:[[    
            {field:'id',title:'编号',width:10},   
	        {field:'item_code_p',title:'编码',width:80},  
	        {field:'item_class_name',title:'类别',width:50}, 
	        {field:'price',title:'价格(元)',width:50},
	        {field:'item_name_p',title:'名称',width:150},    
	        {field:'charge_item_spec',title:'规格',width:50},    
	        {field:'units',title:'单位',width:40}	         
	     ]],
	     onDblClickRow:function(index,row){
		    	$.ajax({
		    		url:'getPriceChingItem.action',
		    		data:{
		    			id:$('#chargitem_id').val(),
		    			item_id_c:row.id,
		    			item_code_c:row.item_code_p,
		    			item_class_c:row.item_class_p,
		    			amount:row.price,
		    			remark:'1'
		    		},
		    		success:function(text){	
		    			if (text.split("-")[0] == 'ok') {
		    			$('#his_price').dialog('close');
		    			window.location.reload(); 
		    			}else{
		    				$.messager.alert("操作提示", text.split("-")[1], "error");
		    			}
		    		},
		    		error:function(){
		    			$.messager.alert('警告信息','操作失败','error');
		    		}
		    	})
		    }
	}) 
}
</script>
<input type="hidden" id="id"  value = "<s:property  value='id'/>"  />
<fieldset style=" margin: 10px;margin-bottom:0px; width:47%;float:left;">
	<legend><strong>HIS诊疗项目查询</strong></legend>
	  <div class="formDiv"> 
		<dl>
		    <dt style="width:60px">项目类别</dt>
			<dd>
				<select class="easyui-combobox" id="his_item_class" name="his_item_class"
						data-options="height:26,width:60,panelHeight:'auto'"></select>
			</dd>
			<dt style="width:60px">项目编码</dt>
			<dd>
				<input type="text" class="textinput" id="his_item_code" value="<s:property  value='his_num'/>"  style="height:26px;width:80px;"/>
			</dd>
			<dt style="width:60px">项目名称</dt>
			<dd>
				<input type="text"  class="textinput"   id="his_item_name"  style="height:26px;width:100px;"/>
			</dd>
			<dd><a href="javascript:getHisItem();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:50px;height:30px;">查询</a></dd>
		</dl>
	 </div>
	 </fieldset>
<fieldset style=" margin: 10px;margin-bottom:0px; width:47%;">
	 <legend><strong>价表项目查询</strong></legend>
	   <div class="formDiv"> 
		<dl>
		 <dt style="width:60px">价表类别</dt>
			<dd><select class="easyui-combobox" id="his_pric_class" name="his_pric_class"
						data-options="height:26,width:60,panelHeight:'auto'"></select>				
			</dd>
			<dt style="width:60px">项目编码</dt>
			<dd>
				<input type="text" class="textinput" id="his_pric_code" value="<s:property  value='his_num'/>"  style="height:26px;width:80px;"/>
			</dd>
			<dt style="width:60px">项目名称</dt>
			<dd>
				<input type="text"  class="textinput"   id="his_pric_name"  style="height:26px;width:100px;"/>
			</dd>
			<dd><a href="javascript:getee();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:50px;height:30px;">查询</a></dd>
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
	</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#his_price').dialog('close')">关闭</a>
	</div>
</div>
