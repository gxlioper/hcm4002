<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>收费项目编辑</title>
</head>
<script type="text/javascript">
$(function(){
	getCityInsuranceItem();
	getClinicedItemCity();
	$("#city_item_code,#city_item_name").bind('keypress', function (event) {
        if (event.keyCode == "13") {
        	getCityInsuranceItem();
        }
 	})
	
})
function getCityInsuranceItem(){
	$('#city_item').datagrid({    
	    url:'getCityInsuranceItemCharge.action',
	    queryParams:{
	    	item_name_city:$('#city_item_name').val(),
	    	item_code_city:$('#city_item_code').val()
	    },
	    pageSize:10,
	    pageList:[10,20,30,40,50],//可以设置每页记录条数的列表 
	    fitColumns:true,
	    striped:true,
	    singleSelect:true,
	    pagination:true,
	    columns:[[    
	        {field:'item_code_city',title:'诊疗项目编码',width:50},    
	        {field:'item_name_city',title:'项目名称',width:100},    
	        {field:'price',title:'价格',width:50},
	    ]],
	    
	    onDblClickRow:function(index,row){
	    	clinicCityItem(index);
	    }
	}); 
}

function  getClinicedItemCity(){
	var lastIndex;
 	$('#clinicedListCity').datagrid({
		 url:'getClinicedItemCityCharge.action', 
		 queryParams:{id:$("#item_price_id").val()},
		 fitColumns:true,
		 singleSelect:true,
	     columns:[[    
	    	{field:'a',title:'操作',"formatter":del_Item,width:10},
	        {field:'clinic_item_code',title:'项目编码',width:20},    
	        {field:'item_name_city',title:'项目名称',width:20},    
	        {field:'item_num',title:'项目数量',width:20,editor:{type:'numberbox',options:{precision:0}}},
	        {field:'item_price',title:'项目单价',width:20,editor:{type:'numberbox',options:{precision:2}}}   
	     ]],
	     onClickCell:function(index, field, row){
		    	//if (lastIndex != index){
		    		$('#clinicedListCity').datagrid('endEdit', lastIndex);
					$('#clinicedListCity').datagrid('beginEdit', index);
				//}
				lastIndex = index;
				var editors = $('#clinicedListCity').datagrid('getEditor',{index:index,field:'item_num'});
				$(editors.target).numberbox({onChange:function(){
						$('#clinicedListCity').datagrid('endEdit', index);
					}
				});
				
				var editors1 = $('#clinicedListCity').datagrid('getEditor',{index:index,field:'item_price'});
				$(editors1.target).numberbox({onChange:function(){
						$('#clinicedListCity').datagrid('endEdit', index);
					}
				});
		    }
	}) 
}

function clinicCityItem(index){
	var cityItem = $("#city_item").datagrid('getRows')[index];
	var clinicedItems = $("#clinicedListCity").datagrid('getRows');
	var s = cityItem.item_code_city;
	var f = 0;
	$.each(clinicedItems,function(k,v){
		if(v.clinic_item_code==s){
			f=1;
			$.messager.alert('提示','诊疗项目已在列表中！','error');
		}
	})
	if(f == '0'){
		$("#clinicedListCity").datagrid('appendRow',{
			clinic_item_code:cityItem.item_code_city,
			item_name_city:cityItem.item_name_city,
			item_num:1,
			item_price:cityItem.price
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
	
	var selections = $('#clinicedListCity').datagrid('getSelections');
	var selectRows = [];
	for (var i = 0; i < selections.length; i++) {
		selectRows.push(selections[i]);
	}
	for (var j = 0; j < selectRows.length; j++) {
		var index = $('#clinicedListCity').datagrid('getRowIndex',selectRows[j]);
		//执行删除行
		//selectchargingItem是datagrid的id,index要删除的行
		$('#clinicedListCity').datagrid('deleteRow', index);
	}
}

function saveCityClinic(){
	
	var rows = $("#clinicedListCity").datagrid('getRows');
	var clinicList = new Array();
	for(var i=0;i<rows.length;i++){
		clinicList.push({
			"medical_price_id":$("#item_price_id").val(),//价表id
			"medical_item_code":rows[i].clinic_item_code,
			"item_num":rows[i].item_num,
			"item_price":rows[i].item_price,
			"medical_type":'02'
		});
	}
	$.ajax({
		url:'saveMedicalPriceMedicalItemCharge.action',
		type : 'post',
		data:{
			item_list:JSON.stringify(clinicList),
			medical_type:"02",//1市医保  2省医保
			medical_price_id:$("#item_price_id").val()
		},
		success:function(text){
			$.messager.alert('提示信息',text, 'info');
			$('#clinic_city').dialog('close');
			$('#chargItemList_city').datagrid('reload');
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
	 	<input type="hidden" id="item_price_id" value="<s:property value='id'/>" />
		<legend><strong>信息检索</strong></legend>
		<div class="formDiv"> 
			<dl>
			    <dt style="height:26px;width:100px;line-height:26px;">医保项目编号</dt>
				<dd><input class="textinput"  type="text" id="city_item_code" value="" style="height:20px;width:100px;"/></dd> 
				<dt style="height:26px;width:100px;padding-left:20px;line-height:26px">医保项目名称</dt>
				<dd><input class="textinput"  type="text" id="city_item_name" value="" style="height:20px;width:135px;"/></dd>
				<dd style="height:26px;width:100px;padding-left:20px;"><a href="javascript:getCityInsuranceItem();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
           </dl>
		 </div>
    	</fieldset>
     </div>
	 <div data-options="region:'center',title:'诊疗项目'" border="false">
       		<table id="city_item" class="easyui-datagrid" style="height:315px"></table>
     </div>
     <div data-options="region:'east',title:'已关联诊疗项目'"  border="false"  style="width:50%;" >
    		<table id="clinicedListCity" class="easyui-datagrid"></table>
     </div>
</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
		<a href="javascript:saveCityClinic();" class="easyui-linkbutton c6" style="width: 80px;">保存</a> &nbsp;
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#clinic_city').dialog('close')">关闭</a>
	</div>
</div>
