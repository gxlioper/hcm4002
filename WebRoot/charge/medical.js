$(function(){
	getChargItemList_city()
	getClinicedCity(0)
	getClinicedItemCity(0)
	
})
/**
 * 价表table
 * @returns
 */
function getChargItemList_city(){
	
	var model = {
			"id":$("#item_code_city").val(),
			"item_name":$("#item_name_city").val()
			/*"is_bind_city":$("#is_bind_city").combobox('getValue')*/
	}
	$("#chargItemList_city").datagrid({
		 url:'queryMedicalPriceListCharge.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {field:'ck',checkbox:true },
		    {align:'center',field:'id',title:'ID',width:25,sortable:true},
		 	{align:'center',field:'item_name',title:'项目名称',width:25},
		 	/*{align:'center',field:'input_code',title:'助记码',width:25},*/
		    {align:'center',field:'price',title:'价格',width:15},
		    {align:'center',field:'xg',title:'修改',width:10,"formatter":ff_xg},
		 	//{align:'center',field:'is_bind_city',title:'关联市医保',width:20,sortable:true,"formatter":f_setFintColor},
		 	{align:'center',field:'sss',title:'市医保关联',width:10,"formatter":f_cxg},
		 	{align:'center',field:'syb',title:'省医保关联',width:10,"formatter":f_cxgSHENG}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	onDblClickRow : function(index, row) {	
	    		$('#binded_city').datagrid('reload',{id:row.id});
	            $("#binded_city_shi").datagrid('reload',{id:row.id}); 
			},
			height: window.screen.height-360,
			rownumbers:true,
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    toolbar:toolbar,
		    fit:false,
		    striped:true
	})
}
//=======================================关联医保====================================
/**
 * 市医保
 * @param val
 * @param row
 * @returns
 */
function f_cxg(val,row){
	 
	return '<a href=\"javascript:updateBindCity(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	/* str='<a href="updateChargingItemPageCharge.action?id='+id">修改</a>';
	 return str;*/
}
/**

* @param id
*/
function updateBindCity(id){
	$('#clinic_city').dialog(
			{
				title : '市医保关联',
				center : 'none',
				href : 'getItemClinicCityPageCharge.action?id='+id,
			});
	$('#clinic_city').dialog('open');
}
/**
 * 省医保
 * @param val
 * @param row
 * @returns
 */
function f_cxgSHENG(val,row){
	 
	return '<a href=\"javascript:updateBindCitySHENG(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	/* str='<a href="updateChargingItemPageCharge.action?id='+id">修改</a>';
	 return str;*/
}
function updateBindCitySHENG(id){
	$('#clinic_city').dialog(
			{
				title : '省医保关联',
				center : 'none',
				href : 'getItemClinicCityPageShengCharge.action?id='+id,
			});
	$('#clinic_city').dialog('open');
}
/**
 * ================================================价表statr crud===========================================
 * @param val
 * @param row
 * @returns
 */
function ff_xg(val,row){
	 
	return '<a href=\"javascript:ff_xg_update(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	/* str='<a href="updateChargingItemPageCharge.action?id='+id">修改</a>';
	 return str;*/
}
/**
* 修改价表项目
* @param id
*/
function ff_xg_update(id){
	$('#dlg-custedit').dialog({
				title : '编辑价表项目',
				center : 'none',
				href : 'updatemedicalPageCharge.action?id='+id,
			});
	$("#dlg-custedit").dialog('open');
}
var toolbar = [ {
	text : '新增价表项目',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '编辑价表项目',
			center : 'none',
			href : 'updatemedicalPageCharge.action'
		});
		$("#dlg-custedit").dialog('open');
	}
}, {
	text:'批量删除价表',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var ids = new Array();
    	var checkedItems = $('#chargItemList_city').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids.push(item.id);
	    }); 
	    deluserrow(ids.toString());
    }
} ];
function deluserrow(ids){
	if(ids==""){
		$.messager.alert('提示信息','请选择要删除的价表!',"error")
		return;
	}
	$.messager.confirm('提示信息','是否确定删除此价表',function(r){
	 	if(r){
	 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
				 url : 'deleteMedicalPriceCharge.action',
				data : {ids:ids},
				type : "post",
				success : function(data) {
						$.messager.alert("操作提示",data);
						$("#chargItemList_city").datagrid('reload');
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			 });
	 	 }
	 });
}
/**
 * 身医保
 * @param id
 * @returns
 */
function  getClinicedCity(id){
 	$('#binded_city').datagrid({
		 url:'getDicProvInsuranceClinicItemListCharge.action', 
		 queryParams:{id:id},
		 fitColumns:true,
		 singleSelect:true,
	     columns:[[    
	        {field:'item_code',title:'项目编码',width:20},    
	        {field:'item_name',title:'项目名称',width:30},    
	        {field:'item_num',title:'项目数量',width:20},
	        {field:'price',title:'项目单价',width:20}   
	     ]]
	}) 
}
function  getClinicedItemCity(id){
 	$('#binded_city_shi').datagrid({
		 url:'getClinicedItemCityCharge.action', 
		 queryParams:{id:id},
		 fitColumns:true,
		 singleSelect:true,
	     columns:[[    
	        {field:'clinic_item_code',title:'项目编码',width:20},    
	        {field:'item_name_city',title:'项目名称',width:20},    
	        {field:'item_num',title:'项目数量',width:20},
	        {field:'item_price',title:'项目单价',width:20}   
	     ]]
	}) 
}