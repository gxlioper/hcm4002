<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>授权功能</title>

<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
</head>


<body>
<input type="hidden" value="<s:property value='id' />" name="userid" id="userid"/>
	<div class="shanghu_dlg_top_1">登录名称：<s:property value="gwmc" />  姓名：<s:property value="gwbm" /></div>
	
	<div class="easyui-tabs" style="height:360px;">
		<div  id="system_job" title="选择角色">
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
		</div>

		<div  id="centerdept"  title="选择科室">				
                 <ul class="easyui-tree" id="dv_center_dept" data-options="checkbox:true,onnodeclick:false,url:'centerAndDeptshow.action?id='+$('#userid').val()"></ul>
				</div>				
			</div>

<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_saveUserJob()">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-s').dialog('close')">取消</a>
	</div>
</div>
<script type="text/javascript">
	function f_saveUserJob(){
		var ids=[];
		var rows=$("#jobList").datagrid('getSelections');
		//授权报表	 		
 		var centerdepts=[];
		var nodes=$("#dv_center_dept").tree("getChecked");			
		for(var i=0;i<nodes.length;i++){			
			if($("#dv_center_dept").tree('isLeaf',nodes[i].target)){					
				centerdepts.push(nodes[i].id);				
			}
		}	
		if(rows.length==0){
			//alert('用户没有选择任何岗位!');
			$.messager.alert("提示","用户没有选择任何岗位！", "warning");
			return ;
		}else if(centerdepts.length == 0){
			$.messager.alert("提示","用户没有选择任何科室！", "warning");
			return ;
		}else{
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
			}
			$.ajax({
				url:'userrolesave.action',
				type:'post',
				data:{
			          id:$("#userid").val(),
			          remark:ids.toString(),
			          center:centerdepts.toString(),
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
</script>
</body>
</html>
