<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/wk_rederCard.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	getGridCard();
	$("#c_yingshou").html($("#yingshou").html());
	$("#ser_card_num").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			getCardInfoBynum();
		} 
	}); 
});
var old_amount;
function getGridCard(){
	var lastIndex;
	var model = {"exam_id":$("#exam_id").val()};
	   $("#cardinfolist").datagrid({
		url: '<%=request.getContextPath()%>/getCardListByexamId.action',
		queryParams: model,
		rownumbers:true,
        pageSize: 15,//每页显示的记录条数，默认为10 
        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
        //toolbar: '#toolbar',
        sortName: 'cdate',
        height:280,
		sortOrder: 'desc',
        columns:[[{align:'center',field:'card_ck',checkbox:true},
           {align:"center",field:"card_num","title":"卡号","width":20},
  		  {align:'center',field:"status_y","title":"卡状态","width":10},
  		  {align:"center",field:"card_type_y","title":"卡类型","width":10},
  		  {align:"center",field:"amount","title":"卡余额(元)","width":15},
  		  {align:"center",field:"xf_amount","title":"消费金额(元)","width":20,editor:{type:'numberbox',options:{'precision':2}}},
  		  {align:"center",field:"card_level_y","title":"卡等级","width":10},
  		  {align:"center",field:"limit_card_count","title":"限制次数","width":15},
  		  {align:"center",field:"card_count","title":"消费次数","width":15},
  		  {align:"center",field:"deadline","title":"有效日期","width":15},
  		  {align:"center",field:"remark","title":"备注","width":15}
  		 ]
  		  ],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	if(value != undefined){
	    		var amounts = 0.0;
	    		var yinshou = Number($("#yingshou").html());
	    		$.each(value.rows, function(index, item){
	    			if(yinshou == 0){
	    				item.xf_amount = yinshou;
		    			$('#cardinfolist').datagrid('refreshRow', index);
	    			}else{
			    		if(item.amount >= yinshou){
			    			item.xf_amount = yinshou;
			    			yinshou = 0;
			    			$('#cardinfolist').datagrid('refreshRow', index);
			    			$('#cardinfolist').datagrid('checkRow', index);
			    		}else{
			    			item.xf_amount = item.amount;
			    			yinshou = yinshou - item.amount;
			    			$('#cardinfolist').datagrid('refreshRow', index);
			    			$('#cardinfolist').datagrid('checkRow', index);
			    		}
	    			}
	    		});
	    	}
	    	for(i=0;i<card_xiaofei.length;i++){
	    		$("#ser_card_num").val(card_xiaofei[i].card_num);
	    		getCardInfoBynum();
	    	}
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
   		fitColumns:true,
   		checkOnSelect:false,
  		striped:true,
  		onClickCell:function(index, field, row){
  	    	$('#cardinfolist').datagrid('clearSelections', index);
  	    	//if (lastIndex != index){
  	    		$('#cardinfolist').datagrid('endEdit', lastIndex);
  				$('#cardinfolist').datagrid('beginEdit', index);
  			//}
  			lastIndex = index;
  			var editors = $('#cardinfolist').datagrid('getEditor',{index:index,field:'xf_amount'});
  			$(editors.target).numberbox({onChange:function(){
  					$('#cardinfolist').datagrid('endEdit', index);
  					card_shofeiheji();
  				}
  			});
  	    },
  	    onBeforeEdit:function(rowIndex, rowData){
  	    	old_amount = rowData.xf_amount;
  	    },
  	    onAfterEdit:function(rowIndex, rowData, changes){
  	    	if(changes.xf_amount != undefined){ //修改卡消费金额
  	    		if(Number(changes.xf_amount) < 0 || Number(changes.xf_amount) > rowData.amount){
  	    			$.messager.alert('提示信息', '消费金额不能大于卡余额或小于0',function(){});
  	    			rowData.xf_amount = old_amount;
  	        		$('#cardinfolist').datagrid('refreshRow', rowIndex);
  	    		}
  	    	}
  	    },
  	    onSelect:function(index,row){
  	    	$("input[name=card_ck]").eq(index).attr("checked",true);
  	    	$('#cardinfolist').datagrid('clearSelections');
  	    	card_shofeiheji();
  	    },
  	    onUnselect:function(index,row){
  	    	$("input[name=card_ck]").eq(index).attr("checked",false);
  	    	card_shofeiheji();
  	    },
  	    onCheckAll:function(rows){
  	    	$('#cardinfolist').datagrid('clearSelections');
  	    	card_shofeiheji();
  	    },
  	    onUncheckAll:function(rows){
  	    	card_shofeiheji();
  	    }
	});
}

