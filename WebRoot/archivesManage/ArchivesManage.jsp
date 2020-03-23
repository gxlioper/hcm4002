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
<title>档案管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/default/ecard/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common_comboTree_box.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
	function searchFun(){
		getGrid();
	}
	/* function cleanFun(){
		$('#arch_num_s').textbox('setValue',"");
		$('#user_name_s').textbox('setValue',"");
		$('#id_num_s').textbox('setValue',"");
		$('#time1').datebox('setValue',"");
		$('#time2').datebox('setValue',"");
		getGrid('reload');
	} */
	
 	var toolbar = [{
		    text:'新增',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#Custom_edit").dialog({
		    		title:'新增',
		    		href:'customadd.action'
		    	});
		    	$("#Custom_edit").dialog("open");
		    }
		}];
	
	$(document).ready(function () {
	    getGrid();
	});
	
	function getGrid(){
		var chk_value ="";    
		  $('input[name = chkItem]:checked').each(function(){    
		   chk_value=chk_value+","+($(this).val());    
		  }); 
		  if(chk_value.length>1){
			  chk_value=chk_value.substring(1,chk_value.length);
		  }
	       var model = {
	    		   "arch_num": $('#arch_num').textbox('getValue'),
	    		   "user_name": $('#user_name').textbox('getValue'),
	    		   "id_num":$("#id_num").textbox('getValue'),
	    		   "sex":$("#sex").textbox('getValue'),
	    		   "time1":$("#time1").datebox('getValue'),
	    		   "time2":$("#time2").datebox('getValue'),
	    		   "chkItem":chk_value,
	    		   };
		   $("#Customlist").datagrid({
			url: 'customeListshow.action',
			queryParams: model,
			ctrlSelect:true,
	        pageSize: 15,//每页显示的记录条数，默认为10 
	        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			sortOrder: 'asc',
			//height:400,
	        columns:[[
	                  {align:'center',field:"caozuo","title":"操作",width:20,"formatter":f_operate},
	                  {align:'center',field:"arch_num","title":dahname,width:20,sortable:true},
	        		  {align:"center",field:"user_name","title":"体检用户姓名","width":20,sortable:true},
	        		  {align:'center',field:"id_num","title":"身份证号","width":20,sortable:true},
	        		  {align:"center",field:"sex","title":"性别","width":18,sortable:true},
	        		  {align:"center",field:"birthday","title":"生日","width":20,sortable:true},
	        		  {align:"center",field:"nation","title":"民族","width":20},
	        		  {align:"center",field:"is_Active_y","title":"是否激活","width":20}
	        		  ]],
		    onLoadSuccess:function(value){
		    	$("#datatotal").val(value.total);
		    },
		    height: window.screen.height-400,
			nowrap:false,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolbar
		});
	}
	 function f_operate(val,row){
		 var str = '<a href=\"javascript:updatecustom(\''+row.id+'\')\">修改</a>&nbsp;&nbsp&nbsp&nbsp;&nbsp;&nbsp&nbsp&nbsp'
		 	str += '<a href=\"javascript:bindPerson(\''+row.id+'\')\">绑定人员</a>'
		 return str;
	 }
	 function updatecustom(id){
	 	$("#Custom_edit").dialog({
			title:'修改',
			href:'upCustom.action?id='+id
		});
		$("#Custom_edit").dialog('open');
		}
	  function bindPerson(id){
		$("#r_edit").dialog({
			title:'绑定人员',
			href:'bindPerson.action?customer_id='+id
		})
		$("#r_edit").dialog('open');
	  }
	    </script>
	</head>
	<body>
	<!-- <div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:80px;"> -->
	<fieldset style="margin:5px;padding-right:0;">
	<legend><strong>信息检索</strong></legend>
	<div  class="user-query" >
		<dl>
			<dt style="height:26px;width:100px;  text-align: center;"><input id="chkItem" name="chkItem" type="checkbox" value="arch_num"/><s:text name="dahname"/></dt>
			<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:140px;"/></dd>
			
			<dt style="height:26px;width:80px;text-align: center;"><input id="chkItem" name="chkItem" type="checkbox" value="user_name"/>用户姓名</dt>
			<dd><input class="easyui-textbox"  type="text" id="user_name" value="" style="height:26px;width:140px;"/></dd>
			
			<dt style="height:26px;width:80px;text-align: center;"><input id="chkItem" name="chkItem" type="checkbox" value="id_num"/>身份证号</dt>
			<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:200px;"/></dd>
			
			<dt style="height:26px;width:80px;text-align: center;"><input id="chkItem" name="chkItem" type="checkbox" value="sex"/>性别</dt>
			<dd><select class="easyui-combobox" id="sex" data-options="height:26,width:80,panelHeight:'auto'" >
			<option value="">全部</option><option value="男">男</option><option value="女">女</option></select></dd>
			
			<dt style="height:26px;width:80px;text-align: center;"><input id="chkItem" name="chkItem" type="checkbox" value="birthday"/>生日</dt>
			<dd><input class="easyui-datebox" id="time1" style="width:100px;height:26px;"/>至</dd>
			<dd><input class="easyui-datebox" id="time2" style="width:100px;height:26px;"/></dd>
			<dd><a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:80px;">查询</a></dd>
		</dl>
	</div>
	 </fieldset>
	 <!-- </div>
	 <div  data-options="region:'center'"> -->
		<fieldset style="margin:5px;padding-right:0;">
		<legend><strong>档案信息</strong></legend>
		  <table id="Customlist" ></table>
		</fieldset>
	<!--  </div>
	 </div> -->
	 
	 <div id="Custom_edit" class="easyui-dialog" data-options="width: 600,height: 400,closed: true,cache: false,modal: true,top:50" ></div>
	  <div id="r_edit" class="easyui-dialog" data-options="width: 600,height: 400,closed: true,cache: false,modal: true,top:50" ></div>
	</body>
	</html>