<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
$(document).ready(function () {  
	$('input').attr("maxlength","20");
	f_getDatadic();	
	f_getDatadicJJLX();
	f_getDatadicHYLX();
	f_getDatadicXZQH();
	f_getDatadicDWGM();
});


//获取菜单

	function f_getDatadic() {
		var comtype = '<s:property value="model.com_Type"/>';
		$('#com_Type').combobox({
			url : 'getDatadis.action?com_Type=TYLX',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == comtype) {
						$('#com_Type').combobox('setValue', data[i].id);
						break;
					} else {
						$('#com_Type').combobox('setValue', data[0].id);
					}
				}
			}
		});
	}
	
	function f_getDatadicDWGM() {
		var comtype = '<s:property value="model.comsizecode"/>';
		$('#comsizecode').combobox({
			url : 'getDatadis.action?com_Type=DANWEIGM',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == comtype) {
						$('#comsizecode').combobox('setValue', data[i].id);
						break;
					} else {
						$('#comsizecode').combobox('setValue', data[0].id);
					}
				}
			}
		});
	}
	
	function f_getDatadicXZQH() {
		var comtype = '<s:property value="model.areacode"/>';
		$('#areacode').combobox({
			url : 'getDatadis.action?com_Type=XINGZHENGQH',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == comtype) {
						$('#areacode').combobox('setValue', data[i].id);
						break;
					} else {
						$('#areacode').combobox('setValue', data[0].id);
					}
				}
			}
		});
	}
	
	function f_getDatadicHYLX() {
		var comtype = '<s:property value="model.industrycode"/>';
		$('#industrycode').combobox({
			url : 'getDatadis.action?com_Type=HANGYELX',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == comtype) {
						$('#industrycode').combobox('setValue', data[i].id);
						break;
					} else {
						$('#industrycode').combobox('setValue', data[0].id);
					}
				}
			}
		});
	}

	function f_getDatadicJJLX() {
		var comtype = '<s:property value="model.economiccode"/>';
		$('#economiccode').combobox({
			url : 'getDatadis.action?com_Type=JINGJILX',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == comtype) {
						$('#economiccode').combobox('setValue', data[i].id);
						break;
					} else {
						$('#economiccode').combobox('setValue', data[0].id);
					}
				}
			}
		});
	}

	function checkinput() {
		if (document.getElementById("com_Name").value == '') {
			alert('单位名称不能为空！');
			document.getElementById("com_Name").focus();
			return false;
		} else if (document.getElementsByName("com_Type")[0].value == '') {
			alert('单位类型不能为空！');
			document.getElementsByName("com_Type")[0].focus();
			return false;
		} else if (document.getElementById("contact_Name").value == '') {
			alert('联系人不能为空！');
			document.getElementById("contact_Name").focus();
			return false;
		} else if (document.getElementById("contact_Phone").value == '') {
			alert('联系人电话不能为空！');
			document.getElementById("contact_Phone").focus();
			return false;
		} else if (document.getElementById("email").value != '') {
			if (!isValidMail(document.getElementById("email").value)) {
				alert('email地址格式错误！');
				document.getElementById("email").focus();
				return false;
			}
		} else if (document.getElementById("remark").value != '') {
			if (document.getElementById("remark").value.length > 200) {
				alert('备注字数超限！');
				document.getElementById("remark").focus();
				return false;
			}
		}
		return true;
	}

	function isValidMail(sText) {
		var reMail = /^(?:[a-z\d]+[_\-\+\.]?)*[a-z\d]+@(?:([a-z\d]+\-?)*[a-z\d]+\.)+([a-z]{2,})+$/i;
		return reMail.test(sText)
	}

	function checkcomNum(edittypess) {
		if ($("#com_Num").val() != '') {
			var reshtml = $.ajax({
				url : "checkcomnum.action",
				data : {
					id : $("#id").val(),
					com_Num : $("#com_Num").val(),
					edittype : edittypess
				},
				async : false
			}).responseText;
			if (reshtml == 'ok') {
				return true;
			} else {
				$.messager.alert("操作提示", "单位编号重复，交易不能继续", "error");
				return false;
			}
		} else {
			return true;
		}

	}

	//本级单位下面创建
	var edittypess;
	function bjdwadd(edittypess) {
		if ((checkinput()) && (checkcomNum(edittypess))) {
			var strnotice = "您确实要当前单位下新增单位吗？";
			if (edittypess == 1) {
				strnotice = "您确实要在当前单位下新增下级单位吗？";
			} else if (edittypess == 2) {
				strnotice = "您确实要当前单位下新增单位吗？";
			} else if (edittypess == 3) {
				strnotice = "您确实要修改当前单位吗？";
			}
			$.messager.confirm('提示信息',strnotice,function(r) {
								if (r) {
									var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
											+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
									$("body").prepend(str);
									$.ajax({
												url : 'zybeditcompany.action',
												data : {
													id : $("#id").val(),
													company_Id : $("#company_Id").val(),
													com_Name : $("#com_Name").val(),
													com_Num : $("#com_Num").val(),
													com_Level : $("#com_Level").val(),
													is_Active : 'Y',
													com_Type : document.getElementsByName("com_Type")[0].value,
													comsizecode:document.getElementsByName("comsizecode")[0].value,
													economiccode:document.getElementsByName("economiccode")[0].value,
													areacode:document.getElementsByName("areacode")[0].value,
													industrycode:document.getElementsByName("industrycode")[0].value,
													contact_Name : $("#contact_Name").val(),
													email : $("#email").val(),
													name_pinyin : $("#name_pinyin").val(),
													edittype : edittypess,
													father_con_num : $("#father_con_num").val(),
													contact_Phone : $("#contact_Phone").val(),
													com_zip : $("#com_zip").val(),
													com_phone : $("#com_phone").val(),
													address : $("#address").val(),
													remark : $("#remark").val()
												},
												type : "post",//数据发送方式   
												success : function(text) {
													$(".loading_div").remove();
													if (text.split("-")[0] == 'ok') {
														$.messager.alert("操作提示",text.split("-")[1]);
														window.parent.reopen();
													} else if (text.split("-")[0] == 'error') {
														$.messager.alert("操作提示",text.split("-")[1],"error");
													}
												},
												error : function() {
													$(".loading_div").remove();
													$.messager.alert("操作提示",
															"操作错误", "error");
												}
											});
								}
							});
		}
	}

	function delselectitem() {
		window.parent.f_getMenuzbOne(0);
	}

	function deledititem() {
		$('#com_Name').textbox('setValue', '');
		$('#com_Num').textbox('setValue', '');
		$("#contact_Name").textbox('setValue', '');
		$("#email").textbox('setValue', '');
		$("#contact_Phone").textbox('setValue', '');
		$("#address").textbox('setValue', '');
		$("#remark").textbox('setValue', '');
	}

	function bjdwdel() {
		if (($("#id").val() == '') || (Number($("#id").val()) <= 0)) {
			$.messager.alert("操作提示", "请选择要删除的单位", "error");
		} else {
			$.messager
					.confirm(
							'提示信息',
							'是否确定删除此单位吗？',
							function(r) {
								if (r) {
									var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
											+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
									$("body").prepend(str);
									$
											.ajax({
												url : 'editcompany.action',
												data : {
													id : $("#id").val(),
													company_Id : $(
															"#company_Id")
															.val(),
													edittype : 4
												},
												type : "post",//数据发送方式   
												success : function(text) {
													$(".loading_div").remove();
													if (text.split("-")[0] == 'ok') {
														$.messager.alert(
																"操作提示", text);
														window.parent.reopen();
													} else if (text.split("-")[0] == 'error') {
														$.messager.alert(
																"操作提示", text,
																"error");
													}
												},
												error : function() {
													$(".loading_div").remove();
													$.messager.alert("操作提示",
															"操作错误", "error");
												}
											});
								}
							});
		}
	}

	/**
	 * 检查项目名称获取拼音
	 */
	function query_pinyin() {
		$.ajax({
			url : 'pinying.action',
			type : 'post',
			data : {
				pinying : $('#com_Name').val()
			},
			success : function(data) {
				$('#name_pinyin').textbox('setValue', data.toUpperCase());
			},
		})
	}
