$(document).ready(function () {
	
	f_getDatadic();
	f_getDatadicDWGM();
	f_getDatadicXZQH();
	f_getDatadicHYLX();
	f_getDatadicJJLX();
	
	f_getDatadicQDLX();
	f_getDatadicQDXJFL();
	f_getDatadicQDKHFL();
	getcompanyCons(0);
	
	//单位名称添加搜索
	$('#com_name').textbox({ 
		height:26,
		width:185,
		required: true,
	    icons: [{
			iconCls:'icon-search',
			handler: function(e){
				$('#contacts_edit').dialog({    
				    title: '选择已存在的单位', 
				    href: 'getSerchCompanyInfoPage.action',
				    width: 900,    
				    height: 400, 
				    closed: true,
				    cache: false,
				    modal:true
				});
				$('#contacts_edit').dialog('open');
			}
		}]
	})
});

$(function(){
	$("#address,#sign_name,#sign_pingying,#sign_count,#sign_year").validatebox({
		required: true,
	});
});

//加载 单位类型 下拉列表
function f_getDatadic() {
	$('#com_type').combobox({
		url : 'getDatadis.action?com_Type=TYLX',
		editable : false, //不可编辑状态
		cache : false,
		height:26,
		width:100,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			$('#com_type').combobox('setValue', data[0].id);
		}
	});
}
//加载 企业规模 下拉列表
function f_getDatadicDWGM() {
	$('#comsizecode').combobox({
		url : 'getDatadis.action?com_Type=DANWEIGM',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		height:26,
		width:100,
		valueField : 'id',
		textField : 'name'
	});
}
//加载 行政区划 下拉列表
function f_getDatadicXZQH() {
	$('#areacodees').combobox({
		url : 'getDatadis.action?com_Type=XINGZHENGQH',
		editable : true, //不可编辑状态
		cache : false,
		height:26,
		width:100,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'name'
	});
}
//加载 行业类型 下拉列表
function f_getDatadicHYLX() {
	$('#industrycode').combobox({
		url : 'getDatadis.action?com_Type=HANGYELX',
		editable : true, //不可编辑状态
		cache : false,
		height:26,
		width:220,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			//$('#industrycode').combobox('setValue', data[0].id);
		}
	});
}
//加载 经济类型 下拉列表
function f_getDatadicJJLX() {
	$('#economiccode').combobox({
		url : 'getDatadis.action?com_Type=JINGJILX',
		editable : true, //不可编辑状态
		cache : false,
		height:26,
		width:220,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			//$('#economiccode').combobox('setValue', data[0].id);
		}
	});
}
//加载 签单类型 下拉列表
function f_getDatadicQDLX() {
	$('#sign_type').combobox({
		url : 'getDatadis.action?com_Type=QDLX',
		editable : false, //不可编辑状态
		cache : false,
		height:26,
		width:143,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			$('#sign_type').combobox('setValue', data[0].id);
		}
	});
}

//加载 新旧分类 下拉列表
function f_getDatadicQDXJFL() {
	$('#old_new_type').combobox({
		url : 'getDatadis.action?com_Type=QDXJFL',
		editable : false, //不可编辑状态
		cache : false,
		height:26,
		width:143,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			$('#old_new_type').combobox('setValue', data[0].id);
		}
	});
}

//加载 客户分类 下拉列表
function f_getDatadicQDKHFL() {
	$('#customer_type').combobox({
		url : 'getDatadis.action?com_Type=QDKHFL',
		editable : false, //不可编辑状态
		cache : false,
		height:26,
		width:143,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			$('#customer_type').combobox('setValue', data[0].id);
		}
	});
}


