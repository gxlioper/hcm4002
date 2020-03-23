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
<title>科室管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/default/ecard/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>department/departadd.js?randomId=<%=Math.random()%>"></script>
	<script type="text/javascript">
	 function chaxun(){
		$("#dep_category1").combobox({
			url :'getDatadis.action?com_Type=KSLX',
			//editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name'
		})
	} 
	function searchFun(){
		getGrid();
	}
	function cleanFun(){
		$('#dep_category1').combobox('setValue',"");
		$('#dep_name1').textbox('setValue',"");
		$('#dep_num1').textbox('setValue',"");
		$("#sex_s").val(' ');
		getGrid('reload');
	}
	
 	var toolbar = [{
		    text:'新增科室',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#dep_edit").dialog({
		    		title:'新增科室',
		    		href:'departadd.action'
		    	});
		    	$("#dep_edit").dialog("open");
		    }
		}/* ,{
			    text:'批量设置体检中心',
			    iconCls:'icon-add',
			    handler:function(){
			    	var dep_data = $('#deplist').datagrid('getSelections');
			    	console.log(dep_data[0].id);
			    	if(dep_data.length == 0 ){
			    		$.messager.alert("提示信息","请选择科室","error");
			    		return;
			    	}
			    	debugger;
			    	var dep_ids = new Array();
			    	for(var i = 0 ; i < dep_data.length ;i++){
			    		dep_ids.push(dep_data[i].id);
			    	}
			    	console.log(dep_ids);
			    	$("#dep_edit").dialog({
			    		title:'设置体检中心',
			    		href:'bachDepCenter.action?dep_ids='+dep_ids.toString()
			    	});
			    	$("#dep_edit").dialog("open");
			    } 
		} */];
	$(document).ready(function () {
	    getGrid();
	    chaxun();
	});
	
	function getGrid(){
	       var model = {"dep_name": $('#dep_name1').textbox('getValue'),"dep_num": $('#dep_num1').textbox('getValue'),"dep_category":$("#dep_category1").combobox('getValue'),"sex":$("#sex_s").val()};
		   $("#deplist").datagrid({
			url: 'departmentList.action',
			queryParams: model,
			ctrlSelect:true,
	        pageSize: 15,//每页显示的记录条数，默认为10 
	        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	       //toolbar: '#toolbar',
	       // sortName: 'dep_name',
			sortOrder: 'asc',
			height:400,
	        columns:[[
        			  {field:'ck',checkbox:true},
	                  {align:'center',field:"dep_num","title":"科室编码",width:18},
	        		  {align:"center",field:"dep_name","title":"科室名称","width":20},
	        		  {align:'center',field:"dep_category","title":"科室类型","width":18},
	        		  {align:"center",field:"sex","title":"适用性别","width":18},
	        		  {align:"center",field:"seq_code","title":"顺序码","width":15},
	        		  {align:"center",field:"calculation_ratess","title":"利润率","width":25,"formatter":xg_rate},
	        		  {align:"center",field:"remark","title":"备注","width":20},
	        		  {align:"center",field:"creater_name","title":"创建人","width":15},       		  
	        		  {align:"center",field:"create_time","title":"创建时间","width":30},
	        		  {align:"center",field:"centernames","title":"体检中心","width":100,"formatter":xg_center},
	        		  {align:"center",field:"updater_name","title":" 更新者","width":15},
	        		  {align:"center",field:"update_time","title":"更新时间","width":30},
	        		  {align:"center",field:"isPrint_Barcode_y","title":"是否打印条码","width":26},       		  
	        		  {align:"center",field:"dep_inter_num","title":"其他科室编码","width":26},
	        		  {align:"left",field:"dep_link","title":"科室链接","width":50},
	        		  {align:"center",field:"xg","title":"修改","width":15,"formatter":xg_sc},
	        		  {align:"center",field:"isActive","title":"启(停)修改","width":22,"formatter":f_qt}
	        		  ]],
		    onLoadSuccess:function(value){
		    	$("#datatotal").val(value.total);
		    	if($("#usertype").val()=='999'){
		    		 $("#deplist").datagrid("hideColumn", "creater"); // 设置隐藏列
		    		 $("#deplist").datagrid("hideColumn", "create_time"); // 设置隐藏列
		    	}else{
		    		 $("#deplist").datagrid("hideColumn", "centernames"); // 设置隐藏列
		    	}
		    },
		    singleSelect:false,
		    collapsible:true,
			pagination:true,
	        fitColumns:true,
	        striped:true,
	      	fit:true,
	        toolbar:toolbar
		});
	}
	 function xg_sc(val,row){
		 var str = '&nbsp;&nbsp;&nbsp;<a href=\"javascript:updatedep(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
//		 		+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + '<a href=\"javascript:deletedep(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>'
		 return str;
	 }
	 function updatedep(id){
					$("#dep_edit").dialog({
					title:'修改科室',
					href:'depupdater.action?id='+id
				});
				$("#dep_edit").dialog('open');
	 }

		function deletedep(id)
		{
			$.messager.confirm('提示信息','是否确定删除该科室？',function(r){
				if(r){
			    $.ajax({
		   		url:'deletedep.action?id='+id,
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
	function xg_rate(val,row){
		 var str = '&nbsp;&nbsp;&nbsp;'+row.calculation_rate+'%'
		 		+ '&nbsp;&nbsp;&nbsp;<a href=\"javascript:updatedepRate(\''+row.id+'\')\">设置</a>';
		 return str;
	 }	
	
	//设置体检中心
	function xg_center(val,row){
		 //var str = '&nbsp;&nbsp;<a href=\"javascript:updateCenter(\''+row.id+'\')\">设置</a>'
		 var str = '&nbsp;&nbsp;'+row.centernames;
		 return str;
	 }
	
	function updateCenter(id){
		$("#dep_center").dialog({
	    		title:'所属体检中心',
	    		href:'detptocenter.action?id='+id
	    	});
		$("#dep_center").dialog("open");
		
	} 
	 
	function updatedepRate(id){
			$.messager.confirm('提示信息','此操作将对本科室所有收费项目设置利润率，确认进行此操作吗？',function(r){
				if(r){
			    $.ajax({
		   		url:'updatedepRate.action?id='+id,
		   		type:'post',
		   		success:function(text){
		   			if (text.split("-")[0] == 'ok') {
		   		  	    $.messager.alert('提示信息', text.split("-")[1]);	
		   			 }else{
		   				$.messager.alert("操作提示", text.split("-")[1], "error");
		   			}
		   		},
		   		error:function(){
		   			$.messager.alert('提示信息','操作失败！','error');
		   		}
		   		});
				}
			})
		} 
	//启停修改
	 function f_qt(val,row){
	  var html='';
	     if(val=="N"){
	       return '<a style="color:#f00;" href=\"javascript:f_startorblock(\''+row.id+'\',\'启用\')\">启用</a>';
	     }else if(val=='Y') {
	        return '<a style="color:#1CC112;" href=\"javascript:f_startorblock(\''+row.id+'\',\'停用\')\">停用</a>';
	      }
	 }


	 //启（停）修改
	 function f_startorblock(id,html){
	 	$.messager.confirm('提示信息','是否确认'+html+'该科室？',function(r){
	 		if(r){
	 		$.ajax({
	      	url:'updateDepartStopOrStart.action?id='+id,
	      	type:'post',
	      	success:function(data){
				if(data.split("-")[0] == 'ok'){
					$.messager.alert("提示信息",html+"该科室成功!");
					$('#deplist').datagrid('reload');
				} else {
					$.messager.alert("提示信息",data.split("-")[1],"error");
				}
	      	},
	      	error:function(){
	      		$.messager.alert('提示信息','操作失败！','error');
	      			}
	 			});
	 		}
	 	})
	 }
	    </script>
	</head>
	<body>
	<input type="hidden" id="usertype" value="<s:property value="#session.username.usertype"/>" />
	<div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:80px;">
	<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
	<legend><strong>科室信息检索</strong></legend>
	<div id="tb" style="padding-right:20px;padding-left:30px;">
		&nbsp;&nbsp;&nbsp;科室名称: <input id="dep_name1" name="dep_name1" class="easyui-textbox"  data-options="prompt:'科室名称'" />
		&nbsp;&nbsp;&nbsp;科室编码: <input id="dep_num1" name="dep_num1" class="easyui-textbox" data-options="prompt:'科室编码'"/>
		&nbsp;&nbsp;&nbsp;科室类型:<input id="dep_category1" name="dep_category1" class="easyui-combobox"  data-options="prompt:'科室类型'" />
		&nbsp;&nbsp;&nbsp;适用性别: <select style="width:150px;" id="sex_s" >
		    	       		<option  value="全部">全部</option>
		    	       		<option  value="男">男</option>
		    	       		<option  value="女">女</option>
		    	       </select>
		&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
		<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="searchFun();">查&nbsp;&nbsp;询&nbsp;</a> -->
		&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
	</div>
	 </fieldset>
	 </div>
	 <div  data-options="region:'center'">
		<fieldset style="margin:5px;padding-right:20px;height:95%;">
		<legend><strong>科室信息</strong></legend>
		  <div id="deplist"></div>
		</fieldset>
	 </div>
	 </div>
	 <div id="dep_center" class="easyui-dialog" data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50" ></div>
	 <div id="dep_edit" class="easyui-dialog" data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50" ></div>
	  <div id="dep_edit" class="easyui-dialog" data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50" ></div>
	</body>
	</html>