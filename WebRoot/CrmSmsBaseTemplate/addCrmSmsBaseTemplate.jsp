<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">

$(function (){
	$('#sms_name').validatebox({
		 required: true
	})
	$('#sms_category').combobox({
		url:'getDatadis.action?com_Type=DXMBLX',    
		valueField:'id',    
		textField:'name',
	   panelHeight:'auto',
	   required:true,
	   onLoadSuccess: function(rec){    
           var data = $('#sms_category').combobox('getData');  
           $('#sms_category').combobox('setValue',data[0].id); 
           for(var i = 0 ; i < data.length ; i++){
        	   if(data[i].id=="<s:property value='m.sms_category'/>"){
        		   $('#sms_category').combobox('setValue',data[i].id);
        		   break;
        	   }
           }
       }
	})
	$('#neirong').datagrid({
		url:'getDatadis.action?com_Type=DXMBNR',
		columns:[[
			    {align:'center',field:'name',title:'内容',width:10},	
			 	]],
		 	  fitColumns:true,
		    striped:true,
		    fit:false,
		    onDblClickRow:function(index, row){
		    	butt(row);
	        }
	})
	
})
function butt(row){
	var neirong = $('#sms_note').val();
	var data = neirong+row.remark;
	$('#sms_note').val(data)
	$('#sms_note').focus();
}
function baocunmoban(){
	if($('#sms_name').val()==""){
		$('#sms_name').focus();
		return;
	}
	if($('#sms_category').combobox('getValue')==""){
		$('#sms_category').textbox('textbox').focus();
		return;
	}
	if($('#sms_note').val()==""){
		$.messager.alert("警告信息","模板内容为必填项","error");
		return;
	}
	var model = {
			id:$('#id').val(),
			sms_name:$('#sms_name').val(),
			sms_note:$('#sms_note').val(),
			sms_category:$('#sms_category').combobox('getValue'),
			sms_state:$('input[name="sms_state"]:checked').val()
	}
	$.ajax({
		url:'saveCrmSmsBaseTemplate.action',
		data:model,
		type:'post',
		success:function(data){
			$.messager.alert("提示信息","保存成功！","info");
			$('#dlg-custedit').dialog('close')
			getgroupuserGrid();
		},error:function(){
			$.messager.alert("警告信息","操作失败！","error");
		}
	})
}

</script>

<input type="hidden"  id ="state"  value="<s:property value='m.sms_state'/>"  />
<input type="hidden"  id="id"  value="<s:property value='id'/>"/>
 <fieldset style=" margin: 10px;">
	<legend><strong>短信模板</strong></legend> 
	<div class="formDiv" style=" margin-top: 15px">
		<dl>
			<dt style="width: 100px;">
				模板名称:
			</dt>
			<dd>
				<input type="text" class="textinput" id="sms_name"  value="<s:property value='m.sms_name'/>"  style="width:350px;height: 26px;"  />
			</dd>
		</dl>
		<dl >
			<dt  style="width: 100px;">
				模板分类 :
			</dt>
			<dd>
				<input type="text"    id="sms_category"  style="width:150px;height: 26px;"  />
			</dd>
			<dt style="width: 80px;">默认模板</dt>
			<dd>
				<s:if test="m.sms_state eq \"Y\"" >
					<input type="radio" value="Y"  name="sms_state" checked="checked"   />是&nbsp;&nbsp;
					<input type="radio" value='N'  name="sms_state"  />否
				</s:if>
				<s:else>
					<input type="radio" value="Y"  name="sms_state"  />是&nbsp;&nbsp;
					<input type="radio" value='N'  name="sms_state"  checked="checked" />否
				</s:else>
			</dd>
		</dl> 
		<dl style="width: 600px;">
			<dt style="width: 100px;">模板内容:
			</dt>
			<dd >
				<textarea rows="" cols=""  id="sms_note"  style="width:350px;height:200px;resize: none"><s:property value='m.sms_note'/></textarea>
			</dd>
			<dd>
				<table id = "neirong"  style="width: 100px;height:200px;" >
			</table>
			</dd>
		</dl>
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:baocunmoban();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>