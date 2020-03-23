<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
$(document).ready(function () {  
	getCompanyInfo();
})
 function getCompanyInfo(){
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 //$("body").prepend(str);
	     $("#compan_show").datagrid({
		 url:'getCompanyInfoList.action',
		 queryParams:{
			 com_Name:$('#c_com_name').val()
	 	 },
	 	 type:'post',
		 rownumbers:false,
		 height:380,
	     pageSize:10,	     
	    // pageNumber:page,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	            {align:'center',field:'com_name',title:'单位名称',width:18},	
			 	{align:'center',field:'ss',title:'提取单位',width:10,"formatter":f_sc}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    	//	$("#datatotal").val(value.total);
	    	//	$(".loading_div").remove();
	    	},
	    	//singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
	    	singleSelect:false,
		    collapsible:false,
			pagination:true,//分页控件
		    fitColumns:true,//自适应
		    //fit:true,
		    striped:true,
	        singleSelect:false,//只允许选择一行
	        onDblClickRow:function(index, row){
	        }
	});
}
function f_sc(val,row) {
	console.log(row);
	return '<a href=\"javascript:tq(\''+row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-add\" alt=\"提取\" /></a>';
}
function tq(id){
	$.ajax({
		url:'extractCompanInfo.action',
		type:'post',
		data:{
			id:id
		},
		success:function(msg){
			if(msg == 'ok'){
				$.messager.alert("提示信息","提取成功","");
				parent.reopen();
			} else {
				$.messager.alert("提示信息",msg,"error");
			}
		},
		error:function(){
			$.messager.alert("提示信息","操作失败","");
		}
		
	})
	//$('#com_Name').textbox('setValue',row.com_name)
	
}

</script>

<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>单位</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="height:26px;width:100px;">单位名称</dt>
					<dd style="height:26px;width:140px;"><input id = "c_com_name" type="text" class="textinput"  /></dd>
					<dd><a href="javascript:getCompanyInfo();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:80px;height:25px;">查询</a></dd>
				</dl>
			</div>	
			<table id="compan_show" class="easyui-datagrid"></table>
 </fieldset>
 


