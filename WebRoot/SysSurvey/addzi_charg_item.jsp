<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(document).ready(function(){
	getgroupuserGri();
})
//------------------------------------------推荐子项目---------------------------------
function getgroupuserGri(){
    $("#zi_aj").datagrid({
	 url:'getSysSurveyChargItem.action',
	 queryParams:{
		 ss_code:$('#a_code').val(),	//收费项目编码
		 ss_name:$('#a_name').val()//收费项目名称
	 },
	 type:'post',
	 rownumbers:false,
    pageSize:10,	
   // pageNumber:page,
    pageList:[10,20,30,40,50],//可以设置每页记录条数的列表 
	 columns:[[
           {align:'center',field:'item_code',title:'收费项目编码',width:18},	
           {align:'center',field:'item_name',title:'收费项目名称',width:28},
		 	{align:'center',field:'sss',title:'添加',width:10,formatter:f_zi_tj}
	 	]],	    	
   	onLoadSuccess:function(value){
   		$("#datatotal").val(value.total);
   		$(".loading_div").remove();
   	},
   	//singleSelect:true,
   	//checkOnSelect:false,
   	//selectOnCheck:false,
   	singleSelect:false,
	    collapsible:false,
		pagination:true,//分页控件
	    fitColumns:true,//自适应
	    striped:true,
       singleSelect:true//只允许选择一行
	});
}
function f_zi_tj(value,row,index){	
	return '<a href=\"javascript:add_zi_f_tj(\''+index+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"添加\" /></a>';
}
/**
 * 推荐项目添加行
 */
function add_zi_f_tj(index){
	var rows = $("#zi_aj").datagrid("getRows");
	var sr = " <tr style='background: #d9d9d9'> "
				+"<td  style='width:120px;text-align: center;' ><a  onclick='tujjian_tr(this);' >删除</a></td>"
				+"<td  style='width:100px'><input type='text'  value='"+xxbm+"'  style='width:100px'/></td>"
				+"<td  style='width:100px'>"+rows[index].item_code+"</td>"
				+"<td  style='width:200px'>"+rows[index].item_name+"</td>"
			 +" </tr> ";
	$(zi_xiangmu_list).append(sr);
	
	var w=window.innerWidth
	|| document.documentElement.clientWidth
	|| document.body.clientWidth;

	var h=window.innerHeight
	|| document.documentElement.clientHeight
	|| document.body.clientHeight;
	
	$.messager.show({
		title:'我的消息',
		msg:'项目已添加成功！', 
		showType:'show',
		style:{
			right:'',
			timeout:10000,
			top:h*0.3,
		}
	});
}
/**
 * 推荐项目删除行
 */
function tujjian_tr(obj){
	var tr = $(obj).parent().parent();
	$(tr).remove();
}

</script>
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>收费项目查询</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="width:70px;">项目编码</dt>
					<dd><input class="textinput"  type="text" id="a_code"  style="height:23px;width:140px;"
					class="easyui-validatebox" /></dd>
					<dt style="width: 70px">项目名称</dt>
					<dd><input class="textinput"  type="text" id="a_name"   style="height:23px;width:140px;"/></dd>
					<dd><a href="javascript:getgroupuserGri();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;height:30px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>收费项目列表</strong></legend> 
      <table id="zi_aj"  style="height:340px">
      </table>	
</fieldset>
