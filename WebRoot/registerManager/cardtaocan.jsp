<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/wk_rederCard.js"></script>
<script type="text/javascript">
var old_amount;
var is_card_num;

$(document).ready(function () {
	getGridCard();
	$("#ser_card_num").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			getCardInfoBynum();
		} 
	}); 
});

function getGridCard(){
	var lastIndex;
	var model = {"card_num":$("#ser_card_num").val()};
	   $("#cardtaocanlist").datagrid({
		url: '<%=request.getContextPath()%>/getCardExamCharing.action',
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
  		  {align:'center',field:"set_name","title":"套餐","width":10},
  		  {align:"center",field:"item_name","title":"收费项目","width":10},
  		  {align:"center",field:"itemnum","title":"数量","width":15},
  		  {align:"center",field:"item_amount","title":"原金额","width":20},
  		  {align:"center",field:"discount","title":"折扣","width":10},
  		  {align:"center",field:"amount","title":"金额","width":15},
  		  {align:"center",field:"set_num","title":"套餐编码","width":15,"hidden":true},
  		  {align:"center",field:"item_code","title":"收费项目编码","width":15,"hidden":true},
  		  {align:"center",field:"id","title":"收费项目id","width":15,"hidden":true},
  		  {align:"center",field:"item_category","title":"收费项目类型","width":15,"hidden":true},
		  {align:"center",field:"sex","title":"适用性别","width":15,"hidden":true}
  		 ]
  		  ],
	    onLoadSuccess:function(value){
	    	is_card_num = $("#ser_card_num").val();
	    }, 
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
   		fitColumns:true,
   		checkOnSelect:true,
  		striped:true
	});
}


function getCardExamSet(card_num){
	var model = {"card_num":card_num};
	var setentities;

	$.ajax({
		url: 'getCardExamSet.action',
		data: model,
		type : "post",//数据发送方式   
		success : function(text) {
			$('#cardset').attr('data',text);
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	}); 
	return setentities;
}

function djtaddcarditem(){
	var itemrows = $('#cardtaocanlist').datagrid('getSelections');
	if(itemrows.length>0){
		var itementities = "";
		var discount = 0;
		var amount = 0;
		var item_amount = 0;
		for(i=0;i<itemrows.length;i++){
			var itemrow = itemrows[i];
			amount = amount+itemrow.amount;
			item_amount = item_amount+itemrow.item_amount;
			discount = discount+itemrow.discount;
			itementities = itementities + JSON.stringify(itemrow);
		}
		discount = discount/itemrows.length;
		var setentities = $('#cardset').attr('data');
		$.ajax({
			url : 'djtGcustSaveCardSet.action',
			data : {
						exam_id : $("#exam_id").val(),
						exam_num:$('#exam_num').val(),
						card_num : $("#ser_card_num").val(),
						itementities: itementities,
						setentities:setentities,
						discount : discount,
						amount : amount,
						item_amount:item_amount
					},
					type : "post",//数据发送方式   
					success : function(text) {
						if(text=="ok"){
							$.messager.alert("本操作员最大权限打"+$('#web_Resource').val()+"折,项目折扣超出权限！");
						}else{
							$(".loading_div").remove();		
							if (text.split("-")[0] == 'ok') {														
								$.messager.alert("操作提示", text.split("-")[1]);
								setselectListGrid();
								reapplydjtlispacs();
							} else {
								$.messager.alert("操作提示", text.split("-")[1], "error");
							}
						}
					},
					error : function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
	}else{
		$.messager.alert("操作提示", "无效收费项目", "error");
	}
	$('#dlg-card').dialog('close');
}

function getCardInfoBynum(type){
	if(type == 1){
		var card_num = "";
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
        url:'getCardExamCharing.action',  
        data:{
          card_num:$("#ser_card_num").val()
          },          
        type: "post",//数据发送方式   
          success: function(data){
        	  $(".loading_div").remove();
        	  	if(data == 'no1'){
        	  		$.messager.alert('提示信息', "该卡号不存在！",function(){});
        	  	}else if(data == 'no2'){
        	  		$.messager.alert('提示信息', "该卡没有绑定套餐！",function(){});
        	  	}else{
        	  		var rows = $('#cardtaocanlist').datagrid('getRows');
        	  		var count = 0;
        	  		var obj = eval("("+data+")");
        	  		/*
        	  		if(obj.status != '1'){
        	  			$.messager.alert('提示信息', "该卡已经"+obj.status_y,function(){});
        	  			return;
        	  		}else if(obj.limit_card_count != '' && obj.limit_card_count <= obj.card_count){
        	  			$.messager.alert('提示信息', "该卡使用次数已经用完！",function(){});
        	  			return;
        	  		}else if(obj.deadline != '' && obj.deadline < obj.get_date){
        	  			$.messager.alert('提示信息', "该卡已超出有效期，不能使用！",function(){});
        	  			return;
        	  		} */
        	  		$('#cardtaocanlist').datagrid('loadData', { total: 0, rows: [] });
        	  		for(var i=0;i<obj.length;i++){
        	  			$('#cardtaocanlist').datagrid("appendRow", obj[i]);
        	  			$('#cardtaocanlist').datagrid("checkRow", i);
        	  		}
        	  		getCardExamSet(is_card_num);
        	  	}
        		
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",function(){});
            }  
    });
}
</script>
<%-- <div style="display:none;">
	<OBJECT id=MWRFATL <s:property value="card_reader_ocx"/>></OBJECT>
	<input type="hidden" id="card_reader_code" value="<s:property value="card_reader_code"/>"/> 
</div> --%>
<div style="display:none;" id="cardset"></div>
<div class="easyui-layout" fit="true">
<div data-options="region:'north'" border="false" style="height:85px;">
<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd>卡号：<input type="text" maxlength="20" style="width:120px;height:30px" id="ser_card_num" name="ser_card_num" class="textinput"/></dd>
							<dd><a href="javascript:void(0)" onclick="getCardInfoBynum(1)" class="button btn-readcard"></a></dd>
						</dl>
					</div>
 			</fieldset>
</div>
<div data-options="region:'center'" border="false" style="padding-left:5px;">
<table id="cardtaocanlist"> 
</table>
<!-- </div>
<div data-options="region:'south'" border="false" style="height:100px;"> -->
<div class="dialog-button-box">
	<div class="inner-button-box">
		<a href="javascript:void(0)" class="easyui-linkbutton C6" style="width:80px;" onclick="djtaddcarditem()">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-card').dialog('close')">关闭</a>
	</div>
</div>
</div>
</div>