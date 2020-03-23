<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script>

$(document).ready(function () {
	getGrids();
})
//查询按钮
function searchFun(){
	getGrids();
}
//清空按钮
function cleanFun(){
	$("#center_name1").val('');
	getGrids();
}

//页面数据加载
function getGrids(){
	 var model = {"center_name": $('#center_name1').val()};
	$("#ExamLists").treegrid({
		 url:'getExaminatioin_center.action',
		 dataType: 'json',
		 queryParams:model,
		 idField:'id',
		 treeField:'center_name',
		 columns:[[{align:"center",field:'center_name',title:'体检中心与部门名称',width:"10"},
			 {align:"center",field:"center_num",title:"体检中心与部门编码"},
		]],
        fitColumns:true,
        fit:true,
    	singleSelect:true,
    });	
}
function f_userSaveDept(){
	var row=$('#ExamLists').treegrid('getSelected');
	if(row==null){
		$.messager.alert('提示信息','请选择部门','error')
	}else{
		$.ajax({
		    url:'saveUserExamDept.action', 
		    type: "post",
			data:{
				id:$("#ids").val(),
				exam_center_parent_id:row.id
			  },          
			success: function(data){
				 $(".loading_div").remove();
			  	 $.messager.alert('提示信息', data);
			  	 $("#dlg-edit").dialog("close");
			      	 getGrid();
			    },
			 error:function(){
				 	$(".loading_div").remove();
			    	$("#dlg-edit").dialog("close");
			    	$.messager.alert('提示信息', "用户操作失败！",function(){});
			    }  
		});
	}
}
</script>
	<div class="easyui-layout" fit="true">
        <div  data-options="region:'north'" style="height:80px;">
		<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
		<legend><strong>体检中心信息检索</strong></legend>
		<div id="tb" style="padding-right:20px;padding-left:50px;">
		<font>用户名&nbsp;:&nbsp;<s:property value="chi_Name"/>&nbsp;&nbsp;&nbsp;&nbsp;</font>
		<input class="textinput"  id="ids" value="<s:property value="id"/>" hidden="true"/>
			体检中心名称:<input class="textinput"  id="center_name1" style="width:150px;height:16">
			<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查询</a>   	       
			<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">清空</a>
		</div>
		 </fieldset>
		  </div>
	     <div  data-options="region:'center'">
		<fieldset style="margin:5px;padding-right:20px;height:85%;">
			<legend><strong>体检中心信息</strong></legend>
				 <div id="ExamLists"> </div>
		</fieldset>
		</div>
	 </div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:f_userSaveDept()" class="easyui-linkbutton c6" style="width:80px;" >保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
	
</div>
</form>
