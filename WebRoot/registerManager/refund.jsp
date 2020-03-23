<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	gettuifei_fees();
	tuifeiheji();
});
var oldamount_t;
function gettuifei_fees(){
	var req_nums = "<s:property value='req_nums'/>";
	var model = {"exam_num":$("#ser_num").val(),"req_nums":req_nums};
	$("#tuifei_fees").datagrid({
		url: 'getChargingWayByExamNum.action',
		queryParams: model,
		rownumbers:true,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		sortName: 'cdate',
		sortOrder: 'desc',
		columns:[[{align:'center',field:'sf_ck_t',checkbox:true},
		          {align:"center",field:"charging_way","title":"收费方式","width":10},
		          {align:'center',field:"amount","title":"收费金额(元)","width":15},
		          {align:"center",field:"amount_t","title":"应退金额(元)","width":15,editor:{type:'numberbox',options:{precision:2}}}
		     	]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	if('<s:property value="isPrintRecepit"/>' == 'Y'){
	    		$("#yishou_t").html($("#yintui").html());
	    	}else{
	    		$("#yishou_t").html($("#yintui_sj").html());	
	    	}
	    	$.each(value.rows, function(index, item){
	    		item.amount_t = 0.0;
	    		$('#tuifei_fees').datagrid('refreshRow', index);
	    	});
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		toolbar:"#toolbar_t",
		checkOnSelect:false,
		onClickCell:function(index, field, row){
	    	//$('#tuifei_fees').datagrid('clearSelections', index);
			$('#tuifei_fees').datagrid('beginEdit', index);
			var editors = $('#tuifei_fees').datagrid('getEditor',{index:index,field:'amount_t'});
			$(editors.target).numberbox({onChange:function(){
					$('#tuifei_fees').datagrid('endEdit', index);
					tuifeiheji();
				}
			});
	    },
	    onBeforeEdit:function(rowIndex, rowData){
	    	oldamount_t = rowData.amount_t;
	    },
	    onAfterEdit:function(rowIndex, rowData, changes){
	    	if(changes.amount_t != undefined){ //输入应退金额
	    		if(Number(changes.amount_t) > rowData.amount || Number(changes.amount_t) < 0){
	    			$.messager.alert('提示信息', '应退金额不能大于收费金额或小于0',function(){});
	    			rowData.amount_t = oldamount_t;
	        		$('#tuifei_fees').datagrid('refreshRow', rowIndex);
	    		}
	    	}
	    },
	    onSelect:function(index,row){
	    	if(row.amount == 0){
	    		$('#tuifei_fees').datagrid('unselectRow', index);
	    	}else{
	    		$("input[name=sf_ck_t]").eq(index).attr("checked",true);
		    	tuifeiheji();
	    	}
	    },
	    onUnselect:function(index,row){
	    	$("input[name=sf_ck_t]").eq(index).attr("checked",false);
	    	tuifeiheji();
	    },
	    onCheckAll:function(rows){
	    	tuifeiheji();
	    },
	    onUncheckAll:function(rows){
	    	tuifeiheji();
	    }
	});
}

function tuifeiheji(){
	var obj = $("input[name=sf_ck_t]");
	var shitui = 0.0;
	var data = $('#tuifei_fees').datagrid('getData');
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			shitui += Number(data.rows[i].amount_t);
		}
	}
	$("#shitui_t").html(decimal(shitui,2));
}

function tuifeiClick(){
	var isPrintRecepit = $("#isPrintRecepit").val();
	if(isPrintRecepit == 'Y'){
		$.messager.confirm('提示信息','确定同时作废所开发票吗？',function(r){
			if(r){
				save_tuifei('ykfpList');
			}
		});
	}else{
		save_tuifei('yksjList');
	}
}

//保存退费信息
function save_tuifei(id){
	var yintui = Number($("#yishou_t").html());
	var shitui_t = Number($("#shitui_t").html());
	
	if(yintui != shitui_t){
		$.messager.alert('提示信息', "应退金额不等于实退金额，不能退费！",function(){});
		return;
	}
	var examInfoCharingItem = new Array();
	var data = $('#'+id).datagrid('getSelections');
	examInfoCharingItem.length = 0;
	
	var account_num = data[0].account_num;
	var req_nums = data[0].req_num;
	for(var i=0;i<data.length;i++){
		examInfoCharingItem.push({"id":data[i].id,
			  "item_id":data[i].item_id,
			  "item_amount":data[i].item_amount,
			  "amount":data[i].amount,
			  "personal_pay":data[i].personal_pay,
			  "discount":data[i].discount,
			  "item_code":data[i].item_code
			  });
	}
	
	
	var charingWay = new Array();
	var obj1 = $('#tuifei_fees').datagrid('getData');
	var input = $("input[name=sf_ck_t]");
	charingWay.length = 0;
	if(input.length > 0){
		for(var i=0;i<input.length;i++){
			if(input[i].checked == true){
				charingWay.push({"charging_way":obj1.rows[i].id,"amount":obj1.rows[i].amount_t});
			}
		}
	}else{
		$.messager.alert('提示信息', "请选择退费方式!",function(){});
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'save_tuifei.action?language='+$("#language").val(),  
        data:{
          exam_id:$("#exam_id").val(),
          exam_num:$("#ser_num").val(),
          amount1:yintui,
          amount2:shitui_t,
          discount: 10,
          charingWays:JSON.stringify(charingWay),
          examInfoCharingItems:JSON.stringify(examInfoCharingItem),
          isPrintRecepit:$("#isPrintRecepit").val(),
          account_num:account_num,
          req_nums:req_nums
          //cardInfos:JSON.stringify(card_xiaofei)
          },          
        type: "post",//数据发送方式   
          success: function(data){
        	  $(".loading_div").remove();
        		$.messager.alert('提示信息', data,'info');
        		$('#dlg-tuifei').dialog('close');
        		chaxun();
        		if(id == 'ykfpList'){
        			$.messager.confirm('提示信息','是否打印发票？',function(r){
        				if(r){
        					fapiao_show();
        				}
        			});
        		}
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",'error');
            }  
    });
}

//回车事件
document.onkeydown=function(event){
	    var e = event || window.event || arguments.callee.caller.arguments[0]; 
			if(e && e.keyCode==27){ // 按 Esc                 
			//要做的事情           
			}           
			if(e && e.keyCode==113){ // 按 F2                 
			  //要做的事情               
			}                         
			if(e && e.keyCode==13){ // enter 键                
			  //要做的事情
				tuifeiClick();
			}        
	};
</script>
<div class="easyui-layout" fit="true">
<div data-options="region:'north'" style="height:328px;">
<table id="tuifei_fees"></table>
</div>
</div>
<div id = "toolbar_t">
	<div style="width: 180px; float: left; font-size: 18px;font-weight:bold; margin: 5px;">应退金额：<font id="yishou_t">0</font>元&nbsp;&nbsp;&nbsp;</div>
	<div style="width: 180px; float: left; font-size: 18px;font-weight:bold; color: #f00; margin-top: 5px;">实退金额：<font id="shitui_t">0</font>元&nbsp;&nbsp;&nbsp;</div>
	<div><input type="hidden" id="isPrintRecepit" value="<s:property value="isPrintRecepit"/>"/>
	<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:100px;" onclick="javascript:tuifeiClick();">确认退费</a></div>
</div>