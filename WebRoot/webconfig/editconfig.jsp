<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script>

$(function(){
	$('#types').validatebox({   
		 	required: true
	});
});
function f_updateconfig()
{
	var code=$("#code").val();
	var types=$("#types").val();
	var memo=$("#memo").val();//1 表示字符型，2表示数字型（不带小数点），3表示带小数点
	if(""==$.trim(types))
	{
		$.messager.alert("提示","类型不能为空","warning");
		$("#types").val("");
		return ;
	}
	//1 表示字符型
	if("1"==memo)
	{
		var reg1 = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
		if(reg1.test(types))
		{
			$.messager.alert("提示","类型不能包含特殊字符","warning");
			return ;
		}
	}
	//2表示数字型（不带小数点），
	else if("2"==memo)
	{
		var reg1 = /^-?\d+$/;
		if(!reg1.test(types))
		{
			$.messager.alert("提示","类型只能为整数！","warning");
			return ;
		} 
		if(Number(types)<=-10000000||Number(types)>=10000000)
		{
			$.messager.alert("提示","类型只能大于-10000000,并且小于10000000!","warning");
			return ;
		}
	}
	//3表示带小数点
	else if("3"==memo)
	{
		var reg=/^-?\d+\.\d+$/;    
        if(!reg.test(types))
        {    
            $.messager.alert("提示","类型只能为小数(格式0.00)!","warning");
            return ;
        }   
		if(Number(types)<=-10000000.00||Number(types)>=10000000.00)
		{
			$.messager.alert("提示","类型只能大于-10000000.00,并且小于10000000.00!","warning");
			return ;
		}
	}
	else
	{
		$.messager.alert("提示","memo的值有误,只能为1,2,3.请检查","warning");
		return ;
	}
	$.ajax({
		url:'<%=request.getContextPath()%>/webConfigupdateConfig.action?code='+code+'&types='+types,
		type:'post',
		success:function(data){
			$.messager.alert("提示",data,"info");
			$("#dd").dialog('close');
			getGrid();
		}
	});
}
</script>
	<input type="hidden" value="${webConfig.code}" id="code"/>
	<input type="hidden" value="${webConfig.memo}" id="memo"/>
	<div class="formdiv">
		<dl>
			<dt>
				编码：
			</dt>
			<dd  style="padding-top:5px;padding-left:5px">
				<s:property value="webConfig.code"/>
			</dd>
		</dl>
		<dl>
			<dt>
				名称：
			</dt>
			<dd  style="padding-top:5px">
				<s:property value="webConfig.name"/>
			</dd>
		</dl>
		<dl>
			<dt>
				类型：
			</dt>
			<dd>
				<input type="text"   class="textinput"  style="width: 244px;" value="${webConfig.types}" id="types"/>
				<span STYLE="color: red"></span>
				<strong class="red">*</strong>
			</dd>
		</dl>
		<dl>
			<dt>
				标记：
			</dt>
			<dd  style="padding-top:5px">
				<s:property value="webConfig.remark"/>
			</dd>
		</dl>
	</div>
  <div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
			<button onclick="javascript:f_updateconfig();" iconcls="icon-ok-light" class="easyui-linkbutton c6 l-btn l-btn-small" group="" id="">确认</button>
			<button onclick="javascript:$('#dd').dialog('close')" iconcls="icon-undo" class="easyui-linkbutton l-btn l-btn-small" group="" id="">返回</button>
	</div>
  </div>	
