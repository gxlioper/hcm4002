<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>

<script type="text/javascript">
$(function(){
	if($('#id').val()>0){
		ddj();
	} else{
		djdyg();
	}
		 update_disease_showlist();
})
function djdyg(){
	$('#disease_combx').combobox({
		url:'getPageDklodegcombobox.action',
		valueField:'id',
		textField:'disease_name',
		mode:'remote',
		onLoadSuccess:function(){
			var later_disease_id_ss="<s:property  value='later_disease_id'/>";
			if(later_disease_id_ss>0){
				$('#disease_combx').combobox('setValue',later_disease_id_ss);
				//$('#disease_combx').combobox('readonly')
			} else {
				/* var combo_value=$('#disease_combx').combobox('getData');
				$('#disease_combx').combobox('setValue',combo_value[0].id); */
			}
		}
	})
}
function ddj(){
	$('#disease_combx').combobox({
		url:'getPageDklodegcombobox.action',
		valueField:'id',
		textField:'disease_name',
		mode:'remote',
		readonly:true,
		onLoadSuccess:function(){
			var later_disease_id_ss="<s:property  value='later_disease_id'/>";
			if(later_disease_id_ss>0){
				$('#disease_combx').combobox('setValue',later_disease_id_ss);
				//$('#disease_combx').combobox('readonly')
			} else {
				/* var combo_value=$('#disease_combx').combobox('getData');
				$('#disease_combx').combobox('setValue',combo_value[0].id); */
			}
		}
	})
}
function disease_showlist(){
	     $("#disease_show").datagrid({
		 queryParams:{
		 },
		 rownumbers:false,
		 columns:[[
	            {align:'left',field:'id',title:'疾病id',hidden:true},	
	            {align:'left',field:'disease_num',title:'疾病编码',width:20},	
	            {align:'left',field:'disease_name',title:'疾病名称',width:20},
	            {align:'center',field:'ss',title:'删除',width:10,"formatter":d_sc}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    fit:true,
		    striped:true
	});
}
function update_disease_showlist(){
	    $("#disease_show").datagrid({
		 url:'updateDiseaseKnowloedgeDTOList.action',
		 queryParams:{
			 id:$('#id').val()
		 },
		 rownumbers:false,
		 columns:[[
	           {align:'left',field:'id',title:'疾病id',hidden:true},	
	           {align:'left',field:'disease_num',title:'疾病编码',width:20},	
	           {align:'left',field:'disease_name',title:'疾病名称',width:20},
	           {align:'center',field:'ss',title:'删除',width:10,"formatter":d_sc}
		 	]],	    	
	   	onLoadSuccess:function(value){
	   		$("#datatotal").val(value.total);
	   		$(".loading_div").remove();
	   	},
	   	singleSelect:false,
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    fit:true,
		    striped:true
	});
}
//删除
function d_sc(val,row,index) {
	return '<a href=\"javascript:shanchuhang(\''+index+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
function shanchuhang(index){
	 var selections  =$('#disease_show').datagrid('getSelections');
	 var selectRows = [];
	 for ( var i= 0; i< selections.length; i++) {
	   selectRows.push(selections[i]);
	 }
	 for(var j =0;j<selectRows.length;j++){
	   var index = $('#disease_show').datagrid('getRowIndex',selectRows[j]);
	   $('#disease_show').datagrid('deleteRow',index);
	 }
}
//------------------------------(下拉框获取所有疾病)--------------------------------------
function get_disese_select(){
	
	$.ajax({
		url:'getPageDklodegcombobox.action',
		data:{
			disease_name:$('#disease_names').val()
		},
		type:'post',
		dataType:'json',
		success:function(data){
			var str = "";
			for (var i = 0; i < data.length; i++) {
				str+=' <li onclick=\'li_onclike(this);\'>'+data[i].disease_name+'<input type=\'hidden\'  data=\''+data[i].disease_num+'\'  value=\''+data[i].id+'\'</li>'
			}
			str='<ul>'+str+'</ul>';
			
			//var cs={'position':'absolute','z-index':'999','display':'block'}4
			$('#disese_select').css("display","");
			$('#disese_select').addClass("cs");
			$('#disese_select').html(str);
		}
			
	})
}
//下拉框单击事件
var streak="0";
function select_oncklik(){
	
}
function examintion_item_blur(obj){
	streak="2";
}
function examintion_item_focus(obj){
	streak="1";
}
function  examintion_item_ss(obj){
	if(streak=='2'){
		$('#disese_select').css('display','none');
	}
}
function li_onclike(li_value){
	var d_name= $(li_value).text();
	var id= $(li_value).find('input').val();
	var num=$(li_value).find('input').attr('data');
	var rows = $('#disease_show').datagrid('getRows');
	var fal=false;
	for (var i = 0; i < rows.length; i++) {
		if(id==rows[i].id){
			$.messager.alert("提示信息","疾病已存在","error");
			fal=true;
			break;
		}
	}
	$('#disese_select').css('display','none');
	
	if(fal){
		return;
	}
	$('#disease_show').datagrid('appendRow',{
		'id':id,
		'disease_num':num,
		'disease_name':d_name
	});
}
//------------------------------------保存合并疾病------------------------
function savadisease(){
	var zi = $('#disease_combx').combobox('getValue');
	if( zi<1 ){
		$.messager.alert("警告信息","请选择对应疾病","error");
		return;
	}
	var save_rows = $('#disease_show').datagrid('getRows');
	if(save_rows.length==0){
		$.messager.alert("警告信息","请添加需要合并的疾病","error");
		return;
	}
	var b_id =new Array();
	var b_name="";
	for (var i = 0; i < save_rows.length; i++) {
		 b_id.push(save_rows[i].id);
		 b_name+=save_rows[i].disease_name+";";
	}
	model = {
			id:$('#id').val(),
			before_disease_id:b_id.toString(),
			later_disease_id:$('#disease_combx').combobox('getValue'),
			name:b_name
	}
	$.ajax({
		url:'savediseaseMerge.action',
		data:model,
		type:'post',
		success:function(data){
			$.messager.alert("提示信息",data,"info");
			$('#dlg-custedit').dialog('close')
			$('#diseasemergetable').datagrid("reload");
		},
		error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	})
}
</script>
<style   type="text/css">
.cs{
	position:absolute;
	z-index:999;
	display:block;
	max-height:200px;
	overflow-y:auto;
	background-color:#ffffff;
	width: 260px;
	border:1px solid #c0c0c0;
	padding-left: 5px;
	}
li:hover{
    background:#F0F8FD;
    display:block;
	} 
</style>
<input type="hidden"  id="id" value="<s:property value='id'/>" />
<input type="hidden"  id="later_disease_id_id" value="<s:property  value='later_disease_id'/>" />
 <fieldset style="width: 100%;float: left;margin: 5px 5px;height:490px;padding-left: 0PX;width: 98%;padding-right: 0px;">
	<legend><strong>合并疾病</strong></legend> 
			<div class="user-query"  style="height:90px;" >
				<dl style="height: 40px;">
					<dt>对应疾病</dt>
					<dd>
						<input type="text"  id="disease_combx"   style="width: 260px;height: 26px;"  />
					</dd>
					<dd></dd>
				</dl>
				<dl style="height: 40px;">
					<dt  >疾病名称</dt>
					<dd>
						<input  class="textinput"   type="text"  onblur="examintion_item_ss(this);"  onkeyup="get_disese_select();"    onclick="get_disese_select()" id="disease_names"  style="height:26px;width:260px;"/>
						<div id="disese_select"   style="display: none;"         onmouseout="examintion_item_blur(this);"  onmouseover="examintion_item_focus(this);" ></div>
					</dd>
					<dd><a href="javascript:void(0)"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
				</dl>
			</div>
		      <table id="disease_show"  style="width:400px;" >
		      </table>	
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:savadisease();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>