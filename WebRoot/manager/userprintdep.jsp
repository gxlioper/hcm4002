	<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

	function f_savePrintDep(){
		var depids = [];
		var dep_row = $("#printdepList").datagrid('getSelections');
		if(dep_row.length == 0){
			$.messager.alert("提示","用户没有选择任何科室！", "warning");
			return ;
		}else{
			for(var i=0;i<dep_row.length;i++){
				depids.push(dep_row[i].dep_id);
			}
			$.ajax({
				url:'printdepsave.action',
				type:'post',
				data:{
			          id:$("#userid").val(),
			          userDepId:depids.toString(),
			          },   
				success:function(data){
					$.messager.alert('提示信息', data,function(){});
					$("#dlg_printdep").dialog('close');
					getGrid();
				},
				error:function(){
					$.messager.alert('提示信息', "用户操作失败！",function(){});
				}
			});			
		}		
	}
	$(function(){
		$('input').attr("maxlength","20");
	})
	
</script>
	<table cellpadding="10" cellspacing="0" width="100%">
		<input type="hidden" value="<s:property value='id' />" name="userid" id="userid" />
		<%-- 	<tr>
				<th width="140" align="right">登录名称：</th>
				<td width="400"><s:property value="gwmc" /></td>
				<th width="80" align="right"><span style="letter-spacing:24px;">姓</span>名：</th>
				<td width="150"><s:property value="gwbm" /></td>
				
			</tr> --%>
			<tr style="height:330px;">
				<th align="right" valign="top" width="80px">选择科室：</th>
				<td valign="top">
 <table class="easyui-datagrid" id="printdepList" data-options="fit:true,singleSelect:false,collapsible:true,url:'getdep_user_print.action?id='+$('#userid').val(),method:'get',fitColumns:true,striped:true,
 onLoadSuccess:function(data){
 	if(data){
 		$.each(data.rows,function(index,item){
 			if(item.checked){
 				$('#printdepList').datagrid('checkRow',index);
 			}
 			
 		});
 	}
 }">
    <thead>
        <tr>
            <th data-options="field:'ck_d',checkbox:true">选择</th>
            <th data-options="field:'dep_name',width:100">科室名称</th>
        </tr>
    </thead>
</table>
				</td>
			</tr>
	</table>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_savePrintDep()">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg_printdep').dialog('close')">取消</a>
	</div>
</div>
