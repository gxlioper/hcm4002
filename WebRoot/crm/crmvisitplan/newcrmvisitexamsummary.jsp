<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	var exam_num = $("#exam_nums").val();
	var arch_num=$("#arch_nums").val();
	tstabs(arch_num);
});
function tstabs(arch_num){
	$('#ts').tabs({  
		fit:true,
	    border:false,
	    onSelect:function(title,index){
	    	$.ajax({
	    		url:'getExamInfoByArchNumAndJoinDate.action',
	    		data:{"arch_num":arch_num,"join_date":title},
	    		type:'post',
	    		success:function(data){
	    			if(data != 'null'){
	    				var obj=eval("("+data+")");
	    					var ex=obj.exam_num;
	    					var eid=obj.id;
	    					getptGrid(ex);
	    					gethyGrid(ex);
	    					getyxGrid(ex);
	    					getFinalSummary(ex);
	    					getExamDisease(ex);
	    					$('#tt').tabs('select',0);
	    					if($('#ts').tabs('getTab',1)!=null){
	    						 $('#ts').tabs('update', {
	 								tab: $('#ts').tabs('getTab',index),
	 								options: {
	 									title:title,    
	 								    content:$('#tt')
	 								}
	 							}); 	
	    					}

	    			}
	    		}
	    	});
	    }    
	});  
	fajaxtabs(arch_num);
	
}
function fajaxtabs(arch_num){
	$.ajax({
		url:'getExamInfoByArchNum.action',
		data:{"arch_num":arch_num},
		type:'post',
		success:function(data){
			if(data != 'null'){
				var obj=eval("("+data+")");
				for(var i=0;i<obj.length;i++){
					var ejoin=obj[i].join_date;
					$('#ts').tabs('add',{    
					    title:ejoin,    
					    content:$('#tt')
					}); 
					$('#ts').tabs('select',0);
				}
			}
		}
	});
	
}
function f_show_picture(val,row){
	if(row.exam_result == 'image_path'){
		return '<a href="javascript:void(0)" onclick = "show_picture('+row.item_id+')">查看图片</a>';
	}else{
		return row.exam_result.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'</br>');
	}
}
function f_color_pt(value,row,index){
	if(row.health_level == 'Y'){
		return 'color:#F00;';
	}else if(row.health_level == 'W'){
		return 'color:#FF9B00;';
	}
	if(row.item_id == '0'){
		return 'background:#FEEAA8;color:#ff5100;';
	}
}

function f_result_pt(value,row,index){
	if(row.item_id == '0'){
		return row.exam_result.replace(/\n/g,'</br>');
	}else{
		return row.exam_result;
	}
}

function f_color_pt1(value,row,index){
	if(row.health_level == 'Y'){
		return 'color:#F00;';
	}else if(row.health_level == 'W'){
		return 'color:#FF9B00;';
	}
}
function f_color_hy(value,row,index){
	if(row.health_level == 1 || row.health_level == 2 || row.health_level == 3){
		return 'color:#F00;';
	}else if(row.health_level == 4){
		return 'color:#FF9B00;';
	}
}
function f_color_yx(val,row){
	if(row.item_name == '检查结论'){
		return 'background:#FEEAA8;color:#ff5100;';
	}
}
//获取普通科室检查结果
function getptGrid(exam_num){
	 var model={"exam_num":exam_num};
	 $("#pt_itemResultList").datagrid({
		 url:'getPtDepResultList.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
		 columns:[[
		           {align:'center',field:'dep_name',title:'收费项目',width:10},
		           {align:'center',field:"exam_doctor","title":"检查医生","width":10},
		           {align:'center',field:"exam_date","title":"检查时间","width":15},
		           {align:'center',field:'item_name',title:'检查项目',width:15,"styler":f_color_pt},
		           {align:'center',field:'exam_result',title:'检查结果',width:25,"styler":f_color_pt,"formatter":f_result_pt}
		 ]],
		 onLoadSuccess:function(value){
	    		MergeCells('pt_itemResultList', 'dep_name,exam_doctor,exam_date');
		 },
		 singleSelect:true,
		 collapsible:true,
		 pagination: false,
		 fitColumns:true,
		 striped:true,
		 fit:true,
		 nowrap:false
});
}


