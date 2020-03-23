<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$("#guidance_content,#guidance_word,#guidance_pinyin").validatebox({
		required: true
	});
});

function f_saveViewWords(){
	if($("#guidance_word").val() == ''){
		$("#guidance_word").focus();
		return;
	}else if($("#guidance_pinyin").val() == ''){
		$("#guidance_pinyin").focus();
		return;
	}else if($("#guidance_content").val() == ''){
		$("#guidance_content").focus();
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveExamSummaryGuidanceLib.action',
		data:{"id":$("#id").val(),
			  "guidance_word":$("#guidance_word").val(),
			  "guidance_pinyin":$("#guidance_pinyin").val(),
			  "guidance_content":$("#guidance_content").val()
		},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				$.messager.alert('提示信息', obj[1],function(){});
				$('#dlg-edit').dialog('close');
				getGrid();
			}else{
				$.messager.alert('提示信息', obj[1],function(){});
			}
		},
		error:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', data,function(){});
		}
	});
}
function pingyingsc(){
	$.ajax({
		url:'pinying.action',
		data:{"pinying":$("#guidance_word").val()},
		type:'post',
		success:function(data){
			$("#guidance_pinyin").val(data);
		},
		error : function() {
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}
</script>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt><input type="hidden" id="id" value="<s:property value="id"/>"/>体检综述名称：</dt>
				<dd><input type="text" style="width:650px;" id="guidance_word" onkeyup="pingyingsc()" onblur="pingyingsc()" value="<s:property value="guidance_word"/>" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>助记码：</dt>
				<dd><input type="text" style="width:650px;" id="guidance_pinyin" value="<s:property value="guidance_pinyin"/>" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>体检综述内容：</dt>
				<dd><textarea style="width:650px;height:120px;resize:none;" id="guidance_content" class="textinput"><s:property value="guidance_content"/></textarea><strong class="red">*</strong></dd>
			</dl>
	</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_saveViewWords();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
</form>
