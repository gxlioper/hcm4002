<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

$(document).ready(function () {
	getSeGrid();
});

 function getSeGrid(){
	 var model={"exam_num":$("#exam_num").val()};
		$("#disease_critical_detail").datagrid({
				 url:'getExamCriticalList.action',
				 dataType: 'json',
				 queryParams:model,
				 rownumbers:true,
				 columns:[[{field:'ck',checkbox:true },
				    {align:"center",field:"id",title:"id",hidden:true},
				    {align:"center",field:"creater",title:"creater",hidden:true},
				    {align:"center",field:"data_source",title:"data_source",hidden:true},
				    {align:'',field:'critical_class_parent_name',title:'大类',width:40},
				 	{align:'',field:'critical_class_name',title:'子类 ',width:20},
				 	{align:'center',field:'exam_result',title:'危急值结果',width:70},
				 	//{align:'center',field:'check_date',title:'体检日期',width:15},//
				 	{align:'center',field:'critical_class_level',title:'等级',width:15},
				 	{align:'center',field:'done_flag_s',title:'处理状态',width:15},
				    {align:'center',field:'caozuo',title:'操作',width:20,"formatter":f_cz_critical}
				 	]],	    	
			    	onLoadSuccess:function(value){
			    	},
			    	singleSelect:false,
					pagination: false,
				    fitColumns:true,
				    fit:true,
				    striped:true,
				    nowrap:false,
				    toolbar:"#toolbar2",
				    checkOnSelect:false
		});
}
 
//危急值列表操作
 function f_cz_critical(val,row,index){
 	var str = '&nbsp;&nbsp;&nbsp;<a href=\"javascript:up_critical(\''+row.id+'\',\''+row.creater+'\',\''+row.data_source+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
 	str+='&nbsp;&nbsp;&nbsp;&nbsp;' + '<a href=\"javascript:del_critical(\''+row.id+'\',\''+row.creater+'\',\''+row.data_source+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
 	return str;
 }

 //新增危急值
 function new_addcritical(){
 	$("#dlg-add").dialog({
 		title:'新增危急值',
 		href:'showNewExamCritical.action?exam_num='+$("#exam_num").val()+'&type='+'KS',
 	});
 	$("#dlg-add").dialog("open");
 }

 //修改危急值判断条件
 function up_critical(rowId,creater,data_source){//id,创建人id,data_source 1表示手动   0表示自动
 	var userid=$("#userid").val();//登陆用户id
 	var is_update_critical=$("#is_update_critical").val(); //是否有删除修改危急值权限  0没有   1有
 	if(userid!=creater){
 		if(is_update_critical==0){
 			$.messager.alert('提示信息', "操作别人创建记录需要有修改权限！",'');
 			return;
 		}else{
 			up_criticalss(rowId);
 		}
 		
 	}else{
 		if(is_update_critical==0){
 			if(data_source==0){
 				$.messager.alert('提示信息', "修改本人的记录，只能修改手动登记的记录！",'');
 				return;
 			}else{
 				up_criticalss(rowId);
 			}
 		}else{
 			up_criticalss(rowId);
 		}
 		
 	}
 	
 	
 	
 }
 //修改危急值
 function up_criticalss(rowId){
 	$("#dlg-add").dialog({
 		title:'修改危急值',
 		href:'showNewExamCritical.action?exam_num='+$("#exam_num").val()+'&critical_id1='+rowId+'&type='+'KS',
 	});
 	$("#dlg-add").dialog("open");
 }

 //删除危急值条件
 function del_critical(rowId,creater,data_source){
 	var userid=$("#userid").val();//登陆用户id
 	var is_update_critical=$("#is_update_critical").val(); //是否有删除修改危急值权限  0没有   1有
 	if(userid!=creater){
 		if(is_update_critical==0){
 			$.messager.alert('提示信息', "操作别人创建记录需要有修改权限！",'');
 			return;
 		}else{
 			del_criticalss(rowId);
 		}
 		
 	}else{
 		if(is_update_critical==0){
 			if(data_source==0){
 				$.messager.alert('提示信息', "修改本人的记录，只能修改手动登记的记录！",'');
 				return;
 			}else{
 				del_criticalss(rowId);
 			}
 		}else{
 			del_criticalss(rowId);
 		}
 		
 	}
 }
 //删除危急值
 function del_criticalss(rowId){
 	$.messager.confirm('提示信息','是否确定删除此危机信息？',function(r){
 	 	if(r){
 	 		$.ajax({
 	 		url:'delCriticalDetail.action?critical_id1='+rowId,
 	 		type:'post',
 	 		success: function(data){
 			  	 $.messager.alert('提示信息', data);
 			  	getSeGrid();
 	 		},
 	 		error:function(){
 	 			$.messager.alert('提示信息','操作失败！','error');
 	 		}
 	 		});
 	 	}
 	 });
 }


</script>
	<div id="toolbar2">
		<div>
		<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;height:27px;" onclick="javascript:new_addcritical();">新增危急值</a>
		</div>	
	</div>
	<div class="easyui-tabs" id="tab_ss" style="height:680px;width:1050px;margin-left:5px;">
  			<div title="危急值列表" style="">
    			<table id="disease_critical_detail"></table>  
    		</div>
	</div>
<!-- 	<div class="dialog-button-box">
		<div class="inner-button-box">
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;" onclick="javascript:f_select();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;" onclick="javascript:$('#dlg-xz').dialog('close')">关闭</a>
		</div>
	</div> -->
	
	<div id="dlg-add" class="easyui-dialog"  data-options="width: 800,height: 600,closed: true,cache: false,modal: true"></div>

