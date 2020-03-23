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
<script type="text/javascript" src="<%=request.getContextPath()%>department/center_departadd.js?randomId=<%=Math.random()%>"></script>
	<script type="text/javascript">
	 function chaxun(){
		$("#dep_category1,#dep_category1_gy").combobox({
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
	$(document).ready(function () {
	    getGrid();
	    chaxun();
	    getGridgy();
	});
	
	function getGrid(){
	       var model = {  
			    		   "c_center_num":"center_num",
			    		   "dep_name": $('#dep_name1').textbox('getValue'),
			    		   "dep_num": $('#dep_num1').textbox('getValue'),
			    		   "dep_category":$("#dep_category1").combobox('getValue'),
			    		   "sex":$("#sex_s").val()
	    		   }
		   $("#deplist").datagrid({
			url: 'departmentList.action',
			queryParams: model,
			ctrlSelect:true,
	        pageSize: 15,//每页显示的记录条数，默认为10 
	        pageList:[15,30,45,60,75,100],//可以设置每页记录条数的列表 
	       //toolbar: '#toolbar',
	       // sortName: 'dep_name',
			sortOrder: 'asc',
			height:390,
	        columns:[[
        			  {field:'ck',checkbox:true},
	                  {align:'center',field:"dep_num","title":"科室编码",width:18},
	        		  {align:"center",field:"dep_name","title":"科室名称","width":20},
	        		  {align:'center',field:"dep_category","title":"科室类型","width":18},
	        		  {align:"center",field:"sex","title":"适用性别","width":18},
	        		  {align:"center",field:"seq_code","title":"顺序码","width":15},
	        	 	  {align:"center",field:"calculation_ratess","title":"利润率","width":25,"formatter":xg_rate}, 
	        		  {align:"center",field:"remark1","title":"备注","width":20},
	        		  {align:"center",field:"dep_address","title":"地址","width":20},
	        		  {align:"center",field:"c_chi_name","title":"操作人","width":30},
	        		  /* {align:"center",field:"u_chi_name","title":" 更新者","width":15}, */
	        		  {align:"center",field:"create_time","title":"更新时间","width":30},
	        		  {align:"center",field:"isPrint_Barcode_cs","title":"是否打印条码","width":26},       		  
	        		  {align:"center",field:"dep_inter_num","title":"其他科室编码","width":26},
	        		  /* {align:"left",field:"dep_link","title":"科室链接","width":50}, */
	        		  {align:"center",field:"xg_sc","title":"修改","width":15,"formatter":xg_sc},
	          		  {align:'center',field:'ss',title:'删除',width:10,"formatter":f_sc}
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
	        toolbar:toolbar1,
	      	fit:true
		});
	}
	function f_sc(val, row) {
		return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}
	function f_deluser(id) {
		$.messager.confirm("提示信息","是否删除？",function(r){
			if(r){
				$.ajax({
					url : 'deleteCenterDep.action?dep_ids='+id,
					type : 'post',
					success : function(data) {
						$.messager.alert('提示信息', data,"info");
						$('#deplist').datagrid('reload');
					},
					error : function() {
						$.messager.alert('提示信息', '操作失败！', 'error');
					}
				});
			}
		})
	}		
	 function xg_sc(val,row){
		 var str = '&nbsp;&nbsp;&nbsp;<a href=\"javascript:updatedepPage(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
		 return str;
	 }
	 function updatedepPage(id){
			$("#dep_edit").dialog({
			title:'修改科室',
			href:'updateCenterDepPage.action?id='+id
		});
		$("#dep_edit").dialog('open');
	 }
	 function updatedep(id){
			$.ajax({
	    		url:"batchCenterDep.action",
	    		data:{dep_ids:id},
	    		success:function(){
	    			$.messager.alert("提示信息","操作成功","info");
	    		},error:function(){
	    			$.messager.alert("提示信息","操作失败","error");
	    		}
	    	})
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
		 var str = '&nbsp;&nbsp;<a href=\"javascript:updateCenter(\''+row.id+'\')\">设置</a>'
		 		+ '&nbsp;&nbsp;'+row.centernames;
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
		   		url:'centerupdatedepRate.action?id='+id,
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
	 //---------------------------多体检--所有有科室--非当前体检中心-----------------------------------
	 function getGridgy(){
	       var model = {
			    		   "dep_name": $('#dep_name1_gy').textbox('getValue'),
			    		   "dep_num": $('#dep_num1_gy').textbox('getValue'),
			    		   "dep_category":$("#dep_category1_gy").combobox('getValue'),
			    		   "sex":$("#sex_s_gy").val(),
			    		   "isActive":"Y"
   		   			}
		   $("#deplist_gy").datagrid({
			url: 'departmentList.action',
			queryParams: model,
			ctrlSelect:true,
	        pageSize: 15,//每页显示的记录条数，默认为10 
	        pageList:[15,30,45,60,75,100],//可以设置每页记录条数的列表 
	       //toolbar: '#toolbar',
	       // sortName: 'dep_name',
			sortOrder: 'asc',
			height:350,
	        columns:[[
        			  {field:'ck',checkbox:true},
	                  {align:'center',field:"dep_num","title":"科室编码",width:18},
	        		  {align:"center",field:"dep_name","title":"科室名称","width":20},
	        		  {align:'center',field:"dep_category","title":"科室类型","width":18},
	        		  {align:"center",field:"sex","title":"适用性别","width":18},
	        		  {align:"center",field:"seq_code","title":"顺序码","width":15},
	        		  {align:"center",field:"calculation_ratess","title":"利润率","width":25},
	        		  {align:"center",field:"remark","title":"备注","width":20},
	        		  {align:"center",field:"creater","title":"创建人","width":15},       		  
	        		  {align:"center",field:"create_time","title":"创建时间","width":30},
	        		/*   {align:"center",field:"centernames","title":"体检中心","width":100,"formatter":xg_center}, */
	        		  {align:"center",field:"updater","title":" 更新者","width":15},
	        		  {align:"center",field:"update_time","title":"更新时间","width":30},
	        		  {align:"center",field:"isPrint_Barcode_y","title":"是否打印条码","width":26},       		  
	        		  {align:"center",field:"dep_inter_num","title":"其他科室编码","width":26},
	        		 /*  {align:"left",field:"dep_link","title":"科室链接","width":50}, */
	        		  {align:"center",field:"tj","title":"添加到本体检中心","width":15,"formatter":tj}
	  
	        		 /*  {align:"center",field:"isActive","title":"启(停)修改","width":22,"formatter":f_qt} */
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
	        toolbar:toolbar,
	      	fit:true
		});
	}
	function searchFungy(){
		getGridgy();
	}
	function cleanFungy(){
		$('#dep_category1_gy').combobox('setValue',"");
		$('#dep_name1_gy').textbox('setValue',"");
		$('#dep_num1_gy').textbox('setValue',"");
		$("#sex_s_gy").val(' ');
	}
	 function tj(val,row){
		 return '<a href=\"javascript:updateBatchCenterdep(\''+row.id+'\',\'启用\')\"><img style="height:20px;width:20px" src=\"themes/al/img/tianjia.png\"  alt=\"添加到体检中心\" /></a>';
	 }
	 function updateBatchCenterdep(id){
		 $.ajax({
	    		url:"batchCenterDep.action",
	    		data:{dep_ids:id},
	    		success:function(){
	    			$.messager.alert("提示信息","操作成功","info");
	    		},error:function(){
	    			$.messager.alert("提示信息","操作失败","error");
	    		}
	    	})
	 }
	 
		
	 	var toolbar = [{
				    text:'批量添加',
				    iconCls:'icon-add',
				    handler:function(){
				    	var dep_rows = $('#deplist_gy').datagrid('getSelections');
				    	console.log(dep_rows);
				    	if(dep_rows.length == 0 ){
				    		$.messager.alert("提示信息","请选择数据","error");
				    		return;
				    	}
				    	var dep_ids = new Array();
				    	for(var i = 0 ; i < dep_rows.length ;i++){
				    		dep_ids.push(dep_rows[i].id);
				    	}
				    	console.log(dep_ids.toString());
				    	//alert(dep_ids.toString());
				    	$.ajax({
				    		url:"batchCenterDep.action",
				    		data:{dep_ids:dep_ids.toString()},
				    		success:function(){
				    			$.messager.alert("提示信息","操作成功","info");
				    		},error:function(){
				    			$.messager.alert("提示信息","操作失败","error");
				    		}
				    	})
				    }
			}];
	 	var toolbar1 =  [{
						    text:'批量删除',
						    iconCls:'icon-add',
						    handler:function(){
						    	var dep_rows = $('#deplist').datagrid('getSelections');
						    	if(dep_rows.length == 0 ){
						    		$.messager.alert("提示信息","请选择数据","error");
						    		return;
						    	}
						    	var dep_ids = new Array();
						    	for(var i = 0 ; i < dep_rows.length ;i++){
						    		dep_ids.push(dep_rows[i].id);
						    	}
						    	f_deluser(dep_ids.toString());
						    }
						}];
    </script>
	</head>
	<body  class="easyui-layout">   
	<input type="hidden" id="usertype" value="<s:property value="#session.username.usertype"/>" />
	 		<div data-options="region:'center'" style="padding:5px;">
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
									&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
								</div>
			     </fieldset>
				 <fieldset style="margin:5px;padding-right:20px;height:95%;">
						<legend><strong>科室信息</strong></legend>
					    <div id="deplist"></div>
		    	 </fieldset>
	 		</div>   
	 		<div data-options="region:'east',title:'科室',collapsed:true,fit:true" style="width:1000px;">
 				 <fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
						<legend><strong>科室信息检索</strong></legend>
								<div id="tb" style="padding-right:20px;padding-left:30px;">
									&nbsp;&nbsp;&nbsp;科室名称: <input id="dep_name1_gy" name="dep_name1_gy" class="easyui-textbox"  data-options="prompt:'科室名称'" />
									&nbsp;&nbsp;&nbsp;科室编码: <input id="dep_num1_gy" name="dep_num1" class="easyui-textbox" data-options="prompt:'科室编码'"/>
									&nbsp;&nbsp;&nbsp;科室类型:<input id="dep_category1_gy" name="dep_category1" class="easyui-combobox"  data-options="prompt:'科室类型'" />
									&nbsp;&nbsp;&nbsp;适用性别: <select style="width:150px;" id="sex_s_gy" >
									    	       		<option  value="全部">全部</option>
									    	       		<option  value="男">男</option>
									    	       		<option  value="女">女</option>
									    	       </select>
									&nbsp;&nbsp;&nbsp;<a href="javascript:searchFungy();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
									<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="searchFun();">查&nbsp;&nbsp;询&nbsp;</a> -->
								</div>
			     </fieldset>
				 <fieldset style="margin:5px;padding-right:20px;height:95%;">
						<legend><strong>科室信息</strong></legend>
					    <div id="deplist_gy"></div>
		    	 </fieldset>
	 		</div>   
	 <div id="dep_center" class="easyui-dialog" data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50" ></div>
	 <div id="dep_edit" class="easyui-dialog" data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50" ></div>
	</body>
	</html>