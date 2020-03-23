<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

function saveproject(){
	if($('#project_type').val()=='2'){
		if($('#project_reson_input').val()!=''){
			yizhuangdan();
		}else{
			$.messager.alert("操作提示","请填写撞单原因");
		}
	}else if($('#project_type').val()=='5'){
			weizhuangdan();
	}
}

function yizhuangdan(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $.ajax({
		 url : 'updateSignBillPlanZhuangDan.action',
		data : {ids:$('#idss').val(),updatestatus:'2',project_reson:$('#project_reson_input').val()},
		type : "post",
		success : function(data) {
			$(".loading_div").remove();
			$('#dlg-edit').dialog('close');
			getCrmSignBillPlan();
			var _parentWin =  window.opener ; 
			_parentWin.getSignBillPlanList(); 
			setTimeout("window.close()", 10000);
			$.messager.confirm('提示信息',data+',10秒钟后该页面将自动关闭,是否立即关闭',function(r){
				if(r){
					window.close()
				}
			})
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
			$(".loading_div").remove();
		}
	 });
}

function weizhuangdan(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $.ajax({
		 url : 'updateSignBillPlanZhuangDan.action',
		data : {ids:$('#ids').val(),updatestatus:'3',project_reson:$('#project_reson_input').val()},
		type : "post",
		success : function(data) {
				$.messager.alert("操作提示",data);
				$(".loading_div").remove();
				$('#dlg-edit').dialog('close');
				getCrmSignBillPlan();
				var _parentWin =  window.opener ; 
				_parentWin.getSignBillPlanList(); // 主窗口getgroupGrid();刷新
				window.close();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
			$(".loading_div").remove();
		}
	 });
}
</script>
<input id="flag" value='<s:property value="flag"/>' hidden="true"/>
<input id="idss" value='<s:property value="id"/>' hidden="true"/>
<input id="project_type" value='<s:property value="project_type"/>' hidden="true"/>
<fieldset style="margin:5px;padding-right:0;width:500">
    <legend><strong>请填写原因</strong></legend>
 
	<dl>
		<dt style="width:75px">名称：</dt>
		<dt style="width:400px">
			<s:property value="project_name"/>
		</dt>
		</dl><dl>
		<dt>请填写原因：</dt>
		<dt>
			<textarea   id="project_reson_input" class="textinput"	style="height: 120px; width: 350px;"><s:property value="project_reson"/></textarea>
		</dt>
	</dl>

</fieldset>
<div class="dialog-button-box">
	<div class="inner-button-box">
	<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:saveproject()">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>