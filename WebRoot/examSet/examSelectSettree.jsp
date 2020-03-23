<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!--表格内容溢出---显示省略号样式  -->
<script type="text/javascript">
	showselecttree();
	gettreesetselectGrid();

/**
 * 显示体检套餐
 */
 function gettreesetselectGrid(){	   
	     $("#groupsetitemselectshow").datagrid({
		 url:'querySelectExamSettree.action',
		 queryParams:{
			 set_name:$('#set_name').val(),
			 settreeid:$('#settreeid').val(),
			 sex:$('#selectsetsex').val(),
			 exam_type:$('#selectsetexamtype').val()
		 },
		 rownumbers:false,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {align:'center',field:'set_num',title:'编码',width:25},
	            {align:'center',field:'set_name',title:'套餐名称',width:50},
			 	{align:'center',field:'sex',title:'适用性别',width:20},
			 	{align:'center',field:'price',title:'金额',width:20},
			 	{align:'center',field:'set_amount',title:'折后金额',width:20},
			 	{align:'center',field:'set_discount',title:'折扣',width:10},
			 	{align:'center',field:'update_name',title:'修改人',width:20},
			 	{align:'center',field:'update_time',title:'修改时间',width:40}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	//singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
    	    fitColumns:true,//自适应
	    	//singleSelect:true,
		    //collapsible:false,
    	    rownumbers:true,
			pagination:true,//分页控件
		    striped:true,
		    onDblClickRow:function(index, row){     	
	        	setvalueselectset(row.id,row.set_name);
	        	$('#dlg-selectsetforsexTOG').dialog('close');
	        }
	       
	       // nowrap:false,//内容显示不下换行
	});
}

/**
--------------------------------------------------树-----------------------------

**/
/**
 * 树
 */
function showselecttree(){

	$("#some_tree").tree({
		 url:'getExamSetTree.action',		
		 onClick:function(node){
			 $('#settreeid').val(node.id.split("-")[0]);
        	 var queryParams = $('#groupsetitemselectshow').datagrid('options').queryParams;  
             queryParams.settreeid = $('#settreeid').val();
             queryParams.set_name="";
             queryParams.sex=$('#selectsetsex').val();
             queryParams.exam_type=$('#selectsetexamtype').val();//
             //重新加载datagrid的数据  
             $("#groupsetitemselectshow").datagrid('reload');  
		 }
	});
}

</script>
<input type="hidden"  id='selectsetexamtype'  value = "<s:property value='model.exam_type'/>"/>
<input type="hidden"   id="selectsetsex" value="<s:property value='model.sex'/>" />
<input type="hidden"   id="settreeid" />
<div data-options="title:'套餐树形结构',split:true" style="float:left;width:250px;height:550px">
    		<ul id="some_tree"  class="easyui-tree"></ul>
</div>   
 <div data-options="title:'体检套餐列表'" style="float:center;padding:5px;">
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检套餐查询</strong></legend>
			<div class="user-query" >
				<dl>
					<dt style="width:70px;">套餐名称</dt>
					<dd>
						<input  type="text" class="textinput" id="set_name"  onkeyup="gettreesetselectGrid();" style="height:23px;width:140px;" />
						<a href="javascript:gettreesetselectGrid();" class="easyui-linkbutton c6" style="width:100px;">查询</a>
					</dd>
					
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;" >
<legend><strong>体检套餐列表</strong></legend> 
      <table id="groupsetitemselectshow"></table>	
 </fieldset>
 </div> 