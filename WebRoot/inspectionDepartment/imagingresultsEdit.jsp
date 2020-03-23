<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(function(){
		$('#data_name').combobox({
			url :'getPositiveFindList.action',
			editable : true, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'data_name',
			editable:false,
			onLoadSuccess : function(data) {
				
			}
		});
	
   	});
	/*  function savepositive_find(){
			$.ajax({
	 		url:'savePositivefind.action?ids='+s_id.toString(),
	 		data:{
	 			positive_find:$("#data_name").combobox('getValue'),
	 		},
	 		success:function(){
	 			$.messager.alert("提示信息","标记成功");
	 			$("#groupusershow").datagrid('reload');
	 		},
	 		error:function(){
	 			$.messager.alert("提示信息","标记失败","error");
	 		}
	 	});

	} */
 	
</script>	
	<fieldset style="margin:5px;padding-right:20px;height:90%;">
	<legend><strong>标记阳性</strong></legend>
<div class="formdiv">
	    	    <dl>
	    	      <dt>阳性标记</dt>
				  <dd><input Style="height:30px;width:135px;"  id="data_name" /></dd>
	    	     </dl>
	    	     </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;"onclick="savepositive_find()" >保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg_edit').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>