</script>
</head>
<body>
<input type="hidden" id="company_Id" value='<s:property value="model.company_Id"/>'/>
<input type="hidden" id="father_con_num" value='<s:property value="model.father_con_num"/>'/>

<input type="hidden" id="id" value='<s:property value="model.id"/>'/>

<input type="hidden" id="com_Level" value='<s:property value="model.com_Level"/>'/>


<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>选中树形单位信息</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="height:26px;width:140px;">上级单位编号</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.company_Id"/></dd>
					<dt style="height:26px;width:140px;">上级单位名称</dt>
					<dd style="height:26px;width:450px;font-weight:bold;"><s:property value="model.dep_Name"/></dd>
				</dl>
				<dl>
				    <dt style="height:26px;width:140px;">本级单位编号</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.id"/></dd>
					<dt style="height:26px;width:140px;">本级单位单名称</dt>
					<dd style="height:26px;width:450px;"><s:property value="model.com_Name"/></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>编辑单位基本信息</strong></legend>
			<div class="user-query">
				<dl>
					<dt >单位名称 <strong class="quest-color">*</strong></dt>
					<dd style="height:26px;width:630px;">
					     <input class="easyui-textbox" maxlength="20" type="text" id="com_Name" data-options="events:{blur:query_pinyin}" value="<s:property value="model.com_Name"/>" style="height:26px;width:390px;"/></dd>
					<dd><a href="javascript:deledititem();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;height:26px;">清除编辑项</a></dd>
					<dd><a href="javascript:delselectitem();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:120px;height:26px;">清除所选单位</a></dd>
				</dl>
				<dl>
					<dt >助记符 <strong class="quest-color">*</strong></dt>
					<dd><input class="easyui-textbox" maxlength="20" type="text" id="name_pinyin" value="<s:property value="model.name_pinyin"/>" style="height:26px;width:140px;"/></dd>
					<dt>单位类型 <strong class="quest-color">*</strong></dt>
					<dd><select class="easyui-combobox" id="com_Type" name="com_Type" data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
			    </dl>
				<dl>
					<dt>单位编码<strong class="quest-color">*</strong></dt>
					<dd><input class="easyui-textbox" maxlength="20"  type="text" id="com_Num" value="<s:property value="model.com_Num"/>" style="width:140px;height:26px;"></input></dd>
                    <dt>企业规模<strong class="quest-color">*</strong></dt>
					<dd><select class="easyui-combobox" id="comsizecode" name="comsizecode"  panelMaxHeight="200px"  data-options="height:26,width:140,panelHeight:'auto'"></select></dd>				
				</dl>
				<dl>
					<dt>经济类型<strong class="quest-color">*</strong></dt>
					<dd><select class="easyui-combobox" id="economiccode" name="economiccode"  panelMaxHeight="200px"  data-options="height:26,width:390,panelHeight:'auto'"></select></dd>				
				</dl>
				<dl>
					<dt>行业类型<strong class="quest-color">*</strong></dt>
					<dd><select class="easyui-combobox" id="industrycode" name="industrycode"  panelMaxHeight="200px"  data-options="height:26,width:390,panelHeight:'auto'"></select></dd>
                    
				</dl>
				<dl>
					<dt>所属区域<strong class="quest-color">*</strong></dt>
					<dd><select class="easyui-combobox" id="areacode" name="areacode" panelMaxHeight="200px"  data-options="height:26,width:200,panelHeight:'auto'"></select></dd>
                    <dt>联系人<strong class="quest-color">*</strong></dt>
					<dd><input class="easyui-textbox"  maxlength="20"  type="text" id="contact_Name" value="<s:property value="model.contact_Name"/>" style="width:80px;height:26px;"></input></dd>		
				</dl>
				<dl>
				    <dt>Email</dt>
					<dd><input class="easyui-textbox"  maxlength="20" type="text" id="email" value="<s:property value="model.email"/>" style="width:140px;height:26px;"></input></dd>
				     <dt>联系电话<strong class="quest-color">*</strong></dt>
					<dd><input class="easyui-textbox" maxlength="11" type="text" id="contact_Phone" value="<s:property value="model.contact_Phone"/>" style="width:140px;height:26px;"></input></dd>
				</dl>
				<dl>
				    <dt>单位邮编</dt>
					<dd><input class="easyui-textbox"  maxlength="20" type="text" id="com_zip" value="<s:property value="model.com_zip"/>" style="width:140px;height:26px;"></input></dd>
				     <dt>单位电话<strong class="quest-color">*</strong></dt>
					<dd><input class="easyui-textbox" maxlength="11" type="text" id="com_phone" value="<s:property value="model.com_phone"/>" style="width:140px;height:26px;"></input></dd>
				</dl>
				<dl>
				    <dt>地址</dt>
					<dd><input class="easyui-textbox" maxlength="100"  type="text" id="address" value="<s:property value="model.address"/>" style="width:630px;height:26px;"></input></dd>
				</dl>
				<dl>
				    <dt>备注</dt>
					<dd><input class="easyui-textbox" maxlength="100"  type="text" id="remark" value="<s:property value="model.remark"/>" style="width:630px;height:26px;"></input></dd>
				</dl>
				</div>
 </fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:bjdwadd(2)" class="easyui-linkbutton c6" style="width:160px;">新增同级单位</a>
	    <a href="javascript:bjdwadd(1)" class="easyui-linkbutton c6" style="width:160px;">新增下级单位</a>
	    <a href="javascript:bjdwadd(3)" class="easyui-linkbutton c6"style="width:80px;" onclick="javascript:$('#dlg-search').dialog('close');$('#dd').datagrid().reload();">单位修改</a>
	    <a href="javascript:bjdwdel()" class="easyui-linkbutton" style="width:80px;" >单位删除</a>
	</div>
</div>
</body>
</html>

