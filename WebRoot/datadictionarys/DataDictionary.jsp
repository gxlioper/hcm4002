<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>数据字典管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/datadictionarys/DataDictionary.js?randomId=<%=Math.random()%>"></script>
	
	<script type="text/javascript">
		$(document).ready(function () {
		    getGrid();
		    search();
		});
		function search(){
			$("#data_type1").combobox({
				url :'queryAllDaDt.action',
				//editable : false, //不可编辑状态
				cache : false,
				//panelHeight : 'auto',//自动高度适合
				valueField : 'data_type',
				textField : 'data_type'
			})
		}
		//查询按钮
		function searchFun(){
			getGrid();
		}
		//清空按钮
		function cleanFun(){
			$("#data_name").textbox('setValue',"");
			$("#data_code").textbox('setValue',"");
			$("#data_type1").combobox('setValue',"");
			getGrid();
		}
		
		//添加按钮
		var toolbar = [{
			    text:'新增',
			    iconCls:'icon-add',
			    handler:function(){
			    	$("#dlg-edit").dialog({
			    		title:'添加',
			    		href:'addDaDt.action'
			    	});
			    	$("#dlg-edit").dialog("open");
			    }
			}];
		//修改按钮
		function f_xg(val,row){	
				return '<a href=\"javascript:updateDaDt(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
		 }
		 //删除按钮
		function f_sc(val,row){
				return '<a href=\"javascript:delDaDt(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
		}
		//更新
		function updateDaDt(id){
					$("#dlg-edit").dialog({
					title:'修改',
					href:'updaterDaDt.action?id='+id
				});
				$("#dlg-edit").dialog('open');
		}
		//删除
		function delDaDt(id)
		{
			$.messager.confirm('提示信息','是否确定删除？',function(r){
				if(r){
					$.ajax({
			   		url:'deleteDaDt.action?id='+id,
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
		function getGrid(){
			var model = {"data_name": $('#data_name').textbox('getValue'),"data_code": $('#data_code').textbox('getValue'),"data_type":$("#data_type1").combobox('getText')};
			   $("#DaDtList").datagrid({
				url: 'datadictionaryList.action',
				queryParams: model,
				ctrlSelect:true,
		        pageSize: 13,//每页显示的记录条数，默认为10 
		        pageList:[13,30,45,60,75],//可以设置每页记录条数的列表 
		        toolbar: '#toolbar',
				height:400,
		        columns:[[{align:'center',field:"id",title:"ID",width:15,checkbox:true},
		                  {align:'center',field:"data_type",title:"数据类型",width:18},
		        		  {align:"center",field:"data_code",title:"数据类型编码",width:25},
		        		  {align:"center",field:"data_code_children",title:"数据编码",width:20},
		        		  {align:'center',field:"data_name",title:"数据名称",width:18},
		        		  {align:"center",field:"remark",title:"备注",width:25},
		        		  {align:"center",field:"creater",title:"创建人",width:15},       		  
		        		  {align:"center",field:"create_time",title:"创建时间",width:30},
		        		  {align:"center",field:"updater",title:" 更新者",width:15},
		        		  {align:"center",field:"update_time",title:"更新时间",width:30},
		        		  {align:"center",field:"seq_code",title:"顺序码",width:26},
		        		  {align:"center",field:"data_classs",title:"适用系统",width:26},
		        		  {align:"center",field:"xiugai",title:"修改",width:15,"formatter":f_xg},
		        		  {align:"center",field:"shanchu",title:"删除",width:15,"formatter":f_sc}
		        		  ]],
			    onLoadSuccess:function(value){
			    	$("#datatotal").val(value.total);
			    },
			    singleSelect:true,
			    collapsible:true,
				pagination:true,
		        fitColumns:true,
		        striped:true,
		      	fit:true,
		        toolbar:toolbar
			});
		}
	</script>

</head>
<body>
	<div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:80px;">
		<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
			<legend><strong>数据字典信息检索</strong></legend>
			  <div id="tb" style="padding-right:20px;padding-left:50px;">
				&nbsp;&nbsp;&nbsp;&nbsp;数据名称: <input id="data_name" name="data_name" class="easyui-textbox"  data-options="prompt:'数据名称'" />
				&nbsp;&nbsp;&nbsp;&nbsp;数据类型编码: <input id="data_code" name="data_code" class="easyui-textbox" data-options="prompt:'数据类型编码'"/>
				&nbsp;&nbsp;&nbsp;&nbsp;数据类型:<input id="data_type1" name="data_type1" class="easyui-combobox" data-options="prompt:'请选择或输入数据类型'" />
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
			  </div>
		 </fieldset>
		 </div>
	 <div  data-options="region:'center'">
		<fieldset style="margin:5px;padding-right:20px;height:440px;">
			<legend><strong>数据字典信息</strong></legend>
				 <div id="DaDtList"> </div>
		</fieldset>
		</div>
	 </div>
	<div id="dlg-edit" class="easyui-dialog" data-options="width: 530,height: 410,closed: true,cache: false,modal: true,top:5"></div>
	</body>
</html>