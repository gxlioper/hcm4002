<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	var type = '<s:property value="model.dep_category"/>';
	var cid = '<s:property value="model.charging_item_ids"/>';
	var eid = '<s:property value="model.exam_num"/>';
	setvalue1(cid,eid,type);
});

function setvalue1(cid,eid,depType){
	getDoctorAndDepRsult(cid,eid);
	if(depType == 17){//一般科室检查
		getptGrid(cid,eid);
	}else if(depType == 131){//检验科室
		gethyGrid(cid,eid);
	}else if(depType == 21){//影像科室
		getyxGrid(cid,eid);
	}else{
		getptGrid(cid,eid);
	}
}

function getptGrid(cid,eid){
	 var model={"exam_num":eid,
			 	"charging_item_ids":cid
			 	};
  $("#itemResultList").datagrid({
		 url:'getPtResultList.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'item_name',title:'检查项目名称',width:10},
		 	{align:'center',field:'exam_result',title:'检查结果',width:10,"styler":f_color_pt}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect:true,
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    fit:true,
		    striped:true,
		    toolbar:"#toolbar_1"
  });
}
function f_color_pt(value,row,index){
	if(row.health_level == 'Y'){
		return 'color:#F00;';
	}else if(row.health_level == 'W'){
		return 'color:#FF9B00;';
	}
}

function gethyGrid(cid,eid){
	 var model={"exam_num":eid,
			 	"charging_item_ids":cid
			 	};
	$("#itemResultList").datagrid({
			 url:'getHyResultList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:false,
		     pageSize: 15,//每页显示的记录条数，默认为10 
		     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			 columns:[[
			    {align:'center',field:'item_name',title:'检查项目名称',width:15},
			 	{align:'center',field:'exam_result',title:'检查结果',width:15,"styler":f_color_hy},
			 	{align:'center',field:'bs',title:'标识',width:10,"formatter":f_bs,"styler":f_color_hy},
			 	{align:'center',field:'item_unit',title:'单位',width:10},
			 	{align:'center',field:'ref_value',title:'参考值',width:15},
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    	},
		    	singleSelect:true,
			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    toolbar:"#toolbar_1"
	});
}
function f_color_hy(value,row,index){
	if(row.ref_indicator == 1 || row.ref_indicator == 2 || row.ref_indicator == 3|| row.health_level == 5|| row.health_level == 6){
		return 'color:#F00;';
	}else if(row.ref_indicator == 4){
		return 'color:#FF9B00;';
	}
}
function f_bs(val,row){
	if(row.ref_indicator == 1){
		return '↑';
	}else if(row.ref_indicator == 2){
		return '↓';
	}else if(row.health_level == 5){
		return row.exam_result+' ↑↑';
	}else if(row.health_level == 6){
		return row.exam_result+' ↓↓';
	}else{
		return '';
	}
}

function getyxGrid(cid,eid){
	 var model={"exam_num":eid,
			 	"charging_item_ids":cid
			 	};
	$("#itemResultList").datagrid({
			 url:'getYxResultList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:false,
		     pageSize: 15,//每页显示的记录条数，默认为10 
		     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			 columns:[[
			    {align:'center',field:'exam_desc',title:'描述',width:20},
			 	{align:'center',field:'exam_result',title:'结论',width:20},
			 	{align:'center',field:'bg',title:'图片',width:10,"formatter":f_show_picture}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    	},
		    	singleSelect:true,
			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    toolbar:"#toolbar_1",
			    nowrap:false
	});
}

function f_show_picture(val,row){
	return '<a href="javascript:void(0)" onclick = "show_picture(\''+row.pacs_req_code+'\')">查看图片</a>';
}

function show_picture(id){
	var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+$("#exam_num1").val();
	newwin = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newwin.focus();
}

function getDoctorAndDepRsult(cid,eid){
	$.ajax({
		url:'getExamDoctorAndDepResult.action',
		data:{"exam_num":eid,
		 	  "charging_item_ids":cid},
		type:'post',
		success:function(data){
			if(data != 'null'){
				var obj=eval("("+data+")");
				$("#doctor_1").html(obj.exam_doctor);
				$("#exam_date_1").html(obj.exam_date);
				$("#depjielun").val(obj.exam_result_summary);
			}
		}
	});
}
</script>
<input type="hidden" id="exam_num1" value="<s:property value="model.exam_num"/>">
<div class="easyui-layout" fit="true">
    <div data-options="region:'center'">
		<table id="itemResultList"></table>
	</div>
	<div data-options="region:'east'" style="width:30%;">
		<div class="easyui-panel" title="科室结论" data-options="fit:true">
		   <textarea readonly="readonly" style="width: 99%;resize:none;border: 0px;height: 100%;font-size:14px;" id="depjielun"></textarea>
		</div>
	</div>
	<div data-options="region:'south'" style="height:47px;" >
		<div class="dialog-button-box">
			<div class="inner-button-box">
			    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
			</div>
		</div>
	</div>
</div>
<div id="toolbar_1">
	<div style="width:250px;float:left;">检查医生：<font id="doctor_1"></font></div>
	<div>检查时间：<font id="exam_date_1"></font></div>
</div>

