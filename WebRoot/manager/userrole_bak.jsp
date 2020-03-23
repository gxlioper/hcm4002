	<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function xuanzhong(data){
	for(var i=0;i<data.length;i++){
		if(data[i].check=='1'){
			$('#ExamLists').datagrid('checkRow',data[i].id);
		}
		for(var j=0;j<data[i].children.length;j++){
			if(data[i].children[j].check=='1'){
				$('#ExamLists').datagrid('checkRow',data[i].children[j].id);
			}
			for(var k=0;k<data[i].children[j].children.length;k++){
				if(data[i].children[j].children[k].check=='1'){
					$('#ExamLists').datagrid('checkRow',data[i].children[j].children[k].id);
				}
			}
		}
	}
}
	function f_saveUserJob(){
		var ids=[];
		var depids = [];
		var centerid=[];
		var rows=$("#jobList").datagrid('getSelections');
		var dep_row = $("#depList").datagrid('getSelections');
		var centers=$("#ExamLists").treegrid('getSelections');
		if(rows.length==0){
			//alert('用户没有选择任何岗位!');
			$.messager.alert("提示","用户没有选择任何岗位！", "warning");
			return ;
		}else if(dep_row.length == 0){
			$.messager.alert("提示","用户没有选择任何科室！", "warning");
			return ;
		}else{
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
			}
			for(var i=0;i<dep_row.length;i++){
				depids.push(dep_row[i].dep_id);
			}
			for(var i=0;i<centers.length;i++){
				centerid.push(centers[i].id);
			}
			$.ajax({
				url:'userrolesave.action',
				type:'post',
				data:{
			          id:$("#userid").val(),
			          remark:ids.toString(),
			          userDepId:depids.toString(),
			          center:centerid.toString(),
			          },   
				success:function(data){
					
					$.messager.alert('提示信息', data,function(){});
					$("#dlg-s").dialog('close');
					getGrid();
				},
				error:function(){
					//alert("用户操作失败！");
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
		<input type="hidden" value="<s:property value='id' />" name="userid" id="userid"/>
			<tr>
				<th width="80" align="right">登录名称：</th>
				<td width="150"><s:property value="gwmc" /></td>
			<!-- </tr>
			<tr> -->
				<th width="80" align="right"><span style="letter-spacing:24px;">姓</span>名：</th>
				<td width="150"><s:property value="gwbm" /></td>
				<th width="100"></th>
				<td width="180"></td>
			</tr>
			<tr style="height:250px;">
				<th align="right" valign="top">选择角色：</th>
				<td >
 <table class="easyui-datagrid" id="jobList" data-options="fit:true,singleSelect:false,collapsible:true,url:'getroleAll.action?id='+$('#userid').val(),method:'get',fitColumns:true,striped:true,
 onLoadSuccess:function(data){
 	if(data){
 		$.each(data.rows,function(index,item){
 			if(item.selected=='checked'){
 				$('#jobList').datagrid('checkRow',index);
 			}
 			
 		});
 	}
 }">
    <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true">选择</th>
            <th data-options="field:'rolename',width:80">角色名称</th>
        </tr>
    </thead>
</table>
				</td>
				<th align="right" valign="top">选择科室：</th>
				<td valign="top">
 <table class="easyui-datagrid" id="depList" data-options="fit:true,singleSelect:false,collapsible:true,url:'getAllDepAndDepUser.action?id='+$('#userid').val(),method:'get',fitColumns:true,striped:true,
 onLoadSuccess:function(data){
 	if(data){
 		$.each(data.rows,function(index,item){
 			if(item.checked){
 				$('#depList').datagrid('checkRow',index);
 			}
 			
 		});
 	}
 }">
    <thead>
        <tr>
            <th data-options="field:'ck_d',checkbox:true">选择</th>
            <th data-options="field:'dep_name',width:80">科室名称</th>
        </tr>
    </thead>
</table>
				</td>
				<th align="right" valign="top">选择行政部门：</th>
				<td valign="top">
 <table class="easyui-treegrid" id="ExamLists" data-options=" idField:'id',treeField:'center_name',fit:true,singleSelect:false,collapsible:true,url:'getExaminatioin_centerList.action?id='+$('#userid').val(),method:'get',fitColumns:true,striped:true,
 onLoadSuccess:function(row,data){
  if(data){
  	xuanzhong(data);	
  }
 }">
    <thead>
        <tr>
            <th data-options="field:'ck_d',checkbox:true">选择</th>
            <th data-options="field:'center_name',width:80">行政部门名称</th>
        </tr>
    </thead>
</table>
				</td>
			</tr>
	</table>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_saveUserJob()">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-s').dialog('close')">取消</a>
	</div>
</div>
