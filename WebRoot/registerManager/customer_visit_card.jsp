<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
<script type="text/javascript">
$(document).ready(function() {
	jiuzhenkadatagrid();
})

 function jiuzhenkadatagrid(){
    var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str); 
     $("#shou_customer_visit_card").datagrid({
		 url:'getjiuzhenkaList.action',
		 queryParams:{
			customer_id:$('#cc_cid').val()
	 	 },
		 rownumbers:false,
	     toolbar:[ {
	    		text : '新增',
	    		iconCls : 'icon-add',
	    		width : 120,
	    		handler : function() {
	    			$("#dlg-jiuizhengka_add").dialog({
	    				title : '新增就诊卡',
	    				href :'getaddjiuzhenkapage.action?customer_id='+$('#cc_cid').val()
	    			});
	    			$("#dlg-jiuizhengka_add").dialog('open');
	    		}
	    	}],
	    // pageSize:15,	     
	    // pageNumber:page,
	    // pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	            {align:'center',field:'visit_no',title:'就诊卡号',width:20},
			 	{align:'center',field:'chi_name',title:'修改人',width:15},
			 	{align:'center',field:'update_time',title:'修改时间',width:20},
			 	{align:'center',field:'sc',title:'删除',width:10,"formatter":j_sc},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$(".loading_div").remove();
	    	},
	    	//singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
	    	singleSelect:false,
		    collapsible:false,
			pagination:true,
		    fitColumns:true,//自适应
		    fit:true,
		    striped:true,
	        singleSelect:true
	      
	});
}
function j_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 单个删除收费项目
 */
function f_deluser(id) {
	$.messager.confirm("提示信息","是否删除收费项目？",function(r){
		if(r){
			$.ajax({
				url : 'deletejiuzhenka.action?id='+id,
				type : 'post',
				success : function(data) {
					$('#shou_customer_visit_card').datagrid("reload");    
					$.messager.alert('提示信息', data);
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	})
}	

</script>
	<input type="hidden"  id="cc_cid"   value="<s:property value='customer_id'/>"  />
	<table id = "shou_customer_visit_card"   ></table>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-jiuizhengka').dialog('close')">关闭</a>
	</div>
</div>
 
