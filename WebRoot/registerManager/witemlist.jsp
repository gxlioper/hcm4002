<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

$(document).ready(function () {
	getwitemGrid();
});
function getwitemGrid(){
	 var model={"exam_num":$("#ser_num").val()};
    $("#witemlist").datagrid({
	 url:'getwitemList.action',
	 dataType: 'json',
	 queryParams:model,
	 toolbar:'#toolbar',
	 rownumbers:true,
    pageSize: 15,//每页显示的记录条数，默认为10 
    pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
    height:200,
	 columns:[[
	    {align:'center',field:'ck',checkbox:true},
	    {align:'center',field:'item_code',title:'项目编码',width:10},
	 	{align:'center',field:'item_name',title:'项目名称',width:15},
	 	{align:'center',field:'dep_name',title:'科室名称',width:10},
	 	{align:'center',field:'exam_status',title:'检查状态',width:10},
	 	{align:'center',field:'item_amount',title:'金额(元)',width:10},
	 	{align:'center',field:'discount',title:'折扣',width:10},
	 	{align:'center',field:'amount',title:'折后金额(元)',width:15},
	 	{align:'center',field:'personal_pay',title:'个人付费金额(元)',width:20},
	 	{align:'center',field:'team_pay',title:'单位付费金额(元)',width:20},
	 	{align:'center',field:'creater',title:'登记人',width:10}	,
	 	{align:'center',field:'create_time',title:'登记时间',width:15}	
	 	]],	    	
   	onLoadSuccess:function(value){
   		$("#datatotal").val(value.total);
   	},
   collapsible:true,
	pagination: false,
   fitColumns:true,
   striped:true
});
}
</script>

<table id="witemlist" class="easyui-datagrid" > 
</table>