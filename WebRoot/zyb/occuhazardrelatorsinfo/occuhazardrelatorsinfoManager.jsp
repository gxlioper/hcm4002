<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>职业危害相关信息管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  

<script type="text/javascript">
	$(document).ready(function () { 
		shu();
		enter();
	});
	
	function enter(){
		document.onkeydown = function(e){//回车查询
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	getData();
		     }
		}
	}
	 var hazardclassID_s;
	 function shuxing(){
		$('#h_tree').tree({    
			 url:'getOccuhazardclasstree.action',
			 dataType:'json',
			 loadFilter :function(data,parent){
					 var obj = data;
					 var newData = new Array();
					 for(var i=0;i<obj.length;i++){
						 newData.push({id:obj[i].hazardclassID,text:obj[i].hazardclass_name});
					 }
					 var newDate2 = [{id:'',text:'职业危害类别',children:newData}];
					 return newDate2;
			 },
			 onClick:function(node){
				 hazardclassID_s=node.id;
				 getData();
			 },
			 onLoadSuccess:function(node,data){  
		           $("#h_tree li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
		           var n = $("#h_tree").tree("getSelected");  
		           if(n!=null){  
		                $("#h_tree").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
		           }  
	        },
		});  
	};
	/**
	 * 树
	 */
	function shu(){
		/* var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);*/
		$("#h_tree").tree({
			 url:'getZybExamSetTree.action',
			 onClick:function(node){
			 },
			 onLoadSuccess:function(node){  
			 		$(".loading_div").remove();
		           $("#h_tree li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
		           var n = $("#h_tree").tree("getSelected");  
		           if(n!=null){  
		                $("#h_tree").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
		           }  
	        },
	        onSelect:function(node){
	        	getData(node.hazardclassID,node.whlb);
	        }
		});
	}
	function getData(id,text){
		 	var f = "";
		    var z = "";
		    var whlb="";
			/* if(text=='whlb'){
				whlb=id;
			} else { */
			
				  var node = $("#h_tree").tree('getSelected');
				    if(node.lx=='lx'){//职业危害体检类别
			 	    	f = '\''+$('#h_tree').tree("getParent",node.target).id+'\''; 
			 	    	z = node.id;
				    } else if(node.ys == 'ys'){//因素
				    	f ='\''+ node.id+'\'';
				    	//z = $("#some_tree").tree("getChildren",node.target);
				    } else {
				    	var zi = $('#h_tree').tree("getChildren",node.target)
				    	f = $('#h_tree').tree('getSelected');
				    	var arra = new Array();
				    	for(var i = 0 ; i < zi.length ; i++){
				    		console.log(zi[i].text);
				    		if(zi[i].ys=='ys'){
				    			arra.push('\''+zi[i].id+'\'');
				    		}
				    	}
				    	f = arra.toString();
				    	if(f==""){
				    		f='\'\'';
				    	}
				    }
				
			/* } */
		  
		
		var model={  "hazard_name":$('#hazard_name_s').textbox('getValue'),
		    	//"hazardclass_name":hazardclass_name_s,
		    	"hazardclassID":hazardclassID_s,
		    	"whlb":whlb,
		    	"hazardfactorsID":f,
		    	"OccuphyexaclassID":z	
		};
		$('#list').datagrid({
		    url:'getOccuhazardrelatorsinfoList.action', 
		    queryParams:model,
		    dataType: 'json',
		    pageSize: 15,//每页显示的记录条数，默认为10 
		    pageList:[15,20,30,60,75],//可以设置每页记录条数的列表 
		    columns:[[
		        {align:"center",field:'id',title:'ID',hidden:true},
		        {align:"left",field:'hazard_name',title:'职业危害名称',width:50},
		        {align:"center",field:'occuphyexaclass_name',title:'职业体检类别',width:40},
		        {align:"left",field:'diseaseandtaboo',title:'目标疾病与职业禁忌证',width:100},
		        {align:"left",field:'checkcontent',title:'检查内容',width:100},
		        {align:"left",field:'checkcriterion',title:'检查依据',width:100},
		        {align:"left",field:'followdisease',title:'疾病跟踪',width:60},
		        {align:"left",field:'examperiod',title:'检查周期',width:50},
		        {align:"left",field:'remark',title:'备注',width:20},
		        {align:"center",field:'upd',title:'修改',width:20,"formatter":f_update},
		        {align:"center",field:'del',title:'删除',width:20,"formatter":f_delete}
		    /*     {align:"center",field:'look',title:'查看',width:20,"formatter":f_look}, */
		        
		    ]] ,
       		pagination:true,//分页控件
        	rownumbers:true,//行号
        	fitColumns:true,
        	/* singleSelect:false,
		    fit:true, */
        	toolbar:toolbar,
        	onDblClickRow:function(index, row){
       	   	 var row_id = $('#list').datagrid('getRows')[index].id;
       	  			updateIndustry(row_id);
       	       }
		}); 
	};
	/**
	  * 定义工具栏
	  */
	  var toolbar = [{
		    text:'新增',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#edit_dlg").dialog({
		    		title:'新增',
		    		href:'addOccuhazardrelatorsinfo.action'
		    	});
		    	$("#edit_dlg").dialog("open");
		    }
		}];
	
	//批量删除
	function delete_hri(ids){

		if(ids==""){
			$.messager.alert('提示信息','请选择要删除的行!')
			return;
		}
		$.messager.confirm('提示信息','是否确定删除选中的行',function(r){
		 	if(r){
				 $.ajax({
					url : 'delete_s.action',
					data : {ids:ids},
					type : "post",
					success : function(data) {
							$.messager.alert("操作提示",data);
							getData();
					},
					error : function() {
						$.messager.alert('提示信息', '操作失败！', 'error');
					}
				 });
		 	 }
		 });
	
	}
	//查看
	function f_look(val,row){
		return '<a href=\"javascript:look(\''+row.id+'\')\">查看</a>';
	}
	
	function look(id){
		 $("#look_dlg").dialog({
				title:'职业危害相关详细信息',
				href:'lookOccuhazardrelatorsinfo.action?id='+id
			});
			$("#look_dlg").dialog('open'); 
	}
	//修改按钮
	function f_update(val,row){	
			return '<a href=\"javascript:updateIndustry(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	 }
	 //删除按钮
	function f_delete(val,row){
			return '<a href=\"javascript:deleteIndustry(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}
	//更新
	
	function updateIndustry(id){
	    $("#edit_dlg").dialog({
			title:'修改',
			href:'updateOccuhazardrelatorsinfo.action?id='+id
		});
		$("#edit_dlg").dialog('open'); 
	}
	//删除
	function deleteIndustry(id){
		$.messager.confirm('提示信息','是否确定删除？',function(r){
			if(r){
				$.ajax({
		   		url:'deleteOccuhazardrelatorsinfo.action?id='+id,
		   		type:'post',
		   		success:function(data){
	   			$.messager.alert('提示信息',data);
	   			getData();
	   		    },
		   		error:function(){
		   			$.messager.alert('提示信息','操作失败！','error');
		   		}
	   		});
			}
		}) 
	}
	function cleanFun(){
		$("#hazard_name_s").textbox('setValue',"");
		$("#occuphyexaclass_name_s").textbox('setValue',"");
		hazardclass_name_s='';
		getData();
	}
	function searchFun(){
		getData();
	}
</script>
</head>
<body class="easyui-layout">
 	<div data-options="region:'west',title:'职业危害因素',split:true" style="width:15%;padding-top:10px;">
 		<div id="h_tree"></div>
    </div> 
    <div data-options="region:'center'" style="padding:5px;background:#FFFFFF;width:90%;">
    	<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
		<legend><strong>信息检索</strong></legend>
		<div id="tb" style="padding-right:20px;padding-left:50px;">
			&nbsp;&nbsp;&nbsp;&nbsp;职业危害名称: <input id="hazard_name_s" class="easyui-textbox"  data-options="prompt:'职业危害名称'" />
			&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getData();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
		</div>
		 </fieldset>
    	<div  id="list"></div>
    </div> 
    <div id="edit_dlg" class="easyui-dialog"  data-options="width: 800,height:650,closed: true,cache: false,modal: true,top:50"></div>
    <div id="look_dlg" class="easyui-dialog"  data-options="width: 800,height: 600,closed: true,cache: false,modal: true,top:50"></div>    
</body> 
</html>