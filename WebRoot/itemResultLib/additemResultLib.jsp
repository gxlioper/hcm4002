<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	adddep_idcombox();
	$('#seq_code').validatebox({    
	    required: true 
	}); 
	//结果内容textbox失去焦点，faf是调用的方法
	/* $('#exam_result').textbox({    
		multiline:true,
		events:{blur:faf},
		required:true,
		validType:'loginName'
	})  */
	
	//是否常用词
	var common_words="<s:property value='common_words'/>";
	if(common_words=="Y"){
		$('#common_wordsY').attr('selected',true);
		$('#common_wordsN').attr('selected',false);
	}else if(common_words=="N"){
		$('#common_wordsY').attr('selected',false);
		$('#common_wordsN').attr('selected',true);
	}
	//是否默认值
	var dev="<s:property value='default_value'/>";
	if(dev=="Y"){
		$('#default_valueY').attr('selected',true);
		$('#default_valueN').attr('selected',false);
	}else if(dev=="N"){
		$('#default_valueY').attr('selected',false);
		$('#default_valueN').attr('selected',true);
	}
  	$("#seq_code").keyup(function () {
         //如果输入非数字，则替换为''，如果输入数字，则在每4位之后添加一个空格分隔
         this.value = this.value.replace(/[^\d]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
     });
     
     $('#exam_result').textbox({
    	 inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
    	 keypress: function(event){
	    	 	if(event.keyCode == 13) {
	    	   		return false;
	    	   	}else{
	    	   		return true;
	    	   	}
	    	 }})
    	 });
     
})
/**
 * 科室下拉框
 */
var ddd;
function adddep_idcombox(){
	//科室
	$('#adddep_id').combobox({
		url:'getDepartment_dep.action',
		valueField:'id',
		textField:'dep_name',
		onLoadSuccess:function(){
	     var val = $('#adddep_id').combobox('getData');
	       var dep_id='<s:property value="model.dep_id"/>';
    	   for (var item in val[0]) {  
               if (item == 'id') {
           	   		if(dep_id>0){
           	   			$('#adddep_id').combobox('setValue',dep_id);
           	   			
           	   			exam_item_idcombox(dep_id);
           	   			/* //修改进入
           	   			var  dsd="<s:property value='exam_item_id'/>";
	           	   			if(dsd==""){
	           	   				alert(88);
	           	   				return;
	           	   			} */
		           	   		 
           	   		}else{
           	   		var ll = $('#adddep_id').combobox('getData');
           		 		 $('#adddep_id').combobox('select',ll[0].id);
           		 		 
           		 		exam_item_idcombox(ll[0].id);
           	   		}
               }  
           }  
	    }
	})
}

$("#adddep_id").combobox({
	onChange: function (n) {
		exam_item_idcombox(n);
	}
});

function exam_item_idcombox(dep_id){
	$.ajax({
			   url:'depItemResultLib.action',
			   data:{dep_id:dep_id},
			   //async:false,
 		   success : function(data) {
	  			 var objet = eval("("+data+")");
	 			var qb = [{id:'',text:'请选择'}]
	 			$.each(objet,function(k,v){
	 				qb.push({"id":v.id,"text":v.item_name});
	 				/* if(v.id==dd){
	 					$('#exam_item_id').combobox("setValue",dd);
	 				} */
	 			})
	 			var  dsd="<s:property value='exam_item_id'/>";
	 			$('#exam_item_id').combobox("loadData",qb);
	 			$.each(objet,function(k,v){
	 				if(dsd==v.id){
	 					$('#exam_item_id').combobox("setValue",v.id);
	 				}
	 			})
	 			//$('#exam_item_id').combobox("select",qb[1].id);
		  }
	   })  
}


/**
 * 拼音
 */
function pinyin(){
	$.ajax({
		url:'pinying.action',
		type:'post',
		data:{pinying:$('#exam_result').textbox("getValue")},
		success:function(data){
			$('#result_num').textbox("setValue",data);
		}
	})
}
/**
 * 项目结果知识库添加方法/修改
 */
