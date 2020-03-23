<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据字典管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  

<script type="text/javascript">
	dataCodeTr = '';
	
	$(document).ready(function () { 
		shuxing();
		getData();
	});
	
	 function shuxing(selectID){
		$('#h_tree').tree({
			 url:'queryAllDaDt.action',
			 dataType:'json',
			 loadFilter :function(data,parent){
					 var obj = data;
					 var newData = new Array();
					 for(var i=0;i<obj.length;i++){
						 newData.push({id:obj[i].data_code,text:obj[i].data_type});
					 }
					 var newDate2 = [{id:'',text:'数据字典类别',children:newData}];
					 return newDate2;
			 },
			 onClick:function(node){
				 getData(node.id);
			 },
			 onLoadSuccess:function(node,data){  
		     	$("#h_tree li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
				if(selectID) {
					var node = $('#h_tree').tree('find', selectID);
					$("#h_tree").tree("select",node.target);
					getData(selectID);
		   		} else {
		   			var n = $("#h_tree").tree("getSelected");  
					if(n!=null){
						$("#h_tree").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法
					}
		   		}
	        },
		});
	};
	
	function getData(data_code){
		dataCodeTr = data_code;
		var model = {
    		   "data_code":data_code,
    		   "data_type":'',
    		   "data_name":'',
	       };
		$('#list').datagrid({
		    url:'datadictionaryList.action', 
		    queryParams:model,
		    dataType: 'json',
		    pageSize: 15,//每页显示的记录条数，默认为10 
		    pageList:[15,20,30,60,75],//可以设置每页记录条数的列表 
		    columns:[[
		        {align:"center",field:"id",title:"id",hidden:true},
		        {align:"center",field:"data_code_children",title:"数据细项编码","width":20},
        		{align:"center",field:"data_name",title:"数据名称","width":20},
        		{align:'center',field:"remark",title:"备注","width":40},
        		{align:'center',field:"seq_code",title:"顺序码","width":10},
        		{align:'center',field:"isActive",title:"启用状态","formatter":f_status},
		        {align:"center",field:'upd',title:'修改',width:15,"formatter":f_update},
		        {align:"center",field:'off_on',title:'停用/启用',width:15,"formatter":f_off_on},
		    ]] ,
		    sortName:'seq_code',
		    sortOrder:'asc',
		    remoteSort:false,
       		pagination:true,//分页控件
        	rownumbers:true,//行号
        	fitColumns:true,
        	singleSelect:false,
		    fit:true,
        	toolbar:toolbar,
        	 onDblClickRow:function(index, row){
        	   	 var row_id = $('#list').datagrid('getRows')[index].id;
        	   			updateDictionary(row_id);
        	       }
		}); 
	};
	/**
	  * 定义工具栏
	  */
	  var toolbar = [{
		    text:'选中类别下新增细项',
		    iconCls:'icon-add',
		    handler:function(){
		    	if(dataCodeTr) {
			    	$("#edit_dlg").dialog({
			    		title:'选中类别下新增细项',
			    		href:'dataItemAdd.action?data_code='+dataCodeTr,
			    	});
			    	$("#edit_dlg").dialog("open");
		    	} else {
		    		$.messager.alert('提示信息',"请先选择一个数据字典类别！");
		    	}
		    }
		}, {
		    text:'新增类别和细项',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#edit_dlg").dialog({
		    		title:'新增类型和细项',
		    		href:'dataTypeAndItemAdd.action'
		    	});
		    	$("#edit_dlg").dialog("open");
		    }
		}];
	
	//修改按钮
	function f_update(val,row){	
			return '<a href=\"javascript:updateDictionary(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	 }
	 //停用/启用按钮
	function f_off_on(val,row){
	    if(row.isActive=="Y"){
	        return '<a style="color:#f00;" href=\"javascript:f_qt(\''+row.id+'\',\'停用\')\">点击停用</a>';
	    }else if(row.isActive=='N') {
	       return '<a style="color:#1CC112;" href=\"javascript:f_qt(\''+row.id+'\',\'启用\')\">点击启用</a>';
	    }
	}
	function f_status(val,row){
	    if(val=="Y"){
	        return '<span style="color:#1CC112;">启用中</span>';
	    }else if(val=='N') {
	       return '<span style="color:#f00;">已停用</span>';
	    }
	}
	//更新
	function updateDictionary(id){
	    $("#edit_dlg").dialog({
			title:'修改',
			href:'dataUpdate.action?id='+id
		});
		$("#edit_dlg").dialog('open'); 
	}
	//停用/启用
	function f_qt(id,html)
    {
    	$.messager.confirm('提示信息','是否确认'+html+'该条记录？',function(r){
    		if(r){
    			$.ajax({
	         	url:'update_dictionary_off_on.action?id='+id,
	         	type:'post',
	         	success:function(data){
	         		var obj=eval("("+data+")");
	         		if(obj=='success'){
	         			$.messager.alert('提示信息',html+"该条记录成功！");
	         			getData(dataCodeTr);
	         		}else if(obj=='error'){
	         			$.messager.alert('提示信息',html+"该条记录失败！",'error');
	         		}else{
	         				$.messager.alert('提示信息',obj);
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
<body class="easyui-layout">
 	<div data-options="region:'west',title:'数据字典类别',split:true" style="width:20%;padding-top:10px;">
 		<div id="h_tree"></div>
    </div> 
   
    <div data-options="region:'center'" style="padding:5px;background:#FFFFFF;width:90%;">
    	<div  id="list"></div>
    </div> 
    <div id="edit_dlg" style="padding-top:50px;" class="easyui-dialog"  data-options="width: 600,height: 450,closed: true,cache: false,modal: true,top:50"></div>
</body> 
</html>