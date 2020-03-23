<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	if($("#flag").val()=='1'){
		$.ajax({
			 url : 'getCrmSignBillPlanFromZhuangdan.action',
			data : {ids:$('#id').val()},
			type : "post",
			async:true, 
			success : function(data) {
				if(data!=null){
					var datastr=eval('('+data+')');
					$("#ck_sign_name").html(datastr[0].sign_name);
					$("#ck_sign_pingying").html(datastr[0].sign_pingying);
					$("#ck_sign_type").html(datastr[0].sign_type);
					$("#ck_sign_num").html(datastr[0].sign_num);
					$("#ck_sign_count").html(datastr[0].sign_count);
					$("#ck_sign_year").html(datastr[0].sign_year);
					$("#ck_old_new_type").html(datastr[0].old_new_type);
					$("#ck_customer_type").html(datastr[0].customer_type);
					$("#ck_protect_date").html(datastr[0].protect_date);
					$("#ck_sign_persion").html(datastr[0].sign_persion);
					$("#ck_sign_amount").html(datastr[0].sign_amount);
					$("#ck_sign_date").html(datastr[0].sign_date);
					$("#ck_end_date").html(datastr[0].end_date);
					$("#ck_abort_date").html(datastr[0].abort_date);
					$("#ck_sign_statuss").html(datastr[0].sign_statuss);
					$("#ck_track_progresss").html(datastr[0].track_progresss);
					$("#ck_track_time").html(datastr[0].track_time);
					$("#ck_creater").html(datastr[0].creater);
					$("#ck_create_time").html(datastr[0].create_time);
					getcompanyCons_ck(datastr[0].company_id);
				}				
			},
		 });
	}else{
		var rows = $("#sign_bill_span_list").datagrid('getRows');
		$("#ck_sign_name").html(rows[row_index].sign_name);
		$("#ck_sign_pingying").html(rows[row_index].sign_pingying);
		$("#ck_sign_type").html(rows[row_index].sign_type);
		$("#ck_sign_count").html(rows[row_index].sign_count);
		$("#ck_sign_year").html(rows[row_index].sign_year);
		$("#ck_old_new_type").html(rows[row_index].old_new_type);
		$("#ck_customer_type").html(rows[row_index].customer_type);
		$("#ck_sign_persion").html(rows[row_index].sign_persion);
		$("#ck_sign_amount").html(rows[row_index].sign_amount);
		$("#ck_sign_date").html(rows[row_index].sign_date);
		$("#ck_end_date").html(rows[row_index].end_date);
		$("#ck_sign_num").html(rows[row_index].sign_num);
		$("#ck_protect_date").html(rows[row_index].protect_date);
		$("#ck_abort_date").html(rows[row_index].abort_date);
		$("#ck_sign_statuss").html(rows[row_index].sign_statuss);
		$("#ck_track_progresss").html(rows[row_index].track_progresss);
		$("#ck_track_time").html(rows[row_index].track_time);
		$("#ck_creater").html(rows[row_index].creater);
		$("#ck_create_time").html(rows[row_index].create_time);
		getcompanyCons_ck(rows[row_index].company_id);

	}	
});

