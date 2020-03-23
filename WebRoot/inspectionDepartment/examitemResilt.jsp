<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	var row = $("#contractlist").datagrid("getRows");
	var data = null;
	for(i=0;i<row.length;i++){
		if(examinfo_id == row[i].exam_num){
			data = row[i];
		}
	}
	
	getcustomerInfo(data);
	getDepAndItem(data.exam_num);
	getFinalSummary(data.exam_num);
	getExamDisease(data.exam_num);
});

//查询人员基本信息
function getcustomerInfo(obj){
	$("#exam_id").val(obj.exam_num);
	$("#user_name1").html(obj.user_name);
	$("#sex1").html(obj.sex);
	$("#age1").html(obj.age);
	$("#company1").html(obj.company);
	$("#customer_type1").html(obj.customer_type);
	$("#set_name1").html(obj.set_name);
}
//总检结论
function getFinalSummary(eid){
	$.ajax({
		url:'getFinalSummaryResult.action',
		data:{"exam_num":eid},
		type:'post',
		success:function(data){
			if(data != 'null'){
				var obj=eval("("+data+")");
				$("#zongjianjielun").val(obj.final_exam_result);
				var is_acc = '<s:property value="isAcceptanceCheck"/>';
				if(is_acc == 'Y'){
					if(obj.acceptance_check == 0){
						$("#hs_button").show();
					}else{
						$("#qx_button").show();
					}
				}
			}
		}
	});
}

function getExamDisease(exam_num){
	 var model={"exam_num":exam_num};
	$("#exam_disease").datagrid({
			 url:'getExamDiseaseResult.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
		     pageSize: 15,//每页显示的记录条数，默认为10 
		     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			 columns:[[
			    {align:'center',field:'disease_name',title:'阳性/疾病发现',width:10},
			 	{align:'center',field:'suggest',title:'阳性/疾病建议',width:20}
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
			    nowrap:false
	});
}

//查询科室与项目树
function getDepAndItem(id){
	$.ajax({
		url:'getDepAndItemTree.action?exam_num='+id,
		type:'post',
		success:function(data){
			mytree = new dTree('mytree','../../images/img/','no','no');
			mytree.add(0,-1,"科室","javascript:void(0)","根目录","_self",false);
			var obj=eval("("+data+")");
			for(var index = 0,l = obj.length;index<l;index++){
				if((obj[index].attributes == null)||(obj[index].attributes == '')||(obj[index].attributes == '0')){
					mytree.add(obj[index].id,
							0,
							obj[index].text,
							"",
							obj[index].text
							,"_self",false);
				}else{
					mytree.add(obj[index].id,
							obj[index].attributes,
							obj[index].text,
							"javascript:setvalue1('"+obj[index].id+"','"+id+"',"+obj[index].state+")",
							obj[index].text,"_self",false);
				}
			}
			$("#dep_item_div").html(mytree.toString());
			getptGrid('c0',0);
		}
	});
}

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
			 	"charging_item_ids":cid.substring(1,cid.length)
			 	};
  $("#itemResultList").datagrid({
		 url:'getPtResultList.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
		 height:388,
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
			 	"charging_item_ids":cid.substring(1,cid.length)
			 	};
	$("#itemResultList").datagrid({
			 url:'getHyResultList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:false,
			 height:388,
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
			 	"charging_item_ids":cid.substring(1,cid.length)
			 	};
	$("#itemResultList").datagrid({
			 url:'getYxResultList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:false,
			 height:388,
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
			    striped:true,
			    toolbar:"#toolbar_1",
			    nowrap:false
	});
}

function f_show_picture(val,row){
	return '<a href="javascript:void(0)" onclick = "show_picture(\''+row.pacs_req_code+'\')">查看图片</a>';
}

function show_picture(id){
	var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+$("#exam_id").val();
	newwin = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newwin.focus();
}

function getDoctorAndDepRsult(cid,eid){
	$.ajax({
		url:'getExamDoctorAndDepResult.action',
		data:{"exam_num":eid,
		 	  "charging_item_ids":cid.substring(1,cid.length)},
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
//核收与取消核收
function f_heshou(check){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'acceptanceCheckExamInfo.action',
		data:{"exam_num":$("#exam_id").val(),
		 	  "acceptance_check":check},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			if(data == 'ok'){
				if(check == 0){
					$.messager.alert('提示信息', '取消核收成功!',function(){});
				}else{
					$.messager.alert('提示信息', '核收成功!',function(){});
				}
				$('#dlg-edit').dialog('close');
				return;
			}else{
				$.messager.alert('提示信息', '未终检，不能核收!',function(){});
				return;
			}
		}
	});
}
</script>
<div class="easyui-layout" style="height:430px;">
	<div data-options="region:'west',split:true" style="width:21%;">
 			<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>基本信息</strong></legend>
					<div class="user-query">
						<dl><dt>姓名：<input type="hidden" name="exam_id" id="exam_id"/></dt><dt id="user_name1"></dt></dl>
						<dl><dt>性别：</dt><dt id="sex1"></dt></dl>
						<dl><dt>年龄：</dt><dt id="age1"></dt></dl>
						<dl><dt>单位：</dt><dt id="company1"></dt></dl>
						<dl><dt>人员类型：</dt><dt id="customer_type1"></dt></dl>
						<dl><dt>体检套餐：</dt><dt id="set_name1"></dt></dl>
					</div>
			</fieldset>
	</div>
    <div data-options="region:'center',border:false">
     			<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
        			<div title="科室检查结果" style="padding:5px;" >
        				<div class="easyui-layout" style="height:390px;">
        					<div data-options="region:'west',split:true" style="width:215px;">
        						<p><a style="margin-left: 25%;" href="javascript: mytree.openAll();">全部展开
        						</a> | <a href="javascript: mytree.closeAll();">全部关闭</a></p> 
                      			<div id="dep_item_div">
                      			</div>
        					</div>
        					<div data-options="region:'center'">
		            			<table id="itemResultList">
		      					</table>
	      					</div>
	      					<div data-options="region:'east'" style="width:192px;">
		            			<div class="easyui-panel" title="科室结论" data-options="">
		            			<textarea readonly="readonly" style="width: 185px;resize:none;border: 0px;height: 351px;font-size:14px;" id="depjielun"></textarea>
		            			</div>
	      					</div>
      					</div>
        			</div>
       				<div title="总检结论与建议" style="padding:5px;">
       					<div class="easyui-layout" style="height:390px;">
   							<div data-options="region:'west'" style="width:223px;">
   								<div class="easyui-panel" title="总检结论" data-options="">
		            			<textarea readonly="readonly" style="width: 215px;resize:none;border: 0px;height: 351px;font-size:14px;" id="zongjianjielun"></textarea>
		            			</div>
   							</div>
   							<div data-options="region:'center'">
	   							<table id="exam_disease">
	      						</table>
      						</div>
      					</div>
        			</div>
    	</div>
	</div>
</div>

<div id="toolbar_1">
	<div style="width:250px;float:left;">检查医生：<font id="doctor_1"></font></div>
	<div>检查时间：<font id="exam_date_1"></font></div>
</div>

<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a id="hs_button" href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" onclick="javascript:f_heshou(1);">核收</a>
	    <a id="qx_button" href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" onclick="javascript:f_heshou(0);">取消核收</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>