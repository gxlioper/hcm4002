<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
$(function (){
	var id ="<s:property value='id'/>";
	if($('#zid').val()>0){
		
		dep_combobox();
		getDisease_exam_item(id);
		var sex = "<s:property value='sex'/>";
		if(sex=='男'){
			$('#sex_quan').attr('checked',false);
			$('#sex_nan').attr('checked',true);
			$('#sex_nv').attr('checked',false);
		}else if(sex=='女'){
			$('#sex_quan').attr('checked',false);
			$('#sex_nan').attr('checked',false);
			$('#sex_nv').attr('checked',true);
		}else{
			$('#sex_quan').attr('checked',true);
			$('#sex_nan').attr('checked',false);
			$('#sex_nv').attr('checked',false);
		}
	}else{
		
		dep_combobox();
		$('#divshou').html(addreload());
		
	} 
})
/**
 * 科室下拉框
 */
function dep_combobox(){
	$('#dep_id').combobox({
		url:'getDepLogixDepartmentDep.action',
		valueField:'id',
		textField:'dep_name',
		editable:false,
		onLoadSuccess:function(){
			var  dep="<s:property value='dep_id'/>"
			if(dep>0){
				$('#dep_id').combobox('setValue',Number(dep));
			}else{
				var dep = $('#dep_id').combobox('getData');
				$('#dep_id').combobox('setValue',dep[0].id);
			}
		}
	})
}
/**
 * 添加加载页面
 */
function addreload(){
	var str= $('#divshou').html();
		 str+='<dl>'
				+'<dd style="width:30%;text-align: center;">'
					+'<div id="jianchaxiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
						+' position:absolute;z-index:8;background:#ffffff;display:none;width:27%;marign 0px"'
						+' onmouseout="examintion_item_blur(this);"  onmouseover="examintion_item_focus(this);"   >'
					+'</div>'
					+'<input type="text" class="textinput" style="width:100%;height: 24px"   onblur="examintion_item_ss(this);"    onclick="diesaseExamination_item(this)"  '
					+'	  onkeyup="diesaseExamination_item(this);"   />'
				+'</dd>'
				+'<dd style="width:20%;text-align: center;">'
					+'<select id="condition">'
						+'<option value=">">大于</option>'
						+'<option value=">=">大于等于</option>'
						+'<option value="<">小于</option>'
						+'<option value="<=">小于等于</option>'
						+'<option value="=">等于</option>'
						+'<option value="in">包含</option>'
						+'<option value="not in">不包含</option>'
					+'</select>'
				+'</dd>'
				+'<dd style="width:20%;text-align: center;"><input type="text" class="textinput" style="width:100%;height:24px;"   /></dd>'
				+'<dd style="width:10%;text-align: center;">'
					+'<select  id="andOrNo" >'
					+'	<option value="and">并且</option>'
					+'	<option value="or" >或者</option>'
					+'</select>'
				+'</dd>'
				+'<dd style="width:10%;text-align: left;">'
					+'<input type="button" value="+"  onclick="addrow()"  style="width:20px;text-align: center;background:#6fa8dc;float:left;color:#ffffff;cursor: pointer;"/>'
					/* +'<input type="button" value="-"  style="width:20px;text-align:center;background:#6fa8dc"/>' */
				+'</dd>'
			+'</dl>'
	return str;
}
//----------------------------------------------------------获取数据-------------------------------
/**
 * 获取所有疾病
 */
function getDiseaseKnowloedge(obj){
	$('#disease_xlk').css('display','block')
	model = {disease_type:$("input[name='disease_logic']:checked").val(),disease_name:$(obj).val()}
	$.ajax({
		url:'getDiseaseKnowloedge.action',
		data:model,
		type:'post',
		success:function(data){
			var obj=eval('('+data+')');
		 	var str="<ul  style='text-align:left'>";
		   	for(var i=0;i<obj.length;i++){
		   		str+="<li   style='padding-left:5px' "
	   			str+="  onclick='diseaseclike(this,"+obj[i].id+")'  ><input type='hidden' value=''"+obj[i].disease_name_type+"/>"+obj[i].disease_name+"</li>"
		   	}
		   	str+="</ul>";
		   	$('#disease_xlk').html(str); 
		}
	}) 
	
}
/**
 * 获取所有收费项目
 */