function getyxGrid(exam_num){
	var model={"exam_num":exam_num};
	$("#yx_itemResultList").datagrid({
			 url:'getYxDepResultList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:false,
			 columns:[[
			       {align:'center',field:'dep_name',title:'收费项目',width:10},
		           {align:'center',field:"exam_doctor","title":"检查医生","width":10},
		           {align:'center',field:"exam_date","title":"检查时间","width":15},
		           {align:'center',field:'item_name',title:'检查项目',width:20,"styler":f_color_yx},
		           {align:'',field:'exam_result',title:'检查结果',width:30,"formatter":f_show_picture,"styler":f_color_yx}
			 	]],	 
			 	onLoadSuccess:function(value){
			 		MergeCells('yx_itemResultList', 'dep_name,exam_doctor,exam_date');
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


function gethyGrid(exam_num){
	var model={"exam_num":exam_num};
	$("#hy_itemResultList").datagrid({
			 url:'getHyDepResultList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:false,
			 columns:[[
				{align:'center',field:'dep_name',title:'收费项目',width:20},
				{align:'center',field:"exam_doctor","title":"检查医生","width":20},
				{align:'center',field:"exam_date","title":"检查时间","width":25},
			    {align:'center',field:'item_name',title:'检查项目',width:20,"styler":f_color_hy},
			 	{align:'center',field:'exam_result',title:'检查结果',width:20,"styler":f_color_hy},
			 	{align:'center',field:'bs',title:'标识',width:10,"formatter":f_bs,"styler":f_color_hy},
			 	{align:'center',field:'item_unit',title:'单位',width:10},
			 	{align:'center',field:'ref_value',title:'参考值',width:20},
			 	]],	  
			 	onLoadSuccess:function(value){
			 		MergeCells('hy_itemResultList', 'dep_name,exam_doctor,exam_date');
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
function f_bs(val,row){
	if(row.health_level == 1){
		return '↑';
	}else if(row.health_level == 2){
		return '↓';
	}else{
		return '';
	}
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
function MergeCells(tableID, fldList) {
    var Arr = fldList.split(",");
     var dg = $('#' + tableID);
    var fldName;
     var RowCount = dg.datagrid("getRows").length;
    var span;
    var PerValue = "";
    var CurValue = "";
     var length = Arr.length - 1;
     for (i = length; i >= 0; i--) {
       fldName = Arr[i];
        PerValue = "";
        span = 1;
        for (row = 0; row <= RowCount; row++) {
            if (row == RowCount) {
                CurValue = "";
            }
            else {
                CurValue = dg.datagrid("getRows")[row][fldName];
            }
             if (PerValue == CurValue) {
                span += 1;
            }
             else {
                var index = row - span;
                 dg.datagrid('mergeCells', {
                    index: index,
                     field: fldName,
                     rowspan: span,
                     colspan: null
                 });
                 span = 1;
                 PerValue = CurValue;
             }
         }
     }
 }


</script>
 <input type="hidden"   id='exam_nums' value="<s:property value='exam_num'/>"/>
  <input type="hidden"   id='arch_nums' value="<s:property value='arch_num'/>"/>
<div id="ts"></div>
			<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
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
					<div title="普通科" style="padding:5px;" >
						<table id="pt_itemResultList"></table>
					</div>
					<div title="影像科" style="padding:5px;" >
						<table id="yx_itemResultList"></table>
					</div>
					<div title="检验科" style="padding:5px;" >
						<table id="hy_itemResultList"></table>
					</div>
				</div>

