<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
$(function (){
	$('#divshou').html(addreload());
})

/**
 * 添加加载页面
 */
function addreload(){
	var str= $('#divshou').html();
		 str+='<dl>'
				+'<dd style="width:20%;text-align: center;">'
					+'<div id="shoufeixiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
					+' position:absolute;z-index:8;background:#ffffff;display:none;width:19%;marign 0px"  '
					+'  onmouseout="textblur(this);"  onmouseover="textfocus(this);"  ></div>'
					+'<input type="text" class="textinput" style="width:100%;height: 24px"  onblur="textclick(this);"   onclick="diesasecharging_item(this)"   '
					+'  onkeyup="diesasecharging_item(this);"   />'
				+'</dd>'
				+'<dd style="width:20%;text-align: center;">'
					+'<div id="jianchaxiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
						+' position:absolute;z-index:8;background:#ffffff;display:none;width:19%;marign 0px"'
						+' onmouseout="examintion_item_blur(this);"  onmouseover="examintion_item_focus(this);"   >'
					+'</div>'
					+'<input type="text" class="textinput" style="width:100%;height: 24px"   onblur="examintion_item_click(this);"    onclick="diesaseExamination_item(this)"  '
					+'	  onkeyup="diesaseExamination_item(this);"   />'
				+'</dd>'
				+'<dd style="width:10%;text-align: center;">'
					+'<select id="condition">'
						+'<option value=">">大于</option>'
						+'<option value=">=">大于等于</option>'
						+'<option value="<">小于</option>'
						+'<option value="<=">小于等于</option>'
						+'<option value="=">等于</option>'
						+'<option value="in">包含</option>'
						+'<option value="not in">不包含</option>'
					+'</select>'
				+'</dd>'
				+'<dd style="width:20%;text-align: center;"><input type="text" class="textinput" style="width:100%;height: 24px"   /></dd>'
				+'<dd style="width:10%;text-align: center;">'
					+'<select  id="andOrNo" >'
					+'	<option value="and">并且</option>'
					+'	<option value="or" >或者</option>'
					+'</select>'
				+'</dd>'
				+'<dd style="width:10%;text-align: left;">'
					+'<input type="button" value="+"  onclick="addrow()"  style="width:20px;text-align: center;background:#6fa8dc;float:left;color:#ffffff;cursor: pointer;"/>'
					/* +'<input type="button" value="-"  style="width:20px;text-align:center;background:#6fa8dc"/>' */
				+'</dd>'
			+'</dl>'
	return str;
}
//----------------------------------------------------------获取数据-------------------------------
/**
 * 获取所有收费项目
 */
function diesasecharging_item(obj){
	var ob = $(obj).parent().children().eq(0);
	$(ob).css('display','block')
	
	var weizhi = $(obj).offset().top;
		weizhi = weizhi-252;
	$(ob).css('top',weizhi)
	
	model = {c_item_name:$(obj).val()}
	$.ajax({
		url:'getDiseaseChargingItem.action',
		type:'post',
		data:model,
		success:function(data){
		   	var obj=eval('('+data+')');
		   	var str="<ul  style='text-align:left;' >";
		   	for(var i=0;i<obj.length;i++){
		   		str+="<li  style='padding-left:5px' "
		   			str+=" onclick='textclick2(this,"+obj[i].id+");'>"+obj[i].item_name+"</li>"
		   	}
		   	str+="</ul>";
		   	$(ob).html(str); 
		}
	})  
}
/**
 * 获取所有检查项目
 */
function diesaseExamination_item(obj){
	var ob = $(obj).parent().children().eq(0);
	$(ob).slideUp("fast");
	$(ob).css('display','block');
	
	var weizhi = $(obj).offset().top;
	weizhi = weizhi-252;
	$(ob).css('top',weizhi)
	
	model = {e_item_name:$(obj).val()}
	$.ajax({
		url:'getDiseaseExaminationItem.action',
		data:model,
		type:'post',
		success:function(data){
			var obj=eval('('+data+')');
		 	var str="<ul  style='text-align:left'>";
		   	for(var i=0;i<obj.length;i++){
		   		str+="<li   style='padding-left:5px' "
	   			str+="  onclick='examintion_item_click2(this,"+obj[i].id+");'  >"+obj[i].item_name+"</li>"
		   	}
		   	str+="</ul>";
		   	$(ob).html(str); 
		}
	}) 
}
//-------------------------------------------收费项目事件------------------------------------
var streak="2";
/**
 * 收费项目下拉框获取焦点
 */
function textfocus(obj){
	streak="1";
}
/**
 * 收费项目失去焦点
 */
function textblur(obj){
	streak="2";
}
/**
 * 收费项项输入框失去焦点关闭下拉框
 */
function textclick(obj){
	if(streak=="2"){
		
		var xlk = $(obj).parent().children().eq(0);
		$(xlk).css('display','none');
		
	}
}
/**
 * 下拉框选择后关闭
 */
function textclick2(obj,value){
	var zhi = $(obj).text();
	var srk=$(obj).parent().parent().parent().children().eq(1);
		$(srk).val(zhi);
		$(srk).attr('data',value);
	//下拉框节点
	var xlk = $(obj).parent().parent()
		$(xlk).css('display','none');
}
//--------------------------------------检查项目事件----------------------
/**
 * 检查项目下拉框失去焦点
 */
function examintion_item_blur(obj){
	streak="2";
}
/**
 * 检查项目下拉框获取焦点
 */
function examintion_item_focus(obj){
	streak="1";
}
/**
 * 检查项目输入框失去焦点关闭下拉框
 */