//获取单位联系人列表
function getcompanyCons(id){
	var model = {"company_id":id};
	$("#company_contacts_list").datagrid({
		url: 'getCompanyContactsList.action',
		queryParams: model,
		rownumbers:false,
		height:170,
		columns:[[{align:"center",field:"contacts_name","title":"姓名","width":10},
		      {align:"center",field:"positions","title":"职务","width":10},
		      {align:"center",field:"important_levels","title":"重要级别","width":12},
              {align:"center",field:"phone","title":"办公电话","width":15},
     		  {align:'center',field:"telephone","title":"手机","width":15},
     		  {align:"center",field:"email","title":"电子邮件","width":15},
     		  {align:"center",field:"id_num","title":"身份证号","width":15},
     		  {align:"center",field:"personal_interests","title":"个人爱好","width":35},
     		  {align:"center",field:"remarke","title":"备注","width":20},
     		  {align:"center",field:"fxg","title":"操作","width":10,"formatter":f_contacts}
     		  ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		striped:true,
		toolbar:"#toobor_contacts"
	});
}

function contacts_edit(){
	contacts_index = '';
	$('#contacts_edit').dialog({    
	    title: '新增单位联系人', 
	    href: 'getCompanyContactsEditPage.action',
	    width: 600,    
	    height: 270, 
	    closed: true,
	    cache: false,
	    modal:true
	});
	$('#contacts_edit').dialog('open');
}

function f_contacts(value,row,index){
	var str = '<a href=\"javascript:fcontacts_xg(\''+index+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>'
	        + ' | ' +'<a href=\"javascript:fcontacts_sc(\''+index+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>'
	return str;
}
var contacts_index = '';
function fcontacts_xg(index){
	contacts_index = index;
	$('#contacts_edit').dialog({    
	    title: '修改单位联系人', 
	    href: 'getCompanyContactsEditPage.action',
	    width: 600,    
	    height: 270, 
	    closed: true,
	    cache: false,
	    modal:true
	});
	$('#contacts_edit').dialog('open');
}

function fcontacts_sc(index){
	$("#company_contacts_list").datagrid('deleteRow',index);
	var rows = $("#company_contacts_list").datagrid('getRows');
	$("#company_contacts_list").datagrid('loadData',rows);
}

function pingyingsc(){
	$.ajax({
		url:'pinying.action',
		data:{"pinying":$("#sign_name").val()},
		type:'post',
		success:function(data){
			$("#sign_pingying").val(data);
		},
		error : function() {
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}

function saveSignBillPlan(){
	if($("#com_name").textbox('getValue') == ''){
		$('#com_name').textbox('textbox').focus();
		return;
	}else if($("#address").val() == ''){
		$("#address").focus();
		return;
	}else if($("#sign_name").val() == ''){
		$("#sign_name").focus();
		return;
	}else if($("#sign_pingying").val() == ''){
		$("#sign_pingying").focus();
		return;
	}else if($("#sign_count").val() == ''){
		$("#sign_count").focus();
		return;
	}else if($("#sign_year").val() == ''){
		$("#sign_year").focus();
		return;
	}
	if (isNaN(Number($("#sign_persion").val())))
	{
		$.messager.alert("操作提示", "估算人数无效");
		$("#sign_persion").focus();
		return;
	}
	if (isNaN(Number($("#sign_amount").val())))
	{
		$.messager.alert("操作提示", "估算金额无效");
		$("#sign_amount").focus();
		return;
	}
	if($("#sign_amount").val().length>6){
		$.messager.alert("操作提示", "估算金额已上百万");
		$("#sign_amount").focus();
		return;
	}
	var rows = $("#company_contacts_list").datagrid('getRows');
	if(rows.length == 0){
		$.messager.alert("操作提示", "请填写单位联系人!", "error");
		return;
	}
	
	var contacts = new Array();
	for(i=0;i<rows.length;i++){
		contacts.push({
			contacts_name : rows[i].contacts_name,
			important_level : rows[i].important_level,
			position : rows[i].position,
			id_num : rows[i].id_num,
			phone : rows[i].phone,
			telephone : rows[i].telephone,
			email : rows[i].email,
			personal_interests : rows[i].personal_interests,
			remarke : rows[i].remarke
		});
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    $("body").prepend(str);
	$.ajax({
		url:'saveSignBillPlan.action',
		type:'post',
		data:{
			"company_id":$("#company_id").val(),
			"com_name":$("#com_name").textbox('getValue'),
			"address":$("#address").val(),
			"com_type":$("#com_type").combobox('getValue'),
			"economiccode":$("#economiccode").combobox('getValue'),
			"comsizecode":$("#comsizecode").combobox('getValue'),
			"industrycode":$("#industrycode").combobox('getValue'),
			"areacode":$("#areacodees").combobox('getValue'),
			"sign_name":$("#sign_name").val(),
			"sign_pingying":$("#sign_pingying").val(),
			"sign_count":$("#sign_count").val(),
			"sign_year":$("#sign_year").val(),
			"sign_persion":$("#sign_persion").val(),
			"sign_amount":$("#sign_amount").val(),
			"sign_type":$("#sign_type").combobox('getValue'),
			"old_new_type":$("#old_new_type").combobox('getValue'),
			"customer_type":$("#customer_type").combobox('getValue'),
			"sign_date":$("#sign_date").datebox('getValue'),
			"end_date":$("#end_date").datebox('getValue'),
			"contacts":JSON.stringify(contacts)
		},
		success:function(data){
			$(".loading_div").remove();
			
			if('ok' == data.split("-")[0]){
				$.messager.alert("操作提示", data.split("-")[1], "info");
				$('#dlg-edit').dialog('close');
				chaxunsingn();
			}else{
				$.messager.alert("操作提示", data.split("-")[1], "error");
			}
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}
