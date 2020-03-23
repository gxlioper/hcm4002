	<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		getRoleList();
		getUserList()
	})
	var b=0;
	function saveEmpower(){
		if(b==1){
			var idss=[];//用户
			var rows=$("#userList").datagrid('getSelections');
			if(rows.length==0){
				$.messager.alert("提示","您当前没有选择任何用户!", "warning");
			}else{
				for(var i=0;i<rows.length;i++){
					idss.push(rows[i].user_id);
				}
				$.ajax({
					url:'saveEmpowerSysInf.action',
					type:'post',
					data:{
				          id:$("#id").val(),
				          ids:idss.toString(),
				          },   
					success:function(data){
						
						$.messager.alert('提示信息', data,function(){});
						$("#dlg-shq").dialog('close');
						$('#informationlist').datagrid('reload');
					},
					error:function(){
						$.messager.alert('提示信息', "授权操作失败！",function(){});
					}
				});			
			}
		}else if(b==2){
			var role_ids=[];//角色
			var role_rows=$("#roleList").datagrid('getSelections');
			if(role_rows.length==0){
				$.messager.alert("提示","您当前没有选择任何角色!", "warning");
			}else{
				for(var i=0;i<role_rows.length;i++){
					role_ids.push(role_rows[i].r_id);
				}
				$.ajax({
					url:'saveEmpowerSysInf.action',
					type:'post',
					data:{
				          id:$("#id").val(),
				          r_ids:role_ids.toString(),
				          },   
					success:function(data){
						$.messager.alert('提示信息', data,function(){});
						$("#dlg-shq").dialog('close');
						//getGrid();
						$('#informationlist').datagrid('reload');
					},
					error:function(){
						$.messager.alert('提示信息', "授权操作失败！",function(){});
					}
				});	
			}
			return ;
		}
	
	}; 
	//角色list
	function getRoleList(){
	       var model = {};
	        $("#roleList").datagrid({
			url: 'roleList.action',
			queryParams: model,
	        pageSize: 15,//每页显示的记录条数，默认为10 
	        columns:[[
	                  {align:"center",field:"r_id","title":"ID","hidden":true},
	        		  {align:"center",field:"ck",checkbox:true},
	        		  {align:'center',field:"role_name",title:"角色名称","width":18},
	        		  ]],
	        		  onLoadSuccess:function(value){
	      		    	$("#datatotal").val(value.total);
	      		    },
			      fitColumns:true,
		});
	}
	//用户list
	function getUserList(){
	       var model = {};
	        $("#userList").datagrid({
			url: 'userListSystemInforms.action?id='+$('#id').val(),
			queryParams: model,
	        columns:[[
	                  {align:"center",field:"user_id","title":"ID","hidden":true},
	        		  {align:"center",field:"ck",checkbox:true},
	        		  {align:'center',field:"chi_name",title:"用户姓名","width":18},
	        		  ]],
			      fitColumns:true,
			      striped:true,
			      onLoadSuccess:function(data){
					 	if(data){
					 		$.each(data.rows,function(index,item){
					 			if(item.selected=='checked'){
					 				$('#userList').datagrid('checkRow',index);
					 			}
					 		});
					 	}
					 }, 
		});
	}
	$('#tt').tabs({    
	    border:false,    
	    onSelect:function(title,index){    
			if(title=='按角色授权') {
				b=2;
			}else{
				b=1;
			}
	    }    
	}); 
	
</script>
<div id="body" style="width:100%;height:360px;">
<div class="easyui-layout" fit="true"> 
	<div data-options="region:'center'," style="padding:5px;background:#eee;height:90%;">
		<div id="tt" >   
			<div title="按用户授权" ><input type="hidden" value="<s:property value='id' />" name="id" id="id"/>  
				<div id="userList"></div>
			</div> 
			<div title="按角色授权" >   
				<div id="roleList"></div>    
			</div>   
		</div>
	</div> 	
</div> </div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:saveEmpower()">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-shq').dialog('close')">取消</a>
	</div>
</div>
