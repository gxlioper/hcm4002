<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	//列表1
	$('#cxcyc').focus();
	$('#cxcyc').keydown(function(event){
		if(event.keyCode == 13){
			getresultShou();
		}
	});
	getresultShou();
 	$('#jgrx').val($(shuangji).val());
 	if(getCookie($('#create_semicolon').attr('id')) == null){
		$('#create_semicolon').attr("checked", false);
	}else{
		$('#create_semicolon').attr("checked", true);
	}
})
//----------------------------------------常用词列表-------------------------------
//常用词列表1
function getresultShou(){
	$("#resultShou").datagrid({
	url: 'getItemeditLibS.action',
	queryParams:{
		item_code:$('#item_tt').val(),
		exam_result:$('#cxcyc').val()
		},
	type:'post',
    columns:[[
     		  {align:"center",field:"exam_result","title":"常用词汇","width":20},
              {align:'center',field:"fsf","title":"操作",width:18,"formatter":f_add},
     		  {align:"center",field:"exam_results","title":"常用词汇","width":18}, 
     		  {align:'center',field:"fds","title":"操作","width":18,"formatter":f_adds},
     		  ]],
        fitColumns:true,
   	    fit:true,
   		striped:true,
   		singleSelect:true
	});
}
//--------------------------------------常用词添加结果1----------------------------------------------------
function f_add(val,row){
	var str="";
	 if(row.exam_result==""){
		str='&nbsp;&nbsp;&nbsp;<a href=\"javascript:f_edit(\''+row.exam_results+'\',\''+row.exam_conclusions+'\')\">添加常用词汇</a>'
	 }else{
	 	str='&nbsp;&nbsp;&nbsp;<a href=\"javascript:f_edit(\''+row.exam_result+'\',\''+row.exam_conclusion+'\')\">添加常用词汇</a>'
	 }
	 return str;
}
function f_edit(exam_result,examcon){
	var id=$(shuangji).parent().find(".jd").val();
	var zhi=$('#jgrx').textbox('getValue');
	var item_list = samp_item[sam_count].depItemList;
	var semicolon = '';
	if($('#create_semicolon')[0].checked){
		semicolon = ';';
	}
	for(i=0;i<item_list.length;i++){
		if(item_list[i].id == id){
			if(item_list[i].default_value == zhi || item_list[i].default_value == exam_result || zhi == '<br>' || zhi == '婉拒检查'){
				zhi = exam_result;
				for(var j =0;j<exam_conclusion.length;j++){
					if(exam_conclusion[j].item_id == id){
						exam_conclusion.splice(j,1);
						j--;
					}
				}
				exam_conclusion.push({'item_id':id,'conclusion':examcon});
			}else{
				zhi += semicolon+exam_result;
				exam_conclusion.push({'item_id':id,'conclusion':examcon});
			}
		}
	}
	$('#jgrx').textbox('setValue',zhi)
}	
//---------------------------------------常用词添加2--------------------------------------------------------
function f_adds(val,row){
	 var str="";
	 if(row.exam_results==""){
		 str = '&nbsp;&nbsp;&nbsp;<a href=\"javascript:f_edit(\''+row.exam_result+'\',\''+row.exam_conclusion+'\')\">添加常用词汇</a>'
	 }else{
	     str = '&nbsp;&nbsp;&nbsp;<a href=\"javascript:f_edit(\''+row.exam_results+'\',\''+row.exam_conclusions+'\')\">添加常用词汇</a>'
	 }
	 return str;
}

function checkthedefault(obj){
	if($(obj)[0].checked){
		setCookie($(obj).attr('id'),1);
	}else{
		delCookie($(obj).attr('id'));
	}
}
//--------------------------------------保存-------------------------
function confirm(){
	$(shuangji).val($('#jgrx').textbox('getValue'));
	$(shuangji).trigger('blur');
	sr(shuangji);
	$('#resultVocabulary').dialog('close');
}

</script>
<input type="hidden"  id="item_tt"  value="<s:property value='item_num'/>"  />
<div style="padding-left: 10px;padding-right:10px">
	<fieldset style="margin:0px;height:30%;padding-left:30px;">
    			<legend><strong>常用词检索</strong></legend>
    			<div class="user-query">
	    			<dl>
	    				<dt style="width: 50px">常用词</dt>
	    				<dd><input type="text" id='cxcyc' class="textinput" /></dd>
	    				<dd>
	    					<a href="javascript:getresultShou();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;height:30px;">查询</a>
	    				</dd>
	    				<dd>
	    					<input type="checkbox" id='create_semicolon' checked="checked" onclick="checkthedefault(this)"/>多个结果词之间是否自动生成分号
	    				</dd>
	    			</dl>
    			</div>
   </fieldset>
   <fieldset style="margin:0px;padding-bottom:30px; height:300px;">
   		<legend><strong>项目常用词列表</strong></legend>
   				<table id='resultShou' style="width:100px;height: 100%;margin-right: 0px"></table>
   </fieldset>
	<div style="width:100%;height:25px;line-height:25px;background:#d9d9d9;margin-top: 5px">&nbsp;&nbsp;录入结果如下</div>
	<input class="easyui-textbox" id="jgrx" data-options="multiline:true" style="width:100%;height:70px"> 
	<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:confirm()" class="easyui-linkbutton c6" style="width:100px;">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#resultVocabulary').dialog('close')">关闭</a>
	</div>
</div>
</div>