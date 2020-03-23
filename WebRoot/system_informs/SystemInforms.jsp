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
<title>站内通知</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/system_informs/SystemInforms.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">


function loadrolename(){
	$("#role_name").combobox({
		url :'queryAllWebRole.action',
		// editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'rolename'
	});
}

$(document).ready(function () {
	
	getGrid();
	
})

	//授权
	var userid;
	var adminId="<s:property value="#application.adminUserId"/>";
	var val;
	function f_shq(val,row){
	 	return '<a href=\"javascript:f_userrole(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"授权\" /></a>';
	}
	//用户授权岗位
	function f_userrole(id){
					$("#dlg-shq").dialog({
					title: '授权',
					href:'empowerInforms.action?id='+id
					});
					$("#dlg-shq").dialog('open');
	}
	
	//启停修改
	function f_qyty(val,row){
	 var html='';
	    if(val=="Y"){
	        if(row.id==adminId){
	        	return '<a style="color:#1CC112;" href=\"javascript:void(\'0\')\">'+html+'</a>';
	        }else{
	           return '<a style="color:#1CC112;" href=\"javascript:f_lockuser(\''+row.id+'\',\'停用\')\">停用</a>';
	        }
	    }else if(val=='N') {
	       return '<a style="color:#f00;" href=\"javascript:f_lockuser(\''+row.id+'\',\'启用\')\">启用</a>';
	     }
	}
	//修改
	function f_xg(val,row){	
			return '<a href=\"javascript:f_xguser(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	 }
	//删除
	function f_sc(val,row){	
			return '<a href=\"javascript:f_deleteInfo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	 }
 
	function f_xguser(id){
				$("#dlg-edit").dialog({
				title:'修改',
				href:'updateSysInform.action?id='+id
						
			});
			$("#dlg-edit").dialog('open');
	}
	function f_deleteInfo(id)
	{
		$.messager.confirm('提示信息','是否确定删除？',function(r){
			if(r){
		    $.ajax({
	   		url:'deleteInforms.action?id='+id,
	   		type:'post',
	   		success:function(data){
	   	
	   			$.messager.alert('提示信息',data); 
	   			 //getGrid();
	   			$('#informationlist').datagrid('reload');
	   		},
	   		error:function(){
	   			$.messager.alert('提示信息','操作失败！','error');
	   		}
	   		});
			}
		})
	}

function getGrid(){
	   var lastIndex;
       var model = {"startDate": $('#startDate').datebox('getValue'),"endDate": $('#endDate').datebox('getValue'),"firstTime": $('#firstTime').datebox('getValue'),"lastTime": $('#lastTime').datebox('getValue')};
	   $("#informationlist").datagrid({
		url: 'systemInformsList.action',
		queryParams: model,
		rownumbers:true,
        pageSize: 15,//每页显示的记录条数，默认为10 
        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
        sortName: 'cdate',
		sortOrder: 'desc',
        //height:430,
        columns:[[
		          {field:'ck',checkbox:true },
				  {align:"center",field:"id","title":"ID","hidden":false},
        		  {align:"center",field:"inform_content","title":"通知内容","width":20},
        		  {align:'center',field:"valid_date","title":"有效时间（有效日期协商）","width":26},
        		  {align:"center",field:"creater","title":"创建人","width":18},
        		  {align:"center",field:"create_time","title":"创建时间","width":18},
        		  {align:"center",field:"updater","title":"修改人","width":18},
        		  {align:"center",field:"update_time","title":"修改时间","width":18},
        		  {align:"center",field:"is_active","title":"启(停)修改","width":18,"formatter":f_qyty},
        		  {align:"center",field:"shc","title":"删除","width":18,"formatter":f_sc},
        		/*   {align:"center",field:"shq","title":"授权","width":18,"formatter":f_shq},      */		  
        		  {align:"center",field:"xg","title":"修改","width":18,"formatter":f_xg},
        		  ]],
        toolbar: [{ 
				 	text:'新增',
			        iconCls:'icon-add',    
			        handler:function(){
			        	
			        	$("#dlg-edit").dialog({
			      	    	title:'新增',
			      	    	href:'addSysInfo.action'
			      	    });
			      	    $("#dlg-edit").dialog("open");
			          }    
		          }],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: true,
        fitColumns:true,
        fit:true,
      
	});
	
	
	}
	function searchFun(){
		getGrid();
	}
	function cleanFun(){
		$('#startDate').datebox('setValue',"");
		$('#endDate').datebox('setValue',"");
		$('#firstTime').datebox('setValue',"");
		$('#lastTime').datebox('setValue',"");
		getGrid();
	}

	 
    </script>
</head>
<body>
<div class="easyui-layout" fit="true">
 <div  data-options="region:'north'" style="height:80px;">
	<fieldset style="margin:5px;padding-right:20px;padding-left:60px;">
	<legend><strong>信息检索</strong></legend>
	<div id="search" style="padding-right:20px;padding-left:80px;">
		&nbsp;&nbsp;&nbsp;有效时间:<input id="startDate" name="starDate" class="easyui-datebox"  data-options="prompt:'起始时间'" />
		&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;<input id="endDate" name="endDate" class="easyui-datebox"  data-options="prompt:'时间'" />
		&nbsp;&nbsp;&nbsp;创建时间:<input id="firstTime" name="firstTime" class="easyui-datebox"  data-options="prompt:'起始时间'" />
		&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;<input id="lastTime" name="lastTime" class="easyui-datebox"  data-options="prompt:'时间'" />
		
		&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
		&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
	</div>
	 </fieldset>
	 </div>
<div data-options="region:'center'" >
 <fieldset style="margin:5px;padding-right:20px;height:95%;">
		<legend><strong>信息列表</strong></legend>
 	<table id="informationlist"></table>
 	</fieldset>
</div>
</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 700,height: 450,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-shq" class="easyui-dialog" data-options="width: 400,height: 450,closed: true,cache: false,modal: true,top:50"></div>

</body>
</html>