function card_shofeiheji(){
	var obj = $("input[name=card_ck]");
	var card_shihsou = 0.0;
	var data = $('#cardinfolist').datagrid('getData');
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			card_shihsou += Number(data.rows[i].xf_amount);
		}
	}
	$("#c_shishou").html(decimal(card_shihsou,2));
}

function getCardInfoBynum(type){
	if(type == 1){
		var card_num = read_card_vip();
		if(card_num.split("-")[0] == 'error'){
			$.messager.alert('提示信息', card_num.split("-")[1],function(){});
			return;
		}else{
			$("#ser_card_num").val(card_num.split("-")[1]);
		}
	}
	if($("#ser_card_num").val() == ''){
		$("#ser_card_num").focus();
		return;
	}else if(/[\u0391-\uFFE5]/g.test($("#ser_card_num").val())){
		$("#ser_card_num").focus();
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'getCardInfoByCardNum.action?language='+$("#language").val(),  
        data:{
          card_num:$("#ser_card_num").val()
          },          
        type: "post",//数据发送方式   
          success: function(data){
        	  $(".loading_div").remove();
        	  	if(data == 'no'){
        	  		$.messager.alert('提示信息', "该卡号不存在！",function(){});
        	  	}else{
        	  		var rows = $('#cardinfolist').datagrid('getRows');
        	  		var count = 0;
        	  		var obj = eval("("+data+")");
        	  		if(obj.status != '1'){
        	  			$.messager.alert('提示信息', "该卡已经"+obj.status_y,function(){});
        	  			return;
        	  		}else if(obj.limit_card_count != '' && obj.limit_card_count <= obj.card_count){
        	  			$.messager.alert('提示信息', "该卡使用次数已经用完！",function(){});
        	  			return;
        	  		}else if(obj.deadline != '' && obj.deadline < obj.get_date){
        	  			$.messager.alert('提示信息', "该卡已超出有效期，不能使用！",function(){});
        	  			return;
        	  		}
        	  		for(var i=0;i<rows.length;i++){
        	  			if(rows[i].id != obj.id){
        	  				count ++;
        	  			}
        	  		}
        	  		if(count == rows.length){
        	  			$('#cardinfolist').datagrid("appendRow", obj);
        	  			var card_shishou = Number($("#c_shishou").html());
        	  			var card_yinshou = Number($("#c_yingshou").html());
        	  			var data = $('#cardinfolist').datagrid('getData');
        	  			var row = data.rows[data.rows.length-1];
        	  			var index = data.rows.length-1;
        	  			if(card_yinshou <= card_shishou){
        	  				row.xf_amount = 0;
        	  				$('#cardinfolist').datagrid('refreshRow', index);
        	  			}else{
        	  				var amount = decimal((card_yinshou - card_shishou),2);
        	  				if(amount >= row.amount){
        	  					row.xf_amount = row.amount;
        	  					$('#cardinfolist').datagrid('refreshRow', index);
        	  					$('#cardinfolist').datagrid('checkRow', index);
        	  				}else{
        	  					row.xf_amount = amount;
        	  					$('#cardinfolist').datagrid('refreshRow', index);
        	  					$('#cardinfolist').datagrid('checkRow', index);
        	  				}
        	  			}
        	  		}
        	  	}
        		
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",function(){});
            }  
    });
}
</script>
<div style="display:none;">
	<OBJECT id=MWRFATL <s:property value="card_reader_ocx"/>></OBJECT>
	<input type="hidden" id="card_reader_code" value="<s:property value="card_reader_code"/>"/> 
</div>
<div class="easyui-layout" fit="true">
<div data-options="region:'north'" border="false" style="height:85px;">
<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd>卡号：<input type="text" maxlength="20" style="width:85px" id="ser_card_num" name="ser_card_num" onfocus="serch_cls_card();"  class="textinput"/></dd>
							<dd><a href="javascript:void(0)" onclick="getCardInfoBynum(1)" class="button btn-readcard"></a></dd>
							<dd style="font-weight:bold;width:180px;font-size:16px;margin-left:100px;margin-top:5px;">应收金额：<font id="c_yingshou">0</font>元&nbsp;&nbsp;&nbsp;</dd>
							<dd style="font-weight:bold;width:180px;font-size:16px;color:#F00;margin-top:5px;">卡实收金额：<font id="c_shishou">0</font>元&nbsp;&nbsp;&nbsp;</dd>
						</dl>
					</div>
 			</fieldset>
</div>
<div data-options="region:'center'" border="false" style="padding-left:5px;">
<table id="cardinfolist"> 
</table>
<!-- </div>
<div data-options="region:'south'" border="false" style="height:100px;"> -->
<!-- <div class="dialog-button-box">
	<div class="inner-button-box">
		<a href="javascript:void(0)" class="easyui-linkbutton C6" style="width:80px;" onclick="javascript:void(0)">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div> -->
</div>
</div>