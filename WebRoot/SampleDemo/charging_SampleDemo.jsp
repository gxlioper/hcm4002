<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	getxiangmguanxi();
	itemshow();

})
//---------------------------------项目关系维护---------------------

function getxiangmguanxi(){	
	 	
	  /*   var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);*/
	     $("#guanxi_shou").datagrid({
		 url:'getjianyanChagingitem.action',
		 queryParams:{
			 item_name_s:$('#item_s').val()
		 },
		 rownumbers:false,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
	            {align:'center',field:'charging_id',title:'项目ID','hidden':true},	
	            {align:'center',field:'item_code',title:'项目编码',width:20},	
			    {align:'left',field:'item_name',title:'项目名称',width:30},
			 	{align:'center',field:'ss',title:'添加',"formatter":item_add}
			 	/* {align:'center',field:'creater',title:'操作人',width:15},
			 	{align:'center',field:'create_time',title:'修改时间',width:15}, */
		 	]],	    	
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:true,
		    striped:true,
		    singleSelect:true,
		    onDblClickRow:function(index,row){
		    	item_appendRow(row.id,row.item_code,row.item_name);
		    }
	});
}
function item_add(value,row,index){
	return '<a href=\"javascript:item_appendRow(\''+row.id+'\',\''+row.item_code+'\',\''+row.item_name+'\')\">添加</a>';
};
function item_appendRow(id,item_code,item_name){
	if(item_panduan(item_code)){
		alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
		return;
	}
	$("#item_show").datagrid('appendRow',{
		item_code:item_code,
		item_name:item_name,
		charging_id:id
	});
}
function item_panduan(item_code){
	var fla = false;
	var row = $("#item_show").datagrid('getRows');
	for(var i = 0 ; i < row.length ; i ++){
		if(row[i].item_code == item_code){
			fla = true;
			break;
		}
	}
	return fla;
}
//----------------项目列表-------------------
function itemshow(){	
		 	
			  /*   var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);*/
			     $("#item_show").datagrid({
				 url:'getSampleDemoChargingItemList.action',
				 queryParams:{
					 sample_demo_id:$('#g_id').val()
				 },
				 rownumbers:false,
				 columns:[[
					 	{align:'center',field:'ss',title:'删除',width:15,"formatter":shanchu_a},
			            {align:'center',field:'item_code',title:'项目编码',width:15},	
					    {align:'left',field:'item_name',title:'项目名称',width:15}
					 	/* {align:'center',field:'creater',title:'操作人',width:15},
					 	{align:'center',field:'create_time',title:'修改时间',width:15}, */
				 	]],	    	
			    	singleSelect:false,
				    collapsible:true,
					/* pagination: true, */
				    fitColumns:true,
				    fit:true,
				    striped:true,
				    singleSelect:true
			});
}
function shanchu_a(value,row,index){
	return '<a href=\"javascript:shanchuhang(\''+index+'\')\">刪除</a>';
};
function shanchuhang(index){
	 var selections  =$('#item_show').datagrid('getSelections');
	 var selectRows = [];
	 for ( var i= 0; i< selections.length; i++) {
	   selectRows.push(selections[i]);
	 }
	 for(var j =0;j<selectRows.length;j++){
	   var index = $('#item_show').datagrid('getRowIndex',selectRows[j]);
	   $('#item_show').datagrid('deleteRow',index);
	 }
}
//------------------保存关系--------------------
function saveitem(){
	var row = $("#item_show").datagrid('getRows');
	console.log(row);
	$.ajax({
		url:'saveSampleDemoChargingItemPage.action',
		type:'post',
		data:{
			sample_demo_id:$('#g_id').val(),
			charging_id_s:JSON.stringify(row)
		},
		success:function(data){
			$.messager.alert("提示信息",data,"info");
			$('#dlg_show').dialog('close');
		},error:function(){
			$.messager.alert("提示信息","操作失败","error");
		}
	})
}

</script>
<div style="height:90%">
	<fieldset style="float: left;height:95%;width:48%;"   >
		<legend><strong>项目</strong></legend>
			<div>
				&nbsp;&nbsp;<label>项目名称</label>&nbsp;&nbsp;
					<input class="textinput" type='text'  id = "item_s"  onkeyup="getxiangmguanxi();" style="width:150px;height: 26px;">
					<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-search" >查询</a> -->
			</div>
	<!-- <label>项目名称</label><input type="text"  /> -->
	<table id ="guanxi_shou">
	</table>
</fieldset>
 <fieldset style="height:95%;width:45%;float: right; ">
		<legend><strong>已关联项目</strong></legend>
	<table id ="item_show">
	</table>
</fieldset>
</fieldset>
</div>
<input type="hidden"  id = "g_id"  name="g_id"  value='<s:property value="id"/>'/>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:saveitem();" class="easyui-linkbutton" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg_show').dialog('close')">关闭</a>
	</div>
</div>
<div id="dlg_add_show" class="easyui-dialog"  data-options="width:600,height:500,closed: true,cache: false,modal: true,top:50"></div>
