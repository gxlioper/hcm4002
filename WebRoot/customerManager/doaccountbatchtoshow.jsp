<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

/**
 * 保存修改
 */
function savedooverbatchto(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
   	 $("body").prepend(str);
	 $.ajax({
			url : 'doaccountbatchto.action',
			data : {
				company_id : $("#company_id").val(),
				batch_id : $("#overbatchids").val(),
				update_time:$("#update_time").datebox('getValue')
			},
			type : "post",// 数据发送方式
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok') {
					$.messager.alert("操作提示", text);
					$('#dlg-custedit-remarke').dialog('close');
					getbatchGrid();
				} else {
					$.messager.alert("操作提示", text,
							"error");
				}

			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误",
						"error");
			}
		});
	 
	}
</script>
<input type="hidden" id="overbatchids" value='<s:property value="model.batch_id"/>'>
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>维护备注信息</strong></legend>
	<div class="user-query">
		<dl>
			<dt>锁定日期</dt>
			<dd><input class="easyui-datebox" type=text id="update_time" value="<s:property value="model.update_time"/>" style="width:100px;height:26px;"></input></dd>
		</dl>
	</div>
 </fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:savedooverbatchto();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit-remarke').dialog('close')">关闭</a>
	</div>
</div>

