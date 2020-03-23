<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>系统配置管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>

	
	<script type="text/javascript" charset="UTF-8">
		$(document).ready(function () {
		    getGrid();
		    search();
		});
		function search(){
			$("#XTPZLX").combobox({
				url :'getDatadis.action?com_Type=XTPZLX',
				//editable : false, //不可编辑状态
				cache : false,
				panelHeight : 'auto',//自动高度适合
				valueField : 'data_code_children',
				textField : 'name'
			})
		}
		//查询按钮
		function searchFun(){
			getGrid();
		}
		//清空按钮
		function cleanFun(){
			$("#data_name").textbox('setValue',"");
			$("#XTPZLX").combobox('setValue',"");
			getGrid();
		}
		
		//添加按钮
		var toolbar = [{
			   /*  text:'新增',
			    iconCls:'icon-add',
			    handler:function(){
			    	$("#dlg-edit").dialog({
			    		title:'添加',
			    		href:'addcenterconfig.action'
			    	});
			    	$("#dlg-edit").dialog("open");
			    } */
			}];
		//修改按钮
		function f_xg(val,row){	
				return '<a href=\"javascript:updateDaDt(\''+row.config_key+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
		 }
		
		//更新
		function updateDaDt(config_key){
			var config_keybm =encodeURI(config_key);
			config_keybm = encodeURI(config_keybm);
					$("#dlg-edit").dialog({
					title:'修改',
					href:'updatecenterconfig.action?config_key='+config_keybm
				});
				$("#dlg-edit").dialog('open');
				
		}
		
		function getGrid(){
			
			var model = {
					"center_common": $('#data_name').textbox('getText'),
					"center_type":$("#XTPZLX").combobox('getValue')
					};
			   $("#DaDtList").datagrid({
				 
				url: 'centerConfigList.action',
				queryParams: model,
				ctrlSelect:true,
		        pageSize: 13,//每页显示的记录条数，默认为10 
		        pageList:[13,30,45,60,75],//可以设置每页记录条数的列表 
		        toolbar: '#toolbar',
				height:400,
		        columns:[[{align:'center',field:"id",title:"ID",width:15,checkbox:true},
		                  {align:'center',field:"center_name",title:"体检中心",width:30},
		                  {align:'center',field:"config_key",title:"配置KEY",width:30},
		        		  {align:"center",field:"config_value",title:"配置VALUE",width:20},
		        		  {align:"center",field:"is_active",title:"是否生效",width:10},
		        		  {align:'center',field:"common",title:"配置说明",width:40},
		        		  {align:'center',field:"data_name",title:"配置类型",width:10},
		        		  {align:"center",field:"xiugai",title:"修改",width:15,"formatter":f_xg}
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
    <div  data-options="region:'north'" style="height:60px;">
		<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
			<legend><strong>系统配置信息检索</strong></legend>
			  <div id="tb" style="padding-right:20px;padding-left:50px;">
			  
			  <dt style="height: 26px; width: 60px;">配置类别:</dt>
				<dd>
					<select class="easyui-combobox" id="XTPZLX" name="XTPZLX"
						data-options="height:26,width:100,panelHeight:'auto'">
					</select>
				</dd>
				&nbsp;&nbsp;&nbsp;&nbsp;配置描述: <input id="data_name" name="data_name" class="easyui-textbox"   />
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
				<!-- &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a> -->
			  </div>
		 </fieldset>
		 </div>
	 <div  data-options="region:'center'">
		<fieldset style="margin:5px;padding-right:20px;height:440px;">
			<legend><strong>系统配置信息</strong></legend>
				 <div id="DaDtList"> </div>
		</fieldset>
		</div>
	 </div>
	<div id="dlg-edit" class="easyui-dialog" data-options="width: 530,height: 410,closed: true,cache: false,modal: true,top:5"></div>
	</body>
</html>