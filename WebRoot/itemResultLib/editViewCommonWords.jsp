<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	$('#sample_id').combobox({    
	    url:'getViewCommonSampleList.action',
	    valueField:'id',    
	    textField:'demo_name',
	    height:27,
	    onLoadSuccess:function(data){
	    	var sample_id = '<s:property value="sample_id"/>';
	    	if(sample_id != 0){
	    		$('#sample_id').combobox('setValue',sample_id);
	    	}
	    }
	});
	$('#is_default').combobox({
		data:[{
			id: '0',
			value: '否'
		},{
			id: '1',
			value: '是'
		}],
		valueField:'id',    
	    textField:'value',
		panelHeight:'auto',
		editable:false,
	    height:27,
	    onLoadSuccess:function(data){
	    	var is_default = '<s:property value="is_default"/>';
	    	$('#is_default').combobox('setValue',is_default);
	    }
	});
});

$(function(){
	$("#exam_result").validatebox({
		required: true
	});
	$("#sample_id").validatebox({
		required: true
	});
	$("#seq_code").validatebox({
		validType:'Number'
	});
	$("#is_default").validatebox({
		required: true
	});
	$.extend($.fn.validatebox.defaults.rules,{
			
			Number:{
				validator:function(value){
					 var reg =/^[0-9]*$/;          
					  return reg.test(value);
				},
				message:'只能输入数字'
			}
		});
});

function f_saveViewWords(){
	var reg =/^[0-9]*$/;
	if($('#sample_id').combobox('getValue') == undefined || $('#sample_id').combobox('getValue') == ''){
		$.messager.alert('提示信息', '请选择样本!',function(){});
		return;
	}else if($("#seq_code").val() != '' && !reg.test($("#seq_code").val())){
		$("#seq_code").focus();
		return;
	}else if($("#exam_result").val() == ''){
		$("#exam_result").focus();
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveViewCommonWords.action',
		data:{"id":$("#id").val(),
			  "sample_id":$('#sample_id').combobox('getValue'),
			  "seq_code":$("#seq_code").val(),
			  "exam_desc":$("#exam_desc").val(),
			  "exam_result":$("#exam_result").val(),
			  "is_default":$("#is_default").combobox('getValue')
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
</script>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt><input type="hidden" id="id" value="<s:property value="id"/>"/>样本名称：</dt>
				<dd><input type="text" id="sample_id"/><strong class="red">*</strong></dd>
				<dt>顺序码：</dt>
				<dd><input type="text" id="seq_code" value="<s:property value="seq_code"/>" class="textinput"/></dd>
			</dl>
			<dl>
				<dt>描述：</dt>
				<dd><textarea style="width:450px;height:80px;resize:none;" id="exam_desc" class="textinput"><s:property value="exam_desc"/></textarea></dd>
			</dl>
			<dl style="margin-top:60px;">
				<dt>结论：</dt>
				<dd><textarea style="width:450px;height:80px;resize:none;" id="exam_result" class="textinput"><s:property value="exam_result"/></textarea><strong class="red">*</strong></dd>
			</dl>
			<dl style="margin-top:50px;">
				<dt>默认值:</dt>
				<dd>
					<select id="is_default" style="height: 26px; width: 450px;"></select>
					<font color="#ff0000">*</font>
				</dd>
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
