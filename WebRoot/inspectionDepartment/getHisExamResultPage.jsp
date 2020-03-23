<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function() {
	gethisexamresultlist();
});
function chaxunhisresult(){
	$("#coustomerDingweilist").datagrid('load',{'exam_num':$("#examinfo_num").val(),
		  's_join_date':$("#his_date_s").datebox('getValue'),
		  'e_join_date':$("#his_date_e").datebox('getValue')});
}
function gethisexamresultlist(){
	$("#coustomerDingweilist").datagrid({
		url: 'getPacsExamResultList.action',
		queryParams: {'exam_num':$("#examinfo_num").val(),
					  's_join_date':$("#his_date_s").val(),
					  'e_join_date':$("#his_date_e").val()},
		rownumbers:false,
		columns:[[
		          {align:'center',field:"item_name","title":"项目名称","width":110},
		          {align:'center',field:"pacs_item_code","title":"PACS编码","width":110},
		          {align:'center',field:"notices","title":"检查描述","width":330},
		          {align:'center',field:"clinic_diagnose","title":"检查结果","width":230},
		          {align:'center',field:"check_doc","title":"检查医生","width":110},
		          {align:'center',field:"check_date","title":"检查时间","width":115},
		          {align:'center',field:"audit_doc","title":"审核医生","width":110},
		          {align:'center',field:"audit_date","title":"审核时间","width":115},
		          {align:'center',field:"status","title":"状态","width":110,"formatter":f_status},
		          {align:'center',field:"trans_date","title":"读取时间","width":115},
		          {align:'center',field:"note","title":"处理信息","width":110}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: false,
		fitColumns:false,
		fit:true,
		border:false,
		nowrap:false,
		rowStyler:function(index,row){
			if(row.status == '0'){
				return 'color:green;';
			}else if(row.status == '2'){
				return 'color:red;';
			}else if(row.status == '3'){
				return 'color:red;';
			}
		}
	});
}
function f_status(val, row){
	if(row.status == '1'){
		return '已读取';
	}else if(row.status == '0'){
		return '未读取';
	}else if(row.status == '2'){
		return '无匹配项目';
	}else{
		return '其他错误';
	}
}
</script>
	<fieldset style="margin:5px;padding-right:0;">
	<legend><strong>信息检索</strong></legend>
				<div class="user-query">
					<dl>
						<dt style="height:26px; width:60px;">体检日期<input type="hidden" id="examinfo_num" value="<s:property value="model.exam_num"/>"></dt>
						<dd><input class="easyui-datebox" id="his_date_s" value="<s:property value="model.s_join_date"/>" style="width:100px;height:26px;"/></dd>
	                     <dt style="height:26px;width:30px;">至</dt>
	                     <dd><input class="easyui-datebox" id="his_date_e" value="<s:property value="model.e_join_date"/>" style="width:100px;height:26px;"/></dd>
	                   	<dd><a href="javascript:chaxunhisresult();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
					</dl>
				</div>
	 </fieldset>
	 
	<fieldset style="margin: 5px; padding-right: 0;height:325px;">
		<legend>
			<strong>查询结果列表</strong>
		</legend>
		<div id="coustomerDingweilist"></div>		
	</fieldset>

<div class="dialog-button-box">
	<div class="inner-button-box">
		<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-hisresult').dialog('close')">关闭</a>
	</div>
</div>

