<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

$(document).ready(function () {
	getSeGridxx();
});

 function getSeGridxx(){
	$("#item_result_xx").datagrid({
		url: 'getAcceptanceItemResult.action',
		queryParams: {'exam_num':$("#exam_num").val()},
		rownumbers:false,
		columns:[[
				  {field:'ck',checkbox:true},
//		          {align:"center",field:"dep","title":"科室","width":10},
		          {align:"center",field:"dep_name","title":"收费项目","width":10},
		          {align:'',field:"item_name","title":"检查项目","width":15},
		          {align:"",field:"exam_result","title":"检查结果","width":45},
//		          {align:'center',field:"exam_status_y","title":"检查状态","width":10},
		          {align:'center',field:"exam_doctor","title":"检查医生","width":10},
		          {align:'center',field:"exam_date","title":"检查时间","width":10}
		          ]],
	    onLoadSuccess:function(value){
	    	//$("#datatotal").val(value.total);
	    	//MergeCells('item_result_xx', 'dep,dep_name,exam_doctor,exam_date,dep,exam_status_y',1);
	    },
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		border:false,
		nowrap:false
	}); 
}
 //确定所选结果
function f_select(){
	var obj = $("#item_result_xx").datagrid('getChecked');
	var result="";
	for(i=0;i<obj.length;i++){
		result+=obj[i].item_name+":"+obj[i].exam_result+"\n\r";
	}
	$(window.parent.$("#exam_result").val(result));
	$('#dlg-xz').dialog('close');
}

</script>

	<div class="easyui-tabs" id="tab_ss" style="height:480px;width:775px;margin-left:5px;">
  			<div title="项目检查结果" style="">
    			<table id="item_result_xx"></table>  
    		</div>
  			   
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;" onclick="javascript:f_select();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;" onclick="javascript:$('#dlg-xz').dialog('close')">关闭</a>
		</div>
	</div>

