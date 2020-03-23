<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	var rows = $("#company_contacts_list").datagrid('getRows');
	$('#position').combobox({
		url : 'getDatadis.action?com_Type=DWZWLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			if(contacts_index != ''){
				$("#position").combobox('setValue',rows[contacts_index].position);
			}else{
				$('#position').combobox('setValue',data[0].id);
			}
		}
	});
	$('#important_level').combobox({
		url : 'getDatadis.action?com_Type=DWZYJB',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			if(contacts_index != ''){
				$("#important_level").combobox('setValue',rows[contacts_index].important_level);
			}else{
				$('#important_level').combobox('setValue',data[0].id);
			}
		}
	});
	
	if(contacts_index != ''){
		$("#contacts_name").val(rows[contacts_index].contacts_name);
		$("#id_num").val(rows[contacts_index].id_num);
		$("#phone").val(rows[contacts_index].phone);
		$("#telephone").val(rows[contacts_index].telephone);
		$("#email").val(rows[contacts_index].email);
		$("#personal_interests").val(rows[contacts_index].personal_interests);
		$("#remarke").val(rows[contacts_index].remarke);
	}
});
$(function(){
	$("#contacts_name,#telephone").validatebox({
		required: true
	});
});
//保存单位联系人
function saveCompanyContacts(){
	if($("#contacts_name").val() == ''){
		$("#contacts_name").focus();
		return;
	}else if($("#telephone").val() == ''){
		$("#telephone").focus();
		return;
	}
	if (isNaN(Number($("#telephone").val())))
	{
		$.messager.alert("操作提示", "手机号无效");
		$("#telephone").focus();
		return;
	}
	if($("#telephone").val().length!=11){
		$.messager.alert("操作提示", "手机号码位数不正确");
		$("#telephone").focus();
		return;
	}
	if(contacts_index != ''){
		var rows = $("#company_contacts_list").datagrid('getRows');
		rows[contacts_index].contacts_name = $("#contacts_name").val();
		rows[contacts_index].important_level = $("#important_level").combobox('getValue');
		rows[contacts_index].important_levels = $("#important_level").combobox('getText');
		rows[contacts_index].position = $("#position").combobox('getValue');
		rows[contacts_index].positions = $("#position").combobox('getText');
		rows[contacts_index].id_num = $("#id_num").val();
		rows[contacts_index].phone = $("#phone").val();
		rows[contacts_index].telephone = $("#telephone").val();
		rows[contacts_index].email = $("#email").val();
		rows[contacts_index].personal_interests = $("#personal_interests").val();
		rows[contacts_index].remarke = $("#remarke").val();
		
		$("#company_contacts_list").datagrid('loadData',rows);
	}else{
		var companyContacts = new Object();
		companyContacts.contacts_name = $("#contacts_name").val();
		companyContacts.important_level = $("#important_level").combobox('getValue');
		companyContacts.important_levels = $("#important_level").combobox('getText');
		companyContacts.position = $("#position").combobox('getValue');
		companyContacts.positions = $("#position").combobox('getText');
		companyContacts.id_num = $("#id_num").val();
		companyContacts.phone = $("#phone").val();
		companyContacts.telephone = $("#telephone").val();
		companyContacts.email = $("#email").val();
		companyContacts.personal_interests = $("#personal_interests").val();
		companyContacts.remarke = $("#remarke").val();
		
		$("#company_contacts_list").datagrid('appendRow',companyContacts);
	}
	$('#contacts_edit').dialog('close');
}
</script>
<div class="user-query" style="padding-top:20px;">
	<dl>
		<dt>姓名：</dt>
		<dd><input type="text" id="contacts_name" value="" class="textinput"/><strong class="quest-color">*</strong></dd>
		<dt>重要级别：</dt>
		<dd><select id="important_level" class="easyui-combobox" data-options="height:26,width:143,panelHeight:'auto'"></select><strong class="quest-color">*</strong></dd>
	</dl>
	<dl>
		<dt>职务：</dt>
		<dd><select id="position" class="easyui-combobox" data-options="height:26,width:143,panelHeight:'auto'"></select><strong class="quest-color">*</strong></dd>
		<dt>身份证号：</dt>
		<dd><input type="text" id="id_num" value="" class="textinput"/><strong class="scolor">*</strong></dd>
	</dl>
	<dl>
		<dt>办公电话：</dt>
		<dd><input type="text" id="phone" value="" class="textinput"/><strong class="scolor">*</strong></dd>
		<dt>手机：</dt>
		<dd><input type="text" id="telephone" value="" class="textinput"/><strong class="scolor">*</strong></dd>
	</dl>
	<dl>
		<dt>电子邮件：</dt>
		<dd><input type="text" id="email" value="" class="textinput"   class="easyui-validatebox"  validType="email"/><strong class="scolor">*</strong></dd>
		<dt>个人爱好：</dt>
		<dd><input type="text" id="personal_interests" value="" class="textinput"/><strong class="scolor">*</strong></dd>
	</dl>
	<dl>
		<dt>备注：</dt>
		<dd><input style="width: 395px;" type="text" id="remarke" value="" class="textinput"/><strong class="scolor">*</strong></dd>
	</dl>
</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:saveCompanyContacts();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#contacts_edit').dialog('close')">关闭</a>
	</div>
</div>

