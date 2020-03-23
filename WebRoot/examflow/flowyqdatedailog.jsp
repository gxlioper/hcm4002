<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
function yanqile(){
	if($('#riqi').datebox("getValue")==""){
		//document.getElementById("riqi").focus();
		return;
	}
	var id="<s:property value='id'/>";
	var exam_num="<s:property value='exam_num'/>";
	$.ajax({
		url:'flowupdateyanqi.action',
		type:'post',
		data:{
		  id:id,
		  exam_num:exam_num,
		  fromacc_date:$('#riqi').datebox('getValue')
		},
		success:function(text){
			if (text.split("-")[0] == 'ok') {
				$('#yq').dialog('close');
				getexamallxmGrid();//放弃项目
			} else {
				$.messager.alert("错误信息",text,"error");
			}
		},
		error:function(){
			$.messager.alert("警告","操作失败","error");
		}
	})
}
</script>
<dl >
	<dt>请选择时间</dt>
	<dd>
		<input type="text" id="riqi"  class= "easyui-datebox" required ="required" />
	</dd>
	</dl>
	<div id="search-buttons"  class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:yanqile();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#yq').dialog('close')">关闭</a>
		</div>
</div>