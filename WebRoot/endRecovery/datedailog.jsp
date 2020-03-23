<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
function yanqile(){
	if($('#riqi').datebox("getValue")==""){
		//document.getElementById("riqi").focus();
		return;
	}
	var id="<s:property value='ids'/>";
	$.ajax({
		url:'updateyanqi.action',
		type:'post',
		data:{
		  ids:id,
		  date:$('#riqi').datebox('getValue')
		},
		success:function(data){
			$('#yq').dialog('close');
			$.messager.alert("提示",data);
			getwjxmGrid();
			gethfqxGrid();
			//重新查询
			chaxun();
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