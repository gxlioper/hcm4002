<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
li:hover{
    background:#c9daf8;
    display:block;
} 
.textinput{
	height: 28px
}
</style>
<script type="text/javascript">
var tiaojian_name = ['条件一','条件二','条件三','条件四','条件五','条件六','条件七','条件八','条件九','条件十','条件十一','条件十二','条件十三','条件十四','条件十五'];
$(function(){
	 var c_sex = '<s:property value="sex"/>';
	 $("input[name='sex'][value="+c_sex+"]").attr("checked",true);
	
    var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    $("body").prepend(str); 
	$('#dl').combobox({    
	    url:'criticalClasslist.action?critical_class_level=2',    
	    valueField:'id',    
	    textField:'critical_class_name',
	    required: true,   
	    editable:false,
	    onLoadSuccess:function(){
	    	var zlfu = "";
	    	 if($('#critical_class_parent_id').val()>0){
	    		 zlfu = $('#critical_class_parent_id').val();
	    		 $('#dl').combobox('setValue',$('#critical_class_parent_id').val());
	    	 } else {
		     	 var val = $('#dl').combobox('getData');
		     	 zlfu = val[0].id;
		    	 $('#dl').combobox('setValue',val[0].id); 
	    	 }
		 	 $('#zl').combobox({    
		    	    url:'criticalClasslist.action?critical_class_level=1&id='+zlfu,    
		    	    valueField:'id',    
		    	    textField:'critical_class_name',  
		    	    required: true,    
		    	    editable:false,
	 	    		onLoadSuccess:function(){
	 	    			if($('#critical_class_id').val()>0){
	 	    				 $('#zl').combobox('setValue',$('#critical_class_id').val())
	 	    			} else {
	 	    				var val = $('#zl').combobox('getData');
			 	    	 	if(val!=""){
			 	    	 		$('#zl').combobox('setValue',val[0].id); 
			 	    	 	} else {
			 	    	 		$('#zl').combobox('clear'); 
			 	    	 	}
	 	    			}
 	    		    }
	    	 });    
		},
		onSelect:function(record){
			$('#zl').combobox('reload','criticalClasslist.action?critical_class_level=1&id='+record.id);  // 使用新的URL重新载入列表数据
		}
	})
	$('#jb').combobox({    
	    url:"getDatadis.action?com_Type=" + "WJZDJ",
	    valueField:'id',    
	    textField:'name',
	    required: true,    
	    editable:false,
		onLoadSuccess : function() {//下拉框默认选择
			if($('#critical_class_level_g').val()>0){
				$('#jb').combobox("setValue",$('#critical_class_level_g').val());
			} else {
				var val = $(this).combobox('getData');
				$(this).combobox('setValue',val[0].id);
			}
		}
	});
	$('#p').panel({    
		  fit:true,    
		  tools: [{    
		    iconCls:'icon-add',    
		    handler:function(){alert('new')}    
		  },{    
		    iconCls:'icon-save',    
		    handler:function(){alert('save')}    
		  }]    
	});
	var id =$("#ecl_id").val();
	if(id != ''){
		getDisease_exam_item(id);
	}else{
		add_tioajian();
	}
	$(".loading_div").remove();
	$('#critical_suggestion').val($('#h_critical_suggestion').val());
	
	if($('#h_disease_type').val()=='Y'){
		$('#disease_logic_Y').attr("checked",true);
		$('#disease_logic_N').attr("checked",false);
	} else if($('#h_disease_type').val()=='N'){
		$('#disease_logic_Y').attr("checked",false);
		$('#disease_logic_N').attr("checked",true);
	} else {
		$('#disease_logic_Y').attr("checked",true);
		$('#disease_logic_N').attr("checked",false);
	}
})
function add_value(obj){
	var str = '<dl style="margin-top: 5px;height: 30px;">'
		    +'<dd style="width:145px;"><input type="hidden" value=""/>'
			+'<input type="text" class="textinput" style="width:145px;height: 26px"  onblur="textclick(this);"   onclick="diesasecharging_item(this)"   '
			+'  onkeyup="diesasecharging_item(this);"   />'
			 +'<div  style="overflow-y:auto;height:150px;border:solid 1px;background:#ffffff;display:none;width:150px;marign 0px;"'
			 +'  onmouseout="charge_blur(this);"  onmouseover="charge_focus(this);"  ></div>'
			 +'</dd>'
			+'</dd>'
		 	+'<dd style="width:150px;"><input type="hidden" value=""/><input type="text" class="textinput" onblur="examintion_item_ss(this);" onclick="diesaseExamination_item(this)" onkeyup="diesaseExamination_item(this);" style="width:144px;height:26px"/>'
		 	+'<div style="overflow-y:auto;max-height:200px;border:solid 1px #3399cc;background:#ffffff;display:none;width:150px;marign 0px" onmouseout="examintion_item_blur(this);" onmouseover="examintion_item_focus(this);"></div></dd>'
			+'<dd><select id="condition" style="width: 80px;height: 28px;">'
			+'<option value=">">大于</option><option value=">=">大于等于</option><option value="<">小于</option>'
			+'<option value="<=">小于等于</option><option value="=">等于</option><option value="in">包含</option>'
			+'<option value="not in">不包含</option></select></dd>'
			+'<dd style="width:100px;"><input type="text" class="textinput" style="width:100%;"/></dd>'
			+'<dd style="width:40px;margin-left: 10px;">'
 			+'<input type="button" value="+" onclick="add_value(this);" style="width:20px;text-align: center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>'
 			+'<input type="button" value="-" onclick="del_value(this);" style="width:20px;text-align:center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>'
			+'</dd></dl>'
	$(obj).parent().parent().parent().append(str);
}
function del_value(obj){
	$(obj).parent().parent().remove();
}
function del_tioajian(obj){
	var tiaojian_index = 0;
	$(".tiaojian_id").each(function(){
		tiaojian_index ++;
	});
	if(tiaojian_index<=1){
		$.messager.alert('提示信息','条件最少添加一条，不能再删除条件。','error');
		return;
	}
	$(obj).parent().parent().parent().parent().remove();
	tiaojian_index = 0;
	$(".tiaojian_id").each(function(){
		$(this).children().eq(0).children().eq(0).html(tiaojian_name[tiaojian_index]);
		tiaojian_index ++;
	});
}
function add_tioajian(){
	var tiaojian_index = 0;
	$(".tiaojian_id").each(function(){
		tiaojian_index ++;
	});
	if(tiaojian_index >= 15){
		$.messager.alert('提示信息','条件最多添加十五条，不能再添加条件。','error');
		return;
	}
	var str = '<fieldset class="tiaojian_id" style="margin-left:10px;height:270px;width:550px;float:left;">'
			 +'<legend><strong>'+tiaojian_name[tiaojian_index]+'</strong></legend>'
			 +'<div class="user-query" style="font-size:14">'
			 +'<dl ></dt>'
			 +'<dd style="float: right;"><input type="button" value="删除条件" onclick="del_tioajian(this);" style="width:80px;text-align: center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/></dd></dl>'
			 +'<dl style="background: #cccccc;"><dt style="width:150px;">收费项目</dt><dt style="width: 150px;">检查项目</dt><dt style="width:30px;text-align: center;">条件</dt>'
			 +'<dt style="width:150px;text-align: center;">条件值</dt><dt style="width:60px;text-align: left">操作</dt>'
			 +'</dl></div><div style="height:200px;overflow: auto;">'
			 +'<dl style="margin-top: 5px;height: 30px;">'
			 +'<dd style="width:145px;"><input type="hidden" value=""/>'
			 +'<input type="text" class="textinput" style="200px;height:28px"  onblur="textclick(this);"   onclick="diesasecharging_item(this)"   '
			 +'  onkeyup="diesasecharging_item(this);"   />'
			 +'<div  style="overflow-y:auto;height:150px;border:solid 1px;background:#ffffff;display:none;width:150px;marign 0px;"'
			 +'  onmouseout="charge_blur(this);"  onmouseover="charge_focus(this);"  ></div>'
			 +'</dd>'
			 +'<dd style="width:150px;"><input type="hidden" value=""/><input type="text" class="textinput" onblur="examintion_item_ss(this);" onclick="diesaseExamination_item(this)" onkeyup="diesaseExamination_item(this);" style="width:144px;"/>'
			 +'<div style="overflow-y:auto;max-height:200px;border:solid 1px #3399cc;background:#ffffff;display:none;width:200px;marign 0px" onmouseout="examintion_item_blur(this);" onmouseover="examintion_item_focus(this);"></div></dd>'
			 +'<dd><select id="condition" style="width: 80px;height: 28px;">'
			 +'<option value=">">大于</option><option value=">=">大于等于</option><option value="<">小于</option>'
			 +'<option value="<=">小于等于</option><option value="=">等于</option><option value="in">包含</option>'
			 +'<option value="not in">不包含</option></select></dd>'
			 +'<dd style="width:100px;"><input type="text" class="textinput" style="width:100%;"/></dd>'
			 +'<dd style="width:40px;margin-left: 10px;">'
	 		 +'<input type="button" value="+" onclick="add_value(this);" style="width:20px;text-align: center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>'
			 +'</dd></dl>'
			 +'</div></fieldset>';
	$("#tiaojanpanel").append(str);
}
var streak="2";
/**
 * 收费项目下拉框获取焦点
 */
