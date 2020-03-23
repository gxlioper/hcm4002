<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
getCharingType();
shofeiheji1();
$(document).ready(function () {

	 
});
$(function(){
	
	$("#invoice_num1,#invoice_name1,#invoice_type1").validatebox({
		required: true
	});
});
function getCharingType(){//获取收费方式
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	$.ajax({
		url:'getDatadis.action?com_Type=SFFSLX',
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			var str = '';
			var obj=eval(data);
			for(var i=0;i<6;i++){
				if(i< obj.length){
					if(obj[i].id == '122'){
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox1(2,this)" style="margin-top:6px;" type="checkbox" style="" childrencode="'+obj[i].data_code_children+'" value="'+obj[i].id+'"/></dd><dt style="width:60px;">'+obj[i].name+'</dt><dd><input readonly="readonly" onclick="showcardInfo()" id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}else{
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox1(1,this)" style="margin-top:6px;" type="checkbox" style="" childrencode="'+obj[i].data_code_children+'" value="'+obj[i].id+'"/></dd><dt style="width:60px;">'+obj[i].name+'</dt><dd><input id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" onblur="blur_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}
				}
			}
			$("#charingtype3").html(str);
			if(obj.length > 6){
				str = '';
				for(var i=6;i<obj.length;i++){
					if(obj[i].id == '122'){
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox1(2,this)" style="margin-top:6px;" type="checkbox" style="" childrencode="'+obj[i].data_code_children+'" value="'+obj[i].id+'"/></dd><dt style="width:60px;">'+obj[i].name+'</dt><dd><input readonly="readonly" onclick="showcardInfo()" id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}else{
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox1(1,this)" style="margin-top:6px;" type="checkbox" style="" childrencode="'+obj[i].data_code_children+'" value="'+obj[i].id+'"/></dd><dt style="width:60px;">'+obj[i].name+'</dt><dd><input id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" onblur="blur_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}
				}
				$("#charingtype4").show();
				$("#charingtype4").html(str);
			}
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}

//计算原价，应收金额
function shofeiheji1(){
	var obj = $("input[name=ck]");
	var yuanjia = 0.0,yingshou = 0.0;
	var data = $('#witemlist').datagrid('getData');
	var team_pay = 0.0;
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			yuanjia += Number(data.rows[i].item_amount)*Number(data.rows[i].itemnum);
			yingshou += Number(data.rows[i].personal_pay);
			team_pay += Number(data.rows[i].team_pay);
		}
	}
	
	$("#yuanjia1").html(decimal(yuanjia,2));
	$("#yingshou1").html(decimal(yingshou,2));
	$("#invoice_type").val(decimal(yingshou,2)+'元');
	if(yuanjia != 0){
		var discount = decimal((yingshou+team_pay)/yuanjia*10,2);
		$("#zongzhekou1").val(discount);
	}
	
	var obj1 = $("input[name=sffs_box]:checked");
	for(var i=0;i<obj1.length;i++){
		obj1[i].checked = false;
		$("#sffs_"+$(obj1[i]).val()).val(0);
	}
	$("#shishou1").html(0);
}
//选择收费方式，计算实收金额与找零
function click_sfbox1(type,ths){
	if(ths != undefined){
		if($(ths)[0].checked){
			var ys = Number($("#yingshou1").html());
			var ss = Number($("#shishou1").html());
			var sy = ys - ss;
			if(sy >= 0){
				$("#sffs_"+$(ths).val()).val(sy);
			}
		}else{
			$("#sffs_"+$(ths).val()).val(0);
			if($(ths).val() == '122'){
				card_xiaofei.length = 0;
			}
		}
	}
	
	var obj = $("input[name=sffs_box]:checked");
	var shishoujine = 0.0;
	if(obj.length > 0){
//		if(obj.length == 1 && type == 1){
//			$("#sffs_"+$(obj[0]).val()).val($("#yingshou").html());
//		}
		for(var i=0;i<obj.length;i++){
			shishoujine += Number($("#sffs_"+$(obj[i]).val()).val());
			if(type == 2 && $(obj[i]).val() == '122'){
				showcardInfo();
			}
		}
	}
	$("#shishou1").html(decimal(shishoujine,2));
	
	var yishou = Number($("#yingshou1").html());
	
	if(shishoujine > yishou){
		$("#zhaoling1").val(shishoujine - yishou);
	}else{
		$("#zhaoling1").val(0);
	}
}


//收费方式金额限制输入数字
function keyup_sf(dom){
	$(dom).val($(dom).val().replace(/[^\d.]/g, ''));
}
//收费方式金额失去焦点计算金额
function blur_sf(dom){
	if($(dom).val() == '' || $(dom).val() == 0){
		$(dom).val(0);
		$(dom).parent().parent().children('dd').eq(0).children('input')[0].checked = false;
	}else{
		$(dom).parent().parent().children('dd').eq(0).children('input')[0].checked = true;
	}
	click_sfbox1();
}
//修改总折扣
function updateAlldiscount(disvar){
	if (!isFloat(disvar)) {
		$.messager.alert('提示信息', '折扣率格式错误！',function(){});
		//alert('折扣率格式错误！');
		$("#zongzhekou1").focus();
	} else if (Number(disvar) > 10 || Number(disvar) < 0) {
		//alert('折后金额不能大于项目金额或小于0！');
		$.messager.alert('提示信息', '折后金额不能大于项目金额或小于0！',function(){});
		$("#zongzhekou1").focus();
	} else {
		var rows = $('#witemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var j = 0; j <= rows.length - 1; j++)//已选择
			{
				var row = rows[j];
					row.discount = disvar;
					row.amount = decimal(Number(row.item_amount)*Number(row.itemnum) * Number(disvar) / 10, 4);
					var personal_pay = decimal(Number(row.amount) - Number(row.team_pay),4);
					if(personal_pay < 0){
	    				//alert("项目"+row.item_name +"折后金额不能小于单位付费金额！");
	    				$.messager.alert('提示信息', "项目"+row.item_name +"折后金额不能小于单位付费金额！",function(){});
	    			}else{
		    			row.personal_pay = personal_pay;
		    			$('#witemlist').datagrid('refreshRow', j);
	    			}
					
			}//j End             
		}
		shofeiheji1();
	}
}
function zhegou() {
    if(Number($("#zongzhekou1").val())<Number($('#webResource').val())){
	    	$.messager.alert('提示信息',"本操作员最大权限可打"+$('#webResource').val()+"折！",'error');
	    }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
	    	$.messager.alert('提示信息',"没有打折权限",'error');
    }else {
    	updateAlldiscount($("#zongzhekou1").val());
    }
    
}
</script>
<form id="add1Form">
	<div class="formdiv" style="height:400px;">
<!-- 		<div id="singleInviockList"></div> -->
			<span style="float: left; font-size: 16px; margin-top: 8px;">收费方式：</span>
			<div  id="charingtype3" class="user-query" style="width: 200px; float: left;"></div>
			<div  id="charingtype4" class="user-query" style="width: 200px; float: left;display:none;"></div>
			<div class="user-query" style="font-size:16px;margin-top:3px;float: left;">
				<dl><dd style="width:150px;">原价：<font id="yuanjia1">0</font>元&nbsp;&nbsp;&nbsp;</dd>
					<dd style="font-weight:bold;width:180px;">应收金额：<font id="yingshou1">0</font>元&nbsp;&nbsp;&nbsp;</dd>
					<dd style="font-weight:bold;color:#f00;width:180px;">实收金额：<font id="shishou1">0</font>元&nbsp;&nbsp;&nbsp;</dd>
					<dd>折扣：</dd><dd><input style="width:40px;" type="text" id="zongzhekou1"  onkeyup="zhegou()" onchange="zhegou()" value="10"/>折</dd>
				</dl>
			</div>
	</div>
	
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_fapiao();">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-fapiao').dialog('close')">关闭</a>
	</div>
</div>
</form>
