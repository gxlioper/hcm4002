<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
li:hover{
    background:#c9daf8;
    display:block;
} 
</style>
<script type="text/javascript">
$(function (){
	var id ="<s:property value='id'/>";
	if(id>0){
		var logic_type ="<s:property value='logic_type'/>";
		var sex = "<s:property value='sex'/>";
		var critical_flag = "<s:property value='critical_flag'/>";
		
		if(logic_type=='Y'){
			$('#disease_logic_Y').attr('checked',true);
			$('#disease_logic_N').attr('checked',false);
		}else{
			$('#disease_logic_Y').attr('checked',false);
			$('#disease_logic_N').attr('checked',true);
		}
		if(critical_flag=='1'){
			$('#critical_flag_shi').attr('selected',true)
			$('#critical_flag_fou').attr('selected',false)
		}else{
			$('#critical_flag_shi').attr('selected',false)
			$('#critical_flag_fou').attr('selected',true)
		}
		
		if(sex=='男'){
			$('#sex_quan').attr('checked',false)
			$('#sex_nan').attr('checked',true)
			$('#sex_nv').attr('checked',false)
		}else if(sex=='女'){
			$('#sex_quan').attr('checked',false)
			$('#sex_nan').attr('checked',false)
			$('#sex_nv').attr('checked',true)
		}else{
			$('#sex_quan').attr('checked',true)
			$('#sex_nan').attr('checked',false)
			$('#sex_nv').attr('checked',false)
		}
		var age_max ="<s:property value='age_max'/>";
		var age_min="<s:property value='age_min'/>";
		//alert(age_max);
		if(age_max>0){
			$('#age_max').val(age_max);
		}
		if(age_min>0){
			$('#age_min').val(age_min);
		}
		getDisease_exam_item(id);
	}else{
		
		$('#divshou').html(addreload());
	}
})
/**
 * 添加加载页面
 */