function charge_focus(obj){
	streak="1";
	console.log("收费项目获取焦点");
}
/**
 * 收费项目失去焦点
 */
function charge_blur(obj){
	console.log("收费项目失去焦点");
	streak="2";
	
}
//------------------------------------------------------保存危急值逻辑----------------------------------------------
function  saveWeijizhi(){
	if($('#dl').combobox('getValue')==0){
		$(this).next('span').find('input').focus();
		return;
	}
	if($('#zl').combobox('getValue')==0){
		$(this).focus();
		return;
	}
	if($('#jb').combobox('getValue')==0){
		$(this).focus();
		return;
	}  
	var items = new Array();
	var logic_index = 1;
    var fal = "Y";
    var fal_msg = "";
    var i = 0;
	$(".tiaojian_id").each(function(){
		var conditions = new Array;
		var con_index = 1;
		$(this).children().eq(2).children().each(function(){
			conditions.push({
				'charging_item_code':$(this).children().eq(0).children().eq(0).val(),
				'item_num':$(this).children().eq(1).children().eq(0).val(),
				'condition_value':$(this).children().eq(3).children().eq(0).val(),
				'condition':$(this).children().eq(2).children().eq(0).val(),
				'logic_index':con_index
			});
			if($(this).children().eq(0).children().eq(0).val()==''){
				fal = 'N';
				fal_msg = tiaojian_name[i]+"----＜收费项目必填项＞";
			}
			if($(this).children().eq(1).children().eq(0).val()=='' && $(this).children().eq(0).children().eq(0).attr('data') != '21'){
				fal = 'N';
				fal_msg =  tiaojian_name[i]+"----＜检查项目必填项＞";
			}
			if($(this).children().eq(3).children().eq(0).val()==''){
				fal = 'N';
				fal_msg =  tiaojian_name[i]+"＜条件值必填项＞";
			}
			con_index ++;
		});
		console.log(conditions);
		items.push({
			'logic_item_name':$(this).children().eq(0).children().eq(0).html(),
			'logic_index':logic_index,
			'itemConditions':JSON.stringify(conditions)
		});
		i++;
	});
	if(fal == 'N'){
		$.messager.alert("提示信息",fal_msg,"error");
		return;
	}
	var model = {
			'critical_class_parent_id':$("#dl").combobox('getValue'),//大类id
			'critical_class_id':$("#zl").combobox('getValue'),//子类id
			'critical_class_level':$("#jb").combobox('getValue'),//级别
			'ecl_id':$('#ecl_id').val(),
			'li':JSON.stringify(items),
			'disease_num':$('#disease_num').val(),
			'age_min':$('#age_min').val(),
			'age_max':$('#age_max').val(),
			'sex':$('input[name="sex"]:checked').val(),
			'critical_suggestion':$('#critical_suggestion').val()
	}
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	$.ajax({
		url:'saveExamCriticalLogic.action',
		data:model,
		type:'post',
		success:function(data){
			$('#examCriticalLogicTable').datagrid('reload');
			$(".loading_div").remove();
			$.messager.alert('提示信息',data);
			$('#dlg-edit').dialog('close');
		},
		error:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息','操作失败','error');
		}
	});
}
//疾病下拉框选择后关闭
function diseaseclike2(obj,disease_num){
	var disease_name=$(obj).text();
 	var disease_type= $(obj).parent().children().eq(0).val();
	$('#disease_id_type').val(disease_name);
	$('#disease_num').val(disease_num);
	$('#disease_xlk').css('display','none'); 
}
/**
 *疾病输入框失去焦点关闭下拉框
 */
