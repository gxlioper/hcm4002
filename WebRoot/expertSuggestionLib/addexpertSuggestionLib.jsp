<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	adddep_idcombox();
	$('#addsugg_word,#addsugg_content').validatebox({    
	    required: true,    
	}); 
	 //$('#addsugg_content').textbox('textbox').attr('maxlength',5);
	// $('.textbox-text ').attr('maxlength',5);
/* 	var t=$('#addsugg_content').val();
	t.textbox('textbox').bind('keydown', function(e){
		if (e.keyCode == 13){	// 当按下回车键时接受输入的值。
			t.textbox('setValue', $(this).val());
		}
	}); */
})
/**
 * 科室下拉框
 */
function adddep_idcombox(){
	$('#adddep_id').combobox({
		url:'getDepartment_dep.action',
		valueField: 'id',    
        textField: 'dep_name',
        onLoadSuccess : function(){//下拉框默认选择
   	       var val = $(adddep_id).combobox('getData');
       	   for (var item in val[0]) {  
                  if (item =='id') {
                	var dep_id="<s:property value='dep_id'/>";
                    if(dep_id>0){
               		   $(this).combobox('select',dep_id);//设置你找选中的
               	   }else{
               		  $(this).combobox('select', val[0][item]);//默认选择第一条
               	   }
                  }  
              }  
   	    }
	})
}
/**
 * 添加专科建议知识库
 */
function addExpert(){
	if($('#addsugg_word').val()==""){
		$('#addsugg_word').focus();
		return;
	} 
	if($('#addsugg_content').val()==""){
		$('#addsugg_content').focus();
		return;
	}
	$.ajax({
		url:'addExpertSuggestionLib.action',
		type:'post',
		data:{
		id:$('#id').val(),
		dep_id:$('#adddep_id').combobox('getValue'),
		sugg_word:$('#addsugg_word').val(),
		sugg_content:$('#addsugg_content').val()
		},
		success:function(data){
			$.messager.alert('提示信息',data,'info');
			$('#dlg-custedit').dialog('close');
			getItemResultLib();
		},
		error:function(){
			$.messager.alert('提示信息','操作失败','error');
		}
	})
}
</script>
<fieldset style=" margin: 10px;">
	<legend><strong>编辑专科建议</strong></legend> 
	<div class="formDiv">
		<dl>
			<dt style="width: 120px">科室</dt>
			<dd>
				<input type="hidden" id='id' value="<s:property value='model.id'/>"/>
				<input type="text"  class="easyui-combobox" id="adddep_id"  style="height: 26px; width: 300px;"/>
				<font color="#ff0000">*</font>
			</dd>
		</dl>
		<dl>
			<dt  style="width: 120px">建议名词</dt>
			<dd>
				<input type="text"  class="textinput" id="addsugg_word"  maxlength="20" value="<s:property value='sugg_word'/>"
				 style="height: 26px; width: 300px;"/>
				<font color="#ff0000">*</font>
			</dd>
		</dl>
		<dl style="height: 100px">
			<dt style="width: 120px">建议内容</dt>
			<dd>
				<!-- id="addsugg_content"  -->
				<textarea rows="4" cols="33"  id="addsugg_content"   innerText='1'  ><s:property value='sugg_content'/></textarea>
			<font color="#ff0000">*</font>
			</dd>
		</dl>
	</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addExpert();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>
</fieldset>