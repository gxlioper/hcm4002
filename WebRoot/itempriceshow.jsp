<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript">
$(document).ready(function () { 	
	//ietmlist();
	pricelist();
});
 
 
 //function ietmlist(){
//	     $("#ietmlist").datagrid({
//		 url:'itemtotal.action',
//		 dataType: 'json',
//		 pageSize: 15,//每页显示的记录条数，默认为10 
//	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
//		 columns:[[
//		 	{align:'center',field:'item_class',title:'项目分类',width:5},
//		 	{align:'center',field:'item_code',title:'诊疗项目代码',width:15},
//		 	{align:'left',field:'item_name',title:'诊疗项目名称',width:35},
//		 	{align:'center',field:'update_date',title:'更新日期',width:15}
//		 	]],	    	
//	    	onLoadSuccess:function(value){
//	    		$("#datatotal").val(value.total);
//	    	},
//	    	nowrap:false,
//			rownumbers:false,
//	    	singleSelect:false,
//		    collapsible:true,
//			pagination: true,
//		    fitColumns:true,
//		    striped:true
//	});
//		}
 
 function pricelist(){
     $("#pricelist").datagrid({
	 url:'pricetotal.action',
	 dataType: 'json',
     pageSize: 15,//每页显示的记录条数，默认为10 
     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	 columns:[[
	 	    {align:'center',field:'item_class',title:'项目分类',width:10},
		 	{align:'center',field:'item_code',title:'项目代码',width:15},
		 	{align:'left',field:'item_name',title:'项目名称',width:35},
		 	{align:'right',field:'item_spec',title:'项目规格',width:10},
		 	{align:'right',field:'units',title:'项目单位',width:10},
		 	{align:'right',field:'price',title:'价格',width:10},
		 	{align:'center',field:'start_date',title:'起始日期',width:25},
		 	{align:'center',field:'stop_date',title:'结束日期',width:25},
		 	{align:'center',field:'create_date',title:'更新日期',width:25},
		 	{align:'center',field:'typess',title:'同步类型',width:15},
		 	{align:'left',field:'memo',title:'说明',width:55}
	 	]],	    	
	 	onLoadSuccess:function(value){ 
    		$("#datatotal").val(value.total);
    	},
    	nowrap:false,
		rownumbers:false,
    	singleSelect:false,
	    collapsible:true,
		pagination: true,
	    fitColumns:true,
	    striped:true
});
}
</script>
<!-- fieldset style="margin:5px;padding-right:0;">
<legend><strong>诊疗项目变化列表</strong></legend>
      <table id="ietmlist">
      </table>	
 </fieldset-->
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>价表变化列表</strong></legend>
      <table id="pricelist">
      </table>	
 </fieldset>