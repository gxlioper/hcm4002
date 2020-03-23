$(document).ready(function () {
	
	$('#item_code').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	getChargItemList_prov();
        }
    });
	$('#item_name').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	getChargItemList_prov();
        }
    });
/*	$('#item_code_city').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	getChargItemList_city();
        }
    });*/
/*	$('#item_name_city').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	getChargItemList_city();
        }
    });*/
	getChargItemList_prov();
	getClinicedProv();
/*	$('#tt').tabs({    
	    onSelect:function(title,index){    
	    	getChargItemList_city(); 
	    	getClinicedCity();
	    }    
	});*/
	
//	$(".panel-body").html("");

/*$("div[class=panel-body]:last").html("医保价表对照");
})*/
})




/*-------------------------------------省医保维护-----------------------------------------------------------------*/

function getChargItemList_prov(){
	
	var model = {
			"item_code":$("#item_code").val(),
			"item_name":$("#item_name").val(),
			"dep_id":$("#c_dep").combobox('getValue')
		/*	"is_bind_prov":$("#is_bind_prov").combobox('getValue')*/
	}
	
	$("#chargItemList").datagrid({
		 url:'getChargItemListForProvCharge.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'item_code',title:'项目编码',width:25,sortable:true},
		 	{align:'center',field:'item_name',title:'项目名称',width:25},
		 	{align:'center',field:'dep_name',title:'科室',width:25},
		    {align:'center',field:'amount',title:'项目金额',width:15},	
		 /*	{align:'center',field:'is_bind_prov',title:'关联省医保',width:20,sortable:true,"formatter":f_setFintColor},*/
		 	{align:'center',field:'sss',title:'关联价表',width:10,"formatter":f_xg}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#binded_prov").datagrid("loadData", { total: 0, rows: [] });
//	    		var rows = $("#chargItemList").datagrid('getRows');
//	    		if(rows.length>0){
//	    			getClinicedProv(rows[0].item_code)
//	    		}
	    		
	    	},
	    	onDblClickRow : function(index, row) {	
	    		getClinicedProv(row.item_code)
			},
			height: window.screen.height-370,
			rownumbers:true,
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:false,
		    striped:true
		    
	})
}

function  getClinicedProv(item_code){
 	$('#binded_prov').datagrid({
		 url:'getChargingItemMedicalPriceCharge.action', 
		 queryParams:{c_charge_item_code:item_code},
		 fitColumns:true,
		 singleSelect:true,
		 pageSize:1000,
	     columns:[[    
	        {field:'id',title:'ID',width:20},    
	        {field:'item_name',title:'名称',width:30},    
	        {field:'item_num',title:'数量',width:10},  
	        {field:'price',title:'项目单价',width:20}   
	     ]],
	     height: window.screen.height-370
	}) 
}

//-------------------------------------修改收费项目页面---------------------
function f_xg(val,row){
	 
	return '<a href=\"javascript:updateBindProv(\''+row.item_code+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	/* str='<a href="updateChargingItemPageCharge.action?id='+id">修改</a>';
	 return str;*/
}
/**
* 修改收费项目
* @param id
*/
function updateBindProv(item_code){
	$('#clinic_prov_v').dialog(
			{
				title : '关联价表',
				center : 'none',
				href : 'getItemClinicProvPageCharge.action?item_code='+item_code,
			});
	$('#clinic_prov_v').dialog('open');
}



/*-------------------------------------市医保维护-----------------------------------------------------------------*/



function  getClinicedCity(item_code){
 	$('#binded_city').datagrid({
		 url:'getClinicedItemCityCharge.action', 
		 queryParams:{item_code:item_code},
		 fitColumns:true,
		 singleSelect:true,
	     columns:[[    
	        {field:'clinic_item_code',title:'项目编码',width:20},    
	        {field:'item_name_city',title:'项目名称',width:30},    
	        {field:'item_num',title:'项目数量',width:20},
	        {field:'item_price',title:'项目单价',width:20}   
	     ]]
	}) 
}

//-------------------------------------修改收费项目页面---------------------
function f_cxg(val,row){
	 
	return '<a href=\"javascript:updateBindCity(\''+row.item_code+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	/* str='<a href="updateChargingItemPageCharge.action?id='+id">修改</a>';
	 return str;*/
}
/**
* 修改收费项目
* @param id
*/
function updateBindCity(item_code){
	$('#clinic_city').dialog(
			{
				title : '省医保诊疗项目',
				center : 'none',
				href : 'getItemClinicCityPageCharge.action?item_code='+item_code,
			});
	$('#clinic_city').dialog('open');
	$('#clinic_city').window('center');
}


function f_setFintColor(val,row){
	
	if(row.is_bind_prov == '否'){
		return '<font color="red">'+row.is_bind_prov+'</font>';
	}
	if(row.is_bind_prov == '是'){
		return row.is_bind_prov;
	}
	if(row.is_bind_city == '否'){
		return '<font color="red">'+row.is_bind_city+'</font>';
	}
	if(row.is_bind_city == '是'){
		return row.is_bind_city;
	}
}



