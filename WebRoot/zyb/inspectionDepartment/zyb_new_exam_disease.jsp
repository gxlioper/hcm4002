<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	
});
$(function(){
	$("#dis_name,#dis_sug").validatebox({
		required: true
	});
});
function f_save_new_addsug(){
	if($("#dis_name").val() == ''){
		$("#dis_name").focus();
		return;
	}else if($("#dis_sug").val() == ''){
		$("#dis_sug").focus();
		return;
	}
	var row = new Object();
	row.id = 0;
	row.exam_info_id = 0;
	row.disease_id = 0;
	row.disease_name = $("#dis_name").val();
	row.disease_index = 0;
	row.disease_type = 'Y';
	row.disease_types = '阳性发现';
	row.suggest = $("#dis_sug").val();
	row.icd_10 = '';
	row.disease_class = '';
	row.disease_classs = '';
	row.exam_result = '';
	row.career_hazards = '';
	row.career_suggest = '';
	row.resultID = '';
	row.occudiseaseIDorcontraindicationID = '';
	row.data_source = 1;
	row.disease_num = '';
	appendDisease(row);
	$('#dlg-edit').dialog('close');
}

</script>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt>阳性发现名称：</dt>
				<dd><input type="text" style="width:350px;" id="dis_name" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl style="height:90px;">
				<dt>阳性发现建议：</dt>
				<dd><textarea style="width:350px;resize:none;height:80px;" id="dis_sug"></textarea><strong class="red">*</strong></dd>
			</dl>
	</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_save_new_addsug();">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
</form>