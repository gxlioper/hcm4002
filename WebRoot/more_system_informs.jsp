<%@ page contentType="text/html;charset=utf-8" %>
<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$.extend($.fn.validatebox.defaults.rules, {    
    minLength: {    
        validator: function(value, param){    
            return value.length >= param[0];    
        },    
        message: 'Please enter at least {0} characters.'   
    }    
});
$(document).ready(function () { 	
	getinform();
});
 function getinform(){
     $("#inform_list").datagrid({
	 url:'getUserSystemInfromsList.action',
	 dataType: 'json',
     pageSize: 15,//每页显示的记录条数，默认为10 
     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	 columns:[[
	 	    {align:'left',field:'inform_content',title:'通知内容',width:55},
		 	{align:'center',field:'updater',title:'通知人',width:10},
	 	    {align:'center',field:'update_time',title:'通知时间',width:15},
	 	   {align:'center',field:'valid_date',title:'有效时间',width:15}
	 	]],	    	
	 	onLoadSuccess:function(value){ 
    		$("#datatotal").val(value.total);
    	},
    	nowrap:false,
		rownumbers:true,
    	singleSelect:false,
	    collapsible:true,
		pagination: false,
	    fitColumns:true,
	    fit:true,
	    striped:true
});
}
</script>
<div class="easyui-layout" style="height:215px;">
	<div data-options="region:'center'" border="false">
		<table id="inform_list">
      	</table>	
	</div>
</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit_heder').dialog('close')">关闭</a>
	</div>
</div>
      
