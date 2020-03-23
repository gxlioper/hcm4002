$(document).ready(function () {
	getwitemGrid();
	getGridCard();
	$("#ser_card_num").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			getCardInfoBynum();
		} 
	});
});


/*************************************************************收费功能**********************************************************************/

var chargtype_id = 122;
var exam_num,exam_id;
function getwitemGrid(){//查询未收费项目列表
	 var lastIndex;
	 var model={"exam_num":$("#exam_num").val()};
     $("#witemlist").datagrid({
	 url:'getwitemList.action',
	 dataType: 'json',
	 queryParams:model,
	 rownumbers:true,
     pageSize: 15,//每页显示的记录条数，默认为10 
     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	 columns:[[
	    {align:'center',field:'ck',checkbox:true},
	    {align:'center',field:'item_code',title:'项目编码',width:12},
	 	{align:'center',field:'item_name',title:'项目名称',width:15},
	 	{align:'center',field:'dep_name',title:'科室名称',width:12},
	 	{align:'center',field:'exam_status_y',title:'检查状态',width:12},
	 	{align:'center',field:'item_amount',title:'金额(元)',width:10},
	 	{align:'center',field:'discount',title:'折扣',width:10},
	 	{align:'center',field:'amount',title:'折后金额(元)',width:15},
	 	{align:'center',field:'creater',title:'登记人',width:10},
	 	{align:'center',field:'create_time',title:'登记时间',width:15},
	 	{align:'center',field:'his_req_status_y',title:'HIS申请',width:15}
	 	]],	    	
    	onLoadSuccess:function(value){
    		$("#datatotal").val(value.total);
    		$('#witemlist').datagrid('checkAll');
    	},
    collapsible:true,
	pagination: false,
    fitColumns:true,
    striped:true,
    fit:true,
    onSelect:function(index,row){
    	shofeiheji();
    },
    onUnselect:function(index,row){
    	shofeiheji();
    },
    onCheckAll:function(rows){
    	shofeiheji();
    },
    onUncheckAll:function(rows){
    	shofeiheji();
    }
});
}
//计算原价，应收金额
function shofeiheji(){
	var obj = $("input[name=ck]");
	var yuanjia = 0.0,yingshou = 0.0;
	var data = $('#witemlist').datagrid('getData');
	var team_pay = 0.0;
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			yuanjia += Number(data.rows[i].item_amount);
			yingshou += Number(data.rows[i].personal_pay);
			team_pay += Number(data.rows[i].team_pay);
		}
	}
	
	$("#yuanjia").html(decimal(yuanjia,2));
	$("#yingshou").html(decimal(yingshou,2));
	if(yuanjia != 0){
		var discount = decimal((yingshou+team_pay)/yuanjia*10,2);
		$("#zongzhekou").val(discount);
	}
}

//收费保存收费信息
function savecollectFees(){
	var yingshou = Number($("#yingshou").html());
	var shishou = Number($("#shishou").html());
	var yuanjia = Number($("#yuanjia").html());
	
	if(shishou < yingshou){
		$.messager.alert('提示信息', "实收金额不能小于应收金额!",function(){});
		return;
	}
	var examInfoCharingItem = new Array();
	var chargingids = new Array();
	var obj = $("input[name=ck]");
	var data = $('#witemlist').datagrid('getData');
	
	var his_flag = false;
	examInfoCharingItem.length = 0;
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			examInfoCharingItem.push({"id":data.rows[i].id,
									  "item_id":data.rows[i].item_id,
									  "item_amount":data.rows[i].item_amount,
									  "amount":data.rows[i].amount,
									  "personal_pay":data.rows[i].personal_pay,
									  "discount":data.rows[i].discount});
			chargingids.push(data.rows[i].item_id);
			
			if(his_flag == false && data.rows[i].his_req_status == 'Y'){
				his_flag = true;
			}
		}
	}
	if(examInfoCharingItem.length <= 0){
		$.messager.alert('提示信息', "请选择需要收费的项目!",function(){});
		return;
	}
	if(his_flag){
		$.messager.alert('提示信息', "存在已发HIS申请项目,请先撤销HIS申请!",function(){});
		return;
	}
	var charingWay = new Array();
	charingWay.length = 0;
	charingWay.push({"charging_way":chargtype_id,"amount":shishou});
	
	var card_xiaofei = new Array();
	
	var obj1 = $("input[name=card_ck]");
	var data = $('#cardinfolist').datagrid('getData');
	for(var j=0;j<obj1.length;j++){
		if(obj1[j].checked == true){
			card_xiaofei.push({
				"card_num":data.rows[j].card_num,
				"amount":data.rows[j].xf_amount,
				"face_amount":data.rows[j].amount,
				"card_count":data.rows[j].card_count
			});
		}
	}
	
	var isPrintRecepit = "N";// 是否打印发票
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'saveCollectFees.action?language='+$("#language").val(),  
        data:{
          exam_num:$("#exam_num").val(),
          exam_id:$("#exam_id").val(),
          amount1:yuanjia,
          amount2:yingshou,
          discount: $("#zongzhekou").val(),
          charingWays:JSON.stringify(charingWay),
          examInfoCharingItems:JSON.stringify(examInfoCharingItem),
          cardInfos:JSON.stringify(card_xiaofei),
          isPrintRecepit:isPrintRecepit,
          chargingIds:chargingids.toString()
          },          
        type: "post",//数据发送方式   
        success: function(data){
        		$(".loading_div").remove();
        		var state = data.split("-");
        		if(state[0] == 'error'){
        			$.messager.alert('提示信息', state[1],'error');
        			return;
        		}else{
        			$.messager.alert("操作提示",state[1], "info",close_page);
        		}
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",function(){});
            }  
    });
}
var old_amount;
function getGridCard(){
	var lastIndex;
	var model = {"exam_id":$("#exam_id").val()};
	   $("#cardinfolist").datagrid({
		url: 'getCardListByexamId.action',
		queryParams: model,
		rownumbers:true,
        pageSize: 15,//每页显示的记录条数，默认为10 
        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
        toolbar: '#toolbar',
        sortName: 'cdate',
		sortOrder: 'desc',
        columns:[[{align:'center',field:'card_ck',checkbox:true},
           {align:"center",field:"card_num","title":"卡号","width":20},
  		  {align:"center",field:"amount","title":"卡余额(元)","width":15},
  		  {align:"center",field:"xf_amount","title":"消费金额(元)","width":20,editor:{type:'numberbox',options:{}}},
  		  {align:"center",field:"card_level_y","title":"卡等级","width":10},
  		  {align:"center",field:"limit_card_count","title":"限制次数","width":15},
  		  {align:"center",field:"card_count","title":"消费次数","width":15},
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
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fit:true,
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
	$("#shishou").html(decimal(card_shihsou,2));
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
        	  			var card_shishou = Number($("#shishou").html());
        	  			var card_yinshou = Number($("#yingshou").html());
        	  			var data = $('#cardinfolist').datagrid('getData');
        	  			var row = data.rows[data.rows.length-1];
        	  			var index = data.rows.length-1;
        	  			if(card_yinshou <= card_shishou){
        	  				row.xf_amount = 0;
        	  				$('#cardinfolist').datagrid('refreshRow', index);
        	  			}else{
        	  				var amount = card_yinshou - card_shishou;
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

function close_page(){
	var _parentWin =  window.opener ;
	var userAgent = navigator.userAgent;
	  window.opener = null;
	  window.open('', '_self');
	  window.close();
	  _parentWin.djtcustChangItemListGrid();
}
