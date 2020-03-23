<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	//$('#start_date').datebox('getValue');
	getdelGrid('','');
});

function chaxundelCard(){
	getdelGrid($('#start_date').datebox('getValue'),$('#end_date').datebox('getValue'));
}

function getdelGrid(star_date,end_date){
	var model = {"star_date":star_date,"end_date":end_date};
	   $("#delcardinfolist").datagrid({
		url: '<%=request.getContextPath()%>/getDeleteCardList.action',
		queryParams: model,
		rownumbers:false,
     pageSize: 15,//每页显示的记录条数，默认为10 
     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
     toolbar: '#toolbar',
     sortName: 'cdate',
	 sortOrder: 'desc',
     height:255,
     columns:[[
              {align:"center",field:"card_num","title":"卡号","width":20},
     		  {align:"center",field:"amount","title":"卡金额(元)","width":15},
     		  {align:"center",field:"card_level","title":"卡类型","width":10},
     		  {align:"center",field:"deadline","title":"有效日期","width":15},
     		  {align:"center",field:"remark","title":"备注","width":20},
     		  {align:"center",field:"creater","title":"删除人","width":15},
     		  {align:"center",field:"create_time","title":"删除时间","width":20}
     		  ]
     		  ],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:false,
	    collapsible:true,
		 pagination: true,
     fitColumns:true
	});
}
</script>
<div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:84px;">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd>删除时间：&nbsp;&nbsp;<input type="text" id="start_date" name="start_date" class="easyui-datebox"/></dd>
					<dd>至：<input type="text" id="end_date" name="end_date" class="easyui-datebox"/></dd>
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:chaxundelCard();">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 </div>
 <div  data-options="region:'center'">
<fieldset style="margin:5px;padding-right:0;height:90%;">
<legend><strong>卡列表</strong></legend>
<table id="delcardinfolist"> 
</table>
</fieldset>
</div>
</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-pl_card').dialog('close')">关闭</a>
	</div>
</div>