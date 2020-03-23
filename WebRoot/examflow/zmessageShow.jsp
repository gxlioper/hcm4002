<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
$(function(){
	$("#touserid").combobox({
		url:'getZDSDoctors.action?id='+$('#messitem_id').val(),
		valueField:'id',    
	    textField:'chi_Name', 
	    Enabled:true,
	    onLoadSuccess:function(){
	    	 var data= $(this).combobox("getData");
                if (data.length > 0) {
                $('#touserid').combobox('select', data[0].id);
	            }
           }
	});	
    }); 

//
function messageSavezds(id){
	    $.ajax({
   		url:'zlxmessagesave.action',
   		data : {
   			remark:$("#message").val(),
   			user_id:document.getElementsByName("touserid")[0].value,
		    exam_num:$('#exam_num').val(),
		    id:$('#messitem_id').val(),
		},
   		type:'post',
   		success:function(text){   			
   			getexamallxmGrid();   			
			if (text.split("-")[0] == 'ok') {
				//alert_autoClose("操作提示", text.split("-")[1],"");
				$('#dlg-message').dialog('close');
			} else {
				$.messager.alert("错误信息",text,"error");
			}
   		},
   		error:function(){
   			$.messager.alert("操作提示", "操作失败！", "error");
   		}
   		});
} 
</script>
     <input type="hidden"   id='messitem_id'  value='<s:text name="id"/>'  />
     <input type="hidden" id="exam_num" value='<s:text name="exam_num"/>'/>
			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 180px; margin-right: 10px" >
    			<legend><strong>信息发送</strong></legend>
				<div class="user-query">
					<dl>
					<dt style="height:26px;width:80px;">选择人员</dt>
			         <dd><select class="easyui-combobox" id="touserid" name="touserid" data-options="height:26,width:100,panelHeight:'auto'"></select></dd> 
					</dl>
					<dl>
					<dt style="height:26px;width:80px;">信息内容</dt>
			        <dd><textarea id="message" name="message" rows="4" cols="25"><s:property value="message"/></textarea></dd> 
					</dl>
				</div>
 			</fieldset>
 			<div id="search-buttons"  class="dialog-button-box">
	   <div class="inner-button-box">
	    <a href="javascript:messageSavezds();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-message').dialog('close')">关闭</a>
	</div>
</div>