function examintion_item_click(obj){
	if(streak=="2"){
		
		var xlk = $(obj).parent().children().eq(0);
		$(xlk).css('display','none');
		
	}
}
/**
 * 检查项目选择后关闭下拉框
 */
function examintion_item_click2(obj,value){
	var zhi = $(obj).text();
	var srk=$(obj).parent().parent().parent().children().eq(1);
		$(srk).val(zhi);
		$(srk).attr('data',value);
		//下拉框关闭
		var xlk = $(obj).parent().parent()
		$(xlk).css('display','none');
}
//----------------------------------------------------------------+-操作-----------------------------------------------
/**
 * 添加行
 */
function addrow(){
	//var str= $('#divshou').html();
	var str="";
	//alert(str);
	 str+='<dl>'
			+'<dd style="width:20%;text-align: center;">'
				+'<div id="shoufeixiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
				+' position:absolute;z-index:8;background:#ffffff;display:none;width:19%;marign 0px"  '
				+'  onmouseout="textblur(this);"  onmouseover="textfocus(this);"  ></div>'
				+'<input type="text" class="textinput" style="width:100%;height: 24px"  onblur="textclick(this);" '
				+'   onkeyup="diesasecharging_item(this);"       onclick="diesasecharging_item(this)"   />'
			+'</dd>'
			+'<dd style="width:20%;text-align: center;">'
				+'<div id="jianchaxiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
					+' position:absolute;z-index:8;background:#ffffff;display:none;width:19%;marign 0px"'
					+' onmouseout="examintion_item_blur(this);"  onmouseover="examintion_item_focus(this);"   >'
				+'</div>'
				+'<input type="text" class="textinput" style="width:100%;height: 24px"   onblur="examintion_item_click(this);"    onclick="diesaseExamination_item(this)" '
				+'   onkeyup="diesaseExamination_item(this);"   />'
			+'</dd>'
			+'<dd style="width:10%;text-align: center;">'
				+'<select id="condition">'
					+'<option value=">">大于</option>'
					+'<option value=">=">大于等于</option>'
					+'<option value="<">小于</option>'
					+'<option value="<=">小于等于</option>'
					+'<option value="=">等于</option>'
					+'<option value="in">包含</option>'
					+'<option value="not in">不包含</option>'
				+'</select>'
			+'</dd>'
			+'<dd style="width:20%;text-align: center;"><input type="text" class="textinput" style="width:100%;height: 24px"   /></dd>'
			+'<dd style="width:10%;text-align: center;">'
				+'<select  id="andOrNo" >'
				+'	<option value="and">并且</option>'
				+'	<option value="or" >或者</option>'
				+'</select>'
			+'</dd>'
			+'<dd style="width:10%;text-align: left;">'
				+'<input type="button" value="+"  onclick="addrow();"  style="width:20px;text-align: center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>&nbsp;&nbsp;'
				+'<input type="button" value="-"  onclick="deletrow(this);"       style="width:20px;text-align:center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>'
			+'</dd>'
		+'</dl>'
		$("#divshou").append(str);
}
/**
 * 删除行
 */
 function deletrow(obj){
	 $(obj).parent().parent().remove();
}
//------------------------------------------------------保存疾病逻辑----------------------------------------------
function  adddisease_logic(){
	$("#dt dl").each(function(){
		$(this).ch
	})
}
</script>
 <fieldset style=" margin: 10px;padding-left: 0px">
	<legend><strong>疾病逻辑编辑</strong></legend> 
	<div class="formDiv" style="margin-top: 15px;margin-left: 5px;">
		<dl style="padding-left: 0px;">
			<dt style="width:8%">
				类型:
			</dt>
			<dd>
				<input type="radio"   name="disease_logic"  value="Y" checked="checked" />&nbsp;阳性指标&nbsp;&nbsp;&nbsp;
				<input type="radio" name="disease_logic"  value="N" />&nbsp;疾病&nbsp;&nbsp;&nbsp;
			</dd>
			<dt style="width:20%">
				适用性别:
			</dt>
			<dd>
				<input type="radio"  name="sex"   value="全部"  checked="checked" />&nbsp;全部&nbsp;&nbsp;&nbsp;
				<input type="radio"  name="sex"  value="男" />&nbsp;男&nbsp;&nbsp;&nbsp;
				<input type="radio"  name="sex"  value="女" />&nbsp;女&nbsp;&nbsp;&nbsp;
			</dd>
			<dt style="width: 20%">危机值提醒标示:</dt>
			<dd>
				<select id="critical_flag" class="easyui-combobox"  style="width:100px;"  data-options='panelHeight:60'>   
				    <option value='0'>否</option>   
				    <option value='1'>是</option>   
				</select>  
			</dd>	
		</dl>
		<dl style="padding-left: 0px;">
			<dt style="width:8%">
				生成疾病 :
			</dt>
			<dd style="width: 80%">
				<input   type="text" id="addname"  maxlength="45" 
					style="height: 26px; width:100%;"  class="textinput"/>
				
			</dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 10px;margin-bottom:60px;min-height:100px">
	<legend><strong>逻辑条件设置</strong></legend> 
		<div id="divshou" class="formDiv"  style="background: #d9d9d9;">
			<dl style="background: #cccccc;">
				<dt style="width:20%;text-align: center;">收费项目</dt>
				<dt style="width:20%;text-align: center;">检查项目</dt>
				<dt style="width:10%;text-align: center;">条件</dt>
				<dt style="width:20%;text-align: center;">条件值</dt>
				<dt style="width:10%;text-align: center;">关系</dt>
				<dt style="width:10%;text-align: left">新增/删除</dt>
			</dl>
		</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:adddisease_logic();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>