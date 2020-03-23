<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	getgroupuserGrid();
	yixuanxiangmu();
})
//-------------------------------显示检查项目列表------------------------------------
/**
 * 显示检查项目列表
 */
var mccf="";
var itemwblx="";
 function getgroupuserGrid(){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#xiangmuliebiao").datagrid({
		 url:'queryExaminationItem.action',
		 queryParams:{
			/*  "exam_num":$('#c_exam_num').val(),
			 "view_num":$('#c_view_num').val(), */
			 "item_num":$('#ad_num').val(),
			 "item_name":$('#ad_demo_name').val(),
		 },
		 rownumbers:false,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
	            {align:'center',field:'item_num',title:'项目编码',width:30},	
			    {align:'left',field:'item_name',title:'项目名称',width:20,},
			 	{align:'center',field:'seq_code',title:'顺序码',width:15}
			 
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:true,
		    striped:true,
	        onDblClickRow:function(index, row){
	        	addrow(row);
	        }
	});
}
 //----------------------添加行---------------------------
 function addrow(row){
	 var chongtu = 1;
	 var yxrow = $("#yixuanxiangmu").datagrid('getRows');
	 for(var i = 0 ; i < yxrow.length ; i++){
		 if(yxrow[i].item_num==row.item_num){
			 chongtu='2';
			 break;
		 }
	 }
	 if(chongtu=='2'){
		$.messager.alert("警告信息","项目冲突","error");
		 return;
	 } else {
		 var charging_item_id = $('#charging_item_id').val();
		 var item_class_id = $('#item_class_id').val();
		 if(item_class_name!="" && $('#add_item_class_id').val()>0){
			 row.item_class_name =item_class_name;
			 row.item_class_id = $('#add_item_class_id').val();
		 }
		 $("#yixuanxiangmu").datagrid('appendRow',row);
	 }
	 
 }
 //------------删除行--------------
 function scrow(value,row,index){
	 return '<a href=\"javascript:deleterow(\''+index+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
 }
 function deleterow(index){
	 var selections  =$('#yixuanxiangmu').datagrid('getSelections');
	 var selectRows = [];
	 for ( var i= 0; i< selections.length; i++) {
	   selectRows.push(selections[i]);
	 }
	 for(var j =0;j<selectRows.length;j++){
	   var index = $('#yixuanxiangmu').datagrid('getRowIndex',selectRows[j]);
	   $('#yixuanxiangmu').datagrid('deleteRow',index);
	 }

 }
 var editIndex = undefined;
 var ind = "";
 function yixuanxiangmu(){	
     $("#yixuanxiangmu").datagrid({
   	 url:'queryExaminationItem.action?page=1&rows=1000',
   	 queryParams:{
		 "charging_item_id":$('#add_charging_item_id').val(),
		 "item_class_id":$('#add_item_class_id').val(),
		 "Remark":"XJCXM"
	 },
	 rownumbers:false,
     pageSize:15,//
     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
	 columns:[[
           	/*{field:'ck',checkbox:true },*/
		 	{align:'center',field:'id',title:'id',width:15},
		 	{align:'center',field:'item_class_id',title:'类别id',width:15},
		 	{align:'center',field:'sc',title:'删除',width:15,'formatter':scrow},
            {align:'center',field:'item_num',title:'项目编码',width:30},	
		    {align:'left',field:'item_name',title:'项目名称',width:20,},
		    {align:'center',field:'item_class_name',title:'类别',width:25,
		    	editor:{
					type:'combobox',
					options:{
						valueField:'id',
						textField:'text',
						method:'get',
						panelHeight:'200',
						url:'getdepExaminationItemList.action?cen=2&id='+$('#add_charging_item_id').val()
					}
				}
  		    }
		 	/* {align:'center',field:'sex',title:'适用性别',width:15} */
		 
	 	]],	  
	 	onLoadSuccess:function(value){
	 		$("#yixuanxiangmu").datagrid('hideColumn','id');
	 		$("#yixuanxiangmu").datagrid('hideColumn','item_class_id'); 
    		$(".loading_div").remove();
    	},
    	singleSelect:true,
	    collapsible:true,
	    fitColumns:true,
	    fit:true,
	    striped:true,
	    onDblClickRow:function(index){
	    	ind = index;
	    	if (editIndex != index){
				if (endEditing()){
					$('#yixuanxiangmu').datagrid('selectRow', index).datagrid('beginEdit', index);
					editIndex = index;
				} else {
					$('#yixuanxiangmu').datagrid('selectRow', editIndex);
				}
			}
	    }
});
}
function zuihoubaocun(){
	var ed = $('#yixuanxiangmu').datagrid('getEditor', {index:editIndex,field:'item_class_name'});
	if(ed==null || ed==""){
		return;
	}
	var text = $(ed.target).combobox('getText');
	var item_class_id = $(ed.target).combobox('getValue');
	var row = $('#yixuanxiangmu').datagrid('getRows');
	for(var i = 0 ; i < row.length ; i++){
		var ed = $('#yixuanxiangmu').datagrid('getEditor', {index:i,field:'item_class_name'});
		if(ed==null || ed==""){
			continue;
		}
		var text = $(ed.target).combobox('getText');
		var item_class_id = $(ed.target).combobox('getValue');
		if(text==item_class_id){
			
		} else {
			$('#yixuanxiangmu').datagrid('getRows')[i]['item_class_name'] = text;
			$('#yixuanxiangmu').datagrid('getRows')[i]['item_class_id'] = item_class_id;
		}
		 $('#yixuanxiangmu').datagrid('refreshRow',i);
	    $("#yixuanxiangmu").datagrid('endEdit',i);
	}
}
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#yixuanxiangmu').datagrid('validateRow', editIndex)){
		/* var ed = $('#yixuanxiangmu').datagrid('getEditor', {index:editIndex,field:'item_class_name'});
		if(ed==null || ed==""){
			return;
		}
		var text = $(ed.target).combobox('getText');
		var item_class_id = $(ed.target).combobox('getValue');
		$('#yixuanxiangmu').datagrid('getRows')[editIndex]['item_class_name'] = text;
		$('#yixuanxiangmu').datagrid('getRows')[editIndex]['item_class_id'] = item_class_id;*/
	   // $('#yixuanxiangmu').datagrid('refreshRow',editIndex);
	   /// $("#yixuanxiangmu").datagrid('endEdit',editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
} 
function accept(){
	if (endEditing()){
		$('#yixuanxiangmu').datagrid('acceptChanges');
	}
}
function saveitem(){
	zuihoubaocun();
	var row = $("#yixuanxiangmu").datagrid('getRows');
    var  item = "";
    var item_class_id = "";
    var  icd = $('#item_class_id').val();
	for(var i = 0 ; i < row.length ; i++){
			if(row[i].item_class_id!=""&&row[i].item_class_id>0){
				item_class_id = row[i].item_class_id;
			} else {
				item_class_id = 0;
			}
		item+='{"charging_item_id":'+charging_item_id+',"exam_item_id":'+row[i].id+',"item_class_id":'+item_class_id+'},';
	}
	var item_s = item.substring(0,item.length-1);
	$.ajax({
		url:'saveItemClass.action',
		data:{
			item_id:'['+item_s+']',
			charging_item_id:$('#charging_item_id').val(),
			item_class_id:$('#add_item_class_id').val()
		},
		type:'post',
		success:function(data){
			$.messager.alert("提示信息",data,"info");
			$('#dlg-custedit').dialog('close');
			getgroupuserGridss();
		},error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	})
}
</script>
<input type="hidden"  id="add_item_class_id" value="<s:property value="item_class_id"/>"  />
<input type="hidden"  id="add_charging_item_id" value="<s:property value="charging_item_id"/>"  />
<fieldset style=" margin: 5px;float:left;width:940px;">
	<legend><strong>查询条件</strong></legend>
	<div class="user-query">
				<dl>
					<dt style="width:60px">项目编码</dt>
					<dd><input class="easyui-textbox"    
					  type="text" id="ad_num"  style="height:26px;width:140px;"/>&nbsp;&nbsp;&nbsp;</dd>
					<dt style="width:60px">项目名称</dt>
					<dd><input class="easyui-textbox"  type="text" id="ad_demo_name"   style="height:26px;width:140px;"/></dd>
					<!-- <dt>关联检验编码</dt>
					<dd><input class="easyui-textbox"  type="text" id="c_exam_num"   style="height:26px;width:140px;"/></dd>
					<dt>关联影像编码</dt>
					<dd><input class="easyui-textbox"  type="text" id="c_view_num"   style="height:26px;width:140px;"/></dd> --> 
					<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
				</dl>
			</div>
</fieldset>
<fieldset style=" margin: 5px;width:46%;height:350px; float:left">
	<legend><strong>项目列表</strong></legend>
	<table id = "xiangmuliebiao"> </table>
</fieldset>
<fieldset style=" margin: 5px;width:46%;height:350px;">
	<legend><strong>已选择项目</strong></legend>
	<table id = "yixuanxiangmu"> </table>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:saveitem()" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>
<div id="lis_open" class="easyui-dialog"  data-options="width: 1200,height: 590,closed: true,cache: false,modal: true"></div>