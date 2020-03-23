<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	itemshow();
})
//----------------项目列表-------------------
function itemshow(){	
			     $("#item_show").datagrid({
				 url:'getSampleDemoListShow.action',
				 queryParams:{
					 charging_id:$('#g_id').val()
				 },
				 rownumbers:false,
				 columns:[[
			            {align:'center',field:'demo_num',title:'样本编码',width:15},	
					    {align:'center',field:'demo_name',title:'样本名称',width:15}
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
</script>
<div style="height:99%">
	<table id ="item_show"></table>
</div>
<input type="hidden"  id = "g_id"  name="g_id"  value='<s:property value="id"/>'/>
