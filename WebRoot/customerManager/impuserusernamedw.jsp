<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(document).ready(function() {
	setfzchareitemListGrid();		
	});

	//----------------------------------------显示分组收费项目-------------------------------------------------
	/**
	 * 显示体检项目套餐信息
	 */
	 var lastIndex; 
	function setfzchareitemListGrid() {
		var custname = encodeURI(encodeURI($("#custname").val()));
		var model = {
			"custname":$("#custname").val()
		};
		$("#coustomerlist").datagrid({
			url : 'impuserusernamedwshow.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : false,
			pageSize: 10,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [ [ {align : 'center',field : 'user_name',title : '姓名',width : 25}, 
			              {align : 'center',field : 'arch_num',title : dahname,width : 20,},			              
			              {align : 'center',field : 'id_num',title : '证件号码',width : 20},
			              {align : 'center',field : 'employeeID',title : '工号',width : 15},
			              {align : 'center',field : 'phone',title : '联系电话',width : 20},			              
			              {align : 'center',field : 'sex',title : '性别',width : 25}, 
			              {align : 'center',field : 'age',title : '年龄',width : 25}, 
			              {align : 'center',field : 'birthday',title : '出生日期',width : 20},	
			              {align : 'center',field : 'is_marriage',title : '婚否',width : 20},	
			              {align : 'center',field : 'join_date',title : '体检日期',width : 20},			              
			              {align : "center",field : "fxfzddd",title : "选择",	width : 15,	"formatter" : f_selectat},
			            ] ],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
			},
			singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false
		});
	}
	
	/**
	  * 显示一条方案
	  * @param val
	  * @param row
	  * @returns {String}
	  */
	  function f_selectat(val,row){	
		  return '<a href=\"javascript:f_savedo(\''+row.id+'\')\">选择</a>';
	  }

	 var examid
	 function f_savedo(examid){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
		 $.ajax({
				url : 'impuserusernamedwdo.action',
				data : {
					exam_id : examid,
					id:$("#id").val(),
					batch_id:$("#batch_idss").val(),
					company_id:$("#company_id").val()
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								getgroupGrid();
								$('#dlg-show').dialog('close');								
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					}); 	
	 }
</script>
<input type="hidden" id="id" value="<s:property value="model.id"/>">
<input type="hidden" id="custname" value="<s:property value="model.custname"/>">
<input type="hidden" id="batch_idss" value="<s:property value="model.batch_id"/>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>姓名模糊查询结果列表</strong>
	</legend>
	<div id="coustomerlist"></div>		
</fieldset>

