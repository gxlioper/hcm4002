<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<title>职业史</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript">
$(document).ready(function() {

	

		if($('#data_code_children').val()=='FSJKJC'){
			//$("#yc_zy").css('display','none'); 
			getcusthisGrid(1);
			getFscusthisGrid(1);
		} else {
			$("#yc_fs").css('display','none'); 
			getcusthisGrid(1);
		} 
		getJWScusthisGrid(1);

	});



	$(function() {
		$('input').attr("maxlength", "20");
	})
	
	//显示一条数据
	var selectexam_id;
	var brushtatle=0;
	function f_findcustomerone(selectexam_id,brushtatle){
		$.post("getzybDjtExamOneShow.action", 
		{
			"exam_id" : selectexam_id
		}, function(jsonPost) {
			var customer = eval(jsonPost);	
			setCustomer(customer,brushtatle);
		}, "json");
	}
		
	function setCustomer(cumtomersd,brushtatle)
	{		
		$("#exam_id").val(cumtomersd.id);
		$("#arch_num").val(cumtomersd.arch_num);	
		$("#exam_num").val(cumtomersd.exam_num);
		$('#addid_num').val(cumtomersd.id_num);		
		$('#occusectorid').val(cumtomersd.occusectorid);
		$("#cyhy_Name").textbox('setValue', cumtomersd.cyhyname);		
		$('#occutypeofworkid').val(cumtomersd.occutypeofworkid);
		$("#cygz_Name").textbox('setValue', cumtomersd.cygzname);		
		$('#occutypeofwork').textbox('setValue',cumtomersd.occutypeofwork);
		$('#occusector').textbox('setValue',cumtomersd.occusector);		
		$('#damage').textbox('setValue',cumtomersd.damage);
		$('#employeeage').textbox('setValue',cumtomersd.employeeage);
		$("#joinDatetime").datebox('setValue',cumtomersd.joinDatetime);
		getJWScusthisGrid();
		if($('#zyb_tjlx').val()=='放射性体检'){
			getFscusthisGrid();
			getcusthisGrid();
		} else {
			getcusthisGrid();
		}
	}

