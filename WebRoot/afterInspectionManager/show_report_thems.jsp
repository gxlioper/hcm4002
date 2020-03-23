<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	$('#exam_num').css('ime-mode','disabled');
	$('#exam_num').focus();
});
function f_cancel_thems(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'cancelReportMail.action',
		data:{exam_num:"'"+$("#exam_num").val()+"'"},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', '取消自取成功!',function(){});
	    	$("#dlg-s").dialog("close");
	    	if($("#ser_exam_num").val() != ''){
	    		getGrid($("#ser_exam_num").val());
	    	}else{
	    		getGrid();
	    	}
		},
		error:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', '操作失败',function(){});
	    	$("#dlg-s").dialog("close");
		}
	});
}
function f_up_remarkez(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'updateReportRemarke.action',
		data:{'exam_num':$("#exam_num").val(),'receive_remark':$("#receive_remark").val()},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', '保存备注成功!','info');
		},
		error:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', '操作失败','info');
		}
	});
}
</script>
 <fieldset style="margin:5px;padding-right:0;">
	<legend><strong>报告领取明细</strong></legend>
		<div class="user-query">
			<dl>
				<dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>体检人姓名：</dt>
				<dd><input type="text" id="user_name" readonly="readonly" value="<s:property value="user_name"/>" class="textinput"/></dd>
				<dt><s:text name="tjhname"/>：</dt>
				<dd><input type="text" id="exam_num" readonly="readonly" value="<s:property value="exam_num"/>" class="textinput boder_ck"/></dd>
			</dl>
			<dl>
				<dt><input type="hidden" name="receive_type" id="receive_type" value="<s:property value="receive_type"/>"/>领取人：</dt>
				<dd><input type="text" id="receive_name" readonly="readonly" value="<s:property value="receive_name"/>" class="textinput boder_ck"/></dd>
				<dt>电话：</dt>
				<dd><input type="text" id="receive_phone" readonly="readonly" value="<s:property value="receive_phone"/>" class="textinput boder_ck"/></dd>
			</dl>
			<dl>
				<dt>领取日期：</dt>
				<dd><input type="text" id="receive_date" readonly="readonly" value="<s:property value="join_date"/>" class="textinput boder_ck" /></dd>
			</dl>
			<dl>
				<dt>备注：</dt>
				<dd><input type="text"  style="width:390px;" id="receive_remark" value="<s:property value="receive_remark"/>" class="textinput boder_ck"/></dd>
			</dl>
			<dl>
				<dt>操作人:</dt> 
				<dd><input type="text" id="creater" readonly="readonly" value="<s:property value="creater"/>" class="textinput boder_ck"/></dd>
				<dt>操作日期：</dt>
				<dd><input type="text" id="create_time" readonly="readonly" value="<s:property value="create_time"/>" class="textinput boder_ck" /></dd>
			</dl>
			<dl>
				<dt>修改人:</dt>
				<dd><input type="text" id="updater" readonly="readonly" value="<s:property value="updater"/>" class="textinput boder_ck"/></dd>
				<dt>修改日期：</dt>
				<dd><input type="text" id="update_time" readonly="readonly" value="<s:property value="update_time"/>" class="textinput boder_ck" /></dd>
			</dl>
		</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_up_remarkez()">修改备注</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:f_cancel_thems()">取消自取</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-s').dialog('close')">关闭</a>
	</div>
</div>
