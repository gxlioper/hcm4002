<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<%  
        application.setAttribute("name","application_James");  
       
   %>  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>体检中心管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>Examinatioin_center/Examinatioin.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
	$(document).ready(function (){
		getGrid()
	})
	//查询按钮
	function searchFun(){
		getGrid();
	}
	//清空按钮
	function cleanFun(){
		$("#center_name1").textbox('setValue',"");
		$("#center_num1").textbox('setValue',"");
		getGrid();
	}
	
	//添加按钮
	var toolbar = [{
		    text:'新增',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#dlg-edit").dialog({
		    		title:'新增体检信息',
		    		href:'addExam.action'
		    	});
		    	$("#dlg-edit").dialog("open");
		    }
		},{
		    text:'新增部门',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#dlg-edit").dialog({
		    		title:'新增部门信息',
		    		href:'addExamDept.action'
		    	});
		    	$("#dlg-edit").dialog("open");
		    }
		}];
	//修改按钮
	function f_xg(val,row){	

			return '<a href=\"javascript:updateExam(\''+row.id+'\',\''+row._parentId+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';	
		
	 }
	 //删除按钮
	function f_sc(val,row){
			return '<a href=\"javascript:delExam(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}
	//更新
	function updateExam(id,parent_id){
		if(parent_id!='0'){
			$("#dlg-edit").dialog({
				title:'修改',
				href:'updaterExamDept.action?id='+id
			});
			$("#dlg-edit").dialog('open');
		}else{
			$("#dlg-edit").dialog({
				title:'修改',
				href:'updaterExam.action?id='+id
			});
			$("#dlg-edit").dialog('open');
		}
	}
	//删除
	function delExam(id)
	{
		$.messager.confirm('提示信息','是否确定删除？',function(r){
			if(r){
				$.ajax({
		   		url:'deleteExamnatioin.action?id='+id,
		   		type:'post',
		   		success:function(data){
	   			$.messager.alert('提示信息',data);
	   			getGrid();
	   		    },
		   		error:function(){
		   			$.messager.alert('提示信息','操作失败！','error');
		   		}
	   		});
			}
		})
	}
	
	//页面数据加载
	function getGrid(){
		 var model = {"center_num": $('#center_num1').textbox('getValue'),"center_name": $('#center_name1').textbox('getValue')};
		$("#ExamList").treegrid({
			 url:'getExaminatioin_center.action',
			 dataType: 'json',
			 queryParams:model,
			 idField:'id',
			 treeField:'center_name',
			 columns:[[{align:"center",field:'center_name',title:'体检中心名称',"width":30},
				 {align:"center",field:"center_num",title:"体检中心编码","width":15},
				 {align:'center',field:"limit_count",title:"体检中心上限","width":30},
       		  {align:"center",field:"creater",title:"创建者","width":15},
       		  {align:"center",field:"create_time",title:"创建时间","width":25},
       		  {align:"center",field:"updater",title:"更新者","width":15},
       		  {align:"center",field:"update_time",title:"更新时间","width":25},
       		  {align:"center",field:"photo_function_status_s",title:"拍照权限","width":10},
       		 {align:"center",field:"_parentId",title:"父级Id",hidden:true},
       		  {align:"center",field:"xiugai",title:"修改","width":10,"formatter":f_xg},
       		  {align:"center",field:"shanchu",title:"删除","width":10,"formatter":f_sc}
			]],
	   		pagination: true,
	        fitColumns:true,
	        striped:true,
	        fit:true,
	        toolbar:toolbar,
	    	singleSelect:true,
	    });	
	}
	
	    </script>
	</head>
	<body>
		<div class="easyui-layout" fit="true">
        <div  data-options="region:'north'" style="height:80px;">
		<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
		<legend><strong>体检中心信息检索</strong></legend>
		<div id="tb" style="padding-right:20px;padding-left:50px;">
			&nbsp;&nbsp;&nbsp;&nbsp;体检中心名称: <input id="center_name1" name="center_name" class="easyui-textbox"  data-options="prompt:'体检中心名称'" />
			&nbsp;&nbsp;&nbsp;&nbsp;体检中心编码: <input id="center_num1" name="center_num" class="easyui-textbox" data-options="prompt:'体检中心编码'"/>
			
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
		</div>
		 </fieldset>
		  </div>
	     <div  data-options="region:'center'">
		<fieldset style="margin:5px;padding-right:20px;height:95%;">
			<legend><strong>体检中心信息</strong></legend>
				 <div id="ExamList"> </div>
		</fieldset>
		</div>
	 </div>
	<div id="dlg-edit" class="easyui-dialog" data-options="width: 530,height: 300,closed: true,cache: false,modal: true,top:5"></div>
	
	</body>
	</html>