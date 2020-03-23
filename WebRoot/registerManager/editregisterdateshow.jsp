<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<script>

function checkinput() {
	var startdate=$("#datetime").datebox('getValue');
	var time1=document.getElementById("time1").value;
	var time2=document.getElementById("time2").value;
	if(startdate==''){
		alert('体检日期不能为空！');
		return false;
	}else if(!isDate(startdate)){
		alert('体检日期格式错误！');
		return false;
	}else if((time1=='')||(time1.length!=5)){
		alert('体检开始时间不能为空或格式错误！');
	    return false;
    }else if(!isTime(time1)){
		alert('体检开始时间格式错误！');
        return false;
    }else if((time2=='')||(time2.length!=5)){
		alert('体检结束时间不能为空或格式错误！');
        return false;
    }else if(!isTime(time2)){
		alert('体检结束时间格式错误！');
        return false;
    }
	return true;
}

/**
 * 保存修改
 */
function addcustomerdo() {
	if (checkinput()) {
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		$.ajax({
			url : 'editregisterdatedo.action',
			data : {
				        birthday:$("#datetime").datebox('getValue'),							
						time1 : $("#time1").val(),
						time2 : $("#time2").val(),
						ids:$("#ids").val(),
						flags:1
					},
					type : "post",//数据发送方式   
					success : function(text) {
			    		$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							$('#dlg-custedit').dialog('close');
							$.messager.alert("操作提示", text);
							getgroupuserGrid();
							$('#dlg-custedit').dialog('close');
						} else {
							$.messager.alert("操作提示", text, "error");
						}
					},
					error : function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
	}
}


</script>
<input type="hidden" id="ids" value="<s:property value="model.ids"/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人员预约</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dt>
				预约体检日期
			</dt>
			<dd><input class="easyui-datebox" type=text id="datetime" style="width:100px;height:26px;"></input></dd>		
		</dl>
		<dl>
			<dt>
				开始时间
			</dt>
			<dd><input class="easyui-textbox" type="text" id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input></dd>	
			<dt>
				结束时间
			</dt>
			<dd><input  class="easyui-textbox" type="text" id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd>			
		</dl>
		<dl>
			<dt style="width:400px;height:26px;">
				注意：1、开始时间和结束时间输入格式如 10:00表示10点00分。
			</dt>
					
		</dl>
	</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addcustomerdo();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>