function disease_click(obj){
	console.log(69);
	if(streak=="2"){
		var jianchaxiangmu = $(obj).parent().children().eq(0);
		$("#disease_xlk").css('display','none');
		
	}
}  
//--------------------------------------检查项目事件----------------------
/**
 * 检查项目下拉框失去焦点
 */
function examintion_item_blur(obj){
	streak="2";
	console.log("检查项目失去焦点");
}
/**
 * 检查项目下拉框获取焦点
 */
function examintion_item_focus(obj){
	console.log("检查项目--获取焦点 ");
	streak="1";
}
/**
 * 检查项目输入框失去焦点关闭下拉框
 */
function  examintion_item_ss(obj){
	if(streak=='2'){
		var jcxm = $(obj).parent().children().eq(2);
		$(jcxm).hide();
	}
}
/**
 * 检查项目选择后关闭下拉框
 */
function examintion_item_click2(obj,value){
	var zhi = $(obj).text();
	$(obj).parent().parent().parent().children().eq(0).val(value);
	$(obj).parent().parent().parent().children().eq(1).val(zhi);
	//下拉框关闭
	var xlk = $(obj).parent().parent();
	$(xlk).hide();
}
/**
 * 获取所有检查项目
 */
function diesaseExamination_item(ths){
		var ob = $(ths).parent().children().eq(0);
		$(ths).css('display','block')
		var weizhi = $(ob).offset().top;
			weizhi = weizhi-200;
		$(ths).css('top',weizhi)
		//alert($(ths).parent().parent().children().children().eq(0).html());
		var charging_item_code = $(ths).parent().parent().children().eq(0).children().eq(0).val();
		var model = {'e_item_name':$(ths).val(),'charging_item_code':charging_item_code}
		$.ajax({
			url:'getDiseaseExaminationItem.action',
			data:model,
			type:'post',
			success:function(data){
				var obj=eval('('+data+')');
			 	var str="<ul style='text-align:left'>";
			   	for(var i=0;i<obj.length;i++){
			   		str+="<li style='padding-left:5px' "
		   			str+='onclick="examintion_item_click2(this,\''+obj[i].item_num+'\');">'+obj[i].item_name+'</li>'
			   	}
			   	str+="</ul>";
			   	$(ths).parent().children().eq(2).html(str);
				$(ths).parent().children().eq(2).show();
			}
		});
}
//-------------------------------------------------------收费项目-----------------------------
/**
 * 获取收费项目
 */