//////////////////////////////职业病历史处理////////////////////////////////////////
var examnum = "";
var editIndex = undefined;
function getcusthisGrid(c){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "exam_num":$("#zys_exam_num").val(),
				 "isradiation":"0"    //放射史
		 };
	     $("#zybocchislist").datagrid({
		 url:'zybCustomerHislist.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 5,//每页显示的记录条数，默认为10 
	     pageList:[5,10,20],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'company',title:'工作单位',width:18},	
		    {align:'center',field:'workshop',title:'车间部门',width:20},
		 	{align:'center',field:'worktype',title:'工种',width:25},
		 	{align:'center',field:'measure',title:'防护措施',width:30},
		 	{align:'center',field:'harmname',title:'有害因素名称',width:20},
		 	{align:'center',field:'concentrations',title:'有害因素浓度',width:20},
		 	{align:'center',field:'startdate',title:'开始时间',width:10},		 	
		 	{align:'center',field:'enddate',title:'结束时间',width:10},
			{align:'center',field:'ss',width:10,title:'修改',"formatter":f_xg_ZYS}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolbar,
	});
}
function f_xg_ZYS(val,row){	
	return '<a href=\"javascript:updateSampleDemoZYS(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
 function updateSampleDemoZYS(id){
		$("#dlg-zybocchisedit").dialog({
		    //left:20,
			//top:0,
			title:'修改职业史',
			//width :1200,
			//height:590,
			href:'zybocchisaupdate.action?zyb_id='+id
		});
		$("#dlg-zybocchisedit").dialog('open');
}
/**
 * 表格编辑行数据保存
 */
function updateupdaterow(model){
	$.ajax({
		url:'zybocchisadddo.action',
		data:model,
		type:'post',
		success:function(data){
				alert_autoClose("操作提示", "保存成功！","");
				//$("#zybocchislist").datagrid('reload');
		},error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	})
}
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'新增',
		iconCls:'icon-add',
		width:58,
	    handler:function(){
	    	if((document.getElementById("addid_num").value=='')&&(document.getElementById("exam_num").value=='')&&(document.getElementById("arch_num").value==''))
	    		{
	    		   $.messager.alert("操作提示", "体检人员无效，请先添加人员", "error");
	    		}else{
	    	       $("#dlg-zybocchisedit").dialog({
		 		       title:'职业史新增',
		 		       href:'zybocchisadd.action?exam_num='+document.getElementById("exam_num").value+'&id_num='+document.getElementById("addid_num").value+'&arch_num='+document.getElementById("arch_num").value
		 	       });
		 	       $("#dlg-zybocchisedit").dialog('open');
	    		}
	    }
	},{
		text:'删除',
		width:58,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#zybocchislist').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    }); 	    	    
    	    delhisrow(ids);
	    }
	}];
 
 /**
  * 批量删除
  */
  var ids;
 function delhisrow(ids){
	/*  if(($("#addbatch_id").val()=='')||(Number($("#addbatch_id").val())<=0)){
 		$.messager.alert("操作提示", "请选择体检任务","error");
 	}else */ 
 	if(ids==''){
 		$.messager.alert("操作提示", "请选择要删除的职业史","error");
 	}else{
	 $.messager.confirm('提示信息','是否确定删除选中职业史？',function(r){
		 	if(r){
		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
	 $.ajax({
			url : 'zybocchisdeldo.action',
			data : {
		            ids:ids
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							alert_autoClose("操作提示", "操作成功！","");
							getcusthisGrid(1);
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
	 });
 	}
 }
    	
	/**
	  * 设置每隔2秒钟刷新父节点的表格
	  */
   function djtupdateAfterClose() {
	     //父窗口去检测子窗口是否关闭，然后通过自我刷新，而不是子窗口去刷新父窗口  
	     if(newWindow.closed == true) {
	     clearInterval(timer); 
	     return;  
	     }  
	}  
	//==========================--------------------------放射性职业史-========================================================--------------------
	 /**
  * 定义工具栏
  */
 var toolss=[{
		text:'新增',
		iconCls:'icon-add',
		width:58,
	    handler:function(){
	    	if((document.getElementById("addid_num").value=='')&&(document.getElementById("exam_num").value=='')&&(document.getElementById("arch_num").value==''))
	    		{
	    		   $.messager.alert("操作提示", "体检人员无效，请先添加人员", "error");
	    		}else{
	    	       $("#dlg-fangsheshi").dialog({
		 		       title:'职业史新增',
		 		       href:'zybFsocchisadd.action?exam_num='+document.getElementById("exam_num").value+'&id_num='+document.getElementById("addid_num").value+'&arch_num='+document.getElementById("arch_num").value
		 	       });
		 	       $("#dlg-fangsheshi").dialog('open');
	    		}
	    }
	},{
		text:'删除',
		width:58,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#zybFsocchislist').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    }); 	    	    
    	    Fsdelhisrow(ids);
	    }
	}];
 	var editIndexFS = undefined;
	function getFscusthisGrid(c){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "exam_num":$("#zys_exam_num").val(),
				 "isradiation":"1"    //放射史
		 };
	     $("#zybFsocchislist").datagrid({
		 url:'zybCustomerHislist.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 5,//每页显示的记录条数，默认为10 
	     pageList:[5,10,20],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'company',title:'工作单位',width:18},	
		    {align:'center',field:'workshop',title:'车间部门',width:20},
		 	{align:'center',field:'worktype',title:'工种',width:25},
		 	{align:'center',field:'radiation',title:'放射线种类',width:30},
		 	{align:'center',field:'man_haur',title:'每日工作量',width:20},
		 	{align:'center',field:'cumulative_exposure',title:'累积受照射剂量',width:20},
		 	{align:'center',field:'history_excessive',title:'过量照射史',width:20},
		 	{align:'center',field:'remark',title:'备注',width:10},
		 	{align:'center',field:'startdate',title:'开始时间',width:15},		 	
		 	{align:'center',field:'enddate',title:'结束时间',width:15},
		 	{align:'center',field:'sss',title:'修改',width:10,"formatter":f_xg},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolss
	    	
	});
}
	 function f_xg(val,row){	
		return '<a href=\"javascript:updateSampleDemo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	}
	 function updateSampleDemo(id){
				$("#dlg-fangsheshi").dialog({
			    //left:20,
				//top:0,
				title:'职业史',
				//width :1200,
				//height:590,
				href:'zybFsocchisupdate.action?zyb_id='+id
			});
			$("#dlg-fangsheshi").dialog('open');
	}
	/**
	  * 批量删除
	  */
	  var ids;
	 function Fsdelhisrow(ids){
	 	if(ids==''){
	 		$.messager.alert("操作提示", "请选择要删除的职业史","error");
	 	}else{
		 $.messager.confirm('提示信息','是否确定删除选中职业史？',function(r){
			 	if(r){
			 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
		 $.ajax({
				url : 'zybocchisdeldo.action',
				data : {
			            ids:ids
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								//$("#zybFsocchislist").datagrid('reload');
								alert_autoClose("操作提示", "操作成功！","");
								getFscusthisGrid();
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
		 });
	 	}
	 }
	    	
 
	//--------------------------既往史--------------------------
	
	function getJWScusthisGrid(c){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "exam_num":$("#zys_exam_num").val()
		 };
	     $("#zybJWSocchislist").datagrid({
		 url:'getDiseaseHistoryTable.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 5,//每页显示的记录条数，默认为10 
	     pageList:[5,10,20],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'left',field:'diseases',title:'疾病名称',width:18},	
		    {align:'left',field:'diagnosiscom',title:'诊疗单位',width:20},
		 	{align:'left',field:'diagnosisnotice',title:'治疗经过',width:25},
		 	{align:'left',field:'diseasereturn',title:'转归',width:30},
		 	{align:'center',field:'diagnosisdate',title:'诊断日期',width:20},
		 	
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolJWS	       
	});
}
	
	  var ids;
	  function JWSdelhisrow(ids){
	 	/*  if(($("#addbatch_id").val()=='')||(Number($("#addbatch_id").val())<=0)){
	  		$.messager.alert("操作提示", "请选择体检任务","error");
	  	}else */ 
	  	if(ids==''){
	  		$.messager.alert("操作提示", "请选择要删除的既往史","error");
	  	}else{
	 	 $.messager.confirm('提示信息','是否确定删除选中既往史？',function(r){
	 		 	if(r){
	 		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 				 $("body").prepend(str);
	 	 $.ajax({
	 			url : 'deletezybDiseaseHistory.action',
	 			data : {
	 		            idss:ids
	 					},
	 					type : "post",//数据发送方式   
	 					success : function(text) {
	 						$(".loading_div").remove();
	 						getJWScusthisGrid();
	 						$.messager.alert("操作提示", text, "info");
	 					},
	 					error : function() {
	 						$(".loading_div").remove();
	 						$.messager.alert("操作提示", "操作错误", "error");					
	 					}
	 				});
	    }
	 	 });
	  	}
	  }
	 /**
	  * 定义工具栏
	  */
	 var toolJWS=[{
			text:'新增',
			iconCls:'icon-add',
			width:58,
		    handler:function(){
		    	if((document.getElementById("addid_num").value=='')&&(document.getElementById("exam_num").value=='')&&(document.getElementById("arch_num").value==''))
		    		{
		    		   $.messager.alert("操作提示", "体检人员无效，请先添加人员", "error");
		    		}else{
		    	       $("#dlg-jiwangshi").dialog({
			 		       title:'职业史新增',
			 		       href:'zybDiseaseHistory.action?exam_num='+document.getElementById("exam_num").value+'&id_num='+document.getElementById("addid_num").value+'&arch_num='+document.getElementById("arch_num").value
			 	       });
			 	       $("#dlg-jiwangshi").dialog('open');
		    		}
		    }
		},{
			text:'删除',
			width:58,
			iconCls:'icon-cancel',
		    handler:function(){
		    	var ids = "";
		    	var checkedItems = $('#zybJWSocchislist').datagrid('getChecked');
	    	    $.each(checkedItems, function(index, item){
	    	    	   ids+="'"+item.id+"',";
	    	    }); 	    	    
	    	    var ss = ids.toString().substring(0,ids.length-1); 
	    	    JWSdelhisrow(ss);
		    }
		}/* ,{
			text:'<a href=\"../../zyb/registerDesk/jiwangshi.xls\">下载模板</a>',
			iconCls:'icon-check',
			handler:function(){
		    }
		},{
		    text:'导入既往史',
			width:100,
			iconCls:'icon-add',
		    handler:function(){
			    		$("#dlg-show").dialog({
					 		title:'上传文件',
					 		href:"getOccuhisUploadPage.action?uploadURL='com/hjw/zyb/ZybUploadJWS'"
					 	});
					 	$("#dlg-show").dialog('open');
		    	
		    } */
	    /*}*/];
</script>
<div>
<input type="hidden"  id="data_code_children"  value="<s:property value='data_code_children'/>"  />
<input type="hidden"  id="zys_exam_num"  value="<s:property value='exam_num'/>"  />
<input type="hidden"  id="addid_num"  value="<s:property value='id_num'/>"  />
<input type="hidden"  id="exam_num"  value="<s:property value='exam_num'/>"  />
<input type="hidden"  id="arch_num"  value="<s:property value='arch_num'/>"  />
<input type="hidden"  id="zyb_tjlx"  value="<s:property value='zyb_tjlx'/>"  />
		<fieldset style="margin: 5px; padding-right: 0;"  id="yc_zy" >
			<legend>
				<strong>非放射性职业史</strong>
			</legend>
			<div class="user-query">
			<table id="zybocchislist">
				</table>
			</div>
		</fieldset>
		<fieldset style="margin: 5px; padding-right: 0;"  id = "yc_fs">
			<legend>
				<strong>放射性职业史</strong>
			</legend>
			<div class="user-query">
			<table id="zybFsocchislist">
				</table>
			</div>
		</fieldset>
		<fieldset style="margin: 5px; padding-right: 0;" >
			<legend>
				<strong>既往史</strong>
			</legend>
			<div class="user-query">
			<table id="zybJWSocchislist">
				</table>
			</div>
		</fieldset>
</div>
<div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-zybocchisedit" class="easyui-dialog"  data-options="width: 850,height: 420,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-fangsheshi" class="easyui-dialog"  data-options="width: 850,height: 420,closed: true,cache: false,modal: true,top:100"></div>
<div id="dlg-jiwangshi" class="easyui-dialog"  data-options="width: 850,height: 420,closed: true,cache: false,modal: true,top:180"></div>
</html>