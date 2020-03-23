<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

	function f_savePrintDep(){
		var centers = [];
		var detptocenter = $("#detptocenterlist").datagrid('getSelections');
		if(detptocenter.length == 0){
			$.messager.alert("提示","用户没有选择任何体检中心！", "warning");
			return ;
		}else{
			for(var i=0;i<detptocenter.length;i++){
				centers.push(detptocenter[i].center_num);
			}
			console.log($("#dep_ids").val());
			console.log(detptocenter);
			$.ajax({
				url:'detptoBachcentersave.action',
				type:'post',
				data:{
					  dep_ids:$("#dep_ids").val(),
			          remark:centers.toString()
			          },   
				success:function(data){
					$.messager.alert('提示信息', data,function(){});
					$("#dep_edit").dialog('close');
					getGrid();
				},
				error:function(){
					$.messager.alert('提示信息', "用户操作失败！",function(){});
				}
			});			
		}		
	}
</script>
<input type="hidden" id="dep_ids" value='<s:property value="dep_ids" />'   />
	<table cellpadding="10" cellspacing="0" width="100%">
		<input type="hidden" value="<s:property value='id' />" name="depid" id="depid" />
		<%-- 	<tr>
				<th width="140" align="right">登录名称：</th>
				<td width="400"><s:property value="gwmc" /></td>
				<th width="80" align="right"><span style="letter-spacing:24px;">姓</span>名：</th>
				<td width="150"><s:property value="gwbm" /></td>
				
			</tr> --%>
			<tr style="height:330px;">
				
				<td valign="top">
 <table class="easyui-datagrid" id="detptocenterlist" data-options="fit:true,singleSelect:false,collapsible:true,url:'getdetptocenter.action?id='+$('#depid').val(),method:'get',fitColumns:true,striped:true,
 onLoadSuccess:function(data){
 	if(data){
 		$.each(data.rows,function(index,item){
 			if(item.chi_name=='true'){
 				$('#detptocenterlist').datagrid('checkRow',index);
 			} 			
 		});
 	}
 }">
    <thead>
        <tr>
            <th data-options="field:'center_num',checkbox:true">选择</th>
            <th data-options="field:'center_name',width:100">体检中心</th>
        </tr>
    </thead>
</table>
				</td>
			</tr>
	</table>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_savePrintDep()">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dep_edit').dialog('close')">取消</a>
	</div>
</div>
