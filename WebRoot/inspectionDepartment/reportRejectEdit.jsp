<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	$.ajax({
        url:'getExamSuggestionList.action',  
        data:{
        	exam_num : $("#exam_num_r").val()
          },          
        type: "post",//数据发送方式   
        success: function(data){
        	if(data != null && data != 'null'){
        		var obj = eval('('+data+')');
        		var str = '';
        		for(var i = 0; i < obj.length; i++){
        			str += (i+1) + '.'+obj[i].notices+'\n';
        		}
        		$("#bohui_text").val(str);
        	}
        }
    });
});
function f_save_bohui(){
	if($("#bohui_text").val() == ''){
		$.messager.alert("警告信息","请输入驳回原因!","error");
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveExamSummaryRehectInfo.action',
		type:'post',
		data:{'exam_info_id':$("#examId").val(),'final_exam_result':$("#bohui_text").val(),'exam_num':$("#exam_num_r").val(),'operation_type':'1'},
		success:function(data){
			$(".loading_div").remove();	
			if(data.split("-")[0] == 'ok'){
				$.messager.alert("操作提示",data.split("-")[1], "info",close_page);
				$('#dlg-reject').dialog('close');
			}else{
				$.messager.alert("警告信息",data.split("-")[1],"error");
			}
		},
		error:function(){
			$(".loading_div").remove();
			$.messager.alert("警告信息","操作失败","error");
		}
	});
}


</script>
<input type="hidden" name="exam_num_r" id="exam_num_r" value="<s:property value="model.exam_num"/>"/>
<input type="hidden" name="examId" id="examId" value="<s:property value="model.exam_id"/>"/>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt><strong class="red">*</strong>驳回原因：</dt>
				<dd><textarea style="width:300px;resize:none;height:150px;" id="bohui_text"></textarea></dd>
			</dl>
	</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_save_bohui();">确定驳回</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-reject').dialog('close')">关闭</a>
	</div>
</div>
</form>