function diesasecharging_item(ths){
	var ob = $(ths).parent().children().eq(0);
	$(ths).css('display','block')
	
	var weizhi = $(ob).offset().top;
		weizhi = weizhi-200;
	$(ths).css('top',weizhi);
	if($(ths).val()==''){
		$(ths).parent().children().eq(0).val('');
	}
	model = {c_item_name:$(ths).val()}
	$.ajax({
		url:'getDiseaseChargingItem.action',
		type:'post',
		data:model,
		success:function(data){
		   	var obj=eval('('+data+')');
		   	var str="<ul  style='text-align:left;' >";
		   	for(var i=0;i<obj.length;i++){
		   		str+="<li  style='padding-left:5px' "
		   			str+=" onclick='textclick2(this,\""+obj[i].item_code+"\",\""+obj[i].dep_category+"\")'>"+obj[i].item_name+"</li>"
		   	}
		   	str+="</ul>";
			$(ths).parent().children().eq(2).html(str);
			$(ths).parent().children().eq(2).show();
		}
	})  
}
/**
 * 收费项项输入框失去焦点关闭下拉框
 */
function textclick(obj){
/* 	var sfxm = $(obj).parent().children().eq(2);
	$(sfxm).css('display','none'); */
	if(streak=="2"){
		var sfxm = $(obj).parent().children().eq(2);
		$(sfxm).css('display','none');
		
	}
}
/**
 * 下拉框选择后关闭
 */
