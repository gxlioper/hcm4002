<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" >
	$(function(){
		getconbobox();
	})
	function getconbobox(){
		$('#fenlei').combobox({    
		    url:'getdepExaminationItemList.action?cen=2&id='+$('#charging_item_id').val(),    
		    valueField:'id',    
		    textField:'text',
		    mode:'remote',
		    required:true,
		    onLoadSuccess:function(){
		    	
		    }
		});
	}
	function save(){
		$('#fenlei').combobox('getValue')
		if($('#fenlei').combobox('getValue')<=0){
			$('#fenlei').textbox('textbox').focus();
			return;
		}
		
		var model = new Object();
		model.item_class_id = $('#fenlei').combobox('getValue');
		model.ids = $('#ids').val();
		
		$.ajax({
			url:'shezhileibie.action',
			data:model,
			type:'post',
			success:function(data){
				$.messager.alert("提示信息","保存成功","info");
				$('#dlg-custedit').dialog('close');
				getgroupuserGridss();
			},
			error:function(){
				$.messager.alert("警告信息","操作失败！","error");
			}
		})
		
	}
</script>
<input type="hidden"  id="ids" value="<s:property value='ids'/>"  />
<input type="hidden"  id="charging_item_id" value="<s:property value='charging_item_id'/>"  />
		<div class="formdiv"  style="margin-top: 50px;">
  			<dl>
  				<dt>类别：</dt>
  				<dd><input type="text" id="fenlei" style="width: 290px;height: 26px;" /></dd>
  			</dl>
	   	</div>
<div class="dialog-button-box">
	<div class="inner-button-box" >
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="save();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>