function diesasecharging_item(obj){
	var ob = $(obj).parent().children().eq(0);
	$(ob).css('display','block')
	
	var weizhi = $(obj).offset().top;
		weizhi = weizhi-252;
	$(ob).css('top',weizhi)
	
	model = {c_item_name:$(obj).val()}
	$.ajax({
		url:'getDiseaseChargingItem.action',
		type:'post',
		data:model,
		success:function(data){
		   	var obj=eval('('+data+')');
		   	var str="<ul  style='text-align:left;' >";
		   	for(var i=0;i<obj.length;i++){
		   		str+="<li  style='padding-left:5px' "
		   			str+=" onclick='textclick2(this,"+obj[i].id+");'>"+obj[i].item_name+"</li>"
		   	}
		   	str+="</ul>";
		   	$(ob).html(str); 
		}
	})  
}
/**
 * 获取所有检查项目
 */
function diesaseExamination_item(obj){
			
	var ob = $(obj).parent().children().eq(0);
	$(ob).css('display','block');
	
	var weizhi = $(obj).offset().top;
	weizhi = weizhi-252;
	$(ob).css('top',weizhi)
	
	model = {e_item_name:$(obj).val(),dep_id:$('#dep_id').combobox('getValue')}
	$.ajax({
		url:'getDepLogicExamItem.action',
		data:model,
		type:'post',
		success:function(data){
			var obj=eval('('+data+')');
		 	var str="<ul  style='text-align:left'>";
		   	for(var i=0;i<obj.length;i++){
		   		str+="<li   style='padding-left:5px' "
	   			str+="  onclick='examintion_item_click2(this,"+obj[i].id+");'  >"+obj[i].item_name+"</li>"
		   	}
		   	str+="</ul>";
		   	$(ob).html(str); 
		}
	}) 
}
//-------------------------------------------收费项目事件------------------------------------
var streak="2";
/**
 * 收费项目下拉框获取焦点
 */
function textfocus(obj){
	streak="1";
}
/**
 * 收费项目失去焦点
 */
function textblur(obj){
	streak="2";
}
/**
 * 收费项项输入框失去焦点关闭下拉框
 */
function textclick(obj){
	if(streak=="2"){
		var sfxm = $(obj).parent().children().eq(0);
		$(sfxm).css('display','none');
		
	}
}
/**
 * 下拉框选择后关闭
 */
function textclick2(obj,value){
	var zhi = $(obj).text();
	var srk=$(obj).parent().parent().parent().children().eq(1);
		$(srk).val(zhi);
		$(srk).attr('data',value);
	//下拉框节点
	var xlk = $(obj).parent().parent()
		$(xlk).css('display','none');
}
//--------------------------------------检查项目事件----------------------
/**
 * 检查项目下拉框失去焦点
 */
function examintion_item_blur(obj){
	streak="2";
}
/**
 * 检查项目下拉框获取焦点
 */
function examintion_item_focus(obj){
	streak="1";
}
/**
 * 检查项目输入框失去焦点关闭下拉框
 */
function  examintion_item_ss(obj){
	if(streak=='2'){
		var jcxm = $(obj).parent().children().eq(0);
		$(jcxm).css('display','none');
	}
}
/**
 * 检查项目选择后关闭下拉框
 */
function examintion_item_click2(obj,value){
	var zhi = $(obj).text();
	var srk=$(obj).parent().parent().parent().children().eq(1);
		$(srk).val(zhi);
		$(srk).attr('data',value);
		//下拉框关闭
		var xlk = $(obj).parent().parent()
		$(xlk).css('display','none');
}

//----------------------------------------------------------------疾病事件---------------------------------------------
function disease_focus(obj){
	streak="1";
}
function disease_blur(obj){
	
	streak="2";
}

/**
 *疾病输入框失去焦点关闭下拉框
 */
function disease_click(obj){
	if(streak=="2"){
		var jianchaxiangmu = $(obj).parent().children().eq(0);
		$("#disease_xlk").css('display','none');
		
	}
}  
//疾病下拉框选择后关闭
function diseaseclike(obj,id){
	var disease_name=$(obj).text();
 	var disease_type= $(obj).parent().children().eq(0).val();
 	
	$('#disease_id_type').val(disease_name);
	$('#disease_id').val(id);
	$('#disease_id_type').attr('data',disease_type);
	
	$('#disease_xlk').css('display','none'); 
}
//----------------------------------------------------------------+-操作-----------------------------------------------
/**
 * 添加行
 */
