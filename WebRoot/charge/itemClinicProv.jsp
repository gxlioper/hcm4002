<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>价表</title>
</head>
<script type="text/javascript">
$(function(){
	getProvInsuranceItem();
	getClinicedItemProv();
	$("#item_code_prov,#item_name_prov").bind('keypress', function (event) {
        if (event.keyCode == "13") {
        	getProvInsuranceItem();
        }
 	})
})
function getProvInsuranceItem(){
	$('#prov_item').datagrid({    
	    url:'queryMedicalPriceListCharge.action',
	    queryParams:{
	    	item_name:$('#item_name_prov').val(),
	    	id:$('#item_code_prov').val()
	    },
	    pageSize:10,
	    pageList:[10,20,30,40,50],//可以设置每页记录条数的列表 
	    fitColumns:true,
	    striped:true,
	    singleSelect:true,
	    pagination:true,
	    columns:[[    
	        {field:'id',title:'ID',width:50},    
	        {field:'item_name',title:'项目名称',width:100},    
	        {field:'price',title:'价格',width:50}
	    ]],
	    
	    onDblClickRow:function(index,row){
	    	clinicProvItem(index);
	    },
	    rownumbers:true,
    	singleSelect:true,
	    collapsible:true,
		pagination: true,
	    fitColumns:true,
	    fit:false,
	    striped:true
	}); 
}


function  getClinicedItemProv(){
	var lastIndex;
 	$('#clinicedListProv').datagrid({
		 url:'getChargingItemMedicalPriceCharge.action', 
		 queryParams:{c_charge_item_code:$("#item_code_provs").val()},
		 fitColumns:true,
		 singleSelect:true,
	     columns:[[    
	    	{field:'a',title:'操作',"formatter":del_Item,width:10},
	        {field:'id',title:'ID',width:20},    
	        {field:'item_name',title:'项目名称',width:20},  
	        {field:'item_num',title:'数量',width:20,editor:{type:'numberbox',options:{precision:0}}}, 
	        {field:'price',title:'单价',width:20/* ,editor:{type:'numberbox',options:{precision:2}}  */}   
	     ]],
	     onClickCell:function(index, field, row){
		    	//if (lastIndex != index){
		    		$('#clinicedListProv').datagrid('endEdit', lastIndex);
					$('#clinicedListProv').datagrid('beginEdit', index);
				//}
				lastIndex = index;
				var editors = $('#clinicedListProv').datagrid('getEditor',{index:index,field:'item_num'});
				$(editors.target).numberbox({onChange:function(){
						$('#clinicedListProv').datagrid('endEdit', index);
					}
				});
				
			/* 	var editors1 = $('#clinicedListProv').datagrid('getEditor',{index:index,field:'price'});
				$(editors1.target).numberbox({onChange:function(){
						$('#clinicedListProv').datagrid('endEdit', index);
					}
				}); */
		    }
	}) 
}

function clinicProvItem(index){
	var provItem = $("#prov_item").datagrid('getRows')[index];
	var clinicedItems = $("#clinicedListProv").datagrid('getRows');
	var s = provItem.id;
	var f = 0;
	$.each(clinicedItems,function(k,v){
		if(v.id==s){
			f=1;
			$.messager.alert('提示','价表项目已在列表中！','error');
		}
	})
	if(f == '0'){
		$("#clinicedListProv").datagrid('appendRow',{
			id:provItem.id,
			item_name:provItem.item_name,
			input_code:provItem.input_code,
			item_num:1,
			price:provItem.price
		});
	}
}

//删除已选择检查项目
function del_Item(value, row, index) {
	return '<a href=\"javascript:del_dTem(\'' + index + '\')\">删除</a>';

}

function del_dTem(index){
	/* $("#clinicedList").datagrid('deleteRow',index);
	$("#clinicedList").datagrid('loadDate'); */
	
	var selections = $('#clinicedListProv').datagrid('getSelections');
	var selectRows = [];
	for (var i = 0; i < selections.length; i++) {
		selectRows.push(selections[i]);
	}
	for (var j = 0; j < selectRows.length; j++) {
		var index = $('#clinicedListProv').datagrid('getRowIndex',selectRows[j]);
		//执行删除行
		//selectchargingItem是datagrid的id,index要删除的行
		$('#clinicedListProv').datagrid('deleteRow', index);
	}
}

function saveProvClinic(){
	var rows = $("#clinicedListProv").datagrid('getRows');
	console.log(rows);
	var clinicList = new Array();
	var item_code = $("#item_code_provs").val();
	for(var i=0;i<rows.length;i++){
//		alert(rows[i].item_price);
		clinicList.push({
			"peis_item_code":$("#item_code_provs").val(),
			"medical_price_id":rows[i].id,
			"input_code":rows[i].input_code,
			"item_num":rows[i].item_num,
			"price":rows[i].price
		});
	}
	$.ajax({
		url:'saveChargingItemMedicalPriceCharge.action',
		type : 'post',
		data:{
			item_list:JSON.stringify(clinicList),
			c_charge_item_code:$("#item_code_provs").val()
		},
		success:function(text){
			if(text.split("-")[0]=='error'){
				$.messager.alert("提示信息",text.split("-")[1],"error");
			} else {
				$.messager.alert("提示信息",text.split("-")[1],"info");
				$('#clinic_prov').dialog('close');
				$('#chargItemList').datagrid('reload');
			}
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}	
	})
}


</script>

<div class="easyui-layout" border="false" fit="true">
	<div data-options="region:'north'" style="height:70px;">
	 	<fieldset>
	 	<input type="hidden" id="item_code_provs" value="<s:property value='model.item_code'/>" />
		<legend><strong>信息检索</strong></legend>
		<div class="formDiv"> 
			<dl>
			    <dt style="height:26px;width:100px;line-height:26px;">价表项目编码</dt>
				<dd><input class="textinput"  type="text" id="item_code_prov" value="" style="height:20px;width:100px;"/></dd> 
				<dt style="height:26px;width:100px;padding-left:20px;line-height:26px">价表项目编名称</dt>
				<dd><input class="textinput"  type="text" id="item_name_prov" value="" style="height:20px;width:135px;"/></dd>
				<dd style="height:26px;width:100px;padding-left:20px;"><a href="javascript:getProvInsuranceItem();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
           </dl>
		 </div>
    	</fieldset>
     </div>
	 <div data-options="region:'center',title:'价表'" border="false">
       		<table id="prov_item" class="easyui-datagrid" style="height:315px"></table>
     </div>
     <div data-options="region:'east',title:'已关联价表'"  style="width:50%;" >
    		<table id="clinicedListProv" class="easyui-datagrid"></table>
     </div>
</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
		<a href="javascript:saveProvClinic();" class="easyui-linkbutton c6" style="width: 80px;">保存</a> &nbsp;
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#clinic_prov_v').dialog('close')">关闭</a>
	</div>
</div>
