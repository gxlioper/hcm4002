<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function (){
	$('#item_class').val($('#h_item_class').val())
})
function saveMedicalPrice(){
 	var model = {
			id:$('#id').val(),
			item_class:$('#item_class').val(),
			item_name:$('#item_name').val(),
			item_spec:$('#item_spec').val(),
			price:$('#price').val(),
			input_code:$('#input_code').val(),
			memo:$('#memo').val(),
			units:$('#units').val()
	} 
 	$.ajax({
		url:'saveMedicalPriceCharge.action',
		data:model,
		type:'post',
		success:function(text){
			$("#chargItemList_city").datagrid('reload');
			$('#dlg-custedit').dialog('close'); 
			$.messager.alert('提示信息',text,"info")
		},
		error:function(){
			$.messager.alert('提示信息',"操作失败","error")
		}
	})  
}
</script>
<input type="hidden" id="id"  value="<s:property value='id'/>"/>
<input type="hidden" id="h_item_class"  value="<s:property value='item_class'/>"/>
 <fieldset style=" margin: 10px;">
	<legend><strong>价表编辑</strong></legend> 
 	<div class="user-query">
		<dl >
			<dt>
				价表类型:
			</dt>
			<dd>
				<select id="item_class" style="height: 26px; width: 244px;"  value="<s:property value="item_class"/>"   name="item_class">
					<option value ="A">西药</option>
					<option value ="B">中药</option>
					<option value ="C">检验</option>
					<option value ="D">检查</option>
					<option value ="E">治疗</option>
					<option value ="F">手术</option>
					<option value ="G">麻醉</option>
					<option value ="H">护理</option>
					<option value ="I">膳食</option>
					<option value ="">其他</option>
				</select>
			</dd>
			<dt>
				名称 :
			</dt>
			<dd>
				<input   type="text" id="item_name" 
				 	value="<s:property value='item_name'/>" 
					style="height: 26px; width: 244px;"  class="textinput"/>
			</dd>
		</dl>
		<dl >
			<dt>规格:</dt>
			<dd>
				<input  type="text" class="textinput"  id="item_spec"
					value="<s:property value="item_spec"/>" class="easyui-validatebox"  
					style="height: 26px; width: 244px;" />
			</dd>
			<dt>单位:</dt>
			<dd>
				<input  type="text" class="textinput"  id="units"
					value="<s:property value="units"/>" class="easyui-validatebox"  
					style="height: 26px; width: 244px;" />
			</dd>
		</dl>
		<dl >		    
			<dt>助记码</dt>
			<dd>
				<input   type="text" maxlength="45" id="input_code"
				value="<s:property value="input_code"/>"  class="textinput"	style="height: 26px; width: 244px;"/>
			</dd>	
			<dt>金额:</dt>
			<dd>
				<input  type="text" class="textinput"  id="price"
					value="<s:property value="price"/>" 
					style="height: 26px; width: 244px;" />
			</dd>
		</dl>
		<br/>
		<dl>
			<dt>备注</dt>
			<dd>
				<input   type="text" maxlength="45" id="memo"
				value="<s:property value="memo"/>"  class="textinput"	style="height: 26px; width:605px;"/>
			</dd>		
		</dl>
	</div> 
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:saveMedicalPrice();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>