function addItemResultLib(){
	/* if($('#exam_result').textbox('getValue')==""){
		$('#exam_result').textbox('textbox').focus();
		return;
	} 
	if($('#result_num').textbox('getValue')==""){
		$('#result_num').textbox('textbox').focus();
		return;
	} */
	if($('#exam_item_id').combobox('getValue')=="" || $('#exam_item_id').combobox('getValue')==0){
		$.messager.alert("提示信息","请选择检查项目","error");
		return;
	}
	if($('#seq_code').val()==""){
		$('#seq_code').focus();
		return;
	}
	$.ajax({
		url:'addItemResultLibMethod.action',
	    type:'post',
		data:{
			 id:$('#id').val(),
			 exam_item_id:$('#exam_item_id').combobox('getValue'),
			 dep_id:$('#adddep_id').combobox('getValue'),
			 exam_result:$('#exam_result').textbox('getValue'),
			 result_num:$('#result_num').textbox('getValue'),
			 default_value:$('#default_value').combobox("getValue"),
			 seq_code:$('#seq_code').val(),
			 common_words:$('#common_words').combobox("getValue"),
			 exam_conclusion:$('#exam_conclusion').textbox('getValue') //检查结论
		},
		success:function(data){
			$.messager.alert('提示信息',data,'info');   
			//$('#dlg-custedit').dialog('close');
			$("#itemResultLibShow").datagrid('reload');
		},
		error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	})
}
</script>
<fieldset style=" margin: 10px;">
	<legend><strong>编辑项目知识结果</strong></legend> 
	<div class="formDiv">
		<dl>
			<dt>科室</dt>
			<dd>
				<input type="hidden" id='id' value="<s:property value='model.id'/>"/>
				<input type="text"  id="adddep_id"  style="height: 26px; width: 300px;"/>
				<font color="#ff0000">*</font>
			</dd>
		</dl>
		<dl>
			<dt>检查项目</dt>
			<dd>
				<input type="text"  class="easyui-combobox" id="exam_item_id"
				 data-options="valueField:'id',textField:'text',editable:true"	 style="height: 26px; width: 300px;"/>
				<font color="#ff0000">*</font>
			</dd>
		</dl>
		<dl style="height: 66px">
			<dt style="height: 60px">结果描述</dt>
			<dd>
				<input type="text" class="easyui-textbox" id="exam_result" value="<s:property value='exam_result'/>"
				style="height:60px; width: 300px;"  data-options="multiline:true,events:{blur:pinyin}"/>
			<font color="#ff0000">*</font>
			</dd>
		</dl>
		
		<dl style="height: 66px">
			<dt style="height: 60px">结论描述</dt>
			<dd>
				<input type="text" class="easyui-textbox" id="exam_conclusion" value="<s:property value='exam_conclusion'/>"
				style="height:60px; width: 300px;" data-options="multiline:true"/>
			<font color="#ff0000">*</font>
			</dd>
		</dl>
		
		<dl style="height: 66px">
			<dt style="height: 60px">结果拼音</dt>
			<dd>
				<input  type="text"  class="easyui-textbox" id="result_num"  value="<s:property value='result_num'/>" 
				style="height:60px; width: 300px;"   data-options="multiline:true,missingMessage:'该项输入为必输项'"/>
			<font color="#ff0000">*</font>
			</dd>
		</dl>
		<dl>
			<dt>是否默认值</dt>
			<dd>
				<select   class="easyui-combobox"   id="default_value"   data-options="panelHeight:'auto',editable:false" 
				style="height: 26px; width: 300px;" >
					<option value='N' id="default_valueN" >否</option>
					<option value='Y' id="default_valueY" >是</option>
				</select>
				<font color="#ff0000">*</font>
			</dd>
		</dl>
		<dl>
			<dt>顺序码</dt>
			<dd>
				<input type="text" class="textinput" id="seq_code" value="<s:property value='seq_code'/>" 
				maxlength="4"	style="height: 26px; width: 300px;"/>
		</dl>
		<dl>
			<dt>是否常用词</dt>
			<dd>
				<select   class="easyui-combobox"  id="common_words"  data-options="panelHeight:'auto',editable:false"
				style="height: 26px; width: 300px;">
					<option value='Y' id='common_wordsY'>是</option>
					<option value='N' id='common_wordsN'>否</option>
				</select>
			</dd>
		</dl>
	</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addItemResultLib();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>
</fieldset>