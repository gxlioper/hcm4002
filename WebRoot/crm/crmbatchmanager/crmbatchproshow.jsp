<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script>
$(document).ready(function () {	
	getbatchproGrid();
});


 //---------------------------------------显示人员计划------------------------------------------------------
 /**
  * 
  */
 function getbatchproGrid(){
		 var model={"batch_id":$("#batch_id").val()};
	     $("#batchprolist").datagrid({
		 url:'getbatchproList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'plandate',title:'计划体检日期',width : 80},
		 	{align:'center',field:'per_num',title:'计划体检人数',width : 60},
		 	{align:'center',field:'creater_date',title:'计划创建时间',width:45}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000,
			toolbar:toolbar
	});
	}
 
  /**
   * 定义工具栏
   */
  var toolbar=[{
 		text:'维护人员计划',
 		iconCls:'icon-edit',
 		width:120,
 	    handler:function(){
 	    	$.ajax({
	    		url : 'checkBatchStatus.action',
	    		type : 'post',
	    		data : {
	    			batch_id:$("#batch_id").val()
	    		},
	    		success : function(data) {
	    			if(data=="未提交"){
	    	 	    	$("#dlg-editpro").dialog({
	    		    		title:'维护人员计划',
	    		    		href:'crmbatchproplanedit.action?batch_id='+$("#batch_id").val()+'&sign_num='+$("#sign_nums").val()
	    		    	});
	    		    	if($("#batch_id").val()>0){
	    		    	   $("#dlg-editpro").dialog("open");
	    		    	}else{
	    		    	  $.messager.alert('提示信息',"请选择单位名称","error");
	    		    	}
	    			}else{
	    				 $.messager.alert('提示信息',"该体检任务已提交审核，不能进行维护人员操作","error");
	    			}
	    		},
	    		error : function() {
	    			$.messager.alert('提示信息', '操作失败！', 'error');
	    		}
	    	});
 	    }
 	}];
 
 /**
  * 显示一条方案
  * @param userid
  */
 function f_showonebatch(){
	 	$("#dlg-show").dialog({
	 		title:'体检任务',
	 		href:'crmbatchoneshow.action?id='+$("#batch_id").val()+'&sign_num='+$("#sign_nums").val()
	 	});
	 	$("#dlg-show").dialog('open');
	 }
</script>
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id" value="<s:property value="model.batch_id"/>">
<input type="hidden" id="sign_nums" value="<s:property value="model.sign_num"/>">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:140px;">单位名称 </dd>
					<dd style="height:20px;width:280px;" ><font id="companyname" style="color:red;font-weight:bold;font-style:italic;"><s:property value="model.comname"/></font></dd>
					<dd style="height:20px;width:140px;" >体检任务名称：</dd>
					<dd style="height:20px;width:280px;" ><font id="batchname" style="color:red;font-weight:bold;font-style:italic;"><s:property value="model.batch_name"/></font>(<a href="javascript:f_showonebatch()">查看</a>)</dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>人员计划列表</strong></legend>
      <table id="batchprolist" class="easyui-datagrid" ></table>	
 </fieldset>
 
 <div id="dlg-editpro" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
  <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
