<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function() {
	gethisLadSamplelist();
});
function gethisLadSamplelist(){
	$("#caiyangjieguo").datagrid({
		url: 'getLisItemList.action',
		queryParams: {'exam_num':$("#sample_exam_num").val(),
					  's_join_date':$("#his_date_s").val(),
					  'e_join_date':$("#his_date_e").val()
		},
		rownumbers:false,
		columns:[[
		          {align:"center",field:"dep_name","title":"收费项目","width":10},
		          {align:"center",field:"item_name","title":"检查项目","width":10,"styler":fn_clolor},
		          {align:"center",field:"exam_result","title":"检查结果","width":15,"styler":fn_clolor},
		          {align:'center',field:"exam_date","title":"检查时间","width":15},
		          {align:"center",field:"exam_doctor","title":"检查医生","width":10},
		          {align:"center",field:"read_flag","title":"状态","width":10,"styler":fn_clolor,"formatter":f_status},
		          {align:"center",field:"note","title":"处理信息","width":10,"styler":fn_clolor}
		         
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	MergeCells1('caiyangjieguo', 'dep_name,exam_doctor,exam_date');
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		border:false,
		nowrap:false
	});
}
function chaxunhisresult(){
	$("#caiyangjieguo").datagrid('load',{'exam_num':$("#examinfo_num").val(),
		  's_join_date':$("#his_date_s").datebox('getValue'),
		  'e_join_date':$("#his_date_e").datebox('getValue')});
}
function fn_lis(val,row){
	return '<a href=\"javascript:fn_lisck(\''+row.TESTNO+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-info\" alt=\"查看LIS检查结果\" /></a>';
}
function fn_lisck(testno){
	result_testno = testno;
	$('#dlg-lisresult').dialog({    
	    title: '查看LIS检查结果',    
	    width: 1200,    
	    height: 500,    
	    closed: true,
	    cache: false,
	    modal: true,    
	    href: 'getLabResultInfoPage.action?exam_num='+testno,    
	});    
	$('#dlg-lisresult').dialog('open');
	$('#dlg-lisresult').dialog('center');
}
function MergeCells1(tableID, fldList) {
            var Arr = fldList.split(",");
             var dg = $('#' + tableID);
            var fldName;
             var RowCount = dg.datagrid("getRows").length;
            var span;
            var PerValue = "";
            var CurValue = "";
             var length = Arr.length - 1;
             for (i = length; i >= 0; i--) {
               fldName = Arr[i];
                PerValue = "";
                span = 1;
                for (row = 0; row <= RowCount; row++) {
                    if (row == RowCount) {
                        CurValue = "";
                    }
                    else {
                        CurValue = dg.datagrid("getRows")[row][fldName];
                    }
                     if (PerValue == CurValue) {
                        span += 1;
                    }
                     else {
                        var index = row - span;
                         dg.datagrid('mergeCells', {
                            index: index,
                             field: fldName,
                             rowspan: span,
                             colspan: null
                         });
                         span = 1;
                         PerValue = CurValue;
                     }
                 }
             }
         }
function f_status(val, row){
	if(row.read_flag == '1'){
		return '已读取';
	}else if(row.read_flag == '0'){
		return '未读取';
	}else if(row.read_flag == '2'){
		return '无匹配项目';
	}else{
		return '其他错误';
	}
}
function fn_clolor(val, row){
	if(row.read_flag == '0'){
		return 'color:green;';
	}else if(row.read_flag == '2'){
		return 'color:red;';
	}else if(row.read_flag == '3'){
		return 'color:red;';
	}
}
</script>
<input type="hidden" id="sample_exam_num" value="<s:property value="model.exam_num"/>"/>
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
			<strong>查询采样结果列表</strong>
		</legend>
		<table id="caiyangjieguo"></table>		
	</fieldset>

<div class="dialog-button-box">
	<div class="inner-button-box">
		<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-hisresult').dialog('close')">关闭</a>
	</div>
</div>
<div id="dlg-lisresult"></div>