function addrow(){
	var str="";
	 str+='<dl>'
		/* 	+'<dd style="width:20%;text-align: center;">'
				+'<div id="shoufeixiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
				+' position:absolute;z-index:8;background:#ffffff;display:none;width:19%;marign 0px"  '
				+' onmouseout="textblur(this);"  onmouseover="textfocus(this);"  ></div>'
				+'<input type="text" class="textinput" style="width:100%;height: 24px"  onblur="textclick(this);"  '
				+'   onkeyup="diesasecharging_item(this);"       onclick="diesasecharging_item(this)"   />'
			+'</dd>' */
			+'<dd style="width:30%;text-align: center;">'
				+'<div id="jianchaxiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
					+' position:absolute;z-index:8;background:#ffffff;display:none;width:27%;marign 0px"'
					+' onmouseout="examintion_item_blur(this);"  onmouseover="examintion_item_focus(this);"   >'
				+'</div>'
				+'<input type="text" class="textinput" style="width:100%;height: 24px"   onblur="examintion_item_ss(this);"    onclick="diesaseExamination_item(this)" '
				+'   onkeyup="diesaseExamination_item(this);"   />'
			+'</dd>'
			+'<dd style="width:20%;text-align: center;">'
				+'<select id="condition">'
					+'<option value=">">大于</option>'
					+'<option value=">=">大于等于</option>'
					+'<option value="<">小于</option>'
					+'<option value="<=">小于等于</option>'
					+'<option value="=">等于</option>'
					+'<option value="in">包含</option>'
					+'<option value="not in">不包含</option>'
				+'</select>'
			+'</dd>'
			+'<dd style="width:20%;text-align: center;"><input type="text" class="textinput" style="width:100%;height: 24px"   /></dd>'
			+'<dd style="width:10%;text-align: center;">'
				+'<select  id="andOrNo" >'
				+'	<option value="and">并且</option>'
				+'	<option value="or" >或者</option>'
				+'</select>'
			+'</dd>'
			+'<dd style="width:10%;text-align: left;">'
			 	+'<input type="button" value="+"  onclick="addrow();"  style="width:20px;text-align: center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>&nbsp;&nbsp;';
			 str+='<input type="button" value="-"  onclick="deletrow(this);"       style="width:20px;text-align:center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>';
		str+='</dd>'
		+'</dl>';
		$("#divshou").append(str);
}
/**
 * 删除行
 */
 function deletrow(obj){
	 $(obj).parent().parent().remove();
}
//------------------------------------------------------保存疾病逻辑----------------------------------------------
function  addDep_logic(){
	
	if($('#conclusion_word').val()==""){
		$.messager.alert('提示信息','请输入结果词！');
		$('#conclusion_word').focus();
		return;
	}
	
	var exam_item_id = "";
	var condition = "";
	var condition_value = "";
	var andOrNo = "";
	var logic_index = "";
	var li = "";
	var i = 1;
	
	$("#divshou dl").each(function(){
			exam_item_id = $(this).children().eq(0).children().eq(1).attr('data');
			exam_item_name = $(this).children().eq(0).children().eq(1).val();
			   condition = $(this).children().eq(1).children().eq(0);
		 condition_value = $(this).children().eq(2).children().eq(0).val();
			     andOrNo = $(this).children().eq(3).children().eq(0);
			     if(exam_item_id>0&&exam_item_name!=''){
				     logic_index = i;
				   	   i++;
						    	 li+='{ exam_item_id:'+exam_item_id+','
						    	 +' condition:"'+$(condition).val()+'",'
						    	 +' condition_value:"'+condition_value+'",'
						    	 +' andOrNo:"'+$(andOrNo).val()+'",'
						    	 +' logic_index:'+logic_index+''
						    	 +'},';
			     }
	})
 	var list ='['+ li.substring(0,li.length-1)+']';
		var model = {
			id:$('#zid').val(),
			dep_id:$('#dep_id').combobox('getValue'),
			conclusion_word:$('#conclusion_word').val(),
			sex:$("input[name='sex']:checked").val(),
			li:list
				} 
	 $.ajax({
		url:'addDepLogic.action',
		data:model,
		type:'post',
		success:function(data){
			$('#groupusershow').datagrid('reload');
			$('#dlg-custedit').dialog('close');
			$.messager.alert('提示信息',data);
		},
		error:function(data){
			$.messager.alert('提示信息','操作失败','error');
		}
	}) 
}
/**
 * 获取科室逻辑和检查项目细项
 */
