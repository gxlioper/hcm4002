<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manager/createMenu.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/select2.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyin_dict_firstletter.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyinUtil.js?randomId=<%=Math.random()%>"></script>

<style>
.select2-container--default{
width: 180px !important; 
}
</style>
<script type="text/javascript">
$(document).ready(function() {
    //$('#gnlist').select2();

});
$(function(){
	$("#menuname,#indexid").validatebox({
		required: true
	});
	$("#indexid").validatebox({
		required:true,
		validType:'Number'
	});
	$.extend($.fn.validatebox.defaults.rules,{			
			Number:{
				validator:function(value){
					 var reg =/^[0-9]*$/;          
					  return reg.test(value);
				},
				message:'只能输入数字'
			}
		});
})

function f_menusave(){
	if($("#menuname").val()==''){
		//alert('菜单项名称不能为空！');
		$("#menuname").focus();
		return ;
	}
	if($("#indexid").val()==''){
		//alert('顺序号不能为空！');
		$("#indexid").focus();
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);

	$.ajax({
		
		url:'addmenu.action',
		data:{father_id:$("#menulist").combobox('getValue'),indexid:$("#indexid").val(),rootid:$("#menulist").combobox('getValue'),menusm:$("#menusm").val(),xtgn_id:$("#gnlist").combobox('getValue'),name:$("#menuname").val(),usertype:$("#usertype").val(),url_type:$("#urltype").val(),other_url:$("#otherurl").val(),ispop:$("#ispop").val()},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			reopen();
			//alert('增加菜单成功！');
			$.messager.alert('提示信息','增加菜单成功！');
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
		
	});
}

function f_menuedit(){
	if($("#menuname").val()==''){
		//alert('菜单项名称不能为空！');
		$("#menuname").focus();
		return ;
	}
	if($("#indexid").val()==''){
		//alert('顺序号不能为空！');
		$("#indexid").focus();
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	$.ajax({
		url:'editmenu.action',
		data:{id:$("#rootid").val(),father_id:$("#menulist").combobox('getValue'),indexid:$("#indexid").val(),rootid:$("#menulist").combobox('getValue'),menusm:$("#menusm").val(),xtgn_id:$("#gnlist").combobox('getValue'),name:$("#menuname").val(),usertype:$("#usertype").val(),url_type:$("#urltype").val(),other_url:$("#otherurl").val(),ispop:$("#ispop").val()},
		type:'post',
		success:function(text){
			$(".loading_div").remove();
			 reopen();
			//alert('修改菜单成功！');
			$.messager.alert('提示信息','修改菜单成功！');
			
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
	
}
function f_menudel(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	$.ajax({
		url:'delmenu.action',
		 data:{rootid:$("#rootid").val()}, 
		 type:'post',
		 success:function(text){
			 $(".loading_div").remove();
		 	 reopen();
		 	//alert('删除菜单成功！');
		 	$.messager.alert('提示信息','删除菜单成功！');
		 },
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
	});
}

</script>

</head>
<body >

<!-- <div id="cont-page" class="include-page" > -->

<input id="id" name="id" value="" type="hidden"/>
<div class="easyui-layout" fit="true" style="margin:5px;">
    <div data-options="region:'west',split:true" title="菜单列表" style="width:200px;">
    	<ul class="easyui-tree" id="tree"></ul>
    </div>
    <div data-options="region:'center',title:'菜单信息'">
	<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt>菜单项名称：</dt>
				<dd><input type="text" style="width:286px;" id="menuname" name="menuname" class="textinput"/> <strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>上级菜单：</dt>
				<dd>
					<select class="easyui-combobox"  id="menulist" data-options="height:30,width:300,panelHeight:'auto'">

					</select>
 					<strong class="red">*</strong>
 				</dd>
			</dl>
			<dl>
				<dt>顺序号：</dt>
				<dd><input type="text" size="4" id="indexid" name="indexid" maxlength="3" size="3" class="textinput"/></dd>
			</dl>
				<input id="usertype" name="usertype" type="hidden" value="1"/>
			<dl>
				<dt>是否使用外部链接：</dt>
				<dd>
				<select id="urltype" name="urltype" onchange="f_getchangerurltype()" >
				<option value="1" selected="selected">不引用外部地址</option>
				<option value="2">引用外部地址</option></select> 
				<strong class="red">*</strong></dd>
			</dl>
			<dl id="wbdz_id">
				<dt>外部引用地址</dt>
				<dd><input id="otherurl" name="otherurl" type="text" maxlength="100" size="30" class="textinput"/><input id="rootid" name="rootid" value="root" type="hidden"/></dd>
				
			</dl>
			<dl id="dygn_id">
				<dt>对应功能：</dt>
				<dd><select  class="easyui-combobox" id="gnlist" name="gnlist" data-options="height:30,width:300,panelHeight:'auto'"></select><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>是否弹出：</dt>
				<dd><select  id="ispop" name="ispop" >
				<option value="1" selected="selected">不弹出页面</option>
				<option value="2">弹出新页面</option>
				</select> <strong class="red">*</strong></dd>
			</dl>
			 <div style="padding-left:200px"><font id="messcolor" color="red"><span id="mes"></span></font></div>
			<dl>
				<dt>&nbsp;</dt>
				<dd>
					<button class="easyui-linkbutton c6" iconCls="icon-ok-light" onclick='javascript:f_menusave()'>新增</button>
					<button class="easyui-linkbutton c6" iconCls="icon-ok-light" onclick='javascript:f_menuedit()'>确认修改</button>
					<button class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:f_menudel()">删除</button>
				</dd>
			</dl>
	</div>
    </div>
</div>

</div>
</body>
</html>