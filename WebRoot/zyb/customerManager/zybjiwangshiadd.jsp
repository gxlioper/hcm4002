<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script>
$(document).ready(function() { 
		
	});

function savejiwangshi(){
	var model = {
			id_num:$('#addid_num').val(),
			exam_num:$('#addexam_num').val(),
			Arch_num:$('#addarch_num').val(),
			diseases:$('#diseases').val(),
			diagnosisdate:$('#diagnosisdate').val(),
			diagnosiscom:$('#diagnosiscom').val(),
			diagnosisnotice:$('#diagnosisnotice').val(),
			diseasereturn:$('#diseasereturn').val()
			
	} 
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	    
	$.ajax({
		url:'savezybDiseaseHistory.action',
		data:model,
		type:'post',
		success:function(data){
			$(".loading_div").remove();        
			alert_autoClose("操作提示",data,"info");
			$('#dlg-jiwangshi').dialog('close');
			getJWScusthisGrid();
		},
		error:function(){
			$(".loading_div").remove();
			$.messager.alert("提示信息","操作失败","error");
		}
	})
}


</script>
<input type="hidden" id="addexam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="addarch_num" value="<s:property value="model.arch_num"/>">
<input type="hidden" id="addid_num" value="<s:property value="model.id_num"/>">
<input type="hidden" id="addbatch_id" value="<s:property value="model.batch_id"/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>既往史维护</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dt>
				疾病名称</strong>
			</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="diseases"
					style="height: 26px; width: 280px;" value="/" />
			</dd>
		</dl>
		<dl>
			<dt>诊断日期</dt>
			<dd><input class="easyui-textbox" type=text id="diagnosisdate" style="width:100px;height:26px;"  value="/"></input></dd>
		</dl>
		<dl>
			<dt>诊断单位</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="diagnosiscom"
					style="height: 26px; width: 280px;"  value="/"/>
			</dd>
		</dl>
		<dl>
			
			<dt>治疗经过</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="diagnosisnotice" 
					style="height: 26px; width: 280px;"  value="/"/>
			</dd>
		
		</dl>
		<dl>
			<dt>转归</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="diseasereturn" 
					style="height: 26px; width: 280px;"  value="/"/>
			</dd>
			
		</dl>
	</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:savejiwangshi();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-jiwangshi').dialog('close')">关闭</a>
	</div>
</div>