function textclick2(obj,value,dep_category){
	var zhi = $(obj).text();
	var srk=$(obj).parent().parent().parent().children().eq(1);
	$(srk).val(zhi);
	$($(obj).parent().parent().parent().children().eq(0)).val(value);
	$($(obj).parent().parent().parent().children().eq(0)).attr("data",dep_category);
	if(dep_category == '21'){
		$($(obj).parent().parent().parent().parent().children().eq(1).children().eq(1)).attr("disabled", true);
	}else{
		$($(obj).parent().parent().parent().parent().children().eq(1).children().eq(1)).attr("disabled", false);
	}
	//下拉框节点
	var xlk = $(obj).parent().parent();
	$(xlk).css('display','none');
}
//--------------------------------------js生成动态条件样式-----------------------------------------------

function getDisease_exam_item(id){
	 $.ajax({
		url:'getExamCriticalLogicItemList.action?ecl_id='+id,
		cache:false, 
		async:false,
		type:'post',
		success:function(data){
			var obj =eval('('+data+')');
			for(i=0;i<obj.length;i++){
				var itemCondition = obj[i].itemConditions;
				add_tioajian();
				for(j=0;j<itemCondition.length;j++){
 					if(j>0){
						add_value($(".tiaojian_id").eq(i).children().eq(2).children().eq(j-1).children().eq(2).children().eq(0));
					} 
					$(".tiaojian_id").eq(i).children().eq(2).children().eq(j).children().eq(0).children().eq(0).val(itemCondition[j].condition_type);
					$(".tiaojian_id").eq(i).children().eq(2).children().eq(j).children().eq(0).children().eq(0).val(itemCondition[j].charging_item_code);
					$(".tiaojian_id").eq(i).children().eq(2).children().eq(j).children().eq(0).children().eq(1).val(itemCondition[j].charging_item_name);
					if(itemCondition[j].item_num != ''){
						$(".tiaojian_id").eq(i).children().eq(2).children().eq(j).children().eq(1).children().eq(0).val(itemCondition[j].item_num);
						$(".tiaojian_id").eq(i).children().eq(2).children().eq(j).children().eq(1).children().eq(1).val(itemCondition[j].item_name);
					}else{
						$(".tiaojian_id").eq(i).children().eq(2).children().eq(j).children().eq(1).children().eq(1).attr("disabled", true);
						$(".tiaojian_id").eq(i).children().eq(2).children().eq(j).children().eq(0).children().eq(0).attr("data",'21');
					}
					$(".tiaojian_id").eq(i).children().eq(2).children().eq(j).children().eq(2).children().eq(0).val(itemCondition[j].condition);
					$(".tiaojian_id").eq(i).children().eq(2).children().eq(j).children().eq(3).children().eq(0).val(itemCondition[j].condition_value);
				}
				
				$(".tiaojian_id").eq(i).children().eq(0).children().eq(0).html(obj.logic_item_name);
				$(".tiaojian_id").eq(i).children().eq(1).children().eq(0).children().eq(1).children().eq(0).val(obj.critical_flag);
			}
		},
		error:function(data){
			$.messager.alert('提示信息','操作失败','error');
		}
	});
}
//疾病
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
	   			str+="  onclick='diseaseclike2(this,\""+obj[i].disease_num+"\")'  ><input type='hidden' value=''"+obj[i].disease_name_type+"/>"+obj[i].disease_name+"</li>"
		   	}
		   	str+="</ul>";
		   	$('#disease_xlk').html(str); 
		}
	}) 
	
}
</script>
<style>
.formdiv dl {
    height:23px 
}
</style>
<input type="hidden" id="critical_class_parent_id" value='<s:property value="critical_class_parent_id"/>' />
<input type="hidden" id="critical_class_id" value='<s:property value="critical_class_id"/>' />
<input type="hidden" id="critical_class_level_g" value='<s:property value="critical_class_level_g"/>' />
<input type="hidden" id="ecl_id" value='<s:property value="ecl_id"/>' />
<input type="hidden" id="h_critical_suggestion" value='<s:property value="critical_suggestion"/>' />
<input type="hidden" id="h_disease_type" value='<s:property value="disease_type"/>' />
<div class="formDiv" style="margin-top: 15px">
	 <fieldset style=" margin: 10px;">
		<legend><strong>基本信息</strong></legend> 
		<div onmouseout="charge_blur(this);" >
		</div>
		<div style="float: left;" >
		 <dl>
			<dt style="margin-left:0px;width:60px" ><font color="#FF0000">*</font>大类</dt>
			<dd><input type="text" id="dl" name="dl" value='<s:property value="parent_id" />' style="height: 26px;width:150px"/></dd>
			<dt style="width: 80px"><font color="#FF0000">*</font>子类</dt>
			<dd><input type="text" id="zl" value='<s:property value="id" />' name="zl"  style="height: 26px;width:150px"/></dd>
			<dt  style="width:60px"><font color="#FF0000">*</font>级别</dt>
			<dd><input type="text" id="jb" value='<s:property value="critical_class_level" />' name="jb"  style="height: 26px;width:100px"/></dd>
		</dl> 
		 <dl>
		 	<dt style="width:60px">
				类型
			</dt>
			<dd style="width:150px">
				<input type="radio"     name="disease_logic"  id="disease_logic_Y"  value="Y"  checked="true" />&nbsp;阳性指标&nbsp;&nbsp;&nbsp;
				<input type="radio" name="disease_logic" id="disease_logic_N"  value="N" />&nbsp;疾病&nbsp;&nbsp;&nbsp;
			</dd>
		 	<dt style="width:80px">
				生成疾病 
			</dt>
			<dd >
				<input type="hidden"  id='disease_num' style="width:330px"  value="<s:property value='disease_num'/>"  />
				<input   type="text"  onblur="disease_click(this);" id="disease_id_type" style="width:330px"  value="<s:property value='disease_name'/>"  onclick="getDiseaseKnowloedge(this);"  onkeyup="getDiseaseKnowloedge(this);"
					  class="textinput"/>
				<div  id="disease_xlk"    onmouseout="disease_blur(this);"     onmouseover="disease_focus(this);"		 style="width:330px; position:absolute;overflow-y:auto;height:150px;border:solid 1px;z-index:15;background:#ffffff;display:none;marign 0px"></div>
			</dd>
		 </dl>
		 <dl>
		 	<dt style="width:60px">
				性别
			</dt>
			<dd style="width:150px">
				<input type="radio" name="sex" checked="checked" value="全部" />全部&nbsp;&nbsp;
				<input type="radio" name="sex" value="男" />男&nbsp;&nbsp;
				<input type="radio" name="sex" value="女" />女
			</dd>
			<dt style="width:80px">
				年龄 
			</dt>
			<dd >
				<input   type="text"   id="age_min" style="width:155px"  value='<s:property value="age_min"/>'  class="textinput"/>
				  至
			    <input   type="text"   id="age_max" style="width:155px" value='<s:property value="age_max"/>'  value="100"  class="textinput"/>
			</dd>
		 </dl>
		</div>
		<div>
		<dl>
			<dt style="width: 40px">
				建议
			</dt>
			<dd>
				<textarea id="critical_suggestion" style="width:445px;height:100px"></textarea>
			</dd>
		</dl>
		</div>
	</fieldset>
	
</div>
<fieldset style="margin: 10px;margin-bottom:50px;min-height:300px;overflow:hidden">
	<legend><strong>逻辑条件设置&nbsp;&nbsp;&nbsp;<font style="color: #fc0606;font-size: 12px;">
	注：条件和条件之间为或者(or)的关系，条件值和条件值之间为并且(and)的关系</font>
	<font style="color: #cecece;font-size: 13px;">———————————————————————————</font>
	<input type="button" value="增加条件" onclick="add_tioajian();" style="width:80px;text-align: center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>
	</strong></legend> 
	<div id="p">
		<div id="tiaojanpanel"></div>
	</div>	
</fieldset> 
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:saveWeijizhi();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
