<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
li:hover{
    background:#c9daf8;
    display:block;
} 
</style>
<script type="text/javascript">
var tiaojian_name = ['条件一','条件二','条件三','条件四','条件五','条件六','条件七','条件八','条件九','条件十','条件十一','条件十二','条件十三','条件十四','条件十五'];
$(function (){
	$('#sex').combobox({
		width:143,
		height:26,
		panelHeight:'auto',
	    valueField:'value',    
	    textField:'label',
	    data:[{
				label: '全部',
				value: '全部'
			},{
				label: '男',
				value: '男'
			},{
				label: '女',
				value: '女'
			}],
		onLoadSuccess:function(data){
			$("#sex").combobox('setValue',data[0].value);
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
	var id =$("#zid").val();
	if(id != ''){
		getDisease_exam_item(id);
	}else{
		add_tioajian();
	}
});
//----------------------------------------------------------获取数据-------------------------------
/**
 * 获取所有疾病
 */
function getDiseaseKnowloedge(obj){
	var model = {disease_type:'Y',disease_name:$(obj).val(),dep_id:$("#zdep_id").val()}
	$.ajax({
		url:'getDiseaseKnowloedge.action',
		data:model,
		type:'post',
		success:function(data){
			var obj=eval('('+data+')');
		 	var str="<ul  style='text-align:left'>";
		   	for(var i=0;i<obj.length;i++){
		   		str+="<li   style='padding-left:5px' "
	   			str+='onclick="diseaseclike(this,\''+obj[i].disease_num+'\')" ><input type="hidden" value="'+obj[i].disease_name_type+'"/>'+obj[i].disease_name+'</li>'
		   	}
		   	str+="</ul>";
		   	$('#disease_xlk').html(str); 
		   	$('#disease_xlk').css('display','block');
		}
	}) 
	
}

/**
 * 获取所有检查项目
 */
function diesaseExamination_item(ths){
	var model = {e_item_name:$(ths).val(),dep_id:$("#zdep_id").val()}
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
		   	var ob = $(ths).parent().children().eq(2);
		   	$(ob).html(str);
			$(ob).css('display','block');
		}
	}); 
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
		var jcxm = $(obj).parent().children().eq(2);
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
	$("#exam_item_id").val(value);
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

//------------------------------------------------------保存疾病逻辑----------------------------------------------
function  adddisease_logic(){
	if($("#exam_item_id_type").val()==""||$("#exam_item_id").val()<1){
		$.messager.alert('提示信息','请选择检查项目！','error');
		$('#exam_item_id_type').focus();
		return;
	}
	if($("#disease_id_type").val()==""||$("#disease_id").val()<1){
		$.messager.alert('提示信息','请选择疾病！','error');
		$('#disease_id_type').focus();
		return;
	}
	var items = new Array();
	var logic_index = 1;
	$(".tiaojian_id").each(function(){
		var conditions = new Array;
		var con_index = 1;
		$(this).children().eq(2).children().each(function(){
			conditions.push({
				'logic_index':con_index,
				'condition':$(this).children().eq(0).children().eq(0).val(),
				'condition_value':$(this).children().eq(1).children().eq(0).val()
			});
			con_index ++;
		});
		items.push({
			'logic_item_name':$(this).children().eq(0).children().eq(0).html(),
			'logic_index':logic_index,
			'critical_flag':$(this).children().eq(1).children().eq(0).children().eq(1).children().eq(0).val(),
			'itemConditions':JSON.stringify(conditions)
		});
	});
	var model = {
			'ids':$("#zid").val(),
			'disease_num':$("#disease_id").val(),
			'logic_name':$("#disease_id_type").val(),
			'item_num':$("#exam_item_id").val(),
			'sex':$("#sex").combobox('getValue'),
			'age_min':$("#age_min").val(),
			'age_max':$("#age_max").val(),
			'logic_class':0,
			'li':JSON.stringify(items)
	}
	$.ajax({
		url:'saveDiseaseLogicSingle.action',
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
	var str = '<fieldset class="tiaojian_id" style="margin-left:10px;height:270px;width:300px;float:left;">'
			 +'<legend><strong>'+tiaojian_name[tiaojian_index]+'</strong></legend>'
			 +'<div class="user-query" style="font-size:14">'
			 +'<dl ><dd style="display:none;">是否危机： </dd><dt style="display:none;"><select style="width: 60px;"><option value="0">否</option><option value="1">是</option></select></dt>'
			 +'<dd style="float: right;"><input type="button" value="删除条件" onclick="del_tioajian(this);" style="width:80px;text-align: center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/></dd></dl>'
			 +'<dl style="background: #cccccc;"><dt style="width:30px;text-align: center;">条件</dt>'
			 +'<dt style="width:200px;text-align: center;">条件值</dt><dt style="width:60px;text-align: left">操作</dt>'
			 +'</dl></div><div style="height:200px;overflow: auto;">'
			 +'<dl style="margin-top: 5px;height: 30px;"><dd><select id="condition" style="width: 80px;height: 28px;">'
			 +'<option value=">">大于</option><option value=">=">大于等于</option><option value="<">小于</option>'
			 +'<option value="<=">小于等于</option><option value="=">等于</option><option value="in">包含</option>'
			 +'<option value="not in">不包含</option></select></dd>'
			 +'<dd style="width:150px;"><input type="text" class="textinput" style="width:100%;"/></dd>'
			 +'<dd style="width:40px;margin-left: 10px;">'
	 		 +'<input type="button" value="+" onclick="add_value(this);" style="width:20px;text-align: center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>'
			 +'</dd></dl>'
			 +'</div></fieldset>';
	$("#tiaojanpanel").append(str);
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
function add_value(obj){
	var str = '<dl style="margin-top: 5px;height: 30px;"><dd><select id="condition" style="width: 80px;height: 28px;">'
			+'<option value=">">大于</option><option value=">=">大于等于</option><option value="<">小于</option>'
			+'<option value="<=">小于等于</option><option value="=">等于</option><option value="in">包含</option>'
			+'<option value="not in">不包含</option></select></dd>'
			+'<dd style="width:150px;"><input type="text" class="textinput" style="width:100%;"/></dd>'
			+'<dd style="width:40px;margin-left: 10px;">'
 			+'<input type="button" value="+" onclick="add_value(this);" style="width:20px;text-align: center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>'
 			+'<input type="button" value="-" onclick="del_value(this);" style="width:20px;text-align:center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/>'
			+'</dd></dl>'
	$(obj).parent().parent().parent().append(str);
}

function del_value(obj){
	$(obj).parent().parent().remove();
}
function getDisease_exam_item(id){
	$.ajax({
		url:'getDiseaseLogicSingleById.action?ids='+id+'&logic_class=0',
		cache:false, 
		async:false,
		type:'post',
		success:function(data){
			var obj =eval('('+data+')');
			
			$("#disease_id").val(obj.disease_num);
			$("#disease_id_type").val(obj.logic_name);
			$("#exam_item_id").val(obj.item_num);
			$("#exam_item_id_type").val(obj.item_name);
			$("#sex").combobox('setValue',obj.sex);
			$("#age_min").val(obj.age_min);
			$("#age_max").val(obj.age_max);
			for(i=0;i<obj.logicItem.length;i++){
				var logicItem = obj.logicItem[i];
				add_tioajian();
				for(j=0;j<logicItem.itemCondition.length;j++){
					if(j>0){
						add_value($(".tiaojian_id").eq(i).children().eq(2).children().eq(j-1).children().eq(2).children().eq(0));
					}
					$(".tiaojian_id").eq(i).children().eq(2).children().eq(j).children().eq(0).children().eq(0).val(logicItem.itemCondition[j].condition);
					$(".tiaojian_id").eq(i).children().eq(2).children().eq(j).children().eq(1).children().eq(0).val(logicItem.itemCondition[j].condition_value);
				}
				$(".tiaojian_id").eq(i).children().eq(0).children().eq(0).html(logicItem.logic_item_name);
				$(".tiaojian_id").eq(i).children().eq(1).children().eq(0).children().eq(1).children().eq(0).val(logicItem.critical_flag);
			}
		},
		error:function(data){
			$.messager.alert('提示信息','操作失败','error');
		}
	});
}
</script>
<input type="hidden" id='zid' value="<s:property value='ids'/>"  />
<input type="hidden" id='zdep_id' value="<s:property value='dep_id'/>"  />
 <fieldset style=" margin: 10px;padding-left: 0px">
	<legend><strong>单项阳性逻辑编辑</strong></legend> 
	<div class="user-query">
		<dl>
			<dt>检查项目：</dt>
			<dd>
				<input type="hidden" id='exam_item_id' value="<s:property value='exam_item_id'/>"/>
				<input type="text" id="exam_item_id_type" class="textinput" onblur="examintion_item_ss(this);" onclick="diesaseExamination_item(this)" onkeyup="diesaseExamination_item(this);" style="width:400px;"/>
				<div id="jianchaxiangmu" style="overflow-y:auto;max-height:200px;border:solid 1px #3399cc;position:relative;position:absolute;z-index:8;background:#ffffff;display:none;width:400px;marign 0px" onmouseout="examintion_item_blur(this);" onmouseover="examintion_item_focus(this);"></div>
			</dd>
			<dt>适用性别：</dt>
			<dd><select id="sex"></select></dd>
		</dl>
		<dl>
			<dt>生成阳性：</dt>
			<dd>
				<input type="hidden" id='disease_id' value="<s:property value='disease_id'/>"/>
				<input type="text" onblur="disease_click(this);" id="disease_id_type" onclick="getDiseaseKnowloedge(this);" onkeyup="getDiseaseKnowloedge(this);" class="textinput" style="width:400px;"/>
				<div id="disease_xlk" onmouseout="disease_blur(this);" onmouseover="disease_focus(this);" style="width:400px; position:absolute;overflow-y:auto;max-height:200px;border:solid 1px #3399cc;z-index:15;background:#ffffff;display:none;marign 0px"></div>
			</dd>
			<dt>适用年龄：</dt>
			<dd>
				<input class="textinput" id="age_min" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g,'')" onpaste="this.value=this.value.replace(/\D/g,'')" value="1" data-options="prompt:'最小年龄'"/>至
				<input class="textinput" id="age_max" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g,'')" onpaste="this.value=this.value.replace(/\D/g,'')" value="100" data-options="prompt:'最大年龄'"/>
			</dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 10px;margin-bottom:50px;height:300px">
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
	    <a href="javascript:adddisease_logic();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>