function addreload(){
	var str= $('#divshou').html();
		 str+='<dl>'
				+'<dd style="width:20%;text-align: center;">'
					+'<div id="shoufeixiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
					+' position:absolute;z-index:8;background:#ffffff;display:none;width:19%;marign 0px"  '
					+'  onmouseout="textblur(this);"  onmouseover="textfocus(this);"  ></div>'
					+'<input type="text" class="textinput" style="width:100%;height: 24px"  onblur="textclick(this);"   onclick="diesasecharging_item(this)"   '
					+'  onkeyup="diesasecharging_item(this);"   />'
				+'</dd>'
				+'<dd style="width:20%;text-align: center;">'
					+'<div id="jianchaxiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
						+' position:absolute;z-index:8;background:#ffffff;display:none;width:19%;marign 0px"'
						+' onmouseout="examintion_item_blur(this);"  onmouseover="examintion_item_focus(this);"   >'
					+'</div>'
					+'<input type="text" class="textinput" style="width:100%;height: 24px"   onblur="examintion_item_ss(this);"    onclick="diesaseExamination_item(this)"  '
					+'	  onkeyup="diesaseExamination_item(this);"   />'
				+'</dd>'
				+'<dd style="width:10%;text-align: center;">'
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
	model = {
		disease_type:$("input[name='disease_logic']:checked").val(),
		disease_name:$(obj).val(),
		dep_id:$('#dep_id').val()
	}
	$.ajax({
		url:'getDepDiseaseKnowloedge.action',
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
	
	
	var guolvExamintion_item = $(obj).parent().parent().children().eq(0).children().eq(1).attr('data');
	var guolvExamintion_item_value = $(obj).parent().parent().children().eq(0).children().eq(1).val();
			
			if(guolvExamintion_item_value==""){
				guolvExamintion_item="";
			}
			
	var ob = $(obj).parent().children().eq(0);
	$(ob).css('display','block');
	
	var weizhi = $(obj).offset().top;
	weizhi = weizhi-252;
	$(ob).css('top',weizhi)
	
	model = {e_item_name:$(obj).val(),c_item_id:guolvExamintion_item}
	$.ajax({
		url:'getDiseaseExaminationItem.action',
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
			+'<dd style="width:20%;text-align: center;">'
				+'<div id="shoufeixiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
				+' position:absolute;z-index:8;background:#ffffff;display:none;width:19%;marign 0px"  '
				+' onmouseout="textblur(this);"  onmouseover="textfocus(this);"  ></div>'
				+'<input type="text" class="textinput" style="width:100%;height: 24px"  onblur="textclick(this);"  '
				+'   onkeyup="diesasecharging_item(this);"       onclick="diesasecharging_item(this)"   />'
			+'</dd>'
			+'<dd style="width:20%;text-align: center;">'
				+'<div id="jianchaxiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
					+' position:absolute;z-index:8;background:#ffffff;display:none;width:19%;marign 0px"'
					+' onmouseout="examintion_item_blur(this);"  onmouseover="examintion_item_focus(this);"   >'
				+'</div>'
				+'<input type="text" class="textinput" style="width:100%;height: 24px"   onblur="examintion_item_ss(this);"    onclick="diesaseExamination_item(this)" '
				+'   onkeyup="diesaseExamination_item(this);"   />'
			+'</dd>'
			+'<dd style="width:10%;text-align: center;">'
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
function  adddisease_logic(){
	
	if($(disease_id_type).val()==""||$(disease_id).val()<1){
		$.messager.alert('提示信息','请选择疾病！');
		$('#disease_id_type').focus();
		return;
	}
	
	var charging_item_id= "";
	var exam_item_id = "";
	var condition = "";
	var condition_value = "";
	var andOrNo = "";
	var logic_index = "";
	var li = "";
	var i = 1;
	
	$("#divshou dl").each(function(){
		charging_item_id = $(this).children().eq(0).children().eq(1).attr('data');
		charging_item_name = $(this).children().eq(0).children().eq(1).val();
			exam_item_id = $(this).children().eq(1).children().eq(1).attr('data');
			exam_item_name = $(this).children().eq(1).children().eq(1).val();
			   condition = $(this).children().eq(2).children().eq(0);
		 condition_value = $(this).children().eq(3).children().eq(0).val();
			     andOrNo = $(this).children().eq(4).children().eq(0);
			     if(exam_item_id>0&&exam_item_name!=''){
				     logic_index = i;
				   	   i++;
						    	 li+='{';
						       if(charging_item_id>0&&charging_item_name!=""){
						    	 li+=' charging_item_id:'+charging_item_id+','
						       }
						    	 li+=' exam_item_id:'+exam_item_id+','
						    	 li+=' condition:"'+$(condition).val()+'",'
						    	 li+=' condition_value:"'+condition_value+'",'
						    	 li+=' andOrNo:"'+$(andOrNo).val()+'",'
						    	 li+=' logic_index:'+logic_index+''
						    	 li+='},';
			     }
	})
 	var list ='['+ li.substring(0,li.length-1)+']';
		var model = {
			id:$('#zid').val(),
			disease_id:$('#disease_id').val(),
			logic_name:$('#disease_id_type').val(),
			logic_type:$("input[name='disease_logic']:checked").val(),
			critical_flag:$('#critical_flag').combobox('getValue'),
			sex:$("input[name='sex']:checked").val(),
			age_min:$("#age_min").val(),
			age_max:$("#age_max").val(),
			li:list
				} 
	 $.ajax({
		url:'addDiseaseKnowloedge.action',
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
 * 获取疾病逻辑和检查项目，收费项目关系
 */
function getDisease_exam_item(id){
	$.ajax({
		url:'getDiseaseKnowloedge1.action?id='+id,
		cache:false, 
		async:false,
		type:'post',
		success:function(data){
			var obj =eval('('+data+')');
			var str="";
			if(obj.length==0){
				$('#divshou').html(addreload());
			}else{
				$('#divshou').html("");
				for(var i=0; i<obj.length;i++){
					 str+='<dl>'
							+'<dd style="width:20%;text-align: center;">'
								+'<div id="shoufeixiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
								+' position:absolute;z-index:8;background:#ffffff;display:none;width:19%;marign 0px"  '
								+'  onmouseout="textblur(this);"  onmouseover="textfocus(this);"  ></div>';
							if(obj[i].ch_id>0){	
							 str+='<input type="text" class="textinput"	value="'+obj[i].ch_name+'"  data='+obj[i].ch_id+'		 style="width:100%;height: 24px"  onblur="textclick(this);" '
								+'   onkeyup="diesasecharging_item(this);"       onclick="diesasecharging_item(this)"   />';
							}else{
							 str+='<input type="text" class="textinput"	  value=""  data=""	 style="width:100%;height: 24px"  onblur="textclick(this);" '
								+'   onkeyup="diesasecharging_item(this);"       onclick="diesasecharging_item(this)"   />';	
							}
						 str+='</dd>'
							+'<dd style="width:20%;text-align: center;">'
								+'<div id="jianchaxiangmu"  style="overflow-y:auto;height:200px;border:solid 1px;position:relative;'
									+' position:absolute;z-index:8;background:#ffffff;display:none;width:19%;marign 0px"'
									+' onmouseout="examintion_item_blur(this);"  onmouseover="examintion_item_focus(this);"   >'
								+'</div>'
								+'<input type="text" class="textinput"	value="'+obj[i].e_name+'"   data="'+obj[i].e_id+'"		 style="width:100%;height: 24px"   onblur="examintion_item_ss(this);"    onclick="diesaseExamination_item(this)" '
								+'   onkeyup="diesaseExamination_item(this);"   />'
							+'</dd>'
							+'<dd style="width:10%;text-align: center;">'
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
						var condition=$("#divshou dl:last").children().eq(2).children().eq(0);
						var andOrNo=$("#divshou dl:last").children().eq(4).children().eq(0);
						$(condition).val(obj[i].condition);
						$(andOrNo).val(obj[i].andOrNo);
						str="";
				} 
			}
		}
	})
}
</script>
<input   type="hidden"   id='dep_id'  value="<s:property value='dep_id'/>"  />
<input   type="hidden"   id='zid'  value="<s:property value='id'/>"  />
 <fieldset style=" margin: 10px;padding-left: 0px">
	<legend><strong>疾病逻辑编辑</strong></legend> 
	<div class="formDiv" style="margin-top: 15px;margin-left: 5px;font-size:14">
		<dl style="padding-left: 0px;">
			<dt style="width:10%">
				类型:
			</dt>
			<dd>
				<input type="radio"     name="disease_logic"  id="disease_logic_Y"  value="Y"  checked="true" />&nbsp;阳性指标&nbsp;&nbsp;&nbsp;
				<input type="radio" name="disease_logic" id="disease_logic_N"  value="N" />&nbsp;疾病&nbsp;&nbsp;&nbsp;
			</dd>
			<dt style="width:19%">危机值提醒标示:</dt>
			<dd>
				<select id="critical_flag" class="easyui-combobox"  style="width:60px;height: 26px"  data-options='panelHeight:60'>   
				    <option   id="critical_flag_fou"  value='0'>否</option>   
				    <option id="critical_flag_shi" value='1'>是</option>   
				</select>  
			</dd>	
			<dt style="width:14%">
				适用性别:
			</dt>
			<dd style="width:20%">
				<input type="radio"  name="sex"  id="sex_quan"  value="全部"  checked="true" />全部
				<input type="radio"  name="sex"  style="margin-left: 5%" id="sex_nan"  value="男" />&nbsp;男
				<input type="radio"  name="sex"   style="margin-left: 5%" id="sex_nv" value="女" />&nbsp;女
			</dd>
			
		</dl>
		<dl style="padding-left: 0px;">
			<dt style="width:10%">
				生成疾病 :
			</dt>
			<dd style="width:46%">
				<input type="hidden"  id='disease_id'  value="<s:property value='disease_id'/>"  />
				<input   type="text"  onblur="disease_click(this);" id="disease_id_type"  maxlength="45"   value="<s:property value='disease_name'/>"  onclick="getDiseaseKnowloedge(this);"  onkeyup="getDiseaseKnowloedge(this);"
					style="height: 26px; width:100%;"  class="textinput"/>
				<div  id="disease_xlk"    onmouseout="disease_blur(this);"     onmouseover="disease_focus(this);"		 style="width:43.5%; position:absolute;overflow-y:auto;height:150px;border:solid 1px;z-index:15;background:#ffffff;display:none;marign 0px"></div>
			</dd>
			<dt style="width:14%">适用年龄:</dt>
			<dd style="width: 20%">
				<input  class="textinput"   onkeyup="this.value=this.value.replace(/\D/g,'')" onpaste="this.value=this.value.replace(/\D/g,'')"     maxlength="4"  id="age_min"  value="1"  data-options="prompt:'最小年龄'" style="width:35%;height: 26px"/>至
				<input  class="textinput"  maxlength="4"  id="age_max"	onkeyup="this.value=this.value.replace(/\D/g,'')"		onpaste="this.value=this.value.replace(/\D/g,'')"   value="100"	   data-options="prompt:'最大年龄'" style="width:35%;height: 26px"/>
			</dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 10px;margin-bottom:60px;min-height:100px">
	<legend><strong>逻辑条件设置</strong></legend> 
		<div  class="formDiv" style="background: #d9d9d9;font-size:14">
			<dl style="background: #cccccc;">
				<dt style="width:20%;text-align: center;">收费项目</dt>
				<dt style="width:20%;text-align: center;">检查项目</dt>
				<dt style="width:10%;text-align: center;">条件</dt>
				<dt style="width:20%;text-align: center;">条件值</dt>
				<dt style="width:10%;text-align: center;">关系</dt>
				<dt style="width:10%;text-align: left">新增/删除</dt>
			</dl>
		</div>
		<div id="divshou" class="formDiv"  style="background: #d9d9d9;font-size:14">
			
		</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:adddisease_logic();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>