//获取单位联系人列表
function getcompanyCons_ck(id){
	var model = {"company_id":id};
	$("#ck_contacts_list").datagrid({
		url: 'getCompanyContactsList.action',
		queryParams: model,
		rownumbers:false,
		height:130,
		columns:[[{align:"center",field:"contacts_name","title":"姓名","width":10},
		      {align:"center",field:"positions","title":"职务","width":10},
		      {align:"center",field:"important_levels","title":"重要级别","width":12},
              {align:"center",field:"phone","title":"手机","width":15},
     		  {align:'center',field:"telephone","title":"办公电话","width":15},
     		  {align:"center",field:"email","title":"电子邮件","width":15},
     		  {align:"center",field:"id_num","title":"身份证号","width":15},
     		  {align:"center",field:"personal_interests","title":"个人爱好","width":35},
     		  {align:"center",field:"remarke","title":"备注","width":20}
     		  ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		striped:true
	});
}
</script>
<input id="flag" value='<s:property value="flag"/>' hidden="true"/>
<input id="id" value='<s:property value="id"/>' hidden="true"/>
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>单位信息</strong></legend>
    <div class="user-query" style="padding-top:0px;">
	<dl>
		<dt style="width:70px;">单位名称：</dt>
		<dt style="width:100px;"><s:property value="com_name"/></dt>
		<dt style="width:70px;">单位类型：</dt>
		<dt style="width:185px;"><s:property value="com_type"/></dt>
		<dt style="width:70px;">经济类型：</dt>
		<dt style="width:220px;"><s:property value="economiccode"/></dt>
		<dt style="width:70px;">企业规模：</dt>
		<dt style="width:100px;"><s:property value="comsizecode"/></dt>
	</dl>
	<dl>
		<dt style="width:70px;">单位地址：</dt>
		<dt style="width:355px;"><s:property value="address"/></dt>
		<dt style="width:70px;">行业类型：</dt>
		<dt style="width:220px;"><s:property value="industrycode"/></dt>
		<dt style="width:70px;">行政区划：</dt>
		<dt style="width:100px;"><s:property value="areacode"/></dt>
	</dl>
	</div>
</fieldset>
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>签单计划</strong></legend>		
	<div class="user-query" style="padding-top:0px;">
		<dl>
			<dt style="width:70px;">签单名称：</dt>
			<dt style="width:239px;" id="ck_sign_name"></dt>
			<dt style="width:70px;">助记码：</dt>
			<dt style="width:165px;" id="ck_sign_pingying"></dt>
			<dt style="width:90px;">签单类型：</dt>
			<dt style="width:100px;" id="ck_sign_type"></dt>
			<dt style="width:70px;">签单编码：</dt>
			<dt id="ck_sign_num"></dt>
		</dl>
		<dl>
			<dt style="width:70px;">签单数量：</dt>
			<dt style="width:74px;" id="ck_sign_count"></dt>
			<dt style="width:70px;">年度：</dt>
			<dt style="width:94px;" id="ck_sign_year"></dt>
			<dt style="width:70px;">新旧分类：</dt>
			<dt style="width:165px;" id="ck_old_new_type"></dt>
			<dt style="width:90px;">客户分类：</dt>
			<dt style="width:100px;" id="ck_customer_type"></dt>
			<dt style="width:90px;">保护开始日期：</dt>
			<dt style="width:125px;" id="ck_protect_date"></dt>
		</dl>
		<dl>
			<dt style="width:70px;">估算人数：</dt>
			<dt style="width:74px;" id="ck_sign_persion"></dt>
			<dt style="width:70px;">估算金额：</dt>
			<dt style="width:94px;" id="ck_sign_amount"></dt>
			<dt style="width:70px;">签单日期：</dt>
			<dt style="width:165px;" id="ck_sign_date"></dt>
			<dt style="width:90px;">体检结束日期：</dt>
			<dt style="width:100px;" id="ck_end_date"></dt>
			<dt style="width:90px;">保护开始日期：</dt>
			<dt style="width:125px;" id="ck_abort_date"></dt>
		</dl>
		<dl>
			<dt style="width:70px;">签单状态：</dt>
			<dt style="width:74px;" id="ck_sign_statuss"></dt>
			<dt style="width:70px;">跟踪进度：</dt>
			<dt style="width:94px;" id="ck_track_progresss"></dt>
			<dt style="width:110px;">跟踪进度变化时间：</dt>
			<dt style="width:125px;" id="ck_track_time"></dt>
			<dt style="width:90px;">申请人：</dt>
			<dt style="width:100px;" id="ck_creater"></dt>
			<dt style="width:70px;">申请时间：</dt>
			<dt style="width:125px;" id="ck_create_time"></dt>
		</dl>
	</div>
</fieldset>
<fieldset style="margin:5px;">
    <legend><strong>单位联系人</strong></legend>
    <table id="ck_contacts_list"></table>
</fieldset>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