function getDisease_exam_item(id){
	$.ajax({
		url:'getDepLogicExamItemShow.action?logic_id='+id,
		success:function(data){
			var obj =eval('('+data+')');
			var str="";
			if(obj.length==0){
				$('#divshou').html(addreload());
			}else{
				for(var i=0; i<obj.length;i++){
					 str+='<dl>'
						 str+='</dd>'
							+'<dd style="width:30%;text-align: center;">'
								+'<div id="jianchaxiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
									+' position:absolute;z-index:8;background:#ffffff;display:none;width:19%;marign 0px"'
									+' onmouseout="examintion_item_blur(this);"  onmouseover="examintion_item_focus(this);"   >'
								+'</div>'
								+'<input type="text" class="textinput"	value="'+obj[i].e_name+'"   data="'+obj[i].e_id+'"		 style="width:100%;height: 24px"   onblur="examintion_item_ss(this);"    onclick="diesaseExamination_item(this)" '
								+'   onkeyup="diesaseExamination_item(this);"   />'
							+'</dd>'
							+'<dd style="width:20%;text-align: center;">'
								+'<select id="condition">'
									+'<option value=">">大于</option>'
									+'<option value=">=">大于等于</option>'
									+'<option value="<">小于</option>'
									+'<option value="<=">小于等于</option>'
									+'<option value="=">等于</option>'
									+'<option value="in">包含</option>'
									+'<option value="not in">不包含</option>'
								+'</select>'
							+'</dd>'
							+'<dd style="width:20%;text-align: center;"><input type="text" class="textinput"   value="'+obj[i].condition_value+'"    style="width:100%;height: 24px"   /></dd>'
							+'<dd style="width:10%;text-align: center;">'
								+'<select  id="andOrNo" >'
								+'	<option value="and">并且</option>'
								+'	<option value="or" >或者</option>'
								+'</select>'
							+'</dd>'
							+'<dd style="width:10%;text-align: left;">'
								+'<input type="button" value="+"  onclick="addrow();"  style="width:20px;text-align: center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>&nbsp;&nbsp;';
								if(i>0){
								str+='<input type="button" value="-"  onclick="deletrow(this);"       style="width:20px;text-align:center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>';
								}
						str+='</dd>';
						+'</dl>';
						$("#divshou").append(str);
						var condition=$("#divshou dl:last").children().eq(1).children().eq(0);
						var andOrNo=$("#divshou dl:last").children().eq(3).children().eq(0);
						$(condition).val(obj[i].condition);
						$(andOrNo).val(obj[i].andOrNo);
						str=""
				} 
			}
		}
	})
}
</script>
<input   type="hidden"   id='zid'  value="<s:property value='id'/>"  />
 <fieldset style=" margin: 10px;padding-left: 0px">
	<legend><strong>科室逻辑编辑</strong></legend> 
	<div  class="formDiv"  style="margin-top: 15px;margin-left: 5px;font-size:14">
		<dl style="width: 100%">
			<dt style="width:100px">
				科室:
			</dt>
			<dd>
				<input id="dep_id" style="height: 26px;width:150px;"  />
			</dd>
			<dt style="width:100px;">
				适用性别:
			</dt>
			<dd style="width:130px;">
				<input type="radio"  name="sex"  id="sex_quan"  value="全部"  checked="true" />全部
				<input type="radio"  name="sex"  style="margin-left: 5%" id="sex_nan"  value="男" />&nbsp;男
				<input type="radio"  name="sex"   style="margin-left: 5%" id="sex_nv" value="女" />&nbsp;女
			</dd>
		</dl>
		<dl>
			<dt style="width:100px;">
				生成结果词 :
			</dt>
			<dd style="margin-right: 0px;padding-right: 0px;width:400px">
				<input  type="text"   id="conclusion_word"   value="<s:property value='conclusion_word'/>"  class="textinput"    style="width: 100%;height: 26px"/>
			</dd>
			
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 10px;margin-bottom:60px;min-height:100px">
	<legend><strong>科室逻辑条件设置</strong></legend> 
		<div  class="formDiv" style="background: #d9d9d9;font-size:14;padding-right: 0px">
			<dl style="background: #cccccc;padding-right: 0px">
				<dt style="width:30%;text-align: center;">检查项目</dt>
				<dt style="width:20%;text-align: center;">条件</dt>
				<dt style="width:20%;text-align: center;">条件值</dt>
				<dt style="width:10%;text-align: center;">关系</dt>
				<dt style="width:10%;text-align: left;margin-right: 0px">新增/删除</dt>
			</dl>
		</div>
		<div id="divshou" class="formDiv"  style="background: #d9d9d9;font-size:14">
			
		</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addDep_logic();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>