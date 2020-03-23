<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	$("#serch_comname").val($("#com_name").textbox('getValue'));
	getcompanyinfo();
});

//获取单位联系人列表
function getcompanyinfo(){
	var model = {"com_name":$("#serch_comname").val()};
	$("#company_info_list").treegrid({
		 url:'getSerchCompanyInfoList.action',
		 dataType: 'json',
		 queryParams:model,
		 height:290,
		 idField:'id',
		 treeField:'com_name',
		 columns:[[{field:'com_name',title:'单位名称',width:40},
		          {align:'center',field:'address',title:'地址',width:25},
		          {align:'center',field:'com_types',title:'单位类型',width:15},
		          {align:'center',field:'comsizecodes',title:'企业规模',width:15},
		          {align:'center',field:'areacodes',title:'行政区划',width:20},
		          {align:'center',field:'economiccodes',title:'经济类型',width:20},
		          {align:'center',field:'industrycodes',title:'行业类型',width:20}
		]],
   		singleSelect:true,
   		onLoadSuccess:function(){
   			$("#serch_comname").focus();
   		},
	    fitColumns:true,
	    rownumbers:true,
	    toolbar:'#sousuo_input'
    });
}

function companyReload(){
	$("#company_info_list").treegrid('load',{"com_name":$("#serch_comname").val()})
}

//确定选择单位信息
function saveCompanyinfo(){
	
	var row = $("#company_info_list").treegrid('getSelected');
	if(row == null){
		$.messager.alert("操作提示", "请选择一个单位!", "error");
		return;
	}
	$("#com_name").textbox('setValue',row.com_name);
	$("#company_id").val(row.id);
	$("#com_type").combobox('setValue',row.com_type);
	$("#address").val(row.address);
	$("#comsizecode").combobox('setValue',row.comsizecode);
	$("#areacode").combobox('setValue',row.areacode);
	$("#economiccode").combobox('setValue',row.economiccode);
	$("#industrycode").combobox('setValue',row.industrycode);
	
	getcompanyCons(row.id);
	
	$('#contacts_edit').dialog('close');
}
</script>
<fieldset style="margin:5px;">
    <legend><strong>单位信息</strong></legend>
    <table id="company_info_list"></table>
</fieldset>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:saveCompanyinfo();">确定选择</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#contacts_edit').dialog('close')">关闭</a>
	</div>
</div>
<div id="sousuo_input">
	单位名称:<input class="textinput" onkeyup="companyReload()" id="serch_comname" style="width:150px;">
	<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:26px;" onclick="javascript:companyReload();">查询</a>
</div>

