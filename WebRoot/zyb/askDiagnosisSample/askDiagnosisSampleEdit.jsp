<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$("#name").validatebox({
		required: true
	});
	var sexx = '<s:property value="sex"/>';
	if(sexx=='1'){
		$('#nan').attr('checked',true);
		$('#nv').attr('checked',false);
		$('#quanbu').attr('checked',false);
	} else if(sexx=='2'){
		$('#nan').attr('checked',false);
		$('#nv').attr('checked',true);
		$('#quanbu').attr('checked',false);
	} else {
		$('#nan').attr('checked',false);
		$('#nv').attr('checked',false);
		$('#quanbu').attr('checked',true);
	}
});

function f_saveaskDia(){
	if($("#name").val() == ''){
		$("#name").focus();
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveZybAskDiagnosisSample.action',
		data:{"id":$("#id").val(),
			  "name":$("#name").val(),
			  "type":$("#type").combobox('getValue'),
			  "seq_code":$("#seq_code").val(),
			  "sub_name":$("#sub_name").val(),
			  "temp_content":$("#temp_content").val(),
			  "sex":$('input:radio[name="sexx"]:checked').val()
		
		},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', data,'info');
			$('#dlg-edit').dialog('close');
			getGrid();
		},
		error:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', data,'error');
		}
	});
}
</script>
<!-- <form id="add1Form"> -->
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt style="width:100px;">名称</dt><dd><input type="hidden" id="id" value="<s:property value="id"/>"/><input id="name" type="text" value="<s:property value="name"/>" style="width:230px;" class="textinput" /><strong class="red">*</strong></dd>
				<dt style="width:100px;">模板类型</dt><dd><select id="type" class="easyui-combobox" data-options="panelHeight:'auto',height:27,onLoadSuccess:function(){$('#type').combobox('setValue','<s:property value="type"/>');}">
					<option value="1" selected="selected">职业健康检查</option>
					<option value="2">放射健康检查</option>
					</select><strong class="red">*</strong></dd>
				<dt style="width:100px;">顺序码</dt><dd><input id="seq_code" type="text" style="width:60px;" value="<s:property value="seq_code"/>" class="textinput" /></dd>
			</dl>
			<dl>
				<dt style="width:100px;">适用性别</dt>
				<dd>
					<input type="radio" value='0'  name="sexx"  id="quanbu" checked="checked"  />全部&nbsp;&nbsp;&nbsp;
					<input type="radio" value='1'  name="sexx"  id="nan" />男&nbsp;&nbsp;&nbsp;
					<input type="radio" value='2'  name="sexx"  id="nv" />女
				</dd>
			</dl>
			<dl>
				<dt style="width:100px;">问题描述</dt>
				<dd><input id="sub_name" type="text" style="width:660px;" value="<s:property value="sub_name"/>" class="textinput" /></dd>
			</dl>
			<dl>
				<dt style="width:100px;">模板内容</dt>
				<dd><textarea type="text" style="width:660px;height:170px;resize:none;" id="temp_content"><s:property value="temp_content"/></textarea></dd>
			</dl>
	</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_saveaskDia();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
<!-- </form> -->
