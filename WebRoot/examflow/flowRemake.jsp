<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
</style>
<script type="text/javascript">
/* var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>';  */
$(function(){
})
/**
 * 显示流程列表
 */
 function getFlowRemake(){
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#examremake").datagrid({
		 url:'queryFlowRemak.action',
		 queryParams:{
			exam_num:$('#f_exam_num').val(),
			pageSize:1000
		 },
		// toolbar:toolbar,
		 rownumbers:false,
		  height:390,
	     pageSize:1000,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
	     rownumbers:true,
		 columns:[[
		        /* {field:'ck',checkbox:true}, */
		   /*      {align:'center',field:'exam_num',title:'体检号',width:18}, */
	            {align:'left',field:'process_name',title:'科室',width:28},
			 	{align:'left',field:'remark',title:'备注',width:100},
			 	{align:'center',field:'chi_name',title:'操作人',width:18},
			 	{align:'center',field:'remark_time',title:'创建时间',width:18}
			 	/* {align:'center',field:'set_discount',title:'折扣',width:18},
			 	{align:'center',field:'creater_name',title:'创建人',width:15},
			 	{align:'center',field:'create_time',title:'创建时间',width:20},
			 	{align:'center',field:'update_name',title:'修改人',width:15},
			 	{align:'center',field:'update_time',title:'修改时间',width:20},
			 	{align:"center",field:"is_Active","title":"启(停)修改","width":18,"formatter":f_qt},
			 	{align:'center',field:'sss',title:'修改',width:10,"formatter":f_xg},
			 	{align:'center',field:'ss',width:10,title:'删除',"formatter":f_sc} */
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	//singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
    	    fitColumns:true,//自适应
	    	//singleSelect:true,
		    //collapsible:false,
			//pagination:true,//分页控件
		    striped:true
	       
	});
}
function xbeizhu(title){
	if(title=="所有备注"){
		getFlowRemake();
	}
}
function saveremake(){
	if($("#a_remake").val() != "" && $("#a_remake").val() != null ){
		var model = {
			exam_num:$("#f_exam_num").val(),
			remark:$('#a_remake').val(),
			id:$('#f_id').val(),
			process:$('#f_process').val()
		}
			$.ajax({
			url:'saveFlowRemak.action',
			type:'post',
			data:model,
			success:function(data){
				$.messager.alert("提示信息","备注已保存","info");
				$('#dlg-beizhu').dialog('close')
			},error:function(){
				$.messager.alert("提示信息","操作失败","error");
			}
		}) 
	}else{
		$.messager.alert("提示信息","信息无效","error");
	}
	
	
}
</script>
<input type="hidden"   id='f_process'  value='<s:property value='process'  />'  />
<input type="hidden"   id='f_id'  value='<s:property value='id'  />'  />
<input type="hidden"   id='f_exam_num'  value='<s:property value='exam_num'  />'  />
<input type="hidden"   id='tjhname'  value='<s:text name="tjhname"  />'  />
<input type="hidden"   id='dahname' value='<s:text name="dahname"  />'  />
<div id="tt" class="easyui-tabs"     data-options="fit:true,onSelect:function(title){xbeizhu(title)}">   
    <div title="编辑备注" style="padding:20px;padding-top:50px;"    >   
    			备注&nbsp;&nbsp;&nbsp;
    			<input type="text" id="a_remake" class="textinput"   value="<s:property value='remark'/>" style="height: 21px;width:650px;"   />
    </div>   
    <div title="所有备注"  style="overflow:auto;">   
        	<div style="margin-bottom:30px;">
				<table id="examremake"></table>
			</div>    
    </div>   
</div>    

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:saveremake();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-beizhu').dialog('close')">关闭</a>
	</div>
</div>
	
