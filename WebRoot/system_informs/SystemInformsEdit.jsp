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
			          id:$("#uid").val(),
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
        		  {align:'center',field:"rolename",title:"角色名称","width":18},
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
 
$(document).ready(function () {
	shifouqiyong();
});
	

	
	$(function(){
	
	 $("#inform_content").validatebox({
			required:true,
		});
	
		
   	});
	
	function shifouqiyong(){
	    var is_active = '<s:property value="is_active"/>';
	    $("#is_active").val(is_active);
	   
  }
	function saveSysInfo(){
		var idss=[];//用户
		var rows=$("#userList").datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			
			idss.push(rows[i].user_id);
		}
		
		var role_ids=[];//角色
		var role_rows=$("#roleList").datagrid('getSelections');
		for(var i=0;i<role_rows.length;i++){
			role_ids.push(role_rows[i].r_id);
		}
		
		if (document.getElementById("inform_content").value == ''){
			$("#inform_content").focus();
			return;
		}
		
		if(idss.length<=0 && role_ids.length<=0  ){
			$.messager.alert('提示','请选择用户或角色');  
		}else{
			$.ajax({
				
				url:'saveSystemInforms.action',
				type:"post",
				data:{
					id:$("#id").val(),
					
					r_ids:role_ids.toString(),
			        ids:idss.toString(),
					is_active:$("#is_active").val(),
					inform_content:$("#inform_content").val(),
					valid_date:$("#valid_date").datebox('getValue'),
				},
				success : function(data) {
					if (data.split("-")[0] == "ok" ) {
						
						$.messager.alert("操作提示", data.split("-")[1]);
						
					} else {
						
						$.messager.alert("操作提示", "操作错误", "error");
						
					} 
					/* $.messager.alert("操作提示",data); */
					$("#dlg-edit").dialog("close");
					//getGrid();
					$('#informationlist').datagrid('reload');
				},
				error : function() {
				$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
		
		
	}
	
	

 	
</script>	

<div id="tts" class="easyui-tabs"  style="width:100%; height: 520px;" data-options="fit:true,border:false,plain:true">
<div title="站内编辑" style="padding:5px;" >
	<div class="formdiv" >
	    	      <dl>
	    	       <dt>有效时间</dt>
	    	       <dd><input id="valid_date" name="valid_date" value="<s:property value="valid_date"/>"  class="easyui-datebox" data-options="prompt:'有效时间'" ></dd>
	    	     </dl>
	    	     <dl>
	    	       <dt>是否启用</dt>
	    	       <dd><select id="is_active" style="width:150px;" >
	    	       		<option  value="Y">是</option>
	    	       		<option value="N">否</option>
	    	       </select>
	    	       </dd>
				    </dl>
				
					<dl>
	    	       <dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>
	    	       		通知内容</dt>
	    	       <dd><textarea style="width:80%;resize:none;" cols="50" rows="4" name="inform_content" id="inform_content" ><s:property value="inform_content"/></textarea>
	    	       </dd>
	    	       <dt  style="position: absolute; height: 25px; width: 100px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
	    	      </dl>
	    	     </div>
	    	    
 	
</div>



<div  title="按用户授权" class="easyui-layout" style="OVERFLOW:auto;WIDTH:200px;HEIGHT:300px">   
	<div id="userList"></div>
</div> 


<div title="按角色授权"  class="easyui-layout" style="OVERFLOW:auto;WIDTH:200px;HEIGHT:300px">   
	<div id="roleList"></div>   
</div>   

	
</div> 
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;"onclick="saveSysInfo();" >保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
		</div>
	</div>
	
<div id="dlg-shq" class="easyui-dialog" data-options="width: 400,height: 450,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 700,height: 450,closed: true,cache: false,modal: true,top:50"></div>